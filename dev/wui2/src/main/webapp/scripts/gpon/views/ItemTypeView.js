var ItemTypeView = 
new Class({
  initialize: function(config) 
  {
    this.dataService = config.dataService;  
  	this.createEvent("editItemType");
  },
  getRenderArea: function() 
  {
    if ($('ItemTypeViewRenderStage')==null) 
    {
		var el = new Element('div'); 
		el.setStyles('width: 0px; height: 0px; display: none');
		el.id = 'ItemTypeViewRenderStage';
		el.injectInside($(document.body));   
    }
    
   	return $('ItemTypeViewRenderStage');
  },
  getElement: function()
  {
    return this.getOrRefreshElement();
  },
  refresh: function () 
  {
    this.getOrRefreshElement();
  },
  getOrRefreshElement: function() 
  { 	   	 
     var fmtFnc = function (elCell, oRecord, oColumn, oData)
     {
  	   var link = new Element('a');
  	   link.appendText('edit '+oData);
  	   link.href='#';
	   link.onclick = function() 
	   {
	     this.fireEvent("editItemType", { id: oData });
	   }.bind(this);
	   $(elCell).empty();
	   link.injectInside($(elCell));
     }

     var columnHeaders = [
        {key:"id", text: "Id", type:"number", sortable:true, resizeable:true},
        {key:"name", text: "Name", sortable:true, resizeable:true},
        {key:"description",  text: "Beschreibung", sortable:true, resizeable:true},
        {key:"id", text: "Action", resizeable:true , formatter: fmtFnc.bind(this)}
     ];
  
     var columnSet = new YAHOO.widget.ColumnSet(columnHeaders);

     var dataSource = new YAHOO.util.DataSource(this.dataService.getAllItemTypes());
     dataSource.responseType = YAHOO.util.DataSource.TYPE_JSARRAY;
  	 dataSource.responseSchema = {
    		fields: ["id","name","description"]
  	 };
  	 // refresh
  	 if (this.tabRenderTo) 
  	 {
  	  this.tabRenderTo.empty();	
  	 }
  	 // new
  	 else 
  	 {
  	  this.tabRenderTo = new Element('div');
  	  this.tabRenderTo.addClass('gpon-ui-it-tab');
  	  this.tabRenderTo.injectInside(this.getRenderArea());
  	 }
  	 
  	 var plc1 = new Element('div').addClass('pagelinks-top').injectInside(this.tabRenderTo);
  	 var body = new Element('div').addClass('tab-body').injectInside(this.tabRenderTo);;
  	 var plc2 = new Element('div').addClass('pagelinks-bottom').injectInside(this.tabRenderTo);
  	 
  	 var dataTable = new YAHOO.widget.DataTable(body, columnSet, dataSource,
   	  {/* caption:"Item Type List", */ paginator:true, paginatorOptions:{ rowsPerPage: 10 ,containers: [plc1, plc2]  } });
  
     /* Begin of DD-Resizer Adjustment*/
     var colList = dataTable.getColumnSet().flat;
     var ddResizers = [];

     for (var i = 0; i < colList.length; i++) 
     {
       var oColumn = colList[i];
	   if (typeof(oColumn.ddResizer)!='undefined') 
	   {
	     ddResizers.push(oColumn.ddResizer);
	   }
     }

     this.adjustDragResizers = function() 
     {
       for (var i = 0; i < ddResizers.length; i++) 
       {
         ddResizers[i].resetConstraints();
       } 
     }
     /* End of DD-Resizer Adjustment */
  
  	 this.dataService.subscribe(
    	"modelEvent", 
    	this.refresh.bind(this));
    	
     return this.tabRenderTo;
  },
  handleMove: function(type, args, object) 
  {
    // if our container stops dragging, we will recalculate
    // the drag resizer positions
    if(args[0]=='endDrag') 
    {  
     this.adjustDragResizers.call();
    }
  }
});


// add YAHOO event handler stuff
YAHOO.augment(ItemTypeView, YAHOO.util.EventProvider);

