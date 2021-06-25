
/***********************************************************************************		
 ***************	 Clonar remitos de materiales con contrato		****************
 ***********************************************************************************/

ClonarRemitosMaterialesConContratoEvent.BUSQUEDA_CLAVE = "CLAVE";
ClonarRemitosMaterialesConContratoEvent.BUSQUEDA_DESCRIPCION = "DESCRIPCION";
ClonarRemitosMaterialesConContratoEvent.CASTELLANO = "castellano";
ClonarRemitosMaterialesConContratoEvent.ORIGINAL = "original";

function ClonarRemitosMaterialesConContratoEvent(div) {
	this.div = div;
	this.busqueda = ClonarRemitosMaterialesConContratoEvent.BUSQUEDA_CLAVE;
	this.tipoBusquedaTitulo = ClonarRemitosMaterialesConContratoEvent.CASTELLANO;
	this.tipoTituloClonar = null;
	this.numeroTituloClonar = null;
	this.idProveedorAnteriorClonar = null;
	this.idProveedorNuevoClonar = null;
	this.contratoAnteriorClonar = null;
	this.contratoNuevoClonar = null;
	this.grupoAnteriorClonar = null;
	this.grupoNuevoClonar = null;
};

extend(ClonarRemitosMaterialesConContratoEvent, Plugin);

ClonarRemitosMaterialesConContratoEvent.prototype.create = function() {
	var self = clonarRemitosMaterialesConContratoEvent;
	self.service = new ClonarRemitosMaterialesConContratoService();
	Component.get("html/clonarRemitosMaterialesConContrato/clonarRemitosMaterialesConContrato.html", clonarRemitosMaterialesConContratoEvent.draw);
};

ClonarRemitosMaterialesConContratoEvent.prototype.draw = function(comp) {
	var self = clonarRemitosMaterialesConContratoEvent;
	$("#viewContainer").empty().append(comp.replace(/{{id}}/g, self.div.id)
			.replace(/{{tipoBusquedaTitulo}}/g, self.tipoBusquedaTitulo));
	Accordion.getInstance(self.getSelector("accordionFormCabecera"));
	Accordion.getInstance(self.getSelector("accordionFormClonar"));
	
	self.getSelector("divFormSeccionClonar").attr("Style", "display: none"); // seccion "Clonar" inicialmente oculta
	
	self.busqueda = ClonarRemitosMaterialesConContratoEvent.BUSQUEDA_CLAVE;
	self.tipoBusquedaTitulo = ClonarRemitosMaterialesConContratoEvent.CASTELLANO;
	self.getSelector("camposBusquedaClave").attr("Style", "display: ");	
	self.getSelector("camposBusquedaDescTitulo").attr("Style", "display: none");
	
	self.getButtonPopupTipoTitulo().click(self.popupTipoTitulo);
	self.getAIrBusquedaDescTitulo().click(self.mostrarBusquedaDescTitulo);
	self.getAIrBusquedaClave().click(self.mostrarBusquedaClave);
	self.getACambiarTipoBusquedaTitulo().click(self.cambiarTipoBusquedaTitulo);
	
	/* Botonera */
	self.getSelector("aceptarBuscarRemitos").button().click(self.buscarRemitos);
	self.getSelector("limpiarBuscarRemitos").button().click(self.limpiarFiltrosbuscarRemitos);
	
	//Botonera y popups de seccion clonar:
	self.getButtonPopupDistribuidorPorCodigo().click(self.drawPopupSituarDistribuidoresPorCodigo);
	self.getButtonPopupDistribuidorPorRazonSocial().click(self.drawPopupSituarDistribuidoresPorRazonSocial);
	self.getSelector("clonar").button().click(self.clonarRemitos);
	self.getSelector("limpiarSeccionClonar").button().click(self.limpiarSeccionClonar);
	
	self.getSelector("tipoTituloCodigo").keypress(function (event) {
		if (event.which == 13) {
			event.preventDefault();
			self.buscarRemitos();
			return;
		}
	});
	self.getSelector("numeroTitulo").keypress(function (event) {
		if (event.which == 13) {
			event.preventDefault();
			self.buscarRemitos();
			return;
		}
	});
	self.getSelector("contratoAnterior").keypress(function (event) {
		if (event.which == 13) {
			event.preventDefault();
			self.buscarRemitos();
			return;
		}
	});
	self.getSelector("grupoAnterior").keypress(function (event) {
		if (event.which == 13) {
			event.preventDefault();
			self.buscarRemitos();
			return;
		}
	});
	self.getSelector("busquedaTitulosDescripcion").keypress(function (event) {
		if (event.which == 13) {
			event.preventDefault();
			self.buscarRemitos();
			return;
		}
	});
};


