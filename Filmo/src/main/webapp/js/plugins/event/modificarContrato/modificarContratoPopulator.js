function ModificarContratoPopulator(div) {
    this.div = div;
};
extend(ModificarContratoPopulator, Plugin);

ModificarContratoPopulator.prototype.populateGrillaContratosPorDistribuidor = function() {
    if (modificarContratoSelector.getInput("busquedaPorDistribuidor").val().length > 0) {
        modificarContratoSelector.getInput("busquedaContratoPorClave").val("");
        ModificarContratoStaticService.doRequest(
                {action:"dameContrato.action", 
                 request: {claveDistribuidor: modificarContratoSelector.getInput("busquedaPorDistribuidor").val().split(" - ")[0].trim()},
                 method: "GET",
                 callback: function(data) {
                     if (data && data.length) {
                         modificarContratoSelector.getGrilla("grillaDeContratos").clearGridData().setGridParam({data: data}).trigger('reloadGrid');
                     } else {
                         modificarContratoSelector.getGrilla("grillaDeContratos").clearGridData();
                     }
                 },
                 responseObject: "contratos"});
    }
};
ModificarContratoPopulator.prototype.populateGrillaContratosPorClave = function() {
    if (modificarContratoSelector.getInput("busquedaContratoPorClave").val().length > 0) {
        modificarContratoSelector.getInput("busquedaPorDistribuidor").val("");
        ModificarContratoStaticService.doRequest(
                {action:"dameContrato.action",
                 request: {claveContrato: modificarContratoSelector.getInput("busquedaContratoPorClave").val().trim()},
                 method: "GET",
                 callback: function(data) {
                     if (data && data.length) {
                         modificarContratoSelector.getGrilla("grillaDeContratos").clearGridData().setGridParam({data: data}).trigger('reloadGrid');
                     }else{
                         modificarContratoSelector.getGrilla("grillaDeContratos").clearGridData();
                     }
                 },
                 responseObject: "contratos"});
    }
};
ModificarContratoPopulator.prototype.populatePopUpTitulo = function(titulo) {
    for (var prop in titulo) {
        modificarContratoSelector.getInput(prop, "tituloPopUp").val(titulo[prop]);
    }
    Component.disable($("#"+this.div.id+"_tituloPopUp :input"));
    
    //modificarContratoSelector.getInput("fechaPosibleEntrega", "tituloPopUp").datepicker({maxDate: 0, dateFormat: 'dd-mm-yy'});
    Datepicker.picker(modificarContratoSelector.getInput("fechaEntrega", "grupoPopUp"));
    modificarContratoSelector.getInput("observaciones", "tituloPopUp").removeAttr("disabled");
    if (titulo.editaFechaPosibleEntrega === "S") {
        modificarContratoSelector.getInput("fechaPosibleEntrega", "tituloPopUp").removeAttr("disabled");
    }
    if (titulo.editaMarcaRecontratacion === "S") {
        modificarContratoSelector.getInput("recontratacion", "tituloPopUp").removeAttr("disabled");
    }
    if (titulo.alertaTitulo) {
        modificarContratoSelector.getInput("alertaTitulo", "tituloPopUp").html("<h2>"+titulo.alertaTitulo+"</h2>");
    }
};
ModificarContratoPopulator.prototype.populatePopUpContratoConCabecera = function(contrato) {
    if (contrato) {
        Component.populateSelect(modificarContratoSelector.getInput("moneda", "contratoConCabeceraPopUp"), modificarContratoBusiness.monedas, "codigo", "abreviatura");
        modificarContratoSelector.getInput("contrato", "contratoConCabeceraPopUp").val(contrato.contrato);
        modificarContratoSelector.getInput("distribuidor", "contratoConCabeceraPopUp").val(contrato.distribuidor);
        modificarContratoSelector.getInput("fechaContrato", "contratoConCabeceraPopUp").val(contrato.fechaContrato.substring(0,10));
        modificarContratoSelector.getInput("fechaAprobacion", "contratoConCabeceraPopUp").val(contrato.fechaAprobacion.substring(0,10));
        modificarContratoSelector.getInput("tipoContrato", "contratoConCabeceraPopUp").val(contrato.tipoContrato);
        modificarContratoSelector.getInput("montoTotal", "contratoConCabeceraPopUp").autoNumeric({vMax: '999999999.99'});
        modificarContratoSelector.getInput("montoTotal", "contratoConCabeceraPopUp").autoNumericSet(contrato.montoTotal);
    	
        modificarContratoSelector.getInput("moneda", "contratoConCabeceraPopUp").val(contrato.moneda);
        
        modificarContratoSelector.getInput("fechaContrato", "contratoConCabeceraPopUp").datepicker({maxDate: 0, dateFormat: 'dd-mm-yy'});
        if (modificarContratoBusiness.contratoConCabecera.fechaContrato) {
            modificarContratoBusiness.contratoConCabecera.fechaContrato = modificarContratoBusiness.contratoConCabecera.fechaContrato.substring(0,10).replace("-","/").replace("-","/");
            modificarContratoSelector.getInput("fechaContrato", "contratoConCabeceraPopUp").datepicker('setDate', 
                    new Date(modificarContratoBusiness.contratoConCabecera.fechaContrato));
        }
        
        modificarContratoSelector.getInput("fechaAprobacion", "contratoConCabeceraPopUp").datepicker({maxDate: 0, dateFormat: 'dd-mm-yy'});
        if (modificarContratoBusiness.contratoConCabecera.fechaAprobacion) {
            modificarContratoBusiness.contratoConCabecera.fechaAprobacion = modificarContratoBusiness.contratoConCabecera.fechaAprobacion.substring(0,10).replace("-","/").replace("-","/");
            modificarContratoSelector.getInput("fechaAprobacion", "contratoConCabeceraPopUp").datepicker('setDate', 
                    new Date(modificarContratoBusiness.contratoConCabecera.fechaAprobacion));
        }
        
        modificarContratoSelector.getInput("distribuidor", "contratoConCabeceraPopUp").click(
                function() {
                    situarPopupEvent.create(
                            "dameDistribuidoresPorNombre.action", 
                            function(row) {
                                modificarContratoSelector.getInput("distribuidor", "contratoConCabeceraPopUp").val(row.codigo);
                            }, 
                            {nombreDistribuidor: ""}, 
                            "distribuidores");
                }
        );
        this.populateObservacionesDePago(contrato);
    } else {
        MESSAGE.alert("No existe el contrato");
    }
};
ModificarContratoPopulator.prototype.populateObservacionesDePago = function(contrato) {
    ModificarContratoStaticService.doRequest(
            {action:"dameObservacionesDePago.action",
                request: {claveContrato: contrato.contrato},
                method: "GET",
                callback: function(data) {
                    if (data && data.length && data.length > 0) {
                        modificarContratoBusiness.contratoConCabecera.observacionesDePago = data;
                        $.each(data, function(index, value) {
                            modificarContratoSelector.getInput("renglon"+(index+1), "contratoConCabeceraPopUp").val(value.observacionDePago);
                            if (modificarContratoSelector.getInput("renglon"+(index+1), "contratoConCabeceraPopUp").val().trim() === "") {
                                modificarContratoSelector.getInput("renglon"+(index+1), "contratoConCabeceraPopUp").hide();
                            } else {
                                modificarContratoSelector.getInput("renglon"+(index+1), "contratoConCabeceraPopUp").show();
                            }
                        });
                    } else {
                        $("#ModificarContratoEventId_contratoConCabeceraPopUp_observaciones").hide();
                    }
                },
                responseObject: "observacionesDePago"});
};
ModificarContratoPopulator.prototype.populateGrillaSenialesEImportes = function(senialesEimportes) {
    modificarContratoSelector.getGrillaSenialesEImportes().clearGridData().setGridParam({data: senialesEimportes}).trigger('reloadGrid');
};
ModificarContratoPopulator.prototype.populateGrillaDeGrupos = function(senial) {
    ModificarContratoStaticService.doRequest(
            {action:"dameGrupos.action",
                request: {claveContrato: modificarContratoBusiness.contratoConCabecera.contrato, senial: senial},
                method: "GET",
                callback: function(data) {
                    if (data && data.length) {
                        modificarContratoSelector.getGrillaGrupos().clearGridData().setGridParam({data: data}).trigger('reloadGrid');
                        modificarContratoSelector.getGrillaGruposContainer().show();
                    }else{
                        modificarContratoSelector.getGrillaGrupos().clearGridData();
                        modificarContratoSelector.getGrillaGruposContainer().show();
                    }
                },
                responseObject: "grupos"});
};
ModificarContratoPopulator.prototype.populatePopUpTituloConGrupo = function(grupo, modo) {
    for (var prop in grupo) {
        modificarContratoSelector.getInput(prop, "tituloConGrupoPopUp").val(grupo[prop]);
    }
    
    if (grupo.cantTiempoExclusivo !== undefined && grupo.cantTiempoExclusivo === 0) {
        modificarContratoSelector.getInput("cantTiempoExclusivo", "tituloConGrupoPopUp").val("");
    }
    
    if (grupo.fechaDerechos && grupo.fechaDerechos.substring(0,4) !== "0001") {
        grupo.fechaDerechos = grupo.fechaDerechos.substring(0,10).replace("-","/").replace("-","/");
        modificarContratoSelector.getInput("fechaDerechos", "tituloConGrupoPopUp").datepicker('setDate', new Date(grupo.fechaDerechos));
    } else {
        modificarContratoSelector.getInput("fechaDerechos", "tituloConGrupoPopUp").datepicker('setDate', null);
    }
    if (grupo.fechaEntrega && grupo.fechaEntrega.substring(0,4) !== "0001") {
        grupo.fechaEntrega = grupo.fechaEntrega.substring(0,10).replace("-","/").replace("-","/");
        modificarContratoSelector.getInput("fechaEntrega", "tituloConGrupoPopUp").datepicker('setDate', new Date(grupo.fechaEntrega));
    } else {
        modificarContratoSelector.getInput("fechaEntrega", "tituloConGrupoPopUp").datepicker('setDate', null);
    }
    if (grupo.fechaComienzoDerechosLibreria && grupo.fechaComienzoDerechosLibreria.substring(0,4) !== "0001") {
        grupo.fechaComienzoDerechosLibreria = grupo.fechaComienzoDerechosLibreria.substring(0,10).replace("-","/").replace("-","/");
        modificarContratoSelector.getInput("fechaComienzoDerechosLibreria", "tituloConGrupoPopUp").datepicker('setDate', new Date(grupo.fechaComienzoDerechosLibreria));
    } else {
        modificarContratoSelector.getInput("fechaComienzoDerechosLibreria", "tituloConGrupoPopUp").datepicker('setDate', null);
    }
    if (grupo.fechaDesde && grupo.fechaDesde.substring(0,4) !== "0001") {
        grupo.fechaDesde = grupo.fechaDesde.substring(0,10).replace("-","/").replace("-","/");
        modificarContratoSelector.getInput("fechaDesde", "tituloConGrupoPopUp").datepicker('setDate', new Date(grupo.fechaDesde));
    } else {    
        modificarContratoSelector.getInput("fechaDesde", "tituloConGrupoPopUp").datepicker('setDate', null);
    }
    if (grupo.fechaHasta && grupo.fechaHasta.substring(0,4) !== "0001") {
        grupo.fechaHasta = grupo.fechaHasta.substring(0,10).replace("-","/").replace("-","/");
        modificarContratoSelector.getInput("fechaHasta", "tituloConGrupoPopUp").datepicker('setDate', new Date(grupo.fechaHasta));
    } else {
        modificarContratoSelector.getInput("fechaHasta", "tituloConGrupoPopUp").datepicker('setDate', null);
    }
    
    if (modificarContratoBusiness.resolverFamilia(modificarContratoSelector.getInput("tipoTitulo", "tituloConGrupoPopUp").val()) === ModificarContratoBusiness.SC) {
        //SIN CAPITULO
        modificarContratoSelector.getLabel("cantTitulos","tituloConGrupoPopUp").html("CANT.TITULOS");
        modificarContratoSelector.getLabel("cantPasadas","tituloConGrupoPopUp").html("CANT.PASADAS CONTRATADAS");
        modificarContratoSelector.getLabel("costo","tituloConGrupoPopUp").html("COSTO UNITARIO");
        modificarContratoSelector.getInput("formula", "tituloConGrupoPopUp").hide();
        modificarContratoSelector.getLabel("formula", "tituloConGrupoPopUp").hide();
    } else {
        //CON CAPITULO
        modificarContratoSelector.getLabel("cantTitulos","tituloConGrupoPopUp").html("CANT. ORIGINALES");
        modificarContratoSelector.getLabel("cantPasadas","tituloConGrupoPopUp").html("CANT. REPETICIONES");
        modificarContratoSelector.getLabel("cantPasadas","tituloConGrupoPopUp").hide();
        modificarContratoSelector.getInput("cantPasadas","tituloConGrupoPopUp").hide();
        modificarContratoSelector.getLabel("costo","tituloConGrupoPopUp").html("COSTO TOTAL");
        modificarContratoSelector.getInput("formula", "tituloConGrupoPopUp").show();
        modificarContratoSelector.getLabel("formula", "tituloConGrupoPopUp").show();
    }
    
    Component.disable($("#"+this.div.id+"_tituloConGrupoPopUp_form :input"));
    if (modo && modo === "T") {
        Component.enable(modificarContratoSelector.getInput("fechaEntrega", "tituloConGrupoPopUp"));
        Component.enable(modificarContratoSelector.getInput("codigoDerechosTerritoriales", "tituloConGrupoPopUp"));
        Component.enable(modificarContratoSelector.getInput("observaciones", "tituloConGrupoPopUp"));
        Component.enable(modificarContratoSelector.getInput("cantTiempoExclusivo", "tituloConGrupoPopUp"));
        Component.enable(modificarContratoSelector.getInput("unidadTiempoExclusivo", "tituloConGrupoPopUp"));
        Component.enable(modificarContratoSelector.getInput("autorizacionAutor", "tituloConGrupoPopUp"));
        Component.enable(modificarContratoSelector.getInput("pagaArgentores", "tituloConGrupoPopUp"));
        if (modificarContratoBusiness.resolverFamilia(modificarContratoSelector.getInput("tipoTitulo", "tituloConGrupoPopUp").val()) === ModificarContratoBusiness.SC) {
            Component.enable($("#ModificarContratoEventId_tituloConGrupoPopUp_senialesHeredadas :input"));
            Component.enable(modificarContratoSelector.getInput("cantPasadas", "tituloConGrupoPopUp"));
            Component.enable(modificarContratoSelector.getInput("costoTotal", "tituloConGrupoPopUp"));
            Component.enable(modificarContratoSelector.getInput("codigoDerechos", "tituloConGrupoPopUp"));
            Component.enable(modificarContratoSelector.getInput("fechaDerechos", "tituloConGrupoPopUp"));
            Component.enable(modificarContratoSelector.getInput("cantTiempo", "tituloConGrupoPopUp"));
            Component.enable(modificarContratoSelector.getInput("unidadDeTiempo", "tituloConGrupoPopUp"));
            Component.enable(modificarContratoSelector.getInput("marcaPerpetuidad", "tituloConGrupoPopUp"));
        }
        if (grupo.permiteEditarPasaLibreria) {
            Component.enable(modificarContratoSelector.getInput("fechaComienzoDerechosLibreria", "tituloConGrupoPopUp"));
            Component.enable(modificarContratoSelector.getInput("pasaLibreria", "tituloConGrupoPopUp"));
        }
        if (grupo.permiteRecontratar) {
            Component.enable(modificarContratoSelector.getInput("recontratacion", "tituloConGrupoPopUp"));
        } else {
            Component.disable(modificarContratoSelector.getInput("recontratacion", "tituloConGrupoPopUp"));
        }
    }
    var senialesHeredadas = grupo.senialHeredada.split("+");
    for (var i in senialesHeredadas) {
        $("#ModificarContratoEventId_tituloConGrupoPopUp_senialesHeredadas > #senialHeredadaCheck"+senialesHeredadas[i]).prop('checked', true);
    }
    
};
ModificarContratoPopulator.prototype.populatePopUpGrupo = function(grupo) {
    for (var prop in grupo) {
        modificarContratoSelector.getInput(prop, "grupoPopUp").val(grupo[prop]);
    }
    
    var senialesHeredadas = grupo.senialHeredada.split("+");
    for (var i in senialesHeredadas) {
        $("#senialHeredadaCheck"+senialesHeredadas[i]).prop('checked', true);
    }
    
    if (modificarContratoBusiness.resolverFamilia(modificarContratoSelector.getInput("tipoTitulo", "grupoPopUp").val()) === ModificarContratoBusiness.SC) {
        modificarContratoSelector.getInput("recontratacion", "grupoPopUp").hide();
        modificarContratoSelector.getLabel("recontratacion", "grupoPopUp").hide();
    } else {
        modificarContratoSelector.getInput("recontratacion", "grupoPopUp").show();
        modificarContratoSelector.getLabel("recontratacion", "grupoPopUp").show();
    }
    if ((modificarContratoDrawer.drawPopUpGrupo.modificaCantidad) || (grupo.flagOutput === "S")) {
        Component.disable($("#"+this.div.id+"_grupoPopUp_form :input"));
        modificarContratoSelector.getInput("cantTitulos", "grupoPopUp").removeAttr("disabled");
    } else if (!modificarContratoDrawer.drawPopUpGrupo.modificaCantidad) {
        Component.disable($("#"+this.div.id+"_grupoPopUp_cantTitulos"));
        Component.disable($("#"+this.div.id+"_grupoPopUp_tipoTitulo"));
        Component.disable($("#"+this.div.id+"_grupoPopUp_contrato"));
        Component.disable($("#"+this.div.id+"_grupoPopUp_distribuidor"));
        Component.disable($("#"+this.div.id+"_grupoPopUp_senial"));
        Component.disable($("#"+this.div.id+"_grupoPopUp_montoSenial"));
        Component.disable($("#"+this.div.id+"_grupoPopUp_nroGrupo"));
        Component.disable($("#"+this.div.id+"_grupoPopUp_er"));
    } else {
        Component.disable($("#"+this.div.id+"_grupoPopUp_form :input"));
    }
    
    modificarContratoSelector.getInput("tipoTitulo", "grupoPopUp").change();
    if (grupo.cantTiempoExclusivo !== undefined && grupo.cantTiempoExclusivo === 0) {
        modificarContratoSelector.getInput("cantTiempoExclusivo", "grupoPopUp").val("");
    }
    if (grupo.cantPasadas !== undefined && grupo.cantPasadas === 0) {
        modificarContratoSelector.getInput("cantPasadas", "grupoPopUp").val(0);
    }
    if (grupo.fechaDerechos && grupo.fechaDerechos.substring(0,4) !== "0001") {
        grupo.fechaDerechos = grupo.fechaDerechos.substring(0,10).replace("-","/").replace("-","/");
        modificarContratoSelector.getInput("fechaDerechos", "grupoPopUp").datepicker('setDate', new Date(grupo.fechaDerechos));

    } else {
        modificarContratoSelector.getInput("fechaDerechos", "grupoPopUp").datepicker('setDate', null);
    }
    if (grupo.fechaEntrega && grupo.fechaEntrega.substring(0,4) !== "0001") {
        grupo.fechaEntrega = grupo.fechaEntrega.substring(0,10).replace("-","/").replace("-","/");
        modificarContratoSelector.getInput("fechaEntrega", "grupoPopUp").datepicker('setDate', new Date(grupo.fechaEntrega));
    } else {
        modificarContratoSelector.getInput("fechaEntrega", "grupoPopUp").datepicker('setDate', null);
    }
    if (grupo.fechaComienzoDerechosLibreria && grupo.fechaComienzoDerechosLibreria.substring(0,4) !== "0001") {
        grupo.fechaComienzoDerechosLibreria = grupo.fechaComienzoDerechosLibreria.substring(0,10).replace("-","/").replace("-","/");
        modificarContratoSelector.getInput("fechaComienzoDerechosLibreria", "grupoPopUp").datepicker('setDate', new Date(grupo.fechaComienzoDerechosLibreria));
    } else {
        modificarContratoSelector.getInput("fechaComienzoDerechosLibreria", "grupoPopUp").datepicker('setDate', null);
    }
};
ModificarContratoPopulator.prototype.populateGrillaTitulos = function(titulos) {
    modificarContratoSelector.getGrillaTitulos().clearGridData().setGridParam({data: titulos}).trigger('reloadGrid');
};
ModificarContratoPopulator.prototype.populateGrillaDeGruposEliminarSenial = function(senial) {
    ModificarContratoStaticService.doRequest(
           {action:"dameGrupos.action",
            request: {claveContrato: modificarContratoBusiness.contratoConCabecera.contrato, senial: senial},
            method: "GET",
            callback: function(data) {
                if (data && data.length) {
                    modificarContratoSelector.getGrillaGruposEliminarSenial().clearGridData().setGridParam({data: data}).trigger('reloadGrid');
                }else{
                    modificarContratoSelector.getGrillaGruposEliminarSenial().clearGridData();
                }
            },
            responseObject: "grupos"});
};

var modificarContratoPopulator = new ModificarContratoPopulator(new DivDefinition('ModificarContratoEventId'));