dojo.provide("dojo.widget.HtmlWidget");
dojo.require("dojo.widget.DomWidget");
dojo.require("dojo.html");
dojo.require("dojo.lang.extras");
dojo.require("dojo.lang.func");

dojo.widget.HtmlWidget = function(args){
	// mixin inheritance
	dojo.widget.DomWidget.call(this);
}

dojo.inherits(dojo.widget.HtmlWidget, dojo.widget.DomWidget);

dojo.lang.extend(dojo.widget.HtmlWidget, {
	widgetType: "HtmlWidget",

	templateCssPath: null,
	templatePath: null,
	allowResizeX: true,
	allowResizeY: true,

	resizeGhost: null,
	initialResizeCoords: null,

	// for displaying/hiding widget
	toggle: "plain",
	toggleDuration: 150,

	animationInProgress: false,

	initialize: function(args, frag){
	},

	postMixInProperties: function(args, frag){
		// now that we know the setting for toggle, get toggle object
		this.toggleObj =
			dojo.widget.HtmlWidget.toggle[this.toggle.toLowerCase()] ||
			dojo.widget.HtmlWidget.toggle.plain;
	},

	getContainerHeight: function(){
		// NOTE: container height must be returned as the INNER height
		dj_unimplemented("dojo.widget.HtmlWidget.getContainerHeight");
	},

	getContainerWidth: function(){
		return this.parent.domNode.offsetWidth;
	},

	setNativeHeight: function(height){
		var ch = this.getContainerHeight();
	},

	resizeSoon: function(){
		if(this.isVisible()){
			dojo.lang.setTimeout(this, this.onResized, 0);
		}
	},

	createNodesFromText: function(txt, wrap){
		return dojo.html.createNodesFromText(txt, wrap);
	},

	destroyRendering: function(finalize){
		try{
			if(!finalize){
				dojo.event.browser.clean(this.domNode);
			}
			delete this.domNode;
		}catch(e){ /* squelch! */ }
	},

	// Displaying/hiding the widget

	isVisible: function(){
		return dojo.style.isVisible(this.domNode);
	},

	doToggle: function(){
		this.isVisible() ? this.hide() : this.show();
	},

	show: function(){
		this.animationInProgress=true;
		this.toggleObj.show(this.domNode, this.toggleDuration, this.explodeSrc,
			dojo.lang.hitch(this, this.onShow));
	},

	onShow: function(){
		this.animationInProgress=false;
	},

	hide: function(){
		this.animationInProgress=true;
		this.toggleObj.hide(this.domNode, this.toggleDuration, this.explodeSrc,
			dojo.lang.hitch(this, this.onHide));
	},

	onHide: function(){
		this.animationInProgress=false;
	}
});


/**** 
	Strategies for displaying/hiding widget
*****/

dojo.widget.HtmlWidget.toggle={}

dojo.widget.HtmlWidget.toggle.plain = {
	show: function(node, duration, explodeSrc, callback){
		dojo.style.show(node);
		if(dojo.lang.isFunction(callback)){ callback(); }
	},

	hide: function(node, duration, explodeSrc, callback){
		dojo.html.hide(node);
		if(dojo.lang.isFunction(callback)){ callback(); }
	}
}

dojo.widget.HtmlWidget.toggle.fade = {
	show: function(node, duration, explodeSrc, callback){
		dojo.fx.html.fadeShow(node, duration, callback);
	},

	hide: function(node, duration, explodeSrc, callback){
		dojo.fx.html.fadeHide(node, duration, callback);
	}
}

dojo.widget.HtmlWidget.toggle.wipe = {
	show: function(node, duration, explodeSrc, callback){
		dojo.fx.html.wipeIn(node, duration, callback);
	},

	hide: function(node, duration, explodeSrc, callback){
		dojo.fx.html.wipeOut(node, duration, callback);
	}
}

dojo.widget.HtmlWidget.toggle.explode = {
	show: function(node, duration, explodeSrc, callback){
		dojo.fx.html.explode(explodeSrc||[0,0,0,0], node, duration, callback);
	},

	hide: function(node, duration, explodeSrc, callback){
		dojo.fx.html.implode(node, explodeSrc||[0,0,0,0], duration, callback);
	}
}
