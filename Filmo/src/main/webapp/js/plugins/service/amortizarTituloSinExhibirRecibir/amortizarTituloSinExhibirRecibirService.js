function AmortizarTituloSinExhibirRecibirService() {
};

AmortizarTituloSinExhibirRecibirService.prototype.getTituloPorCodigo = function(data, tipoBusqueda) {
	BLOCK.showBlock("Buscando títulos...");
	var request = "clave=" + data.codigo + "&senial=" + data.senial;
	var url = tipoBusqueda === AmortizarTituloSinExhibirRecibirEvent.CASTELLANO ? "obtenerTitulosCastellanoSinExhibirRecibirPorCodigo.action" : "obtenerTitulosOriginalSinExhibirRecibirPorCodigo.action";
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
				amortizarTituloSinExhibirRecibirEvent.fillGridTitulos(response.titulosGrilla);
			}
		}
	});
};

AmortizarTituloSinExhibirRecibirService.prototype.getTituloPorDescripcion = function(data, tipoBusqueda) {
	BLOCK.showBlock("Buscando títulos...");
	var request = "descripcion=" + data.descripcion + "&senial=" + data.senial;
	var url = tipoBusqueda === AmortizarTituloSinExhibirRecibirEvent.CASTELLANO ? "obtenerTitulosCastellanoSinExhibirRecibirPorDescripcion.action" : "obtenerTitulosOriginalSinExhibirRecibirPorDescripcion.action";
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
				amortizarTituloSinExhibirRecibirEvent.fillGridTitulos(response.titulosGrilla);
			}
		}
	});
};

AmortizarTituloSinExhibirRecibirService.prototype.getCapitulosDelTitulo = function(data) {
	BLOCK.showBlock("Buscando capítulos...");
	var url = "obtenerTituloSinExhibirRecibirConCapitulos.action";
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
			if (response.tituloSinExhibirRecibirView) {
				amortizarTituloSinExhibirRecibirEvent.fillCabeceraTituloCapitulos(response.tituloSinExhibirRecibirView);
				amortizarTituloSinExhibirRecibirEvent.fillGridCapitulos(response.tituloSinExhibirRecibirView.capitulos);
			}
		}
	});
};

AmortizarTituloSinExhibirRecibirService.prototype.amortizarTitulo = function(data) {
	var popup = popupConfirmacionEvent;
	$.ajax({
		type : 'POST',
		url : 'amortizarTituloSinExhibirRecibir.action',
		data : data,
		success : function(response) {
			if (response.message) {
				MESSAGE.error(response);
				popup.close();
				return;
			}
			MESSAGE.ok("El Título - Contrato se amortizará en el próximo cierre contable.");
			popup.close();
			
			if (amortizarTituloSinExhibirRecibirEvent.ultimaBusqueda.busquedaCodigo) {
				var parametros = {
					codigo : amortizarTituloSinExhibirRecibirEvent.ultimaBusqueda.codigo,
					senial : amortizarTituloSinExhibirRecibirEvent.ultimaBusqueda.senial
				};
				amortizarTituloSinExhibirRecibirEvent.service.getTituloPorCodigo(parametros, amortizarTituloSinExhibirRecibirEvent.tipoTitulo);
			} else {
				var parametros = {
					descripcion : amortizarTituloSinExhibirRecibirEvent.ultimaBusqueda.descripcion,
					senial : amortizarTituloSinExhibirRecibirEvent.ultimaBusqueda.senial
				};
				amortizarTituloSinExhibirRecibirEvent.service.getTituloPorDescripcion(parametros, amortizarTituloSinExhibirRecibirEvent.tipoTitulo);
			}
		}
	});
};
