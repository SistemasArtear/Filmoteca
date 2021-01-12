function ModificarContratoDrawer(div) {
    this.div = div;
};
extend(ModificarContratoDrawer, Plugin);

ModificarContratoDrawer.prototype.drawSenialesExtenderChequeo = function(row) {
    situarPopupEvent.reset();
    situarPopupEvent.nombreSituar = "Extender chequeo tecnico";
    situarPopupEvent.setColumns(["Codigo Senial", "Descripcion"], [{name:"codigo", index:"codigo"}, {name:"descripcion", index:"descripcion"}]);
    situarPopupEvent.doAfterDraw = function() {
        situarPopupEvent.getPopup().dialog({buttons: { "OK": function() { 
            var data = situarPopupEvent.getGrid().jqGrid('getGridParam','data'), item;
            var senialesArray = [];
            for (var i = 0; i <= data.length; i++) {
                item = data[i];
                if (item && item.cb) {
                    senialesArray.push({clave: item.codigo});
                }
            }
            var data = {data: {contrato: row.contrato, seniales: senialesArray}};
            jQuery.ajax({
                    type: 'POST', 
                    url: 'extenderChequeoTecnico.action', 
                    data: JSON.stringify(data),
                    dataType: 'json',
                    contentType: 'application/json; charset=utf-8',
                    success: function(data) {
                        if (data.validacionModificacionContrato && data.validacionModificacionContrato.length && data.validacionModificacionContrato.length > 0) {
                            var hayErrores = false;
                            var hayWarnings = false;
                            var warnings  = new Array();
                            var errores  = new Array();
                            var ok = false;
                            $.each(data.validacionModificacionContrato, function(i, l) {
                                if (l["tipo"] === "E") {
                                    hayErrores = true;
                                    errores.push(l["descripcion"]);
                                } else if (l["tipo"] === "W") {
                                    hayWarnings = true;
                                    warnings.push(l["descripcion"]);
                                } else if (l["tipo"] === "P_OK") {
                                    ok = l["descripcion"] === "S";
                                }
                            });
                            if (hayErrores) {
                                MESSAGE.error(errores[0]);
                            } else if (hayWarnings){
                                MESSAGE.alert(warnings[0]);
                            } else if (ok) {
                                MESSAGE.ok("Chequeo extendido");
                            }
                        }
                        
                        situarPopupEvent.getPopup().dialog("close");
                        
                        
                    }
                });
            } } });
        return;
    };
    
    situarPopupEvent.doAfterClose = function() {
        ModificarContratoStaticService.doRequest(
                {action:"desbloquearContrato.action",
                    request: {claveContrato: row.contrato},
                    method: "GET",
                    callback: function() {
                        return;
                    } 
                });
    };

    situarPopupEvent.doAfterDrawGrid = function() {
        situarPopupEvent.getGrid().jqGrid('setGridParam', {onSelectRow: function(rowid, status){
            var baseIndex = ($(this).getGridParam('page') - 1) * 10;
            var p = this.p, item = p.data[baseIndex + parseInt(rowid) - 1];
            if (typeof (item.cb) === "undefined") {
                item.cb = true;
            } else {
                item.cb = !item.cb;
            }
        }});
        situarPopupEvent.getGrid().jqGrid('setGridParam', {onSelectAll: function(){
            var p = this.p, data = p.data, item;
            for (var i = 0; i <= data.length; i++) {
                item = data[i];
                if (typeof (item.cb) === "undefined") {
                    item.cb = true;
                } else {
                    item.cb = !item.cb;
                }
            }
        }});
        situarPopupEvent.getGrid().jqGrid('setGridParam', {loadComplete: function(){
            var baseIndex = ($(this).getGridParam('page') - 1) * 10;
            var p = this.p, data = p.data, item, $this = $(this);
            for (var i = 0; i <= data.length; i++) {
                item = data[i];
                if (item && item.cb && typeof (item.cb) === "boolean") {
                    $this.jqGrid('setSelection', (i - baseIndex + 1), false);
                } else if (item && item.seleccionado && (item.seleccionado === "S")){
                    $this.jqGrid('setSelection', (i - baseIndex + 1), false);
                }
            }
        }});
        situarPopupEvent.getGrid().trigger('reloadGrid');
        return;
    };
    situarPopupEvent.acciones = [];
    situarPopupEvent.create(
            "dameSenialesChequeo.action", 
            function (row) {
                return false;
            }, 
            {"claveContrato": row.contrato}, 
            "senialesAsignadas",
             true,
             true);
};
ModificarContratoDrawer.prototype.draw = function(comp) {
    var self = modificarContratoDrawer;
    $("#viewContainer").empty().append(comp.replace(/{{id}}/g, self.div.id));
    modificarContratoSelector.getButton("buscarContratoPorClave").button().click(modificarContratoPopulator.populateGrillaContratosPorClave);
    modificarContratoSelector.getButton("buscarContratoPorDistribuidor").button().click(
            function () {
                var distribuidor = modificarContratoSelector.getInput("busquedaPorDistribuidor").val().trim().split("-")[0].trim();
                if (isNaN(distribuidor)) {
                    modificarContratoSelector.getInput("popupBusquedaContratoDistribuidor").click();
                } else {
                    modificarContratoPopulator.populateGrillaContratosPorDistribuidor();
                }
            });
    modificarContratoSelector.getInput("busquedaContratoPorClave").keypress(function (event) {
        if (event.which == 13) {
            event.preventDefault();
            modificarContratoPopulator.populateGrillaContratosPorClave();
            return;
        }
    });
    modificarContratoSelector.getInput("popupBusquedaContratoDistribuidor").click(
            function() {
                var nombreDistribuidor = modificarContratoSelector.getInput("busquedaPorDistribuidor").val().trim();
                modificarContratoSelector.getInput("busquedaContratoPorClave").val("");
                situarPopupEvent.reset();
                situarPopupEvent.nombreSituar = "Seleccion de distribuidor";
                situarPopupEvent.acciones = [];
                situarPopupEvent.setColumns(
                        ["Codigo","Razon Social"],
                        [{name:"codigo", index:"codigo"},{name:"razonSocial", index:"razonSocial"}]);
                situarPopupEvent.doAfterDraw = function() {
                    return;
                };
                situarPopupEvent.create("dameDistribuidoresPorNombre.action", 
                        function(row){
                            modificarContratoSelector.getInput("busquedaPorDistribuidor").val(row.codigo+" - "+row.razonSocial);
                            modificarContratoSelector.getInput("busquedaContratoPorClave").val("");
                        }, 
                        {nombreDistribuidor:  nombreDistribuidor ? nombreDistribuidor : ""}, 
                        "distribuidores");
            }
    );
    modificarContratoSelector.getGrilla("grillaDeContratos").jqGrid({
        height: 'auto',
        autowidth: true,
        datatype: 'local',
        colNames:['Contrato','Distribuidor','Razon Social','Monto Total','Moneda','Fecha Contrato','Senial','Estado', '', ''],
        colModel:[ 
            {name:'contrato',index:'contrato', width:150, sortable:false, align:"center"}, 
            {name:'distribuidor',index:'distribuidor', width:150, sortable:false, align:"center"},
            {name:'razonSocial',index:'razonSocial', width:500, sortable:false},
            {name:'montoTotal',index:'montoTotal', width:200, sortable:false, formatter:'currency', align:"center"},
            {name:'moneda',index:'moneda', width:100, sortable:false, align:"center"},
            {name:'fechaContrato',index:'fechaContrato', align:"center", width:200, sortable:false, formatter: 'date', formatoptions: {srcformat: 'Y-m-d\\TH:i:s', newformat: 'd/m/Y'}},
            {name:'senial',index:'senial', width:100, sortable:false, align:"center"},
            {name:'estado',index:'estado', width:100, sortable:false, align:"center"},
            {name:'modificar',index:'modificar', width:50, sortable:false, align:"center"},
            {name:'extenderChequeo',index:'extenderChequeo', width:50, sortable:false, align:"center"}
        ],
        rowNum: 20,
        rowList:[10,20,30],
        scrollOffset: 0,
        pager: '#ModificarContratoEventId_pagerGrillaDeContratos',
        viewrecords: true, 
        loadonce: true,
        editurl: 'clientArray', 
        caption: 'Contratos',
        ondblClickRow: function(id){
            if (id) {
                modificarContratoBusiness.editarContratoConCabecera(id);
            };
        },
        gridComplete: function() {
            var ids = $(this).jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var rowId = ids[i];
                var modificar = "<center><div onclick='modificarContratoBusiness.editarContratoConCabecera("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-pencil modificar conTooltip'>Modificar</span></div></center>";
                $(this).jqGrid('setRowData', rowId, { modificar: modificar });
                var extenderChequeo = "<center><div onclick='modificarContratoBusiness.extenderChequeoTecnico("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-clock visualizar conTooltip'>Extender Chequeo</span></div></center>";
                $(this).jqGrid('setRowData', rowId, { extenderChequeo: extenderChequeo });
            }
            return;
        }
    });
    Accordion.getInstance(self.getSelector("filtroBusquedaContratos"));
};
ModificarContratoDrawer.prototype.drawPopUpContratoConCabecera = function(comp) {
    $("#popupContainer").empty().append(comp.replace(/{{id}}/g, modificarContratoDrawer.div.id));
    modificarContratoSelector.getPopUp("contratoConCabeceraPopUp").dialog({
        title : 'Cabecera Contrato',
        width: 900,
        show: 'blind',
        hide: 'blind',
        position: ["center", "top"],
        modal: true,
        autoOpen: true,
        open: function() {
            modificarContratoSelector.getGrillaGruposContainer().hide();
            modificarContratoPopulator.populatePopUpContratoConCabecera(modificarContratoBusiness.contratoConCabecera);
            ModificarContratoStaticService.doRequest(
                    {action:"dameSenialImporte.action",
                        request: {claveContrato: modificarContratoBusiness.contratoConCabecera.contrato},
                        method: "GET",
                        callback: function(data) {
                            if (data && data.length !== undefined && data.length > 0) {
                                modificarContratoBusiness.contratoConCabecera.senialesEimportes = data;
                                modificarContratoDrawer.drawGrillaDeSenialesEImportes();
                                modificarContratoPopulator.populateGrillaSenialesEImportes(modificarContratoBusiness.contratoConCabecera.senialesEimportes);
                            } else {
                                MESSAGE.alert("No se pudieron obtener las señales e importes del contrato");
                            }
                        },
                        responseObject: "senialesConImportes"});
        },
        close: function() {
            $(this).dialog("destroy");
            ModificarContratoStaticService.doRequest(
                    {action:"desbloquearContrato.action",
                        request: {claveContrato: modificarContratoBusiness.contratoConCabecera.contrato},
                        method: "GET",
                        callback: function() {
                            modificarContratoSelector.getPopUp("contratoConCabeceraPopUp").remove();
                            if (modificarContratoSelector.getInput("busquedaPorDistribuidor").val() != "") {
                                modificarContratoSelector.getButton("buscarContratoPorDistribuidor").click();
                            } else {
                                modificarContratoSelector.getButton("buscarContratoPorClave").click();
                            };
                        } 
                     });

        },
        buttons: {
            "CONFIRMAR CONTRATO": function() {
                modificarContratoValidator.validarCabeceraSeniales(
                           {contrato: parseInt(modificarContratoSelector.getInput("contrato", "contratoConCabeceraPopUp").val()),
                            distribuidor: parseInt(modificarContratoSelector.getInput("distribuidor", "contratoConCabeceraPopUp").val()),
                            moneda: modificarContratoSelector.getInput("moneda", "contratoConCabeceraPopUp").val(),
                            fechaContrato: modificarContratoSelector.getInput("fechaContrato", "contratoConCabeceraPopUp").val(),
                            fechaAprobacion: modificarContratoSelector.getInput("fechaAprobacion", "contratoConCabeceraPopUp").val(),
                            tipoContrato: modificarContratoSelector.getInput("tipoContrato", "contratoConCabeceraPopUp").val(),
                            monto: modificarContratoSelector.getInput("montoTotal", "contratoConCabeceraPopUp").autoNumericGet()},
                            true);
            },
            Salir: function() {
                if ( modificarContratoSelector.getGrillaGrupos().children().length == 0 ) {
                    modificarContratoValidator.validarCabeceraSeniales(
                            {contrato: parseInt(modificarContratoSelector.getInput("contrato", "contratoConCabeceraPopUp").val()),
                                distribuidor: parseInt(modificarContratoSelector.getInput("distribuidor", "contratoConCabeceraPopUp").val()),
                                moneda: modificarContratoSelector.getInput("moneda", "contratoConCabeceraPopUp").val(),
                                fechaContrato: modificarContratoSelector.getInput("fechaContrato", "contratoConCabeceraPopUp").val(),
                                fechaAprobacion: modificarContratoSelector.getInput("fechaAprobacion", "contratoConCabeceraPopUp").val(),
                                tipoContrato: modificarContratoSelector.getInput("tipoContrato", "contratoConCabeceraPopUp").val(),
                                monto: parseFloat(modificarContratoSelector.getInput("montoTotal", "contratoConCabeceraPopUp").autoNumericGet())},
                                false);
                } else {
                    modificarContratoSelector.getGrillaGrupos().jqGrid('GridUnload');
                    
                    var param = {contrato: parseInt(modificarContratoSelector.getInput("contrato", "contratoConCabeceraPopUp").val()), 
                                 senial: modificarContratoBusiness.contratoConCabecera.senialElegida};
                    ModificarContratoStaticService.doRequest(
                            {action:"validarCabeceraSenial.action",
                             request: Component.serialize(param, "contratoValidationRequest"),
                             method: "GET",
                             callback: function(data) {
                                if (data && data.length && data.length > 0) {
                                    var errores = [];
                                    $.each(data, function() {
                                        errores.push(this["tipo"]+" - "+this["descripcion"]);
                                    });
                                    popupConfirmacionEvent.confirmar = function () {
                                        //modificarContratoBusiness.actualizarImporteSenialAutomaticamente(param);
                                        // TODO
                                        ModificarContratoStaticService.doRequest(
                                                {action:"actualizarImporteSenialAutomaticamente.action",
                                                 request: Component.serialize(param, "contratoValidationRequest"),
                                                 method: "GET",
                                                 callback: function(data) {
                                                     ModificarContratoStaticService.doRequest(
                                                             {action:"dameSenialImporte.action",
                                                                 request: {claveContrato: modificarContratoBusiness.contratoConCabecera.contrato},
                                                                 method: "GET",
                                                                 callback: function(data) {
                                                                     if (data && data.length && data.length > 0) {
                                                                         modificarContratoBusiness.contratoConCabecera.senialesEimportes = data;
                                                                         modificarContratoPopulator.populateGrillaSenialesEImportes(modificarContratoBusiness.contratoConCabecera.senialesEimportes);
                                                                     } else {
                                                                         MESSAGE.error("Ocurrio un error al tratar de obtener la seniales e importes");
                                                                     }
                                                                 },
                                                                 responseObject: "senialesConImportes"});
                                                 },
                                                 responseObject: "senialesConImportes"});
                                        
                                        
                                        
                                        popupConfirmacionEvent.remove();
                                    };
                                    popupConfirmacionEvent.cancelar = function () {
                                        popupConfirmacionEvent.remove();
                                    };
                                    popupConfirmacionEvent.afterDraw = function () {
                                        popupConfirmacionEvent.popTitle = '¿ DESEA QUE SE ACTUALICE EL IMPORTE DEL GRUPO ?';
                                    };
                                    popupConfirmacionEvent.create("errorPopUp", errores.join("<br/>"));
                                }
                             },
                             responseObject: "validacionModificacionContrato"});

                }
            }
        }
    });    
};
ModificarContratoDrawer.prototype.drawPopUpTituloConGrupo = function(comp) {
    var self = modificarContratoDrawer;
    $("#viewContainer").append(comp.replace(/{{id}}/g, self.div.id));
    modificarContratoSelector.getPopUp("tituloConGrupoPopUp").dialog({
        title : 'Titulo Contratado',
        width: 900,
        show: 'blind',
        hide: 'blind',
        position: ["center", "top"],
        modal: true,
        autoOpen: true,
        open: function() {
            //delete modificarContratoBusiness.grupo;
            Datepicker.picker(modificarContratoSelector.getInput("fechaHasta", "tituloConGrupoPopUp"));
            Datepicker.picker(modificarContratoSelector.getInput("fechaDesde", "tituloConGrupoPopUp"));
            Datepicker.picker(modificarContratoSelector.getInput("fechaDerechos", "tituloConGrupoPopUp"));
            Datepicker.picker(modificarContratoSelector.getInput("fechaEntrega", "tituloConGrupoPopUp"));
            Datepicker.picker(modificarContratoSelector.getInput("fechaComienzoDerechosLibreria", "tituloConGrupoPopUp"));
            
            Component.populateSelect(modificarContratoSelector.getInput("tipoImporte", "tituloConGrupoPopUp"), modificarContratoBusiness.tiposDeImporte, "codigo", "descripcion");
            Component.populateSelect(modificarContratoSelector.getInput("codigoDerechos", "tituloConGrupoPopUp"), modificarContratoBusiness.tiposDeDerecho, "codigo", "descripcion");
            Component.populateSelect(modificarContratoSelector.getInput("tipoTitulo", "tituloConGrupoPopUp"), modificarContratoBusiness.tiposDeTitulo, "tipoTitulo", "descripcionTitulo");
            Component.populateSelect(modificarContratoSelector.getInput("codigoDerechosTerritoriales", "tituloConGrupoPopUp"), modificarContratoBusiness.tiposDeDerechoTerritorial, "codigo", "descripcion");
            Component.populateCheckBoxes(modificarContratoSelector.getInput("senialesHeredadas", "tituloConGrupoPopUp"), seniales, "codigo", "descripcion", "senialHeredadaCheck");

            if (modificarContratoBusiness.contratoConCabecera.grupoElegido) {
                ModificarContratoStaticService.doRequest(
                        {action:"dameTituloContratado.action",
                         request: Component.serialize(
                                     {contrato: modificarContratoBusiness.contratoConCabecera.contrato,
                                      grupo: modificarContratoBusiness.contratoConCabecera.grupoElegido,
                                      senial: modificarContratoBusiness.contratoConCabecera.senialElegida,
                                      clave: modificarContratoDrawer.drawPopUpTituloConGrupo.row.clave}, "tituloRequest"),
                         method: "GET",
                         callback: function(data) {
                              if (data) {
                                  if (modificarContratoDrawer.drawPopUpTituloConGrupo.modo) {
                                      modificarContratoPopulator.populatePopUpTituloConGrupo(data, modificarContratoDrawer.drawPopUpTituloConGrupo.modo);
                                  } else {
                                      modificarContratoPopulator.populatePopUpTituloConGrupo(data, "C");
                                  }
                              } else {
                                  MESSAGE.error("Error al buscar el grupo");
                              }
                         },
                         responseObject: "tituloConGrupo"});
            }
       },
        close: function() {
            $(this).dialog("destroy");
            modificarContratoSelector.getPopUp("tituloConGrupoPopUp").remove();
            //modificarContratoPopulator.populateGrillaDeGrupos(modificarContratoBusiness.contratoConCabecera.senialElegida);
        },
        buttons: {
            "OK": function() {
                if (!modificarContratoValidator.validateTituloConGrupo()) {
                    return;
                };

                var senialesHeredadas = "";
                $("#ModificarContratoEventId_tituloConGrupoPopUp_senialesHeredadas :checked").each(function() { 
                    senialesHeredadas = senialesHeredadas + $(this).val() + ";";
                });
                if (senialesHeredadas !== "") {
                    senialesHeredadas = senialesHeredadas.slice(0, senialesHeredadas.length - 1);
                }
                if (modificarContratoBusiness.resolverFamilia(modificarContratoSelector.getInput("tipoTitulo", "tituloConGrupoPopUp").val()) === ModificarContratoBusiness.SC) {
                    modificarContratoBusiness.modificarTituloContratadoSC(modificarContratoDrawer.buildTituloContratadoSC(senialesHeredadas));
                } else {
                    modificarContratoBusiness.modificarTituloContratadoCC(modificarContratoDrawer.buildTituloContratadoCC(senialesHeredadas));
                }
                $(this).dialog("close");
            },
            Cancelar: function() {
                $(this).dialog("close");
            }
        }
        });
};
ModificarContratoDrawer.prototype.drawPopUpTitulo = function(comp) {
    $("#popupContainer").empty().append(comp.replace(/{{id}}/g, modificarContratoDrawer.div.id));
    modificarContratoSelector.getPopUp("tituloPopUp").dialog({
        title : 'Titulo',
        width: 450,
        show: 'blind',
        hide: 'blind',
        modal: true,
        autoOpen: true,
        open: function() {
            if (modificarContratoDrawer.drawPopUpTitulo.row.clave.length >= 6) {
                origen = "T";
            } else {
                origen = "F";
            }
            ModificarContratoStaticService.doRequest(
                    {action:"dameTitulo.action",
                     request: Component.serialize(
                                 {contrato: modificarContratoBusiness.grupo.contrato,
                                  grupo: modificarContratoBusiness.grupo.nroGrupo,
                                  senial: modificarContratoBusiness.grupo.senial,
                                  clave: modificarContratoDrawer.drawPopUpTitulo.row.clave, 
                                  origen: origen,
                                  familia: modificarContratoBusiness.resolverFamilia(modificarContratoBusiness.grupo.tipoTitulo),
                                  sinContrato: modificarContratoDrawer.drawPopUpTitulo.row.sinContrato}, "tituloRequest"),
                     method: "GET",
                     callback: function(data) {
                          if (data) {
                              modificarContratoPopulator.populatePopUpTitulo(data);
                          }
                     },
                     responseObject: "titulo"});
        },
        close: function() {
            $(this).dialog("destroy");
            modificarContratoSelector.getPopUp("tituloPopUp").remove();
        },
        buttons: {
            "Aceptar": function() {
                $(this).dialog("close");
            },
            Cancelar: function() {
                $(this).dialog("close");
            }
        }
    });    
};

