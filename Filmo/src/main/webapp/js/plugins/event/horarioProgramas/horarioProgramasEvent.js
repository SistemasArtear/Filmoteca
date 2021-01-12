function HorarioProgramasEvent(div) {
	this.div = div;
};
extend(HorarioProgramasEvent, Plugin);
HorarioProgramasEvent.prototype.create = function() {
	var self = horarioProgramasEvent;
	self.isDrawedGridPH = false;
	self.service = new HorarioProgramasService();
	Component.get("html/horarioProgramas/listadoPH.html", horarioProgramasEvent.draw);
};
HorarioProgramasEvent.prototype.draw = function(comp) {
	var self = horarioProgramasEvent;
	$("#viewContainer").empty().append(comp.replace(/{{id}}/g, self.div.id));
	//Component.populateSelect(self.getSelector("senial"), seniales, "codigo", "descripcion");
	//self.getSelector("senial").change(function() {
		//if (!Validator.isEmpty(self.getSelector("senial"))) {
			self.getSelector("filtro").show();
			self.getSelector("botonAgregar").show();
			self.senialSeleccionada = $("#senialDefaultUsuario").val();
			self.service.getProgramasPhPorCodigo({senial: $("#senialDefaultUsuario").val()});
			self.getInputBusquedaPHPorCodigo().focus();
		//}else{
			//self.getSelector("filtro").hide();
			//self.getSelector("botonAgregar").hide();
	//	}
	//});
	Accordion.getInstance(self.getAccordionListadoPH());
	self.getButtonBuscarProgramasPHPorCodigo().button().click(self.buscarProgramasPHPorCodigo);
	self.getButtonAgregarProgramaPH().button().click(self.agregarProgramaPH);
	self.getButtonPopUpBuscarPHPorDescripcion().button().click(self.drawPopupSituarProgramasPH);
	self.getInputBusquedaPHPorCodigo().numeric();
	self.getInputBusquedaPHPorCodigo().keypress(function (event) {
		  if (event.which == 13) {
			  event.preventDefault();
			  self.buscarProgramasPHPorCodigo();
			  return;
		  }
		});
};
HorarioProgramasEvent.prototype.drawPopupSituarProgramasPH = function(comp) {
	var data = {descPrograma : "", senial : $("#senialDefaultUsuario").val()};//horarioProgramasEvent.senialSeleccionada
	situarPopupEvent.reset();
	situarPopupEvent.create("buscarProgramasConHorariosPorDescripcion.action", horarioProgramasEvent.programaPHSelected, data, "programasConHorarios");
};
HorarioProgramasEvent.prototype.programaPHSelected = function(data){
	horarioProgramasEvent.service.getProgramasPhPorCodigo({senial: $("#senialDefaultUsuario").val(), codigo : data.codigo});//horarioProgramasEvent.senialSeleccionada
};
HorarioProgramasEvent.prototype.drawGridProgramasPH = function(data){
	var self = horarioProgramasEvent;
	if (!self.isDrawedGridPH) {
		self.drawGrigPH(self);
		self.isDrawedGridPH = true;
	}
	self.getGridProgramasPH().clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};
