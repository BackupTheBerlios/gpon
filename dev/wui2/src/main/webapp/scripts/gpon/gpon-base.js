YAHOO.namespace("gpon.ui.components");
var gpon = YAHOO.gpon; 

var GponBasicService = 
{
  uiElementSequence: 0,
  i18nMode: 'local',
  // laziness forces happiness
  clone: function (myObj)
  {
	eval("var tmp = " + Json.toString(myObj));
	return tmp;
  },
  getUINodeId: function() 
  {
    return 'GPON_BASIC_SERVICE_UI_NODE_'+this.uiElementSequence++;
  },
  simpleTypes:
  {
	  TEXT_TYPE: 'text',
	  URL_TYPE: 'url',
	  DECIMAL_TYPE: 'decimal',
	  DATE_TYPE: 'date',
	  BOOLEAN_TYPE: 'boolean'
  },
  simpleTypesLabel:
  {
	  TEXT_TYPE: 'text',
	  DATE_TYPE: 'date',
	  DECIMAL_TYPE: 'decimal',
	  URL_TYPE: 'url',
	  BOOLEAN_TYPE: 'boolean'
  },
  /** Utility function to generate a key value list from an object list.
   */
  toKeyValueList: function(list, custOpts) 
  {
    if (list==null || list.length == 0) 
    {
      return list;
    }
  
    var opts = 
     {  destKey   : 'value',
        destValue : 'text',
        srcKey    : 'id',
        // srcValue could be a function too
        srcValue  : 'description'
     };
    // merge options
    opts = $merge(opts, custOpts);
    
    var targetList = [];
    
    for (var i = 0; i < list.length; i++) 
    {
      var o = {};
      var elem = list[i];
      
      o[opts.destKey]   = elem[opts.srcKey];
      o[opts.destValue] = 
        // function ?
        ($type(opts.srcValue)=='function')?
           opts.srcValue.call(this,elem):
           elem[opts.srcValue];
      
      targetList.push(o);  
    }
    
    return targetList;
  }
}

gpon.logger = new YAHOO.widget.LogWriter("gpon");

gpon.log = function (message, category) 
{
 gpon.logger.log(message, category);
}