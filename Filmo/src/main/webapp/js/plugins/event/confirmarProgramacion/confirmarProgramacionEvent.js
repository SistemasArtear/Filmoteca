/**
 * FP340 - Confirmar programación
 * @author cetorres
 */

/***********************************************************************************		
 ********************											********************
 ***********************************************************************************/
function ConfirmarProgramacionEvent(div) {
	this.div = div;
	this.tituloSeleccionado;
};

extend(ConfirmarProgramacionEvent, Plugin);

/***********************************************************************************		
 ********************	 		Confirmar programación			********************
 ***********************************************************************************/
ConfirmarProgramacionEvent.prototype.create = function() {
	var self = confirmarProgramacionEvent;
	self.service = new ConfirmarProgramacionService();
    armadoDeProgramacionEvent.service = new ArmadoDeProgramacionService();
	Component.get("html/confirmarProgramacion/confirmarProgramacion.html", confirmarProgramacionEvent.draw);
};

ConfirmarProgramacionEvent.prototype.draw = function(comp) {
	var self = confirmarProgramacionEvent;
	$("#viewContainer").empty().append(comp.replace(/{{id}}/g, self.div.id));

	Accordion.getInstance(self.getSelector("accordionConfirmarProg"));
	
	var yesterday = new Date();
	yesterday.setDate(yesterday.getDate() - 1);
	Datepicker.getInstance(self.getSelector("fecha"), yesterday);
	self.getSelector("fecha").datepicker('option', {maxDate: "-1d"});

	self.getSelector("programa").numeric();
	self.getSelector("capitulo").numeric();
	self.getSelector("parte").numeric();
	self.getSelector("segmento").numeric();
	
	self.getSelector("popupPrograma").click(self.drawPopupSituarProgramas);
	self.getSelector("limpiarPrograma").click(self.limpiarFiltroProgramas);
	//self.getSelector("generarListadoENC").button().click(self.generarListadoENC);
	self.getSelector("popupTitulo").click(self.drawPopupSituarTitulos);
	self.getSelector("limpiarTitulo").click(self.limpiarFiltroTitulos);
	self.getSelector("limpiarFiltros").button().click(self.limpiarPantalla);
	
	
	var filtros = ['Programa', 'Clave', 'Capitulo', 'Parte', 'Segmento'];
	for (var i in filtros) {
	  $("#ConfirmarProgramacionEventId_filtro"+filtros[i]+"DivContainer").hide();
	}
	
	armadoDeProgramacionEvent.service.obtenerGrillaProgramada = function() {
		self.buscarProgramacion();
	};
	self.getSelector("programarCapitulos").button().click(function() {
		if (Validator.isEmpty(self.getSelector("fecha"))) {
			Validator.focus(self.getSelector("fecha"), "Debe ingresar una fecha válida");
		} else if (self.getSelector("fecha").datepicker("getDate") > yesterday) {
			Validator.focus(self.getSelector("fecha"), "La fecha de exhibición debe ser menor que la actual");
		} else {
			self.drawPopupSituarProgramasAlta(true);
		}
    });
	self.getSelector("programarSinCapitulos").button().click(function() {
		if (Validator.isEmpty(self.getSelector("fecha"))) {
			Validator.focus(self.getSelector("fecha"), "Debe ingresar una fecha válida");
		} else if (self.getSelector("fecha").datepicker("getDate") > yesterday) {
			Validator.focus(self.getSelector("fecha"), "La fecha de exhibición debe ser menor que la actual");
		} else {
			self.drawPopupSituarProgramasAlta(false);
		}
    });

	self.getSelector("buscarProgramacion").button().click(self.buscarProgramacion);
	self.drawGridProgramas();
};

ConfirmarProgramacionEvent.prototype.limpiarPantalla = function() {
	var self = confirmarProgramacionEvent;
	var yesterday = new Date();
	yesterday.setDate(yesterday.getDate() - 1);
	self.getSelector("fecha").datepicker('setDate', yesterday);
	self.limpiarFiltroProgramas();
	self.limpiarFiltroTitulos();
	self.getSelector("capitulo").val(null);
	self.getSelector("parte").val(null);
	self.getSelector("segmento").val(null);
	self.fillGrid(new Array());
};

/***********************************************************************************		
 ********************		 	Situar por programas			********************
 ***********************************************************************************/
