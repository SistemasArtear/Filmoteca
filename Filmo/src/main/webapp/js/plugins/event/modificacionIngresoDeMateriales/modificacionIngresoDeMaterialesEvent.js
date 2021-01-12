/**
 * FP220 - Modificación de ingreso de materiales
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.CASTELLANO = "castellano";
ModificacionIngresoDeMaterialesEvent.ORIGINAL = "original";
/***********************************************************************************		
 ********************	 Trabajar con ingreso de materiales		********************
 ***********************************************************************************/
function ModificacionIngresoDeMaterialesEvent(div) {
	this.div = div;
	this.tipoBusquedaTitulos = ModificacionIngresoDeMaterialesEvent.CASTELLANO;
	this.tipoBusquedaTitulosPopup = ModificacionIngresoDeMaterialesEvent.CASTELLANO;
	this.remito = {
			cabecera : {
				idRemito : new Number(),
				fechaIngreso : new Date(),
				fechaRemito : new Date(),
				razonSocialDistribuidor : new String(),
				codigoDistribuidor : new Number(),
				numeroRemito : new String(),
				numeroGuia : new String()
			},
			detalle : {
				titulo : new String(),
				clave : new String(),
				capitulo: new Number(),
				nroParte: new Number(),
				er: new String(),
				codSoporte: new String(),
				descSoporte: new String(),
				cantidadRollos: new Number(),
				codMotivoIngreso: new String(),
				descMotivoIngreso: new String(),
				senial : new String(),
				carrete: new Number(),
				lata: new Number(),
				torta: new Number(),
				borroCh : new String()
			}
	};
	this.modoEdicion;
	this.remitoParaEliminar;
	this.selectTiposBusqueda = [ 
		{codigo: "1", descripcion: "Remito"},
		{codigo: "2", descripcion: "Número de guía"},
		{codigo: "3", descripcion: "Proveedor"},
		{codigo: "4", descripcion: "Fecha"}
	];
};

extend(ModificacionIngresoDeMaterialesEvent, Plugin);

ModificacionIngresoDeMaterialesEvent.prototype.create = function() {
	var self = modificacionIngresoDeMaterialesEvent;
	self.service = new ModificacionIngresoDeMaterialesService();
	Component.get("html/modificacionIngresoDeMateriales/listadoOrden.html", modificacionIngresoDeMaterialesEvent.draw);
};

ModificacionIngresoDeMaterialesEvent.prototype.draw = function(comp) {
	var self = modificacionIngresoDeMaterialesEvent;
	$("#viewContainer").empty().append(comp.replace(/{{id}}/g, self.div.id));

	Accordion.getInstance(self.getAccordionOrden());
	Component.populateSelect(self.getSelectTipoBusqueda(), self.selectTiposBusqueda, "codigo", "descripcion", false);
	
	self.getSelector("popupDistribuidorPorRazonSocial").click(self.drawPopupSituarDistribuidoresPorRazonSocial);
	self.getSelector("popupTitulosPorDistribuidor").click(self.getCompPopupTitulosPorDistribuidor);
	self.getSelectTipoBusqueda().change(self.reloadSituar);
	self.getInputSituarEn().keypress(function (event) {
		if (event.which == 13) {
			event.preventDefault();
			self.determinarTipoBusqueda();
			return;
		}
	});
	self.getAceptarBusquedaRemito().button().click(self.determinarTipoBusqueda);
	self.drawGridOrden();
};

/**
 * Situar distribuidores por razón social
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.drawPopupSituarDistribuidoresPorRazonSocial = function() {
    var distribuidor = $("#ModificacionIngresoDeMaterialesEventId_situarEn").val();
    var data = {razonSocialDistribuidor : distribuidor};
	situarPopupEvent.reset();
	situarPopupEvent.nombreSituar = "Proveedores por razón social";
	situarPopupEvent.create("buscarDistribuidoresParaRemitos.action", modificacionIngresoDeMaterialesEvent.distribuidorSelected, data, "distribuidores");
	   situarPopupEvent.doAfterDrawGrid = function() {
	        $("#buscarDistribuidoresParaRemitos_inputBusquedaSituar").val(distribuidor);
	        situarPopupEvent.getGrid().sortGrid('razonSocial', true);
	        situarPopupEvent.getGrid().setColProp('codigo', {sorttype:'number', align:'right'}).trigger("reloadGrid");
	        situarPopupEvent.getGrid().setColProp('razonSocial', {sorttype:'text', align:'left'}).trigger("reloadGrid");
	    };
};

/**
 * Selección situar distribuidores
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.distribuidorSelected = function(distribuidor) {
	var self = modificacionIngresoDeMaterialesEvent;
	self.getInputSituarEn().val(distribuidor.razonSocial);
};

ModificacionIngresoDeMaterialesEvent.prototype.getCompPopupTitulosPorDistribuidor = function() {
	Component.get("html/modificacionIngresoDeMateriales/popupTituloCapitulo.html", modificacionIngresoDeMaterialesEvent.drawPopupTitulosPorDistribuidor);
};

ModificacionIngresoDeMaterialesEvent.prototype.drawPopupTitulosPorDistribuidor = function(comp) {
	var self = modificacionIngresoDeMaterialesEvent;
	
	$("#popupTituloCapituloContainer").empty().append(comp.replace(/{{id}}/g, self.div.id));
	self.getSelector("popupClave").click(self.drawPopupSituarClave);
	self.getSelector("popupCapitulo").click(self.drawPopupSituarCapitulo);
	self.getSelector("popupParte").click(self.drawPopupSituarParte);
	self.getSelector("buttonBusqueda").button().click(self.buscarTituloCapitulo); 
	
	$("#popupTituloCapituloContainer").dialog({
		width: 600,
		show: 'blind',
		hide: 'blind',
		modal: true,
		autoOpen: false,
		buttons: {
			"Salir": function() {
				$(this).dialog("close");
			}
		}
	});
	
	$("#popupTituloCapituloContainer").dialog("open");
};

/**
 * Situar titulos para búsqueda de remitos
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.drawPopupSituarClave = function() {
	var self = modificacionIngresoDeMaterialesEvent; 
	
	var data = { descripcionTituloCast : "" };
	situarPopupEvent.reset();
	situarPopupEvent.setColumns(['Clave', 'Título'], [{index:'clave', name:'clave', align: 'left'}, {index:'titulo', name:'titulo', align: 'left'}]);
	situarPopupEvent.nombreSituar = "Titulos por descripción - " + modificacionIngresoDeMaterialesEvent.tipoBusquedaTitulosPopup;
	situarPopupEvent.create("buscarTitulosPorDescripcion.action", modificacionIngresoDeMaterialesEvent.tituloSelected, data, "titulos");
	setTimeout(function() {
		$("#buscarTitulosPorDescripcion_inputBusquedaSituar").after("<span id=\"cambiarTipoBusquedaTituloPopup\" style=\"cursor: pointer; display: inline-block;\" class=\"ui-icon ui-icon-transferthick-e-w\" title=\"Cambiar tipo de búsqueda\" />");
		$("#cambiarTipoBusquedaTituloPopup").click(modificacionIngresoDeMaterialesEvent.cambiarTipoBusquedaTitulosPopup);
	}, 1000);
	/* Restauro las columnas al finalizar la selección */
	self.resetearColumnasSituar();
};

/**
 * Selección situar títulos
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.tituloSelected = function(titulo) {
	var self = modificacionIngresoDeMaterialesEvent;
	self.getSelector("inputClave").val(titulo.clave);
	self.getSelector("inputDescripcion").val(titulo.titulo);
	/* Lo llevo al estado original */
	situarPopupEvent.doAfterDraw = function() {
		return;
	};
};

