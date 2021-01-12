/**
 * FP350 - Desconfirmar programación
 * @author cetorres
 */

function DesconfirmarProgramacionEvent(div) {
	this.div = div;
};

extend(DesconfirmarProgramacionEvent, Plugin);

/***********************************************************************************		
 ********************	 	Desconfirmar programación			********************
 ***********************************************************************************/
DesconfirmarProgramacionEvent.prototype.create = function() {
	var self = desconfirmarProgramacionEvent;
	self.service = new DesconfirmarProgramacionService();
	Component.get("html/desconfirmarProgramacion/desconfirmarProgramacion.html", desconfirmarProgramacionEvent.draw);
};

DesconfirmarProgramacionEvent.prototype.draw = function(comp) {
	var self = desconfirmarProgramacionEvent;
	$("#viewContainer").empty().append(comp.replace(/{{id}}/g, self.div.id));

	Accordion.getInstance(self.getSelector("accordionDesconfirmarProg"));
	var yesterday = new Date();
	yesterday.setDate(yesterday.getDate() - 1);
	Datepicker.getInstance(self.getSelector("fecha"), new Date());
	self.getSelector("fecha").datepicker('option', {maxDate: "-1d"});
	self.getSelector("programa").numeric();
	
	var filtros = ['Programa', 'Clave'];
    for (var i in filtros) {
      $("#DesconfirmarProgramacionEventId_filtro"+filtros[i]+"DivContainer").hide();
    }
    
	self.getSelector("popupPrograma").click(self.drawPopupSituarProgramas);
	self.getSelector("limpiarPrograma").click(self.limpiarFiltroProgramas);
	self.getSelector("popupTitulo").click(self.drawPopupSituarTitulos);
	self.getSelector("limpiarTitulo").click(self.limpiarFiltroTitulos);
	self.getSelector("limpiarFiltros").button().click(self.limpiarPantalla);
	self.getSelector("listadoLibreria").button().click(self.listadoLibreria);
	self.getSelector("buscarProgramacion").button().click(self.buscarProgramacion);
	self.drawGridProgramas();
};

DesconfirmarProgramacionEvent.prototype.limpiarPantalla = function() {
	var self = desconfirmarProgramacionEvent;
	var yesterday = new Date();
	yesterday.setDate(yesterday.getDate() - 1);
	self.getSelector("fecha").datepicker('setDate', yesterday);
	self.limpiarFiltroProgramas();
	self.limpiarFiltroTitulos();
	self.fillGrid(new Array());
};

DesconfirmarProgramacionEvent.prototype.listadoLibreria = function(procesarDesconfirmacionRequest) {
	var self = desconfirmarProgramacionEvent;
	self.service.listadoLibreria(procesarDesconfirmacionRequest);
};

/***********************************************************************************		
 ********************		 	Situar por programas			********************
 ***********************************************************************************/
DesconfirmarProgramacionEvent.prototype.drawPopupSituarProgramas = function() {
	var data = {descripcionPrograma : "", senial: variablesSesionUsuarioEvent.dameSenialSeleccionada()};
	situarPopupEvent.reset();
	situarPopupEvent.create("buscarProgramasDesconfirmar.action", desconfirmarProgramacionEvent.programaSelected, data, "programas");
	situarPopupEvent.nombreSituar = "Programa original por descripción";
	situarPopupEvent.doAfterDrawGrid = function() {
		situarPopupEvent.getGrid().sortGrid('descripcion', true);
	};
};

DesconfirmarProgramacionEvent.prototype.programaSelected = function(programa) {
	var self = desconfirmarProgramacionEvent;
	self.getSelector("programa").val(programa.codigo);
	self.getSelector("descripcionPrograma").val(programa.descripcion);
};

DesconfirmarProgramacionEvent.prototype.limpiarFiltroProgramas = function() {
	var self = desconfirmarProgramacionEvent;
	self.getSelector("programa").val(null);
	self.getSelector("descripcionPrograma").val(null);
};

/***********************************************************************************		
 ********************		 	Situar por títulos				********************
 ***********************************************************************************/
DesconfirmarProgramacionEvent.prototype.drawPopupSituarTitulos = function(comp) {
	var data = {descripcionTitulo : ""};
	situarPopupEvent.reset();
	situarPopupEvent.create("buscarTitulosDesconfirmar.action", desconfirmarProgramacionEvent.tituloSelected, data, "titulos");
	situarPopupEvent.nombreSituar = "Títulos por descripción";
	situarPopupEvent.doAfterDrawGrid = function() {
		situarPopupEvent.getGrid().sortGrid('titulo', true);
	};
};

