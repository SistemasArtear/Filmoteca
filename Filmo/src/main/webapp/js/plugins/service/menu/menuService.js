function MenuService() {
};

MenuService.prototype.getMenu = function() {
	$.ajax({
		type : 'GET',
		url : 'menuAction.action',
		success : function(response) {
			menuEvent.completeData(response);
		},
		error : function() {
			MESSAGE.error("No se pudo obtener el men&uacute;. Intente m&aacute;s tarde.");
		}
	});
};
