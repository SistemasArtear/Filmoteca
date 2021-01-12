/**
 * FP170 - Activar título vencido
 * @author cetorres
 */

ActivarTituloVencidoEvent.CASTELLANO = "castellano";
ActivarTituloVencidoEvent.ORIGINAL = "original";

function ActivarTituloVencidoEvent(div) {
	this.div = div;
};

extend(ActivarTituloVencidoEvent, Plugin);

/***********************************************************************************		
 ********************			Activar título vencido			********************
 ***********************************************************************************/
ActivarTituloVencidoEvent.prototype.create = function() {
	var self = activarTituloVencidoEvent;
	self.service = new ActivarTituloVencidoService();
	Component.get("html/activarTituloVencido/busquedaInicial.html", activarTituloVencidoEvent.draw);
};

ActivarTituloVencidoEvent.prototype.draw = function(comp) {
	var self = activarTituloVencidoEvent;
	$("#viewContainer").empty().append(comp.replace(/{{id}}/g, self.div.id));
	Accordion.getInstance(self.getSelector("accordionBusquedaInicial"));
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
		if (self.getSelector("tipoBusqueda").val() == "C") {
			//self.getSelector("header").html("Activar título vencido");
			self.getSelector("busqueda").html("castellano");
		} else {
			//self.getSelector("header").html("Trabajar con títulos");
			self.getSelector("busqueda").html("original");
		}
		self.getSelector("gridTitulos").clearGridData();
		self.getSelector("titulo").val("");
		self.getSelector("clave").val("");
	});
	
	self.drawGridTitulos();
};

/***********************************************************************************		
 ********************		Validación filtros búsqueda			********************
 ***********************************************************************************/
ActivarTituloVencidoEvent.prototype.validBusquedaInicialForm = function() {
	var titulo = this.getSelector("titulo");
	var clave = this.getSelector("clave");
	if (Validator.fillAtLeastOneField(titulo, clave)) {
		Validator.focus(titulo, "Debe ingresar por lo menos un título o una clave.");
		return false;
	}
	return true;
};

/***********************************************************************************		
 ********************			Búsqueda de títulos				********************
 ***********************************************************************************/
ActivarTituloVencidoEvent.prototype.buscarTitulos = function() {
	var self = activarTituloVencidoEvent;
	if (self.validBusquedaInicialForm()) {
		Seniales.formatearClave(self.getSelector("clave").val());
		var data = self.getSelector("formBusquedaInicial").serialize();
		data += "&buscarTitulosRequest.senial=" + $("#senialDefaultUsuario").val();
		self.service.getTitulos(data, function(response) {
			self.fillGridTitulos(response);
		});
	}
};

/***********************************************************************************		
 ********************			jqGrid de títulos				********************
 ***********************************************************************************/
ActivarTituloVencidoEvent.prototype.drawGridTitulos = function() {
	var self = activarTituloVencidoEvent;
	self.getSelector("gridTitulos").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Clave','Título Castellano','Título Original','Actores','',''],
		colModel:[ 
			{name:'clave',index:'clave', align:'center', width:30, sortable:false}, 
			{name:'tituloCastellano',index:'tituloCastellano', align:'left', width:100, sortable:false}, 
			{name:'tituloOriginal',index:'tituloOriginal', align:'left', width:100, sortable:false}, 
			{name:'actores',index:'actores', align:'left', width:130, sortable:false}, 
			{name:'signo',index:'signo', align:'center', width:10, sortable:false}, 
			{name:'activar',index:'activar', align:'center', width:10, sortable:false} 
		],
		rowNum: 20,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#ActivarTituloVencidoEventEventId_pagerGridTitulos',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Titulos',
		gridComplete: function() {
        	var ids = $(this).jqGrid('getDataIDs');
        	for (var i = 0; i < ids.length; i++) {
        		var rowId = ids[i];
				var activar = "<center><div onclick='activarTituloVencidoEvent.activarTituloEnContrato("+rowId+")' style='display:inline-table; cursor:pointer;'><span class='ui-icon ui-icon-radio-on activarTitulo conTooltip'>Activar título en contrato</span></div></center>";
				$(this).jqGrid('setRowData', rowId, { activar: activar });
        	}
	    }
	});
};

ActivarTituloVencidoEvent.prototype.fillGridTitulos = function(data) {
	var self = activarTituloVencidoEvent;
	self.getSelector("gridTitulos").clearGridData().setGridParam({data: data}).trigger('reloadGrid');
};

/***********************************************************************************		
 ********************		Situar contratos del títulos		********************
 ***********************************************************************************/
