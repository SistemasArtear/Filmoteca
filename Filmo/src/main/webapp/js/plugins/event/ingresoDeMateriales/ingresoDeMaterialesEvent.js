/**
 * FP210 - Ingreso de materiales
 * @author cetorres
 */

IngresoDeMaterialesEvent.CASTELLANO = "castellano";
IngresoDeMaterialesEvent.ORIGINAL = "original";

/***********************************************************************************		
 ********************	 Trabajar con ingreso de materiales		********************
 ***********************************************************************************/
function IngresoDeMaterialesEvent(div) {
	this.div = div;
	this.tipoBusquedaTitulos = IngresoDeMaterialesEvent.CASTELLANO;
	this.material = {
			cabecera : {
				idRemito : null,
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
				er: new String(),
				contrato: new Number(),
				senial: new String(),
				grupo: new Number(),
				tipoMaterial: new String()
			}
	};
	this.selectTipoMaterial = [ 
   		{codigo: "N", descripcion: "Sin contrato"},
   		{codigo: "S", descripcion: "Con contrato"}
   	];
};

extend(IngresoDeMaterialesEvent, Plugin);

IngresoDeMaterialesEvent.prototype.create = function() {
	var self = ingresoDeMaterialesEvent;
	self.service = new IngresoDeMaterialesService();
	
	self.tipoBusquedaTitulos = IngresoDeMaterialesEvent.CASTELLANO;
	self.material = {
	            cabecera : {
	                idRemito : null,
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
	                er: new String(),
	                contrato: new Number(),
	                senial: new String(),
	                grupo: new Number(),
	                tipoMaterial: new String()
	            }
	    };
	self.selectTipoMaterial = [ 
	        {codigo: "N", descripcion: "Sin contrato"},
	        {codigo: "S", descripcion: "Con contrato"}
	    ];
	    
	Component.get("html/ingresoDeMateriales/formIngresoMaterialesCabecera.html", ingresoDeMaterialesEvent.draw);
};

IngresoDeMaterialesEvent.prototype.draw = function(comp) {
	var self = ingresoDeMaterialesEvent;
	$("#viewContainer").empty().append(comp.replace(/{{id}}/g, self.div.id));

	Accordion.getInstance(self.getAccordionFormCabecera());
	
	self.getButtonFormCabecera().button().click(self.aceptarFormCabecera);
	self.getInputCodigoDistribuidor().numeric();
	self.getButtonPopupDistribuidorPorCodigo().click(self.drawPopupSituarDistribuidoresPorCodigo);
	self.getButtonPopupDistribuidorPorRazonSocial().click(self.drawPopupSituarDistribuidoresPorRazonSocial);
	
	Datepicker.picker(self.getInputFechaIngreso());
	Datepicker.fullYearDatepicker(self.getInputFechaIngreso());
	self.getInputFechaIngreso().datepicker('setDate', new Date());
	
	Datepicker.picker(self.getInputFechaRemitoGuia());
	Datepicker.fullYearDatepicker(self.getInputFechaRemitoGuia());
	self.getInputFechaRemitoGuia().datepicker('setDate', new Date());
};

/**
 * Situar distribuidores por código
 * @author cetorres
 */
IngresoDeMaterialesEvent.prototype.drawPopupSituarDistribuidoresPorCodigo = function() {
    var distribuidor = $("#IngresoDeMaterialesEventId_codigoDistribuidor").val();
	var data = {codigoDistribuidor: distribuidor};
	situarPopupEvent.reset();
	situarPopupEvent.create("buscarDistribuidoresParaMateriales.action", ingresoDeMaterialesEvent.distribuidorSelected, data, "distribuidores");
	situarPopupEvent.nombreSituar = "Distribuidores por código";
	situarPopupEvent.doAfterDrawGrid = function() {
	    $("#buscarDistribuidoresParaMateriales_inputBusquedaSituar").val(distribuidor);
		situarPopupEvent.getGrid().setColProp('codigo', {sorttype:'number', align:'right'}).trigger("reloadGrid");
		situarPopupEvent.getGrid().setColProp('razonSocial', {sorttype:'text', align:'left'}).trigger("reloadGrid");
	};
};

/**
 * Situar distribuidores por razón social
 * @author cetorres
 */
IngresoDeMaterialesEvent.prototype.drawPopupSituarDistribuidoresPorRazonSocial = function() {
    var distribuidor = $("#IngresoDeMaterialesEventId_razonSocialDistribuidor").val();
	var data = {razonSocialDistribuidor : distribuidor};
	situarPopupEvent.reset();
	situarPopupEvent.create("buscarDistribuidoresParaMateriales.action", ingresoDeMaterialesEvent.distribuidorSelected, data, "distribuidores");
	situarPopupEvent.nombreSituar = "Distribuidores por razón social";
	situarPopupEvent.doAfterDrawGrid = function() {
        $("#buscarDistribuidoresParaMateriales_inputBusquedaSituar").val(distribuidor);
		situarPopupEvent.getGrid().sortGrid('razonSocial', true);
		situarPopupEvent.getGrid().setColProp('codigo', {sorttype:'number', align:'right'}).trigger("reloadGrid");
		situarPopupEvent.getGrid().setColProp('razonSocial', {sorttype:'text', align:'left'}).trigger("reloadGrid");
	};
};

