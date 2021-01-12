function ModificacionIngresoDeMaterialesService() {
};

ModificacionIngresoDeMaterialesService.prototype.buscarRemitosPorNumeroRemito = function(data) {
	BLOCK.showBlock("Buscando remitos...");
	var url = "buscarRemitosPorNumeroRemito.action";
	$.ajax({
		type : 'GET',
		url : url,
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.remitos) {
				modificacionIngresoDeMaterialesEvent.fillGridNroOrdenRemito(response.remitos);
			}
		}
	});
};

ModificacionIngresoDeMaterialesService.prototype.buscarRemitosPorNumeroGuia = function(data) {
	BLOCK.showBlock("Buscando remitos...");
	var url = "buscarRemitosPorGuia.action";
	$.ajax({
		type : 'GET',
		url : url,
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.remitos) {
				modificacionIngresoDeMaterialesEvent.fillGridNroOrdenRemito(response.remitos);
			}
		}
	});
};

ModificacionIngresoDeMaterialesService.prototype.buscarRemitosPorRazonSocial = function(data) {
	BLOCK.showBlock("Buscando remitos...");
	var url = "buscarRemitosPorRazonSocial.action";
	$.ajax({
		type : 'GET',
		url : url,
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.remitos) {
				modificacionIngresoDeMaterialesEvent.fillGridNroOrdenRemito(response.remitos);
			}
		}
	});
};

ModificacionIngresoDeMaterialesService.prototype.buscarRemitosPorFecha = function(data) {
	BLOCK.showBlock("Buscando remitos...");
	var url = "buscarRemitosPorFecha.action";
	$.ajax({
		type : 'GET',
		url : url,
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.remitos) {
				modificacionIngresoDeMaterialesEvent.fillGridNroOrdenRemito(response.remitos);
			}
		}
	});
};

ModificacionIngresoDeMaterialesService.prototype.validarExhibiciones = function(data) {
	var url = "validarExhibiciones.action";
	
	$.ajax({
		type : 'GET',
		url : url,
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.exhibicionesRow != null && response.exhibicionesRow.length > 0) {
				modificacionIngresoDeMaterialesEvent.abrirPopupWarningCabecera(response.exhibicionesRow);
			} else {
				modificacionIngresoDeMaterialesEvent.abrirPopUpModificarCabeceraRemito();
			}
		}
	});
};

ModificacionIngresoDeMaterialesService.prototype.modificarCabeceraRemito = function(data) {
	BLOCK.showBlock("Modificando cabecera del remito...");
	var url = "modificarCabeceraRemito.action";
	$.ajax({
		type : 'POST',
		url : url,
		data : Component.serialize(data, "cabeceraRemitoABM"),
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			MESSAGE.ok("Se modificó el remito exitosamente");
			modificacionIngresoDeMaterialesEvent.volverBusquedaRemito();
			modificacionIngresoDeMaterialesEvent.determinarTipoBusqueda();
		}
	});
};

ModificacionIngresoDeMaterialesService.prototype.validarEliminarRemito = function(data) {
	var url = "validarEliminarRemito.action";
	
	$.ajax({
		async: false,
		type : 'GET',
		url : url,
		data : Component.serialize(data, "validarEliminarRemitoRequest"),
		success : function(response) {
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			var resultadoValidaciones = response.validarEliminarRemitoResponse;
			
			if (resultadoValidaciones.errorValidarCabeceraRemito) {
				MESSAGE.alert(resultadoValidaciones.mensaje);
				return;
			}
			/* Abrir popup confirmar warning */
			if (resultadoValidaciones.confirmacionValidarCabeceraRemito) {
				modificacionIngresoDeMaterialesEvent.abrirPopUpConfirmarContabilizado(resultadoValidaciones.mensaje);
			}
			/* Abrir popup confirmar desenlace */
			if (resultadoValidaciones.confirmacionContabiliza) {
				modificacionIngresoDeMaterialesEvent.abrirPopUpValidarDesenlace();
			}
			/* Abrir popup confirmacion final */
			if (resultadoValidaciones.eliminarRemito) {
				modificacionIngresoDeMaterialesEvent.abrirPopUpEliminarRemito(data.idRemito);
			}
		}
	});
};

