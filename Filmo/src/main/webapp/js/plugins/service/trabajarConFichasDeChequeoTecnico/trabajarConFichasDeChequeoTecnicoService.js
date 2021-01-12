function TrabajarConFichasDeChequeoTecnicoService() {
};
TrabajarConFichasDeChequeoTecnicoService.prototype.buscarFichas = function(data) {
	BLOCK.showBlock("Se estan obteniendo los datos...");
	$.ajax({
		type : 'GET',
		url : 'buscarFichas.action',
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.busquedaFichasResponse) {
				trabajarConFichasDeChequeoTecnicoEvent.responseFichas(response.busquedaFichasResponse);
			}
		}
	});
};
TrabajarConFichasDeChequeoTecnicoService.prototype.visualizarFicha = function(data) {
	BLOCK.showBlock("Se estan obteniendo los datos...");
	var request = "visualizarFichasRequest.nroFicha="+data;
	$.ajax({
		type : 'GET',
		url : 'visualizarFicha.action',
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.visualizarFichaResponse) {
				response.visualizarFichaResponse.ficha.nroFicha = data;
				trabajarConFichasDeChequeoTecnicoEvent.responseVisualizarFicha(response.visualizarFichaResponse);
			}
		}
	});
};
TrabajarConFichasDeChequeoTecnicoService.prototype.cargarFicha = function(data, callback) {
	BLOCK.showBlock("Se estan obteniendo los datos...");
	var request = "visualizarFichasRequest.nroFicha="+data;
	$.ajax({
		type : 'GET',
		url : 'visualizarFicha.action',
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.visualizarFichaResponse) {
				callback(response.visualizarFichaResponse);
			}
		}
	});
};
TrabajarConFichasDeChequeoTecnicoService.prototype.obtenerInfoPrograma = function(clave, capitulo, callback) {
	BLOCK.showBlock("Se estan obteniendo los datos...");
	var request = "clave="+ clave + "&capitulo=" + capitulo;
	$.ajax({
		type : 'GET',
		url : 'chequeoTecnicoObtenerInfoChequeoPrograma.action',
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.chequeo) {
				callback(response.chequeo);
			}
		}
	});
};
TrabajarConFichasDeChequeoTecnicoService.prototype.eliminarFicha = function(data, callback) {
	BLOCK.showBlock("Se está eliminando la ficha...");
	var request = "nroFicha="+data.nroFicha+"&clave="+data.clave;
	$.ajax({
		type : 'POST',
		url : 'chequeoTecnicoEliminarFicha.action',
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.errores) {
                var hayErrores = false;
                var hayWarnings = false;
                var warnings  = new Array();
                var errores  = new Array();
                $.each(response.errores, function(i, l) {
                    if (l["tipoError"] === "E") {
                        hayErrores = true;
                        errores.push(l["descripcion"]);
                    } else if (l["tipoError"] === "W") {
                        hayWarnings = true;
                        warnings.push(l["descripcion"]);
                    }
                });
                if (hayErrores) {
                    MESSAGE.error(errores.join("<br/>"));
                } else if (hayWarnings) {
                    MESSAGE.error(warnings.join("<br/>"));
			    } else {
			        //MESSAGE.ok("La ficha " + data.nroFicha + " se di&oacute; de baja.");
			        callback();
			    }                
				return;
			}
			
		}
	});
};
TrabajarConFichasDeChequeoTecnicoService.prototype.altaFicha = function(data, callback) {
	BLOCK.showBlock("Se está dando de alta la ficha...");
	$.ajax({
		type : 'POST',
		url : 'chequeoTecnicoAltaCabeceraFicha.action',
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			//MESSAGE.ok("La ficha se di&oacute; de alta.");
			callback();
		}
	});
};
TrabajarConFichasDeChequeoTecnicoService.prototype.modificarFicha = function(data, callback) {
	BLOCK.showBlock("Se está modificando la ficha...");
	$.ajax({
		type : 'POST',
		url : 'chequeoTecnicoModificarCabeceraFicha.action',
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			//MESSAGE.ok("La ficha se modificó correctamente.");
			callback();
		}
	});
};
TrabajarConFichasDeChequeoTecnicoService.prototype.inactivarFicha = function(data, callback) {
	BLOCK.showBlock("Se está inactivando la ficha...");
	var request = "nroFicha="+data.nroFicha+"&clave="+data.clave;
	$.ajax({
		type : 'POST',
		url : 'chequeoTecnicoInactivarFicha.action',
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			//MESSAGE.ok("La ficha " + data.nroFicha + " se inactivo.");
			callback();
		}
	});
};
TrabajarConFichasDeChequeoTecnicoService.prototype.infoVigenciaContratos = function(data, callback) {
	BLOCK.showBlock("Buscando vigencia de contratos...");
	var request = "nroFicha="+data.nroFicha+"&clave="+data.clave;
	$.ajax({
		type : 'GET',
		url : 'chequeoTecnicoInfoVigenciaContratos.action',
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.vigenciaContratos) {
				callback(response.vigenciaContratos);
			}
		}
	});
};
TrabajarConFichasDeChequeoTecnicoService.prototype.validarTituloEnCanal = function(data, callback) {
	BLOCK.showBlock("Validando que el titulo este en el canal...");
	var request = "senial="+data.senial+"&clave="+data.clave+"&capitulo="+data.capitulo+"&parte="+data.parte;
	$.ajax({
		type : 'GET',
		url : 'chequeoTecnicoValidarTituloEnCanal.action',
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.resultadoValidacion) {
				callback(response.resultadoValidacion);
			}
		}
	});
};
TrabajarConFichasDeChequeoTecnicoService.prototype.validarSegmentos = function(data, callback) {
	BLOCK.showBlock("Validando segmentos...");
	var request = data.segmentos+"&segmentosRequest.duracionArtistica="+data.duracionArtistica+"&segmentosRequest.cantidadSegmentos="+data.cantidadSegmentos;
	$.ajax({
		type : 'GET',
		url : 'chequeoTecnicoValidarSegmentos.action',
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			callback(response.resultadoValidacionSegmentos);
		}
	});
};
TrabajarConFichasDeChequeoTecnicoService.prototype.eliminarSegmentos = function(nroFicha, callback) {
	BLOCK.showBlock("Eliminando segmentos...");
	var request = "nroFicha="+nroFicha;
	$.ajax({
		type : 'GET',
		url : 'chequeoTecnicoEliminarSegmentos.action',
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			//MESSAGE.ok("Se eliminaron los segmentos.");
			callback();
		}
	});
};
TrabajarConFichasDeChequeoTecnicoService.prototype.guardarSegmentos = function(request, callback) {
	BLOCK.showBlock("Guardando segmentos...");
	$.ajax({
		type : 'GET',
		url : 'chequeoTecnicoGuardarSegmentos.action',
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			//MESSAGE.ok("Se guardaron los segmentos.");
			callback();
		}
	});
};