/**
 * Selección situar distribuidores
 * @author cetorres
 */
IngresoDeMaterialesEvent.prototype.distribuidorSelected = function(distribuidor) {
	var self = ingresoDeMaterialesEvent;
	self.getInputCodigoDistribuidor().val(distribuidor.codigo);
	self.getInputRazonSocialDistribuidor().val(distribuidor.razonSocial);
};

/**
 * Confirmar ingreso de cabecera del remito
 * @author cetorres
 */
IngresoDeMaterialesEvent.prototype.aceptarFormCabecera = function() {
	var self = ingresoDeMaterialesEvent;
	
	if (self.validForm()) {
		self.material.cabecera.fechaIngreso = self.getInputFechaIngreso().val();
		self.material.cabecera.fechaRemito = self.getInputFechaRemitoGuia().val();
		self.material.cabecera.razonSocialDistribuidor = self.getInputRazonSocialDistribuidor().val();
		self.material.cabecera.codigoDistribuidor = self.getInputCodigoDistribuidor().val();
		self.material.cabecera.numeroRemito = self.getInputNumeroRemito().val();
		self.material.cabecera.numeroGuia = self.getInputNumeroGuia().val();
		self.getCompListadoTitulos();
	} 
};

/**
 * Validación de cabecera del remito
 * @author cetorres
 */
IngresoDeMaterialesEvent.prototype.validForm = function() {
	var self = ingresoDeMaterialesEvent;
	
	var fechaIngreso = this.getInputFechaIngreso();
	var fechaRemito = this.getInputFechaRemitoGuia();
	var razonSocial = this.getInputRazonSocialDistribuidor();
	var codigo = this.getInputCodigoDistribuidor();
	var numRemito = this.getInputNumeroRemito();
	var numGuia = this.getInputNumeroGuia();
	
	var data = new Array();
	
	if (self.getInputCodigoDistribuidor().val()) {
		data = {codigoDistribuidor: self.getInputCodigoDistribuidor().val()};
	} else {
		data = {razonSocialDistribuidor : self.getInputRazonSocialDistribuidor().val()}; 
	}
	
	if (self.service.getDatosDistribuidor(data)) {
		return false;
	} else if (Validator.isEmpty(fechaIngreso)) {
		Validator.focus(fechaIngreso);
		return false;
	} else if (Validator.isEmpty(fechaRemito)) {
		Validator.focus(fechaRemito);
		return false;
	} else if (Validator.fillAtLeastOneField(razonSocial, codigo)) {
		Validator.focus(razonSocial, "Debe ingresar el código o la descripción del distribuidor");
		return false;
	} else if (Validator.fillOnlyOneField(numRemito, numGuia)) {
		Validator.focus(numRemito, "Debe ingresar sólo el Nº de remito o el Nº de guía");
		return false;
	} else {
		return true;
	}
};

/**
 * Limpieza de cabecera del remito
 * @author cetorres
 */
IngresoDeMaterialesEvent.prototype.limpiarFiltrosFormCabecera = function() {
	var self = ingresoDeMaterialesEvent;
	self.getInputRazonSocialDistribuidor().val(null);
	self.getInputCodigoDistribuidor().val(null);
	self.getInputNumeroRemito().val(null);
	self.getInputNumeroGuia().val(null);
	self.getInputFechaIngreso().datepicker('setDate', new Date());
	self.getInputFechaRemitoGuia().datepicker('setDate', new Date());
};

/**
 * Getters
 * @author cetorres
 */
IngresoDeMaterialesEvent.prototype.getDivCabecera = function() {
	return $("#" + this.div.id + "_divFormCabecera");
};
IngresoDeMaterialesEvent.prototype.getAccordionFormCabecera = function() {
	return $("#" + this.div.id + "_accordionFormCabecera");
};
IngresoDeMaterialesEvent.prototype.getButtonPopupDistribuidorPorCodigo = function() {
	return $("#" + this.div.id + "_popupDistribuidorPorCodigo");
};
IngresoDeMaterialesEvent.prototype.getButtonPopupDistribuidorPorRazonSocial = function() {
	return $("#" + this.div.id + "_popupDistribuidorPorRazonSocial");
};
IngresoDeMaterialesEvent.prototype.getInputRazonSocialDistribuidor = function() {
	return $("#" + this.div.id + "_razonSocialDistribuidor");
};
IngresoDeMaterialesEvent.prototype.getInputCodigoDistribuidor = function() {
	return $("#" + this.div.id + "_codigoDistribuidor");
};
IngresoDeMaterialesEvent.prototype.getInputNumeroRemito = function() {
	return $("#" + this.div.id + "_numeroRemito");
};
IngresoDeMaterialesEvent.prototype.getInputNumeroGuia = function() {
	return $("#" + this.div.id + "_numeroGuia");
};
IngresoDeMaterialesEvent.prototype.getInputFechaIngreso = function() {
	return $("#" + this.div.id + "_fechaIngreso");
};
IngresoDeMaterialesEvent.prototype.getInputFechaRemitoGuia = function() {
	return $("#" + this.div.id + "_fechaRemitoGuia");
};

/***********************************************************************************		
 ********************			Seleccionar títulos				********************
 ***********************************************************************************/
