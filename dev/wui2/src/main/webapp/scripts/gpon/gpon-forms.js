
/*
 * a form element is connected to 
 * an objects property 
 *
 * var foo = {name: 'Bob', address: {street: 'Park Street', town: 'Virginia'}} 
 *
 * f = new FormElement(foo, "address.street", "shorttext");
 *
 *
 */
var GponFormElement = new Class(
{
	initialize: function(config) 
	{
	  this.targetObject = config.targetObject;
	  this.propExpr     = config.targetPropertyExpr;
	  this.simpleType   = config.targetPropertyType;
	  this.readOnly     = false;
	  if ($type(config.readOnly)) 
	  {
	  	this.readOnly = config.readOnly; 
	  }
	  this.createEvent("propertyChanged");
	},
	setEnumBase: function(enumBase) 
	{
	  this.isEnumBased = true;
	  this.enumBase    = enumBase;
	},
	getObjectsValue: function() 
	{
	  return eval("$type(this.targetObject."+this.propExpr+")?this.targetObject."+this.propExpr+":null");
	},
	getInputNode: function() 
	{
		if ($type(this.inputNode)) 
		{
			return this.inputNode;
		}
		
		var node = null;
		var value = this.getObjectsValue();
		
		if ($type(this.enumBase)) 
		{
			node = new Element('select');
			for (i = 0; i < this.enumBase.length; i++) 
			{
				var enumElement = this.enumBase[i];
				if (enumElement!=null) {
					var option   = new Element('option');
					var text = enumElement.value;
					if ($type(enumElement.text)) 
					{
						text = enumElement.text;
					}
					option.appendText(text);
					option.value = enumElement.value;
					if (value == option.value) 
					{
						option.selected = true;
					}
					option.injectInside(node);
				}
			}
		}
		else {
			switch (this.simpleType) 
			{
				case 'longtext':
					node = new Element('textarea');
					node.rows=10;
					node.cols=40;
				break;
				case 'boolean':
					node = new Element('input');
					node.type='checkbox';
					if (value!=null) {
					 node.defaultChecked = value; // IE
					 node.checked = value;
					}
				break;
				default:
					node = new Element('input');
					node.type='text'; 
			}
		}
		if (value!=null) {
		 node.value = value;
		}
		var me = this;
        node.addEvent('change',function() {
         me.onChange((me.simpleType=='boolean')?this.checked:this.value)
        });
        node.disabled=this.readOnly;
		this.inputNode = node;
		return this.inputNode;
	},
	onChange: function(newValue) 
	{ 
	  // dynamically
	  // alert('Getting new value '+newValue+' for '+this.propExpr);
	  eval("this.targetObject."+this.propExpr+"=newValue");
	  this.fireEvent("propertyChanged",{formElement: this, newValue: newValue, propExpr: this.propExpr});
	}
}
);

// add YAHOO event provider methods
YAHOO.augment(GponFormElement, YAHOO.util.EventProvider);