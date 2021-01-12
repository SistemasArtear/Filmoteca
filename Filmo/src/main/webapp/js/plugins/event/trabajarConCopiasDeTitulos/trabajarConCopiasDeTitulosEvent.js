TrabajarConCopiasDeTitulosEvent.CASTELLANO = "castellano";
TrabajarConCopiasDeTitulosEvent.ORIGINAL = "original";
TrabajarConCopiasDeTitulosEvent.ALTA = "Alta";
TrabajarConCopiasDeTitulosEvent.MODIF = "Modificacion";
TrabajarConCopiasDeTitulosEvent.ROLLOS = "rollos";
TrabajarConCopiasDeTitulosEvent.MASTER = "master";
function TrabajarConCopiasDeTitulosEvent(div) {
	this.div = div;
	this.tipoBusquedaTitulos = TrabajarConCopiasDeTitulosEvent.CASTELLANO;
};
extend(TrabajarConCopiasDeTitulosEvent, Plugin);
TrabajarConCopiasDeTitulosEvent.prototype.create = function() {
	var self = trabajarConCopiasDeTitulosEvent;
	self.service = new TrabajarConCopiasDeTitulosService();
	Component.get("html/trabajarConCopiasDeTitulos/busquedaInicial.html", trabajarConCopiasDeTitulosEvent.draw);
};
TrabajarConCopiasDeTitulosEvent.prototype.draw = function(comp) {
	var self = trabajarConCopiasDeTitulosEvent;
	$("#viewContainer").empty().append(comp.replace(/{{id}}/g, self.div.id));
	//Component.populateSelect(self.getSelector("senial"), seniales, "codigo", "descripcion");
	self.getSelector("buscarTitulos").button().click(self.buscarTitulos);
	self.getSelector("titulo").keypress(function (event) {
		if (event.which == 13) {
			event.preventDefault();
			self.buscarTitulos();
			return;
		}
	});
	self.getSelector("clave").keypress(function (event) {
		if (event.which == 13) {
			event.preventDefault();
			self.buscarTitulos();
			return;
		}
	});
	self.getSelector("tipoBusqueda").change(function() {
		self.getSelector("gridTitulos").clearGridData();
		self.getSelector("titulo").val("");
		self.getSelector("clave").val("");
		if(this.tipoBusquedaTitulos === TrabajarConCopiasDeTitulosEvent.CASTELLANO){
			this.tipoBusquedaTitulos === TrabajarConCopiasDeTitulosEvent.ORIGINAL;
		}else{
			this.tipoBusquedaTitulos === TrabajarConCopiasDeTitulosEvent.CASTELLANO;
		}
	});
	
	self.senialSeleccionada = $("#senialDefaultUsuario").val();
	
	Accordion.getInstance(self.getSelector("accordionBusquedaInicial"));
};
TrabajarConCopiasDeTitulosEvent.prototype.buscarTitulos = function() {
	var self = trabajarConCopiasDeTitulosEvent;
	if (self.validForm()) {
		self.formatearClave();
		var data = self.getSelector("formBusquedaInicial").serialize()
		  +"&busquedaTitulosRequest.senial="+$("#senialDefaultUsuario").val();
		self.service.getTitulos(data, function(response) {
			self.drawGridTitulos(response);
		});
	}
};
TrabajarConCopiasDeTitulosEvent.prototype.formatearClave = function() {
	if (this.getSelector("clave").val() != "") {
        var foo = this.getSelector("clave").val();
        var cantCerosAAgregar = 6 - (foo.length - 2);
        var tipoTitulo = foo.substring(0,2);
        var clave = foo.substring(2);
        var ret = tipoTitulo.toUpperCase();
        while (cantCerosAAgregar > 0) {
            ret = ret + "0"; 
            cantCerosAAgregar--;
        }
        ret = ret + clave;
        this.getSelector("clave").val(ret);
    }
};
TrabajarConCopiasDeTitulosEvent.prototype.validForm = function() {
	
	var titulo = this.getSelector("titulo");
	var clave = this.getSelector("clave");
	
	if (Validator.fillAtLeastOneField(titulo, clave)) {
		Validator.focus(titulo, "Debe ingresar por lo menos un título o una clave.");
		return false;
	}
	return true;
};
TrabajarConCopiasDeTitulosEvent.prototype.drawGridTitulos = function(data) {
	var self = trabajarConCopiasDeTitulosEvent;
	self.getSelector("gridTitulos").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Clave','Título Castellano','Título Original','Actores','','Tiene Copias','Vigente','','',''],
		colModel:[ 
			{name:'clave',index:'clave', width:35, sortable:false}, 
			{name:'tituloCastellano',index:'tituloCastellano', width:100, sortable:false}, 
			{name:'tituloOriginal',index:'tituloOriginal', width:100, sortable:false}, 
			{name:'actores',index:'actores', width:130, sortable:false}, 
			{name:'signo',index:'signo', width:30, sortable:false}, 
			{name:'tieneCopia',index:'tieneCopia', width:30, sortable:false},
			{name:'vigente',index:'vigente', hidden:true},
			{name:'copia',index:'copia', width:15, sortable:false}, 
			{name:'master',index:'master', width:15, sortable:true}, 
			{name:'consultar',index:'consultar', width:15, sortable:false} 
		],
		rowNum: 20,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#TrabajarConCopiasDeTitulosEventId_pagerGridTitulos',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Titulos',
		gridComplete: function() {
        	var ids = $(this).jqGrid('getDataIDs');
        	for (var i = 0; i < ids.length; i++) {
        		var rowId = ids[i];
        		var titulo = $(this).jqGrid('getRowData', rowId);
				var copia = "<center><div onclick='trabajarConCopiasDeTitulosEvent.registrarCopia(\""+titulo.clave+"\",\""+titulo.tituloOriginal+"\",\""+titulo.tituloCastellano+"\",\""+TrabajarConCopiasDeTitulosEvent.ROLLOS+"\",\""+titulo.vigente+"\")' style='cursor:pointer'><span class='ui-icon ui-icon-copy registrarCopia conTooltip'>Registrar Copia</span></div></center>";
				$(this).jqGrid('setRowData', rowId, { copia: copia });
				var master = "<center><div onclick='trabajarConCopiasDeTitulosEvent.registrarCopia(\""+titulo.clave+"\",\""+titulo.tituloOriginal+"\",\""+titulo.tituloCastellano+"\",\""+TrabajarConCopiasDeTitulosEvent.MASTER+"\",\""+titulo.vigente+"\")' style='cursor:pointer'><span class='ui-icon ui-icon-key registrarMaster conTooltip'>Registrar Master</span></div></center>";
				$(this).jqGrid('setRowData', rowId, { master: master });
				var consultar = "<center><div onclick='trabajarConCopiasDeTitulosEvent.consultarCopia(\""+titulo.clave+"\",\""+titulo.tituloOriginal+"\",\""+titulo.tituloCastellano+"\",\""+titulo.vigente+"\")' style='cursor:pointer'><span class='ui-icon ui-icon-document consultarCopia conTooltip'>ConsultarCopia</span></div></center>";
				$(this).jqGrid('setRowData', rowId, { consultar: consultar });
        	}
	    }
	});
	self.getSelector("gridTitulos").clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};