ClonarRemitosMaterialesConContratoEvent.prototype.cambiarTipoBusquedaTitulo = function() {
	var self = clonarRemitosMaterialesConContratoEvent;
	if (self.tipoBusquedaTitulo === TrabajarConTitulosEvent.CASTELLANO) {
		self.tipoBusquedaTitulo = TrabajarConTitulosEvent.ORIGINAL;
	}else{
		self.tipoBusquedaTitulo = TrabajarConTitulosEvent.CASTELLANO;
	}

	self.getSelector("tipoBusquedaTitulo").text(self.tipoBusquedaTitulo);
	self.limpiarFiltrosbuscarRemitos();
};

ClonarRemitosMaterialesConContratoEvent.prototype.mostrarBusquedaClave = function() {
	var self = clonarRemitosMaterialesConContratoEvent;
	self.busqueda = ClonarRemitosMaterialesConContratoEvent.BUSQUEDA_CLAVE;
	self.getSelector("camposBusquedaClave").attr("Style", "display: ");	
	self.getSelector("camposBusquedaDescTitulo").attr("Style", "display: none");
	self.limpiarFiltrosbuscarRemitos();
};

ClonarRemitosMaterialesConContratoEvent.prototype.mostrarBusquedaDescTitulo = function() {
	var self = clonarRemitosMaterialesConContratoEvent;
	self.busqueda = ClonarRemitosMaterialesConContratoEvent.BUSQUEDA_DESCRIPCION;
	self.getSelector("camposBusquedaClave").attr("Style", "display: none");	
	self.getSelector("camposBusquedaDescTitulo").attr("Style", "display: ");
	self.limpiarFiltrosbuscarRemitos();
};

ClonarRemitosMaterialesConContratoEvent.prototype.limpiarFiltrosbuscarRemitos = function() {
	var self = clonarRemitosMaterialesConContratoEvent;
	
	//  Limpia seccion filtros:
	self.getSelector("tipoTituloCodigo").val(null);
	self.getSelector("tipoTituloDesc").text("");
	self.getSelector("numeroTitulo").val(null);
	self.getSelector("contratoAnterior").val(null),  
	self.getSelector("grupoAnterior").val(null);
	self.getSelector("busquedaTitulosDescripcion").val(null);
	
	// Limpia seccion "Clonar":
	self.limpiarSeccionClonar();
	
	// Vuelve a ocultar la seccion "Clonar":
	self.getSelector("divFormSeccionClonar").attr("Style", "display: none");
	
	// Limpia la grilla:
	self.getGridTitulos().clearGridData();
};

ClonarRemitosMaterialesConContratoEvent.prototype.limpiarSeccionClonar = function() {
	// Limpia seccion "Clonar":
	var self = clonarRemitosMaterialesConContratoEvent;
	self.tipoTituloClonar = null;
	self.numeroTituloClonar = null;
	self.idProveedorAnteriorClonar = null;
	self.idProveedorNuevoClonar = null;
	self.contratoAnteriorClonar = null;
	self.contratoNuevoClonar = null;
	self.grupoAnteriorClonar = null;
	self.grupoNuevoClonar = null;
	self.getSpanTipoTituloCodigoClonar().html("");
	self.getSpanNumeroTituloClonar().html("");
	self.getSpanProveedorAnteriorClonar().html("");
	self.getSpanContratoAnteriorClonar().html("");
	self.getSpanGrupoAnteriorClonar().html("");
	self.getInputRazonSocialDistribuidor().val("");
	self.getInputCodigoDistribuidor().val("");
	self.getInputContratoNuevoClonar().val("");
	self.getInputGrupoNuevoClonar().val("");
};