ConfirmarProgramacionEvent.prototype.drawPopupSituarProgramas = function() {
    var data = {descripcionPrograma : "", senial: variablesSesionUsuarioEvent.dameSenialSeleccionada()};
	situarPopupEvent.reset();
	situarPopupEvent.nombreSituar = "Programa original";
	situarPopupEvent.doAfterDrawGrid = function() {
	    situarPopupEvent.getGrid().sortGrid('descripcion', true);
	};
	situarPopupEvent.create("buscarProgramasDesconfirmar.action", confirmarProgramacionEvent.programaSelected, data, "programas");
};

ConfirmarProgramacionEvent.prototype.programaSelected = function(programa) {
	var self = confirmarProgramacionEvent;
	self.getSelector("programa").val(programa.codigo);
	self.getSelector("descripcionPrograma").val(programa.descripcion);
};

ConfirmarProgramacionEvent.prototype.limpiarFiltroProgramas = function() {
	var self = confirmarProgramacionEvent;
	self.getSelector("programa").val(null);
	self.getSelector("descripcionPrograma").val(null);
};


/***********************************************************************************		
 ********************		 	Situar por títulos				********************
 ***********************************************************************************/
ConfirmarProgramacionEvent.prototype.drawPopupSituarTitulos = function() {
	var data = {descripcionTitulo : ""};
    situarPopupEvent.reset();
	situarPopupEvent.nombreSituar = "Títulos por descripción";
	situarPopupEvent.doAfterDrawGrid = function() {
	    situarPopupEvent.getGrid().sortGrid('titulo', true);
	};
	situarPopupEvent.create("buscarTitulosDesconfirmar.action", confirmarProgramacionEvent.tituloSelected, data, "titulos");
};

ConfirmarProgramacionEvent.prototype.tituloSelected = function(titulo) {
	var self = confirmarProgramacionEvent;
	self.getSelector("clave").val(titulo.clave);
	self.getSelector("descripcionTitulo").val(titulo.titulo);
};

ConfirmarProgramacionEvent.prototype.limpiarFiltroTitulos = function() {
	var self = confirmarProgramacionEvent;
	self.getSelector("clave").val(null);
	self.getSelector("descripcionTitulo").val(null);
};

/***********************************************************************************		
 ********************	 		Buscar programación				********************
 ***********************************************************************************/
ConfirmarProgramacionEvent.prototype.buscarProgramacion = function() {
	var yesterday = new Date();
	yesterday.setDate(yesterday.getDate() - 1);
	var self = confirmarProgramacionEvent;

	if (Validator.isEmpty(self.getSelector("fecha"))) {
		Validator.focus(self.getSelector("fecha"), "Debe ingresar una fecha válida");
	} else if (self.getSelector("fecha").datepicker("getDate") > yesterday) {
		Validator.focus(self.getSelector("fecha"), "La fecha de exhibición debe ser menor que la actual");
	} else {
		var listarProgramacionRequest = {
			senial: $("#senialDefaultUsuario").val(),
			fecha: self.getSelector("fecha").val(),
			tipoOrden: self.tipoOrden, 
			codigoPrograma: self.getSelector("programa").val(),
			codigoTitulo: self.getSelector("clave").val(),
			capitulo: self.getSelector("capitulo").val(),
			parte: self.getSelector("parte").val(),
			segmento: self.getSelector("segmento").val()
		};
		self.service.listarProgramacion(listarProgramacionRequest);
	}
};

/***********************************************************************************		
 ********************	 		Grilla programación				********************
 ***********************************************************************************/
