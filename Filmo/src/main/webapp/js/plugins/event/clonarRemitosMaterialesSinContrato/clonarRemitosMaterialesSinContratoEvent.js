
/***********************************************************************************		
 ***************	 Clonar remitos de materiales sin contrato		****************
 ***********************************************************************************/

ClonarRemitosMaterialesSinContratoEvent.BUSQUEDA_CLAVE = "CLAVE";
ClonarRemitosMaterialesSinContratoEvent.BUSQUEDA_DESCRIPCION = "DESCRIPCION";
ClonarRemitosMaterialesSinContratoEvent.CASTELLANO = "castellano";
ClonarRemitosMaterialesSinContratoEvent.ORIGINAL = "original";

function ClonarRemitosMaterialesSinContratoEvent(div) {
	this.div = div;
	this.busqueda = ClonarRemitosMaterialesSinContratoEvent.BUSQUEDA_CLAVE;
	this.tipoBusquedaTitulo = ClonarRemitosMaterialesSinContratoEvent.CASTELLANO;
	this.tipoTituloClonar = null;
	this.numeroTituloClonar = null;
	this.idProveedorAnteriorClonar = null;
	this.idProveedorNuevoClonar = null;
};

extend(ClonarRemitosMaterialesSinContratoEvent, Plugin);

ClonarRemitosMaterialesSinContratoEvent.prototype.create = function() {
	var self = clonarRemitosMaterialesSinContratoEvent;
	self.service = new ClonarRemitosMaterialesSinContratoService();
	Component.get("html/clonarRemitosMaterialesSinContrato/clonarRemitosMaterialesSinContrato.html", clonarRemitosMaterialesSinContratoEvent.draw);
};

ClonarRemitosMaterialesSinContratoEvent.prototype.draw = function(comp) {
	var self = clonarRemitosMaterialesSinContratoEvent;
	var pirulo =  clonarRemitosMaterialesSinContratoEvent;
	$("#viewContainer").empty().append(comp.replace(/{{id}}/g, self.div.id)
			.replace(/{{tipoBusquedaTitulo}}/g, self.tipoBusquedaTitulo));
	Accordion.getInstance(self.getSelector("accordionFormCabecera"));
	Accordion.getInstance(self.getSelector("accordionFormClonar"));
	
	self.getSelector("divFormSeccionClonar").attr("Style", "display: none"); // seccion "Clonar" inicialmente oculta
	
	self.busqueda = ClonarRemitosMaterialesSinContratoEvent.BUSQUEDA_CLAVE;
	self.tipoBusquedaTitulo = ClonarRemitosMaterialesSinContratoEvent.CASTELLANO
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
	self.getSelector("busquedaTitulosDescripcion").keypress(function (event) {
		if (event.which == 13) {
			event.preventDefault();
			self.buscarRemitos();
			return;
		}
	});
};


ClonarRemitosMaterialesSinContratoEvent.prototype.cambiarTipoBusquedaTitulo = function() {
	var self = clonarRemitosMaterialesSinContratoEvent;
	if (self.tipoBusquedaTitulo === TrabajarConTitulosEvent.CASTELLANO) {
		self.tipoBusquedaTitulo = TrabajarConTitulosEvent.ORIGINAL;
	}else{
		self.tipoBusquedaTitulo = TrabajarConTitulosEvent.CASTELLANO;
	}

	self.getSelector("tipoBusquedaTitulo").text(self.tipoBusquedaTitulo);
	self.limpiarFiltrosbuscarRemitos();
};

ClonarRemitosMaterialesSinContratoEvent.prototype.mostrarBusquedaClave = function() {
	var self = clonarRemitosMaterialesSinContratoEvent;
	self.busqueda = ClonarRemitosMaterialesSinContratoEvent.BUSQUEDA_CLAVE;
	self.getSelector("camposBusquedaClave").attr("Style", "display: ");	
	self.getSelector("camposBusquedaDescTitulo").attr("Style", "display: none");
	self.limpiarFiltrosbuscarRemitos();
};

ClonarRemitosMaterialesSinContratoEvent.prototype.mostrarBusquedaDescTitulo = function() {
	var self = clonarRemitosMaterialesSinContratoEvent;
	self.busqueda = ClonarRemitosMaterialesSinContratoEvent.BUSQUEDA_DESCRIPCION;
	self.getSelector("camposBusquedaClave").attr("Style", "display: none");	
	self.getSelector("camposBusquedaDescTitulo").attr("Style", "display: ");
	self.limpiarFiltrosbuscarRemitos();
};

