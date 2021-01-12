/**
 * FP180 - Amortizar título sin exhibir / recibir
 * @author cetorres
 */

AmortizarTituloSinExhibirRecibirEvent.CASTELLANO = "castellano";
AmortizarTituloSinExhibirRecibirEvent.ORIGINAL = "original";

function AmortizarTituloSinExhibirRecibirEvent(div) {
	this.div = div;
	/* Criterio de búsqueda de títulos */
	this.tipoTitulo = AmortizarTituloSinExhibirRecibirEvent.CASTELLANO;
	this.tipoTituloNotSelected = AmortizarTituloSinExhibirRecibirEvent.ORIGINAL;
	
	/* Datos de la última búsqueda realizada */
	this.senialSeleccionada;
	this.ultimaBusqueda;

	/* Información necesaria para amortizar un título */
	this.dataAmortizacion = {
		senial: new String(),
		contrato : new Number(),
		clave: new String(),
		grupo: new Number()
	};
	/* Datos de selección de un título */
	this.distribuidorTituloSelected;
};

extend(AmortizarTituloSinExhibirRecibirEvent, Plugin);

AmortizarTituloSinExhibirRecibirEvent.prototype.create = function() {
	var self = amortizarTituloSinExhibirRecibirEvent;
	self.service = new AmortizarTituloSinExhibirRecibirService();
	Component.get("html/amortizarTituloSinExhibirRecibir/listadoTitulosSinExhibirRecibir.html", amortizarTituloSinExhibirRecibirEvent.draw);
};

AmortizarTituloSinExhibirRecibirEvent.prototype.draw = function(comp) {
	var self = amortizarTituloSinExhibirRecibirEvent;
	$("#viewContainer").empty().append(comp.replace(/{{id}}/g, self.div.id).replace(/{{tipoBusquedaTitulo}}/g, self.tipoTitulo));
	
	self.getAccordionListadoTitulos().accordion({
		collapsible: false,
		active: 0,
		autoHeight: false,
		icons: false
	});
	Component.populateSelect(self.getSelector("senial"), seniales, "codigo", "descripcion", false);
	
	self.getCambiarTipoTitulo().click(self.cambiarTipoBusqueda);
	self.getCambiarTipoTitulo().text("Orden tipo " + self.tipoTituloNotSelected);
	self.getButtonBuscarTituloPorCodigo().button().click(self.buscarTituloPorCodigo);
	self.getButtonBuscarTituloPorDesc().button().click(self.buscarTituloPorDescripcion);
	self.getButtonLimpiar().button().click(self.limpiarFiltros);
	
	self.getInputBusquedaTituloPorCodigo().keypress(function (event) {
		if (event.which == 13) {
			event.preventDefault();
			self.buscarTituloPorCodigo();
			return;
		}
	});
	self.getInputBusquedaTituloPorDescripcion().keypress(function (event) {
		if (event.which == 13) {
			event.preventDefault();
			self.buscarTituloPorDescripcion();
			return;
		}
	});
	self.drawGridTitulos();
	$("#"+self.div.id+"_gridContainerTitulos").attr("style","display:none");
};

/***********************************************************************************		
 ********************	Listado titulos sin exhibir / recibir	********************
 ***********************************************************************************/
/**
 * Modificación del criterio de busqueda de titulos  
 * @author cetorres
 */
AmortizarTituloSinExhibirRecibirEvent.prototype.cambiarTipoBusqueda = function() {
	/* Modifico el criterio de búsqueda */
	var self = amortizarTituloSinExhibirRecibirEvent;
	if (self.tipoTitulo === AmortizarTituloSinExhibirRecibirEvent.CASTELLANO) {
		self.tipoTitulo = AmortizarTituloSinExhibirRecibirEvent.ORIGINAL;
		self.tipoTituloNotSelected = AmortizarTituloSinExhibirRecibirEvent.CASTELLANO;
	} else {
		self.tipoTitulo = AmortizarTituloSinExhibirRecibirEvent.CASTELLANO;
		self.tipoTituloNotSelected = AmortizarTituloSinExhibirRecibirEvent.ORIGINAL;
	}
	self.getTipoBusqueda().text(self.tipoTitulo);
	self.getCambiarTipoTitulo().text("Orden tipo " + self.tipoTituloNotSelected);
	/* Limpio los filtros y la grilla */
	self.limpiarFiltros();
};

