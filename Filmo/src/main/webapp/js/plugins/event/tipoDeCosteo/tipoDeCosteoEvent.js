function TipoDeCosteoEvent(divContainer, prefijoId, contrato, senial, nroGrupo) {
	this.divContainer = divContainer;
	this.prefijoId = prefijoId;
	this.contrato = contrato;
	this.senial = senial;
	this.nroGrupo = nroGrupo;
	this.tipoDeCosteo = "";
	
};
extend(TipoDeCosteoEvent, Plugin);

TipoDeCosteoEvent.prototype.getDivContainer = function() {return $("#"+this.divContainer);};
TipoDeCosteoEvent.prototype.get = function(selector) {return $("#"+this.prefijoId+ "-" + selector);};

TipoDeCosteoEvent.init = function(divContainer, prefijoId, contrato, senial, nroGrupo) {
	var tipoDeCosteoEvent = new TipoDeCosteoEvent(divContainer,prefijoId, contrato, senial, nroGrupo); 
//	var tipoDeCosteoEvent = new TipoDeCosteoEvent("popupTipoDeCosteo","TipoDeCosteoEvent", 2292, 'CTN', 1); 
	tipoDeCosteoEvent.buscarTipoDeCosteo($.proxy(tipoDeCosteoEvent.trabajarConCosteos, tipoDeCosteoEvent));
//	tipoDeCosteoEvent.getHtmlToDraw();
};

TipoDeCosteoEvent.initPruebas_test = function(contrato, senial, nroGrupo) {
	var tipoDeCosteoEvent = new TipoDeCosteoEvent("viewContainer","TipoDeCosteoEvent", contrato, senial, nroGrupo); 
//	var tipoDeCosteoEvent = new TipoDeCosteoEvent("popupTipoDeCosteo","TipoDeCosteoEvent", 2292, 'CTN', 1); 
	tipoDeCosteoEvent.buscarTipoDeCosteo($.proxy(tipoDeCosteoEvent.trabajarConCosteos, tipoDeCosteoEvent));
//	tipoDeCosteoEvent.getHtmlToDraw();
};

TipoDeCosteoEvent.prototype.buscarTipoDeCosteo = function(callback) {
	BLOCK.showBlock("Cargando..");
	var self = this;
	var re = {
			contrato: self.contrato,
			senial: self.senial,
			nroGrupo: self.nroGrupo
	};
	$.ajax({
		type: "POST",
		url: "altaContratoBuscarTipoCosteoByGrupoContratoSenial.action",
		data: JSON.stringify({buscarTipoDeCosteoRequest : re}),//Component.serialize(buscarTipoDeCosteoRequest, "buscarTipoDeCosteoRequest"), 
		dataType: "json",
        contentType: 'application/json; charset=utf-8'
//		,responseObject: "tipoDeCosteo" 
	}).done(function(response){
		callback(response);
		BLOCK.hideBlock();
	}).fail(function(){
		MESSAGE.error("Ocurri&oacute; un error.");
	}).always(function(){
		BLOCK.hideBlock();
	});
};


TipoDeCosteoEvent.prototype.getHtmlToDraw = function() {
	/* Bloquear pantalla */
	var that = this;
	BLOCK.showBlock("Cargando");
	Component.get("html/tipoDeCosteo/TipoDeCosteo.html", $.proxy(this.drawPopup, this));
};	


TipoDeCosteoEvent.prototype.drawPopup = function(comp) {
	var that = this;
	that.getDivContainer().dialog({
		width: 550, show: 'blind', hide: 'blind', modal: true, autoOpen: false,
		buttons: {
			"Salir": function() {
				$(this).dialog("close");
			},
			"Aceptar": function(){
				costeo = $('input:radio[name='+that.prefijoId+'-radioButtonTipoCosteo]:checked').val();
				that.trabajarConCosteos(costeo);
//				$(this).dialog("close");
			}
		}
	});
	that.getDivContainer().empty().append(comp.replace(/{{id}}/g, that.prefijoId));
	that.getDivContainer().dialog("open");
	BLOCK.hideBlock();
};
TipoDeCosteoEvent.prototype.trabajarConCosteos = function(costeo) {
	var that = this;
	that.tipoDeCosteo = costeo; 
	console.log(this.tipoDeCosteo);
	/*
	  IF P_TIPO_COSTEO = 1      THEN      --SI ES TITULO_RATING
	  ELSIF P_TIPO_COSTEO = 2 THEN     --SI ES TITULO EXCEDENTE
	  ELSIF P_TIPO_COSTEO = 3 THEN    --SI ES TITULO MIXTO
	  ELSIF P_TIPO_COSTEO = 4 THEN    --  TITULO CFM
	*/
	if(that.tipoDeCosteo=="CFM"){
		that.trabajarConCosteosValidarSeleccion(4, CostoFijoMensualEvent.init);
//		CostoFijoMensualEvent.init(that);
	}else if(that.tipoDeCosteo=="EXCEDENTE"){
		that.trabajarConCosteosValidarSeleccion(2, CosteoExcedenteEvent.init);
//		CosteoExcedenteEvent.init(that);
	}else if(that.tipoDeCosteo=="RATING"){
		that.trabajarConCosteosValidarSeleccion(1, CosteoRatingEvent.init);
//		CosteoRatingEvent.init(that);
	}else if(that.tipoDeCosteo=="MIXTO"){
		that.trabajarConCosteosValidarSeleccion(3, CosteoMixtoEvent.init);
//		CosteoMixtoEvent.init(that);
	}else{
//		alert("Error debe seleccionar un tipo de costeo");
		that.getHtmlToDraw();
	}
};
TipoDeCosteoEvent.prototype.trabajarConCosteosValidarSeleccion = function(idSeleccion, callback) {
	var that = this;
/*
  IF P_TIPO_COSTEO = 1      THEN      --SI ES TITULO_RATING
  ELSIF P_TIPO_COSTEO = 2 THEN     --SI ES TITULO EXCEDENTE
  ELSIF P_TIPO_COSTEO = 3 THEN    --SI ES TITULO MIXTO
  ELSIF P_TIPO_COSTEO = 4 THEN    --  TITULO CFM
*/
	var validarSeleccion = {
			contrato: that.contrato,
			senial: that.senial,
			nroGrupo : that.nroGrupo,
			idSeleccion : idSeleccion
		};
		$.ajax({
			type: "POST",
			url: "tipoDeCosteoValidarSeleccion.action",
			data: JSON.stringify({validarSeleccion : validarSeleccion}),//Component.serialize(buscarTipoDeCosteoRequest, "buscarTipoDeCosteoRequest"), 
			dataType: "json",
	        contentType: 'application/json; charset=utf-8'
		}).done(function(response){
			if(response=="OK"){
				that.getDivContainer().dialog("close");
				callback(that);
			}else{
				MESSAGE.error(response);
			}
			BLOCK.hideBlock();
		}).fail(function(){
			MESSAGE.error("Ocurri&oacute; un error.");
		}).always(function(){
			BLOCK.hideBlock();
		});

	
};

