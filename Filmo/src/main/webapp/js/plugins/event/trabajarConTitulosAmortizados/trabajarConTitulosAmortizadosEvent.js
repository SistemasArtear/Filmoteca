TrabajarConTitulosAmortizadosEvent.CASTELLANO = "castellano";
TrabajarConTitulosAmortizadosEvent.ORIGINAL = "original";
TrabajarConTitulosAmortizadosEvent.ADD = "ALTA";
TrabajarConTitulosAmortizadosEvent.UPDATE = "MODIFICACION";

function TrabajarConTitulosAmortizadosEvent(div) {
	this.div = div;
	this.tipoTitulo = TrabajarConTitulosAmortizadosEvent.CASTELLANO;
};
extend(TrabajarConTitulosAmortizadosEvent, Plugin);

TrabajarConTitulosAmortizadosEvent.prototype.create = function() {
	var self = trabajarConTitulosAmortizadosEvent;
	self.service = new TrabajarConTitulosService();
	Component.get("html/trabajarConTitulosAmortizados/listadoTitulosAmortizados.html", trabajarConTitulosAmortizadosEvent.draw);
};

TrabajarConTitulosAmortizadosEvent.prototype.draw = function(comp) {
	var self = trabajarConTitulosAmortizadosEvent;
	$("#viewContainer").empty().append(comp.replace(/{{id}}/g, self.div.id)
			.replace(/{{tipoBusquedaTitulo}}/g, self.tipoTitulo));
	
	self.getAccordionListadoTitulos().accordion({
		collapsible: false,
		active: 0,
		autoHeight: false,
		icons: false
	});
	
	self.getACambiarTipoBusqueda().click(self.cambiarTipoBusqueda);
	self.getButtonBuscarTitulosPorDesc().button().click(self.buscarTitulosAmortizadosPorDescripcion);
	self.getButtonBuscarTitulosPorCodigo().button().click(self.buscarTitulosAmortizadosPorCodigo);
	self.getInputBusquedaTitulosDescripcion().keypress(function (event) {
		if (event.which == 13) {
			event.preventDefault();
			self.buscarTitulosAmortizadosPorDescripcion();
			return;
		}
	});
	self.getInputBusquedaTituloCodigo().keypress(function (event) {
		if (event.which == 13) {
			event.preventDefault();
			self.buscarTitulosAmortizadosPorCodigo();
			return;
		}
	});
};

TrabajarConTitulosAmortizadosEvent.prototype.drawGrigTitulos = function(data) {
    this.getGridTitulos().jqGrid({
        height: 'auto',
        autowidth: true,
        datatype: 'local',
        colNames:['Clave','Titulo','Contrato','Distribuidor','PER','REC', 'por Amortizar', ""],
        colModel:[ 
            {name:'clave',index:'clave', width:50, sortable:false}, 
            {name:'titulo',index:'titulo', width:200, sortable:false}, 
            {name:'contrato',index:'contrato', width:30, sortable:false}, 
            {name:'distribuidor',index:'distribuidor', width:200, sortable:false}, 
            {name:'per',index:'per', width:30, sortable:false}, 
            {name:'rec',index:'rec', width:30, sortable:false}, 
            {name:'porAmortizar',index:'porAmortizar', width:30, aling:'center', sortable:false},
            {name:'activarTitulo',index:'activarTitulo', width:30, aling:'center', sortable:false}
        ],
        rowNum: 20,
        rowList:[10,20,30],
        scrollOffset: 0,
        pager: '#TrabajarConTitulosAmortizadosEventId_pagerGridTitulos',
        viewrecords: true, 
        loadonce: true,
        editurl: 'clientArray', 
        caption: 'Titulos',
        gridComplete: function() {
            var ids = $(this).jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var rowId = ids[i];
                var activar = "<div onclick='trabajarConTitulosAmortizadosEvent.activarTitulo("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-pencil modificar conTooltip'>Modificar</span></div>";
                $(this).jqGrid('setRowData', rowId, { activarTitulo: activar });
            }
        }
    });
    this.getGridTitulos().clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};

TrabajarConTitulosAmortizadosEvent.prototype.drawPopupActivarTitulo = function(data) {
    var self = trabajarConTitulosAmortizadosEvent;
    self.getDiv().append("<div id='"+self.div.id+"_activarTitulo'><p>El Titulo-Contrato se activara nuevamente. Confirma</div>");
    self.getPopupActivarTituto().dialog({
        title : 'Activar Titulo',
        width: 600,
        show: 'blind',
        hide: 'blind',
        modal: true,
        autoOpen: false,
        buttons: {
            "Activar": function() {
                self.service.activarTitulo(data);
                $( this ).dialog( "close" );
            },
            "Salir": function() {
                $( this ).dialog( "close" );
            }
        }
    });
};

