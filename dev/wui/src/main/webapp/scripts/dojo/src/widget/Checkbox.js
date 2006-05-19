dojo.provide("dojo.widget.Checkbox");

dojo.require("dojo.widget.*");
dojo.require("dojo.event");
dojo.require("dojo.html");

dojo.widget.tags.addParseTreeHandler("dojo:Checkbox");

dojo.widget.Checkbox = function(){
	dojo.widget.Widget.call(this);
};
dojo.inherits(dojo.widget.Checkbox, dojo.widget.Widget);

dojo.lang.extend(dojo.widget.Checkbox, {
	widgetType: "Checkbox"
});

dojo.requireAfterIf("html", "dojo.widget.html.Checkbox");