DesconfirmarProgramacionEvent.prototype.tituloSelected = function(titulo) {
	var self = desconfirmarProgramacionEvent;
	self.getSelector("clave").val(titulo.clave);
	self.getSelector("descripcionTitulo").val(titulo.titulo);
};

DesconfirmarProgramacionEvent.prototype.limpiarFiltroTitulos = function() {
	var self = desconfirmarProgramacionEvent;
	self.getSelector("clave").val(null);
	self.getSelector("descripcionTitulo").val(null);
};

/***********************************************************************************		
 ********************	 		Buscar programación				********************
 ***********************************************************************************/
DesconfirmarProgramacionEvent.prototype.buscarProgramacion = function() {
	var yesterday = new Date();
	yesterday.setDate(yesterday.getDate() - 1);
	var self = desconfirmarProgramacionEvent;

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
			codigoTitulo: self.getSelector("clave").val()
		};
		self.service.listarProrgamacion(listarProgramacionRequest);
	}
};

/***********************************************************************************		
 ********************	 		Grilla programación				********************
 ***********************************************************************************/
DesconfirmarProgramacionEvent.prototype.drawGridProgramas = function() {
	var self = desconfirmarProgramacionEvent;
	self.getSelector("grid").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Hora','Programa','Descripción','Título','Contrato','Clave', 'Capítulo', 'P', 'S', 'F', 'D', 'OR', '', '', '', '', ''],
		colModel:[ 
			{name:'hora',				index:'hora', 				width:30, align:'center', sortable:false, sorttype:'text', 
				formatter:function(value, options, rData) { 
					var hora = value.substring(0,2);
					var minutos = value.substring(2,4);
					/* Horarios en formato HH:MM */
					return hora + ":" + minutos;
				}
			}, 
			{name:'codigoPrograma',		index:'codigoPrograma', 	width:40, align:'center', sortable:false, sorttype:'number'}, 
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
			{name:'desconfirmar',		index:'desconfirmar', 		width:20, align:'center', sortable:false},
			{name:'fecha',				index:'fecha',				hidden:true},
			{name:'senial',				index:'fecha',				hidden:true},
			{name:'senialCon',			index:'senialCon',			hidden:true},
			{name:'grupo',				index:'grupo',				hidden:true}
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
        	var fecha = desconfirmarProgramacionEvent.getSelector("fecha").val();
        	for (var i = 0; i < ids.length; i++) {
        		var rowId = ids[i];
				var desconfirmar = "<div style='display:inline-table; cursor:pointer' onclick='desconfirmarProgramacionEvent.generarRequest("+rowId+")'><span class='ui-icon ui-icon-circle-close desconfirmar conTooltip'>Desconfirmar</span></div>";
				$(this).jqGrid('setRowData', rowId, { desconfirmar: desconfirmar, fecha: fecha });
        	}
	    }
	});
	
	desconfirmarProgramacionEvent.createButtonOrdenClave();
};

DesconfirmarProgramacionEvent.prototype.fillGrid = function(data) {
	var self = desconfirmarProgramacionEvent;
	self.getSelector("grid").clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};

/***********************************************************************************		
 ********************	 		Ordenamiento grilla				********************
 ***********************************************************************************/
DesconfirmarProgramacionEvent.prototype.createButtonOrdenClave = function() {
	desconfirmarProgramacionEvent.getSelector("grid").jqGrid('navGrid', '#DesconfirmarProgramacionEventId_pagerGrid', {edit:false, add:false, del:false, search:false, refresh:false}).navButtonAdd('#DesconfirmarProgramacionEventId_pagerGrid', {
		caption: "Ordenar por clave",
		buttonicon: "ui-icon-triangle-2-n-s",
		id:"gridOrder",
		onClickButton: function() {
			desconfirmarProgramacionEvent.ordenPorHoraPrograma();
		}
	});
	desconfirmarProgramacionEvent.tipoOrden = "H";
};
	
DesconfirmarProgramacionEvent.prototype.createButtonOrdenHoraPrograma = function() {
	desconfirmarProgramacionEvent.getSelector("grid").jqGrid('navGrid', '#DesconfirmarProgramacionEventId_pagerGrid', {edit:false, add:false, del:false, search:false, refresh:false}).navButtonAdd('#DesconfirmarProgramacionEventId_pagerGrid', {
		caption: "Ordenar por hora, programa",
		buttonicon: "ui-icon-triangle-2-n-s",
		id:"gridOrder",
		onClickButton: function() {
			desconfirmarProgramacionEvent.ordenPorClave();
		}
	});
	desconfirmarProgramacionEvent.tipoOrden = "C";
};