/**
 * Búsquedas por código
 * @author cetorres
 */
AmortizarTituloSinExhibirRecibirEvent.prototype.buscarTituloPorCodigo = function() {
	var self = amortizarTituloSinExhibirRecibirEvent;
	
	if (Validator.isEmpty(self.getInputBusquedaTituloPorCodigo(), false)) {
		Validator.focus(self.getInputBusquedaTituloPorCodigo(), "Debe ingresar alguna clave");
		return;
	}
	

	var data = {
		codigo : self.getInputBusquedaTituloPorCodigo().val(),
		senial : $("#senialDefaultUsuario").val()
	};

	self.ultimaBusqueda = {
		codigo : self.getInputBusquedaTituloPorCodigo().val(),
		senial : $("#senialDefaultUsuario").val(),
		busquedaCodigo : true,
		busquedaRazonSocial : false
	};
	
	self.senialSeleccionada = $("#senialDefaultUsuario").val();
	$("#"+self.div.id+"_gridContainerTitulos").attr("style","display: block");
	self.service.getTituloPorCodigo(data, self.tipoTitulo);
};

/**
 * Búsquedas por descripción
 * @author cetorres
 */
AmortizarTituloSinExhibirRecibirEvent.prototype.buscarTituloPorDescripcion = function() {
	var self = amortizarTituloSinExhibirRecibirEvent;
	
	if (Validator.isEmpty(self.getInputBusquedaTituloPorDescripcion(), false)) {
		Validator.focus(self.getInputBusquedaTituloPorDescripcion(), "Debe ingresar algún título");
		return;
	}
	
	
	var data = {
		descripcion : self.getInputBusquedaTituloPorDescripcion().val(),
		senial : $("#senialDefaultUsuario").val()
	};
	
	self.ultimaBusqueda = {
		descripcion : self.getInputBusquedaTituloPorDescripcion().val(),
		senial : $("#senialDefaultUsuario").val(),
		busquedaCodigo : false,
		busquedaRazonSocial : true
	};
	
	self.senialSeleccionada = $("#senialDefaultUsuario").val();
	$("#"+self.div.id+"_gridContainerTitulos").attr("style","display: block");
	self.service.getTituloPorDescripcion(data, self.tipoTitulo);
};

/**
 * Limpieza de filtros
 * @author cetorres
 */
AmortizarTituloSinExhibirRecibirEvent.prototype.limpiarFiltros = function() {
	var self = amortizarTituloSinExhibirRecibirEvent;
	self.getInputBusquedaTituloPorCodigo().val(null);
	self.getInputBusquedaTituloPorDescripcion().val(null);
	
	self.getGridTitulos().clearGridData();
};
/**
 * jqGrid de títulos
 * @author cetorres
 */