ModificacionIngresoDeMaterialesService.prototype.validarContabilizadoCabeceraRemito = function(data) {
	var url = "validarContabilizadoCabeceraRemito.action";
	
	$.ajax({
		async: false,
		type : 'GET',
		url : url,
		data : Component.serialize(data, "validarEliminarRemitoRequest"),
		success : function(response) {
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			var resultadoValidaciones = response.validarEliminarRemitoResponse;
			/* Abrir popup confirmar desenlace */
			if (resultadoValidaciones.confirmacionContabiliza) {
				modificacionIngresoDeMaterialesEvent.abrirPopUpValidarDesenlace();
			}
			/* Abrir popup confirmacion final */
			if (resultadoValidaciones.eliminarRemito) {
				modificacionIngresoDeMaterialesEvent.abrirPopUpEliminarRemito(data.idRemito);
			}
		}
	});
};

ModificacionIngresoDeMaterialesService.prototype.validarDesenlaceRemito = function(data) {
	var url = "validarDesenlaceRemito.action";
	
	$.ajax({
		async: false,
		type : 'GET',
		url : url,
		data : data,
		success : function(response) {
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.warning) {
				modificacionIngresoDeMaterialesEvent.abrirPopUpChequeoDesenlaceRemito(response.warning);
			} else {
				modificacionIngresoDeMaterialesEvent.abrirPopUpEliminarRemito(data.idRemito);
			}
		}
	});
};


ModificacionIngresoDeMaterialesService.prototype.eliminarRemito = function(data) {
	BLOCK.showBlock("Eliminando el remito...");
	
	var url = "eliminarRemito.action";
	$.ajax({
		type : 'POST',
		url : url,
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			MESSAGE.ok("Se eliminó el remito exitosamente");
			modificacionIngresoDeMaterialesEvent.determinarTipoBusqueda();
		}
	});
};

ModificacionIngresoDeMaterialesService.prototype.buscarTitulosRemito = function(data) {
	BLOCK.showBlock("Buscando titulos...");
	var url = "buscarTitulosRemito.action";
	$.ajax({
		type : 'GET',
		url : url,
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.titulosRemito) {
				modificacionIngresoDeMaterialesEvent.fillGridTitulos(response.titulosRemito);
			}
		}
	});
};

ModificacionIngresoDeMaterialesService.prototype.validarSoporte = function(data) {
	var result = false;
	$.ajax({
		async: false,
		type : 'GET',
		url : "validarSoporte.action",
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			result = !response.result;
		}
	});
	return result;
};

ModificacionIngresoDeMaterialesService.prototype.validarMotivoIngreso = function(data) {
	var result = false;
	$.ajax({
		async: false,
		type : 'GET',
		url : "validarMotivoIngreso.action",
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			result = !response.result;
		}
	});
	return result;
};

ModificacionIngresoDeMaterialesService.prototype.validarModificarDetalleRemito = function(data) {
	var isValid = true;
	var url = "validarModificarDetalleRemito.action";
	$.ajax({
		async: false,
		type : 'GET',
		url : url,
		data : Component.serialize(data.validarModificarEliminarDetalleRequest, "validarModificarEliminarDetalleRequest"),
		success : function(response) {
			if (response.message) {
				MESSAGE.error(response.message);
				isValid = false;
				return;
			}
			if (response.warning) {
				MESSAGE.alert(response.warning);
				isValid = false;
				return;
			}
		}
	});
	return isValid;
};

ModificacionIngresoDeMaterialesService.prototype.modificarDetalleRemito = function(data) {
	BLOCK.showBlock("Modificando detalle del remito...");
	var url = "modificarDetalleRemito.action";
	$.ajax({
		type : 'POST',
		url : url,
		data : Component.serialize(data.detalleRemitoABM, "detalleRemitoABM"),
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			MESSAGE.ok("Se modificó el detalle del remito exitosamente");
			modificacionIngresoDeMaterialesEvent.volverSoporteTitulo();
			modificacionIngresoDeMaterialesEvent.buscarTitulosRemito();
		}
	});
	
};