DesconfirmarProgramacionEvent.prototype.ordenPorClave = function() {
	var self = desconfirmarProgramacionEvent;
	self.getSelector("grid").clearGridData();
	self.getSelector("grid").remapColumns([1,2,3,4,5,0,6,7,8,9,10,11,12,13,14,15,16], true, false);
	$('#gridOrder').remove();
	self.createButtonOrdenClave();
	self.buscarProgramacion();
};

DesconfirmarProgramacionEvent.prototype.ordenPorHoraPrograma = function() {
	var self = desconfirmarProgramacionEvent;
	self.getSelector("grid").clearGridData();
	self.getSelector("grid").remapColumns([5,0,1,2,3,4,6,7,8,9,10,11,12,13,14,15,16], true, false);
	$('#gridOrder').remove();
	self.createButtonOrdenHoraPrograma();
	self.buscarProgramacion();
};

/***********************************************************************************		
 ********************			Desconfirmaciones				********************
 ***********************************************************************************/
DesconfirmarProgramacionEvent.prototype.generarRequest = function(rowId) {
	var row = desconfirmarProgramacionEvent.getSelector("grid").jqGrid('getRowData', rowId);
	
	var procesarDesconfirmacionRequest = {
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
		fecha: row.fecha
	};

	desconfirmarProgramacionEvent.abrirPopupDesconfirmar(procesarDesconfirmacionRequest);
};

DesconfirmarProgramacionEvent.prototype.abrirPopupDesconfirmar = function(procesarDesconfirmacionRequest) {
	var popup = popupConfirmacionEvent;
	
	popup.cancelar = function() {
		popup.close();
		popup.remove();
		procesarDesconfirmacionRequest.respuestaConfirmacion = 'N';
		desconfirmarProgramacionEvent.desconfirmarExhibicion(procesarDesconfirmacionRequest, false);
	};
	popup.confirmar = function() {
		popup.close();
		popup.remove();
		procesarDesconfirmacionRequest.respuestaConfirmacion = 'S';
		desconfirmarProgramacionEvent.desconfirmarExhibicion(procesarDesconfirmacionRequest, false);
	};
	popup.afterDraw = function() {
		desconfirmarProgramacionEvent.getSelector("botonSiConfirmacionPopup").blur();
		desconfirmarProgramacionEvent.getSelector("botonNoConfirmacionPopup").focus();
		$("#botonera").append("<button id=\"" + desconfirmarProgramacionEvent.div.id + "_cancelar\">Cancelar</button>");
		desconfirmarProgramacionEvent.getSelector("cancelar").button().click(function() {
			popup.close();
			popup.remove(); 
		});	
	};
	var mensajeConfirmacion = "¿Desea eliminar la exhibición de la grilla?";
	popup.create(desconfirmarProgramacionEvent.div.id, mensajeConfirmacion);	
};

DesconfirmarProgramacionEvent.prototype.abrirAdvertenciaPopupDesconfirmar = function(procesarDesconfirmacionRequest, mensaje) {
	var self = desconfirmarProgramacionEvent;
	
	var popup = popupConfirmacionEvent;
	popup.cancelar = function() {
		popup.close();
		popup.remove();
	};
	popup.confirmar = function() {
		popup.close();
		popup.remove();
		procesarDesconfirmacionRequest.respuestaWarning = 'S';
		self.desconfirmarExhibicion(procesarDesconfirmacionRequest, true);
	};
	popup.afterDraw = function() {
		desconfirmarProgramacionEvent.getSelector("cancelar").remove();
		desconfirmarProgramacionEvent.getSelector("botonSiConfirmacionPopup").blur();
		desconfirmarProgramacionEvent.getSelector("botonNoConfirmacionPopup").focus();
	};
	
	popup.create(self.div.id, mensaje);
};

DesconfirmarProgramacionEvent.prototype.desconfirmarExhibicion = function(procesarDesconfirmacionRequest, ejecucionFinal) {
	desconfirmarProgramacionEvent.service.procesarDesconfirmacion(procesarDesconfirmacionRequest, ejecucionFinal);
};

/* Initialize */
var desconfirmarProgramacionEvent = new DesconfirmarProgramacionEvent(new DivDefinition('DesconfirmarProgramacionEventId'));