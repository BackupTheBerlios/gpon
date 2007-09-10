var ITEMCREATELAUNCHER_RENDER_STAGE_ID = 'ItemCreateLauncherRenderStage';

var ItemCreateLauncher = 
new Class({
  initialize: function(config) 
  {
    this.dataService = config.dataService;  
  	this.createEvent("createItem");
  },
  getRenderArea: function() 
  {
    if ($(ITEMCREATELAUNCHER_RENDER_STAGE_ID)==null) 
    {
		var el = new Element('div'); 
		el.setStyles('width: 0px; height: 0px; display: none');
		el.id = ITEMCREATELAUNCHER_RENDER_STAGE_ID;
		el.injectInside($(document.body));   
    }
    
   	return $(ITEMCREATELAUNCHER_RENDER_STAGE_ID);
  },
  getElement: function()
  {
  	 var bodyEl = new Element('div').injectInside(this.getRenderArea());
  
	 var itemTypes = this.dataService.getAllItemTypes();
   
  	 var typeEnumBase = [{value: -1, text: '-'}];
   
     for (var i=0; i < itemTypes.length; i++) 
     {
      var itemType = itemTypes[i];
      typeEnumBase.push({value: itemType.id, text: itemType.name});
     }
   
     var itemATypeInputElement = new GponFormElement(
      { targetObject       : this,
	    targetPropertyExpr : 'itemTypeId',
	    targetPropertyType : 'shorttext'});
     itemATypeInputElement.setEnumBase(typeEnumBase);
     itemATypeInputElement.getInputNode().injectInside(bodyEl);

	 var searchButton = new YAHOO.widget.Button({ 
	                                        type: "button",  
	                                        label: "create",  
	                                        container: bodyEl  
	                                    });
	                                    
     searchButton.on("click", this.create.bind(this));	
     
     return bodyEl;
  },
  create: function() 
  {
  	this.fireEvent("createItem", { itemTypeId: this.itemTypeId });  
  }
});


// add YAHOO event handler stuff
YAHOO.augment(ItemCreateLauncher, YAHOO.util.EventProvider);