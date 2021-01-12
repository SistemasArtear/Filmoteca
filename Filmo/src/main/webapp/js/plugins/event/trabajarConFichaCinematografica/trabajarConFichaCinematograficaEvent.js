TrabajarConFichaCinematograficaEvent.CASTELLANO = "castellano";
TrabajarConFichaCinematograficaEvent.ORIGINAL = "original";
TrabajarConFichaCinematograficaEvent.ADD = "Alta";
TrabajarConFichaCinematograficaEvent.UPDATE = "Modificación";
TrabajarConFichaCinematograficaEvent.TEMPLATES = {
	actores : '<div class="FormRowA"><span>{{actor}}</span></div><div class="FormRowB"><span>{{personaje}}</span></div>',
	sinopsis : '<div class="FormRowA"><span>{{sinopsis}}</span></div>',
	inputAltaActores : '<div class="FormRowA actorCount">\
					<input type="text" name="fichaCompleta.actores[{{index}}].actor" id="TrabajarConFichaCinematograficaEventId_actor{{index}}" style="width:150px; text-transform: uppercase;" maxlength="30"/>\
				</div>\
				<div class="FormRowB">\
					<input type="text" name="fichaCompleta.actores[{{index}}].personaje" id="TrabajarConFichaCinematograficaEventId_personaje{{index}}" style="width:150px; text-transform: uppercase;" maxlength="30"/>\
				</div>',
	inputAltaSinopsis : '<div class="FormRowC sinopsisCount">\
					<input type="text" name="fichaCompleta.sinopsis[{{index}}].detalle" id="TrabajarConFichaCinematograficaEventId_sinopsis{{index}}" style="width: 50%; text-transform: uppercase;" maxlength="50"/>\
				</div>'
};
function TrabajarConFichaCinematograficaEvent(div) {
	this.div = div;
	this.tipoBusquedaTitulos = TrabajarConFichaCinematograficaEvent.CASTELLANO;
};
extend(TrabajarConFichaCinematograficaEvent, Plugin);
TrabajarConFichaCinematograficaEvent.prototype.create = function() {
	var self = trabajarConFichaCinematograficaEvent;
	self.service = new TrabajarConFichaCinematograficaService();
	Component.get("html/trabajarConFichaCinematografica/listadoFichas.html", self.draw);
};
TrabajarConFichaCinematograficaEvent.prototype.draw = function(comp) {
	var self = trabajarConFichaCinematograficaEvent;
	$("#viewContainer").empty().append(comp.replace(/{{id}}/g, self.div.id));
	Accordion.getInstance(self.getSelector("accordionFormInicial"));
	self.getSelector("buscarFichas").button().click(self.buscarFichas);
	self.getSelector("tituloBusqueda").keypress(function (event) {
		if (event.which == 13) {
			event.preventDefault();
			self.buscarFichas();
			return;
		}
	});
	self.getSelector("tipoBusqueda").change(function() {
		self.getSelector("gridFichas").clearGridData();
		self.getSelector("tituloBusqueda").val("");
		if(this.tipoBusquedaTitulos === TrabajarConCopiasDeTitulosEvent.CASTELLANO){
			this.tipoBusquedaTitulos === TrabajarConCopiasDeTitulosEvent.ORIGINAL;
		}else{
			this.tipoBusquedaTitulos === TrabajarConCopiasDeTitulosEvent.CASTELLANO;
		}
	});
	self.getSelector("tituloBusqueda").focus();
	self.getSelector("crearFicha").button().click(function() {
		self.accion = TrabajarConFichaCinematograficaEvent.ADD;
		self.drawAltaModifFicha();
	});
};
TrabajarConFichaCinematograficaEvent.prototype.buscarFichas = function() {
	var self = trabajarConFichaCinematograficaEvent;
	if (self.validForm()) {
		var data = self.getSelector("formInicial").serialize();
		self.service.getFichas(data, function(response) {
			self.drawGridFichas(response);
		});
	}
};
TrabajarConFichaCinematograficaEvent.prototype.validForm = function() {
	var descripcion = this.getSelector("tituloBusqueda");
	if (Validator.isEmpty(descripcion)) {
		Validator.focus(descripcion);
		return false;
	}
	return true;
};
TrabajarConFichaCinematograficaEvent.prototype.drawGridFichas = function(data) {
	var self = trabajarConFichaCinematograficaEvent;
	self.getSelector("gridFichas").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['','Título Castellano','Título Original','Actor 1','Actor 2','','',''],
		colModel:[ 
		    {name:'idFicha',index:'idFicha', hidden:true}, 
			{name:'tituloCastellano',index:'tituloCastellano', width:100, sortable:false}, 
			{name:'tituloOriginal',index:'tituloOriginal', width:100, sortable:false}, 
			{name:'actor1',index:'actor1', width:90, sortable:false}, 
			{name:'actor2',index:'actor2', width:90, sortable:false}, 
			{name:'modificar',index:'modificar', width:20, sortable:false}, 
			{name:'eliminar',index:'eliminar', width:20, sortable:true}, 
			{name:'visualizar',index:'visualizar', width:20, sortable:false} 
		],
		rowNum: 20,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#TrabajarConFichaCinematograficaEventId_pagerGridFichas',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Fichas Cinematográficas',
		gridComplete: function() {
        	var ids = $(this).jqGrid('getDataIDs');
        	for (var i = 0; i < ids.length; i++) {
        		var rowId = ids[i];
        		var ficha = $(this).jqGrid('getRowData', rowId);
				var modificar = "<center><div onclick='trabajarConFichaCinematograficaEvent.modificarFicha("+ficha.idFicha+")' style='cursor:pointer'><span class='ui-icon ui-icon-pencil modificar conTooltip'>Modificar</span></div></center>";
				$(this).jqGrid('setRowData', rowId, { modificar: modificar });
				var eliminar = "<center><div onclick='trabajarConFichaCinematograficaEvent.eliminarFicha("+ficha.idFicha+")' style='cursor:pointer'><span class='ui-icon ui-icon-trash eliminar conTooltip'>Eliminar</span></div></center>";
				$(this).jqGrid('setRowData', rowId, { eliminar: eliminar });
				var visualizar = "<center><div onclick='trabajarConFichaCinematograficaEvent.visualizarFicha("+ficha.idFicha+")' style='cursor:pointer'><span class='ui-icon ui-icon-search visualizar conTooltip'>Visualizar</span></div></center>";
				$(this).jqGrid('setRowData', rowId, { visualizar: visualizar });
        	}
	    }
	});
	self.getSelector("gridFichas").clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};
