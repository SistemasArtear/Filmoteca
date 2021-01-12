function TrabajarConTitulosService() {
};
TrabajarConTitulosService.prototype.getTitulosAmortizadosPorCodigo = function(data, tipoBusqueda) {
    BLOCK.showBlock("Se estan obteniendo los datos...");
    var request = "clave=" + data.clave + "&senial=" + data.senial;
    var url = tipoBusqueda === TrabajarConTitulosEvent.CASTELLANO ? "obtenerTitulosAmortizadosCastellanoPorCodigo.action" : "obtenerTitulosAmortizadosOriginalPorCodigo.action";
    $.ajax({
        type : 'GET',
        url : url,
        cache: false,
        data : request,
        success : function(response) {
            BLOCK.hideBlock();
            if (response.message) {
                MESSAGE.error(response.message);
                return;
            }
            if (response.titulosAmortizadosGrilla) {
                trabajarConTitulosAmortizadosEvent.responseTitulos(response.titulosAmortizadosGrilla);
            }
        }
    });
};
TrabajarConTitulosService.prototype.getTitulosAmortizadosPorDescripcion = function(data, tipoBusqueda) {
    BLOCK.showBlock("Se estan obteniendo los datos...");
    var request = "descripcion=" + data.descripcion + "&senial=" + data.senial;
    var url = tipoBusqueda === TrabajarConTitulosEvent.CASTELLANO ? "obtenerTitulosAmortizadosCastellanoPorDescripcion.action" : "obtenerTitulosAmortizadosOriginalPorDescripcion.action";
    $.ajax({
        type : 'GET',
        url : url,
        cache: false,
        data : request,
        success : function(response) {
            BLOCK.hideBlock();
            if (response.message) {
                MESSAGE.error(response.message);
                return;
            }
            if (response.titulosAmortizadosGrilla) {
                trabajarConTitulosAmortizadosEvent.responseTitulos(response.titulosAmortizadosGrilla);
            }
        }
    });
};
TrabajarConTitulosService.prototype.fueContabilizado = function(data) {
    BLOCK.showBlock("Se estan obteniendo los datos...");
    var url = "fueContabilizado.action";
    var request = data && data.codigo ? "clave="+data.codigo : null;
    $.ajax({
        type : 'GET',
        url : url,
        data : request,
        cache: false,
        success : function(response) {
            BLOCK.hideBlock();
            if (response.message) {
                MESSAGE.error(response.message);
                return;
            }
            if (response.fueContabilizado) {
                MESSAGE.error("El titulo ya fue contabilizado como amortizado sin exhibir, no puede volver a activarse");
            } else {
                trabajarConTitulosAmortizadosEvent.drawPopupActivarTitulo({codigo : data.codigo});
                trabajarConTitulosAmortizadosEvent.abrirPopupActivarTitulo();
            }
        }
    });
};
TrabajarConTitulosService.prototype.activarTitulo = function(data) {
    BLOCK.showBlock("Se estan obteniendo los datos...");
    var url = "activarTitulo.action";
    var request = data && data.codigo ? "clave="+data.codigo : null;
    $.ajax({
        type : 'POST',
        url : url,
        cache: false,
        data : request,
        success : function(response) {
            BLOCK.hideBlock();
            if (response.message) {
                MESSAGE.error(response.message);
                return;
            }
            if (response.tituloActivado) {
                MESSAGE.ok("Titulo activado");
            } else {
                MESSAGE.error("El titulo NO pudo ser activado");
            }
            trabajarConTitulosAmortizadosEvent.getGridTitulos().clearGridData().trigger('reloadGrid');
        }
    });
};
TrabajarConTitulosService.prototype.getTitulosPorDescripcion = function(data, tipoBusqueda) {
	BLOCK.showBlock("Se estan obteniendo los datos...");
	var request = "";
	if (data) {
		request = request + "senial=" + data.senial;
		request = request + (data.descripcion ? "&descripcion=" + data.descripcion : "");
	}
	var url = tipoBusqueda === TrabajarConTitulosEvent.CASTELLANO ? "obtenerTitulosCastellanoPorDescripcion.action" : "obtenerTitulosOriginalPorDescripcion.action";
	$.ajax({
		type : 'GET',
		url : url,
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.titulosGrilla) {
				trabajarConTitulosEvent.responseTitulos(response.titulosGrilla);
			}
		}
	});
};
TrabajarConTitulosService.prototype.getTitulosPorCodigo = function(data, tipoBusqueda) {
	BLOCK.showBlock("Se estan obteniendo los datos...");
	var request = "";
	if (data) {
		request = request + "senial=" + data.senial;
		request = request + (data.codigo ? "&clave=" + data.codigo : "");
	}
	var url = tipoBusqueda === TrabajarConTitulosEvent.CASTELLANO ? "obtenerTitulosCastellanoPorCodigo.action" : "obtenerTitulosOriginalPorCodigo.action";
	$.ajax({
		type : 'GET',
		url : url,
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.titulosGrilla) {
				trabajarConTitulosEvent.responseTitulos(response.titulosGrilla);
			}
		}
	});
};
TrabajarConTitulosService.prototype.abmTitulo = function(data, operation) {
	data = data + "&operacion=" + operation;
	$.ajax({
		type : 'POST',
		url : 'abmTitulo.action',
		data : data,
		success : function(response) {
			if (response) {
				MESSAGE.error(response);
				return;
			}
			var message;
			if (operation === TrabajarConTitulosEvent.ADD) {
				message = "El t&iacute;tulo se dio de alta correctamente.";
			}else{
				message = "El t&iacute;tulo se modificó correctamente.";
			}
			MESSAGE.ok(message);
			trabajarConTitulosEvent.cancelarABMTitulo();
			trabajarConTitulosEvent.buscarTitulos();
		}
	});
};
TrabajarConTitulosService.prototype.obtenerTitulo = function(senial, data) {
	$.ajax({
		type : 'GET',
		url : 'cargarTitulo.action?senial='+senial+'&clave='+data.clave,
		success : function(response) {
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.tituloView) {
				trabajarConTitulosEvent.completeFormForUpdate(response.tituloView);
			}
		}
	});
};
TrabajarConTitulosService.prototype.getContrato = function(data) {
	var url = 'cargarContrato.action?senial='+data.senial+'&clave=' + trabajarConTitulosEvent.rowSelected.clave + '&codigoContrato=' + data.codigo; 
	$.ajax({
		type : 'GET',
		url : url,
		success : function(response) {
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.contratoView) {
				trabajarConTitulosEvent.drawContrato(response.contratoView);
			}
		}
	});
};
TrabajarConTitulosService.prototype.getFichasCinematograficas = function(descripcion, tipoBusqueda) {
	var data = "descripcion="+descripcion;
	var url = "obtenerFichasCinematograficasOriginal.action";
	if (tipoBusqueda) {
		url = tipoBusqueda === TrabajarConTitulosEvent.CASTELLANO ? "obtenerFichasCinematograficasCast.action" : "obtenerFichasCinematograficasOriginal.action";
	}
	$.ajax({
		type : 'GET',
		url : url,
		data : data,
		success : function(response) {
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.fichasCinematograficas) {
				trabajarConTitulosEvent.openPopupFichas(response.fichasCinematograficas);
			}
		}
	});
};
TrabajarConTitulosService.prototype.getFicha = function(codigo) {
	var data = "codigoFicha="+codigo;
	$.ajax({
		type : 'GET',
		url : 'cargarFichaCinematografica.action',
		data : data,
		success : function(response) {
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.fichaCinematograficaView) {
				trabajarConTitulosEvent.completeFormForUpdate(response.fichaCinematograficaView);
			}
		}
	});
};
TrabajarConTitulosService.prototype.cargarDetalleContrato = function(row) {
	var data = "senial="+row.senial+'&clave=' + trabajarConTitulosEvent.rowSelected.clave + '&codigoContrato=' + row.codigo; 
	$.ajax({
		type : 'GET',
		url : 'trabajarConTitulosCargarDetalleContrato.action',
		data : data,
		success : function(response) {
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.detalleContrato) {
				trabajarConTitulosEvent.visualizarContrato(response.detalleContrato);
			}
		}
	});
};