ClonarRemitosMaterialesSinContratoEvent.prototype.limpiarFiltrosbuscarRemitos = function() {
	var self = clonarRemitosMaterialesSinContratoEvent;
	
	//  Limpia seccion filtros:
	self.getSelector("tipoTituloCodigo").val(null);
	self.getSelector("tipoTituloDesc").text("");
	self.getSelector("numeroTitulo").val(null);
	self.getSelector("busquedaTitulosDescripcion").val(null);
	
	// Limpia seccion "Clonar":
	self.limpiarSeccionClonar();
	
	// Vuelve a ocultar la seccion "Clonar":
	self.getSelector("divFormSeccionClonar").attr("Style", "display: none");
	
	// Limpia la grilla:
	self.getGridTitulos().clearGridData();
};

ClonarRemitosMaterialesSinContratoEvent.prototype.limpiarSeccionClonar = function() {
	// Limpia seccion "Clonar":
	var self = clonarRemitosMaterialesSinContratoEvent;
	self.tipoTituloClonar = null;
	self.numeroTituloClonar = null;
	self.idProveedorAnteriorClonar = null;
	self.idProveedorNuevoClonar = null;
	self.getSpanTipoTituloCodigoClonar().html("");
	self.getSpanNumeroTituloClonar().html("");
	self.getSpanProveedorAnteriorClonar().html("");
	self.getInputRazonSocialDistribuidor().val("");
	self.getInputCodigoDistribuidor().val("");
};

ClonarRemitosMaterialesSinContratoEvent.prototype.buscarRemitos = function() {
	var self = clonarRemitosMaterialesSinContratoEvent;
	var data = {};
	
	if(self.busqueda === ClonarRemitosMaterialesSinContratoEvent.BUSQUEDA_CLAVE) {
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
		
		data = {
				tipoTitulo : self.getSelector("tipoTituloCodigo").val(),
				numeroTitulo : self.getSelector("numeroTitulo").val(),
				tituloOriginal : null,
				tituloCastellano : null
			};
		
	} else if (self.busqueda === ClonarRemitosMaterialesSinContratoEvent.BUSQUEDA_DESCRIPCION) {
		if (Validator.isEmpty(self.getSelector("busquedaTitulosDescripcion") || !self.getSelector("busquedaTitulosDescripcion"))) {
			Validator.focus(self.getSelector("busquedaTitulosDescripcion"), "Debe ingresar el texto a buscar");
			return;
		}
		
		if(self.tipoBusquedaTitulo === ClonarRemitosMaterialesSinContratoEvent.CASTELLANO) {
			data = {
					tipoTitulo : null,
					numeroTitulo : null,
					tituloOriginal : null,
					tituloCastellano : self.getSelector("busquedaTitulosDescripcion").val()
				}
		} else {
			data = {
					tipoTitulo : null,
					numeroTitulo : null,
					tituloOriginal : self.getSelector("busquedaTitulosDescripcion").val(),
					tituloCastellano : null
				}			
		}
	}
	
	var isValid = self.service.buscarRemitos(data, $.proxy(self.responseTitulos,self));
};

ClonarRemitosMaterialesSinContratoEvent.prototype.clonarRemitos = function() {
	var self = clonarRemitosMaterialesSinContratoEvent;
	
	if (self.tipoTituloClonar == null  || self.tipoTituloClonar == ""
		|| self.numeroTituloClonar == null  || self.numeroTituloClonar == "" 
			|| self.idProveedorAnteriorClonar == null  || self.idProveedorAnteriorClonar == "") {
		MESSAGE.alert("Debe seleccionar un Tipo Título  - Nro. Título - Proveedor Anterior para clonar");
		return;
	}
	
	if (Validator.isEmpty(self.getInputCodigoDistribuidor() || !self.getInputCodigoDistribuidor())) {
		Validator.focus(self.getInputCodigoDistribuidor(), "Debe seleccionar el nuevo proveedor para clonar");
		return;
	} else if ( isNaN(self.getInputCodigoDistribuidor().val()) || !Number.isInteger(parseFloat(self.getInputCodigoDistribuidor().val()))) {
		Validator.focus(self.getInputCodigoDistribuidor(), "El código del nuevo proveedor para clonar debe ser numérico");
		return;
	}
	
	self.idProveedorNuevoClonar = self.getInputCodigoDistribuidor().val();
	
	var data = {
			tipoTitulo : self.tipoTituloClonar,
			numeroTitulo : self.numeroTituloClonar,
			idProveedorAnterior : self.idProveedorAnteriorClonar,
			idProveedorNuevo : self.idProveedorNuevoClonar
		};
		
	var isValid = self.service.clonarRemitos(data, $.proxy(self.responseClonar,self));
	
};

