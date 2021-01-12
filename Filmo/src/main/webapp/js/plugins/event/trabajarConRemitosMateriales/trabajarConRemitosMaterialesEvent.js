/**
 * FP240 - Trabajar con remitos de materiales
 * @author cetorres
 */
/***********************************************************************************		
 ********************	 Trabajar con remitos de materiales		********************
 ***********************************************************************************/
function TrabajarConRemitosMaterialesEvent(div) {
	this.div = div;
	this.remito = new Remito();
	this.selectMotivo = [ 
  		{codigo: "SD", descripcion: "Devolución"},
  		{codigo: "SP", descripcion: "Préstamo"}
  	];
};

extend(TrabajarConRemitosMaterialesEvent, Plugin);

TrabajarConRemitosMaterialesEvent.prototype.create = function() {
	var self = trabajarConRemitosMaterialesEvent;
	self.service = new TrabajarConRemitosMaterialesService();
	Component.get("html/trabajarConRemitosMateriales/trabajarConEmisionRemitos.html", trabajarConRemitosMaterialesEvent.draw);
};

TrabajarConRemitosMaterialesEvent.prototype.draw = function(comp) {
	var self = trabajarConRemitosMaterialesEvent;
	$("#viewContainer").empty().append(comp.replace(/{{id}}/g, self.div.id));
	Accordion.getInstance(self.getSelector("accordionFormCabecera"));
	/* Campos */
	Component.populateSelect(self.getSelector("motivo"), self.selectMotivo, "codigo", "descripcion", false);
	Datepicker.getInstance(self.getSelector("fechaRemito"), new Date());
	/* Campos numéricos */
	self.getSelector("numeroGuiaFedex").numeric();
	self.getSelector("prestamo").numeric();
	self.getSelector("codigoDistribuidor").numeric();
	/* Botonera */
	self.getSelector("aceptarEmisionRemito").button().click(self.aceptarEmisionRemito);
	self.getSelector("limpiarFiltrosEmisionRemito").button().click(self.limpiarFiltrosEmisionRemito);
	/* Popup */
	self.getSelector("popupDistribuidorPorRazonSocial").click(self.drawPopupSituarDistribuidoresPorRazonSocial);
};

/**
 * Situar distribuidores por razón social
 * @author cetorres
 */
TrabajarConRemitosMaterialesEvent.prototype.drawPopupSituarDistribuidoresPorRazonSocial = function(comp) {
	var data = {razonSocialDistribuidor : ""};
	situarPopupEvent.reset();
	situarPopupEvent.nombreSituar = "Distribuidores por razón social";
	situarPopupEvent.create("buscarDistribuidoresParaRemitosSalida.action", trabajarConRemitosMaterialesEvent.distribuidorSelected, data, "distribuidores");
};

/**
 * Selección situar distribuidores
 * @author cetorres
 */
TrabajarConRemitosMaterialesEvent.prototype.distribuidorSelected = function(distribuidor) {
	var self = trabajarConRemitosMaterialesEvent;
	self.getSelector("codigoDistribuidor").val(distribuidor.codigo);
	self.getSelector("razonSocialDistribuidor").val(distribuidor.razonSocial);
};

/**
 * Comfirmación de la cabecera
 * @author cetorres
 */