ConfirmarProgramacionEvent.prototype.drawGridProgramas = function() {
	var self = confirmarProgramacionEvent;
	var lastsel = null;
	var edicionRatingExcedente = true;
	self.getSelector("grid").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Programa','Hora','Descripción','Título','Contrato','Clave', 'Capítulo', 'P', 'S', 'F', 'D', 'OR','Rating','Exced.','','','','','','','','','','','',''],
		colModel:[ 
		    {name:'codigoPrograma',		index:'codigoPrograma', 	width:40, align:'center', sortable:false, sorttype:'number'},
			{name:'hora',				index:'hora', 				width:30, align:'center', sortable:false, sorttype:'text', 
				formatter:function(value, options, rData) { 
					var hora = value.substring(0,2);
					var minutos = value.substring(2,4);
					/* Horarios en formato HH:MM */
					return hora + ":" + minutos;
				}
			}, 
			{name:'descripcionPrograma',index:'descripcionPrograma',width:120, align:'left',  sortable:false}, 
			{name:'titulo',				index:'titulo', 			width:120, align:'left',  sortable:false},
			{name:'contrato',			index:'contrato', 			width:35, align:'center', sortable:false}, 
			{name:'clave',				index:'clave', 				width:40, align:'center', sortable:false, sorttype:'text'},
			{name:'capitulo',			index:'capitulo', 			width:35, align:'center', sortable:false}, 
			{name:'parte',				index:'parte', 				width:20, align:'center', sortable:false}, 
			{name:'segmento',			index:'segmento', 			width:20, align:'center', sortable:false}, 
			{name:'fraccion',			index:'fraccion', 			width:20, align:'center', sortable:false}, 
			{name:'de',					index:'de', 				width:20, align:'center', sortable:false}, 
			{name:'orden',				index:'orden', 				width:20, align:'center', sortable:false},
			{name:'nuevoValorRating',	index:'nuevoValorRating',	width:20, align:'center', sortable:false},
			{name:'nuevoValorExcedente',index:'nuevoValorExcedente',width:20, align:'center', sortable:false},
			{name:'mas',				index:'mas',				width:20, align:'center', sortable:false, formatter:function(value, options, rData) { return value > 1 ? "<div style='display:inline-table; cursor:pointer;'><span class='ui-icon ui-icon-circle-plus conTooltip' title=\"" + value + "\"></span></div>": '' ; }},
			{name:'editar',				index:'editar',				width:20, align:'center', sortable:false, formatter:function(value,options,rData){return "<div style='display:inline-table; cursor:pointer;'><span class='ui-icon 	ui-icon-pencil conTooltip' title=\"Modificar rating/excedente \">Editar</span></div>"}},
			{name:'confirmar',			index:'confirmar', 			width:20, align:'center', sortable:false},
			{name:'eliminar',			index:'eliminar', 			width:20, align:'center', sortable:false},
			{name:'confirmarCambioRatingExcedente', index:'confirmarCambioRatingExcedente', hidden:true, width:20, align:'center', sortable:false, formatter:function(value,options,rData){return "<div style='display:inline-table; cursor:pointer;'><span class='ui-icon 	ui-icon-check conTooltip' title=\"Confirmar modificación rating/excedente \">ConfirmarModificacionRatingExcedente</span></div>"}},
			{name:'cancelar',  			index:'cancelar',  			width:20, align:'center', sortable:false, hidden:true, formatter:function(value,options,rData){return "<div style='display:inline-table; cursor:pointer;'><span class='ui-icon 	ui-icon-closethick conTooltip' title=\"Cancelar cambio rating/excedente \">Cancelar</span></div>"}},
			{name:'fecha',				index:'fecha',				hidden:true, formatter:function(value, options, rData) { return confirmarProgramacionEvent.getSelector("fecha").val();} },
			{name:'senial',				index:'senial',				hidden:true},
			{name:'senialCon',			index:'senialCon',			hidden:true},
			{name:'grupo',				index:'grupo',				hidden:true},
			{name:'rating',				index:'rating',				hidden: true},
			{name:'excedente',			index:'excedente',			hidden: true},
		],
		rowNum: 20,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + this.div.id + '_pagerGrid',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Programas',
		gridComplete: function() {
        	var ids = $(this).jqGrid('getDataIDs');
        	for (var i = 0; i < ids.length; i++) {
        		var rowId = ids[i];
				var confirmar = "<div style='display:inline-table; cursor:pointer' onclick='confirmarProgramacionEvent.confirmarExhibicion("+rowId+")'><span class='ui-icon ui-icon-circle-check confirmar conTooltip'>Confirmar</span></div>";
				var eliminar = "<div style='display:inline-table; cursor:pointer' onclick='confirmarProgramacionEvent.eliminar("+rowId+")'><span class='ui-icon ui-icon-trash eliminar conTooltip'>Eliminar</span></div>";
				$(this).jqGrid('setRowData', rowId, {confirmar: confirmar, eliminar: eliminar});
        	}
	    },
		onCellSelect: function(id,cellidx,cellvalue){
			if (lastsel == null)
				lastsel = id;
			
			if(id && id == lastsel){
				var esRating = null;
				var columnaNuevoValorRating = $(this).jqGrid("getColProp","nuevoValorRating");
				var columnaNuevoValorExcedente = $(this).jqGrid("getColProp","nuevoValorExcedente");
				columnaNuevoValorRating.editable = false;
				columnaNuevoValorExcedente.editable = false;

				var row = $(this).jqGrid("getLocalRow",id);
				
				if(row.rating == "1"){
					columnaNuevoValorRating.editable = true;
					columnaNuevoValorExcedente.editable = false;
					esRating = true;
				}else if (row.excedente == "1"){
					columnaNuevoValorExcedente.editable = true;
					columnaNuevoValorRating.editable = false;
					esRating = false;
				}else{
					lastsel = null;
					MESSAGE.alert("El programa no aplica para modificar ni el rating ni el excedente.");
				}
				
				if((row.rating == "1" || row.excedente == "1") && $(cellvalue).text() === 'Editar'){
					
					self.toggleColumnasGrillaYBotonConfirmarTodo(edicionRatingExcedente);
					
					$(this).jqGrid('restoreRow',lastsel);
					
					$(this).jqGrid('editRow',id,{
						keys:true,
						aftersavefunc: function (rowid){
							var rowEnEdicion = $(this).jqGrid("getLocalRow",id);
							var mensajeValidacion = self.validarFormatoRatingExcedenteYretornarMensajeError(esRating,(esRating ? rowEnEdicion.nuevoValorRating : rowEnEdicion.nuevoValorExcedente));
							
							if (mensajeValidacion == "")
							{
								lastsel = null;
								self.toggleColumnasGrillaYBotonConfirmarTodo(!edicionRatingExcedente);							
							}else{
								MESSAGE.alert(mensajeValidacion);
							}
						},
						afterrestorefunc: function (rowid){
							lastsel = null;
							self.toggleColumnasGrillaYBotonConfirmarTodo(!edicionRatingExcedente);
						}
					});
				}
				
				if($(cellvalue).text() === "ConfirmarModificacionRatingExcedente"){
					$(this).jqGrid('saveRow',id,{
						aftersavefunc: function (rowid){
							var rowEnEdicion = $(this).jqGrid("getLocalRow",id);
							var mensajeValidacion = self.validarFormatoRatingExcedenteYretornarMensajeError(esRating,(esRating ? rowEnEdicion.nuevoValorRating : rowEnEdicion.nuevoValorExcedente));
							
							if (mensajeValidacion == "")
							{
								lastsel = null;
								self.toggleColumnasGrillaYBotonConfirmarTodo(!edicionRatingExcedente);							
							}else{
								MESSAGE.alert(mensajeValidacion);
							}
						},
						afterrestorefunc: function (rowid){
							lastsel = null;
							self.toggleColumnasGrillaYBotonConfirmarTodo(!edicionRatingExcedente);
						}
					});
				}
				
				if($(cellvalue).text() === 'Cancelar'){
					$(this).jqGrid('restoreRow',lastsel);
					lastsel = null;
					self.toggleColumnasGrillaYBotonConfirmarTodo(!edicionRatingExcedente);
				}
			}
		}
	});
	
	confirmarProgramacionEvent.createButtonOrdenClave();
};

