dojo.provide("dojo.animation.AnimationEvent");

dojo.require("dojo.lang");

dojo.animation.AnimationEvent = function(anim, type, coords, sTime, cTime, eTime, dur, pct, fps) {
	this.type = type; // "animate", "begin", "end", "play", "pause", "stop"
	this.animation = anim;

	this.coords = coords;
	this.x = coords[0];
	this.y = coords[1];
	this.z = coords[2];

	this.startTime = sTime;
	this.currentTime = cTime;
	this.endTime = eTime;

	this.duration = dur;
	this.percent = pct;
	this.fps = fps;
};
dojo.lang.extend(dojo.animation.AnimationEvent, {
	coordsAsInts: function() {
		var cints = new Array(this.coords.length);
		for(var i = 0; i < this.coords.length; i++) {
			cints[i] = Math.round(this.coords[i]);
		}
		return cints;
	}
});