TrabajarConCopiasDeTitulosEvent.prototype.consultarCopia = function(clave, titOrig, titCast, vigente) {
	var self = trabajarConCopiasDeTitulosEvent;
	self.tituloSeleccionado = {
			senial : $("#senialDefaultUsuario").val(),
			clave : clave,
			titOrig : titOrig,
			titCast : titCast,
			vigente : vigente
	};
	Component.get("html/trabajarConCopiasDeTitulos/consultarCopia.html", function(comp) {
		self.service.consultarDatosTitulo(self.tituloSeleccionado, function(response) {
			self.getSelector("busquedaInicial").hide();
			comp = comp.replace(/{{id}}/g, self.div.id).replace(/{{clave}}/g, clave)
				.replace(/{{tituloOriginal}}/g, titOrig).replace(/{{tituloCastellano}}/g, titCast)
				.replace(/{{capitulo}}/, self.tituloConCapitulos(clave)?"Con":"Sin");
			if (self.getSelector("consultarCopia").length) {
				self.getSelector("consultarCopia").replaceWith(comp);
			}else{
				self.getDiv().append(comp);
			}
			Accordion.getInstance(self.getSelector("accordionConsultaCopia"));
			self.getSelector("agregarMaster").button().click(function() {
				self.registrarCopia(clave, titOrig, titCast, TrabajarConCopiasDeTitulosEvent.MASTER, vigente);
			});
			self.getSelector("agregarCopia").button().click(function() {
				self.registrarCopia(clave, titOrig, titCast, TrabajarConCopiasDeTitulosEvent.ROLLOS, vigente);
			});
			self.getSelector("volverABusquedaInicial").button().click(function() {
				self.getSelector("consultarCopia").hide();
				self.getSelector("busquedaInicial").show();
			});
			self.drawGridDetalleTitulo(response);
		});
	});
};

