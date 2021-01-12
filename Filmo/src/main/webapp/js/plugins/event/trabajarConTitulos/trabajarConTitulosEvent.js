TrabajarConTitulosEvent.CASTELLANO = "castellano";
TrabajarConTitulosEvent.ORIGINAL = "original";
TrabajarConTitulosEvent.ADD = "ALTA";
TrabajarConTitulosEvent.UPDATE = "MODIFICACION";
function TrabajarConTitulosEvent(div) {
	this.div = div;
	this.tipoTitulo = TrabajarConTitulosEvent.CASTELLANO;
	this.tipoFicha = TrabajarConTitulosEvent.ORIGINAL;
};
extend(TrabajarConTitulosEvent, Plugin);
TrabajarConTitulosEvent.prototype.create = function() {
	var self = trabajarConTitulosEvent;
	self.service = new TrabajarConTitulosService();
	Component.get("html/trabajarConTitulos/listadoTitulos.html", trabajarConTitulosEvent.draw);
};

TrabajarConTitulosEvent.prototype.draw = function(comp) {
	var self = trabajarConTitulosEvent;
	$("#viewContainer").empty().append(comp.replace(/{{id}}/g, self.div.id)
			.replace(/{{tipoBusquedaTitulo}}/g, self.tipoTitulo));
	Accordion.getInstance(self.getAccordionListadoTitulos());
	Component.populateSelect(self.getSelector("senial"), seniales, "codigo", "descripcion");
	/*self.getSelector("senial").change(function() {
		if (!Validator.isEmpty(self.getSelector("senial"))) {
			self.getSelector("filtro").show();
			self.senialSeleccionada = $(this).val();
			self.getInputBusquedaTitulosDescripcion().focus();
		}else{
			self.getSelector("filtro").hide();
		}
	});*/
	self.getACambiarTipoBusqueda().click(self.cambiarTipoBusqueda);
	self.getAAgregarTitulo().click(self.agregarTitulo);
	self.getButtonBuscarTitulosPorDesc().button().click(self.buscarTitulosPorDescripcion);
	self.getButtonBuscarTitulosPorCodigo().button().click(self.buscarTitulosPorCodigo);
	self.getInputBusquedaTitulosDescripcion().keypress(function (event) {
		if (event.which == 13) {
			event.preventDefault();
			self.buscarTitulosPorDescripcion();
			return;
		}
	});
	self.getInputBusquedaTituloCodigo().keypress(function (event) {
		if (event.which == 13) {
			event.preventDefault();
			self.buscarTitulosPorCodigo();
			return;
		}
	});
};

