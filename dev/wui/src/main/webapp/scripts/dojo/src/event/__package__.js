dojo.hostenv.conditionalLoadModule({
	common: ["dojo.event", "dojo.event.topic"],
	browser: ["dojo.event.browser"],
	dashboard: ["dojo.event.browser"]
});
dojo.hostenv.moduleLoaded("dojo.event.*");