TrabajarConCopiasDeTitulosEvent.prototype.registrarCopia = function(clave, titOrig, titCast, tipoCopia, vigente) {
	var self = trabajarConCopiasDeTitulosEvent;
	self.tituloSeleccionado = {
			senial : $("#senialDefaultUsuario").val(),
			clave : clave,
			titOrig : titOrig,
			titCast : titCast,
			vigente : vigente
	};
	if (vigente === "N") {
		MESSAGE.alert("Título no vigente o sin pasadas pendientes.");
		return;
	}
	self.service.chequearMaterialesCopia(
			{clave:clave,senial:$("#senialDefaultUsuario").val()},function(validacion) {
		if (validacion && validacion === "N") {
			MESSAGE.alert("El material no se encuentra en el canal.");
			return;
		}
		self.tipoCopia = tipoCopia;
		self.esConCapitulos = self.tituloConCapitulos(clave);
		var tipoCapitulos = self.esConCapitulos ? "con" : "sin";
		Component.get("html/trabajarConCopiasDeTitulos/altaCopia.html", function(comp) {
			self.getSelector("busquedaInicial").hide();
			self.getSelector("consultarCopia").hide();
			comp = comp.replace(/{{id}}/g, self.div.id).replace(/{{clave}}/g, clave).replace(/{{senial}}/g, $("#senialDefaultUsuario").val())
			.replace(/{{tituloOriginal}}/g, titOrig).replace(/{{tituloCastellano}}/g, titCast)
			.replace(/{{capitulo}}/, tipoCapitulos).replace(/{{tipoCopia}}/, self.tipoCopia);
			if (self.tipoCopia === TrabajarConCopiasDeTitulosEvent.ROLLOS) {
				if (self.esConCapitulos) {
					comp = comp.replace(/{{object}}/g, "rolloCC");
				}else{
					comp = comp.replace(/{{object}}/g, "rolloSC");
				}
			}else{
				if (self.esConCapitulos) {
					comp = comp.replace(/{{object}}/g, "masterCC");
				}else{
					comp = comp.replace(/{{object}}/g, "masterSC");
				}
			}
			if (self.getSelector("altaCopia").length) {
				self.getSelector("altaCopia").replaceWith(comp);
			}else{
				self.getDiv().append(comp);
			}
			if (self.esConCapitulos) {
				self.getSelector("sin_capitulosForm").hide();
				self.getSelector("con_capitulosForm").show();
				Datepicker.getInstance(self.getSelector("con_fecha"), new Date());
			}else{
				self.getSelector("con_capitulosForm").hide();
				self.getSelector("sin_capitulosForm").show();
				Datepicker.getInstance(self.getSelector("sin_fecha"), new Date());
			}
			if (self.tipoCopia === TrabajarConCopiasDeTitulosEvent.MASTER) {
				self.getSelector(tipoCapitulos+"_trRollos").hide();
			}
			Accordion.getInstance(self.getSelector("accordionAltaCopia"));
			//Accordion.getInstance(self.getSelector("accordionAltaCopiaSegmento"));
			self.getSelector("aceptarAltaCopia").button().click(self.aceptarAltaCopia );
			self.getSelector("volverAltaCopia").button().click(function() {
				self.getSelector("altaCopia").hide();
				self.getSelector("consultarCopia").show();
				self.consultarCopia(self.tituloSeleccionado.clave, self.tituloSeleccionado.titOrig, self.tituloSeleccionado.titCast, self.tituloSeleccionado.vigente);
			});
			self.getSelector(tipoCapitulos+"_buscarSoportes").click(function() {
			    situarPopupEvent.reset();
				situarPopupEvent.create("trabajarConCopiasDeTitulosBuscarSoportes.action", function(row) {
					self.getSelector(tipoCapitulos+"_soporte").val(row.codigo);
				}, {descripcion : ""}, "soportes");
			});
			self.getSelector("buscarCapitulos").click(function() {
			    situarPopupEvent.reset();
				situarPopupEvent.create("trabajarConCopiasDeTitulosBuscarCapitulos.action", function(row) {
					self.getSelector("capitulo").val(row.capitulo);
					self.getSelector("parte").val(row.parte);
					self.getSelector("segmento").val(row.segmento);
				}, {clave : clave}, "capitulos");
			});
			for ( var i = 0; i < 5; i++) {
				self.getSelector(tipoCapitulos+"_rollo"+i).numeric();
				if (self.esConCapitulos && i === 1) {
					break;
				}
			}
			self.getSelector("capitulo").numeric();
		});
	});
};