IngresoDeMaterialesEvent.prototype.getCompListadoTitulos = function() {
	Component.get("html/ingresoDeMateriales/listadoTitulos.html", ingresoDeMaterialesEvent.drawListadoTitulos);
};

IngresoDeMaterialesEvent.prototype.drawListadoTitulos = function(comp) {
	var self = ingresoDeMaterialesEvent;
	self.getDivCabecera().hide();
	comp = comp.replace(/{{id}}/g, self.div.id)
			   .replace(/{{codigo}}/g, self.material.cabecera.codigoDistribuidor)
			   .replace(/{{razonSocial}}/g, self.material.cabecera.razonSocialDistribuidor)
			   .replace(/{{remito-guia}}/g, self.material.cabecera.numeroRemito ? self.material.cabecera.numeroRemito : self.material.cabecera.numeroGuia)
			   .replace(/{{fechaRemitoGuia}}/g, self.material.cabecera.fechaRemito)
			   .replace(/{{fechaIngreso}}/g, self.material.cabecera.fechaIngreso)
			   .replace(/{{tipoBusquedaTitulo}}/g, self.tipoBusquedaTitulos);
	if (self.getDivTitulos().length) {
		self.getDivTitulos().replaceWith(comp);
	} else {
		self.getDiv().append(comp);
	}
	
	Accordion.getInstance(self.getAccordionFormTitulos());
	
	
	Component.populateSelect(self.getSelectTipoMaterial(), self.selectTipoMaterial, "codigo", "descripcion", false);
	self.getSelectTipoMaterial()[0].removeChild(self.getSelectTipoMaterial()[0].options[0]);
	/* Por defecto, la opción que figura es 'Con contrato' */
	self.getSelectTipoMaterial().val("S");
	/* Botonera */
	self.getCambiarTipoBusquedaTitulo().click(self.cambiarTipoBusquedaTitulos);
	self.getButtonAceptarBusquedaTitulos().button().click(self.buscarTitulosPorDescripcion);
	self.getButtonVolverBusquedaTitulos().button().click(self.volverBusquedaTitulos);
	
	self.drawGridTitulos();
	self.cambiarLabelGridTitulos();
	self.limpiarFiltrosFormCabecera();
};

/**
 * Cambio del criterio de búsqueda de títulos
 * @author cetorres
 */
IngresoDeMaterialesEvent.prototype.cambiarLabelGridTitulos = function() {
	var self = ingresoDeMaterialesEvent;
	self.getGridTitulos().setLabel('titulo', 'Título ' + ingresoDeMaterialesEvent.tipoBusquedaTitulos, '', '');
};

/**
 * Cambio del criterio de búsqueda de títulos
 * @author cetorres
 */
IngresoDeMaterialesEvent.prototype.cambiarTipoBusquedaTitulos = function() {
	var self = ingresoDeMaterialesEvent;
	if (self.tipoBusquedaTitulos === IngresoDeMaterialesEvent.CASTELLANO) {
		self.tipoBusquedaTitulos = IngresoDeMaterialesEvent.ORIGINAL;
	} else {
		self.tipoBusquedaTitulos = IngresoDeMaterialesEvent.CASTELLANO;
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
IngresoDeMaterialesEvent.prototype.buscarTitulosPorDescripcion = function() {
	var self = ingresoDeMaterialesEvent;
	
		var data = {
			descripcionTitulo : self.getInputTituloBusqueda().val(),
			codigoDistribuidor: self.material.cabecera.codigoDistribuidor,
			tipoMaterial: self.getSelectTipoMaterial().val(),
			senial : $("#senialDefaultUsuario").val()
		};
		self.material.detalle.tipoMaterial = self.getSelectTipoMaterial().val();
		self.material.detalle.senial = $("#senialDefaultUsuario").val();
		self.service.getTitulosPorDescripcion(data, self.tipoBusquedaTitulos);
	 
};

/**
 * jqGrid de títulos
 * @author cetorres
 */
IngresoDeMaterialesEvent.prototype.drawGridTitulos = function() {
	var self = ingresoDeMaterialesEvent;
	self.getGridTitulos().jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Titulo', 'Contrato', '', 'Clave', 'E / R', '', 'Fórmula', ''],
		colModel:[ 
			{name:'titulo', 			index:'titulo', 			width:300, 	sortable:false,	align:'left'}, 
			{name:'contrato', 			index:'contrato',			width:70, 	sortable:false, align:'center', formatter:function(value, options, rData) { return value > 0 ? value : ''; }},   
			{name:'mas', 				index:'mas', 				width:30, 	sortable:false, align:'center', formatter:function(value, options, rData) { return value > 1 ? "<div style='display:inline-table'><span class='ui-icon ui-icon-circle-plus conTooltip' title=\"" + value + "\"></span></div>": '' ; }}, 
			{name:'clave', 				index:'clave', 				width:60, 	sortable:false, align:'center'}, 
			{name:'estrenoRepeticion', 	index:'estrenoRepeticion', 	width:30, 	sortable:false, align:'center'}, 
			{name:'formula', 			index:'formula', 			width:150, 	sortable:false, align:'center'},
			{name:'grupo', 				index:'grupo', 											hidden:true},
			{name:'verTitulo', 			index:'verTitulo', 			width:30, 	sortable:false, align:'center'}
		],
		rowNum: 20,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#IngresoDeMaterialesEventId_pagerGridTitulos',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Títulos',
		gridComplete: function() { 							
			var ids = $(this).jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var rowData = $(this).jqGrid('getRowData', ids[i]);
				if (rowData.estrenoRepeticion!='') {
					var title = (rowData.estrenoRepeticion == "E") ? "Estreno" : "Repetición";
					self.getGridTitulos().setCell(ids[i], 'estrenoRepeticion', '', '', {'title': title});
				}
				var divVerTitulo = "<div onclick='ingresoDeMaterialesEvent.getCompTituloSeleccionado(\"" + ids[i] + "\")' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-pencil conTooltip' title='Ver título'></span></div>";
    			$(this).jqGrid('setRowData', ids[i], { verTitulo: divVerTitulo });
			}
			self.getGridTitulos().trigger("reloadGrid");
		}
	});
	
	/* Grilla con tamaño automático */
	Component.resizableGrid(self.getGridTitulos());
};