TrabajarConFichasDeChequeoTecnicoService.prototype.guardarActoresSinopsisYObservaciones = function(request, callback) {
	BLOCK.showBlock("Guardando actores, sinopsis y observaciones...");
	$.ajax({
		type : 'GET',
		url : 'chequeoTecnicoGuardarActoresSinopsisYObservaciones.action',
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			//MESSAGE.ok("Se guardaron los datos.");
			callback();
		}
	});
};

TrabajarConFichasDeChequeoTecnicoService.prototype.validarChequeoDetalle = function(request, callback) {
	BLOCK.showBlock("Validando datos de ficha...");
	$.ajax({
		type : 'GET',
		url : 'chequeoTecnicoValidarChequeoDetalle.action',
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			callback();
		}
	});
};

TrabajarConFichasDeChequeoTecnicoService.prototype.guardarChequeoDetalle = function(request, callback) {
	BLOCK.showBlock("Guardando datos de ficha...");
	$.ajax({
		type : 'GET',
		url : 'chequeoTecnicoGuardarChequeoDetalle.action',
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			//MESSAGE.ok("Se guardaron los datos de la ficha.");
			callback();
			trabajarConFichasDeChequeoTecnicoEvent.buscarFichas();
		}
	});
};

TrabajarConFichasDeChequeoTecnicoService.prototype.buscarSenialesOkFilm = function(nroFicha, film, callback) {
	$.ajax({
		type : 'GET',
		url : 'chequeoTecnicoBuscarSenialesOkFilm.action',
		data : "nroFicha="+nroFicha+"&film="+film,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.senialesOkFilm) {
				callback(response.senialesOkFilm);
			}
		}
	});
};
TrabajarConFichasDeChequeoTecnicoService.prototype.guardarSenialesOkFilm = function(request, callback) {
	$.ajax({
		type : 'GET',
		url : 'chequeoTecnicoGuardarSenialesOkFilm.action',
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			//MESSAGE.ok("Se guardaron los datos.");
			callback();
		}
	});
};
/*TrabajarConFichasDeChequeoTecnicoService.prototype.popupTitulosConContratosExhibidos = function(data, callback) {
	var request = "contratosExhibidosRequest.clave="+data.clave
					+"&contratosExhibidosRequest.senial="+data.senial
					+"&contratosExhibidosRequest.film="+data.film
					+"&contratosExhibidosRequest.capitulo="+data.capitulo
					+"&contratosExhibidosRequest.parte="+data.parte;
	$.ajax({
		type : 'GET',
		url : 'chequeoTecnicoPopupTitulosConContratosExhibidos.action',
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.resultadoPopup) {
				callback(response.resultadoPopup);
			}
		}
	});
};*/

