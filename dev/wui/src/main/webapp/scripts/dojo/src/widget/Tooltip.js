dojo.provide("dojo.widget.Tooltip");
dojo.require("dojo.widget.Widget");

dojo.widget.tags.addParseTreeHandler("dojo:tooltip");

dojo.widget.Tooltip = function(){
	dojo.widget.Widget.call(this);

	this.widgetType = "Tooltip";
	this.isContainer = true;
}
dojo.inherits(dojo.widget.Tooltip, dojo.widget.Widget);

dojo.requireAfterIf("html", "dojo.widget.html.Tooltip");
