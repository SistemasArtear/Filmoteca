TrabajarConFichasDeChequeoTecnicoEvent.busquedaPorClave = "C";
TrabajarConFichasDeChequeoTecnicoEvent.busquedaPorTitOriginal = "O";
TrabajarConFichasDeChequeoTecnicoEvent.busquedaPorTitCastellano = "A";
TrabajarConFichasDeChequeoTecnicoEvent.alta = "Alta";
TrabajarConFichasDeChequeoTecnicoEvent.modificacion = "Modificación";
TrabajarConFichasDeChequeoTecnicoEvent.baja = "Baja";
TrabajarConFichasDeChequeoTecnicoEvent.TEMPLATES = {
		inputAltaActores : '<div class="FormRowA actorCount">\
						<input type="text" name="actoresSinopsisObservRequest.actores[{{index}}].actor" id="TrabajarConFichasDeChequeoTecnicoEventId_actor{{index}}" style="width: 200px;" maxlength="30"/>\
					</div>\
					<div class="FormRowB">\
						<input type="text" name="actoresSinopsisObservRequest.actores[{{index}}].personaje" id="TrabajarConFichasDeChequeoTecnicoEventId_personaje{{index}}" style="width: 200px;" maxlength="30"/>\
					</div>',
		inputAltaSinopsis : '<div class="FormRowC sinopsisCount">\
						<input type="text" name="actoresSinopsisObservRequest.sinopsis[{{index}}]" id="TrabajarConFichasDeChequeoTecnicoEventId_sinopsis{{index}}" style="width: 50%;" maxlength="50"/>\
					</div>',
		inputAltaObservaciones : '<div class="FormRowC observacionCount">\
						<input type="text" name="actoresSinopsisObservRequest.observaciones[{{index}}]" id="TrabajarConFichasDeChequeoTecnicoEventId_observacion{{index}}" style="width: 50%;" maxlength="50"/>\
					</div>',
		primerTrSegmento : '<tr><td><label>Segmento</label></td><td><label>T&iacute;tulo</label></td><td><label>Duraci&oacute;n</label></td><td><label>Rdo. VTR</label></td></tr>',
		segmentos : '<tr>\
				<td>\
					<input type="text" name="segmentosRequest.segmentos[{{index}}].numero" id="TrabajarConFichasDeChequeoTecnicoEventId_numero{{index}}" style="width: 50px;" disabled="disabled"/>\
				</td>\
				<td>\
					<input type="text" name="segmentosRequest.segmentos[{{index}}].titulo" id="TrabajarConFichasDeChequeoTecnicoEventId_titulo{{index}}" style="width: 200px;"/>\
				</td>\
				<td>\
					<input type="text" name="segmentosRequest.segmentos[{{index}}].duracion" id="TrabajarConFichasDeChequeoTecnicoEventId_duracion{{index}}" style="width: 50px;"/>\
				</td>\
				<td>\
					<select name="segmentosRequest.segmentos[{{index}}].chequeo" id="TrabajarConFichasDeChequeoTecnicoEventId_chequeo{{index}}" style="width: 50px;">\
						<option value="" label=""></option>\
						<option value="S" label="SI">SI</option>\
						<option value="N" label="NO">NO</option>\
					</select>\
				</td>\
			</tr>',
		primerTrSeniales : '<tr><td><label>Se&ntilde;al</label></td><td><label>VTR</label></td><td><label>Film</label></td></tr>',
		seniales : '<tr>\
				<td>\
					<input type="text" id="TrabajarConFichasDeChequeoTecnicoEventId_senialFilm{{index}}" disabled="disabled"/>\
					<input type="hidden" name="senialesFilmRequest.seniales[{{index}}].senial" id="TrabajarConFichasDeChequeoTecnicoEventId_senialFilmHidden{{index}}"/>\
				</td>\
				<td>\
					<select name="senialesFilmRequest.seniales[{{index}}].okVTR" id="TrabajarConFichasDeChequeoTecnicoEventId_okVTR{{index}}">\
						<option value="S" label="SI">SI</option>\
						<option value="N" label="NO">NO</option>\
					</select>\
				</td>\
				<td>\
					<select name="senialesFilmRequest.seniales[{{index}}].okFilm" id="TrabajarConFichasDeChequeoTecnicoEventId_okFilm{{index}}">\
						<option value="S" label="SI">SI</option>\
						<option value="N" label="NO">NO</option>\
					</select>\
				</td>\
			</tr>',
		trRollos : '<tr class="rollosCount"><td>\
				<input type="text" name="altaModifRollosRequest.rollos[{{index}}].soporte" id="{{id}}_soporteCopia{{index}}" style="width: 75px"/>\
				<span id="{{id}}_popupBusquedaSoporteCopia{{index}}" style="cursor: pointer; display: inline-block;" class="ui-icon ui-icon-search" title="Buscar Soportes"/>\
			</td><td>\
				<input type="text" name="altaModifRollosRequest.rollos[{{index}}].rollo" id="{{id}}_nroRolloCopia{{index}}" style="width: 85px" maxlength="6"/>\
			</td><td>\
				<select name="altaModifRollosRequest.rollos[{{index}}].sugerido" id="{{id}}_sugeridoCopia{{index}}" style="width: 85px">\
					<option value="" label=""></option>\
					<option value="S" label="Si">Si</option>\
					<option value="N" label="No">No</option>\
				</select>\
			</td>\
			<input type="hidden" name="altaModifRollosRequest.rollos[{{index}}].secuencia" id="{{id}}_secuenciaCopia{{index}}" value="0">\
			<input type="hidden" name="altaModifRollosRequest.rollos[{{index}}].copia" id="{{id}}_numeroCopia{{index}}" value="0">\
			</tr>'
	};
