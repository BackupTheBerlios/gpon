ItemTypeEditor = 
new Class({
 initialize: function(config) 
 {
   this.itemTypeObject = {};
   this.mode = "create";
   this.templateLocator     = config.editorTemplate;
   this.ipdTemplateLocator  = config.ipdEditorTemplate;
   this.dataService    = config.dataService;
   this.createEvent("propertyChanged");
   this.createEvent("syncedWithDB");
 },
 // sets a not synchronized object
 setNewObject: function(pItemTypeObject) 
 {
   this.itemTypeObject = 
   	GponBasicService.clone(pItemTypeObject);
   this.mode = "create";
 },
 // sets a persistent object
 setPersistentObject: function(pItemTypeObject, cloneIt) 
 {
   if ($type(cloneIt) && cloneIt==true) 
   {
      this.itemTypeObject = 
   	    GponBasicService.clone(pItemTypeObject);
   }
   else 
   {
      this.itemTypeObject = 
         pItemTypeObject;
   }
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
   var editorTemplate = GponTemplateService.getRemoteTemplate(this.templateLocator);
   //
   // name
   //
   var nameInputElement = new GponFormElement(
      { targetObject       : this.itemTypeObject,
	    targetPropertyExpr : 'name',
	    targetPropertyType : 'shorttext'});
	    
   	  
   nameInputElement.subscribe("propertyChanged", this.onChange.bind(this));
   nameInputElement.getInputNode().injectInside(editorTemplate.getAp('name'));
   //
   // description
   //
   var descInputElement = new GponFormElement(
      { targetObject       : this.itemTypeObject,
	    targetPropertyExpr : 'description',
	    targetPropertyType : 'shorttext'});
   descInputElement.subscribe("propertyChanged", this.onChange.bind(this));
   descInputElement.getInputNode().injectInside(editorTemplate.getAp('description'));
   //
   // basetype
   // get base types
   var itemTypes = this.dataService.getAllItemTypes();
   
   var baseTypes = [{value: -1, text: '-'}];
   
   for (var i=0; i < itemTypes.length; i++) 
   {
     var itemType = itemTypes[i];
     if ($type(this.itemTypeObject) && 
         $type(this.itemTypeObject.id) && this.itemTypeObject.id==itemType.id) 
     { 
       // skip: type can't be basetype of itself
     }
     else 
     {
       baseTypes.push({value: itemType.id, text: itemType.name});
     }
   }
   
   var baseInputElement = new GponFormElement(
      { targetObject       : this.itemTypeObject,
	    targetPropertyExpr : 'baseTypeId',
	    targetPropertyType : 'shorttext'});
   baseInputElement.setEnumBase(baseTypes);
   baseInputElement.subscribe("propertyChanged", this.onChange.bind(this));
   baseInputElement.getInputNode().injectInside(editorTemplate.getAp('basetype'));
   
   // itempropertyDecl
   var ipdAnchor = editorTemplate.getAp('itemPropertyDecls');
   
   if ($type(this.itemTypeObject.itemPropertyDecls)) 
   {
     for (var i =0; i < this.itemTypeObject.itemPropertyDecls.length; i++) 
     {
       var readOnly = false;
       // ipd
       var ipd = this.itemTypeObject.itemPropertyDecls[i];
       // read only, if inherited property
       if ($type(ipd.typeId) && ipd.typeId > 0) 
       {
       	 if ($type(this.itemTypeObject.id) && this.itemTypeObject.id != ipd.typeId)
       	 {
       	   readOnly = true;
       	   ipd.status = 'inherited';
       	 } 
       }
     
       // insert an input node for ipd[i]
       this.getIpdAtIndexAsNode(i, readOnly).injectInside(ipdAnchor);
     }
   }
   // New Button
   var newPropBtn = new Element('button');
   newPropBtn.addEvent('click',function() {
     me.getNewIpdNode(ipdAnchor);
   });
   newPropBtn.appendText('New Property');
   newPropBtn.injectInside(editorTemplate.getAp('newIpdBtn'));
   // ? Button
   var actionBtn = new Element('button');
   actionBtn.addEvent('click',function() {
     alert(Json.toString(me.getObject()));
     var datCallback = function(obj) 
     {
     	   me.onUpdateCallback(obj);
     };
     var errCallback = function(msg)
     {
        me.onServerErrorCallback(msg);
     };
     if (me.mode=="create") {
       me.dataService.createItemType(me.getObject(),
                                 {  
                                   callback:     datCallback,
  								   errorHandler: errCallback
  								  });
  	  } 
  	  else if (me.mode=="update")
  	  {
  	    me.dataService.updateItemType(me.getObject(),
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
   actionBtn.injectInside(editorTemplate.getAp('actionBtn'));
   this.oldNode = editorTemplate.getRootElement();
   return editorTemplate.getRootElement();
 },
 getNewIpdNode: function(anchorNode) 
 {
   var nextIpdIdx = -1;
   // compute next index
   if (!$type(this.itemTypeObject.itemPropertyDecls)) 
   {
     this.itemTypeObject.itemPropertyDecls = new Array();
   }
   // compute next id
   nextIpdIdx = this.itemTypeObject.itemPropertyDecls.length;
   // create ipd object
   this.itemTypeObject.itemPropertyDecls[nextIpdIdx] = new Object();
   // create input node 
   var node = this.getIpdAtIndexAsNode(nextIpdIdx);
   node.injectInside(anchorNode);
  },
  getIpdAtIndexAsNode: function(idx, readOnly) {
   var me = this;
   var ipdTemplate = GponTemplateService.getRemoteTemplate(this.ipdTemplateLocator);
   var ipdNode =ipdTemplate.getRootElement(); 
   ipdNode.id = GponBasicService.getUINodeId();
   
   var ipdNameElement = new GponFormElement(
      { targetObject       : this.itemTypeObject,
	    targetPropertyExpr : 'itemPropertyDecls['+idx+'].name',
	    targetPropertyType : 'shorttext',
	    readOnly           : readOnly
	    });
   ipdNameElement.subscribe("propertyChanged", this.onChange.bind(this)); 
   ipdNameElement.getInputNode().injectInside(ipdTemplate.getAp('name'));
   
   // description
   var ipdDescElement = new GponFormElement({ targetObject       : this.itemTypeObject,
	    targetPropertyExpr : 'itemPropertyDecls['+idx+'].description',
	    targetPropertyType : 'shorttext',
	    readOnly           : readOnly
	    });
   ipdDescElement.subscribe("propertyChanged", this.onChange.bind(this));
   ipdDescElement.getInputNode().injectInside(ipdTemplate.getAp('description'));
   
   // simple type
   var simpleTypes = new Array();
   simpleTypes.push({text: '-', value: undefined});
   
   for (i in GponBasicService.simpleTypes) 
   {
	   var elem = {};
	   elem.value = GponBasicService.simpleTypes[i];
	   elem.text  = GponBasicService.simpleTypesLabel[i];
	   simpleTypes.push(elem);
   }
   
   var simpleTypeElement = new GponFormElement(
      { targetObject       : this.itemTypeObject,
	    targetPropertyExpr : 'itemPropertyDecls['+idx+'].valueTypeId',
	    targetPropertyType : 'shorttext',
	    readOnly           : readOnly
	    });
   simpleTypeElement.setEnumBase(simpleTypes);
   simpleTypeElement.subscribe("propertyChanged", this.onChange.bind(this));
   simpleTypeElement.getInputNode().injectInside(ipdTemplate.getAp('simpletype'));
   
   // mandatory
   var ipdMandElement = new GponFormElement({ targetObject       : this.itemTypeObject,
	    targetPropertyExpr : 'itemPropertyDecls['+idx+'].mandatory',
	    targetPropertyType : 'boolean',
	    readOnly           : readOnly
	    });
	    
   ipdMandElement.subscribe("propertyChanged", this.onChange.bind(this));
   ipdMandElement.getInputNode().injectInside(ipdTemplate.getAp('mandatory'));
   
   // typic
   var ipdTypicElement = new GponFormElement({ targetObject : this.itemTypeObject,
	    targetPropertyExpr : 'itemPropertyDecls['+idx+'].typic',
	    targetPropertyType : 'boolean',
	    readOnly           : readOnly
	    });
	    
   ipdTypicElement.subscribe("propertyChanged", this.onChange.bind(this));
   ipdTypicElement.getInputNode().injectInside(ipdTemplate.getAp('typic'));
   
   if (!readOnly) {
    var delBtn = new Element('button');
    delBtn.addEvent('click',function() {
      me.removeIpd(ipdNode.id,idx);
    });
    delBtn.appendText('Delete IPD');
    delBtn.injectInside(ipdTemplate.getAp('delBtn'));
   }
   return ipdNode;
 },
 removeIpd: function(nodeId, index) 
 {
   this.itemTypeObject.itemPropertyDecls[index].status='deleted';
   $(nodeId).remove();
 },
 getLiveObject: function() 
 {
   return this.itemTypeObject;
 },
 getObject: function() 
 {
   var clone = GponBasicService.clone(this.itemTypeObject);
   var ipds = clone.itemPropertyDecls;
   clone.itemPropertyDecls = new Array();
   if (ipds!=null) 
   {
     for (var i = 0; i < ipds.length; i++) 
     {
       // nur nichtleere Objekte mit status != deleted werden akzeptiert
       if (ipds[i]!=null && 
           !($type(ipds[i].status) && (ipds[i].status=='deleted' || ipds[i].status=='inherited'))) 
       {
         clone.itemPropertyDecls.push(ipds[i]);
       }
     }
   }
   
   return clone;
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
YAHOO.augment(ItemTypeEditor, YAHOO.util.EventProvider);