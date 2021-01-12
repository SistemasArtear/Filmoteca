/*
 * 
 * 
 * 
 * 
 *  Event para el tipo de costeo por EXCEDENTE
 *  
 *  
 *  
 *  
 */
function CosteoExcedenteEvent(divContainer, prefijoId, contrato, senial, nroGrupo,tipoTitulo, titulo, canje) {
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


extend(CosteoExcedenteEvent, Plugin);
CosteoExcedenteEvent.prototype.getDivContainer = function() {return $("#"+this.divContainer);};
CosteoExcedenteEvent.prototype.get = function(selector) {return $("#"+this.prefijoId+ "-" + selector);};

CosteoExcedenteEvent.init = function(tcosteoEvent) {
//	var divContainer = tcosteoEvent.divContainer;//utiliza el mismo div contenedor que el padre, para abrir de vuelta el popUp sobre el mismo div
	//utiliza el mismo div contenedor que el padre, para abrir de vuelta el popUp sobre el mismo div
	var excedenteEvent = new CosteoExcedenteEvent(tcosteoEvent.divContainer,"CosteoExcedenteEvent", tcosteoEvent.contrato, tcosteoEvent.senial, tcosteoEvent.nroGrupo); 
	excedenteEvent.getHtmlToDraw();
};
CosteoExcedenteEvent.initModificar = function(divContainer, contrato, senial, nroGrupo, tipoTitulo, titulo, canje) {
//	var divContainer = tcosteoEvent.divContainer;//utiliza el mismo div contenedor que el padre, para abrir de vuelta el popUp sobre el mismo div
	//utiliza el mismo div contenedor que el padre, para abrir de vuelta el popUp sobre el mismo div
	var excedenteEvent = new CosteoExcedenteEvent(divContainer,"CosteoExcedenteEvent", contrato, senial, nroGrupo, tipoTitulo, titulo, canje); 
	excedenteEvent.modificar = true;
	excedenteEvent.getHtmlToDraw();
};

CosteoExcedenteEvent.prototype.getHtmlToDraw = function() {
	/* Bloquear pantalla */
	var that = this;
	BLOCK.showBlock("Cargando");
	Component.get("html/tipoDeCosteo/Excedente.html", $.proxy(this.drawPopup, this));
};	


CosteoExcedenteEvent.prototype.drawPopup = function(comp) {
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
	that.get("minutoDesde").numeric();
	that.get("minutoHasta").numeric();
	if(that.modificar){
		that.get("div-radioButtonTipoContrado").hide();
		var canje = that.canje=="S"?"CANJE":"NO CANJE";
		that.get("span-tipoContrato").text(canje);
		that.drawGridModificar();
		that.get("buttonAgregarExcedente").button().click($.proxy(that.agregarRegistroABM, that));
	}else{
		that.get("buttonAgregarExcedente").button().click($.proxy(that.agregarRegistros, that));
		that.buscarDescripcionCanjeParaElGrupo();
		that.drawGrid();
	}
	that.getDivContainer().dialog("open");
	BLOCK.hideBlock();
};
CosteoExcedenteEvent.prototype.drawGrid = function() {
	var that = this;
	
	that.get("tGrillaExcedente").jqGrid({
		height: 'auto',
//		autowidth: true,
		width : 720,
		datatype: 'local',
		colNames:['','','', 'Minuto Desde', 'Minuto Hasta', 'Valor', ''],
		colModel:[ 
		    {name:'contrato', index:'nroGrupo', 			align:'center', hidden:true},
		    {name:'senial', index:'nroGrupo', 			align:'center', hidden:true},
		    {name:'nroGrupo', index:'nroGrupo', 			align:'center', hidden:true},
		    {name:'minutoDesde',	  index:'minutoDesde', 	align:'right', width: 200,  key:true},
		    {name:'minutoHasta',	  index:'minutoHasta', 	align:'right', width: 200,  key:true},
		    {name:'valor',		index:'valor', 		align:'right', width: 390, formatter: 'currency'},
		    {name:'eliminarExcedente',		index:'eliminarExcedente',		align:'center', width: 20}
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + that.prefijoId + '-pagerGrillaExcedente',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Registros',
        gridComplete: function() {
        	var ids = $(this).jqGrid('getDataIDs');
        	for (var i = 0; i < ids.length; i++) {
        		var rowId = ids[i];
        		var valor1 = that.get("tGrillaExcedente").jqGrid("getCell", rowId, "minutoDesde");
        		var valor2 = that.get("tGrillaExcedente").jqGrid("getCell", rowId, "minutoHasta");
        		var valor3 = that.get("tGrillaExcedente").jqGrid("getCell", rowId, "valor");
        		var descripcion = that.get("tGrillaExcedente").jqGrid("getCell", rowId, "valor");
        		var idObject = that.prefijoId+"-idLink-"+valor1+"-"+valor2;
        		var link = "<span id='"+idObject+"'  class='ui-icon ui-icon-trash' href=# style=\"color: blue; cursor: pointer;\" title=\"ELIMINAR REGISTRO\"></span>";
        		$(this).jqGrid('setRowData', rowId, { eliminarExcedente: link });
        		$("#"+idObject).unbind("click").bind("click",$.proxy(that.eliminarRegistro, that, valor1, valor2, valor3));
        	}
        }
	});
	
//	Component.resizableGrid(that.get("tGrillaCFM"));
	that.buscarRegistros();
};
CosteoExcedenteEvent.prototype.drawGridModificar = function() {
	var that = this;
	that.get("tGrillaExcedente").jqGrid({
		height: 'auto',
		width : 730,
		datatype: 'local',
		colNames:['','','', 'Minuto Desde', 'Minuto Hasta', 'Valor', '',''],
		colModel:[ 
			{name:'contrato', index:'nroGrupo', 			align:'center', hidden:true},
			{name:'senial', index:'nroGrupo', 			align:'center', hidden:true},
			{name:'nroGrupo', index:'nroGrupo', 			align:'center', hidden:true},
			{name:'minutoDesde',	  index:'minutoDesde', 	align:'right', width: 160,  key:true},
			{name:'minutoHasta',	  index:'minutoHasta', 	align:'right', width: 160,  key:true},
			{name:'valor',		index:'valor', 		align:'right', width: 380, formatter: 'currency'},
			{name:'ABMmodificarExcedente',		index:'ABMmodificarExcedente',		align:'center', width: 70},
			{name:'ABMeliminarExcedente',		index:'ABMeliminarExcedente',		align:'center', width: 70}
			],
			rowNum: 10,
			rowList:[10,20,30],
			scrollOffset: 0,
			pager: '#' + that.prefijoId + '-pagerGrillaExcedente',
			viewrecords: true, 
			loadonce: true,
			editurl: 'clientArray', 
			caption: 'Registros',
			gridComplete: function() {
				var ids = $(this).jqGrid('getDataIDs');
				for (var i = 0; i < ids.length; i++) {
					var rowId = ids[i];
					var valor1 = that.get("tGrillaExcedente").jqGrid("getCell", rowId, "minutoDesde");
					var valor2 = that.get("tGrillaExcedente").jqGrid("getCell", rowId, "minutoHasta");
					var valor3 = that.get("tGrillaExcedente").jqGrid("getCell", rowId, "valor");
					var descripcion = that.get("tGrillaExcedente").jqGrid("getCell", rowId, "valor");
					var idObjectE = that.prefijoId+"-idLinkE-"+valor1+"-"+valor2;
					var idObjectM = that.prefijoId+"-idLinkM-"+valor1+"-"+valor2;
					var linkE = "<span id='"+idObjectE+"'  class='ui-icon ui-icon-trash conTooltip' href=# style=\"color: blue; cursor: pointer; margin:auto;\" title=\"ELIMINAR REGISTRO\"></span>";
					var linkM = "<span id='"+idObjectM+"'  class='ui-icon ui-icon-pencil conTooltip' href=# style=\"color: blue; cursor: pointer; margin:auto;\" title=\"MODIFICAR REGISTRO\"></span>";
					$(this).jqGrid('setRowData', rowId, { ABMeliminarExcedente: linkE });
					$(this).jqGrid('setRowData', rowId, { ABMmodificarExcedente: linkM });
					$("#"+idObjectE).unbind("click").bind("click",$.proxy(that.eliminarRegistroABM, that, valor1, valor2, valor3));
					$("#"+idObjectM).unbind("click").bind("click",$.proxy(that.modificarRegistroABM, that, valor1, valor2, valor3));
				}
			}
	});
	that.buscarRegistrosModificacion();
};
CosteoExcedenteEvent.prototype.buscarDescripcionCanjeParaElGrupo = function() {
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

CosteoExcedenteEvent.prototype.eliminarRegistro = function(valor1, valor2, valor3){
	var that = this;
	console.log("Se eliminara el rgistro con valor1 : " + valor1 + ", valor2: "+ valor2 + ", valor3: "+ valor3);
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
			url: "tipoDeCosteoEliminarRegistrosExcedente.action",
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
CosteoExcedenteEvent.prototype.modificarRegistroABM = function(valor1, valor2, valor3){
	var that = this;
	that.get("div-popUp-modificacion").load("html/tipoDeCosteo/ModificarRegistro.html", function() {
		that.get("div-popUp-modificacion").dialog({
			width: 770, show: 'blind', hide: 'blind', modal: true, autoOpen: false
		});
		that.get("div-popUp-modificacion").html(that.get("div-popUp-modificacion").html().replace(/{{id}}/g, that.prefijoId));
		that.get("cancelar").unbind("click").button().click(function(){
			that.get("div-popUp-modificacion").dialog("close");
		});
		that.get("modificar").unbind("click").button().click(function(){
			if (!Validator.isEmpty(that.get("modificar-valor3")) && (Validator.isEmpty(that.get("modificar-valor3")) || that.get("modificar-valor3").autoNumericGet() == "0")) {
				Validator.focus(that.get("modificar-valor3"), "Debe ingresar un valor.");
				return;
			}
			var ABMExcedenteRequest = {
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
				url: "tipoDeCosteoABMExcedente.action",
				data: JSON.stringify({ABMExcedenteRequest : ABMExcedenteRequest}),//Component.serialize(buscarTipoDeCosteoRequest, "buscarTipoDeCosteoRequest"), 
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
		that.get("label-valor1").text("MINUTO DESDE:");
		that.get("label-valor2").text("MINUTO HASTA:");
		that.get("modificar-valor1").val(valor1);
		that.get("modificar-valor2").val(valor2);
		that.get("modificar-valor3").val(valor3);
		that.get("modificar-valor3").autoNumeric({vMax: '999999999.99', aDec:".", aSep: ''});
		that.get("div-popUp-modificacion").dialog("open");
		setTimeout(function(){
			Validator.focusOnly(that.get("modificar-valor3"));
			}, 2000);
	});
};
CosteoExcedenteEvent.prototype.eliminarRegistroABM = function(valor1, valor2, valor3){
	var that = this;
	console.log("Se eliminara el rgistro con valor1 : " + valor1 + ", valor2: "+ valor2 + ", valor3: "+ valor3);
	//p_accion Acción (I) Insertar, (U) Actualizar, (D) Eliminar
	//contrato, senial, nroGrupo, tipoTitulo, titulo, canje
	var ABMExcedenteRequest = {
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
		url: "tipoDeCosteoABMExcedente.action",
		data: JSON.stringify({ABMExcedenteRequest : ABMExcedenteRequest}),//Component.serialize(buscarTipoDeCosteoRequest, "buscarTipoDeCosteoRequest"), 
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
CosteoExcedenteEvent.prototype.agregarRegistroABM = function() {
	var that = this;
	if(Validator.isEmpty(that.get("minutoDesde"))||Validator.isEmpty(that.get("minutoHasta"))||Validator.isEmpty(that.get("valor"))){
		Validator.focus(that.get("minutoDesde"), "Debe ingresar un valor.");
		Validator.focus(that.get("minutoHasta"), "Debe ingresar un valor.");
		Validator.focus(that.get("valor"), "Debe ingresar un valor.");
		return;
	}
	if (!Validator.isEmpty(that.get("valor")) && (Validator.isEmpty(that.get("valor")) || that.get("valor").autoNumericGet() == "0")) {
		Validator.focus(that.get("valor"), "Debe ingresar un valor.");
		return;
	}
	var ABMExcedenteRequest = {
		contrato: that.contrato,
		senial: that.senial,
		nroGrupo : that.nroGrupo,
		tipoTitulo : that.tipoTitulo,
		titulo : that.titulo,
		canje : that.canje,
		accion : "I",
		valor1 : parseInt(that.get("minutoDesde").val()),
		valor2 : parseInt(that.get("minutoHasta").val()),
		valor3 : parseFloat(that.get("valor").val())
	};
	$.ajax({
		type: "POST",
		url: "tipoDeCosteoABMExcedente.action",
		data: JSON.stringify({ABMExcedenteRequest : ABMExcedenteRequest}),//Component.serialize(buscarTipoDeCosteoRequest, "buscarTipoDeCosteoRequest"), 
		dataType: "json",
		contentType: 'application/json; charset=utf-8'
	}).done(function(response){
		if(response=="OK"){
			MESSAGE.ok("El registro ha sido agregado.")
			that.get("minutoDesde").val("");
			that.get("minutoHasta").val("");
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

CosteoExcedenteEvent.prototype.buscarRegistros = function() {
	
	var self = this;
	var buscarGruposContratoRequest = {
		contrato: self.contrato,
		senial: self.senial,
		nroGrupo : self.nroGrupo
	};
	$.ajax({
		type: "POST",
		url: "tipoDeCosteoObtenerRegistrosExcedente.action",
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
CosteoExcedenteEvent.prototype.buscarRegistrosModificacion = function() {
	var self = this;
	var ABMExcedenteRequest = {
			contrato: self.contrato,
			senial: self.senial,
			nroGrupo : self.nroGrupo,
			tipoTitulo : self.tipoTitulo,
			titulo : self.titulo
	};
	$.ajax({
		type: "POST",
		url: "tipoDeCosteoObtenerRegistrosExcedenteABM.action",
		data: JSON.stringify({ABMExcedenteRequest : ABMExcedenteRequest}),//Component.serialize(buscarTipoDeCosteoRequest, "buscarTipoDeCosteoRequest"), 
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

CosteoExcedenteEvent.prototype.actualizarGrilla = function(data) {
	var that = this;
	that.get("tGrillaExcedente").clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};
CosteoExcedenteEvent.prototype.agregarRegistros = function() {
	var that = this;

	if(Validator.isEmpty(that.get("minutoDesde"))||Validator.isEmpty(that.get("minutoHasta"))||Validator.isEmpty(that.get("valor"))){
		Validator.focus(that.get("minutoDesde"), "Debe ingresar un valor.");
		Validator.focus(that.get("minutoHasta"), "Debe ingresar un valor.");
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
		minutoDesde : parseInt(that.get("minutoDesde").val()),
		minutoHasta : parseInt(that.get("minutoHasta").val()),
		valor : parseFloat(that.get("valor").val()),
		tipoContrato : $('input:radio[name='+that.prefijoId+'-radioButtonTipoContrato]:checked').val()
	};
	$.ajax({
		type: "POST",
		url: "tipoDeCosteoAgregarRegistrosExcedente.action",
		data: JSON.stringify({agregarRegistroRequest : agregarRegistroRequest}),//Component.serialize(buscarTipoDeCosteoRequest, "buscarTipoDeCosteoRequest"), 
		dataType: "json",
        contentType: 'application/json; charset=utf-8'
	}).done(function(response){
		if(response=="OK"){
			MESSAGE.ok("El registro ha sido agregado.")
			that.get("minutoDesde").val("");
			that.get("minutoHasta").val("");
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