ConfirmarProgramacionEvent.prototype.fillGrid = function(data) {
	var self = confirmarProgramacionEvent;
	self.getSelector("grid").clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};

ConfirmarProgramacionEvent.prototype.resizeGrid = function(obj){
    var $grid = obj;
    newWidth = $grid.closest(".ui-jqgrid").parent().width();
    $grid.jqGrid("setGridWidth", newWidth, true);
};

ConfirmarProgramacionEvent.prototype.toggleColumnasGrillaYBotonConfirmarTodo = function(edicionRatingExcedente){
	var self = confirmarProgramacionEvent;
	var grid = self.getSelector("grid");
	if(edicionRatingExcedente){
		grid.jqGrid("hideCol","confirmar");
		grid.jqGrid("hideCol","eliminar");
		grid.jqGrid("showCol","confirmarCambioRatingExcedente");
		grid.jqGrid("showCol","cancelar");
		$("#gridConfirmarTodo").addClass('ui-state-disabled');
	}else{
		grid.jqGrid("showCol","confirmar");
		grid.jqGrid("showCol","eliminar");
		grid.jqGrid("hideCol","confirmarCambioRatingExcedente");
		grid.jqGrid("hideCol","cancelar");
		$("#gridConfirmarTodo").removeClass('ui-state-disabled');
	}
	self.resizeGrid(grid);
}

ConfirmarProgramacionEvent.prototype.validarFormatoRatingExcedenteYretornarMensajeError = function(esRating,valor){
	var self = confirmarProgramacionEvent;
	
	if(valor.includes("."))
		valor = valor.replace(".",",");
	
	var regex = undefined;
	var message = "";
	if (esRating){
		regex = new RegExp(/^\d\d?(,\d\d?)?$/);
		message = "El valor de rating " + valor + " no es correcto. No debe ser mayor a 2 dígitos y no puede tener más de 2 decimales.";
	}
	else {
		regex = new RegExp(/^\d\d?\d?$/);
		message = "El valor de excedente " + valor + " no es correcto. No debe ser mayor a 3 dígitos y no puede tener decimales.";
	}
	
	if (regex.test(valor))
		message = "";
	else
		MESSAGE.alert(message);
	
	return message;
}

