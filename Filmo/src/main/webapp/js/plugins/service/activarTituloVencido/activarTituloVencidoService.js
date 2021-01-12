function ActivarTituloVencidoService() {
};

ActivarTituloVencidoService.prototype.getTitulos = function(data, callback) {
	BLOCK.showBlock("Se estan obteniendo los datos...");
	$.ajax({
		type : "GET",
		url : "activarTituloVencidoBuscarTitulos.action",
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.listadoTitulos) {
				callback(response.listadoTitulos);
			}
		}
	});
};

ActivarTituloVencidoService.prototype.validarContratoParaElTitulo = function(data, callback) {
	BLOCK.showBlock("Se estan obteniendo los datos...");
	$.ajax({
		type : "GET",
		url : "activarTituloVencidoValidarContrato.action",
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.warningValidarContrato) {
				MESSAGE.alert(response.warningValidarContrato);
			} else {
				callback();
			}
		}
	});
};

ActivarTituloVencidoService.prototype.buscarVigenciaContratos = function(data, callback) {
	BLOCK.showBlock("Se estan obteniendo los datos...");
	$.ajax({
		type : "GET",
		url : "activarTituloVencidoVisualizarModificacionContratos.action",
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.visualizarModificacionContratosResponse) {
				callback[0](response.visualizarModificacionContratosResponse[0]);
				callback[1](response.visualizarModificacionContratosResponse);
			}
		}
	});
};