ActivarTituloVencidoEvent.prototype.activarTituloEnContrato = function(rowId) {
	var self = activarTituloVencidoEvent;
	self.tituloSeleccionado = self.getSelector("gridTitulos").jqGrid('getRowData', rowId);
	var colNames = ['Número', 'Fecha', 'Desde', 'Hasta', 'Estado', '', ''];
	var colModel = [
        {index:'numero', 		name:'numero',		  align: 'center', sorttype: 'number'},
        {index:'fecha', 		name:'fecha',		  align: 'center', sortable: false,	formatter:'date', formatoptions : {newformat: 'd/m/Y'}},  
        {index:'vigenciaDesde', name:'vigenciaDesde', align: 'center', sortable: false},
        {index:'vigenciaHasta', name:'vigenciaHasta', align: 'center', sortable: false},
        {index:'estado', 		name:'estado', 		  align: 'center', sortable: false},
        {index:'grupo',         name:'grupo',         align: 'center', sortable: false,  hidden:true},
        {index:'razonSocialDistribuidor', 	name:'razonSocialDistribuidor',  hidden:true}
    ];
	situarPopupEvent.reset();
	situarPopupEvent.setColumns(colNames, colModel);
	
	situarPopupEvent.nombreSituar = "Seleccionar contrato para el título " + self.tituloSeleccionado.clave;
	situarPopupEvent.doAfterDrawGrid = function() {
		var groupHeaders = [
    	    {startColumnName: 'numero', 		numberOfColumns: 2, titleText: 'Contrato'}, 
    	    {startColumnName: 'vigenciaDesde', 	numberOfColumns: 2, titleText: 'Fecha vigencia'} 
    	];
    	situarPopupEvent.getGrid().jqGrid('setGroupHeaders', { useColSpanStyle: true, groupHeaders: groupHeaders});
		situarPopupEvent.getGrid().sortGrid('numero', true);
	};
	situarPopupEvent.create("activarTituloVencidoBuscarContratosParaElTitulo.action", self.validarContratoParaElTitulo, {senial : $("#senialDefaultUsuario").val(), clave : self.tituloSeleccionado.clave}, "contratosParaTitulo", true);
};

/***********************************************************************************		
 ********************		Validación contrato seleccionado	********************
 ***********************************************************************************/
ActivarTituloVencidoEvent.prototype.validarContratoParaElTitulo = function(contrato) {
	var self = activarTituloVencidoEvent;
	if (contrato) {
	    self.contratoSeleccionado = contrato;
	}
	var data = {contrato : self.contratoSeleccionado.numero, clave : self.tituloSeleccionado.clave, senial : $("#senialDefaultUsuario").val()};
    activarTituloVencidoEvent.contrato = data.contrato; 
    activarTituloVencidoEvent.clave = data.clave; 
    activarTituloVencidoEvent.senial = data.senial;
    activarTituloVencidoEvent.grupo = self.contratoSeleccionado.grupo; 
	self.service.validarContratoParaElTitulo(data, self.validacionContratoParaElTituloResponse);
};

ActivarTituloVencidoEvent.prototype.validacionContratoParaElTituloResponse = function() {
	var self = activarTituloVencidoEvent;
	Component.get("html/activarTituloVencido/modifVigenciaContrato.html", function(comp) {
		self.getSelector("busquedaInicial").hide();
		comp = comp.replace(/{{id}}/g, self.div.id).replace(/{{nroContrato}}/g, self.contratoSeleccionado.numero)
		.replace(/{{senial}}/g, $("#senialDefaultUsuario").val()).replace(/{{distribuidor}}/g, self.contratoSeleccionado.razonSocialDistribuidor)
		.replace(/{{clave}}/, self.tituloSeleccionado.clave + " " + self.tituloSeleccionado.tituloCastellano + " / " + self.tituloSeleccionado.tituloOriginal)
		.replace(/{{estado}}/g, self.contratoSeleccionado.estado).replace(/{{vigenciaTipoDerecho}}/, self.contratoSeleccionado.vigenciaDesde + " " + self.contratoSeleccionado.vigenciaHasta);
		if (self.getSelector("modifVigenciasDiv").length) {
			self.getSelector("modifVigenciasDiv").replaceWith(comp);
		} else{
			self.getDiv().append(comp);
		}
		Accordion.getInstance(self.getSelector("accordionModifVigencias"));
		self.getSelector("payTVButton").button().click(self.payTV);
		self.getSelector("ampDerechosButton").button().click(self.ampliacionDerechos);
		self.getSelector("volverAPrimerPantallaButton").button().click(function() {
			self.getSelector("modifVigenciasDiv").hide();
			self.getSelector("busquedaInicial").show();
		});
		var data = {contrato : self.contratoSeleccionado.numero, clave : self.tituloSeleccionado.clave, senial : $("#senialDefaultUsuario").val()};
		self.service.buscarVigenciaContratos(data,[self.drawCabeceraContratos, self.drawGridVigenciaContratos]);
	});
};