TrabajarConTitulosEvent.prototype.drawFormABMTitulo = function(comp) {
	var self = trabajarConTitulosEvent;
	self.getViewListadoTitulos().hide();
	comp = comp.replace(/{{id}}/g, self.div.id)
			   .replace(/{{senial}}/g, $("#senialDefaultUsuario").val())
			   .replace(/{{operacionTitulo}}/g, (self.operacion == TrabajarConTitulosEvent.ADD ? "Alta" : "Modificación"))
			   .replace(/{{tipoBusquedaFicha}}/g, self.tipoFicha);
	if (self.operacion === TrabajarConTitulosEvent.UPDATE) {
		comp = comp.replace(/{{clave}}/g, self.rowSelected.clave);
		self.service.obtenerTitulo($("#senialDefaultUsuario").val(), self.rowSelected);
	}
	if (self.getViewABMTitulos().length) {
		self.getViewABMTitulos().replaceWith(comp);
	}else{
		self.getDiv().append(comp);
	}
	
	self.getViewABMTitulos().accordion({
		collapsible: false,
		active: 0,
		autoHeight: false,
		icons: false
	});
	
	self.getAccordionDivActores().accordion({
		collapsible: false,
		active: 0,
		autoHeight: false,
		icons: false
	});
	
	if (self.operacion === TrabajarConTitulosEvent.ADD) {
		self.getButtonPopupFicha().show().button().click(self.openPopupFichas);
		self.getPopupFicha().dialog({
			title : 'Situar en Ficha Cinematografica',
			width: 600,
			show: 'blind',
			hide: 'blind',
			modal: true,
			autoOpen: false,
			position: 'center',
			buttons: {
				"Salir": function() {
					$( this ).dialog( "close" );
				}
			}
		});
	}
	self.getButtonAceptarFormABMTitulo().button().click(self.aceptarABMTitulo);
	self.getButtonCancelarFormABMTitulo().button().click(self.cancelarABMTitulo);
	self.getButtonPopupTipoTitulo().click(self.popupTipoTitulo);
	self.getButtonPopupCalifOficial().click(self.popupCalifOficial);
	self.getFormABMTitulo().validate({
		rules : {
			"titulo.tipoTitulo" : "required",
			"titulo.tituloOriginal" : "required",
			"titulo.tituloCastellano" : "required",
			"titulo.calificacionOficial" : "required"
		},
		submitHandler : function(form) {
			return false;
		},
		errorPlacement : function(error, element) {
		},
		invalidHandler: function(form, validator) {
			var errors = validator.numberOfInvalids();
		    if (errors) {
		    	var message = errors == 1
		    		? 'Te falta completar un campo requerido. Ha sido resaltado.'
		    				: 'Te faltan completar ' + errors + ' campos requeridos. Han sido resaltados.';
		    	MESSAGE.alert(message);
		    }
		},
		errorClass : 'invalid'
	});
	$(".add-inputActor").click(self.addInputActor);
	
	if (self.operacion === TrabajarConTitulosEvent.UPDATE){
		$("#"+self.div.id+"_tipoTituloCodigo").prop('disabled', true);
		$("#"+self.div.id+"_popupTipoTitulo").attr("Style", "cursor: pointer; display: none");
		
	}else{
		$("#"+self.div.id+"_tipoTituloCodigo").prop('disabled', false);
		$("#"+self.div.id+"_popupTipoTitulo").attr("Style", "cursor: pointer; display: ");
	}
};

TrabajarConTitulosEvent.prototype.addInputActor = function() {
	if (!$(".remove-inputActor").length) {
		$(".table-actors tr:first").append('<td><span class="remove-inputActor ui-icon ui-icon-minusthick" style="cursor: pointer;" onclick="trabajarConTitulosEvent.removeInputActor()">-</span></td>'); 
	}
	$(".table-actors").append('<tr><th><td><label>Actor:</label></td><td><input type="text" name="titulo.actores.actor"/></td></th><th><td><label>Personaje:</label></td><td><input type="text" name="titulo.actores.personaje"/></td></th>'); 
	
};
TrabajarConTitulosEvent.prototype.removeInputActor = function() {
	$('.table-actors tr:last').remove();
	if ($(".table-actors tr").length < 3) {
		$(".remove-inputActor").remove();
	}
};
TrabajarConTitulosEvent.prototype.cancelarABMTitulo = function() {
	trabajarConTitulosEvent.getViewABMTitulos().hide();
	trabajarConTitulosEvent.getViewListadoTitulos().show();
};
TrabajarConTitulosEvent.prototype.cambiarTipoBusqueda = function() {
	var self = trabajarConTitulosEvent;
	if (self.tipoTitulo === TrabajarConTitulosEvent.CASTELLANO) {
		self.tipoTitulo = TrabajarConTitulosEvent.ORIGINAL;
	}else{
		self.tipoTitulo = TrabajarConTitulosEvent.CASTELLANO;
	}
	$("#"+self.div.id+"_tipoBusqueda").text(self.tipoTitulo);
	self.getInputBusquedaTitulosDescripcion().val("");
	self.getInputBusquedaTituloCodigo().val("");
	self.getGridTitulos().clearGridData();
};

