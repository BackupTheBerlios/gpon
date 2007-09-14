var ITEMEDITOR_RENDER_STAGE_ID = 'ItemEditorRenderStage';

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
   },
   mappedAssociations:
   {
    // each association type has its own slot
    a<'-' if reverse><assType 1> : [ ... associated ids ... ] ,
    ...
    a<'-' if reverse><assType m> : [ ... associated ids ... ]
   }
 }
 */
 mappedItem: null,
 initialize: function(config) 
 {
   this.mode = "create";
   this.dataService = config.dataService;
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
   var mappedItem = 
   {
    // 'p'+declId -> prop
    mappedProperties: {},
    // 'a'+assTypeId -> [ <associations> ]
    mappedAssociations: {}
   };
   
   mappedItem.typeId = type.id;
   
   for (var i=0; type.itemPropertyDecls && i < type.itemPropertyDecls.length; i++) 
   {
    var propDecl = type.itemPropertyDecls[i];
    
   	mappedItem.mappedProperties['p'+propDecl.id] = { declId: propDecl.id };    
   } 
   
   for (var i=0; type.associationTypes && i < type.associationTypes.length; i++) 
   {
    var associationType = type.associationTypes[i];
    
    // if we are the a side
   	mappedItem.mappedAssociations['a'+associationType.id] = [];
   	// if we are the b side (reverse)
   	mappedItem.mappedAssociations['a-'+associationType.id] = [];    
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
   	
   	for (var i =0; item.associations && i < item.associations.length; i++) 
   	{
   	  var association = item.associations[i];
   	  
   	  if (item.id == association.itemAId) {
   	    // normal direction
		mappedItem.mappedAssociations['a'+association.typeId].push(association.itemBId);
   	  } 
   	  else 
   	  {
   	    // reverse direction
		mappedItem.mappedAssociations['a-'+association.typeId].push(association.itemAId);
   	  }
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
 getMainTabNode: function() 
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
       
       var inputElClass = gpon.ui.addons.defaults.inputsById[ipd.valueTypeId];
       
       var inputEl = new GponFormElement(
         { targetObject       : this.mappedItem.mappedProperties,
	       targetPropertyExpr : 'p'+ipd.id+'.value',
	       customInputElementClass: inputElClass
	       });
	       
       inputEl.subscribe("propertyChanged", this.onChange.bind(this));
       inputEl.getInputNode().injectInside(node);   
     }
   }
   
   return node;
 },
 getInputNode: function() 
 {
   me = this;
 
   // mission: build up a tabview container
   var tabContainer = new Element('div').injectInside(this._getRenderArea());
   tabContainer.setStyle('height','300px');
  
   this.tabView = new YAHOO.widget.TabView();
   
   this.mainTab = new YAHOO.widget.Tab({
        label: 'Attr.',
        // content: '<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat.</p>',
        contentEl: this.getMainTabNode(),
        active: true
   });
    
   this.tabView.addTab(this.mainTab); 
   
   this.associationTabs = {};
   
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
	       reverse: false,
	       mapKey: 'a'+at.id
	      };
	   
	      var assocEditor = new AssociationEditor(config);
	  
	      var associatedIds = [];
	  
	  	  for (var j = 0; 
	  	       this.mappedItem && 
	  	       this.mappedItem.mappedAssociations && 
	  	       j < this.mappedItem.mappedAssociations['a'+at.id].length; 
	  	       j++) 
	  	  {
			var associatedId = this.mappedItem.mappedAssociations['a'+at.id][j];
		    associatedIds.push(associatedId);
	  	  }
	   
	      assocEditor.setAssociatedItemIds(associatedIds);

		  // new tab
		  this.associationTabs['a'+at.id] = new YAHOO.widget.Tab({
               label: at.name+':'+at.itemBRoleName,
               contentEl: assocEditor.getInputNode()
               });
    
          this.tabView.addTab(this.associationTabs['a'+at.id]); 
		  
		  
	      // assocEditor.getInputNode().injectInside(node);
	      
	      // data exchange is event based
	      assocEditor.subscribe("propertyChanged",this.onAssociationChange.bind(this));
       }
       
       // if we are the b side 
       if (this.itemType.superTypeIds.contains(at.itemBTypeId)) 
       {
          var config = 
	      {
	       dataService: this.dataService,
	       associationType: at,
	       reverse: true,
	       mapKey: 'a-'+at.id
	      };
	   
	      var assocEditor = new AssociationEditor(config);
	   
	      var associatedIds = [];
	  
	  	  for (var j = 0; 
	  	       this.mappedItem && 
	  	       this.mappedItem.mappedAssociations && 
	  	       j < this.mappedItem.mappedAssociations['a-'+at.id].length; 
	  	       j++) 
	  	  {
			var associatedId = this.mappedItem.mappedAssociations['a-'+at.id][j];
		    associatedIds.push(associatedId);
	  	  }
	   
	      assocEditor.setAssociatedItemIds(associatedIds);
	   
	      // new tab
		  this.associationTabs['a-'+at.id] = new YAHOO.widget.Tab({
               label: at.name+':'+at.itemARoleName,
               contentEl: assocEditor.getInputNode()
               });
    
          this.tabView.addTab(this.associationTabs['a-'+at.id]);
	   
	      // assocEditor.getInputNode().injectInside(node);
	      assocEditor.subscribe("propertyChanged",this.onAssociationChange.bind(this));
       }
     }
   }
   
   this.tabView.appendTo(tabContainer);
   
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
   actionBtn.injectInside(tabContainer);
   
   this.oldNode = tabContainer;
   
   return tabContainer;
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
 onAssociationChange: function(argument, associatedObject) 
  {
   // argument: the association editor
   var assocEditor = argument;	
   	
   this.mappedItem.mappedAssociations[assocEditor.mapKey] 
   			= assocEditor.getAssociatedItemIds();
  
   alert("association changed: "+this.mappedItem.mappedAssociations[assocEditor.mapKey]);
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
   item.associations = [];
  
   for (prop in mappedItem.mappedProperties) 
   {
   	var value = mappedItem.mappedProperties[prop].value;
   	
   	if (value != null || value == "") {
   	 item.properties.push(mappedItem.mappedProperties[prop]);
   	}
   }
   
   for (assocKey in mappedItem.mappedAssociations) 
   {
    if (mappedItem.mappedAssociations[assocKey]==null || 
	    mappedItem.mappedAssociations[assocKey].length == 0) 
	 {
	 	continue;   
	 }
      
    var reverse = false;
    var atId = -1;
   
   	if (assocKey.charAt(1)=='-') 
   	{
   	  reverse = true;
   	  atId = parseInt(assocKey.substr(2));
   	}
   	else 
   	{
   	  atId = parseInt(assocKey.substr(1));
   	}
   
    for (var i = 0; i < mappedItem.mappedAssociations[assocKey].length; i++) 
    {
      var assoc = {};
      assoc.typeId = atId;
      var otherItemId =  mappedItem.mappedAssociations[assocKey][i];
      if (reverse) 
      {
      	assoc.itemAId = otherItemId;
      	assoc.itemBId = item.id;
      }
      else 
      {
      	assoc.itemAId = item.id;
      	assoc.itemBId = otherItemId;
      }
      item.associations.push(assoc);
    } 
   }
   
   return item;
  },
  _getRenderArea: function() 
  {
    if ($(ITEMEDITOR_RENDER_STAGE_ID)==null) 
    {
		var el = new Element('div'); 
		el.setStyles('width: 0px; height: 0px; display: none');
		el.id = ITEMEDITOR_RENDER_STAGE_ID;
		el.injectInside($(document.body));   
    }
    
   	return $(ITEMEDITOR_RENDER_STAGE_ID);
  }
 }
);

// add YAHOO event provider methods
YAHOO.augment(ItemEditor, YAHOO.util.EventProvider);