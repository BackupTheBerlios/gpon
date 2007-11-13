gpon.dst = {};

gpon.dst.getEnumBasedEditor = function(opts /* value, onChange, enumBase, readOnly */) 
{
	var node = new Element('select');
	for (i = 0; i < opts.enumBase.length; i++) 
	{
		var enumElement = opts.enumBase[i];
		if (enumElement!=null) {
			var option   = new Element('option');
			var text = enumElement.value;
			if ($type(enumElement.text)) 
			{
				text = enumElement.text;
			}
			option.appendText(text);
			option.value = enumElement.value;
			if (opts.value == option.value) 
			{
				option.selected = true;
			}
			option.injectInside(node);
		}
	}
	
	if (opts.readOnly) 
	{
	  node.disabled=true;
	}	
	
	node.addEvent('change',function() {
	  opts.onChange(node.value);
	});		
	return node;
} 


gpon.dst['text'] = {
  base: 'text',
  name: 'text',
  /* NO PARAMETERS
  params: <array of parameter objects with attributes: name, description, type>,
  */
  /* UI Elements: Declaration */
  
  //
  // getDeclEditor: function(parentEl, decl, onChange) {... /* void */}  // places an decl editor inside the parentEl and registers onChange, 
  //                                                              // if something changes. signature of onChange:  
  //                                                              // onChange: function( <array of name,value pairs for params>)
  // getDeclView: function(parentEl, decl) {... /* void */} // places a decl view element inside the parentEl
  
  /* UI Elements: Values */
  // places a value editor inside parentEl and registers onChange
  //
  // opts = {value: ..., onChange: ..., enumBase: ..., readOnly: ...}
  //
  //
  getEditor: function(opts /* value, onChange, enumBase, readOnly */) {
  
    if ($type(opts.enumBase)) 
    {
      // a select node is generated
      return gpon.dst.getEnumBasedEditor(opts);
    }
  
    var node = new Element('input');
	node.addClass('dst-text-editor');	
		
	if (opts.readOnly) 
	{
	  node.disabled=true;
	}
	node.addEvent('change',function() {
      opts.onChange(node.value);
    });		
    
    if (opts.value) {
		node.value=opts.value;
	}
    
    return node;
  }, 
  getView: function(value) 
  {
  	var span = new Element('span');
  	span.addClass('dst-text-view');
  	span.appendText(value);
  	return span;
  }, 
  /* conversion */
  // converts input to normal form
  inputToNormal: function(input  , locale) 
  { 
   // id
   return input; 
  }, 
  // converts normal to input form
  normalToInput: function(normal , locale) 
  { 
   // id
   return normal;  
  }, 
  /* validation */
  // returns "is value a valid normal form"
  isValidNF: function(value) 
  { 
   return true;
  }, 
  // returns "is value a valid input form"
  isValidIF: function(value, locale) 
  { 
   return true;
  } 
}

gpon.dst['text:longtext'] = {
  base: 'text',
  name: 'longtext',
  /* NO PARAMETERS
  params: <array of parameter objects with attributes: name, description, type>,
  */
  /* UI Elements: Declaration */
  
  //
  // getDeclEditor: function(parentEl, decl, onChange) {... /* void */}  // places an decl editor inside the parentEl and registers onChange, 
  //                                                              // if something changes. signature of onChange:  
  //                                                              // onChange: function( <array of name,value pairs for params>)
  // getDeclView: function(parentEl, decl) {... /* void */} // places a decl view element inside the parentEl
  
  /* UI Elements: Values */
  // places a value editor inside parentEl and registers onChange
  //
  // opts = {value: ..., onChange: ..., enumBase: ..., readOnly: ...}
  //
  //
  getEditor: function(opts /* value, onChange, enumBase, readOnly */) {

    if ($type(opts.enumBase)) 
    {
      // a select node is generated
      return gpon.dst.getEnumBasedEditor(opts);
    }
  
    var textarea = new Element('textarea');
    textarea.addClass('dst-longtext-editor');
    textarea.rows=10;
    textarea.cols=50;
    textarea.value = opts.value;
    textarea.addEvent('change',function() {
      opts.onChange(textarea.value);
    });
    return textarea;
  }, 
  getView: function(value) 
  {
  	var span = new Element('span');
	span.addClass('dst-longtext-view');
  	span.appendText(value);
  	return span;
  }, 
  /* conversion */
  // converts input to normal form
  inputToNormal: function(input  , locale) 
  { 
   // id
   return input; 
  }, 
  // converts normal to input form
  normalToInput: function(normal , locale) 
  { 
   // id
   return normal;  
  }, 
  /* validation */
  // returns "is value a valid normal form"
  isValidNF: function(value) 
  { 
   return true;
  }, 
  // returns "is value a valid input form"
  isValidIF: function(value, locale) 
  { 
   return true;
  } 
}

