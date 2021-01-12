/***********************************************************************************		
 ********************				Pantalla FP11010/11			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirVisualizarTitulosGrupo = function() {
	var self = altaContratoEvent;
	Component.get("html/altaContrato/visualizarTitulosGrupo.html", self.drawVisualizarTitulosGrupo);
};

AltaContratoEvent.prototype.drawVisualizarTitulosGrupo = function(comp) {
	var self = altaContratoEvent;
	comp = comp.replace(/{{id}}/g, self.div.id);
	
	if (self.getSelector("formVisualizarTitulosGrupo").length) {
		self.getSelector("formVisualizarTitulosGrupo").replaceWith(comp);
	} else {
		self.getDiv().append(comp);
	}
	/* Accordion */
	Accordion.getInstance(self.getSelector("accordionVisualizarTitulosGrupo"));
	
	var esTituloCC = self.determinarFamiliaTitulo(self.contrato.montoSenialTT.tipoTitulo);
	
	/* Inicializo el contador de títulos ingresados */
	if (self.contrato.montoSenialTT.titulosIngresados == null) {
		self.contrato.montoSenialTT.titulosIngresados = 0;
	}
	
	if (esTituloCC) {
		self.getSelector("formCargaGrupoCC").hide();
		self.getSelector("nroGrupoVisualizarTitulosGrupo").html(self.contrato.grupoCC.nroGrupo);
		self.getSelector("estrenoVisualizarTitulosGrupo").html(self.contrato.grupoCC.estrenoRepeticion == "E" ? "Estreno" : "Repetición");
		self.getSelector("nombreGrupoVisualizarTitulosGrupo").html(self.contrato.grupoCC.nombreGrupo);
		self.getSelector("tituloBusquedaVisualizarTitulosGrupo").val(self.contrato.grupoCC.nombreGrupo);
		self.getSelector("ingresadasVisualizarTitulosGrupo").html("1 de 1");
	} else {
		self.getSelector("formCargaGrupoSC").hide();
		self.getSelector("nroGrupoVisualizarTitulosGrupo").html(self.contrato.grupoSC.nroGrupo);
		self.getSelector("estrenoVisualizarTitulosGrupo").html(self.contrato.grupoSC.estrenoRepeticion == "E" ? "Estreno" : "Repetición");
		self.getSelector("nombreGrupoVisualizarTitulosGrupo").html(self.contrato.grupoSC.nombreGrupo);
		self.getSelector("ingresadasVisualizarTitulosGrupo").html(self.contrato.montoSenialTT.titulosIngresados + " de " + self.contrato.grupoSC.cantidadTitulos);
	}
	
	self.getSelector("senialVisualizarTitulosGrupo").html(self.contrato.montoSenialTT.senial);
	self.getSelector("distribuidorVisualizarTitulosGrupo").html(self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("distribuidorVisualizarTitulosGrupo").attr("title", self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("montoVisualizarTitulosGrupo").html(self.contrato.montoSenialTT.monto);
	self.getSelector("tipoTituloVisualizarTitulosGrupo").html(self.contrato.montoSenialTT.tipoTitulo);
	
	/* Botonera */
	self.getSelector("agregarTituloVisualizarTitulosGrupo").button().click(function() {
		if (Validator.isEmpty(self.getSelector("tituloBusquedaVisualizarTitulosGrupo"))) {
			Validator.focus(self.getSelector("tituloBusquedaVisualizarTitulosGrupo"), "Debe ingresar un título");		
		} else {
			/* Inicializo las variables */
			var grupo = {};
			var titulo = {};
			/* Determino con que familia de títulos estoy trabajando para buscar el grupo */
			var esTituloCC = self.determinarFamiliaTitulo(self.contrato.montoSenialTT.tipoTitulo);
			if (esTituloCC) {
				grupo = self.recuperarDatosGrupo(self.contrato.grupoCC.nroGrupo);	
			} else {
				grupo = self.recuperarDatosGrupo(self.contrato.grupoSC.nroGrupo);
			}
			/* Inicializo el contador de títulos ingresados */
			if (self.contrato.montoSenialTT.titulosIngresados == null) {
				self.contrato.montoSenialTT.titulosIngresados = 0;
			}
			/* Asigno lo ingresado en el input de búsqueda al nuevo título */
			titulo.titulo = self.getSelector("tituloBusquedaVisualizarTitulosGrupo").val();
			self.getSelector("tituloBusquedaVisualizarTitulosGrupo").val("");
			/* Abrir FP11012 */
			var params = {grupo: grupo, titulo: titulo, descripcionTitulo: self.getSelector("tituloBusquedaVisualizarTitulosGrupo").val()};
			self.abrirSeleccionTitulo(params);
		}
	});
	self.getSelector("buscarTitulosGrupoVisualizarTitulosGrupo").button().click(self.buscarTitulosGrupo);
	self.getSelector("cambiarOrdenVisualizarTitulosGrupo").click(self.cambiarTipoBusquedaTitulosGrupo);
	self.drawGridTitulosGrupo();
};

AltaContratoEvent.prototype.cambiarTipoBusquedaTitulosGrupo = function() {
	var self = altaContratoEvent;
	var tipoBusqueda = (self.getSelector("tipoBusquedaVisualizarTitulosGrupo").html() == "castellano" ? "original" : "castellano");
	self.getSelector("tipoBusquedaVisualizarTitulosGrupo").html(tipoBusqueda);
	self.getSelector("gridTitulosGrupo").clearGridData();
};

AltaContratoEvent.prototype.drawGridTitulosGrupo = function() {
	var self = altaContratoEvent;
	self.getSelector("gridTitulosGrupo").jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:['Clave', 'Título', '', 'Actores', 'Sin contrato', ''],
		colModel:[ 
		    {name:'clave', 		 		 index:'clave', 	  align:'center', width: 40, 
		    	formatter:function(value, options, rData) { 
		    		if (/^\d+$(\.\d+)?$/.test(value)) {
		    			return "<span style='visibility: hidden'>" + value  + "</span>";	
		    		}  else {
		    			return value;
		    		}
		    	}
		    },
		    {name:'titulo', 	 		 index:'titulo', 	  		align:'left', 
		    	formatter:function(value, options, rData) {
		    		if (self.getSelector("tipoBusquedaVisualizarTitulosGrupo").html() == "castellano") {
		    			return Component.trimWhitespace(rData['tituloCastellano']);
		    		} else {
		    			return Component.trimWhitespace(rData['tituloOriginal']);	
		    		}
		    	}
		    },
		    {name:'tituloAlternativo', 	 index:'tituloAlternativo', hidden:true, 
		    	formatter:function(value, options, rData) {
		    		if (self.getSelector("tipoBusquedaVisualizarTitulosGrupo").html() == "castellano") {
		    			return Component.trimWhitespace(rData['tituloOriginal']);	
		    		} else {
		    			return Component.trimWhitespace(rData['tituloCastellano']);
		    		}
		    	}
		    },
		    {name:'actores', 	 		 index:'actores',	  align:'left', 
		    	formatter:function(value, options, rData) { 
			    	var primerActor = "";
			    	if (rData['primerActor'] != null && rData['primerActor'].trim() != "") {
			    		primerActor = Component.trimWhitespace(rData['primerActor']);
			    	} 
			    	var segundoActor = "";
			    	if (rData['segundoActor'] != null && rData['segundoActor'].trim() != "") {
			    		segundoActor = Component.trimWhitespace(rData['segundoActor']); 
			    	}
			    	var separador = "";
			    	if (primerActor != "" && segundoActor != "") {
			    		separador = " / ";
			    	}
			    	return primerActor + separador + segundoActor;
			    }
		    },
		    {name:'sinContrato', 		 index:'sinContrato', align:'center', width: 50, 
		    	formatter:function(value, options, rData) { 
		    		if (value === "S") {
		    			return "<div style='display:inline-table'><span class='ui-icon ui-icon-circle-check conTooltip'></span></div>";	
		    		}  else {
		    			return "";
		    		}
		    	}
		    },
		    {name:'seleccionar', 		 index:'seleccionar', align:'center', width: 20}	
		],
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#' + self.div.id + '_pagerTitulosGrupo',
		viewrecords: true, 
		loadonce: true,
		editurl: 'clientArray', 
		caption: 'Títulos',
		gridComplete: function() { 							
			var ids = $(this).jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var seleccionar = "<div title='Seleccionar' onclick='altaContratoEvent.validarAperturaPantallaSeleccionTituloGrupo(" + ids[i] + ");' style='cursor:pointer; display:inline-table'><span class='ui-icon ui-icon-check conTooltip'></span></div>";
				$(this).jqGrid('setRowData', ids[i], { seleccionar: seleccionar });
			}
		}
	});
	
	/* Grilla con tamaño automático */
	Component.resizableGrid(self.getSelector("gridTitulosGrupo"));
};