TrabajarConTitulosEvent.prototype.buscarTitulos = function() {
	var self = trabajarConTitulosEvent;
	var descripcion = self.getInputBusquedaTitulosDescripcion().val();
	var codigo = self.getInputBusquedaTituloCodigo().val();
	if (descripcion && descripcion !== "") {
		self.buscarTitulosPorDescripcion();
	}else if (codigo && codigo !== "") {
		self.buscarTitulosPorCodigo();
	}
};

TrabajarConTitulosEvent.prototype.buscarTitulosPorDescripcion = function() {
	var self = trabajarConTitulosEvent;
	var descripcion = self.getInputBusquedaTitulosDescripcion().val();
	if (!descripcion || descripcion === "") {
		return;
	}
	var data = {
		descripcion : descripcion,
		senial : $("#senialDefaultUsuario").val()
	};
	self.service.getTitulosPorDescripcion(data, self.tipoTitulo);
};

TrabajarConTitulosEvent.prototype.buscarTitulosPorCodigo = function() {
	var self = trabajarConTitulosEvent;
	var codigo = self.getInputBusquedaTituloCodigo().val();
	if (!codigo || codigo === "") {
		return;
	}
	var data = {
		codigo : codigo,
		senial : $("#senialDefaultUsuario").val()
	};
	self.service.getTitulosPorCodigo(data, self.tipoTitulo);
};

TrabajarConTitulosEvent.prototype.responseTitulos = function(data) {
	this.drawGrigTitulos(data);
};
TrabajarConTitulosEvent.prototype.drawGrigTitulos = function(data) {
	this.getGridTitulos().jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Clave','Titulo','Actores','','CRT','MAT', "", ""],
		colModel:[ 
			{name:'clave',index:'clave', width:150, sortable:false}, 
			{name:'titulo',index:'titulo', width:300, sortable:false}, 
			{name:'actores',index:'actores', width:300, sortable:false}, 
			{name:'signo',index:'signo', width:30, sortable:false}, 
			{name:'contrato',index:'contrato', width:30, sortable:false}, 
			{name:'recibido',index:'recibido', width:30, sortable:false}, 
			{name:'modificarTitulo',index:'modificarTitulo', width:30, aling:'center', sortable:false},
			{name:'modificarTituloContrato',index:'modificarTituloContrato', width:30, aling:'center', sortable:false}
		],
		rowNum: 20,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#TrabajarConTitulosEventId_pagerGridTitulos',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Titulos',
		gridComplete: function() {
        	var ids = $(this).jqGrid('getDataIDs');
        	for (var i = 0; i < ids.length; i++) {
        		var rowId = ids[i];
        		var contrato =  $(this).jqGrid('getRowData', rowId).contrato;
        		if (contrato === "S") {
        			var modificar = "<div onclick='trabajarConTitulosEvent.modificarTituloContrato("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-pencil modificar conTooltip'>Modificar Titulo Contratado</span></div>";
        			$(this).jqGrid('setRowData', rowId, { modificarTituloContrato: modificar });
				}else{
					var modificar = "<div onclick='trabajarConTitulosEvent.modificarTitulo("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-pencil modificar conTooltip'>Modificar Titulo</span></div>";
					$(this).jqGrid('setRowData', rowId, { modificarTitulo: modificar });
				}
        	}
	    }
	});
	this.getGridTitulos().clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};