/**
 * Cambio del criterio de búsqueda de títulos
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.cambiarTipoBusquedaTitulosPopup = function() {
	var self = modificacionIngresoDeMaterialesEvent;
	
	if (self.tipoBusquedaTitulosPopup === ModificacionIngresoDeMaterialesEvent.CASTELLANO) {
		self.tipoBusquedaTitulosPopup = ModificacionIngresoDeMaterialesEvent.ORIGINAL;
		situarPopupEvent.data = { descripcionTituloOrig : ""};
	} else {
		self.tipoBusquedaTitulosPopup = ModificacionIngresoDeMaterialesEvent.CASTELLANO;
		situarPopupEvent.data = { descripcionTituloCast : ""};
	}
	
	$("#ui-dialog-title-buscarTitulosPorDescripcion_popupSituar").html("Titulos por descripción - " + self.tipoBusquedaTitulosPopup);
	$("#gview_buscarTitulosPorDescripcion_gridSituar .ui-jqgrid-title").html("Titulos por descripción - " + self.tipoBusquedaTitulosPopup);
	$("#buscarTitulosPorDescripcion_situarLabel").html("Titulos por descripción - " + self.tipoBusquedaTitulosPopup);
};

/**
 * Situar capítulos para búsqueda de remitos
 * @author cetorres
 */	
ModificacionIngresoDeMaterialesEvent.prototype.drawPopupSituarCapitulo = function() {
	var self = modificacionIngresoDeMaterialesEvent; 

	if (Validator.isEmpty(self.getSelector("inputClave"))) {
		Validator.focus(self.getSelector("inputClave"), "Debe ingresar un título");
		self.getSelector("inputClave").val("");
	} else if (self.getSelector("inputClave").val().substring(0,2) == "LM" || self.getSelector("inputClave").val().substring(0,2) == "PE" 
		     || self.getSelector("inputClave").val().substring(0,2) == "LA") {
		MESSAGE.alert("No se puede elegir capítulo y/o parte si el título es sin capítulo.", 3000);
	} else {
		var data = {clave: self.getSelector("inputClave").val(), capitulo : ""};
		situarPopupEvent.reset();
		situarPopupEvent.setColumns(['Capítulo', ''], [{index:'numeroCapitulo', name:'numeroCapitulo', align: 'center'}, {index:'numeroParte', name:'numeroParte', hidden:true}]); 
		situarPopupEvent.nombreSituar = "Capítulos del título";
		situarPopupEvent.create("buscarCapitulos.action", modificacionIngresoDeMaterialesEvent.capituloSelected, data, "capitulos", true);
		/* Restauro las columnas al finalizar la selección */
		self.resetearColumnasSituar();
	}
};

/**
 * Selección situar capítulos
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.capituloSelected = function(capitulo) {
	var self = modificacionIngresoDeMaterialesEvent;
	self.getSelector("inputCapitulo").val(capitulo.numeroCapitulo);
	/* Lo llevo al estado original */
	situarPopupEvent.doAfterDraw = function() {
		return;
	};
};

/**
 * Situar partes para búsqueda de remitos
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.drawPopupSituarParte = function() {
	var self = modificacionIngresoDeMaterialesEvent; 
	var data = {clave : self.getSelector("inputClave").val(), capitulo: self.getSelector("inputCapitulo").val()};
	situarPopupEvent.reset();
	situarPopupEvent.setColumns(['', 'Parte'], [{index:'numeroCapitulo', name:'numeroCapitulo', hidden:true}, {index:'numeroParte', name:'numeroParte', align: 'center'}]); 
	situarPopupEvent.nombreSituar = "Partes del capítulo de un título";
	situarPopupEvent.create("buscarPartes.action", modificacionIngresoDeMaterialesEvent.parteSelected, data, "capitulos", true);
	/* Restauro las columnas al finalizar la selección */
	self.resetearColumnasSituar();
};

/**
 * Selección situar títulos
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.parteSelected = function(parte) {
	var self = modificacionIngresoDeMaterialesEvent;
	self.getSelector("inputParte").val(parte.numeroParte);
	/* Lo llevo al estado original */
	situarPopupEvent.doAfterDraw = function() {
		return;
	};
};

/**
 * Restaura las columnas al orden original
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.resetearColumnasSituar = function() {
	situarPopupEvent.doAfterDraw = function() {
		situarPopupEvent.getPopup().dialog({
			close: function(event, ui) {
				situarPopupEvent.setColumns(undefined, undefined);
				situarPopupEvent.getDiv().remove();
				$(this).remove();
	    	}
		});
	};
};

ModificacionIngresoDeMaterialesEvent.prototype.buscarTituloCapitulo = function() {
	var self = modificacionIngresoDeMaterialesEvent; 
	self.getSelector("claveTitulo").html(self.getSelector("inputClave").val());
	if (self.getSelector("inputDescripcion").val().trim() != "") {
		self.getSelector("descripcionTitulo").html(" - " + self.getSelector("inputDescripcion").val());
	}
	self.getSelector("capituloTitulo").html(self.getSelector("inputCapitulo").val());
	self.getSelector("parteTitulo").html(self.getSelector("parteTitulo").val());
	$("#popupTituloCapituloContainer").dialog("close");
	$("#popupTituloCapituloContainer").dialog("remove");
};

/**
 * jqGrid de remitos
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.drawGridOrden = function() {
	var self = modificacionIngresoDeMaterialesEvent;
	self.getGridOrden().jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['', '', 'Distribuidor', 'Fecha Rto./Guía', 'Remito', 'Guía', 'Fecha de ingreso', '', '', '', ''],
		colModel:[ 
		    {name:'idRemito', 					index:'idRemito',					hidden:true},      
		    {name:'codigoDistribuidor', 		index:'codigoDistribuidor',			hidden:true},
		    {name:'razonSocialDistribuidor', 	index:'razonSocialDistribuidor', 	width:300, 	sortable:true,	align:'left', 	formatter:function(value, options, rData) { return rData['codigoDistribuidor'] + " " + value; }}, 
			{name:'fechaRemitoGuia', 			index:'fechaRemitoGuia',			width:80, 	sortable:true,	align:'center',	formatter:'date', formatoptions : {newformat: 'd/m/Y'}},  
			{name:'numeroRemito', 				index:'numeroRemito', 				width:100, 	sortable:true,	align:'center'}, 
			{name:'numeroGuia', 				index:'numeroGuia', 				width:100, 	sortable:true,	align:'center'}, 
			{name:'fechaMovimiento', 			index:'fechaMovimiento',			width:80, 	sortable:false,	align:'center',	formatter:'date', formatoptions : {newformat: 'd/m/Y'}},    
			{name:'verRemito', 					index:'verRemito', 					width:30, 	sortable:false, align:'center'},
			{name:'modificar',					index:'modificar',					width:30, 	sortable:false, align:'center'},
			{name:'trabajarConTitulos',			index:'trabajarConTitulos',			width:30, 	sortable:false, align:'center'},
			{name:'eliminarRemito',				index:'eliminarRemito', 			width:30, 	sortable:false, align:'center'}
		],
		rowNum: 20,
		rowList:[10,20,30],
	   	scrollOffset: 0,
	   	pager: "#" + this.div.id + '_pagerOrden',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Remitos',
		gridComplete: function() { 							
			var ids = $(this).jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var divVerRemito = "<div onclick='modificacionIngresoDeMaterialesEvent.getCompRemitoSeleccionado(" + ids[i] + ")' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-search conTooltip' title='Ver remito'></span></div>";
				var divModificar = "<div onclick='modificacionIngresoDeMaterialesEvent.getCompModificarRemitoSeleccionado(" + ids[i] + ")' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-pencil conTooltip' title='Modificar'></span></div>";
				var divTrabajarConTitulos = "<div onclick='modificacionIngresoDeMaterialesEvent.getCompTrabajarConTitulos(" + ids[i] + ")' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-pencil conTooltip' title='Trabajar con títulos'></span></div>";
    			var divEliminarRemito = "<div onclick='modificacionIngresoDeMaterialesEvent.validarEliminarRemito(\"" + ids[i] + "\")' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-trash conTooltip' title='Eliminar remito'></span></div>";
				$(this).jqGrid('setRowData', ids[i], { verRemito: divVerRemito, modificar: divModificar, trabajarConTitulos: divTrabajarConTitulos, eliminarRemito: divEliminarRemito });
			}
		}
	});
	
	/* Grilla con tamaño automático */
	Component.resizableGrid(self.getGridOrden());
};