TrabajarConFichaCinematograficaEvent.prototype.eliminarFicha = function(id) {
	var self = trabajarConFichaCinematograficaEvent;
	self.fichaSeleccionada = id;
	self.visualizarFicha(id, function() {
		self.getSelector("accionV").text("Eliminación");
		self.getSelector("eliminarFicha").show();
		self.getSelector("eliminarFicha").button().click(function() {
			popupConfirmacionEvent.confirmar = function () {
				self.service.eliminarFicha(self.fichaSeleccionada, function() {
					self.volverDeVisualizacion();
					self.buscarFichas();
				});
				popupConfirmacionEvent.close();
		    };
		    popupConfirmacionEvent.cancelar = function () {
		        popupConfirmacionEvent.close();
		    };
		    popupConfirmacionEvent.create("eliminarFichaCinematograficaPopUp","¿Confirma el borrado de la ficha cinematográfica?");
			
		});
	});
	
};
TrabajarConFichaCinematograficaEvent.prototype.visualizarFicha = function(id, callback) {
	var self = trabajarConFichaCinematograficaEvent;
	self.service.getFicha(id, function(response) {
		var ficha = response.ficha;
		Component.get("html/trabajarConFichaCinematografica/visualizarFicha.html", function(comp) {
			self.getSelector("divFormInicial").hide();
			comp = comp.replace(/{{id}}/g, self.div.id)
				.replace(/{{tituloOriginal}}/g, ficha.tituloOriginal)
				.replace(/{{tituloCastellano}}/g, ficha.tituloCastellano)
				.replace(/{{color}}/g, ficha.color)
				.replace(/{{califComCod}}/g, ficha.califComercialCod ? ficha.califComercialCod : "")
				.replace(/{{califComDesc}}/g, ficha.califComercialDesc ? ficha.califComercialDesc : "")
				.replace(/{{califArtCod}}/g, ficha.califArtisticaCod ? ficha.califArtisticaCod : "")
				.replace(/{{califArtDesc}}/g, ficha.califArtisticaDesc ? ficha.califArtisticaDesc : "")
				.replace(/{{sello}}/g, ficha.selloProductor)
				.replace(/{{generoCod}}/g, ficha.generoCod ? ficha.generoCod : "")
				.replace(/{{generoDesc}}/g, ficha.generoDesc ? ficha.generoDesc : "")
				.replace(/{{anioProduccion}}/g, ficha.anioProduccion)
				.replace(/{{productor}}/g, ficha.productor)
				.replace(/{{paisOrigen}}/g, ficha.pais)
				.replace(/{{director}}/g, ficha.director)
				.replace(/{{autor}}/g, ficha.autor)
				.replace(/{{califOficialCod}}/g, ficha.califMoralCod ? ficha.califMoralCod : "")
				.replace(/{{califOficiaDesc}}/g, ficha.califMoralDesc ? ficha.califMoralDesc : "")
				.replace(/{{duracion}}/g, ficha.duracion)
				.replace(/{{fechaEstrenoCine}}/g, function() {
					if ("0001-01-01T00:00:00" === ficha.fechaEstrenoCine) {
						return "";
					}
					return $.datepicker.formatDate('dd/mm/yy', $.datepicker.parseDate('yy-mm-dd', ficha.fechaEstrenoCine));
				})
				.replace(/{{fechaEstrenoVideo}}/g,function() {
						if ("0001-01-01T00:00:00" === ficha.fechaEstrenoVideo) {
							return "";
						}
						return $.datepicker.formatDate('dd/mm/yy', $.datepicker.parseDate('yy-mm-dd', ficha.fechaEstrenoVideo));
				})
				.replace(/{{actoresPersonajes}}/g, function() {
					var template = "";
					for ( var i in response.actores) {
						template = template + 
						TrabajarConFichaCinematograficaEvent.TEMPLATES.actores
							.replace(/{{actor}}/g, response.actores[i].actor)
							.replace(/{{personaje}}/g, response.actores[i].personaje);
					}
					return template;
				})
				.replace(/{{sinopsis}}/g, function() {
					var template = "";
					for ( var i in response.sinopsis) {
						template = template + 
						TrabajarConFichaCinematograficaEvent.TEMPLATES.sinopsis
							.replace(/{{sinopsis}}/g, response.sinopsis[i].detalle);
					}
					return template;
				});
			if (self.getSelector("visualizarFicha").length) {
				self.getSelector("visualizarFicha").replaceWith(comp);
			}else{
				self.getDiv().append(comp);
			}
			self.getSelector("accionV").text("Visualización");
			Accordion.getInstance(self.getSelector("visualizarFicha"));
			Accordion.getInstance(self.getSelector("divActoresV"));
			Accordion.getInstance(self.getSelector("divSinopsisV"));
			self.getSelector("volverDeVisualizacion").button().click(self.volverDeVisualizacion);
			self.getSelector("eliminarFicha").hide();
			if (callback) {
				callback();
			}
		});
	});
};
TrabajarConFichaCinematograficaEvent.prototype.volverDeVisualizacion = function() {
	trabajarConFichaCinematograficaEvent.getSelector("visualizarFicha").hide();
	trabajarConFichaCinematograficaEvent.getSelector("divFormInicial").show();
};
TrabajarConFichaCinematograficaEvent.prototype.volverDeAltaModif = function() {
	trabajarConFichaCinematograficaEvent.getSelector("altaModifFicha").hide();
	trabajarConFichaCinematograficaEvent.getSelector("divFormInicial").show();
	if (!Validator.isEmpty(trabajarConFichaCinematograficaEvent.getSelector("tituloBusqueda"))) {
		trabajarConFichaCinematograficaEvent.buscarFichas();
	}
};