ClonarRemitosMaterialesConContratoEvent.prototype.buscarRemitos = function() {
	var self = clonarRemitosMaterialesConContratoEvent;
	var data = {};
	
	if(self.busqueda === ClonarRemitosMaterialesConContratoEvent.BUSQUEDA_CLAVE) {
		if (Validator.isEmpty(self.getSelector("tipoTituloCodigo") || !self.getSelector("tipoTituloCodigo"))) {
			Validator.focus(self.getSelector("tipoTituloCodigo"), "Debe seleccionar el Tipo de Título a buscar");
			return;
		}
		
		if (Validator.isEmpty(self.getSelector("numeroTitulo") || !self.getSelector("numeroTitulo"))) {
			Validator.focus(self.getSelector("numeroTitulo"), "Debe ingresar el Nro. de Título a buscar");
			return;
		} else if ( isNaN(self.getSelector("numeroTitulo").val()) || !Number.isInteger(parseFloat(self.getSelector("numeroTitulo").val()))) {
			Validator.focus(self.getSelector("numeroTitulo"), "El Nro. de Título a buscar debe ser numérico");
			return;
		}
		
		if (Validator.isEmpty(self.getSelector("contratoAnterior") || !self.getSelector("contratoAnterior"))) {
			Validator.focus(self.getSelector("contratoAnterior"), "Debe ingresar el Contrato Anterior a buscar");
			return;
		} else if ( isNaN(self.getSelector("contratoAnterior").val()) || !Number.isInteger(parseFloat(self.getSelector("contratoAnterior").val()))) {
			Validator.focus(self.getSelector("contratoAnterior"), "El Contrato Anterior a buscar debe ser numérico");
			return;
		}
		
		if (Validator.isEmpty(self.getSelector("grupoAnterior") || !self.getSelector("contratoAnterior"))) {
			Validator.focus(self.getSelector("grupoAnterior"), "Debe ingresar el Grupo Anterior a buscar");
			return;
		} else if ( isNaN(self.getSelector("grupoAnterior").val()) || !Number.isInteger(parseFloat(self.getSelector("grupoAnterior").val()))) {
			Validator.focus(self.getSelector("grupoAnterior"), "El Grupo Anterior a buscar debe ser numérico");
			return
		}

		data = {
				tipoTitulo : self.getSelector("tipoTituloCodigo").val(),
				numeroTitulo : self.getSelector("numeroTitulo").val(),
				contratoAnterior : self.getSelector("contratoAnterior").val(),  
				grupoAnterior : self.getSelector("grupoAnterior").val(),
				tituloOriginal : null,
				tituloCastellano : null
			};
		
	} else if (self.busqueda === ClonarRemitosMaterialesConContratoEvent.BUSQUEDA_DESCRIPCION) {
		if (Validator.isEmpty(self.getSelector("busquedaTitulosDescripcion") || !self.getSelector("busquedaTitulosDescripcion"))) {
			Validator.focus(self.getSelector("busquedaTitulosDescripcion"), "Debe ingresar el texto a buscar");
			return;
		}
		
		if(self.tipoBusquedaTitulo === ClonarRemitosMaterialesConContratoEvent.CASTELLANO) {
			data = {
					tipoTitulo : null,
					numeroTitulo : null,
					contratoAnterior : null,  
					grupoAnterior : null,
					tituloOriginal : null,
					tituloCastellano : self.getSelector("busquedaTitulosDescripcion").val()
				}
		} else {
			data = {
					tipoTitulo : null,
					numeroTitulo : null,
					contratoAnterior : null, 
					grupoAnterior : null,
					tituloOriginal : self.getSelector("busquedaTitulosDescripcion").val(),
					tituloCastellano : null
				}			
		}
	}
	
	var isValid = self.service.buscarRemitos(data, $.proxy(self.responseTitulos,self));
};

