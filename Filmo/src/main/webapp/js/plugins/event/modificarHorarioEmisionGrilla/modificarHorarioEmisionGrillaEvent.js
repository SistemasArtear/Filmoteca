function ModificarHorarioEmisionGrillaEvent(div) {
	this.div = div;
};
extend(ModificarHorarioEmisionGrillaEvent, Plugin);
ModificarHorarioEmisionGrillaEvent.prototype.create = function() {
	var self = modificarHorarioEmisionGrillaEvent;
	self.service = new ModificarHorarioEmisionGrillaService();
	Component.get("html/modificarHorarioEmisionGrilla/horarioEmisionGrilla.html", modificarHorarioEmisionGrillaEvent.draw);
};

ModificarHorarioEmisionGrillaEvent.prototype.draw = function(comp) {
	var self = modificarHorarioEmisionGrillaEvent;
	$("#viewContainer").empty().append(comp.replace(/{{id}}/g, self.div.id));
	//Component.populateSelect(self.getSelector("senial"), seniales, "codigo", "descripcion");
	Accordion.getInstance(self.getAcordionFormInicial());
	Accordion.getInstance(self.getAccordionFormInicialEmisionGrilla());
	Datepicker.picker(self.getInputFechaExhibicion());
	self.getButtonAceptarFormInicial().button().click(self.aceptarFormInicial);
	self.getButtonPopupProgramas().button().click(self.popupProgramas);
	self.getButtonAceptarFormNuevoHorario().button().click(self.aceptarGuardarHorario);
	self.getButtonVolverAlFormInicial().button().click(self.volverAlFormInicial);
};
ModificarHorarioEmisionGrillaEvent.prototype.popupProgramas = function() {
	/*var senial = modificarHorarioEmisionGrillaEvent.getSelector("senial");
	if (Validator.isEmpty(senial)) {
		Validator.focus(senial);
		return;
	}*/
	var data = {descripcion : "", senial : $("#senialDefaultUsuario").val()};
	situarPopupEvent.reset();
	situarPopupEvent.create("buscarProgramasPorDescripcion.action", 
			modificarHorarioEmisionGrillaEvent.programaSeleccionado, 
			data, "programas");
};
ModificarHorarioEmisionGrillaEvent.prototype.programaSeleccionado = function(data) {
	modificarHorarioEmisionGrillaEvent.getInputPrograma().val(data.codigo);
	$("#"+modificarHorarioEmisionGrillaEvent.div.id+"_programaDesc").text(data.descripcion);
};
ModificarHorarioEmisionGrillaEvent.prototype.aceptarFormInicial = function() {
	var self = modificarHorarioEmisionGrillaEvent;
	if (self.validForm()) {
		var data = self.getFormInicial().serialize();
		data = data + "&senial=" + $("#senialDefaultUsuario").val();
		self.service.getHorarioEmisionGrilla(data);
	}
};
ModificarHorarioEmisionGrillaEvent.prototype.validForm = function() {
	var fechaExhibision = this.getInputFechaExhibicion();
	var programa = this.getInputPrograma();
	
	/*if (Validator.isEmpty(senial)) {
		Validator.focus(senial);
		return false;
	}else */if (Validator.isEmpty(fechaExhibision)) {
		Validator.focus(fechaExhibision);
		return false;
	} else if (this.getInputFechaExhibicion().datepicker("getDate") <= new Date()) {
		Validator.focus(fechaExhibision, "La Fecha de Exhibici&oacute;n debe ser mayor al d&iacute;a actual ");
		return false;
	} else if (Validator.isEmpty(programa)) {
		Validator.focus(programa);
		return false;
	}
	return true;
};

