dojo.hostenv.conditionalLoadModule({
	common: ["dojo.storage"],
	browser: ["dojo.storage.browser"],
	dashboard: ["dojo.storage.dashboard"]
});
dojo.hostenv.moduleLoaded("dojo.storage.*");