AmortizarTituloSinExhibirRecibirEvent.prototype.drawGridTitulos = function() {
	var self = amortizarTituloSinExhibirRecibirEvent;
	self.getGridTitulos().jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Clave', 'Titulo', 'Contrato', 'Distribuidor', 'PER', 'REC', 'Por amortizar', '', '', '', ''],
		colModel:[ 
				{name:'clave',			index:'clave', 			width:150, 	sortable:false,	align:'center'}, 
				{name:'titulo',			index:'titulo', 		width:300, 	sortable:false,	align:'left'},  
				{name:'contrato',		index:'contrato', 		width:70, 	sortable:false,	align:'center'},
				{name:'distribuidor',	index:'distribuidor', 	width:300, 	sortable:false,	align:'left'},
				{name:'perpetuo',		index:'perpetuo', 		width:30, 	sortable:false,	align:'center'},
				{name:'recibido',		index:'recibido', 		width:30, 	sortable:false,	align:'center'},
				{name:'porAmortizar',	index:'porAmortizar', 	width:300, 	sortable:false,	align:'right',	formatter:'currency'},
				{name:'senial',			index:'senial', 									hidden:true},
				{name:'grupo',			index:'grupo',	 									hidden:true},
				{name:'amortizar',		index:'amortizar', 		width:30, 	sortable:false,	align:'center'},
				{name:'verCapitulos',	index:'verCapitulos', 	width:30,	sortable:false,	align:'center'}
		],
		rowNum: 20,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: "#" + this.div.id + '_pagerGridTitulos',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Títulos',
		gridComplete: function() {
        	var ids = $(this).jqGrid('getDataIDs');
        	for (var i = 0; i < ids.length; i++) {
        		var rowId = ids[i];
        		var contrato =  $(this).jqGrid('getRowData', rowId).contrato;
        		var clave = $(this).jqGrid('getRowData', rowId).clave;
        		var grupo = $(this).jqGrid('getRowData', rowId).grupo;
        		var distribuidor = $(this).jqGrid('getRowData', rowId).distribuidor;
    			var divAmortizar = "<div onclick='amortizarTituloSinExhibirRecibirEvent.abrirPopUpAmortizar(\"" + $("#senialDefaultUsuario").val() + "\", " + contrato + ", \"" + clave + "\", " + grupo + ")' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-pencil conTooltip' title='Amortizar'></span></div>";
    			$(this).jqGrid('setRowData', rowId, { amortizar: divAmortizar });
				var divVerCapitulos = "<div onclick='amortizarTituloSinExhibirRecibirEvent.verCapitulos(\"" + $("#senialDefaultUsuario").val() + "\", " + contrato + ", \"" + clave + "\", \"" + distribuidor + "\")' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-search conTooltip' title='Ver capítulos'></span></div>";
				$(this).jqGrid('setRowData', rowId, { verCapitulos: divVerCapitulos });
        	}
	    }
	});
};

AmortizarTituloSinExhibirRecibirEvent.prototype.fillGridTitulos = function(data) {
	var self = amortizarTituloSinExhibirRecibirEvent;
	self.getGridTitulos().clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};

/**
 * Getters
 * @author cetorres
 */
AmortizarTituloSinExhibirRecibirEvent.prototype.getViewListadoTitulosSinExhibirRecibir = function() {
	return $("#" + this.div.id + "_listadoTitulosSinExhibirRecibir");
};
AmortizarTituloSinExhibirRecibirEvent.prototype.getAccordionListadoTitulos = function() {
	return $("#" + this.div.id + "_accordionListadoTitulos");
};
AmortizarTituloSinExhibirRecibirEvent.prototype.getInputBusquedaTituloPorCodigo = function() {
	return $("#" + this.div.id + "_inputBusquedaTituloCodigo");
};
AmortizarTituloSinExhibirRecibirEvent.prototype.getInputBusquedaTituloPorDescripcion = function() {
	return $("#" + this.div.id + "_inputBusquedaTituloDescripcion");
};
AmortizarTituloSinExhibirRecibirEvent.prototype.getSelectSenial = function() {
	return $("#" + this.div.id + "_senial");
};
AmortizarTituloSinExhibirRecibirEvent.prototype.getButtonLimpiar = function() {
	return $("#" + this.div.id + "_limpiar");
};
AmortizarTituloSinExhibirRecibirEvent.prototype.getButtonBuscarTituloPorCodigo = function() {
	return $("#" + this.div.id + "_buscarTituloPorCodigo");
};
AmortizarTituloSinExhibirRecibirEvent.prototype.getButtonBuscarTituloPorDesc = function() {
	return $("#" + this.div.id + "_buscarTituloPorDescripcion");
};
AmortizarTituloSinExhibirRecibirEvent.prototype.getCambiarTipoTitulo = function() {
	return $("#" + this.div.id + "_cambiarTipoTitulo");
};
AmortizarTituloSinExhibirRecibirEvent.prototype.getTipoBusqueda = function() {
	return $("#" + this.div.id + "_tipoBusqueda");
};
AmortizarTituloSinExhibirRecibirEvent.prototype.getGridTitulos = function() {
	return $("#" + this.div.id + "_gridTitulos");
};

