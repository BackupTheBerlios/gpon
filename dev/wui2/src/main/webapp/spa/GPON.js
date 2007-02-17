var GPON = {
	nextID: function() 
	{
		if (this._lastid_==null) 
		{
		  this._lastid_ = 0;
		}
		
		this._lastid_++;
		return this._lastid_;
	}
};

// Item Type
GPON.ItemType = new Class(
{
	initialize: function(pId , pName, pDescription, pBaseType, pItemPropertyDecls){
		this.id = id;
		this.name=  pName;
		this.description = pDescription;
		this.baseType= pBaseType;
		this.itemPropertyDecls = pItemPropertyDecls
	},
	// getter
	getId: function() {
		return this.id;
	},
	getName: function() {
		return this.name;
	},
	getDescription: function() {
		return this.description;
	},
	getBaseType: function () 
	{
	  return this.baseType;
	},
	// item property declarations are added after instantiation
	getItemPropertyDecls: function() 
	{
		return this.itemPropertyDecls;
	},
	setItemPropertyDecls: function(pItemPropertyDecls) 
	{
		this.itemPropertyDecls = pItemPropertyDecls;
	},
	addItemPropertyDecl:function(pItemPropertyDecls) 
	{
		this.itemPropertyDecls.push(pItemPropertyDecls);
	} 
});

// Item Property Declaration
GPON.ItemPropertyDecl = new Class(
{
	initialize: function(pId , pName, pDescription, pValueType){
		this.id = id;
		this.name=  pName;
		this.description = pDescription;
		this.valueType= pValueType;
	},
	// getter
	getId: function() {
		return this.id;
	},
	getName: function() {
		return this.name;
	},
	getDescription: function() {
		return this.description;
	},
	getValueType: function () 
	{
	  return this.valueType;
	} 
});


GPON.PropHandle = new Class(
{
	initialize: function(pLabel, pType, pValue){
		this.pLabel = pLabel;
		this.pType  = pType;
		this.pValue = pValue;
	},
	propLabel: function() {
		return this.pLabel;
	},
	propType: function() {
		return this.pType;
	},
	propValue: function() {
		return this.pValue;
	},
	setValue: function (newValue) 
	{
	        this.pValue = newValue;
	},
	// creates an appropriate inputNode for the 
	// given type
	createInputNode: function(gponForm) 
	{
	  var containerNode = new Element('div');
	  var node = null;
	  
	  if (this.pType == 'longtext') 
	  {
	   node = new Element('textarea');
	   node.rows=10;
	   node.cols=40;
	  }
	  else 
	  {
	   node = new Element('input');
	   node.type='text'; 
	  }
	  
	  node.injectInside(containerNode);
	  node.value=this.pValue;
	  var curPropHandle = this;
	  node.addEvent('change',function() {gponForm.putData(curPropHandle,node.value)});
	  containerNode.appendText(this.propType());
	  return containerNode;	  
	},
	createOutputNode: function(gponForm) 
	{
	  var containerNode = new Element('div');
	  //var node = new Element('p');
	  //node.injectInside(containerNode);
	  if (this.pType=='imgUrl') 
	  {
	    var node = new Element('img');
	    node.src=this.pValue;
	    node.injectInside(containerNode);
	  }
	  else if(this.pType=='longtext') 
	  {
	    var node = new Element('pre');
	    node.appendText(this.pValue);
	    node.injectInside(containerNode);
	  }
	  else {
	    containerNode.appendText(this.pValue);
	  }
	  return containerNode;	  
	}
}

);

GPON.Form = new Class({
	initialize: function(propHandles){
		this.propHandles = propHandles; //save our opitons.
		this.containerId = 'cn_'+GPON.nextID();
	},
	propertyCount: function() {
		if ($type(this.propHandles)=='array') 
		{
		  return this.propHandles.length;
		} 
		else 
		{
		 return 0;
		}
	},
	asInputFormElement: function() 
	{
	  log('build form');
	  // form node
	  var formNode = new Element('form');
	  formNode.id = this.containerId+'_input';
	  formNode.addClass('formNode');
	  formNode.ondblclick = this.toOutput.bindWithEvent(this);
	  // button bar
	  var formControlBar = new Element('div');
	  formControlBar.addClass('formControlBar');
	  formControlBar.injectInside(formNode);
	  // form command buttons
	  var save = new Element('a');
	  save.href='javascript:void();';
	  save.injectInside(formControlBar);
	  save.setHTML('save');
	  save.onclick = this.toOutput.bindWithEvent(this);
	  // table
	  // form fields
	  var tableNode = new Element('table');
	  tableNode.injectInside(formNode);
	  var tbodyNode = new Element('tbody');
	  tbodyNode.injectInside(tableNode);
	  for (i=0; i < this.propHandles.length; i++) 
	  {
	    // row
	    rowNode = new Element('tr');
	    rowNode.injectInside(tbodyNode);
	    // cell
	    // -> propety label
	    cellNode = new Element('td');
	    cellNode.injectInside(rowNode);
	    cellNode.appendText(this.propHandles[i].propLabel());
	    // cell
	    // -> input element
	    cellNode = new Element('td');
	    cellNode.injectInside(rowNode);
	    inputNode = this.propHandles[i].createInputNode(this);
	    inputNode.injectInside(cellNode);
	  }
	  return formNode;
	},
	asOutputDisplayElement: function() 
	{
	  var formNode = new Element('div');
	  formNode.id = this.containerId+'_output';
	  formNode.addClass('formNode');
	  formNode.ondblclick = this.toInput.bindWithEvent(this);
	  // button bar
	  var formControlBar = new Element('div');
	  formControlBar.addClass('formControlBar');
	  formControlBar.injectInside(formNode);
	  // form command buttons
	  var edit = new Element('a');
	  edit.href='javascript:void();';
	  edit.injectInside(formControlBar);
	  edit.setHTML('edit');
	  edit.onclick = this.toInput.bindWithEvent(this);
	  // table
	  var tableNode = new Element('table');
	  tableNode.injectInside(formNode);
	  var tbodyNode = new Element('tbody');
	  tbodyNode.injectInside(tableNode);
	  for (i=0; i < this.propHandles.length; i++) 
	  {
	    // row
	    rowNode = new Element('tr');
	    rowNode.injectInside(tbodyNode);
	    // cell
	    // -> propety label
	    cellNode = new Element('td');
	    cellNode.injectInside(rowNode);
	    cellNode.appendText(this.propHandles[i].propLabel());
	    // cell
	    // -> input element
	    cellNode = new Element('td');
	    cellNode.injectInside(rowNode);
	    inputNode = this.propHandles[i].createOutputNode(this);
	    inputNode.injectInside(cellNode);
	  }
	  return formNode;
	},
	putData: function(propHandle, newValue) 
	{
	  propHandle.setValue(newValue);
	},
	toOutput: function(event)
	{
	  var myOutputDOM = this.asOutputDisplayElement();
	  $(this.containerId+'_input').replaceWith(myOutputDOM);
	  return false;
	},
	toInput: function(event)
	{
	  var myInputDOM = this.asInputFormElement();
	  $(this.containerId+'_output').replaceWith(myInputDOM);
	  return false;
	}
});