/**
 * Llenado de jqGrid de remitos
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.fillGridNroOrdenRemito = function(data) {
	var self = modificacionIngresoDeMaterialesEvent;
	self.getGridOrden().clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};

ModificacionIngresoDeMaterialesEvent.prototype.reloadSituar = function() {
	var self = modificacionIngresoDeMaterialesEvent;

	switch (parseInt(self.getSelectTipoBusqueda().val())) {
		case 1:
			self.destroyDatepicker("Orden Número de remito");
			break;
		case 2:
			self.destroyDatepicker("Orden Número de guía");
			break;
		case 3:
			self.destroyDatepicker("Orden Proveedor");
			self.getSelector("popupDistribuidorPorRazonSocial").show();
			self.getSelector("popupTitulosPorDistribuidor").show();
			self.getSelector("filtroTitulo").show();
			break;
		case 4:
			self.destroyDatepicker("Orden Fecha");
			Datepicker.getInstance(self.getInputSituarEn(), new Date());
			break;
		default:
			break;
	}
};

ModificacionIngresoDeMaterialesEvent.prototype.destroyDatepicker = function(labelSituar) {
	var self = modificacionIngresoDeMaterialesEvent;
	self.getInputSituarEn().val("");
	self.getLabelSituarEn().html(labelSituar);
	self.getInputSituarEn().datepicker("destroy");
	self.getInputSituarEn().unmask();
	self.getInputSituarEn().unbind();
	self.getSelector("popupDistribuidorPorRazonSocial").hide();
	self.getSelector("popupTitulosPorDistribuidor").hide();
	self.getSelector("filtroTitulo").hide();
	self.getSelector("claveTitulo").html("");
	self.getSelector("capituloTitulo").html("");
	self.getSelector("parteTitulo").html("");
};

/**
 * Determina que búsqueda debe efectuarse 
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.determinarTipoBusqueda = function() {
	var self = modificacionIngresoDeMaterialesEvent;
	
	switch (parseInt(self.getSelectTipoBusqueda().val())) {
		case 1:
			self.buscarRemitosPorNumeroRemito();
			break;
		case 2:
			self.buscarRemitosPorNumeroGuia();
			break;
		case 3:
			self.buscarRemitosPorRazonSocial();
			break;
		case 4:
			self.buscarRemitosPorFecha();
			break;
		default:
			Validator.focus(self.getSelectTipoBusqueda(), "Debe seleccionar un criterio de búsqueda");
			break;
	}
};

/**
 * Búsqueda de remitos por número de remito
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.buscarRemitosPorNumeroRemito = function() {
	var data = { numeroRemito : modificacionIngresoDeMaterialesEvent.getInputSituarEn().val(),
	             senial: $("#senialDefaultUsuario").val() };
	modificacionIngresoDeMaterialesEvent.service.buscarRemitosPorNumeroRemito(data);
};

/**
 * Búsqueda de remitos por número de guía
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.buscarRemitosPorNumeroGuia = function() {
	var data = { numeroGuia : modificacionIngresoDeMaterialesEvent.getInputSituarEn().val(),
	             senial: $("#senialDefaultUsuario").val() };
	modificacionIngresoDeMaterialesEvent.service.buscarRemitosPorNumeroGuia(data);
};

/**
 * Búsqueda de remitos por razón social del proveedor
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.buscarRemitosPorRazonSocial = function() {
	var self = modificacionIngresoDeMaterialesEvent;
	var data = { razonSocialDistribuidor : modificacionIngresoDeMaterialesEvent.getInputSituarEn().val(), 
	             senial: $("#senialDefaultUsuario").val() };
	if (self.getSelector("claveTitulo").html().trim() != "") {
		data.clave = self.getSelector("claveTitulo").html();
	}
	if (self.getSelector("capituloTitulo").html().trim() != "") {
		data.capitulo = self.getSelector("capituloTitulo").html();
	}
	if (self.getSelector("parteTitulo").html().trim() != "") {
		data.parte = self.getSelector("parteTitulo").html();
	}
	modificacionIngresoDeMaterialesEvent.service.buscarRemitosPorRazonSocial(data);
};

/**
 * Búsqueda de remitos por fecha
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.buscarRemitosPorFecha = function() {
	var data = { fecha : modificacionIngresoDeMaterialesEvent.getInputSituarEn().val(), 
	             senial: $("#senialDefaultUsuario").val() };
	modificacionIngresoDeMaterialesEvent.service.buscarRemitosPorFecha(data);
};

/**
 * Getters
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.getAccordionOrden = function() {
	return $("#" + this.div.id + "_accordionOrden");
};
ModificacionIngresoDeMaterialesEvent.prototype.getDivListadoOrden = function() {
	return $("#" + this.div.id + "_listadoOrden");
};
ModificacionIngresoDeMaterialesEvent.prototype.getLabelSituarEn = function() {
	return $("#" + this.div.id + "_labelSituar");
};
ModificacionIngresoDeMaterialesEvent.prototype.getInputSituarEn = function() {
	return $("#" + this.div.id + "_situarEn");
};
ModificacionIngresoDeMaterialesEvent.prototype.getSelectTipoBusqueda = function() {
	return $("#" + this.div.id + "_tipoBusqueda");
};
ModificacionIngresoDeMaterialesEvent.prototype.getAceptarBusquedaRemito = function() {
	return $("#" + this.div.id + "_aceptarBusquedaRemito");
};
ModificacionIngresoDeMaterialesEvent.prototype.getGridOrden = function() {
	return $("#" + this.div.id + "_gridOrden");
};

/***********************************************************************************		
 ********************			Visualizacion cabecera			********************
 ***********************************************************************************/
/**
 * Visualización de la cabecera del remito
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.getCompRemitoSeleccionado = function(idRow) {
	var row = modificacionIngresoDeMaterialesEvent.getGridOrden().jqGrid('getRowData', idRow);
	var self = modificacionIngresoDeMaterialesEvent;
	self.remito.cabecera.codigoDistribuidor = row.codigoDistribuidor;
	self.remito.cabecera.razonSocialDistribuidor = row.razonSocialDistribuidor;
	self.remito.cabecera.fechaRemito = row.fechaRemitoGuia;
	self.remito.cabecera.numeroRemito = row.numeroRemito ? row.numeroRemito : null;
	self.remito.cabecera.numeroGuia = row.numeroGuia ? row.numeroGuia : null;
	self.remito.cabecera.fechaIngreso = row.fechaMovimiento;
	Component.get("html/modificacionIngresoDeMateriales/cabeceraRemitoSeleccionado.html", modificacionIngresoDeMaterialesEvent.drawDetalleRemitoSeleccionado);
};

ModificacionIngresoDeMaterialesEvent.prototype.drawDetalleRemitoSeleccionado = function(comp) {
	var self = modificacionIngresoDeMaterialesEvent;
	self.getDivListadoOrden().hide();
	comp = comp.replace(/{{id}}/g, self.div.id)
			   .replace(/{{razonSocial}}/g, self.remito.cabecera.razonSocialDistribuidor)
			   .replace(/{{remito}}/g, self.remito.cabecera.numeroRemito)
			   .replace(/{{guia}}/g, self.remito.cabecera.numeroGuia)
			   .replace(/{{fechaRemitoGuia}}/g, self.remito.cabecera.fechaRemito)
			   .replace(/{{fechaIngreso}}/g, self.remito.cabecera.fechaIngreso);
	
	if (self.getDivCabeceraRemitoSeleccionado().length) {
		self.getDivCabeceraRemitoSeleccionado().replaceWith(comp);
	} else {
		self.getDiv().append(comp);
	}
	
	Accordion.getInstance(self.getAccordionCabeceraRemitoSeleccionado());
	self.getAccordionModificacionCabeceraRemitoSeleccionado().hide();
	self.getAccordionCabeceraRemitoSeleccionado().show();
	
	self.getButtonVolverBusquedaRemito().button().click(self.volverBusquedaRemito);
	
};

/***********************************************************************************		
 ********************			Modificación cabecera			********************
 ***********************************************************************************/
