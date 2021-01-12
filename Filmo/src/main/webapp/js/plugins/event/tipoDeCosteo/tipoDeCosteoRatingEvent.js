/*
 * 
 * 
 * 
 * 
 *  Event para el tipo de costeo por RATING
 *  
 *  
 *  
 *  
 */
function CosteoRatingEvent(divContainer, prefijoId, contrato, senial, nroGrupo, tipoTitulo, titulo, canje) {
	this.divContainer = divContainer;
	this.prefijoId = prefijoId;
	this.contrato = contrato;
	this.senial = senial;
	this.nroGrupo = nroGrupo;
	this.tipoTitulo = tipoTitulo;
	this.titulo = titulo;
	this.canje = canje;
	this.modificar = false;
	
};
extend(CosteoRatingEvent, Plugin);
CosteoRatingEvent.prototype.getDivContainer = function() {return $("#"+this.divContainer);};
CosteoRatingEvent.prototype.get = function(selector) {return $("#"+this.prefijoId+ "-" + selector);};

CosteoRatingEvent.init = function(tcosteoEvent) {
	//en caso de registro bloqueado : 
	//DELETE FROM  BLOQUEO_CONTRATOS WHERE CONTRATO = 2328;
//	var divContainer = tcosteoEvent.divContainer;//utiliza el mismo div contenedor que el padre, para abrir de vuelta el popUp sobre el mismo div
	//utiliza el mismo div contenedor que el padre, para abrir de vuelta el popUp sobre el mismo div
	var ratingEvent = new CosteoRatingEvent(tcosteoEvent.divContainer,"CosteoRatingEvent", tcosteoEvent.contrato, tcosteoEvent.senial, tcosteoEvent.nroGrupo); 
	ratingEvent.getHtmlToDraw();
};

CosteoRatingEvent.initModificar = function(divContainer, contrato, senial, nroGrupo, tipoTitulo, titulo, canje) {
//	var divContainer = tcosteoEvent.divContainer;//utiliza el mismo div contenedor que el padre, para abrir de vuelta el popUp sobre el mismo div
	//utiliza el mismo div contenedor que el padre, para abrir de vuelta el popUp sobre el mismo div
	var ratingEvent = new CosteoRatingEvent(divContainer,"CosteoRatingEvent", contrato, senial, nroGrupo, tipoTitulo, titulo, canje); 
	ratingEvent.modificar = true;
	ratingEvent.getHtmlToDraw();
};

CosteoRatingEvent.prototype.getHtmlToDraw = function() {
	/* Bloquear pantalla */
	var that = this;
	BLOCK.showBlock("Cargando");
	Component.get("html/tipoDeCosteo/Rating.html", $.proxy(this.drawPopup, this));
};	


