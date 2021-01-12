function ConfirmarSinAmortizacionEvent(div) {
	this.div = div;
};
extend(ConfirmarSinAmortizacionEvent, Plugin);
ConfirmarSinAmortizacionEvent.prototype.create = function() {
	var self = confirmarSinAmortizacionEvent;
	self.service = new ConfirmarSinAmortizacionService();
	Component.get("html/confirmarSinAmortizacion/confirmar.html", confirmarSinAmortizacionEvent.draw);
};
ConfirmarSinAmortizacionEvent.prototype.draw = function(comp) {
	var self = confirmarSinAmortizacionEvent;
	$("#viewContainer").empty().append(comp.replace(/{{id}}/g, self.div.id));
	//Component.populateSelect(self.getSelector("senial"), seniales, "codigo", "descripcion");
	Datepicker.picker(self.getSelector("fecha"));
	Datepicker.fullYearDatepicker(self.getSelector("fecha"));
	self.getSelector("aceptarFormInicial").button().click(self.aceptarForm);
	self.getSelector("buscarPorPrograma").click(self.popupBuscarProgramasCodigo);
	self.getSelector("buscarPorClave").click(self.popupBuscarProgramasClave);
	self.getSelector("accordion").accordion({
		collapsible: false,
		active: 0,
		autoHeight: false,
		icons: false
	});
};
ConfirmarSinAmortizacionEvent.prototype.aceptarForm = function() {
	var self = confirmarSinAmortizacionEvent;
	if (self.validForm()) {
		self.fecha = self.getSelector("fecha").val();
		self.senial = $("#senialDefaultUsuario").val();
		var data = {
			data : self.getSelector("formInicial").serialize() + '&titulosProgramasRequest.senial=' + self.senial,
			callback : function(data) {
				self.drawGrigProgramas(data);
			}
		};
		self.service.getProgramas(data);
		self.getSelector("filtro").show();
	}
	
};
ConfirmarSinAmortizacionEvent.prototype.validForm = function() {
	var fecha = this.getSelector("fecha");
	/*var senial = this.getSelector("senial");
	if (Validator.isEmpty(senial)) {
		Validator.focus(senial);
		return false;
	}*/
	if (Validator.isEmpty(fecha)) {
		Validator.focus(fecha);
		return false;
	}
	var hoy = new Date();
	hoy.setHours(0,0,0,0);
	if (fecha.datepicker("getDate") > hoy) {
		Validator.focus(fecha, "La fecha no puede ser mayor a la de hoy.");
		return false;
	}
	return true;
};
ConfirmarSinAmortizacionEvent.prototype.popupBuscarProgramasCodigo = function(data) {
    situarPopupEvent.reset();
	situarPopupEvent.create("confirmarSinAmortizacionBuscarProgramasCodigo.action", 
			function(data) {
				confirmarSinAmortizacionEvent.getSelector("programa").val(data.codigo);
			}, 
			{descripcion : "", senial : $("#senialDefaultUsuario").val() }, "programasCodigo");
};
ConfirmarSinAmortizacionEvent.prototype.popupBuscarProgramasClave = function() {
    situarPopupEvent.reset();
	situarPopupEvent.create("confirmarSinAmortizacionBuscarProgramasClave.action", 
			function(data) {
				confirmarSinAmortizacionEvent.getSelector("clave").val(data.clave);
			}, 
			{descripcion : ""}, "programasClave");
};
ConfirmarSinAmortizacionEvent.prototype.drawGrigProgramas = function(data) {
	var self = confirmarSinAmortizacionEvent;
	self.getSelector("grid").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Hora','Programa','Descripción','Titulo','Clave', 'Cap', 'P', 'S', 'F', 'D', 'OR', ''],
		colModel:[ 
			{name:'hora',index:'hora', width:40, sortable:true}, 
			{name:'programa',index:'programa', width:50, sortable:false, align:'right'}, 
			{name:'descripcion',index:'descripcion', width:100, sortable:false}, 
			{name:'titulo',index:'titulo', width:100, sortable:false}, 
			{name:'clave',index:'clave', width:50, sortable:true}, 
			{name:'capitulo',index:'capitulo', width:20, sortable:false, align:'right'}, 
			{name:'parte',index:'parte', width:20, sortable:false}, 
			{name:'segmento',index:'segmento', width:20, sortable:false}, 
			{name:'fraccion',index:'fraccion', width:20, sortable:false}, 
			{name:'de',index:'de', width:20, sortable:false}, 
			{name:'orden',index:'orden', width:20, sortable:false}, 
			{name:'confirmar',index:'confirmar', width:20, aling:'center', sortable:false}
		],
		rowNum: 20,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#ConfirmarSinAmortizacionEventId_pagerGrid',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Programas',
		gridComplete : function() {
        	var ids = $(this).jqGrid('getDataIDs');
        	for (var i = 0; i < ids.length; i++) {
        		var rowId = ids[i];
        		self.tituloPrograma =  $(this).jqGrid('getRowData', rowId);
				var confirmar = "<div onclick='confirmarSinAmortizacionEvent.confirmar("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-radio-on desconfirmar conTooltip'>Confirmar</span></div>";
				
				var row = $(this).jqGrid('getRowData', rowId);
				if (row.capitulo === "0") {
				    $(this).jqGrid('setRowData', rowId, { capitulo: "" });
				}
                if (row.parte === "0") {
                    $(this).jqGrid('setRowData', rowId, { parte: "" });
                }
                if (row.segmento === "0") {
                    $(this).jqGrid('setRowData', rowId, { segmento: "" });
                }

				if ((row.titulo.trim()).length > 25) {
                    $(this).jqGrid('setRowData', rowId, { titulo: (row.titulo.trim()).substring(0, 24)+"..." });
                }
                if ((row.descripcion.trim()).length > 25) {
                    $(this).jqGrid('setRowData', rowId, { descripcion: (row.descripcion.trim()).substring(0, 24)+"..." });
                }
				$(this).jqGrid('setRowData', rowId, { confirmar: confirmar });
        	}
	    }
	});
	for ( var i in data) {
		var horaDesde = data[i].hora.toString();
		horaDesde = horaDesde.substring(0, 2) + ":" + horaDesde.substring(2,4);
		data[i].hora = horaDesde;
	}
	self.getSelector("grid").clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};
ConfirmarSinAmortizacionEvent.prototype.confirmar = function(rowId) {
	var self = confirmarSinAmortizacionEvent;
    self.tituloPrograma =  self.getSelector("grid").jqGrid('getRowData', rowId);
	self.tituloPrograma.senial = $("#senialDefaultUsuario").val();
	self.tituloPrograma.fecha = self.fecha;
	popupConfirmacionEvent.confirmar = function () {
		confirmarSinAmortizacionEvent.service.confirmar(
		        {data: self.tituloPrograma,
		         callback: self.aceptarForm});
		popupConfirmacionEvent.close();
    };
    popupConfirmacionEvent.cancelar = function () {
        popupConfirmacionEvent.close();
    };
    popupConfirmacionEvent.create("confirmarProgramaPopUp","¿Esta seguro que desea confirmar?");
};
var confirmarSinAmortizacionEvent = new ConfirmarSinAmortizacionEvent(new DivDefinition('ConfirmarSinAmortizacionEventId'));