function TrabajarConCopiasDeTitulosService() {
};
TrabajarConCopiasDeTitulosService.prototype.getTitulos = function(data, callback) {
	BLOCK.showBlock("Se estan obteniendo los datos...");
	$.ajax({
		type : "GET",
		url : "trabajarConCopiasDeTitulosBuscarTitulos.action",
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
TrabajarConCopiasDeTitulosService.prototype.consultarDatosTitulo = function(data, callback) {
	BLOCK.showBlock("Se estan obteniendo los datos...");
	$.ajax({
		type : "GET",
		url : "trabajarConCopiasDeTitulosBuscarDatosCopias.action",
		data : "senial="+data.senial+"&clave="+data.clave,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.listadoCopias) {
				callback(response.listadoCopias);
			}
		}
	});
};
TrabajarConCopiasDeTitulosService.prototype.altaCopia = function(data, callback) {
	BLOCK.showBlock("Se estan registrando los datos...");
	$.ajax({
		type : "POST",
		url : "trabajarConCopiasDeTitulosAltaMasterRollos.action",
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			MESSAGE.ok("Alta correcta.");
			callback();
		}
	});
};
TrabajarConCopiasDeTitulosService.prototype.modifCopia = function(data, callback) {
	BLOCK.showBlock("Se estan modificando los datos...");
	$.ajax({
		type : "POST",
		url : "trabajarConCopiasDeTitulosModifMasterRollo.action",
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			MESSAGE.ok("Modificación correcta.");
			callback();
		}
	});
};
TrabajarConCopiasDeTitulosService.prototype.eliminarCopia = function(data, callback) {
	BLOCK.showBlock("Se estan eviando los datos...");
	var request = "bajaRequest.clave="+data.clave+
					"&bajaRequest.senial="+data.senial+
					"&bajaRequest.soporte="+data.copia.soporte+
					"&bajaRequest.copia="+data.copia.copia+
					"&bajaRequest.fecha="+data.copia.fecha+
					"&bajaRequest.secuencia="+data.copia.secuencia+
					"&bajaRequest.rollo="+data.copia.rollo;
	if (trabajarConCopiasDeTitulosEvent.tituloConCapitulos(data.clave)) {
		request = request + "&bajaRequest.capitulo="+data.copia.capitulo+
					"&bajaRequest.parte="+data.copia.parte+
					"&bajaRequest.segmento="+data.copia.segmento;
	}
	$.ajax({
		type : "POST",
		url : "trabajarConCopiasDeTitulosBajaMasterRollos.action",
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			MESSAGE.ok("Baja correcta.");
			callback();
		}
	});
};

TrabajarConCopiasDeTitulosService.prototype.verificarMasterSoporte = function(data, callback) {
	var request = "master.clave="+data.clave+
		"&master.senial="+data.senial+
		"&master.soporte="+data.copia.soporte;
	if (trabajarConCopiasDeTitulosEvent.tituloConCapitulos(data.clave)) {
		request = request + "&master.capitulo="+data.copia.capitulo+
			"&master.parte="+data.copia.parte+
			"&master.segmento="+data.copia.segmento;
	}
	$.ajax({
		type : "POST",
		url : "trabajarConCopiasDeTitulosVerificarMasterSoporte.action",
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.resultadoVerificacion) {
				callback(response.resultadoVerificacion);
			}
		}
	});
};

TrabajarConCopiasDeTitulosService.prototype.verificarRechazoCVR = function(data, callback) {
	var request = "master.clave="+data.clave+
		"&master.senial="+data.senial+
		"&master.soporte="+data.copia.soporte;
	$.ajax({
		type : "POST",
		url : "trabajarConCopiasDeTitulosVerificarRechazoCVR.action",
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.resultadoVerificacion) {
				callback(response.resultadoVerificacion);
			}
		}
	});
};

TrabajarConCopiasDeTitulosService.prototype.chequearMaterialesCopia = function(data, callback) {
	var request = "clave="+data.clave+"&senial="+data.senial;
	$.ajax({
		type : "POST",
		url : "trabajarConCopiasDeTitulosChequearMaterialesCopia.action",
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
//			if (response.resultadoVerificacion) {
				callback(response.resultadoVerificacion);
//			}
		}
	});
};

TrabajarConCopiasDeTitulosService.prototype.validarAlta = function(data, callback) {
	$.ajax({
		type : "POST",
		url : "trabajarConCopiasDeTitulosValidarAlta.action",
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.resultadoValidacionAlta) {
				callback(data,response.resultadoValidacionAlta);
			}
		}
	});
};

TrabajarConCopiasDeTitulosService.prototype.validarBaja = function(data, callback) {
	var request = "validarBajaRequest.clave="+data.clave+
		"&validarBajaRequest.senial="+data.senial+
		"&validarBajaRequest.masterOCopia="+data.copia.copia+
		"&validarBajaRequest.secuencia="+data.copia.secuencia;
	if (trabajarConCopiasDeTitulosEvent.tituloConCapitulos(data.clave)) {
	request = request + "&validarBajaRequest.capitulo="+data.copia.capitulo+
		"&validarBajaRequest.parte="+data.copia.parte+
		"&validarBajaRequest.segmento="+data.copia.segmento;
	}
	$.ajax({
		type : "POST",
		url : "trabajarConCopiasDeTitulosValidarBaja.action",
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.resultadoValidacionBaja) {
				callback(response.resultadoValidacionBaja);
			}
		}
	});
};
