ItemEditor = 
new Class({
 // current item type
 itemType: null,
 /* mapped item has the following structure:
 { id: <id>,
   typeId: <typeId>,
   mappedProperties: 
   {
    // each property object is mapped by p<declId>  
  	p<declId 1> : { id: <id>, declId: <declId>, itemId: <itemId>, value: <value> }, 
    ...
    p<declId x> : { id: <id>, declId: <declId>, itemId: <itemId>, value: <value> } 
   }
 }
 */
 mappedItem: null,
 initialize: function(config) 
 {
   this.mode = "create";
   this.dataService    = config.dataService;
   this.setItemType(this.dataService.getItemTypeById(config.itemTypeId));
   this.mappedItem = this.mapItem(null, this.getItemType());
   this.createEvent("propertyChanged");
   this.createEvent("syncedWithDB");
 },
 setItemType: function(itemType) 
 {
   this.itemType = itemType;
 },
 getItemType: function() 
 {
   return this.itemType;
 },
 mapItem: function(item, type) 
 {
   // 
   var mappedItem = {mappedProperties: {}};
   
   mappedItem.typeId = type.id;
   
   for (var i=0; type.itemPropertyDecls && i < type.itemPropertyDecls.length; i++) 
   {
    var propDecl = type.itemPropertyDecls[i];
    
   	mappedItem.mappedProperties['p'+propDecl.id] = { declId: propDecl.id };    
   } 
    
   // copy values if present  
   if ($type(item) && item != null) 
   {
   	mappedItem.id = item.id;
   	
   	
   	for (var i=0; item.properties && i < item.properties.length; i++) 
   	{
   	  var prop = item.properties[i];
   	  mappedItem.mappedProperties['p'+prop.declId] = prop;
   	}
   }
   
   return mappedItem;
 },
 // sets a not synchronized object
 setNewObject: function(pItemObject) 
 {
   this.mappedItem = 
   	this.mapItem(pItemObject, this.getItemType());
   this.mode = "create";
 },
 // sets a persistent object
 setPersistentObject: function(pItemObject) 
 {
   this.mappedItem = 
   	this.mapItem(pItemObject,this.getItemType());

   this.mode = "update";
 },
 refreshEditor: function() 
 {
   if (!$type(this.oldNode)) 
   {
     alert("No known editor node to refresh");
   }
   
   $(this.oldNode).replaceWith(this.getInputNode());
 },
 getInputNode: function() 
 {
   var me = this;
   // var editorTemplate = GponTemplateService.getRemoteTemplate(this.templateLocation);
   
   var node = new Element('div');
   
   node.appendText(this.itemType.name);
   
   //
   // properties
   //
   
   if ($type(this.itemType.itemPropertyDecls)) 
   {
     for (var i =0; i < this.itemType.itemPropertyDecls.length; i++) 
     {
       var readOnly = false;
       // ipd
       var ipd = this.itemType.itemPropertyDecls[i];

       var ipdNode = new Element('div').injectInside(node);
       ipdNode.appendText(ipd.description);
       
       var inputEl = new GponFormElement(
         { targetObject       : this.mappedItem.mappedProperties,
	       targetPropertyExpr : 'p'+ipd.id+'.value',
	       targetPropertyType : 'shorttext'});
	       
       inputEl.subscribe("propertyChanged", this.onChange.bind(this));
       inputEl.getInputNode().injectInside(node);   
     }
   }
   
   // associations
   
   if ($type(this.itemType.associationTypes)) 
   {
     for (var i =0; i < this.itemType.associationTypes.length; i++) 
     {     
       // at
       var at = this.itemType.associationTypes[i];
	  
	   // if t is a unary association, there could be two
	   // association editors
	   
	   // if we are the a side 
       if (this.itemType.superTypeIds.contains(at.itemATypeId)) 
       {
         var config = 
	      {
	       dataService: this.dataService,
	       associationType: at,
	       reverse: false
	      };
	   
	      var assocEditor = new AssociationEditor(config);
	   
	      assocEditor.getInputNode().injectInside(node);
       }
       
       // if we are the b side 
       if (this.itemType.superTypeIds.contains(at.itemBTypeId)) 
       {
          var config = 
	      {
	       dataService: this.dataService,
	       associationType: at,
	       reverse: true
	      };
	   
	      var assocEditor = new AssociationEditor(config);
	   
	      assocEditor.getInputNode().injectInside(node);
       }
       
	   
	   
     }
   }
   
   
   // ? Button
   var actionBtn = new Element('button');
   actionBtn.addEvent('click',function() {
   
     var item = me.getItem(me.mappedItem);
   
     alert(Json.toString(item));
     var datCallback = function(obj) 
     {
     	   me.onUpdateCallback(obj);
     };
     var errCallback = function(msg)
     {
        me.onServerErrorCallback(msg);
     };
     if (me.mode=="create") {
       me.dataService.createItem(item,
                                 {  
                                   callback:     datCallback,
  								   errorHandler: errCallback
  								  });
  	  } 
  	  else if (me.mode=="update")
  	  {
  	    me.dataService.updateItem(item,
                                 {  
                                   callback:     datCallback,
  								   errorHandler: errCallback
  								  });
  	  }							
  	  else 
  	  {
  	    alert("Don't know what to do in this editor state.");
  	  }  
   });
   actionBtn.appendText(this.mode);
   actionBtn.injectInside(node);
   
   this.oldNode = node;
   
   return node;
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
 /*
  * reads out the mapped item
  */
 getItem: function(mappedItem) 
  {
   var item = {};
   
   item.id = mappedItem.id;
   item.typeId = mappedItem.typeId;
  
   item.properties = [];
  
   for (prop in mappedItem.mappedProperties) 
   {
   	var value = mappedItem.mappedProperties[prop].value;
   	
   	if (value != null || value == "") {
   	 item.properties.push(mappedItem.mappedProperties[prop]);
   	}
   }
   return item;
  } 
 }
);

// add YAHOO event provider methods
YAHOO.augment(ItemEditor, YAHOO.util.EventProvider);