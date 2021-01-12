function ChequeoOperativoEvent(div) {
	this.div = div;
};
extend(ChequeoOperativoEvent, Plugin);
ChequeoOperativoEvent.prototype.create = function() {
	var self = chequeoOperativoEvent;
	self.isDrawedGridPH = false;
	self.service = new ChequeoOperativoService();
	Component.get("html/chequeoOperativo/chequeoOperativo.html", chequeoOperativoEvent.draw);
};
ChequeoOperativoEvent.prototype.draw = function(comp) {
	var self = chequeoOperativoEvent;
	$("#viewContainer").empty().append(comp.replace(/{{id}}/g, self.div.id));
	
	Accordion.getInstance(self.getAcordionFormInicial());
	self.getInputFecha().datepicker({
		showOn: "button",
		dateFormat: "mm/yy",
		buttonImage: "img/calendar.gif",
		changeMonth: true,
		changeYear: true,
		buttonImageOnly: true
	}).mask("99/9999");

	self.getButtonAceptarFormInicial().button().click(self.aceptarFormInicial);
};

ChequeoOperativoEvent.prototype.aceptarFormInicial = function() {
	var self = chequeoOperativoEvent;
	if (self.validForm()) {
		var data = self.getFormInicial().serialize();
		self.service.chequearFecha(data);
	}
	 
	 
	return true;
};

ChequeoOperativoEvent.prototype.validForm = function() {
	var fecha = this.getInputFecha();

	if (Validator.isEmpty(fecha)) {
		Validator.focus(fecha);
		return false;
	} 
	var mes = parseInt(fecha.val().substring(0,2),10);
	if (mes > 12 || mes < 1){
		Validator.focus(fecha, "Fecha invalida");
		return false;
	}
	return true;
};


ChequeoOperativoEvent.prototype.getAcordionFormInicial = function() {
	return $("#" + this.div.id + "_accordionFormInicial");
};
ChequeoOperativoEvent.prototype.getInputFecha = function() {
	return $("#" + this.div.id + "_fechaBusqueda");
};
ChequeoOperativoEvent.prototype.getButtonAceptarFormInicial = function() {
	return $("#" + this.div.id + "_aceptarFormInicial");
};

ChequeoOperativoEvent.prototype.getFormInicial = function() {
	return $("#" + this.div.id + "_formInicial");
};

ChequeoOperativoEvent.prototype.getChequeoOperativoPopUp = function() {
    return $("#" + this.div.id + "_confirmacionChequeoOperativo");
};

ChequeoOperativoEvent.prototype.drawPopUpChequeoOperativo = function(comp) {
	 var self = chequeoOperativoEvent;
	    
	    if (self.getChequeoOperativoPopUp().length == 0) {
	        $("#viewContainer").append(comp.replace(/{{id}}/g, self.div.id));
	        self.getChequeoOperativoPopUp().dialog({
	            title : 'Confirmar chequeo operativo',
	            width: 300,
	            show: 'blind',
	            hide: 'blind',
	            modal: true,
	            autoOpen: true,
	            close: function(){
	                self.getChequeoOperativoPopUp().remove();
	            },
	            buttons: {
	            	Confirmar: function() {
	            		self.service.confirmarChequeo(chequeoOperativoEvent.periodoCO);
	            		self.getChequeoOperativoPopUp().remove();
	            		},
	            	Cancelar: function() {
	            		self.service.procesarChequeo(chequeoOperativoEvent.periodoCO);
	            		self.getChequeoOperativoPopUp().remove();
            		}
	            	
	            }
	        });
	        }
};

var chequeoOperativoEvent = new ChequeoOperativoEvent(new DivDefinition('ChequeoOperativoEventId'));