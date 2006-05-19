dojo.provide("dojo.widget.DebugConsole");
dojo.require("dojo.widget.Widget");

dojo.widget.DebugConsole= function(){
	dojo.widget.Widget.call(this);

	this.widgetType = "DebugConsole";
	this.isContainer = true;
}
dojo.inherits(dojo.widget.DebugConsole, dojo.widget.Widget);
dojo.widget.tags.addParseTreeHandler("dojo:debugconsole");
dojo.requireAfterIf("html", "dojo.widget.html.DebugConsole");