TrabajarConFichaCinematograficaEvent.prototype.drawAltaModifFicha = function(callback) {
	Component.get("html/trabajarConFichaCinematografica/altaModifFicha.html", function(comp) {
		var self = trabajarConFichaCinematograficaEvent;
		self.getSelector("divFormInicial").hide();
		comp = comp.replace(/{{id}}/g, self.div.id).replace(/{{accion}}/, self.accion);
		if (self.getSelector("altaModifFicha").length) {
			self.getSelector("altaModifFicha").replaceWith(comp);
		}else{
			self.getDiv().append(comp);
		}
		Accordion.getInstance(self.getSelector("altaModifFicha"));
		Accordion.getInstance(self.getSelector("divActores"));
		Accordion.getInstance(self.getSelector("divSinopsis"));
		self.getSelector("volverDeAltaModif").button().click(self.volverDeAltaModif);
		self.getSelector("aceptarFicha").button().click(self.aceptarAltaModifFicha);
		self.getSelector("anioProduccion").numeric();
		self.getSelector("duracion").numeric();
		self.getSelector("generoCod").numeric();
		Datepicker.getInstance(self.getSelector("fechaEstrenoCine"));
		Datepicker.getInstance(self.getSelector("fechaEstrenoVideo"));
		self.getSelector("popupCalifComercial").click(function() {
		    situarPopupEvent.reset();
			situarPopupEvent.create("trabajarFichaCinematograficaAyudaSituar.action", function(row) {
				self.getSelector("califComercialCod").val(row.codigo);
				self.getSelector("califComercialDesc").text(row.descripcion);
			}, {descripcion : "", ayudaFichas : "CC"}, "listaAyuda");
		});
		self.getSelector("popupCalifArtistica").click(function() {
		    situarPopupEvent.reset();
			situarPopupEvent.create("trabajarFichaCinematograficaAyudaSituar.action", function(row) {
				self.getSelector("califArtisticaCod").val(row.codigo);
				self.getSelector("califArtisticaDesc").text(row.descripcion);
			}, {descripcion : "", ayudaFichas : "CA"}, "listaAyuda");
		});
		self.getSelector("popupGenero").click(function() {
		    situarPopupEvent.reset();
			situarPopupEvent.create("trabajarFichaCinematograficaAyudaSituar.action", function(row) {
				self.getSelector("generoCod").val(row.codigo);
				self.getSelector("generoDesc").text(row.descripcion);
			}, {descripcion : "", ayudaFichas : "GE"}, "listaAyuda");
		});
		self.getSelector("popupCalifMoral").click(function() {
		    situarPopupEvent.reset();
			situarPopupEvent.create("trabajarFichaCinematograficaAyudaSituar.action", function(row) {
				self.getSelector("califMoralCod").val(row.codigo);
				self.getSelector("califMoralDesc").text(row.descripcion);
			}, {descripcion : "", ayudaFichas : "CM"}, "listaAyuda");
		});
		if (callback) {
			callback();
		}
	});
};
TrabajarConFichaCinematograficaEvent.prototype.addInputActor = function() {
	var index = $(".actorCount").length;
	trabajarConFichaCinematograficaEvent.getSelector("divToAppendActores")
	.append(TrabajarConFichaCinematograficaEvent.TEMPLATES.inputAltaActores.replace(/{{index}}/g,index)); 
};
TrabajarConFichaCinematograficaEvent.prototype.removeInputActor = function() {
	if ($(".actorCount").length > 1) {
		var self = trabajarConFichaCinematograficaEvent;
		$('#'+self.div.id+'_divToAppendActores div:last').remove();
		$('#'+self.div.id+'_divToAppendActores div:last').remove();
	}
};
TrabajarConFichaCinematograficaEvent.prototype.addInputSinopsis = function() {
	var index = $(".sinopsisCount").length;
	trabajarConFichaCinematograficaEvent.getSelector("divToAppendSinopsis")
	.append(TrabajarConFichaCinematograficaEvent.TEMPLATES.inputAltaSinopsis.replace(/{{index}}/g,index)); 
};
TrabajarConFichaCinematograficaEvent.prototype.removeInputSinopsis = function() {
	if ($(".sinopsisCount").length > 1) {
		var self = trabajarConFichaCinematograficaEvent;
		$('#'+self.div.id+'_divToAppendSinopsis div:last').remove();
	}
};
TrabajarConFichaCinematograficaEvent.prototype.aceptarAltaModifFicha = function() {
	var self = trabajarConFichaCinematograficaEvent;
	if (self.validFormAltaModif()) {
		var data = self.getSelector("formAltaModif").serialize();
		if (self.accion === TrabajarConFichaCinematograficaEvent.ADD) {
			self.service.guardarFicha(data, self.volverDeAltaModif);
		}else if (self.accion === TrabajarConFichaCinematograficaEvent.UPDATE) {
			self.service.modificarFicha(data, self.volverDeAltaModif);
		}
	}
};
TrabajarConFichaCinematograficaEvent.prototype.validFormAltaModif = function() {
	var castellano = this.getSelector("tituloCastellano");
	var original = this.getSelector("tituloOriginal");
	var color = this.getSelector("color");
	if (Validator.fillAtLeastOneField(castellano, original)) {
		Validator.focus(castellano);
		return false;
	}
	if (Validator.isEmpty(color)) {
		Validator.focus(color);
		return false;
	}
	return true;
};
TrabajarConFichaCinematograficaEvent.prototype.modificarFicha = function(idFicha) {
	var self = trabajarConFichaCinematograficaEvent;
	self.accion = TrabajarConFichaCinematograficaEvent.UPDATE;
	self.fichaSeleccionada = idFicha;
	self.drawAltaModifFicha(function() {
		self.service.getFicha(self.fichaSeleccionada, function(response) {
			var ficha = response.ficha; 
			for ( var key in ficha) {
				self.getSelector(key).val(ficha[key] ? ficha[key] : "");
			}
			self.getSelector("califComercialDesc").text(ficha.califComercialDesc ? ficha.califComercialDesc : "");
			self.getSelector("califArtisticaDesc").text(ficha.califArtisticaDesc ? ficha.califArtisticaDesc : "");
			self.getSelector("califMoralDesc").text(ficha.califMoralDesc ? ficha.califMoralDesc : "");
			self.getSelector("generoDesc").text(ficha.generoDesc ? ficha.generoDesc : "");
			self.getSelector("fechaEstrenoCine").val(function() {
				if ("0001-01-01T00:00:00" === ficha.fechaEstrenoCine) {
					return "";
				}
				return $.datepicker.formatDate('dd/mm/yy', $.datepicker.parseDate('yy-mm-dd', ficha.fechaEstrenoCine));
			});
			self.getSelector("fechaEstrenoVideo").val(function() {
				if ("0001-01-01T00:00:00" === ficha.fechaEstrenoVideo) {
					return "";
				}
				return $.datepicker.formatDate('dd/mm/yy', $.datepicker.parseDate('yy-mm-dd', ficha.fechaEstrenoVideo));
			});
			for ( var i in response.actores) {
				if (i > 2) {
					self.getSelector("divToAppendActores")
					.append(TrabajarConFichaCinematograficaEvent.TEMPLATES.inputAltaActores.replace(/{{index}}/g,i));
				}
				self.getSelector("actor"+i).val(response.actores[i].actor);
				self.getSelector("personaje"+i).val(response.actores[i].personaje);
			}
			for ( var i in response.sinopsis) {
				if (i > 2) {
					self.getSelector("divToAppendSinopsis")
					.append(TrabajarConFichaCinematograficaEvent.TEMPLATES.inputAltaSinopsis.replace(/{{index}}/g,i));
				}
				self.getSelector("sinopsis"+i).val(response.sinopsis[i].detalle);
			}
		});
	});
};
var trabajarConFichaCinematograficaEvent = new TrabajarConFichaCinematograficaEvent(new DivDefinition('TrabajarConFichaCinematograficaEventId'));