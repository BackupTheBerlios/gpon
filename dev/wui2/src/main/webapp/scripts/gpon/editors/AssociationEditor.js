AssociationEditor = 
new Class({
 // current item type
 item: null, /* RemotItemType */
 associationType: null, /* RemoteAssociationType */
 reverse: null, /* boolean */
 assoaciatedItemIds: null, /* array of associated items */
 currentSelectedItemId: null, /* selected via ac_field */
 associatedItemsDisplay: null, /* widget to display associated items */
 initialize: function(config) 
 {
   /* 
    * 
    */
   this.associationType = config.associationType;
   this.reverse = config.reverse;
   this.dataService = config.dataService;
   this.createEvent("propertyChanged");
   this.createEvent("syncedWithDB");
   this.assoaciatedItemIds = [];
   this.otherItemType = 
     this.dataService.getItemTypeById(
      (this.reverse)?
         this.associationType.itemATypeId:
         this.associationType.itemBTypeId);
 },
 setAssociatedItemIds: function(ids)
 {
 	this.assoaciatedItemIds = ids;
 },
 getAssociatedItemIds: function() 
 {
 	return this.assoaciatedItemIds;
 },
 getInputNode: function() 
 {
   var elem = new Element('div');
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
     new Element('input').injectInside(elem);
    
    me.refreshAssociatedItems();
    
    var addButton = new Element('button').appendText('<<').injectInside(elem);
    
    addButton.onclick = function() 
    {
     var obj = me;
    
     if (me.currentSelectedItemId &&
         me.currentSelectedItemId > 0) 
     {
       if (!me.assoaciatedItemIds.contains(me.currentSelectedItemId)) 
       {
         me.assoaciatedItemIds.push(me.currentSelectedItemId);
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
   // TODO: items should be displayed in a table
   this.associatedItemsDisplay.value =
   	this.assoaciatedItemIds.join(',');
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
  }
 }
);

// add YAHOO event provider methods
YAHOO.augment(AssociationEditor, YAHOO.util.EventProvider);