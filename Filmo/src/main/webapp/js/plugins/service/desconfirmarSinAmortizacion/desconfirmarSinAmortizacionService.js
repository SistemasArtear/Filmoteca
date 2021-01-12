function DesconfirmarSinAmortizacionService() {
};
DesconfirmarSinAmortizacionService.prototype.getProgramas = function(data) {
	BLOCK.showBlock("Se estan obteniendo los datos...");
	$.ajax({
		type : "GET",
		url : "desconfirmarSinAmortizacionBuscarTitulosProgramas.action",
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
DesconfirmarSinAmortizacionService.prototype.desconfirmar = function(data) {
	BLOCK.showBlock("Se est&aacute; realizando la acci&oacute;n...");
	var request = "desconfirmarRequest.senial=" + data.data.senial +
			"&desconfirmarRequest.fecha=" + data.data.fecha + 
			"&desconfirmarRequest.programa=" + data.data.programa +
			"&desconfirmarRequest.numCapitulo=" + data.data.capitulo +
			"&desconfirmarRequest.parte=" + data.data.parte +
			"&desconfirmarRequest.segmento=" + data.data.segmento +
			"&desconfirmarRequest.fraccion=" + data.data.fraccion +
			"&desconfirmarRequest.clave=" + data.data.clave;
	
	$.ajax({
		type : "GET",
		url : "desconfirmarSinAmortizacionDesconfirmar.action",
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			MESSAGE.ok("Se desconfirmo correctamente.");
			data.callback();
		}
	});
};