/***********************************************************************************		
 ********************	 		Ordenamiento grilla				********************
 ***********************************************************************************/
ConfirmarProgramacionEvent.prototype.createButtonOrdenClave = function() {
	confirmarProgramacionEvent.getSelector("grid").jqGrid('navGrid', '#ConfirmarProgramacionEventId_pagerGrid', {edit:false, add:false, del:false, search:false, refresh:false}).navButtonAdd('#ConfirmarProgramacionEventId_pagerGrid', {
		caption: "Ordenar por clave",
		buttonicon: "ui-icon-triangle-2-n-s",
		id:"gridOrder",
		onClickButton: function() {
			confirmarProgramacionEvent.ordenPorHoraPrograma();
		}
	}).navButtonAdd('#ConfirmarProgramacionEventId_pagerGrid', {
		caption: "Confirmar todo",
		buttonicon: "ui-icon-circle-check",
		id:"gridConfirmarTodo",
		onClickButton: function() {
			confirmarProgramacionEvent.confirmarExhibicionGrillaProgramacion();
		}
	});
	confirmarProgramacionEvent.tipoOrden = "H";
};
	
ConfirmarProgramacionEvent.prototype.createButtonOrdenHoraPrograma = function() {
	confirmarProgramacionEvent.getSelector("grid").jqGrid('navGrid', '#ConfirmarProgramacionEventId_pagerGrid', {edit:false, add:false, del:false, search:false, refresh:false}).navButtonAdd('#ConfirmarProgramacionEventId_pagerGrid', {
		caption: "Ordenar por hora, programa",
		buttonicon: "ui-icon-triangle-2-n-s",
		id:"gridOrder",
		onClickButton: function() {
			confirmarProgramacionEvent.ordenPorClave();
		}
	}).navButtonAdd('#ConfirmarProgramacionEventId_pagerGrid', {
		caption: "Confirmar todo",
		buttonicon: "ui-icon-circle-check",
		id:"gridConfirmarTodo",
		onClickButton: function() {
			confirmarProgramacionEvent.confirmarExhibicionGrillaProgramacion();
		}
	});
	confirmarProgramacionEvent.tipoOrden = "C";
};

ConfirmarProgramacionEvent.prototype.ordenPorClave = function(data) {
	var self = confirmarProgramacionEvent;
	self.getSelector("grid").remapColumns([1,4,2,3,5,0,6,7,8,9,10,11,12,13,14,15,16,17,18], true, false);
	$('#gridOrder').remove();
	$('#gridConfirmarTodo').remove();
	self.createButtonOrdenClave();
	self.buscarProgramacion();
};

ConfirmarProgramacionEvent.prototype.ordenPorHoraPrograma = function() {
	var self = confirmarProgramacionEvent;
	self.getSelector("grid").remapColumns([5,0,2,3,1,4,6,7,8,9,10,11,12,13,14,15,16,17,18], true, false);
	$('#gridOrder').remove();
	$('#gridConfirmarTodo').remove();
	self.createButtonOrdenHoraPrograma();
	self.buscarProgramacion();
};

/***********************************************************************************		
 ********************		Situar por programas - Alta			********************
 ***********************************************************************************/
ConfirmarProgramacionEvent.prototype.drawPopupSituarProgramasAlta = function(conCapitulos) {
	var data = {descripcionPrograma : "", senial: variablesSesionUsuarioEvent.dameSenialSeleccionada()};
	situarPopupEvent.reset();
	situarPopupEvent.nombreSituar = "Programa original";
	situarPopupEvent.doAfterDrawGrid = function() {
	    situarPopupEvent.getGrid().sortGrid('descripcion', true);
	};
	if (conCapitulos) {
		situarPopupEvent.create("buscarProgramasDesconfirmar.action", confirmarProgramacionEvent.programaAltaSelectedCC, data, "programas");	
	} else {
		situarPopupEvent.create("buscarProgramasDesconfirmar.action", confirmarProgramacionEvent.programaAltaSelectedSC, data, "programas");
	}
};

