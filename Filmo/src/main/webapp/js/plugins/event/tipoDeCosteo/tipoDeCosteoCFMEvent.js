/*
 * 
 * 
 * 
 * 
 *  Event para el tipo de costeo por CFM
 *  
 *  
 *  
 *  
 */
function CostoFijoMensualEvent(divContainer, prefijoId, contrato, senial, nroGrupo,tipoTitulo, titulo, canje) {
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
extend(CostoFijoMensualEvent, Plugin);

CostoFijoMensualEvent.prototype.getDivContainer = function() {return $("#"+this.divContainer);};
CostoFijoMensualEvent.prototype.get = function(selector) {return $("#"+this.prefijoId+ "-" + selector);};

CostoFijoMensualEvent.init = function(tcosteoEvent) {
//	var divContainer = tcosteoEvent.divContainer;//utiliza el mismo div contenedor que el padre, para abrir de vuelta el popUp sobre el mismo div
	//utiliza el mismo div contenedor que el padre, para abrir de vuelta el popUp sobre el mismo div
	var cfmEvent = new CostoFijoMensualEvent(tcosteoEvent.divContainer,"CostoFijoMensualEvent", tcosteoEvent.contrato, tcosteoEvent.senial, tcosteoEvent.nroGrupo); 
	cfmEvent.getHtmlToDraw();
};

CostoFijoMensualEvent.initModificar = function(divContainer, contrato, senial, nroGrupo, tipoTitulo, titulo, canje) {
//	var divContainer = tcosteoEvent.divContainer;//utiliza el mismo div contenedor que el padre, para abrir de vuelta el popUp sobre el mismo div
	//utiliza el mismo div contenedor que el padre, para abrir de vuelta el popUp sobre el mismo div
	var cfmEvent = new CostoFijoMensualEvent(divContainer,"CostoFijoMensualEvent", contrato, senial, nroGrupo, tipoTitulo, titulo, canje); 
	cfmEvent.modificar = true;
	cfmEvent.getHtmlToDraw();
};


CostoFijoMensualEvent.prototype.getHtmlToDraw = function() {
	/* Bloquear pantalla */
	var that = this;
	BLOCK.showBlock("Cargando");
	Component.get("html/tipoDeCosteo/FijoMensual.html", $.proxy(this.drawPopup, this));
};	


CostoFijoMensualEvent.prototype.drawPopup = function(comp) {
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
	that.get("valorMes").autoNumeric({vMax: '999999999.99', aDec:".", aSep: ''});
	that.get("valorMes").autoNumericSet("0");
	that.get("anio").numeric();
	that.get("mes").numeric();
	if(that.modificar){
		that.get("div-radioButtonTipoContrado").hide();
		var canje = that.canje=="S"?"CANJE":"NO CANJE";
		that.get("span-tipoContrato").text(canje);
		that.drawGridModificar();
		that.get("buttonAgregarCFM").button().click($.proxy(that.agregarRegistroABM, that));
	}else{
		that.get("buttonAgregarCFM").button().click($.proxy(that.agregarRegistros, that));
		that.buscarDescripcionCanjeParaElGrupo();
		that.drawGrid();
	}
	that.getDivContainer().dialog("open");
	BLOCK.hideBlock();
};
CostoFijoMensualEvent.prototype.drawGrid = function() {
	var that = this;
	that.get("tGrillaCFM").jqGrid({
		height: 'auto',
		width : 730,
		datatype: 'local',
		colNames:['','','', 'Año Mes', 'Valor', ''],
		colModel:[ 
		    {name:'contrato', index:'nroGrupo', 			align:'center', hidden:true},
		    {name:'senial', index:'nroGrupo', 			align:'center', hidden:true},
		    {name:'nroGrupo', index:'nroGrupo', 			align:'center', hidden:true},
		    {name:'anioMes',	  index:'anioMes', 	align:'right', width: 200,  key:true},
		    {name:'valor',		index:'valor', 		align:'right', width: 590, formatter: 'currency'},
		    {name:'eliminarCFM',		index:'eliminarCFM',		align:'center', width: 20}
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + that.prefijoId + '-pagerGrillaCFM',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Registros',
        gridComplete: function() {
        	var ids = $(this).jqGrid('getDataIDs');
        	for (var i = 0; i < ids.length; i++) {
        		var rowId = ids[i];
        		var valor1 = that.get("tGrillaCFM").jqGrid("getCell", rowId, "anioMes");
        		var valor2 = that.get("tGrillaCFM").jqGrid("getCell", rowId, "valor");
        		var descripcion = that.get("tGrillaCFM").jqGrid("getCell", rowId, "valor");
        		var idObject = that.prefijoId+"-idLink-"+valor1;
        		var link = "<span id='"+idObject+"'  class='ui-icon ui-icon-trash' href=# style=\"color: blue; cursor: pointer;\" title=\"ELIMINAR REGISTRO\"></span>";
        		$(this).jqGrid('setRowData', rowId, { eliminarCFM: link });
        		$("#"+idObject).unbind("click").bind("click",$.proxy(that.eliminarRegistro, that, valor1, valor2));
        	}
        }
	});
	that.buscarRegistros();
};
CostoFijoMensualEvent.prototype.drawGridModificar = function() {
	var that = this;
	that.get("tGrillaCFM").jqGrid({
		height: 'auto',
		width : 730,
		datatype: 'local',
		colNames:['','','', 'Año Mes', 'Valor', '',''],
		colModel:[ 
		    {name:'contrato', index:'nroGrupo', 			align:'center', hidden:true},
		    {name:'senial', index:'nroGrupo', 			align:'center', hidden:true},
		    {name:'nroGrupo', index:'nroGrupo', 			align:'center', hidden:true},
		    {name:'anioMes',	  index:'anioMes', 	align:'right', width: 200,  key:true},
		    {name:'valor',		index:'valor', 		align:'right', width: 590, formatter: 'currency'},
			{name:'ABMmodificar',		index:'ABMmodificar',		align:'center', width: 70},
			{name:'ABMeliminar',		index:'ABMeliminar',		align:'center', width: 70}
			],
			rowNum: 10,
			rowList:[10,20,30],
			scrollOffset: 0,
			pager: '#' + that.prefijoId + '-pagerGrillaCFM',
			viewrecords: true, 
			loadonce: true,
			editurl: 'clientArray', 
			caption: 'Registros',
			gridComplete: function() {
				var ids = $(this).jqGrid('getDataIDs');
				for (var i = 0; i < ids.length; i++) {
					var rowId = ids[i];
					var valor1 = that.get("tGrillaCFM").jqGrid("getCell", rowId, "anioMes");
					var anio = "";
					var mes = "";
					if(valor1.length>0){
						if(valor1.length==5){alert("El campo anioMes debe tener 6 caracteres");}
						if(valor1.length==6){anio=valor1.substr(0,4);mes=valor1.substr(4,2);}
						console.log("anio: " + anio + " , mes: " + mes);
					}
					var valor3 = that.get("tGrillaCFM").jqGrid("getCell", rowId, "valor");
					var descripcion = that.get("tGrillaCFM").jqGrid("getCell", rowId, "valor");
					var idObjectE = that.prefijoId+"-idLinkE-"+valor1;
					var idObjectM = that.prefijoId+"-idLinkM-"+valor1;
					var linkE = "<span id='"+idObjectE+"'  class='ui-icon ui-icon-trash conTooltip' href=# style=\"color: blue; cursor: pointer; margin:auto;\" title=\"ELIMINAR REGISTRO\"></span>";
					var linkM = "<span id='"+idObjectM+"'  class='ui-icon ui-icon-pencil conTooltip' href=# style=\"color: blue; cursor: pointer; margin:auto;\" title=\"MODIFICAR REGISTRO\"></span>";
					$(this).jqGrid('setRowData', rowId, { ABMeliminar: linkE });
					$(this).jqGrid('setRowData', rowId, { ABMmodificar: linkM });
					$("#"+idObjectE).unbind("click").bind("click",$.proxy(that.eliminarRegistroABM, that, anio, mes, valor3));
					$("#"+idObjectM).unbind("click").bind("click",$.proxy(that.modificarRegistroABM, that, anio, mes, valor3));
				}
			}
	});
	that.buscarRegistrosModificacion();
};
CostoFijoMensualEvent.prototype.agregarRegistroABM = function() {
	var that = this;
	
	if(Validator.isEmpty(that.get("valorMes"))||Validator.isEmpty(that.get("anio"))||Validator.isEmpty(that.get("mes"))){
		Validator.focus(that.get("valorMes"), "Debe ingresar un valor.");
		Validator.focus(that.get("mes"), "Debe ingresar un valor.");
		Validator.focus(that.get("anio"), "Debe ingresar un valor.");
		return;
	}
	if (!Validator.isEmpty(that.get("valorMes")) && (Validator.isEmpty(that.get("valorMes")) || that.get("valorMes").autoNumericGet() == "0")) {
		Validator.focus(that.get("valorMes"), "Debe ingresar un valor.");
		return;
	}
	
	var ABMRequest = {
			contrato: that.contrato,
			senial: that.senial,
			nroGrupo : that.nroGrupo,
			tipoTitulo : that.tipoTitulo,
			titulo : that.titulo,
			canje : that.canje,
			accion : "I",
			mes : parseInt(that.get("mes").val()),
			anio : parseInt(that.get("anio").val()),
			valor : parseFloat(that.get("valorMes").val())
	};
	$.ajax({
		type: "POST",
		url: "tipoDeCosteoABMFijo.action",
		data: JSON.stringify({ABMRequest : ABMRequest}),//Component.serialize(buscarTipoDeCosteoRequest, "buscarTipoDeCosteoRequest"), 
		dataType: "json",
		contentType: 'application/json; charset=utf-8'
	}).done(function(response){
		if(response=="OK"){
			MESSAGE.ok("El registro ha sido agregado.")
			that.get("valorMes").val("");
			that.get("mes").val("");
			that.get("anio").val("");
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
CostoFijoMensualEvent.prototype.modificarRegistroABM = function(valor1, valor2, valor3){
	var that = this;
	that.get("div-popUp-modificacion").load("html/tipoDeCosteo/ModificarRegistro.html", function() {
		that.get("div-popUp-modificacion").dialog({
			width: 770, show: 'blind', hide: 'blind', modal: true, autoOpen: false
		});
		that.get("div-popUp-modificacion").html(that.get("div-popUp-modificacion").html().replace(/{{id}}/g, that.prefijoId));
		that.get("label-valor1").text("AÑO :");
		that.get("label-valor2").text("MES :");
		that.get("modificar-valor1").val(valor1);//anio
		that.get("modificar-valor2").val(valor2);//mes
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
			var ABMRequest = {
				contrato: that.contrato,
				senial: that.senial,
				nroGrupo : that.nroGrupo,
				tipoTitulo : that.tipoTitulo,
				titulo : that.titulo,
				canje : that.canje,
				accion : "U",
				anio : parseInt(that.get("modificar-valor1").val()),
				mes : parseInt(that.get("modificar-valor2").val()),
				valor : parseFloat(that.get("modificar-valor3").val())
			};
			$.ajax({
				type: "POST",
				url: "tipoDeCosteoABMFijo.action",
				data: JSON.stringify({ABMRequest : ABMRequest}),//Component.serialize(buscarTipoDeCosteoRequest, "buscarTipoDeCosteoRequest"), 
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
			}, 2000);
	});
};
CostoFijoMensualEvent.prototype.eliminarRegistroABM = function(valor1, valor2, valor3){
	var that = this;
	console.log("Se eliminara el rgistro con anio : " + valor1 + ", mes: "+ valor2 + ", valor3 : "+ valor3);
	//p_accion Acción (I) Insertar, (U) Actualizar, (D) Eliminar
	//contrato, senial, nroGrupo, tipoTitulo, titulo, canje
	var ABMRequest = {
			contrato: that.contrato,
			senial: that.senial,
			nroGrupo : that.nroGrupo,
			tipoTitulo : that.tipoTitulo,
			titulo : that.titulo,
			canje : that.canje,
			accion : "D",
			anio : parseInt(valor1),
			mes : parseInt(valor2),
			valor : parseFloat(valor3)
	};
	$.ajax({
		type: "POST",
		url: "tipoDeCosteoABMFijo.action",
		data: JSON.stringify({ABMRequest : ABMRequest}),//Component.serialize(buscarTipoDeCosteoRequest, "buscarTipoDeCosteoRequest"), 
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
};

CostoFijoMensualEvent.prototype.buscarDescripcionCanjeParaElGrupo = function() {
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

CostoFijoMensualEvent.prototype.eliminarRegistro = function(valor1, valor3){
	var that = this;
	console.log("Se eliminara el rgistro con valor1 : " + valor1 + ", valor3: "+ valor3);
	var eliminarRegistroRequest = {
			contrato: that.contrato,
			senial: that.senial,
			nroGrupo : that.nroGrupo,
			valor1 : valor1,
			valor3 : valor3
		};
		$.ajax({
			type: "POST",
			url: "tipoDeCosteoEliminarRegistrosCFM.action",
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

CostoFijoMensualEvent.prototype.buscarRegistros = function() {
	
	var self = this;
	var buscarGruposContratoRequest = {
		contrato: self.contrato,
		senial: self.senial,
		nroGrupo : self.nroGrupo
	};
	$.ajax({
		type: "POST",
		url: "tipoDeCosteoObtenerRegistrosCFM.action",
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
CostoFijoMensualEvent.prototype.buscarRegistrosModificacion = function() {
	var self = this;
	var ABMRequest = {
			contrato: self.contrato,
			senial: self.senial,
			nroGrupo : self.nroGrupo,
			tipoTitulo : self.tipoTitulo,
			titulo : self.titulo
	};
	$.ajax({
		type: "POST",
		url: "tipoDeCosteoObtenerRegistrosABM.action",
		data: JSON.stringify({ABMRequest : ABMRequest}),//Component.serialize(buscarTipoDeCosteoRequest, "buscarTipoDeCosteoRequest"), 
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
CostoFijoMensualEvent.prototype.actualizarGrilla = function(data) {
	var that = this;
	that.get("tGrillaCFM").clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};
CostoFijoMensualEvent.prototype.agregarRegistros = function() {
	var that = this;

	if(Validator.isEmpty(that.get("valorMes"))||Validator.isEmpty(that.get("anio"))||Validator.isEmpty(that.get("mes"))){
		Validator.focus(that.get("valorMes"), "Debe ingresar un valor.");
		Validator.focus(that.get("mes"), "Debe ingresar un valor.");
		Validator.focus(that.get("anio"), "Debe ingresar un valor.");
		return;
	}
	if (!Validator.isEmpty(that.get("valorMes")) && (Validator.isEmpty(that.get("valorMes")) || that.get("valorMes").autoNumericGet() == "0")) {
		Validator.focus(that.get("valorMes"), "Debe ingresar un valor.");
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
		mes : parseInt(that.get("mes").val()),
		anio : parseInt(that.get("anio").val()),
		valor : parseFloat(that.get("valorMes").val()),
		tipoContrato : $('input:radio[name='+that.prefijoId+'-radioButtonTipoContrato]:checked').val()
	};
	$.ajax({
		type: "POST",
		url: "tipoDeCosteoAgregarRegistrosCFM.action",
		data: JSON.stringify({agregarRegistroRequest : agregarRegistroRequest}),//Component.serialize(buscarTipoDeCosteoRequest, "buscarTipoDeCosteoRequest"), 
		dataType: "json",
        contentType: 'application/json; charset=utf-8'
	}).done(function(response){
		if(response=="OK"){
			MESSAGE.ok("El registro ha sido agregado.")
			that.get("valorMes").val("");
			that.get("mes").val("");
			that.get("anio").val("");
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