HorarioProgramasEvent.prototype.drawGrigPH = function(self) {
	self.getGridProgramasPH().jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Clave','Titulo','','',''],
		colModel:[ 
			{name:'codigo',index:'codigo', width:150, sortable:true, sorttype:'int'}, 
			{name:'descripcion',index:'descripcion', width:300, sortable:true}, 
			{name:'visualizar',index:'visualizar', width:30, aling:'center', sortable:false},
			{name:'modificar',index:'modificar', width:30, aling:'center', sortable:false},
			{name:'eliminar',index:'eliminar', width:30, aling:'center', sortable:false}
		],
		rowNum: 20,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#HorarioProgramasEventId_pagerGridProgramasPH',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Programas',
		gridComplete: function() {
        	var ids = $(this).jqGrid('getDataIDs');
        	for (var i = 0; i < ids.length; i++) {
        		var rowId = ids[i];
        		var visualizar = "<center><div onclick='horarioProgramasEvent.visualizarProgramasPH("+rowId+", \"visualizar\")' style='cursor:pointer'><span class='ui-icon ui-icon-search visualizar conTooltip'>Visualizar</span></div></center>";
        		$(this).jqGrid('setRowData', rowId, { visualizar: visualizar });
        		var modificar = "<center><div onclick='horarioProgramasEvent.visualizarProgramasPH("+rowId+", \"modificar\")' style='cursor:pointer'><span class='ui-icon ui-icon-pencil modificar conTooltip'>Modificar</span></div></center>";
        		$(this).jqGrid('setRowData', rowId, { modificar: modificar });
        		var eliminar = "<center><div onclick='horarioProgramasEvent.visualizarProgramasPH("+rowId+", \"eliminar\")' style='cursor:pointer'><span class='ui-icon ui-icon-trash eliminar conTooltip'>Eliminar</span></div></center>";
        		$(this).jqGrid('setRowData', rowId, { eliminar: eliminar });
        	}
	    }
	});
};
HorarioProgramasEvent.prototype.getRowData = function(grid, rowId) {
    var rowData = grid.jqGrid('getRowData', rowId);
    return rowData;
};
HorarioProgramasEvent.prototype.visualizarProgramasPH = function(rowId, event) {
	this.event = event;
	this.programaSeleccionado = this.getRowData(event==="alta"?this.getGridMaestroProgramas():this.getGridProgramasPH(), rowId);
	Component.get("html/horarioProgramas/horarios.html", this.drawHorarios);
};
HorarioProgramasEvent.prototype.drawHorarios = function(comp) {
	var self = horarioProgramasEvent;
	if (self.event === "alta") {
		self.renderHorarios(comp);
		self.altaHorariosDraw();
	}else{
		var data = {
			codPrograma : self.programaSeleccionado.codigo,
			senial: $("#senialDefaultUsuario").val()
		};
		self.service.getHorariosPrograma(data, function(response) {
			self.renderHorarios(comp);
			self.horariosProgramasResponse(response);
		});
	}
};

