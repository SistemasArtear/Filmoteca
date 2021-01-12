/**
 * FP110 - Alta de contrato
 * @author cetorres
 */
function AltaContratoEvent(div) {
	this.div = div;
	this.contrato = new ContratoPOJO();
};

extend(AltaContratoEvent, Plugin);

AltaContratoEvent.CC = "CC";
AltaContratoEvent.SC = "SC";

AltaContratoEvent.selectTipoContrato = [ 
	{codigo: "S", descripcion: "Canje"},
	{codigo: "N", descripcion: "No canje"},
	{codigo: "F", descripcion: "Factura"}
];

AltaContratoEvent.selectSN = [ 
	{codigo: "S", descripcion: "S"},
	{codigo: "N", descripcion: "N"}
];

AltaContratoEvent.selectER = [ 
	{codigo: "E", descripcion: "Estreno"},
	{codigo: "R", descripcion: "Repetición"}
];

AltaContratoEvent.selectUnidadTiempo = [ 
	{codigo: "D", descripcion: "Día"},
	{codigo: "M", descripcion: "Mes"},
	{codigo: "A", descripcion: "Año"}
];

function ContratoPOJO() {
	this.numero = new Number();
	this.cabecera = {
		proveedor : new Number(),
		moneda: new String(),
		fechaContrato: new Date(),
		fechaAprobacion: new Date(),
		montoTotal: new Number(),
		canje: new String(),
		observaciones: new String()
	};
	this.montoSenialTT = new MontoSenialTTPOJO();
	this.marcaDesenlaceGrupo = new Boolean();
	this.grupoCC = new GrupoCCPOJO();
	this.grupoSC = new GrupoSCPOJO();
	this.tituloSeleccionado;
	this.tituloRecontratacionSeleccionado;
	/* FP11005 / 07 */
	this.titulosRecontratar = new Array();
	this.tituloRecontratar;
	/* FP11013 */
	this.capitulosConfirmados = new Array();
	this.capitulosConfirmadosRM = new Array();
	/* FP11014 */
	this.capituloTituloRecontratacionSeleccionado;
	this.capituloConfirmado;
	this.capituloConfirmadoRM;
};

function MontoSenialTTPOJO () {
	this.monto = new Number();
	this.senial = new String();
	this.tipoTitulo = new String();
	this.titulosIngresados = new Number();
};

function GrupoCCPOJO() {
	this.contrato = new Number();
	this.senial = new String();
	this.nroGrupo = new Number();
	this.tipoTitulo = new String();
	this.nombreGrupo = new String();
	this.cantidadOriginales = new Number();
	this.cantidadRepeticiones = new Number();
	this.tipoImporte = new String();
	this.costoTotal = new Number();
	this.estrenoRepeticion = new String();
	this.fechaComienzoDerechos = new Date();
	this.fechaPosibleEntrega = new Date();
	this.tipoDerecho = new String();
	this.cantidadTiempo = new Number();
	this.unidadTiempo = new String();
	this.derechoTerrirorial = new String();
	this.marcaPerpetuidad = new String();
	this.planEntrega = new String();
	this.recontratacion = new String();
	this.cantidadTiempoExclusividad = new Number();
	this.unidadTiempoExclusividad = new String();
	this.autorizacionAutor = new String();
	this.pagaArgentores = new String();
	this.pasaLibreria = new String();
	this.fechaComienzoDerechosLibreria = new Date();
	this.primerIdAmortizacion = new Number();
	this.segundoIdAmortizacion = new Number();
};

function GrupoSCPOJO() {
	this.contrato = new Number();
	this.senial = new String();
	this.nroGrupo = new Number();
	this.tipoTitulo = new String();
	this.nombreGrupo = new String();
	this.cantidadTitulos = new Number();
	this.cantidadPasadasContratadas = new Number();
	this.tipoImporte = new String();
	this.costoUnitario = new Number();
	this.estrenoRepeticion = new String();
	this.fechaComienzoDerechos = new Date();
	this.tipoDerecho = new String();
	this.cantidadTiempo = new Number();
	this.unidadTiempo = new String();
	this.derechoTerrirorial = new String();
	this.marcaPerpetuidad = new String();
	this.planEntrega = new String();
	this.cantidadTiempoExclusividad = new Number();
	this.unidadTiempoExclusividad = new String();
	this.autorizacionAutor = new String();
	this.pagaArgentores = new String();
	this.pasaLibreria = new String();
	this.fechaComienzoDerechosLibreria = new Date();
};

/***********************************************************************************		
 ********************				Pantalla FP11001			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.create = function() {
	/* Bloquear pantalla */
	BLOCK.showBlock("Cargando");
	
	var self = altaContratoEvent;
	self.service = new AltaContratoService();
	Component.get("html/altaContrato/datosContrato.html", self.draw);
};	

AltaContratoEvent.prototype.draw = function(comp) {
	var self = altaContratoEvent;
	self.contrato = new ContratoPOJO();
	
	$("#viewContainer").empty().append(comp.replace(/{{id}}/g, self.div.id));

	/* Accordion */
	Accordion.getInstance(self.getSelector("accordionCabecera"));
    /* Botonera */
	self.getSelector("aceptarCabecera").button().click(self.validarDatosCabecera);
	/* Monedas e importes */
	self.getSelector("montoTotal").autoNumeric({vMax: '999999999.99'});
	self.getSelector("montoTotal").autoNumericSet("0");
	var data = {
		action:"altaContratoBuscarMonedas.action", 
		request: {},
		async: false,
		method: "GET", 
		responseObject: "monedas", 
		callback: function(monedas) {
			Component.populateSelect(self.getSelector("moneda"), monedas, "codigo", "abreviatura", true);
		}
	};
	self.service.doRequest(data);
	/* Datepickers  - No puede ser mayor a la fecha del sistema */
	Datepicker.getInstance(self.getSelector("fechaContrato"), new Date());
	self.getSelector("fechaContrato").datepicker('option', {maxDate: 0});
	Datepicker.getInstance(self.getSelector("fechaAprobacion"), new Date());
	self.getSelector("fechaAprobacion").datepicker('option', {maxDate: 0});
	/* Tipo contrato - Es obligatorio */
	Component.populateSelect(self.getSelector("tipoContrato"), AltaContratoEvent.selectTipoContrato, "codigo", "descripcion", false);
	self.getSelector("tipoContrato")[0].removeChild(self.getSelector("tipoContrato")[0].options[0]);
	self.getSelector("tipoContrato").val("N");
	/* Popup distribuidor */
	self.getSelector("popupDistribuidor").click(self.drawPopupDistribuidor);
	self.getSelector("limpiarDistribuidor").click(self.limpiarDatosDistribuidor);
	/* Desbloquear pantalla */
	BLOCK.hideBlock();
};