ConfirmarProgramacionEvent.prototype.programaAltaSelectedCC = function(programa) {
	situarPopupEvent.doAfterDrawGrid = function() {
		return;
	};
	armadoDeProgramacionEvent.codigoDelPrograma = programa.codigo;
    armadoDeProgramacionEvent.descripcionDelPrograma = programa.descripcion;
    armadoDeProgramacionEvent.idSenial = $("#senialDefaultUsuario").val();
	armadoDeProgramacionEvent.tituloQueDeseaProgramar = "CC";
	armadoDeProgramacionEvent.workingFromConfirmacionDeProgramacion = true;
	armadoDeProgramacionEvent.tipoOperacion = "A";
    Component.get("html/armadoDeProgramacion/popUpTituloProgramadoConCapitulo.html", armadoDeProgramacionEvent.drawPopUpTituloProgramadoConCapitulo);
    setTimeout(function() {
    	armadoDeProgramacionEvent.getInputTituloProgramadoConCapitulo("fechaExhibicion").val(confirmarProgramacionEvent.getSelector("fecha").val());
	    armadoDeProgramacionEvent.getInputTituloProgramadoConCapitulo("fechaExhibicion").datepicker('disable');
	}, 1000);
    $("#ArmadoDeProgramacionEventId_confirmacionFinalPopUp").bind('beforeunload', function() {
    	confirmarProgramacionEvent.buscarProgramacion();
    	$("#ArmadoDeProgramacionEventId_confirmacionFinalPopUp").bind('beforeunload', function() {
            return;
        });
    });
};

ConfirmarProgramacionEvent.prototype.programaAltaSelectedSC = function(programa) {
	situarPopupEvent.doAfterDrawGrid = function() {
		return;
	};
	armadoDeProgramacionEvent.codigoDelPrograma = programa.codigo;
    armadoDeProgramacionEvent.descripcionDelPrograma = programa.descripcion;
	armadoDeProgramacionEvent.idSenial = $("#senialDefaultUsuario").val();
	armadoDeProgramacionEvent.tituloQueDeseaProgramar = "SC";
	armadoDeProgramacionEvent.tipoOperacion = "A";
	armadoDeProgramacionEvent.workingFromConfirmacionDeProgramacion = true;
    Component.get("html/armadoDeProgramacion/popUpTituloProgramadoSinCapitulo.html", armadoDeProgramacionEvent.drawPopUpTituloProgramadoSinCapitulo);
    setTimeout(function() {
    	armadoDeProgramacionEvent.getInputTituloProgramadoSinCapitulo("fechaExhibicion").val(confirmarProgramacionEvent.getSelector("fecha").val());
    	armadoDeProgramacionEvent.getInputTituloProgramadoSinCapitulo("fechaExhibicion").datepicker('disable');
	}, 1000);
    $("#ArmadoDeProgramacionEventId_confirmacionFinalPopUp").bind('beforeunload', function() {
    	confirmarProgramacionEvent.buscarProgramacion();
    	$("#ArmadoDeProgramacionEventId_confirmacionFinalPopUp").bind('beforeunload', function() {
            return;
        });
    });
};

/***********************************************************************************		
 ********************				Confirmaciones				********************
 ***********************************************************************************/
ConfirmarProgramacionEvent.prototype.confirmarExhibicion = function(rowId) {
	var row = confirmarProgramacionEvent.getSelector("grid").jqGrid('getRowData', rowId);
	
	if (row.mas != "") {
		confirmarProgramacionEvent.tituloSeleccionado = row;
		Component.get("html/confirmarProgramacion/seleccionContratoProgramacion.html", confirmarProgramacionEvent.drawConfirmarExhibicionMultiplesContratos);
	} else {
		var procesarConfirmacionRequest = {
			codigo: row.codigoPrograma,
			tipoTitulo: row.clave.substring(0,2),
			numeroTitulo: row.clave.substring(2,8), 
			capitulo: row.capitulo,
			parte: row.parte,
			segmento: row.segmento,
			contrato: row.contrato,
			grupo: row.grupo,
			senial: row.senial,
			senialCon: row.senialCon,
			fecha: row.fecha,
			fraccion: row.fraccion,
			de: row.de,
			tipoRatingExcedente: (row.rating == "1" ? "RATING" : (row.excedente == "1" ? "EXCEDENTE" : null)),
			valorNuevoRatingExcedente: (row.rating == "1" ? row.nuevoValorRating : (row.excedente == "1" ? row.nuevoValorExcedente : null))
		};
		confirmarProgramacionEvent.service.procesarConfirmacion(procesarConfirmacionRequest);
	}
};

/***********************************************************************************		
 ********************				Confirmar todo				********************
 ***********************************************************************************/