/**
 * Redirección a detalle de la cabecera del remito
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.getCompModificarRemitoSeleccionado = function(idRow) {
	var row = modificacionIngresoDeMaterialesEvent.getGridOrden().jqGrid('getRowData', idRow);
	var self = modificacionIngresoDeMaterialesEvent;
	self.remito.cabecera.idRemito = row.idRemito;
	self.remito.cabecera.codigoDistribuidor = row.codigoDistribuidor;
	self.remito.cabecera.razonSocialDistribuidor = row.razonSocialDistribuidor;
	self.remito.cabecera.fechaRemito = row.fechaRemitoGuia;
	self.remito.cabecera.numeroRemito = row.numeroRemito ? row.numeroRemito : null;
	self.remito.cabecera.numeroGuia = row.numeroGuia ? row.numeroGuia : null;
	self.remito.cabecera.fechaIngreso = row.fechaMovimiento;
	Component.get("html/modificacionIngresoDeMateriales/cabeceraRemitoSeleccionado.html", modificacionIngresoDeMaterialesEvent.drawDetalleModificacionRemitoSeleccionado);
};

ModificacionIngresoDeMaterialesEvent.prototype.drawDetalleModificacionRemitoSeleccionado = function(comp) {
	var self = modificacionIngresoDeMaterialesEvent;
	self.getDivListadoOrden().hide();
	comp = comp.replace(/{{id}}/g, self.div.id)
			   .replace(/{{razonSocial}}/g, self.remito.cabecera.razonSocialDistribuidor);
	
	
	if (self.getDivCabeceraRemitoSeleccionado().length) {
		self.getDivCabeceraRemitoSeleccionado().replaceWith(comp);
	} else {
		self.getDiv().append(comp);
	}
	
	Accordion.getInstance(self.getAccordionModificacionCabeceraRemitoSeleccionado());
	
	self.getInputNumeroRemito().val(self.remito.cabecera.numeroRemito.trim() == "0" ? "" : self.remito.cabecera.numeroRemito);
	self.getInputNumeroGuia().val(self.remito.cabecera.numeroGuia.trim() == "0" ? "" : self.remito.cabecera.numeroGuia);
	
	Datepicker.picker(self.getInputFechaIngreso());
	Datepicker.fullYearDatepicker(self.getInputFechaIngreso());
	self.getInputFechaIngreso().datepicker('setDate', self.remito.cabecera.fechaIngreso);
	
	Datepicker.picker(self.getInputFechaRemitoGuia());
	Datepicker.fullYearDatepicker(self.getInputFechaRemitoGuia());
	self.getInputFechaRemitoGuia().datepicker('setDate', self.remito.cabecera.fechaRemito);
	
	self.getButtonVolverModificacionBusquedaRemito().button().click(self.volverBusquedaRemito);
	self.getButtonAceptarModificacionCabecera().button().click(self.validarModificarCabeceraRemito);
	self.getAccordionCabeceraRemitoSeleccionado().hide();
	self.getAccordionModificacionCabeceraRemitoSeleccionado().show();
};

/**
 * Validacion de los datos de modificación de remito
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.validarModificarCabeceraRemito = function() {
	var self = modificacionIngresoDeMaterialesEvent;
	
	if (self.validarNroRemitoGuia()) {
		MESSAGE.alert("Debe ingresar sólo el Nº de remito o el Nº de guía", 3000);
		self.getInputNumeroRemito().effect("highlight", {color:'yellow'}, 3000);
		self.getInputNumeroGuia().effect("highlight", {color:'yellow'}, 3000);
		return false;
	} else if (Validator.isEmpty(self.getInputFechaIngreso(), false)) {
		Validator.focus(self.getInputFechaIngreso(), "Debe ingresar una fecha");
		return false;
	} else if (Validator.isEmpty(self.getInputFechaRemitoGuia(), false)) {
		Validator.focus(self.getInputFechaRemitoGuia(), "Debe ingresar una fecha");
		return false;
	} 
	
	self.validarExhibiciones();
};

ModificacionIngresoDeMaterialesEvent.prototype.validarNroRemitoGuia = function() {
	var self = modificacionIngresoDeMaterialesEvent;

	var nroRemito = self.getInputNumeroRemito().val().trim();
	var nroGuia = self.getInputNumeroGuia().val().trim();

	if ((nroRemito == "" && nroGuia == "") || (nroRemito != "" && nroGuia != "")) {
		return true;
	} else {
		nroRemito == "" ? self.getInputNumeroRemito().val(" ") : self.getInputNumeroGuia().val(" ");
		return false;
	}
};

ModificacionIngresoDeMaterialesEvent.prototype.validarExhibiciones = function() {
	var self = modificacionIngresoDeMaterialesEvent;
	
	var data = {
		codigoDistribuidor: self.remito.cabecera.codigoDistribuidor, 
		idRemito: self.remito.cabecera.idRemito,
		senial: $("#senialDefaultUsuario").val()
	};
	
	if (self.getInputNumeroRemito().val().trim() !== "") {
		data.numeroRemito = self.getInputNumeroRemito().val(); 
	} else {
		data.numeroGuia = self.getInputNumeroGuia().val();
	}
		
	self.service.validarExhibiciones(data);
};

ModificacionIngresoDeMaterialesEvent.prototype.abrirPopupWarningCabecera = function(titulos) {
	var self = modificacionIngresoDeMaterialesEvent;
	Component.get("html/modificacionIngresoDeMateriales/popupWarningCabecera.html", function(comp) {
		self.drawPopupWarningCabecera(comp, titulos);
	});
};

ModificacionIngresoDeMaterialesEvent.prototype.drawPopupWarningCabecera = function(comp, titulos) {
	var self = modificacionIngresoDeMaterialesEvent;
	
	$("#popupWarningCabecera").dialog({
		width: 800, show: 'blind', hide: 'blind', modal: true, autoOpen: false,
		buttons: {
			"Salir": function() {
				$(this).dialog("close");
			}
		}
	});
	$("#popupWarningCabecera").empty().append(comp.replace(/{{id}}/g, self.div.id));
	$("#popupWarningCabecera").dialog("open");
	
	self.drawGridPopupWarningCabecera(titulos);
};

ModificacionIngresoDeMaterialesEvent.prototype.drawGridPopupWarningCabecera = function(titulos) {
	var self = modificacionIngresoDeMaterialesEvent;
	
	self.getSelector("gridPopupWarningCabecera").jqGrid({
		height: 'auto',
		autowidth: true,
		shrinkToFit: true,
		datatype: 'local',
		colNames:['Clave', 'Título', 'Capitulo' , 'Parte'],
		colModel:[ 
          	{name:'clave', 				 index:'clave', 			align:'center'},
          	{name:'titulo', 	 		 index:'titulo', 			align:'center'},
          	{name:'numeroCapitulo', 	 index:'numeroCapitulo',	align:'center'},
          	{name:'numeroParte', 	 	 index:'numeroParte', 		align:'center'} 
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + self.div.id + '_pagerPopupWarningCabecera',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray'
	});
	
	self.getSelector("gridPopupWarningCabecera").clearGridData().setGridParam({data: titulos}).trigger('reloadGrid');
	
	/* Grilla con tamaño automático */
	//Component.resizableGrid(self.getSelector("gridPopupWarningCabecera"));
};

ModificacionIngresoDeMaterialesEvent.prototype.cierrePopupDatosTitulosSH = function() {
	$("#popupWarningCabecera").dialog("close");
	$("#popupWarningCabecera").dialog("remove");
};

/**
 * Apertura del popup de modificación de remito
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.abrirPopUpModificarCabeceraRemito = function() {
	var popup = popupConfirmacionEvent;
	var mensajeConfirmacion = "¿Desea modificar los títulos del remito?";    
	
	var self = modificacionIngresoDeMaterialesEvent;
	popup.create(self.div.id, mensajeConfirmacion);
	popup.confirmar = function() {
		self.modificarCabeceraRemito();
		popup.close();
		popup.remove();
	};
	popup.cancelar = function() {
		popup.close();
		popup.remove();
	};
};

/**
 * Modificación de remito
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.modificarCabeceraRemito = function() {
	var self = modificacionIngresoDeMaterialesEvent;
	
	var data = {
		numeroRemito : self.getInputNumeroRemito().val().trim() == "" ? 0 : self.getInputNumeroRemito().val(),
		numeroGuia : self.getInputNumeroGuia().val().trim() == "" ? 0 : self.getInputNumeroGuia().val(),
		idRemito: self.remito.cabecera.idRemito,
		fechaIngreso : self.getInputFechaIngreso().val(), 
		fechaRemito: self.getInputFechaRemitoGuia().val()
	};
	
	self.service.modificarCabeceraRemito(data);
};

ModificacionIngresoDeMaterialesEvent.prototype.volverBusquedaRemito = function() {
	var self = modificacionIngresoDeMaterialesEvent;
	self.getDivCabeceraRemitoSeleccionado().hide();
	self.getDivListadoOrden().show();
};

/***********************************************************************************		
 ********************			Trabajar con títulos 			********************
 ***********************************************************************************/