TrabajarConCopiasDeTitulosEvent.prototype.validFormAlta = function(tipoCapitulos, tipoCopia) {
	var self = trabajarConCopiasDeTitulosEvent;
	var soporte = self.getSelector(tipoCapitulos + "_soporte");
	var rollo = self.getSelector(tipoCapitulos + "_rollo0");
	var fecha = self.getSelector(tipoCapitulos + "_fecha");
	var capitulo = self.getSelector("capitulo");
	if (Validator.isEmpty(soporte)) {
		Validator.focus(soporte);
		return false;
	}
	if (self.esConCapitulos && Validator.isEmpty(capitulo)) {
		Validator.focus(capitulo);
		return false;
	}
	if (tipoCopia === TrabajarConCopiasDeTitulosEvent.ROLLOS 
			&& Validator.isEmpty(rollo)) {
		Validator.focus(rollo);
		return false;
	}
	if (Validator.isEmpty(fecha)) {
		Validator.focus(fecha);
		return false;
	}
	return true;
};


TrabajarConCopiasDeTitulosEvent.prototype.drawGridDetalleTitulo = function(data) {
	var colNames = ['secuencia','Copia','Soporte','Rollo','Fecha','Master','',''];
	var colModel = [ 
	                {name:'secuencia',index:'secuencia', hidden:true},
	    			{name:'copia',index:'copia', width:30, sortable:false}, 
	    			{name:'soporte',index:'soporte', width:30, sortable:false}, 
	    			{name:'rollo',index:'rollo', width:30, sortable:false}, 
	    			{name:'fecha',index:'fecha', width:70, sortable:false, formatter: 'date', formatoptions: { srcformat: 'Y/m/d', newformat: 'd/m/Y'}}, 
	    			{name:'master',index:'master', width:30, sortable:true}, 
	    			{name:'modificar',index:'modificar', width:20, sortable:false}, 
	    			{name:'eliminar',index:'eliminar', width:20, sortable:false} ];
	var self = trabajarConCopiasDeTitulosEvent;
	if (self.tituloConCapitulos(self.tituloSeleccionado.clave)) {
		colNames.unshift('Nro Capítulo', 'Parte', 'Segmento');
		colModel.unshift({name:'capitulo',index:'capitulo', width:30, sortable:false},
		                  {name:'parte',index:'parte', width:30, sortable:false},
						  {name:'segmento',index:'segmento', width:30, sortable:false});
	}
	self.getSelector("gridDetalleTitulo").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:colNames,
		colModel:colModel,
		rowNum: 20,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#TrabajarConCopiasDeTitulosEventId_pagerGridDetalleTitulo',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Titulos',
		gridComplete: function() {
        	var ids = $(this).jqGrid('getDataIDs');
        	for (var i = 0; i < ids.length; i++) {
        		var rowId = ids[i];
				var modificar = "<center><div onclick='trabajarConCopiasDeTitulosEvent.modificarCopia("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-pencil modificar conTooltip'>Modificar</span></div></center>";
				$(this).jqGrid('setRowData', rowId, { modificar: modificar });
				var eliminar = "<center><div onclick='trabajarConCopiasDeTitulosEvent.eliminarCopia("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-trash eliminar conTooltip'>Eliminar</span></div></center>";
				$(this).jqGrid('setRowData', rowId, { eliminar: eliminar });
        	}
	    }
	});
	self.getSelector("gridDetalleTitulo").clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};