/***********************************************************************************		
 ********************		Modificación vigencias contratos	********************
 ***********************************************************************************/
ActivarTituloVencidoEvent.prototype.drawCabeceraContratos = function(data) {
	var self = activarTituloVencidoEvent;
	self.getSelector("nroContratoLabel").html(data.contrato);
	self.getSelector("distribuidorLabel").html(data.razonSocial);
	self.getSelector("claveLabel").html(data.clave + " " + data.tituloCast + " / " + data.tituloOrig);
	self.getSelector("estadoLabel").html(data.estadoStandBy);
	var fechaRealDesde = $.datepicker.formatDate('dd/mm/yy', new Date(data.fechaRealDesde));
	var fechaRealHasta = $.datepicker.formatDate('dd/mm/yy', new Date(data.fechaRealHasta));
	self.getSelector("vigenciaTipoDerecho").html(fechaRealDesde + " " + fechaRealHasta + " " + data.tipoDerecho);
};

ActivarTituloVencidoEvent.prototype.drawGridVigenciaContratos = function(data) {
	var self = activarTituloVencidoEvent;
	self.getSelector("gridVigenciaContratos").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Tipo Modif. Vigencia','Desde','Hasta', 'Anu','Desde','Hasta','Desde','Hasta','Observaciones'],
		colModel:[ 
			{name:'tipoModifVigencia',	index:'tipoModifVigencia', 	align:'center', sortable:false, formatter:function(value, options, rData) { return rData['tipoModifVigencia'] + " - " + rData['descModifVigencia'];}},
			{name:'fechaDesdePaytv',	index:'fechaDesdePaytv', 	align:'center', sortable:false, formatter:function(value, options, rData) { 
				if (value != undefined && value != "0001-01-01T00:00:00") {
					return $.datepicker.formatDate('dd/mm/yy', new Date(value));
				} else {
					return "";
				}
			}},
			{name:'fechaHastaPaytv',	index:'fechaHastaPaytv', 	align:'center', sortable:false,	formatter:function(value, options, rData) { 
				if (value != undefined && value != "0001-01-01T00:00:00") {
					return $.datepicker.formatDate('dd/mm/yy', new Date(value));
				} else {
					return "";
				}
			}}, 
			{name:'idPaytvAnulado',		index:'idPaytvAnulado', 	align:'center', sortable:false, formatter:function(value, options, rData) { return value != 0 ? value : "";}}, 
			{name:'fechaAntDesde',		index:'fechaAntDesde', 		align:'center', sortable:false,	formatter:function(value, options, rData) { 
				if (value != undefined && value != "0001-01-01T00:00:00") {
					return $.datepicker.formatDate('dd/mm/yy', new Date(value));
				} else {
					return "";
				}
			}},
			{name:'fechaAntHasta',		index:'fechaAntHasta', 		align:'center', sortable:false,	formatter:function(value, options, rData) { 
				if (value != undefined && value != "0001-01-01T00:00:00") {
					return $.datepicker.formatDate('dd/mm/yy', new Date(value));
				} else {
					return "";
				}
			}}, 
			{name:'fechaNuevaDesde',	index:'fechaNuevaDesde',	align:'center', sortable:false,	formatter:function(value, options, rData) { 
				if (value != undefined && value != "0001-01-01T00:00:00") {
					return $.datepicker.formatDate('dd/mm/yy', new Date(value));
				} else {
					return "";
				}
			}},
			{name:'fechaNuevaHasta',	index:'fechaNuevaHasta', 	align:'center', sortable:false,	formatter:function(value, options, rData) { 
				if (value != undefined && value != "0001-01-01T00:00:00") {
					return $.datepicker.formatDate('dd/mm/yy', new Date(value));
				} else {
					return "";
				}
			}},
			{name:'observaciones',		index:'observaciones', 		align:'center', sortable:false}
		],
		rowNum: 20,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#ActivarTituloVencidoEventEventId_pagerGridVigenciaContratos',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Datos'
	});
	self.getSelector("gridVigenciaContratos").clearGridData().setGridParam({data: data}).trigger('reloadGrid');
	var groupHeaders = [
	    {startColumnName: 'fechaDesdePaytv', 	numberOfColumns: 2, titleText: 'Fecha PayTV'}, 
	    {startColumnName: 'fechaAntDesde', 		numberOfColumns: 2, titleText: 'Fecha Anterior'},
	    {startColumnName: 'fechaNuevaDesde', 	numberOfColumns: 2, titleText: 'Fecha Nueva'} 
	];
	self.getSelector("gridVigenciaContratos").jqGrid('setGroupHeaders', { useColSpanStyle: true, groupHeaders: groupHeaders}).trigger('reloadGrid');
};
ActivarTituloVencidoEvent.prototype.procesarPayTV = function(request, modo, skipWarnings) {
    activarTituloVencidoEvent.procesarPayTV.req = request;
    activarTituloVencidoEvent.procesarPayTV.modo = modo;
    ModificarContratoStaticService.doRequest(
            {action:"procesarPayTV.action",
             request: Component.serialize(activarTituloVencidoEvent.procesarPayTV.req, "vigenciaRequest") + ( skipWarnings ? "&skipWarnings=" + skipWarnings : "" ),
             method: "GET",
             callback: function(data) {
                     if (data && (data.length != undefined)) {
                         var hayErrores = false;
                         var hayWarnings = false;
                         var warnings  = new Array();
                         var errores  = new Array();
                         $.each(data, function(i, l) {
                             if (l["tipo"] === "E") {
                                 hayErrores = true;
                                 errores.push(l["descripcion"]);
                             } else if (l["tipo"] === "W") {
                                 hayWarnings = true;
                                 warnings.push(l["descripcion"]);
                             }
                         });
                         if (hayErrores) {
                             MESSAGE.error(errores[0]);
                         } else if (hayWarnings){
                             popupConfirmacionEvent.confirmar = function () {
                                 activarTituloVencidoEvent.procesarPayTV(activarTituloVencidoEvent.procesarPayTV.req, activarTituloVencidoEvent.procesarPayTV.req.modo, true);
                                 popupConfirmacionEvent.remove();
                             };
                             popupConfirmacionEvent.cancelar = function () {
                                 popupConfirmacionEvent.remove();
                             };
                             popupConfirmacionEvent.afterDraw = function () {
                                 return;
                             };
                             popupConfirmacionEvent.popTitle = 'WARNINGS';
                             popupConfirmacionEvent.create("warningsPopUp", warnings.join("<br/>"));
                         } else {
                             MESSAGE.ok("PayTV procesado");
                             activarTituloVencidoEvent.getSelector("popupPayTVDiv").dialog( "close" );
                             activarTituloVencidoEvent.getSelector("popupPayTVDiv").remove();
                             activarTituloVencidoEvent.validarContratoParaElTitulo();
                         }
                     }
            },
            responseObject: "validacionModificacionContrato"});
};
ActivarTituloVencidoEvent.prototype.payTV = function() {
	var self = activarTituloVencidoEvent;
	Component.get("html/activarTituloVencido/popupPayTV.html", function(comp) {
		comp = comp.replace(/{{id}}/g, self.div.id);
		self.getSelector("modifVigenciasDiv").append(comp);
		Datepicker.getInstance(self.getSelector("fechaDesdePayTV"));
		Datepicker.getInstance(self.getSelector("fechaHastaPayTV"));
		self.getSelector("popupPayTVDiv").dialog({
			title : 'Alta Pay-TV',
			width: 600,
			show: 'blind',
			hide: 'blind',
			modal: true,
			autoOpen: true,
			position: 'center',
			buttons: {
				"Salir": function() {
					$( this ).dialog( "close" );
					self.getSelector("popupPayTVDiv").remove();
					$( this ).remove();
				},
				"Aceptar": function() {
				    activarTituloVencidoEvent.procesarPayTV.req = 
				                    {contrato: activarTituloVencidoEvent.contrato, 
				                     clave: activarTituloVencidoEvent.clave, 
				                     grupo: activarTituloVencidoEvent.grupo, 
				                     senial: activarTituloVencidoEvent.senial,
                                     payTVId: 0,
				                     fechaDesdePayTV: $("#ActivarTituloVencidoEventEventId_fechaDesdePayTV").val(),
				                     fechaHastaPayTV: $("#ActivarTituloVencidoEventEventId_fechaHastaPayTV").val(),
				                     observaciones: $("#ActivarTituloVencidoEventEventId_observacionPayTV").val()};
			        activarTituloVencidoEvent.procesarPayTV.modo = "A";
			        activarTituloVencidoEvent.procesarPayTV(activarTituloVencidoEvent.procesarPayTV.req, activarTituloVencidoEvent.procesarPayTV.modo, false);
				}
			}
		});
	});
};