TrabajarConRemitosMaterialesEvent.prototype.aceptarEmisionRemito = function() {
	var self = trabajarConRemitosMaterialesEvent;
	var data = {
		codigoDistribuidor: self.getSelector("codigoDistribuidor").val(),
		fechaRemito: self.getSelector("fechaRemito").val(),
		senial : $("#senialDefaultUsuario").val()
	};
	
	if (Validator.isEmpty(self.getSelector("codigoDistribuidor"), false)) {
		Validator.focus(self.getSelector("codigoDistribuidor"), "Debe seleccionar algún distribuidor");
		return;
	} else {
		var isValid = self.service.validarDistribuidor(data);
		if (!isValid) {
			return;
		}
	}
	
	var isValid = self.service.validarFechaSenial(data);
	if (!isValid) {
		return;
	}
	
	/* Validación para motivo: Debe ingresar un motivo de préstamo/devolución */
	if (Validator.isEmpty(self.getSelector("motivo"), false)) {
		Validator.focus(self.getSelector("motivo"), "Debe seleccionar algún motivo");
		return;
	}
	if (self.getSelector("motivo").val() == "SP" && Validator.isEmpty(self.getSelector("prestamo"), false)) {
		Validator.focus(self.getSelector("prestamo"), "Debe ingresar los días de préstamo");
		return;
	}
	if (self.getSelector("motivo").val() == "SD" && !Validator.isEmpty(self.getSelector("prestamo"), false)) {
		Validator.focus(self.getSelector("prestamo"), "No debe ingresar los días de préstamo si el motivo es devolución");
		return;
	}

	self.remito.senial = $("#senialDefaultUsuario").val();
	self.remito.fechaRemito = self.getSelector("fechaRemito").val();
	self.remito.numFedex = self.getSelector("numeroGuiaFedex").val() ? self.getSelector("numeroGuiaFedex").val() : 0;
	self.remito.destinatario = self.getSelector("destinatario").val();
	self.remito.motivo = self.getSelector("motivo").val();
	self.remito.descripcionMotivo = (self.remito.motivo == "SD" ? "SD Salida por devolución" : "SP Salida por préstamo");
	self.remito.diasPrestamo = (self.remito.motivo == "SP" ? self.getSelector("prestamo").val() : 0);
	self.remito.observaciones = self.getSelector("observaciones").val();
	self.remito.codigoDistribuidor = self.getSelector("codigoDistribuidor").val();
	self.remito.razonSocialDistribuidor = self.getSelector("razonSocialDistribuidor").val() != "" ? self.getSelector("razonSocialDistribuidor").val()
																								  : self.service.getDatosDistribuidor({codigoDistribuidor : self.getSelector("codigoDistribuidor").val()});
	self.getCompSeleccionTitulosRemito();
	self.limpiarFiltrosEmisionRemito();
};


/**
 * Limpieza de filtros
 * @author cetorres
 */
TrabajarConRemitosMaterialesEvent.prototype.limpiarFiltrosEmisionRemito = function() {
	var self = trabajarConRemitosMaterialesEvent;
	self.getSelector("fechaRemito").datepicker('setDate', new Date());
	self.getSelector("numeroGuiaFedex").val(null);
	self.getSelector("destinatario").val(null);
	self.getSelector("motivo").val(null);
	self.getSelector("prestamo").val(null);
	self.getSelector("observaciones").val(null);
	self.getSelector("codigoDistribuidor").val(null);
	self.getSelector("razonSocialDistribuidor").val(null);
};

/***********************************************************************************		
 ********************	Selección de títulos para el remito		********************
 ***********************************************************************************/
TrabajarConRemitosMaterialesEvent.prototype.getCompSeleccionTitulosRemito = function() {
	Component.get("html/trabajarConRemitosMateriales/seleccionTitulosRemito.html", trabajarConRemitosMaterialesEvent.drawFormSeleccionTitulosRemito);
};

TrabajarConRemitosMaterialesEvent.prototype.drawFormSeleccionTitulosRemito = function(comp) {
	var self = trabajarConRemitosMaterialesEvent;
	self.getSelector("divFormCabecera").hide();
	comp = comp.replace(/{{id}}/g, self.div.id)
			   .replace(/{{nroRemito}}/g, "")
			   .replace(/{{fechaRemito}}/g, self.remito.fechaRemito)
			   .replace(/{{razonSocialDistribuidor}}/g, self.remito.razonSocialDistribuidor)
			   .replace(/{{descripcionMotivo}}/g, self.remito.descripcionMotivo)
			   .replace(/{{destinatario}}/g, self.remito.destinatario);
	if (self.getSelector("divSeleccionTitulosRemito").length) {
		self.getSelector("divSeleccionTitulosRemito").replaceWith(comp);
	} else {
		self.getDiv().append(comp);
	}
	/* Accordion */
	Accordion.getInstance(self.getSelector("accordionSeleccionTitulosRemito"));
	Accordion.getInstance(self.getSelector("accordionCargaTitulosEmisionRemito"));
	/* Botonera */
	self.getSelector("limpiarFiltros").button().click(self.limpiarFiltrosTitulosEmisionRemito);
	self.getSelector("volverFormCabeceraRemito").button().click(self.confirmarRetorno);
	self.getSelector("guardarTitulosEmisionRemito").button().click(self.validarAgregarTitulo);
	self.getSelector("emitirRemitos").button().click(self.emitirRemitos);
	/* Campos */
	self.getSelector("capitulo").numeric();
	self.getSelector("parte").numeric();
	/* Popup */
	self.getSelector("popupClave").click(self.drawPopupSituarTitulosPorClave);
	self.getSelector("popupCapitulo").click(self.drawPopupSituarCapituloPorClave);
	/* Con cap - SE MS SU */
	/* Sin cap - LM PE LA */
	self.drawGridTitulos();
};


