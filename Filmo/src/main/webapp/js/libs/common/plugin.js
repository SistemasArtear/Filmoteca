var extend = function(subClass, superClass) {
	var F = function() {
	};
	F.prototype = superClass.prototype;
	subClass.prototype = new F();
	subClass.prototype.constructor = subClass;
	subClass.prototype.superclass = superClass;
	if (superClass.prototype.constructor == Object.prototype.constructor) {
		superClass.prototype.constructor = superClass;
	}
};
// Div definition
function DivDefinition(id, divContainerId) {
	this.id = id;
	this.divContainerId = divContainerId;
};
function Plugin(div) {
	this.div = div;
	this.created = false;
};
Plugin.prototype.create = function() {
};
Plugin.prototype.hide = function() {
	this.getDiv().hide();
};
Plugin.prototype.show = function() {
	this.getDiv().show();
};
Plugin.prototype.div;
Plugin.prototype.getDiv = function() {
	return $('#' + this.div.id);
};
Plugin.prototype.getSelector = function(id) {
    return $("#" + this.div.id + "_" + id);
};