TrabajarConTitulosAmortizadosEvent.prototype.abrirPopupActivarTitulo = function() {
    this.getPopupActivarTituto().dialog("open");
};

TrabajarConTitulosAmortizadosEvent.prototype.cambiarTipoBusqueda = function() {
	var self = trabajarConTitulosAmortizadosEvent;
	if (self.tipoTitulo === TrabajarConTitulosAmortizadosEvent.CASTELLANO) {
		self.tipoTitulo = TrabajarConTitulosAmortizadosEvent.ORIGINAL;
	}else{
		self.tipoTitulo = TrabajarConTitulosAmortizadosEvent.CASTELLANO;
	}
	$("#"+self.div.id+"_tipoBusqueda").text(self.tipoTitulo);
	self.getInputBusquedaTitulosDescripcion().val("");
	self.getInputBusquedaTituloCodigo().val("");
	self.getGridTitulos().clearGridData();
};  

TrabajarConTitulosAmortizadosEvent.prototype.buscarTitulosAmortizadosPorDescripcion = function() {
	var self = trabajarConTitulosAmortizadosEvent;
	var descripcion = self.getInputBusquedaTitulosDescripcion().val();
	if (!descripcion || descripcion === "") {
		return;
	}
	var data = {
		descripcion : descripcion,
		senial: $("#senialDefaultUsuario").val()
	};
	self.service.getTitulosAmortizadosPorDescripcion(data, self.tipoTitulo);
};
TrabajarConTitulosAmortizadosEvent.prototype.buscarTitulosAmortizadosPorCodigo = function() {
    var self = trabajarConTitulosAmortizadosEvent;
    var codigo = self.getInputBusquedaTituloCodigo().val();
    if (!codigo || codigo === "") {
        return;
    }
    var data = {
        clave : codigo,
        senial: $("#senialDefaultUsuario").val()
    };
    self.service.getTitulosAmortizadosPorCodigo(data, self.tipoTitulo);
};
TrabajarConTitulosAmortizadosEvent.prototype.activarTitulo = function(rowId) {
    var self = trabajarConTitulosAmortizadosEvent;
    var claveDelTituloQueSeraActivado = self.getGridTitulos().jqGrid('getRowData', rowId).clave;
    var fueContabilizado = self.service.fueContabilizado({codigo : claveDelTituloQueSeraActivado});
};
TrabajarConTitulosAmortizadosEvent.prototype.responseTitulos = function(data) {
	this.drawGrigTitulos(data);
};

TrabajarConTitulosAmortizadosEvent.prototype.getAccordionListadoTitulos = function() {
	return $("#" + this.div.id + "_accordionListadoTitulos");
};
TrabajarConTitulosAmortizadosEvent.prototype.getButtonBuscarTitulosPorDesc = function() {
	return $("#" + this.div.id + "_buscarTitulosPorDescripcion");
};
TrabajarConTitulosAmortizadosEvent.prototype.getButtonBuscarTitulosPorCodigo = function() {
	return $("#" + this.div.id + "_buscarTituloPorCodigo");
};
TrabajarConTitulosAmortizadosEvent.prototype.getInputBusquedaTitulosDescripcion = function() {
	return $("#" + this.div.id + "_inputBusquedaTitulosDescripcion");
};
TrabajarConTitulosAmortizadosEvent.prototype.getPopupActivarTituto = function() {
    return $("#" + this.div.id + "_activarTitulo");
};
TrabajarConTitulosAmortizadosEvent.prototype.getInputBusquedaTituloCodigo = function() {
	return $("#" + this.div.id + "_inputBusquedaTituloCodigo");
};
TrabajarConTitulosAmortizadosEvent.prototype.getGridTitulos = function() {
	return $("#" + this.div.id + "_gridTitulos");
};
TrabajarConTitulosAmortizadosEvent.prototype.getACambiarTipoBusqueda = function() {
	return $("#" + this.div.id + "_cambiarTipoTitulo");
};
var trabajarConTitulosAmortizadosEvent = new TrabajarConTitulosAmortizadosEvent(new DivDefinition('TrabajarConTitulosAmortizadosEventId'));