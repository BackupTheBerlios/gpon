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
	  SHORTTEXT_TYPE: 1,
	  DATE_TYPE: 5,
	  DECIMAL_TYPE: 3,
	  INTEGER_TYPE: 3,
	  IMGURL_TYPE: 4,
	  URL_TYPE: 2,
	  LONGTEXT_TYPE: 1,
	  BOOLEAN_TYPE: 1
  },
  simpleTypesLabel:
  {
	  SHORTTEXT_TYPE: 'shorttext',
	  DATE_TYPE: 'date',
	  DECIMAL_TYPE: 'decimal',
	  INTEGER_TYPE: 'integer',
	  IMGURL_TYPE: 'imgurl',
	  URL_TYPE: 'url',
	  LONGTEXT_TYPE: 'longtext',
	  BOOLEAN_TYPE: 'boolean'
  },
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
