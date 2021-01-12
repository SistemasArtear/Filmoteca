function DesconfirmarSinAmortizacionEvent(div) {
	this.div = div;
};
extend(DesconfirmarSinAmortizacionEvent, Plugin);
DesconfirmarSinAmortizacionEvent.prototype.create = function() {
	var self = desconfirmarSinAmortizacionEvent;
	self.service = new DesconfirmarSinAmortizacionService();
	Component.get("html/desconfirmarSinAmortizacion/desconfirmar.html", desconfirmarSinAmortizacionEvent.draw);
};
DesconfirmarSinAmortizacionEvent.prototype.draw = function(comp) {
	var self = desconfirmarSinAmortizacionEvent;
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
DesconfirmarSinAmortizacionEvent.prototype.aceptarForm = function() {
	var self = desconfirmarSinAmortizacionEvent;
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
DesconfirmarSinAmortizacionEvent.prototype.validForm = function() {
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
DesconfirmarSinAmortizacionEvent.prototype.popupBuscarProgramasCodigo = function(data) {
    situarPopupEvent.reset();
	situarPopupEvent.create("desconfirmarSinAmortizacionBuscarProgramasCodigo.action", 
			function(data) {
				desconfirmarSinAmortizacionEvent.getSelector("programa").val(data.codigo);
			}, 
			{descripcion : "", senial : $("#senialDefaultUsuario").val() }, "programasCodigo");
};
DesconfirmarSinAmortizacionEvent.prototype.popupBuscarProgramasClave = function() {
    situarPopupEvent.reset();
	situarPopupEvent.create("desconfirmarSinAmortizacionBuscarProgramasClave.action", 
			function(data) {
				desconfirmarSinAmortizacionEvent.getSelector("clave").val(data.clave);
			}, 
			{descripcion : ""}, "programasClave");
};
DesconfirmarSinAmortizacionEvent.prototype.drawGrigProgramas = function(data) {
	var self = desconfirmarSinAmortizacionEvent;
	self.getSelector("grid").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Hora','Programa','Descripción','Titulo','Cont','Clave', 'Cap', 'P', 'S', 'F', 'D', 'OR', ''],
		colModel:[ 
			{name:'horaDesde',index:'horaDesde', width:50, sortable:true}, 
			{name:'programa',index:'programa', width:50, sortable:true}, 
			{name:'descripcion',index:'descripcion', width:100, sortable:false}, 
			{name:'titulo',index:'titulo', width:100, sortable:false}, 
			{name:'contrato',index:'contrato', width:20, sortable:false}, 
			{name:'clave',index:'clave', width:50, sortable:true}, 
			{name:'capitulo',index:'capitulo', width:20, sortable:false}, 
			{name:'parte',index:'parte', width:20, sortable:false}, 
			{name:'segmento',index:'segmento', width:20, sortable:false}, 
			{name:'fraccion',index:'fraccion', width:20, sortable:false}, 
			{name:'de',index:'de', width:20, sortable:false}, 
			{name:'orden',index:'orden', width:20, sortable:false}, 
			{name:'desconfirmar',index:'desconfirmar', width:20, aling:'center', sortable:false}
		],
		rowNum: 20,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#DesconfirmarSinAmortizacionEventId_pagerGrid',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Programas',
		gridComplete: function() {
        	var ids = $(this).jqGrid('getDataIDs');
        	for (var i = 0; i < ids.length; i++) {
        		var rowId = ids[i];
				var desconfirmar = "<div onclick='desconfirmarSinAmortizacionEvent.desconfirmar("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-radio-on desconfirmar conTooltip'>Desconfirmar</span></div>";
				$(this).jqGrid('setRowData', rowId, { desconfirmar: desconfirmar });
        	}
	    }
	});
	for ( var i in data) {
		var hora = data[i].horaDesde.toString();
		hora = hora.substring(0, hora.length-2);
		hora = hora.substring(hora.length-4, hora.length-2) + ":" + hora.substring(hora.length-2);
		data[i].horaDesde = hora;
	}
	self.getSelector("grid").clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};
DesconfirmarSinAmortizacionEvent.prototype.desconfirmar = function(rowId) {
	var self = desconfirmarSinAmortizacionEvent;
	self.tituloPrograma =  self.getSelector("grid").jqGrid('getRowData', rowId);
	self.tituloPrograma.senial = $("#senialDefaultUsuario").val();
	self.tituloPrograma.fecha = self.fecha;
	popupConfirmacionEvent.confirmar = function () {
		desconfirmarSinAmortizacionEvent.service.desconfirmar({data : self.tituloPrograma,
			callback : self.aceptarForm});
		popupConfirmacionEvent.close();
    };
    popupConfirmacionEvent.cancelar = function () {
        popupConfirmacionEvent.close();
    };
    popupConfirmacionEvent.create("desconfirmarProgramaPopUp","¿Esta seguro que desea desconfirmar?");
};
var desconfirmarSinAmortizacionEvent = new DesconfirmarSinAmortizacionEvent(new DivDefinition('DesconfirmarSinAmortizacionEventId'));