/***********************************************************************************		
 ********************			Visualización capítulos			********************
 ***********************************************************************************/
AmortizarTituloSinExhibirRecibirEvent.prototype.getCompVisualizacionCapitulos = function() {
	Component.get("html/amortizarTituloSinExhibirRecibir/visualizacionCapitulos.html", amortizarTituloSinExhibirRecibirEvent.drawFormVisualizacionCapitulos);
};

AmortizarTituloSinExhibirRecibirEvent.prototype.drawFormVisualizacionCapitulos = function(comp) {
	var self = amortizarTituloSinExhibirRecibirEvent;
	self.getViewListadoTitulosSinExhibirRecibir().hide();
	comp = comp.replace(/{{id}}/g, self.div.id);
	
	if (self.getViewVisualizacionCapitulos().length) {
		self.getViewVisualizacionCapitulos().replaceWith(comp);
	} else {
		self.getDiv().append(comp);
	}
	
	self.getAccordionListadoCapitulos().accordion({
		collapsible: false,
		active: 0,
		autoHeight: false,
		icons: false
	});
	
	self.getButtonVolver().button().click(self.volver);
	self.drawGridCapitulos(null);
};

/**
 * Búsquedas de capítulos para un título
 * @author cetorres
 */
AmortizarTituloSinExhibirRecibirEvent.prototype.verCapitulos = function(senial, contrato, clave, distribuidor) {
	var self = amortizarTituloSinExhibirRecibirEvent;
	self.distribuidorTituloSelected = distribuidor;
	self.getCompVisualizacionCapitulos();
	var data = {
		senial: senial,
		contrato : contrato,
		clave: clave
	};
	self.service.getCapitulosDelTitulo(data);
};

AmortizarTituloSinExhibirRecibirEvent.prototype.fillCabeceraTituloCapitulos = function(data) {
	var self = amortizarTituloSinExhibirRecibirEvent;
	/* Seteo los datos de la cabecera */
	self.getTituloCastellano().html(data.tituloCastellano);
	self.getDistribuidor().html(self.distribuidorTituloSelected);
	self.getContratoTitulo().html(data.contrato);
	self.getFormula().html(data.titulo);
	self.getTotalCapitulosRecibidosTitulo().html(data.totalCapitulos);
	self.getMontoPorAmortizarTitulo().autoNumericSet(data.porAmortizar);
	self.getMontoPorAmortizarTitulo().html(self.getMontoPorAmortizarTitulo().val());
};

/**
 * jqGrid de capítulos
 * @author cetorres
 */
AmortizarTituloSinExhibirRecibirEvent.prototype.drawGridCapitulos = function(data) {
	var self = amortizarTituloSinExhibirRecibirEvent;
	self.getGridCapitulos().jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Nro. Cap.', 'Título - Capítulo', 'Rec.', 'Conf. Exhib.'],
		colModel:[ 
			{name:'nroCapitulo',	index:'nroCapitulo', 	width:30, 	sortable:false, align:'center',	sorttype:"int"},
			{name:'descripcion',	index:'descripcion',	width:400, 	sortable:false,	align:'left'},
			{name:'recibido',		index:'recibido',		width:30, 	sortable:false,	align:'center'},
			{name:'confExhibicion',	index:'confExhibicion',	width:30, 	sortable:false,	align:'center'} 
		],
		sortname: 'nroCapitulo',
		sortorder: 'asc',
		rowNum: 20,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: "#" + this.div.id + '_pagerGridCapitulos',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Capítulos'
	});
};