IngresoDeMaterialesEvent.prototype.fillGridTitulos = function(data) {
	var self = ingresoDeMaterialesEvent;
	self.getGridTitulos().clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};

/**
 * Vuelta al ingreso de la cabecera del remito
 * @author cetorres
 */
IngresoDeMaterialesEvent.prototype.volverBusquedaTitulos = function() {
	var self = ingresoDeMaterialesEvent;
	self.material.cabecera.idRemito = null;
	self.getDivTitulos().hide();
	self.getDivCabecera().show();
};

/**
 * Getters
 * @author cetorres
 */
IngresoDeMaterialesEvent.prototype.getDivTitulos = function() {
	return $("#" + this.div.id + "_divTitulos");
};
IngresoDeMaterialesEvent.prototype.getFormCabecera = function() {
	return $("#" + this.div.id + "_formCabecera");
};
IngresoDeMaterialesEvent.prototype.getAccordionFormTitulos = function() {
	return $("#" + this.div.id + "_accordionFormTitulos");
};
IngresoDeMaterialesEvent.prototype.getLabelTipoBusquedaTitulo = function() {
	return $("#" + this.div.id + "_tipoBusquedaTitulo");
};
IngresoDeMaterialesEvent.prototype.getInputTituloBusqueda = function() {
	return $("#" + this.div.id + "_tituloBusqueda");
};
IngresoDeMaterialesEvent.prototype.getSelectTipoMaterial = function() {
	return $("#" + this.div.id + "_tipoMaterial");
};
IngresoDeMaterialesEvent.prototype.getButtonFormCabecera = function() {
	return $("#" + this.div.id + "_aceptarFormCabecera");
};
IngresoDeMaterialesEvent.prototype.getCambiarTipoBusquedaTitulo = function() {
	return $("#" + this.div.id + "_cambiarTipoBusquedaTitulo");
};
IngresoDeMaterialesEvent.prototype.getButtonAceptarBusquedaTitulos = function() {
	return $("#" + this.div.id + "_aceptarBusquedaTitulos");
};
IngresoDeMaterialesEvent.prototype.getButtonVolverBusquedaTitulos = function() {
	return $("#" + this.div.id + "_volverBusquedaTitulos");
};
IngresoDeMaterialesEvent.prototype.getGridTitulos = function() {
	return $("#" + this.div.id + "_gridTitulos");
};

/***********************************************************************************		
 ********************			Ingresar sin capítulos			********************
 ***********************************************************************************/
IngresoDeMaterialesEvent.prototype.getCompTituloSeleccionado = function(id) {
	var row = ingresoDeMaterialesEvent.getGridTitulos().jqGrid('getRowData', id);
	
	var self = ingresoDeMaterialesEvent;
	self.material.detalle.contrato = row.contrato;
	self.material.detalle.titulo = row.titulo;
	self.material.detalle.clave = row.clave;
	self.material.detalle.er = row.estrenoRepeticion;
	self.material.detalle.grupo = row.grupo;
	
	Component.get("html/ingresoDeMateriales/formIngresoMaterialesDetalle.html", ingresoDeMaterialesEvent.onTituloSeleccionado);
};

IngresoDeMaterialesEvent.prototype.onTituloSeleccionado = function(comp) {
	var self = ingresoDeMaterialesEvent;
	self.getDivTitulos().hide();
	comp = comp.replace(/{{id}}/g, self.div.id)
	   .replace(/{{codigo}}/g, self.material.cabecera.codigoDistribuidor)
	   .replace(/{{razonSocial}}/g, self.material.cabecera.razonSocialDistribuidor)
	   .replace(/{{clave}}/g, self.material.detalle.clave)
	   .replace(/{{titulo}}/g, self.material.detalle.titulo)
	   .replace(/{{er}}/g, self.material.detalle.er);
	
	if (self.getDivMaterialesDetalle().length) {
		self.getDivMaterialesDetalle().replaceWith(comp);
	} else {
		self.getDiv().append(comp);
	}
	
	Accordion.getInstance(self.getAccordionFormCargaCapitulos());
	
	var data = { tipoTituloSeleccionado : self.material.detalle.clave.substring(0,2) };
	self.service.determinarFamiliaTitulo(data);
};

