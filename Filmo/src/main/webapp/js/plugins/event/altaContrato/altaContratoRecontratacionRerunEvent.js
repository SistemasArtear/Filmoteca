/***********************************************************************************		
 ********************				Pantalla FP11015			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirSeleccionTituloRecontratacionEnlaces = function(origen) {
	var self = altaContratoEvent;
	Component.get("html/altaContrato/seleccionTituloRecontratacionEnlaces.html", function(comp) {
		self.drawSeleccionTituloRecontratacionEnlaces(comp, origen);
	});
};

AltaContratoEvent.prototype.drawSeleccionTituloRecontratacionEnlaces = function(comp, origen) {
	var self = altaContratoEvent;
	comp = comp.replace(/{{id}}/g, self.div.id);
	
	if (self.getSelector("formSeleccionTituloRecontratacionEnlaces").length) {
		self.getSelector("formSeleccionTituloRecontratacionEnlaces").replaceWith(comp);
	} else {
		self.getDiv().append(comp);
	}
	/* Accordion */
	Accordion.getInstance(self.getSelector("accordionSeleccionTituloRecontratacionEnlaces"));
	
	/* Botonera */
	if (origen == "FP11005") {
		self.getSelector("volverSeleccionTituloRecontratacionEnlaces").button().click(self.volverSeleccionTituloRecontratacionEnlacesFP11005);
	} else if (origen == "FP11007") {
		self.getSelector("volverSeleccionTituloRecontratacionEnlaces").button().click(self.volverSeleccionTituloRecontratacionEnlacesFP11007);	
	} else if (origen == "FP11009") {
		self.getSelector("volverSeleccionTituloRecontratacionEnlaces").button().click(self.volverSeleccionTituloRecontratacionEnlacesFP11009);	
	} else if (origen == "FP11012") {
		self.getSelector("volverSeleccionTituloRecontratacionEnlaces").button().click(self.volverSeleccionTituloRecontratacionEnlacesFP11012);
	} else if (origen == "FP11013") {
		self.getSelector("volverSeleccionTituloRecontratacionEnlaces").button().click(self.volverSeleccionTituloRecontratacionEnlacesFP11013);
	} else if (origen == "FP11014") {
		self.getSelector("volverSeleccionTituloRecontratacionEnlaces").button().click(self.volverSeleccionTituloRecontratacionEnlacesFP11014);
	} else if (origen == "FP11018") {
		self.getSelector("volverSeleccionTituloRecontratacionEnlaces").button().click(self.volverSeleccionTituloRecontratacionEnlacesFP11018);
	}
	
	/* Datos grupo */
	if (self.contrato.grupoCC.nroGrupo != ""){
		self.getSelector("grupoSeleccionTituloRecontratacionEnlaces").html(self.contrato.grupoCC.nroGrupo);
	}
	else {
		self.getSelector("grupoSeleccionTituloRecontratacionEnlaces").html(self.contrato.grupoSC.nroGrupo);
	}
	self.getSelector("senialSeleccionTituloRecontratacionEnlaces").html(self.contrato.montoSenialTT.senial);
	self.getSelector("distribuidorSeleccionTituloRecontratacionEnlaces").html(self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("distribuidorSeleccionTituloRecontratacionEnlaces").attr("title", self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("tituloSeleccionTituloRecontratacionEnlaces").html(self.contrato.tituloRecontratacionSeleccionado.clave + " " + self.contrato.tituloRecontratacionSeleccionado.titulo);
	self.getSelector("tituloSeleccionTituloRecontratacionEnlaces").attr("title", self.contrato.tituloRecontratacionSeleccionado.clave + " " + self.contrato.tituloRecontratacionSeleccionado.titulo);
	self.getSelector("vigenciaNuevoContratoSeleccionTituloRecontratacionEnlaces").html();
	
	self.drawGridSeleccionTituloRecontratacionEnlaces();
};

AltaContratoEvent.prototype.drawGridSeleccionTituloRecontratacionEnlaces = function() {
	var self = altaContratoEvent;
    var grupoActual = self.getSelector("grupoSeleccionTituloRecontratacionEnlaces").text();
	self.getSelector("gridSeleccionTituloRecontratacionEnlaces").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Contrato - Grupo', 'Distribuidor', 'Desde', 'Hasta', 'Anterior', 'Posterior', 'Mod. vigencia', '', '', ''],
		colModel:[ 
		    {name:'contratoGrupo',		index:'contratoGrupo',		align:'center', sortable:false, formatter:function(value, options, rData) { 
		    	return self.formatearContrato(rData['contrato']) + " - " + rData['grupo'];
			}},
		    {name:'distribuidor',		index:'distribuidor',		align:'center', sortable:false},
		    {name:'vigenciaDesde',		index:'vigenciaDesde',		align:'center', formatter:'date', formatoptions:{newformat: 'd/m/Y'}, sortable:false},
		    {name:'vigenciaHasta',		index:'vigenciaHasta',		align:'center', formatter:'date', formatoptions:{newformat: 'd/m/Y'}, sortable:false},
		    {name:'enlazadoAnterior',	index:'enlazadoAnterior',	align:'center', sortable:false, formatter:function(value, options, rData) { 
		    	if (rData['enlazadoAnterior'] != 0) {
		    		return self.formatearContrato(rData['enlazadoAnterior']) + " - " + rData['grupoAnterior'];
		    	} else {
		    		return "";
		    	}
			}},
		    {name:'enlazadoPosterior',	index:'enlazadoPosterior',	align:'center', sortable:false, formatter:function(value, options, rData) { 
		    	if (rData['enlazadoPosterior'] != 0) {
		    		return self.formatearContrato(rData['enlazadoPosterior']) + " - " + rData['grupoPosterior'];
		    	} else {
		    		return "";
		    	}
			}},
		    {name:'modVigencia',		index:'modVigencia',		align:'center', sortable:false},
		    {name:'enlazar',			index:'enlazar',			align:'center', sortable:false, formatter: function(value, options, rData) { 
		    	if (self.formatearContrato(rData['contrato']) != "Pendiente" ||( self.formatearContrato(rData['contrato']) == "Pendiente" && grupoActual != rData['grupo'] )) {
		    	return "<div onclick='altaContratoEvent.validarEnlacePosterior(" + rData['contrato'] + ", " + rData['grupo'] 
		    			+ ", \"" + rData['vigenciaDesde'] + "\", \"" + rData['vigenciaHasta']	+ "\", \"" + rData['enlazadoAnterior'] 
		    			+ "\", \"" + rData['enlazadoPosterior']  +  "\", \"" + rData['grupoAnterior'] + "\", \"" + rData['grupoPosterior'] 
	    				+ "\")' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-locked conTooltip' title='Enlazar'></span></div>";
		    	}
		    	else {
		    		return "";
		    	}
	    	}},	
		    {name:'desenlazar',			index:'desenlazar',			align:'center', sortable:false, formatter: function(value, options, rData) { 
		    	if (self.formatearContrato(rData['contrato']) == "Pendiente" && grupoActual == rData['grupo']) {
		    		return "<div onclick='altaContratoEvent.validarDesenlaceTituloContrato(" + rData['contrato'] + ", " + rData['grupo'] + ")' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-unlocked conTooltip' title='Desenlazar'></span></div>";
		    	}
		    	else {
		    		return "";
		    	}
	    	}},
		    {name:'verModVigencia',		index:'verModVigencia',		align:'center', sortable:false, formatter: function(value, options, rData) { 
		    	return "<div onclick='altaContratoEvent.abrirVisualizarModificacionesVigencia(" + rData['contrato'] + ", " + rData['grupo'] + ")' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-search conTooltip' title='Ver modificaciones de vigencia'></span></div>";
	    	}}
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#AltaContratoEventId_pagerSeleccionTituloRecontratacionEnlaces',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Contratos'
	});
	
	var groupHeaders = [
	    {startColumnName: 'vigenciaDesde', 		numberOfColumns: 2, titleText: 'Vigencia'}, 
	    {startColumnName: 'enlazadoAnterior', 	numberOfColumns: 2, titleText: 'Enlazado'}
	];
	self.getSelector("gridSeleccionTituloRecontratacionEnlaces").jqGrid('setGroupHeaders', { useColSpanStyle: true, groupHeaders: groupHeaders });
	
	/* Grilla con tamaño automático */
	Component.resizableGrid(self.getSelector("gridSeleccionTituloRecontratacionEnlaces"));
	
	self.buscarContratosEnlazadosTituloRecontratacion();
};