TrabajarConFichasDeChequeoTecnicoService.prototype.popupTitulosConContratosExhibidos = function(nroFicha, film, callback) {
	BLOCK.showBlock("popupTitulosConContratosExhibidos...");
	$.ajax({
		type : 'GET',
		url : 'chequeoTecnicoPopupTitulosConContratosExhibidos.action',
		data : "nroFicha="+nroFicha+"&film="+film,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.resultadoPopup) {
				callback(response.resultadoPopup);
			}
		}
	});
};

TrabajarConFichasDeChequeoTecnicoService.prototype.popupTitulosConContratosExhibidosSN = function(nroFicha, film, callback) {
	BLOCK.showBlock("popupTitulosConContratosExhibidosSN...");
	$.ajax({
		type : 'GET',
		url : 'chequeoTecnicoPopupTitulosConContratosExhibidosSN.action',
		data : "nroFicha="+nroFicha+"&film="+film,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.resultadoPopup) {
				callback(response.resultadoPopup);
			}
		}
	});
};

TrabajarConFichasDeChequeoTecnicoService.prototype.popupVerificarDesenlace = function(data, callback) {
	BLOCK.showBlock("Popup Verificar Desenlace...");
	var request = "verificarDesenlaceRequest.nroFicha="+data.nroFicha
		+"&verificarDesenlaceRequest.clave="+data.clave		
		+"&verificarDesenlaceRequest.capitulo="+data.capitulo
		+"&verificarDesenlaceRequest.parte="+data.parte
	    +"&verificarDesenlaceRequest.okFilmo="+data.okFilmo;
	
	$.ajax({
		type : 'GET',
		url : 'chequeoTecnicoVerificarDesenlace.action',
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.verificarDesenlaceResponse) {
				callback(response);
			}
		}
	});
};

TrabajarConFichasDeChequeoTecnicoService.prototype.validarPayTV = function(nroFicha, clave, callback) {
	BLOCK.showBlock("Validando PayTV...");
	
	
	$.ajax({
		type : 'GET',
		url : 'chequeoTecnicoValidarPayTV.action',
		data : "nroFicha="+nroFicha+"&clave="+clave,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.validarPayTV) {
				callback(response);
			}
		}
	});
};