/**
 * Situar distribuidores por razón social
 * @author cetorres
 */
TrabajarConRemitosMaterialesEvent.prototype.drawPopupSituarTitulosPorClave = function(comp) {
	var data = {
		descripcionTitulo:	"",
		codigoDistribuidor:	trabajarConRemitosMaterialesEvent.remito.codigoDistribuidor
	};
	situarPopupEvent.reset();
	situarPopupEvent.nombreSituar = "Títulos por descripción";
	situarPopupEvent.create("buscarTitulosParaCarga.action", trabajarConRemitosMaterialesEvent.tituloSelected, data, "titulos");
};

/**
 * Selección situar distribuidores
 * @author cetorres
 */
TrabajarConRemitosMaterialesEvent.prototype.tituloSelected = function(titulo) {
	var self = trabajarConRemitosMaterialesEvent;
	self.getSelector("clave").val(titulo.clave);
	self.getSelector("programa").val(titulo.titulo);
};

/**
 * Situar distribuidores por razón social
 * @author cetorres
 */
TrabajarConRemitosMaterialesEvent.prototype.drawPopupSituarCapituloPorClave = function(comp) {
	var self = trabajarConRemitosMaterialesEvent;
	
	if (Validator.isEmpty(self.getSelector("clave"))) {
		Validator.focus(self.getSelector("clave"), "Debe ingresar un título");
		self.getSelector("clave").val("");
		return false;
	} else if (self.getSelector("clave").val().substring(0,2) == "LM" || self.getSelector("clave").val().substring(0,2) == "PE" || self.getSelector("clave").val().substring(0,2) == "LA") {
		MESSAGE.alert("No se puede elegir capítulo y/o parte si el título es sin capítulo.", 3000);
		return false;
	} 
	
	var data = {
		capitulo:"",
		claveTitulo: trabajarConRemitosMaterialesEvent.getSelector("clave").val()
	};
	situarPopupEvent.reset();
	situarPopupEvent.nombreSituar = "Capítulos del título";
	situarPopupEvent.create("buscarCapitulosParaCarga.action", trabajarConRemitosMaterialesEvent.capituloSelected, data, "capitulos");
};

/**
 * Selección situar distribuidores
 * @author cetorres
 */
TrabajarConRemitosMaterialesEvent.prototype.capituloSelected = function(capitulo) {
	var self = trabajarConRemitosMaterialesEvent;
	self.getSelector("capitulo").val(capitulo.numeroCapitulo);
	self.getSelector("parte").val(capitulo.numeroParte);
};

TrabajarConRemitosMaterialesEvent.prototype.drawGridTitulos = function() {
	var self = trabajarConRemitosMaterialesEvent;
	self.getSelector("gridCargaTitulos").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Clave', 'Programa', 'Capítulo', 'Parte','Cantidad'],
		colModel:[ 
			{name:'clave', 		index:'clave', 		sortable:false,	align:'left'},
			{name:'programa',   index:'programa',   sortable:false,	align:'left'},
			{name:'capitulo', 	index:'capitulo', 	sortable:false, align:'center'},
			{name:'parte', 	    index:'parte', 	    sortable:false, align:'center'},
		    {name:'cantidad',   index:'cantidad',   sortable:false, hidden:true}
		],
		rowNum: 20,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#TrabajarConRemitosMaterialesEventId_pagerCargaTitulos',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Títulos'
	});
};

