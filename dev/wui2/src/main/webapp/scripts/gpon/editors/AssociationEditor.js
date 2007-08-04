AssociationEditor = 
new Class({
 // current item type
 item: null, /* RemotItemType */
 associationType: null, /* RemoteAssociationType */
 reverse: null, /* boolean */
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
   
   // a button to add 
 
 
   return elem;
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