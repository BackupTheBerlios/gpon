dojo.hostenv.conditionalLoadModule({
	common: ["dojo.dnd.DragAndDrop"],
	browser: ["dojo.dnd.HtmlDragAndDrop"],
	dashboard: ["dojo.dnd.HtmlDragAndDrop"]
});
dojo.hostenv.moduleLoaded("dojo.dnd.*");