/**
 * Formulario carga con capitulos(FP021017)
 * @author cetorres
 */
IngresoDeMaterialesEvent.prototype.drawFormularioCargaConCapitulos = function() {
	var self = ingresoDeMaterialesEvent;
	/* Accordion */
	Accordion.getInstance(self.getAccordionFormCC());
	/* Botonera */
	for (var i=0; i <= 8; i++) {
	    $("#" + self.div.id + "_popupSoporteCC_" + i).click(self.drawPopupSituarSoporte);
	}
	self.getButtonGuardarSoporteTituloCC().button().click(function(){
	    var capituloYparte = [];
		for (var i=0; i <= 8; i++) {
		    if ($("#" + ingresoDeMaterialesEvent.div.id + "_nroCapituloCC_" + i).val() != "") {
		        capituloYparte.push( 
		                {capitulo: $("#" + ingresoDeMaterialesEvent.div.id + "_nroCapituloCC_" + i).val(), 
		                 parte: $("#" + ingresoDeMaterialesEvent.div.id + "_nroParteCC_" + i).val()} );
		    }
		}
		var resultado = jQuery.map( capituloYparte, function( a, index ) {
		    var ret = false;
		    for (var i=0; i < capituloYparte.length; i++) {
		        if ( (a.capitulo == capituloYparte[i].capitulo) && (a.parte == capituloYparte[i].parte) && (index != i) )
	            ret = true;
	        }
		    return ret;
		});
        for (var i=0; i < resultado.length; i++) {
            if (resultado[i]) {
                MESSAGE.alert("No puede repetirse capitulos/partes, revise los datos");
                return;
            }
        }
		
		for (var i=0; i <= 8; i++) {
    		var data = {
    		        soporteTitulo : {
    		            idProveedor : self.material.cabecera.codigoDistribuidor,
    		            nroRemito : self.material.cabecera.numeroRemito,
    		            nroGuia : self.material.cabecera.numeroGuia,
    		            fechaRtoGuia : self.material.cabecera.fechaRemito,
    		            fechaMov : self.material.cabecera.fechaIngreso,
    		            contrato : self.material.detalle.contrato,
    		            grupo : self.material.detalle.grupo,
    		            senial : self.material.detalle.senial,
    		            clave : self.material.detalle.clave,
    		            nroCap : $("#" + self.div.id + "_nroCapituloCC_" + i).val(),
    		            nroParte : $("#" + self.div.id + "_nroParteCC_" + i).val() != "" ? parseInt($("#" + self.div.id + "_nroParteCC_" + i).val()) : 0,
	                    codSoporte : $("#" + self.div.id + "_soporteCC_" + i).val().toUpperCase(),
	                    cantRollos : 0, 
	                    requiereChequeo : 'S', 
	                    tipoMaterial: self.material.detalle.tipoMaterial
    		        }
    		};
    		if (self.material.cabecera.idRemito != null) {
    		    data.soporteTitulo.idRemito = self.material.cabecera.idRemito;
    		};
    		if ( ($("#" + self.div.id + "_nroCapituloCC_" + i).val() != "") && ($("#" + self.div.id + "_soporteCC_" + i).val() != "") ) {
    		    self.service.altaSoporteTitulo(data, true);
    		}
		}
        ingresoDeMaterialesEvent.limpiarCamposCC();
		return;
	});
	
//	var nroCapitulos = 0;
//	$("#IngresoDeMaterialesEventId_agregarMasCapitulos").button().click(function(){
//	    nroCapitulos++;
//	    var capitulos = $("#capituloParte").clone(); 
//	    var inputs = $(capitulos[0]).find("input");
//	    $.each(inputs, function(index, value) {
//	        var oldId = $(value).attr("id");
//	        $(value).attr("id", oldId.replace("0",nroCapitulos));
//	        $(value).val("");
//	      });
//	    capitulos.appendTo("#capitulosPartes");
//	    return;
//    });
	
	
	self.getButtonVolverFormTitulos().button().click(self.volverSoporteTitulo);
	/* Inputs ['Capítulo', 'Parte', 'Soporte', 'Carrete', 'Lata', 'Torta', 'Motivo'] */
	self.getInputNroCapituloCC().numeric();
	self.getInputNroParteCC().numeric();
//	self.getInputCarreteCC().numeric();
//	self.getInputLataCC().numeric();
//	self.getInputTortaCC().numeric();
	/* Mostrado */
	self.getDivCC().show();
	self.getDivSC().hide();
	self.drawGridCargaCapitulos();
};	

IngresoDeMaterialesEvent.prototype.drawGridCargaCapitulos = function() {
	var self = ingresoDeMaterialesEvent;
	self.getGridCargaCapitulos().jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Capítulo', 'Parte', 'Soporte', 'Carrete', 'Lata', 'Torta', 'Motivo'],
		colModel:[ 
			{name:'capitulo', 		index:'capitulo', 		width:70, 	sortable:false,	align:'center'}, 
			{name:'parte', 			index:'parte',			width:70, 	sortable:false, align:'center'},  
			{name:'codigoSoporte', 	index:'codigoSoporte', 	width:70, 	sortable:false, align:'center'}, 
			{name:'carrete', 		index:'carrete', 		width:70, 	sortable:false, align:'center'}, 
			{name:'lata', 			index:'lata', 			width:70, 	sortable:false, align:'center'}, 
			{name:'torta', 			index:'torta', 			width:70, 	sortable:false, align:'center'},
			{name:'motivo', 		index:'motivo', 		width:200, 	sortable:false, align:'left'}
		],
		rowNum: 20,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#IngresoDeMaterialesEventId_pagerCargaCapitulos',
		viewrecords: true, 
		caption: 'Capítulos'   	
	});
	
	/* Grilla con tamaño automático */
	Component.resizableGrid(self.getGridCargaCapitulos());
};