TrabajarConFichasDeChequeoTecnicoService.prototype.ejecutarDesenlace = function(data, callback) {
	BLOCK.showBlock("Ejecutar Desenlace...");
	var request = "verificarDesenlaceRequest.nroFicha="+data.nroFicha
		+"&verificarDesenlaceRequest.clave="+data.clave		
		+"&verificarDesenlaceRequest.capitulo="+data.capitulo
		+"&verificarDesenlaceRequest.parte="+data.parte;
	$.ajax({
		type : 'GET',
		url : 'chequeoTecnicoEjecutarDesenlace.action',
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.mensaje != "OK") {
				MESSAGE.error(response.message);
				return;
			}
			else {
				callback(response);
			}
		}
	});
};

TrabajarConFichasDeChequeoTecnicoService.prototype.actualizarPY6001 = function(data) {
	BLOCK.showBlock("Actualizar PY6001...");
	$.ajax({
		type : 'GET',
		url : 'chequeoTecnicoActualizarPY6001.action',
		data : "nroFicha="+data.nroFicha,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			//MESSAGE.ok("OK");
			//callback();
		}
	});
};


TrabajarConFichasDeChequeoTecnicoService.prototype.popupTitulosConContratosExhibidosRechazo = function(nroFicha, callback) {
	BLOCK.showBlock("Ejecutar Rechazo...");
	$.ajax({
		type : 'GET',
		url : 'chequeoTecnicoPopupTitulosConContratosExhibidosRechazo.action',
		data : "nroFicha="+nroFicha,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			//MESSAGE.ok("Rechazo Exitoso.");
			callback();
		}
	});
};

TrabajarConFichasDeChequeoTecnicoService.prototype.buscarRollos = function(data, callback) {
	var request = "senial="+data.senial+"&nroFicha="+data.ficha;
	$.ajax({
		type : 'GET',
		url : 'chequeoTecnicoBuscarRollos.action',
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.rollos) {
				callback(response.rollos);
			}
		}
	});
};

TrabajarConFichasDeChequeoTecnicoService.prototype.altaDeRollos = function(data, callback) {
	$.ajax({
		type : 'GET',
		url : 'chequeoTecnicoAltaDeRollos.action',
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			//MESSAGE.ok("Alta exitosa.");
			callback();
		}
	});
};

TrabajarConFichasDeChequeoTecnicoService.prototype.modificacionDeRollos = function(data, callback) {
	$.ajax({
		type : 'GET',
		url : 'chequeoTecnicoAltaOModificacionDeRollos.action',
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			//MESSAGE.ok("Modificación exitosa.");
			callback();
		}
	});
};

TrabajarConFichasDeChequeoTecnicoService.prototype.buscarContratosParaAsociarLaCopia = function(data, callback) {
	var request = "clave="+data.clave+"&senial="+data.senial+"&nroFicha="+data.nroFicha;
	$.ajax({
		type : 'GET',
		url : 'chequeoTecnicoBuscarContratosParaAsociarLaCopia.action',
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.contratosParaAsociar) {
				callback(response.contratosParaAsociar);
			}
		}
	});
};

TrabajarConFichasDeChequeoTecnicoService.prototype.registrarMaster = function(data, callback) {
	var request = "clave="+data.clave+"&senial="+data.senial+"&nroFicha="+data.nroFicha;
	$.ajax({
		type : 'GET',
		url : 'chequeoTecnicoRegistrarMaster.action',
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			//MESSAGE.ok("Asociación exitosa.");
			callback();
		}
	});
};

TrabajarConFichasDeChequeoTecnicoService.prototype.imprimirReporte = function(data, callback) {
	var request = "nroFicha="+data.nroFicha;
	$.ajax({
		type : 'GET',
		url : 'generarReporteFichaChequeo.action',
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			//MESSAGE.ok("Reporte Exitoso");
			callback();
		}
	});
};