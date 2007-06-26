GponTemplateService = {}

GponTemplateService.AttachPointsVisitor = new Class(
{
 initialize: function() {},
 begin: function(rootElement) 
 {
   this.rootElement = rootElement;
   this.rootElement.gponAttachPointByName = {};
   this.rootElement.gponAttachPointNames = [];
 },
 visit: function(elem, level) 
 {
   if (elem.attributes && elem.attributes['gponAttachPoint']) 
   {
      // DOM Node is saved with ap name
      var apName = elem.attributes['gponAttachPoint'].value;
      this.rootElement.gponAttachPointByName[apName] = elem;
      this.rootElement.gponAttachPointNames.push(apName);
      // $('log').appendText('AP found: '+apName);
      // new Element('br').injectInside($('log'));
   }
 },
 traverseOn: function (element) 
 {
    if (element.id =='log') 
   {
     return false;
   }	
   return true;
 }
});

GponTemplateService.TreeWalker = new Class(
  { 
	initialize: function(visitor) 
	{
		this.visitor=visitor;
	},
	walkQ: function(elem) 
	{
		var queue = new Array();
		var i, currentElem, childs;
		queue.push(elem);
		while (queue.length > 0) {
			currentElem = queue.pop();
			this.visit(currentElem);
			childs = currentElem.childNodes.length;
			for (i = 0; i < childs; i++) {
				queue.push(currentElem.childNodes[i]);
			}
		}
	},
	walk: function (startElem) 
	{
	  this.begin(startElem);
	  this.walkR(startElem,0);
	  this.end(startElem);
	},
	walkR: function (currentElem, level) 
	{
		if (!this.traverseOn(currentElem)) 
		{
		 return;
		}
		this.visit(currentElem, level);
		for (var i = 0; i < currentElem.childNodes.length; i++) {
		  this.walkR(currentElem.childNodes[i], level+1);
		}
	},
	traverseOn: function (elem) 
	{
		if ($type(this.visitor)) 
		{
			return this.visitor.traverseOn(elem);
		}
		return true;
	}, 
	visit: function (elem, level) 
	{
		if ($type(this.visitor) && $type(this.visitor.visit)) 
		{
			this.visitor.visit(elem, level);
		}
	},
	begin: function (elem) 
	{
		if ($type(this.visitor) && $type(this.visitor.begin)) 
		{
			this.visitor.begin(elem);
		}
	},
	end: function (elem) 
	{
		if ($type(this.visitor) && $type(this.visitor.end)) 
		{
			this.visitor.end(elem);
		}
	}});

// convinience methods to access a template copy
GponTemplateService.GponTemplate = new Class(
{
	initialize: function(templateInstanceNode) 
	{
	  this.bodyNode = templateInstanceNode;
	},
	getAp: function(apName) 
	{
		if (this.bodyNode.gponAttachPointByName && this.bodyNode.gponAttachPointByName[apName]) 
		{
		  var apNode = $(this.bodyNode.gponAttachPointByName[apName]);
		  return apNode;
		}
		// default: templates root node
		return this.getRootElement(); 
	},
	getRootElement: function () 
	{
	 return $(this.bodyNode);
	}
}
)

/**
 * Clones a node, collects its attachpoints and wraps it 
 * in a GponTemplate object.
 *
 */
 
GponTemplateService.getTemplate = function(templateId)
{
  var clone = $(templateId).clone();
  clone.id=GponBasicService.getUINodeId();
  var tw = new GponTemplateService.TreeWalker(new GponTemplateService.AttachPointsVisitor());
  tw.walk(clone);
  return new GponTemplateService.GponTemplate(clone);
}

GponTemplateService.getRenderArea = function() 
{
   if ($('GponTemplateServiceRenderStage')==null) 
    {
		var el = new Element('div'); 
		el.setStyles('width: 0px; height: 0px; display: none');
		el.id = 'GponTemplateServiceRenderStage';
		el.injectInside($(document.body));   
    }
    
   	return $('GponTemplateServiceRenderStage');
}
// Hash: URL -> Node 
GponTemplateService.remoteURLNodes = {};

GponTemplateService.getRemoteTemplate = function(templateLocator) 
{
  
  if ($type(GponTemplateService.remoteURLNodes[templateLocator.url]) && 
      GponTemplateService.remoteURLNodes[templateLocator.url] != null) 
  {

  }
  else 
  {
    // create new template element
    var el = new Element('div');
    el.id = GponBasicService.getUINodeId();
    // inject inside renderstage
    el.injectInside(GponTemplateService.getRenderArea());
    // get template content with ajax
    var myAjax = new Ajax(templateLocator.url, {method: 'get', update: el , async: false}).request();
    // cache it
    GponTemplateService.remoteURLNodes[templateLocator.url] = el;
    // a) you can specify a URL to get a html snippet
    // b) additionaly you can specify an id to get a nested element of that snippet
    //
    if (templateLocator.id) 
    {
      GponTemplateService.remoteURLNodes[templateLocator.url] = $(templateLocator.id);
    }
  }
  
  return GponTemplateService.getTemplate(GponTemplateService.remoteURLNodes[templateLocator.url]);
}