ClonarRemitosMaterialesSinContratoEvent.prototype.responseClonar = function() {
	var self = clonarRemitosMaterialesSinContratoEvent;
	self.limpiarSeccionClonar();
	self.buscarRemitos();
	MESSAGE.alert("Ha finalizado la clonación de remitos");
};

ClonarRemitosMaterialesSinContratoEvent.prototype.popupTipoTitulo = function() {
    situarPopupEvent.reset();
	situarPopupEvent.nombreSituar = "Situar Tipo Titulos";
	situarPopupEvent.create("obtenerTiposDeTitulos.action", clonarRemitosMaterialesSinContratoEvent.tipoTituloSelected, {descripcion : ""}, "tiposDeTitulos");
};

ClonarRemitosMaterialesSinContratoEvent.prototype.tipoTituloSelected = function(row) {
	$("#"+clonarRemitosMaterialesSinContratoEvent.div.id+"_tipoTituloCodigo").val(row.codigo);
	$("#"+clonarRemitosMaterialesSinContratoEvent.div.id+"_tipoTituloDesc").text(row.descripcion);
};

ClonarRemitosMaterialesSinContratoEvent.prototype.responseTitulos = function(data) {
	var self = clonarRemitosMaterialesSinContratoEvent;	
	this.drawGrigTitulos(data);
};

ClonarRemitosMaterialesSinContratoEvent.prototype.drawPopupSituarDistribuidoresPorCodigo = function() {
    var distribuidor = $("#ClonarRemitosMaterialesSinContratoEventId_codigoDistribuidor").val();
	var data = {codigoDistribuidor: distribuidor};
	situarPopupEvent.reset();
	situarPopupEvent.create("buscarDistribuidoresParaMateriales.action", clonarRemitosMaterialesSinContratoEvent.distribuidorSelected, data, "distribuidores");
	situarPopupEvent.nombreSituar = "Distribuidores por código";
	situarPopupEvent.doAfterDrawGrid = function() {
	    $("#buscarDistribuidoresParaMateriales_inputBusquedaSituar").val(distribuidor);
		situarPopupEvent.getGrid().setColProp('codigo', {sorttype:'number', align:'right'}).trigger("reloadGrid");
		situarPopupEvent.getGrid().setColProp('razonSocial', {sorttype:'text', align:'left'}).trigger("reloadGrid");
	};
};

ClonarRemitosMaterialesSinContratoEvent.prototype.drawPopupSituarDistribuidoresPorRazonSocial = function() {
    var distribuidor = $("#ClonarRemitosMaterialesSinContratoEventId_razonSocialDistribuidor").val();
	var data = {razonSocialDistribuidor : distribuidor};
	situarPopupEvent.reset();
	situarPopupEvent.create("buscarDistribuidoresParaMateriales.action", clonarRemitosMaterialesSinContratoEvent.distribuidorSelected, data, "distribuidores");
	situarPopupEvent.nombreSituar = "Distribuidores por razón social";
	situarPopupEvent.doAfterDrawGrid = function() {
        $("#buscarDistribuidoresParaMateriales_inputBusquedaSituar").val(distribuidor);
		situarPopupEvent.getGrid().sortGrid('razonSocial', true);
		situarPopupEvent.getGrid().setColProp('codigo', {sorttype:'number', align:'right'}).trigger("reloadGrid");
		situarPopupEvent.getGrid().setColProp('razonSocial', {sorttype:'text', align:'left'}).trigger("reloadGrid");
	};
};

ClonarRemitosMaterialesSinContratoEvent.prototype.distribuidorSelected = function(distribuidor) {
	var self = clonarRemitosMaterialesSinContratoEvent;
	self.getInputCodigoDistribuidor().val(distribuidor.codigo);
	self.getInputRazonSocialDistribuidor().val(distribuidor.razonSocial);
};