AltaContratoEvent.prototype.buscarContratosEnlazadosTituloRecontratacion = function() {
	/* Bloquear pantalla */
	BLOCK.showBlock("Buscando contratos");
	
	var self = altaContratoEvent;
	var esTituloCC = self.determinarFamiliaTitulo(self.contrato.montoSenialTT.tipoTitulo);
	
	var contratosEnlazadosTituloRecontratacionRequest = {
		contrato: esTituloCC ? self.contrato.grupoCC.contrato : self.contrato.grupoSC.contrato,
        grupo: esTituloCC ? self.contrato.grupoCC.nroGrupo : self.contrato.grupoSC.nroGrupo,
        senial: self.contrato.montoSenialTT.senial,
        clave: self.contrato.tituloRecontratacionSeleccionado.clave
	};
	
	var data = {
		async: false,
		action:"altaContratoContratosEnlazadosTituloRecontratacion.action", 
		request: Component.serialize(contratosEnlazadosTituloRecontratacionRequest, "contratosEnlazadosTituloRecontratacionRequest"),  
		method: "GET", 
		responseObject: "contratosEnlazadosTituloRecontratacion", 
		callback: function(contratosEnlazadosTituloRecontratacion) {
			self.getSelector("gridSeleccionTituloRecontratacionEnlaces").clearGridData().setGridParam({data: contratosEnlazadosTituloRecontratacion}).trigger('reloadGrid');
			/* Desbloquear pantalla */
			BLOCK.hideBlock();
		}
	};
	self.service.doRequest(data);
};

