dojo.hostenv.conditionalLoadModule({
	common: [
		"dojo.collections.Collections",
		"dojo.collections.SortedList", 
		"dojo.collections.Dictionary", 
		"dojo.collections.Queue", 
		"dojo.collections.ArrayList", 
		"dojo.collections.Stack",
		"dojo.collections.Set"
	]
});
dojo.hostenv.moduleLoaded("dojo.collections.*");