/**
 * Trabajar con títulos
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.getCompTrabajarConTitulos = function(idRow) {
	var row = modificacionIngresoDeMaterialesEvent.getGridOrden().jqGrid('getRowData', idRow);
	var self = modificacionIngresoDeMaterialesEvent;
	self.remito.cabecera.idRemito = row.idRemito;
	self.remito.cabecera.codigoDistribuidor = row.codigoDistribuidor;
	self.remito.cabecera.razonSocialDistribuidor = row.razonSocialDistribuidor;
	self.remito.cabecera.fechaRemito = row.fechaRemitoGuia;
	self.remito.cabecera.numeroRemito = row.numeroRemito ? row.numeroRemito : null;
	self.remito.cabecera.numeroGuia = row.numeroGuia ? row.numeroGuia : null;
	self.remito.cabecera.fechaIngreso = row.fechaMovimiento;
	Component.get("html/modificacionIngresoDeMateriales/listadoTitulos.html", modificacionIngresoDeMaterialesEvent.drawTrabajarConTitulos);
};

ModificacionIngresoDeMaterialesEvent.prototype.drawTrabajarConTitulos = function(comp) {
	var self = modificacionIngresoDeMaterialesEvent;
	self.getDivListadoOrden().hide();
	comp = comp.replace(/{{id}}/g, self.div.id)
			   .replace(/{{razonSocial}}/g, self.remito.cabecera.razonSocialDistribuidor)
			   .replace(/{{remito-guia}}/g, self.remito.cabecera.numeroRemito ? self.remito.cabecera.numeroRemito : self.remito.cabecera.numeroGuia)
			   .replace(/{{fechaRemitoGuia}}/g, self.remito.cabecera.fechaRemito)
			   .replace(/{{fechaIngreso}}/g, self.remito.cabecera.fechaIngreso)
			   .replace(/{{tipoBusquedaTitulo}}/g, self.tipoBusquedaTitulos);
	if (self.getDivTitulos().length) {
		self.getDivTitulos().replaceWith(comp);
	} else {
		self.getDiv().append(comp);
	}
	
	Accordion.getInstance(self.getAccordionFormTitulos());
	
	Component.populateSelect(self.getSelector("senial"), seniales, "codigo", "descripcion", false);
	self.getSelector("senial").val(variablesSesionUsuarioEvent.senialDefaultUsuario);
	
	/* Botonera */
	self.getCambiarTipoBusquedaTitulo().click(self.cambiarTipoBusquedaTitulos);
	self.getButtonAceptarBusquedaTitulos().button().click(self.buscarTitulosRemito);
	self.getButtonVolverBusquedaTitulos().button().click(self.volverBusquedaTitulos);
	self.getSelector("agregarTitulos").button().click(self.agregarTituloRemito);
	
	self.drawGridTitulos();
	self.buscarTitulosRemito();
	self.cambiarLabelGridTitulos();
};

/**
 * Vuelta al ingreso de la cabecera del remito
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.volverBusquedaTitulos = function() {
	var self = modificacionIngresoDeMaterialesEvent;
	self.getDivTitulos().hide();
	self.getDivListadoOrden().show();
};

/**
 * Cambio del criterio de búsqueda de títulos
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.cambiarLabelGridTitulos = function() {
	var self = modificacionIngresoDeMaterialesEvent;
	self.getGridTitulos().setLabel('titulo', 'Título ' + modificacionIngresoDeMaterialesEvent.tipoBusquedaTitulos, '', '');
};

/**
 * Cambio del criterio de búsqueda de títulos
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.cambiarTipoBusquedaTitulos = function() {
	var self = modificacionIngresoDeMaterialesEvent;
	if (self.tipoBusquedaTitulos === ModificacionIngresoDeMaterialesEvent.CASTELLANO) {
		self.tipoBusquedaTitulos = ModificacionIngresoDeMaterialesEvent.ORIGINAL;
	} else {
		self.tipoBusquedaTitulos = ModificacionIngresoDeMaterialesEvent.CASTELLANO;
	}
	self.getLabelTipoBusquedaTitulo().text(self.tipoBusquedaTitulos);
	self.cambiarLabelGridTitulos();
	self.getInputTituloBusqueda().val("");
	self.getGridTitulos().clearGridData();
};

/**
 * Búsqueda de titulos por descripción
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.buscarTitulosRemito = function() {
	var self = modificacionIngresoDeMaterialesEvent;
	
	
		var data = {
			idRemito: self.remito.cabecera.idRemito,
			descripcionTituloCast: self.tipoBusquedaTitulos === ModificacionIngresoDeMaterialesEvent.CASTELLANO && self.getInputTituloBusqueda().val() !=null ? self.getInputTituloBusqueda().val() : null,
			descripcionTituloOrig: self.tipoBusquedaTitulos === ModificacionIngresoDeMaterialesEvent.ORIGINAL && self.getInputTituloBusqueda().val() !=null ? self.getInputTituloBusqueda().val() : null,
			senial : $("#senialDefaultUsuario").val()
		};
		self.remito.detalle.senial = $("#senialDefaultUsuario").val();
		self.service.buscarTitulosRemito(data);
	
};

/**
 * jqGrid de títulos
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.drawGridTitulos = function() {
	var self = modificacionIngresoDeMaterialesEvent;
	self.getGridTitulos().jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Titulo', 'Clave', 'Capítulo', 'Parte', '', 'Soporte', 'Rollos', '', '', '', '', '', '', '', '', '', ''],
		colModel:[ 
			{name:'titulo', 			index:'titulo', 			width:300, 	sortable:false,	align:'left'}, 
			{name:'clave', 				index:'clave', 				width:60, 	sortable:false, align:'center'}, 
			{name:'capitulo', 			index:'capitulo',			width:70, 	sortable:false, align:'center'},  
			{name:'parte', 				index:'parte', 				width:30, 	sortable:false, align:'center'}, 
			{name:'descSoporte', 		index:'descSoporte', 		hidden: true},
			{name:'codSoporte', 		index:'codSoporte', 		width:40, 	sortable:false, align:'center'},
			{name:'rollos', 			index:'rollos', 			width:150, 	sortable:false, align:'center'},
			{name:'descSoporte', 		index:'descSoporte', 		hidden: true},
			{name:'codMotivoIngreso', 	index:'codMotivoIngreso', 	hidden: true},
			{name:'descMotivoIngreso', 	index:'descMotivoIngreso', 	hidden: true},
			{name:'carrete', 			index:'carrete', 			hidden: true},
			{name:'lata',	 			index:'lata', 				hidden: true},
			{name:'torta',	 			index:'torta', 				hidden: true},
			{name:'estrenoRepeticion',	index:'estrenoRepeticion', 	hidden: true},
			{name:'verTitulo', 			index:'verTitulo', 			width:30, 	sortable:false, align:'center'},
			{name:'modificarTitulo', 	index:'modificarTitulo', 	width:30, 	sortable:false, align:'center'},
			{name:'eliminarTitulo', 	index:'eliminarTitulo', 	width:30, 	sortable:false, align:'center'}
		],
		rowNum: 20,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#ModificacionIngresoDeMaterialesEventId_pagerGridTitulos',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Títulos',
		gridComplete: function() { 							
			var ids = $(this).jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var divVerTitulo = "<div onclick='modificacionIngresoDeMaterialesEvent.getCompTituloSeleccionado(" + ids[i] + ", \"V\")' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-pencil conTooltip' title='Ver título'></span></div>";
				var divModificarTitulo = "<div onclick='modificacionIngresoDeMaterialesEvent.getCompTituloSeleccionado(" + ids[i] + ", \"M\")' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-pencil conTooltip' title='Modificar título'></span></div>";
				var divEliminarTitulo = "<div onclick='modificacionIngresoDeMaterialesEvent.getCompTituloSeleccionado(" + ids[i] + ", \"B\")' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-trash conTooltip' title='Eliminar título'></span></div>";
				$(this).jqGrid('setRowData', ids[i], { verTitulo: divVerTitulo, modificarTitulo: divModificarTitulo, eliminarTitulo: divEliminarTitulo });
			}
			self.getGridTitulos().trigger("reloadGrid");
		}
	});
	
	/* Grilla con tamaño automático */
	Component.resizableGrid(self.getGridTitulos());
};

