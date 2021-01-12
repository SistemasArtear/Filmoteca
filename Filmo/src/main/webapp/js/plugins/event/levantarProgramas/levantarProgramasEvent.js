function LevantarProgramasEvent(div) {
	this.div = div;
};
extend(LevantarProgramasEvent, Plugin);
LevantarProgramasEvent.prototype.create = function() {
	var self = levantarProgramasEvent;
	self.service = new LevantarProgramasService();
	Component.get("html/levantarProgramas/listadoProgramas.html", levantarProgramasEvent.draw);
};
LevantarProgramasEvent.prototype.draw = function(comp) {
	var self = levantarProgramasEvent;
	$("#viewContainer").empty().append(comp.replace(/{{id}}/g, self.div.id));
	self.getSelector("accordionFormInicial").accordion({
		collapsible: false,
		active: 0,
		autoHeight: false,
		icons: false
	});
	
	//Component.populateSelect($("#"+self.div.id+"_senial"), seniales, "codigo", "descripcion");
	//self.getSelector("senial").change(function() {
		//if (self.validForm()) {
			self.getSelector("filtro").show();
			self.senialSeleccionada = $("#senialDefaultUsuario").val();
			self.service.levantarListaDeProgramas(self.senialSeleccionada, function(response) {
				self.drawGridProgramas(response);
			});
	//	}
	//});
	self.getSelector("codPrograma").numeric();
	self.getSelector("codPrograma").keypress(function (event) {
        if (event.which == 13) {
            event.preventDefault();
            self.filtrarProgramas();
            return;
        }
      });
  self.getSelector("descPrograma").keypress(function (event) {
        if (event.which == 13) {
            event.preventDefault();
            self.filtrarProgramas();
            return;
        }
      });
	self.getSelector("filtrarProgramas").button().click(self.filtrarProgramas);
};
LevantarProgramasEvent.prototype.validForm = function() {
	var senial = this.getSelector("senial");
	if (Validator.isEmpty(senial)) {
		Validator.focus(senial);
		return false;
	}
	return true;
};
LevantarProgramasEvent.prototype.drawGridProgramas = function(data) {
	var self = levantarProgramasEvent;
	self.getSelector("gridInicial").jqGrid({
		height: 'auto',
		datatype: 'local',
		autowidth: true,
		colNames:['Programa','Descripción'],
		colModel:[ 
			{name:'codigo',index:'codigo', width:150, sortable:false}, 
			{name:'descripcion',index:'descripcion', width:300, sortable:false} 
		],
		rowNum: 20,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#LevantarProgramasEventId_pagerGridInicial',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Programas',
		ondblClickRow: function(id){ 
			var data = $(this).jqGrid('getRowData', id);
			self.codProgramaSeleccionado = data.codigo;
			data.senial = self.senialSeleccionada;
			self.service.obtenerMayorFechaProgramada(
					data, function(mayorFecha) {
						data.fechaDesde = $.datepicker.formatDate('dd/mm/yy', new Date());
						data.fechaHasta = mayorFecha;
						self.dataRequestGrillaProgramada = data;
						Component.get("html/levantarProgramas/listadoTitulos.html", function(comp) {
							self.obtenerGrillaProgramada();
							self.drawListadoTitulos(comp, data);
						});
					}
			);
		}
	});
	self.getSelector("gridInicial").clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};
