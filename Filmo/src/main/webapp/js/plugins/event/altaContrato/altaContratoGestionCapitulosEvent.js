/***********************************************************************************		
 ********************				Pantalla FP11018			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirEliminacionCapitulosContrato = function() {
	var self = altaContratoEvent;
	Component.get("html/altaContrato/eliminacionCapitulosContrato.html", self.drawEliminacionCapitulosContrato);
};

AltaContratoEvent.prototype.drawEliminacionCapitulosContrato = function(comp) {
	var self = altaContratoEvent;
	comp = comp.replace(/{{id}}/g, self.div.id);
	
	self.getSelector("formCargaGrupoCC").hide();

	if (self.getSelector("formEliminacionCapitulosContrato").length) {
		self.getSelector("formEliminacionCapitulosContrato").replaceWith(comp);
	} else {
		self.getDiv().append(comp);
	}
	/* Accordion */
	Accordion.getInstance(self.getSelector("accordionEliminacionCapitulosContrato"));
	/* Botonera */
	self.getSelector("volverEliminacionCapitulosContrato").button().click(self.volverEliminacionCapitulosContrato);
	/* Datos grupo */
	self.getSelector("senialEliminacionCapitulosContrato").html(self.contrato.montoSenialTT.senial);
	self.getSelector("distribuidorEliminacionCapitulosContrato").html(self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("distribuidorEliminacionCapitulosContrato").attr("title", self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("montoEliminacionCapitulosContrato").html(self.contrato.montoSenialTT.monto);
	self.getSelector("nroGrupoEliminacionCapitulosContrato").html(self.contrato.grupoCC.nroGrupo);
	self.getSelector("tipoTituloEliminacionCapitulosContrato").html(self.contrato.montoSenialTT.tipoTitulo);
	self.getSelector("nombreGrupoEliminacionCapitulosContrato").html(self.contrato.grupoCC.nombreGrupo);
	self.getSelector("nuevaFormulaEliminacionCapitulosContrato").html(self.contrato.grupoCC.cantidadOriginales + " / " + self.contrato.grupoCC.cantidadRepeticiones);
	self.drawGridEliminacionCapitulosContrato();
};

AltaContratoEvent.prototype.drawGridEliminacionCapitulosContrato = function() {
	var self = altaContratoEvent;
	
	self.getSelector("gridEliminacionCapitulosContrato").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Nro. de capítulo', 'Parte', 'Título - Capítulo', 'Cont. Rec.', 'Prog.', 'Conf. Exh.', '', ''],
		colModel:[ 
		    {name:'numeroCapitulo',	index:'numeroCapitulo',	align:'center'},
		    {name:'parte',			index:'parte',			align:'center', formatter:function(value, options, rData) {
		    	return value == 0 ? "" : value;
    		}},
		    {name:'tituloCapitulo',	index:'tituloCapitulo',	align:'center', formatter:function(value, options, rData) {
		    	return Component.trimWhitespace(rData['tituloCapitulo']);
    		}},
		    {name:'contRec',		index:'contRec',		align:'center'},
		    {name:'prog',			index:'prog',			align:'center'},
		    {name:'confExh',		index:'confExh',		align:'center'},
		    {name:'eliminar',		index:'eliminar',		align:'center', width:30, formatter:function(value, options, rData) {
		    	return "<div onclick='altaContratoEvent.primerValidacionRerunBajaCapitulos(\"" + rData['clave'] + "\", " + rData['numeroCapitulo'] + ", " + rData['parte'] + ")' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-trash conTooltip' title='Eliminar capítulo'></span></div>";
    		}},
    		{name:'clave',			index:'clave',			align:'center', hidden: true}
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + self.div.id + '_pagerEliminacionCapitulosContrato',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Capítulos'
	});
	
	/* Grilla con tamaño automático */
	Component.resizableGrid(self.getSelector("gridEliminacionCapitulosContrato"));
	/* Buscar capítulos */
	self.buscarCapitulosEliminacion();
};

