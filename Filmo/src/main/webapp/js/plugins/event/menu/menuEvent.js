MenuEvent.prototype.templates = {
	menu: "<li><a href='#' id='menu-{{id}}'>{{label}}</a><ul id='items-menu-{{id}}' class='subnav'></ul></li>",
	submenu: "<li><a href='#' id='submenu-{{id}}'>&gt;&nbsp;{{label}}</a></li>"
};
function MenuEvent() {
};
MenuEvent.prototype.drawMenu = function() {
	Component.get("html/header/header.html", menuEvent.draw);
};
MenuEvent.prototype.draw = function(comp) {
	$("#headerContainer").html(comp);
	variablesSesionUsuarioEvent.drawDialog();
	var menuService = new MenuService();
	menuService.getMenu();
};
MenuEvent.prototype.completeData = function(data) {
	for (var i = 0; i < data.menu.subMenu.length; i++) {
		var menu = data.menu.subMenu[i];
		$("#itemsMenu").append(this.templates.menu.replace(/{{label}}/g, menu.title).replace(/{{id}}/g, menu.key));
		for (var j = 0; j < menu.subMenu.length; j++) {
			var submenu = menu.subMenu[j];
			$("#items-menu-"+menu.key).append(this.templates.submenu.replace(/{{label}}/g, submenu.title).replace(/{{id}}/g, submenu.key));
			menuEvent.addEvent(submenu);
		}
	}
};
MenuEvent.prototype.addEvent = function(submenu) {
	var action = menuEvent.getAction(submenu.action);
	$("#submenu-"+submenu.key).click(action);
};
MenuEvent.prototype.getAction = function(actionString) {
	var context = window;
	var namespaces = actionString.split(".");
	var func = namespaces.pop();
	for (var i = 0; i < namespaces.length; i++) {
		context = context[namespaces[i]];
	}
	return context[func];
};
var menuEvent = new MenuEvent();