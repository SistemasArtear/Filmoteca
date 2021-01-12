function TrabajarConRemitosMaterialesService() {
};

TrabajarConRemitosMaterialesService.prototype.validarDistribuidor = function(data) {
	var result = false;
	$.ajax({
		async: false,
		type : 'GET',
		url : "validarDistribuidor.action",
		data : data,
		success : function(response) {
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.warning) {
				Validator.focus(trabajarConRemitosMaterialesEvent.getSelector("codigoDistribuidor"), response.warning);
				return;
			}
			result = true;
		}
	});
	return result;
};

TrabajarConRemitosMaterialesService.prototype.validarFechaSenial = function(data) {
	var result = false;
	$.ajax({
		async: false,
		type : 'GET',
		url : "validarFechaSenial.action",
		data : data,
		success : function(response) {
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.warning) {
				Validator.focus(trabajarConRemitosMaterialesEvent.getSelector("fechaRemito"), response.warning);
				return;
			}
			result = true;
		}
	});
	return result;
};

TrabajarConRemitosMaterialesService.prototype.getDatosDistribuidor = function(data) {
	var razonSocialDistribuidor = new String();
	
	$.ajax({
		async: false,
		type : 'GET',
		url : "buscarDistribuidoresParaMateriales.action",
		data : data,
		success : function(response) {
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.distribuidores && response.distribuidores.length == 1) {
				razonSocialDistribuidor = response.distribuidores[0].razonSocial;
			}
		}
	});
	
	return razonSocialDistribuidor;
};

TrabajarConRemitosMaterialesService.prototype.actualizarCantidad = function(data) {
	$.ajax({
		type : 'POST',
		url : "actualizarCantidad.action",
		async: false,
		data :  Component.serialize(data, "actualizarCantidadRequest"),
		success : function(response) {
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
		}
	});
};

TrabajarConRemitosMaterialesService.prototype.validarNuevaSenial = function(data) {
	$.ajax({
		type : 'GET',
		url : "validarNuevaSenial.action",
		data :  Component.serialize(data, "validarNuevaSenialRequest"),
		success : function(response) {
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.validarNuevaSenialResponse.mensaje) {
				trabajarConRemitosMaterialesEvent.abrirPopupErrores(response.validarNuevaSenialResponse);
				
			} else {
				trabajarConRemitosMaterialesEvent.recargarGrillaTitulos();

			}
		}
	});
};

TrabajarConRemitosMaterialesService.prototype.ejecutarRemito = function(data) {
	var result;
	$.ajax({
		type : 'POST',
		url : "ejecutarRemito.action",
		async: false,
		data : Component.serialize(data, "ejecutarRemitoRequest"),
		success : function(response) {
			
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.ejecutarRemitoResponse && response.ejecutarRemitoResponse.mensaje != "OK") {
				MESSAGE.alert(response.ejecutarRemitoResponse.mensaje);
			} else {
				MESSAGE.ok("Se ha guardado el remito con éxito");
			}
			trabajarConRemitosMaterialesEvent.remito.idRemito = response.ejecutarRemitoResponse.idRemito;
			result = response.ejecutarRemitoResponse.idRemito;
		}
	});
	return result;
};

TrabajarConRemitosMaterialesService.prototype.imprimirRemito = function(data) {
	$.ajax({
		type : 'POST',
		url : "imprimirRemito.action",
		data : Component.serialize(data, "imprimirRemitoRequest"),
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.imprimirRemitoResponse) {
				var reporte = new String();
				$(response.imprimirRemitoResponse).each(function(i, detalleRemito) {
				    reporte += "Tipo título: " + detalleRemito.tipoTitulo;
				    reporte += ", Nro. título: " + detalleRemito.nroTitulo;
				    reporte += ", Nro. capítulo: " + detalleRemito.nroCapitulo;
				    reporte += ", Nro. parte: " + detalleRemito.nroParte;
				    reporte += ", Soporte: " + detalleRemito.soporte + "\n";
				});
				//alert(reporte);
			} 
		}
	});
};

TrabajarConRemitosMaterialesService.prototype.generarReporteRemito = function(data) {
	$.ajax({
		type : 'POST',
		url : "generarReporteRemito.action",
		data : Component.serialize(data, "imprimirRemitoRequest"),
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			MESSAGE.ok("Reporte Exitoso");
		}
	});
};

TrabajarConRemitosMaterialesService.prototype.validarCapitulo = function(data) {
	var result = false;
	$.ajax({
		async: false,
		type : 'GET',
		url : "buscarCapitulosParaCarga.action",
		data : data,
		success : function(response) {
			if (response.capitulos != undefined && response.capitulos.length > 0) {
				result = true;
			}else {
				result = false;
			}
			
		}
	});
	return result;
};