ModificarContratoDrawer.prototype.drawPopUpGrupo = function(comp) {
    var self = modificarContratoDrawer;
    $("#viewContainer").append(comp.replace(/{{id}}/g, self.div.id));
    
    var botonOk = function() {
        modificarContratoBusiness.titulosElegidos = [];
        modificarContratoBusiness.capitulosElegidos = [];
        
        if (!modificarContratoValidator.validateGrupo()) {
            return;
        };

        var senialesHeredadas = "";
        $("#ModificarContratoEventId_grupoPopUp_senialesHeredadas :checked").each(function() { 
            senialesHeredadas = senialesHeredadas + $(this).val() + ";";
        });
        if (senialesHeredadas !== "") {
            senialesHeredadas = senialesHeredadas.slice(0, senialesHeredadas.length - 1);
        }

        if (parseInt(modificarContratoSelector.getInput("cantTitulos", "grupoPopUp").val()) + parseInt(modificarContratoSelector.getInput("cantPasadas", "grupoPopUp").val()) > 9999) {
        	MESSAGE.alert("La suma de los campos 'CANT. ORIGINALES' y 'CANT. REPETICIONES' no puede superar los 4 dígitos");
        	return;
        }
        
        if (modificarContratoBusiness.grupo) {
            //MODIFICACION de grupo
            if (modificarContratoDrawer.drawPopUpGrupo.modificaCantidad) {
                //MODIFICACION de CANTIDAD
                modificarContratoBusiness.modificacionDeCantidadGrupo.diff = parseInt(modificarContratoSelector.getInput("cantTitulos", "grupoPopUp").val()) - modificarContratoBusiness.grupo.cantTitulos;
                modificarContratoBusiness.grupo.cantTitulos = parseInt(modificarContratoSelector.getInput("cantTitulos", "grupoPopUp").val());
                modificarContratoBusiness.grupo.modo = "A";
                modificarContratoBusiness.grupo.nroRelacionante = 0;
                modificarContratoValidator.validarModificacionDeCantidadGrupo(modificarContratoBusiness.grupo);
            } else if (modificarContratoDrawer.drawPopUpGrupo.modificaCantidad == false) {
                //MODIFICACION de GRUPO
                modificarContratoBusiness.grupo = modificarContratoDrawer.buildGrupo(senialesHeredadas);
                modificarContratoBusiness.grupo.modo = "M"; 
                modificarContratoValidator.validarModificacionDeGrupo(modificarContratoBusiness.grupo);
            } else {
                //VISUALIZACIONS
                return;
            }
        } else {
            //ALTA de grupo
            modificarContratoBusiness.cantTitulosAAgregar = parseInt(modificarContratoSelector.getInput("cantTitulos", "grupoPopUp").val()) || 0;
            modificarContratoBusiness.grupo = modificarContratoDrawer.buildGrupo(senialesHeredadas);
            if (modificarContratoBusiness.crearGrupo.modo !== "AR") {
                modificarContratoBusiness.grupo.modo = "A";
                modificarContratoBusiness.grupo.nroRelacionante = 0;
            } else {
                modificarContratoBusiness.grupo.modo = "AR";
                modificarContratoBusiness.grupo.nroRelacionante = modificarContratoBusiness.crearGrupo.nroRelacionante;
            }
            modificarContratoValidator.validarAltaDeGrupo(modificarContratoBusiness.grupo);
        }
        //$(this).dialog("close");
        //modificarContratoSelector.getPopUp("grupoPopUp").dialog("close");
    };
    
    var botonesGrupoPopup;
    
    if (modificarContratoBusiness.grupo && modificarContratoDrawer.drawPopUpGrupo.modificaCantidad == undefined)   { 
    	botonesGrupoPopup = {
            Salir: function() {
                $(this).dialog("close");
            }
        };
    } else {
    	botonesGrupoPopup = {
                "OK": botonOk,
                Salir: function() {
                    $(this).dialog("close");
                }
            };
    }
    
    
    modificarContratoSelector.getPopUp("grupoPopUp").dialog({
        title : 'Grupo',
        width: 900,
        show: 'blind',
        hide: 'blind',
        position: ["center", "top"],
        modal: true,
        autoOpen: true,
        open: function() {
            delete modificarContratoBusiness.grupo;
            modificarContratoSelector.getInput("pasaLibreria", "grupoPopUp").val("N");
            modificarContratoSelector.getInput("tipoTitulo", "grupoPopUp").bind('change', function () {
                if (modificarContratoBusiness.resolverFamilia($(this).val()) === ModificarContratoBusiness.SC) {
                    //SIN CAPITULO
                    modificarContratoSelector.getLabel("cantTitulos","grupoPopUp").html("CANT.TITULOS");
                    modificarContratoSelector.getLabel("cantPasadas","grupoPopUp").html("PASADAS CONTRATADAS");
                    modificarContratoSelector.getLabel("costo","grupoPopUp").html("COSTO UNITARIO");
                    modificarContratoSelector.getInput("recontratacion", "grupoPopUp").hide();
                    modificarContratoSelector.getLabel("recontratacion", "grupoPopUp").hide();
                } else {
                    //CON CAPITULO
                    modificarContratoSelector.getLabel("cantTitulos","grupoPopUp").html("CANT. ORIGINALES");
                    modificarContratoSelector.getLabel("cantPasadas","grupoPopUp").html("CANT. REPETICIONES");
                    modificarContratoSelector.getLabel("costo","grupoPopUp").html("COSTO TOTAL");
                    modificarContratoSelector.getInput("recontratacion", "grupoPopUp").show();
                    modificarContratoSelector.getLabel("recontratacion", "grupoPopUp").show();
                };
            });
           
            modificarContratoSelector.getInput("tipoTitulo", "grupoPopUp").focus();
            Component.populateSelect(modificarContratoSelector.getInput("tipoTitulo", "grupoPopUp"), modificarContratoBusiness.tiposDeTitulo, "tipoTitulo", "descripcionTitulo");
            Component.populateSelect(modificarContratoSelector.getInput("tipoImporte", "grupoPopUp"), modificarContratoBusiness.tiposDeImporte, "codigo", "descripcion");
            Component.populateSelect(modificarContratoSelector.getInput("codigoDerechos", "grupoPopUp"), modificarContratoBusiness.tiposDeDerecho, "codigo", "descripcion");
            Component.populateSelect(modificarContratoSelector.getInput("codigoDerechosTerritoriales", "grupoPopUp"), modificarContratoBusiness.tiposDeDerechoTerritorial, "codigo", "descripcion");

            modificarContratoSelector.getInput("cantTitulos", "grupoPopUp").bind('keypress', function (e) {
                return !(e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57) && e.which != 46);
            });
            
            Datepicker.picker(modificarContratoSelector.getInput("fechaDerechos", "grupoPopUp"));
            Datepicker.picker(modificarContratoSelector.getInput("fechaEntrega", "grupoPopUp"));
            Datepicker.picker(modificarContratoSelector.getInput("fechaComienzoDerechosLibreria", "grupoPopUp"));
            var sen;
            
            if (modificarContratoBusiness.contratoConCabecera.grupoElegido) {
                //entonces el grupo existente
                sen = modificarContratoBusiness.contratoConCabecera.senialElegida;
                ModificarContratoStaticService.doRequest(
                        {action:"dameGrupo.action",
                         request: {claveContrato: modificarContratoBusiness.contratoConCabecera.contrato, 
                                   senial: sen, 
                                   claveGrupo: modificarContratoBusiness.contratoConCabecera.grupoElegido},
                         method: "GET",
                         callback: function(data) {
                              if (data) {
                                  modificarContratoBusiness.grupo = data;
                                  modificarContratoPopulator.populatePopUpGrupo(data);
                                  if (modificarContratoDrawer.drawPopUpGrupo.modificaCantidad === undefined) {
                                      Component.disable($("#"+modificarContratoDrawer.div.id+"_grupoPopUp_form :input"));
                                  } else if (modificarContratoDrawer.drawPopUpGrupo.modificaCantidad) {
                                      modificarContratoBusiness.cantTitulosAAgregar = modificarContratoBusiness.grupo.cantTitulos;
                                  }
                              } else {
                                  MESSAGE.error("Error al buscar el grupo");
                              }
                         },
                         responseObject: "grupo"});
                
                if (modificarContratoDrawer.drawPopUpGrupo.modificaCantidad === undefined) {
                    //Solo si se trata de una visualizacion
                    modificarContratoBusiness.cantTitulosAAgregar = undefined;
                    modificarContratoDrawer.drawGrillaTitulos();
                    ModificarContratoStaticService.doRequest(
                            {action:"dameTitulosPorContrato.action", 
                             request: {"titulosRequest.contrato": modificarContratoBusiness.contratoConCabecera.contrato, 
                                       "titulosRequest.grupo": modificarContratoBusiness.contratoConCabecera.grupoElegido, 
                                       "titulosRequest.senial": modificarContratoBusiness.contratoConCabecera.senialElegida,
                                       "titulosRequest.tituloABuscar": modificarContratoDrawer.drawPopUpTituloConGrupo.row != undefined ? modificarContratoDrawer.drawPopUpTituloConGrupo.row.clave : "",
                                       "titulosRequest.buscarPor": $("#ModificarContratoEventId_grupoPopUp_grillaDeTitulosContainer :checked").first().val()},
                             method: "GET",
                             callback: function(data) {
                                 if (data && data.length) {
                                     modificarContratoPopulator.populateGrillaTitulos(data);
                                 } else {
                                     //MESSAGE.alert("El grupo no tiene titulos");
                                 }
                             },
                             responseObject: "titulosContratados"});
                }
            } else {
                //entonces es un grupo nuevo
                sen = modificarContratoBusiness.contratoConCabecera.senialElegida;
                ModificarContratoStaticService.doRequest(
                {action:"dameSenialesHeredadas.action",
                request: {senial: sen},
                method: "GET",
                callback: function(data) {
                    if (data) {
                        for (var i in data) {
                            $("#senialHeredadaCheck"+data[i].codigo).prop('checked', true);
                        }
                    } else {
                        MESSAGE.error("Ocurrio un error al obtener las seniales heredadas");
                    }
                },
                responseObject: "senialesHeredadas"});

                ModificarContratoStaticService.doRequest(
                        {action:"dameSiguienteNumeroGrupo.action",
                         request: {claveContrato: modificarContratoBusiness.contratoConCabecera.contrato, 
                                   senial: modificarContratoBusiness.contratoConCabecera.senialElegida},
                         method: "GET",
                         callback: function(data) {
                              if (data) {
                                  modificarContratoSelector.getInput("nroGrupo", "grupoPopUp").val(data).attr('disabled','disabled');
                              } else {
                                  MESSAGE.error("Ocurrio un error al calcular el nro del nuevo grupo");
                              }
                         },
                         responseObject: "nroGrupoResponse"});

                modificarContratoSelector.getInput("contrato", "grupoPopUp").val(modificarContratoBusiness.contratoConCabecera.contrato).attr('disabled','disabled');
                modificarContratoSelector.getInput("distribuidor", "grupoPopUp").val(modificarContratoBusiness.contratoConCabecera.distribuidor).attr('disabled','disabled');
                modificarContratoSelector.getInput("senial", "grupoPopUp").val(modificarContratoBusiness.contratoConCabecera.senialElegida).attr('disabled','disabled');
                modificarContratoSelector.getInput("montoSenial", "grupoPopUp").val(modificarContratoBusiness.contratoConCabecera.importeSenialElegida).attr('disabled','disabled');
            }
            
            var senialesAct = [];
            for(var i = 0; i < seniales.length; i++) {
                if (seniales[i].codigo != sen)
                    senialesAct.push(seniales[i]);                
            }
            
            Component.populateCheckBoxes(modificarContratoSelector.getInput("senialesHeredadas", "grupoPopUp"), senialesAct, "codigo", "descripcion", "senialHeredadaCheck");
       },
        close: function() {
            delete modificarContratoBusiness.contratoConCabecera.grupoElegido;
            //$(this).dialog("destroy");
            modificarContratoSelector.getPopUp("grupoPopUp").remove();
            modificarContratoPopulator.populateGrillaDeGrupos(modificarContratoBusiness.contratoConCabecera.senialElegida);
            
            if (modificarContratoBusiness.trabjarConVigenciasDesdeMaestros != undefined) {
                ModificarContratoStaticService.doRequest(
                        {action:"desbloquearContrato.action",
                            request: {claveContrato: modificarContratoBusiness.contratoConCabecera.contrato},
                            method: "GET",
                            callback: function() {
                                return;
                            } 
                        });
            }

        },
        buttons: botonesGrupoPopup
        });
};

