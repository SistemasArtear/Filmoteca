function ModificarRatingExcedenteEvent(div){
	this.div = div;
}

extend(ModificarRatingExcedenteEvent,Plugin);

ModificarRatingExcedenteEvent.prototype.create = function(){
	var self = modificarRatingExcedenteEvent;
	self.serviceModificarRatingExcedente = new ModificarRatingExcedenteService();
	self.serviceLevantarProgramas = new LevantarProgramasService();
	Component.get("html/modificarRatingExcedente/modificarRatingExcedente.html", modificarRatingExcedenteEvent.draw);
};

ModificarRatingExcedenteEvent.prototype.draw = function(comp){
	var self = modificarRatingExcedenteEvent;
	$("#viewContainer").empty().append(comp.replace(/{{id}}/g, self.div.id));
	
	Accordion.getInstance(self.getJqObjectById("accordionFormCabecera"));
	
	Datepicker.picker(self.getJqObjectById("fechaExhibicion"));
	Datepicker.fullYearDatepicker(self.getJqObjectById("fechaExhibicion"));
	self.getJqObjectById("fechaExhibicion").datepicker('setDate', "");
	
	self.idSenial = $("#senialDefaultUsuario").val();
	
	self.serviceLevantarProgramas.levantarListaDeProgramas(self.idSenial,self.cargarProgramas);
	
	self.getJqObjectById("tipoNroTituloSeleccion").prop('disabled',true);
	self.getJqObjectById("codProgramaSeleccion").prop('disabled',true);
	self.getJqObjectById("capituloSeleccion").prop('disabled',true);
	self.getJqObjectById("contratoSeleccion").prop('disabled',true);
	self.getJqObjectById("contactoGrupoSeleccion").prop('disabled',true);
	self.getJqObjectById("fechaExhibicionSeleccion").prop('disabled',true);
	self.getJqObjectById("tipoRatingExcedenteSeleccion").prop('disabled',true);
	self.getJqObjectById("valorRatingExcedenteSeleccion").prop('disabled',true);
	
	self.getJqObjectById("buscar").button().click(self.buscarRatingsExcedentes);
	self.getJqObjectById("modificar").button().click(self.modificarValorRatingExcedente);
	
	self.initGrilla();
};

ModificarRatingExcedenteEvent.prototype.cargarProgramas = function(data){
	var self = modificarRatingExcedenteEvent;
	for (var i = 0; i < data.length; i++) {
		if (i === 0)
			self.getJqObjectById("programa").append("<option value='0'>-</option>");
		self.getJqObjectById("programa").append("<option value='" + data[i].codigo + "'>" + data[i].descripcion + "</option>");
	}
}

ModificarRatingExcedenteEvent.prototype.initGrilla = function(){
	var self = modificarRatingExcedenteEvent;
	var lastsel;
	self.getJqObjectById("grillaRatingExcedentes").jqGrid({
		height: 'auto',
        autowidth: true,
        datatype: 'local',
        colNames:['TipoNroTirulo','Cap.','Contrato','Grupo','Fecha exhibición','Tipo Rating/Excedente','Valor Rating/Excedente','Código y descripción programa','',''],
        colModel:[ 
            {name:'tipoNroTitulo',index:'tipoNroTitulo', width:150, sortable:true}, 
            {name:'capitulo',index:'capitulo', width:300, sortable:true},
            {name:'contrato',index:'contrato', width:300, sortable:true},
            {name:'grupo',index:'grupo', width:300, sortable:true},
            {name:'fechaExhibicion',index:'fechaExhibicion', align: 'center', width:300, sortable:true, formatter: 'date', formatoptions: {newformat:'d/m/Y'}},
            {name:'tipoRatingExcedente',index:'tipoRatingExcedente', width:300, sortable:true, align: 'center'},
            {name:'valorRatingExcedente',index:'valorRatingExcendete', width:300, sortable:true, editable:true//, 
            	//formatter: 'number', formatoptions: {decimalSeparator:".", decimalPlaces: 2}
            },
            {name:'codigoDesPrograma',index:'codigoDesPrograma', width:300, sortable:true},
            {name:'iconoEditar',index:'iconoEditar',width:50,formatter:self.formatIcono},
            {name:'iconoConfirmar',index:'iconoConfirmar',width:50,formatter:self.formatIcono, hidden:true}
        ],
        rowNum: 10,
        rowList:[10,20,30],
        scrollOffset: 0,
        viewrecords: true, 
        loadonce: true,
        caption: 'Títulos con rating y/o excedentes',
        onCellSelect: function(rowid,iCol,cellcontent){
    		
        	if (lastsel == null)
    			lastsel = rowid;
    		
        	if(rowid && rowid==lastsel){
            	if ($(cellcontent).text().trim() === 'Modificar'){
            		$(this).jqGrid('hideCol','iconoEditar');
            		$(this).jqGrid('showCol','iconoConfirmar');
            		$(this).jqGrid('editRow',rowid,{
            			keys:true,
            			aftersavefunc:function(rowid){
                    		$(this).jqGrid('showCol','iconoEditar');
                    		$(this).jqGrid('hideCol','iconoConfirmar');
            				datosFila = $(this).jqGrid('getRowData',rowid);
            				self.modificarValorRatingExcedente(datosFila);
            				lastsel = null;
            			},
            			afterrestorefunc:function(rowid){
                    		$(this).jqGrid('showCol','iconoEditar');
                    		$(this).jqGrid('hideCol','iconoConfirmar');
            				datosFila = $(this).jqGrid('getRowData',rowid);
            				lastsel = null;
            			},
            			oneditfunc:function(rowid){
            				$(':focus').select();
            			}
            		});
            	}
            	if ($(cellcontent).text().trim() === 'Confirmar'){
            		$(this).jqGrid('showCol','iconoEditar');
            		$(this).jqGrid('hideCol','iconoConfirmar');
            		$(this).jqGrid('saveRow',rowid,{
            			keys:true,
            			aftersavefunc:function(rowid){
            				datosFila = $(this).jqGrid('getRowData',rowid);
            				self.modificarValorRatingExcedente(datosFila);
            				lastsel = null;
            			},
            			afterrestorefunc:function(rowid){
            				datosFila = $(this).jqGrid('getRowData',rowid);
            				lastsel = null;
            			}
            		});
            	}
        	}
        	
        	self.resizeGrid(self.getJqObjectById("grillaRatingExcedentes"));
        }
	});
	
};