ClonarRemitosMaterialesConContratoEvent.prototype.clonarRemitos = function() {
	var self = clonarRemitosMaterialesConContratoEvent;
	
	if (self.tipoTituloClonar == null  || self.tipoTituloClonar == ""
		|| self.numeroTituloClonar == null  || self.numeroTituloClonar == "" 
			|| self.idProveedorAnteriorClonar == null  || self.idProveedorAnteriorClonar == ""
				|| self.contratoAnteriorClonar == null  || self.contratoAnteriorClonar == ""	
					|| self.grupoAnteriorClonar == null  || self.grupoAnteriorClonar == "") {
		MESSAGE.alert("Debe seleccionar un Tipo Título  - Nro. Título - Proveedor Anterior - Contrato Anterior - Grupo anterior para clonar");
		return;
	}
	
	
	if (Validator.isEmpty(self.getInputCodigoDistribuidor() || !self.getInputCodigoDistribuidor())) {
		Validator.focus(self.getInputCodigoDistribuidor(), "Debe seleccionar el nuevo proveedor para clonar");
		return;
	} else if ( isNaN(self.getInputCodigoDistribuidor().val()) || !Number.isInteger(parseFloat(self.getInputCodigoDistribuidor().val()))) {
		Validator.focus(self.getInputCodigoDistribuidor(), "El código del nuevo proveedor para clonar debe ser numérico");
		return;
	}

	if (Validator.isEmpty(self.getInputContratoNuevoClonar() || !self.getInputContratoNuevoClonar())) {
		Validator.focus(self.getInputContratoNuevoClonar(), "Debe ingresar el nuevo contrato para clonar");
		return;
	} else if ( isNaN(self.getInputContratoNuevoClonar().val()) || !Number.isInteger(parseFloat(self.getInputContratoNuevoClonar().val()))) {
		Validator.focus(self.getInputContratoNuevoClonar(), "El nuevo contrato para clonar debe ser numérico");
		return;
	}
	
	if (Validator.isEmpty(self.getInputGrupoNuevoClonar() || !self.getInputGrupoNuevoClonar())) {
		Validator.focus(self.getInputGrupoNuevoClonar(), "Debe ingresar el nuevo grupo para clonar");
		return;
	} else if ( isNaN(self.getInputGrupoNuevoClonar().val()) || !Number.isInteger(parseFloat(self.getInputGrupoNuevoClonar().val()))) {
		Validator.focus(self.getInputGrupoNuevoClonar(), "El nuevo grupo para clonar debe ser numérico");
		return;
	}
	
	
	self.idProveedorNuevoClonar = self.getInputCodigoDistribuidor().val();
	self.contratoNuevoClonar = self.getInputContratoNuevoClonar().val();
	self.grupoNuevoClonar = self.getInputGrupoNuevoClonar().val();
	
	var data = {
			tipoTitulo : self.tipoTituloClonar,
			numeroTitulo : self.numeroTituloClonar,
			idProveedorAnterior : self.idProveedorAnteriorClonar,
			idProveedorNuevo : self.idProveedorNuevoClonar,
			contratoAnterior : self.contratoAnteriorClonar,
			contratoNuevo : self.contratoNuevoClonar,
			grupoAnterior : self.grupoAnteriorClonar,
			grupoNuevo : self.grupoNuevoClonar
		};
		
	var isValid = self.service.clonarRemitos(data, $.proxy(self.responseClonar,self));
	
};

ClonarRemitosMaterialesConContratoEvent.prototype.responseClonar = function() {
	var self = clonarRemitosMaterialesConContratoEvent;
	self.limpiarSeccionClonar();
	self.buscarRemitos();
	MESSAGE.alert("Ha finalizado la clonación de remitos");
};

ClonarRemitosMaterialesConContratoEvent.prototype.popupTipoTitulo = function() {
    situarPopupEvent.reset();
	situarPopupEvent.nombreSituar = "Situar Tipo Titulos";
	situarPopupEvent.create("obtenerTiposDeTitulos.action", clonarRemitosMaterialesConContratoEvent.tipoTituloSelected, {descripcion : ""}, "tiposDeTitulos");
};

ClonarRemitosMaterialesConContratoEvent.prototype.tipoTituloSelected = function(row) {
	$("#"+clonarRemitosMaterialesConContratoEvent.div.id+"_tipoTituloCodigo").val(row.codigo);
	$("#"+clonarRemitosMaterialesConContratoEvent.div.id+"_tipoTituloDesc").text(row.descripcion);
};

ClonarRemitosMaterialesConContratoEvent.prototype.responseTitulos = function(data) {
	var self = clonarRemitosMaterialesConContratoEvent;	
	this.drawGrigTitulos(data);
};