ModificacionIngresoDeMaterialesService.prototype.validarEliminarDetalleRemito = function(data) {
	var isValid = true;
	var url = "validarEliminarDetalleRemito.action";
	
	$.ajax({
		async: false,
		type : 'GET',
		url : url,
		data : Component.serialize(data.validarModificarEliminarDetalleRequest, "validarModificarEliminarDetalleRequest"),
		success : function(response) {
			if (response.message) {
				isValid = false;
				MESSAGE.error(response.message);
				return;
			}
			var resultadoValidaciones = response.validarEliminarDetalleRemitoResponse;
			modificacionIngresoDeMaterialesEvent.remito.detalle.borroCh = resultadoValidaciones.borroCh;
			
			if (resultadoValidaciones.errorValidarContabilizado || resultadoValidaciones.errorVerificar || resultadoValidaciones.errorValidarRPF22022) {
				isValid = false;
				MESSAGE.error(resultadoValidaciones.respuesta);
			}
			if (resultadoValidaciones.confirmacionContabiliza) {
				isValid = false;
				modificacionIngresoDeMaterialesEvent.abrirPopUpConfirmacionContabiliza(resultadoValidaciones.respuesta);
			}
			if (resultadoValidaciones.eliminarRemitoDesde) {
				isValid = true;
				Component.get("html/modificacionIngresoDeMateriales/detalleRemitoSeleccionado.html", modificacionIngresoDeMaterialesEvent.drawTituloSeleccionado);
				modificacionIngresoDeMaterialesEvent.abrirPopUpEliminarDetalleRemito(modificacionIngresoDeMaterialesEvent.eliminarDetalleRemitoDesde);
			}
		}
	});
	return isValid;
};

ModificacionIngresoDeMaterialesService.prototype.chequeoEliminarRemitoDesde = function(data) {
//	var url = "chequeoEliminarRemitoDesde.action";
//	
//	$.ajax({
//		type : 'GET',
//		url : url,
//		data : Component.serialize(data.validarModificarEliminarDetalleRequest, "validarModificarEliminarDetalleRequest"),
//		success : function(response) {
//			if (response.message) {
//				MESSAGE.error(response.message);
//				return;
//			}
//			if (response.warning) {
//				modificacionIngresoDeMaterialesEvent.abrirPopUpChequeoDesenlace(response.warning);
//			} else {
//				Component.get("html/modificacionIngresoDeMateriales/detalleRemitoSeleccionado.html", modificacionIngresoDeMaterialesEvent.drawTituloSeleccionado);
//				modificacionIngresoDeMaterialesEvent.abrirPopUpEliminarDetalleRemito(modificacionIngresoDeMaterialesEvent.eliminarDetalleRemitoDesde);
//			}
//		}
//	});
	
	modificacionIngresoDeMaterialesEvent.abrirPopUpEliminarDetalleRemito(modificacionIngresoDeMaterialesEvent.eliminarDetalleRemitoDesde);
};

ModificacionIngresoDeMaterialesService.prototype.eliminarDetalleRemito = function(data) {
	var url = "eliminarDetalleRemito.action";
	
	$.ajax({
		type : 'POST',
		url : url,
		data : Component.serialize(data.validarModificarEliminarDetalleRequest, "validarModificarEliminarDetalleRequest"),
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			MESSAGE.ok("Se eliminó el detalle del remito exitosamente");
			modificacionIngresoDeMaterialesEvent.volverSoporteTitulo();
			modificacionIngresoDeMaterialesEvent.buscarTitulosRemito();
		}
	});
};

ModificacionIngresoDeMaterialesService.prototype.eliminarDetalleRemitoDesde = function(data) {
	var url = "eliminarDetalleRemitoDesde.action";
	
	$.ajax({
	    async : false,
		type : 'POST',
		url : url,
		data : Component.serialize(data.validarModificarEliminarDetalleRequest, "validarModificarEliminarDetalleRequest"),		
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			MESSAGE.ok("Se eliminó el detalle del remito exitosamente");
			if (modificacionIngresoDeMaterialesEvent.getGridTitulos().getDataIDs().length-1 > 0) {
		         modificacionIngresoDeMaterialesEvent.volverSoporteTitulo();
		         modificacionIngresoDeMaterialesEvent.buscarTitulosRemito();
			} else {
			    modificacionIngresoDeMaterialesEvent.volverSoporteTitulo(); 
	            modificacionIngresoDeMaterialesEvent.volverBusquedaTitulos();
	            modificacionIngresoDeMaterialesEvent.determinarTipoBusqueda(modificacionIngresoDeMaterialesEvent.getSelectTipoBusqueda().val());
	            modificacionIngresoDeMaterialesEvent.drawGridOrden();
			}

		}
	});
};