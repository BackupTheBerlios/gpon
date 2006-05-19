dojo.provide("dojo.widget.SplitSortableTable");
dojo.require("dojo.widget.*");
dojo.requireAfterIf("html", "dojo.widget.html.SplitSortableTable");
dojo.widget.tags.addParseTreeHandler("dojo:splitsortabletable");

//	set up the general widget
dojo.widget.SplitSortableTable=function(){
	dojo.widget.Widget.call(this);
	this.widgetType="SplitSortableTable";
	this.isContainer=false;

	//	custom properties
	this.enableMultipleSelect=false;
	this.maximumNumberOfSelections=1;
	this.enableAlternateRows=false;
	this.minRows=0;	//	0 means ignore.
	// new: for multiplicity constraints in gpon
	this.maxRows=0; //	0 means ignore.
	this.defaultDateFormat="%D";
	this.data=[];
	this.selected=null
	this.columns=[];
	this.sortIndex=0;		//	index of the column sorted on, first is the default.
	this.sortDirection=0;	//	0==asc, 1==desc
	this.sortFunctions={};	//	you can add to this if needed.
};
dojo.inherits(dojo.widget.SplitSortableTable, dojo.widget.Widget);
