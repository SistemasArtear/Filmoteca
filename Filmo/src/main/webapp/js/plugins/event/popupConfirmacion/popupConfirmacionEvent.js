function PopupConfirmacionEvent(div) {
	this.div = div;
	this.mensajeConfirmacion = "";
};

extend(PopupConfirmacionEvent, Plugin);

PopupConfirmacionEvent.prototype.create = function(divId, mensajeConfirmacion) {
	this.div = new DivDefinition(divId);
	this.mensajeConfirmacion = mensajeConfirmacion;
	Component.get("html/popupConfirmacion/popupConfirmacion.html", popupConfirmacionEvent.draw);
};

PopupConfirmacionEvent.prototype.draw = function(comp) {
	var self = popupConfirmacionEvent;
	
	$("#popupContainer").empty().append(comp.replace(/{{id}}/g, self.div.id));
	
	self.getBotonConfimar().button().click(self.confirmar);
	self.getBotonCancelar().button().click(self.cancelar);
	self.getMensajeConfirmacion().html(self.mensajeConfirmacion);
	
	self.getPopup().dialog({
		minHeight: 0,
		height: 'auto',
		width: 600,
		position: 'center',
		show: 'blind',
		hide: 'blind',
		modal: true,
		closeOnEscape: true,
		resizable: false,
		autoOpen: false,
		title: self.popTitle ? self.popTitle : 'Confirmación',
		close: function(event, ui) { 
			$(this).remove();
    	}
	});
	
	if (!self.getPopup().dialog("isOpen")) {
		self.getPopup().dialog("open");
	}
	
	self.afterDraw();
};

PopupConfirmacionEvent.prototype.close = function() {
	this.getPopup().dialog("close");
	popupConfirmacionEvent.remove();
};
PopupConfirmacionEvent.prototype.open = function() {
    this.getPopup().dialog("open");
    popupConfirmacionEvent.remove();
};
PopupConfirmacionEvent.prototype.remove = function() {
    this.afterDraw = function () {};
    this.getPopup().remove();
};
PopupConfirmacionEvent.prototype.confirmar = function() {
};
PopupConfirmacionEvent.prototype.cancelar = function() {
};
PopupConfirmacionEvent.prototype.afterDraw = function() {
};


PopupConfirmacionEvent.prototype.getBotonConfimar = function() {
	return $("#" + this.div.id + "_botonSiConfirmacionPopup");
};
PopupConfirmacionEvent.prototype.getBotonCancelar= function() {
	return $("#" + this.div.id + "_botonNoConfirmacionPopup");
};
PopupConfirmacionEvent.prototype.getMensajeConfirmacion = function() {
	return $("#" + this.div.id + "_mensajeConfirmacionPopup");
};
PopupConfirmacionEvent.prototype.getPopup = function() {
	return $("#" + this.div.id + "_popupConfirmacion");
};

var popupConfirmacionEvent = new PopupConfirmacionEvent();