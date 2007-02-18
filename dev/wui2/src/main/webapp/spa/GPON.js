var GPON = {
	nextID: function() 
	{
		if (this._lastid_==null) 
		{
		  this._lastid_ = 0;
		}
		
		this._lastid_++;
		return this._lastid_;
	},
    encode_utf8: function (rohtext)
	{
	// dient der Normalisierung des Zeilenumbruchs
	rohtext = rohtext.replace(/\r\n/g,"\n");
	var utftext = "";
	for(var n=0; n<rohtext.length; n++)
		{
		// ermitteln des Unicodes des  aktuellen Zeichens
		var c=rohtext.charCodeAt(n);
		// alle Zeichen von 0-127 => 1byte
		if (c<128)
			utftext += String.fromCharCode(c);
		// alle Zeichen von 127 bis 2047 => 2byte
		else if((c>127) && (c<2048)) {
			utftext += String.fromCharCode((c>>6)|192);
			utftext += String.fromCharCode((c&63)|128);}
		// alle Zeichen von 2048 bis 66536 => 3byte
		else {
			utftext += String.fromCharCode((c>>12)|224);
			utftext += String.fromCharCode(((c>>6)&63)|128);
			utftext += String.fromCharCode((c&63)|128);}
		}
	return utftext;
	},
    decode_utf8: function (utftext)
	{
	var plaintext = ""; var i=0; var c=c1=c2=0;
	// while-Schleife, weil einige Zeichen uebersprungen werden
	while(i<utftext.length)
		{
		c = utftext.charCodeAt(i);
		if (c<128) {
			plaintext += String.fromCharCode(c); i++;}
		else if((c>191) && (c<224)) {
			c2 = utftext.charCodeAt(i+1);
			plaintext += String.fromCharCode(((c&31)<<6) | (c2&63));
			i+=2;}
		else {
			c2 = utftext.charCodeAt(i+1); c3 = utftext.charCodeAt(i+2);
			plaintext += String.fromCharCode(((c&15)<<12) | ((c2&63)<<6) | (c3&63));
			i+=3;}
		}
	return plaintext;
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
	  
	  if (this.pType == 'longtext' || this.pType == 'htmltext') 
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
	  else if(this.pType=='htmltext') 
	  {
	    var node = new Element('div');
	    node.setHTML(this.pValue);
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
	  var formMainPanel = new Element('div');
	  formMainPanel.addClass('formMainPanelInput');
	  formMainPanel.injectInside(formNode);
	  // table
	  // form fields
	  var tableNode = new Element('table');
	  tableNode.injectInside(formMainPanel);
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
	  
	  formNode.dragHandle=formControlBar;
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
	  // main
	  var formMainPanel = new Element('div');
	  formMainPanel.addClass('formMainPanelOutput');
	  formMainPanel.injectInside(formNode);
	  // table
	  var tableNode = new Element('table');
	  tableNode.injectInside(formMainPanel);
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
	  formNode.dragHandle=formControlBar;	  
	  return formNode;
	},
	putData: function(propHandle, newValue) 
	{
	  propHandle.setValue(newValue);
	},
	toOutput: function(event)
	{
	  // send data to server
	  var str = Json.toString(this.propHandles);
	  
	  var ajaxOptions = 
	  {
	    method: 'post',
	    postBody: str,
	    onComplete: function() { alert('oncomplete');}
	  }
	  
	  var myAjax = new Ajax('http://localhost:8080/wui2/ajax/spa.do', 
	                        ajaxOptions
	                        ).request();
	
	  var myOutputDOM = this.asOutputDisplayElement();
	  $(this.containerId+'_input').replaceWith(myOutputDOM);
      myOutputDOM.getParent().effect('margin',{duration: 10}).start(0,10);
	  return false;
	},
	toInput: function(event)
	{
	  var myInputDOM = this.asInputFormElement();
	  $(this.containerId+'_output').replaceWith(myInputDOM);
	  myInputDOM.getParent().effect('margin').start(10,0);
	  return false;
	}
});

