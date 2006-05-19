dojo.provide("dojo.widget.HslColorPicker");

dojo.require("dojo.widget.*");
dojo.require("dojo.widget.Widget");
dojo.require("dojo.graphics.color");
dojo.widget.tags.addParseTreeHandler("dojo:hslcolorpicker");

dojo.requireAfterIf(dojo.render.svg.support.builtin, "dojo.widget.svg.HslColorPicker");

dojo.widget.HslColorPicker=function(){
	dojo.widget.Widget.call(this);
	this.widgetType = "HslColorPicker";
	this.isContainer = false;
}
dojo.inherits(dojo.widget.HslColorPicker, dojo.widget.Widget);
