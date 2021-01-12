function ConfirmarSinAmortizacionService() {
};
ConfirmarSinAmortizacionService.prototype.getProgramas = function(data) {
	BLOCK.showBlock("Se estan obteniendo los datos...");
	$.ajax({
		type : "GET",
		url : "confirmarSinAmortizacionBuscarTitulosProgramas.action",
		data : data.data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.titulosProgramas) {
				data.callback(response.titulosProgramas);
			}
		}
	});
};
ConfirmarSinAmortizacionService.prototype.confirmar = function(data) {
	BLOCK.showBlock("Se est&aacute; realizando la acci&oacute;n...");
	var request = "confirmarRequest.senial=" + data.data.senial +
			"&confirmarRequest.fecha=" + data.data.fecha + 
			"&confirmarRequest.programa=" + data.data.programa +
			"&confirmarRequest.numCapitulo=" + data.data.capitulo +
			"&confirmarRequest.parte=" + data.data.parte +
			"&confirmarRequest.segmento=" + data.data.segmento +
			"&confirmarRequest.fraccion=" + data.data.fraccion +
			"&confirmarRequest.clave=" + data.data.clave;
	
	$.ajax({
		type : "GET",
		url : "confirmarSinAmortizacionConfirmar.action",
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			MESSAGE.ok("Se confirmo correctamente.");
			data.callback();
		}
	});
};