AltaContratoEvent.prototype.buscarTitulosGrupo = function() {
	/* Bloquear pantalla */
	BLOCK.showBlock("Buscando títulos");
	
	var self = altaContratoEvent;
	
	var buscarTitulosGrupoRequest = {
		senial: self.contrato.montoSenialTT.senial,
		descripcion: self.getSelector("tituloBusquedaVisualizarTitulosGrupo").val(),
		tipoTitulo: self.contrato.montoSenialTT.tipoTitulo,
		tipoBusqueda: (self.getSelector("tipoBusquedaVisualizarTitulosGrupo").html() == "castellano" ? "CAST" : "ORIG")
	};
	
	var data = {
		async: false,
		action:"altaContratoBuscarTitulosGrupo.action", 
		request: Component.serialize(buscarTitulosGrupoRequest, "buscarTitulosGrupoRequest"),
		method: "GET", 
		responseObject: "titulosGrupo", 
		callback: function(titulosGrupo) {
			/*Cargo la grilla */
			self.getSelector("gridTitulosGrupo").clearGridData().setGridParam({data: titulosGrupo}).trigger('reloadGrid');
			/* Desbloquear pantalla */
			BLOCK.hideBlock();
		}
	};
	setTimeout(function() {
		self.service.doRequest(data);	
	}, 500);
};

AltaContratoEvent.prototype.validarAperturaPantallaSeleccionTituloGrupo = function(idTitulo) {
	var self = altaContratoEvent;
	
	/* Recupero el título seleccionado */
	var titulo = self.getSelector("gridTitulosGrupo").jqGrid('getRowData', idTitulo);

	/* Determinar el origen: ficha (F) o título (T) */
	titulo.origen = (/<span style="visibility: hidden">/.test(titulo.clave) ? "F" : "T");
	/* Extraer clave título o clave ficha cinematográfica */
	titulo.clave = /[a-zA-Z]{0,2}\d{1,7}/.exec(titulo.clave)[0];
	
	if (self.validarPerpetuidadTitulo(titulo.clave)) {
		MESSAGE.alert("El título elegido es a perpetuidad y no tiene sentido recontratar");
		return;
	}
	
	if (titulo.sinContrato != "" && (self.contrato.grupoCC.recontratacion == "S")){
		MESSAGE.alert("No puede seleccionar este titulo por ser una recontratación");
		return;
	}

	var esTituloCC = self.determinarFamiliaTitulo(self.contrato.montoSenialTT.tipoTitulo);
	self.getSelector("tituloBusquedaVisualizarTitulosGrupo").val("");
	var grupo = new Object();
	if (esTituloCC) {
		grupo = self.recuperarDatosGrupo(self.contrato.grupoCC.nroGrupo);	
	} else {
		grupo = self.recuperarDatosGrupo(self.contrato.grupoSC.nroGrupo);
	}
	/* Inicializo el contador de títulos ingresados */
	if (self.contrato.montoSenialTT.titulosIngresados == null) {
		self.contrato.montoSenialTT.titulosIngresados = 0;
	}
	var params = {grupo: grupo, titulo: titulo, descripcionTitulo: self.getSelector("tituloBusquedaVisualizarTitulosGrupo").val()};
	
	if (esTituloCC && grupo.recontratacion == 'S' && grupo.estrenoRepeticion == 'R') {
		/* Abrir FP11013 */
		self.abrirSeleccionTituloRecontratacion(params);
	} else {
		/* Abrir FP11012 */
		self.abrirSeleccionTitulo(params);
	}
};