IngresoDeMaterialesEvent.prototype.fillGridCargaCapitulos = function(data) {
	var self = ingresoDeMaterialesEvent;
	self.getGridCargaCapitulos().clearGridData();
	self.getGridCargaCapitulos().setGridParam({data: data}).trigger('reloadGrid');
};

IngresoDeMaterialesEvent.prototype.agregarCapitulo = function(data, motivoIngreso) {
	var self = ingresoDeMaterialesEvent;
	
	var registro = { 'capitulo' : data.nroCap,
					 'parte' : data.nroParte,
					 'codigoSoporte' : data.codSoporte,
//					 'carrete' : self.getInputCarreteCC().val(), 
//					 'lata' : self.getInputLataCC().val(),
//					 'torta' : self.getInputTortaCC().val(), 
					 'motivo': motivoIngreso
	};
	var data = self.getGridCargaCapitulos().getRowData();

	var exists = false;
	$.each(data, function (i, fila) {
	    if (fila.capitulo == registro.capitulo && fila.parte == registro.parte && fila.codigoSoporte == registro.codigoSoporte
//	    		&& fila.carrete == registro.carrete && fila.lata == registro.lata && fila.torta == registro.torta 
	    		&& fila.motivo == registro.motivo) {
	    	exists = true;
	    	return;
	    }
	});

	if (!exists) {
		self.getGridCargaCapitulos().addRowData(self.getGridCargaCapitulos().getGridParam('records') + 1, registro, "last", null);
		self.getGridCargaCapitulos().trigger("reloadGrid");
	} 
};

IngresoDeMaterialesEvent.prototype.limpiarCamposCC = function() {
	var inputs = $("#capitulosPartes").find("input");
    $.each(inputs, function(index, value) {
        $(value).val(null);
      });
};

/**
 * Formulario carga sin capitulos(FP021009)
 * @author cetorres
 */
IngresoDeMaterialesEvent.prototype.drawFormularioCargaSinCapitulos = function() {
	var self = ingresoDeMaterialesEvent;
	/* Accordion */
	Accordion.getInstance(self.getAccordionFormSC());
	/* Botonera */
	self.getButtonPopupSoporteSC().click(self.drawPopupSituarSoporte);
	self.getButtonGuardarSoporteTituloSC().button().click(function(){
		self.altaSoporteTitulo(false);
	});
	self.getButtonVolverFormTitulos().button().click(self.volverSoporteTitulo);
	/* Inputs */
	self.getInputCantidadRollosSC().numeric();
	self.getInputCantidadRollosSC().val("1");
//	self.getInputCarreteSC().numeric();
//	self.getInputLataSC().numeric();
//	self.getInputTortaSC().numeric();
	/* Mostrado */
	self.getDivCC().hide();
	self.getDivSC().show();
};

IngresoDeMaterialesEvent.prototype.drawPopupSituarSoporte = function(comp) {
	var data = {codigoSoporte: ''};
	situarPopupEvent.reset();
	situarPopupEvent.nombreSituar = "Soportes";
	situarPopupEvent.create("buscarSoportesParaMateriales.action", ingresoDeMaterialesEvent.soporteSelected, data, "soportes");
	ingresoDeMaterialesEvent.soporteSelected.soporteSelectedInput = comp;
};

IngresoDeMaterialesEvent.prototype.soporteSelected = function(soporte) {
	var self = ingresoDeMaterialesEvent;
	self.getInputSoporteSC().val(soporte.codigo);
	$("#" + $(ingresoDeMaterialesEvent.soporteSelected.soporteSelectedInput.currentTarget).attr("id").replace("popupSoporteCC","soporteCC")).val(soporte.codigo);
};

IngresoDeMaterialesEvent.prototype.validateAltaSoporteTitulo = function(conCapitulos) {
	var self = ingresoDeMaterialesEvent;
	
	if (conCapitulos) {
		if (Validator.isEmpty(self.getInputNroCapituloCC(), false)) {
			Validator.focus(self.getInputNroCapituloCC(), "Ingrese un número de capítulo");
			return false;
		}
		if (Validator.isEmpty(self.getInputSoporteCC(), false)) {
			Validator.focus(self.getInputSoporteCC(), "Soporte inexistente");
			return false;
		} else {
			var data = {codigoSoporte: self.getInputSoporteCC().val().toUpperCase()};
			var isValid = self.service.validarSoporte(data);
			if (!isValid) {
				Validator.focus(self.getInputSoporteCC(), "Ingrese un soporte válido");
				return false;
			}
		}
	} else {
		if (Validator.isEmpty(self.getInputSoporteSC(), false)) {
			Validator.focus(self.getInputSoporteSC(), "Soporte inexistente");
			return false;
		} else {
			var data = {codigoSoporte: self.getInputSoporteSC().val().toUpperCase()};
			var isValid = self.service.validarSoporte(data);
			if (!isValid) {
				Validator.focus(self.getInputSoporteSC(), "Ingrese un soporte válido");
				return false;
			}
		}
		if (self.getInputCantidadRollosSC().val() == "" || self.getInputCantidadRollosSC().val() == 0) {
			Validator.focus(self.getInputCantidadRollosSC(), "La cantidad de rollos no puede ser nula.");
			return false;
		}
	}
	
	return true;
};