LevantarProgramasEvent.prototype.filtrarProgramas = function() {
    var self = levantarProgramasEvent;
    var data = {
        codigo : self.getSelector("codPrograma").val(),
        descripcion : self.getSelector("descPrograma").val()
    };
    filtros = {groupOp:"OR",rules:[]};
    if (data && data.codigo) {
        filtros.rules.push({field:"codigo",op:"cn",data:data.codigo});
    };
    if (data && data.descripcion) {
        filtros.rules.push({field:"descripcion",op:"cn",data:data.descripcion.toUpperCase()});
    };
    self.getSelector("gridInicial")[0].p.search = true;
    $.extend(self.getSelector("gridInicial")[0].p.postData,{filters:JSON.stringify(filtros)});
    self.getSelector("gridInicial").trigger("reloadGrid");
};
LevantarProgramasEvent.prototype.drawGrillaTitulos = function(data) {
	var self = levantarProgramasEvent;
	self.getSelector("gridTitulos").jqGrid({
		height: 'auto',
        autowidth: true,
        datatype: 'local',
        colNames:['Dia','Fecha','Titulo Castellano','Clave','Cap','P','S','Fr','De','OR'],
        colModel:[ 
            {name:'dia',index:'dia', width:10, sortable:false},
            {name:'fecha',index:'fecha', width:20, sortable:false},
            {name:'tituloCast',index:'tituloCast', width:50, sortable:false},
            {name:'clave',index:'clave', width:10, sortable:false},
            {name:'capitulo',index:'cap', width:5, sortable:false},
            {name:'parte',index:'p', width:5, sortable:false},
            {name:'segmento',index:'s', width:5, sortable:false},
            {name:'fraccion',index:'fr', width:5, sortable:false},
            {name:'totalDeFracciones',index:'de', width:5, sortable:false},
            {name:'ordenSalida',index:'or', width:5, sortable:false}
        ],
        rowNum: 20,
        rowList:[10,20,30],
        scrollOffset: 0,
        pager: '#LevantarProgramasEventId_pagerGridTitulos',
        viewrecords: true, 
        loadonce: true,
        editurl: 'clientArray', 
        caption: 'Administración de Grilla',
        multiselect: true
    });
	self.getSelector("gridTitulos").clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};