/**
 * Llenado de jqGrid de titulos
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.fillGridTitulos = function(data) {
	var self = modificacionIngresoDeMaterialesEvent;
	self.getGridTitulos().clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};

/**/
/***********************************************************************************		
 ********************	Trabajar con títulos - Detalle remito	********************
 ***********************************************************************************/
ModificacionIngresoDeMaterialesEvent.prototype.agregarTituloRemito = function() {
	ingresoDeMaterialesEvent.service = new IngresoDeMaterialesService();
	ingresoDeMaterialesEvent.material.cabecera.idRemito = modificacionIngresoDeMaterialesEvent.remito.cabecera.idRemito;
	ingresoDeMaterialesEvent.material.cabecera.codigoDistribuidor = modificacionIngresoDeMaterialesEvent.remito.cabecera.codigoDistribuidor;
	ingresoDeMaterialesEvent.material.cabecera.razonSocialDistribuidor = modificacionIngresoDeMaterialesEvent.remito.cabecera.razonSocialDistribuidor;
	ingresoDeMaterialesEvent.material.cabecera.fechaRemito = modificacionIngresoDeMaterialesEvent.remito.cabecera.fechaRemito;
	if (modificacionIngresoDeMaterialesEvent.remito.cabecera.numeroRemito.trim() != "" && modificacionIngresoDeMaterialesEvent.remito.cabecera.numeroRemito.trim() != "0") {
		ingresoDeMaterialesEvent.material.cabecera.numeroRemito = modificacionIngresoDeMaterialesEvent.remito.cabecera.numeroRemito;
	}
	if (modificacionIngresoDeMaterialesEvent.remito.cabecera.numeroGuia.trim() != "" && modificacionIngresoDeMaterialesEvent.remito.cabecera.numeroGuia.trim() != "0") {
		ingresoDeMaterialesEvent.material.cabecera.numeroGuia = modificacionIngresoDeMaterialesEvent.remito.cabecera.numeroGuia;
	}
	ingresoDeMaterialesEvent.material.cabecera.fechaIngreso = modificacionIngresoDeMaterialesEvent.remito.cabecera.fechaIngreso;
	ingresoDeMaterialesEvent.getCompListadoTitulosDesdeModificacion();
};

/***********************************************************************************		
 ********************	Trabajar con títulos - Detalle remito	********************
 ***********************************************************************************/
/**
 * Redirección a detalle del remito
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.getCompTituloSeleccionado = function(idTitulo, edicion) {
	var self = modificacionIngresoDeMaterialesEvent;
	var row = self.getGridTitulos().jqGrid('getRowData', idTitulo);
	self.remito.detalle.titulo = row.titulo; 
	self.remito.detalle.clave = row.clave;
	self.remito.detalle.capitulo = row.capitulo;
	self.remito.detalle.nroParte = row.parte;
	self.remito.detalle.codSoporte = row.codSoporte;
	self.remito.detalle.descSoporte = row.descSoporte;
	self.remito.detalle.cantidadRollos = row.rollos;
	self.remito.detalle.codMotivoIngreso = row.codMotivoIngreso;
	self.remito.detalle.descMotivoIngreso = row.descMotivoIngreso;
	self.remito.detalle.er = row.estrenoRepeticion;
	self.remito.detalle.carrete = row.carrete;
	self.remito.detalle.lata = row.lata;
	self.remito.detalle.torta = row.torta;
	self.modoEdicion = edicion;
	
	if (edicion == 'M') {
		var data = {
			validarModificarEliminarDetalleRequest : {
				idRemito : self.remito.cabecera.idRemito,
				tipoTitulo : self.remito.detalle.clave.substring(0,2),
				numeroTitulo : self.remito.detalle.clave.substring(2,8), 
				capitulo : self.remito.detalle.capitulo,
				parte : self.remito.detalle.nroParte
			} 
		};
		var isValid = self.service.validarModificarDetalleRemito(data);
		
		if (!isValid) {
			return;
		}
		Component.get("html/modificacionIngresoDeMateriales/detalleRemitoSeleccionado.html", modificacionIngresoDeMaterialesEvent.drawTituloSeleccionado);
	} else if (edicion == 'B') {
		var data = {
			validarModificarEliminarDetalleRequest : {
				idRemito : self.remito.cabecera.idRemito,
				tipoTitulo : self.remito.detalle.clave.substring(0,2),
				numeroTitulo : self.remito.detalle.clave.substring(2,8), 
				capitulo : self.remito.detalle.capitulo,
				parte : self.remito.detalle.nroParte
			} 
		};
		var isValid = self.service.validarEliminarDetalleRemito(data);
		
		if (!isValid) {
			return;
		}
	} else {
		Component.get("html/modificacionIngresoDeMateriales/detalleRemitoSeleccionado.html", modificacionIngresoDeMaterialesEvent.drawTituloSeleccionado);
	}
	
};


ModificacionIngresoDeMaterialesEvent.prototype.drawTituloSeleccionado = function(comp) {
	var self = modificacionIngresoDeMaterialesEvent;
	self.getDivTitulos().hide();
	comp = comp.replace(/{{id}}/g, self.div.id)
			   .replace(/{{clave}}/g, self.remito.detalle.clave)
			   .replace(/{{titulo}}/g, self.remito.detalle.titulo)
			   .replace(/{{nroCapitulo}}/g, self.remito.detalle.capitulo == 0 ? "" : self.remito.detalle.capitulo)
			   .replace(/{{nroParte}}/g, self.remito.detalle.nroParte == 0 ? "" : self.remito.detalle.nroParte)
			   .replace(/{{razonSocial}}/g, self.remito.cabecera.razonSocialDistribuidor)
			   .replace(/{{er}}/g, self.remito.detalle.er == 0 ? "" : self.remito.detalle.er);
	
	if (self.modoEdicion != 'M') {
		comp = comp.replace(/{{soporte}}/g, self.remito.detalle.codSoporte + " - " + self.remito.detalle.descSoporte)
				   .replace(/{{cantRollos}}/g, self.remito.detalle.cantidadRollos)
				   .replace(/{{motivoIngreso}}/g, self.remito.detalle.codMotivoIngreso + " - " + self.remito.detalle.descMotivoIngreso)
				   .replace(/{{carrete}}/g, self.remito.detalle.carrete == 0 ? "" : self.remito.detalle.carrete)
				   .replace(/{{lata}}/g, self.remito.detalle.lata == 0 ? "" : self.remito.detalle.lata)
				   .replace(/{{torta}}/g, self.remito.detalle.torta == 0 ? "" : self.remito.detalle.torta);
	}
	
	if (self.getDivDetalleRemitoSeleccionado().length) {
		self.getDivDetalleRemitoSeleccionado().replaceWith(comp);
	} else {
		self.getDiv().append(comp);
	}
	
	if (self.modoEdicion == 'M') {
		Accordion.getInstance(self.getAccordionFormDetalleRemitoSeleccionadoEdicion());
		self.getSelector("modificarDetalleRemito").button().click(self.validarModificarDetalleRemito);
		self.getSelector("codSoporte").val(self.remito.detalle.codSoporte.trim()); 
		self.getSelector("descSoporte").val(self.remito.detalle.descSoporte);
		self.getSelector("rollos").numeric();
		self.getSelector("rollos").val(self.remito.detalle.cantidadRollos);
		self.getSelector("carrete").numeric();
		self.getSelector("carrete").val(self.remito.detalle.carrete);
		self.getSelector("lata").numeric();
		self.getSelector("lata").val(self.remito.detalle.lata);
		self.getSelector("torta").numeric();
		self.getSelector("torta").val(self.remito.detalle.torta);
		self.getSelector("popupSoportes").click(self.drawPopupSituarSoporte);
		self.getSelector("volverFormTitulosEdicion").button().click(self.volverSoporteTitulo);
		self.getSelector("modificarDetalleRemito").button().click(self.validarModificarDetalleTitulo);
		self.getSelector("accordionFormDetalleRemitoSeleccionadoReadOnly").hide();
		self.getSelector("accordionFormDetalleRemitoSeleccionadoEdicion").show();
	} else {
		Accordion.getInstance(self.getAccordionFormDetalleRemitoSeleccionadoReadOnly());
		self.getSelector("volverFormTitulosReadOnly").button().click(self.volverSoporteTitulo);
		self.getSelector("accordionFormDetalleRemitoSeleccionadoEdicion").hide();
		self.getSelector("accordionFormDetalleRemitoSeleccionadoReadOnly").show();
	}
	
};

ModificacionIngresoDeMaterialesEvent.prototype.volverSoporteTitulo = function() {
	var self = modificacionIngresoDeMaterialesEvent;
	self.getDivDetalleRemitoSeleccionado().hide();
	self.getDivTitulos().show();
};

/***********************************************************************************		
 ********************		   Modificar detalle remito			********************
 ***********************************************************************************/
