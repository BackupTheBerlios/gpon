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

/*

    _                   _          _                           _
   (_)_ __  _ __  _   _| |_    ___| | ___ _ __ ___   ___ _ __ | |_ ___
   | | '_ \| '_ \| | | | __|  / _ \ |/ _ \ '_ ` _ \ / _ \ '_ \| __/ __|
   | | | | | |_) | |_| | |_  |  __/ |  __/ | | | | |  __/ | | | |_\__ \
   |_|_| |_| .__/ \__,_|\__|  \___|_|\___|_| |_| |_|\___|_| |_|\__|___/
           |_|

*/

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

/*
               _               _          _                           _
    ___  _   _| |_ _ __  _   _| |_    ___| | ___ _ __ ___   ___ _ __ | |_ ___
   / _ \| | | | __| '_ \| | | | __|  / _ \ |/ _ \ '_ ` _ \ / _ \ '_ \| __/ __|
  | (_) | |_| | |_| |_) | |_| | |_  |  __/ |  __/ | | | | |  __/ | | | |_\__ \
   \___/ \__,_|\__| .__/ \__,_|\__|  \___|_|\___|_| |_| |_|\___|_| |_|\__|___/
                  |_|
*/
gpon.ui.addons.defaults.outputsById = [];

gpon.ui.addons.defaults.DefaultBooleanOutput = new Class(
{
	initialize: function(value) 
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
		node.disabled=true;
        
        if (this.value) {
			node.value=this.checked;
		}
        
        return node;
	} 
}
);

gpon.ui.addons.defaults.DefaultTextOutput = new Class(
{
	initialize: function(value) 
	{
		this.value = value;		
	},
	getElement: function() 
	{
		node = new Element('span');

		node.appendText(this.value);
        
        return node;
	} 
}
);

gpon.ui.addons.defaults.DefaultUrlOutput = new Class(
{
	initialize: function(value) 
	{
		this.value = value;		
	},
	getElement: function() 
	{
		node = new Element('a');
		node.href = this.value;		
		node.appendText(this.value);
        
        return node;
	} 
}
);

gpon.ui.addons.defaults.DefaultImgUrlOutput = new Class(
{
	initialize: function(value) 
	{
		this.value = value;		
	},
	getElement: function() 
	{
		node = new Element('img');
		node.src = this.value;		
		node.title = this.value;
		node.alt = this.value;
        
        return node;
	} 
}
);

gpon.ui.addons.defaults.outputsById[GponBasicService.simpleTypes.TEXT_TYPE] 
 = gpon.ui.addons.defaults.DefaultTextOutput;
gpon.ui.addons.defaults.outputsById[GponBasicService.simpleTypes.DATE_TYPE]
 = gpon.ui.addons.defaults.DefaultTextOutput;
gpon.ui.addons.defaults.outputsById[GponBasicService.simpleTypes.DECIMAL_TYPE]
 = gpon.ui.addons.defaults.DefaultTextOutput;
gpon.ui.addons.defaults.outputsById[GponBasicService.simpleTypes.URL_TYPE]
 = gpon.ui.addons.defaults.DefaultUrlOutput;
gpon.ui.addons.defaults.outputsById[GponBasicService.simpleTypes.BOOLEAN_TYPE]
 = gpon.ui.addons.defaults.DefaultBooleanOutput;
 