TrabajarConCopiasDeTitulosEvent.prototype.modificarCopia = function(rowId) {
	var self = trabajarConCopiasDeTitulosEvent;
	var data = self.tituloSeleccionado;
	data.copia = self.getSelector("gridDetalleTitulo").jqGrid('getRowData', rowId);
	self.esConCapitulos = self.tituloConCapitulos(data.clave);
	var tipoCapitulos = self.esConCapitulos ? "con" : "sin";
	self.tipoCopia = data.copia.master === "X" ? TrabajarConCopiasDeTitulosEvent.MASTER : TrabajarConCopiasDeTitulosEvent.ROLLOS; 
	Component.get("html/trabajarConCopiasDeTitulos/modifCopia.html", function(comp) {
		self.getSelector("busquedaInicial").hide();
		self.getSelector("consultarCopia").hide();
		comp = comp.replace(/{{id}}/g, self.div.id)
		.replace(/{{tipoCapitulo}}/, tipoCapitulos)
		.replace(/{{tipoCopia}}/, self.tipoCopia)
		.replace(/{{clave}}/g, data.clave)
		.replace(/{{senial}}/g, $("#senialDefaultUsuario").val())
		.replace(/{{tituloOriginal}}/g, data.titOrig)
		.replace(/{{tituloCastellano}}/g, data.titCast)
		.replace(/{{nroCopia}}/g, data.copia.copia)
		.replace(/{{secuencia}}/g, data.copia.secuencia)
		.replace(/{{capitulo}}/g, data.copia.capitulo?data.copia.capitulo:"")
		.replace(/{{parte}}/g, data.copia.parte?data.copia.parte:"")
		.replace(/{{segmento}}/g, data.copia.segmento?data.copia.segmento:"")
		.replace(/{{codSoporte}}/g, data.copia.soporte)
		.replace(/{{rollo}}/g, data.copia.rollo);
		if (self.tipoCopia === TrabajarConCopiasDeTitulosEvent.ROLLOS) {
			if (self.esConCapitulos) {
				comp = comp.replace(/{{object}}/g, "rolloCC");
			}else{
				comp = comp.replace(/{{object}}/g, "rolloSC");
			}
		}else{
			if (self.esConCapitulos) {
				comp = comp.replace(/{{object}}/g, "masterCC");
			}else{
				comp = comp.replace(/{{object}}/g, "masterSC");
			}
		}
		if (self.getSelector("modifCopia").length) {
			self.getSelector("modifCopia").replaceWith(comp);
		}else{
			self.getDiv().append(comp);
		}
		if (self.esConCapitulos) {
			self.getSelector("datosConCapitulos").show();
		}else{
			self.getSelector("datosConCapitulos").hide();
		}
		if (self.tipoCopia === TrabajarConCopiasDeTitulosEvent.MASTER) {
			self.getSelector("trRollos").hide();
		}
		Datepicker.getInstance(self.getSelector("fechaModif"), data.copia.fecha);
		Accordion.getInstance(self.getSelector("accordionModifCopia"));
		self.getSelector("rolloModif").numeric();
		self.getSelector("aceptarModifCopia").button().click(function() {
			if (self.validFormModif(self.tipoCopia)) {
				var request = self.getSelector("modifCopiaForm").serialize();
				self.service.modifCopia(request, function() {
					self.consultarCopia(data.clave, data.titOrig, data.titCast, data.vigente);
					self.getSelector("modifCopia").hide();
				});
			}
		});
		self.getSelector("volverModifCopia").button().click(function() {
			self.getSelector("modifCopia").hide();
			self.getSelector("consultarCopia").show();
			self.consultarCopia(data.clave, data.titOrig, data.titCast, data.vigente);
		});
	});
};

TrabajarConCopiasDeTitulosEvent.prototype.validFormModif = function(tipoCopia) {
	var self = trabajarConCopiasDeTitulosEvent;
	var rollo = self.getSelector("rolloModif");
	var fecha = self.getSelector("fechaModif");
	if (Validator.isEmpty(fecha)) {
		Validator.focus(fecha);
		return false;
	}
	if (tipoCopia === TrabajarConCopiasDeTitulosEvent.ROLLOS 
			&& Validator.isEmpty(rollo)) {
		Validator.focus(rollo);
		return false;
	}
	return true;
};
TrabajarConCopiasDeTitulosEvent.prototype.eliminarCopia = function(rowId) {
	var self = trabajarConCopiasDeTitulosEvent;
	self.rowIdCopia = rowId;
	popupConfirmacionEvent.confirmar = function () {
		self.tituloSeleccionado.copia = self.getSelector("gridDetalleTitulo").jqGrid('getRowData', self.rowIdCopia);
		self.service.validarBaja(self.tituloSeleccionado, function(response) {
			if (response.tipo === "W") {
				popupConfirmacionEvent.confirmar = function () {
					self.service.eliminarCopia(self.tituloSeleccionado, function() {
						self.consultarCopia(self.tituloSeleccionado.clave, 
								self.tituloSeleccionado.titOrig, self.tituloSeleccionado.titCast, self.tituloSeleccionado.vigente);
						self.getSelector("consultarCopia").hide();
					});
					popupConfirmacionEvent.close();
			    };
			    popupConfirmacionEvent.cancelar = function () {
			        popupConfirmacionEvent.close();
			    };
			    popupConfirmacionEvent.create("validarBajaCopiaMasterPopUp",response.mensaje);
			}else if (response.tipo === "E") {
				MESSAGE.error(response.mensaje);
			}else{
				self.service.eliminarCopia(self.tituloSeleccionado, function() {
					self.consultarCopia(self.tituloSeleccionado.clave, 
							self.tituloSeleccionado.titOrig, self.tituloSeleccionado.titCast, self.tituloSeleccionado.vigente);
					self.getSelector("consultarCopia").hide();
				});
			}
		});
		popupConfirmacionEvent.close();
    };
    popupConfirmacionEvent.cancelar = function () {
        popupConfirmacionEvent.close();
    };
    popupConfirmacionEvent.create("eliminarCopiaDeTituloPopUp","¿Esta seguro que desea eliminarlo?");
	
};