AltaContratoEvent.prototype.validarDatosCabecera = function() {
	var self = altaContratoEvent;
	var today = new Date();
	
	
	/* Validar Ingreso de distribuidor */
	if (Validator.isEmpty(self.getSelector("codigoDistribuidor") || !self.distribuidorValido())) {
		Validator.focus(self.getSelector("codigoDistribuidor"), "Distribuidor inexistente");
		return;
	}
	/* Validar ingreso de fecha de contrato */
	if (Validator.isEmpty(self.getSelector("fechaContrato"))) {
		Validator.focus(self.getSelector("fechaContrato"), "Debe ingresar una fecha");
		return;
	}
	/* Validar que la fecha de contrato sea menor a la actual*/
	if (self.getSelector("fechaContrato").datepicker("getDate") > today) {
		Validator.focus(self.getSelector("fechaContrato"), "La fecha de contrato debe ser menor que la actual");
		return;
	}
	
	/* Validar Ingreso de fecha de aprobación (debe ser igual o mayor a la fecha de Contrato) */
	if (Validator.isEmpty(self.getSelector("fechaContrato")) || (self.getSelector("fechaAprobacion").datepicker("getDate") < self.getSelector("fechaContrato").datepicker("getDate"))) {
		Validator.focus(self.getSelector("fechaAprobacion"), "Debe ingresar una fecha y debe ser igual o mayor a la fecha de contrato");
		return;
	}
	
	/* Validar que la fecha de Aprobacion sea menor a la actual*/
	if (self.getSelector("fechaAprobacion").datepicker("getDate") > today) {
		Validator.focus(self.getSelector("fechaAprobacion"), "La fecha de aprobación debe ser menor que la actual");
		return;
	}
	
	/* Se selecciono tipo de moneda y no se ingreso el monto o es 0. */
	if (!Validator.isEmpty(self.getSelector("moneda")) && (Validator.isEmpty(self.getSelector("montoTotal")) || self.getSelector("montoTotal").autoNumericGet() == "0")) {
		Validator.focus(self.getSelector("montoTotal"), "Debe ingresar importe si ingresa moneda");
		return;
	}
	/* Se no selecciono tipo de moneda y el monto es mayor a 0. */
	if (Validator.isEmpty(self.getSelector("moneda")) && self.getSelector("montoTotal").autoNumericGet() > 0) {
		Validator.focus(self.getSelector("moneda"), "Debe ingresar el tipo de moneda");
		return;
	}
	/* Si no se selecciona tipo de moneda y no se ingresa el monto total o es 0, mostrar warning FP11062 */
	if (Validator.isEmpty(self.getSelector("moneda")) && (Validator.isEmpty(self.getSelector("montoTotal")) || self.getSelector("montoTotal").autoNumericGet() == "0")) {
		self.abrirWarningTipoMoneda();
		return;
	}
	self.guardarDatosCabeceraContrato();
};