TrabajarConRemitosMaterialesEvent.prototype.validarAgregarTitulo = function() {
	var self = trabajarConRemitosMaterialesEvent;
	
	if (Validator.isEmpty(self.getSelector("clave"))) {
		Validator.focus(self.getSelector("clave"), "Debe ingresar un título");
		self.getSelector("clave").val("");
		return false;
	} else if ((self.getSelector("clave").val().substring(0,2) == "LM" || self.getSelector("clave").val().substring(0,2) == "PE" 
		     || self.getSelector("clave").val().substring(0,2) == "LA") && (!Validator.isEmpty(self.getSelector("capitulo")) || !Validator.isEmpty(self.getSelector("parte")))) {
		MESSAGE.alert("No se puede elegir capítulo y/o parte si el título es sin capítulo.", 3000);
		self.getSelector("capitulo").effect("highlight", {color:'yellow'}, 3000);
		self.getSelector("parte").effect("highlight", {color:'yellow'}, 3000);
		self.getSelector("capitulo").val("");
		self.getSelector("parte").val("");
		return false;
	} else if(self.getSelector("clave").val().substring(0,2) == "SE" || self.getSelector("clave").val().substring(0,2) == "SU"  || self.getSelector("clave").val().substring(0,2) == "MS" ) {
		// si es un titulo con capitulos
		if (Validator.isEmpty(self.getSelector("capitulo")) || Validator.isEmpty(self.getSelector("parte"))){
			MESSAGE.alert("El capítulo y parte son obligatorios si el título es con capítulo.", 3000);
			self.getSelector("capitulo").effect("highlight", {color:'yellow'}, 3000);
			self.getSelector("parte").effect("highlight", {color:'yellow'}, 3000);
			return false;
		}
		if (!Validator.isEmpty(self.getSelector("capitulo"))){
			var datos = {
					"capitulo" : self.getSelector("capitulo").val(),
					"claveTitulo " : self.getSelector("clave").val()
			};
			if (!self.service.validarCapitulo(datos)) {
				MESSAGE.alert("No existe el capitulo", 3000);
				self.getSelector("capitulo").effect("highlight", {color:'yellow'}, 3000);
				return false;
			}
		}
	}
	
	self.agregarTitulo();
};

TrabajarConRemitosMaterialesEvent.prototype.emitirRemitos = function() {
var self = trabajarConRemitosMaterialesEvent;
BLOCK.showBlock("Guardando remito...");
	if (self.getSelector("gridCargaTitulos").getRowData().length == 0) {
		BLOCK.hideBlock();
		var popup = popupConfirmacionEvent;
		var mensajeConfirmacion = "El remito esta vacio y  no se generará. ¿Desea cancelarlo?";    
		
		popup.create(self.div.id, mensajeConfirmacion);
		popup.confirmar = function() {
			self.volverCabeceraRemito();
			popup.close();
		};
		popup.cancelar = function() {
			popup.close();
		};
	} else {
		var rows = self.getSelector("gridCargaTitulos").jqGrid('getGridParam','data');
		var idRemito;
		$.each(rows, function (i, fila) {
			var data = { 'clave' : fila.clave,
					 'capitulo' : fila.capitulo,
					 'parte' : fila.parte
			};
			if (fila.cantidad == "S") {
				self.actualizarCantidad(data);
			}
			idRemito = self.guardar(data);
		});
		if (idRemito != undefined){
			self.generarReporteRemito(idRemito);
		}
		BLOCK.hideBlock();
		self.volverCabeceraRemito();
	}
};


TrabajarConRemitosMaterialesEvent.prototype.agregarTitulo = function() {
	var self = trabajarConRemitosMaterialesEvent;
	
	var registro = { 'clave' : self.getSelector("clave").val().toUpperCase(),
					 'programa' : self.getSelector("programa").val(),
					 'capitulo' : self.getSelector("capitulo").val(),
					 'parte' : self.getSelector("parte").val() == 0 ? "" :self.getSelector("parte").val()
	};

	var data = self.getSelector("gridCargaTitulos").getRowData();
	var exists = false;
	$.each(data, function (i, fila) {
	    if (fila.clave == registro.clave && fila.capitulo == registro.capitulo && fila.parte == registro.parte) {
	    	exists = true;
	    	return;
	    }
	});

	if (!exists) {
		self.validarNuevaSenial(registro);
	} else {
		MESSAGE.alert("Los datos ya fueron ingresados previamente");
	}
};

