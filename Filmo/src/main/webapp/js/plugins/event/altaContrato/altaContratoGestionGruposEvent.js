/***********************************************************************************		
 ********************				Pantalla FP11005/07			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.obtenerProximoNumeroGrupo = function() {
	var self = altaContratoEvent;
	
	var buscarNumeroGrupoRequest = {
		contrato: self.contrato.numero,
		senial: self.contrato.montoSenialTT.senial 
	};
	
	var data = {
		async: false,
		action:"altaContratoBuscarNumeroGrupo.action", 
		request: Component.serialize(buscarNumeroGrupoRequest, "buscarNumeroGrupoRequest"), 
		method: "GET", 
		responseObject: "numeroGrupo", 
		callback: function(numeroGrupo) {
			var esTituloCC = self.determinarFamiliaTitulo(self.contrato.montoSenialTT.tipoTitulo);

			if (esTituloCC) {
				self.getSelector("nroGrupoCargaGrupoCC").html(numeroGrupo);
				self.contrato.grupoCC.nroGrupo = numeroGrupo;	
			} else {
				self.getSelector("nroGrupoCargaGrupoSC").html(numeroGrupo);
				self.contrato.grupoSC.nroGrupo = numeroGrupo;			
			}
		}
	};
	self.service.doRequest(data);
};

/***********************************************************************************		
 ********************       Pantalla FP11005/07 - Warnings      ********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirPopupWarningsModificacionGrupo = function(params) {
	var self = altaContratoEvent;
	Component.get("html/altaContrato/popupWarningsModificacionGrupo.html", function(comp) {
		self.drawPopupWarningsModificacionGrupo(comp, params);
	});
};

AltaContratoEvent.prototype.drawPopupWarningsModificacionGrupo = function(comp, params) {
	var self = altaContratoEvent;
	
	$("#popupWarningsModificacionGrupo").dialog({
		width: 600, show: 'blind', hide: 'blind', modal: true, autoOpen: false,
		buttons: {
			"Salir": function() {
				$(this).dialog("close");
			}
		}
	});
	$("#popupWarningsModificacionGrupo").empty().append(comp.replace(/{{id}}/g, self.div.id));
	$("#popupWarningsModificacionGrupo").dialog("open");
	
	/* Seteo el mensaje a mostrar en el popup */
	self.getSelector("mensajeWarningsModificacionGrupo").html(params.mensaje);
	/* Cargo la grilla con los datos del desenlace */
	self.drawGridWarningsModificacionGrupo(params.datosDesenlace);
};

AltaContratoEvent.prototype.drawGridWarningsModificacionGrupo = function(datosDesenlace) {
	var self = altaContratoEvent;
	
	self.getSelector("gridWarningsModificacionGrupo").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Título', 'Contrato', 'Grupo', 'Señal'],
		colModel:[ 
		    {name:'titulo', 		index:'titulo', align:'center', formatter:function(value, options, rData) { 
		    	return rData['tipoTitulo'] + rData['nroTitulo'];
		    }},
		    {name:'contrato', 		index:'contrato', 		align:'center'},
		    {name:'grupo', 			index:'grupo', 			align:'center'},
		    {name:'senial', 		index:'senial', 		align:'center'}
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + this.div.id + '_pagerWarningsModificacionGrupo',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Datos del desenlace'
	});
	
	self.getSelector("gridWarningsModificacionGrupo").clearGridData().setGridParam({data: datosDesenlace}).trigger('reloadGrid');
};

AltaContratoEvent.prototype.cierrePopupWarningsModificacionGrupo = function() {
	$("#popupWarningsModificacionGrupo").dialog("close");
	$("#popupWarningsModificacionGrupo").dialog("remove");
};

/***********************************************************************************		
 ********************				Pantalla FP11009			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirVisualizarGruposContrato = function() {
	/* Bloquear pantalla */
	BLOCK.showBlock("Cargando grupos del contrato");
	
	setTimeout(function() {
		Component.get("html/altaContrato/visualizarGruposContrato.html", altaContratoEvent.drawVisualizarGruposContrato);
	}, 750);
};