TrabajarConTitulosEvent.prototype.agregarTitulo = function() {
	var self = trabajarConTitulosEvent;
	self.operacion = TrabajarConTitulosEvent.ADD;
	self.getCompABMTitulo();
};
TrabajarConTitulosEvent.prototype.modificarTitulo = function(rowId) {
	var self = trabajarConTitulosEvent;
	self.rowSelected = self.getGridTitulos().jqGrid('getRowData', rowId);
	self.operacion = TrabajarConTitulosEvent.UPDATE;
	self.getCompABMTitulo();
};
TrabajarConTitulosEvent.prototype.getCompABMTitulo = function() {
	Component.get("html/trabajarConTitulos/formABMTitulo.html", trabajarConTitulosEvent.drawFormABMTitulo);
};
TrabajarConTitulosEvent.prototype.aceptarABMTitulo = function() {
	var self = trabajarConTitulosEvent;
	if (self.getFormABMTitulo().valid()) {
		var data = self.getFormABMTitulo().serialize();
		if (self.operacion === TrabajarConTitulosEvent.UPDATE){
			data = data +
			"&titulo.tipoTitulo="+$("#"+self.div.id+"_tipoTituloCodigo").val();						
		}
		
		self.service.abmTitulo(data, self.operacion);
	}
};
TrabajarConTitulosEvent.prototype.completeFormForUpdate = function(data) {
	this.operacion === TrabajarConTitulosEvent.ADD ? $("#"+this.div.id+"_claveTitulo").hide() :
	$("#"+this.div.id+"_claveTitulo").show();
	$("#"+this.div.id+"_tipoTituloCodigo").val(data.codigoTipo);
	$("#"+this.div.id+"_tipoTituloDesc").text(data.descripcionTipo?data.descripcionTipo:"");
	$("#"+this.div.id+"_tituloOriginal").val(data.tituloOriginal.trim());
	$("#"+this.div.id+"_tituloCastellano").val(data.tituloCastellano.trim());
	$("#"+this.div.id+"_calificacionOficialCodigo").val(data.codigoCalificacion);
	$("#"+this.div.id+"_calificacionOficialDesc").text(data.descripcionCalificacion?data.descripcionCalificacion:"");
	for ( var i in data.actores) {
		var actor = data.actores[i];
		if (i == 0 || i == 1) {
			var numero = parseInt(i)+parseInt(1);
			$("#"+this.div.id+"_actor"+numero).val(actor.actor.trim());
			$("#"+this.div.id+"_personaje"+numero).val(actor.personaje.trim());
		}else{
			$(".table-actors").append('<tr><th><td><label>Actor:</label></td><td><input type="text" name="titulo.actores.actor" value="'+actor.actor.trim()+'"/></td></th>'+
					'<th><td><label>Personaje:</label></td><td><input type="text" name="titulo.actores.personaje" value="'+actor.personaje.trim()+'"/></td></th>'); 
		}
	}
};
TrabajarConTitulosEvent.prototype.modificarTituloContrato = function(rowId) {
	var self = trabajarConTitulosEvent;
	self.rowSelected = self.getGridTitulos().jqGrid('getRowData', rowId);
	
	situarPopupEvent.reset();
	situarPopupEvent.nombreSituar = "Situar Contratos";
	situarPopupEvent.acciones = [{
		callback : function(data) {
            self.contratoSeleccionado = data;
			self.service.getContrato(data);
		},
		nombre : "Seleccionar",
		imagen : "check"
	},{
		callback : function(data) {
			self.contratoSeleccionado = data;
			self.service.cargarDetalleContrato(data);
		},
		nombre : "Visualizar",
		imagen : "document"
	}];
	situarPopupEvent.create(
	        "obtenerContratos.action", 
	        function(data) { self.service.getContrato($("#senialDefaultUsuario").val(), data); }, 
	        {codigoContrato : "", clave : self.rowSelected.clave, senial : $("#senialDefaultUsuario").val()}, "contratos");
};
TrabajarConTitulosEvent.prototype.drawContrato = function(contrato) {
    var self = trabajarConTitulosEvent;
    modificarContratoInit.init();
    
    ModificarContratoStaticService.doRequest(
            {action:"estaBloqueado.action",
                request: {claveContrato: contrato.codigo},
                method: "GET",
                callback: function(estaBloqueado) {
                    if (!estaBloqueado) {
                        ModificarContratoStaticService.doRequest(
                                {action:"bloquearContrato.action",
                                    request: {claveContrato: contrato.codigo},
                                    method: "GET",
                                    callback: function(data) {
                                        ModificarContratoStaticService.doRequest(
                                                {action:"dameContratoConCabecera.action",
                                                    request: {claveContrato: contrato.codigo, claveDistribuidor: contrato.proveedor},
                                                    method: "GET",
                                                    callback: function(data) {
                                                        if (data && data.length != undefined && data.length > 0) {
                                                            modificarContratoBusiness.contratoConCabecera = data[0];
                                                            modificarContratoBusiness.contratoConCabecera.grupoElegido = self.contratoSeleccionado.grupo;
                                                            modificarContratoBusiness.contratoConCabecera.senialElegida = contrato.senial_cont;
                                                            //modificarContratoBusiness.contratoConCabecera.senialElegida = variablesSesionUsuarioEvent.dameSenialSeleccionada();
                                                            modificarContratoDrawer.drawPopUpGrupo.modificaCantidad = undefined;
                                                            modificarContratoDrawer.drawPopUpTituloConGrupo.row = {};
                                                            modificarContratoDrawer.drawPopUpTituloConGrupo.row.clave = self.rowSelected.clave;
                                                            modificarContratoBusiness.trabjarConVigenciasDesdeMaestros = false;
                                                            if (contrato.perpetuidad === "N") {
                                                                //("Aca se debe llamar al MODIFICACION DE CONTRATO, como modo de trabajo. Perpetuidad = 'N'")
                                                                modificarContratoBusiness.trabjarConVigenciasDesdeMaestros = true;
                                                                modificarContratoBusiness.trabjarConVigencias.modo = "T";
                                                                modificarContratoDrawer.drawPopUpTituloConGrupo.modo = "T";
                                                            } else {
                                                                //("Aca se debe llamar al MODIFICACION DE CONTRATO, como modo de consulta. Perpetuidad = 'Distinto de N'")
                                                                modificarContratoBusiness.trabjarConVigencias.modo = "C";
                                                                modificarContratoDrawer.drawPopUpTituloConGrupo.modo = "C";
                                                            }
                                                            //modificarContratoBusiness.contratoConCabecera.grupoElegido = row.nroGrupo;
                                                            Component.get("html/modificarContrato/PopUpGrupo.html", modificarContratoDrawer.drawPopUpGrupo);
                                                            //Component.get("html/modificarContrato/PopUpTituloConGrupo.html", modificarContratoDrawer.drawPopUpTituloConGrupo);
                                                        } else {
                                                            MESSAGE.error("Ocurrio un error al obtener el contrato");
                                                        }
                                                    },
                                                    responseObject: "contratos"});
                                    } 
                                 });
                    } else {
                        MESSAGE.error("El contrato bloqueado");
                    }
                },
                responseObject: "estaBloqueado"});
};
TrabajarConTitulosEvent.prototype.popupTipoTitulo = function() {
    situarPopupEvent.reset();
	situarPopupEvent.nombreSituar = "Situar Tipo Titulos";
	situarPopupEvent.create("obtenerTiposDeTitulos.action", trabajarConTitulosEvent.tipoTituloSelected, {descripcion : ""}, "tiposDeTitulos");
};
TrabajarConTitulosEvent.prototype.tipoTituloSelected = function(row) {
	$("#"+trabajarConTitulosEvent.div.id+"_tipoTituloCodigo").val(row.codigo);
	$("#"+trabajarConTitulosEvent.div.id+"_tipoTituloDesc").text(row.descripcion);
};
TrabajarConTitulosEvent.prototype.popupCalifOficial = function() {
    situarPopupEvent.reset();
	situarPopupEvent.nombreSituar = "Situar Calificaciones Oficiales";
	situarPopupEvent.create("obtenerCalificacionesOficiales.action", trabajarConTitulosEvent.califOficialSelected, {descripcion : ""}, "calificacionesOficiales");
};
TrabajarConTitulosEvent.prototype.califOficialSelected = function(row) {
	$("#"+trabajarConTitulosEvent.div.id+"_calificacionOficialCodigo").val(row.codigo);
	$("#"+trabajarConTitulosEvent.div.id+"_calificacionOficialDesc").text(row.descripcion);
};
TrabajarConTitulosEvent.prototype.openPopupFichas = function(fichas) {
	var self = trabajarConTitulosEvent;
	if (!self.getPopupFicha().dialog("isOpen")) {
		self.getPopupFicha().dialog("open");
	}
	$("#"+self.div.id+"_cambiarOrigenBusquedaFicha").click(self.cambiarOrigenFicha);
	$("#"+self.div.id+"_buttonBusquedaSituarFicha").button().click(self.buscarFichas);
	$("#"+self.div.id+"_inputBusquedaSituarFicha").keypress(function (event) {
		if (event.which == 13) {
			event.preventDefault();
			self.buscarFichas();
		}
	});
	if (fichas && fichas.length) {
		self.drawGrigFichas(fichas);
	} else {
		self.getGridFichas().clearGridData();
	}
};
TrabajarConTitulosEvent.prototype.drawGrigFichas = function(data) {
	this.getGridFichas().jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['', 'Titulo','',''],
		colModel:[ {name:'codigo',index:'codigo', width:150, sortable:false, hidden: true}, 
		           {name:'descripcion',index:'descripcion', width:150, sortable:false}, 
		           {name:'seleccionar',index:'seleccionar', width:30, sortable:false}, 
		           {name:'visualizar',index:'visualizar', width:30, sortable:false}],
		rowNum: 20,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#TrabajarConTitulosEventId_pagerGridSituarFicha',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Situar en Ficha',
		gridComplete: function() {
        	var ids = $(this).jqGrid('getDataIDs');
        	for (var i in ids) {
        		var rowId = ids[i];
       			var seleccionar = "<div onclick='trabajarConTitulosEvent.seleccionarFicha("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-document seleccionar conTooltip'>Seleccionar Ficha</span></div>";
       			$(this).jqGrid('setRowData', rowId, { seleccionar: seleccionar });
				var visualizar = "<div onclick='trabajarConTitulosEvent.visualizarFicha("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-search visualizar conTooltip'>Visualizar Ficha</span></div>";
				$(this).jqGrid('setRowData', rowId, { visualizar: visualizar });
        	}
	    }
		
	});
	this.getGridFichas().clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};