/***********************************************************************************		
 ********************  Pantalla FP11015 - Validacion desenlace  ********************
 ***********************************************************************************/
AltaContratoEvent.prototype.validarDesenlaceTituloContrato = function(contrato, grupo) {
	/* Bloquear pantalla */
	BLOCK.showBlock("Desenlazando");
	
	var self = altaContratoEvent;
	
	var validarDesenlaceTituloContratoRequest = {
		contrato: contrato,
		senial: self.contrato.montoSenialTT.senial,
		grupo: grupo,
		clave: self.contrato.tituloRecontratacionSeleccionado.clave        
	};
	
	var data = {
		async: false,
		action:"altaContratoValidarDesenlaceTituloContrato.action", 
		request: Component.serialize(validarDesenlaceTituloContratoRequest, "validarDesenlaceTituloContratoRequest"),  
		method: "GET", 
		responseObject: "validarDesenlaceTituloContratoResponse", 
		callback: function(validarDesenlaceTituloContratoResponse) {
			if (validarDesenlaceTituloContratoResponse.hayErrores) {
				/* Desbloquear pantalla */
				BLOCK.hideBlock();
				
				/* Muestro los mensajes de error que retorna el desenlace */				
				var params = {
					showBotonera: false,
					datosDesenlace: validarDesenlaceTituloContratoResponse.errores,
					mensaje: "Hubo errores al intentar desenlazar los contratos." 
					    + (validarDesenlaceTituloContratoResponse.idReporte ? (" Se generó un reporte con ID: " + validarDesenlaceTituloContratoResponse.idReporte) : "")
				};
				self.abrirPopupDatosDesenlace(params);
			} else if (validarDesenlaceTituloContratoResponse.errores) {
				/* Desbloquear pantalla */
				BLOCK.hideBlock();
				
				/* Muestro los mensajes de error que retorna el desenlace y espera confirmación */
				var params = {
					callback: function() {
						self.cierrePopupDatosDesenlace();
						var params = { contrato: contrato, grupo: grupo };
						self.ejecutarDesenlaceTituloContrato(params);
					},
					showBotonera: true,
					datosDesenlace: validarDesenlaceTituloContratoResponse.errores,
					mensaje: "Hubo errores al intentar desenlazar los contratos. ¿Desea continuar?"
				};
				self.abrirPopupDatosDesenlace(params);
			} else {
				var params = { contrato: contrato, grupo: grupo };
				self.ejecutarDesenlaceTituloContrato(params);
			}
		}
	};
	self.service.doRequest(data);
};

/***********************************************************************************		
 ********************   Pantalla FP11015 - Ejecución desenlace  ********************
 ***********************************************************************************/