ModificarRatingExcedenteEvent.prototype.resizeGrid = function(obj){
    var $grid = obj;
    newWidth = $grid.closest(".ui-jqgrid").parent().width();
    $grid.jqGrid("setGridWidth", newWidth, true);
};

ModificarRatingExcedenteEvent.prototype.formatIcono = function(cellvalue, options, rowObject){
	if (cellvalue === 'editar')
		return "<span class='ui-icon ui-icon-pencil modificar conTooltip'>Modificar</span>";
	if (cellvalue === 'check')
		return "<span class='ui-icon ui-icon-check conTooltip'>Confirmar</span>";
};

ModificarRatingExcedenteEvent.prototype.buscarRatingsExcedentes = function(){
	var self = modificarRatingExcedenteEvent;
	
	var filtros = self.obtenerFiltrosDeBusqueda();
	
	self.serviceModificarRatingExcedente.ejecutarBusqueda(filtros,self.cargarResultadosBusqueda);
}

ModificarRatingExcedenteEvent.prototype.modificarValorRatingExcedente = function(datosFila){
	var self = modificarRatingExcedenteEvent;
	
	var datos = self.obtenerDatosAmodificar(datosFila);
	
	if(datos.valorNuevoRatingExcedente.includes("."))
		datos.valorNuevoRatingExcedente = datos.valorNuevoRatingExcedente.replace(".",",");
	
	var regex = undefined;
	var message = "";
	if (datos.tipoRatingExcedente === "Rating"){
		regex = new RegExp(/^\d\d?(,\d\d?)?$/);
		message = "El valor de rating " + datos.valorNuevoRatingExcedente + " no es correcto. No debe ser mayor a 2 dígitos y no puede tener más de 2 decimales."
	}
	else if (datos.tipoRatingExcedente === "Excedente"){
		regex = new RegExp(/^\d\d?\d?$/);
		message = "El valor de excedente " + datos.valorNuevoRatingExcedente + " no es correcto. No debe ser mayor a 3 dígitos y no puede tener decimales."
	}
		
	
	if (regex.test(datos.valorNuevoRatingExcedente))
		self.serviceModificarRatingExcedente.modificarValorRatingExcedente(datos,self.buscarRatingsExcedentes);		
	else{
		MESSAGE.alert(message);
		self.buscarRatingsExcedentes();
	}
		
}

ModificarRatingExcedenteEvent.prototype.cargarResultadosBusqueda = function(data){
	var self = modificarRatingExcedenteEvent;
	self.getJqObjectById("grillaRatingExcedentes").jqGrid("clearGridData");
	for (var i = 0; i < data.responseTitulosRatingExcedente.length; i++) {
		data.responseTitulosRatingExcedente[i].iconoEditar = "editar";
		data.responseTitulosRatingExcedente[i].iconoConfirmar = "check";
	}
	self.getJqObjectById("grillaRatingExcedentes").jqGrid("setGridParam",{data: data.responseTitulosRatingExcedente}).trigger("reloadGrid");
}

ModificarRatingExcedenteEvent.prototype.obtenerFiltrosDeBusqueda = function(){
	var self = modificarRatingExcedenteEvent;
	
	var filtros = null;
	
	var fechaExhibicion = self.getJqObjectById("fechaExhibicion").val();
	
	filtros = {
		tipoNroTitulo: self.getJqObjectById("tipoNroTitulo").val(),
		fechaExhibicion: fechaExhibicion === "" ? fechaExhibicion : moment(self.getJqObjectById("fechaExhibicion").val(),"DD/MM/YYYY").format("DD/MM/YYYY"),
		programa: {
			codigo: self.getJqObjectById("programa").find(":selected").val(),
			descripcion: self.getJqObjectById("programa").find(":selected").text()
		},
		idSenial: self.idSenial
	};
	
	return filtros;
}

ModificarRatingExcedenteEvent.prototype.obtenerDatosAmodificar = function(rowData){
	var self = modificarRatingExcedenteEvent;
	
	var datos = null;
		
	datos = {
			tipoNroTitulo: rowData.tipoNroTitulo,
			nroCapitulo: rowData.capitulo,
			grupo: rowData.grupo,
			contrato: rowData.contrato,
			fechaExhibicion: rowData.fechaExhibicion,
			tipoRatingExcedente: rowData.tipoRatingExcedente,
			valorNuevoRatingExcedente: rowData.valorRatingExcedente,
			codPrograma: rowData.codigoDesPrograma.split(" ")[0],
			idSenial: self.idSenial	
	}
	
	return datos;
}

ModificarRatingExcedenteEvent.prototype.getJqObjectById = function(id){
	return $("#" + this.div.id + "_" + id);
}

var modificarRatingExcedenteEvent = new ModificarRatingExcedenteEvent(new DivDefinition("modificarRatingExcedente"));