CosteoRatingEvent.prototype.drawPopup = function(comp) {
	var that = this;
	that.getDivContainer().dialog({
		width: 770, show: 'blind', hide: 'blind', modal: true, autoOpen: false,
		buttons: {
			"Salir": function() {
				$(this).dialog("close");
			},
			"Aceptar": function(){
//				that.trabajarConCosteos();
				$(this).dialog("close");
			}
		}
	});
	that.getDivContainer().empty().append(comp.replace(/{{id}}/g, that.prefijoId));
	that.get("valor").autoNumeric({vMax: '999999999.99', aDec:".", aSep: ''});
	that.get("valor").autoNumericSet("0");
	that.get("ratingDesde").numeric();
	that.get("ratingHasta").numeric();

	if(that.modificar){
		that.get("div-radioButtonTipoContrado").hide();
		var canje = that.canje=="S"?"CANJE":"NO CANJE";
		that.get("span-tipoContrato").text(canje);
		that.drawGridModificar();
		that.get("buttonAgregarRating").button().click($.proxy(that.agregarRegistroABM, that));
	}else{
		that.get("buttonAgregarRating").button().click($.proxy(that.agregarRegistros, that));
		that.buscarDescripcionCanjeParaElGrupo();
		that.drawGrid();
	}

	that.getDivContainer().dialog("open");
	BLOCK.hideBlock();
};
CosteoRatingEvent.prototype.drawGrid = function() {
	var that = this;
	that.get("tGrillaRating").jqGrid({
		height: 'auto',
		width : 720,
		datatype: 'local',
		colNames:['','','', 'Rating Desde','Rating Hasta', 'Valor', ''],
		colModel:[ 
		    {name:'contrato', index:'nroGrupo', 			align:'center', hidden:true},
		    {name:'senial', index:'nroGrupo', 			align:'center', hidden:true},
		    {name:'nroGrupo', index:'nroGrupo', 			align:'center', hidden:true},
		    {name:'ratingDesde',	  index:'ratingDesde', 	align:'right', width: 200,  key:true},
		    {name:'ratingHasta',	  index:'ratingHasta', 	align:'right', width: 200,  key:true},
		    {name:'valor',		index:'valor', 		align:'right', width: 590, formatter: 'currency'},
		    {name:'eliminarRating',		index:'eliminarRating',		align:'center', width: 20}
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + that.prefijoId + '-pagerGrillaRating',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Registros',
        gridComplete: function() {
        	var ids = $(this).jqGrid('getDataIDs');
        	for (var i = 0; i < ids.length; i++) {
        		var rowId = ids[i];
        		var valor1 = that.get("tGrillaRating").jqGrid("getCell", rowId, "ratingDesde");
        		var valor2 = that.get("tGrillaRating").jqGrid("getCell", rowId, "ratingHasta");
        		var valor3 = that.get("tGrillaRating").jqGrid("getCell", rowId, "valor");
        		var descripcion = that.get("tGrillaRating").jqGrid("getCell", rowId, "valor");
        		var idObject = that.prefijoId+"-idLink-"+valor1+"-"+valor2;
        		var link = "<span id='"+idObject+"'  class='ui-icon ui-icon-trash' href=# style=\"color: blue; cursor: pointer;\" title=\"ELIMINAR REGISTRO\"></span>";
        		$(this).jqGrid('setRowData', rowId, { eliminarRating: link });
        		$("#"+idObject).unbind("click").bind("click",$.proxy(that.eliminarRegistro, that, valor1, valor2, valor3));
        	}
        }
	});
	that.buscarRegistros();
};
CosteoRatingEvent.prototype.drawGridModificar = function() {
	var that = this;
	that.get("tGrillaRating").jqGrid({
		height: 'auto',
		width : 720,
		datatype: 'local',
		colNames:['','','', 'Rating Desde','Rating Hasta', 'Valor', '', ''],
		colModel:[ 
			{name:'contrato', index:'nroGrupo', 			align:'center', hidden:true},
			{name:'senial', index:'nroGrupo', 			align:'center', hidden:true},
			{name:'nroGrupo', index:'nroGrupo', 			align:'center', hidden:true},
			{name:'ratingDesde',	  index:'ratingDesde', 	align:'right', width: 200,  key:true},
			{name:'ratingHasta',	  index:'ratingHasta', 	align:'right', width: 200,  key:true},
			{name:'valor',		index:'valor', 		align:'right', width: 590, formatter: 'currency'},
			{name:'ABMmodificarRating',		index:'ABMmodificarRating',		align:'center', width: 70},
			{name:'ABMeliminarRating',		index:'ABMeliminarRating',		align:'center', width: 70}
			],
			rowNum: 10,
			rowList:[10,20,30],
			scrollOffset: 0,
			pager: '#' + that.prefijoId + '-pagerGrillaRating',
			viewrecords: true, 
			loadonce: true,
			editurl: 'clientArray', 
			caption: 'Registros',
			gridComplete: function() {
				var ids = $(this).jqGrid('getDataIDs');
				for (var i = 0; i < ids.length; i++) {
					var rowId = ids[i];
					var valor1 = that.get("tGrillaRating").jqGrid("getCell", rowId, "ratingDesde");
					var valor2 = that.get("tGrillaRating").jqGrid("getCell", rowId, "ratingHasta");
					var valor3 = that.get("tGrillaRating").jqGrid("getCell", rowId, "valor");
					
					var idObjectE = that.prefijoId+"-idLinkE-"+valor1+"-"+valor2;
					var idObjectM = that.prefijoId+"-idLinkM-"+valor1+"-"+valor2;
					var linkE = "<span id='"+idObjectE+"'  class='ui-icon ui-icon-trash conTooltip' href=# style=\"color: blue; cursor: pointer; margin:auto;\" title=\"ELIMINAR REGISTRO\"></span>";
					var linkM = "<span id='"+idObjectM+"'  class='ui-icon ui-icon-pencil conTooltip' href=# style=\"color: blue; cursor: pointer; margin:auto;\" title=\"MODIFICAR REGISTRO\"></span>";
					$(this).jqGrid('setRowData', rowId, { ABMeliminarRating: linkE });
					$(this).jqGrid('setRowData', rowId, { ABMmodificarRating: linkM });
					$("#"+idObjectE).unbind("click").bind("click",$.proxy(that.eliminarRegistroABM, that, valor1, valor2, valor3));
					$("#"+idObjectM).unbind("click").bind("click",$.proxy(that.modificarRegistroABM, that, valor1, valor2, valor3));
				}
			}
	});
	that.buscarRegistrosModificacion();
};
CosteoRatingEvent.prototype.buscarDescripcionCanjeParaElGrupo = function() {
	BLOCK.showBlock("Cargando..");
	var that = this;
	var re = {contrato: that.contrato};
	$.ajax({
		type: "POST",
		url: "buscarDescripcionCanjeByGrupo.action",
		data: JSON.stringify({buscarTipoDeCosteoRequest : re}),//Component.serialize(buscarTipoDeCosteoRequest, "buscarTipoDeCosteoRequest"), 
		dataType: "json",
        contentType: 'application/json; charset=utf-8'
//		,responseObject: "tipoDeCosteo" 
	}).done(function(response){
		that.get("descripcionCanjeCabecera").text(response);
		BLOCK.hideBlock();
	}).fail(function(){
		MESSAGE.error("Ocurri&oacute; un error.");
	}).always(function(){
		BLOCK.hideBlock();
	});
};

CosteoRatingEvent.prototype.eliminarRegistro = function(valor1, valor2, valor3){
	var that = this;
	console.log("Se eliminara el rgistro con valor1 : " + valor1 + ", valor3: "+ valor3);
	var eliminarRegistroRequest = {
			contrato: that.contrato,
			senial: that.senial,
			nroGrupo : that.nroGrupo,
			valor1 : valor1,
			valor2 : valor2,
			valor3 : valor3
		};
		$.ajax({
			type: "POST",
			url: "tipoDeCosteoEliminarRegistrosRating.action",
			data: JSON.stringify({eliminarRegistroRequest : eliminarRegistroRequest}),//Component.serialize(buscarTipoDeCosteoRequest, "buscarTipoDeCosteoRequest"), 
			dataType: "json",
	        contentType: 'application/json; charset=utf-8'
		}).done(function(response){
			if(response=="OK"){
				MESSAGE.ok("El registro ha sido eliminado.")
				that.buscarRegistros();
			}else{
				MESSAGE.error(response);
			}
			BLOCK.hideBlock();
		}).fail(function(){
			MESSAGE.error("Ocurri&oacute; un error.");
		}).always(function(){
			BLOCK.hideBlock();
		});
	
	//
};
CosteoRatingEvent.prototype.modificarRegistroABM = function(valor1, valor2, valor3){
	var that = this;
	that.get("div-popUp-modificacion").load("html/tipoDeCosteo/ModificarRegistro.html", function() {
		that.get("div-popUp-modificacion").dialog({
			width: 770, show: 'blind', hide: 'blind', modal: true, autoOpen: false,
		});
		that.get("div-popUp-modificacion").html(that.get("div-popUp-modificacion").html().replace(/{{id}}/g, that.prefijoId));
		that.get("label-valor1").text("RATING DESDE:");
		that.get("label-valor2").text("RATING HASTA:");
		that.get("modificar-valor1").val(valor1);
		that.get("modificar-valor2").val(valor2);
		that.get("modificar-valor3").val(valor3);
		that.get("modificar-valor3").autoNumeric({vMax: '999999999.99', aDec:".", aSep: ''});
		that.get("div-popUp-modificacion").dialog("open");
		that.get("cancelar").unbind("click").button().click(function(){
			that.get("div-popUp-modificacion").dialog("close");
		});
		that.get("modificar").unbind("click").button().click(function(){
			if (!Validator.isEmpty(that.get("modificar-valor3")) && (Validator.isEmpty(that.get("modificar-valor3")) || that.get("modificar-valor3").autoNumericGet() == "0")) {
				Validator.focus(that.get("modificar-valor3"), "Debe ingresar un valor.");
				return;
			}
			var ABMRatingRequest = {
				contrato: that.contrato,
				senial: that.senial,
				nroGrupo : that.nroGrupo,
				tipoTitulo : that.tipoTitulo,
				titulo : that.titulo,
				canje : that.canje,
				accion : "U",
				valor1 : parseInt(that.get("modificar-valor1").val()),
				valor2 : parseInt(that.get("modificar-valor2").val()),
				valor3 : parseFloat(that.get("modificar-valor3").val())
			};
			$.ajax({
				type: "POST",
				url: "tipoDeCosteoABMRating.action",
				data: JSON.stringify({ABMRatingRequest : ABMRatingRequest}),//Component.serialize(buscarTipoDeCosteoRequest, "buscarTipoDeCosteoRequest"), 
				dataType: "json",
				contentType: 'application/json; charset=utf-8'
			}).done(function(response){
				if(response=="OK"){
					MESSAGE.ok("El registro ha sido modificado.")
					that.buscarRegistrosModificacion();
					that.get("div-popUp-modificacion").dialog("close");
				}else{
					MESSAGE.error(response);
				}
				BLOCK.hideBlock();
			}).fail(function(){
				MESSAGE.error("Ocurri&oacute; un error.");
			}).always(function(){
				BLOCK.hideBlock();
			});
		});
		setTimeout(function(){
			Validator.focusOnly(that.get("modificar-valor3"));
			}, 1000);
	});
};
CosteoRatingEvent.prototype.eliminarRegistroABM = function(valor1, valor2, valor3){
	var that = this;
	console.log("Se eliminara el rgistro con valor1 : " + valor1 + ", valor2: "+ valor2 + ", valor3: "+ valor3);
	//contrato, senial, nroGrupo, tipoTitulo, titulo, canje
	var ABMRatingRequest = {
			contrato: that.contrato,
			senial: that.senial,
			nroGrupo : that.nroGrupo,
			tipoTitulo : that.tipoTitulo,
			titulo : that.titulo,
			canje : that.canje,
			accion : "D",
			valor1 : parseInt(valor1),
			valor2 : parseInt(valor2),
			valor3 : parseFloat(valor3)
	};
	$.ajax({
		type: "POST",
		url: "tipoDeCosteoABMRating.action",
		data: JSON.stringify({ABMRatingRequest : ABMRatingRequest}),//Component.serialize(buscarTipoDeCosteoRequest, "buscarTipoDeCosteoRequest"), 
		dataType: "json",
		contentType: 'application/json; charset=utf-8'
	}).done(function(response){
		if(response=="OK"){
			MESSAGE.ok("El registro ha sido eliminado.")
			that.buscarRegistrosModificacion();
		}else{
			MESSAGE.error(response);
		}
		BLOCK.hideBlock();
	}).fail(function(){
		MESSAGE.error("Ocurri&oacute; un error.");
	}).always(function(){
		BLOCK.hideBlock();
	});
	
	//
};
CosteoRatingEvent.prototype.agregarRegistroABM = function() {
	var that = this;
	if(Validator.isEmpty(that.get("ratingDesde"))||Validator.isEmpty(that.get("ratingHasta"))||Validator.isEmpty(that.get("valor"))){
		Validator.focus(that.get("ratingDesde"), "Debe ingresar un valor.");
		Validator.focus(that.get("ratingHasta"), "Debe ingresar un valor.");
		Validator.focus(that.get("valor"), "Debe ingresar un valor.");
		return;
	}
	if (!Validator.isEmpty(that.get("valor")) && (Validator.isEmpty(that.get("valor")) || that.get("valor").autoNumericGet() == "0")) {
		Validator.focus(that.get("valor"), "Debe ingresar un valor.");
		return;
	}
	var ABMRatingRequest = {
		contrato: that.contrato,
		senial: that.senial,
		nroGrupo : that.nroGrupo,
		tipoTitulo : that.tipoTitulo,
		titulo : that.titulo,
		canje : that.canje,
		accion : "I",
		valor1 : parseInt(that.get("ratingDesde").val()),
		valor2 : parseInt(that.get("ratingHasta").val()),
		valor3 : parseFloat(that.get("valor").val())
	};
	$.ajax({
		type: "POST",
		url: "tipoDeCosteoABMRating.action",
		data: JSON.stringify({ABMRatingRequest : ABMRatingRequest}),//Component.serialize(buscarTipoDeCosteoRequest, "buscarTipoDeCosteoRequest"), 
		dataType: "json",
		contentType: 'application/json; charset=utf-8'
	}).done(function(response){
		if(response=="OK"){
			MESSAGE.ok("El registro ha sido agregado.")
			that.get("ratingDesde").val("");
			that.get("ratingHasta").val("");
			that.get("valor").val("");
			that.buscarRegistrosModificacion();
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

CosteoRatingEvent.prototype.buscarRegistros = function() {
	
	var self = this;
	var buscarGruposContratoRequest = {
		contrato: self.contrato,
		senial: self.senial,
		nroGrupo : self.nroGrupo
	};
	$.ajax({
		type: "POST",
		url: "tipoDeCosteoObtenerRegistrosRating.action",
		data: JSON.stringify({buscarTipoDeCosteoRequest : buscarGruposContratoRequest}),//Component.serialize(buscarTipoDeCosteoRequest, "buscarTipoDeCosteoRequest"), 
		dataType: "json",
        contentType: 'application/json; charset=utf-8'
	}).done(function(response){
		self.actualizarGrilla(response);
		BLOCK.hideBlock();
	}).fail(function(){
		MESSAGE.error("Ocurri&oacute; un error.");
	}).always(function(){
		BLOCK.hideBlock();
	});
};
CosteoRatingEvent.prototype.buscarRegistrosModificacion = function() {
	var self = this;
	var ABMRatingRequest = {
			contrato: self.contrato,
			senial: self.senial,
			nroGrupo : self.nroGrupo,
			tipoTitulo : self.tipoTitulo,
			titulo : self.titulo
	};
	$.ajax({
		type: "POST",
		url: "tipoDeCosteoObtenerRegistrosRatingABM.action",
		data: JSON.stringify({ABMRatingRequest : ABMRatingRequest}),//Component.serialize(buscarTipoDeCosteoRequest, "buscarTipoDeCosteoRequest"), 
		dataType: "json",
		contentType: 'application/json; charset=utf-8'
	}).done(function(response){
		self.actualizarGrilla(response);
		BLOCK.hideBlock();
	}).fail(function(){
		MESSAGE.error("Ocurri&oacute; un error.");
	}).always(function(){
		BLOCK.hideBlock();
	});
};

CosteoRatingEvent.prototype.actualizarGrilla = function(data) {
	var that = this;
	that.get("tGrillaRating").clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};
CosteoRatingEvent.prototype.agregarRegistros = function() {
	var that = this;

	if(Validator.isEmpty(that.get("ratingDesde"))||Validator.isEmpty(that.get("ratingHasta"))||Validator.isEmpty(that.get("valor"))){
		Validator.focus(that.get("ratingDesde"), "Debe ingresar un valor.");
		Validator.focus(that.get("ratingHasta"), "Debe ingresar un valor.");
		Validator.focus(that.get("valor"), "Debe ingresar un valor.");
		return;
	}
	if (!Validator.isEmpty(that.get("valor")) && (Validator.isEmpty(that.get("valor")) || that.get("valor").autoNumericGet() == "0")) {
		Validator.focus(that.get("valor"), "Debe ingresar un valor.");
		return;
	}
	console.log(" tipo de contrato " + $('input:radio[name='+that.prefijoId+'-radioButtonTipoContrato]:checked').val());
/*
    private int mes;
    private int anio;
    private BigDecimal valor;
 */
	var agregarRegistroRequest = {
		contrato: that.contrato,
		senial: that.senial,
		nroGrupo : that.nroGrupo,
		ratingDesde : parseInt(that.get("ratingDesde").val()),
		ratingHasta : parseInt(that.get("ratingHasta").val()),
		valor : parseFloat(that.get("valor").val()),
		tipoContrato : $('input:radio[name='+that.prefijoId+'-radioButtonTipoContrato]:checked').val()
	};
	$.ajax({
		type: "POST",
		url: "tipoDeCosteoAgregarRegistrosRating.action",
		data: JSON.stringify({agregarRegistroRequest : agregarRegistroRequest}),//Component.serialize(buscarTipoDeCosteoRequest, "buscarTipoDeCosteoRequest"), 
		dataType: "json",
        contentType: 'application/json; charset=utf-8'
	}).done(function(response){
		if(response=="OK"){
			MESSAGE.ok("El registro ha sido agregado.")
			that.get("ratingDesde").val("");
			that.get("ratingHasta").val("");
			that.get("valor").val("");
			that.buscarRegistros();
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