AltaContratoEvent.prototype.ejecutarDesenlaceTituloContrato = function(datosDesenlace) {
	var self = altaContratoEvent;
	
	var ejecutarDesenlaceTituloContratoRequest = {
		contrato: datosDesenlace.contrato,
		senial: self.contrato.montoSenialTT.senial,
		grupo: datosDesenlace.grupo,
		clave: self.contrato.tituloRecontratacionSeleccionado.clave      
	};
	
	var data = {
		async: false,
		action:"altaContratoEjecutarDesenlaceTituloContrato.action", 
		request: Component.serialize(ejecutarDesenlaceTituloContratoRequest, "ejecutarDesenlaceTituloContratoRequest"),  
		method: "POST", 
		responseObject: "ejecutarDesenlaceTituloContratoResponse", 
		callback: function(ejecutarDesenlaceTituloContratoResponse) {
			/* Desbloquear pantalla */
			BLOCK.hideBlock();
			
			if (ejecutarDesenlaceTituloContratoResponse.hayErrores) {
				/* Muestro los mensajes de error que retorna el desenlace */				
				var params = {
					showBotonera: false,
					datosDesenlace: ejecutarDesenlaceTituloContratoResponse.errores,
					mensaje: "Hubo errores al intentar desenlazar los contratos."
				};
				self.abrirPopupDatosDesenlace(params);
			} else {
				MESSAGE.ok("Desenlace exitoso");
				self.buscarContratosEnlazadosTituloRecontratacion();
			}
		}
	};
	self.service.doRequest(data);
};

/***********************************************************************************		
 ********************	 Pantalla FP11015 - Validacion enlace	********************
 ***********************************************************************************/
AltaContratoEvent.prototype.validarEnlacePosterior = function(contrato, grupo, vigenciaDesde, vigenciaHasta, enlazadoAnt, enlazadoPos, grupoAnterior, grupoPosterior) {
	/* Bloquear pantalla */
	BLOCK.showBlock("Enlazando");
	
	var self = altaContratoEvent;
	
	var datosEnlace = {
		contrato : contrato,
		grupo: grupo,
		numeroAnterior: enlazadoAnt,
		grupoAnterior: grupoAnterior,
		numeroPos: enlazadoPos,
		grupoPosterior: grupoPosterior,
		fechaDesde: vigenciaDesde,
		fechaHasta: vigenciaHasta
	};
	
	var gPost ;
	if (self.contrato.grupoCC.nroGrupo != ""){
		gPost = self.contrato.grupoCC.nroGrupo;
	}
	else {
		gPost = self.contrato.grupoSC.nroGrupo;
	}
	
	var validarEnlacePosteriorRequest = {
		/* Contrato en el que se esta trabajando */	
		contratoPosterior: self.contrato.numero,
		/* Grupo en el que se esta trabajando */
		
		grupoPosterior: gPost,
		/* Titulo seleccionado en pantallas anteriores */
		clavePosterior: self.contrato.tituloRecontratacionSeleccionado.clave,
		/* Señal en la que se esta trabajando */
		senialPosterior: self.contrato.montoSenialTT.senial,
		
		/* Contrato en grilla */
		contrato: datosEnlace.contrato,
		/* Grupo en grilla */
		grupo: datosEnlace.grupo,
		/* Enlazado anterior en grilla */
		numeroAnterior: datosEnlace.numeroAnterior,
		/* Grupo anterior en grilla*/
		grupoAnterior: datosEnlace.grupoAnterior,
		/* Enlazado posterior en grilla */
		numeroPos: datosEnlace.numeroPos,
		/* Grupo posterior en grilla */
		grupoPos: datosEnlace.grupoPosterior,
		
		/* Vigencia desde en grilla */	
		fechaDesde: datosEnlace.fechaDesde,
		/* Vigencia hasta en grilla */
		fechaHasta: datosEnlace.fechaHasta
	};
	
	var data = {
		async: false,
		action:"altaContratoValidarEnlacePosterior.action", 
		request: Component.serialize(validarEnlacePosteriorRequest, "validarEnlacePosteriorRequest"),  
		method: "GET", 
		responseObject: "validarEnlacePosteriorResponse", 
		callback: function(validarEnlacePosteriorResponse) {
			if (validarEnlacePosteriorResponse.hayErrores) {
				/* Desloquear pantalla */
				BLOCK.hideBlock();
				
				/* Muestro los mensajes de error que retorna el enlace */				
				var params = {
					showBotonera: false,
					datosDesenlace: validarEnlacePosteriorResponse.erroresDesenlace,
					mensaje: "Hubo errores al intentar enlazar los contratos."
				};
				self.abrirPopupDatosDesenlace(params);
			} else if (validarEnlacePosteriorResponse.erroresDesenlace) {
				/* Desloquear pantalla */
				BLOCK.hideBlock();
				
				/* Muestro los mensajes de error que retorna el enlace y espera confirmación */
				var params = {
					callback: function() {
						self.cierrePopupDatosDesenlace();
						self.ejecutarEnlacePosterior(validarEnlacePosteriorRequest);
					},
					showBotonera: true,
					datosDesenlace: validarEnlacePosteriorResponse.erroresDesenlace,
					mensaje: "Hubo errores al intentar enlazar los contratos. ¿Desea continuar?"
				};
				self.abrirPopupDatosDesenlace(params);
			} else if (validarEnlacePosteriorResponse.erroresVigencia) {
				/* Desloquear pantalla */
				BLOCK.hideBlock();
				
				/* Muestro los mensajes de error que retorna el enlace y espera confirmación */
				var params = {
					callback: function() {
						self.cierrePopupDatosVigencia();
						self.ejecutarEnlacePosterior(validarEnlacePosteriorRequest);
						self.buscarContratosEnlazadosTituloRecontratacion();
					},
					showBotonera: true,
					datosVigencia: validarEnlacePosteriorResponse.erroresVigencia,
					mensaje: "Al contrato actual se le actualizarán las fechas de vigencia. ¿Desea continuar?"
				};
				self.abrirPopupDatosVigencia(params);
			} else {
				self.ejecutarEnlacePosterior(validarEnlacePosteriorRequest);
			}
		}
	};
	self.service.doRequest(data);
};

