dojo.provide("dojo.widget.html.DebugConsole");

dojo.require("dojo.widget.*");
dojo.require("dojo.widget.FloatingPane");

// Collection of widgets in a bar, like Windows task bar
dojo.widget.html.DebugConsole= function(){

	dojo.widget.html.FloatingPane.call(this);
	dojo.widget.DebugConsole.call(this);
}

dojo.inherits(dojo.widget.html.DebugConsole, dojo.widget.html.FloatingPane);

dojo.lang.extend(dojo.widget.html.DebugConsole, {
	postCreate: function() {
		dojo.widget.html.DebugConsole.superclass.postCreate.call(this);
		this.clientPane.domNode.id = "debugConsoleClientPane"
		djConfig.isDebug = true;
		djConfig.debugContainerId = this.clientPane.domNode.id;
	}
});
