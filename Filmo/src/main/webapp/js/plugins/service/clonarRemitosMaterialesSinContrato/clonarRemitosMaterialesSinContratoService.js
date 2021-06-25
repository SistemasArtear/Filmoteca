function ClonarRemitosMaterialesSinContratoService() {
};

ClonarRemitosMaterialesSinContratoService.prototype.buscarRemitos = function(data ,callback) {
	BLOCK.showBlock("Buscando...");
	$.ajax({
		async: false,
		type : 'POST',
		url : "buscarRemitosParaClonarSinContrato.action",
		data : JSON.stringify(data),
		contentType: 'application/json',
		dataType: 'json',
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.remitosGrilla) {
				callback(response.remitosGrilla);
				return;
			}
		}
	});
};

ClonarRemitosMaterialesSinContratoService.prototype.clonarRemitos = function(data ,callback) {
	BLOCK.showBlock("Procesando...");
	$.ajax({
		async: false,
		type : 'POST',
		url : "clonarRemitosSinContrato.action",
		data : JSON.stringify(data),
		contentType: 'application/json',
		dataType: 'json',
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			} else {
				callback();
				return;
			}
		}
	});
};