ClonarRemitosMaterialesConContratoEvent.prototype.drawPopupSituarDistribuidoresPorCodigo = function() {
    var distribuidor = $("#ClonarRemitosMaterialesConContratoEventId_codigoDistribuidor").val();
	var data = {codigoDistribuidor: distribuidor};
	situarPopupEvent.reset();
	situarPopupEvent.create("buscarDistribuidoresParaMateriales.action", clonarRemitosMaterialesConContratoEvent.distribuidorSelected, data, "distribuidores");
	situarPopupEvent.nombreSituar = "Distribuidores por código";
	situarPopupEvent.doAfterDrawGrid = function() {
	    $("#buscarDistribuidoresParaMateriales_inputBusquedaSituar").val(distribuidor);
		situarPopupEvent.getGrid().setColProp('codigo', {sorttype:'number', align:'right'}).trigger("reloadGrid");
		situarPopupEvent.getGrid().setColProp('razonSocial', {sorttype:'text', align:'left'}).trigger("reloadGrid");
	};
};

ClonarRemitosMaterialesConContratoEvent.prototype.drawPopupSituarDistribuidoresPorRazonSocial = function() {
    var distribuidor = $("#ClonarRemitosMaterialesConContratoEventId_razonSocialDistribuidor").val();
	var data = {razonSocialDistribuidor : distribuidor};
	situarPopupEvent.reset();
	situarPopupEvent.create("buscarDistribuidoresParaMateriales.action", clonarRemitosMaterialesConContratoEvent.distribuidorSelected, data, "distribuidores");
	situarPopupEvent.nombreSituar = "Distribuidores por razón social";
	situarPopupEvent.doAfterDrawGrid = function() {
        $("#buscarDistribuidoresParaMateriales_inputBusquedaSituar").val(distribuidor);
		situarPopupEvent.getGrid().sortGrid('razonSocial', true);
		situarPopupEvent.getGrid().setColProp('codigo', {sorttype:'number', align:'right'}).trigger("reloadGrid");
		situarPopupEvent.getGrid().setColProp('razonSocial', {sorttype:'text', align:'left'}).trigger("reloadGrid");
	};
};

ClonarRemitosMaterialesConContratoEvent.prototype.distribuidorSelected = function(distribuidor) {
	var self = clonarRemitosMaterialesConContratoEvent;
	self.getInputCodigoDistribuidor().val(distribuidor.codigo);
	self.getInputRazonSocialDistribuidor().val(distribuidor.razonSocial);
};

ClonarRemitosMaterialesConContratoEvent.prototype.drawGrigTitulos = function(data) {
	var self = clonarRemitosMaterialesConContratoEvent;
	this.getGridTitulos().jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Nro Remito','Tipo Titulo', 'Nro. Titulo', 'Capitulo', 'Titulo Catellano', 'Titulo Original', 'Contrato', 'Grupo', 'Codigo Proveedor', 'Proveedor'],
		colModel:[ 
			{name:'nroRemito',index:'nroRemito', width:150, align: "center", sortable:true}, 
			{name:'tipoTitulo',index:'tipoTitulo', width:100, align: "center", sortable:true}, 
			{name:'nroTitulo',index:'nroTitulo', width:130, align: "center", sortable:true},
			{name:'capitulo',index:'capitulo', width:150, align: "center", sortable:true},
			{name:'tituloCastellano',index:'titulo', width:300, sortable:true},
			{name:'tituloOriginal',index:'titulo', width:300, sortable:true},
			{name:'contrato',index:'contrato', width:150, align: "center", sortable:true},
			{name:'grupo',index:'grupo', width:100, align: "center", sortable:true},
			{name:'idProveedor',index:'idProveedor', width:200, align: "center", sortable:true},
			{name:'razonSocialProveedor',index:'razonSocialProveedor', width:300, sortable:true}
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#ClonarRemitosMaterialesConContratoEventId_pagerGridTitulos',
		sortname: "nroRemito",
		sortorder: "desc",
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Seleccione el registro que desee clonar:',
		onSelectRow : function(id){
			self.tipoTituloClonar = $(this).jqGrid('getCell', id, 'tipoTitulo');
			self.numeroTituloClonar = $(this).jqGrid('getCell', id, 'nroTitulo');
			self.idProveedorAnteriorClonar = $(this).jqGrid('getCell', id, 'idProveedor');
			self.idProveedorNuevoClonar = $(this).jqGrid('getCell', id, 'razonSocialProveedor');
			self.contratoAnteriorClonar = $(this).jqGrid('getCell', id, 'contrato');
			self.grupoAnteriorClonar = $(this).jqGrid('getCell', id, 'grupo');
			self.getSpanTipoTituloCodigoClonar().html(self.tipoTituloClonar);
			self.getSpanNumeroTituloClonar().html(self.numeroTituloClonar);
			self.getSpanProveedorAnteriorClonar().html(self.idProveedorAnteriorClonar + "  " + $(this).jqGrid('getCell', id, 'razonSocialProveedor'));
			self.getSpanContratoAnteriorClonar().html(self.contratoAnteriorClonar);
			self.getSpanGrupoAnteriorClonar().html(self.grupoAnteriorClonar);
			self.getSelector("divFormSeccionClonar").attr("Style", "display: "); // Muestra seccion "Clonar"
			self.getInputContratoNuevoClonar().focus();
		}
	});
	this.getGridTitulos().clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};


