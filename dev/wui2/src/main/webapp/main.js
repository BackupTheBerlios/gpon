/*
 * YUI, mootools, dwr and gpon-xxx are present
 *
 */

YAHOO.namespace("gpon.ui.components");
var gpon = YAHOO.gpon; 

gpon.ui.components._elid_seq = 1;
gpon.ui.components.wm = new YAHOO.widget.OverlayManager();
/* next element id */
gpon.ui.components.nextElementId = function () { return 'gpon-ui-'+gpon.ui.components._elid_seq++; };

gpon.ui.components.windows = {};


var sampleType = 
{
  name:        'server.unix',
  description: 'UNIX Server',
  itemPropertyDecls:
  [
    {name: 'cpuType' , description: 'CPU Type', valueTypeId: 1, mandatory: true},
    {name: 'serialNo', description: 'Seriennummer', valueTypeId: 1}
  ]
}

function getPanel(custOptions) 
{
  var pos = getNewPanelPosition();

  var options = 
  { 
     /* underlay: 'none', // disables shadow */
     width:"700px", 
     x: pos.x,
     y: pos.y, 
     visible:false, 
     draggable:true, 
     close:true
  }

  options = $merge(options,custOptions)

  if ($type(options.resizeable) && options.resizeable == false) 
  {
    return new YAHOO.widget.Panel(
       gpon.ui.components.nextElementId(), 
       // see mootools merge (custOptions extends and overwrites options)
       options
       );
  }
  else {
    return new YAHOO.widget.ResizePanel(
       gpon.ui.components.nextElementId(), 
       // see mootools merge (custOptions extends and overwrites options)
       options
       );
  }
}


function getNewItemTypeEditor() 
{
  return new ItemTypeEditor({
   		editorTemplate:    { url: 'scripts/gpon/editors/ItemTypeEditorTemplate.html' },
    	ipdEditorTemplate: { url: 'scripts/gpon/editors/ItemPropertyDeclEditorTemplate.html', 
    	                     id: 'ItemPropertyDeclEditorTemplate'}, 
    	dataService: GponDataService
     });
}
function getNewAssociationTypeEditor() 
{
  return new AssociationTypeEditor({editorTemplate: { url: 'scripts/gpon/editors/AssociationTypeEditorTemplate.html' },
    					       dataService: GponDataService
    				          });
}

function newItemType(destination) 
{
  var editor = getNewItemTypeEditor();
  editor.setNewObject(sampleType);
  // editor.getInputNode().injectInside($(destination));

  var panel = getPanel();

  panel.setHeader("<div class='tl'></div><span>New Item Type</span><div class='tr'></div>");
  panel.setBody(editor.getInputNode());
  panel.setFooter("&minus; General Purpose Object Network &minus;");
  panel.render($(gpon.ui.components.renderStageId));
  gpon.ui.components.wm.register(panel);
  panel.show();
  panel.focus();
}


function newAssociationType(destination) 
{
   var editor = getNewAssociationTypeEditor();
   editor.setNewObject({});
  // editor.getInputNode().injectInside($(destination));

  var panel = getPanel();

  panel.setHeader("<div class='tl'></div><span>New Association Type</span><div class='tr'></div>");
  panel.setBody(editor.getInputNode());
  panel.setFooter("&minus; General Purpose Object Network &minus;");
  panel.render($(gpon.ui.components.renderStageId));
  gpon.ui.components.wm.register(panel);
  panel.show();
  panel.focus();
}


function pickAndEditItemType(params) 
{
    var id = params.id;
    var list = GponDataService.getAllItemTypes();

	for (var i = 0; i < list.length; i++) 
	{
	   var itemType = list[i];
	   if (itemType.id == id) 
	   {
	     editItemType(itemType);
	     return;
	   }
	}
	alert('No item type with id '+id+' found!');
}

function pickAndEditAssociationType(params) 
{
    var id = params.id;
    var list = GponDataService.getAllAssociationTypes();

	for (var i = 0; i < list.length; i++) 
	{
	   var associationType = list[i];
	   if (associationType.id == id) 
	   {
	     editAssociationType(associationType);
	     return;
	   }
	}
	alert('No item type with id '+id+' found!');
}


function showItemTypes(destination) 
{

  if (!gpon.ui.components.windows.ItemTypesWindow) 
  {
   // datatable wrapper 
   var view = 
    new ItemTypeView(
     {dataService: GponDataService}
    );

   view.subscribe("editItemType",pickAndEditItemType)

   // view window
   var panel = getPanel();

   panel.setHeader("<div class='tl'></div><span>Item Type List</span><div class='tr'></div>");
   panel.setBody(view.getElement());
   panel.setFooter("&minus; General Purpose Object Network &minus;");
   panel.render($(gpon.ui.components.renderStageId));
   gpon.ui.components.wm.register(panel);
   panel.show();
   panel.dragEvent.subscribe(view.handleMove.bind(view));
  
   gpon.ui.components.windows.ItemTypesWindow = panel;
  }
  else 
  {
   gpon.ui.components.windows.ItemTypesWindow.show();
  }
  gpon.ui.components.windows.ItemTypesWindow.focus();
}