ClonarRemitosMaterialesSinContratoEvent.prototype.drawGrigTitulos = function(data) {
	var self = clonarRemitosMaterialesSinContratoEvent;
	this.getGridTitulos().jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Nro Remito','Tipo Titulo', 'Nro. Titulo', 'Titulo Catellano', 'Titulo Original', 'Codigo Proveedor', 'Proveedor'],
		colModel:[ 
			{name:'nroRemito',index:'nroRemito', width:150, align: "center", sortable:true}, 
			{name:'tipoTitulo',index:'tipoTitulo', width:100, align: "center", sortable:true}, 
			{name:'nroTitulo',index:'nroTitulo', width:100, align: "center", sortable:true},
			{name:'tituloCastellano',index:'titulo', width:300, sortable:true},
			{name:'tituloOriginal',index:'titulo', width:300, sortable:true},
			{name:'idProveedor',index:'idProveedor', width:150, align: "center", sortable:true},
			{name:'razonSocialProveedor',index:'razonSocialProveedor', width:300, sortable:true}
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#ClonarRemitosMaterialesSinContratoEventId_pagerGridTitulos',
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
			self.getSpanTipoTituloCodigoClonar().html(self.tipoTituloClonar);
			self.getSpanNumeroTituloClonar().html(self.numeroTituloClonar);
			self.getSpanProveedorAnteriorClonar().html(self.idProveedorAnteriorClonar + "  " + $(this).jqGrid('getCell', id, 'razonSocialProveedor'));
			self.getSelector("divFormSeccionClonar").attr("Style", "display: "); // Muestra seccion "Clonar"
			self.getInputRazonSocialDistribuidor().focus();
		}
	});
	this.getGridTitulos().clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};


ClonarRemitosMaterialesSinContratoEvent.prototype.getButtonPopupTipoTitulo = function() {
	return $("#" + this.div.id + "_popupTipoTitulo");
};

ClonarRemitosMaterialesSinContratoEvent.prototype.getGridTitulos = function() {
	return $("#" + this.div.id + "_gridTitulos");
};

ClonarRemitosMaterialesSinContratoEvent.prototype.getButtonPopupDistribuidorPorCodigo = function() {
	return $("#" + this.div.id + "_popupDistribuidorPorCodigo");
};

ClonarRemitosMaterialesSinContratoEvent.prototype.getButtonPopupDistribuidorPorRazonSocial = function() {
	return $("#" + this.div.id + "_popupDistribuidorPorRazonSocial");
};

ClonarRemitosMaterialesSinContratoEvent.prototype.getInputRazonSocialDistribuidor = function() {
	return $("#" + this.div.id + "_razonSocialDistribuidor");
};

ClonarRemitosMaterialesSinContratoEvent.prototype.getInputCodigoDistribuidor = function() {
	return $("#" + this.div.id + "_codigoDistribuidor");
};

ClonarRemitosMaterialesSinContratoEvent.prototype.getInputBusquedaTitulosDescripcion = function() {
	return $("#" + this.div.id + "_busquedaTitulosDescripcion");
};

ClonarRemitosMaterialesSinContratoEvent.prototype.getSpanTipoTituloCodigoClonar = function() {
	return $("#" + this.div.id + "_tipoTituloCodigoClonar");
};

ClonarRemitosMaterialesSinContratoEvent.prototype.getSpanNumeroTituloClonar = function() {
	return $("#" + this.div.id + "_numeroTituloClonar");
};

ClonarRemitosMaterialesSinContratoEvent.prototype.getSpanProveedorAnteriorClonar = function() {
	return $("#" + this.div.id + "_proveedorAnteriorClonar");
};

ClonarRemitosMaterialesSinContratoEvent.prototype.getACambiarTipoBusquedaTitulo = function() {
	return $("#" + this.div.id + "_cambiarTipoBusquedaTitulo");
};

ClonarRemitosMaterialesSinContratoEvent.prototype.getAIrBusquedaDescTitulo = function() {
	return $("#" + this.div.id + "_irBusquedaDescTitulo");
};

ClonarRemitosMaterialesSinContratoEvent.prototype.getAIrBusquedaClave = function() {
	return $("#" + this.div.id + "_irBusquedaClave");
};

/* Initialize */
var clonarRemitosMaterialesSinContratoEvent = new ClonarRemitosMaterialesSinContratoEvent(new DivDefinition('ClonarRemitosMaterialesSinContratoEventId'));

