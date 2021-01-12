/***********************************************************************************		
 ********************				Pantalla FP11005			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirCargaDatosGrupoCC = function(tipoTitulo) {
	BLOCK.showBlock("Cargando datos del grupo");
	
	var self = altaContratoEvent;
	self.cierrePopupTipoTituloGrupo();
	self.contrato.montoSenialTT.tipoTitulo = tipoTitulo;
	
	setTimeout(function() {
		Component.get("html/altaContrato/cargaDatosGrupoCC.html", self.drawCargaDatosGrupoCC);
	}, 750);
};

AltaContratoEvent.prototype.drawCargaDatosGrupoCC = function(comp) {
	var self = altaContratoEvent;

	self.getSelector("formCargaContrato").hide();
	comp = comp.replace(/{{id}}/g, self.div.id);
	
	if (self.getSelector("formCargaGrupoCC").length) {
		self.getSelector("formCargaGrupoCC").replaceWith(comp);
	} else {
		self.getDiv().append(comp);
	}
	/* Accordion */
	Accordion.getInstance(self.getSelector("accordionCargaGrupoCC"));
	
	/* Botonera */
	self.getSelector("volverDatosCargaGrupoCC").button().click(self.volverDatosCargaGrupoCC);
	self.getSelector("aceptarDatosCargaGrupoCC").button().click(function() {
		self.validarDatosGrupoCC(self.validarAmortizacionCC);
	});
	
	/* Cabecera */
	self.getSelector("senialCargaGrupoCC").html(self.contrato.montoSenialTT.senial);
	self.getSelector("distribuidorCargaGrupoCC").html(self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("distribuidorCargaGrupoCC").attr("title", self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("montoTotalCargaGrupoCC").html(self.contrato.montoSenialTT.monto);
	self.obtenerProximoNumeroGrupo();
	self.getSelector("tipoTituloCargaGrupoCC").html(self.contrato.montoSenialTT.tipoTitulo);
	
	/* Situar señales heredadas */
	self.situarSenialesHeredadasCC();
	/* Cantidad originales */
	self.getSelector("cantidadOriginalesCargaGrupoCC").numeric();
	/* Cantidad repeticiones */
	self.getSelector("cantidadRepeticionesCargaGrupoCC").numeric();
	/* Situar tipos importe */
	self.buscarTiposImporte("tipoImporteCargaGrupoCC");
	/* Costo total */
	self.getSelector("costoTotalCargaGrupoCC").autoNumeric({vMax: '999999999.99'});
	/* Estreno / Repetición */
	Component.populateSelect(self.getSelector("estrenoRepeticionCargaGrupoCC"), AltaContratoEvent.selectER, "codigo", "descripcion", false);
	self.getSelector("estrenoRepeticionCargaGrupoCC")[0].removeChild(self.getSelector("estrenoRepeticionCargaGrupoCC")[0].options[0]);
	/* Fecha comienzo derechos territoriales */
	Datepicker.getInstance(self.getSelector("fechaComienzoDerechosCargaGrupoCC"));
	/* Fecha posible entrega */
	Datepicker.getInstance(self.getSelector("fechaPosibleEntregaCargaGrupoCC"));
	/* Situar tipos derecho */
	self.buscarTiposDerecho("codigoComienzoDerechosCargaGrupoCC");
	/* Cantidad tiempo */
	self.getSelector("cantidadTiempoCargaGrupoCC").numeric();
	/* Unidad tiempo: debe tener la opcion 'vacio' */
	Component.populateSelect(self.getSelector("codigoUnidadTiempoCargaGrupoCC"), AltaContratoEvent.selectUnidadTiempo, "codigo", "descripcion", false);
	/* Situar tipos derecho territorial */
	self.buscarTiposDerechoTerritorial("codigoDerechosTerritorialesCargaGrupoCC");
	/* Marca de perpetuidad */
	Component.populateSelect(self.getSelector("marcaPerpetuidadCargaGrupoCC"), AltaContratoEvent.selectSN, "codigo", "descripcion", false);
	self.getSelector("marcaPerpetuidadCargaGrupoCC")[0].removeChild(self.getSelector("marcaPerpetuidadCargaGrupoCC")[0].options[0]);
	self.getSelector("marcaPerpetuidadCargaGrupoCC").val("N");
	/* Recontratación */
	Component.populateSelect(self.getSelector("recontratacionCargaGrupoCC"), AltaContratoEvent.selectSN, "codigo", "descripcion", false);
	self.getSelector("recontratacionCargaGrupoCC")[0].removeChild(self.getSelector("recontratacionCargaGrupoCC")[0].options[0]);
	self.getSelector("recontratacionCargaGrupoCC").val("N");
	/* Cantidad tiempo exclusividad */
	self.getSelector("cantidadTiempoExclusividadCargaGrupoCC").numeric();
	/* Unidad tiempo exclusividad: debe tener la opción 'vacio' */
	Component.populateSelect(self.getSelector("unidadTiempoExclusividadCargaGrupoCC"), AltaContratoEvent.selectUnidadTiempo, "codigo", "descripcion", false);
	/* Autorización autor: debe tener la opción 'vacio' */
	Component.populateSelect(self.getSelector("autorizacionAutorCargaGrupoCC"), AltaContratoEvent.selectSN, "codigo", "descripcion", false);
	/* Paga argentores: debe tener la opción 'vacio' */
	Component.populateSelect(self.getSelector("pagaArgentoresCargaGrupoCC"), AltaContratoEvent.selectSN, "codigo", "descripcion", false);
	/* Pasa librería */
	Component.populateSelect(self.getSelector("pasaLibreriaCargaGrupoCC"), AltaContratoEvent.selectSN, "codigo", "descripcion", false);
	self.getSelector("pasaLibreriaCargaGrupoCC")[0].removeChild(self.getSelector("pasaLibreriaCargaGrupoCC")[0].options[0]);
	self.getSelector("pasaLibreriaCargaGrupoCC").val("N");
	/* Fecha comienzo derechos librería */
	Datepicker.getInstance(self.getSelector("fechaComienzoDerechosLibreriaCargaGrupoCC"));
	
	/* Desbloquear pantalla */
	BLOCK.hideBlock();
};

AltaContratoEvent.prototype.situarSenialesHeredadasCC = function() {
	var self = altaContratoEvent;
	
	var buscarSenialesHeredadasRequest = {
		senial: self.contrato.montoSenialTT.senial
	};
	
	var data = {
		async: false,
		action:"altaContratoBuscarSenialHeredadaCC.action", 
		request: Component.serialize(buscarSenialesHeredadasRequest, "buscarSenialesHeredadasRequest"), 
		method: "GET", 
		responseObject: "senialHeredada", 
		callback: function(senialHeredada) {
			if (senialHeredada) {
				self.getSelector("cargarSenialesHeredadasCargaGrupoCC").show();
				self.getSelector("senialesHeredadasCargaGrupoCC").html(senialHeredada);
				altaContratoEvent.validarAsigacionSenialHeredadaCC(senialHeredada);
				self.getSelector("cargarSenialesHeredadasCargaGrupoCC").click(self.abrirCargaSenialesHeredadas);				
			} else {
				self.getSelector("cargarSenialesHeredadasCargaGrupoCC").hide();
			}
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.validarDatosGrupoCC = function(callback) {
	var self = altaContratoEvent;
	
	/* Validar ingreso de nombre de grupo */
	if (Validator.isEmpty(self.getSelector("nombreGrupoCargaGrupoCC"))) {
		Validator.focus(self.getSelector("nombreGrupoCargaGrupoCC"), "Debe ingresar un nombre para el grupo");
		return;
	}
	/* Validar ingreso cantidad de originales */
	if (Validator.isEmpty(self.getSelector("cantidadOriginalesCargaGrupoCC"))) {
		Validator.focus(self.getSelector("cantidadOriginalesCargaGrupoCC"), "Debe ingresar la cantidad de originales");
		return;
	} 
	/* Validar ingreso cantidad de repeticiones: si marca perpetuidad es 'S' no debe cargarse este campo. */
	if (!Validator.isEmpty(self.getSelector("cantidadRepeticionesCargaGrupoCC")) && self.getSelector("marcaPerpetuidadCargaGrupoCC").val() == "S") {
		Validator.focus(self.getSelector("cantidadRepeticionesCargaGrupoCC"), "No debe ingresar la cantidad de repeticiones");
		return;
	} else if (Validator.isEmpty(self.getSelector("cantidadRepeticionesCargaGrupoCC")) && self.getSelector("marcaPerpetuidadCargaGrupoCC").val() == "N"){
		Validator.focus(self.getSelector("cantidadRepeticionesCargaGrupoCC"), "Debe ingresar la cantidad de repeticiones");
		return;
	}
	/* Validar ingreso tipo importe: para tipo importe FIJ o VAS, debe ingresar el costo total */
	if (Validator.isEmpty(self.getSelector("tipoImporteCargaGrupoCC"))){
       Validator.focus(self.getSelector("tipoImporteCargaGrupoCC"), "Debe ingresar un tipo de importe para el grupo");
       return;
	}
	if ((self.getSelector("tipoImporteCargaGrupoCC").val() == "FIJ" || self.getSelector("tipoImporteCargaGrupoCC").val() == "VAS") && (Validator.isEmpty(self.getSelector("costoTotalCargaGrupoCC")) || self.getSelector("costoTotalCargaGrupoCC").val() == 0)) {
		Validator.focus(self.getSelector("costoTotalCargaGrupoCC"), "Debe ingresar el costo total");
		return;
	}
	/* Validar estreno/repetición: si es recontratación = ‘S’ debe ser ‘R’ */
	if (self.getSelector("estrenoRepeticionCargaGrupoCC").val() != "R" && self.getSelector("recontratacionCargaGrupoCC").val() == "S") {
		Validator.focus(self.getSelector("estrenoRepeticionCargaGrupoCC"), "La marca estreno/repetición debe ser R");
		return;
	}
	/* Validar ingreso comienzo derechos: obligatorio si el código de comienzo de derechos es ‘F’. 
	 * Debe estar en blanco si el código de comienzo de derechos es ‘R’ o ‘C’*/
	if (Validator.isEmpty(self.getSelector("fechaComienzoDerechosCargaGrupoCC")) && self.getSelector("codigoComienzoDerechosCargaGrupoCC").val() == "F") {
		Validator.focus(self.getSelector("fechaComienzoDerechosCargaGrupoCC"), "Debe ingresar una fecha de comienzo de derechos");
		return;
	} else if (!Validator.isEmpty(self.getSelector("fechaComienzoDerechosCargaGrupoCC")) && self.getSelector("codigoComienzoDerechosCargaGrupoCC").val() != "F") {
		Validator.focus(self.getSelector("fechaComienzoDerechosCargaGrupoCC"), "No debe ingresar una fecha de comienzo de derechos");
		return;
	}
	/* Validar ingreso tipos de derecho */
	if (Validator.isEmpty(self.getSelector("codigoComienzoDerechosCargaGrupoCC"))) {
		Validator.focus(self.getSelector("codigoComienzoDerechosCargaGrupoCC"), "Código de comienzo de derecho no existente");
		return;
	}
	/* Validar ingreso cantidad de tiempo */
	if (Validator.isEmpty(self.getSelector("cantidadTiempoCargaGrupoCC")) && self.getSelector("marcaPerpetuidadCargaGrupoCC").val() == "N") {
		Validator.focus(self.getSelector("cantidadTiempoCargaGrupoCC"), "Debe ingresar la cantidad de tiempo");
		return;
	} else if (!Validator.isEmpty(self.getSelector("cantidadTiempoCargaGrupoCC")) && self.getSelector("marcaPerpetuidadCargaGrupoCC").val() != "N") {
		Validator.focus(self.getSelector("cantidadTiempoCargaGrupoCC"), "No debe ingresar la cantidad de tiempo");
		return;
	}
	/* Validar ingreso unidad de tiempo */
	if (Validator.isEmpty(self.getSelector("codigoUnidadTiempoCargaGrupoCC")) && self.getSelector("marcaPerpetuidadCargaGrupoCC").val() == "N") {
		Validator.focus(self.getSelector("codigoUnidadTiempoCargaGrupoCC"), "La unidad de tiempo no puede ser nula");
		return;
	} else if (!Validator.isEmpty(self.getSelector("codigoUnidadTiempoCargaGrupoCC")) && self.getSelector("marcaPerpetuidadCargaGrupoCC").val() != "N") {
		Validator.focus(self.getSelector("codigoUnidadTiempoCargaGrupoCC"), "No debe ingresar la unidad de tiempo no puede ser nula");
		return;
	}
	/* Validar ingreso tipos de derecho territorial */
	if (Validator.isEmpty(self.getSelector("codigoDerechosTerritorialesCargaGrupoCC"))) {
		Validator.focus(self.getSelector("codigoDerechosTerritorialesCargaGrupoCC"), "Código de derecho territorial no existente");
		return;
	}
	/* Validar ingreso cantidad tiempo exclusividad */
	if (!Validator.isEmpty(self.getSelector("cantidadTiempoExclusividadCargaGrupoCC")) && Validator.isEmpty(self.getSelector("unidadTiempoExclusividadCargaGrupoCC"))) {
		Validator.focus(self.getSelector("unidadTiempoExclusividadCargaGrupoCC"), "Debe ingresar unidad de tiempo de exclusividad");
		return;
	}
	/* Validar ingreso unidad tiempo exclusividad */
	if (Validator.isEmpty(self.getSelector("cantidadTiempoExclusividadCargaGrupoCC")) && !Validator.isEmpty(self.getSelector("unidadTiempoExclusividadCargaGrupoCC"))) {
		Validator.focus(self.getSelector("cantidadTiempoExclusividadCargaGrupoCC"), "Debe ingresar la cantidad de tiempo de exclusividad");
		return;
	}
	/* TODO: Validar ingreso pasa a librería (ValidaPasaLibrería PL) & fecha comienzo libreria : Debe estar bloqueado si se encuentra en el parámetro general ‘NPL’ en 
	 * PT6001 la concatenación de GR6001.SnkCod con GR6001.GRkTpoTit */
//	if (self.getSelector("pasaLibreriaCargaGrupoCC").val() == "S" && self.getSelector("marcaPerpetuidadCargaGrupoCC").val() == "N") {
//		Validator.focus(self.getSelector("cantidadRepeticionesCargaGrupoCC"), "cantidadRepeticionesCargaGrupoCC");
//		return;
//	}
	
	callback();
};

AltaContratoEvent.prototype.validarRecontratacion = function() {
	var self = altaContratoEvent;
	
	var validarRecontratacionRequest = {
		contrato: self.contrato.numero,
		grupo: self.contrato.grupoCC.nroGrupo,
		senial: self.contrato.montoSenialTT.senial
	};
	
	var recontratacionValida = new Boolean();
	var data = {
		async: false,
		action:"altaContratoValidarRecontratacion.action", 
		request: Component.serialize(validarRecontratacionRequest, "validarRecontratacionRequest"), 
		method: "GET", 
		responseObject: "resultado", 
		callback: function(exito) {
			recontratacionValida = exito;
		}
	};
	self.service.doRequest(data);
	
	return recontratacionValida;
};

AltaContratoEvent.prototype.validarAmortizacionCC = function() {
	var self = altaContratoEvent;
	
	var validarAmortizacionRequest = {
		contrato: self.contrato.numero,
		grupo: self.contrato.grupoCC.nroGrupo,
		senial: self.contrato.montoSenialTT.senial,
		tipoTitulo: self.contrato.montoSenialTT.tipoTitulo,
		pasaLibreria: self.getSelector("pasaLibreriaCargaGrupoCC").val(),
		marcaPerpetuidad: self.getSelector("marcaPerpetuidadCargaGrupoCC").val(),
		cantidadOriginales: self.getSelector("cantidadOriginalesCargaGrupoCC").val(),
		cantidadPasadasContratadas: self.getSelector("cantidadRepeticionesCargaGrupoCC").val()
	};
	
	var data = {
		async: false,
		action:"altaContratoValidarAmortizacionCC.action", 
		request: Component.serialize(validarAmortizacionRequest, "validarAmortizacionRequest"), 
		method: "GET", 
		responseObject: "validarAmortizacionResponse", 
		callback: function(validarAmortizacionResponse) {
			if (validarAmortizacionResponse.exito) {
				/* Abrir FP11095 */
				self.abrirPopupFormulasAmortizacion(validarAmortizacionResponse);
			} else {
				MESSAGE.alert(validarAmortizacionResponse.mensaje);
			};
		}
	};
	
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.abrirPopupFormulasAmortizacion = function(validarAmortizacionResponse) {
	var self = altaContratoEvent;
	Component.get("html/altaContrato/popupFormulasAmortizacion.html", function(comp) {
		self.drawPopupFormulasAmortizacion(comp, validarAmortizacionResponse);
	});
};

AltaContratoEvent.prototype.drawPopupFormulasAmortizacion = function(comp, validarAmortizacionResponse) {
	var self = altaContratoEvent;
	
	$("#popupFormulasAmortizacion").dialog({
		width: 600, show: 'blind', hide: 'blind', modal: true, autoOpen: false,
		buttons: {
			"Salir": function() {
				$(this).dialog("close");
			}
		}
	});
	$("#popupFormulasAmortizacion").empty().append(comp.replace(/{{id}}/g, self.div.id));
	
	var esTituloCC = self.determinarFamiliaTitulo(self.contrato.montoSenialTT.tipoTitulo);
	if (esTituloCC) {
		self.contrato.grupoCC.primerIdAmortizacion = validarAmortizacionResponse.primerIdAmortizacion;
		self.contrato.grupoCC.segundoIdAmortizacion = validarAmortizacionResponse.segundoIdAmortizacion;
		self.getSelector("confirmarDatosCargaGrupo").button().click(function() {
			self.altaGrupoCC();
			$("#popupFormulasAmortizacion").dialog("close");
			$("#popupFormulasAmortizacion").dialog("remove");
		});
	} else {
		self.contrato.grupoSC.primerIdAmortizacion = validarAmortizacionResponse.primerIdAmortizacion;
		self.contrato.grupoSC.segundoIdAmortizacion = validarAmortizacionResponse.segundoIdAmortizacion;
		self.getSelector("confirmarDatosCargaGrupo").button().click(function() {
			self.altaGrupoSC();
			$("#popupFormulasAmortizacion").dialog("close");
			$("#popupFormulasAmortizacion").dialog("remove");
		});
	}
	self.getSelector("primerFormulaAmortizacion").html(validarAmortizacionResponse.descripcionPrimerIdAmortizacion);
	self.getSelector("segundaFormulaAmortizacion").html(validarAmortizacionResponse.descripcionSegundoIdAmortizacion);
	self.getSelector("primerIdAmortizacion").html("Id de Amortizacion: " + validarAmortizacionResponse.primerIdAmortizacion);
	
	$("#popupFormulasAmortizacion").dialog("open");
};

AltaContratoEvent.prototype.altaGrupoCC = function() {
	var self = altaContratoEvent;
	
	/* Datos que se obtienen de acciones pasadas */
	self.contrato.grupoCC.contrato = self.contrato.numero;
	self.contrato.grupoCC.senial = self.contrato.montoSenialTT.senial;
	self.contrato.grupoCC.tipoTitulo = self.contrato.montoSenialTT.tipoTitulo;
	
	/* Datos ingresados por pantalla */
	self.contrato.grupoCC.tipoDerecho = self.getSelector("codigoComienzoDerechosCargaGrupoCC").val();
	self.contrato.grupoCC.derechoTerrirorial = self.getSelector("codigoDerechosTerritorialesCargaGrupoCC").val();
	self.contrato.grupoCC.tipoImporte = self.getSelector("tipoImporteCargaGrupoCC").val();
	self.contrato.grupoCC.nombreGrupo = self.getSelector("nombreGrupoCargaGrupoCC").val().toUpperCase();
	self.contrato.grupoCC.cantidadOriginales = self.getSelector("cantidadOriginalesCargaGrupoCC").val();
	self.contrato.grupoCC.cantidadRepeticiones = ((self.getSelector("cantidadRepeticionesCargaGrupoCC").val() != null && self.getSelector("cantidadRepeticionesCargaGrupoCC").val() != "") ? self.getSelector("cantidadRepeticionesCargaGrupoCC").val() : 0);
	self.contrato.grupoCC.marcaPerpetuidad = self.getSelector("marcaPerpetuidadCargaGrupoCC").val();
	self.contrato.grupoCC.estrenoRepeticion = self.getSelector("estrenoRepeticionCargaGrupoCC").val();
	self.contrato.grupoCC.costoTotal = self.getSelector("costoTotalCargaGrupoCC").autoNumericGet();
	self.contrato.grupoCC.autorizacionAutor = ((self.getSelector("autorizacionAutorCargaGrupoCC").val() != null && self.getSelector("autorizacionAutorCargaGrupoCC").val() != "") ? self.getSelector("autorizacionAutorCargaGrupoCC").val() : " ");
	self.contrato.grupoCC.fechaComienzoDerechos = ((self.getSelector("fechaComienzoDerechosCargaGrupoCC").val() != null && self.getSelector("fechaComienzoDerechosCargaGrupoCC").val() != "") ? self.getSelector("fechaComienzoDerechosCargaGrupoCC").val() : "01/01/0001");
	self.contrato.grupoCC.cantidadTiempo = ((self.getSelector("cantidadTiempoCargaGrupoCC").val() != null && self.getSelector("cantidadTiempoCargaGrupoCC").val() != "") ? self.getSelector("cantidadTiempoCargaGrupoCC").val() : 0); 
	self.contrato.grupoCC.unidadTiempo = ((self.getSelector("codigoUnidadTiempoCargaGrupoCC").val() != null && self.getSelector("codigoUnidadTiempoCargaGrupoCC").val() != "") ? self.getSelector("codigoUnidadTiempoCargaGrupoCC").val() : " "); 
	self.contrato.grupoCC.planEntrega = ((self.getSelector("planEntregaCargaGrupoCC").val() != null && self.getSelector("planEntregaCargaGrupoCC").val() != "") ? self.getSelector("planEntregaCargaGrupoCC").val() : " ");
	self.contrato.grupoCC.pasaLibreria = self.getSelector("pasaLibreriaCargaGrupoCC").val();
	self.contrato.grupoCC.fechaComienzoDerechosLibreria = ((self.getSelector("fechaComienzoDerechosLibreriaCargaGrupoCC").val() != null && self.getSelector("fechaComienzoDerechosLibreriaCargaGrupoCC").val() != "") ? self.getSelector("fechaComienzoDerechosLibreriaCargaGrupoCC").val() : "01/01/0001");
	self.contrato.grupoCC.fechaPosibleEntrega = ((self.getSelector("fechaPosibleEntregaCargaGrupoCC").val() != null && self.getSelector("fechaPosibleEntregaCargaGrupoCC").val() != "") ? self.getSelector("fechaPosibleEntregaCargaGrupoCC").val() : "01/01/0001");
	self.contrato.grupoCC.cantidadTiempoExclusividad = ((self.getSelector("cantidadTiempoExclusividadCargaGrupoCC").val() != null && self.getSelector("cantidadTiempoExclusividadCargaGrupoCC").val() != "") ? self.getSelector("cantidadTiempoExclusividadCargaGrupoCC").val() : 0);
	self.contrato.grupoCC.unidadTiempoExclusividad = ((self.getSelector("unidadTiempoExclusividadCargaGrupoCC").val() != null && self.getSelector("unidadTiempoExclusividadCargaGrupoCC").val() != "") ? self.getSelector("unidadTiempoExclusividadCargaGrupoCC").val() : " ");
	self.contrato.grupoCC.recontratacion = ((self.getSelector("recontratacionCargaGrupoCC").val() != null && self.getSelector("recontratacionCargaGrupoCC").val() != "") ? self.getSelector("recontratacionCargaGrupoCC").val() : " ");
	self.contrato.grupoCC.pagaArgentores = ((self.getSelector("pagaArgentoresCargaGrupoCC").val() != null && self.getSelector("pagaArgentoresCargaGrupoCC").val() != "") ? self.getSelector("pagaArgentoresCargaGrupoCC").val() : " ");
	
	var data = {
		async: false,	
		action:"altaContratoAltaGrupoCC.action", 
		request: Component.serialize(self.contrato.grupoCC, "altaGrupoCCRequest"), 
		method: "POST", 
		responseObject: "resultado", 
		callback: function(exito) {
			if (exito) {
				MESSAGE.ok("El grupo se creó con éxito");
				self.abrirVisualizarTitulosGrupo();
			} else {
				MESSAGE.error("No se pudo dar de alta el grupo");
			};
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.volverDatosCargaGrupoCC = function() {
	var self = altaContratoEvent;
	//generamos los parametros para poder llamar al popUp Seleccion Tipo Titulo Grupo
	var params = { senial: self.contrato.montoSenialTT.senial, importe : self.contrato.montoSenialTT.monto };
	/* Limpio los datos del grupo y del monto, señal y tipo titulo con el que se estaban trabajando */
	self.contrato.montoSenialTT = new MontoSenialTTPOJO();
	self.contrato.grupoCC = new GrupoCCPOJO();
	self.getSelector("formCargaGrupoCC").hide();
	self.getSelector("formCargaContrato").show();
	self.abrirSeleccionTipoTituloGrupo(params);
};

/***********************************************************************************		
 ********************	Pantalla FP11009 a FP11005 - Visualizar	********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirLecturaDatosGrupoCC = function(tipoTitulo) {
	var self = altaContratoEvent;
	self.contrato.montoSenialTT.tipoTitulo = tipoTitulo;
	var grupo = self.recuperarDatosGrupo(self.contrato.grupoCC.nroGrupo);
	Component.get("html/altaContrato/lecturaDatosGrupoCC.html", function(comp) {
		self.drawLecturaDatosGrupoCC(comp, grupo);
	});
};

AltaContratoEvent.prototype.drawLecturaDatosGrupoCC = function(comp, grupo) {
	var self = altaContratoEvent;

	self.getSelector("formVisualizarGruposContrato").hide();
	comp = comp.replace(/{{id}}/g, self.div.id);
	
	if (self.getSelector("formLecturaDatosGrupoCC").length) {
		self.getSelector("formLecturaDatosGrupoCC").replaceWith(comp);
	} else {
		self.getDiv().append(comp);
	}
	/* Accordion */
	Accordion.getInstance(self.getSelector("accordionLecturaDatosGrupoCC"));
	/* Botonera */
	self.getSelector("volverLecturaDatosGrupoCC").button().click(self.volverLecturaDatosGrupoCC);
	self.getSelector("eliminarGrupoLecturaDatosGrupoCC").button().click(function() {
		self.eliminarGrupoContrato(self.contrato.montoSenialTT.tipoTitulo);
	});
	/* Seteo de datos */
	self.getSelector("senialLecturaDatosGrupoCC").html(self.contrato.montoSenialTT.senial);
	self.getSelector("distribuidorLecturaDatosGrupoCC").html(self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("distribuidorLecturaDatosGrupoCC").attr("title", self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("montoTotalLecturaDatosGrupoCC").html(self.contrato.montoSenialTT.monto);
	self.getSelector("nroGrupoLecturaDatosGrupoCC").html(self.contrato.grupoCC.nroGrupo);
	self.getSelector("tipoTituloLecturaDatosGrupoCC").html(self.contrato.montoSenialTT.tipoTitulo);
	self.situarSenialesHeredadasReadOnly();
	self.getSelector("nombreGrupoLecturaDatosGrupoCC").html(grupo.nombreGrupo);
	self.getSelector("cantidadOriginalesLecturaDatosGrupoCC").html(grupo.cantidadOriginales);
	self.getSelector("cantidadRepeticionesLecturaDatosGrupoCC").html(grupo.cantidadRepeticiones);
	self.getSelector("tipoImporteLecturaDatosGrupoCC").html(grupo.tipoImporte);
	self.getSelector("costoTotalLecturaDatosGrupoCC").html(grupo.costoTotal);
	self.getSelector("estrenoRepeticionLecturaDatosGrupoCC").html(grupo.estrenoRepeticion);
	
    var dateSplit = grupo.fechaComienzoDerechos.substring(0,10).split("-");
    var objDate = new Date(dateSplit[0] + " " + dateSplit[1] + " " + dateSplit[2]);
    //self.getSelector("fechaComienzoDerechosLecturaDatosGrupoCC").datepicker('setDate', objDate);
    self.getSelector("fechaComienzoDerechosLecturaDatosGrupoCC").html($.datepicker.formatDate('dd/mm/yy', objDate));

    dateSplit = grupo.fechaComienzoDerechos.substring(0,10).split("-");
    objDate = new Date(dateSplit[0] + " " + dateSplit[1] + " " + dateSplit[2]);
    //self.getSelector("fechaPosibleEntregaLecturaDatosGrupoCC").datepicker('setDate', objDate);
	self.getSelector("fechaPosibleEntregaLecturaDatosGrupoCC").html($.datepicker.formatDate('dd/mm/yy', objDate));
	
	self.getSelector("codigoComienzoDerechosLecturaDatosGrupoCC").html(grupo.tipoDerecho);
	self.getSelector("cantidadTiempoLecturaDatosGrupoCC").html(grupo.cantidadTiempo);
	self.getSelector("codigoUnidadTiempoLecturaDatosGrupoCC").html(grupo.unidadTiempo);
	self.getSelector("codigoDerechosTerritorialesLecturaDatosGrupoCC").html(grupo.derechoTerrirorial);
	self.getSelector("marcaPerpetuidadLecturaDatosGrupoCC").html(grupo.marcaPerpetuidad);
	self.getSelector("planEntregaLecturaDatosGrupoCC").html(grupo.planEntrega);
	self.getSelector("recontratacionLecturaDatosGrupoCC").html(grupo.recontratacion);
	self.getSelector("cantidadTiempoExclusividadLecturaDatosGrupoCC").html(grupo.cantidadTiempoExclusividad);
	self.getSelector("unidadTiempoExclusividadLecturaDatosGrupoCC").html(grupo.unidadTiempoExclusividad);
	self.getSelector("autorizacionAutorLecturaDatosGrupoCC").html(grupo.autorizacionAutor);
	self.getSelector("pagaArgentoresLecturaDatosGrupoCC").html(grupo.pagaArgentores);
	self.getSelector("pasaLibreriaLecturaDatosGrupoCC").html(grupo.pasaLibreria);
	
	dateSplit = grupo.fechaComienzoDerechosLibreria.substring(0,10).split("-");
    objDate = new Date(dateSplit[0] + " " + dateSplit[1] + " " + dateSplit[2]);
    self.getSelector("fechaComienzoDerechosLibreriaLecturaDatosGrupoCC").datepicker('setDate', objDate);
	//self.getSelector("fechaComienzoDerechosLibreriaLecturaDatosGrupoCC").html($.datepicker.formatDate('dd/mm/yy', new Date(grupo.fechaComienzoDerechosLibreria)));
};

AltaContratoEvent.prototype.volverLecturaDatosGrupoCC = function() {
	var self = altaContratoEvent;
	self.getSelector("formLecturaDatosGrupoCC").hide();
	self.getSelector("formVisualizarGruposContrato").show();
};

/***********************************************************************************		
 ********************	Pantalla FP11009 a FP11005 - Modificar	********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirModificacionDatosGrupoCC = function() {
	var self = altaContratoEvent;
	var grupo = self.recuperarDatosGrupo(self.contrato.grupoCC.nroGrupo);

	setTimeout(function() {
		Component.get("html/altaContrato/cargaDatosGrupoCC.html", function(comp) {
			self.drawModificacionDatosGrupoCC(comp, grupo);
		});
	}, 750);
};

AltaContratoEvent.prototype.drawModificacionDatosGrupoCC = function(comp, grupo) {
	var self = altaContratoEvent;

	self.getSelector("formVisualizarGruposContrato").hide();
	comp = comp.replace(/{{id}}/g, self.div.id);
	
	if (self.getSelector("formCargaGrupoCC").length) {
		self.getSelector("formCargaGrupoCC").replaceWith(comp);
	} else {
		self.getDiv().append(comp);
	}
	/* Accordion */
	Accordion.getInstance(self.getSelector("accordionCargaGrupoCC"));
	/* Botonera */
	self.getSelector("volverDatosCargaGrupoCC").button().click(self.volverModificacionDatosGrupoCC);
	self.getSelector("aceptarDatosCargaGrupoCC").button().click(function() {
		self.validarDatosGrupoCC(self.validarModificacionDatosGrupoCC);
	});
	/* Seteo de datos output */
	self.getSelector("senialCargaGrupoCC").html(self.contrato.montoSenialTT.senial);
	self.getSelector("distribuidorCargaGrupoCC").html(self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("distribuidorCargaGrupoCC").attr("title", self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("montoTotalCargaGrupoCC").html(self.contrato.montoSenialTT.monto);
	self.getSelector("nroGrupoCargaGrupoCC").html(self.contrato.grupoCC.nroGrupo);
	self.getSelector("tipoTituloCargaGrupoCC").html(self.contrato.montoSenialTT.tipoTitulo);
	/* Situar señales heredadas */
	self.situarSenialesHeredadasCC();
	/* Nombre grupo - Sólo lectura */
	self.getSelector("nombreGrupoCargaGrupoCC").attr('disabled', 'disabled');
	self.getSelector("nombreGrupoCargaGrupoCC").addClass('readOnly');
	self.getSelector("nombreGrupoCargaGrupoCC").val(grupo.nombreGrupo);
	/* Cantidad originales */
	self.getSelector("cantidadOriginalesCargaGrupoCC").numeric();
	if (grupo.cantidadOriginales > 0) {
		self.getSelector("cantidadOriginalesCargaGrupoCC").val(grupo.cantidadOriginales);
	}
	/* Cantidad repeticiones */
	self.getSelector("cantidadRepeticionesCargaGrupoCC").numeric();
	if (grupo.cantidadRepeticiones > 0) {
		self.getSelector("cantidadRepeticionesCargaGrupoCC").val(grupo.cantidadRepeticiones);
	}
	/* Situar tipos importe */
	self.buscarTiposImporte("tipoImporteCargaGrupoCC");
	self.getSelector("tipoImporteCargaGrupoCC").val(grupo.tipoImporte);
	/* Costo total */
	self.getSelector("costoTotalCargaGrupoCC").autoNumeric({vMax: '999999999.99'});
	if (grupo.costoTotal > 0) {
		self.getSelector("costoTotalCargaGrupoCC").autoNumericSet(grupo.costoTotal);
	}
	/* Estreno / Repetición - Sólo lectura */
	self.getSelector("estrenoRepeticionCargaGrupoCC").attr('disabled', 'disabled');
	Component.populateSelect(self.getSelector("estrenoRepeticionCargaGrupoCC"), AltaContratoEvent.selectER, "codigo", "descripcion", false);
	self.getSelector("estrenoRepeticionCargaGrupoCC")[0].removeChild(self.getSelector("estrenoRepeticionCargaGrupoCC")[0].options[0]);
	self.getSelector("estrenoRepeticionCargaGrupoCC").val(grupo.estrenoRepeticion);
	/* Fecha comienzo derechos territoriales */
	if ((new Date(grupo.fechaComienzoDerechos).getFullYear() != 1) && (new Date(grupo.fechaComienzoDerechos).getFullYear() != 0) && (new Date(grupo.fechaComienzoDerechos).getFullYear() != 1900) && (new Date(grupo.fechaComienzoDerechos).getFullYear() != 1901) ){
	    var dateSplit = grupo.fechaComienzoDerechos.substring(0,10).split("-");
	    var objDate = new Date(dateSplit[0] + " " + dateSplit[1] + " " + dateSplit[2]);
	    //self.getSelector("fechaComienzoDerechosCargaGrupoCC").datepicker('setDate', objDate);
		Datepicker.getInstance(self.getSelector("fechaComienzoDerechosCargaGrupoCC"), objDate);	
	} else {
		Datepicker.getInstance(self.getSelector("fechaComienzoDerechosCargaGrupoCC"));
	}
	/* Fecha posible entrega */
	if ((new Date(grupo.fechaPosibleEntrega).getFullYear() != 1) && (new Date(grupo.fechaPosibleEntrega).getFullYear() != 0) && (new Date(grupo.fechaPosibleEntrega).getFullYear() != 1900) && (new Date(grupo.fechaPosibleEntrega).getFullYear() != 1901) ){
	    var dateSplit = grupo.fechaPosibleEntrega.substring(0,10).split("-");
	    var objDate = new Date(dateSplit[0] + " " + dateSplit[1] + " " + dateSplit[2]);
        //self.getSelector("fechaPosibleEntregaCargaGrupoCC").datepicker('setDate', objDate);
		Datepicker.getInstance(self.getSelector("fechaPosibleEntregaCargaGrupoCC"), objDate);
	} else {
		Datepicker.getInstance(self.getSelector("fechaPosibleEntregaCargaGrupoCC"));
	}
	/* Situar tipos derecho */
	self.buscarTiposDerecho("codigoComienzoDerechosCargaGrupoCC");
	self.getSelector("codigoComienzoDerechosCargaGrupoCC").val(grupo.tipoDerecho);
	/* Cantidad tiempo */
	self.getSelector("cantidadTiempoCargaGrupoCC").numeric();
	if (grupo.cantidadTiempo > 0) {
		self.getSelector("cantidadTiempoCargaGrupoCC").val(grupo.cantidadTiempo);
	}
	/* Unidad tiempo: debe tener la opcion 'vacio' */
	Component.populateSelect(self.getSelector("codigoUnidadTiempoCargaGrupoCC"), AltaContratoEvent.selectUnidadTiempo, "codigo", "descripcion", false);
	self.getSelector("codigoUnidadTiempoCargaGrupoCC").val(grupo.unidadTiempo);
	/* Situar tipos derecho territorial */
	self.buscarTiposDerechoTerritorial("codigoDerechosTerritorialesCargaGrupoCC");
	self.getSelector("codigoDerechosTerritorialesCargaGrupoCC").val(grupo.derechoTerrirorial);
	/* Marca de perpetuidad */
	Component.populateSelect(self.getSelector("marcaPerpetuidadCargaGrupoCC"), AltaContratoEvent.selectSN, "codigo", "descripcion", false);
	self.getSelector("marcaPerpetuidadCargaGrupoCC")[0].removeChild(self.getSelector("marcaPerpetuidadCargaGrupoCC")[0].options[0]);
	self.getSelector("marcaPerpetuidadCargaGrupoCC").val(grupo.marcaPerpetuidad);
	/* Plan de entrega */
	if (grupo.planEntrega.trim() != "") {
		self.getSelector("planEntregaCargaGrupoCC").val(grupo.planEntrega);
	}
	/* Recontratación - Validar recontratación */
	Component.populateSelect(self.getSelector("recontratacionCargaGrupoCC"), AltaContratoEvent.selectSN, "codigo", "descripcion", false);
	self.getSelector("recontratacionCargaGrupoCC").val(grupo.recontratacion);
	if (!self.validarRecontratacion()) {
		self.getSelector("recontratacionCargaGrupoCC").attr('disabled', 'disabled');
	}
	/* Cantidad tiempo exclusividad */
	self.getSelector("cantidadTiempoExclusividadCargaGrupoCC").numeric();
	if (grupo.cantidadTiempoExclusividad > 0) {
		self.getSelector("cantidadTiempoExclusividadCargaGrupoCC").val(grupo.cantidadTiempoExclusividad);
	}
	/* Unidad tiempo exclusividad: debe tener la opción 'vacio' */
	Component.populateSelect(self.getSelector("unidadTiempoExclusividadCargaGrupoCC"), AltaContratoEvent.selectUnidadTiempo, "codigo", "descripcion", false);
	self.getSelector("unidadTiempoExclusividadCargaGrupoCC").val(grupo.unidadTiempoExclusividad);
	/* Autorización autor: debe tener la opción 'vacio' */
	Component.populateSelect(self.getSelector("autorizacionAutorCargaGrupoCC"), AltaContratoEvent.selectSN, "codigo", "descripcion", false);
	self.getSelector("autorizacionAutorCargaGrupoCC").val(grupo.autorizacionAutor);
	/* Paga argentores: debe tener la opción 'vacio' */
	Component.populateSelect(self.getSelector("pagaArgentoresCargaGrupoCC"), AltaContratoEvent.selectSN, "codigo", "descripcion", false);	
	self.getSelector("pagaArgentoresCargaGrupoCC").val(grupo.pagaArgentores);
	/* Pasa librería */
	Component.populateSelect(self.getSelector("pasaLibreriaCargaGrupoCC"), AltaContratoEvent.selectSN, "codigo", "descripcion", false);
	self.getSelector("pasaLibreriaCargaGrupoCC")[0].removeChild(self.getSelector("pasaLibreriaCargaGrupoCC")[0].options[0]);
	self.getSelector("pasaLibreriaCargaGrupoCC").val(grupo.pasaLibreria);
	/* Fecha comienzo derechos librería */
	if ((new Date(grupo.fechaComienzoDerechosLibreria).getFullYear() != 1) && (new Date(grupo.fechaComienzoDerechosLibreria).getFullYear() != 0) && (new Date(grupo.fechaComienzoDerechosLibreria).getFullYear() != 1900) && (new Date(grupo.fechaComienzoDerechosLibreria).getFullYear() != 1901) ){
        var dateSplit = grupo.fechaComienzoDerechosLibreria.substring(0,10).split("-");
        var objDate = new Date(dateSplit[0] + " " + dateSplit[1] + " " + dateSplit[2]);
        //self.getSelector("fechaComienzoDerechosLibreriaCargaGrupoCC").datepicker('setDate', objDate);
        Datepicker.getInstance(self.getSelector("fechaComienzoDerechosLibreriaCargaGrupoCC"), objDate);
	} else {
		Datepicker.getInstance(self.getSelector("fechaComienzoDerechosLibreriaCargaGrupoCC"));
	}
	
	/* Datos a comparar */
	self.contrato.grupoCC.cantidadOriginales = grupo.cantidadOriginales;
	self.contrato.grupoCC.recontratacion = grupo.recontratacion;
	
	/* Desbloquear pantalla */
	BLOCK.hideBlock();
};

AltaContratoEvent.prototype.validarModificacionDatosGrupoCC = function() {
	var self = altaContratoEvent;

	var validarModificacionGrupoCCRequest = {
		contrato : self.contrato.numero,
	    grupo: self.contrato.grupoCC.nroGrupo,
	    senial: self.contrato.montoSenialTT.senial,
	    tipoTitulo: self.contrato.montoSenialTT.tipoTitulo,
	    tipoImporte: self.getSelector("tipoImporteCargaGrupoCC").val(),
	    idTipoDerecho: self.getSelector("codigoComienzoDerechosCargaGrupoCC").val(),
	    fechaComienzoDerechos: ((self.getSelector("fechaComienzoDerechosCargaGrupoCC").val() != null && self.getSelector("fechaComienzoDerechosCargaGrupoCC").val() != "") ? self.getSelector("fechaComienzoDerechosCargaGrupoCC").val() : "01/01/0001"),
	    cantidadTiempo: ((self.getSelector("cantidadTiempoCargaGrupoCC").val() != null && self.getSelector("cantidadTiempoCargaGrupoCC").val() != "") ? self.getSelector("cantidadTiempoCargaGrupoCC").val() : 0), 
		unidadTiempo: ((self.getSelector("codigoUnidadTiempoCargaGrupoCC").val() != null && self.getSelector("codigoUnidadTiempoCargaGrupoCC").val() != "") ? self.getSelector("codigoUnidadTiempoCargaGrupoCC").val() : " "),
	    marcaPerpetuidad: self.getSelector("marcaPerpetuidadCargaGrupoCC").val(),
	    pasaLibreria: self.getSelector("pasaLibreriaCargaGrupoCC").val(),
	    cantidadOriginales: self.getSelector("cantidadOriginalesCargaGrupoCC").val(),
	    cantidadRepeticiones: ((self.getSelector("cantidadRepeticionesCargaGrupoCC").val() != null && self.getSelector("cantidadRepeticionesCargaGrupoCC").val() != "") ? self.getSelector("cantidadRepeticionesCargaGrupoCC").val() : 0)
	};

	var data = {
		async: false,
		action:"altaContratoValidarModificacionGrupoCC.action", 
		request: Component.serialize(validarModificacionGrupoCCRequest, "validarModificacionGrupoCCRequest"), 
		method: "GET", 
		responseObject: "validarModificacionGrupoCCResponse", 
		callback: function(validarModificacionGrupoCCResponse) {
			if (!validarModificacionGrupoCCResponse.hayErrores) {
				self.eliminarConsultarCC();
				self.modificacionGrupoCC();
			} else {
                self.abrirPopupValidacionGrupo(validarModificacionGrupoCCResponse.errores);
                console.log(validarModificacionGrupoCCResponse.errores);
			};
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.eliminarConsultarCC = function() {
	var self = altaContratoEvent;
	
	var eliminarConsultarCCRequest = {
		contrato: self.contrato.numero,
		grupo: self.contrato.grupoCC.nroGrupo,
		senial: self.contrato.montoSenialTT.senial,
	    tipoTitulo: self.contrato.montoSenialTT.tipoTitulo,
	    nroTitulo: null	
	};
	
	var data = {
		async: false,
		action:"altaContratoEliminarConsultarCC.action", 
		request: Component.serialize(eliminarConsultarCCRequest, "eliminarConsultarCCRequest"), 
		method: "POST", 
		responseObject: "resultado", 
		callback: function(exito) {
			if (!exito) {
				MESSAGE.error("Ocurrio un error durante la operación");
			}
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.modificacionGrupoCC = function() {
	var self = altaContratoEvent;
	
	var modificacionGrupoCCRequest = { 
		contrato : self.contrato.numero,
		grupo: self.contrato.grupoCC.nroGrupo,
		senial: self.contrato.montoSenialTT.senial,
		tipoTitulo: self.contrato.montoSenialTT.tipoTitulo,
		tipoImporte: self.getSelector("tipoImporteCargaGrupoCC").val(),
		idTipoDerecho: self.getSelector("codigoComienzoDerechosCargaGrupoCC").val(),
		fechaComienzoDerechos: ((self.getSelector("fechaComienzoDerechosCargaGrupoCC").val() != null && self.getSelector("fechaComienzoDerechosCargaGrupoCC").val() != "") ? self.getSelector("fechaComienzoDerechosCargaGrupoCC").val() : "01/01/0001"),
		cantidadTiempo: ((self.getSelector("cantidadTiempoCargaGrupoCC").val() != null && self.getSelector("cantidadTiempoCargaGrupoCC").val() != "") ? self.getSelector("cantidadTiempoCargaGrupoCC").val() : 0), 
		unidadTiempo: ((self.getSelector("codigoUnidadTiempoCargaGrupoCC").val() != null && self.getSelector("codigoUnidadTiempoCargaGrupoCC").val() != "") ? self.getSelector("codigoUnidadTiempoCargaGrupoCC").val() : " "),
	    marcaPerpetuidad: self.getSelector("marcaPerpetuidadCargaGrupoCC").val(),
	    pasaLibreria: self.getSelector("pasaLibreriaCargaGrupoCC").val(),
	    cantidadOriginales: self.getSelector("cantidadOriginalesCargaGrupoCC").val(),
	    cantidadRepeticiones: ((self.getSelector("cantidadRepeticionesCargaGrupoCC").val() != null && self.getSelector("cantidadRepeticionesCargaGrupoCC").val() != "") ? self.getSelector("cantidadRepeticionesCargaGrupoCC").val() : 0),
	    marcaRecontratacion: ((self.getSelector("recontratacionCargaGrupoCC").val() != null && self.getSelector("recontratacionCargaGrupoCC").val() != "") ? self.getSelector("recontratacionCargaGrupoCC").val() : " "),
	    fechaEntrega: ((self.getSelector("fechaPosibleEntregaCargaGrupoCC").val() != null && self.getSelector("fechaPosibleEntregaCargaGrupoCC").val() != "") ? self.getSelector("fechaPosibleEntregaCargaGrupoCC").val() : "01/01/0001"),
	    importeGrupo: self.getSelector("costoTotalCargaGrupoCC").autoNumericGet(),
	    derechosTerritoriales: self.getSelector("codigoDerechosTerritorialesCargaGrupoCC").val(),
	    cantidadTiempoExclusivo: ((self.getSelector("cantidadTiempoExclusividadCargaGrupoCC").val() != null && self.getSelector("cantidadTiempoExclusividadCargaGrupoCC").val() != "") ? self.getSelector("cantidadTiempoExclusividadCargaGrupoCC").val() : 0),
	    unidadTiempoExclusivo: self.getSelector("unidadTiempoExclusividadCargaGrupoCC").val(),
	    autorizacionAutor: self.getSelector("autorizacionAutorCargaGrupoCC").val(),
	    pagaArgentores: self.getSelector("pagaArgentoresCargaGrupoCC").val(),
	    fechaComienzoLibreria: ((self.getSelector("fechaComienzoDerechosLibreriaCargaGrupoCC").val() != null && self.getSelector("fechaComienzoDerechosLibreriaCargaGrupoCC").val() != "") ? self.getSelector("fechaComienzoDerechosLibreriaCargaGrupoCC").val() : "01/01/0001") 
	};
	
	var data = {
		async: false,
		action:"altaContratoModificacionGrupoCC.action", 
		request: Component.serialize(modificacionGrupoCCRequest, "modificacionGrupoCCRequest"), 
		method: "POST", 
		responseObject: "modificacionGrupoCCResponse", 
		callback: function(modificacionGrupoCCResponse) {
			if (modificacionGrupoCCResponse.hayErrores) {
				/* Abro el warning con los datos de los títulos */
				var params = {
					mensaje: modificacionGrupoCCResponse.mensaje,
					datosDesenlace: modificacionGrupoCCResponse.erroresRerun
				};
				self.abrirPopupWarningsModificacionGrupo(params);
				
				/* Guardo los titulos para más adelante */
				self.contrato.titulosRecontratar = modificacionGrupoCCResponse.erroresRerun;
				self.contrato.tituloRecontratar = self.contrato.titulosRecontratar[0];
				
				/* Seteo los datos para la próxima pantalla */
				self.contrato.tituloRecontratacionSeleccionado = new Object();
				/* Datos del grupo */
				self.contrato.tituloRecontratacionSeleccionado.contrato = self.contrato.numero;
				self.contrato.tituloRecontratacionSeleccionado.grupo = self.contrato.grupoCC.nroGrupo;
				/* Datos del título 
				 * FIXME: Ver de donde obtengo la descripción del título */
				self.contrato.tituloRecontratacionSeleccionado.clave = self.contrato.tituloRecontratar.tipoTitulo + tituloRecontratar.nroTitulo;
				self.contrato.tituloRecontratacionSeleccionado.titulo = "";
				/* Abrir FP11015 */
				self.getSelector("formCargaGrupoCC").hide();
				self.abrirSeleccionTituloRecontratacionEnlaces("FP11005");
			} else {
				MESSAGE.ok("Se modificó el grupo con éxito");
				
				/* Valido a donde debo redirigir */
				if (self.contrato.grupoCC.cantidadOriginales > self.getSelector("cantidadOriginalesCargaGrupoCC").val()) {
					/* Actualizo la cantidad nueva de originales y de repeticiones */
					self.contrato.grupoCC.cantidadOriginales = self.getSelector("cantidadOriginalesCargaGrupoCC").val();
					self.contrato.grupoCC.cantidadRepeticiones = self.getSelector("cantidadRepeticionesCargaGrupoCC").val();
					/* Disminuyo la cantidad de originales - Redirigir a FP11018 */
					self.getSelector("formCargaGrupoCC").hide();
					self.abrirEliminacionCapitulosContrato();
				} else if (self.contrato.grupoCC.cantidadOriginales < self.getSelector("cantidadOriginalesCargaGrupoCC").val()) {
					/* Actualizo la cantidad nueva de originales y de repeticiones */
					self.contrato.grupoCC.cantidadOriginales = self.getSelector("cantidadOriginalesCargaGrupoCC").val();
					self.contrato.grupoCC.cantidadRepeticiones = self.getSelector("cantidadRepeticionesCargaGrupoCC").val();
					/* Incrementó la cantidad de originales - Redirigir a FP11019
					   Si el grupo es de recontratación, debe seleccionar capítulos por pantalla. Sino llamar a confirmación automática */
					if (self.contrato.grupoCC.recontratacion == "S") {
						self.getSelector("formCargaGrupoCC").hide();
						self.abrirAdicionCapitulosContrato();
					} else {
						self.volverModificacionDatosGrupoCC();
					}
				} else {
				    self.buscarGruposContrato();
					self.volverModificacionDatosGrupoCC();
				}
			}
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.volverModificacionDatosGrupoCC = function() {
	var self = altaContratoEvent;
	self.getSelector("formCargaGrupoCC").hide();
	self.getSelector("formVisualizarGruposContrato").show();
};