HorarioProgramasEvent.prototype.renderHorarios = function(comp) {
	var self = horarioProgramasEvent;
	comp = comp.replace(/{{id}}/g, self.div.id).replace(/{{codigo}}/g, 
			self.programaSeleccionado.codigo)
			.replace(/{{senial}}/g, $("#senialDefaultUsuario").val())
			.replace(/{{descripcion}}/g, self.programaSeleccionado.descripcion);
	if (self.getDivHorarios().length) {
		self.getDivHorarios().replaceWith(comp);
	}else{
		self.getDiv().append(comp);
	}
	Accordion.getInstance(self.getAccordionHorarios());
	self.getButtonVolverHorario().button().click(self.volverHorarioPH);
	self.getFormHorarios().submit(function(){return false;});
	
};
HorarioProgramasEvent.prototype.altaHorariosDraw = function(data) {
	this.getDivListadoMP().hide();
	$(".botonModificar, .botonEliminar").hide();
	this.getButtonGuardarHorario().show().button().click(this.altaHorarioPH);
	this.getInputsHorarios().mask("?99:99",{ placeholder:"0"});
	this.getInputsHorarios().focusout(function(e) {
	var valor = e.target.value;
	var horas = valor.substring(0,2);
	var horasNum = parseInt(horas);
	var valorFinal = valor;
	if (horasNum > 24){
		valorFinal = '0' + valor.substring(0,1) + ':' + valor.substring(1,2) + valor.substring(3,4);	
	}
	e.target.value = valorFinal;

});
};
HorarioProgramasEvent.prototype.horariosProgramasResponse = function(data) {
	this.getDivListadoPH().hide();
	$(".botonGuardar, .botonModificar, .botonEliminar").hide();
	if (this.event === "modificar") {
		this.getButtonModificarHorario().show().button().click(this.modificarHorarioPH);
	}else if(this.event === "eliminar"){
		this.getButtonEliminarHorario().show().button().click(this.eliminarHorarioPH);
	}
	for ( i in data) {
		var horario = data[i];
		if (horario.desde.toString().length < 6) {
			if (horario.desde === 0) {
				horario.desde = "00:00";
			}else{
				horario.desde = "0" + horario.desde;
			}
		}
		if (horario.hasta.toString().length < 6) {
			if (horario.hasta === 0) {
				horario.hasta = "00:00";
			}else{
				horario.hasta = "0" + horario.hasta;
			}
		}
		if (horario.dia === 1) {
			$("#"+this.div.id+"_lunesDesde").val(horario.desde);
			$("#"+this.div.id+"_lunesHasta").val(horario.hasta);
		}else if (horario.dia === 2) {
			$("#"+this.div.id+"_martesDesde").val(horario.desde);
			$("#"+this.div.id+"_martesHasta").val(horario.hasta);
		}else if (horario.dia === 3) {
			$("#"+this.div.id+"_miercolesDesde").val(horario.desde);
			$("#"+this.div.id+"_miercolesHasta").val(horario.hasta);
		}else if (horario.dia === 4) {
			$("#"+this.div.id+"_juevesDesde").val(horario.desde);
			$("#"+this.div.id+"_juevesHasta").val(horario.hasta);
		}else if (horario.dia === 5) {
			$("#"+this.div.id+"_viernesDesde").val(horario.desde);
			$("#"+this.div.id+"_viernesHasta").val(horario.hasta);
		}else if (horario.dia === 6) {
			$("#"+this.div.id+"_sabadoDesde").val(horario.desde);
			$("#"+this.div.id+"_sabadoHasta").val(horario.hasta);
		}else if (horario.dia === 7) {
			$("#"+this.div.id+"_domingoDesde").val(horario.desde);
			$("#"+this.div.id+"_domingoHasta").val(horario.hasta);
		}
	}
	
	this.getInputsHorarios().mask("?99:99", {placeholder:"0"} );
	this.getInputsHorarios().focusout(function(e){
		var valor = e.target.value;
		var horas = valor.substring(0,2);
		var horasNum = parseInt(horas);
		var valorFinal = valor;
		if (horasNum > 24){
		valorFinal = '0' + valor.substring(0,1) + ':' + valor.substring(1,2) + valor.substring(3,4);
		}
		e.target.value = valorFinal;
		} );
};
HorarioProgramasEvent.prototype.volverHorarioPH = function() {
	horarioProgramasEvent.getDivHorarios().hide();
	horarioProgramasEvent.getDivListadoMP().hide();
	horarioProgramasEvent.getDivListadoPH().show();
};
HorarioProgramasEvent.prototype.modificarHorarioPH = function() {
	var self = horarioProgramasEvent;
	var data = self.getFormHorarios().serialize();
	
	var desde = $.map(horarioProgramasEvent.getFormHorarios().serializeArray(), function( n ) {
	    return (n.name.indexOf("desde") != -1) ? n : null;
	});
	var hasta = $.map(horarioProgramasEvent.getFormHorarios().serializeArray(), function( n ) {
	    return (n.name.indexOf("hasta") != -1) ? n : null;
	});
	for (var i = 0; i < 7; i++) {
	    var horaDesde = desde[i].value.split(":")[0];
	    var minDesde = desde[i].value.split(":")[1];

	    var horaHasta = hasta[i].value.split(":")[0];
	    var minHasta = hasta[i].value.split(":")[1];
	    
	    if (horaDesde > horaHasta) {
	        MESSAGE.alert("Los horarios desde deben ser menores a los horarios hasta");
	        return;
	    } else if ((horaDesde == horaHasta) && (minDesde > minHasta)) {
	        MESSAGE.alert("Los horarios desde deben ser menores a los horarios hasta");
	        return;    
	    } else {
	        //alert("valid");
	    }
	}
	
	self.service.validarFechaParaModificarHorarios(data, function(response) {
		if (response) {
			self.service.modificarPrograma(data);
		}else{
			Component.get("html/horarioProgramas/popupNuevaFecha.html", function(comp) {
				$("#dialog").empty().append(comp.replace(/{{id}}/g, self.div.id));
				Datepicker.getInstance(self.getSelector("nuevaFecha"), new Date());
				self.getSelector("popupNuevaFecha").dialog({
					height: 'auto',
					width: 600,
					position: 'center',
					show: 'blind',
					hide: 'blind',
					modal: true,
					resizable: false,
					autoOpen: true,
					title: 'Advertencia',
					buttons: {
						"Confirmar" : function() {
							var hoy = new Date();
							hoy.setHours(0,0,0,0);
							if (Validator.isEmpty(self.getSelector("nuevaFecha")) || self.getSelector("nuevaFecha").datepicker("getDate") < hoy) {
								Validator.focus(self.getSelector("nuevaFecha"), "La fecha no puede ser menor a la de hoy.");
								return;
							}
							var request = {
								serialize : data,
								nuevaFecha : self.getSelector("nuevaFecha").val()
							};
							self.service.modificarNuevaFecha(request);
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
			});
		}
	});
};
HorarioProgramasEvent.prototype.formatHorario = function(value) {
	if (value) {
		return parseInt(value.replace(":","")+"00",10);
	}
	return value;
};
HorarioProgramasEvent.prototype.eliminarHorarioPH = function() {
	var self = horarioProgramasEvent;
	var data = self.getFormHorarios().serialize();
	self.service.validarFechaParaEliminarHorarios(data, function(response) {
		if (!response) {
			self.service.eliminarPrograma({senial: $("#senialDefaultUsuario").val(), codPrograma:self.programaSeleccionado.codigo});
		}else{
			popupConfirmacionEvent.confirmar = function () {
				popupConfirmacionEvent.close();
				Component.get("html/horarioProgramas/popupNuevaFecha.html", function(comp) {
					$("#dialog").empty().append(comp.replace(/{{id}}/g, self.div.id));
					Datepicker.getInstance(self.getSelector("nuevaFecha"), new Date());
					self.getSelector("popupNuevaFecha").dialog({
						height: 'auto',
						width: 600,
						position: 'center',
						show: 'blind',
						hide: 'blind',
						modal: true,
						resizable: false,
						autoOpen: true,
						title: 'Advertencia',
						buttons: {
							"Confirmar" : function() {
								var hoy = new Date();
								hoy.setHours(0,0,0,0);
								if (Validator.isEmpty(self.getSelector("nuevaFecha")) || self.getSelector("nuevaFecha").datepicker("getDate") < hoy) {
									Validator.focus(self.getSelector("nuevaFecha"), "La fecha no puede ser menor a la de hoy.");
									return;
								}
								var request = {
									serialize : data,
									nuevaFecha : self.getSelector("nuevaFecha").val()
								};
								self.service.eliminarNuevaFecha(request);
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
				});
		    };
		    popupConfirmacionEvent.cancelar = function () {
		        popupConfirmacionEvent.close();
		    };
		    popupConfirmacionEvent.create("confirmarEliminarHorariosDiasFuturosPopUp","El Programa tiene Títulos a exhibir en el futuro. ¿Confirma la Baja?");
		}
	});
};
HorarioProgramasEvent.prototype.agregarProgramaPH = function() {
	var self = horarioProgramasEvent;
	self.getDivListadoPH().hide();
	self.event = "agregar";
	Component.get("html/horarioProgramas/listadoMP.html", self.drawListadoMP);
};
HorarioProgramasEvent.prototype.drawListadoMP = function(comp) {
	var self = horarioProgramasEvent;
	if (self.getDivListadoMP().length) {
		self.getDivListadoPH().hide();
		self.getDivListadoMP().show();
	}else{
		self.paginationMP = {
			offset : 0,
			limit : 20
		};
		self.getDiv().append(comp.replace(/{{id}}/g, self.div.id));
		self.getAccordionListadoMP().accordion({
			collapsible: false,
			active: 0,
			autoHeight: false,
			icons: false
		});
		
		self.getButtonBuscarProgramasMPPorCodigo().button().click(self.buscarProgramasMPPorCodigo);
		self.getButtonVolverMaestroProgramas().button().click(self.volverHorarioPH);
		self.getButtonPopUpBuscarMPPorDescripcion().button().click(self.drawPopupGridMaestroProgramas);
		self.getInputBusquedaMPPorCodigo().numeric();
		self.getInputBusquedaMPPorCodigo().keypress(function (event) {
			  if (event.which == 13) {
				  event.preventDefault();
				  self.buscarProgramasMPPorCodigo();
				  return;
			  }
		});
		self.service.getProgramasMPPorCodigo(self.paginationMP);
	}
};
HorarioProgramasEvent.prototype.drawPopupGridMaestroProgramas = function(data){
	var data = {descPrograma : ""};
	situarPopupEvent.reset();
	situarPopupEvent.create("buscarMaestroProgramasPorDescripcion.action", horarioProgramasEvent.programaMPSelected, data, "maestroProgramas");
};
HorarioProgramasEvent.prototype.drawGridMaestroProgramas = function(data){
	var self = horarioProgramasEvent;
	self.drawGrigMP();
	var reader = {
			  root: function(obj) { return data.programas; },
			  page: function(obj) { return Math.abs(data.pagination.offset/data.pagination.limit)+1; },
			  total: function(obj) { return Math.floor(data.pagination.total/data.pagination.limit); },
			  records: function(obj) { return data.pagination.total; }
	};
	self.getGridMaestroProgramas().jqGrid('clearGridData');
	self.getGridMaestroProgramas().setGridParam({data: data.programas, localReader: reader}).trigger('reloadGrid');
	self.paginationMP = data.pagination;
};
HorarioProgramasEvent.prototype.programaMPSelected = function(data){
	var self = horarioProgramasEvent;
	self.event = "alta";
	self.programaSeleccionado = data;
	Component.get("html/horarioProgramas/horarios.html", self.drawHorarios);
};
HorarioProgramasEvent.prototype.drawGrigMP = function() {
	var self = horarioProgramasEvent;
	self.getGridMaestroProgramas().jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Clave','Titulo',''],
		colModel:[ 
		          {name:'codigo',index:'codigo', width:150, sortable:false}, 
		          {name:'descripcion',index:'descripcion', width:300, sortable:false}, 
		          {name:'agregar',index:'agregar', width:30, aling:'center', sortable:false}
		          ],
		rowNum: 20,
        scrollOffset: 0,
        pager: '#HorarioProgramasEventId_pagerGridMaestroProgramas',
        viewrecords: true, 
        loadonce: true,
        gridview: true,
        editurl: 'clientArray', 
        caption: 'Maestro Programas',
        gridComplete: function() {
        	var grid = self.getGridMaestroProgramas();
        	var ids = grid.jqGrid('getDataIDs');
        	for (var i = 0; i < ids.length; i++) {
        		var rowId = ids[i];
        		var agregar = "<center><div onclick='horarioProgramasEvent.visualizarProgramasPH("+rowId+", \"alta\")' style='cursor:pointer'><span class='ui-icon ui-icon-plus agregar conTooltip'>Agregar</span></div></center>";
        		grid.jqGrid('setRowData', rowId, { agregar: agregar });
        	}
        },
        onPaging: function (btn) {
        	if (btn !== "user" && btn !== "records") {
				var action = btn.split("_")[0];
				self.changePageMP("Codigo",action);
			}
        }
	});
};
HorarioProgramasEvent.prototype.changePageMP = function(service, action){
	if (action === "next") {
		if(this.paginationMP.hasNext){
			this.paginationMP.offset = this.paginationMP.offset + this.paginationMP.limit;
			this.service["getProgramasMPPor"+service](this.paginationMP);
		}
	}
	if (action === "prev") {
		if(this.paginationMP.hasPrevious){
			this.paginationMP.offset = this.paginationMP.offset - this.paginationMP.limit;
			this.service["getProgramasMPPor"+service](this.paginationMP);
		}
	}
	if (action === "last") {
		this.paginationMP.offset =  Math.floor(this.paginationMP.total/this.paginationMP.limit)*this.paginationMP.limit;
		this.service["getProgramasMPPor"+service](this.paginationMP);
	}
	if (action === "first") {
		this.paginationMP.offset = 0;
		this.service["getProgramasMPPor"+service](this.paginationMP);
	}
	
};
HorarioProgramasEvent.prototype.altaHorarioPH = function() {
	var self = horarioProgramasEvent;
	var data = self.getFormHorarios().serialize();
	self.service.altaPrograma(data);
};
HorarioProgramasEvent.prototype.buscarProgramasPHPorCodigo = function() {
	var self = horarioProgramasEvent;
	var data = {
		codigo : self.getInputBusquedaPHPorCodigo().val(),
		senial: $("#senialDefaultUsuario").val()
	};
	self.service.getProgramasPhPorCodigo(data);
};
HorarioProgramasEvent.prototype.buscarProgramasPorDescripcion = function() {
	var self = horarioProgramasEvent;
	var	descripcion = self.getInputBusquedaPorDescripcion().val();
	if (self.popupMaestro) {
		self.paginationMP.descripcion = descripcion;
		self.paginationMP.offset = 0;
		self.service.getProgramasMPPorDescripcion(self.paginationMP);
	}else{
		self.service.getProgramasPhPorDescripcion({descripcion: descripcion});
	}
};
HorarioProgramasEvent.prototype.buscarProgramasMPPorCodigo = function() {
	var self = horarioProgramasEvent;
	self.paginationMP = {
			offset : 0,
			limit : 20,
			codigo : self.getInputBusquedaMPPorCodigo().val()
	};
	self.service.getProgramasMPPorCodigo(self.paginationMP);
};
HorarioProgramasEvent.prototype.popupSitualenProgramasPorDesc = function(data, maestro) {
	this.popupMaestro = maestro;
	this.getPopupSituarEnPrograma().dialog("open");
	this.drawGrigSituar();
	var programas = data;
	if (this.popupMaestro) {
		programas = data.programas;
		var reader = {
				  root: function(obj) { return programas; },
				  page: function(obj) { return Math.abs(data.pagination.offset/data.pagination.limit)+1; },
				  total: function(obj) { return Math.floor(data.pagination.total/data.pagination.limit); },
				  records: function(obj) { return data.pagination.total; }
		};
		this.getGridProgramasDesc().jqGrid('clearGridData');
		this.getGridProgramasDesc().setGridParam({data: programas, localReader: reader}).trigger('reloadGrid');
		return;
	}
	this.getGridProgramasDesc().clearGridData().setGridParam({data: programas}).trigger('reloadGrid');
};
HorarioProgramasEvent.prototype.drawGrigSituar = function() {
	var self = this;
	this.getGridProgramasDesc().jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Clave','Titulo'],
		colModel:[ 
			{name:'codigo',index:'codigo', width:150, sortable:false}, 
			{name:'descripcion',index:'descripcion', width:300, sortable:false}
		],
		rowNum: 20,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#HorarioProgramasEventId_pagerGridProgramasDesc',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Situar en Programa',
		onSelectRow: function(id){ 
			var codigo = $(this).jqGrid('getRowData', id).codigo;
			if (!self.popupMaestro) {
				horarioProgramasEvent.service.getProgramasPhPorCodigo({senial: $("#senialDefaultUsuario").val(), codigo : codigo});//horarioProgramasEvent.senialSeleccionada
			}else{
				horarioProgramasEvent.paginationMP.offset = 0;
				horarioProgramasEvent.paginationMP.codigo = codigo;
				horarioProgramasEvent.service.getProgramasMPPorCodigo(horarioProgramasEvent.paginationMP);
			}
			horarioProgramasEvent.getPopupSituarEnPrograma().dialog("close");
		},
		onPaging: function (btn) {
        	if (self.popupMaestro) {
        		if (btn !== "user" && btn !== "records") {
        			var action = btn.split("_")[0];
        			self.changePageMP("Descripcion", action);
        		}
			}
        }
	});
};
HorarioProgramasEvent.prototype.getAccordionListadoMP = function() {
	return $("#" + this.div.id + "_accordionListadoMP");
};
HorarioProgramasEvent.prototype.getDivListadoMP = function() {
	return $("#" + this.div.id + "_listadoMP");
};
HorarioProgramasEvent.prototype.getAccordionListadoPH = function() {
	return $("#" + this.div.id + "_accordionListadoPH");
};
HorarioProgramasEvent.prototype.getDivListadoPH = function() {
	return $("#" + this.div.id + "_listadoPH");
};
HorarioProgramasEvent.prototype.getAccordionHorarios = function() {
	return $("#" + this.div.id + "_accordionHorarios");
};
HorarioProgramasEvent.prototype.getDivHorarios = function() {
	return $("#" + this.div.id + "_horarios");
};
HorarioProgramasEvent.prototype.getFormHorarios = function() {
	return $("#" + this.div.id + "_formHorarios");
};
HorarioProgramasEvent.prototype.getInputsHorarios = function() {
	return $(".hhmm");
};
HorarioProgramasEvent.prototype.getButtonBuscarMaestroProgramas = function() {
	return $("#" + this.div.id + "_buscarMaestroPrograma");
};
HorarioProgramasEvent.prototype.getButtonBuscarProgramasPHPorCodigo = function() {
	return $("#" + this.div.id + "_buscarProgramaPHPorCodigo");
};
HorarioProgramasEvent.prototype.getButtonBuscarProgramasMPPorCodigo = function() {
	return $("#" + this.div.id + "_buscarProgramaMPPorCodigo");
};
HorarioProgramasEvent.prototype.getButtonAgregarProgramaPH = function() {
	return $("#" + this.div.id + "_agregarProgramaPH");
};
HorarioProgramasEvent.prototype.getButtonGuardarHorario = function() {
	return $("#" + this.div.id + "_guardarHorario");
};
HorarioProgramasEvent.prototype.getButtonEliminarHorario = function() {
	return $("#" + this.div.id + "_eliminarHorario");
};
HorarioProgramasEvent.prototype.getButtonModificarHorario = function() {
	return $("#" + this.div.id + "_modificarHorario");
};
HorarioProgramasEvent.prototype.getButtonVolverHorario = function() {
	return $("#" + this.div.id + "_volverHorario");
};
HorarioProgramasEvent.prototype.getButtonVolverMaestroProgramas = function() {
	return $("#" + this.div.id + "_volverMaestroProgramas");
};
HorarioProgramasEvent.prototype.getGridProgramasPH = function() {
	return $("#" + this.div.id + "_gridProgramasPH");
};
HorarioProgramasEvent.prototype.getGridProgramasDesc = function() {
	return $("#" + this.div.id + "_gridProgramasDesc");
};
HorarioProgramasEvent.prototype.getGridMaestroProgramas = function() {
	return $("#" + this.div.id + "_gridMaestroProgramas");
};
HorarioProgramasEvent.prototype.getInputBusquedaPHPorCodigo = function() {
	return $("#" + this.div.id + "_busquedaPHPorCodigo");
};
HorarioProgramasEvent.prototype.getInputBusquedaMPPorCodigo = function() {
	return $("#" + this.div.id + "_busquedaMPPorCodigo");
};
HorarioProgramasEvent.prototype.getInputBusquedaPorDescripcion = function() {
	return $("#" + this.div.id + "_busquedaPorDescripcion");
};
HorarioProgramasEvent.prototype.getButtonBuscarPorDescripcion = function() {
	return $("#" + this.div.id + "_buscarProgramaPorDesc");
};
HorarioProgramasEvent.prototype.getButtonPopUpBuscarPorDescripcion = function() {
	return $('button[name="' + this.div.id + '_popupBuscarPorDesc"]');
};
HorarioProgramasEvent.prototype.getButtonPopUpBuscarMPPorDescripcion = function() {
	return $("#" + this.div.id + "_popupBuscarPorDescMP");
};
HorarioProgramasEvent.prototype.getButtonPopUpBuscarPHPorDescripcion = function() {
	return $("#" + this.div.id + "_popupBuscarPorDescPH");
};
HorarioProgramasEvent.prototype.getPopupSituarEnPrograma = function() {
	return $("#" + this.div.id + "_popupSituarEnPrograma");
};

var horarioProgramasEvent = new HorarioProgramasEvent(new DivDefinition('HorarioProgramasEventId'));