ModificarHorarioEmisionGrillaEvent.prototype.responseHorario = function(data) {
	this.getDivHorario().show();
	this.getInputDesde().unmask();
	this.getInputHasta().unmask();
	this.getInputDesde().val(this.formatHorario(data.desde));
	this.getInputHasta().val(this.formatHorario(data.hasta));
	this.getInputDesde().mask("?99:99");
	this.getInputHasta().mask("?99:99");
	$("#"+this.div.id+"_programa").text(this.getInputPrograma().val() + " " + $("#"+this.div.id+"_programaDesc").text());
	$("#"+this.div.id+"_fecha").text(data.fecha + " desde " + this.getInputDesde().val() + " hasta " + this.getInputHasta().val());
};

ModificarHorarioEmisionGrillaEvent.prototype.formatHorario = function(h) {
	var horario = h.toString();
	var length = horario.length;
	if (length < 6) {
		if (length < 5) {
			if (length < 4) {
				horario = "000" + horario;
			}else{
				horario = "00" + horario;
			}
		}else{
			horario = "0" + horario;
		}
	}
	return horario;
};

ModificarHorarioEmisionGrillaEvent.prototype.volverAlFormInicial = function(data) {
	modificarHorarioEmisionGrillaEvent.getDivHorario().hide();
};

ModificarHorarioEmisionGrillaEvent.prototype.aceptarGuardarHorario = function(data) {
	var self = modificarHorarioEmisionGrillaEvent;
	var data = "desde=" + self.getInputDesde().val().replace(/:/g, "") + "00" +
	"&hasta=" + self.getInputHasta().val().replace(/:/g, "") + "00" +
	"&fecha=" + self.getInputFechaExhibicion().val() +
	"&codPrograma=" + self.getInputPrograma().val() +
	"&senial=" + $("#senialDefaultUsuario").val();
	self.service.guardarHorarioEmisionGrilla(data);
};

ModificarHorarioEmisionGrillaEvent.prototype.getAcordionFormInicial = function() {
	return $("#" + this.div.id + "_accordionFormInicial");
};
ModificarHorarioEmisionGrillaEvent.prototype.getAccordionFormInicialEmisionGrilla = function() {
	return $("#" + this.div.id + "_accordionFormInicialEmisionGrilla");
};
ModificarHorarioEmisionGrillaEvent.prototype.getInputPrograma = function() {
	return $("#" + this.div.id + "_programaBusqueda");
};
ModificarHorarioEmisionGrillaEvent.prototype.getInputFechaExhibicion = function() {
	return $("#" + this.div.id + "_fechaExhibicionBusqueda");
};
ModificarHorarioEmisionGrillaEvent.prototype.getButtonAceptarFormInicial = function() {
	return $("#" + this.div.id + "_aceptarFormInicial");
};
ModificarHorarioEmisionGrillaEvent.prototype.getFormInicial = function() {
	return $("#" + this.div.id + "_formInicial");
};
ModificarHorarioEmisionGrillaEvent.prototype.getDivFormInicial = function() {
	return $("#" + this.div.id + "_divFormInicial");
};
ModificarHorarioEmisionGrillaEvent.prototype.getDivHorario = function() {
	return $("#" + this.div.id + "_horarioEmisionGrilla");
};
ModificarHorarioEmisionGrillaEvent.prototype.getButtonPopupProgramas = function() {
	return $("#" + this.div.id + "_popupProgramas");
};
ModificarHorarioEmisionGrillaEvent.prototype.getButtonVolverAlFormInicial = function() {
	return $("#" + this.div.id + "_volverAlFormInicial");
};
ModificarHorarioEmisionGrillaEvent.prototype.getButtonAceptarFormNuevoHorario = function() {
	return $("#" + this.div.id + "_aceptarFormNuevoHorario");
};
ModificarHorarioEmisionGrillaEvent.prototype.getInputDesde = function() {
	return $("#" + this.div.id + "_desde");
};
ModificarHorarioEmisionGrillaEvent.prototype.getInputHasta = function() {
	return $("#" + this.div.id + "_hasta");
};
var modificarHorarioEmisionGrillaEvent = new ModificarHorarioEmisionGrillaEvent(new DivDefinition('ModificarHorarioEmisionGrillaEventId'));