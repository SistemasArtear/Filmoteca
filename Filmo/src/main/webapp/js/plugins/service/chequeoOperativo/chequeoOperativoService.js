function ChequeoOperativoService() {
};

ChequeoOperativoService.prototype.chequearFecha = function(data){
	
	//chequeoOpChequearFecha
	BLOCK.showBlock("Se estan obteniendo los datos...");
	$.ajax({
		type : 'GET',
		url : 'validarChequeoOperativo.action',
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.resultado != 'ok') {
				MESSAGE.error(response.message);
				return false;
			}
			chequeoOperativoEvent.periodoCO = data;
			chequeoOperativoEvent.service.generarReportes(data);
			
		}
	});
};

ChequeoOperativoService.prototype.generarReportes = function(data){
	$.ajax({
		type : 'GET',
		url : 'generarReportesChequeoOperativo.action',
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.resultado != 'ok') {
				MESSAGE.error(response.message);
				return false;
			}
			else {
				if(response.hayRegistrosReporteLibreria) {
					MESSAGE.error("Hay errores operativos, se emitio listado id = " + response.idReporteLibreria);
				}
				else {
					Component.get("html/chequeoOperativo/popUpChequeoOperativo.html", chequeoOperativoEvent.drawPopUpChequeoOperativo);
				}
				return true;
			}
		}
	});
};


ChequeoOperativoService.prototype.procesarChequeo = function(data){
	BLOCK.showBlock("Se esta procesando el chequeo...");
	$.ajax({
		type : 'GET',
		url : 'procesarChequeoOperativo.action',
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.resultado != 'ok') {
				MESSAGE.error(response.message);
				return false;
			}
			MESSAGE.ok("Chequeo procesado");
			return true;
		}
	});
};

ChequeoOperativoService.prototype.confirmarChequeo = function(data){
	BLOCK.showBlock("Se esta confirmando el chequeo...");
	$.ajax({
		type : 'GET',
		url : 'confirmarChequeoOperativo.action',
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.resultado != 'ok') {
				MESSAGE.error(response.message);
				return false;
			}
			MESSAGE.ok("Chequeo confirmado")
			return true;
		}
	});
};
