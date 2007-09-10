function ItemTypeNameFormatter(elCell, oRecord, oColumn, oData)
{
 var picked = 
   GponDataService.getAllItemTypes().filter(
    function(item, index){
        return item.id == oData;
    });
 
 if (picked && picked != null && picked.length == 1) 
 {
   elCell.innerHTML = picked[0].name;
 } 
}

var AssociationTypeView = 
new Class({
  initialize: function(config) 
  {
    this.dataService = config.dataService;  
  	this.createEvent("editAssociationType");
  },
  getRenderArea: function() 
  {
    if ($('AssociationTypeViewRenderStage')==null) 
    {
		var el = new Element('div'); 
		el.setStyles('width: 0px; height: 0px; display: none');
		el.id = 'AssociationTypeViewRenderStage';
		el.injectInside($(document.body));   
    }
    
   	return $('AssociationTypeViewRenderStage');
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
	     this.fireEvent("editAssociationType", { id: oData });
	   }.bind(this);
	   $(elCell).empty();
	   link.injectInside($(elCell));
     }

     var columnHeaders = [
        {key:"id", label: "Id", type:"number", sortable:true, resizeable:true},
        {key:"name", label: "Name", sortable:true, resizeable:true},
        {key:"description",  label: "Beschreibung", sortable:true, resizeable:true},
        {key:"multiplicity",  label: "Mult.", sortable:true, resizeable:true},
        {key:"visibility",  label: "Vis.", sortable:true, resizeable:true},
        {key:"itemATypeId", label: "A Type", resizeable:true , formatter: ItemTypeNameFormatter.bind(this)},
        {key:"itemBTypeId", label: "B Type", resizeable:true , formatter: ItemTypeNameFormatter.bind(this)},
        {key:"itemARoleName", label: "A Rolle", resizeable:true },
        {key:"itemBRoleName", label: "B Rolle", resizeable:true },
        {key:"id", label: "Action", resizeable:true , formatter: fmtFnc.bind(this)}
     ];
  
     var columnSet = new YAHOO.widget.ColumnSet(columnHeaders);

     var dataSource = new YAHOO.util.DataSource(this.dataService.getAllAssociationTypes());
     dataSource.responseType = YAHOO.util.DataSource.TYPE_JSARRAY;
  	 dataSource.responseSchema = {
    		fields: ["id","name","description","multiplicity","visibility","itemATypeId",
    		         "itemBTypeId","itemARoleName","itemBRoleName"]
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
  	  this.tabRenderTo.addClass('gpon-ui-at-tab');
  	  this.tabRenderTo.injectInside(this.getRenderArea());
  	 }
  	 
  	 var plc1 = new Element('div').addClass('pagelinks-top').injectInside(this.tabRenderTo);
  	 var body = new Element('div').addClass('tab-body').injectInside(this.tabRenderTo);;
  	 var plc2 = new Element('div').addClass('pagelinks-bottom').injectInside(this.tabRenderTo);
  	 
  	 var dataTable = new YAHOO.widget.DataTable(body, columnSet, dataSource,
   	  	   { paginator:{ rowsPerPage: 10 ,containers: [plc1, plc2]  },  paginated:true});
  
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
YAHOO.augment(AssociationTypeView, YAHOO.util.EventProvider);

