/***********************************************************************************		
 ********************				Pantalla FP11020			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirEliminacionTituloContrato = function() {
	var self = altaContratoEvent;
	Component.get("html/altaContrato/eliminacionTituloContrato.html", self.drawEliminacionTituloContrato);
};

AltaContratoEvent.prototype.drawEliminacionTituloContrato = function(comp) {
	var self = altaContratoEvent;
	comp = comp.replace(/{{id}}/g, self.div.id);
	
	if (self.getSelector("formEliminacionTituloContrato").length) {
		self.getSelector("formEliminacionTituloContrato").replaceWith(comp);
	} else {
		self.getDiv().append(comp);
	}
	/* Accordion */
	Accordion.getInstance(self.getSelector("accordionEliminacionTituloContrato"));
	/* Botonera */
	self.getSelector("volverEliminacionTituloContrato").button().click(self.volverEliminacionTituloContrato);
	/* Datos grupo */
	self.getSelector("senialEliminacionTituloContrato").html(self.contrato.montoSenialTT.senial);
	self.getSelector("distribuidorEliminacionTituloContrato").html(self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("distribuidorEliminacionTituloContrato").attr("title", self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("montoEliminacionTituloContrato").html(self.contrato.montoSenialTT.monto);
	self.getSelector("nroGrupoEliminacionTituloContrato").html(self.contrato.grupoSC.nroGrupo);
	self.getSelector("tipoTituloEliminacionTituloContrato").html(self.contrato.montoSenialTT.tipoTitulo);
	self.getSelector("nombreGrupoEliminacionTituloContrato").html(self.contrato.grupoSC.nombreGrupo);
	self.drawGridEliminacionTituloContrato();
};

AltaContratoEvent.prototype.drawGridEliminacionTituloContrato = function() {
	var self = altaContratoEvent;
	
	self.getSelector("gridEliminacionTituloContrato").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Clave', 'Título', 'Prog.', 'Conf. Exhib.', 'A cons.', '', ''],
		colModel:[ 
		    {name:'clave',				index:'clave',				align:'center'},
		    {name:'tituloCastellano',	index:'tituloCastellano',	align:'left', formatter:function(value, options, rData) {
		    	return Component.trimWhitespace(rData['tituloCastellano']);
    		}},
		    {name:'prog',				index:'prog',				align:'center'},
		    {name:'confExh',			index:'confExh',			align:'center'},
		    {name:'aCons',				index:'aCons',				align:'center'},
		    {name:'eliminar',			index:'eliminar',			align:'center', formatter:function(value, options, rData) {
		    	return "<div onclick='altaContratoEvent.validarEliminacionTituloContrato(\"" + rData['clave'] + "\", \"" + rData['prog'] + "\")' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-trash conTooltip' title='Eliminar'></span></div>";
		    }},
		    {name:'rerun',				index:'rerun',	hidden:true}
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + self.div.id + '_pagerEliminacionTituloContrato',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Títulos'
	});
	
	/* Grilla con tamaño automático */
	Component.resizableGrid(self.getSelector("gridEliminacionTituloContrato"));
	/* Buscar títulos */
	self.buscarTitulosEliminacionContrato();
};

AltaContratoEvent.prototype.buscarTitulosEliminacionContrato = function() {
	var self = altaContratoEvent;
	
	var buscarTitulosEliminarContratoRequest = {
		contrato: self.contrato.numero,
		grupo: self.contrato.grupoSC.nroGrupo,
		senial: self.contrato.montoSenialTT.senial
	};
	
	var data = {
		async: false,
		action:"altaContratoBuscarTitulosEliminarContrato.action", 
		request: Component.serialize(buscarTitulosEliminarContratoRequest, "buscarTitulosEliminarContratoRequest"),  
		method: "GET", 
		responseObject: "titulosEliminarContrato", 
		callback: function(titulosEliminarContrato) {
			self.getSelector("gridEliminacionTituloContrato").clearGridData().setGridParam({data: titulosEliminarContrato}).trigger('reloadGrid');
		}
	};
	self.service.doRequest(data);
	
};