ConfirmarProgramacionEvent.prototype.confirmarExhibicionGrillaProgramacion = function() {
	var yesterday = new Date();
	yesterday.setDate(yesterday.getDate() - 1);
	var self = confirmarProgramacionEvent;
	var listaProcesarConfirmacionRequest = [];
	var esRating = null;
	var mensajeErrorFormatoRatingExcedente = "";

	if (Validator.isEmpty(self.getSelector("fecha"))) {
		Validator.focus(self.getSelector("fecha"), "Debe ingresar una fecha válida");
	} else if (self.getSelector("fecha").datepicker("getDate") > yesterday) {
		Validator.focus(self.getSelector("fecha"), "La fecha de exhibición debe ser menor que la actual");
	} else {
//		var procesarConfirmacionRequest = {
//			senial: $("#senialDefaultUsuario").val(),
//			fecha: self.getSelector("fecha").val(),
//			clave: self.getSelector("clave").val(),
//			codigo: self.getSelector("programa").val(),
//			capitulo: self.getSelector("capitulo").val(),
//			parte: self.getSelector("parte").val(),
//			segmento: self.getSelector("segmento").val()
//		};
		
		var datosGrilla = self.getSelector("grid").jqGrid("getGridParam","data");
		for (var i = 0; i < datosGrilla.length; i++) {
			if(datosGrilla[i].rating == "1")
				esRating = true;
			if(datosGrilla[i].excedente == "1")
				esRating = false;
//			mensajeErrorFormatoRatingExcedente += self.validarFormatoRatingExcedenteYretornarMensajeError(esRating,(esRating ? datosGrilla[i].nuevoValorRating : datosGrilla[i].nuevoValorExcedente)) + "<br>";		
			
//			if (mensajeErrorFormatoRatingExcedente == ""){
				programaAconfirmar = {
						tipoTitulo: datosGrilla[i].clave.substring(0,2),
						numeroTitulo: datosGrilla[i].clave.substring(2,8),
						fecha: self.getSelector("fecha").val() === "" ? self.getSelector("fecha").val() : moment(self.getSelector("fecha").val(),"DD/MM/YYYY").format(),
						senial: $("#senialDefaultUsuario").val(),
						capitulo: datosGrilla[i].capitulo,
						codigo: datosGrilla[i].codigoPrograma,
						tipoRatingExcedente: (esRating ? "RATING" : "EXCEDENTE"),
						valorNuevoRatingExcedente: (esRating ? datosGrilla[i].nuevoValorRating : datosGrilla[i].nuevoValorExcedente)
				}
				
				listaProcesarConfirmacionRequest.push(programaAconfirmar);
//			}

		}
		
//		if (mensajeErrorFormatoRatingExcedente == ""){
			self.service.procesarConfirmacionGrillaProgramacion({listaProcesarConfirmacionRequest: listaProcesarConfirmacionRequest});
//		}else{
//			MESSAGE.alert(mensajeErrorFormatoRatingExcedente);
//		}
		
	}
};

/***********************************************************************************		
 ********************		Confirmar - Multiples contratos		********************
 ***********************************************************************************/
ConfirmarProgramacionEvent.prototype.drawConfirmarExhibicionMultiplesContratos = function(comp) {
	var self = confirmarProgramacionEvent;
	self.getSelector("divConfirmarProg").hide();
	
	comp = comp.replace(/{{id}}/g, self.div.id)
			   .replace(/{{senial}}/g, self.tituloSeleccionado.senial)
			   .replace(/{{fecha}}/g, self.tituloSeleccionado.fecha)
			   .replace(/{{programa}}/g, self.tituloSeleccionado.codigoPrograma + " " + self.tituloSeleccionado.descripcionPrograma)
			   .replace(/{{clave}}/g, self.tituloSeleccionado.clave + " " + self.tituloSeleccionado.titulo)
			   .replace(/{{capitulo}}/g, self.tituloSeleccionado.capitulo)
			   .replace(/{{hora}}/g, self.tituloSeleccionado.hora)
			   .replace(/{{parte}}/g, self.tituloSeleccionado.parte)
			   .replace(/{{segmento}}/g, self.tituloSeleccionado.segmento)
			   .replace(/{{orden}}/g, self.tituloSeleccionado.orden);
	
	if (self.getSelector("divSeleccionContrato").length) {
		self.getSelector("divSeleccionContrato").replaceWith(comp);
	} else {
		self.getDiv().append(comp);
	}
	
	Accordion.getInstance(self.getSelector("accordionFormContrato"));
	self.drawGridContratos();
};