ActivarTituloVencidoEvent.prototype.ampliacionDerechos = function(skipWarnings) {
	var self = activarTituloVencidoEvent;
	Component.get("html/activarTituloVencido/popupAmpliacionDerechos.html", function(comp) {
		comp = comp.replace(/{{id}}/g, self.div.id);
		self.getSelector("modifVigenciasDiv").append(comp);
		Datepicker.getInstance(self.getSelector("fechaHastaAmpDerechos"));
		self.getSelector("popupAmpliacionDerechosDiv").dialog({
			title : 'Ampliación derechos',
			width: 600,
			show: 'blind',
			hide: 'blind',
			modal: true,
			autoOpen: true,
			position: 'center',
			buttons: {
				"Salir": function() {
					$( this ).dialog( "close" );
					self.getSelector("popupAmpliacionDerechosDiv").remove();
					$( this ).remove();
				},  
				"Aceptar": function() {
				    activarTituloVencidoEvent.ampliacionDerechos.request =  
                                                {contrato: activarTituloVencidoEvent.contrato, 
				                                 clave: activarTituloVencidoEvent.clave, 
				                                 grupo: activarTituloVencidoEvent.grupo, 
				                                 senial: activarTituloVencidoEvent.senial,
				                                 fechaHastaPayTV: $("#ActivarTituloVencidoEventEventId_fechaHastaAmpDerechos").val(),
				                                 observaciones: $("#ActivarTituloVencidoEventEventId_observacionAmpDerechos").val()};
			        ModificarContratoStaticService.doRequest(
			                 {action:"ampliarDerechos.action",
			                  request: Component.serialize(activarTituloVencidoEvent.ampliacionDerechos.request, "vigenciaRequest") + ( skipWarnings ? "&skipWarnings=" + skipWarnings : "" ),
			                  method: "GET",
			                  callback: function(data) {
			                      if (data && data.length != undefined) {
			                          var hayErrores = false;
			                          var hayWarnings = false;
			                          var warnings  = new Array();
			                          var errores  = new Array();
			                          $.each(data, function(i, l) {
			                              if (l["tipo"] === "E") {
			                                  hayErrores = true;
			                                  errores.push(l["descripcion"] !== "" ? l["descripcion"] : "Error al ampliar derechos");
			                              } else if (l["tipo"] === "W") {
			                                  hayWarnings = true;
			                                  warnings.push(l["descripcion"]);
			                              }
			                          });
			                          if (hayErrores) {
			                              MESSAGE.error(errores[0], 15000);
			                          } else if (hayWarnings){
			                              popupConfirmacionEvent.confirmar = function () {
			                                  ModificarContratoStaticService.doRequest(
			                                          {action:"ampliarDerechos.action",
			                                           request: Component.serialize(activarTituloVencidoEvent.ampliacionDerechos.request, "vigenciaRequest") + "&skipWarnings=true",
			                                           method: "GET",
			                                           callback: function(data) {
			                                               MESSAGE.ok("Derechos ampliados");
			                                               activarTituloVencidoEvent.getSelector("popupAmpliacionDerechosDiv").dialog( "close" );
			                                               activarTituloVencidoEvent.getSelector("popupAmpliacionDerechosDiv").remove();
			                                               activarTituloVencidoEvent.validarContratoParaElTitulo();
			                                           } });
			                                  popupConfirmacionEvent.remove();
			                              };
			                              popupConfirmacionEvent.cancelar = function () {
			                                  popupConfirmacionEvent.remove();
			                              };
			                              popupConfirmacionEvent.afterDraw = function () {
			                                  return;
			                              };
			                              popupConfirmacionEvent.popTitle = 'WARNINGS';
			                              popupConfirmacionEvent.create("warningsPopUp", warnings.join("<br/>"));
			                          } else {
			                              MESSAGE.ok("Derechos ampliados");
			                              activarTituloVencidoEvent.getSelector("popupAmpliacionDerechosDiv").dialog( "close" );
			                              activarTituloVencidoEvent.getSelector("popupAmpliacionDerechosDiv").remove();
			                              activarTituloVencidoEvent.validarContratoParaElTitulo();
			                          }
			                      } else {
			                          MESSAGE.error("Ocurrio un error al actualizar los derechos");
			                      }
			                  },
			                  responseObject: "validacionModificacionContrato"});
				}
			}
		});
	});
};

/* Initialize */
var activarTituloVencidoEvent = new ActivarTituloVencidoEvent(new DivDefinition('ActivarTituloVencidoEventEventId'));