TrabajarConCopiasDeTitulosEvent.prototype.aceptarAltaCopia = function() {
	var self = trabajarConCopiasDeTitulosEvent;
	var tipoCapitulos = self.esConCapitulos ? "con" : "sin";
	if(self.validFormAlta(tipoCapitulos, self.tipoCopia)){
		var parte = self.getSelector("parte");
		var segmento = self.getSelector("segmento");
		if (Validator.isEmpty(parte)) {
			parte.val(0);
		}
		if (Validator.isEmpty(segmento)) {
			segmento.val(0);
		}
		var data = self.getSelector(tipoCapitulos+"_capitulosForm").serialize();
		self.service.validarAlta(data, self.validarAltaCallbackFn);
		
	};	
	
};

TrabajarConCopiasDeTitulosEvent.prototype.validarAltaCallbackFn = function (data, response) {
	var self = trabajarConCopiasDeTitulosEvent;
	if (response.tipo === "W") {
		popupConfirmacionEvent.confirmar = function () {
			self.service.altaCopia(data, function() {
				self.borrarFormAltaCopia();
			});
			popupConfirmacionEvent.close();
	    };
	    popupConfirmacionEvent.cancelar = function () {
	        popupConfirmacionEvent.close();
	    };
	    popupConfirmacionEvent.create("validarAltaCopiaMasterPopUp",response.mensaje);
	}else if (response.tipo === "E") {
		MESSAGE.error(response.mensaje);
	}else{
		self.service.altaCopia(data, function() {
			self.borrarFormAltaCopia();
		});
	}
};

TrabajarConCopiasDeTitulosEvent.prototype.borrarFormAltaCopia = function() {
	var self = trabajarConCopiasDeTitulosEvent;
	var tipoCapitulos = self.esConCapitulos ? "con" : "sin";
	if (self.getSelector(tipoCapitulos+"_soporte") != null)
		self.getSelector(tipoCapitulos+"_soporte").val("");
	if (self.getSelector("capitulo") != null)
		self.getSelector("capitulo").val("");
	if (self.getSelector("parte") != null)
		self.getSelector("parte").val("");
	if (self.getSelector("segmento") != null)
		self.getSelector("segmento").val("");
	if (self.getSelector(tipoCapitulos+"_rollo0") != null)
		self.getSelector(tipoCapitulos+"_rollo0").val("");
	if (self.getSelector(tipoCapitulos+"_rollo1") != null)
		self.getSelector(tipoCapitulos+"_rollo1").val("");
	if (self.getSelector(tipoCapitulos+"_rollo2") != null)
		self.getSelector(tipoCapitulos+"_rollo2").val("");
	if (self.getSelector(tipoCapitulos+"_rollo3") != null)
		self.getSelector(tipoCapitulos+"_rollo3").val("");
	if (self.getSelector(tipoCapitulos+"_fecha") != null)
		Datepicker.getInstance(self.getSelector(tipoCapitulos + "_fecha"), new Date());
};

TrabajarConCopiasDeTitulosEvent.prototype.tituloConCapitulos = function(clave) {
	var tipo = clave.substring(0,2);
	if (tipo === "SE" || tipo === "MS" || tipo === "SU") {
		return  true;
	}
	return false;
};
var trabajarConCopiasDeTitulosEvent = new TrabajarConCopiasDeTitulosEvent(new DivDefinition('TrabajarConCopiasDeTitulosEventId'));