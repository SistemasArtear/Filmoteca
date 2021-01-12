/***********************************************************************************		
 ********************				Pantalla FP9001				********************
 ***********************************************************************************/
AltaContratoEvent.prototype.drawPopupDistribuidor = function() {
	var self = altaContratoEvent;
	situarPopupEvent.reset();
	situarPopupEvent.nombreSituar = "Distribuidores";
	situarPopupEvent.create("altaContratoBuscarDistribuidores.action", function(row) {
		self.getSelector("codigoDistribuidor").val(row.codigo);
		self.getSelector("descripcionDistribuidor").val(row.razonSocial);
	}, {descripcion: ""}, "distribuidores");
};

AltaContratoEvent.prototype.limpiarDatosDistribuidor = function() {
	var self = altaContratoEvent;
	self.getSelector("codigoDistribuidor").val(null);
	self.getSelector("descripcionDistribuidor").val(null);
};

/***********************************************************************************		
 ********************				Pantalla FP9017 			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.buscarTiposImporte = function(selector) {
	var self = altaContratoEvent;

	var data = {
		async: false,
		action:"altaContratoBuscarTiposImporte.action", 
		request: {}, 
		method: "GET", 
		responseObject: "tiposImporte", 
		callback: function(tiposImporte) {
			Component.populateSelect(self.getSelector(selector), tiposImporte, "codigo", "descripcion", true);
			//self.getSelector(selector)[0].removeChild(self.getSelector(selector)[0].options[0]);
		}
	};
	self.service.doRequest(data);
	
};

/***********************************************************************************		
 ********************				Pantalla FP9018 			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.buscarTiposDerechoTerritorial = function(selector) {
	var self = altaContratoEvent;
	
	var data = {
		async: false,
		action:"altaContratoBuscarTiposDerechoTerritorial.action", 
		request: {}, 
		method: "GET", 
		responseObject: "tiposDerechoTerritorial", 
		callback: function(tiposDerechoTerritorial) {
			Component.populateSelect(self.getSelector(selector), tiposDerechoTerritorial, "codigo", "descripcion", true);
		}
	};
	self.service.doRequest(data);
};

/***********************************************************************************		
 ********************				Pantalla FP9019 			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.buscarTiposDerecho = function(selector) {
	var self = altaContratoEvent;
	
	var data = {
		async: false,
		action:"altaContratoBuscarTiposDerecho.action", 
		request: {}, 
		method: "GET", 
		responseObject: "tiposDerecho", 
		callback: function(tiposDerecho) {
			Component.populateSelect(self.getSelector(selector), tiposDerecho, "codigo", "descripcion", true);
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.turnOnReadOnly = function(array) {
	for (var i = 0; i < array.length; i++) {
		array[i].attr('disabled', 'disabled');
		array[i].addClass('readOnly');
	}
};

AltaContratoEvent.prototype.turnOffReadOnly = function(array) {
	for (var i = 0; i < array.length; i++) {
		array[i].attr('disabled', false);
		array[i].removeClass('readOnly');
	}
};

/***********************************************************************************		
 ********************				Pantalla FP11062			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirWarningTipoMoneda = function() {
	var self = altaContratoEvent;

	var popup = popupConfirmacionEvent;
	popup.cancelar = function() {
		popup.close();
		popup.remove();
	};
	popup.confirmar = function() {
		popup.close();
		popup.remove();
		self.guardarDatosCabeceraContrato();
	};
	popup.create(self.div.id, "Va a dar de alta un contrato sin importe. ¿Desea continuar?");
};

/***********************************************************************************		
 ********************				Pantalla FP110XX			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirWarningActualizarCabeceraEliminada = function(mensaje) {
	var self = altaContratoEvent;

	var popup = popupConfirmacionEvent;
	popup.cancelar = function() {
		popup.close();
		popup.remove();
		self.buscarSenialesImportes();
	};
	popup.confirmar = function() {
		popup.close();
		popup.remove();
		self.actualizarCabeceraEliminada();
	};
	popup.create(self.div.id, mensaje);
};

/***********************************************************************************		
 ********************				Pantalla FP11070			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirWarningVolverDatosCargaGrupoCC = function() {
	var self = altaContratoEvent;

	var popup = popupConfirmacionEvent;
	popup.cancelar = function() {
		popup.close();
		popup.remove();
	};
	popup.confirmar = function() {
		popup.close();
		popup.remove();
		self.volverDatosCargaGrupoCC();
	};
	popup.create(self.div.id, "Se borrarán los grupos no confirmados. ¿Desea continuar?");
};

/***********************************************************************************		
 ********************				Pantalla FP11099			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirWarningRetornoCargaContrato = function() {
	var self = altaContratoEvent;

	var popup = popupConfirmacionEvent;
	popup.cancelar = function() {
		popup.close();
		popup.remove();
	};
	popup.confirmar = function() {
		popup.close();
		popup.remove();
		self.confirmarSenialesContrato();
	};
	popup.create(self.div.id, "Se borrarán todos los datos ingresados para este contrato. ¿Cancela el ingreso del contrato?");
};

AltaContratoEvent.prototype.determinarFamiliaTitulo = function(tipoTitulo) {
	if (tipoTitulo === "SE" || tipoTitulo === "MS" || tipoTitulo === "SU" || tipoTitulo === "CF") {
		return true; 
	} else {
		return false;
	}
};