IngresoDeMaterialesEvent.prototype.altaSoporteTitulo = function(conCapitulos) {
	var self = ingresoDeMaterialesEvent;
	if (self.validateAltaSoporteTitulo(conCapitulos)) {
		var data = {
			soporteTitulo : {
			    idProveedor : self.material.cabecera.codigoDistribuidor,
			    nroRemito : self.material.cabecera.numeroRemito,
			    nroGuia : self.material.cabecera.numeroGuia,
			    fechaRtoGuia : self.material.cabecera.fechaRemito,
			    fechaMov : self.material.cabecera.fechaIngreso,
			    contrato : self.material.detalle.contrato,
				grupo : self.material.detalle.grupo,
			    senial : self.material.detalle.senial,
			    clave : self.material.detalle.clave,
			    nroCap : conCapitulos ? self.getInputNroCapituloCC().val() : 0,
			    nroParte : conCapitulos ? (self.getInputNroParteCC().val() != "" ? parseInt(self.getInputNroParteCC().val()) : 0) : 0,
			    codSoporte : conCapitulos ? self.getInputSoporteCC().val().toUpperCase() : self.getInputSoporteSC().val().toUpperCase(),
			    cantRollos : conCapitulos ? 0 : parseInt(self.getInputCantidadRollosSC().val()), 
	    		requiereChequeo : 'S',
//			    carrete : conCapitulos ? (self.getInputCarreteCC().val() != "" ? parseInt(self.getInputCarreteCC().val()) : 0) 
//			    					   : (self.getInputCarreteSC().val() != "" ? parseInt(self.getInputCarreteSC().val()) : 0),
//			    lata : conCapitulos ? (self.getInputLataCC().val() != "" ? parseInt(self.getInputLataCC().val()) : 0) 
//    					   			: (self.getInputLataSC().val() != "" ? parseInt(self.getInputLataSC().val()) : 0), 
//			    torta : conCapitulos ? (self.getInputTortaCC().val() != "" ? parseInt(self.getInputTortaCC().val()) : 0) 
//			   						 : (self.getInputTortaSC().val() != "" ? parseInt(self.getInputTortaSC().val()) : 0), 
			    tipoMaterial: self.material.detalle.tipoMaterial
			}
		};
		if (self.material.cabecera.idRemito != null) {
			data.soporteTitulo.idRemito = self.material.cabecera.idRemito;
		};
		self.service.altaSoporteTitulo(data, conCapitulos);
	}
};

IngresoDeMaterialesEvent.prototype.volverSoporteTitulo = function() {
	var self = ingresoDeMaterialesEvent;
	self.getDivMaterialesDetalle().hide();
	self.getDivTitulos().show();
	self.buscarTitulosPorDescripcion();
};

/***********************************************************************************		
 ********************	Seleccionar títulos desde modificación	********************
 ***********************************************************************************/
IngresoDeMaterialesEvent.prototype.getCompListadoTitulosDesdeModificacion = function() {
	Component.get("html/ingresoDeMateriales/listadoTitulos.html", ingresoDeMaterialesEvent.drawListadoTitulosDesdeModificacion);
};

IngresoDeMaterialesEvent.prototype.drawListadoTitulosDesdeModificacion = function(comp) {
	var self = ingresoDeMaterialesEvent;
	modificacionIngresoDeMaterialesEvent.getDiv().hide();
	comp = comp.replace(/{{id}}/g, self.div.id)
			   .replace(/{{codigo}}/g, "")
			   .replace(/{{razonSocial}}/g, self.material.cabecera.razonSocialDistribuidor)
			   .replace(/{{remito-guia}}/g, self.material.cabecera.numeroRemito ? self.material.cabecera.numeroRemito : self.material.cabecera.numeroGuia)
			   .replace(/{{fechaRemitoGuia}}/g, self.material.cabecera.fechaRemito)
			   .replace(/{{fechaIngreso}}/g, self.material.cabecera.fechaIngreso)
			   .replace(/{{tipoBusquedaTitulo}}/g, self.tipoBusquedaTitulos);
	
	self.getDiv().empty();
	self.getDiv().append("<h1>Trabajar con ingreso de materiales</h1>");
	self.getDiv().append(comp);
	Accordion.getInstance(self.getAccordionFormTitulos());
	self.getDiv().show();
	
	Component.populateSelect(self.getSelector("senial"), seniales, "codigo", "descripcion", false);
	Component.populateSelect(self.getSelectTipoMaterial(), self.selectTipoMaterial, "codigo", "descripcion", false);
	self.getSelectTipoMaterial()[0].removeChild(self.getSelectTipoMaterial()[0].options[0]);
	/* Por defecto, la opción que figura es 'Con contrato' */
	self.getSelectTipoMaterial().val("S");
	/* Botonera */
	self.getCambiarTipoBusquedaTitulo().click(self.cambiarTipoBusquedaTitulos);
	self.getButtonAceptarBusquedaTitulos().button().click(self.buscarTitulosPorDescripcion);
	self.getButtonVolverBusquedaTitulos().button().click(self.volverBusquedaTitulosModificacion);
	
	self.drawGridTitulos();
	self.cambiarLabelGridTitulos();
	self.limpiarFiltrosFormCabecera();
};