ConfirmarProgramacionEvent.prototype.drawGridContratos = function() {
	var self = confirmarProgramacionEvent;
	
	self.getSelector("gridContratos").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Contrato', 'Grupo', 'Señal', ''],
		colModel:[ 
		    {name:'contrato',  index:'contrato',  align:'center', width:30, sortable:false},
			{name:'grupo',	   index:'hora', 	  align:'center', width:30, sortable:false},
			{name:'senial',	   index:'hora', 	  align:'center', width:30, sortable:false},
			{name:'confirmar', index:'confirmar', align:'center', width:10, sortable:false}
		],
		rowNum: 20,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + this.div.id + '_pagerGridContratos',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Contratos',
		gridComplete: function() {
        	var ids = $(this).jqGrid('getDataIDs');
        	for (var i = 0; i < ids.length; i++) {
        		var rowId = ids[i];
				var confirmar = "<div style='display:inline-table; cursor:pointer' onclick='confirmarProgramacionEvent.confirmarExhibicionMultiplesContratos("+rowId+")'><span class='ui-icon ui-icon-circle-check confirmar conTooltip'>Confirmar</span></div>";
				$(this).jqGrid('setRowData', rowId, {confirmar: confirmar});
        	}
	    }
	});
	
	var buscarContratosRequest = {
		codigo: self.tituloSeleccionado.codigoPrograma,
		tipoTitulo:  self.tituloSeleccionado.clave.substring(0,2),
		numeroTitulo:  self.tituloSeleccionado.clave.substring(2,8),
		capitulo: self.tituloSeleccionado.capitulo,
		parte: self.tituloSeleccionado.parte,
		segmento: self.tituloSeleccionado.segmento,
		contrato: self.tituloSeleccionado.contrato,
		grupo: self.tituloSeleccionado.grupo,
		senial: self.tituloSeleccionado.senial,
		senialCon: self.tituloSeleccionado.senialCon,
		fecha: self.tituloSeleccionado.fecha
	};
	self.service.listarContratos(buscarContratosRequest);
};

ConfirmarProgramacionEvent.prototype.fillGridContratos = function(data) {
	var self = confirmarProgramacionEvent;
	self.getSelector("gridContratos").clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};

ConfirmarProgramacionEvent.prototype.confirmarExhibicionMultiplesContratos = function(rowId) {
	var row = confirmarProgramacionEvent.getSelector("gridContratos").jqGrid('getRowData', rowId);
	var self = confirmarProgramacionEvent;
	
	var procesarConfirmacionRequest = {
		codigo: self.tituloSeleccionado.codigoPrograma,
		tipoTitulo: self.tituloSeleccionado.clave.substring(0,2),
		numeroTitulo: self.tituloSeleccionado.clave.substring(2,8), 
		capitulo: self.tituloSeleccionado.capitulo,
		parte: self.tituloSeleccionado.parte,
		segmento: self.tituloSeleccionado.segmento,
		contrato: row.contrato,
		grupo: row.grupo,
		senial: self.tituloSeleccionado.senial,
		senialCon: self.tituloSeleccionado.senialCon,
		fecha: self.tituloSeleccionado.fecha,
		fraccion: self.tituloSeleccionado.fraccion,
		de: self.tituloSeleccionado.de
	};
	
	confirmarProgramacionEvent.getSelector("divSeleccionContrato").hide();
	confirmarProgramacionEvent.getSelector("divConfirmarProg").show();
	confirmarProgramacionEvent.service.procesarConfirmacion(procesarConfirmacionRequest);
};

/***********************************************************************************		
 ********************			Eliminar exhibición				********************
 ***********************************************************************************/
ConfirmarProgramacionEvent.prototype.eliminar = function(rowId) {
	var row = confirmarProgramacionEvent.getSelector("grid").jqGrid('getRowData', rowId);
	
	var tituloProgramadoSeleccionado = {
		fecha: row.fecha,
	    clave: row.clave,
	    capitulo: row.capitulo,
	    parte: row.parte,
	    segmento: row.segmento,
	    fraccion: row.fraccion,
	    totalDeFracciones: row.de,
	    ordenSalida: row.orden,
	    senial: row.senial,
	    codigoDelPrograma: row.codigoPrograma
	};
	
	armadoDeProgramacionEvent.tituloProgramadoSeleccionado = tituloProgramadoSeleccionado;
	armadoDeProgramacionEvent.tipoOperacion = "B";
	Component.get("html/armadoDeProgramacion/popUpEliminarTituloProgramado.html", armadoDeProgramacionEvent.drawPopUpEliminarTituloProgramado);
};

/* Initialize */
var confirmarProgramacionEvent = new ConfirmarProgramacionEvent(new DivDefinition('ConfirmarProgramacionEventId'));