/**
 * Modificacion del detalle de un título del remito
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.validarModificarDetalleRemito = function() {
	var self = modificacionIngresoDeMaterialesEvent;
	var data = {
		codigoSoporte: self.getSelector("codSoporte").val().toUpperCase()
	};
	
	if (Validator.isEmpty(self.getSelector("codSoporte"))) {
		Validator.focus(self.getSelector("codSoporte"), "Debe seleccionar un soporte");
		return;
	} else if (self.service.validarSoporte(data)) {
		Validator.focus(self.getSelector("codSoporte"), "Debe ingresar un soporte válido");
		return;
	}
	
	self.abrirPopupModificarDetalleRemito();
};

ModificacionIngresoDeMaterialesEvent.prototype.abrirPopupModificarDetalleRemito = function() {
	var popup = popupConfirmacionEvent;
	var mensajeConfirmacion = "¿Confirma la modificación?";    
	
	var self = modificacionIngresoDeMaterialesEvent;
	popup.create(self.div.id, mensajeConfirmacion);
	popup.confirmar = function() {
		self.modificarDetalleRemito();
		popup.close();
		popup.remove();
	};
	popup.cancelar = function() {
		popup.close();
		popup.remove();
	};
};

/**
 * Modificacion del detalle de un título del remito
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.modificarDetalleRemito = function() {
	var self = modificacionIngresoDeMaterialesEvent;
	
	var data = {
		detalleRemitoABM : {
			idRemito : self.remito.cabecera.idRemito,
			tipoTitulo : self.remito.detalle.clave.substring(0,2),
			numeroTitulo : self.remito.detalle.clave.substring(2,8), 
			capitulo : self.remito.detalle.capitulo,
			parte : self.remito.detalle.nroParte,
			codSoporte : self.getSelector("codSoporte").val().toUpperCase(),
		    carrete : self.getSelector("carrete").val(),
		    lata : self.getSelector("lata").val(),
		    torta : self.getSelector("torta").val(),
		    cantRollos : self.getSelector("rollos").val()
		} 
	};
	
	self.service.modificarDetalleRemito(data);
};

/**
 * Situar distribuidores por codigo de soporte
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.drawPopupSituarSoporte = function() {
	var data = {codigoSoporte : ""};
	situarPopupEvent.reset();
	situarPopupEvent.nombreSituar = "Soportes";
	situarPopupEvent.create("buscarSoportesParaRemitos.action", modificacionIngresoDeMaterialesEvent.soporteSelected, data, "soportes");
};

/**
 * Selección situar soportes
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.soporteSelected = function(soporte) {
	var self = modificacionIngresoDeMaterialesEvent;
	self.getSelector("codSoporte").val(soporte.codigo);
	self.getSelector("descSoporte").val(soporte.descripcion);
};

/**
 * Getters
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.getDivTitulos = function() {
	return $("#" + this.div.id + "_divTitulos");
};
ModificacionIngresoDeMaterialesEvent.prototype.getFormCabecera = function() {
	return $("#" + this.div.id + "_formCabecera");
};
ModificacionIngresoDeMaterialesEvent.prototype.getDivDetalleRemitoSeleccionado = function() {
	return $("#" + this.div.id + "_divDetalleRemitoSeleccionado");
};
ModificacionIngresoDeMaterialesEvent.prototype.getAccordionFormDetalleRemitoSeleccionadoReadOnly = function() {
	return $("#" + this.div.id + "_accordionFormDetalleRemitoSeleccionadoReadOnly");
};
ModificacionIngresoDeMaterialesEvent.prototype.getAccordionFormDetalleRemitoSeleccionadoEdicion = function() {
	return $("#" + this.div.id + "_accordionFormDetalleRemitoSeleccionadoEdicion");
};
ModificacionIngresoDeMaterialesEvent.prototype.getAccordionFormTitulos = function() {
	return $("#" + this.div.id + "_accordionFormTitulos");
};
ModificacionIngresoDeMaterialesEvent.prototype.getLabelTipoBusquedaTitulo = function() {
	return $("#" + this.div.id + "_tipoBusquedaTitulo");
};
ModificacionIngresoDeMaterialesEvent.prototype.getInputTituloBusqueda = function() {
	return $("#" + this.div.id + "_tituloBusqueda");
};
ModificacionIngresoDeMaterialesEvent.prototype.getButtonFormCabecera = function() {
	return $("#" + this.div.id + "_aceptarFormCabecera");
};
ModificacionIngresoDeMaterialesEvent.prototype.getCambiarTipoBusquedaTitulo = function() {
	return $("#" + this.div.id + "_cambiarTipoBusquedaTitulo");
};
ModificacionIngresoDeMaterialesEvent.prototype.getButtonAceptarBusquedaTitulos = function() {
	return $("#" + this.div.id + "_aceptarBusquedaTitulos");
};
ModificacionIngresoDeMaterialesEvent.prototype.getButtonVolverBusquedaTitulos = function() {
	return $("#" + this.div.id + "_volverBusquedaTitulos");
};
ModificacionIngresoDeMaterialesEvent.prototype.getGridTitulos = function() {
	return $("#" + this.div.id + "_gridTitulos");
};

/***********************************************************************************		
 ********************			Eliminar detalle remito			********************
 ***********************************************************************************/
ModificacionIngresoDeMaterialesEvent.prototype.abrirPopUpConfirmacionContabiliza = function(mensaje) {
	var popup = popupConfirmacionEvent;
	var mensajeConfirmacion = mensaje;    
	
	var self = modificacionIngresoDeMaterialesEvent;
	popup.create(self.div.id, mensajeConfirmacion);
	popup.confirmar = function() {
		self.chequearDetalleRemitoDesdePopup();
		popup.close();
		popup.remove();
	};
	popup.cancelar = function() {
		popup.close();
		popup.remove();
	};
};

ModificacionIngresoDeMaterialesEvent.prototype.chequearDetalleRemitoDesdePopup = function() {
	var self = modificacionIngresoDeMaterialesEvent;

	var data = {
		validarModificarEliminarDetalleRequest : {
			idRemito : self.remito.cabecera.idRemito,
			tipoTitulo : self.remito.detalle.clave.substring(0,2),
			numeroTitulo : self.remito.detalle.clave.substring(2,8) 
		} 
	};
	
	self.service.chequeoEliminarRemitoDesde(data);
};

