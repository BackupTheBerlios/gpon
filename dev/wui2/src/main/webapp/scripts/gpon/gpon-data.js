var GponDataServiceClass = new Class( 
{
  itemTypes: [],
  initialize: function(ajaxService) 
  {
   this.ajaxService = ajaxService;  
   /* custom events */
   this.createEvent("modelEvent");
   this.modelDirty = 
    { it: true, 
      at: true 
    }
  },
  /* GPON Ajax Service Wrapper */  
  
  // ItemType update, create
  updateItemType: function(type, cb) 
  {
    this.ajaxService.updateItemType(type,cb);
    this.modelDirty.it = true;
    this.fireEvent("modelEvent",{entity: 'ItemType', op: 'update'});
  },
  createItemType: function(type, cb) 
  {
    this.ajaxService.createItemType(type,cb);
    this.modelDirty.it = true;
    this.fireEvent("modelEvent",{entity: 'ItemType', op: 'create'});
  },
  // AssociationType update, create
  updateAssociationType: function(type, cb) 
  {
    this.ajaxService.updateAssociationType(type,cb);
    this.modelDirty.at = true;
    this.fireEvent("modelEvent",{entity: 'AssociationType', op: 'update'});
  },
  createAssociationType: function(type, cb) 
  {
    this.ajaxService.createAssociationType(type,cb);
    this.modelDirty.at = true;
    this.fireEvent("modelEvent",{entity: 'AssociationType', op: 'create'});
  },
  
  /* getAllItemTypes */
  getAllItemTypes: function() 
  {
    if (this.modelDirty.it) {   
     var cb = function(types) 
     {
       this.itemTypes = types;
     }
     this.ajaxService.getAllItemTypes(cb.bind(this));
     this.modelDirty.it=false;
    }
    return this.itemTypes;
  } ,
  /* getAllAssociationTypes */
  getAllAssociationTypes: function() 
  {
    if (this.modelDirty.at) {   
     var cb = function(types) 
     {
       this.associationTypes = types;
     }
     this.ajaxService.getAllAssociationTypes(cb.bind(this));
     this.modelDirty.at=false;
    }
    return this.associationTypes;
  }
});

// add YAHOO event handler stuff
YAHOO.augment(GponDataServiceClass, YAHOO.util.EventProvider);


// instantiate with DWR service
var GponDataService = new GponDataServiceClass(ajaxService);