/***********************************************************************************		
 ********************				Pantalla FP11063			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.validarPerpetuidadTitulo = function(clave) {
	var self = altaContratoEvent;
	
	var esTituloCC = self.determinarFamiliaTitulo(self.contrato.montoSenialTT.tipoTitulo);
	
	var validarPerpetuidadTituloRequest = {
		contrato: self.contrato.numero,
		grupo: (esTituloCC ? self.contrato.grupoCC.nroGrupo : self.contrato.grupoSC.nroGrupo),
		senial: self.contrato.montoSenialTT.senial,
		clave: clave
	};
	
	var esValido = new Boolean();
	
	var data = {
		async: false,
		action:"altaContratoValidarPerpetuidadTitulo.action", 
		request: Component.serialize(validarPerpetuidadTituloRequest, "validarPerpetuidadTituloRequest"),
		method: "GET", 
		responseObject: "resultado", 
		callback: function(isTituloValido) {
			esValido = isTituloValido;
		}
	};
	self.service.doRequest(data);
	
	return esValido;
};

/* FIXME: revisar*/
AltaContratoEvent.prototype.volverVisualizarTitulosGrupo = function() {
	var self = altaContratoEvent;
	/* Limpio los datos viejos */ 
	self.contrato.montoSenialTT = new MontoSenialTTPOJO();
	self.contrato.grupoCC = new GrupoCCPOJO();
	self.contrato.grupoSC = new GrupoSCPOJO();
	self.getSelector("formVisualizarTitulosGrupo").hide();
	self.getSelector("formCargaContrato").show();
};

/***********************************************************************************		
 ********************				Pantalla FP11012			********************
 ***********************************************************************************/
AltaContratoEvent.prototype.abrirSeleccionTitulo = function(params) {
	var self = altaContratoEvent;
	Component.get("html/altaContrato/seleccionTitulo.html", function(comp) {
		self.drawSeleccionTitulo(comp, params);
	});
};