ClonarRemitosMaterialesConContratoEvent.prototype.getButtonPopupTipoTitulo = function() {
	return $("#" + this.div.id + "_popupTipoTitulo");
};

ClonarRemitosMaterialesConContratoEvent.prototype.getGridTitulos = function() {
	return $("#" + this.div.id + "_gridTitulos");
};

ClonarRemitosMaterialesConContratoEvent.prototype.getButtonPopupDistribuidorPorCodigo = function() {
	return $("#" + this.div.id + "_popupDistribuidorPorCodigo");
};

ClonarRemitosMaterialesConContratoEvent.prototype.getButtonPopupDistribuidorPorRazonSocial = function() {
	return $("#" + this.div.id + "_popupDistribuidorPorRazonSocial");
};

ClonarRemitosMaterialesConContratoEvent.prototype.getInputRazonSocialDistribuidor = function() {
	return $("#" + this.div.id + "_razonSocialDistribuidor");
};

ClonarRemitosMaterialesConContratoEvent.prototype.getInputCodigoDistribuidor = function() {
	return $("#" + this.div.id + "_codigoDistribuidor");
};

ClonarRemitosMaterialesConContratoEvent.prototype.getInputContratoNuevoClonar = function() {
	return $("#" + this.div.id + "_contratoNuevoClonar");
};

ClonarRemitosMaterialesConContratoEvent.prototype.getInputGrupoNuevoClonar = function() {
	return $("#" + this.div.id + "_grupoNuevoClonar");
};

ClonarRemitosMaterialesConContratoEvent.prototype.getInputBusquedaTitulosDescripcion = function() {
	return $("#" + this.div.id + "_busquedaTitulosDescripcion");
};

ClonarRemitosMaterialesConContratoEvent.prototype.getSpanTipoTituloCodigoClonar = function() {
	return $("#" + this.div.id + "_tipoTituloCodigoClonar");
};

ClonarRemitosMaterialesConContratoEvent.prototype.getSpanNumeroTituloClonar = function() {
	return $("#" + this.div.id + "_numeroTituloClonar");
};

ClonarRemitosMaterialesConContratoEvent.prototype.getSpanProveedorAnteriorClonar = function() {
	return $("#" + this.div.id + "_proveedorAnteriorClonar");
};

ClonarRemitosMaterialesConContratoEvent.prototype.getSpanContratoAnteriorClonar = function() {
	return $("#" + this.div.id + "_contratoAnteriorClonar");
};

ClonarRemitosMaterialesConContratoEvent.prototype.getSpanGrupoAnteriorClonar = function() {
	return $("#" + this.div.id + "_grupoAnteriorClonar");
};

ClonarRemitosMaterialesConContratoEvent.prototype.getACambiarTipoBusquedaTitulo = function() {
	return $("#" + this.div.id + "_cambiarTipoBusquedaTitulo");
};

ClonarRemitosMaterialesConContratoEvent.prototype.getAIrBusquedaDescTitulo = function() {
	return $("#" + this.div.id + "_irBusquedaDescTitulo");
};

ClonarRemitosMaterialesConContratoEvent.prototype.getAIrBusquedaClave = function() {
	return $("#" + this.div.id + "_irBusquedaClave");
};

/* Initialize */
var clonarRemitosMaterialesConContratoEvent = new ClonarRemitosMaterialesConContratoEvent(new DivDefinition('ClonarRemitosMaterialesConContratoEventId'));