function showAssociationTypes(destination) 
{
  if (!gpon.ui.components.windows.AssociationTypesWindow) 
  {
  // renders the at table
  var view = 
   new AssociationTypeView(
    {dataService: GponDataService}
   );

  view.subscribe("editAssociationType",pickAndEditAssociationType)

  var panel = getPanel();
  
  panel.setHeader("<div class='tl'></div><span>Association Type List</span><div class='tr'></div>");
 
  panel.setBody(view.getElement());
  panel.setFooter("&minus; General Purpose Object Network &minus;");
  panel.render($(gpon.ui.components.renderStageId));
  gpon.ui.components.wm.register(panel);
  panel.show();
  
  panel.dragEvent.subscribe(view.handleMove.bind(view));
  
  gpon.ui.components.windows.AssociationTypesWindow = panel;
  } 
  else
  {
   gpon.ui.components.windows.AssociationTypesWindow.show();
  }
  gpon.ui.components.windows.AssociationTypesWindow.focus();
}


function editItemType(obj) 
{
 var editor = getNewItemTypeEditor();
 editor.setPersistentObject(obj);
 showEditor(editor, 'Edit Item Type');
 // editor.getInputNode().injectInside($(destination));
 // showMessage(Json.toString(obj).replace(/,/g," , "));
}

function editAssociationType(obj) 
{
 var editor = 
 	getNewAssociationTypeEditor();
 editor.setPersistentObject(obj);
 showEditor(editor, 'Edit Association Type');
}


var panelToX = 100;
var panelToY = 50;

function getNewPanelPosition() 
{
  panelToX =   100 + (((panelToX-100) + 10) % 300);
  panelToY =   50  + (((panelToY-50) + 10) % 40);
  
  return {x: panelToX, y: panelToY};
}


function showEditor(editor, title) 
{
    
	var panel = getPanel();

  // editor and panel are kept in closure
  var setHeader = function(title) {
    panel.setHeader(
    "<div class='tl'></div><span>"+
    title+
    "</span><div class='tr'></div>");
  }
  setHeader(title+' '+editor.getLiveObject().description+
            ' ('+editor.getLiveObject().name+')');
  panel.setBody(editor.getInputNode());
  panel.setFooter("&minus; General Purpose Object Network &minus;");
  panel.render($(gpon.ui.components.renderStageId));
  
  gpon.ui.components.wm.register(panel);
  
  // if editor changed, title will be updated and a '*' is printed
  editor.subscribe("propertyChanged", function(argument, assObject) 
  {
    setHeader(title+' '+editor.getLiveObject().description+
            ' ('+editor.getLiveObject().name+')*');
  })  
  
  editor.subscribe("syncedWithDB", function(argument, assObject) 
  {
    setHeader(editor.getLiveObject().description+
            ' ('+editor.getLiveObject().name+')');
  }) 
  
  panel.show();
  
  // alert('panel.element.id'+panel.element.id);
  
  // alert(panel.cfg.getProperty("draggable"));
  
  var pos =   getNewPanelPosition();
  
  new YAHOO.util.Motion(panel.element.id,{points: 
    { to: [pos.x,pos.y],
      control: [[100,800],[-100,200],[500,500]]
    }}).animate();
  panel.focus();
}

function showItemSearch() 
{
  var panel = getPanel();

  // editor and panel are kept in closure
  var setHeader = function(title) {
    panel.setHeader(
    "<div class='tl'></div><span>"+
    title+
    "</span><div class='tr'></div>");
  }

  setHeader("Item Search");
  panel.setFooter("&minus; General Purpose Object Network &minus;");

  var itemSearch = new ItemSearch({dataService: GponDataService});
  
  panel.setBody(itemSearch.getElement());

  panel.render($(gpon.ui.components.renderStageId));
  gpon.ui.components.wm.register(panel);
  panel.show();
}

function showNewItem() 
{
	var itemEditor = new ItemEditor({itemTypeId: 2, dataService: GponDataService});
	
	var panel = getPanel();

    var setHeader = function(title) {
     panel.setHeader(
     "<div class='tl'></div><span>"+
     title+
     "</span><div class='tr'></div>");
   }

    setHeader("New Item");
    panel.setFooter("&minus; General Purpose Object Network &minus;");
    panel.setBody(itemEditor.getInputNode());
    panel.render($(gpon.ui.components.renderStageId));
    gpon.ui.components.wm.register(panel);
    panel.show();
}