AmortizarTituloSinExhibirRecibirEvent.prototype.fillGridCapitulos = function(data) {
	var self = amortizarTituloSinExhibirRecibirEvent;
	self.getGridCapitulos().clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};

AmortizarTituloSinExhibirRecibirEvent.prototype.volver = function() {
	var self = amortizarTituloSinExhibirRecibirEvent;
	self.getViewVisualizacionCapitulos().hide();
	self.getViewListadoTitulosSinExhibirRecibir().show();
};

/**
 * Getters
 * @author cetorres
 */
AmortizarTituloSinExhibirRecibirEvent.prototype.getViewVisualizacionCapitulos = function() {
	return $("#" + this.div.id + "_visualizacionCapitulos");
};
AmortizarTituloSinExhibirRecibirEvent.prototype.getAccordionListadoCapitulos = function() {
	return $("#" + this.div.id + "_listadoCapitulos");
};
AmortizarTituloSinExhibirRecibirEvent.prototype.getButtonVolver = function() {
	return $("#" + this.div.id + "_volver");
};
AmortizarTituloSinExhibirRecibirEvent.prototype.getTituloCastellano = function() {
	return $("#" + this.div.id + "_tituloCastellano");
};
AmortizarTituloSinExhibirRecibirEvent.prototype.getContratoTitulo = function() {
	return $("#" + this.div.id + "_contratoTitulo");
};
AmortizarTituloSinExhibirRecibirEvent.prototype.getDistribuidor = function() {
	return $("#" + this.div.id + "_distribuidor");
};
AmortizarTituloSinExhibirRecibirEvent.prototype.getFormula = function() {
	return $("#" + this.div.id + "_formula");
};
AmortizarTituloSinExhibirRecibirEvent.prototype.getTotalCapitulosRecibidosTitulo = function() {
	return $("#" + this.div.id + "_totalCapitulosRecibidosTitulo");
};
AmortizarTituloSinExhibirRecibirEvent.prototype.getMontoPorAmortizarTitulo = function() {
	return $("#" + this.div.id + "_montoPorAmortizarTitulo");
};
AmortizarTituloSinExhibirRecibirEvent.prototype.getGridCapitulos = function() {
	return $("#" + this.div.id + "_gridCapitulos");
};

/***********************************************************************************		
 ********************	Amortizar titulo sin exhibir / recibir	********************
 ***********************************************************************************/
AmortizarTituloSinExhibirRecibirEvent.prototype.abrirPopUpAmortizar = function(senial, contrato, clave, grupo) {
	var self = amortizarTituloSinExhibirRecibirEvent;
	self.dataAmortizacion.senial = senial;
	self.dataAmortizacion.contrato = contrato;
	self.dataAmortizacion.clave = clave;
	self.dataAmortizacion.grupo = grupo;
	
	var popup = popupConfirmacionEvent;
	var mensajeConfirmacion = "El Título - Contrato se amortizará en el próximo cierre contable. ¿Confirma?";    
	popup.create(self.div.id, mensajeConfirmacion);
	popup.cancelar = function() {
		popup.close();
	};
	popup.confirmar = function() {
		self.amortizarTitulo();
	};
	
};

AmortizarTituloSinExhibirRecibirEvent.prototype.amortizarTitulo = function() {
	var self = amortizarTituloSinExhibirRecibirEvent;
	self.service.amortizarTitulo(self.dataAmortizacion);
};

/* Initialize */
var amortizarTituloSinExhibirRecibirEvent = new AmortizarTituloSinExhibirRecibirEvent(new DivDefinition('AmortizarTituloSinExhibirRecibirEventId'));