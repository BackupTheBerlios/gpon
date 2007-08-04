var GponDataServiceClass = new Class( 
{
  itemTypes: [],
  initialize: function(ajaxService) 
  {
   this.ajaxService = ajaxService;  
   /* custom events */
   this.createEvent("modelEvent");
   this.createEvent("dataEvent");
   this.modelDirty = 
    { it: true, 
      at: true,
      i: true 
    }
  },
  /* GPON Ajax Service Wrapper */  
  
  // Item update, create
  updateItem: function(item, cb) 
  {
    this.ajaxService.updateItem(item, cb);
    this.modelDirty.i = true;
    this.fireEvent("dataEvent",{entity: 'Item', op: 'update'});
  },
  createItem: function(item, cb) 
  {
    this.ajaxService.createItem(item,cb);
    this.modelDirty.i = true;
    this.fireEvent("dataEvent",{entity: 'Item', op: 'create'});
  },
  
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
  getItemTypeById: function (id) 
  {
    this.getAllItemTypes(); 
  
    return this.itemTypesById[id];
  },
  /* getAllItemTypes */
  getAllItemTypes: function() 
  {
    if (this.modelDirty.it) {   
     var cb = function(types) 
     {
       this.itemTypes = types;
       this.itemTypesById = this._mapById(types);
     }
     this.ajaxService.getAllItemTypes(cb.bind(this));
     this.modelDirty.it=false;
    }
    return this.itemTypes;
  } ,
  getAssociationTypeById: function (id) 
  {
    return this.associationTypesById[id];
  },
  /* getAllAssociationTypes */
  getAllAssociationTypes: function() 
  {
    if (this.modelDirty.at) {   
     var cb = function(types) 
     {
       this.associationTypes = types;
       this.associationTypesById = this._mapById(types);
     }
     this.ajaxService.getAllAssociationTypes(cb.bind(this));
     this.modelDirty.at=false;
    }
    return this.associationTypes;
  },
  /* search items */
  searchItems: function(remoteQuery) 
  {
    var result;
    var cb = function(types) 
    {
       result = types;
    }
    this.ajaxService.searchItems(remoteQuery,cb.bind(this));
    return result;
  },
  /**
   * itemToIpdIdMap converts a remoteItem object to a map
   * every itemProperty is mapped to its id prefixed by 'ipd' (decorator funcs have to applied for proper rendering)
   * id is mapped to key 'id'
   * typeId is mapped to key 'typeId'
   * complete item is mapped to key 'item'
   */
   itemToIpdByIdMap: function (item) 
   {
     var mapped = {};
     mapped.id = item.id;
     mapped.typeId = item.typeId;
     mapped.item = item;
     for (var pix=0; item.properties && pix < item.properties.length; pix++) 
     {
       var  property = item.properties[pix];
       mapped['ipd'+property.declId]=property;
     } 
     return mapped;
   },
  /**
   * converts a list of objects to a map (object.id is the key, object is the value)
   */  
  _mapById: function(objects) 
  {
   var map = {}; 
   for (var i = 0; i < objects.length; i++) 
   {
       map[objects[i].id] = objects[i];
   }
   return map;
  } 
});

// add YAHOO event handler stuff
YAHOO.augment(GponDataServiceClass, YAHOO.util.EventProvider);


// instantiate with DWR service
var GponDataService = new GponDataServiceClass(ajaxService);