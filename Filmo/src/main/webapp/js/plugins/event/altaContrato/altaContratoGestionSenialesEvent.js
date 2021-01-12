/***********************************************************************************		
 ********************				Pantalla FP11006/08			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirCargaSenialesHeredadas = function() {
	var self = altaContratoEvent;
	Component.get("html/altaContrato/cargaSenialesHeredadas.html", self.drawCargaSenialesHeredadas);
};

AltaContratoEvent.prototype.drawCargaSenialesHeredadas = function(comp) {
	var self = altaContratoEvent;
	
	$("#popupCargaSenialesHeredadas").dialog({
		width: 600, show: 'blind', hide: 'blind', modal: true, autoOpen: false,
		buttons: {
			"Salir": function() {
				$(this).dialog("close");
			}
		}
	});
	$("#popupCargaSenialesHeredadas").empty().append(comp.replace(/{{id}}/g, self.div.id));
	$("#popupCargaSenialesHeredadas").dialog("open");
	self.drawGridSenialesHeredadas();
};

AltaContratoEvent.prototype.drawGridSenialesHeredadas = function() {
	var self = altaContratoEvent;
	var familiaTitulo = (self.determinarFamiliaTitulo(self.contrato.montoSenialTT.tipoTitulo) ? "CC" : "SC");
	
	self.getSelector("gridCargaSenialesHeredadas").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Asignada', 'Código', 'Descripción', ''],
		colModel:[ 
		    {name:'asignada', 		 index:'asignada', 			align:'center', width:'15', 
				formatter:function(value, options, rData) {
					if (value.trim() != "") {
						return "<div style='display:inline-table;'><span class='ui-icon ui-icon-circle-close conTooltip' title='Asignada'></span></div>";
					} else {
						return "";
					}
				}
		    },
		    {name:'codigo', 		 index:'codigo',			align:'left'},
			{name:'descripcion',	 index:'descripcion',		align:'left'},
			{name:'agregarEliminar', index:'agregarEliminar',	align:'center', width:'15', 
				formatter:function(value, options, rData) { 
					if (rData['asignada'].trim() != "") {
						return "<div onclick='altaContratoEvent.desasignarSenialHeredada" + familiaTitulo + "(\""+ rData['codigo'] + "\");' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-trash conTooltip' title='Eliminar señal'></span></div>";
					} else {
						return "<div onclick='altaContratoEvent.validarAsigacionSenialHeredada" + familiaTitulo + "(\"" + rData['codigo'] + "\");' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-circle-plus conTooltip' title='Agregar señal'></span></div>";
					}
				}
			},
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + self.div.id + '_pagerCargaSenialesHeredadas',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Señales heredadas'
	});
	
	self.buscarDatosGrillaSenialesHeredadas();
};

AltaContratoEvent.prototype.buscarDatosGrillaSenialesHeredadas = function() {
	var self = altaContratoEvent;
	
	var buscarSenialesHeredadasRequest = {
		contrato: self.contrato.numero,
		grupo: (self.determinarFamiliaTitulo(self.contrato.montoSenialTT.tipoTitulo) ? self.contrato.grupoCC.nroGrupo : self.contrato.grupoSC.nroGrupo),
		senial: self.contrato.montoSenialTT.senial
	};
	
	var data = {
		async: false,
		action:"altaContratoBuscarSenialesHeredadas.action", 
		request: Component.serialize(buscarSenialesHeredadasRequest, "buscarSenialesHeredadasRequest"), 
		method: "GET", 
		responseObject: "senialesHeredadas", 
		callback: function(senialesHeredadas) {
			self.getSelector("gridCargaSenialesHeredadas").clearGridData().setGridParam({data: senialesHeredadas}).trigger('reloadGrid');
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.cierrePopupCargaSenialesHeredadas = function() {
	$("#popupCargaSenialesHeredadas").dialog("close");
	$("#popupCargaSenialesHeredadas").dialog("remove");
};

/***********************************************************************************		
 *****************   Pantalla FP11006/08 - Warning asignación SH   *****************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirPopupDatosTitulosSH = function(params) {
	var self = altaContratoEvent;
	Component.get("html/altaContrato/popupDatosTitulosSH.html", function(comp) {
		self.drawPopupDatosTitulosSH(comp, params);
	});
};

AltaContratoEvent.prototype.drawPopupDatosTitulosSH = function(comp, params) {
	var self = altaContratoEvent;
	
	$("#popupDatosTitulosSH").dialog({
		width: 600, show: 'blind', hide: 'blind', modal: true, autoOpen: false,
		buttons: {
			"Salir": function() {
				$(this).dialog("close");
			}
		}
	});
	$("#popupDatosTitulosSH").empty().append(comp.replace(/{{id}}/g, self.div.id));
	$("#popupDatosTitulosSH").dialog("open");
	
	/* Botonera */
	self.getSelector("confirmarDatosTitulosSH").button().click(function() {
		var esTituloCC = self.determinarFamiliaTitulo(self.contrato.montoSenialTT.tipoTitulo);
		self.cierrePopupDatosTitulosSH();
		if (esTituloCC) {
			self.asignarSenialHeredadaCC(params.request);	
		} else {
			self.asignarSenialHeredadaSC(params.request);
		}
	});
	self.getSelector("rechazarDatosTitulosSH").button().click(self.cierrePopupDatosTitulosSH);
	self.drawGridDatosTitulosSH(params.titulos);
};