function TrabajarConFichasDeChequeoTecnicoEvent(div) {
	this.div = div;
	this.tipoBusqueda = TrabajarConFichasDeChequeoTecnicoEvent.busquedaPorClave;
};
extend(TrabajarConFichasDeChequeoTecnicoEvent, Plugin);
TrabajarConFichasDeChequeoTecnicoEvent.prototype.create = function() {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.service = new TrabajarConFichasDeChequeoTecnicoService();
	Component.get("html/trabajarConFichasDeChequeoTecnico/busquedaInicial.html", trabajarConFichasDeChequeoTecnicoEvent.draw);
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.draw = function(comp) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	$("#viewContainer").empty().append(comp.replace(/{{id}}/g, self.div.id));
	Accordion.getInstance(self.getAccordionBusquedaInicial());
	//Component.populateSelect(self.getSelector("senial"), seniales, "codigo", "descripcion");
	//Component.populateSelect($("#senialDefaultUsuario").val(), seniales, "codigo", "descripcion");
	Datepicker.getInstance(self.getInputFechaDesde());
	Datepicker.getInstance(self.getInputFechaHasta());
	self.getInputsText().keypress(function(event) {
		if ( event.which == 13 ) {
			self.buscarFichas();
		} else {
			return;
		}
	});
	
	self.getButtonBuscarFichas().button().click(self.buscarFichas);
	self.getSelector("agregarFicha").button().click(self.agregarFicha);
	self.getSelector("tipoBusqueda").change(function() {
		  if (this.value === TrabajarConFichasDeChequeoTecnicoEvent.busquedaPorClave) {
			  self.getDivBusquedaPorClave().show();
			  self.getDivBusquedaPorTituloOriginal().hide();
			  self.getDivBusquedaPorTituloCastellano().hide();
			  self.tipoBusqueda = TrabajarConFichasDeChequeoTecnicoEvent.busquedaPorClave;
		  } else if (this.value === TrabajarConFichasDeChequeoTecnicoEvent.busquedaPorTitOriginal) {
			  self.getDivBusquedaPorClave().hide();
			  self.getDivBusquedaPorTituloOriginal().show();
			  self.getDivBusquedaPorTituloCastellano().hide();
			  self.tipoBusqueda = TrabajarConFichasDeChequeoTecnicoEvent.busquedaPorTitOriginal;
		  } else if (this.value === TrabajarConFichasDeChequeoTecnicoEvent.busquedaPorTitCastellano) {
			  self.getDivBusquedaPorClave().hide();
			  self.getDivBusquedaPorTituloOriginal().hide();
			  self.getDivBusquedaPorTituloCastellano().show();
			  self.tipoBusqueda = TrabajarConFichasDeChequeoTecnicoEvent.busquedaPorTitCastellano;
		  }
	});
	
	
	/*self.getSelector("senial").change(function() {
		if (!Validator.isEmpty(self.getSelector("senial"))) {*/
			self.getSelector("agregarFicha").show();
			/*self.senialSeleccionada = self.getSelector("senial").val();
		}else{
			self.getSelector("agregarFicha").hide();
		}
	});*/
	
	self.getSelector("senial").val($("#senialDefaultUsuario").val());
	$("#"+self.div.id+"_senial").prop('disabled', true);
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.buscarFichas = function() {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	var data = "" ;
	if (self.validForm()) {
		if(self.getSelector("tipoBusqueda").val() === TrabajarConFichasDeChequeoTecnicoEvent.busquedaPorClave){ 
			self.getSelector("inputClave").val(Seniales.formatearClave(self.getSelector("inputClave").val()));
			data = "busquedaFichasRequest.clave=" + self.getSelector("inputClave").val() +
			"&busquedaFichasRequest.capitulo=" + self.getSelector("inputCapitulo").val()+
			"&busquedaFichasRequest.parte=" + self.getSelector("inputParte").val();
		}
		
		if(self.getSelector("tipoBusqueda").val() === TrabajarConFichasDeChequeoTecnicoEvent.busquedaPorTitOriginal){
			data =  "busquedaFichasRequest.tituloOriginal=" + self.getSelector("inputTituloOriginal").val();
		}
		

		if(self.getSelector("tipoBusqueda").val() === TrabajarConFichasDeChequeoTecnicoEvent.busquedaPorTitCastellano){
			data = "busquedaFichasRequest.tituloCastellano=" + self.getSelector("inputTituloCastellano").val();
		}
		
		data = data + "&busquedaFichasRequest.fichasActivas=" + self.getSelector("fichas").val() +
		"&busquedaFichasRequest.senial=" + $("#senialDefaultUsuario").val() +
		"&busquedaFichasRequest.chequeo=" + self.getSelector("chequeo").val() +
		"&busquedaFichasRequest.orden=" + self.tipoBusqueda+
		"&busquedaFichasRequest.fechaDesde=" + self.getSelector("fechaDesde").val()+
		"&busquedaFichasRequest.fechaHasta=" + self.getSelector("fechaHasta").val();		
		self.service.buscarFichas(data);
	}
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.validFormVolverAlta = function() {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	/*var senial = self.getSelector("senial");
	if (Validator.isEmpty(senial)) {
		
		return false;
	}*/
	if (this.tipoBusqueda === TrabajarConFichasDeChequeoTecnicoEvent.busquedaPorClave) {
		var clave = self.getSelector("inputClave");
		if (Validator.isEmpty(clave)) {
			
			return false;
		}
	} else if (this.tipoBusqueda === TrabajarConFichasDeChequeoTecnicoEvent.busquedaPorTitOriginal) {
		var titOrig = self.getSelector("inputTituloOriginal");
		if (Validator.isEmpty(titOrig)) {
			
			return false;
		}
	} else if (this.tipoBusqueda === TrabajarConFichasDeChequeoTecnicoEvent.busquedaPorTitCastellano) {
		var titCast = self.getSelector("inputTituloCastellano");
		if (Validator.isEmpty(titCast)) {
			
			return false;
		}
	}
	return true;
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.validForm = function() {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	/*var senial = self.getSelector("senial");
	if (Validator.isEmpty(senial)) {
		Validator.focus(senial);
		return false;
	}*/
	if (this.tipoBusqueda === TrabajarConFichasDeChequeoTecnicoEvent.busquedaPorClave) {
		var clave = self.getSelector("inputClave");
		if (Validator.isEmpty(clave)) {
			Validator.focus(clave);
			return false;
		}
	} else if (this.tipoBusqueda === TrabajarConFichasDeChequeoTecnicoEvent.busquedaPorTitOriginal) {
		var titOrig = self.getSelector("inputTituloOriginal");
		if (Validator.isEmpty(titOrig)) {
			Validator.focus(titOrig);
			return false;
		}
	} else if (this.tipoBusqueda === TrabajarConFichasDeChequeoTecnicoEvent.busquedaPorTitCastellano) {
		var titCast = self.getSelector("inputTituloCastellano");
		if (Validator.isEmpty(titCast)) {
			Validator.focus(titCast);
			return false;
		}
	}
	return true;
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.responseFichas = function(data) {
	this.drawGrigListadoFichas(data);
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.drawGrigListadoFichas = function(data) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.getGridListadoFichas().jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Clave','Capitulo','Parte','Titulo Original','Titulo Castellano','Ficha','Fecha', "Soporte", "Estado", "", "", "", "", "", ""],
		colModel:[ 
			{name:'clave',index:'clave', width:100, sortable:true}, 
			{name:'capitulo',index:'capitulo', width:50, sortable:true}, 
			{name:'parte',index:'parte', width:50, sortable:true}, 
			{name:'titulo',index:'titulo', width:300, sortable:true}, 
			{name:'tituloCastellano',index:'tituloCastellano', width:300, sortable:true}, 
			{name:'nroFicha',index:'nroFicha', width:100, sortable:false}, 
			{name:'fecha',index:'fecha', width:100, sortable:false, formatter: 'date', formatoptions: { srcformat: 'Y/m/d', newformat: 'd/m/Y'}}, 
			{name:'soporte',index:'soporte', width:50, sortable:false},
			{name:'estado',index:'estado', width:50, sortable:false},
			{name:'modificar',index:'modificar', width:30, aling:'center', sortable:false},
			{name:'eliminar',index:'eliminar', width:30, aling:'center', sortable:false},
			{name:'visualizar',index:'visualizar', width:30, aling:'center', sortable:false},
			{name:'chequeo',index:'chequeo', width:30, aling:'center', sortable:false},
			{name:'imprimir',index:'imprimir', width:30, aling:'center', sortable:false},
			{name:'inactivar',index:'inactivar', width:30, aling:'center', sortable:false}
		],
		rowNum: 20,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#TrabajarConFichasDeChequeoTecnicoEventId_pagerGridFichas',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Fichas',
		gridComplete: function() {
        	var ids = $(this).jqGrid('getDataIDs');
        	for (var i = 0; i < ids.length; i++) {
        		var rowId = ids[i];
        		var ficha = $(this).jqGrid('getRowData', rowId);
        		var modificar = "<div onclick='trabajarConFichasDeChequeoTecnicoEvent.modificarFicha("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-pencil modificar conTooltip'>Modificar</span></div>";
        		$(this).jqGrid('setRowData', rowId, { modificar: modificar });
        		var eliminar = "<div onclick='trabajarConFichasDeChequeoTecnicoEvent.eliminarFicha("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-trash eliminar conTooltip'>Eliminar</span></div>";
        		$(this).jqGrid('setRowData', rowId, { eliminar: eliminar });
        		var visualizar = "<div onclick='trabajarConFichasDeChequeoTecnicoEvent.service.visualizarFicha("+ficha.nroFicha+")' style='cursor:pointer'><span class='ui-icon ui-icon-search visualizar conTooltip'>Visualizar</span></div>";
        		$(this).jqGrid('setRowData', rowId, { visualizar: visualizar });
        		var chequeo = "<div onclick='trabajarConFichasDeChequeoTecnicoEvent.chequeoFicha("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-check rdochequeo conTooltip'>Rdo. Chequeo</span></div>";
        		$(this).jqGrid('setRowData', rowId, { chequeo: chequeo });
        		var imprimir = "<div onclick='trabajarConFichasDeChequeoTecnicoEvent.imprimirFichaTecnica("+ficha.nroFicha+")' style='cursor:pointer'><span class='ui-icon ui-icon-print imprimmir conTooltip'>Imprimir</span></div>";
        		$(this).jqGrid('setRowData', rowId, { imprimir: imprimir });
        		var inactivar = "<div onclick='trabajarConFichasDeChequeoTecnicoEvent.inactivarFicha("+ficha.nroFicha+",\""+ficha.clave+"\",\""+ficha.estado+"\")' style='cursor:pointer'><span class='ui-icon ui-icon-minus inactivar conTooltip'>Inactivar</span></div>";
        		$(this).jqGrid('setRowData', rowId, { inactivar: inactivar });
        	}
	    }
	});
	self.getGridListadoFichas().clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.modificarFicha = function(rowId) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.ficha = self.getGridListadoFichas().jqGrid('getRowData', rowId);
	if(self.validarInactividad()){return;};
	self.nroFichaSeleccionada = self.ficha.nroFicha;
	self.accion = TrabajarConFichasDeChequeoTecnicoEvent.modificacion;
	var data = {
		nroFicha:self.ficha.nroFicha,
		clave:self.ficha.clave
	};
	self.service.infoVigenciaContratos(data, function(response) {
		if (response.length) {
			
			var NoVigente = "S";
			//verifico si alguno de los contratos de la ficha no esta vigente
			for ( var i in response) {
				var vigente = response[i].vigente;
				if (vigente==="N") {
					NoVigente="N";
				}					
			}
			//Si existe algun contrato no vigente muestro la grilla
			if(NoVigente==="N"){
				self.drawDialogContratos(self.drawAltaModifBajaFicha);
				self.drawGridContratos(response);
				return;
			}
			
		}
		self.service.cargarFicha(self.nroFichaSeleccionada, self.drawAltaModifBajaFicha);
	});
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.agregarFicha = function() {
	trabajarConFichasDeChequeoTecnicoEvent.accion = TrabajarConFichasDeChequeoTecnicoEvent.alta;
	trabajarConFichasDeChequeoTecnicoEvent.nroFichaSeleccionada = "";
	trabajarConFichasDeChequeoTecnicoEvent.drawAltaModifBajaFicha();
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.eliminarFicha = function(data) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.ficha = self.getGridListadoFichas().jqGrid('getRowData', data);
	if(self.validarInactividad()){return;};
	self.nroFichaSeleccionada = self.ficha.nroFicha;
	self.accion = TrabajarConFichasDeChequeoTecnicoEvent.baja;
	self.service.cargarFicha(self.nroFichaSeleccionada, self.drawAltaModifBajaFicha);
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.imprimirFicha = function(data) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.ficha = self.getGridListadoFichas().jqGrid('getRowData', data);
	if(self.validarInactividad()){return;};
	self.nroFichaSeleccionada = self.ficha.nroFicha;
	self.accion = TrabajarConFichasDeChequeoTecnicoEvent.baja;
	self.service.imprimirReporte(self.nroFichaSeleccionada, self.volverDeAltaModifFicha);
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.validarInactividad = function(){
	if (trabajarConFichasDeChequeoTecnicoEvent.ficha.estado === "I") {
		MESSAGE.alert("La ficha de chequeo está inactiva.");
		return true;
	}
	return false;
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.drawAltaModifBajaFicha = function(data) {
	Component.get("html/trabajarConFichasDeChequeoTecnico/altaModifCabeceraFicha.html", function(comp) {
		var self = trabajarConFichasDeChequeoTecnicoEvent;
		self.getDivBusquedaInicial().hide();
		comp = comp.replace(/{{id}}/g, self.div.id).replace(/{{accion}}/, self.accion)
			.replace(/{{senial}}/g, $("#senialDefaultUsuario").val())
			.replace(/{{nroFicha}}/g, (self.nroFichaSeleccionada)?self.nroFichaSeleccionada:"");
		if (self.getDivAgregarFicha().length) {
			self.getDivAgregarFicha().replaceWith(comp);
		}else{
			self.getDiv().append(comp);
		}
		Accordion.getInstance(self.getSelector("altaModifFichaDiv"));
		Accordion.getInstance(self.getSelector("divActoresAM"));
		
		self.getSelector("popupBusquedaProgTitOriginalAltaModifFicha").click(function() {
		    situarPopupEvent.reset();
			situarPopupEvent.nombreSituar = "Situar";
			var colNames = ['Clave', 'Capitulo', 'Parte', 'Titulo Castellano', 'Titulo Original'];
			var colModel = [
		        {index:'clave', 		name:'clave',		  align: 'center', sortable: false},
		        {index:'capitulo', 		name:'capitulo', align: 'center', sortable: false},  
		        {index:'parte', name:'parte', align: 'center', sortable: false},
		        {index:'tituloCastellano', name:'tituloCastellano', sortable: false},
		        {index:'tituloOriginal', 		name:'tituloOriginal', 	sortable: false}
		    ];
			
			situarPopupEvent.setColumns(colNames, colModel);
			situarPopupEvent.create("chequeoTecnicoBuscarProgramasPorTituloOriginal.action", trabajarConFichasDeChequeoTecnicoEvent.programaSelected, {titulo : ""}, "programas");
		
		});
		
		self.getSelector("popupBusquedaProgTitCastellanoAltaModifFicha").click(function() {
		    situarPopupEvent.reset();
			situarPopupEvent.nombreSituar = "Situar";
			var colNames = ['Clave', 'Capitulo', 'Parte', 'Titulo Castellano', 'Titulo Original'];
			var colModel = [
		        {index:'clave', 		name:'clave',		  align: 'center', sortable: false},
		        {index:'capitulo', 		name:'capitulo', align: 'center', sortable: false},  
		        {index:'parte', name:'parte', align: 'center', sortable: false},
		        {index:'tituloCastellano', name:'tituloCastellano', sortable: false},
		        {index:'tituloOriginal', 		name:'tituloOriginal', 	sortable: false}
		    ];
			
			situarPopupEvent.setColumns(colNames, colModel);
			situarPopupEvent.create("chequeoTecnicoBuscarProgramasPorTituloCastellano.action", trabajarConFichasDeChequeoTecnicoEvent.programaSelected, {titulo : ""}, "programas");
		});
		self.getSelector("popupEmisoraAltaModifFicha").click(function() {
		    situarPopupEvent.reset();
			situarPopupEvent.nombreSituar = "Situar";
			situarPopupEvent.setColumns(undefined, undefined);
			situarPopupEvent.create("chequeoTecnicoBuscarSeniales.action", trabajarConFichasDeChequeoTecnicoEvent.senialSelected, {descripcion : ""}, "seniales");
		});
		
		self.getSelector("cancelarAltaModifFicha").button().click(self.volverDeAltaModifFicha);
		self.getSelector("aceptarAltaModifFicha").button().click(self.aceptarAltaModifBajaFicha);
		Datepicker.getInstance(self.getSelector("fechaSolicitudAltaModifFicha"), new Date());
		self.completarDataCabeceraFicha(data);
		self.getSelector("popupBusquedaSoporteAlta").click(function() {
			var selector = self.getSelector("soporteAltaModifFicha");
			situarPopupEvent.reset();
			situarPopupEvent.nombreSituar = "Situar";
			situarPopupEvent.create("chequeoTecnicoBuscarSoportes.action", function(response) {
				selector.val(response.CODIGO);
			}, {descripcion : ""}, "resultadoSituar");
		});
		
		if (self.accion === TrabajarConFichasDeChequeoTecnicoEvent.modificacion){
			$("#"+self.div.id+"_claveAltaModifFicha").prop('disabled', true);
		}else{
			$("#"+self.div.id+"_claveAltaModifFicha").prop('disabled', false);
		}
			
	});
 };

TrabajarConFichasDeChequeoTecnicoEvent.prototype.volverDeAltaModifFicha = function(data) {
	trabajarConFichasDeChequeoTecnicoEvent.getDivAgregarFicha().hide();
	trabajarConFichasDeChequeoTecnicoEvent.getDivBusquedaInicial().show();
	if(trabajarConFichasDeChequeoTecnicoEvent.validFormVolverAlta()){
		trabajarConFichasDeChequeoTecnicoEvent.buscarFichas();
	}
	  
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.completarDataCabeceraFicha = function(data) {
	if (data) {
		var self = trabajarConFichasDeChequeoTecnicoEvent;
		self.getSelector("fichaChequeoLabel").text(self.ficha.nroFicha);
		if (data.ficha.fechaSolicitud != undefined){
			self.getSelector("fechaSolicitudAltaModifFicha").datepicker('setDate',$.datepicker.parseDate('yy-mm-dd', data.ficha.fechaSolicitud));
		}
		else {
			self.getSelector("fechaSolicitudAltaModifFicha").datepicker('setDate', null);
		}
		self.getSelector("claveAltaModifFicha").val(data.ficha.clave);
		self.getSelector("capituloAltaModifFicha").val(data.ficha.capitulo);
		self.getSelector("parteAltaModifFicha").val(data.ficha.parte);
		self.getSelector("soporteAltaModifFicha").val(data.ficha.soporte);
		self.getSelector("tituloOriginalLabel").text(data.ficha.tituloOriginal);
		self.getSelector("tituloCastellanoLabel").text(data.ficha.tituloCastellano);
		self.getSelector("tituloOffLabel").text(data.ficha.tituloOff);
		for ( var i = 1; i <= data.seniales.length; i++) {
			self.getSelector("emisora"+i+"AltaModifFicha").val(data.seniales[i-1].codigo);
		}
		self.getSelector("selloAltaModifFicha").val(data.ficha.sello);
		self.getSelector("productorAltaModifFicha").val(data.ficha.productor);
		self.getSelector("autorAltaModifFicha").val(data.ficha.autor);
		self.getSelector("directorAltaModifFicha").val(data.ficha.director);
		for ( var i = 1; i <= data.actores.length; i++) {
			self.getSelector("actor"+i+"AltaModifFicha").val(data.actores[i-1].actor);
		}
	}
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.aceptarAltaModifBajaFicha = function() {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	if (self.accion === TrabajarConFichasDeChequeoTecnicoEvent.baja) {
		popupConfirmacionEvent.confirmar = function () {
			self.service.eliminarFicha({nroFicha:self.ficha.nroFicha, clave:self.ficha.clave}, self.volverDeAltaModifFicha);
			popupConfirmacionEvent.close();
	    };
	    popupConfirmacionEvent.cancelar = function () {
	        popupConfirmacionEvent.close();
	    };
        popupConfirmacionEvent.popTitle = 'Borrar Ficha';
	    popupConfirmacionEvent.create("borarCabeceraFichaChequeoPopUp","¿Confirma el borrado de la ficha?");
	}else if (self.accion === TrabajarConFichasDeChequeoTecnicoEvent.alta) {
		if(trabajarConFichasDeChequeoTecnicoEvent.validarEmisoras()){
			var data = self.getSelector("formAltaModifFicha").serialize();// + 
			// "&altaModifFichaRequest.senial=" + $("#senialDefaultUsuario").val();
			self.service.altaFicha(data, self.volverDeAltaModifFicha);
		}
		
	}else if (self.accion === TrabajarConFichasDeChequeoTecnicoEvent.modificacion) {
		var data = self.getSelector("formAltaModifFicha").serialize()
		+"&altaModifFichaRequest.clave=" + self.getSelector("claveAltaModifFicha").val();
		self.service.modificarFicha(data, self.volverDeAltaModifFicha);
	}
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.programaSelected = function(row) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	
	self.getSelector("claveAltaModifFicha").val(row.clave);
	self.getSelector("tituloOriginalLabel").text(row.tituloOriginal);
	self.getSelector("tituloCastellanoLabel").text(row.tituloCastellano);
	self.getSelector("capituloAltaModifFicha").val(row.capitulo);
	self.getSelector("parteAltaModifFicha").val(row.parte);
	self.service.obtenerInfoPrograma(row.clave, row.capitulo, function(data) {
		//self.getSelector("capituloAltaModifFicha").val(data.programa.capitulo);
		//self.getSelector("parteAltaModifFicha").val(data.programa.parte);
		self.getSelector("soporteAltaModifFicha").val(data.programa.soporte);
		self.getSelector("tituloOffLabel").text(data.programa.tituloOff);
		var info = null;
		for ( var i = 1; i <= data.infoAdicionales.length; i++) {
			info = data.infoAdicionales[i-1];
			self.getSelector("actor"+(i)+"AltaModifFicha").val(info.actores);
		}
		self.getSelector("selloAltaModifFicha").val(info.sello);
		self.getSelector("productorAltaModifFicha").val(info.productora);
		self.getSelector("autorAltaModifFicha").val(info.autor);
		self.getSelector("directorAltaModifFicha").val(info.director);
	});
	situarPopupEvent.setColumns(undefined, undefined);
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.senialSelected = function(row) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	situarPopupEvent.setColumns(undefined, undefined);
	for ( var i = 1; i < 6; i++) {
		if (self.getSelector("emisora"+i+"AltaModifFicha").val() === "") {
			self.getSelector("emisora"+i+"AltaModifFicha").val(row.codigo);
			break;
		};
		if (i === 5) {
			self.getSelector("emisora"+i+"AltaModifFicha").val(row.codigo);
			break;
		}
	}
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.validarEmisoras = function() {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	for ( var i = 1; i < 6; i++) {
		if (self.getSelector("emisora"+i+"AltaModifFicha").val().trim() != "") {
			return true;			
		};		
	};
	Validator.focus(self.getSelector("emisora1AltaModifFicha"));
	return false;
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.responseVisualizarFicha = function(data) {
	Component.get("html/trabajarConFichasDeChequeoTecnico/visualizacionFicha.html", function(comp) {
		var self = trabajarConFichasDeChequeoTecnicoEvent;
		self.getDivBusquedaInicial().hide();
		for ( var key in data.ficha) {
			if (data.ficha[key] === null) {
				data.ficha[key] = "";
			}
		}
		comp = comp.replace(/{{id}}/g, self.div.id)
		.replace(/{{fichaChequeo}}/g, data.ficha.nroFicha)
		.replace(/{{fechaSolicitud}}/g, $.datepicker.formatDate('dd/mm/yy', $.datepicker.parseDate('yy-mm-dd', data.ficha.fechaSolicitud)))
		.replace(/{{tituloOriginal}}/g, data.ficha.tituloOriginal)
		.replace(/{{tituloCastellano}}/g, data.ficha.tituloCastellano)
		.replace(/{{tituloOff}}/g, data.ficha.tituloOff)
		.replace(/{{clave}}/g, data.ficha.clave)
		.replace(/{{capitulo}}/g, data.ficha.capitulo == 0 ? "" : data.ficha.capitulo)
		.replace(/{{parte}}/g, data.ficha.parte == 0 ? "" : data.ficha.parte)
		.replace(/{{soporte}}/g, data.ficha.soporte)
		.replace(/{{soporteDescripcion}}/g, data.ficha.soporteDescripcion)
		.replace(/{{emisoras}}/g, function() {
			 var arr = jQuery.map(data.seniales, function(n, i){
			     return n.codigo;
			 });
			 return arr.join(" ");
		})
		.replace(/{{sello}}/g, data.ficha.sello)
		.replace(/{{productor}}/g, data.ficha.productor)
		.replace(/{{autor}}/g, data.ficha.autor)
		.replace(/{{director}}/g, data.ficha.director)
		.replace(/{{genero}}/g, data.ficha.genero == 0 ? "" : data.ficha.genero)
		.replace(/{{generoDescripcion}}/g, data.ficha.generoDescripcion)
		.replace(/{{color}}/g, data.ficha.color)
		.replace(/{{pais}}/g, data.ficha.pais)
		.replace(/{{anio}}/g, data.ficha.anio == 0 ? "" : data.ficha.anio)
		.replace(/{{califArtistica}}/g, data.ficha.califArtistica)
		.replace(/{{califArtisticaDesc}}/g, data.ficha.califArtisticaDesc)
		.replace(/{{califOficial}}/g, data.ficha.califMoralOficial)
		.replace(/{{califMoralOficialDesc}}/g, data.ficha.califMoralOficialDesc)
		.replace(/{{califAutocontrol}}/g, data.ficha.califAutocontrol == 0 ? "" : data.ficha.califAutocontrol)
		.replace(/{{califAutocontrolDesc}}/g, data.ficha.califAutocontrolDesc)
		.replace(/{{estadoCopia}}/g, data.ficha.estadoCopia)
		.replace(/{{audio}}/g, data.ficha.audio)
		.replace(/{{imagen}}/g, data.ficha.imagen)
		.replace(/{{duracion}}/g, data.ficha.duracionArtistica == 0 ? "" : data.ficha.duracionArtistica)
		.replace(/{{tipoAudio}}/g, data.ficha.tipoAudio)
		.replace(/{{capSegmentado}}/g, data.ficha.capSegmentado)
		.replace(/{{cantSegmentos}}/g, data.ficha.cantSegmentos == 0 ? "" : data.ficha.cantSegmentos)
		.replace(/{{operadorVTR}}/g, data.ficha.operadorVTR)
		.replace(/{{fechaChequeo}}/g,  $.datepicker.formatDate('dd/mm/yy', $.datepicker.parseDate('yy-mm-dd', data.ficha.fechaChequeo)))
		.replace(/{{actores}}/g, function() {
			var arr = jQuery.map(data.actores, function(n, i){
				return n.actor;
			});
			return arr.join("</br>");
		})
		.replace(/{{personajes}}/g, function() {
			var arr = jQuery.map(data.actores, function(n, i){
				return n.personaje;
			});
			return arr.join("</br>");
		})
		.replace(/{{observaciones}}/g, function() {
			var arr = jQuery.map(data.observaciones, function(n, i){
				return n.observacion;
			});
			return arr.join("</br>");
		})
		.replace(/{{senialesOK}}/g, function() {
			var arr = jQuery.map(data.senialesOkFilm, function(n, i){
				return n.senial;
			});
			return arr.join("</br></br>");
		})
		.replace(/{{vtrOK}}/g, function() {
			var arr = jQuery.map(data.senialesOkFilm, function(n, i){
				return n.okVTR;
			});
			return arr.join("</br></br>");
		})
		.replace(/{{filmOK}}/g, function() {
			var arr = jQuery.map(data.senialesOkFilm, function(n, i){
				return n.okFilm;
			});
			return arr.join("</br></br>");
		});
		if (self.getDivVisualizarFicha().length) {
			self.getDivVisualizarFicha().replaceWith(comp);
		}else{
			self.getDiv().append(comp);
		}
		self.getSelector("volverVisualizarFicha").button().click(function() {
			trabajarConFichasDeChequeoTecnicoEvent.getDivVisualizarFicha().hide();
			trabajarConFichasDeChequeoTecnicoEvent.getDivBusquedaInicial().show();
		});
		Accordion.getInstance(self.getSelector("visualizacionFichaDiv"));
		Accordion.getInstance(self.getSelector("divResultadoChequeoV"));
		Accordion.getInstance(self.getSelector("divActoresV"));
		Accordion.getInstance(self.getSelector("divObservacionesV"));
		Accordion.getInstance(self.getSelector("divSenialesOKV"));
	});
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.chequeoFicha = function(rowId) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.ficha = self.getGridListadoFichas().jqGrid('getRowData', rowId);
	if(self.validarInactividad()){return;};
	self.service.validarTituloEnCanal({clave:self.ficha.clave, senial:$("#senialDefaultUsuario").val(), capitulo:self.ficha.capitulo, parte:self.ficha.parte},function(estaEnCanal){
		if (estaEnCanal !== "S") {
			return;
		}
		self.nroFichaSeleccionada = self.ficha.nroFicha;
		var data = {
				nroFicha:self.ficha.nroFicha,
				clave:self.ficha.clave
		};
		self.service.infoVigenciaContratos(data, function(response) {
			if (response.length) {
				var NoVigente = "S";
				//verifico si alguno de los contratos de la ficha no esta vigente
				for ( var i in response) {
					var vigente = response[i].vigente;
					if (vigente==="N") {
						NoVigente="N";
					}					
				}
				//Si existe algun contrato no vigente muestro la grilla
				if(NoVigente==="N"){
					self.drawDialogContratos(self.drawAltaModifChequeo);
					self.drawGridContratos(response);
					return;
				}else{
					//Sino, cargo la ficha de chequeo
					self.service.cargarFicha(self.nroFichaSeleccionada, self.drawAltaModifChequeo);
				}
				
			}
			self.service.cargarFicha(self.nroFichaSeleccionada, self.drawAltaModifChequeo);
		});
	});
};



TrabajarConFichasDeChequeoTecnicoEvent.prototype.drawDialogContratos = function(callback) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.getSelector("popupContratos").dialog({
		title : 'Contratos',
		width: 600,
		show: 'blind',
		hide: 'blind',
		modal: true,
		autoOpen: true,
		position: 'center',
		buttons: {
			"Salir": function() {
				$( this ).dialog( "close" );
			},
			"Continuar": function() {
				self.service.cargarFicha(self.nroFichaSeleccionada, callback);
				$( this ).dialog( "close" );
			}
		}
	});
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.drawGridContratos = function(data) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.getSelector("gridContratos").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Contrato','Grupo','Señal','Proveedor','Vigente'],
		colModel:[ 
			{name:'contrato',index:'contrato', sortable:false, width:50}, 
			{name:'grupo',index:'grupo', sortable:false, width:50}, 
			{name:'senial',index:'senial', sortable:false, width:50}, 
			{name:'proveedor',index:'proveedor', sortable:false, width:100}, 
			{name:'vigente',index:'vigente', sortable:false, width:50}
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#TrabajarConFichasDeChequeoTecnicoEventId_pagerGridContratos',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Contratos'
	});
	self.getSelector("gridContratos").clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.drawAltaModifChequeo = function(data) {
	Component.get("html/trabajarConFichasDeChequeoTecnico/altaModifChequeo.html", function(comp) {
		var self = trabajarConFichasDeChequeoTecnicoEvent;
		self.getDivBusquedaInicial().hide();
		self.fichaCompletaResponse = data;
		comp = self.fillDatosDePantalla(comp, data);
		
		if (self.getDivChequeoFicha().length) {
			self.getDivChequeoFicha().replaceWith(comp);
		}else{
			self.getDiv().append(comp);
		}
		
		
		
		self.getSelector("capituloSegmentadoAltaModifChequeo").change(function() {
			
			
			  if (this.value == "N") {
				  $("#"+self.div.id+"_cantSegmentosAltaModifChequeo").prop('disabled', true);
				  $("#"+self.div.id+"_cantSegmentosAltaModifChequeo").val("0");
				  
			  } else
			  {
				  $("#"+self.div.id+"_cantSegmentosAltaModifChequeo").prop('disabled', false);
			  }
		});
		
		
		self.fillDatosDeCombos(data);
		self.disabledOrEnabledInputSinCapitulos();
		self.getSelector("generoAltaModifChequeo").numeric();
		self.getSelector("anioAltaModifChequeo").numeric();
		self.getSelector("durArtisticaAltaModifChequeo").numeric();
		self.getSelector("cantSegmentosAltaModifChequeo").numeric();
		self.getSelector("creditosAltaModifChequeo").numeric();
		Accordion.getInstance(self.getDivChequeoFicha());
		Accordion.getInstance(self.getSelector("divActores"));
		Accordion.getInstance(self.getSelector("divSinopsis"));
		Accordion.getInstance(self.getSelector("divSegmentos"));
		Accordion.getInstance(self.getSelector("divObservaciones"));
		Accordion.getInstance(self.getSelector("divSenialesOkFilm"));
		Datepicker.getInstance(self.getSelector("fechaChequeoAltaModifChequeo"));
		
		self.getSelector("siguienteAltaModifChequeoFicha").button().click(self.guardarChequeoFicha);
		self.getSelector("confirmarAltaModifChequeoFicha").button().click(self.confirmarChequeoFicha);
		self.getSelector("siguienteAltaModifChequeoActores").button().click(self.guardaActoresSinopsisObservFicha);
		self.getSelector("siguienteAltaModifChequeoSegmentos").button().click(self.guardaInfoSegmentos);
		self.getSelector("siguienteAltaModifChequeoSenialesOkFilm").button().click(self.guardaSenialesOkFilm);
		self.getSelector("cancelarAltaModifChequeoFicha").button().click(self.cancelarCheckeo);
		self.getSelector("volverAltaModifChequeoActores").button().click(self.volverAChequeoDesdeActores);
		self.getSelector("volverAltaModifChequeoSegmentos").button().click(self.volverAChequeoDesdeSegmentos);
		self.getSelector("volverAltaModifChequeoSenialesOkFilm").button().click(self.volverAChequeoDesdeSenialesOkFilm);
		self.createSituarEvents();
		self.validateChequeo();
		
		if(self.getSelector("capituloSegmentadoAltaModifChequeo").val()=="N")
		{
			$("#"+self.div.id+"_cantSegmentosAltaModifChequeo").prop('disabled', true);
			$("#"+self.div.id+"_cantSegmentosAltaModifChequeo").val("0");
		}else{
			$("#"+self.div.id+"_cantSegmentosAltaModifChequeo").prop('disabled', false);
			
		}	
		if (!esTituloConCapitulos(self.ficha.clave)){
			$("#"+self.div.id+"_capituloChequeo").hide();
		}
	});
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.disabledOrEnabledInputSinCapitulos = function() {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	if (esTituloConCapitulos(self.ficha.clave)) {
		$("."+self.div.id+"_inputConCapitulos").prop('disabled', false);
	}else{
		$("."+self.div.id+"_inputConCapitulos").prop('disabled', true);
	}
};



TrabajarConFichasDeChequeoTecnicoEvent.prototype.fillDatosDePantalla = function(comp, data) {
	for ( var key in data.ficha) {
		if (data.ficha[key] === null) {
			data.ficha[key] = "";
		}
	}
	comp = comp.replace(/{{id}}/g, trabajarConFichasDeChequeoTecnicoEvent.div.id)
	.replace(/{{senial}}/g, $("#senialDefaultUsuario").val())
	.replace(/{{fichaChequeo}}/g, trabajarConFichasDeChequeoTecnicoEvent.ficha.nroFicha)
	.replace(/{{fechaSolicitud}}/g, $.datepicker.formatDate('dd/mm/yy', $.datepicker.parseDate('yy-mm-dd', data.ficha.fechaSolicitud)))
	.replace(/{{emisoras}}/g, function() {
		var arr = jQuery.map(data.seniales, function(n, i){
			return n.codigo;
		});
		return arr.join(" ");
	})
	.replace(/{{clave}}/g, data.ficha.clave)
	.replace(/{{tituloOriginal}}/g, data.ficha.tituloOriginal)
	.replace(/{{tituloCastellano}}/g, data.ficha.tituloCastellano)
	.replace(/{{tituloOff}}/g, data.ficha.tituloOff)
	.replace(/{{soporte}}/g, data.ficha.soporte)
	.replace(/{{capitulo}}/g, data.ficha.capitulo == 0 ? "" : data.ficha.capitulo)
	.replace(/{{parte}}/g, data.ficha.parte == 0 ? "" : data.ficha.parte)
	.replace(/{{genero}}/g, data.ficha.genero == 0 ? "" : data.ficha.genero)
	.replace(/{{generoDescripcion}}/g, data.ficha.generoDescripcion)
	.replace(/{{anio}}/g, data.ficha.anio == 0 ? "" : data.ficha.anio)
	.replace(/{{pais}}/g, data.ficha.pais)
	.replace(/{{sello}}/g, data.ficha.sello)
	.replace(/{{autor}}/g, data.ficha.autor)
	.replace(/{{director}}/g, data.ficha.director)
	.replace(/{{productor}}/g, data.ficha.productor)
	.replace(/{{califArtistica}}/g, data.ficha.califArtistica)
	.replace(/{{califArtisticaDesc}}/g, data.ficha.califArtisticaDesc)
	.replace(/{{califOficial}}/g, data.ficha.califMoralOficial)
	.replace(/{{califMoralOficialDesc}}/g, data.ficha.califMoralOficialDesc)
	.replace(/{{califAutocontrol}}/g, data.ficha.califAutocontrol == 0 ? "" : data.ficha.califAutocontrol)
	.replace(/{{califAutocontrolDesc}}/g, data.ficha.califAutocontrolDesc)
	.replace(/{{duracion}}/g, data.ficha.duracionArtistica == 0 ? "" : data.ficha.duracionArtistica)
	.replace(/{{descCapitulo}}/g, data.ficha.descCapitulo.trim())
	.replace(/{{tipoAudio}}/g, data.ficha.tipoAudio)
	.replace(/{{cantSegmentos}}/g, data.ficha.cantSegmentos == 0 ? "" : data.ficha.cantSegmentos)
	.replace(/{{operadorVTR}}/g, data.ficha.operadorVTR.trim())
	.replace(/{{credito}}/g, data.ficha.credito == 0 ? "" : data.ficha.credito)
	.replace(/{{fechaChequeo}}/g,  $.datepicker.formatDate('dd/mm/yy', $.datepicker.parseDate('yy-mm-dd', data.ficha.fechaChequeo)));
	return comp;
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.fillDatosDeCombos = function(data) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.getSelector("colorAltaModifChequeo").val(data.ficha.color);
	self.getSelector("nacionalidadAltaModifChequeo").val(data.ficha.nacionalidad);
	self.getSelector("estCopiaAltaModifChequeo").val(data.ficha.estadoCopia);
	self.getSelector("audioAltaModifChequeo").val(data.ficha.audio);
	self.getSelector("imagenAltaModifChequeo").val(data.ficha.imagen);
	
    if(data.ficha.autAutor.trim()==="")
	    //self.getSelector("autAutorAltaModifChequeo").val("");
        self.getSelector("autAutorAltaModifChequeo").val("N");
    else
    	self.getSelector("autAutorAltaModifChequeo").val(data.ficha.autAutor);
    
    if(data.ficha.pagaArgentores.trim()==="")
	    //self.getSelector("pagaArgentoresAltaModifChequeo").val("");
        self.getSelector("pagaArgentoresAltaModifChequeo").val("N");
    else
    	self.getSelector("pagaArgentoresAltaModifChequeo").val(data.ficha.pagaArgentores);
    
    if(data.ficha.capSegmentado.trim()===""){
    	$("#"+self.div.id+"_cantSegmentosAltaModifChequeo").prop('disabled', true);
		$("#"+self.div.id+"_cantSegmentosAltaModifChequeo").val("0");
		self.getSelector("capituloSegmentadoAltaModifChequeo").val("N");
    }	    
    else
    	self.getSelector("capituloSegmentadoAltaModifChequeo").val(data.ficha.capSegmentado);
    
    if(data.ficha.film.trim()===""){
    	self.getSelector("filmAltaModifChequeo").val("");
    }else{
    	self.getSelector("filmAltaModifChequeo").val(data.ficha.film.trim());
    }
    
    if(data.ficha.aceptadoVTR.trim()===""){
    	self.getSelector("aceptadoVTRAltaModifChequeo").val("");
    }else{
    	self.getSelector("aceptadoVTRAltaModifChequeo").val(data.ficha.aceptadoVTR.trim());
    }
	
	
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.createSituarEvents = function() {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.getSelector("popupBusquedaGeneroAltaModifChequeo").click(function() {
	    situarPopupEvent.reset();
		situarPopupEvent.nombreSituar = "Situar";
		situarPopupEvent.create("chequeoTecnicoBuscarGeneros.action", function(row) {
			self.getSelector("generoAltaModifChequeo").val(row.codigo);
			self.getSelector("generoChequeoLabelValor").html(row.descripcion);
		}, {descripcion : ""}, "resultadoSituar");
	});
	self.getSelector("popupBusquedaCalifArtisticaAltaModifChequeo").click(function() {
	    situarPopupEvent.reset();
		situarPopupEvent.nombreSituar = "Situar";
		situarPopupEvent.create("chequeoTecnicoBuscarCalifArtistica.action", function(row) {
			self.getSelector("califArtisticaAltaModifChequeo").val(row.codigo);
			self.getSelector("califArtisticaChequeoLabel").html(row.descripcion);
		}, {descripcion : ""}, "resultadoSituar");
	});
	self.getSelector("popupBusquedaCalifAutocontrolAltaModifChequeo").click(function() {
	    situarPopupEvent.reset();
		situarPopupEvent.nombreSituar = "Situar";
		situarPopupEvent.create("chequeoTecnicoBuscarCalifAutocontrol.action", function(row) {
			self.getSelector("califAutocontrolAltaModifChequeo").val(row.codigo);
			self.getSelector("califAutocontrolChequeoLabel").html(row.descripcion);
		}, {descripcion : ""}, "resultadoSituar");
	});
	self.getSelector("popupBusquedaCalifOficialAltaModifChequeo").click(function() {
	    situarPopupEvent.reset();
		situarPopupEvent.nombreSituar = "Situar";
		situarPopupEvent.create("chequeoTecnicoBuscarCalifOficial.action", function(row) {
			self.getSelector("califOficialAltaModifChequeo").val(row.codigo);
			self.getSelector("califOficialChequeoLabel").html(row.descripcion);
		}, {descripcion : ""}, "resultadoSituar");
	});
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.validateChequeo = function() {
	trabajarConFichasDeChequeoTecnicoEvent.getFormAltaModifChequeo().validate({
		rules : {
			"fichaRequest.tituloOriginal" : "required",
			"fichaRequest.tituloCastellano" : "required",
			"fichaRequest.genero" : "required",
//			"fichaRequest.color" : "required",
//			"fichaRequest.anio" : "required",
//			"fichaRequest.pais" : "required",
//			"fichaRequest.nacionalidad" : "required",
//			"fichaRequest.califArtistica" : "required",
//			"fichaRequest.califOficial" : "required",
//			"fichaRequest.califAutocontrol" : "required",
//			"fichaRequest.estCopia" : "required",
//			"fichaRequest.audio" : "required",
//			"fichaRequest.imagen" : "required",
//			"fichaRequest.durArtistica" : "required",
//			"fichaRequest.creditos" : "required",
//			"fichaRequest.tipoAudio" : "required",
//			"fichaRequest.autAutor" : "required",
//			"fichaRequest.pagaArgentores" : "required",
//			"fichaRequest.capituloSegmentado" : "required",
//			"fichaRequest.cantSegmentos" : "required",
//			"fichaRequest.aceptadoVTR" : "required",
//			"fichaRequest.film" : "required",
//			"fichaRequest.operadorVTR" : "required",
			"fichaRequest.fechaChequeo" : "required"
		},
		submitHandler : function(form) {
			return false;
		},
		errorPlacement : function(error, element) {
		},
		invalidHandler: function(form, validator) {
			var errors = validator.numberOfInvalids();
		    if (errors) {
		    	validator.errorList[0].element.name.split(".")[1];
		    	
		        var errors = "";
		        if (validator.errorList.length > 0) {
		            for (var x=0 ; x<validator.errorList.length ; x++) {
		                
		                var nombresAMostrar = { "tituloOriginal": "Tiulo Original", "tituloCastellano": "Titulo Castellano", "genero": "Genero", "fechaChequeo": "Fecha Chequeo"};
		                var key = validator.errorList[x].element.name.split(".")[1];
		                errors += "  *" + nombresAMostrar[key] + "*  ";
		            }
		        }
		    	MESSAGE.alert("Los campos: " + errors + " son obligatorios");
		    }
		},
		errorClass : 'invalid'
	});
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.cancelarCheckeo = function() {
	trabajarConFichasDeChequeoTecnicoEvent.getDivChequeoFicha().hide();
	trabajarConFichasDeChequeoTecnicoEvent.getSelector("altaModifChequeoSegmentosDiv").hide();
	trabajarConFichasDeChequeoTecnicoEvent.getSelector("altaModifChequeoActoresDiv").hide();
	trabajarConFichasDeChequeoTecnicoEvent.getSelector("altaModifChequeoSenialesOkFilmDiv").hide();
	trabajarConFichasDeChequeoTecnicoEvent.getDivBusquedaInicial().show();
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.volverAChequeoDesdeSegmentos = function() {
	trabajarConFichasDeChequeoTecnicoEvent.getSelector("altaModifChequeoSegmentosDiv").hide();
	trabajarConFichasDeChequeoTecnicoEvent.getSelector("altaModifChequeoFichaDiv").show();
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.volverAChequeoDesdeSenialesOkFilm = function(confirmar) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.getSelector("altaModifChequeoSenialesOkFilmDiv").hide();
	self.getSelector("altaModifChequeoFichaDiv").show();
	self.volverACargarFicha(confirmar);
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.volverAChequeoDesdeActores = function(confirmar) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.getSelector("altaModifChequeoActoresDiv").hide();
	self.getSelector("altaModifChequeoFichaDiv").show();
	self.volverACargarFicha(confirmar);
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.volverACargarFicha = function(confirmar) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.service.cargarFicha(self.nroFichaSeleccionada, function(data) {
		self.fichaCompletaResponse = data;
	});
	if (confirmar) {
		self.getSelector("siguienteAltaModifChequeoFicha").hide();
		self.getSelector("confirmarAltaModifChequeoFicha").show();
	}
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.addInputActor = function() {
	var index = $(".actorCount").length;
	trabajarConFichasDeChequeoTecnicoEvent.getSelector("divToAppendActores")
	.append(TrabajarConFichasDeChequeoTecnicoEvent.TEMPLATES.inputAltaActores.replace(/{{index}}/g,index)); 
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.removeInputActor = function() {
	if ($(".actorCount").length > 1) {
		var self = trabajarConFichasDeChequeoTecnicoEvent;
		$('#'+self.div.id+'_divToAppendActores div:last').remove();
		$('#'+self.div.id+'_divToAppendActores div:last').remove();
	}
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.addInputSinopsis = function() {
	var index = $(".sinopsisCount").length;
	trabajarConFichasDeChequeoTecnicoEvent.getSelector("divToAppendSinopsis")
	.append(TrabajarConFichasDeChequeoTecnicoEvent.TEMPLATES.inputAltaSinopsis.replace(/{{index}}/g,index)); 
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.removeInputSinopsis = function() {
	if ($(".sinopsisCount").length > 1) {
		var self = trabajarConFichasDeChequeoTecnicoEvent;
		$('#'+self.div.id+'_divToAppendSinopsis div:last').remove();
	}
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.addInputObservacion = function() {
	var index = $(".observacionCount").length;
	trabajarConFichasDeChequeoTecnicoEvent.getSelector("divToAppendObservaciones")
	.append(TrabajarConFichasDeChequeoTecnicoEvent.TEMPLATES.inputAltaObservaciones.replace(/{{index}}/g,index)); 
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.removeInputObservacion = function() {
	if ($(".observacionCount").length > 1) {
		var self = trabajarConFichasDeChequeoTecnicoEvent;
		$('#'+self.div.id+'_divToAppendObservaciones div:last').remove();
	}
};



TrabajarConFichasDeChequeoTecnicoEvent.prototype.confirmarChequeoFicha = function() {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	
	if (self.chequeoFichaSerialized !== (self.getFormAltaModifChequeo().serialize()+"&fichaRequest.confirma="+"N")) {
		self.guardarChequeoFicha();
		return;
	}
	

	self.service.validarPayTV(self.ficha.nroFicha, self.fichaCompletaResponse.ficha.clave, function(response){
		
			if(response.validarPayTV.error==="W" || response.validarPayTV.error=== null){
			   if(response.validarPayTV.error=== "W"){
				 self.drawPopupPayTV(response); 
			   }
				
			  self.service.popupTitulosConContratosExhibidos(self.ficha.nroFicha, self.okFilm, function(response) {
					if (response.mensaje!=null) {			
						self.drawPopupContratosExhibidos(response);
					}else{			
						self.service.popupTitulosConContratosExhibidosSN(self.ficha.nroFicha, self.okFilm, function(response) {
							if(response.mensaje!=null){
								self.drawPopupContratosExhibidosSN(response);
							}else{
								var data = {
										nroFicha : self.ficha.nroFicha,
										clave : self.fichaCompletaResponse.ficha.clave,							
										capitulo : self.fichaCompletaResponse.ficha.capitulo,
										parte : self.fichaCompletaResponse.ficha.parte,
										okFilmo : self.okFilm  
								};
								self.service.popupVerificarDesenlace(data, function(response) {
									if(response.verificarDesenlaceResponse.ejecutaEnlace==="S"){
                                        self.chequeoFichaSerialized = self.getFormAltaModifChequeo().serialize() + "&fichaRequest.confirma="+"S";
                                        self.service.guardarChequeoDetalle(self.chequeoFichaSerialized, function() {});
									    trabajarConFichasDeChequeoTecnicoEvent.cancelarCheckeo();
									    modificarContratoBusiness.trabajarReRunDesdeChequeo(
									            response.verificarDesenlaceResponse.contrato, 
	                                            self.fichaCompletaResponse.ficha.clave, 
	                                            response.verificarDesenlaceResponse.grupo, 
	                                            response.verificarDesenlaceResponse.senial,
	                                            "T", "M");
									} else{
										if(response.verificarDesenlaceResponse.mensaje!=null){
											self.popupVerificarDesenlace(response);
										}else if(response.verificarDesenlaceResponse.ejecuta==="S"){
											var data = {
													nroFicha:self.ficha.nroFicha
											};
											self.service.actualizarPY6001(data);
											self.chequeoFichaSerialized = self.getFormAltaModifChequeo().serialize() + "&fichaRequest.confirma="+"S";
                                            self.service.guardarChequeoDetalle(self.chequeoFichaSerialized, function() {
                                                self.cancelarCheckeo();
                                            });
										}else{
											self.chequeoFichaSerialized = self.getFormAltaModifChequeo().serialize() + "&fichaRequest.confirma="+"S";
											self.service.guardarChequeoDetalle(self.chequeoFichaSerialized, function() {
												self.cancelarCheckeo();
											});
										}
									}
									
									
								});
								
								/*self.service.guardarChequeoDetalle(self.chequeoFichaSerialized, function() {
									self.cancelarCheckeo();
								});*/
							}
							
						});
						
					}
				});
			}else{
	
				self.drawPopupPayTV(response);
				self.cancelarCheckeo();
			}
		
	});
	
		
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.drawPopupContratosExhibidos = function(data) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.getSelector("mensajeContratosExhibidos").text("");
	self.getSelector("popupTitulosConContratosExhibidos").dialog({
		title : 'Contratos Exhibidos',
		width: 600,
		show: 'blind',
		hide: 'blind',
		modal: true,
		autoOpen: true,
		position: 'center',
		buttons: {
			
			"Salir": function() {
				
				$( this ).dialog( "close" );
			}
		}
	});
	self.drawGridContratosExhibidos(data);
	self.getSelector("mensajeContratosExhibidos").text(data.mensaje);
};

/*TrabajarConFichasDeChequeoTecnicoEvent.prototype.drawPopupConfirmar = function() {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.getSelector("mensajeConfirmacion").text("");
	self.getSelector("popupPrueba").dialog({
		title : 'Errores con Confirmación',
		width: 520,
		show: 'blind',
		hide: 'blind',
		modal: true,
		autoOpen: true,
		position: 'center',
		buttons: {
			"Si": function() {
				$( this ).dialog( "close" );
				var data = {
						senial : $("#senialDefaultUsuario").val(),
						clave : self.fichaCompletaResponse.ficha.clave,
						capitulo : self.fichaCompletaResponse.ficha.capitulo,
						parte : self.fichaCompletaResponse.ficha.parte
				};
				self.service.popupTitulosConContratosExhibidosSN(data, function(response) {
					if (response && response.contratosExhibidos.length) {
						self.drawPopupContratosExhibidosSN(response);
					}
				});
			},
			"No": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	//self.drawGridContratosExhibidos(data);
	self.getSelector("mensajeConfirmacion").text("Hay un rerun con contrato posterior para el titulo, el sistema intentará desenlazar este contrato  Desea hacer la modificación?");
	
};*/

TrabajarConFichasDeChequeoTecnicoEvent.prototype.drawPopupPayTV = function(data) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.getSelector("mensajeContratosExhibidos").text("");
	self.getSelector("popupTitulosConContratosExhibidos").dialog({
		title : 'Verificar Pay TV',
		width: 600,
		show: 'blind',
		hide: 'blind',
		modal: true,
		autoOpen: true,
		position: 'center',
		buttons: {
			"OK": function() {
				$( this ).dialog( "close" );				
			}
		}
	});
	//self.getSelector("mensajeContratosExhibidos").text(data.mensaje);
	self.drawGridWarningErroresPayTV(data);
	
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.drawPopupContratosExhibidosSN = function(data) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.getSelector("mensajeContratosExhibidos").text("");
	self.getSelector("popupTitulosConContratosExhibidos").dialog({
		title : 'Contratos Exhibidos',
		width: 600,
		show: 'blind',
		hide: 'blind',
		modal: true,
		autoOpen: true,
		position: 'center',
		buttons: {
			"Si": function() {
				$( this ).dialog( "close" );
				self.chequeoFichaSerialized = self.getFormAltaModifChequeo().serialize() +
                "&fichaRequest.confirma="+"S";
				self.service.guardarChequeoDetalle(self.chequeoFichaSerialized, function() {
					self.cancelarCheckeo();
					$( this ).dialog( "close" );
				});
			},
			"No": function(){			
				$( this ).dialog( "close" );
			}
		}
	});
	self.getSelector("mensajeContratosExhibidos").text(data.mensaje);
	self.drawGridContratosExhibidos(data);
	
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.popupVerificarDesenlace = function(data) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.getSelector("mensajeContratosExhibidos").text(data.verificarDesenlaceResponse.mensaje);
	self.getSelector("popupTitulosConContratosExhibidos").dialog({
		title : 'Verificar Descenlace',
		width: 600,
		show: 'blind',
		hide: 'blind',
		modal: true,
		autoOpen: true,
		position: 'center',
		buttons: {
			"Si": function() {
				
				if(data.error=="E"){//Si tiene listado de errores
					$( this ).dialog( "close" );
					//muestro grilla de errores
					self.popupVerificarDesenlaceError(data);
					
				}else{
					//TODO llamar al ejecutar py
					if(data.verificarDesenlaceResponse.ejecuta=="S"){
						var dataAux = {
								nroFicha:self.ficha.nroFicha
						};
						self.service.actualizarPY6001(dataAux);
					}
					//TODO ejecutar descenlace
					var dataAux2 = {
							nroFicha : self.ficha.nroFicha,
							senial : $("#senialDefaultUsuario").val(),
							clave : self.fichaCompletaResponse.ficha.clave,
							capitulo : self.fichaCompletaResponse.ficha.capitulo,
							parte : self.fichaCompletaResponse.ficha.parte
					};
					self.service.ejecutarDesenlace(dataAux2, function(response) {
						if (response.mensaje == "OK") {
							//TODO grabar
							self.getSelector("popupTitulosConContratosExhibidos").dialog( "close" );
							self.chequeoFichaSerialized = self.getFormAltaModifChequeo().serialize() +
                            "&fichaRequest.confirma="+"S";
							self.service.guardarChequeoDetalle(self.chequeoFichaSerialized, function() {
								self.cancelarCheckeo();
								self.getSelector("popupTitulosConContratosExhibidos").dialog( "close" );
							});
						}else{
							alert("No ejecuto Desenlace");
							self.getSelector("popupTitulosConContratosExhibidos").dialog( "close" );
						}
					});
					
					
				}
				
			},
			"No": function(){			
				$( this ).dialog( "close" );
			}
		}
	});
	self.getSelector("mensajeContratosExhibidos").text(data.mensaje);
	//self.drawGridContratosExhibidos(data);
	
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.popupVerificarDesenlaceError = function(data) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.getSelector("mensajeContratosExhibidos").text("");
	self.getSelector("popupTitulosConContratosExhibidos").dialog({
		title : 'Errores Desenlace',
		width: 600,
		show: 'blind',
		hide: 'blind',
		modal: true,
		autoOpen: true,
		position: 'center',
		buttons: {
			"Salir": function() {
				$( this ).dialog( "close" );
				
			}
		}
	});
	//self.getSelector("mensajeContratosExhibidos").text(data.mensaje);
	self.drawGridErroresDescenlace(data);
	
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.drawGridWarningErroresPayTV = function(data) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.getSelector("gridContratosExhibidos").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Tipo','Descripcion'],
		colModel:[ 
			{name:'tipoError',index:'contrato', sortable:false, width:50}, 
			{name:'descripcion',index:'descripcion', sortable:false, width:50},			 
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#TrabajarConFichasDeChequeoTecnicoEventId_pagerGridContratosExhibidos',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		hidegrid: false
	});
	self.getSelector("gridContratosExhibidos").clearGridData().setGridParam({data: data.validarPayTV.erroresDesenlace}).trigger('reloadGrid');
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.drawGridContratosExhibidos = function(data) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.getSelector("gridContratosExhibidos").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Contrato','Grupo','Señal','Fecha Exhibición'],
		colModel:[ 
			{name:'contrato',index:'contrato', sortable:false, width:50}, 
			{name:'grupo',index:'grupo', sortable:false, width:50}, 
			{name:'senial',index:'senial', sortable:false, width:50}, 
			{name:'fecha',index:'fecha', sortable:false, width:100, formatter: 'date', formatoptions: { srcformat: 'Y/m/d', newformat: 'd/m/Y'}}, 
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#TrabajarConFichasDeChequeoTecnicoEventId_pagerGridContratosExhibidos',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		hidegrid: false
	});
	self.getSelector("gridContratosExhibidos").clearGridData().setGridParam({data: data.contratosExhibidos}).trigger('reloadGrid');
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.drawGridErroresDescenlace= function(data) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.getSelector("gridContratosExhibidos").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Tipo Error','Descripción','Contrato','Grupo', 'Señal', 'Tipo Titulo', 'Nro Titulo', 'Fecha Desde', 'Tipo Listado'],
		colModel:[ 
			{name:'tipoError',index:'tipoError', sortable:false, width:50}, 
			{name:'descripcion',index:'descripcion', sortable:false, width:50}, 
			{name:'contrato',index:'contrato', sortable:false, width:50}, 
			{name:'grupo',index:'grupo', sortable:false, width:50},
			{name:'senial',index:'senial', sortable:false, width:50},
			{name:'tipoTitulo',index:'tipoTitulo', sortable:false, width:50},
			{name:'nroTitulo',index:'nroTitulo', sortable:false, width:50},
			{name:'fechaDesde',index:'fechaDesde', sortable:false, width:100, formatter: 'date', formatoptions: { srcformat: 'Y/m/d', newformat: 'd/m/Y'}},
			{name:'tipoListado',index:'tipoListado', sortable:false, width:50},
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#TrabajarConFichasDeChequeoTecnicoEventId_pagerGridContratosExhibidos',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		hidegrid: false
	});
	self.getSelector("gridContratosExhibidos").clearGridData().setGridParam({data: data.erroresDesenlace}).trigger('reloadGrid');
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.guardarChequeoFicha = function() {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.okFilm = self.getSelector("filmAltaModifChequeo").val();
	if (esTituloConCapitulos(self.ficha.clave)) {
		self.chequeoFichaSerialized = self.getFormAltaModifChequeo().serialize();
	}else{
		self.chequeoFichaSerialized = self.getFormAltaModifChequeo().serialize();
        /*+"&fichaRequest.aceptadoVTR="+self.getSelector("aceptadoVTRAltaModifChequeo").val()
        +"&fichaRequest.film="+self.getSelector("filmAltaModifChequeo").val();*/
	}
	self.chequeoFichaSerialized = self.getFormAltaModifChequeo().serialize() +
	                             "&fichaRequest.confirma="+"N";
	if(!self.getFormAltaModifChequeo().valid()){
		return;
	}
	var califArtistica = $("#TrabajarConFichasDeChequeoTecnicoEventId_califArtisticaAltaModifChequeo").val().trim().toUpperCase();
	if ( (califArtistica != "") && (califArtistica != "B") && (califArtistica != "E") && (califArtistica != "M") && (califArtistica != "MB") && (califArtistica != "R") ) {
        MESSAGE.alert("La calificación artistica debe ser B, E, M, MB o R");
	    return;
	}
	self.service.validarChequeoDetalle(self.chequeoFichaSerialized, function() {
		if (esTituloConCapitulos(self.fichaCompletaResponse.ficha.clave)) {
			if(!self.validInputSegmentado()){
				return;
			}
			if(self.getSelector("cantSegmentosAltaModifChequeo").val()==="0"){
				self.service.guardarChequeoDetalle(self.chequeoFichaSerialized, function() {
					self.getSelector("altaModifChequeoFichaDiv").hide();
					self.getSelector("altaModifChequeoActoresDiv").show();
					self.fillActoresSinopsisObservaciones();
				});
			}else{
				self.service.guardarChequeoDetalle(self.chequeoFichaSerialized, function() {
					self.getSelector("altaModifChequeoFichaDiv").hide();
					self.getSelector("altaModifChequeoSegmentosDiv").show();
					self.fillSegmentos();
				});
			}
			
		}else{
			self.service.guardarChequeoDetalle(self.chequeoFichaSerialized, function() {
				self.getSelector("altaModifChequeoFichaDiv").hide();
				self.getSelector("altaModifChequeoActoresDiv").show();
				self.fillActoresSinopsisObservaciones();
			});
		}
		
	});
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.validInputSegmentado = function() {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	var segmentado = self.getSelector("capituloSegmentadoAltaModifChequeo");
	var cantidadSeg = self.getSelector("cantSegmentosAltaModifChequeo");
	if (Validator.isEmpty(segmentado)) {
		Validator.focus(segmentado);
		return false;
	}else if(segmentado.val() === "S" && (Validator.isEmpty(cantidadSeg) || cantidadSeg.val() === "0")){
		Validator.focus(cantidadSeg, "Debe Ingresar Cant. de Segmentos si el Capítulo es segmentado");
		return false;
	}else if (segmentado.val() === "N" && self.fichaCompletaResponse.segmentos.length) {
		popupConfirmacionEvent.confirmar = function () {
			self.service.eliminarSegmentos(
					self.ficha.nroFicha,
					function() {
						
						self.getSelector("altaModifChequeoSegmentosDiv").hide();
						self.getSelector("altaModifChequeoFichaDiv").hide();
						self.getSelector("altaModifChequeoActoresDiv").show();
						self.fillActoresSinopsisObservaciones();
					});
			popupConfirmacionEvent.close();
	    };
	    popupConfirmacionEvent.cancelar = function () {
	        popupConfirmacionEvent.close();
	    };
	    popupConfirmacionEvent.popTitle = 'Eliminar Segmentos';
	    popupConfirmacionEvent.create("eliminarSegmentosFichaChequeoPopUp","La ficha contiene segmentos, desea eliminarlos?");
		
	}
	return true;
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.fillSegmentos = function() {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.duracionArtistica = self.getSelector("durArtisticaAltaModifChequeo").val();
	self.totalSegmentos = self.getSelector("cantSegmentosAltaModifChequeo").val();
	self.getSelector("divToAppendSegmentos").empty().append(TrabajarConFichasDeChequeoTecnicoEvent.TEMPLATES.primerTrSegmento);
	for ( var i = 0; i < self.totalSegmentos; i++) {
		self.getSelector("divToAppendSegmentos")
		.append(TrabajarConFichasDeChequeoTecnicoEvent.TEMPLATES.segmentos.replace(/{{index}}/g,i));
		var segmento = self.fichaCompletaResponse.segmentos[i];
		if (segmento) {
			self.getSelector("titulo"+i).val(segmento.titulo);
			self.getSelector("duracion"+i).val(segmento.duracion);
			self.getSelector("chequeo"+i).val(segmento.chequeo);
		}
		self.getSelector("numero"+i).val(i+1);
		self.getSelector("duracion"+i).numeric();
	}  
	
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.fillActoresSinopsisObservaciones = function() {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	
	
	for ( var i in self.fichaCompletaResponse.actores) {
		var actor = self.fichaCompletaResponse.actores[i];
		if (i > 0) {
			self.getSelector("divToAppendActores")
			.append(TrabajarConFichasDeChequeoTecnicoEvent.TEMPLATES.inputAltaActores.replace(/{{index}}/g,i)); 
		}
		self.getSelector("actor"+i).val(actor.actor);
		self.getSelector("personaje"+i).val(actor.personaje);
		//self.getSelector("personaje"+i).val(actor.personaje);
	}
	for ( var i in self.fichaCompletaResponse.sinopsis) {
		var sinopsis = self.fichaCompletaResponse.sinopsis[i];
		if (i > 0) {
			self.getSelector("divToAppendSinopsis")
			.append(TrabajarConFichasDeChequeoTecnicoEvent.TEMPLATES.inputAltaSinopsis.replace(/{{index}}/g,i)); 
		}
		self.getSelector("sinopsis"+i).val(sinopsis.sinopsis);
	}
	for ( var i in self.fichaCompletaResponse.observaciones) {
		var observacion = self.fichaCompletaResponse.observaciones[i];
		if (i > 0) {
			self.getSelector("divToAppendObservaciones")
			.append(TrabajarConFichasDeChequeoTecnicoEvent.TEMPLATES.inputAltaObservaciones.replace(/{{index}}/g,i)); 
		}
		self.getSelector("observacion"+i).val(observacion.observacion);
	}
	
	self.getSelector("altaModifChequeoActoresDiv").find("#TrabajarConFichasDeChequeoTecnicoEventId_tituloOriginalChequeoLabel").html(self.getFormAltaModifChequeo().find("#TrabajarConFichasDeChequeoTecnicoEventId_tituloOrigAltaModifChequeo").val());
	self.getSelector("altaModifChequeoActoresDiv").find("#TrabajarConFichasDeChequeoTecnicoEventId_tituloOffChequeoLabel").html( self.getFormAltaModifChequeo().find("#TrabajarConFichasDeChequeoTecnicoEventId_tituloOffAltaModifChequeo").val());
	self.getSelector("altaModifChequeoActoresDiv").find("#TrabajarConFichasDeChequeoTecnicoEventId_tituloCastChequeoLabel").html( self.getFormAltaModifChequeo().find("#TrabajarConFichasDeChequeoTecnicoEventId_tituloCastAltaModifChequeo").val());
	
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.guardaInfoSegmentos = function() {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	if (self.validFormSegmentos()) {
		self.segmentosSerialized = self.getSelector("formAltaModifChequeoSegmentos").serialize();
		self.service.validarSegmentos(
				{segmentos : self.segmentosSerialized, duracionArtistica : self.duracionArtistica, cantidadSegmentos : self.totalSegmentos},
				function(response) {
					if (response) {
						self.service.guardarSegmentos(self.segmentosSerialized, function() {
							self.getSelector("altaModifChequeoSegmentosDiv").hide();
							self.getSelector("altaModifChequeoActoresDiv").show();
							self.fillActoresSinopsisObservaciones();
						});
						return;
					}
					MESSAGE.error("La duración total de segmentos no coincide con la del capítulo");
				});
	}
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.validFormSegmentos = function() {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	for ( var i = 0; i < self.totalSegmentos; i++) {
		var titulo = self.getSelector("titulo"+i);
		var duracion = self.getSelector("duracion"+i);
		var chequeo = self.getSelector("chequeo"+i);
		if (Validator.isEmpty(titulo)) {
			Validator.focus(titulo);
			return false;
		}
		if (Validator.isEmpty(duracion)) {
			Validator.focus(duracion);
			return false;
		}
		if (Validator.isEmpty(chequeo)) {
			Validator.focus(chequeo);
			return false;
		}
	}	
	return true;
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.validarSinopsisObservaciones = function() {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	
	var index = $(".sinopsisCount").length;	
	for ( var i=0; i < index; i++) {
		var sinopsis = self.getSelector("sinopsis"+i);
		
		if (Validator.isEmpty(sinopsis)) {
			/*Validator.focus(sinopsis);
			return false;*/
			sinopsis.val(" ");
		}
		
	}
	
	var indexObs = $(".observacionCount").length;
	for ( var y=0; y < indexObs; y++) {
		var observacion = self.getSelector("observacion"+y);
		
		if (Validator.isEmpty(observacion)) {
			/*Validator.focus(observacion);
			return false;*/
			observacion.val(" ");
		}		
	}
	return true;
	
};


TrabajarConFichasDeChequeoTecnicoEvent.prototype.guardaActoresSinopsisObservFicha = function() {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.actoresSinopsisObservSerialized = self.getSelector("formAltaModifChequeoActores").serialize();
	
	if (self.validarSinopsisObservaciones()){
		self.service.guardarActoresSinopsisYObservaciones(self.actoresSinopsisObservSerialized, function() {
			self.service.buscarSenialesOkFilm(self.ficha.nroFicha, self.okFilm, function(senialesOkFilm) {
				self.senialesOkFilm = senialesOkFilm;
				if (senialesOkFilm.length) {
					self.getSelector("altaModifChequeoActoresDiv").hide();
					self.getSelector("altaModifChequeoSenialesOkFilmDiv").show();
					self.getSelector("divToAppendSenialesOkFilm").empty().append(TrabajarConFichasDeChequeoTecnicoEvent.TEMPLATES.primerTrSeniales);
					for ( var i = 0; i < senialesOkFilm.length; i++) {
						self.getSelector("divToAppendSenialesOkFilm")
						.append(TrabajarConFichasDeChequeoTecnicoEvent.TEMPLATES.seniales.replace(/{{index}}/g,i));
						var senial = senialesOkFilm[i];
						if (senial) {
							self.getSelector("senialFilm"+i).val(senial.senial);
							self.getSelector("senialFilmHidden"+i).val(senial.senial);
							self.getSelector("okVTR"+i).val(senial.okVTR);
							self.getSelector("okFilm"+i).val(senial.okFilm);
						}
					}  
					return;
				}
				self.volverAChequeoDesdeActores(true);
				if(self.okFilm==="S"){
					self.popupDeseaRegistrarCopia();
				}
			    	
			});
		});
	}
	
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.guardaSenialesOkFilm = function() {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.senialesOkFilmSerialized = self.getSelector("formAltaModifChequeoSenialesOkFilm").serialize();
	if (!self.validFormSenialesOkFilm()) {
		return;
	}
	self.service.guardarSenialesOkFilm(self.senialesOkFilmSerialized, function() {
		self.volverAChequeoDesdeSenialesOkFilm(true);
		self.popupDeseaRegistrarCopia();
	});
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.validFormSenialesOkFilm = function() {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	for ( var i = 0; i < self.senialesOkFilm.length; i++) {
		var vtr = self.getSelector("okVTR"+i);
		var film = self.getSelector("okFilm"+i);
		var senialFilm = self.getSelector("senialFilm"+i);
		if(senialFilm.val() === $("#senialDefaultUsuario").val()){
			if(film.val()!==self.getSelector("filmAltaModifChequeo").val()){
				self.getSelector("filmAltaModifChequeo").val(film.val());	
			}
			if(vtr.val()!==self.getSelector("aceptadoVTRAltaModifChequeo").val()){
				self.getSelector("aceptadoVTRAltaModifChequeo").val(vtr.val());
			}			
		}
		if (Validator.isEmpty(vtr)) {
			Validator.focus(vtr);
			return false;
		}
		if (Validator.isEmpty(film)) {
			Validator.focus(film);
			return false;
		}
	}	
	return true;
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.popupDeseaRegistrarCopia = function() {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	if (self.okFilm === "N") {
		return;
	}
	popupConfirmacionEvent.confirmar = function () {
		var data = {
			senial : $("#senialDefaultUsuario").val(),
			ficha : self.nroFichaSeleccionada 
		};
		self.service.buscarRollos(data, self.drawTrabajarConCopias);
		popupConfirmacionEvent.close();
    };
    popupConfirmacionEvent.cancelar = function () {
        popupConfirmacionEvent.close();
    };
    popupConfirmacionEvent.popTitle = 'Registrar Copia';
    popupConfirmacionEvent.create("deseaRegistrarCopiaFichaPopUp","¿Desea registrar una copia?");
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.drawTrabajarConCopias = function(data) {
	Component.get("html/trabajarConFichasDeChequeoTecnico/altaModifCopia.html", function(comp) {
		var self = trabajarConFichasDeChequeoTecnicoEvent;
		self.getSelector("altaModifChequeoDiv").hide();
		comp = comp.replace(/{{id}}/g, self.div.id)
			.replace(/{{clave}}/g, self.ficha.clave)
			.replace(/{{tituloOriginal}}/g, self.ficha.titulo)
			.replace(/{{tituloCastellano}}/g, self.ficha.tituloCastellano)
			.replace(/{{capitulo}}/g, self.ficha.capitulo)
			.replace(/{{parte}}/g, self.ficha.parte);
		if (self.getSelector("listadoCopiasDiv").length) {
			self.getSelector("listadoCopiasDiv").replaceWith(comp);
		}else{
			self.getDiv().append(comp);
		}
		Accordion.getInstance(self.getSelector("accordionListadoCopiasDiv"));
		self.getSelector("agregarCopias").button().click(self.abrirPopupRollos);
		self.getSelector("volverDeListadoCopias").button().click(function() {
			self.getSelector("listadoCopiasDiv").hide();
			self.getSelector("altaModifChequeoDiv").show();
		});
		self.drawGridCopias(data);
		self.popupAltaModifCopia();
		self.agregarFuncionalidadDelPopup(data);
		if (!data.length) {
			self.abrirPopupRollos("A");
		}
	});
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.abrirPopupRollos = function(accion, rowId) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	if (accion === "M") {
		self.accionRollos = "M";
		self.getSelector("tipoAccion").text("Modificación");
		var copia = self.getSelector("gridCopias").jqGrid('getRowData', rowId);
		var lista = self.getSelector("gridCopias").jqGrid('getRowData');
		lista = jQuery.grep(lista, function(o){
			return (o.senial === copia.senial);
		});
		$(".rollosCount").remove();
		for ( var i in lista) {
			var c = lista[i];
			self.drawTrCopia(i);
			self.getSelector("soporteCopia"+i).val(c.soporte);
			self.getSelector("nroRolloCopia"+i).val(c.rollo);
			self.getSelector("sugeridoCopia"+i).val(c.sugerido);
			self.getSelector("secuenciaCopia"+i).val(c.secuencia);
			self.getSelector("numeroCopia"+i).val(c.copia);
		}
		self.getSelector("senialCopia").val(copia.senial);
		//$("#"+self.div.id+"_senialCopia").html($("#senialDefaultUsuario").val());
		self.getSelector("origenCopia").val(copia.origen);
		self.getSelector("fechaEmisionCopia").val(copia.fecha);
		self.getSelector("cantRollosCopia").val(lista.length);
	}else{
		self.accionRollos = "A";
		self.getSelector("tipoAccion").text("Alta");
		$(".rollosCount").remove();
		self.getSelector("senialCopia").val("");
		//$("#"+self.div.id+"_senialCopia").html($("#senialDefaultUsuario").val());
		self.getSelector("origenCopia").val("");
		self.getSelector("fechaEmisionCopia").val("");
		self.getSelector("cantRollosCopia").val("");
	}
	self.getSelector("popupFormAltaModifCopia").dialog("open");
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.agregarFuncionalidadDelPopup = function(data) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	Accordion.getInstance(self.getSelector("popupFormAltaModifCopia"));
	//Datepicker.getInstance(self.getSelector("fechaEmisionCopia"));
	Datepicker.picker(self.getSelector("fechaEmisionCopia"));
	self.getSelector("popupBusquedaSenialesCopia").click(function() {
	    situarPopupEvent.reset();
		situarPopupEvent.nombreSituar = "Situar";
		situarPopupEvent.create("chequeoTecnicoBuscarSenialesAltaCopia.action", function(data) {
			self.getSelector("senialCopia").val(data.codigo);
		}, {descripcion : "", nroFicha: self.ficha.nroFicha}, "resultadoSeniales");
	});
	self.getSelector("cantRollosCopia").numeric();
	self.getSelector("cantRollosCopia").keyup(function() {
		var trsCount = $(".rollosCount").length ? $(".rollosCount").length : 0;
		var cantElegidos = self.getSelector("cantRollosCopia").val();
		var cantParaEliminar = trsCount - cantElegidos;
		for ( var i = 0; i < cantParaEliminar; i++) {
			$(".rollosCount:last").remove();
		}
		for ( var i = 0; i < cantElegidos; i++) {
			if (i >= trsCount) {
				self.drawTrCopia(i);
			}
		}
	});
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.drawTrCopia = function(i) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.getSelector("divToAppendCopias")
		.append(TrabajarConFichasDeChequeoTecnicoEvent.TEMPLATES.trRollos
				.replace(/{{id}}/g,self.div.id).replace(/{{index}}/g,i)); 
	self.getSelector("nroRolloCopia"+i).numeric();
	self.getSelector("popupBusquedaSoporteCopia"+i).click(function() {
		var selector = self.getSelector("soporteCopia"+this.id.substring(this.id.length - 1));
		situarPopupEvent.reset();
		situarPopupEvent.nombreSituar = "Situar";
		situarPopupEvent.create("chequeoTecnicoBuscarSoportes.action", function(response) {
			selector.val(response.CODIGO);
		}, {descripcion : ""}, "resultadoSituar");
	});
	
	
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.popupAltaModifCopia = function() {
	trabajarConFichasDeChequeoTecnicoEvent.getSelector("popupFormAltaModifCopia").dialog({
		title : 'Trabajar con Rollos',
		width: 660,
		show: 'blind',
		hide: 'blind',
		modal: true,
		autoOpen: false,
		position: 'center',
		buttons: {
			"Salir": function() {
				$( this ).dialog( "close" );
			},
			"Aceptar": function() {
				trabajarConFichasDeChequeoTecnicoEvent.guardarRollos();
			}
		}
	});
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.drawGridCopias = function(data) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	self.getSelector("gridCopias").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Senial','Nro. Copia','Soporte','Rollo','Secuencia','Origen','Fecha Emisión','Borrar',''],
		colModel:[ 
			{name:'senial',index:'senial', sortable:false, width:50}, 
			{name:'copia',index:'copia', sortable:false, width:50}, 
			{name:'soporte',index:'soporte', sortable:false, width:50}, 
			{name:'rollo',index:'rollo', sortable:false, width:50}, 
			{name:'secuencia',index:'secuencia', sortable:false, width:50}, 
			{name:'origen',index:'origen', sortable:false, width:50}, 
			{name:'fecha',index:'fecha', sortable:false, width:100, formatter: 'date', formatoptions: { srcformat: 'Y/m/d', newformat: 'd/m/Y'}}, 
			{name:'sugerido',index:'sugerido', sortable:false, width:50}, 
			{name:'modificar',index:'modificar', width:30, aling:'center', sortable:false}
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#TrabajarConFichasDeChequeoTecnicoEventId_pagerGridCopias',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray',
		caption: 'Copias',
		gridComplete: function() {
        	var ids = $(this).jqGrid('getDataIDs');
        	for (var i = 0; i < ids.length; i++) {
        		var rowId = ids[i];
        		var modificar = "<div onclick='trabajarConFichasDeChequeoTecnicoEvent.abrirPopupRollos(\"M\","+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-pencil modificar conTooltip'>Modificar</span></div>";
        		$(this).jqGrid('setRowData', rowId, { modificar: modificar });
        	}
	    }
	});
	self.getSelector("gridCopias").clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.guardarRollos = function() {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	if (!self.validFormRollos()) {
		return;
	}
	//se setean estos campos aqui porque son hidden y el replace de responseVisualizarFicha() no sirve cuando se vuelve a ingresar
	// TO DO refacorizar todo esto

	$("input[name='altaModifRollosRequest.clave']").val(self.ficha.clave);
	$("input[name='altaModifRollosRequest.capitulo']").val(self.ficha.capitulo);
	$("input[name='altaModifRollosRequest.parte']").val(self.ficha.parte);
	
	var data = self.getSelector("formAltaModifRollos").serialize();

	if (self.accionRollos === "M") {
		self.service.modificacionDeRollos(data, self.volverABuscarRollos);
	}else{
		self.service.altaDeRollos(data, self.volverABuscarRollos);
	}

//	if (self.getSelector("origenCopia").val() === "M") {
//		self.service.buscarContratosParaAsociarLaCopia(
//				{senial : $("#senialDefaultUsuario").val(), clave : self.ficha.clave, nroFicha : self.ficha.nroFicha}, 
//				self.drawContratosParaAsociarMater);
//	}
	
	self.getSelector("popupFormAltaModifCopia").dialog("close");
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.drawContratosParaAsociarMater = function(response) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	if (!response.length) {
		return;
	}
	self.getSelector("popupContratosParaAsociarMasters").dialog({
		title : 'Trabajar con Rollos',
		width: 660,
		show: 'blind',
		hide: 'blind',
		modal: true,
		autoOpen: true,
		position: 'center',
		buttons: {
			"Salir": function() {
				$( this ).dialog( "close" );
			},
			"Aceptar": function() {
				self.service.registrarMaster(
					{senial : $("#senialDefaultUsuario").val(), clave : self.ficha.clave, nroFicha : self.ficha.nroFicha},
					function() { 
						self.getSelector("popupContratosParaAsociarMasters").dialog( "close" );
					});
			}
		}
	});
	self.getSelector("gridContratosParaAsociarMasters").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Contrato','Grupo','Señal','Proveedor'],
		colModel:[ 
			{name:'contrato',index:'contrato', sortable:false, width:30}, 
			{name:'grupo',index:'grupo', sortable:false, width:30}, 
			{name:'senial',index:'senial', sortable:false, width:30}, 
			{name:'proveedor',index:'proveedor', sortable:false, width:150} 
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#TrabajarConFichasDeChequeoTecnicoEventId_pagerGridContratosParaAsociarMasters',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray',
		caption: 'Contratos'
	});
	self.getSelector("gridContratosParaAsociarMasters").clearGridData().setGridParam({data: response}).trigger('reloadGrid');
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.volverABuscarRollos = function() {
	var data = {
		senial : $("#senialDefaultUsuario").val(),
		ficha : trabajarConFichasDeChequeoTecnicoEvent.nroFichaSeleccionada 
	};
	trabajarConFichasDeChequeoTecnicoEvent.service.buscarRollos(data, 
			trabajarConFichasDeChequeoTecnicoEvent.drawGridCopias);
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.validFormRollos = function(nroFicha, clave) {
	var self = trabajarConFichasDeChequeoTecnicoEvent;
	var senial = self.getSelector("senialCopia");
	var rollosCopia = self.getSelector("cantRollosCopia");
	var origen = self.getSelector("origenCopia");
	var fecha = self.getSelector("fechaEmisionCopia");
	
    if (Validator.isEmpty(senial)) {
        Validator.focus(senial);
        return false;
    }
	if (Validator.isEmpty(origen)) {
		Validator.focus(origen);
		return false;
	}
	if (Validator.isEmpty(fecha)) {
		Validator.focus(fecha);
		return false;
	}
	if (Validator.isEmpty(rollosCopia)) {
        Validator.focus(rollosCopia);
        return false;
    }
	if (Validator.isLessThanZero(rollosCopia, true, "Cant. de Rollos")) {
        Validator.focus(rollosCopia);
        return false;
    }
	if (!Datepicker.isAValidateDate(fecha.val())) {
	    Validator.focus(fecha);
	    return false;
	}

	for ( var i = 0; i < rollosCopia.val(); i++) {
		var soporte = self.getSelector("soporteCopia"+i);
		var rollo = self.getSelector("nroRolloCopia"+i);
		var sugerido = self.getSelector("sugeridoCopia"+i);
		if (Validator.isEmpty(soporte)) {
			Validator.focus(soporte);
			return false;
		}
		if (Validator.isEmpty(rollo)) {
			Validator.focus(rollo);
			return false;
		}
		if (Validator.isEmpty(sugerido)) {
			Validator.focus(sugerido);
			return false;
		}
	}	
	return true;
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.inactivarFicha = function(nroFicha, clave, estado) {
	if (estado == "A") {
		popupConfirmacionEvent.confirmar = function () {
			trabajarConFichasDeChequeoTecnicoEvent.service.inactivarFicha(
					{nroFicha:nroFicha,clave:clave},trabajarConFichasDeChequeoTecnicoEvent.buscarFichas);
			popupConfirmacionEvent.close();
		};
		popupConfirmacionEvent.cancelar = function () {
			popupConfirmacionEvent.close();
		};
		popupConfirmacionEvent.popTitle = 'Inactivar Ficha';
		popupConfirmacionEvent.create("inactivarFichaPopUp","¿Confirma la inactivaci&oacute;n?");
	}
	else {
		alert("La ficha ya se encuentra Inactiva");
		
	}
};

TrabajarConFichasDeChequeoTecnicoEvent.prototype.imprimirFichaTecnica = function(nroFicha) {

		trabajarConFichasDeChequeoTecnicoEvent.service.imprimirReporte(
				{nroFicha:nroFicha},trabajarConFichasDeChequeoTecnicoEvent.buscarFichas);
		
  
};


TrabajarConFichasDeChequeoTecnicoEvent.prototype.getAccordionBusquedaInicial = function() {
	return $("#" + this.div.id + "_accordionBusquedaInicial");
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.getInputFechaDesde = function() {
	return $("#" + this.div.id + "_fechaDesde");
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.getInputFechaHasta = function() {
	return $("#" + this.div.id + "_fechaHasta");
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.getButtonBuscarFichas = function() {
	return $("#" + this.div.id + "_buscarFichas");
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.getInputsText = function() {
	return $("#" + this.div.id + "_accordionBusquedaInicial input");
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.getDivBusquedaPorClave = function() {
	return $("#" + this.div.id + "_busquedaPorClave");
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.getDivBusquedaPorTituloOriginal = function() {
	return $("#" + this.div.id + "_busquedaPorTituloOriginal");
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.getDivBusquedaPorTituloCastellano = function() {
	return $("#" + this.div.id + "_busquedaPorTituloCastellano");
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.getGridListadoFichas = function() {
	return $("#" + this.div.id + "_gridFichas");
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.getDivBusquedaInicial = function() {
	return $("#" + this.div.id + "_busquedaInicial");
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.getDivAgregarFicha = function() {
	return $("#" + this.div.id + "_altaModifFichaDiv");
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.getDivVisualizarFicha = function() {
	return $("#" + this.div.id + "_visualizacionFichaDiv");
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.getDivChequeoFicha = function() {
	return $("#" + this.div.id + "_altaModifChequeoDiv");
};
TrabajarConFichasDeChequeoTecnicoEvent.prototype.getFormAltaModifChequeo = function() {
	return $("#" + this.div.id + "_formAltaModifChequeoFicha");
};
var trabajarConFichasDeChequeoTecnicoEvent = new TrabajarConFichasDeChequeoTecnicoEvent(new DivDefinition('TrabajarConFichasDeChequeoTecnicoEventId'));