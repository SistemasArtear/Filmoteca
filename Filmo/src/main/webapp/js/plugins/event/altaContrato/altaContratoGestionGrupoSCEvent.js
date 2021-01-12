/***********************************************************************************		
 ********************				Pantalla FP11007			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirCargaDatosGrupoSC = function(tipoTitulo) {
	BLOCK.showBlock("Cargando datos del grupo");
	
	var self = altaContratoEvent;
	self.cierrePopupTipoTituloGrupo();
	self.contrato.montoSenialTT.tipoTitulo = tipoTitulo;
	
	setTimeout(function() {
		Component.get("html/altaContrato/cargaDatosGrupoSC.html", self.drawCargaDatosGrupoSC);
	}, 750);
};

AltaContratoEvent.prototype.drawCargaDatosGrupoSC = function(comp) {
	var self = altaContratoEvent;
	
	self.getSelector("formCargaContrato").hide();
	comp = comp.replace(/{{id}}/g, self.div.id);
	
	if (self.getSelector("formCargaGrupoSC").length) {
		self.getSelector("formCargaGrupoSC").replaceWith(comp);
	} else {
		self.getDiv().append(comp);
	}
	
	/* Accordion */
	Accordion.getInstance(self.getSelector("accordionCargaGrupoSC"));
	
	/* Botonera */
	self.getSelector("volverDatosCargaGrupoSC").button().click(self.volverDatosCargaGrupoSC);
	self.getSelector("aceptarDatosCargaGrupoSC").button().click(function() {
		self.validarDatosGrupoSC(self.validarAmortizacionSC);
	});
	
	/* Señal */
	self.getSelector("senialCargaGrupoSC").html(self.contrato.montoSenialTT.senial);
	/* Distribuidor */
	self.getSelector("distribuidorCargaGrupoSC").html(self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("distribuidorCargaGrupoSC").attr("title", self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	/* Monto total */
	self.getSelector("montoTotalCargaGrupoSC").html(self.contrato.montoSenialTT.monto);
	/* Nro. grupo */
	self.obtenerProximoNumeroGrupo();
	/* Tipo título */
	self.getSelector("tipoTituloCargaGrupoSC").html(self.contrato.montoSenialTT.tipoTitulo);
	/* Situar señales heredadas */
	self.situarSenialesHeredadasSC();
	/* Cantidad de títulos */
	self.getSelector("cantidadTitulosCargaGrupoSC").numeric();
	/* Cantidad de pasadas contratadas */
	self.getSelector("cantidadPasadasContratadasCargaGrupoSC").numeric();
	/* Situar tipos importe */
	self.buscarTiposImporte("tipoImporteCargaGrupoSC");
	/* Costo unitario */
	self.getSelector("costoUnitarioCargaGrupoSC").autoNumeric({vMax: '999999999.99'});
	/* Estreno / Repetición */
	Component.populateSelect(self.getSelector("estrenoRepeticionCargaGrupoSC"), AltaContratoEvent.selectER, "codigo", "descripcion", false);
	//self.getSelector("estrenoRepeticionCargaGrupoSC")[0].removeChild(self.getSelector("estrenoRepeticionCargaGrupoSC")[0].options[0]);
	/* Fecha comienzo derechos territoriales */
	Datepicker.getInstance(self.getSelector("fechaComienzoDerechosCargaGrupoSC"));
	/* Situar tipos derecho */
	self.buscarTiposDerecho("codigoComienzoDerechosCargaGrupoSC");
	/* Cantidad tiempo */
	self.getSelector("cantidadTiempoCargaGrupoSC").numeric();
	/* Unidad tiempo: debe tener la opcion 'vacio' */
	Component.populateSelect(self.getSelector("codigoUnidadTiempoCargaGrupoSC"), AltaContratoEvent.selectUnidadTiempo, "codigo", "descripcion", false);
	/* Situar tipos derecho territorial */
	self.buscarTiposDerechoTerritorial("codigoDerechosTerritorialesCargaGrupoSC");
	/* Marca de perpetuidad */
	Component.populateSelect(self.getSelector("marcaPerpetuidadCargaGrupoSC"), AltaContratoEvent.selectSN, "codigo", "descripcion", false);
	self.getSelector("marcaPerpetuidadCargaGrupoSC")[0].removeChild(self.getSelector("marcaPerpetuidadCargaGrupoSC")[0].options[0]);
	self.getSelector("marcaPerpetuidadCargaGrupoSC").val('N');
	/* Cantidad tiempo exclusividad */
	self.getSelector("cantidadTiempoExclusividadCargaGrupoSC").numeric();
	/* Unidad tiempo exclusividad: debe tener la opción 'vacio' */
	Component.populateSelect(self.getSelector("unidadTiempoExclusividadCargaGrupoSC"), AltaContratoEvent.selectUnidadTiempo, "codigo", "descripcion", false);
	/* Autorización autor: debe tener la opción 'vacio' */
	Component.populateSelect(self.getSelector("autorizacionAutorCargaGrupoSC"), AltaContratoEvent.selectSN, "codigo", "descripcion", false);
	//self.getSelector("autorizacionAutorCargaGrupoSC")[0].removeChild(self.getSelector("autorizacionAutorCargaGrupoSC")[0].options[0]);
	/* Paga argentores: debe tener la opción 'vacio' */
	Component.populateSelect(self.getSelector("pagaArgentoresCargaGrupoSC"), AltaContratoEvent.selectSN, "codigo", "descripcion", false);
	//self.getSelector("pagaArgentoresCargaGrupoSC")[0].removeChild(self.getSelector("pagaArgentoresCargaGrupoSC")[0].options[0]);
	/* Pasa librería */
	Component.populateSelect(self.getSelector("pasaLibreriaCargaGrupoSC"), AltaContratoEvent.selectSN, "codigo", "descripcion", false);
	self.getSelector("pasaLibreriaCargaGrupoSC")[0].removeChild(self.getSelector("pasaLibreriaCargaGrupoSC")[0].options[0]);
	self.getSelector("pasaLibreriaCargaGrupoSC").val("N");
	/* Fecha comienzo derechos librería */
	Datepicker.getInstance(self.getSelector("fechaComienzoDerechosLibreriaCargaGrupoSC"));
	
	/* Desbloquear pantalla */
	BLOCK.hideBlock();
};

AltaContratoEvent.prototype.situarSenialesHeredadasSC = function() {
	var self = altaContratoEvent;
	
	var buscarSenialesHeredadasRequest = {
		senial: self.contrato.montoSenialTT.senial
	};
	
	var data = {
		async: false,
		action:"altaContratoBuscarSenialHeredadaSC.action", 
		request: Component.serialize(buscarSenialesHeredadasRequest, "buscarSenialesHeredadasRequest"), 
		method: "GET", 
		responseObject: "senialHeredada", 
		callback: function(senialHeredada) {
			if (senialHeredada) {
				self.getSelector("cargarSenialesHeredadasCargaGrupoSC").show();
				self.getSelector("senialesHeredadasCargaGrupoSC").html(senialHeredada);
				altaContratoEvent.validarAsigacionSenialHeredadaSC(senialHeredada);
				self.getSelector("cargarSenialesHeredadasCargaGrupoSC").click(self.abrirCargaSenialesHeredadas);				
			} else {
				self.getSelector("cargarSenialesHeredadasCargaGrupoSC").hide();
			}
		}
	};
	
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.validarDatosGrupoSC = function(callback) {
	var self = altaContratoEvent;
	
	/* Validar ingreso de nombre de grupo */
	if (Validator.isEmpty(self.getSelector("nombreGrupoCargaGrupoSC"))) {
		Validator.focus(self.getSelector("nombreGrupoCargaGrupoSC"), "Debe ingresar un nombre para el grupo");
		return;
	}
	
	/*Validar tipo importe */
	if (Validator.isEmpty(self.getSelector("tipoImporteCargaGrupoSC"))){
		Validator.focus(self.getSelector("tipoImporteCargaGrupoSC"), "Debe ingresar un tipo de importe para el grupo");
		return;
	}
	
	/*Validar tipo Estreno - Repeticion */
	if (Validator.isEmpty(self.getSelector("estrenoRepeticionCargaGrupoSC"))){
		Validator.focus(self.getSelector("estrenoRepeticionCargaGrupoSC"), "Debe ingresar si es estreno o repeticion");
		return;
	}
	
	/* Validar ingreso cantidad de títulos */
	if (Validator.isEmpty(self.getSelector("cantidadTitulosCargaGrupoSC"))) {
		Validator.focus(self.getSelector("cantidadTitulosCargaGrupoSC"), "Debe ingresar la cantidad de títulos");
		return;
	} 
	/* Validar ingreso cantidad de pasadas contratadas: si marca perpetuidad es 'S', debe ser 0. */
	if (!Validator.isEmpty(self.getSelector("cantidadPasadasContratadasCargaGrupoSC")) && self.getSelector("cantidadPasadasContratadasCargaGrupoSC").val() > 0  && self.getSelector("marcaPerpetuidadCargaGrupoSC").val() == "S") {
		Validator.focus(self.getSelector("cantidadPasadasContratadasCargaGrupoSC"), "No debe ingresar la cantidad de pasadas");
		return;
	} 
	/* Validar ingreso tipo importe: para tipo importe FIJ o VAS, debe ingresar el importe unitario */
	if ((self.getSelector("tipoImporteCargaGrupoSC").val() == "FIJ" || self.getSelector("tipoImporteCargaGrupoSC").val() == "VAS") && (Validator.isEmpty(self.getSelector("costoUnitarioCargaGrupoSC")) || self.getSelector("costoUnitarioCargaGrupoSC").val() == 0)) {
		Validator.focus(self.getSelector("costoUnitarioCargaGrupoSC"), "Debe ingresar el importe unitario");
		return;
	}
	/* Validar ingreso comienzo derechos: obligatorio si el código de comienzo de derechos es ‘F’. 
	 * Debe estar en blanco si el código de comienzo de derechos es ‘R’ o ‘C’*/
	if (Validator.isEmpty(self.getSelector("fechaComienzoDerechosCargaGrupoSC")) && self.getSelector("codigoComienzoDerechosCargaGrupoSC").val() == "F") {
		Validator.focus(self.getSelector("fechaComienzoDerechosCargaGrupoSC"), "Debe ingresar una fecha de comienzo de derechos");
		return;
	} else if (!Validator.isEmpty(self.getSelector("fechaComienzoDerechosCargaGrupoSC")) && self.getSelector("codigoComienzoDerechosCargaGrupoSC").val() != "F") {
		Validator.focus(self.getSelector("fechaComienzoDerechosCargaGrupoSC"), "No debe ingresar una fecha de comienzo de derechos");
		return;
	}
	/* Validar ingreso tipos de derecho */
	if (Validator.isEmpty(self.getSelector("codigoComienzoDerechosCargaGrupoSC"))) {
		Validator.focus(self.getSelector("codigoComienzoDerechosCargaGrupoSC"), "Código de comienzo de derecho no existente");
		return;
	}
	/* Validar ingreso cantidad de tiempo */
	if (Validator.isEmpty(self.getSelector("cantidadTiempoCargaGrupoSC")) && self.getSelector("marcaPerpetuidadCargaGrupoSC").val() == "N") {
		Validator.focus(self.getSelector("cantidadTiempoCargaGrupoSC"), "Debe ingresar la cantidad de tiempo");
		return;
	} else if (!Validator.isEmpty(self.getSelector("cantidadTiempoCargaGrupoSC")) && self.getSelector("marcaPerpetuidadCargaGrupoSC").val() != "N") {
		Validator.focus(self.getSelector("cantidadTiempoCargaGrupoSC"), "No debe ingresar la cantidad de tiempo");
		return;
	}
	/* Validar ingreso unidad de tiempo */
	if (Validator.isEmpty(self.getSelector("codigoUnidadTiempoCargaGrupoSC")) && self.getSelector("marcaPerpetuidadCargaGrupoSC").val() == "N") {
		Validator.focus(self.getSelector("codigoUnidadTiempoCargaGrupoSC"), "No debe ingresar la unidad de tiempo");
		return;
	} else if (!Validator.isEmpty(self.getSelector("codigoUnidadTiempoCargaGrupoSC")) && self.getSelector("marcaPerpetuidadCargaGrupoSC").val() != "N") {
		Validator.focus(self.getSelector("codigoUnidadTiempoCargaGrupoSC"), "La unidad de tiempo no puede ser nula");
		return;
	}
	/* Validar ingreso tipos de derecho territorial */
	if (Validator.isEmpty(self.getSelector("codigoDerechosTerritorialesCargaGrupoSC"))) {
		Validator.focus(self.getSelector("codigoDerechosTerritorialesCargaGrupoSC"), "Código de derecho territorial no existente");
		return;
	}
	/* Validar ingreso cantidad tiempo exclusividad */
	if (!Validator.isEmpty(self.getSelector("cantidadTiempoExclusividadCargaGrupoSC")) && Validator.isEmpty(self.getSelector("unidadTiempoExclusividadCargaGrupoSC"))) {
		Validator.focus(self.getSelector("unidadTiempoExclusividadCargaGrupoSC"), "Debe ingresar la unidad de tiempo de exclusividad");
		return;
	}
	/* Validar ingreso unidad tiempo exclusividad */
	if (Validator.isEmpty(self.getSelector("cantidadTiempoExclusividadCargaGrupoSC")) && !Validator.isEmpty(self.getSelector("unidadTiempoExclusividadCargaGrupoSC"))) {
		Validator.focus(self.getSelector("cantidadTiempoExclusividadCargaGrupoSC"), "Debe ingresar la cantidad de tiempo de exclusividad");
		return;
	}

	callback();
};

AltaContratoEvent.prototype.validarAmortizacionSC = function() {
	var self = altaContratoEvent;
	
	var validarAmortizacionRequest = {
		contrato: self.contrato.numero,
		grupo: self.contrato.grupoSC.nroGrupo,
		senial: self.contrato.montoSenialTT.senial,
		tipoTitulo: self.contrato.montoSenialTT.tipoTitulo,
        marcaPerpetuidad: self.getSelector("marcaPerpetuidadCargaGrupoSC").val(),
        pasaLibreria: self.getSelector("pasaLibreriaCargaGrupoSC").val(),
        cantidadTitulos: self.getSelector("cantidadTitulosCargaGrupoSC").val(),
        cantidadPasadasContratadas: ((self.getSelector("cantidadPasadasContratadasCargaGrupoSC").val() != null && self.getSelector("cantidadPasadasContratadasCargaGrupoSC").val() != "") ? self.getSelector("cantidadPasadasContratadasCargaGrupoSC").val() : 0)
	};
	
	var data = {
		async: false,
		action:"altaContratoValidarAmortizacionSC.action", 
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

AltaContratoEvent.prototype.altaGrupoSC = function() {
	var self = altaContratoEvent;
	
	/* Datos que se obtienen de acciones pasadas */
	self.contrato.grupoSC.contrato = self.contrato.numero;
	self.contrato.grupoSC.senial = self.contrato.montoSenialTT.senial;
	self.contrato.grupoSC.tipoTitulo = self.contrato.montoSenialTT.tipoTitulo;
	
	/* Datos ingresados por pantalla */
	self.contrato.grupoSC.tipoDerecho = self.getSelector("codigoComienzoDerechosCargaGrupoSC").val();
	self.contrato.grupoSC.derechoTerrirorial = self.getSelector("codigoDerechosTerritorialesCargaGrupoSC").val();
	self.contrato.grupoSC.tipoImporte = self.getSelector("tipoImporteCargaGrupoSC").val();
	self.contrato.grupoSC.nombreGrupo = self.getSelector("nombreGrupoCargaGrupoSC").val().toUpperCase();
	self.contrato.grupoSC.cantidadTitulos = self.getSelector("cantidadTitulosCargaGrupoSC").val();
	self.contrato.grupoSC.cantidadPasadasContratadas = ((self.getSelector("cantidadPasadasContratadasCargaGrupoSC").val() != null && self.getSelector("cantidadPasadasContratadasCargaGrupoSC").val() != "") ? self.getSelector("cantidadPasadasContratadasCargaGrupoSC").val() : 0);
	self.contrato.grupoSC.marcaPerpetuidad = self.getSelector("marcaPerpetuidadCargaGrupoSC").val();
	self.contrato.grupoSC.estrenoRepeticion = self.getSelector("estrenoRepeticionCargaGrupoSC").val();
	self.contrato.grupoSC.costoUnitario = self.getSelector("costoUnitarioCargaGrupoSC").autoNumericGet();
	self.contrato.grupoSC.autorizacionAutor = ((self.getSelector("autorizacionAutorCargaGrupoSC").val() != null && self.getSelector("autorizacionAutorCargaGrupoSC").val() != "") ? self.getSelector("autorizacionAutorCargaGrupoSC").val() : " ");
	self.contrato.grupoSC.fechaPosibleEntrega = "01/01/0001";
	self.contrato.grupoSC.fechaComienzoDerechos = ((self.getSelector("fechaComienzoDerechosCargaGrupoSC").val() != null && self.getSelector("fechaComienzoDerechosCargaGrupoSC").val() != "") ? self.getSelector("fechaComienzoDerechosCargaGrupoSC").val() : "01/01/0001");
	self.contrato.grupoSC.cantidadTiempo = ((self.getSelector("cantidadTiempoCargaGrupoSC").val() != null && self.getSelector("cantidadTiempoCargaGrupoSC").val() != "") ? self.getSelector("cantidadTiempoCargaGrupoSC").val() : 0);
	self.contrato.grupoSC.unidadTiempo = ((self.getSelector("codigoUnidadTiempoCargaGrupoSC").val() != null && self.getSelector("codigoUnidadTiempoCargaGrupoSC").val() != "") ? self.getSelector("codigoUnidadTiempoCargaGrupoSC").val() : " ");
	self.contrato.grupoSC.planEntrega = ((self.getSelector("planEntregaCargaGrupoSC").val() != null && self.getSelector("planEntregaCargaGrupoSC").val() != "") ? self.getSelector("planEntregaCargaGrupoSC").val() : " ");
	self.contrato.grupoSC.pasaLibreria = self.getSelector("pasaLibreriaCargaGrupoSC").val();
	self.contrato.grupoSC.fechaComienzoDerechosLibreria = ((self.getSelector("fechaComienzoDerechosLibreriaCargaGrupoSC").val() != null && self.getSelector("fechaComienzoDerechosLibreriaCargaGrupoSC").val() != "") ? self.getSelector("fechaComienzoDerechosLibreriaCargaGrupoSC").val() : "01/01/0001");
	self.contrato.grupoSC.cantidadTiempoExclusividad = ((self.getSelector("cantidadTiempoExclusividadCargaGrupoSC").val() != null && self.getSelector("cantidadTiempoExclusividadCargaGrupoSC").val() != "") ? self.getSelector("cantidadTiempoExclusividadCargaGrupoSC").val() : 0);
	self.contrato.grupoSC.unidadTiempoExclusividad = ((self.getSelector("unidadTiempoExclusividadCargaGrupoSC").val() != null && self.getSelector("unidadTiempoExclusividadCargaGrupoSC").val() != "") ? self.getSelector("unidadTiempoExclusividadCargaGrupoSC").val() : " ");
	self.contrato.grupoSC.pagaArgentores = ((self.getSelector("pagaArgentoresCargaGrupoSC").val() != null && self.getSelector("pagaArgentoresCargaGrupoSC").val() != "") ? self.getSelector("pagaArgentoresCargaGrupoSC").val() : " ");
	
	var data = {
		async: false,
		action:"altaContratoAltaGrupoSC.action", 
		request: Component.serialize(self.contrato.grupoSC, "altaGrupoSCRequest"), 
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

AltaContratoEvent.prototype.volverDatosCargaGrupoSC = function() {
	var self = altaContratoEvent;
	//generamos los parametros para poder llamar al popUp Seleccion Tipo Titulo Grupo
	var params = { senial: self.contrato.montoSenialTT.senial, importe : self.contrato.montoSenialTT.monto };
	/* Limpio los datos del grupo y del monto, señal y tipo titulo con el que se estaban trabajando */
	self.contrato.montoSenialTT = new MontoSenialTTPOJO();
	self.contrato.grupoSC = new GrupoSCPOJO();
	
	self.getSelector("formCargaGrupoSC").hide();
	self.getSelector("formCargaContrato").show();
	self.abrirSeleccionTipoTituloGrupo(params);
};

/***********************************************************************************		
 ********************	Pantalla FP11009 a FP11007 - Visualizar	********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirLecturaDatosGrupoSC = function(tipoTitulo) {
	var self = altaContratoEvent;
	self.contrato.montoSenialTT.tipoTitulo = tipoTitulo;
	var grupo = self.recuperarDatosGrupo(self.contrato.grupoSC.nroGrupo);
	Component.get("html/altaContrato/lecturaDatosGrupoSC.html", function(comp) {
		self.drawLecturaDatosGrupoSC(comp, grupo);
	});
};

AltaContratoEvent.prototype.drawLecturaDatosGrupoSC = function(comp, grupo) {
	var self = altaContratoEvent;

	self.getSelector("formVisualizarGruposContrato").hide();
	comp = comp.replace(/{{id}}/g, self.div.id);
	
	if (self.getSelector("formLecturaDatosGrupoSC").length) {
		self.getSelector("formLecturaDatosGrupoSC").replaceWith(comp);
	} else {
		self.getDiv().append(comp);
	}
	/* Accordion */
	Accordion.getInstance(self.getSelector("accordionLecturaDatosGrupoSC"));
	/* Botonera */
	self.getSelector("volverLecturaDatosGrupoSC").button().click(self.volverLecturaDatosGrupoSC);
	self.getSelector("eliminarGrupoLecturaDatosGrupoSC").button().click(function() {
		self.eliminarGrupoContrato(self.contrato.montoSenialTT.tipoTitulo);
	});
	/* Seteo de datos */
	self.getSelector("senialLecturaDatosGrupoSC").html(self.contrato.montoSenialTT.senial);
	self.getSelector("distribuidorLecturaDatosGrupoSC").html(self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("distribuidorLecturaDatosGrupoSC").attr("title", self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("montoTotalLecturaDatosGrupoSC").html(self.contrato.montoSenialTT.monto);
	self.getSelector("nroGrupoLecturaDatosGrupoSC").html(self.contrato.grupoSC.nroGrupo);
	self.getSelector("tipoTituloLecturaDatosGrupoSC").html(self.contrato.montoSenialTT.tipoTitulo);
	self.situarSenialesHeredadasReadOnly();
	self.getSelector("nombreGrupoLecturaDatosGrupoSC").html(grupo.nombreGrupo);
	self.getSelector("cantidadTitulosLecturaDatosGrupoSC").html(grupo.cantidadTitulos);
	self.getSelector("cantidadPasadasContratadasLecturaDatosGrupoSC").html(grupo.cantidadPasadasContratadas);
	self.getSelector("tipoImporteLecturaDatosGrupoSC").html(grupo.tipoImporte);
	self.getSelector("costoUnitarioLecturaDatosGrupoSC").html(grupo.costoUnitario);
	self.getSelector("estrenoRepeticionLecturaDatosGrupoSC").html(grupo.estrenoRepeticion);
	
    var dateSplit = grupo.fechaComienzoDerechos.substring(0,10).split("-");
    var objDate = new Date(dateSplit[0] + " " + dateSplit[2] + " " + dateSplit[1]);
    //self.getSelector("fechaComienzoDerechosLecturaDatosGrupoSC").datepicker('setDate', objDate);
    self.getSelector("fechaComienzoDerechosLecturaDatosGrupoSC").html($.datepicker.formatDate('dd/mm/yy', objDate));

    dateSplit = grupo.fechaComienzoDerechosLibreria.substring(0,10).split("-");
    objDate = new Date(dateSplit[0] + " " + dateSplit[2] + " " + dateSplit[1]);
    //self.getSelector("fechaComienzoDerechosLibreriaLecturaDatosGrupoSC").datepicker('setDate', objDate);
    self.getSelector("fechaComienzoDerechosLibreriaLecturaDatosGrupoSC").html($.datepicker.formatDate('dd/mm/yy', objDate));

	self.getSelector("codigoComienzoDerechosLecturaDatosGrupoSC").html(grupo.tipoDerecho);
	self.getSelector("cantidadTiempoLecturaDatosGrupoSC").html(grupo.cantidadTiempo);
	self.getSelector("codigoUnidadTiempoLecturaDatosGrupoSC").html(grupo.unidadTiempo);
	self.getSelector("codigoDerechosTerritorialesLecturaDatosGrupoSC").html(grupo.derechoTerrirorial);
	self.getSelector("marcaPerpetuidadLecturaDatosGrupoSC").html(grupo.marcaPerpetuidad);
	self.getSelector("planEntregaLecturaDatosGrupoSC").html(grupo.planEntrega);
	self.getSelector("cantidadTiempoExclusividadLecturaDatosGrupoSC").html(grupo.cantidadTiempoExclusividad);
	self.getSelector("unidadTiempoExclusividadLecturaDatosGrupoSC").html(grupo.unidadTiempoExclusividad);
	self.getSelector("autorizacionAutorLecturaDatosGrupoSC").html(grupo.autorizacionAutor);
	self.getSelector("pagaArgentoresLecturaDatosGrupoSC").html(grupo.pagaArgentores);
	self.getSelector("pasaLibreriaLecturaDatosGrupoSC").html(grupo.pasaLibreria);
};

AltaContratoEvent.prototype.volverLecturaDatosGrupoSC = function() {
	var self = altaContratoEvent;
	self.getSelector("formLecturaDatosGrupoSC").hide();
	self.getSelector("formVisualizarGruposContrato").show();
};

/***********************************************************************************		
 ********************	Pantalla FP11009 a FP11007 - Modificar	********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirModificacionDatosGrupoSC = function() {
	var self = altaContratoEvent;
	var grupo = self.recuperarDatosGrupo(self.contrato.grupoSC.nroGrupo);
	setTimeout(function() {
		Component.get("html/altaContrato/cargaDatosGrupoSC.html", function(comp) {
			self.drawModificacionDatosGrupoSC(comp, grupo);
		});
	}, 750);
};

AltaContratoEvent.prototype.drawModificacionDatosGrupoSC = function(comp, grupo) {
	var self = altaContratoEvent;

	self.getSelector("formVisualizarGruposContrato").hide();
	comp = comp.replace(/{{id}}/g, self.div.id);
	
	if (self.getSelector("formCargaGrupoSC").length) {
		self.getSelector("formCargaGrupoSC").replaceWith(comp);
	} else {
		self.getDiv().append(comp);
	}
	/* Accordion */
	Accordion.getInstance(self.getSelector("accordionCargaGrupoSC"));
	/* Botonera */
	self.getSelector("volverDatosCargaGrupoSC").button().click(self.volverModificacionDatosGrupoSC);
	self.getSelector("aceptarDatosCargaGrupoSC").button().click(function() {
		self.validarDatosGrupoSC(self.validarCambioTipoImporteSC);
	});
	/* Seteo de datos output */
	self.getSelector("senialCargaGrupoSC").html(self.contrato.montoSenialTT.senial);
	self.getSelector("distribuidorCargaGrupoSC").html(self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("distribuidorCargaGrupoSC").attr("title", self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("montoTotalCargaGrupoSC").html(self.contrato.montoSenialTT.monto);
	self.getSelector("nroGrupoCargaGrupoSC").html(self.contrato.grupoSC.nroGrupo);
	self.getSelector("tipoTituloCargaGrupoSC").html(self.contrato.montoSenialTT.tipoTitulo);
	/* Situar señales heredadas */
	self.situarSenialesHeredadasSC();
	/* Nombre grupo */
	self.getSelector("nombreGrupoCargaGrupoSC").val(grupo.nombreGrupo);
	/* Cantidad de títulos */	
	self.getSelector("cantidadTitulosCargaGrupoSC").numeric();
	if (grupo.cantidadTitulos > 0) {
		self.getSelector("cantidadTitulosCargaGrupoSC").val(grupo.cantidadTitulos);
	}
	/* Cantidad de pasadas contratadas */
	self.getSelector("cantidadPasadasContratadasCargaGrupoSC").numeric();
	if (grupo.cantidadPasadasContratadas > 0) {
		self.getSelector("cantidadPasadasContratadasCargaGrupoSC").val(grupo.cantidadPasadasContratadas);
	}
	/* Situar tipos importe */
	self.buscarTiposImporte("tipoImporteCargaGrupoSC");
	self.getSelector("tipoImporteCargaGrupoSC").val(grupo.tipoImporte);
	/* Costo unitario */
	self.getSelector("costoUnitarioCargaGrupoSC").autoNumeric({vMax: '999999999.99'});
	if (grupo.costoUnitario > 0) {
		self.getSelector("costoUnitarioCargaGrupoSC").val(grupo.costoUnitario);
	}
	/* Estreno / Repetición */
	Component.populateSelect(self.getSelector("estrenoRepeticionCargaGrupoSC"), AltaContratoEvent.selectER, "codigo", "descripcion", false);
	self.getSelector("estrenoRepeticionCargaGrupoSC")[0].removeChild(self.getSelector("estrenoRepeticionCargaGrupoSC")[0].options[0]);
	self.getSelector("estrenoRepeticionCargaGrupoSC").val(grupo.estrenoRepeticion);
	/* Fecha comienzo derechos territoriales */
    if ((new Date(grupo.fechaComienzoDerechos).getFullYear() != 1) && (new Date(grupo.fechaComienzoDerechos).getFullYear() != 0) && (new Date(grupo.fechaComienzoDerechos).getFullYear() != 1900) && (new Date(grupo.fechaComienzoDerechos).getFullYear() != 1901) && (new Date(grupo.fechaComienzoDerechos).getFullYear() != 1)) {
	    var dateSplit = grupo.fechaComienzoDerechos.substring(0,10).split("-");
	    var objDate = new Date(dateSplit[0] + " " + dateSplit[1] + " " + dateSplit[2]);
	    //self.getSelector("fechaComienzoDerechosCargaGrupoSC").datepicker('setDate', objDate);
		Datepicker.getInstance(self.getSelector("fechaComienzoDerechosCargaGrupoSC"), objDate);
	} else {
		Datepicker.getInstance(self.getSelector("fechaComienzoDerechosCargaGrupoSC"));
	}
	/* Situar tipos derecho */
	self.buscarTiposDerecho("codigoComienzoDerechosCargaGrupoSC");
	self.getSelector("codigoComienzoDerechosCargaGrupoSC").val(grupo.tipoDerecho);
	/* Cantidad tiempo */
	self.getSelector("cantidadTiempoCargaGrupoSC").numeric();
	if (grupo.cantidadTiempo > 0) {
		self.getSelector("cantidadTiempoCargaGrupoSC").val(grupo.cantidadTiempo);
	}
	/* Unidad tiempo: debe tener la opcion 'vacio' */
	Component.populateSelect(self.getSelector("codigoUnidadTiempoCargaGrupoSC"), AltaContratoEvent.selectUnidadTiempo, "codigo", "descripcion", false);
	self.getSelector("codigoUnidadTiempoCargaGrupoSC").val(grupo.unidadTiempo);
	/* Situar tipos derecho territorial */
	self.buscarTiposDerechoTerritorial("codigoDerechosTerritorialesCargaGrupoSC");
	self.getSelector("codigoDerechosTerritorialesCargaGrupoSC").val(grupo.derechoTerrirorial);
	/* Marca de perpetuidad */
	Component.populateSelect(self.getSelector("marcaPerpetuidadCargaGrupoSC"), AltaContratoEvent.selectSN, "codigo", "descripcion", false);
	//self.getSelector("marcaPerpetuidadCargaGrupoSC")[0].removeChild(self.getSelector("marcaPerpetuidadCargaGrupoSC")[0].options[0]);
	self.getSelector("marcaPerpetuidadCargaGrupoSC").val(grupo.marcaPerpetuidad);
	/* Plan de entrega */
	if (grupo.planEntrega.trim() != "") {
		self.getSelector("planEntregaCargaGrupoSC").val(grupo.planEntrega);
	}
	/* Cantidad tiempo exclusividad */
	if (grupo.cantidadTiempoExclusividad > 0) {
		self.getSelector("cantidadTiempoExclusividadCargaGrupoSC").numeric();
		self.getSelector("cantidadTiempoExclusividadCargaGrupoSC").val(grupo.cantidadTiempoExclusividad);
	}
	/* Unidad tiempo exclusividad: debe tener la opción 'vacio' */
	Component.populateSelect(self.getSelector("unidadTiempoExclusividadCargaGrupoSC"), AltaContratoEvent.selectUnidadTiempo, "codigo", "descripcion", false);
	self.getSelector("unidadTiempoExclusividadCargaGrupoSC").val(grupo.unidadTiempoExclusividad);
	/* Autorización autor: debe tener la opción 'vacio' */
	Component.populateSelect(self.getSelector("autorizacionAutorCargaGrupoSC"), AltaContratoEvent.selectSN, "codigo", "descripcion", false);
	//self.getSelector("autorizacionAutorCargaGrupoSC")[0].removeChild(self.getSelector("autorizacionAutorCargaGrupoSC")[0].options[0]);
	self.getSelector("autorizacionAutorCargaGrupoSC").val(grupo.autorizacionAutor);
	/* Paga argentores: debe tener la opción 'vacio' */
	Component.populateSelect(self.getSelector("pagaArgentoresCargaGrupoSC"), AltaContratoEvent.selectSN, "codigo", "descripcion", false);
	//self.getSelector("pagaArgentoresCargaGrupoSC")[0].removeChild(self.getSelector("pagaArgentoresCargaGrupoSC")[0].options[0]);
	self.getSelector("pagaArgentoresCargaGrupoSC").val(grupo.pagaArgentores);
	/* Pasa librería */
	Component.populateSelect(self.getSelector("pasaLibreriaCargaGrupoSC"), AltaContratoEvent.selectSN, "codigo", "descripcion", false);
	//self.getSelector("pasaLibreriaCargaGrupoSC")[0].removeChild(self.getSelector("pasaLibreriaCargaGrupoSC")[0].options[0]);
	self.getSelector("pasaLibreriaCargaGrupoSC").val(grupo.pasaLibreria);
	/* Fecha comienzo derechos librería */
	if ((new Date(grupo.fechaComienzoDerechosLibreria).getFullYear() != 1) && (new Date(grupo.fechaComienzoDerechosLibreria).getFullYear() != 0) && (new Date(grupo.fechaComienzoDerechosLibreria).getFullYear() != 1900) && (new Date(grupo.fechaComienzoDerechosLibreria).getFullYear() != 1901) ){
        var dateSplit = grupo.fechaComienzoDerechosLibreria.substring(0,10).split("-");
        var objDate = new Date(dateSplit[0] + " " + dateSplit[1] + " " + dateSplit[2]);
		Datepicker.getInstance(self.getSelector("fechaComienzoDerechosLibreriaCargaGrupoSC"), objDate);
	} else {
		Datepicker.getInstance(self.getSelector("fechaComienzoDerechosLibreriaCargaGrupoSC"));
	}
	
	/* Datos a comparar */
	self.contrato.grupoSC.tipoImporte = grupo.tipoImporte;
	self.contrato.grupoSC.pasaLibreria = grupo.pasaLibreria;
	self.contrato.grupoSC.cantidadTitulos = grupo.cantidadTitulos;
	self.contrato.grupoSC.cantidadPasadasContratadas = grupo.cantidadPasadasContratadas;
	
	/* Desbloquear pantalla */
	BLOCK.hideBlock();
};

/***********************************************************************************		
 ******************** Pantalla FP11009 a FP11007 - Validaciones ********************
 ***********************************************************************************/
AltaContratoEvent.prototype.validarCambioTipoImporteSC = function() {
	var self = altaContratoEvent;
	
	var validarCambioTipoImporteSCRequest = {
		contrato: self.contrato.numero,
		grupo: self.contrato.grupoSC.nroGrupo,
		senial: self.contrato.montoSenialTT.senial,
		tipoImporte: self.getSelector("tipoImporteCargaGrupoSC").val(),
		tipoTitulo: self.contrato.montoSenialTT.tipoTitulo,
		marcaPerpetuidad: self.getSelector("marcaPerpetuidadCargaGrupoSC").val(),
		cantidadPasadas: ((self.getSelector("cantidadPasadasContratadasCargaGrupoSC").val() != null && self.getSelector("cantidadPasadasContratadasCargaGrupoSC").val() != "") ? self.getSelector("cantidadPasadasContratadasCargaGrupoSC").val() : 0),
		pasaLibreria: self.getSelector("pasaLibreriaCargaGrupoSC").val()
	};
	
	var data = {
		async: false,
		action:"altaContratoValidarCambioTipoImporteSC.action", 
		request: Component.serialize(validarCambioTipoImporteSCRequest, "validarCambioTipoImporteSCRequest"), 
		method: "GET", 
		responseObject: "validarCambioTipoImporteSCResponse", 
		callback: function(validarCambioTipoImporteSCResponse) {
			/* Si hay errores, mostrar mensaje. Sino, seguir con las validaciones. */
			if (validarCambioTipoImporteSCResponse.hayErrores) {
				MESSAGE.alert(validarCambioTipoImporteSCResponse.mensaje);	
			} else {
				self.validarPasaLibreriaNaSSC();
			}
		}
	};
	self.service.doRequest(data);		
};

AltaContratoEvent.prototype.validarPasaLibreriaNaSSC = function() {
	var self = altaContratoEvent;
	
	var validarPasaLibreriaNaSSCRequest = {
		contrato: self.contrato.numero,
		grupo: self.contrato.grupoSC.nroGrupo,
		senial: self.contrato.montoSenialTT.senial,
		tipoTitulo: self.contrato.montoSenialTT.tipoTitulo,
		marcaPerpetuidad: self.getSelector("marcaPerpetuidadCargaGrupoSC").val(),
		cantidadPasadas: ((self.getSelector("cantidadPasadasContratadasCargaGrupoSC").val() != null && self.getSelector("cantidadPasadasContratadasCargaGrupoSC").val() != "") ? self.getSelector("cantidadPasadasContratadasCargaGrupoSC").val() : 0),
		pasaLibreria: self.getSelector("pasaLibreriaCargaGrupoSC").val()
	};
	
	var data = {
		async: false,
		action:"altaContratoValidarPasaLibreriaNaSSC.action", 
		request: Component.serialize(validarPasaLibreriaNaSSCRequest, "validarPasaLibreriaNaSSCRequest"), 
		method: "GET", 
		responseObject: "validarPasaLibreriaNaSSCResponse", 
		callback: function(validarPasaLibreriaNaSSCResponse) {
			/* Si hay errores, mostrar mensaje. Sino, seguir con las validaciones. */
			if (validarPasaLibreriaNaSSCResponse.hayErrores) {
				MESSAGE.alert(validarPasaLibreriaNaSSCResponse.mensaje);	
			} else {
				self.validarCantidadPasadasContratadasSC();
			}
		}
	};
	self.service.doRequest(data);	
};

AltaContratoEvent.prototype.validarCantidadPasadasContratadasSC = function() {
	var self = altaContratoEvent;
	
	var validarCantidadPasadasContratadasSCRequest = {
		contrato: self.contrato.numero,
		grupo: self.contrato.grupoSC.nroGrupo,
		senial: self.contrato.montoSenialTT.senial,
		tipoImporte: self.getSelector("tipoImporteCargaGrupoSC").val(),
		tipoTitulo: self.contrato.montoSenialTT.tipoTitulo,
		marcaPerpetuidad: self.getSelector("marcaPerpetuidadCargaGrupoSC").val(),
		cantidadPasadas: ((self.getSelector("cantidadPasadasContratadasCargaGrupoSC").val() != null && self.getSelector("cantidadPasadasContratadasCargaGrupoSC").val() != "") ? self.getSelector("cantidadPasadasContratadasCargaGrupoSC").val() : 0),
		pasaLibreria: self.getSelector("pasaLibreriaCargaGrupoSC").val()
	};
	
	var data = {
		async: false,
		action:"altaContratoValidarCantidadPasadasContratadasSC.action", 
		request: Component.serialize(validarCantidadPasadasContratadasSCRequest, "validarCantidadPasadasContratadasSCRequest"), 
		method: "GET", 
		responseObject: "validarCantidadPasadasContratadasSCResponse", 
		callback: function(validarCantidadPasadasContratadasSCResponse) {
			/* Si hay errores, mostrar mensaje. Sino, seguir con las validaciones. */
			if (validarCantidadPasadasContratadasSCResponse.hayErrores) {
				MESSAGE.alert(validarCantidadPasadasContratadasSCResponse.mensaje);	
			} else {
				self.validarRerunSC();
			}
		}
	};
	self.service.doRequest(data);	
};

/*FIXME*/
AltaContratoEvent.prototype.validarRerunSC = function() {
	var self = altaContratoEvent;
	
	var validarRerunSCRequest = {
		contrato : self.contrato.numero,
	    grupo: self.contrato.grupoSC.nroGrupo,
	    senial: self.contrato.montoSenialTT.senial
	};
	
	var data = {
		async: false,
		action:"altaContratoValidarRerunSC.action", 
		request: Component.serialize(validarRerunSCRequest, "validarRerunSCRequest"), 
		method: "GET", 
		responseObject: "validarRerunSCResponse", 
		callback: function(validarRerunSCResponse) {
			/* Si hay errores, mostrar mensaje de confirmación. Sino, seguir con las validaciones. */
			if (validarRerunSCResponse.hayErrores) {
				self.abrirPopupTitulosDesenlaceSC(validarRerunSCResponse.titulos);
			} else {
				/* Llamar a la validación final de modificacion de grupo SC, con enlazar nueva vigencia en false. */
				self.validarModificacionDatosGrupoSC('N');
			}
		}
	};
	self.service.doRequest(data);	
};

/***********************************************************************************		
 ********************	Pantalla FP11007 - Validaciones rerun	********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirPopupTitulosDesenlaceSC = function(titulos) {
	var self = altaContratoEvent;
	Component.get("html/altaContrato/popupTitulosDesenlaceSC.html", function(comp) {
		self.drawPopupTitulosDesenlaceSC(comp, titulos);
	});
};

AltaContratoEvent.prototype.drawPopupTitulosDesenlaceSC = function(comp, titulos) {
	var self = altaContratoEvent;
	$("#popupTitulosDesenlaceSC").dialog({
		width: 600, show: 'blind', hide: 'blind', modal: true, autoOpen: false,
		buttons: {
			"Salir": function() {
				$(this).dialog("close");
			}
		}
	});
	$("#popupTitulosDesenlaceSC").empty().append(comp.replace(/{{id}}/g, self.div.id));
	$("#popupTitulosDesenlaceSC").dialog("open");
	
	/* Botonera */
	self.getSelector("confirmarTitulosDesenlaceSC").button().click(self.validarDesenlaceSC);
	self.getSelector("rechazarTitulosDesenlaceSC").button().click(self.cierrePopupTitulosDesenlaceSC);
	
	self.drawGridDatosDesenlace(titulos);
};

AltaContratoEvent.prototype.drawGridTitulosDesenlaceSC = function(titulos) {
	var self = altaContratoEvent;
	
	self.getSelector("gridTitulosDesenlaceSC").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Título'],
		colModel:[ 
		    {name:'titulo', 		index:'titulo', align:'center', formatter:function(value, options, rData) { 
		    	return rData['tipoTitulo'] + rData['numeroTitulo'];
		    }}
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + this.div.id + '_pagerTitulosDesenlaceSC',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Rerun'
	});
	
	self.getSelector("gridTitulosDesenlaceSC").clearGridData().setGridParam({data: titulos}).trigger('reloadGrid');
};

AltaContratoEvent.prototype.cierrePopupTitulosDesenlaceSC = function() {
	$("#popupTitulosDesenlaceSC").dialog("close");
	$("#popupTitulosDesenlaceSC").dialog("remove");
};

AltaContratoEvent.prototype.validarDesenlaceSC = function() {
	var self = altaContratoEvent;
	
	var validarDesenlaceSCRequest = {
		contrato : self.contrato.numero,
	    grupo: self.contrato.grupoSC.nroGrupo,
	    senial: self.contrato.montoSenialTT.senial
	};
	
	var data = {
		async: false,
		action:"altaContratoValidarDesenlaceSC.action", 
		request: Component.serialize(validarDesenlaceSCRequest, "validarDesenlaceSCRequest"), 
		method: "GET", 
		responseObject: "validarDesenlaceSCResponse", 
		callback: function(validarDesenlaceSCResponse) {
			if (validarDesenlaceSCResponse.hayErrores) {
				var params = {
					mensaje: "Errores que se producen al desenlazar",
					datosDesenlace: validarDesenlaceSCResponse.erroresDesenlace,
					showBotonera: false
				};
				self.abrirPopupDatosDesenlace(params);
			} else if (validarDesenlaceSCResponse.erroresDesenlace) {
				var params = {
					mensaje: "Errores que se producen al desenlazar",
					datosDesenlace: validarDesenlaceSCResponse.erroresDesenlace,
					showBotonera: true,
					callback: self.validarModificacionDatosGrupoSC
				};
				self.abrirPopupDatosDesenlace(params);
			} else {
				/* Llamar a la validación final de modificacion de grupo SC, con enlazar nueva vigencia en true. */
				self.validarModificacionDatosGrupoSC('S');
			}
		}
	};
	self.service.doRequest(data);	
};

/***********************************************************************************		
 ********************		Pantalla Errores Modificación		********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirPopupValidacionGrupo = function(params) {
	var self = altaContratoEvent;
	Component.get("html/altaContrato/popupValidacionGrupo.html", function(comp) {
		self.drawPopupValidacionGrupo(comp, params);
	});
};

AltaContratoEvent.prototype.drawPopupValidacionGrupo = function(comp, params) {
	var self = altaContratoEvent;
	
	$("#popupValidacionGrupo").dialog({
		width: 800, show: 'blind', hide: 'blind', modal: true, autoOpen: false,
		buttons: {
			"Salir": function() {
				$(this).dialog("close");
			}
		}
	});
	$("#popupValidacionGrupo").empty().append(comp.replace(/{{id}}/g, self.div.id));
	$("#popupValidacionGrupo").dialog("open");
	
	/* Cargo la grilla con los datos del desenlace */
	self.drawGridValidacionGrupo(params);
};

AltaContratoEvent.prototype.drawGridValidacionGrupo = function(params) {
	var self = altaContratoEvent;
	
	self.getSelector("gridValidacionGrupo").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Tipo error', 'Descripción'],
		colModel:[ 
		    {name:'tipoError', 		index:'tipoError', 		align:'center'},
		    {name:'descripcion', 	index:'descripcion', 	align:'center'}
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + this.div.id + '_pagerValidacionGrupo',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Datos'
	});
	
	self.getSelector("gridValidacionGrupo").clearGridData().setGridParam({data: params}).trigger('reloadGrid');
};

AltaContratoEvent.prototype.cierrePopupValidacionGrupo = function() {
	$("#popupValidacionGrupo").dialog("close");
	$("#popupValidacionGrupo").dialog("remove");
};

AltaContratoEvent.prototype.validarModificacionDatosGrupoSC = function(enlazarNuevaVigencia) {
	var self = altaContratoEvent;
	
	var validarModificacionGrupoSCRequest = {
		contrato : self.contrato.numero,
	    grupo: self.contrato.grupoSC.nroGrupo,
	    senial: self.contrato.montoSenialTT.senial,
	    tipoTitulo: self.contrato.montoSenialTT.tipoTitulo,
	    tipoImporte: self.getSelector("tipoImporteCargaGrupoSC").val(),
	    idTipoDerecho: self.getSelector("codigoComienzoDerechosCargaGrupoSC").val(),
	    fechaComienzoDerechos: ((self.getSelector("fechaComienzoDerechosCargaGrupoSC").val() != null && self.getSelector("fechaComienzoDerechosCargaGrupoSC").val() != "") ? self.getSelector("fechaComienzoDerechosCargaGrupoSC").val() : "01/01/0001"),
	    cantidadTiempo: ((self.getSelector("cantidadTiempoCargaGrupoSC").val() != null && self.getSelector("cantidadTiempoCargaGrupoSC").val() != "") ? self.getSelector("cantidadTiempoCargaGrupoSC").val() : 0),
	    unidadTiempo: ((self.getSelector("codigoUnidadTiempoCargaGrupoSC").val() != null && self.getSelector("codigoUnidadTiempoCargaGrupoSC").val() != "") ? self.getSelector("codigoUnidadTiempoCargaGrupoSC").val() : " "),
	    marcaPerpetuidad: self.getSelector("marcaPerpetuidadCargaGrupoSC").val(),
	    cantidadTitulos: self.getSelector("cantidadTitulosCargaGrupoSC").val(),
	    enlazarNuevaVigencia : enlazarNuevaVigencia
	};

	var data = {
		async: false,
		action:"altaContratoValidarModificacionGrupoSC.action", 
		request: Component.serialize(validarModificacionGrupoSCRequest, "validarModificacionGrupoSCRequest"), 
		method: "GET", 
		responseObject: "validarModificacionGrupoSCResponse", 
		callback: function(validarModificacionGrupoSCResponse) {
			if (validarModificacionGrupoSCResponse.hayErrores) {
				self.abrirPopupValidacionGrupo(validarModificacionGrupoSCResponse.errores);
			} else {
				self.eliminarConsultarSC();
				self.modificacionGrupoSC();				
			};
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.eliminarConsultarSC = function() {
	var self = altaContratoEvent;
	
	var eliminarConsultarSCRequest = {
		contrato: self.contrato.numero,
		grupo: self.contrato.grupoSC.nroGrupo,
		senial: self.contrato.montoSenialTT.senial,
	    tipoTitulo: self.contrato.montoSenialTT.tipoTitulo,
	    nroTitulo: 0	
	};
	
	var data = {
		async: false,
		action:"altaContratoEliminarConsultarSC.action", 
		request: Component.serialize(eliminarConsultarSCRequest, "eliminarConsultarSCRequest"), 
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

AltaContratoEvent.prototype.modificacionGrupoSC = function() {
	var self = altaContratoEvent;
	
	var modificacionGrupoSCRequest = { 
		contrato: self.contrato.numero,
	    grupo: self.contrato.grupoSC.nroGrupo,
	    senial: self.contrato.montoSenialTT.senial,
	    tipoTitulo: self.contrato.montoSenialTT.tipoTitulo,
	    tipoImporte: self.getSelector("tipoImporteCargaGrupoSC").val(),
	    idTipoDerecho: self.getSelector("codigoComienzoDerechosCargaGrupoSC").val(),
	    fechaComienzoDerechos: ((self.getSelector("fechaComienzoDerechosCargaGrupoSC").val() != null && self.getSelector("fechaComienzoDerechosCargaGrupoSC").val() != "") ? self.getSelector("fechaComienzoDerechosCargaGrupoSC").val() : "01/01/0001"),
	    cantidadTiempo: ((self.getSelector("cantidadTiempoCargaGrupoSC").val() != null && self.getSelector("cantidadTiempoCargaGrupoSC").val() != "") ? self.getSelector("cantidadTiempoCargaGrupoSC").val() : 0),
	    unidadTiempo: ((self.getSelector("codigoUnidadTiempoCargaGrupoSC").val() != null && self.getSelector("codigoUnidadTiempoCargaGrupoSC").val() != "") ? self.getSelector("codigoUnidadTiempoCargaGrupoSC").val() : " "),
	    marcaPerpetuidad: self.getSelector("marcaPerpetuidadCargaGrupoSC").val(),
	    importeUnitario: self.getSelector("costoUnitarioCargaGrupoSC").autoNumericGet(),
	    importeGrupo: self.contrato.montoSenialTT.monto,
	    pasaLibreria: self.getSelector("pasaLibreriaCargaGrupoSC").val(),
	    cantidadTitulos: self.getSelector("cantidadTitulosCargaGrupoSC").val(),
	    cantidadPasadas: ((self.getSelector("cantidadPasadasContratadasCargaGrupoSC").val() != null && self.getSelector("cantidadPasadasContratadasCargaGrupoSC").val() != "") ? self.getSelector("cantidadPasadasContratadasCargaGrupoSC").val() : 0),
	    nombreGrupo: self.getSelector("nombreGrupoCargaGrupoSC").val(),
	    ER: self.getSelector("estrenoRepeticionCargaGrupoSC").val(),
	    derechosTerritoriales: self.getSelector("codigoDerechosTerritorialesCargaGrupoSC").val(),
	    cantidadTiempoExclusivo: ((self.getSelector("cantidadTiempoExclusividadCargaGrupoSC").val() != null && self.getSelector("cantidadTiempoExclusividadCargaGrupoSC").val() != "") ? self.getSelector("cantidadTiempoExclusividadCargaGrupoSC").val() : 0),
	    unidadTiempoExclusivo: self.getSelector("unidadTiempoExclusividadCargaGrupoSC").val(),
	    autorizacionAutor: self.getSelector("autorizacionAutorCargaGrupoSC").val(),
	    pagaArgentores: self.getSelector("pagaArgentoresCargaGrupoSC").val(),
	    fechaComienzoLibreria: ((self.getSelector("fechaComienzoDerechosLibreriaCargaGrupoSC").val() != null && self.getSelector("fechaComienzoDerechosLibreriaCargaGrupoSC").val() != "") ? self.getSelector("fechaComienzoDerechosLibreriaCargaGrupoSC").val() : "01/01/0001") 
	};
	
	var data = {
		async: false,
		action:"altaContratoModificacionGrupoSC.action", 
		request: Component.serialize(modificacionGrupoSCRequest, "modificacionGrupoSCRequest"), 
		method: "POST", 
		responseObject: "modificacionGrupoSCResponse", 
		callback: function(modificacionGrupoSCResponse) {
			if (modificacionGrupoSCResponse.hayErrores) {
				/* Abro el warning con los datos de los títulos */
				var params = {
					mensaje: modificacionGrupoSCResponse.mensaje,
					datosDesenlace: modificacionGrupoSCResponse.erroresRerun
				};
				self.abrirPopupWarningsModificacionGrupo(params);
				
				/* Guardo los titulos para más adelante */
				self.contrato.titulosRecontratar = modificacionGrupoSCResponse.erroresRerun;
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
				/* Abrir FP11015 */
				self.getSelector("formCargaGrupoSC").hide();
				self.abrirSeleccionTituloRecontratacionEnlaces("FP11007");
			} else {
				MESSAGE.ok("Se modificó el grupo con éxito");

				if (self.contrato.grupoSC.cantidadTitulos > self.getSelector("cantidadTitulosCargaGrupoSC").val()) {
					/* Actualizo la cantidad nueva de títulos */
					self.contrato.grupoSC.cantidadTitulos = self.getSelector("cantidadTitulosCargaGrupoSC").val();
					/* Disminuyó la cantidad de originales - Redirigir a FP11020 */
					self.getSelector("formCargaGrupoSC").hide();
					self.abrirEliminacionTituloContrato();
				} else if (self.contrato.grupoSC.cantidadTitulos < self.getSelector("cantidadTitulosCargaGrupoSC").val()) {
					/* Seteo el contador de títulos ingresados */
					self.contrato.montoSenialTT.titulosIngresados = self.contrato.grupoSC.cantidadTitulos;
					/* Actualizo la cantidad nueva de títulos */
					self.contrato.grupoSC.cantidadTitulos = self.getSelector("cantidadTitulosCargaGrupoSC").val();
					/* Incrementó la cantidad de originales - Redirigir a FP11010 */
					self.getSelector("formCargaGrupoSC").hide();
					self.abrirVisualizarTitulosGrupo();
				} else {
				    self.buscarGruposContrato();
					self.volverModificacionDatosGrupoSC();	
				}
			}
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.volverModificacionDatosGrupoSC = function() {
	var self = altaContratoEvent;
	self.getSelector("formCargaGrupoSC").hide();
	self.getSelector("formVisualizarGruposContrato").show();
};