AltaContratoEvent.prototype.validarEliminacionTituloContrato = function(clave, prog) {
	var self = altaContratoEvent;
	
	var validarEliminacionTituloContratoRequest = {
		contrato: self.contrato.numero,
		grupo: self.contrato.grupoSC.nroGrupo,
		senial: self.contrato.montoSenialTT.senial,
		clave: clave
	};
	
	var data = {
		async: false,
		action:"altaContratoValidarEliminacionTituloContrato.action", 
		request: Component.serialize(validarEliminacionTituloContratoRequest, "validarEliminacionTituloContratoRequest"),  
		method: "GET", 
		responseObject: "validarEliminacionTituloContratoResponse", 
		callback: function(validarEliminacionTituloContratoResponse) {
			if (validarEliminacionTituloContratoResponse.hayErrores) {
				/* Muestro los mensajes de error que retorna el desenlace */				
				var params = {
					showBotonera: false,
					datosDesenlace: validarEliminacionTituloContratoResponse.erroresDesenlace,
					mensaje: "Hubo errores al intentar desenlazar los contratos."
				};
				self.abrirPopupDatosDesenlace(params);
			} else if (validarEliminacionTituloContratoResponse.erroresDesenlace) {
				/* Muestro los mensajes de error que retorna el desenlace y espera confirmación */
				var params = {
					callback: function() {
						self.cierrePopupDatosDesenlace();
						var argumentos = { clave: clave, prog: prog };
						self.confirmarEliminacionTituloContrato(argumentos);
					},
					showBotonera: true,
					datosDesenlace: validarEliminacionTituloContratoResponse.erroresDesenlace,
					mensaje: "Hubo errores al intentar desenlazar los contratos. ¿Desea continuar?"
				};
				self.abrirPopupDatosDesenlace(params);
			} else {
				var argumentos = { clave: clave, prog: prog };
				self.confirmarEliminacionTituloContrato(argumentos);
			}
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.confirmarEliminacionTituloContrato = function(argumentos) {
	var self = altaContratoEvent;
	
	/* Obtener los datos de la grilla */
	var confirmarEliminacionTituloContratoRequest = {
		contrato: self.contrato.numero,
		grupo: self.contrato.grupoSC.nroGrupo,
		senial: self.contrato.montoSenialTT.senial,
		clave: argumentos.clave,
		s20Prog: argumentos.prog
	};
	
	var data = {
		async: false,
		action:"altaContratoConfirmarEliminacionTituloContrato.action", 
		request: Component.serialize(confirmarEliminacionTituloContratoRequest, "confirmarEliminacionTituloContratoRequest"),  
		method: "POST", 
		responseObject: "confirmarEliminacionTituloContratoResponse", 
		callback: function(confirmarEliminacionTituloContratoResponse) {
			if (confirmarEliminacionTituloContratoResponse.hayErrores) {
				/* Muestro los mensajes de error que retorna el desenlace */				
				var params = {
					showBotonera: false,
					datosDesenlace: confirmarEliminacionTituloContratoResponse.erroresDesenlace,
					mensaje: "Hubo errores al intentar desenlazar los contratos."
				};
				self.abrirPopupDatosDesenlace(params);
			} else {
				if (confirmarEliminacionTituloContratoResponse.mensaje == "") {
					/* Limpio los datos viejos */ 
					self.getSelector("formEliminacionTituloContrato").hide();
					self.contrato.grupoCC = new GrupoCCPOJO();
					self.contrato.grupoSC = new GrupoSCPOJO();
					self.contrato.montoSenialTT.tipoTitulo = null;
					self.contrato.montoSenialTT.titulosIngresados = null;
					/* Redirijo a FP11009 */
					self.abrirVisualizarGruposContrato();
				} else {
					MESSAGE.alert(confirmarEliminacionTituloContratoResponse.mensaje);
					self.buscarTitulosEliminacionContrato();	
				}
			}
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.volverEliminacionTituloContrato = function() {
	var self = altaContratoEvent;
	/* Limpio los datos viejos */ 
	self.getSelector("formEliminacionTituloContrato").hide();
	self.contrato.grupoCC = new GrupoCCPOJO();
	self.contrato.grupoSC = new GrupoSCPOJO();
	self.contrato.montoSenialTT.tipoTitulo = null;
	self.contrato.montoSenialTT.titulosIngresados = null;
	/* Redirijo a FP11009 */
	self.abrirVisualizarGruposContrato();	
};