/**
 * Apertura del popup de para confirmar la baja
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.abrirPopUpChequeoDesenlace = function(mensaje) {
	var popup = popupConfirmacionEvent;
	
	var self = modificacionIngresoDeMaterialesEvent;
	popup.create(self.div.id, mensaje);
	popup.confirmar = function() {
		Component.get("html/modificacionIngresoDeMateriales/detalleRemitoSeleccionado.html", modificacionIngresoDeMaterialesEvent.drawTituloSeleccionado);
		popup.close();
		popup.remove();
		self.abrirPopUpEliminarDetalleRemito(self.eliminarDetalleRemito);
	};
	popup.cancelar = function() {
		popup.close();
		popup.remove();
	};
};

ModificacionIngresoDeMaterialesEvent.prototype.abrirPopUpEliminarDetalleRemito = function(operacion) {
	var popup = popupConfirmacionEvent;
	var mensajeConfirmacion = "¿Confirma la baja?";    
	
	var self = modificacionIngresoDeMaterialesEvent;
	popup.create(self.div.id, mensajeConfirmacion);
	popup.confirmar = function() {
		operacion();
		popup.close();
		popup.remove();
	};
	popup.cancelar = function() {
		popup.close();
		popup.remove();
	};
};

ModificacionIngresoDeMaterialesEvent.prototype.eliminarDetalleRemito = function() {
	var self = modificacionIngresoDeMaterialesEvent;
	
	var data = {
		validarModificarEliminarDetalleRequest : {
			idRemito : self.remito.cabecera.idRemito,
			tipoTitulo : self.remito.detalle.clave.substring(0,2),
			numeroTitulo : self.remito.detalle.clave.substring(2,8),
			capitulo : self.remito.detalle.capitulo,
			parte : self.remito.detalle.nroParte,
			contabiliza : "S",
			borroCh : self.remito.detalle.borroCh
		} 
	};
	
	self.service.eliminarDetalleRemito(data);
};

ModificacionIngresoDeMaterialesEvent.prototype.eliminarDetalleRemitoDesde = function() {
	var self = modificacionIngresoDeMaterialesEvent;
	
	var data = {
		validarModificarEliminarDetalleRequest : {
			idRemito : self.remito.cabecera.idRemito,
			tipoTitulo : self.remito.detalle.clave.substring(0,2),
			numeroTitulo : self.remito.detalle.clave.substring(2,8),
			capitulo : self.remito.detalle.capitulo,
			parte : self.remito.detalle.nroParte}
	};
	
	self.service.eliminarDetalleRemitoDesde(data);
};

/***********************************************************************************		
 ********************				Eliminar remito				********************
 ***********************************************************************************/
/**
 * Baja de remito
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.validarEliminarRemito = function(idRow) {
	var row = modificacionIngresoDeMaterialesEvent.getGridOrden().jqGrid('getRowData', idRow);
	var self = modificacionIngresoDeMaterialesEvent;
	var data = { idRemito: row.idRemito };
	self.remitoParaEliminar = row.idRemito;
	self.service.validarEliminarRemito(data);
};

ModificacionIngresoDeMaterialesEvent.prototype.abrirPopUpConfirmarContabilizado = function(mensaje) {
	var popup = popupConfirmacionEvent;
	var mensajeConfirmacion = mensaje;    
	
	var self = modificacionIngresoDeMaterialesEvent;
	popup.create(self.div.id, mensajeConfirmacion);
	popup.confirmar = function() {
		var data = { idRemito : self.remitoParaEliminar };
		self.service.validarContabilizadoCabeceraRemito(data);
		popup.close();
		popup.remove();
	};
	popup.cancelar = function() {
		popup.close();
		popup.remove();
	};
};

ModificacionIngresoDeMaterialesEvent.prototype.abrirPopUpValidarDesenlace = function() {
	var popup = popupConfirmacionEvent;
	var mensajeConfirmacion = "Hay capítulos contabilizados. ¿Desea continuar?";    
	
	var self = modificacionIngresoDeMaterialesEvent;
	popup.create(self.div.id, mensajeConfirmacion);
	popup.confirmar = function() {
		var data = { idRemito : self.remitoParaEliminar };
		self.service.validarDesenlaceRemito(data);
		popup.close();
		popup.remove();
	};
	popup.cancelar = function() {
		popup.close();
		popup.remove();
	};
};

ModificacionIngresoDeMaterialesEvent.prototype.abrirPopUpChequeoDesenlaceRemito = function(mensajes) {
	var popup = popupConfirmacionEvent;
	var mensajeConfirmacion = mensajes;    
	
	var self = modificacionIngresoDeMaterialesEvent;
	popup.create(self.div.id, mensajeConfirmacion);
	popup.confirmar = function() {
		var data = { idRemito : self.remitoParaEliminar };
		popup.close();
		popup.remove();
		self.abrirPopUpEliminarRemito(data);
	};
	popup.cancelar = function() {
		popup.close();
		popup.remove();
	};
};

/**
 * Apertura del popup de baja de remito
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.abrirPopUpEliminarRemito = function(idRemito) {
	var popup = popupConfirmacionEvent;
	var mensajeConfirmacion = "¿Confirma la baja del remito?";    
	
	var self = modificacionIngresoDeMaterialesEvent;
	popup.create(self.div.id, mensajeConfirmacion);
	self.remitoParaEliminar = idRemito;
	popup.confirmar = function() {
		var data = {idRemito : self.remitoParaEliminar};
		self.service.eliminarRemito(data);
		popup.close();
		popup.remove();
	};
	popup.cancelar = function() {
		popup.close();
		popup.remove();
	};
};

/***********************************************************************************		
 ********************				FPCANCELAR					********************
 ***********************************************************************************/
/**
 * Apertura del popup de validacion de títulos sin capítulos
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.abrirPopUpTituloSinCapitulos = function(mensaje) {
	var popup = popupConfirmacionEvent;
	
	var self = modificacionIngresoDeMaterialesEvent;
	popup.create(self.div.id, mensaje);
	popup.confirmar = function() {
		self.service.confirmacionTituloSinCapitulos();
		popup.close();
		popup.remove();
	};
	popup.cancelar = function() {
		popup.close();
		popup.remove();
	};
};



/**
 * Getters
 * @author cetorres
 */
ModificacionIngresoDeMaterialesEvent.prototype.getDivCabeceraRemitoSeleccionado = function() {
	return $("#" + this.div.id + "_divCabeceraRemitoSeleccionado");
};
ModificacionIngresoDeMaterialesEvent.prototype.getAccordionCabeceraRemitoSeleccionado = function() {
	return $("#" + this.div.id + "_accordionCabeceraRemitoSeleccionado");
};
ModificacionIngresoDeMaterialesEvent.prototype.getAccordionModificacionCabeceraRemitoSeleccionado = function() {
	return $("#" + this.div.id + "_accordionModificacionCabeceraRemitoSeleccionado");
};
ModificacionIngresoDeMaterialesEvent.prototype.getButtonVolverBusquedaRemito = function() {
	return $("#" + this.div.id + "_volverBusquedaRemito");
};
ModificacionIngresoDeMaterialesEvent.prototype.getButtonVolverModificacionBusquedaRemito = function() {
	return $("#" + this.div.id + "_volverModificacionBusquedaRemito");
};
ModificacionIngresoDeMaterialesEvent.prototype.getButtonAceptarModificacionCabecera = function() {
	return $("#" + this.div.id + "_aceptarModificacionCabecera");
};
ModificacionIngresoDeMaterialesEvent.prototype.getInputNumeroRemito = function() {
	return $("#" + this.div.id + "_numeroRemito");
};
ModificacionIngresoDeMaterialesEvent.prototype.getInputNumeroGuia = function() {
	return $("#" + this.div.id + "_numeroGuia");
};
ModificacionIngresoDeMaterialesEvent.prototype.getInputFechaIngreso = function() {
	return $("#" + this.div.id + "_fechaIngreso");
};
ModificacionIngresoDeMaterialesEvent.prototype.getInputFechaRemitoGuia = function() {
	return $("#" + this.div.id + "_fechaRemitoGuia");
};

var modificacionIngresoDeMaterialesEvent = new ModificacionIngresoDeMaterialesEvent(new DivDefinition('ModificacionIngresoDeMaterialesEventId'));