gpon.dst['text:url'] = 
$merge(gpon.dst['text'],
{
  name: 'url',
  getView: function(value) 
  {
  	node = new Element('a');
	node.href = value;		
	node.appendText(value);    
    return node;
  }, 
  /* validation */
  // returns "is value a valid normal form"
  isValidNF: function(value) 
  { 
   return true;
  }, 
  // returns "is value a valid input form"
  isValidIF: function(value, locale) 
  { 
   return true;
  } 
});

gpon.dst['text:img'] = 
$merge(gpon.dst['text'],
{
  name: 'img',
  getView: function(value) 
  {
  	node = new Element('img');
	node.src = value;		
	node.title = value;
	node.alt = value;
        
    return node;
  }, 
  /* validation */
  // returns "is value a valid normal form"
  isValidNF: function(value) 
  { 
   return true;
  }, 
  // returns "is value a valid input form"
  isValidIF: function(value, locale) 
  { 
   return true;
  } 
});

/** 
 * boolean
 */

gpon.dst['boolean'] = {
  base: 'boolean',
  name: 'boolean',
  isBase: true,
  /* NO PARAMETERS
  params: <array of parameter objects with attributes: name, description, type>,
  */
  /* UI Elements: Declaration */
  
  //
  // getDeclEditor: function(parentEl, decl, onChange) {... /* void */}  // places an decl editor inside the parentEl and registers onChange, 
  //                                                              // if something changes. signature of onChange:  
  //                                                              // onChange: function( <array of name,value pairs for params>)
  // getDeclView: function(parentEl, decl) {... /* void */} // places a decl view element inside the parentEl
  
  /* UI Elements: Values */
  // places a value editor inside parentEl and registers onChange
  //
  // opts = {value: ..., onChange: ..., enumBase: ..., readOnly: ...}
  //
  //
  getEditor: function(opts /* value, onChange, enumBase, readOnly */) {
  
    	var node = new Element('input');
		node.type='checkbox';
		node.addClass('dst-boolean-editor');
		if (opts.value!=null) {
		 var boolValue = ((''+opts.value).toLowerCase() == 'true');
		 node.defaultChecked = boolValue; // IE
		 node.checked = boolValue;
		}
		
		if (opts.readOnly) 
		{
		  node.disabled=true;
		}
		
		var fnc = function() {
		   opts.onChange(''+node.checked);
        }
		
		node.addEvent('change',fnc);		
        
        return node;
    
  }, 
  getView: function(value) 
  {
  	    var node = new Element('input');
		node.type='checkbox';
	    node.addClass('dst-boolean-view');
		if (value!=null) {
   		 var boolValue = ((''+value).toLowerCase() == 'true');	
		 node.defaultChecked = boolValue; // IE
		 node.checked = boolValue;
		}
        
        return node;
  }, 
  /* conversion */
  // converts input to normal form
  inputToNormal: function(input  , locale) 
  { 
   // id
   return input; 
  }, 
  // converts normal to input form
  normalToInput: function(normal , locale) 
  { 
   // id
   return normal;  
  }, 
  /* validation */
  // returns "is value a valid normal form"
  isValidNF: function(value) 
  { 
   return true;
  }, 
  // returns "is value a valid input form"
  isValidIF: function(value, locale) 
  { 
   return true;
  } 
}