/***********************************************************************************		
 ********************	 Pantalla FP11015 - Ejecucion enlace	********************
 ***********************************************************************************/
AltaContratoEvent.prototype.ejecutarEnlacePosterior = function(datosEnlacePosterior) {
	var self = altaContratoEvent;
	
	var ejecutarEnlacePosteriorRequest = datosEnlacePosterior;
	
	var data = {
		async: false,
		action:"altaContratoEjecutarEnlacePosterior.action", 
		request: Component.serialize(ejecutarEnlacePosteriorRequest, "ejecutarEnlacePosteriorRequest"),  
		method: "POST", 
		responseObject: "ejecutarEnlacePosteriorResponse", 
		callback: function(ejecutarEnlacePosteriorResponse) {
			/* Desloquear pantalla */
			BLOCK.hideBlock();
			
			if (ejecutarEnlacePosteriorResponse.hayErrores) {
				/* Muestro los mensajes de error que retorna el enlace */				
				var paramsDesenlace = {
					showBotonera: false,
					datosDesenlace: ejecutarEnlacePosteriorResponse.erroresDesenlace,
					mensaje: "Hubo errores al intentar enlazar los contratos."
				};
				self.abrirPopupDatosDesenlace(paramsDesenlace);
				
				var paramsVigencia = {
					showBotonera: false,
					datosVigencia: ejecutarEnlacePosterior.erroresVigencia,
					mensaje: "Hubo errores al intentar enlazar los contratos."
				};
				self.abrirPopupDatosVigencia(paramsVigencia);
			} else {
				MESSAGE.ok("Enlace exitoso");
				self.buscarContratosEnlazadosTituloRecontratacion();
			}
			
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.volverSeleccionTituloRecontratacionEnlacesFP11005 = function() {
	var self = altaContratoEvent;
	/* Regreso a la modificación del grupo CC */
	self.getSelector("formSeleccionTituloRecontratacionEnlaces").hide();
	self.getSelector("formCargaGrupoCC").show();		
};

AltaContratoEvent.prototype.volverSeleccionTituloRecontratacionEnlacesFP11007 = function() {
	var self = altaContratoEvent;
	self.contrato.titulosRecontratar.push(self.contrato.tituloRecontratar);

	if (self.contrato.titulosRecontratar != null && self.contrato.titulosRecontratar.length > 0) {
		/* Guardo los titulos para más adelante */
		self.contrato.tituloRecontratar = self.contrato.titulosRecontratar[0];
		/* Seteo los datos para la próxima pantalla */
		self.contrato.tituloRecontratacionSeleccionado = new Object();
		/* Datos del grupo */
		self.contrato.tituloRecontratacionSeleccionado.contrato = self.contrato.numero;
		self.contrato.tituloRecontratacionSeleccionado.grupo = self.contrato.grupoSC.nroGrupo;
		/* Datos del título 
		 * FIXME: Ver de donde obtengo la descripcnion del título*/
		self.contrato.tituloRecontratacionSeleccionado.clave = self.contrato.tituloRecontratar.tipoTitulo + tituloRecontratar.nroTitulo;
		self.contrato.tituloRecontratacionSeleccionado.titulo = "";
		/* Recargo la pantalla */
		self.abrirSeleccionTituloRecontratacionEnlaces("FP11007");
	} else {
		/* Limpio las variables auxiliares */
		self.contrato.titulosRecontratar = new Array();
		self.contrato.tituloRecontratar = new Object();
		/* Regreso a la modificación del grupo SC */
		self.getSelector("formSeleccionTituloRecontratacionEnlaces").hide();
		self.getSelector("formCargaGrupoSC").show();		
	}
};

AltaContratoEvent.prototype.volverSeleccionTituloRecontratacionEnlacesFP11009 = function() {
	var self = altaContratoEvent;
	self.getSelector("formSeleccionTituloRecontratacionEnlaces").hide();
	/* Limpio los datos viejos */ 
	self.contrato.grupoCC = new GrupoCCPOJO();
	self.contrato.grupoSC = new GrupoSCPOJO();
	self.contrato.montoSenialTT.tipoTitulo = null;
	self.contrato.montoSenialTT.titulosIngresados = null;
	/* Redirijo a FP11009 */
	self.abrirVisualizarGruposContrato();
};

AltaContratoEvent.prototype.volverSeleccionTituloRecontratacionEnlacesFP11012 = function() {
	var self = altaContratoEvent;
	self.getSelector("formSeleccionTituloRecontratacionEnlaces").hide();
	
	var esTituloCC = self.determinarFamiliaTitulo(self.contrato.montoSenialTT.tipoTitulo);
	
	if (esTituloCC) {
		/* Limpio los datos viejos */ 
		self.contrato.grupoCC = new GrupoCCPOJO();
		self.contrato.grupoSC = new GrupoSCPOJO();
		self.contrato.montoSenialTT.tipoTitulo = null;
		self.contrato.montoSenialTT.titulosIngresados = null;
		if (self.contrato.tituloRecontratacionSeleccionado.coincideMonto == "S"){
			self.contrato.tituloRecontratacionSeleccionado.coincideMonto = null;
			self.volverVisualizarGruposContrato();
		}else {
			/* Redirijo a FP11009 */
			self.abrirVisualizarGruposContrato();
		}
	} else {
		/* Si se ingreso la totalidad de los títulos, redirijo a FP11009 sino a FP11010/11 */
		if (self.contrato.montoSenialTT.titulosIngresados == self.contrato.grupoSC.cantidadTitulos) {
			/* Limpio los datos viejos */ 
			self.contrato.grupoCC = new GrupoCCPOJO();
			self.contrato.grupoSC = new GrupoSCPOJO();
			self.contrato.montoSenialTT.tipoTitulo = null;
			self.contrato.montoSenialTT.titulosIngresados = null;
			if (self.contrato.tituloRecontratacionSeleccionado.coincideMonto == "S"){
				self.contrato.tituloRecontratacionSeleccionado.coincideMonto = null;
				self.volverVisualizarGruposContrato();
			}else {
				/* Redirijo a FP11009 */
				self.abrirVisualizarGruposContrato();
			}
		} else {
			/* Actualizo los datos de pantalla */
			self.getSelector("ingresadasSeleccionTitulo").html(self.contrato.montoSenialTT.titulosIngresados + " de " + self.contrato.grupoSC.cantidadTitulos);
			self.getSelector("ingresadasVisualizarTitulosGrupo").html(self.contrato.montoSenialTT.titulosIngresados + " de " + self.contrato.grupoSC.cantidadTitulos);
			/* Vuelvo a FP11010/11 */
			self.getSelector("formVisualizarTitulosGrupo").show();
		}	
	}
};

AltaContratoEvent.prototype.volverSeleccionTituloRecontratacionEnlacesFP11013 = function() {
	var self = altaContratoEvent;
	self.getSelector("formSeleccionTituloRecontratacionEnlaces").hide();
	//self.getSelector("formVisualizarTitulosGrupo").show();
	self.abrirVisualizarGruposContrato();
};

AltaContratoEvent.prototype.volverSeleccionTituloRecontratacionEnlacesFP11014 = function() {
	var self = altaContratoEvent;
	self.getSelector("formSeleccionTituloRecontratacionEnlaces").hide();
	
//	if (self.contrato.montoSenialTT.titulosIngresados == self.contrato.grupoCC.cantidadOriginales || self.contrato.montoSenialTT.titulosIngresados == self.contrato.CantCapitulosRS) {
		/* Limpio los datos viejos */ 
		self.contrato.tituloRecontratacionSeleccionado = null;
		self.contrato.capituloConfirmado = null;
		self.contrato.capituloConfirmadoRM = null;
		self.contrato.grupoCC = new GrupoCCPOJO();
		self.contrato.grupoSC = new GrupoSCPOJO();
		self.contrato.montoSenialTT.tipoTitulo = null;
		self.contrato.montoSenialTT.titulosIngresados = null;
		/* Redirijo a FP11009 */
		self.abrirVisualizarGruposContrato();
//	} else {
//		/* Volver a FP11014 */
//		self.getSelector("formSeleccionCapitulosTituloRecontratacion").show();
//		var capitulos = self.buscarCapitulosTituloRecontratacion();
//		self.getSelector("gridSeleccionCapitulosTituloRecontratacion").clearGridData().setGridParam({data: capitulos}).trigger('reloadGrid');
//	}
};

AltaContratoEvent.prototype.volverSeleccionTituloRecontratacionEnlacesFP11018 = function() {
	var self = altaContratoEvent;
	self.getSelector("formSeleccionTituloRecontratacionEnlaces").hide();
	self.getSelector("formEliminacionCapitulosContrato").show();
};

/***********************************************************************************		
 ********************				Pantalla FP11016			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirVisualizarModificacionesVigencia = function(contrato, grupo) {
	var self = altaContratoEvent;
	
	var params = {
		contrato: contrato,
		grupo: grupo
	};
	
	Component.get("html/altaContrato/visualizarModificacionesVigencia.html", function(comp) {
		self.drawVisualizarModificacionesVigencia(comp, params);
	});
};

AltaContratoEvent.prototype.drawVisualizarModificacionesVigencia = function(comp, params) {
	var self = altaContratoEvent;
	comp = comp.replace(/{{id}}/g, self.div.id);
	
	self.getSelector("formSeleccionTituloRecontratacionEnlaces").hide();

	if (self.getSelector("formVisualizarModificacionesVigencia").length) {
		self.getSelector("formVisualizarModificacionesVigencia").replaceWith(comp);
	} else {
		self.getDiv().append(comp);
	}
	/* Accordion */
	Accordion.getInstance(self.getSelector("accordionVisualizarModificacionesVigencia"));
	/* Botonera */
	self.getSelector("volverVisualizarModificacionesVigencia").button().click(self.volverVisualizarModificacionesVigencia);
	/* Datos grupo */
	self.getSelector("senialVisualizarModificacionesVigencia").html(self.contrato.montoSenialTT.senial);
	self.getSelector("distribuidorVisualizarModificacionesVigencia").html(self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("distribuidorVisualizarModificacionesVigencia").attr("title", self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("tituloVisualizarModificacionesVigencia").html(self.contrato.tituloRecontratacionSeleccionado.clave + " " + self.contrato.tituloRecontratacionSeleccionado.titulo);
	self.getSelector("tituloVisualizarModificacionesVigencia").attr("title", self.contrato.tituloRecontratacionSeleccionado.clave + " " + self.contrato.tituloRecontratacionSeleccionado.titulo);
	self.drawGridVisualizarModificacionesVigencia(params);
};


AltaContratoEvent.prototype.drawGridVisualizarModificacionesVigencia = function(params) {
	var self = altaContratoEvent;

	self.getSelector("gridVisualizarModificacionesVigencia").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Tip. Mod. Vigencia', 'Desde', 'Hasta', 'Desde', 'Hasta', 'Desde', 'Hasta'],
		colModel:[ 
		    {name:'tipoModVigencia',	index:'tipoModVigencia',	align:'center', sortable:false, formatter:function(value, options, rData) { 
		    	return rData['tipoVigencia'] + "  " + rData['descripcionVigencia'];
			}},
		    {name:'fechaDesdePayTv',	index:'fechaDesdePayTv',	align:'center', width:80, formatter:'date', formatoptions:{newformat: 'd/m/Y'}, sortable:false},
		    {name:'fechaHastaPayTv',	index:'fechaHastaPayTv',	align:'center', width:80, formatter:'date', formatoptions:{newformat: 'd/m/Y'}, sortable:false},
		    {name:'fechaDesdeAnterior',	index:'fechaDesdeAnterior',	align:'center', width:80, formatter:'date', formatoptions:{newformat: 'd/m/Y'}, sortable:false},
		    {name:'fechaHastaAnterior',	index:'fechaHastaAnterior',	align:'center', width:80, formatter:'date', formatoptions:{newformat: 'd/m/Y'}, sortable:false},
		    {name:'fechaDesdeNueva',	index:'fechaDesdeNueva',	align:'center', width:80, formatter:'date', formatoptions:{newformat: 'd/m/Y'}, sortable:false},
		    {name:'fechaHastaNueva',	index:'fechaHastaNueva',	align:'center', width:80, formatter:'date', formatoptions:{newformat: 'd/m/Y'}, sortable:false}
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#AltaContratoEventId_pagerVisualizarModificacionesVigencia',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Modificaciones de vigencia'
	});

	var groupHeaders = [
	    {startColumnName: 'fechaDesdePayTv', 	numberOfColumns: 2, titleText: 'Fecha Pay TV'}, 
	    {startColumnName: 'fechaDesdeAnterior', numberOfColumns: 2, titleText: 'Fecha anterior'},
	    {startColumnName: 'fechaDesdeNueva', 	numberOfColumns: 2, titleText: 'Fecha nueva'}
	];
	self.getSelector("gridVisualizarModificacionesVigencia").jqGrid('setGroupHeaders', { useColSpanStyle: true, groupHeaders: groupHeaders});
	
	/* Grilla con tamaño automático */
	Component.resizableGrid(self.getSelector("gridVisualizarModificacionesVigencia"));
	
	self.buscarModificacionesVigencia(params);
};

/* Si el contrato es igual al que se esta creando devueve "Pendiente", si no el numero de contrato */    
AltaContratoEvent.prototype.formatearContrato = function(contrato){
	var self = altaContratoEvent;
	var esTituloCC = self.determinarFamiliaTitulo(self.contrato.montoSenialTT.tipoTitulo);
	var contratoSelf = esTituloCC ? self.contrato.grupoCC.contrato : self.contrato.grupoSC.contrato;
	var resultado = "Pendiente";
	if (contrato != contratoSelf) {
		resultado = contrato;
	}
	return resultado;
};

AltaContratoEvent.prototype.volverVisualizarModificacionesVigencia = function() {
	var self = altaContratoEvent;
	self.getSelector("formVisualizarModificacionesVigencia").hide();
	self.getSelector("formSeleccionTituloRecontratacionEnlaces").show();
};

AltaContratoEvent.prototype.buscarModificacionesVigencia = function(params) {
	var self = altaContratoEvent;
	
	var visualizarModificacionesVigenciaRequest = {
		contrato: params.contrato,
        grupo: params.grupo,
	    senial: self.contrato.montoSenialTT.senial,
	    clave: self.contrato.tituloRecontratacionSeleccionado.clave
	};
	
	var data = {
		async: false,
		action:"altaContratoVisualizarModificacionesVigencia.action", 
		request: Component.serialize(visualizarModificacionesVigenciaRequest, "visualizarModificacionesVigenciaRequest"),  
		method: "GET", 
		responseObject: "modificacionesVigencia", 
		callback: function(modificacionesVigencia) {
			self.getSelector("gridVisualizarModificacionesVigencia").clearGridData().setGridParam({data: modificacionesVigencia}).trigger('reloadGrid');
		}
	};
	self.service.doRequest(data);
};