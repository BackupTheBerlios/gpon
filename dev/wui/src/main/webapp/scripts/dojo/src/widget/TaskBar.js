dojo.provide("dojo.widget.TaskBar");
dojo.provide("dojo.widget.TaskBarItem");
dojo.require("dojo.widget.Widget");

dojo.widget.TaskBar = function(){
	dojo.widget.Widget.call(this);

	this.widgetType = "TaskBar";
	this.isContainer = true;
}
dojo.inherits(dojo.widget.TaskBar, dojo.widget.Widget);
dojo.widget.tags.addParseTreeHandler("dojo:taskbar");

dojo.widget.TaskBarItem = function(){
	dojo.widget.Widget.call(this);

	this.widgetType = "TaskBarItem";
}
dojo.inherits(dojo.widget.TaskBarItem, dojo.widget.Widget);
dojo.widget.tags.addParseTreeHandler("dojo:taskbaritem");

dojo.requireAfterIf("html", "dojo.widget.html.TaskBar");