AltaContratoEvent.prototype.drawSeleccionTitulo = function(comp, params) {
	var self = altaContratoEvent;
	comp = comp.replace(/{{id}}/g, self.div.id);
	
	self.getSelector("formVisualizarTitulosGrupo").hide();

	if (self.getSelector("formSeleccionTitulo").length) {
		self.getSelector("formSeleccionTitulo").replaceWith(comp);
	} else {
		self.getDiv().append(comp);
	}
	/* Accordion */
	Accordion.getInstance(self.getSelector("accordionSeleccionTitulo"));
	/* Datos grupo */
	self.getSelector("senialSeleccionTitulo").html(self.contrato.montoSenialTT.senial);
	self.getSelector("distribuidorSeleccionTitulo").html(self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("distribuidorSeleccionTitulo").attr("title", self.contrato.cabecera.proveedor + " " + self.contrato.cabecera.razonSocial);
	self.getSelector("montoSeleccionTitulo").html(self.contrato.montoSenialTT.monto);
	var esTituloCC = self.determinarFamiliaTitulo(self.contrato.montoSenialTT.tipoTitulo);
	self.getSelector("nroGrupoSeleccionTitulo").html(esTituloCC ? self.contrato.grupoCC.nroGrupo : self.contrato.grupoSC.nroGrupo);
	self.getSelector("tipoTituloSeleccionTitulo").html(self.contrato.montoSenialTT.tipoTitulo);
	
	if (esTituloCC) {
		self.getSelector("estrenoSeleccionTitulo").html(self.contrato.grupoCC.estrenoRepeticion == "E" ? "Estreno" : "Repetición");
		self.contrato.grupoCC.tipoDerecho = params.grupo.tipoDerecho;
		/* Datepicker - Fecha posible entrega */
		Datepicker.getInstance(self.getSelector("fechaPosibleEntregaSeleccionTitulo"));
		self.getSelector("fechaPosibleEntregaSeleccionTitulo").show();
		self.contrato.grupoCC.cantidadOriginales = params.grupo.cantidadOriginales;
		self.contrato.grupoCC.marcaPerpetuidad = params.grupo.marcaPerpetuidad;
		self.getSelector("ingresadasSeleccionTitulo").html("1 de 1");
	} else {
		self.getSelector("estrenoSeleccionTitulo").html(self.contrato.grupoSC.estrenoRepeticion == "E" ? "Estreno" : "Repetición");
		self.contrato.grupoSC.tipoDerecho = params.grupo.tipoDerecho;
		self.contrato.grupoSC.cantidadTitulos = params.grupo.cantidadTitulos;
		self.contrato.grupoSC.marcaPerpetuidad = params.grupo.marcaPerpetuidad;
		self.getSelector("ingresadasSeleccionTitulo").html((self.contrato.montoSenialTT.titulosIngresados + 1 ) + " de " + self.contrato.grupoSC.cantidadTitulos);
	}
	self.getSelector("nombreGrupoSeleccionTitulo").html(esTituloCC ? self.contrato.grupoCC.nombreGrupo : self.contrato.grupoSC.nombreGrupo);
	self.getSelector("recontratacionSeleccionTitulo").html(esTituloCC ? self.contrato.grupoCC.recontratacion : "");
	
	/* Habilitar / deshabilitar campos */
	if (params.titulo.clave) {
		var readOnlyFields = [ 
			self.getSelector("claveSeleccionTitulo"),
			self.getSelector("tituloCastellanoSeleccionTitulo"),
			self.getSelector("tituloOriginalSeleccionTitulo"),
			self.getSelector("actorUnoSeleccionTitulo"),
			self.getSelector("actorDosSeleccionTitulo"),
			self.getSelector("recontratacionSeleccionTitulo")
			//self.getSelector("calificacionOficialSeleccionTitulo")
		];
		self.turnOnReadOnly(readOnlyFields);
		/* Clave */
		self.getSelector("claveSeleccionTitulo").html(params.titulo.clave);
		if (params.titulo.origen == "F") {
			self.getSelector("claveSeleccionTitulo").attr("style", "visibility: hidden");	
		} else {
			self.getSelector("claveSeleccionTitulo").attr("style", "visibility: visible");
		}
		/* Actores */
		if (params.titulo.actores != undefined && params.titulo.actores != "") {
			var actores = params.titulo.actores.split(" / ");
			self.getSelector("actorUnoSeleccionTitulo").val(actores[0] != undefined ? actores[0] : "");
			self.getSelector("actorDosSeleccionTitulo").val(actores[1] != undefined ? actores[1] : "");
		}
	} else {
		/* Obtener clave desde pl porque es un título completamente nuevo */
		var nuevaClave = self.obtenerNuevaClaveTitulo();
		self.getSelector("claveSeleccionTitulo").html(nuevaClave);
		params.titulo.clave = nuevaClave;
		params.titulo.origen = "N";
	}
	/* Título */
	if (self.getSelector("tipoBusquedaVisualizarTitulosGrupo").html() == "castellano") {
		self.getSelector("tituloCastellanoSeleccionTitulo").val(params.titulo.titulo);
		self.getSelector("tituloOriginalSeleccionTitulo").val(params.titulo.tituloAlternativo);
	} else {
		self.getSelector("tituloOriginalSeleccionTitulo").val(params.titulo.titulo);
		self.getSelector("tituloCastellanoSeleccionTitulo").val(params.titulo.tituloAlternativo);
	}
	self.contrato.tituloSeleccionado = params.titulo;
	/* Situar calificación oficial */
	self.situarCalificacionesOficiales("calificacionOficialSeleccionTitulo");
	/* Botonera */
	self.getSelector("volverSeleccionTitulo").button().click(self.volverSeleccionTituloSinActualizar);
	self.getSelector("agregarTituloSeleccionTitulo").button().click(self.validacionExistenciaTitulo);
};

AltaContratoEvent.prototype.obtenerNuevaClaveTitulo = function() {
	var self = altaContratoEvent;
	
	var obtenerNuevaClaveTituloRequest = {
		tipoTitulo: self.contrato.montoSenialTT.tipoTitulo
	};
	
	var nuevaClave = new Number();
	
	var data = {
		async: false,
		action:"altaContratoObtenerNuevaClaveTitulo.action", 
		request: Component.serialize(obtenerNuevaClaveTituloRequest, "obtenerNuevaClaveTituloRequest"),
		method: "GET", 
		responseObject: "nuevaClaveTitulo", 
		callback: function(nuevaClaveTitulo) {
			nuevaClave = nuevaClaveTitulo;
		}
	};
	self.service.doRequest(data);
	
	var prefijo = new String();
	var cantidadCeros = 6 - nuevaClave.toString().length;
	
	for (var i = 1; i <= cantidadCeros; i++) {
		prefijo += "0";
	}
	
	return self.contrato.montoSenialTT.tipoTitulo + prefijo + nuevaClave.toString();
};

AltaContratoEvent.prototype.situarCalificacionesOficiales = function(selector) {
	var self = altaContratoEvent;
	
	var data = {
		async: false,
		action:"altaContratoBuscarCalificacionesOficiales.action", 
		request: {clave : self.contrato.tituloSeleccionado.clave}, 
		method: "GET", 
		responseObject: "calificacionesOficiales", 
		callback: function(calificacionesOficiales) {
            for (var calificacionOficialDefault in calificacionesOficiales) {
                Component.populateSelect(self.getSelector(selector), calificacionesOficiales[calificacionOficialDefault], "calificacion", "descripcion", true);
                $("#AltaContratoEventId_calificacionOficialSeleccionTitulo option").filter(function() { return $(this).val().trim() == calificacionOficialDefault.trim(); }).attr("selected", "selected");
            }
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.validacionExistenciaTitulo = function() {
	var self = altaContratoEvent;

	if (Validator.isEmpty(self.getSelector("tituloCastellanoSeleccionTitulo")) && self.contrato.tituloSeleccionado.origen != "F") {
		Validator.focus(self.getSelector("tituloCastellanoSeleccionTitulo"), "Debe indicar el titulo en castellano");
	} else if (Validator.isEmpty(self.getSelector("tituloOriginalSeleccionTitulo")) && self.contrato.tituloSeleccionado.origen != "F") {
        Validator.focus(self.getSelector("tituloOriginalSeleccionTitulo"), "Debe indicar el titulo original");
	} else if (Validator.isEmpty(self.getSelector("calificacionOficialSeleccionTitulo")) && self.contrato.tituloSeleccionado.origen != "F") {
	    Validator.focus(self.getSelector("calificacionOficialSeleccionTitulo"), "Seleccione una calificación");
	} else {
		var esTituloCC = self.determinarFamiliaTitulo(self.contrato.montoSenialTT.tipoTitulo);
		
		var validacionExistenciaTituloRequest = {
			contrato: self.contrato.numero,
			grupo: (esTituloCC ? self.contrato.grupoCC.nroGrupo : self.contrato.grupoSC.nroGrupo),
			senial: self.contrato.montoSenialTT.senial,
			clave: self.getSelector("claveSeleccionTitulo").html()		
		};
		
		var data = {
			async: false,
			action:"altaContratoValidacionExistenciaTitulo.action", 
			request: Component.serialize(validacionExistenciaTituloRequest, "validacionExistenciaTituloRequest"), 
			method: "GET", 
			responseObject: "validacionExistenciaTituloResponse", 
			callback: function(validacionExistenciaTituloResponse) {
				if (validacionExistenciaTituloResponse.error == "N") {
					/* Si no hay error pero hay mensaje, pedir confirmación */
					if (validacionExistenciaTituloResponse.mensaje && validacionExistenciaTituloResponse.mensaje != "OK") {
						self.abrirPopupConfirmacionValidacionExistenciaTitulo(validacionExistenciaTituloResponse.mensaje);
					} else {
						self.validacionRecepcionMaster();
					}	
				} else {
					MESSAGE.alert(validacionExistenciaTituloResponse.mensaje);
				}
			}
		};
		self.service.doRequest(data);
	}
};

AltaContratoEvent.prototype.abrirPopupConfirmacionValidacionExistenciaTitulo = function(mensaje) {
	var self = altaContratoEvent;

	var popup = popupConfirmacionEvent;
	popup.cancelar = function() {
		popup.close();
		popup.remove();
	};
	popup.confirmar = function() {
		popup.close();
		popup.remove();
		self.validacionRecepcionMaster();
	};
	popup.create(self.div.id, mensaje);
};

AltaContratoEvent.prototype.validacionRecepcionMaster = function() {
	var self = altaContratoEvent;
	
	var esTituloCC = self.determinarFamiliaTitulo(self.contrato.montoSenialTT.tipoTitulo);
	
	var validacionRecepcionMasterRequest = {
		contrato: self.contrato.numero,
		grupo: (esTituloCC ? self.contrato.grupoCC.nroGrupo : self.contrato.grupoSC.nroGrupo),
		senial: self.contrato.montoSenialTT.senial,
		tipoDerecho : (esTituloCC ? self.contrato.grupoCC.tipoDerecho : self.contrato.grupoSC.tipoDerecho),
		clave: self.getSelector("claveSeleccionTitulo").html()	
	};
	
	var data = {
		async: false,
		action:"altaContratoValidacionRecepcionMaster.action", 
		request: Component.serialize(validacionRecepcionMasterRequest, "validacionRecepcionMasterRequest"), 
		method: "GET", 
		responseObject: "validacionRecepcionMasterResponse", 
		callback: function(validacionRecepcionMasterResponse) {

			var params = {
				error : validacionRecepcionMasterResponse.errores,
				datosRemito : validacionRecepcionMasterResponse.datosRemito[0],
				grillaRemito : validacionRecepcionMasterResponse.datosRemito,
				grillaSoporte : validacionRecepcionMasterResponse.datosSoporte,
				estaEnSNC : validacionRecepcionMasterResponse.estaEnSNC,
				recOrig : validacionRecepcionMasterResponse.recOrig,
				recCopia : validacionRecepcionMasterResponse.recCopia
			};
			self.abrirPopupWarningsRecepcionMaster(params);
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.abrirPopupWarningsRecepcionMaster = function(params) {
	var self = altaContratoEvent;
	Component.get("html/altaContrato/popupWarningsRecepcionMaster.html", function(comp) {
		self.drawPopupWarningsRecepcionMaster(comp, params);
	});
};

AltaContratoEvent.prototype.drawPopupWarningsRecepcionMaster = function(comp, params) {
	var self = altaContratoEvent;

	$("#popupWarningsRecepcionMaster").dialog({width: 900, show: 'blind', title: "", hide: 'blind', modal: true, autoOpen: false,
		buttons: {
			"Salir": function() {
				$(this).dialog("close");
			}
		}
	});
	$("#popupWarningsRecepcionMaster").empty().append(comp.replace(/{{id}}/g, self.div.id));

	var esTituloCC = self.determinarFamiliaTitulo(self.contrato.montoSenialTT.tipoTitulo);
	var tipoDerecho  = (esTituloCC ? self.contrato.grupoCC.tipoDerecho : self.contrato.grupoSC.tipoDerecho);
	
	/* Validar que componentes mostrar */
	if (params.estaEnSNC) {
		/* CASO A - Mostrar el remito para preguntar si es válido para dar por recibido el título. 
		 * RecOrig en "S" hasta el fin a menos que el usuario seleccione el botón NO. 
		 * RecCopia en "N" hasta el fin. */
		if (params.recOrig) {
			/* Abro el popup */
			$("#popupWarningsRecepcionMaster").dialog("open");
			
			/* Mostrar grilla con los datos del remito */
			self.getSelector("divGrillaWarningsRecepcionMaster").show();
			self.getSelector("gridWarningsRecepcionMaster").jqGrid({
				height: 'auto',
				autowidth: true,
				datatype: 'local',
				colNames:['Remito', 'Guía', 'Fecha ingreso', 'Fecha remito', 'Chqueo volver'],
				colModel:[ 
				    {name:'nroRemito',		 index:'nroRemito',			align:'center'},
				    {name:'nroGuia',		 index:'nroGuia',			align:'center'},
				    {name:'fechaIngreso',	 index:'fechaIngreso',		align:'center',	formatter:'date', formatoptions:{newformat: 'd/m/Y'}},
				    {name:'fechaRtoGuia',	 index:'fechaRemito',		align:'center',	formatter:'date', formatoptions:{newformat: 'd/m/Y'}},
				    {name:'chequeo',	 	 index:'chequeoVolver',		align:'center'}
				],
				rowNum: 10,
				rowList:[10,20,30],
			   	scrollOffset: 0,
				pager: '#' + self.div.id + '_pagerWarningsRecepcionMaster',
				viewrecords: true, 
				loadonce: true,
				editurl: 'clientArray', 
				caption: 'Datos remito'
			});
			self.getSelector("gridWarningsRecepcionMaster").clearGridData().setGridParam({data: params.grillaRemito}).trigger('reloadGrid');
			
			/* Botonera */
			self.getSelector("confirmarDatosWarningsRecepcionMaster").button().click(function() {
				/* Seteo los datos de la respuesta */
				params.datosRemito.recOrig = 'S';
				params.datosRemito.recCopia = 'N';
				/* Seteo la fecha copia ya que la fecha remito la obtengo de la grilla */
				params.datosRemito.fechaCopia = "01/01/0001";
				/* Cierro el popup y confirmo */
				self.cierrePopupWarningsRecepcionMaster();
				self.confirmarSeleccionTitulo(params);
			});
			
			self.getSelector("cancelarWarningsRecepcionMaster").button().click(function() {
				/* Seteo los datos de la respuesta */
				params.datosRemito.recOrig = 'N';
				params.datosRemito.recCopia = 'N';
				/* Seteo la fecha copia y reemplazo la fecha de remito */
				params.datosRemito.fechaRem = "01/01/0001";
				params.datosRemito.fechaCopia = "01/01/0001";
				/* Cierro el popup y confirmo */
				self.cierrePopupWarningsRecepcionMaster();
				self.confirmarSeleccionTitulo(params);
			});
		}
		/* CASO B - Se encontró un master para el título y está en SNC.
		 * RecCopia en "S" hasta el fin a menos que el usuario seleccione el botón NO. 
		 * RecOrig en "N" hasta el fin.
		 */
		if (params.recCopia) {
			/* Setep el mensaje a mostrar */
			$("#popupWarningsRecepcionMaster").dialog({title: "Se encontró un master para el título y está en SNC"});
			/* Abro el popup */
			$("#popupWarningsRecepcionMaster").dialog("open");
			
			/* Botonera */
			self.getSelector("confirmarDatosWarningsRecepcionMaster").button().click(function() {
				/* Seteo los datos de la respuesta */
				params.datosRemito.recOrig = 'N';
				params.datosRemito.recCopia = 'S';
				params.datosRemito.fechaRem = "01/01/0001";
				params.datosRemito.fechaCopia = "01/01/0001";
				/* Cierro el popup y confirmo */
				self.cierrePopupWarningsRecepcionMaster();
				self.confirmarSeleccionTitulo(params);
			});
			
			self.getSelector("cancelarWarningsRecepcionMaster").button().click(function() {
				/* Seteo los datos de la respuesta */
				params.datosRemito.recOrig = 'N';
				params.datosRemito.recCopia = 'N';
				params.datosRemito.fechaRem = "01/01/0001";
				params.datosRemito.fechaCopia = "01/01/0001";
				/* Cierro el popup y confirmo */
				self.cierrePopupWarningsRecepcionMaster();
				self.confirmarSeleccionTitulo(params);
			});
		}
		
		if (!params.recOrig && !params.recCopia) {
			/* Seteo los datos de la respuesta */
			params.datosRemito.recOrig = 'N';
			params.datosRemito.recCopia = 'N';
			params.datosRemito.fechaRem = "01/01/0001";
			params.datosRemito.fechaCopia = "01/01/0001";
			/* Cierro el popup y confirmo */
			self.confirmarSeleccionTitulo(params);
		}
	} 
	/* CASO C - Se encontró un master para el título y no está en SNC.
	 * RecCopia en "S" hasta el fin a menos que el usuario seleccione el botón NO. 
	 * RecOrig en "N" hasta el fin.
	 */
	else if (params.recCopia) {
		/* Seteo el mensaje de advertencia */
		$("#popupWarningsRecepcionMaster").dialog({title: "El sistema encontró master para el título, ¿da por válido asociarlo al contrato?"});
		/* Abro el popup */
		$("#popupWarningsRecepcionMaster").dialog("open");
		
		/* Mostrar grilla con los datos del soporte */
		self.getSelector("divGrillaWarningsRecepcionMaster").show();
		
		self.getSelector("gridWarningsRecepcionMaster").jqGrid({
			height: 'auto',
			autowidth: true,
			datatype: 'local',
			colNames:['Soporte', 'Fecha copia'],
			colModel:[ 
			    {name:'soporte',		index:'soporte',		align:'center'},
			    {name:'fechaCopia',		index:'fechaCopia',		align:'center',	formatter:'date', formatoptions:{newformat: 'd/m/Y'}}
			],
			rowNum: 10,
			rowList:[10,20,30],
		   	scrollOffset: 0,
			pager: '#' + self.div.id + '_pagerWarningsRecepcionMaster',
			viewrecords: true, 
			loadonce: true,
			editurl: 'clientArray', 
			caption: 'Soportes'
		});
		self.getSelector("gridWarningsRecepcionMaster").clearGridData().setGridParam({data: params.grillaSoporte}).trigger('reloadGrid');
		
		self.getSelector("confirmarDatosWarningsRecepcionMaster").button().click(function() {
			/* Cierro el popup */
			self.cierrePopupWarningsRecepcionMaster();
			/* Solicito el ingreso de la fecha */
			if (params.error.tipoError == "N" && tipoDerecho == "R") {
				$("#popupWarningsRecepcionMaster").dialog({width: 900, show: 'blind', title: "Ingrese la fecha que se tomará como fecha de vigencia desde para el título", 
					hide: 'blind', modal: true, autoOpen: false,
					buttons: {
						"Salir": function() {
							$(this).dialog("close");
						}
					}
				});
				$("#popupWarningsRecepcionMaster").empty().append(comp.replace(/{{id}}/g, self.div.id));
				$("#popupWarningsRecepcionMaster").dialog("open");
				
				self.getSelector("divFechaCopiaWarningsRecepcionMaster").show();

				/* Mostrar solo input de fecha copia */
				Datepicker.getInstance(self.getSelector("fechaCopiaWarningsRecepcionMaster"), new Date());
				
				self.getSelector("confirmarDatosWarningsRecepcionMaster").button().click(function() {
					if (Validator.isEmpty(self.getSelector("fechaCopiaWarningsRecepcionMaster"))) {
						Validator.focus(self.getSelector("fechaCopiaWarningsRecepcionMaster"), "Ingrese una fecha válida");
					} else {
						/* Seteo los datos de la respuesta */
						params.datosRemito.recOrig = 'N';
						params.datosRemito.recCopia = 'S';
						params.datosRemito.fechaRem = "01/01/0001";
						params.datosRemito.fechaCopia = self.getSelector("fechaCopiaWarningsRecepcionMaster").val();
						/* Cierro el popup y confirmo */
						self.cierrePopupWarningsRecepcionMaster();
						self.confirmarSeleccionTitulo(params);
					}
				});
				
				self.getSelector("cancelarWarningsRecepcionMaster").button().click(function() {
					/* Seteo los datos de la respuesta */
					params.datosRemito.recOrig = 'N';
					params.datosRemito.recCopia = 'N';
					params.datosRemito.fechaRem = "01/01/0001";
					params.datosRemito.fechaCopia = "01/01/0001";
					/* Cierro el popup y confirmo */
					self.cierrePopupWarningsRecepcionMaster();
					self.confirmarSeleccionTitulo(params);
				});
			} else {
				self.confirmarSeleccionTitulo(params);
			}
		});

		self.getSelector("cancelarWarningsRecepcionMaster").button().click(function() {
			/* Seteo los datos de la respuesta */
			params.datosRemito.recOrig = 'N';
			params.datosRemito.recCopia = 'N';
			params.datosRemito.fechaRem = "01/01/0001";
			params.datosRemito.fechaCopia = "01/01/0001";
			/* Cierro el popup y confirmo */
			self.cierrePopupWarningsRecepcionMaster();
			self.confirmarSeleccionTitulo(params);
		});
	} else {
		/* Seteo los datos de la respuesta */
		
		/* se comento este hardcodeo porque en el caso de titulos sc , los datos originales estaban bien, no hace falta modificarlos */
		
/*		params.datosRemito.recOrig = 'N';
		params.datosRemito.recCopia = 'N';
		params.datosRemito.fechaRem = "01/01/0001";
		params.datosRemito.fechaCopia = "01/01/0001"; */
		
		/* Cierro el popup y confirmo */
		params.datosRemito.recOrig = params.recOrig ? 'S': 'N';
		params.datosRemito.recCopia = params.recCopia ? 'S': 'N';
		
		self.confirmarSeleccionTitulo(params);
	}
};

AltaContratoEvent.prototype.cierrePopupWarningsRecepcionMaster = function() {
	$("#popupWarningsRecepcionMaster").dialog("close");
	$("#popupWarningsRecepcionMaster").dialog("remove");
};

AltaContratoEvent.prototype.confirmarSeleccionTitulo = function(params) {
	var self = altaContratoEvent;
	
	var esTituloCC = self.determinarFamiliaTitulo(self.contrato.montoSenialTT.tipoTitulo);
	
	var confirmarSeleccionTituloRequest = {
		contrato: self.contrato.numero,
		grupo: (esTituloCC ? self.contrato.grupoCC.nroGrupo : self.contrato.grupoSC.nroGrupo),
		senial: self.contrato.montoSenialTT.senial,
		clave: self.contrato.tituloSeleccionado.origen == "F" ? null : self.contrato.tituloSeleccionado.clave,
		proveedor: self.contrato.cabecera.proveedor,
		origen: self.contrato.tituloSeleccionado.origen,
		marPerm: (esTituloCC ? self.contrato.grupoCC.marcaPerpetuidad : self.contrato.grupoSC.marcaPerpetuidad),
		tipoDerecho: (esTituloCC ? self.contrato.grupoCC.tipoDerecho : self.contrato.grupoSC.tipoDerecho),
		chequeo: params.datosRemito.chequeo,
//		tituloCastSinContrato: (self.contrato.tituloSeleccionado.sinContrato != "" && self.getSelector("tipoBusquedaVisualizarTitulosGrupo").html() == "castellano" ? "S" : "N"),
//		tituloOrigSinContrato: (self.contrato.tituloSeleccionado.sinContrato != "" && self.getSelector("tipoBusquedaVisualizarTitulosGrupo").html() != "castellano" ? "S" : "N"),
		tituloCastSinContrato: (self.contrato.tituloSeleccionado.sinContrato != "" ? "S" : "N"),
	    tituloOrigSinContrato: (self.contrato.tituloSeleccionado.sinContrato != "" ? "S" : "N"),
		recOrig: params.datosRemito.recOrig,
		recCopia: params.datosRemito.recCopia,
		fechaCopia: params.datosRemito.fechaCopia,
		nroRtoInterno: params.datosRemito.nroRemito,
		fechaRem: params.datosRemito.fechaRtoGuia,
		calificacionOficial: self.getSelector("calificacionOficialSeleccionTitulo").val().trim() == "" ? null : self.getSelector("calificacionOficialSeleccionTitulo").val(),
		tituloOriginal: self.getSelector("tituloOriginalSeleccionTitulo").val().trim() == "" ? null : self.getSelector("tituloOriginalSeleccionTitulo").val(),
		tituloCastellano: self.getSelector("tituloCastellanoSeleccionTitulo").val().trim() == "" ? null : self.getSelector("tituloCastellanoSeleccionTitulo").val(),
		claveFichaCinematografica: self.contrato.tituloSeleccionado.origen == "F" ? self.contrato.tituloSeleccionado.clave : 0,
		primerActor: self.getSelector("actorUnoSeleccionTitulo").val(),
		segundoActor: self.getSelector("actorDosSeleccionTitulo").val(),
		observaciones: self.getSelector("observacionesSeleccionTitulo").val() == "" ? " " :self.getSelector("observacionesSeleccionTitulo").val()
	};	
	
	var data = {
		async: false,
		action:"altaContratoConfirmarSeleccionTitulo.action", 
		request: Component.serialize(confirmarSeleccionTituloRequest, "confirmarSeleccionTituloRequest"), 
		method: "POST", 
		responseObject: "confirmarSeleccionTituloResponse", 
		callback: function(confirmarSeleccionTituloResponse) {
			if (confirmarSeleccionTituloResponse.tipoError && confirmarSeleccionTituloResponse.tipoError == "W") {	
				MESSAGE.alert(confirmarSeleccionTituloResponse.descripcion);
			} else if (confirmarSeleccionTituloResponse.rerun == "S") {
				/* Incremento el contador de títulos ya ingresados */
				self.contrato.montoSenialTT.titulosIngresados++;
				
				/* Seteo los datos necesarios para la busqueda en FP11015 */
				self.contrato.tituloRecontratacionSeleccionado = new Object();
				self.contrato.tituloRecontratacionSeleccionado.clave = self.contrato.tituloSeleccionado.clave;
				self.contrato.tituloRecontratacionSeleccionado.titulo = self.contrato.tituloSeleccionado.titulo;
				self.contrato.tituloRecontratacionSeleccionado.contrato = self.contrato.numero;
				self.contrato.tituloRecontratacionSeleccionado.grupo = (esTituloCC ? self.contrato.grupoCC.nroGrupo : self.contrato.grupoSC.nroGrupo);
				self.contrato.tituloRecontratacionSeleccionado.coincideMonto = confirmarSeleccionTituloResponse.coincideMonto;
				
				/* Redirigir a FP11015 */
				self.getSelector("formSeleccionTitulo").hide();
				self.abrirSeleccionTituloRecontratacionEnlaces("FP11012");
			} else {
				/* Incremento el contador de títulos ingresados */
				self.contrato.montoSenialTT.titulosIngresados++;
				
				/* Si el grupo es CC, se ingresan solo capítulos de un único título. Si es SC, solo controlo la cantidad de ingresos. */
				if (esTituloCC) {
					if (confirmarSeleccionTituloResponse.coincideMonto == "S"){
						self.getSelector("formSeleccionTitulo").hide();
						self.volverVisualizarGruposContrato();
					} else {
						self.volverSeleccionTituloVisualizarGrupos();
					}
				} else {
					var cantidadTitulos = (esTituloCC ? self.contrato.grupoCC.cantidadOriginales : self.contrato.grupoSC.cantidadTitulos);
					/* Si se ingresó la totalidad de los títulos, redirijo a FP11009 sino a FP11010/11 */
					if (self.contrato.montoSenialTT.titulosIngresados == cantidadTitulos) {
						if (confirmarSeleccionTituloResponse.coincideMonto == "S"){
							self.getSelector("formSeleccionTitulo").hide();
							self.volverVisualizarGruposContrato();
						} else {
							self.volverSeleccionTituloVisualizarGrupos();
						}
					} else {
						self.volverSeleccionTituloVisualizarTitulos();
						self.buscarTitulosGrupo();
					}
				}
			}
			/* Borro los datos temporales almacenados del titulo seleccionado en FP11010/11 */ 
			self.contrato.tituloSeleccionado = null;
		}
	};
	self.service.doRequest(data);
};

AltaContratoEvent.prototype.volverSeleccionTituloSinActualizar = function() {
	var self = altaContratoEvent;
	/* Vuelvo a FP11010/11 */
	self.getSelector("formSeleccionTitulo").hide();
	self.getSelector("formVisualizarTitulosGrupo").show();
};

AltaContratoEvent.prototype.volverSeleccionTituloVisualizarTitulos = function() {
	var self = altaContratoEvent;
	/* Determino la familia del grupo en el que se está trabajando */
	var esTituloCC = self.determinarFamiliaTitulo(self.contrato.montoSenialTT.tipoTitulo);
	/* Actualizo los datos de pantalla */
	if (esTituloCC) {
		self.getSelector("ingresadasSeleccionTitulo").html(self.contrato.montoSenialTT.titulosIngresados + " de " + self.contrato.grupoCC.cantidadOriginales);
		self.getSelector("ingresadasVisualizarTitulosGrupo").html(self.contrato.montoSenialTT.titulosIngresados + " de " + self.contrato.grupoCC.cantidadOriginales);
	} else {
		self.getSelector("ingresadasSeleccionTitulo").html(self.contrato.montoSenialTT.titulosIngresados + " de " + self.contrato.grupoSC.cantidadTitulos);
		self.getSelector("ingresadasVisualizarTitulosGrupo").html(self.contrato.montoSenialTT.titulosIngresados + " de " + self.contrato.grupoSC.cantidadTitulos);
	}
	/* Vuelvo a FP11010/11 */
	self.getSelector("formSeleccionTitulo").hide();
	self.getSelector("formVisualizarTitulosGrupo").show();
};

AltaContratoEvent.prototype.volverSeleccionTituloVisualizarGrupos = function() {
	var self = altaContratoEvent;
	/* Limpio los datos viejos */ 
	self.contrato.grupoCC = new GrupoCCPOJO();
	self.contrato.grupoSC = new GrupoSCPOJO();
	self.contrato.montoSenialTT.tipoTitulo = null;
	self.contrato.montoSenialTT.titulosIngresados = null;
	/* Redirijo a FP11009 */
	self.getSelector("formSeleccionTitulo").hide();
	self.abrirVisualizarGruposContrato();
};