TrabajarConRemitosMaterialesEvent.prototype.validarNuevaSenial = function(registro) {
	var self = trabajarConRemitosMaterialesEvent;
	
	registro.capitulo = (registro.capitulo == "" ? 0 : registro.capitulo);
	registro.parte = (registro.parte == "" ? 0 : registro.parte);
	registro.senial = $("#senialDefaultUsuario").val();
	delete registro.programa;
	
	self.service.validarNuevaSenial(registro);
};

TrabajarConRemitosMaterialesEvent.prototype.abrirPopupErrores = function(params) {
	var self = trabajarConRemitosMaterialesEvent;
	Component.get("html/trabajarConRemitosMateriales/popupErrores.html", function(comp) {
		self.drawPopupErrores(comp, params);
	});
};

TrabajarConRemitosMaterialesEvent.prototype.drawPopupErrores = function(comp, params) {
	var self = trabajarConRemitosMaterialesEvent;
	
	$("#popupErrores").dialog({
		width: 600, show: 'blind', title: params.mensaje, hide: 'blind', modal: true, autoOpen: false,
		buttons: {
			"Salir": function() {
				$(this).dialog("close");
			}
		}
	});
	$("#popupErrores").empty().append(comp.replace(/{{id}}/g, self.div.id));
	$("#popupErrores").dialog("open");
	
	/* Botonera */
	self.getSelector("descontarPopupErrores").button().click(function() {
		self.cierrePopupErrores();
		trabajarConRemitosMaterialesEvent.recargarGrillaTitulos();

	});
	self.getSelector("noDescontarPopupErrores").button().click(function() {
		self.cierrePopupErrores();
		trabajarConRemitosMaterialesEvent.recargarGrillaTitulos("N");

	});
	/* Grilla */
	self.drawGridPopupErrores(params.registros);
};

TrabajarConRemitosMaterialesEvent.prototype.drawGridPopupErrores = function(registros) {
	var self = trabajarConRemitosMaterialesEvent;
	
	self.getSelector("gridPopupErrores").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Fecha desde', 'Fecha hasta', 'Clave', 'Contrato', 'Grupo', 'Exhibición'],
		colModel:[ 
          	{name:'fechaDesde',	 index:'fechaDesde', 	align:'center',	formatter:'date', formatoptions:{newformat: 'd/m/Y'}},
          	{name:'fechaHasta',	 index:'fechaHasta', 	align:'center',	formatter:'date', formatoptions:{newformat: 'd/m/Y'}},
          	{name:'clave', 		 index:'clave', 		align:'center'},
          	{name:'contrato', 	 index:'contrato', 		align:'center'},
          	{name:'grupo', 		 index:'grupo', 		align:'center'},
          	{name:'exhibicion',  index:'exhibicion', 	align:'center'}
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + self.div.id + '_pagerPopupErrores',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray'
	});
	
	self.getSelector("gridPopupErrores").clearGridData().setGridParam({data: registros}).trigger('reloadGrid');
};

TrabajarConRemitosMaterialesEvent.prototype.cierrePopupErrores = function() {
	$("#popupErrores").dialog("close");
	$("#popupErrores").dialog("remove");
};

TrabajarConRemitosMaterialesEvent.prototype.actualizarCantidad = function(data) {
	var self = trabajarConRemitosMaterialesEvent;
	
	var actualizarCantidadRequest = {
		clave: data.clave.toUpperCase(), 
		capitulo: data.capitulo,
		parte: data.parte  
	};
	
	self.service.actualizarCantidad(actualizarCantidadRequest);
};