AltaContratoEvent.prototype.drawVisualizarGruposContrato = function(comp) {
	var self = altaContratoEvent;

	self.getSelector("formCargaContrato").hide();
	comp = comp.replace(/{{id}}/g, self.div.id);
	
	if (self.getSelector("formVisualizarGruposContrato").length) {
		self.getSelector("formVisualizarGruposContrato").replaceWith(comp);
	} else {
		self.getDiv().append(comp);
	}
	/* Accordion */
	Accordion.getInstance(self.getSelector("accordionVisualizarGruposContrato"));
	
	/* Carga de datos de la cabecera */
	self.getSelector("senialVisualizarGruposContrato").html(self.contrato.montoSenialTT.senial);
	self.getSelector("distribuidorVisualizarGruposContrato").html(self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("distribuidorVisualizarGruposContrato").attr("title", self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("montoSenialVisualizarGruposContrato").html(self.contrato.montoSenialTT.monto);
	self.getSelector("montoCargadoVisualizarGruposContrato").html(self.contrato.cabecera.montoTotal);
	
	/* Botonera */
	self.getSelector("volverVisualizarGruposContrato").button().click(self.volverVisualizarGruposContrato);
	self.getSelector("agregarGrupoVisualizarGruposContrato").button().click(function() {
		var data = { importe: self.contrato.montoSenialTT.monto, senial: self.contrato.montoSenialTT.senial };
		self.abrirSeleccionTipoTituloGrupo(data);
	});
	
	/* Grilla de grupos para el contrato */
	self.drawGridGruposContrato();
};

AltaContratoEvent.prototype.drawGridGruposContrato = function() {
	var self = altaContratoEvent;
	
	self.getSelector("gridGruposContrato").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Nro. grupo', 'Tipo título', 'Nombre de grupo', 'Importe', 'Estreno / Repetición', 'Senial heredada', '', '', '', ''],
		colModel:[ 
		    {name:'nroGrupo',			index:'nroGrupo', 			align:'center', width: 70},
		    {name:'tipoTitulo',			index:'tipoTitulo', 		align:'center', width: 70},
		    {name:'nombreGrupo',		index:'nombreGrupo',		align:'left', formatter:function(value, options, rData) { 
		    	return Component.trimWhitespace(rData['nombreGrupo']);
		    }},
		    {name:'importeGrupo',		index:'importeGrupo', 		align:'center', width: 70, formatter: 'currency'},
		    {name:'estrenoRepeticion',	index:'estrenoRepeticion', 	align:'center', width: 80},
		    {name:'senialHeredada',		index:'senialHeredada', 	align:'center'},
		    {name:'modificarGrupo',		index:'modificarGrupo',		align:'center', width: 30, formatter:function(value, options, rData) { 
		    	return "<div onclick='altaContratoEvent.abrirModificacionDatosGrupo(" + rData['nroGrupo'] + ", \"" + rData['tipoTitulo'] + "\", \"" + rData['nombreGrupo'] + "\", " + rData['importeGrupo'] + ");' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-pencil conTooltip' title='Modificar grupo'></span></div>";
			}},
		    {name:'eliminarGrupo',		index:'eliminarGrupo',		align:'center', width: 30, formatter:function(value, options, rData) {
		    	return "<div onclick='altaContratoEvent.validarDesenlaceContratoGrupo(" + rData['nroGrupo'] + ", \"" + rData['tipoTitulo'] + "\");' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-trash conTooltip' title='Eliminar grupo'></span></div>";
			}},
			{name:'visualizarGrupo',	index:'visualizarGrupo',	align:'center', width: 30, formatter:function(value, options, rData) {
				return "<div onclick='altaContratoEvent.abrirVisualizarTitulosGrupoReadOnly(" + rData['nroGrupo'] + ", \"" + rData['tipoTitulo'] + "\", \"" + rData['nombreGrupo'] + "\", \"" + rData['estrenoRepeticion'] + "\");' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-search conTooltip' title='Visualizar títulos del grupo'></span></div>";
			}},
		    {name:'tipoDeCosteo',		index:'tipoDeCosteo',		align:'center', width: 30, formatter:function(value, options, rData) { 
		    	return "<div onclick='altaContratoEvent.abrirTipoDeCosteo(" + rData['nroGrupo'] + ", \"" + rData['tipoTitulo'] + "\", \"" + rData['nombreGrupo'] + "\", " + rData['importeGrupo'] + ");' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-gear conTooltip' title='Tipo De Costeo'></span></div>";
			}}
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + self.div.id + '_pagerGruposContrato',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Grupos'
	});
	
	/* Grilla con tamaño automático */
	Component.resizableGrid(self.getSelector("gridGruposContrato"));
	
	self.buscarGruposContrato();
};

AltaContratoEvent.prototype.buscarGruposContrato = function() {
	var self = altaContratoEvent;
	
	var buscarGruposContratoRequest = {
		contrato: self.contrato.numero,
		senial: self.contrato.montoSenialTT.senial
	};
	
	var data = {
		async: false,
		action:"altaContratoBuscarGruposContrato.action", 
		request: Component.serialize(buscarGruposContratoRequest, "buscarGruposContratoRequest"), 
		method: "GET", 
		responseObject: "gruposContrato", 
		callback: function(gruposContrato) {
			self.getSelector("gridGruposContrato").clearGridData().setGridParam({data: gruposContrato}).trigger('reloadGrid');
			/* Desbloquear pantalla */
			BLOCK.hideBlock();
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.volverVisualizarGruposContrato = function() {
	var self = altaContratoEvent;
	self.getSelector("formVisualizarGruposContrato").hide();
	self.getSelector("formCargaContrato").show();
};

/***********************************************************************************		
 ********************		Pantalla FP11009 a FP11005/07		********************
 ***********************************************************************************/
AltaContratoEvent.prototype.recuperarDatosGrupo = function(nroGrupo) {
	var self = altaContratoEvent;
	
	var recuperarDatosGrupoRequest = {
		contrato: self.contrato.numero,
		senial: self.contrato.montoSenialTT.senial,
		grupo : nroGrupo
	};
	
	var grupo = null;
	
	var data = {
		async: false,
		action:"altaContratoRecuperarDatosGrupo.action", 
		request: Component.serialize(recuperarDatosGrupoRequest, "recuperarDatosGrupoRequest"), 
		method: "GET", 
		responseObject: "recuperarDatosGrupoResponse", 
		callback: function(recuperarDatosGrupoResponse) {
			grupo = recuperarDatosGrupoResponse;
		}
	};
	self.service.doRequest(data);
	
	return grupo;
};

AltaContratoEvent.prototype.validarDesenlaceContratoGrupo = function(grupo, tipoTitulo) {
	var self = altaContratoEvent;
	
	var desenlaceContratoGrupoRequest = {
		contrato: self.contrato.numero,
		senial: self.contrato.montoSenialTT.senial,
		grupo : grupo
	};
	
	var data = {
		async: false,
		action:"altaContratoDesenlaceContratoGrupo.action", 
		request: Component.serialize(desenlaceContratoGrupoRequest, "desenlaceContratoGrupoRequest"), 
		method: "POST", 
		responseObject: "desenlaceContratoGrupoResponse", 
		callback: function(desenlaceContratoGrupoResponse) {
			if (desenlaceContratoGrupoResponse.errores) {
				if (desenlaceContratoGrupoResponse.error == "S") {
					/* Muestro los mensajes de error que retorna el desenlace */
					var params = {
						showBotonera: false,
						datosDesenlace: desenlaceContratoGrupoResponse.errores,
						mensaje: "Hubo errores al intentar desenlazar el grupo del contrato"
					};
					self.abrirPopupDatosDesenlace(params);
				} else {
					var params = {
						callback: function() {
							/* Muestro los mensajes de error que retorna el desenlace y espera confirmación */
							self.contrato.marcaDesenlaceGrupo = true;
							
							var esTituloCC = self.determinarFamiliaTitulo(tipoTitulo);
							
							if (esTituloCC) {
								self.contrato.grupoCC.nroGrupo = grupo;
								self.abrirLecturaDatosGrupoCC(tipoTitulo);
							} else {
								self.contrato.grupoSC.nroGrupo = grupo;
								self.abrirLecturaDatosGrupoSC(tipoTitulo);
							}
						},
						showBotonera: true,
						datosDesenlace: desenlaceContratoGrupoResponse.errores,
						mensaje: "Hubo errores al intentar desenlazar los titulos del grupo. ¿Desea continuar?"
					};
					self.abrirPopupDatosDesenlace(params);
				}
			} else {
				self.contrato.marcaDesenlaceGrupo = false;
				
				var esTituloCC = self.determinarFamiliaTitulo(tipoTitulo);
				
				if (esTituloCC) {
					self.contrato.grupoCC.nroGrupo = grupo;
					self.abrirLecturaDatosGrupoCC(tipoTitulo);
				} else {
					self.contrato.grupoSC.nroGrupo = grupo;
					self.abrirLecturaDatosGrupoSC(tipoTitulo);
				}
			}
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.eliminarGrupoContrato = function(tipoTitulo) {
	var self = altaContratoEvent;
	
	var esTituloCC = self.determinarFamiliaTitulo(tipoTitulo);
	var sufijoTipoTitulo = (esTituloCC ? "CC" : "SC");
	
	var eliminarGrupoContratoRequest = {
		contrato: self.contrato.numero,
		grupo: self.getSelector("nroGrupoLecturaDatosGrupo" + sufijoTipoTitulo).html(),
		senial: self.getSelector("senialLecturaDatosGrupo" + sufijoTipoTitulo).html(),
		tipoTitulo: ' ',
		nroTitulo: 0,
		desenlace: self.contrato.marcaDesenlaceGrupo
	};
	
	var data = {
		async: false,
		action:"altaContratoEliminarGrupoContrato.action", 
		request: Component.serialize(eliminarGrupoContratoRequest, "eliminarGrupoContratoRequest"), 
		method: "POST", 
		responseObject: "eliminarGrupoContratoResponse", 
		callback: function(eliminarGrupoContratoResponse) {
			if (eliminarGrupoContratoResponse.resultado) {
				MESSAGE.ok("Se elimino el grupo con éxito");
				if (esTituloCC) {
					self.volverLecturaDatosGrupoCC();
				} else {
					self.volverLecturaDatosGrupoSC();
				}
				self.buscarGruposContrato();
			} else {
				MESSAGE.error("No se pudo eliminar el grupo con éxito");
			}
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.situarSenialesHeredadasReadOnly = function() {
	var self = altaContratoEvent;
	
	var buscarSenialesHeredadasRequest = {
		senial: self.contrato.montoSenialTT.senial
	};
	
	var esTituloCC = self.determinarFamiliaTitulo(self.contrato.montoSenialTT.tipoTitulo);
	var sufijoTipoTitulo = (esTituloCC ? "CC" : "SC");
	
	var data = {
		async: true,
		action: "altaContratoBuscarSenialHeredada" + sufijoTipoTitulo + ".action", 
		request: Component.serialize(buscarSenialesHeredadasRequest, "buscarSenialesHeredadasRequest"), 
		method: "GET", 
		responseObject: "senialHeredada", 
		callback: function(senialHeredada) {
			if (senialHeredada) {
				self.getSelector("senialesHeredadasLecturaDatosGrupo" + sufijoTipoTitulo).html(senialHeredada);
			}
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.abrirModificacionDatosGrupo = function(nroGrupo, tipoTitulo, nombreGrupo) {
	/* Bloquear pantalla */
	BLOCK.showBlock("Cargando datos del grupo");
	
	var self = altaContratoEvent;
	
	self.contrato.montoSenialTT.tipoTitulo = tipoTitulo;
	
	var esTituloCC = self.determinarFamiliaTitulo(tipoTitulo);
	if (esTituloCC) {
		self.contrato.grupoCC = new GrupoCCPOJO();
		self.contrato.grupoCC.nroGrupo = nroGrupo;
		self.contrato.grupoCC.tipoTitulo = tipoTitulo;
		self.contrato.grupoCC.nombreGrupo = nombreGrupo;
		self.abrirModificacionDatosGrupoCC();
	} else {
		self.contrato.grupoSC = new GrupoSCPOJO();
		self.contrato.grupoSC.nroGrupo = nroGrupo;
		self.contrato.grupoSC.tipoTitulo = tipoTitulo;
		self.contrato.grupoSC.nombreGrupo = nombreGrupo;
		self.abrirModificacionDatosGrupoSC();
	}
};
AltaContratoEvent.prototype.abrirTipoDeCosteo = function(nroGrupo, tipoTitulo, nombreGrupo) {
	/* Bloquear pantalla */
	console.log("Abrir tipo de costeo");
//	BLOCK.showBlock("Cargando datos del grupo");
	var self = altaContratoEvent;
	console.log("Inicializando carga de costeos parametros: divContainer: 'popupTipoDeCosteo', prefijo : 'TipoDeCosteoEvent',contrato numero: "+ self.contrato.numero +", senial: '"+ self.contrato.montoSenialTT.senial+"' nroGrupo : "+ nroGrupo);
	TipoDeCosteoEvent.init("popupTipoDeCosteo","TipoDeCosteoEvent",self.contrato.numero, self.contrato.montoSenialTT.senial, nroGrupo);
//	var esTituloCC = self.determinarFamiliaTitulo(tipoTitulo);
//	if (esTituloCC) {
//		self.contrato.grupoCC = new GrupoCCPOJO();
//		self.contrato.grupoCC.nroGrupo = nroGrupo;
//		self.contrato.grupoCC.tipoTitulo = tipoTitulo;
//		self.contrato.grupoCC.nombreGrupo = nombreGrupo;
//		self.abrirModificacionDatosGrupoCC();
//	} else {
//		self.contrato.grupoSC = new GrupoSCPOJO();
//		self.contrato.grupoSC.nroGrupo = nroGrupo;
//		self.contrato.grupoSC.tipoTitulo = tipoTitulo;
//		self.contrato.grupoSC.nombreGrupo = nombreGrupo;
//		self.abrirModificacionDatosGrupoSC();
//	}
};