AltaContratoEvent.prototype.buscarCapitulosEliminacion = function() {
	/* Bloquear pantalla */
	BLOCK.showBlock("Buscando capítulos");
	
	var self = altaContratoEvent;
	
	var buscarCapitulosEliminacionRequest = {
		contrato: self.contrato.numero,
		grupo: self.contrato.grupoCC.nroGrupo,
		senial: self.contrato.montoSenialTT.senial
	};
	
	var data = {
		async: false,
		action:"altaContratoBuscarCapitulosEliminacion.action", 
		request: Component.serialize(buscarCapitulosEliminacionRequest, "buscarCapitulosEliminacionRequest"),  
		method: "GET", 
		responseObject: "capitulosTituloRecontratacion", 
		callback: function(capitulosTituloRecontratacion) {
			self.getSelector("gridEliminacionCapitulosContrato").clearGridData().setGridParam({data: capitulosTituloRecontratacion}).trigger('reloadGrid');
			/* Desbloquear pantalla */
			BLOCK.hideBlock();
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.primerValidacionRerunBajaCapitulos = function(clave, capitulo, parte) {
	var self = altaContratoEvent;
	
	var primerValidacionRerunBajaCapitulosRequest = {
		contrato: self.contrato.numero,
		grupo: self.contrato.grupoCC.nroGrupo,
		senial: self.contrato.montoSenialTT.senial,
		clave: clave,
		cantidadCapitulos: 1
	};
	
	var data = {
		async: false,
		action:"altaContratoPrimerValidacionRerunBajaCapitulos.action", 
		request: Component.serialize(primerValidacionRerunBajaCapitulosRequest, "primerValidacionRerunBajaCapitulosRequest"),  
		method: "GET", 
		responseObject: "primerValidacionRerunBajaCapitulosResponse", 
		callback: function(primerValidacionRerunBajaCapitulosResponse) {
			if (primerValidacionRerunBajaCapitulosResponse.mensaje) {
				var parametros = {mensaje: primerValidacionRerunBajaCapitulosResponse.mensaje, clave: clave, capitulo: capitulo, parte: parte};
				self.abrirWarningValidacionRerunBajaCapitulos(parametros);
			} else {
				/* Pasar el parametro desenlace = 'N' */
				var parametros = {desenlace: 'N', clave: clave, capitulo: capitulo, parte: parte};
				self.confirmarBajaCapitulos(parametros);
			}
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.abrirWarningValidacionRerunBajaCapitulos = function(parametros) {
	var self = altaContratoEvent;

	var popup = popupConfirmacionEvent;
	popup.cancelar = function() {
		popup.close();
		popup.remove();
	};
	popup.confirmar = function() {
		popup.close();
		popup.remove();
		self.segundaValidacionRerunBajaCapitulos(parametros);
	};
	popup.create(self.div.id, parametros.mensaje);
};

AltaContratoEvent.prototype.segundaValidacionRerunBajaCapitulos = function(datos) {
	var self = altaContratoEvent;
	
	var segundaValidacionRerunBajaCapitulosRequest = {
		contrato: self.contrato.numero,
		grupo: self.contrato.grupoCC.nroGrupo,
		senial: self.contrato.montoSenialTT.senial,
		clave: datos.clave
	};
	
	var data = {
		async: false,
		action:"altaContratoSegundaValidacionRerunBajaCapitulosRequest.action", 
		request: Component.serialize(segundaValidacionRerunBajaCapitulosRequest, "segundaValidacionRerunBajaCapitulosRequest"),  
		method: "GET", 
		responseObject: "segundaValidacionRerunBajaCapitulosResponse", 
		callback: function(segundaValidacionRerunBajaCapitulosResponse) {
			if (segundaValidacionRerunBajaCapitulosResponse.hayErrores) {
				/* Muestro los mensajes de error que retorna el desenlace */				
				var paramsDesenlace = {
					showBotonera: false,
					datosDesenlace: segundaValidacionRerunBajaCapitulosResponse.erroresDesenlace,
					mensaje: "Hubo errores al intentar desenlazar."
				};
				self.abrirPopupDatosDesenlace(paramsDesenlace);
				
				var paramsVigencia = {
					showBotonera: false,
					datosVigencia: segundaValidacionRerunBajaCapitulosResponse.erroresVigencia,
					mensaje: "Hubo errores al intentar desenlazar."
				};
				self.abrirPopupDatosVigencia(paramsVigencia);
				
			} else if (segundaValidacionRerunBajaCapitulosResponse.erroresDesenlace || segundaValidacionRerunBajaCapitulosResponse.erroresVigencia) {
				/* Mostrar popup warnings con confirmacion y desenlace = 'S' */
				if (segundaValidacionRerunBajaCapitulosResponse.erroresDesenlace) {
					var params = {
						callback: function() {
							var parametros = {desenlace: 'S', clave: datos.clave, capitulo: datos.capitulo, parte: datos.parte};
							self.cierrePopupDatosDesenlace();
							self.confirmarBajaCapitulos(parametros);
						},
						showBotonera: true,
						datosDesenlace: segundaValidacionRerunBajaCapitulosResponse.erroresDesenlace,
						mensaje: "Hubo errores al intentar desenlazar. ¿Desea continuar?"
					};
					self.abrirPopupDatosDesenlace(params);
				}
				/* Mostrar popup warnings con confirmacion y desenlace = 'S' */
				if (segundaValidacionRerunBajaCapitulosResponse.erroresVigencia) {
					var params = {
						callback: function() {
							var parametros = {desenlace: 'S', clave: datos.clave, capitulo: datos.capitulo, parte: datos.parte};
							self.cierrePopupDatosVigencia();
							self.confirmarBajaCapitulos(parametros);
						},
						showBotonera: true,
						datosVigencia: segundaValidacionRerunBajaCapitulosResponse.erroresVigencia,
						mensaje: "Hubo errores al intentar desenlazar. ¿Desea continuar?"
					};
					self.abrirPopupDatosVigencia(params);
				}
			} else {
				/* Pasar el parametro desenlace = 'N' */
				var parametros = {desenlace: 'N', clave: datos.clave, capitulo: datos.capitulo, parte: datos.parte};
				self.confirmarBajaCapitulos(parametros);
			}
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.confirmarBajaCapitulos = function(parametros) {
	var self = altaContratoEvent;
	
	var confirmarBajaCapitulosRequest = {
		contrato: self.contrato.numero,
		grupo: self.contrato.grupoCC.nroGrupo,
		senial: self.contrato.montoSenialTT.senial,
		clave: parametros.clave,
		capitulo: parametros.capitulo,
		parte: parametros.parte,
		desenlace: parametros.desenlace
	};
	
	var data = {
		async: false,
		action:"altaContratoConfirmarBajaCapitulos.action", 
		request: Component.serialize(confirmarBajaCapitulosRequest, "confirmarBajaCapitulosRequest"),  
		method: "POST", 
		responseObject: "confirmarBajaCapitulosResponse", 
		callback: function(confirmarBajaCapitulosResponse) {
			var mensaje = self.validarCantidadCapitulosBaja();
			
			if (mensaje != "") {
				MESSAGE.alert(mensaje);
			}
			
			if (confirmarBajaCapitulosResponse.rerun) {
				/* Seteo los datos necesarios para la busqueda en FP11015 */
				self.contrato.tituloRecontratacionSeleccionado = new Object();
				self.contrato.tituloRecontratacionSeleccionado.clave = parametros.clave;
				/* FIXME: ver de donde obtener el titulo */
				self.contrato.tituloRecontratacionSeleccionado.titulo = "";
				self.contrato.tituloRecontratacionSeleccionado.contrato = self.contrato.numero;
				self.contrato.tituloRecontratacionSeleccionado.grupo = (esTituloCC ? self.contrato.grupoCC.nroGrupo : self.contrato.grupoSC.nroGrupo);
				
				/* Redirigir a FP11015 */
				var pantallaRetornoRerun = mensaje == "" ? "FP11009" : "FP11018";
				self.getSelector("formEliminacionCapitulosContrato").hide();
				self.abrirSeleccionTituloRecontratacionEnlaces(pantallaRetornoRerun);
			} else {
				/* Si la validacion no retorna nada, regreso a FP11009. Sino, voy recargo la grilla */
				if (mensaje != "") {
					self.buscarCapitulosEliminacion();
				} else {
					/* Limpio los datos viejos */ 
					self.getSelector("formEliminacionCapitulosContrato").hide();
					self.contrato.grupoCC = new GrupoCCPOJO();
					self.contrato.grupoSC = new GrupoSCPOJO();
					self.contrato.montoSenialTT.tipoTitulo = null;
					self.contrato.montoSenialTT.titulosIngresados = null;
					/* Redirijo a FP11009 */
					self.abrirVisualizarGruposContrato();
				}
 			}
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.validarCantidadCapitulosBaja = function() {
	var self = altaContratoEvent;
	
	var validarCantidadCapitulosBajaRequest = {
		contrato: self.contrato.numero,
		grupo: self.contrato.grupoCC.nroGrupo,
		senial: self.contrato.montoSenialTT.senial
	};

	var mensaje = new String();
	
	var data = {
		async: false,
		action:"altaContratoValidarCantidadCapitulosBaja.action", 
		request: Component.serialize(validarCantidadCapitulosBajaRequest, "validarCantidadCapitulosBajaRequest"),  
		method: "GET", 
		responseObject: "mensajeCantidadCapitulosBaja", 
		callback: function(mensajeCantidadCapitulosBaja) {
			mensaje = mensajeCantidadCapitulosBaja;
		}
	};
	self.service.doRequest(data);

	return mensaje;
};

AltaContratoEvent.prototype.volverEliminacionCapitulosContrato = function() {
	var self = altaContratoEvent;
	self.getSelector("formEliminacionCapitulosContrato").hide();
	self.getSelector("formCargaContrato").show();
};

/***********************************************************************************		
 ********************				Pantalla FP11019			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirAdicionCapitulosContrato = function() {
	var self = altaContratoEvent;
	Component.get("html/altaContrato/adicionCapitulosContrato.html", self.drawAdicionCapitulosContrato);
};

AltaContratoEvent.prototype.drawAdicionCapitulosContrato = function(comp) {
	var self = altaContratoEvent;
	comp = comp.replace(/{{id}}/g, self.div.id);
	
	if (self.getSelector("formAdicionCapitulosContrato").length) {
		self.getSelector("formAdicionCapitulosContrato").replaceWith(comp);
	} else {
		self.getDiv().append(comp);
	}
	/* Accordion */
	Accordion.getInstance(self.getSelector("accordionAdicionCapitulosContrato"));
	/* Botonera */
	self.getSelector("volverAdicionCapitulosContrato").button().click(self.volverAdicionCapitulosContrato);
	/* Datos grupo */
	self.getSelector("senialAdicionCapitulosContrato").html(self.contrato.montoSenialTT.senial);
	self.getSelector("distribuidorAdicionCapitulosContrato").html(self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("distribuidorAdicionCapitulosContrato").attr("title", self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("montoAdicionCapitulosContrato").html(self.contrato.montoSenialTT.monto);
	self.getSelector("nroGrupoAdicionCapitulosContrato").html(self.contrato.grupoCC.nroGrupo);
	self.getSelector("tipoTituloAdicionCapitulosContrato").html(self.contrato.montoSenialTT.tipoTitulo);
	self.getSelector("nombreGrupoAdicionCapitulosContrato").html(self.contrato.grupoCC.nombreGrupo);
	self.getSelector("nuevaFormulaAdicionCapitulosContrato").html(self.contrato.grupoCC.cantidadOriginales + " / " + self.contrato.grupoCC.cantidadRepeticiones);
	self.drawGridAdicionCapitulosContrato();
};

AltaContratoEvent.prototype.drawGridAdicionCapitulosContrato = function() {
	var self = altaContratoEvent;
	
	self.getSelector("gridAdicionCapitulosContrato").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Nro. de capítulo', 'Parte', 'Título - Capítulo', '', ''],
		colModel:[
		    {name:'numeroCapitulo',	index:'numeroCapitulo',	align:'center'},
		    {name:'parte',			index:'parte',			align:'center', formatter:function(value, options, rData) {
		    	return value == 0 ? "" : value;
    		}},
		    {name:'tituloCapitulo',	index:'tituloCapitulo',	align:'left', formatter:function(value, options, rData) {
		    	return Component.trimWhitespace(rData['tituloCapitulo']);
    		}},
    		{name:'agregar',		index:'agregar',		align:'center', width:30, formatter:function(value, options, rData) {
		    	return "<div onclick='altaContratoEvent.primerValidacionRerunBajaCapitulos(\"" + rData['clave'] + "\", " + rData['numeroCapitulo'] + ", " + rData['parte'] + ")' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-plus conTooltip' title='Agregar capítulo'></span></div>";
    		}},
    		{name:'clave',			index:'clave',			align:'center', hidden: true}
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + self.div.id + '_pagerAdicionCapitulosContrato',
		viewrecords: true, 
		loadonce: true,
		multiselect: true,
		editurl: 'clientArray', 
		caption: 'Capítulos'
	});
	
	/* Grilla con tamaño automático */
	Component.resizableGrid(self.getSelector("gridAdicionCapitulosContrato"));
	
	var buscarCapitulosAdicionRequest = {
		contrato: self.contrato.numero,
		grupo: self.contrato.grupoCC.nroGrupo,
		senial: self.contrato.montoSenialTT.senial,
		proveedor: self.contrato.cabecera.proveedor
	};
	
	var data = {
		async: false,
		action:"altaContratoBuscarCapitulosAdicion.action", 
		request: Component.serialize(buscarCapitulosAdicionRequest, "buscarCapitulosAdicionRequest"),  
		method: "GET", 
		responseObject: "capitulosTituloRecontratacion", 
		callback: function(capitulosTituloRecontratacion) {
			self.getSelector("gridAdicionCapitulosContrato").clearGridData().setGridParam({data: capitulosTituloRecontratacion}).trigger('reloadGrid');
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.validarRemitoCapitulosSNC = function(clave, capitulo, parte) {
	var self = altaContratoEvent;
	
	var validarRemitoCapitulosSNCRequest = {
		contrato: self.contrato.numero,
		grupo: self.contrato.grupoCC.nroGrupo,
		senial: self.contrato.montoSenialTT.senial,
		clave: clave,
		capitulo: capitulo,
		parte: parte,
		proveedor: self.contrato.cabecera.proveedor
	};
	
	var data = {
		async: false,
		action:"altaContratoValidarRemitoCapitulosSNC.action", 
		request: Component.serialize(validarRemitoCapitulosSNCRequest, "validarRemitoCapitulosSNCRequest"),  
		method: "GET", 
		responseObject: "validarRemitoCapitulosSNCResponse", 
		callback: function(validarRemitoCapitulosSNCResponse) {
			if (validarRemitoCapitulosSNCResponse) {
				/* FIXME: Mostrar los datos por pantalla */
 			}
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.confirmarAltaCapitulos = function() {
	var self = altaContratoEvent;
	
	var confirmarAltaCapitulosRequest = {
		contrato: self.contrato.numero,
		grupo: self.contrato.grupoCC.nroGrupo,
		senial: self.contrato.montoSenialTT.senial,
		clave: self.contrato.tituloRecontratacionSeleccionado.clave,
		capitulo: capitulo,
		parte: parte,
		proveedor: self.contrato.cabecera.proveedor,
		fechaVigencia: null,
		confRemi: null,
		confCopia: null,
		rerun: null,
		desdeTitulo: null
	};
	
	var data = {
		async: false,
		action:"altaContratoConfirmarAltaCapitulos.action", 
		request: Component.serialize(confirmarAltaCapitulosRequest, "confirmarAltaCapitulosRequest"),  
		method: "POST", 
		responseObject: "confirmarAltaCapitulosResponse", 
		callback: function(confirmarAltaCapitulosResponse) {
			if (confirmarAltaCapitulosResponse) {
				/* FIXME: Mostrar los datos por pantalla */
 			}
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.volverAdicionCapitulosContrato = function() {
	var self = altaContratoEvent;
	/* Limpio los datos viejos */ 
	self.contrato.grupoCC = new GrupoCCPOJO();
	self.contrato.grupoSC = new GrupoSCPOJO();
	self.contrato.montoSenialTT.tipoTitulo = null;
	self.contrato.montoSenialTT.titulosIngresados = null;
	/* Redirijo a FP11009 */
	self.getSelector("formAdicionCapitulosContrato").hide();
	self.abrirVisualizarGruposContrato();
};