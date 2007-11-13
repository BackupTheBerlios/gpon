/*
 * a form element is connected to a target property of a target object
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
	  this.targetObject     = config.targetObject;
	  this.propExpr         = config.targetPropertyExpr;
	  this.simpleType       = config.targetPropertyType;
	  this.readOnly         = false;
	  /**
	   * TODO: CustomInputElement should be well documented
	   */
	  // this.customInputElementClass = config.customInputElementClass;
	  this.ipd = config.ipd;
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
	    if (!$type(this.inputNode)) 
		{
			if ($type(this.ipd)) 
			{
				this.inputNode = this.getInputNodeCustom();
			}
			else 
			{
				this.inputNode = this.getInputNodeDefault();
			}
		}
		
		return this.inputNode;
	},
	getInputNodeCustom: function() 
	{
		var me = this;
	    var value = this.getObjectsValue();
		// callback method
		var onChangeFunc = function(value) 
		{
			me.onChange(value);
		}
	    // CTOR called with initial value
		var inputEl = null; 
		
		if ($type(this.ipd.derivedType) &&
			$type(gpon.dst[this.ipd.valueType+':'+this.ipd.derivedType])) 
		{
		    // derived simple type
		    var dst = gpon.dst[this.ipd.valueType+':'+this.ipd.derivedType];
		    var opts = {
		      value: value,
		      onChange: onChangeFunc,
		      enumBase: this.enumBase, 
		      readOnly: this.readOnly
		    }
			return dst.getEditor(opts);
		}
		else if ($type(gpon.dst[this.ipd.valueType]))
		{
		    // derived simple type (only for the simple type)
		    var dst = gpon.dst[this.ipd.valueType];
		    var opts = {
		      value: value,
		      onChange: onChangeFunc,
		      enumBase: this.enumBase, 
		      readOnly: this.readOnly
		    }
			return dst.getEditor(opts);
		} 
		else 
		{
		 var customInputElementClass = 
		 	gpon.ui.addons.defaults.inputsById[this.ipd.valueType];   
		 var iel = new customInputElementClass(onChangeFunc, value, this.enumBase, this.readOnly);		
		 return iel.getElement();
        }
	}, 
	getInputNodeDefault: function() 
	{
		
		
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
		return node;
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