ModificarContratoDrawer.prototype.buildGrupo = function(senialesHeredadas) {
    return {
        contrato: modificarContratoSelector.getInput("contrato", "grupoPopUp").val(),
        distribuidor: modificarContratoSelector.getInput("distribuidor", "grupoPopUp").val(),
        senial: modificarContratoSelector.getInput("senial", "grupoPopUp").val(),
        nroGrupo: parseInt(modificarContratoSelector.getInput("nroGrupo", "grupoPopUp").val()) || 0,
        tipoTitulo: modificarContratoSelector.getInput("tipoTitulo", "grupoPopUp").val(),
        senialHeredada: senialesHeredadas,
        nombreGrupo: modificarContratoSelector.getInput("nombreGrupo", "grupoPopUp").val(),
        cantTitulos: parseInt(modificarContratoSelector.getInput("cantTitulos", "grupoPopUp").val()) || 0,
        cantPasadas: parseInt(modificarContratoSelector.getInput("cantPasadas", "grupoPopUp").val()) || 0,
        tipoImporte: modificarContratoSelector.getInput("tipoImporte", "grupoPopUp").val(),
        costoTotal: modificarContratoSelector.getInput("costoTotal", "grupoPopUp").val() == null ? 0 : parseFloat(modificarContratoSelector.getInput("costoTotal", "grupoPopUp").val().replace(",",".")),
        er: modificarContratoSelector.getInput("er", "grupoPopUp").val(),
        fechaDerechos: modificarContratoSelector.getInput("fechaDerechos", "grupoPopUp").val(),
        fechaEntrega: modificarContratoSelector.getInput("fechaEntrega", "grupoPopUp").val(),
        codigoDerechos: modificarContratoSelector.getInput("codigoDerechos", "grupoPopUp").val(),
        cantTiempo: parseInt(modificarContratoSelector.getInput("cantTiempo", "grupoPopUp").val()) || 0,
        unidadDeTiempo: modificarContratoSelector.getInput("unidadDeTiempo", "grupoPopUp").val(),
        codigoDerechosTerritoriales: modificarContratoSelector.getInput("codigoDerechosTerritoriales", "grupoPopUp").val(),
        descripcionDerechosTerritoriales: modificarContratoSelector.getInput("codigoDerechosTerritoriales", "grupoPopUp").val(),
        marcaPerpetuidad: modificarContratoSelector.getInput("marcaPerpetuidad", "grupoPopUp").val(),
        planEntrega: modificarContratoSelector.getInput("planEntrega", "grupoPopUp").val(),
        recontratacion: modificarContratoSelector.getInput("recontratacion", "grupoPopUp").val(),
        cantTiempoExclusivo: parseInt(modificarContratoSelector.getInput("cantTiempoExclusivo", "grupoPopUp").val()) || 0,
        unidadTiempoExclusivo: modificarContratoSelector.getInput("unidadTiempoExclusivo", "grupoPopUp").val(),
        autorizacionAutor: modificarContratoSelector.getInput("autorizacionAutor", "grupoPopUp").val(),
        pagaArgentores: modificarContratoSelector.getInput("pagaArgentores", "grupoPopUp").val(),
        pasaLibreria: modificarContratoSelector.getInput("pasaLibreria", "grupoPopUp").val(),
        fechaComienzoDerechosLibreria: modificarContratoSelector.getInput("fechaComienzoDerechosLibreria", "grupoPopUp").val()
    };
};
ModificarContratoDrawer.prototype.buildTituloContratadoCC = function(senialesHeredadas) {
    return {
        contrato: modificarContratoSelector.getInput("contrato", "tituloConGrupoPopUp").val(),
        distribuidor: modificarContratoSelector.getInput("distribuidor", "tituloConGrupoPopUp").val(),
        senial: modificarContratoBusiness.contratoConCabecera.senialElegida,
        senialHeredada: modificarContratoSelector.getInput("senial", "tituloConGrupoPopUp").val(),
        nroGrupo: parseInt(modificarContratoSelector.getInput("nroGrupo", "tituloConGrupoPopUp").val()) || 0,
        nombreGrupo: modificarContratoSelector.getInput("nombreGrupo", "tituloConGrupoPopUp").val(),
        tipoTitulo: modificarContratoSelector.getInput("tipoTitulo", "tituloConGrupoPopUp").val(),
        clave: modificarContratoSelector.getInput("clave", "tituloConGrupoPopUp").val(),
        costoTotal: modificarContratoSelector.getInput("costoTotal", "tituloConGrupoPopUp").val() == null ? 0 : parseFloat(modificarContratoSelector.getInput("costoTotal", "tituloConGrupoPopUp").val().replace(",",".")),
        cantPasadas: parseInt(modificarContratoSelector.getInput("cantPasadas", "tituloConGrupoPopUp").val()) || 0,
        recontratacion: modificarContratoSelector.getInput("recontratacion", "tituloConGrupoPopUp").val(),
        fechaEntrega: modificarContratoSelector.getInput("fechaEntrega", "tituloConGrupoPopUp").val(),
        pasaLibreria: modificarContratoSelector.getInput("pasaLibreria", "tituloConGrupoPopUp").val(),
        fechaComienzoDerechosLibreria: modificarContratoSelector.getInput("fechaComienzoDerechosLibreria", "tituloConGrupoPopUp").val(),
        fechaDerechos: modificarContratoSelector.getInput("fechaDerechos", "tituloConGrupoPopUp").val(),
        marcaPerpetuidad: modificarContratoSelector.getInput("marcaPerpetuidad", "tituloConGrupoPopUp").val(),
        cantTiempoExclusivo: parseInt(modificarContratoSelector.getInput("cantTiempoExclusivo", "tituloConGrupoPopUp").val()) || 0,
        unidadTiempoExclusivo: modificarContratoSelector.getInput("unidadTiempoExclusivo", "tituloConGrupoPopUp").val(),
        codigoDerechos: modificarContratoSelector.getInput("codigoDerechos", "tituloConGrupoPopUp").val(),
        codigoDerechosTerritoriales: modificarContratoSelector.getInput("codigoDerechosTerritoriales", "tituloConGrupoPopUp").val(),
        observaciones: modificarContratoSelector.getInput("observaciones", "tituloConGrupoPopUp").val(),
        cantTiempo: parseInt(modificarContratoSelector.getInput("cantTiempo", "tituloConGrupoPopUp").val()) || 0,
        unidadDeTiempo: modificarContratoSelector.getInput("unidadDeTiempo", "tituloConGrupoPopUp").val(),
        autorizacionAutor: modificarContratoSelector.getInput("autorizacionAutor", "tituloConGrupoPopUp").val(),
        pagaArgentores: modificarContratoSelector.getInput("pagaArgentores", "tituloConGrupoPopUp").val()
    };
};
ModificarContratoDrawer.prototype.buildTituloContratadoSC = function(senialesHeredadas) {
    return {
        contrato: modificarContratoSelector.getInput("contrato", "tituloConGrupoPopUp").val(),
        distribuidor: modificarContratoSelector.getInput("distribuidor", "tituloConGrupoPopUp").val(),
        senial: modificarContratoBusiness.contratoConCabecera.senialElegida,
        senialHeredada: senialesHeredadas,
        nroGrupo: parseInt(modificarContratoSelector.getInput("nroGrupo", "tituloConGrupoPopUp").val()) || 0,
        nombreGrupo: modificarContratoSelector.getInput("nombreGrupo", "tituloConGrupoPopUp").val(),
        tipoTitulo: modificarContratoSelector.getInput("tipoTitulo", "tituloConGrupoPopUp").val(),
        tipoImporte: modificarContratoSelector.getInput("tipoImporte", "tituloConGrupoPopUp").val(),
        clave: modificarContratoSelector.getInput("clave", "tituloConGrupoPopUp").val(),
        fechaEntrega: modificarContratoSelector.getInput("fechaEntrega", "tituloConGrupoPopUp").val(),
        codigoDerechosTerritoriales: modificarContratoSelector.getInput("codigoDerechosTerritoriales", "tituloConGrupoPopUp").val(),
        observaciones: modificarContratoSelector.getInput("observaciones", "tituloConGrupoPopUp").val(),
        cantPasadas: parseInt(modificarContratoSelector.getInput("cantPasadas", "tituloConGrupoPopUp").val()) || 0,
        pasaLibreria: modificarContratoSelector.getInput("pasaLibreria", "tituloConGrupoPopUp").val(),
        fechaDerechos: modificarContratoSelector.getInput("fechaDerechos", "tituloConGrupoPopUp").val(),
        cantTiempo: parseInt(modificarContratoSelector.getInput("cantTiempo", "tituloConGrupoPopUp").val()) || 0,
        unidadDeTiempo: modificarContratoSelector.getInput("unidadDeTiempo", "tituloConGrupoPopUp").val(),
        codigoDerechos: modificarContratoSelector.getInput("codigoDerechos", "tituloConGrupoPopUp").val(),
        autorizacionAutor: modificarContratoSelector.getInput("autorizacionAutor", "tituloConGrupoPopUp").val(),
        pagaArgentores: modificarContratoSelector.getInput("pagaArgentores", "tituloConGrupoPopUp").val(),
        marcaPerpetuidad: modificarContratoSelector.getInput("marcaPerpetuidad", "tituloConGrupoPopUp").val(),
        fechaComienzoDerechosLibreria: modificarContratoSelector.getInput("fechaComienzoDerechosLibreria", "tituloConGrupoPopUp").val(),
        costoTotal: modificarContratoSelector.getInput("costoTotal", "tituloConGrupoPopUp").val() == null ? 0 : parseFloat(modificarContratoSelector.getInput("costoTotal", "tituloConGrupoPopUp").val().replace(",","."))
    };
};
ModificarContratoDrawer.prototype.drawGrillaDeSenialesEImportes = function() {
    modificarContratoSelector.getGrillaSenialesEImportes().jqGrid({
        height: 'auto',
        autowidth: true,
        datatype: 'local',
        colNames:['Codigo Senial','Descripcion','Importe','Modif.','Eliminar','Grupos'],
        colModel:[ 
            {name:'codigoSenial',index:'codigoSenial', width:150, sortable:false}, 
            {name:'descripcionSenial',index:'descripcionSenial', width:300, sortable:false},
            {name:'importe',index:'importe', width:150, sortable:false, formatter:'currency'},
            {name:'modificar',index:'modificar', width:50, sortable:false},
            {name:'eliminar',index:'eliminar', width:50, sortable:false},
            {name:'grupos',index:'grupos', width:50, sortable:false}
        ],
        rowNum: 10,
        rowList:[10,20,30],
        scrollOffset: 0,
        pager: '#ModificarContratoEventId_contratoConCabeceraPopUp_pagerGrillaSenialesEimportes',
        viewrecords: true, 
        loadonce: true,
        editurl: 'clientArray', 
        caption: 'Seniales',
        ondblClickRow: function(id){
            return;
        },
        gridComplete: function() {
            var ids = $(this).jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var rowId = ids[i];
                var eliminar = "<center><div onclick='modificarContratoBusiness.eliminarSenialImporte("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-trash eliminar conTooltip'>Eliminar</span></div></center>";
                $(this).jqGrid('setRowData', rowId, { eliminar: eliminar });
                var modificar = "<center><div onclick='modificarContratoBusiness.modificarSenialImporte("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-pencil modificar conTooltip'>Modificar</span></div></center>";
                $(this).jqGrid('setRowData', rowId, { modificar: modificar });
                var grupos = "<center><div onclick='modificarContratoBusiness.trabajarConGrupos("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-search visualizar conTooltip'>Visualizar</span></div></center>";
                $(this).jqGrid('setRowData', rowId, { grupos: grupos });
            }
            return;
        }
    })
    .navGrid('#ModificarContratoEventId_contratoConCabeceraPopUp_pagerGrillaSenialesEimportes',{edit:false,add:false,del:false,search:false,refresh:false})
    .navButtonAdd('#ModificarContratoEventId_contratoConCabeceraPopUp_pagerGrillaSenialesEimportes',{
            caption:"Agregar Senial", 
            buttonicon:"ui-icon-add", 
            onClickButton: modificarContratoBusiness.agregarSenial, 
            position:"last"
    });
};
ModificarContratoDrawer.prototype.drawGrillaDeGrupos = function() {
    modificarContratoSelector.getGrillaGrupos().jqGrid({
        height: 'auto',
        autowidth: true,
        datatype: 'local',
        colNames:['Grupo','Tipo','Nombre','Importe','E/R','Señal H.','Modif.','Cant.','Eliminar','Ver Titulos', 'Reemplazar Titulos','Reemplazar Grupo'],
        colModel:[ 
            {name:'nroGrupo',index:'nroGrupo', width:40, sortable:false}, 
            {name:'tipoTitulo',index:'tipoTitulo', width:45, sortable:false},
            {name:'nombreGrupo',index:'nombreGrupo', width:180, sortable:false},
            {name:'costoTotal',index:'costoTotal', width:70, sortable:false, formatter:'currency'},
            {name:'er',index:'er', width:30, sortable:false},
            {name:'senialHeredada',index:'senialHeredada', width:50, sortable:false},
            {name:'modificarGrupo',index:'modificarGrupo', width:40, sortable:false},
            {name:'modificarCantidad',index:'modificarCantidad', width:50, sortable:false},
            {name:'eliminarGrupo',index:'eliminarGrupo', width:50, sortable:false},
            {name:'verTitulos',index:'verTitulos', width:80, sortable:false},
            {name:'reemplazarTitulos',index:'reemplazarTitulos', width:120, sortable:false},
            {name:'reemplazarGrupo',index:'reemplazarGrupo', width:120, sortable:false}
        ],
        rowNum: 10,
        rowList:[10,20,30],
        scrollOffset: 0,
        pager: '#ModificarContratoEventId_contratoConCabeceraPopUp_pagerGrillaGrupos',
        viewrecords: true, 
        loadonce: true,
        editurl: 'clientArray', 
        caption: 'Grupos',
        ondblClickRow: function(id){
            return;
        },
        gridComplete: function() {
            var ids = $(this).jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var rowId = ids[i];
                var eliminarGrupo = "<center><div onclick='modificarContratoBusiness.eliminarGrupo("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-trash eliminar conTooltip'>Eliminar</span></div></center>";
                $(this).jqGrid('setRowData', rowId, { eliminarGrupo: eliminarGrupo });
                var modificarGrupo = "<center><div onclick='modificarContratoBusiness.modificarGrupo("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-pencil modificar conTooltip'>Modificar</span></div></center>";
                $(this).jqGrid('setRowData', rowId, { modificarGrupo: modificarGrupo });
                var modificarCantidad = "<center><div onclick='modificarContratoBusiness.modificarCantidad("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-pencil modificar conTooltip'>Modificar Cantidad</span></div></center>";
                $(this).jqGrid('setRowData', rowId, { modificarCantidad: modificarCantidad });
                var verTitulos = "<center><div onclick='modificarContratoBusiness.verTitulos("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-search visualizar conTooltip'>Visualizar</span></div></center>";
                $(this).jqGrid('setRowData', rowId, { verTitulos: verTitulos });
                var reemplazarTitulos = "<center><div onclick='modificarContratoBusiness.reemplazarTitulos("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-pencil modificar conTooltip'>ReemplazarTitulos</span></div></center>";
                $(this).jqGrid('setRowData', rowId, { reemplazarTitulos: reemplazarTitulos });
                var reemplazarGrupo = "<center><div onclick='modificarContratoBusiness.reemplazarGrupo("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-pencil modificar conTooltip'>ReemplazarGrupo</span></div></center>";
                $(this).jqGrid('setRowData', rowId, { reemplazarGrupo: reemplazarGrupo });
            }
            return;
        }
    });
    modificarContratoSelector.getGrillaGrupos().jqGrid()
            .navGrid('#ModificarContratoEventId_contratoConCabeceraPopUp_pagerGrillaGrupos', {edit:false,add:false,del:false,search:false,refresh:false})
            .navButtonAdd('#ModificarContratoEventId_contratoConCabeceraPopUp_pagerGrillaGrupos',
            {
                caption:"Agregar Grupo", 
                buttonicon:"ui-icon-add", 
                onClickButton: modificarContratoBusiness.crearGrupo,
                position:"first"
            });

};
ModificarContratoDrawer.prototype.drawGrillaTitulos = function() {
    var obj = {htmlObject: "input", type: "radio", id:"grillaDeTitulosPorVigencia", values: ["vigentes", "todos"]};
    switch (obj.htmlObject) {
        case "input":
            for (var j in obj.values) {
                modificarContratoSelector.getGrillaTitulos().before($("<label>").text(obj.values[j]));
                var radio = $('<'+obj.htmlObject+'>').attr({type: obj.type, id: obj.id+"_"+obj.values[j], value: obj.values[j], style:"display: inline-block", name: "buscarPor"});
                modificarContratoSelector.getGrillaTitulos().before(radio);
                radio.bind('click', function(e) { 
                    ModificarContratoStaticService.doRequest(
                            {action:"dameTitulosPorContrato.action", 
                             request: {"titulosRequest.contrato": modificarContratoBusiness.contratoConCabecera.contrato, 
                                       "titulosRequest.grupo": modificarContratoBusiness.contratoConCabecera.grupoElegido, 
                                       "titulosRequest.senial": modificarContratoBusiness.contratoConCabecera.senialElegida,
                                       "titulosRequest.tituloABuscar": modificarContratoDrawer.drawPopUpTituloConGrupo.row != undefined ? modificarContratoDrawer.drawPopUpTituloConGrupo.row.clave : "",
                                       "titulosRequest.buscarPor": $("#ModificarContratoEventId_grupoPopUp_grillaDeTitulosContainer :checked").first().val()},
                             method: "GET",
                             callback: function(data) {
                                 if (data && data.length) {
                                     modificarContratoPopulator.populateGrillaTitulos(data);
                                 } else {
                                     MESSAGE.alert("El grupo no tiene titulos");
                                 }
                             },
                             responseObject: "titulosContratados"});
                    });
            }
            $("#grillaDeTitulosPorVigencia_vigentes").attr("checked", "checked");
            break;
        default:
            break;
    }
    
    modificarContratoSelector.getGrillaTitulos().jqGrid({
        height: 'auto',
        autowidth: true,
        datatype: 'local',
        colNames:['Clave','Titulo','Recibido','Conf.Exhibido','A Consutar','ReRun','StandBy','ReRun','Modif','Capitulos','Vigencias','Desmarcar', 'T.Costeo', ''],
        colModel:[ 
            {name:'clave',index:'clave', width:70, sortable:false}, 
            {name:'tituloCastellano',index:'tituloCastellano', width:230, sortable:false},
            {name:'recibido',index:'recibido', width:70, sortable:false},
            {name:'exhibicionConfirmada',index:'exhibicionConfirmada', width:70, sortable:false},
            {name:'aConsultar',index:'aConsultar', width:70, sortable:false},
            {name:'reRun',index:'reRun', width:70, sortable:false, hidden:true},
            {name:'standby',index:'standby', width:70, sortable:false},
            {name:'trabajarReRun',index:'trabajarReRun', width:70, sortable:false},
            {name:'modificar',index:'modificar', width:40, sortable:false},
            {name:'capitulos',index:'capitulos', width:70, sortable:false},
            {name:'vigencias',index:'vigencias', width:70, sortable:false},
            {name:'desmarcar',index:'desmarcar', width:70, sortable:false},
            {name:'tipoCosteo',index:'tipoCosteo', width:70, sortable:false, align:"center"},            
            {name:'canje',index:'canje', hidden:true}            
        ],
        rowNum: 10,
        rowList:[10,20,30],
        scrollOffset: 0,
        pager: '#ModificarContratoEventId_grupoPopUp_pagerGrillaDeTitulos',
        viewrecords: true, 
        loadonce: true,
        editurl: 'clientArray', 
        caption: 'Titulos',
        ondblClickRow: function(id){
            return;
        },
        gridComplete: function() {
            var ids = $(this).jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var rowId = ids[i];
                var row = $(this).jqGrid('getRowData', rowId);
                var foo;
                
                if (row.aConsultar === "S") {
                    foo = "<center><div><span class='ui-icon ui-icon-check conTooltip'>A Consultar</span></div></center>";
                } else if (row.aConsultar === "N") {
                    foo = "<center><div><span class='ui-icon ui-icon-close conTooltip'>A Consultar</span></div></center>";
                }
                $(this).jqGrid('setRowData', rowId, { aConsultar: foo });
                
                if (row.recibido === "S") {
                    foo = "<center><div><span class='ui-icon ui-icon-check conTooltip'>Recibido</span></div></center>";
                } else {
                    foo = "<center><div><span class='ui-icon ui-icon-close conTooltip'>Recibido</span></div></center>";
                }
                $(this).jqGrid('setRowData', rowId, { recibido: foo });
                
                if (row.exhibicionConfirmada === "S") {
                    foo = "<center><div><span class='ui-icon ui-icon-check conTooltip'>Exhib. Confirmada</span></div></center>";
                } else {
                    foo = "<center><div><span class='ui-icon ui-icon-close conTooltip'>Exhib. Confirmada</span></div></center>";
                }
                $(this).jqGrid('setRowData', rowId, { exhibicionConfirmada: foo });
                
                if (row.standby === "S") {
                    foo = "<center><div><span class='ui-icon ui-icon-check conTooltip'>standby</span></div></center>";
                } else {
                    foo = "<center><div><span class='ui-icon ui-icon-close conTooltip'>standby</span></div></center>";
                }
                $(this).jqGrid('setRowData', rowId, { standby: foo });

                var claveTitulo = modificarContratoSelector.getGrillaTitulos().jqGrid('getRowData', rowId).clave;
                var estiloDelIconoReRun = "";
                if (row.reRun === "S") {
                    estiloDelIconoReRun = "ui-icon ui-icon-pencil";
                } else {
                    estiloDelIconoReRun = "ui-icon ui-icon-close";
                }
                if (row.aConsultar === "N") {
                    if (modificarContratoBusiness.trabjarConVigenciasDesdeMaestros) {
                        foo = "<center><div onclick='modificarContratoBusiness.trabajarReRun(\""+claveTitulo+"\",\"T\",\"M\")' style='cursor:pointer'><span class='"+estiloDelIconoReRun+" modificar conTooltip'>trabajarReRun</span></div></center>";
                    } else {
                        foo = "<center><div onclick='modificarContratoBusiness.trabajarReRun(\""+claveTitulo+"\",\"C\",\"M\")' style='cursor:pointer'><span class='"+estiloDelIconoReRun+" modificar conTooltip'>trabajarReRun</span></div></center>";
                    }
                } else {
                    foo = "<center><div style='cursor:pointer'><span class='"+estiloDelIconoReRun+" conTooltip'>trabajarReRun</span></div></center>";
                }
                $(this).jqGrid('setRowData', rowId, { trabajarReRun: foo });
                
                foo = "<center><div onclick='modificarContratoBusiness.modificarTitulo("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-pencil modificar conTooltip'>modificarTitulo</span></div></center>";
                $(this).jqGrid('setRowData', rowId, { modificar: foo });
                
                if (modificarContratoBusiness.resolverFamilia(row.clave.substring(0,2)) === ModificarContratoBusiness.CC) {
                    foo  = "<center><div onclick='modificarContratoBusiness.verCapitulos("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-search visualizar conTooltip'>verCapitulos</span></div></center>";
                } else {
                    foo = "<center><div><span class='ui-icon ui-icon-close conTooltip'>Sin Capitulos</span></div></center>";
                }
                $(this).jqGrid('setRowData', rowId, { capitulos: foo });
                
                var trabjarConVigencias;
                if (modificarContratoBusiness.trabjarConVigenciasDesdeMaestros) {
                     trabjarConVigencias = "<center><div onclick='modificarContratoBusiness.trabjarConVigencias("+rowId+", \"T\")' style='cursor:pointer'><span class='ui-icon ui-icon-pencil visualizar conTooltip'>trabjarConVigencias</span></div></center>";
                } else {
                     trabjarConVigencias = "<center><div onclick='modificarContratoBusiness.trabjarConVigencias("+rowId+", \"C\")' style='cursor:pointer'><span class='ui-icon ui-icon-pencil visualizar conTooltip'>trabjarConVigencias</span></div></center>";
                }
                $(this).jqGrid('setRowData', rowId, { vigencias: trabjarConVigencias });
                
                var desmarcarTitulo = "<center><div onclick='modificarContratoValidator.validarDesmarcarTitulo("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-pencil modificar conTooltip'>desmarcarTitulo</span></div></center>";
                $(this).jqGrid('setRowData', rowId, { desmarcar: desmarcarTitulo });
                
                foo = "<center><div><span title='No existen datos cargados'></span></div></center>";
                if (row.tipoCosteo === "E") {
                	console.log(modificarContratoDrawer.editarTipoCosteo);
                    foo = "<a href=\"#\" style=\"color: blue\" title='Editar Tipo Excedente' onclick='modificarContratoDrawer.editarTipoCosteoExcedente(\""+row.clave+"\" , \""+row.canje+"\")'>E</a>";
                } else if (row.tipoCosteo === "R") {
                	foo = "<a href=\"#\" style=\"color: blue\" title='Editar Tipo Rating' onclick='modificarContratoDrawer.editarTipoCosteoRating(\""+row.clave+"\" , \""+row.canje+"\")'>R</a>";
//                	foo = "<center><div onclick='modificarContratoDrawer.editarTipoCosteoRating(\""+row.clave+"\" , \""+row.canje+"\")'><span title='Editar Tipo Rating'>R</span></div></center>";
                } else if (row.tipoCosteo === "CFM") {
                	foo = "<a href=\"#\" style=\"color: blue\" title='Editar Costo Fijo Mensual' onclick='modificarContratoDrawer.editarTipoCosteoCFM(\""+row.clave+"\" , \""+row.canje+"\")'>CFM</a>";
//                	foo = "<center><div onclick='modificarContratoDrawer.editarTipoCosteoCFM(\""+row.clave+"\" , \""+row.canje+"\")'><span title='Editar Costo Fijo Mensual'>CFM</span></div></center>";
                } else if (row.tipoCosteo === "MIXTO") {
                	foo = "<a href=\"#\" style=\"color: blue\" title='Editar Tipo MIXTO' onclick='modificarContratoDrawer.editarTipoCosteoMixto(\""+row.clave+"\" , \""+row.canje+"\")'>Mixto</a>";
//                	foo = "<center><div onclick='modificarContratoDrawer.editarTipoCosteoMixto(\""+row.clave+"\" , \""+row.canje+"\")'><span title='Editar Tipo MIXTO'>Mixto</span></div></center>";
	            } else if (row.tipoCosteo === "A") {
	            	foo = "<a href=\"#\" style=\"color: blue\" title='Definir Tipo de Costeo' onclick='modificarContratoDrawer.altaTipoCosteo(\""+row.clave+"\" , \""+row.canje+"\")'>Agregar</a>";
//	            	foo = "<center><div onclick='modificarContratoDrawer.altaTipoCosteo(\""+row.clave+"\" , \""+row.canje+"\")'><span title='Definir Tipo de Costeo'>Agregar</span></div></center>";
	            }
                $(this).jqGrid('setRowData', rowId, { tipoCosteo: foo });
//            		var valor1 = that.get("tGrillaCFM").jqGrid("getCell", rowId, "anioMes");
//            		var valor2 = that.get("tGrillaCFM").jqGrid("getCell", rowId, "valor");
//            		var descripcion = that.get("tGrillaCFM").jqGrid("getCell", rowId, "valor");
//            		var idObject = that.prefijoId+"-idLink-"+valor1;
//            		var link = "<span id='"+idObject+"'  class='ui-icon ui-icon-trash' href=# style=\"color: blue; cursor: pointer;\" title=\"ELIMINAR REGISTRO\"></span>";
//            		$(this).jqGrid('setRowData', rowId, { eliminarCFM: link });
//            		$("#"+idObject).unbind("click").bind("click",$.proxy(that.eliminarRegistro, that, valor1, valor2));
            }
            return;
        }
    });
};
ModificarContratoDrawer.prototype.editarTipoCosteoCFM = function(clave, canje) {
	console.log("Editar cfm");
	var contrato=modificarContratoBusiness.grupo.contrato;
	var senial=modificarContratoBusiness.grupo.senial;
	var tipoTitulo =modificarContratoBusiness.grupo.tipoTitulo;
	var nroGrupo = modificarContratoBusiness.grupo.nroGrupo;
	var strClave = clave//"SE010799";
	var titulo = strClave.replace(tipoTitulo, "");
	CostoFijoMensualEvent.initModificar("popupModificarTipoDeCosteo", contrato, senial, nroGrupo, tipoTitulo, titulo, canje);
};
ModificarContratoDrawer.prototype.editarTipoCosteoExcedente = function(clave, canje) {
	console.log("Editar Excedente");
	var contrato=modificarContratoBusiness.grupo.contrato;
	var senial=modificarContratoBusiness.grupo.senial;
	var tipoTitulo =modificarContratoBusiness.grupo.tipoTitulo;
	var nroGrupo = modificarContratoBusiness.grupo.nroGrupo;
	var strClave = clave//"SE010799";
	var titulo = strClave.replace(tipoTitulo, "");
	//divContainer popupModificarTipoDeCosteo
	//params divContainer, contrato, senial, nroGrupo, tipoTilulo, titulo, canje
	CosteoExcedenteEvent.initModificar("popupModificarTipoDeCosteo", contrato, senial, nroGrupo, tipoTitulo, titulo, canje);
//	console.log("numero de grupo : "+nroGrupo);
//	console.log("tipo titulo : "+tipoTitulo);
//	console.log("titulo : "+titulo);
//	console.log("senial : "+senial);
//	console.log("contrato : "+contrato);
//	console.log("canje : "+canje);
};
ModificarContratoDrawer.prototype.editarTipoCosteoRating = function(clave, canje) {
	console.log("Editar Rating");
	var contrato=modificarContratoBusiness.grupo.contrato;
	var senial=modificarContratoBusiness.grupo.senial;
	var tipoTitulo =modificarContratoBusiness.grupo.tipoTitulo;
	var nroGrupo = modificarContratoBusiness.grupo.nroGrupo;
	var strClave = clave//"SE010799";
	var titulo = strClave.replace(tipoTitulo, "");
	CosteoRatingEvent.initModificar("popupModificarTipoDeCosteo", contrato, senial, nroGrupo, tipoTitulo, titulo, canje);
};
ModificarContratoDrawer.prototype.editarTipoCosteoMixto = function(clave, canje) {
	console.log("Editar Tipo MIXTO");
	var contrato=modificarContratoBusiness.grupo.contrato;
	var senial=modificarContratoBusiness.grupo.senial;
	var tipoTitulo =modificarContratoBusiness.grupo.tipoTitulo;
	var nroGrupo = modificarContratoBusiness.grupo.nroGrupo;
	var strClave = clave//"SE010799";
	var titulo = strClave.replace(tipoTitulo, "");
	CosteoMixtoEvent.initModificar("popupModificarTipoDeCosteo", contrato, senial, nroGrupo, tipoTitulo, titulo, canje);
};
ModificarContratoDrawer.prototype.altaTipoCosteo = function(clave, canje) {
	console.log("Generar alta de costeo");
	var contrato=modificarContratoBusiness.grupo.contrato;
	var senial=modificarContratoBusiness.grupo.senial;
//	var tipoTitulo =modificarContratoBusiness.grupo.tipoTitulo;
	var nroGrupo = modificarContratoBusiness.grupo.nroGrupo;
//	var strClave = clave//"SE010799";
//	var titulo = strClave.replace(tipoTitulo, "");
	TipoDeCosteoEvent.init("popupModificarTipoDeCosteo","TipoDeCosteoEvent", contrato, senial, nroGrupo);
//	CosteoMixtoEvent.initModificar("popupModificarTipoDeCosteo", contrato, senial, nroGrupo, tipoTitulo, titulo, canje);
};
ModificarContratoDrawer.prototype.drawGrillaDeGruposEliminarSenial = function() {
    modificarContratoSelector.getGrillaGruposEliminarSenial().jqGrid(
       {height: 'auto',
        autowidth: true,
        datatype: 'local',
        colNames:['Grupo','Tipo','Nombre','Importe','E/R','Señal H.'],
        colModel:[ 
            {name:'nroGrupo',index:'nroGrupo', width:40, sortable:false}, 
            {name:'tipoTitulo',index:'tipoTitulo', width:45, sortable:false},
            {name:'nombreGrupo',index:'nombreGrupo', width:210, sortable:false},
            {name:'importeGrupo',index:'importeGrupo', width:80, sortable:false},
            {name:'er',index:'er', width:30, sortable:false},
            {name:'senialHeredada',index:'senialHeredada', width:50, sortable:false}
        ],
        rowNum: 10,
        rowList:[10,20,30],
        scrollOffset: 0,
        pager: '#pagerGrillaDeGruposEliminarSenial',
        viewrecords: true, 
        loadonce: true,
        editurl: 'clientArray', 
        caption: 'Grupos'});
};

var modificarContratoDrawer = new ModificarContratoDrawer(new DivDefinition('ModificarContratoEventId'));