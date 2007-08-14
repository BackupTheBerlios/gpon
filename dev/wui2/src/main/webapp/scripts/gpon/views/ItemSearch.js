var ITEMSEARCH_RENDER_STAGE_ID = 'ItemSearchRenderStage';

/*
 switch tabs:
 
 this.tabView.set('activeTab',this.resultTab); 

 */

var ItemSearch = 
new Class({
  /*
   * whole panel is associated to this 
   * item type
   */
  typeId: null,
  type:   null,
  /* result list
   */
  resultSet: [],
  /* 
   * this element contains the type specific search dialog
   */
  searchDialogEl: null,
  /*
   * 
   */
  searchResultEl: null,
  /*
   * TabView element(s)
   */
  tabView: null, 
  queryTab: null,
  resultTab: null,
  /*
   * represents the current query
   */
  query: null,
  /*
   * ctor
   */
  initialize: function(config) 
  {
    this.dataService = config.dataService;  
  	this.createEvent("editItem");
  },
  _getRenderArea: function() 
  {
    if ($(ITEMSEARCH_RENDER_STAGE_ID)==null) 
    {
		var el = new Element('div'); 
		el.setStyles('width: 0px; height: 0px; display: none');
		el.id = ITEMSEARCH_RENDER_STAGE_ID;
		el.injectInside($(document.body));   
    }
    
   	return $(ITEMSEARCH_RENDER_STAGE_ID);
  },
  getElement: function()
  {
    var tabContainer = new Element('div').injectInside(this._getRenderArea());
    tabContainer.setStyle('height','300px');
  
    this.tabView = new YAHOO.widget.TabView();
    
    this.queryTab = new YAHOO.widget.Tab({
        label: 'Query',
        // content: '<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat.</p>',
        contentEl: this.getQueryTabElement(),
        active: true
    });
    
    this.tabView.addTab(this.queryTab); 
    
    
    
    this.resultTab = new YAHOO.widget.Tab({
        label: 'Results',
        contentEl: this.getResultTab()
    }) 
    
    this.tabView.addTab(this.resultTab);
        
    this.tabView.appendTo(tabContainer);
    
    return tabContainer;
  },
  getQueryTabElement: function() 
  {
    // read all types
  	var itemTypes = this.dataService.getAllItemTypes();
  	var elem = new Element('div');
  	// diplay a type pull down
  	var typeChooser = new GponFormElement(
  	 {
  	  targetObject: this,
	  targetPropertyExpr: 'typeId',
	  targetPropertyType: 'integer'
  	 });
  	// a label function
  	var myLabelFunc = function (elem) { return elem.description+' ('+elem.id+')'};
  	// enumeration base -> form element becomes a pull down now
  	var typesBase = GponBasicService.toKeyValueList(itemTypes, {srcValue: myLabelFunc}); 
    typeChooser.setEnumBase(typesBase);
    typeChooser.getInputNode().injectInside(elem);
    // wire it
    typeChooser.subscribe('propertyChanged', this.displaySearchDialog.bind(this));
    // remember the search dialog element
    this.searchDialogEl = new Element('div').injectInside(elem).appendText('SEARCH DIALOG');
    return elem; 
  },
  getResultTab: function() 
  {
  	this.searchResultEl = new Element('div').appendText('Please go to the Query tab!');
    return this.searchResultEl;
  },
  /** builds the search dialog according to current item types prop decls.
   */
  displaySearchDialog: function () 
  {
   this.type =
    this.dataService.getItemTypeById(this.typeId);
    
   this.query = {};
   // 
   this.query.criteria = {};
    
   // clear search dialog
   this.searchDialogEl.empty();
   
   // lines of the form: <property name> <op> <value input> 
   var table = new Element('table');
   var tbody = new Element('tbody').injectInside(table);
   
   var rowBuilderFnc = function (el,index) 
     {
       // back object
       this.query.criteria[el.name] = {}; 
       this.query.criteria[el.name].op = '=';
       
       // table row
       var row = new Element('tr').injectInside(tbody);
       // label
       var labelEl = new Element('td').injectInside(row);
       labelEl.appendText(el.description);
       // op
       var opEl = new Element('td').injectInside(row);
       var opChooser = new GponFormElement(
  	    {
  	      targetObject: this.query,
	      targetPropertyExpr: 'criteria[\''+el.name+'\'].op',
	      targetPropertyType: 'shorttext'
  	    });
       
       opChooser.setEnumBase([{text:'<', value: '<'},
                            {text:'=', value: '='},
                            {text:'>', value: '>'}]);
      
       opChooser.getInputNode().injectInside(opEl);
      
       // value
       var valueEl = new Element('td').injectInside(row);
       var valueInput = new GponFormElement(
  	    {
  	      targetObject: this.query,
	      targetPropertyExpr: 'criteria[\''+el.name+'\'].value',
	      targetPropertyType: 'shorttext'
  	    });
       valueInput.getInputNode().injectInside(valueEl);
    }
   // apply row building function
   this.type.itemPropertyDecls.forEach(rowBuilderFnc.bind(this));
   
   table.injectInside(this.searchDialogEl);
   // search button
   var searchButton = new YAHOO.widget.Button({ 
	                                        type: "button",  
	                                        label: "search",  
	                                        container: this.searchDialogEl  
	                                    });
	                                    
   searchButton.on("click", this.search.bind(this)); 
  },
  displaySearchResult: function() 
  {
   // clear result tab
   this.searchResultEl.empty(); 
   
   var pageLinksTop    = new Element('div').injectInside(this.searchResultEl).addClass('pagelinks-top');
   var elem = new Element('div').injectInside(this.searchResultEl).addClass('gpon-ui-i-tab');
   var pageLinksBottom = new Element('div').injectInside(this.searchResultEl).addClass('pagelinks-bottom');
   
   var mapped = this.resultSet.map(this.dataService.itemToIpdByIdMap);
   
   /*
    var myColumnHeaders = [
     {key:"lnr_g",      text: " ", formatter: myFnc},
     {key:"lnr_g",      text: "Nr.",        sortable:true, resizeable:true},
     {key:"werktitel",  text: 'Titel',      sortable:true, resizeable:true},
     {key:"bearbeiter", text: 'Bearbeiter', sortable:true, resizeable:true},
     {key:"kompo",      text: 'Komponist',  sortable:true, resizeable:true},
     {key:"interpret",  text: "Interpret",  sortable:true, resizeable:true}
    ];
    */
   // FIXME: formatters should be defined in GponBasicService (accessible by ipd id) 
   var fooFmt = function (elCell, oRecord, oColumn, oData) 
   { 
    $(elCell).empty();
    
    if (oData && oData.value) {
     $(elCell).appendText(oData.value);
    }
   };
    
   var actionFmt = function (elCell, oRecord, oColumn, oData) 
   {   
       var link = new Element('a');
  	   link.appendText('edit '+oData);
  	   link.href='#';
	   link.onclick = function() 
	   {
	     this.fireEvent("editItem", { id: oData });
	   }.bind(this);
	   $(elCell).empty();
	   link.injectInside($(elCell));
    
   }; 

   var typeFmt = function (elCell, oRecord, oColumn, oData) 
   { 
    $(elCell).empty().appendText(this.dataService.getItemTypeById(oData).name);
   }; 

    
   var myColumnHeaders = [];
   var myFields        = [];
   
   myColumnHeaders.push({key: 'id', label: 'ID', sortable:true, resizeable:true});
   myFields.push('id');
   myColumnHeaders.push({key: 'typeId', label: 'Type', sortable:true, resizeable:true,formatter: typeFmt.bind(this)});
   myFields.push('typeId');
   
   
   addColumnHeaderFnc = function(element, id) 
   {
       // el is a remoteItemPropertyDecl object
   	   myColumnHeaders.push(
   	     {
   	       key: 'ipd'+element.id,
   	       label: element.description,
   	       sortable:true, 
   	       resizeable:true,
   	       formatter: fooFmt
   	     }
   	   );
   	   
   	   myFields.push('ipd'+element.id);
   }
   
   this.type.itemPropertyDecls.forEach(addColumnHeaderFnc.bind(this));
   
   
   myColumnHeaders.push({key: 'id', label: 'actions', formatter: actionFmt.bind(this)});
   myFields.push('id');
   
   
    
   var myColumnSet = new YAHOO.widget.ColumnSet(myColumnHeaders);

   var myDataSource = new YAHOO.util.DataSource(mapped);
   myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSARRAY;
   myDataSource.responseSchema = {
     fields: myFields
   };
   var myDataTable = new YAHOO.widget.DataTable(elem, myColumnSet, myDataSource, 
	   {paginator: true, 
		  rowsPerPage: 5, 
			paginatorOptions: {
			 containers: [pageLinksTop,pageLinksBottom]
			}
		 });
   
   
   
   // build up a result DataTable
   // var back = new Element('div').appendText('Back to Query').setStyles('background-color:white; text-align:center').injectInside(this.searchResultEl);
   // YAHOO.util.Event.addListener(back,'mouseover',this.switchToQuery.bind(this));
  },
  /*
   * searches
   */
  search: function() 
  {
    if (this.query && this.query.criteria) 
    {
      var queryParts = [];
      for (prop in this.query.criteria) 
      {
        // get criterion object criterion = { op,  value }
        var criterion = this.query.criteria[prop];
        if (criterion.value && criterion.value.length > 0) 
        {
          // FIXME: type distinction
          queryParts.push('${'+prop+'} '+ criterion.op +' \''+criterion.value+'\'');
        }       
      }
    }
    else 
    {
     return;
    }
    
    // form remote query object
    var remoteQuery = {};
    remoteQuery.typeId = this.typeId;
    if (queryParts.length > 0) 
    {
     remoteQuery.spec = queryParts.join(" and ");
    }
    
    // fire 
    this.resultSet = [];
    this.resultSet = this.dataService.searchItems(remoteQuery);
    
    // alert("row count: "+this.resultSet?this.resultSet.length:0);
    
    this.displaySearchResult();
    
    this.tabView.set('activeTab',this.resultTab); 
  },
  switchToQuery: function() 
  {
   this.tabView.set('activeTab',this.queryTab); 
  } 
});


// add YAHOO event handler stuff
YAHOO.augment(ItemSearch, YAHOO.util.EventProvider);