TrabajarConRemitosMaterialesEvent.prototype.guardar = function(data) {
	var self = trabajarConRemitosMaterialesEvent;
	self.remito.clave = data.clave.toUpperCase(); 
	self.remito.capitulo = data.capitulo;
	self.remito.parte = data.parte; 
	self.remito.estadoImprimir = "S";

	var ejecutarRemitoRequest = JSON.parse(JSON.stringify(self.remito));
	delete ejecutarRemitoRequest.razonSocialDistribuidor;
	delete ejecutarRemitoRequest.descripcionMotivo;
	var result = self.service.ejecutarRemito(ejecutarRemitoRequest);
	return result;
};

TrabajarConRemitosMaterialesEvent.prototype.recargarGrillaTitulos = function(data) {
	var self = trabajarConRemitosMaterialesEvent;
	var registro = { 'clave' : self.getSelector("clave").val().toUpperCase(),
					 'programa' : self.getSelector("programa").val(),
					 'capitulo' : self.getSelector("capitulo").val(),
					 'parte' : self.getSelector("parte").val() == 0 ? "" :self.getSelector("parte").val(),
					 'cantidad' : data == undefined ? "S" : data
	};
	self.getSelector("gridCargaTitulos").addRowData(self.getSelector("gridCargaTitulos").getGridParam('records') + 1, registro, "last", null);
	self.getSelector("gridCargaTitulos").trigger("reloadGrid");
	self.limpiarFiltrosTitulosEmisionRemito();
};

/**
 * Limpieza de filtros
 * @author cetorres
 */
TrabajarConRemitosMaterialesEvent.prototype.limpiarFiltrosTitulosEmisionRemito = function() {
	var self = trabajarConRemitosMaterialesEvent;
	self.getSelector("clave").val(null);
	self.getSelector("programa").val(null);
	self.getSelector("capitulo").val(null);
	self.getSelector("parte").val(null);
};

/**
 * Apertura del popup de confirmacion de retorno
 * @author cetorres
 */
TrabajarConRemitosMaterialesEvent.prototype.confirmarRetorno = function() {
	var self = trabajarConRemitosMaterialesEvent;
	
	if (self.getSelector("gridCargaTitulos").getRowData().length == 0) {
		var popup = popupConfirmacionEvent;
		var mensajeConfirmacion = "El remito no se generará. ¿Desea cancelarlo?";    
		
		popup.create(self.div.id, mensajeConfirmacion);
		popup.confirmar = function() {
			self.volverCabeceraRemito();
			popup.close();
		};
		popup.cancelar = function() {
			popup.close();
		};
	} else {
		self.volverCabeceraRemito();
	}
};


TrabajarConRemitosMaterialesEvent.prototype.volverCabeceraRemito = function() {
	var self = trabajarConRemitosMaterialesEvent;
	self.getSelector("divSeleccionTitulosRemito").hide();
	self.getSelector("divFormCabecera").show();
	self.remito = new Remito();
};

TrabajarConRemitosMaterialesEvent.prototype.imprimirRemito = function(idRemito) {
	var self = trabajarConRemitosMaterialesEvent;
	var imprimirRemitoRequest = {idRemito: idRemito};
	self.service.imprimirRemito(imprimirRemitoRequest);
	self.generarReporteRemito(idRemito);
};

TrabajarConRemitosMaterialesEvent.prototype.generarReporteRemito = function(idRemito) {
	var self = trabajarConRemitosMaterialesEvent;
	var imprimirRemitoRequest = {idRemito: idRemito};
	self.service.generarReporteRemito(imprimirRemitoRequest);
};

function Remito() {
	this.fechaRemito = new Date();
	this.numFedex = new Number();
	this.destinatario = new String();
	this.motivo = new String();
	this.descripcionMotivo = new String();
	this.diasPrestamo = new Number();
	this.observaciones = new String();
	this.codigoDistribuidor = new Number(); 
	this.razonSocialDistribuidor = new String();
	this.senial = new String();
	this.estadoImprimir = new String();
	this.clave = new String();
	this.capitulo = new String();
	this.parte = new String();
};

/* Initialize */
var trabajarConRemitosMaterialesEvent = new TrabajarConRemitosMaterialesEvent(new DivDefinition('TrabajarConRemitosMaterialesEventId'));