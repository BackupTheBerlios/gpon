dojo.provide("dojo.widget.Button2");
dojo.provide("dojo.widget.DropDownButton2");
dojo.provide("dojo.widget.ComboButton2");
dojo.require("dojo.widget.Widget");

dojo.widget.tags.addParseTreeHandler("dojo:button2");
dojo.widget.tags.addParseTreeHandler("dojo:dropdownbutton2");
dojo.widget.tags.addParseTreeHandler("dojo:combobutton2");

dojo.widget.Button2 = function(){
}
dojo.lang.extend(dojo.widget.Button2, {
	widgetType: "Button2",
	isContainer: true,

	// Constructor arguments
	caption: "",
	disabled: false,
	onClick: function(){ }
});

dojo.widget.DropDownButton2 = function(){
}
dojo.inherits(dojo.widget.DropDownButton2, dojo.widget.Button2);
dojo.lang.extend(dojo.widget.DropDownButton2, {
	widgetType: "DropDownButton2",
	isContainer: true,

	// constructor arguments
	menuId: ''
});

dojo.widget.ComboButton2 = function(){
}
dojo.inherits(dojo.widget.ComboButton2, dojo.widget.Button2);
dojo.lang.extend(dojo.widget.ComboButton2, {
	widgetType: "ComboButton2",
	isContainer: true,

	// constructor arguments
	menuId: ''
});

dojo.requireAfterIf("html", "dojo.widget.html.Button2");

