/***********************************************************************************		
 ********************				Pantalla FP11013			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirSeleccionTituloRecontratacion = function(params) {
	var self = altaContratoEvent;
	Component.get("html/altaContrato/seleccionTituloRecontratacion.html", function(comp) {
		self.drawSeleccionTituloRecontratacion(comp, params);
	});
};

AltaContratoEvent.prototype.drawSeleccionTituloRecontratacion = function(comp, params) {
	var self = altaContratoEvent;
	comp = comp.replace(/{{id}}/g, self.div.id);
	
	self.getSelector("formVisualizarTitulosGrupo").hide();

	if (self.getSelector("formSeleccionTituloRecontratacion").length) {
		self.getSelector("formSeleccionTituloRecontratacion").replaceWith(comp);
	} else {
		self.getDiv().append(comp);
	}
	
	/* Accordion */
	Accordion.getInstance(self.getSelector("accordionSeleccionTituloRecontratacion"));
	
	/* Botonera */
	self.getSelector("volverSeleccionTituloRecontratacion").button().click(self.volverSeleccionTituloRecontratacion);
	
	/* Datos grupo */
	self.getSelector("senialSeleccionTituloRecontratacion").html(self.contrato.montoSenialTT.senial);
	self.getSelector("distribuidorSeleccionTituloRecontratacion").html(self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("distribuidorSeleccionTituloRecontratacion").attr("title", self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("montoSeleccionTituloRecontratacion").html(self.contrato.montoSenialTT.monto);
	self.getSelector("nroGrupoSeleccionTituloRecontratacion").html(self.contrato.grupoCC.nroGrupo);
	self.getSelector("tipoTituloSeleccionTituloRecontratacion").html(self.contrato.montoSenialTT.tipoTitulo);
	self.getSelector("estrenoSeleccionTituloRecontratacion").html(self.contrato.grupoCC.estrenoRepeticion == "E" ? "Estreno" : "Repetición");
	var pasadas = parseInt(self.contrato.grupoCC.cantidadOriginales) + parseInt(self.contrato.grupoCC.cantidadRepeticiones);
	self.contrato.formula = self.contrato.grupoCC.cantidadOriginales + " / " + self.contrato.grupoCC.cantidadRepeticiones + " / " + pasadas;
	self.getSelector("formulaSeleccionTituloRecontratacion").html(self.contrato.grupoCC.cantidadOriginales + " / " + self.contrato.grupoCC.cantidadRepeticiones + " / " + pasadas); 
	
	/* Seteo los datos del grupo para usarlos posteriormente */
	self.contrato.grupoCC.estrenoRepeticion = params.grupo.estrenoRepeticion;
	self.contrato.grupoCC.cantidadOriginales = params.grupo.cantidadOriginales;
	self.contrato.grupoCC.nombreGrupo = params.grupo.nombreGrupo;
	self.contrato.grupoCC.tipoDerecho = params.grupo.tipoDerecho;
	
	/* Datos grupo */
	self.getSelector("ingresadasSeleccionTituloRecontratacion").html(self.contrato.montoSenialTT.titulosIngresados + " de " + self.contrato.grupoCC.cantidadOriginales);
	self.getSelector("nombreGrupoSeleccionTituloRecontratacion").html(self.contrato.grupoCC.nombreGrupo);
	self.getSelector("claveSeleccionTitulo").html(params.titulo.clave);
	self.contrato.tituloSeleccionado = params.titulo;
	
	/* Cargo la grílla con títulos */
	self.drawGridTitulosRecontratacion();
};

AltaContratoEvent.prototype.drawGridTitulosRecontratacion = function() {
	var self = altaContratoEvent;
	
	self.getSelector("gridTitulosRecontratacion").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Clave', 'Título', 'Contrato', 'Grupo', 'Fórmula', 'Capítulo', 'Todos', 'Capitulos'],
		colModel:[ 
		    {name:'clave', 		 		 index:'clave', 	  		  align:'center'},
		    {name:'titulo', 	 		 index:'titulo', 	  		  align:'left', formatter:function(value, options, rData) {
    			return Component.trimWhitespace(rData['titulo']);	
	    	}},
		    {name:'contrato',			 index:'contrato', 	  		  align:'center'},
		    {name:'grupo', 				 index:'grupo',				  align:'center'},
		    {name:'formula',			 index:'formula',	  		  align:'center'},
		    {name:'seleccionarCapitulo', index:'seleccionarCapitulo', align:'center'},
		    {name:'seleccionarTodos', 	 index:'seleccionarTodos', 	  align:'center'},
		    {name:'capitulos', 	 		 index:'capitulos',     	  align:'center' , hidden:'true'}
		   
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + self.div.id + '_pagerTitulosRecontratacion',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Títulos',
		gridComplete: function() { 							
			var ids = $(this).jqGrid('getDataIDs');
			var rowsF= $(this).jqGrid('getRowData')
			for (var i = 0; i < ids.length; i++) {
				var cantCapContrato = rowsF[i].formula.substr(0, rowsF[i].formula.indexOf('/'));
				var seleccionarCapitulo = "<div onclick='altaContratoEvent.seleccionCapitulosTituloRecontratacion(" + ids[i] + ", false);' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-circle-check conTooltip'></span></div>";
				var seleccionarTodos = "<div> </div>";
				if (parseInt(cantCapContrato) <= self.contrato.grupoCC.cantidadOriginales) {
					seleccionarTodos = "<div onclick='altaContratoEvent.seleccionCapitulosTituloRecontratacion(" + ids[i] + ", true);' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-circle-check conTooltip'></span></div>";
				}
				
				$(this).jqGrid('setRowData', ids[i], { seleccionarCapitulo: seleccionarCapitulo, seleccionarTodos: seleccionarTodos });
			}
		}
	});
	
	/* Grilla con tamaño automático */
	Component.resizableGrid(self.getSelector("gridTitulosRecontratacion"));
	
	var buscarTitulosRecontratacionRequest = {
		clave: self.contrato.tituloSeleccionado.clave,
		senial: self.contrato.montoSenialTT.senial, 
		contrato: self.contrato.numero, 
		grupo: self.contrato.grupoCC.nroGrupo
	};
	
	var data = {
		async: false,
		action:"altaContratoBuscarTitulosRecontratacion.action", 
		request: Component.serialize(buscarTitulosRecontratacionRequest, "buscarTitulosRecontratacionRequest"),  
		method: "GET", 
		responseObject: "titulosRecontratacion", 
		callback: function(titulosRecontratacion) {
			if (titulosRecontratacion) {
				self.getSelector("gridTitulosRecontratacion").clearGridData().setGridParam({data: titulosRecontratacion}).trigger('reloadGrid');
			} else {
				MESSAGE.alert("El título seleccionado no tiene contratos anteriores");
			}
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.seleccionCapitulosTituloRecontratacion = function(id, seleccionarTodos) {
	var self = altaContratoEvent;
	
	var tituloRecontratacion = self.getSelector("gridTitulosRecontratacion").jqGrid('getRowData', id);
	self.contrato.tituloRecontratacionSeleccionado = tituloRecontratacion;
	self.contrato.CantCapitulosRS = tituloRecontratacion.capitulos; //parseInt(tituloRecontratacion.formula.substr(0, tituloRecontratacion.formula.indexOf('/')));
	if (seleccionarTodos) {
		self.validarRemitoSNC();
	} else {
		/* Abrir pantalla FP11014 con clave, contrato, grupo directo sin validaciones */
		self.ultimoRerun = false;
	    self.seleccionoAlgunCapitulo = false;
		self.abrirSeleccionCapitulosTituloRecontratacion(tituloRecontratacion);
	}
};

/***********************************************************************************		
 **********		Pantalla FP11013 - Selección 'todos' - Validar remito	  **********
 ***********************************************************************************/
AltaContratoEvent.prototype.validarRemitoSNC = function() {
	var self = altaContratoEvent;
	
	var validarRemitoSNCRequest = {
	    contrato: self.contrato.numero, 
		grupo: self.contrato.grupoCC.nroGrupo,
		senial: self.contrato.montoSenialTT.senial, 
	    clave: self.contrato.tituloRecontratacionSeleccionado.clave
	};
    
	var data = {
		async: false,
		action:"altaContratoValidarRemitoSNC.action", 
		request: Component.serialize(validarRemitoSNCRequest, "validarRemitoSNCRequest"),  
		method: "GET", 
		responseObject: "validarRemitoSNCResponse", 
		callback: function(validarRemitoSNCResponse) {
			if (validarRemitoSNCResponse) {
				/* Abrir popup con datos */
				self.abrirPopupValidarRemitoSNC(validarRemitoSNCResponse);
			} else {
				/* Llamar a validación de master de títulos */
				self.validarMasterTitulo(self.contrato.tituloRecontratacionSeleccionado);
			}
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.abrirPopupValidarRemitoSNC = function(validarRemitoSNCResponse) {
	var self = altaContratoEvent;
	Component.get("html/altaContrato/popupValidarRemitoSNC.html", function(comp) {
		self.drawPopupValidarRemitoSNC(comp, validarRemitoSNCResponse);
	});
};

AltaContratoEvent.prototype.drawPopupValidarRemitoSNC = function(comp, validarRemitoSNCResponse) {
	var self = altaContratoEvent;
	/* Inicializo el array temporal donde guardo los capitulos confirmados */
	self.contrato.capitulosConfirmados = new Array();
	
	$("#popupValidarRemitoSNC").dialog({width: 900, show: 'blind', hide: 'blind', modal: true, autoOpen: false,
		buttons: {
			"Salir": function() {
				$(this).dialog("close");
			}
		}
	});
	$("#popupValidarRemitoSNC").empty().append(comp.replace(/{{id}}/g, self.div.id));
	$("#popupValidarRemitoSNC").dialog("open");
	
	self.getSelector("continuarValidarRemitoSNC").button().click(function() {
		self.cierrePopupValidarRemitoSNC();
		
		if (self.contrato.capitulosConfirmados.length > 0) {
			self.abrirPopupConfirmarMaster();
		} else {
			self.confirmarSeleccionCapitulosTituloRecontratacion();
		}
	});
	
	self.getSelector("gridValidarRemitoSNC").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Capítulo', 'Parte', 'Remito', 'Guía', 'Fórmula', 'Fecha ingreso', 'Fecha remito', 'Chqueo volver', ''],
		colModel:[ 
		    {name:'capitulo',		 index:'capitulo',			align:'center'},
		    {name:'parte',			 index:'parte',				align:'center'},
		    {name:'nroRemito',		 index:'nroRemito',			align:'center'},
		    {name:'nroGuia',		 index:'nroGuia',			align:'center'},
		    {name:'formula',		 index:'formula',			align:'center'},
		    {name:'fechaIngreso',	 index:'fechaIngreso',		align:'center',	formatter:'date', formatoptions:{newformat: 'd/m/Y'}},
		    {name:'fechaRemito',	 index:'fechaRemito',		align:'center',	formatter:'date', formatoptions:{newformat: 'd/m/Y'}},
		    {name:'chequeoVolver',	 index:'chequeoVolver',		align:'center'},
		    {name:'confirmarRemito', index:'confirmarRemito',	align:'center'}
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + self.div.id + '_pagerValidarRemitoSNC',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: '',
		gridComplete: function() { 							
			var ids = $(this).jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var confirmarRemito = "<div onclick='altaContratoEvent.confirmarRemitoValidadoSNC(" + ids[i] + ");' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-circle-check conTooltip' title='Confirmar remito'></span></div>";
				$(this).jqGrid('setRowData', ids[i], { confirmarRemito: confirmarRemito });
			}
		}
	});
	
	self.getSelector("gridValidarRemitoSNC").clearGridData().setGridParam({data: validarRemitoSNCResponse}).trigger('reloadGrid');
};

AltaContratoEvent.prototype.confirmarRemitoValidadoSNC = function(idRemito) {
	var self = altaContratoEvent;
	/* Agrego el capítulo a un array temporal */
	var capituloConfirmado = self.getSelector("gridValidarRemitoSNC").jqGrid('getRowData', idRemito);
	self.contrato.capitulosConfirmados.push(capituloConfirmado);
	/* Inhabilito el registro en la grilla */
	self.getSelector("gridValidarRemitoSNC").setCell(idRemito, 'confirmarRemito', '&nbsp', '', '');
};

AltaContratoEvent.prototype.cierrePopupValidarRemitoSNC = function() {
	$("#popupValidarRemitoSNC").dialog("close");
	$("#popupValidarRemitoSNC").dialog("remove");
};

/***********************************************************************************		
 **********		Pantalla FP11013 - Selección 'todos' - Confirmar master	  **********
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirPopupConfirmarMaster = function() {
	var self = altaContratoEvent;
	Component.get("html/altaContrato/popupConfirmarMaster.html", self.drawPopupConfirmarMaster);
};

AltaContratoEvent.prototype.drawPopupConfirmarMaster = function(comp) {
	var self = altaContratoEvent;

	$("#popupConfirmarMaster").dialog({
		width: 900, show: 'blind', hide: 'blind', modal: true, autoOpen: false,
		buttons: {
			"Salir": function() {
				$(this).dialog("close");
			}
		}
	});
	$("#popupConfirmarMaster").empty().append(comp.replace(/{{id}}/g, self.div.id));
	$("#popupConfirmarMaster").dialog("open");
	
	self.getSelector("continuarConfirmarMaster").button().click(function() {
		self.cierrePopupConfirmarMaster();
		if (self.contrato.capitulosConfirmados.length > 0) {
			self.validarMasterTitulo(self.contrato.capitulosConfirmados[0]);	
		} else {
			self.confirmarSeleccionCapitulosTituloRecontratacion();	
		}
	});
	
	self.getSelector("gridConfirmarMaster").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Capítulo', 'Parte', '', '', '', '', '', '', ''],
		colModel:[ 
		    {name:'capitulo',		 index:'capitulo',			align:'center'},
		    {name:'parte',			 index:'parte',				align:'center'},
		    {name:'confirmarMaster', index:'confirmarRemito',	align:'center'},
		    {name:'nroRemito',		 index:'nroRemito',			hidden: true},
		    {name:'nroGuia',		 index:'nroGuia',			hidden: true},
		    {name:'formula',		 index:'formula',			hidden: true},
		    {name:'fechaIngreso',	 index:'fechaIngreso',		hidden: true,	formatter:'date', formatoptions:{newformat: 'd/m/Y'}},
		    {name:'fechaRemito',	 index:'fechaRemito',		hidden: true,	formatter:'date', formatoptions:{newformat: 'd/m/Y'}},
		    {name:'chequeoVolver',	 index:'chequeoVolver',		hidden: true}
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + self.div.id + '_pagerConfirmarMaster',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: '',
		gridComplete: function() { 							
			var ids = $(this).jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var confirmarMaster = "<div onclick='altaContratoEvent.confirmarMaster(" + ids[i] + ");' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-circle-check conTooltip' title='Confirmar master'></span></div>";
				$(this).jqGrid('setRowData', ids[i], { confirmarMaster: confirmarMaster });
			}
		}
	});
	
	self.getSelector("gridConfirmarMaster").clearGridData().setGridParam({data: self.contrato.capitulosConfirmados}).trigger('reloadGrid');
	self.capitulosConfirmados = new Array();
};

AltaContratoEvent.prototype.confirmarMaster = function(idMaster) {
	var self = altaContratoEvent;
	var capituloConfirmado = self.getSelector("gridConfirmarMaster").jqGrid('getRowData', idMaster);
	self.contrato.capitulosConfirmados.push(capituloConfirmado);
	self.getSelector("gridConfirmarMaster").setCell(idMaster, 'confirmarMaster', '&nbsp', '', '');
};

AltaContratoEvent.prototype.cierrePopupConfirmarMaster = function() {
	$("#popupConfirmarMaster").dialog("close");
	$("#popupConfirmarMaster").dialog("remove");
};

/***********************************************************************************		
 **********		Pantalla FP11013 - Selección 'todos' - Validar master	  **********
 ***********************************************************************************/
AltaContratoEvent.prototype.validarMasterTitulo = function(master) {
	var self = altaContratoEvent;
	
	/* Valido si debo redirigir a confirmación */ 
	if (self.contrato.capitulosConfirmados.length > 0) {
	    var validarMasterTituloRequest = {
    	    clave: self.contrato.tituloRecontratacionSeleccionado.clave,
			senial: self.contrato.montoSenialTT.senial,
			capitulo: master.capitulo,
		    parte: master.parte,
		    proveedor: self.contrato.cabecera.proveedor
		};
	    
		var data = {
			async: false,
			action:"altaContratoValidarMasterTitulo.action", 
			request: Component.serialize(validarMasterTituloRequest, "validarMasterTituloRequest"),  
			method: "GET", 
			responseObject: "validarMasterTituloResponse", 
			callback: function(validarMasterTituloResponse) {
				if (validarMasterTituloResponse) {
					var params = {
						master: master,
						validarMasterTituloResponse: validarMasterTituloResponse
					};
					self.abrirPopupValidarMasterTitulo(params);
				} else {
					self.contrato.capitulosConfirmados = $.grep(self.contrato.capitulosConfirmados, function (capConf) { 
						return capConf.capitulo == master.capitulo && capConf.parte == master.parte; 
					}, true);	
				}
			}
		};
		self.service.doRequest(data);
	} else {
		self.confirmarSeleccionCapitulosTituloRecontratacion();
	}
};

AltaContratoEvent.prototype.abrirPopupValidarMasterTitulo = function(params) {
	var self = altaContratoEvent;
	Component.get("html/altaContrato/popupValidarMasterTitulo.html", function(comp) {
		self.drawPopupValidarMasterTitulo(comp, params);
	});
};

AltaContratoEvent.prototype.drawPopupValidarMasterTitulo = function(comp, params) {
	var self = altaContratoEvent;
	
	$("#popupValidarMasterTitulo").dialog({ width: 900, show: 'blind', hide: 'blind', modal: true, autoOpen: false,
		buttons: {
			"Salir": function() {
				$(this).dialog("close");
			}
		}
	});
	$("#popupValidarMasterTitulo").empty().append(comp.replace(/{{id}}/g, self.div.id));
	$("#popupValidarMasterTitulo").dialog("open");
	
	/* Mostrar la leyenda: ‘Ingrese la fecha que se tomará como fecha de vigencia desde para el título’ */
	if (self.contrato.grupoCC.tipoDerecho == "R") {
		Datepicker.getInstance(self.getSelector("fechaCopia"), new Date());
		self.getSelector("divFechaCopiaValidarMasterTitulo").show();
	} else {
		self.getSelector("divFechaCopiaValidarMasterTitulo").hide();
	}
	
	self.getSelector("confirmarDatosValidarMasterTitulo").button().click(function() {
		var argumentos = {
			master: params.master,
			confirma: true
		};
		self.confirmarDatosValidarMasterTitulo(argumentos);
	});
	self.getSelector("rechazaDatosValidarMasterTitulo").button().click(function() {
		var argumentos = {
			master: params.master,
			confirma: false
		};
		self.confirmarDatosValidarMasterTitulo(argumentos);
	});
	
	self.getSelector("gridValidarMasterTitulo").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Soporte', 'Fecha copia'],
		colModel:[ 
		    {name:'soporte',		index:'soporte',		align:'center'},
		    {name:'fechaCopia',		index:'fechaCopia',		align:'center',	formatter:'date', formatoptions:{newformat: 'd/m/Y'}}
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + self.div.id + '_pagerValidarMasterTitulo',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: ''
	});
	
	self.getSelector("gridValidarMasterTitulo").clearGridData().setGridParam({data: params.validarMasterTituloResponse}).trigger('reloadGrid');
};

AltaContratoEvent.prototype.confirmarDatosValidarMasterTitulo = function(argumentos) {
	var self = altaContratoEvent;
	if (argumentos.confirma) {
		argumentos.master.recCopia = 'S';
		if (self.contrato.grupoCC.tipoDerecho == "R") {
			argumentos.master.fechaCopia = self.getSelector("fechaCopia").val();
		} else {
			argumentos.master.fechaCopia = "01/01/0001";
		}
	} else {
		argumentos.master.recCopia = 'N';
		argumentos.master.fechaCopia = "01/01/0001";
	}

	self.contrato.capitulosConfirmadosRM.push(argumentos.master);
	self.contrato.capitulosConfirmados = $.grep(self.contrato.capitulosConfirmados, function (capConf) { 
		return capConf.capitulo == argumentos.master.capitulo && capConf.parte == argumentos.master.parte; 
	}, true);
	
	self.validarMasterTitulo(self.contrato.capitulosConfirmados[0]);
};

/***********************************************************************************		
 **********			Pantalla FP11013 - Selección 'todos' - Confirmar	  **********
 ***********************************************************************************/
AltaContratoEvent.prototype.confirmarSeleccionCapitulosTituloRecontratacion = function() {
	var self = altaContratoEvent;

	var marcaRerun = false;
	self.contrato.capitulosConfirmados = self.buscarCapitulosTituloRecontratacion();
	
	for (var i = 0; i < self.contrato.capitulosConfirmadosRM.length; i++) {
		self.contrato.capitulosConfirmados = $.grep(self.contrato.capitulosConfirmados, function (capConf) { 
			return capConf.capitulo == self.contrato.capitulosConfirmadosRM[i].capitulo && capConf.parte == self.contrato.capitulosConfirmadosRM[i].parte; 
		}, true);
		self.contrato.capitulosConfirmados.push(self.contrato.capitulosConfirmadosRM[i]);
	}
	
	if (self.contrato.capitulosConfirmados.length > 0){
		for (var i = 0; i < self.contrato.capitulosConfirmados.length; i++) {
			var capituloActual = self.contrato.capitulosConfirmados[i];
		
			var confirmarSeleccionCapitulosRequest = {
					contrato: self.contrato.numero, 
					grupo: self.contrato.grupoCC.nroGrupo,
					senial: self.contrato.montoSenialTT.senial,
					clave: self.contrato.tituloRecontratacionSeleccionado.clave,
					proveedor: self.contrato.cabecera.proveedor,
					capitulo: capituloActual.capitulo == undefined ? capituloActual.numeroCapitulo : capituloActual.capitulo,
					parte: capituloActual.parte,
					recCopia: (capituloActual.recCopia == undefined ? "N": capituloActual.recCopia),
					fechaCopia: (capituloActual.fechaCopia == undefined ? "01/01/0001" : capituloActual.fechaCopia)
			};
	    
			var data = {
					async: false,
					action:"altaContratoConfirmarSeleccionCapituloTituloRecontratacion.action", 
					request: Component.serialize(confirmarSeleccionCapitulosRequest, "confirmarSeleccionCapitulosRequest"),  
					method: "POST", 
					responseObject: "confirmarSeleccionCapitulosResponse", 
					callback: function(confirmarSeleccionCapitulosResponse) {
						/* Marca para Ir a FP11015 */
						if (confirmarSeleccionCapitulosResponse.rerun) {
							marcaRerun = true; 
						}
					}
			};
			self.service.doRequest(data);
		}
	}
	else {  /* Si no hay capitulos confirmados */
		marcaRerun = self.recontratarTituloTodosLosCapitulosNoConfirmados();
	}
	/* Incremento el contador de titulos ingresados para que sea igual al orginal y cumpla condicion de salida */
	self.contrato.montoSenialTT.titulosIngresados = self.contrato.grupoCC.cantidadOriginales;
	
	if (marcaRerun) {
		/* Ir a FP11015 */
		$("#popupConfirmarMaster").dialog("close");
		$("#popupConfirmarMaster").dialog("remove");
		self.getSelector("formSeleccionTituloRecontratacion").hide();
		self.abrirSeleccionTituloRecontratacionEnlaces("FP11013");
	} else {
		/* Cierro el popup si se abrio y limpio las variables que sirven para filtrar capítulos */
		self.cierrePopupValidarMasterTitulo();		
		
		/* Reseteo la variable almacenados del titulo seleccionado en FP11013 y FP11010/11 */
		self.contrato.tituloRecontratacionSeleccionado = null;
		self.contrato.tituloSeleccionado = null;
		
		self.getSelector("formSeleccionTituloRecontratacion").hide();
		/* Valido a donde debo regresar */
		if (self.contrato.montoSenialTT.titulosIngresados == self.contrato.grupoCC.cantidadOriginales) {
			/* Volver a FP11009 */
			self.abrirVisualizarGruposContrato();
		} else {
			/* Volver a FP11010/11 */
			self.getSelector("formVisualizarTitulosGrupo").show();
		}
	}
};

AltaContratoEvent.prototype.cierrePopupValidarMasterTitulo = function() {
	var self = altaContratoEvent;
	self.contrato.capitulosConfirmados = new Array();
	self.contrato.capitulosConfirmadosRM = new Array();
	$("#popupValidarMasterTitulo").dialog("close");
	$("#popupValidarMasterTitulo").dialog("remove");
};

AltaContratoEvent.prototype.volverSeleccionTituloRecontratacion = function() {
	var self = altaContratoEvent;
	self.getSelector("formSeleccionTituloRecontratacion").hide();
	self.getSelector("formVisualizarTitulosGrupo").show();
};

/***********************************************************************************		
 ********************				Pantalla FP11014			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirSeleccionCapitulosTituloRecontratacion = function(tituloSeleccionado) {
	var self = altaContratoEvent;
	Component.get("html/altaContrato/seleccionCapitulosTituloRecontratacion.html", function(comp) {
		self.drawSeleccionCapitulosTituloRecontratacion(comp, tituloSeleccionado);
	});
};

AltaContratoEvent.prototype.drawSeleccionCapitulosTituloRecontratacion = function(comp, tituloSeleccionado) {
	var self = altaContratoEvent;
	comp = comp.replace(/{{id}}/g, self.div.id);
	
	self.getSelector("formSeleccionTituloRecontratacion").hide();
	
	if (self.getSelector("formSeleccionCapitulosTituloRecontratacion").length) {
		self.getSelector("formSeleccionCapitulosTituloRecontratacion").replaceWith(comp);
	} else {
		self.getDiv().append(comp);
	}
	/* Accordion */
	Accordion.getInstance(self.getSelector("accordionSeleccionCapitulosTituloRecontratacion"));
	/* Botonera */
	self.getSelector("volverSeleccionCapitulosTituloRecontratacion").button().click(self.aceptarCapitulos);
	/* Datos grupo */
	self.getSelector("senialSeleccionCapitulosTituloRecontratacion").html(self.contrato.montoSenialTT.senial);
	self.getSelector("distribuidorSeleccionCapitulosTituloRecontratacion").html(self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("distribuidorSeleccionCapitulosTituloRecontratacion").attr("title", self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("montoSeleccionCapitulosTituloRecontratacion").html(self.contrato.montoSenialTT.monto);
	self.getSelector("nroGrupoSeleccionCapitulosTituloRecontratacion").html(self.contrato.grupoCC.nroGrupo);
	self.getSelector("tipoTituloSeleccionCapitulosTituloRecontratacion").html(self.contrato.montoSenialTT.tipoTitulo);
	self.getSelector("estrenoSeleccionCapitulosTituloRecontratacion").html(self.contrato.grupoCC.estrenoRepeticion == "E" ? "Estreno" : "Repetición");
	self.getSelector("nombreGrupoSeleccionCapitulosTituloRecontratacion").html(self.contrato.grupoCC.nombreGrupo);
	self.getSelector("claveSeleccionCapitulosTituloRecontratacion").html(tituloSeleccionado.clave);
	self.getSelector("formulaSeleccionCapitulosTituloRecontratacion").html(self.contrato.formula);
	self.drawGridSeleccionCapitulosTituloRecontratacion();
};

AltaContratoEvent.prototype.drawGridSeleccionCapitulosTituloRecontratacion = function() {
	var self = altaContratoEvent;
	
	self.getSelector("gridSeleccionCapitulosTituloRecontratacion").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Nro. de capítulo', 'Parte', 'Título - Capítulo', 'Contrato', ''],
		colModel:[ 
		    {name:'numeroCapitulo',			index:'numeroCapitulo',			align:'center'},
		    {name:'parte',					index:'parte',					align:'center'},
		    {name:'tituloCapitulo',			index:'tituloCapitulo',			align:'left', formatter:function(value, options, rData) {
    			return Component.trimWhitespace(rData['tituloCapitulo']);	
	    	}},
	    	{name:'sinContrato',			index:'sinContrato',			align:'center'},
		    {name:'seleccionarCapitulo',	index:'seleccionarCapitulo',	align:'center', width:20}
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + self.div.id + '_pagerSeleccionCapitulosTituloRecontratacion',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Capítulos',
		gridComplete: function() { 							
			var ids = $(this).jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var seleccionarCapitulo = "<div onclick='altaContratoEvent.validarRemitoSNCCapituloRecontratacion(" + ids[i] + ");' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-circle-check conTooltip' title='Seleccionar capítulo'></span></div>";
				$(this).jqGrid('setRowData', ids[i], { seleccionarCapitulo: seleccionarCapitulo });
			}
		}
	});
	
	/* Grilla con tamaño automático */
	Component.resizableGrid(self.getSelector("gridSeleccionCapitulosTituloRecontratacion"));
	
	var capitulos = self.buscarCapitulosTituloRecontratacion();
	self.cantCapConf = capitulos.length;
	self.getSelector("gridSeleccionCapitulosTituloRecontratacion").clearGridData().setGridParam({data: capitulos}).trigger('reloadGrid');
};

AltaContratoEvent.prototype.buscarCapitulosTituloRecontratacion = function() {
	var self = altaContratoEvent;
	
	var buscarCapitulosTituloRecontratacionRequest = {
		contrato: self.contrato.tituloRecontratacionSeleccionado.contrato, 
		grupo: self.contrato.tituloRecontratacionSeleccionado.grupo,
	    senial: self.contrato.montoSenialTT.senial,
	    clave: self.contrato.tituloRecontratacionSeleccionado.clave,
	    contratoPendiente: self.contrato.numero,
	    grupoPendiente: self.contrato.grupoCC.nroGrupo,
	    senialPendiente: self.contrato.montoSenialTT.senial
	};
	
	var capitulos = new Array();
	
	var data = {
		async: false,
		action:"altaContratoBuscarCapitulosTituloRecontratacion.action", 
		request: Component.serialize(buscarCapitulosTituloRecontratacionRequest, "buscarCapitulosTituloRecontratacionRequest"),  
		method: "GET", 
		responseObject: "capitulosTituloRecontratacion", 
		callback: function(capitulosTituloRecontratacion) {
			capitulos = capitulosTituloRecontratacion;
		}
	};
	self.service.doRequest(data);

	return capitulos;
};

/***********************************************************************************		
 **********		Pantalla FP11014 - Selección 'capitulo' - Validar remito  **********
 ***********************************************************************************/
AltaContratoEvent.prototype.validarRemitoSNCCapituloRecontratacion = function(idCapitulo) {
	var self = altaContratoEvent;
	
	self.contrato.capituloConfirmado = self.getSelector("gridSeleccionCapitulosTituloRecontratacion").jqGrid('getRowData', idCapitulo);

	var validarRemitoSNCCapituloRecontratacionRequest = {
		contrato: self.contrato.numero, 
		grupo: self.contrato.grupoCC.nroGrupo,
	    senial: self.contrato.montoSenialTT.senial,
	    proveedor: self.contrato.cabecera.proveedor,
	    clave: self.contrato.tituloRecontratacionSeleccionado.clave
	};
    
	var data = {
		async: false,
		action:"altaContratoValidarRemitoSNCCapituloRecontratacion.action", 
		request: Component.serialize(validarRemitoSNCCapituloRecontratacionRequest, "validarRemitoSNCCapituloRecontratacionRequest"),  
		method: "GET", 
		responseObject: "validarRemitoSNCCapituloRecontratacionResponse", 
		callback: function(validarRemitoSNCCapituloRecontratacionResponse) {
			if (validarRemitoSNCCapituloRecontratacionResponse && validarRemitoSNCCapituloRecontratacionResponse.length > 0) {
				/* Abrir popup con datos */
				self.abrirPopupValidarRemitoSNCCapituloRecontratacion(validarRemitoSNCCapituloRecontratacionResponse);
			} else {
				/* Llamar a validación de master de títulos */
				self.validarRemitoNoSNC();
			}
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.abrirPopupValidarRemitoSNCCapituloRecontratacion = function(validarRemitoSNCCapituloRecontratacionResponse) {
	var self = altaContratoEvent;
	Component.get("html/altaContrato/popupValidarRemitoSNC.html", function(comp) {
		self.drawPopupValidarRemitoSNCCapituloRecontratacion(comp, validarRemitoSNCCapituloRecontratacionResponse);
	});
};

AltaContratoEvent.prototype.drawPopupValidarRemitoSNCCapituloRecontratacion = function(comp, validarRemitoSNCCapituloRecontratacionResponse) {
	var self = altaContratoEvent;
	
	$("#popupValidarRemitoSNC").dialog({width: 900, show: 'blind', hide: 'blind', modal: true, autoOpen: false,
		buttons: {
			"Salir": function() {
				$(this).dialog("close");
			}
		}
	});
	$("#popupValidarRemitoSNC").empty().append(comp.replace(/{{id}}/g, self.div.id));
	$("#popupValidarRemitoSNC").dialog("open");
	
	self.getSelector("continuarValidarRemitoSNC").button().click(function() {
		self.cierrePopupValidarRemitoSNC();
		
		if (self.contrato.capituloConfirmadoRM) {
			self.abrirPopupConfirmarMasterCapituloRecontratacion();
		} else {
			self.confirmarSeleccionCapituloTituloRecontratacion();
		}
	});
	
	self.getSelector("gridValidarRemitoSNC").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Capítulo', 'Parte', 'Remito', 'Guía', 'Fórmula', 'Fecha ingreso', 'Fecha remito', 'Chqueo volver', ''],
		colModel:[ 
		    {name:'capitulo',		 index:'capitulo',			align:'center'},
		    {name:'parte',			 index:'parte',				align:'center'},
		    {name:'nroRemito',		 index:'nroRemito',			align:'center'},
		    {name:'nroGuia',		 index:'nroGuia',			align:'center'},
		    {name:'formula',		 index:'formula',			align:'center'},
		    {name:'fechaIngreso',	 index:'fechaIngreso',		align:'center',	formatter:'date', formatoptions:{newformat: 'd/m/Y'}},
		    {name:'fechaRemito',	 index:'fechaRemito',		align:'center',	formatter:'date', formatoptions:{newformat: 'd/m/Y'}},
		    {name:'chequeoVolver',	 index:'chequeoVolver',		align:'center'},
		    {name:'confirmarRemito', index:'confirmarRemito',	align:'center'}
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + self.div.id + '_pagerValidarRemitoSNC',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: '',
		gridComplete: function() { 							
			var ids = $(this).jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var confirmarRemito = "<div onclick='altaContratoEvent.confirmarRemitoValidadoSNCCapituloRecontratacion();' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-circle-check conTooltip' title='Confirmar remito'></span></div>";
				$(this).jqGrid('setRowData', ids[i], { confirmarRemito: confirmarRemito });
			}
		}
	});
	
	self.getSelector("gridValidarRemitoSNC").clearGridData().setGridParam({data: validarRemitoSNCCapituloRecontratacionResponse}).trigger('reloadGrid');
	/* Marca para determinar los datos a confirmar */
	self.contrato.capituloConfirmadoRM = false;
};

AltaContratoEvent.prototype.confirmarRemitoValidadoSNCCapituloRecontratacion = function() {
	var self = altaContratoEvent;
	self.contrato.capituloConfirmadoRM = true;
};

/***********************************************************************************		
 **********	  Pantalla FP11014 - Selección 'capitulo' - Confirmar master  **********
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirPopupConfirmarMasterCapituloRecontratacion = function() {
	var self = altaContratoEvent;
	Component.get("html/altaContrato/popupConfirmarMaster.html", self.drawPopupConfirmarMasterCapituloRecontratacion);
};

AltaContratoEvent.prototype.drawPopupConfirmarMasterCapituloRecontratacion = function(comp) {
	var self = altaContratoEvent;

	$("#popupConfirmarMaster").dialog({
		width: 900, show: 'blind', hide: 'blind', modal: true, autoOpen: false,
		buttons: {
			"Salir": function() {
				$(this).dialog("close");
			}
		}
	});
	$("#popupConfirmarMaster").empty().append(comp.replace(/{{id}}/g, self.div.id));
	$("#popupConfirmarMaster").dialog("open");
	
	self.getSelector("continuarConfirmarMaster").button().click(function() {
		self.cierrePopupConfirmarMaster();
		if (self.contrato.capituloConfirmadosRM) {
			self.validarRemitoNoSNC();	
		} else {
			self.confirmarSeleccionCapituloTituloRecontratacion();	
		}
	});
	
	self.getSelector("gridConfirmarMaster").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Capítulo', 'Parte', '', '', '', '', '', '', ''],
		colModel:[ 
		    {name:'capitulo',		 index:'capitulo',			align:'center'},
		    {name:'parte',			 index:'parte',				align:'center'},
		    {name:'confirmarMaster', index:'confirmarRemito',	align:'center'},
		    {name:'nroRemito',		 index:'nroRemito',			hidden: true},
		    {name:'nroGuia',		 index:'nroGuia',			hidden: true},
		    {name:'formula',		 index:'formula',			hidden: true},
		    {name:'fechaIngreso',	 index:'fechaIngreso',		hidden: true,	formatter:'date', formatoptions:{newformat: 'd/m/Y'}},
		    {name:'fechaRemito',	 index:'fechaRemito',		hidden: true,	formatter:'date', formatoptions:{newformat: 'd/m/Y'}},
		    {name:'chequeoVolver',	 index:'chequeoVolver',		hidden: true}
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + self.div.id + '_pagerConfirmarMaster',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: '',
		gridComplete: function() { 							
			var ids = $(this).jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var confirmarMaster = "<div onclick='altaContratoEvent.confirmarMasterCapituloRecontratacion()' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-circle-check conTooltip' title='Confirmar master'></span></div>";
				$(this).jqGrid('setRowData', ids[i], { confirmarMaster: confirmarMaster });
			}
		}
	});
	
	self.getSelector("gridConfirmarMaster").clearGridData().setGridParam({data: self.contrato.capitulosConfirmados}).trigger('reloadGrid');
	/* Marca para determinar los datos a confirmar */
	self.contrato.capituloConfirmadoRM = false;
};

/***********************************************************************************		
 **********		Pantalla FP11014 - Selección 'todos' - Validar master	  **********
 ***********************************************************************************/
AltaContratoEvent.prototype.validarRemitoNoSNC = function() {
	var self = altaContratoEvent;
	
	/* Valido si debo redirigir a confirmación */ 
	if (self.contrato.capituloConfirmadoRM) {
	    var validarRemitoNoSNCRequest = {
    	    clave: self.contrato.tituloRecontratacionSeleccionado.clave,
			senial: self.contrato.montoSenialTT.senial,
			capitulo: self.contrato.capituloConfirmado.capitulo,
		    parte: self.contrato.capituloConfirmado.parte,
		    proveedor: self.contrato.cabecera.proveedor
		};
	    
		var data = {
			async: false,
			action:"altaContratoValidarRemitoNoSNC.action", 
			request: Component.serialize(validarRemitoNoSNCRequest, "validarRemitoNoSNCRequest"),  
			method: "GET", 
			responseObject: "validarRemitoNoSNCResponse", 
			callback: function(validarRemitoNoSNCResponse) {
				if (validarRemitoNoSNCResponse  && validarRemitoNoSNCResponse.length > 0) {
					var params = {
						master: self.contrato.capituloConfirmado,
						validarRemitoNoSNCResponse: validarRemitoNoSNCResponse
					};
					self.abrirPopupValidarMasterTitulo(params);
				} else {
					self.confirmarSeleccionCapituloTituloRecontratacion();
				}
			}
		};
		self.service.doRequest(data);
	} else {
		self.confirmarSeleccionCapituloTituloRecontratacion();
	}
};

AltaContratoEvent.prototype.abrirPopupValidarRemitoNoSNC = function(params) {
	var self = altaContratoEvent;
	Component.get("html/altaContrato/popupValidarMasterTitulo.html", function(comp) {
		self.drawPopupValidarRemitoNoSNC(comp, params);
	});
};

AltaContratoEvent.prototype.drawPopupValidarRemitoNoSNC = function(comp, params) {
	var self = altaContratoEvent;
	
	$("#popupValidarMasterTitulo").dialog({ width: 900, show: 'blind', hide: 'blind', modal: true, autoOpen: false,
		buttons: {
			"Salir": function() {
				$(this).dialog("close");
			}
		}
	});
	$("#popupValidarMasterTitulo").empty().append(comp.replace(/{{id}}/g, self.div.id));
	$("#popupValidarMasterTitulo").dialog("open");
	
	/* Mostrar la leyenda: ‘Ingrese la fecha que se tomará como fecha de vigencia desde para el título’ */
	if (self.contrato.grupoCC.tipoDerecho == "R") {
		Datepicker.getInstance(self.getSelector("fechaCopia"), new Date());
		self.getSelector("divFechaCopiaValidarMasterTitulo").show();
	} else {
		self.getSelector("divFechaCopiaValidarMasterTitulo").hide();
	}
	
	self.getSelector("confirmarDatosValidarMasterTitulo").button().click(function() {
		self.confirmarDatosValidarRemitoNoSNC(true);
	});
	self.getSelector("rechazaDatosValidarMasterTitulo").button().click(function() {
		self.confirmarDatosValidarRemitoNoSNC(false);
	});
	
	self.getSelector("gridValidarMasterTitulo").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Soporte', 'Fecha copia'],
		colModel:[ 
		    {name:'soporte',		index:'soporte',		align:'center'},
		    {name:'fechaCopia',		index:'fechaCopia',		align:'center',	formatter:'date', formatoptions:{newformat: 'd/m/Y'}}
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + self.div.id + '_pagerValidarMasterTitulo',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: ''
	});
	
	self.getSelector("gridValidarMasterTitulo").clearGridData().setGridParam({data: params.validarRemitoNoSNCResponse}).trigger('reloadGrid');
};

AltaContratoEvent.prototype.confirmarDatosValidarRemitoNoSNC = function(confirma) {
	var self = altaContratoEvent;
	if (confirma) {
		self.contrato.capituloConfirmado.recCopia = 'S';
		if (self.contrato.grupoCC.tipoDerecho == "R") {
			self.contrato.capituloConfirmado.fechaCopia = self.getSelector("fechaCopia").val();
		} else {
			self.contrato.capituloConfirmado.fechaCopia = "01/01/0001";
		}
	} else {
		self.contrato.capituloConfirmado.recCopia = 'N';
		self.contrato.capituloConfirmado.fechaCopia = "01/01/0001";
	}
	self.confirmarSeleccionCapituloTituloRecontratacion();
};

/***********************************************************************************		
 **********			Pantalla FP11014 - Selección 'todos' - Confirmar	  **********
 ***********************************************************************************/
AltaContratoEvent.prototype.confirmarSeleccionCapituloTituloRecontratacion = function() {
	var self = altaContratoEvent;

    var confirmarSeleccionCapitulosRequest = {
		contrato: self.contrato.numero, 
		grupo: self.contrato.grupoCC.nroGrupo,
	    senial: self.contrato.montoSenialTT.senial,
        clave: self.contrato.tituloRecontratacionSeleccionado.clave,
        proveedor: self.contrato.cabecera.proveedor,
        capitulo: self.contrato.capituloConfirmado.capitulo == undefined ? self.contrato.capituloConfirmado.numeroCapitulo : self.contrato.capituloConfirmado.capitulo,
        parte: self.contrato.capituloConfirmado.parte,
        recCopia: (self.contrato.capituloConfirmado.recCopia == undefined ? "N": self.contrato.capituloConfirmado.recCopia),
        fechaCopia: (self.contrato.capituloConfirmado.fechaCopia == undefined ? "01/01/0001" : self.contrato.capituloConfirmado.fechaCopia)
	};
    
	var data = {
		async: false,
		action:"altaContratoConfirmarSeleccionCapituloTituloRecontratacion.action", 
		request: Component.serialize(confirmarSeleccionCapitulosRequest, "confirmarSeleccionCapitulosRequest"),  
		method: "POST", 
		responseObject: "confirmarSeleccionCapitulosResponse", 
		callback: function(confirmarSeleccionCapitulosResponse) {
			/* Incremento el contador de titulos ingresados */
			self.contrato.montoSenialTT.titulosIngresados++;
			self.cierrePopupValidarMasterTitulo();
			self.seleccionoAlgunCapitulo = true;
			self.ultimoRerun = confirmarSeleccionCapitulosResponse.rerun;
			/* Marca para Ir a FP11015 */
			if (confirmarSeleccionCapitulosResponse.rerun) {
				
				if (self.contrato.montoSenialTT.titulosIngresados == self.contrato.grupoCC.cantidadOriginales || self.contrato.montoSenialTT.titulosIngresados == self.contrato.CantCapitulosRS){
					/* Ir a FP11015 */
					self.getSelector("formSeleccionCapitulosTituloRecontratacion").hide();
					self.abrirSeleccionTituloRecontratacionEnlaces("FP11014");
				}else {
					/* Volver a FP11010/11 */
					self.volverSeleccionCapitulosTituloRecontratacion();
				}
			} else {
				self.cierrePopupValidarMasterTitulo();
				
				if (self.contrato.montoSenialTT.titulosIngresados == self.contrato.grupoCC.cantidadOriginales || self.contrato.montoSenialTT.titulosIngresados == self.contrato.CantCapitulosRS) {
					/* FP11009 */
					self.volverSeleccionCapituloTituloRecontratacion();
				} else {
					/* Volver a FP11010/11 */
					self.volverSeleccionCapitulosTituloRecontratacion();
				}
			}
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.volverSeleccionCapitulosTituloRecontratacion = function() {
	var self = altaContratoEvent;
	self.contrato.capituloConfirmado = null;
	self.contrato.capituloConfirmadoRM = null;
	self.abrirSeleccionCapitulosTituloRecontratacion(self.contrato.tituloRecontratacionSeleccionado);
};

AltaContratoEvent.prototype.volverSeleccionCapituloTituloRecontratacion = function() {
	var self = altaContratoEvent;
	self.contrato.capituloConfirmado = null;
	self.contrato.capituloConfirmadoRM = null;
	self.contrato.tituloRecontratacionSeleccionado = null;
	self.getSelector("formSeleccionCapitulosTituloRecontratacion").hide();
	self.abrirVisualizarGruposContrato();
};

AltaContratoEvent.prototype.recontratarTituloTodosLosCapitulosNoConfirmados = function() {
var self = altaContratoEvent;
	
	var esTituloCC = self.determinarFamiliaTitulo(self.contrato.montoSenialTT.tipoTitulo);
	
	var rerun = {};
	
	var params = self.obtenerDatosRemito();
	
	var confirmarSeleccionTituloRequest = {
		contrato: self.contrato.numero,
		grupo: (esTituloCC ? self.contrato.grupoCC.nroGrupo : self.contrato.grupoSC.nroGrupo),
		senial: self.contrato.montoSenialTT.senial,
		clave: self.contrato.tituloSeleccionado.origen == "F" ? null : self.contrato.tituloSeleccionado.clave,
		proveedor: self.contrato.cabecera.proveedor,
		origen: self.contrato.tituloSeleccionado.origen,
		marPerm: (esTituloCC ? self.contrato.grupoCC.marcaPerpetuidad : self.contrato.grupoSC.marcaPerpetuidad),
		tipoDerecho: (esTituloCC ? self.contrato.grupoCC.tipoDerecho : self.contrato.grupoSC.tipoDerecho),
		chequeo: params.datosRemito.chequeo,
		tituloCastSinContrato: (self.contrato.tituloSeleccionado.sinContrato != ""  ? "S" : "N"),
		tituloOrigSinContrato: (self.contrato.tituloSeleccionado.sinContrato != ""  ? "S" : "N"),
		recOrig: (params.recOrig ? "S" : "N"),
		recCopia: (params.recCopia ? "S" : "N"),
		fechaCopia: params.datosRemito.fechaCopia,
		nroRtoInterno: params.datosRemito.nroRemito,
		fechaRem: params.datosRemito.fechaRtoGuia,
		tituloOriginal: self.contrato.tituloSeleccionado.titulo,
		tituloCastellano: self.contrato.tituloSeleccionado.tituloAlternativo,
		claveFichaCinematografica: self.contrato.tituloSeleccionado.origen == "F" ? self.contrato.tituloSeleccionado.clave : 0
	};	
	
	var data = {
		async: false,
		action:"altaContratoConfirmarSeleccionTituloRecontratacion.action", 
		request: Component.serialize(confirmarSeleccionTituloRequest, "confirmarSeleccionTituloRequest"), 
		method: "POST", 
		responseObject: "confirmarSeleccionTituloResponse", 
		callback: function(confirmarSeleccionTituloResponse) {
			rerun = {
					valor : confirmarSeleccionTituloResponse.rerun == 'S' ? true : false
			};
			if (confirmarSeleccionTituloResponse.tipoError && confirmarSeleccionTituloResponse.tipoError == "W") {	
				MESSAGE.alert(confirmarSeleccionTituloResponse.descripcion);
			} 
		}
	};
	self.service.doRequest(data);
	return rerun.valor;
};
AltaContratoEvent.prototype.obtenerDatosRemito = function() {
	 	
	var self = altaContratoEvent;
	var	params = {};
	var validacionRecepcionMasterRequest = {
		contrato: self.contrato.numero,
		grupo: self.contrato.grupoCC.nroGrupo ,
		senial: self.contrato.montoSenialTT.senial,
		tipoDerecho : self.contrato.grupoCC.tipoDerecho,
		clave: self.contrato.tituloSeleccionado.clave	
	};
	
	var data = {
		async: false,
		action:"altaContratoValidacionRecepcionMaster.action", 
		request: Component.serialize(validacionRecepcionMasterRequest, "validacionRecepcionMasterRequest"), 
		method: "GET", 
		responseObject: "validacionRecepcionMasterResponse", 
		callback: function(validacionRecepcionMasterResponse) {

			params = {
				error : validacionRecepcionMasterResponse.errores,
				datosRemito : validacionRecepcionMasterResponse.datosRemito[0],
				grillaRemito : validacionRecepcionMasterResponse.datosRemito,
				grillaSoporte : validacionRecepcionMasterResponse.datosSoporte,
				estaEnSNC : validacionRecepcionMasterResponse.estaEnSNC,
				recOrig : validacionRecepcionMasterResponse.recOrig,
				recCopia : validacionRecepcionMasterResponse.recCopia
			};
			
		}
	};
	
	self.service.doRequest(data);
	
	return params;
};

AltaContratoEvent.prototype.aceptarCapitulos = function() {
	var self = altaContratoEvent;
	if (self.cantCapConf < 1) {
		self.ultimoRerun = self.recontratarTituloTodosLosCapitulosNoConfirmados();
		self.seleccionoAlgunCapitulo = true;
	}
	
	if(self.seleccionoAlgunCapitulo) {
		if (self.ultimoRerun){
			//se va a la pantalla de rerun
			self.getSelector("formSeleccionCapitulosTituloRecontratacion").hide();
			self.abrirSeleccionTituloRecontratacionEnlaces("FP11014");
		}
		else {
			self.cierrePopupValidarMasterTitulo();
			/* FP11009 */
			self.volverSeleccionCapituloTituloRecontratacion();
		}
	} else {
		//vuelve a la pantalla de contratos
		self.getSelector("formSeleccionCapitulosTituloRecontratacion").hide();
		self.abrirVisualizarTitulosGrupo();
	}
			
		
	
};