TrabajarConTitulosEvent.prototype.buscarFichas = function(value) {
	var desc =  $("#" + trabajarConTitulosEvent.div.id + "_inputBusquedaSituarFicha").val();
	trabajarConTitulosEvent.service.getFichasCinematograficas(desc, trabajarConTitulosEvent.tipoFicha);
};
TrabajarConTitulosEvent.prototype.seleccionarFicha = function(rowId) {
	var codigo = this.getGridFichas().jqGrid('getRowData', rowId).codigo;
	this.service.getFicha(codigo);
	this.getPopupFicha().dialog("close");
};
TrabajarConTitulosEvent.prototype.visualizarFicha = function(rowId) {
	alert("Se debe redirigir al modulo de cinematografica");
};
TrabajarConTitulosEvent.prototype.cambiarOrigenFicha = function() {
	var self = trabajarConTitulosEvent;
	if (self.tipoFicha === TrabajarConTitulosEvent.CASTELLANO) {
		self.tipoFicha = TrabajarConTitulosEvent.ORIGINAL;
	}else{
		self.tipoFicha = TrabajarConTitulosEvent.CASTELLANO;
	}
	$("#"+self.div.id+"_origenBusquedaFicha").text(self.tipoFicha);
	$("#"+self.div.id+"_inputBusquedaSituarFicha").val("");
	self.getGridFichas().clearGridData();
};
TrabajarConTitulosEvent.prototype.visualizarContrato = function(data) {
	var self = trabajarConTitulosEvent;
	Component.get("html/trabajarConTitulos/visualizarContrato.html", function(comp) {
		self.getSelector("listadoTitulos").hide();
		comp = comp.replace(/{{id}}/g, self.div.id).replace(/{{contrato}}/g, self.contratoSeleccionado.codigo);
		for ( var key in data) {
			comp = comp.replace("{{"+key+"}}", data[key]);
		}
		if (self.getSelector("visualizarContrato").length) {
			self.getSelector("visualizarContrato").replaceWith(comp);
		}else{
			self.getDiv().append(comp);
		}
		Accordion.getInstance(self.getSelector("visualizarContrato"));
		self.getSelector("volverDeVisualizacion").button().click(function() {
			self.getSelector("visualizarContrato").hide();
			self.getSelector("listadoTitulos").show();
		});
	});
};
TrabajarConTitulosEvent.prototype.getViewListadoTitulos = function() {
	return $("#" + this.div.id + "_listadoTitulos");
};
TrabajarConTitulosEvent.prototype.getViewABMTitulos = function() {
	return $("#" + this.div.id + "_ABMTitulos");
};
TrabajarConTitulosEvent.prototype.getAccordionListadoTitulos = function() {
	return $("#" + this.div.id + "_accordionListadoTitulos");
};
TrabajarConTitulosEvent.prototype.getButtonBuscarTitulosPorDesc = function() {
	return $("#" + this.div.id + "_buscarTitulosPorDescripcion");
};
TrabajarConTitulosEvent.prototype.getButtonBuscarTitulosPorCodigo = function() {
	return $("#" + this.div.id + "_buscarTituloPorCodigo");
};
TrabajarConTitulosEvent.prototype.getButtonAceptarFormABMTitulo = function() {
	return $("#" + this.div.id + "_aceptarFormABMTitulo");
};
TrabajarConTitulosEvent.prototype.getButtonCancelarFormABMTitulo = function() {
	return $("#" + this.div.id + "_cancelarFormABMTitulo");
};
TrabajarConTitulosEvent.prototype.getInputBusquedaTitulosDescripcion = function() {
	return $("#" + this.div.id + "_inputBusquedaTitulosDescripcion");
};
TrabajarConTitulosEvent.prototype.getInputBusquedaTituloCodigo = function() {
	return $("#" + this.div.id + "_inputBusquedaTituloCodigo");
};
TrabajarConTitulosEvent.prototype.getGridTitulos = function() {
	return $("#" + this.div.id + "_gridTitulos");
};
TrabajarConTitulosEvent.prototype.getAAgregarTitulo = function() {
	return $("#" + this.div.id + "_agregarTitulo");
};
TrabajarConTitulosEvent.prototype.getACambiarTipoBusqueda = function() {
	return $("#" + this.div.id + "_cambiarTipoTitulo");
};
TrabajarConTitulosEvent.prototype.getFormABMTitulo = function() {
	return $("#" + this.div.id + "_formABMTitulo");
};
TrabajarConTitulosEvent.prototype.getAccordionDivActores = function() {
	return $("#" + this.div.id + "_divActores");
};
TrabajarConTitulosEvent.prototype.getButtonPopupTipoTitulo = function() {
	return $("#" + this.div.id + "_popupTipoTitulo");
};
TrabajarConTitulosEvent.prototype.getButtonPopupCalifOficial = function() {
	return $("#" + this.div.id + "_popupCalifOficial");
};
TrabajarConTitulosEvent.prototype.getButtonPopupFicha = function() {
	return $("#" + this.div.id + "_popupFichaCinematografica");
};
TrabajarConTitulosEvent.prototype.getPopupFicha = function() {
	return $("#" + this.div.id + "_popupSituarFicha");
};
TrabajarConTitulosEvent.prototype.getGridFichas = function() {
	return $("#" + this.div.id + "_gridSituarFicha");
};
var trabajarConTitulosEvent = new TrabajarConTitulosEvent(new DivDefinition('TrabajarConTitulosEventId'));