/**
 * Vuelta al ingreso de la cabecera del remito
 * @author cetorres
 */
IngresoDeMaterialesEvent.prototype.volverBusquedaTitulosModificacion = function() {
	var self = ingresoDeMaterialesEvent;
	self.material.cabecera.idRemito = null;
	self.getDiv().hide();
	modificacionIngresoDeMaterialesEvent.getDiv().show();
	modificacionIngresoDeMaterialesEvent.buscarTitulosRemito();
};

/**
 * Getters generales
 * @author cetorres
 */
IngresoDeMaterialesEvent.prototype.getDivMaterialesDetalle = function() {
	return $("#" + this.div.id + "_divMaterialesDetalle");
};
IngresoDeMaterialesEvent.prototype.getAccordionFormCargaCapitulos = function() {
	return $("#" + this.div.id + "_accordionFormCargaCapitulos");
};
IngresoDeMaterialesEvent.prototype.getButtonVolverFormTitulos = function() {
	return $("#" + this.div.id + "_volverFormTitulos");
};

/**
 * Getters - con capitulos
 * @author cetorres
 */
IngresoDeMaterialesEvent.prototype.getDivCC = function() {
	return $("#" + this.div.id + "_cc");
};
IngresoDeMaterialesEvent.prototype.getAccordionFormCC = function() {
	return $("#" + this.div.id + "_accordionFormCC");
};
IngresoDeMaterialesEvent.prototype.getInputNroCapituloCC = function() {
	return $("#" + this.div.id + "_nroCapituloCC_0");
};
IngresoDeMaterialesEvent.prototype.getInputNroParteCC = function() {
	return $("#" + this.div.id + "_nroParteCC_0");
};
IngresoDeMaterialesEvent.prototype.getInputSoporteCC = function() {
	return $("#" + this.div.id + "_soporteCC_0");
};
IngresoDeMaterialesEvent.prototype.getButtonPopupSoporteCC = function() {
	return $("#" + this.div.id + "_popupSoporteCC");
};
IngresoDeMaterialesEvent.prototype.getInputCarreteCC = function() {
	return $("#" + this.div.id + "_carreteCC");
};
IngresoDeMaterialesEvent.prototype.getInputLataCC = function() {
	return $("#" + this.div.id + "_lataCC");
};
IngresoDeMaterialesEvent.prototype.getInputTortaCC = function() {
	return $("#" + this.div.id + "_tortaCC");
};
IngresoDeMaterialesEvent.prototype.getGridCargaCapitulos = function() {
	return $("#" + this.div.id + "_gridCargaCapitulos");
};
IngresoDeMaterialesEvent.prototype.getButtonGuardarSoporteTituloCC = function() {
	return $("#" + this.div.id + "_guardarSoporteTituloCC");
};

/**
 * Getters - sin capitulos
 * @author cetorres
 */
IngresoDeMaterialesEvent.prototype.getDivSC = function() {
	return $("#" + this.div.id + "_sc");
};
IngresoDeMaterialesEvent.prototype.getAccordionFormSC = function() {
	return $("#" + this.div.id + "_accordionFormSC");
};
IngresoDeMaterialesEvent.prototype.getInputSoporteSC = function() {
	return $("#" + this.div.id + "_soporteSC");
};
IngresoDeMaterialesEvent.prototype.getButtonPopupSoporteSC = function() {
	return $("#" + this.div.id + "_popupSoporteSC");
};
IngresoDeMaterialesEvent.prototype.getInputCantidadRollosSC = function() {
	return $("#" + this.div.id + "_cantidadRollosSC");
};
IngresoDeMaterialesEvent.prototype.getCheckboxRequiereChequeoSC = function() {
	return $("#" + this.div.id + "_requiereChequeoSC");
};
IngresoDeMaterialesEvent.prototype.getInputCarreteSC = function() {
	return $("#" + this.div.id + "_carreteSC");
};
IngresoDeMaterialesEvent.prototype.getInputLataSC = function() {
	return $("#" + this.div.id + "_lataSC");
};
IngresoDeMaterialesEvent.prototype.getInputTortaSC = function() {
	return $("#" + this.div.id + "_tortaSC");
};
IngresoDeMaterialesEvent.prototype.getInputObservacionesSC = function() {
	return $("#" + this.div.id + "_observacionesSC");
};
IngresoDeMaterialesEvent.prototype.getButtonGuardarSoporteTituloSC = function() {
	return $("#" + this.div.id + "_guardarSoporteTituloSC");
};

/* Initialize */
var ingresoDeMaterialesEvent = new IngresoDeMaterialesEvent(new DivDefinition('IngresoDeMaterialesEventId'));