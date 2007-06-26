AssociationTypeEditor = 
new Class({
 initialize: function(config) 
 {
   this.associationTypeObject = {};
   this.mode = "create";
   this.templateLocator     = config.editorTemplate;
   this.dataService    = config.dataService;
   this.createEvent("propertyChanged");
   this.createEvent("syncedWithDB");
 },
 // sets a not synchronized object
 setNewObject: function(pAssociationTypeObject) 
 {
   this.associationTypeObject = 
   	GponBasicService.clone(pAssociationTypeObject);
   this.mode = "create";
 },
 // sets a persistent object
 setPersistentObject: function(pAssociationTypeObject, cloneIt) 
 {
   if ($type(cloneIt) && cloneIt==true) 
   {
      this.associationTypeObject = 
   	    GponBasicService.clone(pAssociationTypeObject);
   }
   else 
   {
      this.associationTypeObject = 
         pAssociationTypeObject;
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
      { targetObject       : this.associationTypeObject,
	    targetPropertyExpr : 'name',
	    targetPropertyType : 'shorttext'});
	    
   	  
   nameInputElement.subscribe("propertyChanged", this.onChange.bind(this));
   nameInputElement.getInputNode().injectInside(editorTemplate.getAp('name'));
   //
   // description
   //
   var descInputElement = new GponFormElement(
      { targetObject       : this.associationTypeObject,
	    targetPropertyExpr : 'description',
	    targetPropertyType : 'shorttext'});
   descInputElement.subscribe("propertyChanged", this.onChange.bind(this));
   descInputElement.getInputNode().injectInside(editorTemplate.getAp('description'));
   
   // A-side role name
   var aRoleInputElement = new GponFormElement(
      { targetObject       : this.associationTypeObject,
	    targetPropertyExpr : 'itemARoleName',
	    targetPropertyType : 'shorttext'});
   aRoleInputElement.subscribe("propertyChanged", this.onChange.bind(this));
   aRoleInputElement.getInputNode().injectInside(editorTemplate.getAp('itemARoleName'));
   
   // B-side role name
   var bRoleInputElement = new GponFormElement(
      { targetObject       : this.associationTypeObject,
	    targetPropertyExpr : 'itemBRoleName',
	    targetPropertyType : 'shorttext'});
   bRoleInputElement.subscribe("propertyChanged", this.onChange.bind(this));
   bRoleInputElement.getInputNode().injectInside(editorTemplate.getAp('itemBRoleName'));
      
   //
   // multiplicity
   //
   var multInputElement = new GponFormElement(
      { targetObject       : this.associationTypeObject,
	    targetPropertyExpr : 'multiplicity',
	    targetPropertyType : 'shorttext'});
   multInputElement.subscribe("propertyChanged", this.onChange.bind(this));
   multInputElement.setEnumBase(
   		[
    		{value:'OneToMany', text: '1:N'},
    		{value: 'ManyToMany', text: 'M:N'}
   		]);
   multInputElement.getInputNode().injectInside(editorTemplate.getAp('multiplicity'));
   
   //
   // visibility
   //
   var visInputElement = new GponFormElement(
      { targetObject       : this.associationTypeObject,
	    targetPropertyExpr : 'visibility',
	    targetPropertyType : 'shorttext'});
   visInputElement.subscribe("propertyChanged", this.onChange.bind(this));
   visInputElement.setEnumBase(
   		[
         	{ value: 'ba', text: 'A<-B' },
         	{ value: 'abba', text: 'A<->B' }
        ]);
   visInputElement.getInputNode().injectInside(editorTemplate.getAp('visibility'));
   
   var itemTypes = this.dataService.getAllItemTypes();
   
   var typeEnumBase = [{value: -1, text: '-'}];
   
   for (var i=0; i < itemTypes.length; i++) 
   {
     var itemType = itemTypes[i];
     typeEnumBase.push({value: itemType.id, text: itemType.name});
   }
   
   // itemATypeId
   
   // itemATypeId
    
   var itemATypeInputElement = new GponFormElement(
      { targetObject       : this.associationTypeObject,
	    targetPropertyExpr : 'itemATypeId',
	    targetPropertyType : 'shorttext'});
   itemATypeInputElement.setEnumBase(typeEnumBase);
   itemATypeInputElement.subscribe("propertyChanged", this.onChange.bind(this));
   itemATypeInputElement.getInputNode().injectInside(editorTemplate.getAp('itemATypeId'));

   var itemBTypeInputElement = new GponFormElement(
      { targetObject       : this.associationTypeObject,
	    targetPropertyExpr : 'itemBTypeId',
	    targetPropertyType : 'shorttext'});
   itemBTypeInputElement.setEnumBase(typeEnumBase);
   itemBTypeInputElement.subscribe("propertyChanged", this.onChange.bind(this));
   itemBTypeInputElement.getInputNode().injectInside(editorTemplate.getAp('itemBTypeId'));

 
   // ? Button
   var actionButton = new YAHOO.widget.Button(
   { label     : this.mode, 
     id        : GponBasicService.getUINodeId(), 
     container : editorTemplate.getAp('actionBtn') 
    });
    
   actionButton.on("click", function() {
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
       me.dataService.createAssociationType(me.getObject(),
                                 {  
                                   callback:     datCallback,
  								   errorHandler: errCallback
  								  });
  	  } 
  	  else if (me.mode=="update")
  	  {
  	    me.dataService.updateAssociationType(me.getObject(),
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
  
   this.oldNode = editorTemplate.getRootElement();
   return editorTemplate.getRootElement();
 },
 getLiveObject: function() 
 {
   return this.associationTypeObject;
 },
 getObject: function() 
 {
   var clone = GponBasicService.clone(this.associationTypeObject);
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
YAHOO.augment(AssociationTypeEditor, YAHOO.util.EventProvider);