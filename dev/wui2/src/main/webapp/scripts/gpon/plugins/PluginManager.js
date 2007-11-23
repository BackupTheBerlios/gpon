/** Plugins are loaded from: 
 *  system.plugins
 */
 
var PLUGIN_TYPE_NAME = "system.plugins"; 
 
gpon.plugins = {}; 
 
var PluginManager = new Class(
{
	initialize: function (config) 
	{
	  this.dataService = config.dataService;
	}
	,
	load: function() 
	{
	  var pluginItemType = this.dataService.getItemTypeByName(PLUGIN_TYPE_NAME);
	  
	  if (!$type(pluginItemType)) 
	  {
	  	alert("Item type '"+PLUGIN_TYPE_NAME+"' not defined!");
	  	return;
	  }
	  
	  var query = {typeId: pluginItemType.id};
	  var plugins = this.dataService.searchItems(query);
	  
	  if ($type(plugins) && plugins.length>0) 
	  {
	  
	  	for (var idx=0; idx < plugins.length; idx++) 
	  	{
	  		var entry = new GponItemWrapper(plugins[idx],pluginItemType);
	  		
	  		try {
		  		var plugin = eval(entry.propsByName['code']);
		  		
		  		if (plugin.register && $type(plugin.register)=='function') {
		  		 gpon.plugins[entry.propsByName['name']] = plugin;
		  		 plugin.register(this); 
		  		 gpon.log("Registered plugin: "+entry.propsByName['name']);
		  		}
	  		} 
	  		catch (e) 
	  		{
	  			alert('Unable to load '+entry.propsByName['name']);
	  		}
	  	}
	  
	    gpon.log("Plugins loaded.","error");
	  }
	  else {
	    YAHOO.log("No plugins registered.");
	  }
	  
	}
}
);