AltaContratoEvent.prototype.drawGridDatosTitulosSH = function(titulos) {
	var self = altaContratoEvent;
	
	self.getSelector("gridDatosTitulosSH").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Título'],
		colModel:[ 
		    {name:'titulo', 		 index:'titulo', 			align:'center', formatter:function(value, options, rData) { 
		    	return rData['tipoTitulo'] + rData['numeroTitulo'];
			}},
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + self.div.id + '_pagerDatosTitulosSH',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray' 
	});
	
	self.getSelector("gridDatosTitulosSH").clearGridData().setGridParam({data: titulos}).trigger('reloadGrid');
};

AltaContratoEvent.prototype.cierrePopupDatosTitulosSH = function() {
	$("#popupDatosTitulosSH").dialog("close");
	$("#popupDatosTitulosSH").dialog("remove");
};

/***********************************************************************************		
 ********************				Pantalla FP11006			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.validarAsigacionSenialHeredadaCC = function(codigoSenial) {
	var self = altaContratoEvent;
	
	var validarAsignacionSenialHeredadaRequest = {
		contrato: self.contrato.numero,
		grupo: self.contrato.grupoCC.nroGrupo,
		senial: self.contrato.montoSenialTT.senial,
		senialHeredada: codigoSenial
	};
	
//	var data = {
//		async: false,
//		action:"altaContratoValidarAsignacionSenialHeredadaCC.action", 
//		request: Component.serialize(validarAsignacionSenialHeredadaRequest, "validarAsignacionSenialHeredadaRequest"), 
//		method: "GET", 
//		responseObject: "validarAsignacionSenialHeredadaResponse", 
//		callback: function(validarAsignacionSenialHeredadaResponse) {
//			if (validarAsignacionSenialHeredadaResponse && validarAsignacionSenialHeredadaResponse.length > 0) {
//				var params = {
//					titulos: validarAsignacionSenialHeredadaResponse,
//					request: validarAsignacionSenialHeredadaRequest
//				};
//				self.abrirPopupDatosTitulosSH(params);
//			} else {
				self.asignarSenialHeredadaCC(validarAsignacionSenialHeredadaRequest);
//			}
//		}
//	};
//	self.service.doRequest(data);
    
};

AltaContratoEvent.prototype.asignarSenialHeredadaCC = function(request) {
	var self = altaContratoEvent;
	
	var data = {
		async: false,
		action:"altaContratoAsignarSenialHeredadaCC.action", 
		request: Component.serialize(request, "asignarSenialHeredadaRequest"), 
		method: "POST", 
		responseObject: "resultado", 
		callback: function(exito) {
			if (exito) {
				//MESSAGE.ok("Se agregó la señal con éxito");
			} else {
				MESSAGE.error("No se pudo agregar la señal");
			}
			self.buscarDatosGrillaSenialesHeredadas();
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.desasignarSenialHeredadaCC = function(codigoSenial) {
	var self = altaContratoEvent;
	
	var desasignarSenialHeredadaRequest = {
		contrato: self.contrato.numero,
		grupo: self.contrato.grupoCC.nroGrupo,
		senial: self.contrato.montoSenialTT.senial,
		senialHeredada: codigoSenial
	};
	
	var data = {
		async: false,
		action:"altaContratoDesasignarSenialHeredadaCC.action", 
		request: Component.serialize(desasignarSenialHeredadaRequest, "desasignarSenialHeredadaRequest"), 
		method: "POST", 
		responseObject: "resultado", 
		callback: function(exito) {
			if (exito) {
				MESSAGE.ok("Se eliminó la señal con éxito");
			} else {
				MESSAGE.error("No se pudo eliminar la señal");
			}
			self.buscarDatosGrillaSenialesHeredadas();
		}
	};
	self.service.doRequest(data);
};

/***********************************************************************************		
 ********************				Pantalla FP11008			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.validarAsigacionSenialHeredadaSC = function(codigoSenial) {
	var self = altaContratoEvent;
	
	var validarAsignacionSenialHeredadaRequest = {
		contrato: self.contrato.numero,
		grupo: self.contrato.grupoSC.nroGrupo,
		senial: self.contrato.montoSenialTT.senial,
		senialHeredada: codigoSenial
	};
	
//	var data = {
//		async: false,
//		action:"altaContratoValidarAsignacionSenialHeredadaSC.action", 
//		request: Component.serialize(validarAsignacionSenialHeredadaRequest, "validarAsignacionSenialHeredadaRequest"), 
//		method: "GET", 
//		responseObject: "validarAsignacionSenialHeredadaResponse", 
//		callback: function(validarAsignacionSenialHeredadaResponse) {
//			if (validarAsignacionSenialHeredadaResponse && validarAsignacionSenialHeredadaResponse.length > 0) {
//				var params = {
//					titulos: validarAsignacionSenialHeredadaResponse,
//					request: validarAsignacionSenialHeredadaRequest
//				};
//				self.abrirPopupDatosTitulosSH(params);
//			} else {
				self.asignarSenialHeredadaSC(validarAsignacionSenialHeredadaRequest);
//			}
//		}
//	};
//	self.service.doRequest(data);
};

AltaContratoEvent.prototype.asignarSenialHeredadaSC = function(request) {
	var self = altaContratoEvent;
	
	var data = {
		async: false,
		action:"altaContratoAsignarSenialHeredadaSC.action", 
		request: Component.serialize(request, "asignarSenialHeredadaRequest"), 
		method: "POST", 
		responseObject: "resultado", 
		callback: function(exito) {
			if (exito) {
				//MESSAGE.ok("Se agregó la señal con éxito");
			} else {
				MESSAGE.error("No se pudo agregar la señal");
			}
			self.buscarDatosGrillaSenialesHeredadas();
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.desasignarSenialHeredadaSC = function(codigoSenial) {
	var self = altaContratoEvent;
	
	var desasignarSenialHeredadaRequest = {
		contrato: self.contrato.numero,
		grupo: self.contrato.grupoSC.nroGrupo,
		senial: self.contrato.montoSenialTT.senial,
		senialHeredada: codigoSenial
	};
	
	var data = {
		async: false,
		action:"altaContratoDesasignarSenialHeredadaSC.action", 
		request: Component.serialize(desasignarSenialHeredadaRequest, "desasignarSenialHeredadaRequest"), 
		method: "POST", 
		responseObject: "resultado", 
		callback: function(exito) {
			if (exito) {
				MESSAGE.ok("Se eliminó la señal con éxito");
			} else {
				MESSAGE.error("No se pudo eliminar la señal");
			}
			self.buscarDatosGrillaSenialesHeredadas();
		}
	};
	self.service.doRequest(data);
};
