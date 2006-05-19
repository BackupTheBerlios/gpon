dojo.provide("dojo.widget.Button");
dojo.require("dojo.widget.Widget");

dojo.widget.tags.addParseTreeHandler("dojo:button");

dojo.widget.Button = function(){
	dojo.widget.Widget.call(this);

	this.widgetType = "Button";
	this.isContainer = true;
}
dojo.inherits(dojo.widget.Button, dojo.widget.Widget);
dojo.requireAfterIf("html", "dojo.widget.html.Button");
