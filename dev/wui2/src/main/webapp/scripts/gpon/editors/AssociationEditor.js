var ASSOCEDITOR_RENDER_STAGE_ID = 'AssocEditorRenderStage';

AssociationEditor = 
new Class({
 // current item type
 item: null, /* RemotItemType */
 associationType: null, /* RemoteAssociationType */
 reverse: null, /* boolean */
 multiplicity: null , /* 'one', 'many' */
 associatedItemIds: null, /* array of associated items */
 currentSelectedItemId: null, /* selected via ac_field */
 associatedItemsDisplay: null, /* widget to display associated items */
 initialize: function(config) 
 {
   /* 
    * 
    */
   this.associationType = config.associationType;
   this.reverse = config.reverse;
   this.multiplicity = 'many';
   this.mapKey       = config.mapKey;
   
   if (this.associationType.multiplicity == 'OneToMany' &&
       this.reverse) 
   {
	    this.multiplicity = 'one';
   }

   this.dataService = config.dataService;
   this.createEvent("propertyChanged");
   this.createEvent("syncedWithDB");
   this.associatedItemIds = [];
   this.otherItemType = 
     this.dataService.getItemTypeById(
      (this.reverse)?
         this.associationType.itemATypeId:
         this.associationType.itemBTypeId);
 },
 setAssociatedItemIds: function(ids)
 {
 	this.associatedItemIds = ids;
 },
 getAssociatedItemIds: function() 
 {
 	return this.associatedItemIds;
 },
 getInputNode: function() 
 {
   var elem = new Element('div').injectInside(this._getRenderArea());
   elem.appendText('ae: '+this.associationType.name+' ');
   if (this.reverse) 
   {
     elem.appendText(this.associationType.itemARoleName);
   }
   else 
   {
     elem.appendText(this.associationType.itemBRoleName);
   }
    
   // display all associated objects in a 
   // datatable
   // an autocompleter field to add new items
   var ac = new Element('div').injectInside($(document.body)).addClass('gpon-autocomplete');
   var ac_input = new Element('input').injectInside(ac);
   ac_input.type = "text";
   var ac_container = new Element('div').injectInside(ac);
   
   var oACDS = new YAHOO.widget.DS_JSFunction(this.getItemsForAutoCompletion.bind(this)); 
   oACDS.maxCacheEntries = 0; 
   // Instantiate AutoComplete 
  var oAutoComp = 
   new YAHOO.widget.AutoComplete(ac_input,ac_container, oACDS); 
  // oAutoComp.alwaysShowContainer = true; 
  oAutoComp.minQueryLength = 0; 
  oAutoComp.useShadow = true; 
  oAutoComp.forceSelection = false; 
  oAutoComp.maxResultsDisplayed = 50; 
  var me = this;
  oAutoComp.formatResult = function(oResultItem, sQuery) { 
	        var sMarkup = oResultItem[0]; 
	        return sMarkup;
  }; 

  // Show custom message if no results found
  var myOnDataReturn = function(sType, aArgs) {
        var oAutoComp = aArgs[0];
        var sQuery = aArgs[1];
        var aResults = aArgs[2];

        if(aResults.length == 0) {
            oAutoComp.setBody("<div>No matching results</div>");
        }
    };
    
    // display associated items with typic columns
    me.associatedItemsDisplay = 
     new Element('div').injectInside(elem);
    
    me.refreshAssociatedItems();
    
    var addButton = new Element('button').appendText('<<').injectInside(elem);
    
    addButton.onclick = function() 
    {
     var obj = me;
    
     if (me.currentSelectedItemId &&
         me.currentSelectedItemId > 0) 
     {
       if (!me.associatedItemIds.contains(me.currentSelectedItemId)) 
       {
         if (me.multiplicity == 'one') 
         {
         	me.associatedItemIds = [];
         }
         me.associatedItemIds.push(me.currentSelectedItemId);
         me.currentSelectedItemId = 0;
         me.refreshAssociatedItems();
         oAutoComp._clearSelection();
         me.fireEvent("propertyChanged",me);
         
       }
     }
    }
    
    var itemSelectHandler = function(sType, aArgs) { 
        var oMyAcInstance = aArgs[0]; // your AutoComplete instance 
	    var elListItem = aArgs[1]; //the <li> element selected in the suggestion 
	                               //container 
	    var aData = aArgs[2]; //array of the data for the item as returned by the DataSource 
	    
	    me.currentSelectedItemId= aData[1].id
	    
	    alert('id is '+aData[1].id);
	}; 
   
    oAutoComp.dataReturnEvent.subscribe(myOnDataReturn);
    oAutoComp.itemSelectEvent.subscribe(itemSelectHandler); 
  // a button to add 
  // oAutoComp.alwaysShowContainer = true;
  ac.injectInside(elem);
  return elem;
 },
 getItemsForAutoCompletion: function(sQuery) 
 {
   var list = this.dataService.searchItemsFulltext(this.otherItemType.id, sQuery);
  
  var aResults = [];
   
  if (list && list.length > 0) 
  {
   for (var i = 0; i <  list.length; i++) 
    {
     var itemWrapper =
	   new GponItemWrapper(list[i], this.otherItemType); 
     // display comma seperated list of specific attributes
     aResults.push([itemWrapper.getSpecificValues().join(','),list[i]]);
    }
  }
  
  return aResults;
 },
 // refresh the associated items display
 refreshAssociatedItems: function() {
 
   var me = this; 
   var typeId = (this.reverse)?this.associationType.itemATypeId:
      		                this.associationType.itemBTypeId;
   var type   = this.dataService.getItemTypeById(typeId);   		                
   // retrieve all items via data service
   
   var associatedMappedObjects = [];
   
   if (this.associatedItemIds && 
       this.associatedItemIds !=null && 
       this.associatedItemIds.length > 0) 
   {
     var items =
      this.associatedItemIds.map
      (
      	function(element, index) 
      	{
      		var item = me.dataService.getItemById(element);
      		// cast
      		item.typeId = 
      		 typeId;
      		return item;
      	}     
      )
 
 	  associatedMappedObjects = items.map(this.dataService.itemToIpdByIdMap);
 
   }
   
   var fooFmt = function (elCell, oRecord, oColumn, oData) 
   { 
    $(elCell).empty();
    
    if (oData && oData.value) {
     $(elCell).appendText(oData.value);
    }
   };
    
   var myColumnHeaders = [];
   var myFields        = [];
   
   myColumnHeaders.push({key: 'id', label: 'ID', sortable:true, resizeable:true});
   myFields.push('id');
   
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
   
   var actionFmt = function (elCell, oRecord, oColumn, oData) 
   {   
       var link = new Element('a');
  	   link.appendText('remove');
  	   link.href='#';
	   link.onclick = function() 
	   {
	     me.removeItem(oData);
	   }.bind(this);
	   $(elCell).empty();
	   link.injectInside($(elCell));
    
   }; 
   
   // a column per property
   type.itemPropertyDecls.forEach(addColumnHeaderFnc.bind(this));
    
   myColumnHeaders.push({key: 'id', label: 'actions', formatter: actionFmt.bind(this)});
   myFields.push('id'); 
    
   var myColumnSet = new YAHOO.widget.ColumnSet(myColumnHeaders);

   var myDataSource = new YAHOO.util.DataSource(associatedMappedObjects);
   myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSARRAY;
   myDataSource.responseSchema = {
     fields: myFields
   };
   var myDataTable = new YAHOO.widget.DataTable(this.associatedItemsDisplay, myColumnSet, myDataSource);
 },
 // Callback Handlers to react on server events
 onUpdateCallback: function (obj) 
 {
     this.setPersistentObject(obj);
 	 this.refreshEditor();
 	 this.fireEvent("syncedWithDB");
 },
 onServerErrorCallback: function(msg) 
 {
   alert('onServerErrorCallback: '+msg);
 },
 // FLAT signature
 onChange: function(argument, associatedObject) 
  {
  	// rethrow
  	this.fireEvent("propertyChanged",argument);   
  },
  removeItem: function(id) 
  {
    this.associatedItemIds.remove(id);
    this.refreshAssociatedItems();
  	this.fireEvent("propertyChanged",this);
  },
  _getRenderArea: function() 
  {
    if ($(ASSOCEDITOR_RENDER_STAGE_ID)==null) 
    {
		var el = new Element('div'); 
		el.setStyles('width: 0px; height: 0px; display: none');
		el.id = ASSOCEDITOR_RENDER_STAGE_ID;
		el.injectInside($(document.body));   
    }
    
   	return $(ASSOCEDITOR_RENDER_STAGE_ID);
  }
 }
);

// add YAHOO event provider methods
YAHOO.augment(AssociationEditor, YAHOO.util.EventProvider);