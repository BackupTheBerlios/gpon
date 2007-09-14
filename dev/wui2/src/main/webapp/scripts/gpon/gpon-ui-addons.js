/** 
 * GPON ui addons registry
 */

gpon.ui.addons = { defaults: {}}; 

/**
 * default input handlers
 */
 
// enumeration based abstract implementation
var _EnumElementTemplate = new Class(
{
	getEnumElement: function () 
	{
			var node = new Element('select');
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
					if (this.value == option.value) 
					{
						option.selected = true;
					}
					option.injectInside(node);
				}
			}
		
		    if (this.readOnly) 
		    {
		      node.disabled=true;
		    }	
			
			var me = this;
		    node.addEvent('change',function() {
             me.onChange(this.value);
            });		
            return node;
	}
}
); 
 
// text

gpon.ui.addons.defaults.DefaultTextInput = new Class(
{
	initialize: function(onChangeFunc, value, enumBase, readOnly) 
	{
		this.value = value;
		this.onChange = onChangeFunc;
		if ($type(enumBase)) 
		{
			this.isEnumBased = true;
	  		this.enumBase    = enumBase;		
		}
		this.readOnly = readOnly;
	},
	getElement: function() 
	{
		if ($type(this.enumBase)) 
		{
			return this.getEnumElement();
		}
	
		var node = new Element('input');
		
		var me = this;
		if (this.readOnly) 
		{
		  node.disabled=true;
		}
		node.addEvent('change',function() {
		 alert('DTE');
         me.onChange(this.value);
        });		
        
        if (this.value) {
			node.value=this.value;
		}
        
        return node;
	} 
}
);

YAHOO.lang.augment(gpon.ui.addons.defaults.DefaultTextInput, _EnumElementTemplate);

// boolean
gpon.ui.addons.defaults.DefaultBooleanInput = new Class(
{
	initialize: function(onChangeFunc, value, enumBase, readOnly) 
	{
		this.value = value;
		
		if ($type(this.value)) 
		{
		  if ($type(this.value) == 'boolean') 
		  {
		  	// ok
		  }
		  else 
		  {
		    this.value = ((''+this.value).toLowerCase() == 'true');
		  }
		}
		
		this.callMaster = onChangeFunc;
		if ($type(enumBase)) 
		{
			this.isEnumBased = true;
	  		this.enumBase    = enumBase;		
		}
		this.readOnly = readOnly;
	},
	getElement: function() 
	{
		node = new Element('input');
		node.type='checkbox';
		if (this.value!=null) {
		 node.defaultChecked = this.value; // IE
		 node.checked = this.value;
		}
		
		var me = this;
		if (this.readOnly) 
		{
		  node.disabled=true;
		}
		
		var fnc = function() {
		   me.callMaster(''+this.checked);
        }
		
		node.addEvent('change',fnc);		
        
        if (this.value) {
			node.value=this.checked;
		}
        
        return node;
	} 
}
);


gpon.ui.addons.defaults.inputsById = [];

gpon.ui.addons.defaults.inputsById[GponBasicService.simpleTypes.TEXT_TYPE] 
 = gpon.ui.addons.defaults.DefaultTextInput;
gpon.ui.addons.defaults.inputsById[GponBasicService.simpleTypes.DATE_TYPE]
 = gpon.ui.addons.defaults.DefaultTextInput;
gpon.ui.addons.defaults.inputsById[GponBasicService.simpleTypes.DECIMAL_TYPE]
 = gpon.ui.addons.defaults.DefaultTextInput;
gpon.ui.addons.defaults.inputsById[GponBasicService.simpleTypes.INTEGER_TYPE]
 = gpon.ui.addons.defaults.DefaultTextInput;
gpon.ui.addons.defaults.inputsById[GponBasicService.simpleTypes.IMGURL_TYPE]
 = gpon.ui.addons.defaults.DefaultTextInput;
gpon.ui.addons.defaults.inputsById[GponBasicService.simpleTypes.URL_TYPE]
 = gpon.ui.addons.defaults.DefaultTextInput;
gpon.ui.addons.defaults.inputsById[GponBasicService.simpleTypes.BOOLEAN_TYPE]
 = gpon.ui.addons.defaults.DefaultBooleanInput;