AltaContratoEvent.prototype.guardarDatosCabeceraContrato = function() {
	/* Bloquear pantalla */
	BLOCK.showBlock("Guardando");
	
	var self = altaContratoEvent;
	/* Genero la cabecera del contrato */
	self.contrato.cabecera.proveedor = self.getSelector("codigoDistribuidor").val();
	self.contrato.cabecera.moneda = self.getSelector("moneda").val();
	self.contrato.cabecera.fechaContrato = self.getSelector("fechaContrato").val();
	self.contrato.cabecera.fechaAprobacion = self.getSelector("fechaAprobacion").val();
	self.contrato.cabecera.montoTotal = self.getSelector("montoTotal").autoNumericGet();
	self.contrato.cabecera.canje = self.getSelector("tipoContrato").val();
	self.contrato.cabecera.observaciones = self.getSelector("observaciones").val();

	var params = { codigoDistribuidor: self.getSelector("codigoDistribuidor").val() };
    
	$.ajax({
    	async: true,
        type : "GET",
        url : "buscarDistribuidoresParaMateriales.action",
        data : params,
        success : function(response) {
            if (response.distribuidores) {
            	self.contrato.cabecera.razonSocial = Component.trimWhitespace(response.distribuidores[0].razonSocial);
            }
        }
    });
	
	/* Guardar datos de la cabecera */
	var data = {
		async: false,
		action:"altaContratoGuardarDatosCabeceraContrato.action", 
		request: Component.serialize(self.contrato.cabecera, "guardarDatosCabeceraContratoRequest"), 
		method: "POST", 
		responseObject: "guardarDatosCabeceraContratoResponse", 
		callback: function(guardarDatosCabeceraContratoResponse) {
			/* Desbloquear pantalla */
			setTimeout(BLOCK.hideBlock, 1500);
			
			if (guardarDatosCabeceraContratoResponse.resultado) {
				/* Obtengo el numero de contrato para usarlo más adelante */
				self.contrato.numero = guardarDatosCabeceraContratoResponse.contrato;
				/* Abrir popup monto x señal */
				self.limpiarDatosCabeceraContrato();
				/* Abrir FP11002 */
				self.abrirPopupMontoSenial(self.setOpcionesAltaMontoSenial);
				/* Cargo en background FP11003 */
				self.abrirCargaContratoSeniales();
			} else {
				MESSAGE.error("Ocurrió un error al intentar guardar la cabecera");
			}
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.limpiarDatosCabeceraContrato = function() {
	var self = altaContratoEvent;
	self.limpiarDatosDistribuidor();
	self.getSelector("moneda").val(null);
	self.getSelector("fechaContrato").datepicker('setDate', new Date());
	self.getSelector("fechaAprobacion").datepicker('setDate', new Date());
	self.getSelector("montoTotal").autoNumericSet("0");
	self.getSelector("tipoContrato").val(null);
	self.getSelector("observaciones").val(null);
};

/***********************************************************************************		
 ********************				Pantalla FP11002			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirPopupMontoSenial = function(callback, params) {
	/* Bloquear pantalla */
	BLOCK.showBlock("Cargando señales");
	
	var self = altaContratoEvent;
	
	setTimeout(function() {
		Component.get("html/altaContrato/popupMontoPorSenial.html", function(data) {
			self.drawPopupMontoSenial(data);
			params == undefined ? callback() :callback(params[0], params[1]);
		});
	}, 750);
};

AltaContratoEvent.prototype.drawPopupMontoSenial = function(comp) {
	var self = altaContratoEvent;
	
	$("#popupMontoSenial").dialog({
		width: 600, show: 'blind', hide: 'blind', modal: true, autoOpen: false,
		buttons: {
			"Salir": function() {
				$(this).dialog("close");
			}
		}
	});
	$("#popupMontoSenial").empty().append(comp.replace(/{{id}}/g, self.div.id));
	/* Señales */
	Component.populateSelect(self.getSelector("senialMontoPorSenial"), seniales, "codigo", "descripcion", false);
	/* Monto por señal */
	self.getSelector("importeMontoPorSenial").autoNumeric({vMax: '999999999.99'});
	
	$("#popupMontoSenial").dialog("open");
	/* Desbloquear pantalla */
	BLOCK.hideBlock();
};

AltaContratoEvent.prototype.setOpcionesAltaMontoSenial = function() {
	var self = altaContratoEvent;
	self.getSelector("importeMontoPorSenial").autoNumericSet("0");
	self.getSelector("confirmarMontoSenialMontoPorSenial").button().click(function() {
	if (self.getSelector("senialMontoPorSenial").val() == ''){
			MESSAGE.error("Eliga una señal");
			return;
		}
		self.confirmarMontoPorSenial(function(data) {
			/* Cierre popup */
			self.cierrePopupMontoSenial();
			/* Recarga grilla FP11003 */
			self.buscarSenialesImportes();
			/* Abrir FP11004 */
			self.abrirSeleccionTipoTituloGrupo(data);
		});
	});
};

AltaContratoEvent.prototype.confirmarMontoPorSenial = function(callback) {
	var self = altaContratoEvent;

	/* Compruebo la existencia de la señal para el contrato */
	if (self.existeSenialContrato()) {
		Validator.focus(self.getSelector("senialMontoPorSenial"), "Monto por señal ya ingresado");
	} else {
		/* Bloquear pantalla */
		BLOCK.showBlock("Guardando");
		/* El monto de la señal podrá informarse en 0. En ese caso, si el monto del contrato es <> 0 y si es la primera señal que se informa, se asumirá como monto 
		 * de la señal el del contrato. Al finalizar, llamar a FP011004. */
		var importe = new Number();
		if (self.getSelector("gridSenialImporte").getRowData().length == 0 && self.getSelector("importeMontoPorSenial").autoNumericGet() == 0 && self.contrato.cabecera.montoTotal != 0) {
			importe =  self.contrato.cabecera.montoTotal;
		} else {
			importe = self.getSelector("importeMontoPorSenial").autoNumericGet();
		}
		var guardarContratoSenialImporteRequest = {
			contrato: self.contrato.numero,
			senial : self.getSelector("senialMontoPorSenial").val(),
			importe : importe
		};
		
		var data = {
			async: false,
			action:"altaContratoGuardarContratoSenialImporte.action", 
			request: Component.serialize(guardarContratoSenialImporteRequest, "guardarContratoSenialImporteRequest"), 
			method: "POST", 
			responseObject: "resultado", 
			callback: function(exito) {
				/* Desbloquear pantalla */
				BLOCK.hideBlock();
				if (exito) {
					callback(guardarContratoSenialImporteRequest);
				} else {
					MESSAGE.error("Ocurrió un error durante la operación");
				}
			}
		};
		self.service.doRequest(data);
	}
};

AltaContratoEvent.prototype.existeSenialContrato = function() {
	var self = altaContratoEvent;
	return self.getSelector("gridSenialImporte").getRowData().filter(function (registro) { return registro.codigoSenial == self.getSelector("senialMontoPorSenial").val(); }).length > 0;
};

AltaContratoEvent.prototype.cierrePopupMontoSenial = function() {
	$("#popupMontoSenial").dialog("close");
	$("#popupMontoSenial").dialog("remove");
};

/***********************************************************************************		
 ********************				Pantalla FP11003			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirCargaContratoSeniales = function() {
	var self = altaContratoEvent;
	Component.get("html/altaContrato/cargaContratoSeniales.html", self.drawCargaContratoSeniales);
};

AltaContratoEvent.prototype.drawCargaContratoSeniales = function(comp) {
	var self = altaContratoEvent;
	self.getSelector("formAltaContrato").hide();
	comp = comp.replace(/{{id}}/g, self.div.id);
	
	if (self.getSelector("formCargaContrato").length) {
		self.getSelector("formCargaContrato").replaceWith(comp);
	} else {
		self.getDiv().append(comp);
	}
	/* Accordion */
	Accordion.getInstance(self.getSelector("accordionDatosCabeceraContratoCabecera"));
	/* Botonera */
	self.getSelector("volverCargaContrato").button().click(self.abrirWarningRetornoCargaContrato);//self.confirmarSenialesContrato);
	self.getSelector("agregarSenialCargaContrato").button().click(function() {
		self.abrirPopupMontoSenial(self.setOpcionesAltaDesdeMontoSenial);
	});
	self.getSelector("confirmarCargaContrato").button().click(self.confirmarSenialesContrato);
	/* Carga de datos de la cabecera del contrato - Sólo lectura */
	self.fillCabeceraCargaContratoSeniales();
	/* Grilla de monto - señal */
	self.drawGridSenialImporte();
};

AltaContratoEvent.prototype.fillCabeceraCargaContratoSeniales = function() {
	var self = altaContratoEvent;
	self.getSelector("contratoCargaContrato").html("Pendiente");
	self.getSelector("distribuidorCargaContrato").html(self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("distribuidorCargaContrato").attr("title", self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("monedaCargaContrato").html(self.contrato.cabecera.moneda);
	self.getSelector("fechaContratoCargaContrato").html(self.contrato.cabecera.fechaContrato);
	self.getSelector("fechaAprobacionCargaContrato").html(self.contrato.cabecera.fechaAprobacion);
	self.getSelector("tipoContratoCargaContrato").html(self.contrato.cabecera.canje);
	self.getSelector("montoTotalCargaContrato").autoNumericSet(self.contrato.cabecera.montoTotal);
	self.getSelector("montoTotalCargaContrato").html(self.getSelector("montoTotalCargaContrato").val());
}; 

AltaContratoEvent.prototype.drawGridSenialImporte = function() {
	var self = altaContratoEvent;
	self.getSelector("gridSenialImporte").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Señal', 'Descripción', 'Importe', 'Grupos', 'Modificar', 'Eliminar'],
		colModel:[ 
		    {name:'codigoSenial', 		index:'codigoSenial', 		align:'center'},
		    {name:'descripcionSenial', 	index:'descripcionSenial', 	align:'center', formatter:function(value, options, rData) {
		    	return Component.trimWhitespace(rData['descripcionSenial']);
		    }},
		    {name:'importe', 			index:'importe', 			align:'center', formatter:'currency'},
		    {name:'trabajarGrupos', 	index:'trabajarGrupos', 	align:'center', formatter:function(value, options, rData) { 
		    	return "<div onclick='altaContratoEvent.validarExistenciaGrupos(\"" + rData['codigoSenial'] + "\", " + rData['importe'] + ");' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-clipboard conTooltip' title='Trabajar con grupos'></span></div>";
		    }},
		    {name:'modificar', 			index:'modificar', 			align:'center', formatter:function(value, options, rData) { 
		    	return "<div onclick='altaContratoEvent.abrirPopupMontoSenial(altaContratoEvent.setOpcionesModificacionMontoSenial, [\"" + rData['codigoSenial'] + "\", " + rData['importe'] + "]);' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-pencil conTooltip' title='Modificar'></span></div>";
		    }},
		    {name:'eliminar', 			index:'eliminar', 			align:'center', formatter:function(value, options, rData) { 
		    	return "<div onclick='altaContratoEvent.desenlaceContratoSenial(\"" + rData['codigoSenial'] + "\");' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-trash conTooltip' title='Eliminar'></span></div>";
		    }},
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + this.div.id + '_pagerGridSenialImporte',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Señales'
	});
	
	self.buscarSenialesImportes();
	
	/* Grilla con tamaño automático */
	Component.resizableGrid(self.getSelector("gridSenialImporte"));
};

AltaContratoEvent.prototype.buscarSenialesImportes = function() {
	var self = altaContratoEvent;
	var data = {
		async: false,
		action:"altaContratoBuscarSenialesImportes.action", 
		request: Component.serialize({contrato: self.contrato.numero}, "buscarSenialesImportesRequest"), 
		method: "GET", 
		responseObject: "senialesImportes", 
		callback: function(senialesImportes) {
			self.getSelector("gridSenialImporte").clearGridData().setGridParam({data: senialesImportes}).trigger('reloadGrid');
		}
	};
	
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.setOpcionesAltaDesdeMontoSenial = function() {
	var self = altaContratoEvent;
	self.getSelector("importeMontoPorSenial").autoNumericSet("0");
	self.getSelector("confirmarMontoSenialMontoPorSenial").button().click(function() {
		if (self.getSelector("senialMontoPorSenial").val() == ''){
			MESSAGE.error("Eliga una señal");
			return;
		}
		self.confirmarMontoPorSenial(function() {
			self.getSelector("senialMontoPorSenial").val(null);
			self.getSelector("importeMontoPorSenial").autoNumericSet("0");
			self.buscarSenialesImportes();
		});
	});
};

AltaContratoEvent.prototype.setOpcionesModificacionMontoSenial = function(senial, importe) {
	var self = altaContratoEvent;
	self.getSelector("senialMontoPorSenial").attr('disabled', 'disabled');
	self.getSelector("senialMontoPorSenial").val(senial);
	self.getSelector("importeMontoPorSenial").autoNumericSet(importe);
	self.getSelector("confirmarMontoSenialMontoPorSenial").button().click(self.modificarMontoSenial);
};

AltaContratoEvent.prototype.validarExistenciaGrupos = function(senial, monto) {
	var self = altaContratoEvent;
	
	var data = {
		async: false,
		action:"altaContratoValidarExistenciaGrupos.action", 
		request: Component.serialize({contrato: self.contrato.numero, senial: senial}, "validarExistenciaGruposRequest"), 
		method: "GET", 
		responseObject: "resultado", 
		callback: function(existenGrupos) {
			if (existenGrupos) {
				/* Abrir FP11009 */
				self.contrato.montoSenialTT.monto = monto;
				self.contrato.montoSenialTT.senial = senial;
				self.abrirVisualizarGruposContrato();
			} else {
				/* Abrir FP11004 */
				var params = { senial: senial, importe : monto };
				self.abrirSeleccionTipoTituloGrupo(params);
			}
		}
	};
	
	self.service.doRequest(data);
};

/***********************************************************************************		
 ********************     Pantalla FP11003 - Modificar señal    ********************
 ***********************************************************************************/
AltaContratoEvent.prototype.modificarMontoSenial = function() {
	/* Bloquear pantalla */
	BLOCK.showBlock("Guardando cambios");
	
	var self = altaContratoEvent;
	
	var guardarContratoSenialImporteRequest = {
		contrato: self.contrato.numero,
		senial : self.getSelector("senialMontoPorSenial").val(),
		importe : self.getSelector("importeMontoPorSenial").autoNumericGet()
	};
	
	var data = {
		async: false,
		action:"altaContratoModificarContratoSenialImporte.action", 
		request: Component.serialize(guardarContratoSenialImporteRequest, "guardarContratoSenialImporteRequest"), 
		method: "POST", 
		responseObject: "resultado", 
		callback: function(exito) {
			/* Desbloquear pantalla */
			BLOCK.hideBlock();
			if (exito) {
				self.cierrePopupMontoSenial();
				self.buscarSenialesImportes();
			} else {
				MESSAGE.error("No se pudo modificar el importe");
			}
		}
	};
	self.service.doRequest(data);
};

/***********************************************************************************		
 ********************     Pantalla FP11003 - Eliminar señal     ********************
 ***********************************************************************************/
AltaContratoEvent.prototype.desenlaceContratoSenial = function(senial) {
	/* Bloquear pantalla */
	BLOCK.showBlock("Eliminando monto por señal");
	
	var self = altaContratoEvent;

	var desenlaceContratoSenialRequest = {
		contrato: self.contrato.numero,
		senial : senial,
        grupo : 0,
        tipoTitulo: ' ',
        nroTitulo: 0	
	};
	
	var data = {
		async: false,
		action:"altaContratoDesenlaceContratoSenial.action", 
		request: Component.serialize(desenlaceContratoSenialRequest, "desenlaceContratoSenialRequest"), 
		method: "POST", 
		responseObject: "desenlaceContratoSenialResponse", 
		callback: function(desenlaceContratoSenialResponse) {
			if (desenlaceContratoSenialResponse.error == "N") {
				self.eliminarMontoSenial(senial);
			} else {
				/* Desbloquear pantalla */
				BLOCK.hideBlock();
				setTimeout(function() {
					var params = {
						mensaje: "Errores que se producen al desenlazar los títulos del contrato - señal",
						datosDesenlace: desenlaceContratoSenialResponse.errores,
						showBotonera: false
					};
					self.abrirPopupDatosDesenlace(params);	
				}, 750);
			}
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.eliminarMontoSenial = function(senial) {
	var self = altaContratoEvent;

	var eliminarMontoSenialRequest = {
		contrato: self.contrato.numero,
		senial : senial
	};
	
	var data = {
		async: false,
		action:"altaContratoEliminarMontoSenial.action", 
		request: Component.serialize(eliminarMontoSenialRequest, "eliminarMontoSenialRequest"), 
		method: "POST", 
		responseObject: "resultado", 
		callback: function(exito) {
			if (exito) {
				self.validarSumaGrupos();
			} else {
				/* Desbloquear pantalla */
				BLOCK.hideBlock();
				MESSAGE.error("No se pudo eliminar el monto-señal seleccionado");
			}
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.validarSumaGrupos = function() {
	var self = altaContratoEvent;

	var validarSumaGruposRequest = {
		contrato: self.contrato.numero
	};
	
	var data = {
		async: false,
		action:"altaContratoValidarSumaGrupos.action", 
		request: Component.serialize(validarSumaGruposRequest, "validarSumaGruposRequest"), 
		method: "GET", 
		responseObject: "validarSumaGruposResponse", 
		callback: function(validarSumaGruposResponse) {
			/* Desbloquear pantalla */
			BLOCK.hideBlock();
			
			if (validarSumaGruposResponse.exito) {
				MESSAGE.ok("Se elimino el monto por señal con éxito");
				self.buscarSenialesImportes();
			} else {
				self.abrirWarningActualizarCabeceraEliminada(validarSumaGruposResponse.mensaje);
			}
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.actualizarCabeceraEliminada = function() {
	var self = altaContratoEvent;

	var actualizarCabeceraEliminadaRequest = {
		contrato: self.contrato.numero
	};
	
	var data = {
		async: false,
		action:"altaContratoActualizarCabeceraEliminada.action", 
		request: Component.serialize(actualizarCabeceraEliminadaRequest, "actualizarCabeceraEliminadaRequest"), 
		method: "POST", 
		responseObject: "actualizarCabeceraEliminadaResponse", 
		callback: function(exito) {
			if (exito) {
				MESSAGE.ok("Se elimino el monto por señal con éxito");
			} else {
				MESSAGE.error("No se pudo actualizar la cabecera eliminada");
			}
			self.buscarSenialesImportes();
		}
	};
	self.service.doRequest(data);
};

/***********************************************************************************		
 ********************		Pantalla Warnings desenlace			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirPopupDatosDesenlace = function(params) {
	var self = altaContratoEvent;
	Component.get("html/altaContrato/popupDatosDesenlace.html", function(comp) {
		self.drawPopupDatosDesenlace(comp, params);
	});
};

AltaContratoEvent.prototype.drawPopupDatosDesenlace = function(comp, params) {
	var self = altaContratoEvent;
	
	$("#popupDatosDesenlace").dialog({
		width: 800, show: 'blind', hide: 'blind', modal: true, autoOpen: false,
		buttons: {
			"Salir": function() {
				$(this).dialog("close");
			}
		}
	});
	$("#popupDatosDesenlace").empty().append(comp.replace(/{{id}}/g, self.div.id));
	$("#popupDatosDesenlace").dialog("open");
	
	/* Botonera */
	if (params.showBotonera) {
		self.getSelector("botoneraDatosDesenlace").show();
		self.getSelector("confirmarDatosDesenlace").button().click(params.callback);
		self.getSelector("rechazarDatosDesenlace").button().click(self.cierrePopupDatosDesenlace);
	}
	/* Seteo el mensaje a mostrar en el popup */
	self.getSelector("mensajeDatosDesenlace").html(params.mensaje);
	/* Cargo la grilla con los datos del desenlace */
	self.drawGridDatosDesenlace(params.datosDesenlace);
};

AltaContratoEvent.prototype.drawGridDatosDesenlace = function(datosDesenlace) {
	var self = altaContratoEvent;
	
	self.getSelector("gridDatosDesenlace").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Tipo error', 'Descripción', 'Contrato', 'Grupo', 'Señal', 'Título', 'Fecha desde', 'Tipo listado'],
		colModel:[ 
		    {name:'tipoError', 		index:'tipoError', 		align:'center'},
		    {name:'descripcion', 	index:'descripcion', 	align:'center'},
		    {name:'contrato', 		index:'contrato', 		align:'center'},
		    {name:'grupo', 			index:'grupo', 			align:'center'},
		    {name:'senial', 		index:'senial', 		align:'center'},
		    {name:'titulo', 		index:'titulo', align:'center', formatter:function(value, options, rData) { 
		    	return rData['tipoTitulo'] + rData['nroTitulo'];
		    }},
		    {name:'fechaDesde', 	index:'fechaDesde', 	align:'center', formatter:'date', formatoptions:{newformat: 'd/m/Y'}},
		    {name:'tipoListado', 	index:'tipoListado', 	align:'center'}
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + this.div.id + '_pagerDatosDesenlace',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Datos'
	});
	
	self.getSelector("gridDatosDesenlace").clearGridData().setGridParam({data: datosDesenlace}).trigger('reloadGrid');
};

AltaContratoEvent.prototype.cierrePopupDatosDesenlace = function() {
	$("#popupDatosDesenlace").dialog("close");
	$("#popupDatosDesenlace").dialog("remove");
};

/***********************************************************************************		
 ********************		Pantalla Warnings vigencia			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirPopupDatosVigencia = function(params) {
	var self = altaContratoEvent;
	Component.get("html/altaContrato/popupDatosVigencia.html", function(comp) {
		self.drawPopupDatosVigencia(comp, params);
	});
};

AltaContratoEvent.prototype.drawPopupDatosVigencia = function(comp, params) {
	var self = altaContratoEvent;
	
	$("#popupDatosVigencia").dialog({
		width: 800, show: 'blind', hide: 'blind', modal: true, autoOpen: false,
		buttons: {
			"Salir": function() {
				$(this).dialog("close");
			}
		}
	});
	$("#popupDatosVigencia").empty().append(comp.replace(/{{id}}/g, self.div.id));
	$("#popupDatosVigencia").dialog("open");
	
	/* Botonera */
	if (params.showBotonera) {
		self.getSelector("botoneraDatosVigencia").show();
		self.getSelector("confirmarDatosVigencia").button().click(params.callback);
		self.getSelector("rechazarDatosVigencia").button().click(self.cierrePopupDatosVigencia);
	}
	/* Seteo el mensaje a mostrar en el popup */
	self.getSelector("mensajeDatosVigencia").html(params.mensaje);
	/* Cargo la grilla con los datos del desenlace */
	self.drawGridDatosVigencia(params.datosVigencia);
};

AltaContratoEvent.prototype.drawGridDatosVigencia = function(datosVigencia) {
	var self = altaContratoEvent;
	
	self.getSelector("gridDatosVigencia").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Contrato', 'Grupo', 'Comienzo', 'Fin', 'Clave'],
		colModel:[
			{name:'contrato', 	index:'contrato', 	align:'center' , formatter:function(value, options, rData) { 
		    	return self.formatearContrato(rData['contrato']);
			}},
			{name:'grupo', 		index:'grupo', 		align:'center'},      
			{name:'comienzo', 	index:'comienzo', 	align:'center', formatter:'date', formatoptions:{newformat: 'd/m/Y'}},		          
			{name:'fin', 		index:'fin', 		align:'center', formatter:'date', formatoptions:{newformat: 'd/m/Y'}},	
			{name:'clave', 		index:'clave', 		align:'center'}	
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + this.div.id + '_pagerDatosVigencia',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Datos'
	});
	
	self.getSelector("gridDatosVigencia").clearGridData().setGridParam({data: datosVigencia}).trigger('reloadGrid');
};

AltaContratoEvent.prototype.cierrePopupDatosVigencia = function() {
	$("#popupDatosVigencia").dialog("close");
	$("#popupDatosVigencia").dialog("remove");
};

/***********************************************************************************		
 ********************		 Pantalla FP11003 - Confirmar		********************
 ***********************************************************************************/
AltaContratoEvent.prototype.confirmarSenialesContrato = function() {
	var self = altaContratoEvent;
	
	if (self.getSelector("gridSenialImporte").getRowData().length > 0) {
		var confirmarSenialesContratoRequest = {
			contrato: self.contrato.numero
		};
			
		var data = {
			async: false,
			action:"altaContratoConfirmarSenialesContrato.action", 
			request: Component.serialize(confirmarSenialesContratoRequest, "confirmarSenialesContratoRequest"), 
			method: "POST", 
			responseObject: "confirmarSenialesContratoResponse", 
			callback: function(confirmarSenialesContratoResponse) {
				if (confirmarSenialesContratoResponse.mensaje && confirmarSenialesContratoResponse.mensaje != "Contrato OK") {
					if (confirmarSenialesContratoResponse.mensaje == "Hay diferencias entre la cabecera del Contrato y la Sumatoria de los montos por Contrato-Señal" ){
						//aca va el popup
						popupConfirmacionEvent.confirmar = function () {
							var data = {
									contrato: self.contrato.numero
								};
                            self.modificarCabeceraContrato(data);
                            popupConfirmacionEvent.remove();
                        };
                        popupConfirmacionEvent.cancelar = function () {
                            popupConfirmacionEvent.remove();
                        };
                        popupConfirmacionEvent.afterDraw = function () {
                            return;
                        };
                        popupConfirmacionEvent.popTitle = 'Alerta';
                       
                        popupConfirmacionEvent.create("warningsPopUp", "Hay diferencias entre la cabecera del Contrato y la Sumatoria de los montos por Contrato-Señal, desea modificar la cabecera?");
						
					}
					else {
					
						var mensajeError = "<ul>" + confirmarSenialesContratoResponse.mensaje +
											"<li> Contrato: " + confirmarSenialesContratoResponse.contrato + "</li>" + 
											"<li> Grupo: " + confirmarSenialesContratoResponse.grupo + "</li>" +
											"<li> Señal: " + confirmarSenialesContratoResponse.senial + "</li>" +
									   "</ul>";
						MESSAGE.alert(mensajeError);
					}
				} else {
					MESSAGE.ok("Confirmación exitosa");
					self.volverCargaContrato();
				}
				self.buscarSenialesImportes();
			}
		};
		self.service.doRequest(data);
	}
};

AltaContratoEvent.prototype.modificarCabeceraContrato = function(modifRequest){
	var self = altaContratoEvent;
	var data = {
			async: false,
			action:"altaContratoModificarCabeceraContrato.action", 
			request: Component.serialize(modifRequest, "confirmarSenialesContratoRequest"), 
			method: "POST", 
			responseObject: "confirmarSenialesContratoResponse", 
			callback:function(confirmarSenialesContratoResponse) {
				if (confirmarSenialesContratoResponse.mensaje){
					var mensajeError = "<ul>" + confirmarSenialesContratoResponse.mensaje +
					"<li> Contrato: " + confirmarSenialesContratoResponse.contrato + "</li>" + 
					"<li> Grupo: " + confirmarSenialesContratoResponse.grupo + "</li>" +
					"<li> Señal: " + confirmarSenialesContratoResponse.senial + "</li>" +
					"</ul>";
					MESSAGE.alert(mensajeError);
				}else {
					MESSAGE.ok("Confirmación exitosa");
					self.volverCargaContrato();
				}
			}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.volverCargaContrato = function() {
	var self = altaContratoEvent;
	/* Limpio el POJO de contrato */
	self.contrato = new ContratoPOJO();
	self.getSelector("formCargaContrato").hide();
	self.getSelector("formAltaContrato").show();
};

/***********************************************************************************		
 ********************				Pantalla FP11004			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirSeleccionTipoTituloGrupo = function(data) {
	/* Bloquear pantalla */
	BLOCK.showBlock("Cargando tipos de título");
	
	var self = altaContratoEvent;
	
	setTimeout(function() {
		/* Seteo los datos del monto y señal seleccionados */
		self.contrato.montoSenialTT.monto = data.importe;
		self.contrato.montoSenialTT.senial = data.senial;
		Component.get("html/altaContrato/popupTipoTituloGrupo.html", self.drawPopupTipoTituloGrupo);
	}, 750);
};

AltaContratoEvent.prototype.drawPopupTipoTituloGrupo = function(comp) {
	var self = altaContratoEvent;
	
	$("#popupTipoTituloGrupo").dialog({
		width: 600, show: 'blind', hide: 'blind', modal: true, autoOpen: false,
		buttons: {
			"Salir": function() {
				$(this).dialog("close");
			}
		}
	});
	$("#popupTipoTituloGrupo").empty().append(comp.replace(/{{id}}/g, self.div.id));
	$("#popupTipoTituloGrupo").dialog("open");
	
	/*Carga de grilla de titulos*/
	self.drawGridTiposTitulo();
};

AltaContratoEvent.prototype.drawGridTiposTitulo = function() {
	var self = altaContratoEvent;
	
	self.getSelector("gridTiposTitulo").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Tipo de título',''],
		colModel:[ 
		    {name:'descripcionTitulo', index:'descripcionTitulo', align:'left', formatter:function(value, options, rData) {
		    	return Component.trimWhitespace(rData['descripcionTitulo']);
		    }},
			{name:'seleccion', index:'seleccion', align:'center', width:'15', formatter:function(value, options, rData) {
				if (rData['tipoTitulo'] === "SE" || rData['tipoTitulo'] === "MS" || rData['tipoTitulo'] === "SU" || rData['tipoTitulo'] === "CF") {
					return "<div onclick='altaContratoEvent.abrirCargaDatosGrupoCC(\"" + rData['tipoTitulo'] + "\");' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-pencil conTooltip' title='Seleccionar'></span></div>";
				} else {
					return "<div onclick='altaContratoEvent.abrirCargaDatosGrupoSC(\"" + rData['tipoTitulo'] + "\");' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-pencil conTooltip' title='Seleccionar'></span></div>";
				}
			  }
		    }
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + this.div.id + '_pagerTiposTitulo',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Tipos de título'
	});
	
	var data = {
		async: false,
		action:"altaContratoBuscarTiposTitulo.action", 
		request: {}, 
		method: "GET", 
		responseObject: "tiposTitulo", 
		callback: function(tiposTitulo) {
			self.getSelector("gridTiposTitulo").clearGridData().setGridParam({data: tiposTitulo}).trigger('reloadGrid');
			/* Desbloquear pantalla */
			BLOCK.hideBlock();
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.cierrePopupTipoTituloGrupo = function() {
	$("#popupTipoTituloGrupo").dialog("close");
	$("#popupTipoTituloGrupo").dialog("remove");
	altaContratoEvent.getSelector("formVisualizarGruposContrato").hide();
};

/***********************************************************************************		
 ********************				Pantalla FP11021			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirVisualizarTitulosGrupoReadOnly = function(nroGrupo, tipoTitulo, nombreGrupo, estrenoRepeticion) {
	BLOCK.showBlock("Cargando títulos del grupo");
	
	var self = altaContratoEvent;
	
	var grupo = {
		nroGrupo : nroGrupo,
		tipoTitulo : tipoTitulo,
		nombreGrupo : nombreGrupo,
		estrenoRepeticion : estrenoRepeticion
	};
	
	setTimeout(function() {
		Component.get("html/altaContrato/visualizarTitulosGrupoReadOnly.html", function(comp) {
			self.drawVisualizarTitulosGrupoReadOnly(comp, grupo);
		});
	}, 750);
};

AltaContratoEvent.prototype.drawVisualizarTitulosGrupoReadOnly = function(comp, grupo) {
	var self = altaContratoEvent;
	comp = comp.replace(/{{id}}/g, self.div.id);
	self.getSelector("formVisualizarGruposContrato").hide();
	
	if (self.getSelector("formVisualizarTitulosGrupoReadOnly").length) {
		self.getSelector("formVisualizarTitulosGrupoReadOnly").replaceWith(comp);
	} else {
		self.getDiv().append(comp);
	}
	/* Accordion */
	Accordion.getInstance(self.getSelector("accordionVisualizarTitulosGrupoReadOnly"));
	/* Datos de sólo lectura */
	self.getSelector("senialVisualizarTitulosGrupoReadOnly").html(self.contrato.montoSenialTT.senial);
	self.getSelector("distribuidorVisualizarTitulosGrupoReadOnly").html(self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("distribuidorVisualizarTitulosGrupoReadOnly").attr("title", self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("montoVisualizarTitulosGrupoReadOnly").html(self.contrato.montoSenialTT.monto);
	self.getSelector("nroGrupoVisualizarTitulosGrupoReadOnly").html(grupo.nroGrupo);
	self.getSelector("tipoTituloVisualizarTitulosGrupoReadOnly").html(grupo.tipoTitulo);
	self.getSelector("estrenoVisualizarTitulosGrupoReadOnly").html(grupo.estrenoRepeticion == "E" ? "Estreno" : "Repetición");
	self.getSelector("nombreGrupoVisualizarTitulosGrupoReadOnly").html(grupo.nombreGrupo);
	self.getSelector("volverVisualizarTitulosGrupoReadOnly").button().click(self.volverVisualizarTitulosGrupoReadOnly);
	/* Grilla de títulos para el grupo seleccionado */
	self.drawGridTitulosGrupoReadOnly();
};

AltaContratoEvent.prototype.drawGridTitulosGrupoReadOnly = function() {
	var self = altaContratoEvent;
	self.getSelector("gridTitulosGrupoReadOnly").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Clave', 'Título original', 'Actores'],
		colModel:[ 
		    {name:'clave', 		 		index:'clave', 	  			  align:'center', width: 40},
		    {name:'tituloOriginal', 	index:'tituloOriginal', 	  align:'left', formatter:function(value, options, rData) {
		    	return Component.trimWhitespace(rData['tituloOriginal']);
		    }},
		    {name:'actores', 	 		index:'actores',	  		  align:'left', 
		    	formatter:function(value, options, rData) { 
			    	var primerActor = "";
			    	if (rData['primerActor'] != null && rData['primerActor'].trim() != "") {
			    		primerActor = Component.trimWhitespace(rData['primerActor']);
			    	} 
			    	var segundoActor = "";
			    	if (rData['segundoActor'] != null && rData['segundoActor'].trim() != "") {
			    		segundoActor = Component.trimWhitespace(rData['segundoActor']); 
			    	}
			    	var separador = "";
			    	if (primerActor != "" && segundoActor != "") {
			    		separador = " / ";
			    	}
			    	return primerActor + separador + segundoActor;
			    }
		    }
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + self.div.id + '_pagerTitulosGrupoReadOnly',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Títulos'
	});
	
	/* Grilla con tamaño automático */
	Component.resizableGrid(self.getSelector("gridTitulosGrupoReadOnly"));
	
	self.buscarTitulosGrupoReadOnly();
};

AltaContratoEvent.prototype.buscarTitulosGrupoReadOnly = function() {
	var self = altaContratoEvent;
	
	var buscarTitulosGrupoRequest = {
		contrato: self.contrato.numero,
		grupo: self.getSelector("nroGrupoVisualizarTitulosGrupoReadOnly").html(),
		senial: self.contrato.montoSenialTT.senial
	};
	
	var data = {
		async: false,
		action:"altaContratoBuscarTitulosGrupoReadOnly.action", 
		request: Component.serialize(buscarTitulosGrupoRequest, "buscarTitulosGrupoRequest"),
		method: "GET", 
		responseObject: "titulosGrupo", 
		callback: function(titulosGrupo) {
			/* Cargar los títulos y la cantidad en pantalla */
			self.getSelector("gridTitulosGrupoReadOnly").clearGridData().setGridParam({data: titulosGrupo}).trigger('reloadGrid');
			self.getSelector("totalTitulosGrupoVisualizarTitulosGrupoReadOnly").html(titulosGrupo.length);
			/* Desbloquear pantalla */
			BLOCK.hideBlock();
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.volverVisualizarTitulosGrupoReadOnly = function() {
	var self = altaContratoEvent;
	self.getSelector("formVisualizarTitulosGrupoReadOnly").hide();
	self.getSelector("formVisualizarGruposContrato").show();
};

/* Initialize */
var altaContratoEvent = new AltaContratoEvent(new DivDefinition('AltaContratoEventId'));