LevantarProgramasEvent.prototype.drawListadoTitulos = function(comp, data) {
	var self = levantarProgramasEvent;
	self.getSelector("divFormInicial").hide();
	comp = comp.replace(/{{id}}/g, self.div.id)
			   .replace(/{{programa}}/g, data.codigo)
			   .replace(/{{senial}}/g, data.senial)
			   .replace(/{{descripcion}}/g, data.descripcion);
	if (self.getSelector("divListadoTitulos").length) {
		self.getSelector("divListadoTitulos").replaceWith(comp);
	}else{
		self.getDiv().append(comp);
	}
	Datepicker.getInstance(self.getSelector("fechaDesde"), data.fechaDesde);
	Datepicker.getInstance(self.getSelector("fechaHasta"), $.datepicker.parseDate('yy-mm-dd', data.fechaHasta));
	self.getSelector("filtrarFormTitulos").button().click(function() {
		data.fechaDesde = self.getSelector("fechaDesde").val();
		var hoy = new Date();
		hoy.setHours(0,0,0,0);
		if (self.getSelector("fechaDesde").datepicker("getDate") < hoy) {
			Validator.focus(self.getSelector("fechaDesde"), "La fecha no puede ser menor a la de hoy.");
			return;
		}
		data.fechaHasta = self.getSelector("fechaHasta").val();
		self.service.obtenerGrillaProgramada(data, function(titulos) {
			self.getSelector("gridTitulos").clearGridData().setGridParam({data: titulos}).trigger('reloadGrid');
		});
	});
	self.getSelector("volverAFormInicial").button().click(function() {
		self.getSelector("divListadoTitulos").hide();
		self.getSelector("divFormInicial").show();
	});
	self.getSelector("validarLevantarButton").button().click(self.validarLevantarButton);
};
LevantarProgramasEvent.prototype.validarLevantarButton = function() {
	var self = levantarProgramasEvent;
	self.titulosSeleccionados = [];
	var idsSeleccionados = self.getSelector("gridTitulos").jqGrid('getGridParam','selarrrow');
	if (idsSeleccionados.length <= 0 ) {
		MESSAGE.alert("Debe seleccionar por lo menos un titulo.");
		return;
	}
	for ( var i in idsSeleccionados) {
		var rowData = self.getSelector("gridTitulos").jqGrid('getRowData', idsSeleccionados[i]);
		self.titulosSeleccionados.push(rowData);
	}
	var data = { 
		titulos : self.titulosSeleccionados,
		senial : self.senialSeleccionada,
		codPrograma : self.codProgramaSeleccionado
	};
	self.service.validacionLevantarExhibiciones(data, function(response) {
		var exhibicionesConfirmadas = [];
		var exhibicionesSinConfirmar = [];
		for ( var i in response) {
			if (response[i].ok === "S") {
				exhibicionesConfirmadas.push(data.titulos[i]);
			}else{
				exhibicionesSinConfirmar.push(response[i].tituloProgramado);
			}
		}
		data.titulos = exhibicionesSinConfirmar.slice();
		if (exhibicionesConfirmadas.length > 0) {
			self.popupConfirmados(exhibicionesConfirmadas, data);
		}else if (data.titulos.length > 0) {
			self.service.levantarExhibiciones(data, function(response) {
				self.obtenerGrillaProgramada();
			});
		}
	});
};
LevantarProgramasEvent.prototype.obtenerGrillaProgramada = function() {
	var self = levantarProgramasEvent;
	self.service.obtenerGrillaProgramada(
			self.dataRequestGrillaProgramada, function(titulos) {
				self.drawGrillaTitulos(titulos);
			});
};
LevantarProgramasEvent.prototype.popupConfirmados = function(confirmados, aLevantar) {
	var self = levantarProgramasEvent;
	Component.get("html/levantarProgramas/popupConfirmados.html", function(comp) {
		$("#dialog").empty().append(comp.replace(/{{id}}/g, self.div.id));
		self.getSelector("popupConfirmados").dialog({
			height: 'auto',
			width: 1000,
			position: 'center',
			show: 'blind',
			hide: 'blind',
			modal: true,
			resizable: false,
			autoOpen: true,
			title: 'Advertencia',
			buttons: {
				"Confirmar" : function() {
					if (aLevantar.length) {
						self.service.levantarExhibiciones(aLevantar, function(response) {
							self.obtenerGrillaProgramada();
						});
					}else{
						MESSAGE.alert("No hay exhibiciones sin confirmar para levantar.");
					}
					$( this ).dialog( "close" );
				},
				"Cancelar" : function() {
					$( this ).dialog( "close" );
				}
			},
			close: function(event, ui) { 
				$( this ).remove();
	    	}
		});
		self.drawGrillaConfirmados(confirmados);
	});
};
LevantarProgramasEvent.prototype.drawGrillaConfirmados = function(data) {
	var self = levantarProgramasEvent;
	self.getSelector("gridConfirmados").jqGrid({
		height: 'auto',
        autowidth: true,
        datatype: 'local',
        colNames:['Dia','Fecha','Titulo Castellano','Clave','Cap','P','S','Fr','De','OR'],
        colModel:[ 
            {name:'dia',index:'dia', width:5, sortable:false},
            {name:'fecha',index:'fecha', width:10, sortable:false, formatter: 'date', formatoptions: { srcformat: 'Y/m/d', newformat: 'd/m/Y'}},
            {name:'tituloCast',index:'tituloCast', width:30, sortable:false},
            {name:'clave',index:'clave', width:5, sortable:false},
            {name:'capitulo',index:'cap', width:5, sortable:false},
            {name:'parte',index:'p', width:5, sortable:false},
            {name:'segmento',index:'s', width:5, sortable:false},
            {name:'fraccion',index:'fr', width:5, sortable:false},
            {name:'totalDeFracciones',index:'de', width:5, sortable:false},
            {name:'ordenSalida',index:'or', width:5, sortable:false}
        ],
        rowNum: 20,
        rowList:[10,20,30],
        scrollOffset: 0,
        pager: '#LevantarProgramasEventId_pagerGridConfirmados',
        viewrecords: true, 
        loadonce: true,
        editurl: 'clientArray', 
        caption: 'Exhibiciones Confirmadas'
    });
	self.getSelector("gridConfirmados").clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};
var levantarProgramasEvent = new LevantarProgramasEvent(new DivDefinition('LevantarProgramasEventId'));