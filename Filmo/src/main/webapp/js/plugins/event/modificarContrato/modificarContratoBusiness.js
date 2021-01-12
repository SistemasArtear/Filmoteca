function ModificarContratoBusiness() {
    //modificarContratoBusiness.contratoConCabecera.grupoElegido
    //modificarContratoBusiness.contratoConCabecera.grupoAReemplazar
    //modificarContratoBusiness.contratoConCabecera.importeSenialElegida
    //modificarContratoBusiness.grupo
};
extend(ModificarContratoBusiness, Plugin);
ModificarContratoBusiness.CC = "CC";
ModificarContratoBusiness.SC = "SC";
ModificarContratoBusiness.prototype.extenderChequeoTecnico = function(rowId) {
    var row = modificarContratoSelector.getGrilla("grillaDeContratos").jqGrid('getRowData', rowId);
    ModificarContratoStaticService.doRequest(
            {action:"estaBloqueado.action",
                request: {claveContrato: row.contrato},
                method: "GET",
                callback: function(estaBloqueado) {
                    if (!estaBloqueado) {
                        ModificarContratoStaticService.doRequest(
                                {action:"bloquearContrato.action",
                                    request: {claveContrato: row.contrato},
                                    method: "GET",
                                    callback: function(data) {
                                        modificarContratoValidator.validarExtenderChequeoTecnico(row);
                                    } 
                                 });
                    } else {
                        MESSAGE.error("El contrato bloqueado");
                    }
                },
                responseObject: "estaBloqueado"});
};

ModificarContratoBusiness.prototype.editarContratoConCabecera = function(rowId) {
    var row = modificarContratoSelector.getGrilla("grillaDeContratos").jqGrid('getRowData', rowId);
    if (row) {
        ModificarContratoStaticService.doRequest(
            {action:"estaBloqueado.action",
                request: {claveContrato: row.contrato},
                method: "GET",
                callback: function(estaBloqueado) {
                    if (!estaBloqueado) {
                        ModificarContratoStaticService.doRequest(
                                {action:"bloquearContrato.action",
                                    request: {claveContrato: row.contrato},
                                    method: "GET",
                                    callback: function(data) {
                                        ModificarContratoStaticService.doRequest(
                                                {action:"dameContratoConCabecera.action",
                                                    request: {claveContrato: row.contrato, claveDistribuidor: row.distribuidor},
                                                    method: "GET",
                                                    callback: function(data) {
                                                        if (data && data.length != undefined && data.length > 0) {
                                                            modificarContratoBusiness.contratoConCabecera = data[0];
                                                            Component.get("html/modificarContrato/PopUpContrato.html", modificarContratoDrawer.drawPopUpContratoConCabecera);
                                                            modificarContratoSelector.getPopUp("contratoConCabeceraPopUp").dialog('open');
                                                            
                                                        } else {
                                                            MESSAGE.error("Ocurrio un error al obtener el contrato");
                                                        }
                                                    },
                                                    responseObject: "contratos"});
                                    } 
                                 });
                    } else {
                        MESSAGE.error("El contrato bloqueado");
                    }
                },
                responseObject: "estaBloqueado"});
        
    }
};
ModificarContratoBusiness.prototype.recargarContratoConCabecera = function(contrato) {
    ModificarContratoStaticService.doRequest(
            {action:"dameContratoConCabecera.action",
             request: {claveContrato: contrato.contrato, claveDistribuidor: contrato.distribuidor},
             method: "GET",
             callback: function(data) {
                 if (data && data.length != undefined && data.length > 0) {
                     modificarContratoBusiness.contratoConCabecera = data[0];
                     modificarContratoSelector.getPopUp("contratoConCabeceraPopUp").remove();
                     Component.get("html/modificarContrato/PopUpContrato.html", modificarContratoDrawer.drawPopUpContratoConCabecera);
                     modificarContratoSelector.getPopUp("contratoConCabeceraPopUp").dialog('open');
                 } else {
                     MESSAGE.error("Ocurrio un error al obtener el contrato");
                 }
             },
             responseObject: "contratos"});
};
ModificarContratoBusiness.prototype.trabajarConGrupos = function(rowId) {
    if (rowId) {
        var row = modificarContratoSelector.getGrillaSenialesEImportes().jqGrid('getRowData', rowId);
        Component.disable($("#"+modificarContratoDrawer.div.id+"_contratoConCabeceraPopUp_cabecera :input"));
        Component.disable($("#"+modificarContratoDrawer.div.id+"_contratoConCabeceraPopUp_observaciones :input"));
        if ( modificarContratoSelector.getGrillaGrupos().children().length == 0 ) {
            modificarContratoDrawer.drawGrillaDeGrupos();
        }
        modificarContratoBusiness.contratoConCabecera.senialElegida = row.codigoSenial;
        modificarContratoBusiness.contratoConCabecera.importeSenialElegida = row.importe;
        modificarContratoPopulator.populateGrillaDeGrupos(modificarContratoBusiness.contratoConCabecera.senialElegida);
    } else {
        MESSAGE.error("Debe seleccionar un grupo");
    }
};
ModificarContratoBusiness.prototype.modificarSenialImporte = function(rowId) {
    if (rowId) {
        var row = modificarContratoSelector.getGrillaSenialesEImportes().jqGrid('getRowData', rowId);
        modificarContratoBusiness.modificarImporte({codigo: row.codigoSenial, importe: row.importe});
    } else {
        MESSAGE.error("Debe seleccionar un grupo");
    }
};
ModificarContratoBusiness.prototype.borrarGrupo = function(claveContrato, nroGrupo, senial) {
    ModificarContratoStaticService.doRequest(
            {action:"eliminarGrupo.action",
             request: {claveContrato: claveContrato, claveGrupo: nroGrupo, senial: senial},
             method: "GET",
             callback: function(data) {
                 if (data && data.length != undefined && data.length > 0) {
                     var ok = false;
                     $.each(data, function(i, l) {
                         if (l["tipo"] === "P_OK") {
                             ok = true;
                         }
                     });
                     if (ok) {
                         MESSAGE.ok("Grupo Eliminado");
                         modificarContratoPopulator.populateGrillaDeGrupos(modificarContratoBusiness.contratoConCabecera.senialElegida);
                     } else {
                         MESSAGE.alert("No se pudo eliminar el grupo");
                     }
                 }
             },
             responseObject: "validacionModificacionContrato"});
        return;
};
ModificarContratoBusiness.prototype.eliminarGrupo = function(rowId) {
    var row = modificarContratoSelector.getGrillaGrupos().jqGrid('getRowData', rowId);
    popupConfirmacionEvent.confirmar = function () {
        modificarContratoValidator.validateEliminarGrupo(modificarContratoBusiness.contratoConCabecera.contrato, row.nroGrupo, modificarContratoBusiness.contratoConCabecera.senialElegida);
        popupConfirmacionEvent.remove();
    };
    popupConfirmacionEvent.cancelar = function () {
        popupConfirmacionEvent.remove();
    };
    popupConfirmacionEvent.afterDraw = function () {
        return;
    };
    popupConfirmacionEvent.popTile = "Eliminar grupo";
    popupConfirmacionEvent.create("eliminarGrupoPopUp", "Desea eliminar el grupo?");
    return;
};
ModificarContratoBusiness.prototype.modificarGrupo = function(rowId) {
    if (rowId) {
        var row = modificarContratoSelector.getGrillaGrupos().jqGrid('getRowData', rowId);
        modificarContratoBusiness.contratoConCabecera.grupoElegido = row.nroGrupo;
        modificarContratoDrawer.drawPopUpGrupo.modificaCantidad = false;
        modificarContratoBusiness.trabjarConVigencias.modo = "C";
        Component.get("html/modificarContrato/PopUpGrupo.html", modificarContratoDrawer.drawPopUpGrupo);
    } else {
        MESSAGE.error("Debe seleccionar un grupo");
    }
    return;
};
ModificarContratoBusiness.prototype.modificarCantidad = function(rowId) {
    if (rowId) {
        var row = modificarContratoSelector.getGrillaGrupos().jqGrid('getRowData', rowId);
        modificarContratoBusiness.contratoConCabecera.grupoElegido = row.nroGrupo;
        modificarContratoDrawer.drawPopUpGrupo.modificaCantidad = true;
        Component.get("html/modificarContrato/PopUpGrupo.html", modificarContratoDrawer.drawPopUpGrupo);
    } else {
        MESSAGE.error("Debe seleccionar un grupo");
    }
    return;
};
ModificarContratoBusiness.prototype.crearGrupo = function(modo, nroRelacionante) {
    delete modificarContratoBusiness.grupo;
    modificarContratoBusiness.crearGrupo.modo = modo;
    modificarContratoBusiness.crearGrupo.nroRelacionante = nroRelacionante;
    modificarContratoBusiness.contratoConCabecera.grupoElegido = null;
    Component.get("html/modificarContrato/PopUpGrupo.html", modificarContratoDrawer.drawPopUpGrupo);
    return;
};
ModificarContratoBusiness.prototype.verTitulos = function(rowId) {
    modificarContratoBusiness.trabjarConVigenciasDesdeMaestros = false;
    if (rowId) {
        var row = modificarContratoSelector.getGrillaGrupos().jqGrid('getRowData', rowId);
        modificarContratoBusiness.contratoConCabecera.grupoElegido = row.nroGrupo;
        modificarContratoDrawer.drawPopUpTituloConGrupo.row = undefined;
        modificarContratoDrawer.drawPopUpGrupo.modificaCantidad = undefined;
        modificarContratoDrawer.drawPopUpTituloConGrupo.modo = "C";
        modificarContratoBusiness.trabjarConVigencias.modo = "C";
        Component.get("html/modificarContrato/PopUpGrupo.html", modificarContratoDrawer.drawPopUpGrupo);
    } else {
        MESSAGE.error("Debe seleccionar un grupo");
    }
    return;
};
ModificarContratoBusiness.prototype.reemplazarTitulos = function(rowId) {
	var self = modificarContratoBusiness;
	if (self.contratoConCabecera != null && self.contratoConCabecera.grupoElegido != null) {
    	modificarContratoBusiness.contratoConCabecera.grupoElegido = null;
    }
    modificarContratoBusiness.titulosElegidos = [];
    if (rowId) {
        var row = modificarContratoSelector.getGrillaGrupos().jqGrid('getRowData', rowId);
        modificarContratoBusiness.contratoConCabecera.grupoElegido = row.nroGrupo;
        situarPopupEvent.reset();
        situarPopupEvent.nombreSituar = "Reemplazo de Titulos";
        situarPopupEvent.setColumns(["Clave","Titulo Castellano","Grupo","Programado","Confirmada","A Consultar"],
                [{name:"clave", index:"clave"},{name:"tituloCastellano", index:"tituloCastellano"},
                 {name:"grupo", index:"grupo"},{name:"programado", index:"programado"},
                 {name:"exhibicionConfirmada", index:"exhibicionConfirmada"},{name:"aConsultar", index:"aConsultar"}]);
        situarPopupEvent.doAfterDraw = function() {
            situarPopupEvent.getPopup().dialog({buttons: { "OK": function() { 
                var data = situarPopupEvent.getGrid().jqGrid('getGridParam','data'), item;
                var titulosArray = [];
                if (data) {
                    for (var i = 0; i <= data.length; i++) {
                        item = data[i];
                        if (item && item.cb) {
                            titulosArray.push({clave: item.clave});
                        }
                    }
                }
                if (titulosArray.length > 0) {
                    var data = {data: {contrato: modificarContratoBusiness.contratoConCabecera.contrato, grupo: parseInt(modificarContratoBusiness.contratoConCabecera.grupoElegido), senial: modificarContratoBusiness.contratoConCabecera.senialElegida, titulos: titulosArray}};
                    BLOCK.showBlock("Se estan obteniendo los datos...");
                    jQuery.ajax({
                        type: 'POST', 
                        url: 'reemplazarTitulos.action', 
                        data: JSON.stringify(data),
                        dataType: 'json',
                        contentType: 'application/json; charset=utf-8',
                        success: function(data) {
                        	BLOCK.hideBlock();
                            if (data.validacionModificacionContrato && data.validacionModificacionContrato.length && data.validacionModificacionContrato.length > 0) {
                                var hayErrores = false;
                                var hayWarnings = false;
                                var warnings  = new Array();
                                var errores  = new Array();
                                var nroRelacionante = null;
                                var importeReemplazo = null;
                                var gruposReemplazo = null;
                                var ok = false;
                                $.each(data.validacionModificacionContrato, function(i, l) {
                                    if (l["tipo"] === "E") {
                                        hayErrores = true;
                                        errores.push(l["descripcion"]);
                                    } else if (l["tipo"] === "W") {
                                        hayWarnings = true;
                                        warnings.push(l["descripcion"]);
                                    } else if (l["tipo"] === "D_NRO_RELACIONANTE") {
                                        nroRelacionante = l["nroAdvertencia"];
                                        ok = true;
                                    } else if (l["tipo"] === "D_IMPORTE_REEMP") {
                                        importeReemplazo = l["nroAdvertencia"];
                                        ok = true;
                                    } else if (l["tipo"] === "D_GRUPOS_REEMP") {
                                        gruposReemplazo = l["nroAdvertencia"];
                                        ok = true;
                                    }
                                });
                                if (hayErrores) {
                                    MESSAGE.error(errores.join("<br/>"));
                                } else if (hayWarnings){
                                    popupConfirmacionEvent.confirmar = function () {
                                        modificarContratoBusiness.gruposAReemplazar(
                                               {contrato: modificarContratoBusiness.contratoConCabecera.contrato, 
                                                senial: modificarContratoBusiness.contratoConCabecera.senialElegida,
                                                mayorGrupo: gruposReemplazo});
                                        popupConfirmacionEvent.remove();
                                    };
                                    popupConfirmacionEvent.cancelar = function () {
                                        popupConfirmacionEvent.remove();
                                    };
                                    popupConfirmacionEvent.afterDraw = function () {
                                        return;
                                    };
                                    popupConfirmacionEvent.popTitle = 'WARNING';
                                    popupConfirmacionEvent.create("warningsPopUp", warnings.join("<br/>"));
                                } else if (ok) {
                                    modificarContratoBusiness.modificacionDeCantidadGrupo.diff = titulosArray.length;
                                    modificarContratoBusiness.gruposAReemplazar(
                                           {contrato: modificarContratoBusiness.contratoConCabecera.contrato, 
                                            senial: modificarContratoBusiness.contratoConCabecera.senialElegida,
                                            importeReemplazo: importeReemplazo,
                                            nroRelacionante: nroRelacionante,
                                            grupo: gruposReemplazo});
                                }
                            }
                            situarPopupEvent.getPopup().dialog("close");
                        }
                    });
                }
                } } });
            return;
        };
        situarPopupEvent.doAfterDrawGrid = function() {
            situarPopupEvent.cantidadSeleccionada = 0;
            
            situarPopupEvent.getGrid().jqGrid('setGridParam', {beforeSelectRow: function(rowid, e) {$(this).jqGrid('setSelection', rowid, true);} });
            
            situarPopupEvent.getGrid().jqGrid('setGridParam', {onSelectRow: function(rowid, status){
                var baseIndex = ($(this).getGridParam('page') - 1) * 10;
                var p = this.p, item = p.data[baseIndex + parseInt(rowid) - 1];
                var row = $(this).jqGrid('getRowData', rowid);
                if (typeof (item.cb) === "undefined") {
                    //primera vez
                    if ( ((row.exhibicionConfirmada === "N") || (row.programado === " ")) ) {
                        item.cb = true;
                    } else {
                        situarPopupEvent.getGrid().jqGrid('setSelection', rowid, false);
                        item.cb = false;
                    }
                } else {
                    if (!(item.cb)) {
                        if ( ((row.exhibicionConfirmada === "N") || (row.programado === " ")) ) {
                            item.cb = true;
                        }
                    } else {
                        item.cb = false;
                    }
                }
            }});
            situarPopupEvent.getGrid().jqGrid('setGridParam', {onSelectAll: function(){
                return;
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
            
            if (situarPopupEvent.multiselect) {
                var inputSelectAll = "#cb_"+situarPopupEvent.div.id+"_gridSituar";
                $(inputSelectAll).remove();
            }

            return;
        };
        situarPopupEvent.acciones = [];
        situarPopupEvent.doAfterClose = function () { return; };
        situarPopupEvent.create(
                "dameTitulosAReemplazarPorContrato.action", 
                function (row) {
                    return false;
                }, 
                {"titulosRequest.contrato": modificarContratoBusiness.contratoConCabecera.contrato, 
                "titulosRequest.grupo": modificarContratoBusiness.contratoConCabecera.grupoElegido, 
                "titulosRequest.senial": modificarContratoBusiness.contratoConCabecera.senialElegida}, 
                "titulosContratados",
                 true,
                 true);
    } else {
        MESSAGE.alert("Debe seleccionar un grupo");
    }
    return;
};
ModificarContratoBusiness.prototype.reemplazarGrupo = function(rowId, skipWarnings) {
    var self = modificarContratoBusiness;
    if (self.contratoConCabecera != null && self.contratoConCabecera.grupoElegido != null) {
    	modificarContratoBusiness.contratoConCabecera.grupoElegido = null;
    }
    if (rowId) {
        var row = modificarContratoSelector.getGrillaGrupos().jqGrid('getRowData', rowId);
        ModificarContratoStaticService.doRequest(
                {action: "reemplazarGrupo.action",
                 request: Component.serialize({contrato: modificarContratoBusiness.contratoConCabecera.contrato,
                                               mayorGrupo: row.nroGrupo,
                                               senial: modificarContratoBusiness.contratoConCabecera.senialElegida}, "gruposConReemplazoRequest")
                                               + (skipWarnings ? "&skipWarnings=true" : ""),
                 method: "GET",
                 callback: function(data) {
                     if (data && data.length != undefined && data.length > 0) {
                         var hayErrores = false;
                         var hayWarnings = false;
                         var warnings  = new Array();
                         var errores  = new Array();
                         var nroRelacionante;
                         var importeReemplazo;
                         var mayorGrupo;
                         var ok = false;
                         $.each(data, function(i, l) {
                             if (l["tipo"] === "E") {
                                 hayErrores = true;
                                 errores.push(l["descripcion"]);
                             } else if (l["tipo"] === "W") {
                                 hayWarnings = true;
                                 warnings.push(l["descripcion"]);
                             } else if (l["tipo"] === "D_NRO_RELACIONANTE") {
                                 nroRelacionante = l["nroAdvertencia"];
                                 ok = true;
                             } else if (l["tipo"] === "D_IMPORTE_REEMP") {
                                 importeReemplazo = l["nroAdvertencia"];
                                 ok = true;
                             } else if (l["tipo"] === "D_MAYOR_GRUPO") {
                                 mayorGrupo = l["nroAdvertencia"];
                                 ok = true;
                             }
                         });
                         if (hayErrores) {
                             MESSAGE.error(errores[0]);
                         } else if (hayWarnings){
                             popupConfirmacionEvent.confirmar = function () {
                                 modificarContratoBusiness.reemplazarGrupo(rowId, true);
                                 popupConfirmacionEvent.remove();
                             };
                             popupConfirmacionEvent.cancelar = function () {
                                 popupConfirmacionEvent.remove();
                             };
                             popupConfirmacionEvent.afterDraw = function () {
                                 return;
                             };
                             popupConfirmacionEvent.popTitle = 'WARNING';
                             popupConfirmacionEvent.create("warningsPopup", warnings.join("<br/>"));
                         } else if (ok) {
                             modificarContratoBusiness.gruposAReemplazar(
                                     {contrato: modificarContratoBusiness.contratoConCabecera.contrato, 
                                      senial: modificarContratoBusiness.contratoConCabecera.senialElegida,
                                      grupo: row.nroGrupo,
                                      importeReemplazo: importeReemplazo,
                                      nroRelacionante: nroRelacionante,
                                      mayorGrupo: mayorGrupo});
                         }
                     }
                 },
                 responseObject: "validacionModificacionContrato"});
    }
};
ModificarContratoBusiness.prototype.gruposAReemplazar = function(req) {
    if (req) {
        modificarContratoBusiness.gruposAReemplazar.req = req;
    }
    ModificarContratoStaticService.doRequest(
            {action:"dameGrupo.action",
             request: {claveContrato: modificarContratoBusiness.contratoConCabecera.contrato, 
                       senial: modificarContratoBusiness.contratoConCabecera.senialElegida, 
                       claveGrupo: modificarContratoBusiness.gruposAReemplazar.req.grupo},
             method: "GET",
             callback: function(data) {
                  if (data) {
                      modificarContratoBusiness.grupo = data;
                      modificarContratoBusiness.grupo.nroRelacionante = modificarContratoBusiness.gruposAReemplazar.req.nroRelacionante;
                      modificarContratoBusiness.grupo.modo = "MR";
                      situarPopupEvent.reset();
                      situarPopupEvent.nombreSituar = "Grupos a Reemplazar";
                      situarPopupEvent.setColumns(
                              ["Contrato","Grupo","Senial", "Importe", ""],
                              [{name:"contrato", index:"contrato"},{name:"nroGrupo", index:"nroGrupo"},{name:"senial", index:"senial"},
                               {name:"costoTotal", index:"costoTotal"},
                               {name:"puedeReemplazar", index:"puedeReemplazar", hidden: true}]);
                      situarPopupEvent.doAfterDraw = function() {
                          situarPopupEvent.getPopup().dialog(
                                  {buttons: 
                                      {"Agregar Grupo": function() {
                                                              situarPopupEvent.getPopup().dialog("close");
                                                              modificarContratoBusiness.crearGrupo("AR", modificarContratoBusiness.gruposAReemplazar.req.nroRelacionante);
                                                        }
                                      }
                                  }); 
                          
                          
                          return;
                      };
                      situarPopupEvent.doAfterDrawGrid = function() {
                          var nuevosGrupos = situarPopupEvent.getGrid().jqGrid('getGridParam','data');
                          var sumatoria = 0;
                          if (nuevosGrupos) {
                              if (nuevosGrupos.length > 0) {
                                  jQuery.each(nuevosGrupos, function (i, val) {
                                      sumatoria = sumatoria + val.costoTotal;
                                  });
                              }
                          }
                          return;
                      };
                      
                      situarPopupEvent.req = req;
                      situarPopupEvent.acciones = [
                                                   {callback: 
                                                       function(rowData){
                                                           situarPopupEvent.getPopup().dialog("close");
                                                           modificarContratoBusiness.modificacionDeCantidadGrupo(modificarContratoBusiness.grupo);
                                                       }, 
                                                   nombre: "Agregar Titulo", 
                                                   imagen: "bullet"
                                                   }
                                                   ];
                      situarPopupEvent.doAfterClose = function () {
                          
                          return; 
                      };
                      situarPopupEvent.create(
                              "dameGruposConReemplazo.action", 
                              function (row) {
                                  return;
                              }, 
                              {"gruposConReemplazoRequest.contrato": req.contrato,
                               "gruposConReemplazoRequest.mayorGrupo": parseInt(req.grupo),
                               "gruposConReemplazoRequest.senial": req.senial},
                               "grupos",
                               true);
                  } else {
                      MESSAGE.error("Error al buscar el grupo");
                  }
             },
             responseObject: "grupo"});

};
ModificarContratoBusiness.prototype.ingresarTitulos = function(cantidad, tipoTitulo, recontratacion) {
    var self = modificarContratoBusiness;
    modificarContratoBusiness.cantTitulosAAgregar = cantidad;
//    console.log("Cant. Titulos: " + cantidad);
//    console.log("Tipo Titulo: " + tipoTitulo);
//    console.log("Recontratacion: " + recontratacion);
//    console.log("popup situar lenght :" +$("#dameTitulos_popupSituar").length);
    if ($("#dameTitulos_popupSituar").length > 0) {
//        console.log("Cierro el popUp Situar");
        $("#dameTitulos_popupSituar").dialog("close");
        $("#dameTitulos_popupSituar").remove();
    }
    if (cantidad > 0) {
        situarPopupEvent.reset();
        situarPopupEvent.nombreSituar = "Ingresar Titulo";
        situarPopupEvent.setColumns(
                ["Clave","Tipo","Titulo","Actores","Sin Contrato"],
                [{name:"clave", index:"clave"},{name:"tipo", index:"tipo", hidden: true}, {name:"titulo", index:"titulo"},{name:"actores", index:"actores"},{name:"sinContrato", index:"sinContrato"}]);
        
        situarPopupEvent.doAfterDraw = function() {
            situarPopupEvent.getPopup().dialog( "option", "buttons", []);
            situarPopupEvent.getFuncionalidadAdicionalContainer().empty();
            $("#dameTitulos_inputBusquedaSituar").val("");
            //No quieren q aparezca el nombre del grupo en el input
            //$("#dameTitulos_inputBusquedaSituar").val(modificarContratoBusiness.grupo.nombreGrupo);
            var obj = {htmlObject: "input", type: "radio", id:"dameTitulos", values: ["TituloOriginal", "TituloCastellano"]};
            switch (obj.htmlObject) {
                case "input":
                    for (var j in obj.values) { 
                        situarPopupEvent.getFuncionalidadAdicionalContainer().append($("<label>").text('por '+obj.values[j]));
                        var radio = $('<'+obj.htmlObject+'>').attr({type: obj.type, id: obj.id+"_"+obj.values[j], value: obj.values[j], style:"display: inline-block", name: "buscarPor"});
                        situarPopupEvent.getFuncionalidadAdicionalContainer().append(radio);
                        radio.bind('click', function(e) { 
                            situarPopupEvent.data = {"titulosRequest.tituloABuscar": "", 
                                                     "titulosRequest.tipoTitulo": tipoTitulo,
                                                     "titulosRequest.buscarPor": $("#dameTitulos_funcionalidadAdicionalContainer :checked").first().val(),
                                                     "titulosRequest.familia": modificarContratoBusiness.resolverFamilia(tipoTitulo), 
                                                     "titulosRequest.senial": modificarContratoBusiness.grupo.senial,
                                                     "titulosRequest.marcaRecontratacion": modificarContratoBusiness.grupo.recontratacion,
                                                     "titulosRequest.estrenoOrepeticion": modificarContratoBusiness.grupo.er}; });
                    }
                    $($("#dameTitulos_funcionalidadAdicionalContainer :input")[0]).attr("checked", "checked");
                    break;
                default:
                    break;
            }
            
            if ((modificarContratoBusiness.grupo.recontratacion === "N") || (modificarContratoBusiness.resolverFamilia(modificarContratoBusiness.grupo.tipoTitulo) === ModificarContratoBusiness.SC)) {
//                console.log(8);
//            	console.log("popup crear nuevo titulo:" +$("#dameTitulos_crearNuevoTitulo").length);
            	if ($("#dameTitulos_crearNuevoTitulo").length === 0) {
            		var agregarNuevoButton = $("<input>").attr({type: "button", id: "dameTitulos_crearNuevoTitulo", value: "Crear Nuevo Titulo", style:"display: inline-block", name: "crearNuevoTitulo"});
            		situarPopupEvent.getFuncionalidadAdicionalContainer().before(agregarNuevoButton);
            		
            		$("#dameTitulos_crearNuevoTitulo").button().click(function(event) {
//            			console.log(9);
            			//situarPopupEvent.getPopup().dialog("close");
            			Component.get(
            					"html/modificarContrato/PopUpTitulo.html",
            					function (comp) {
            						var claveTitulo = "";
            						$("#popupContainer").empty().append(comp.replace(/{{id}}/g, modificarContratoDrawer.div.id));
            						modificarContratoSelector.getPopUp("tituloPopUp").dialog({
            							title : 'Titulo',
            							width: 450,
            							show: 'blind',
            							hide: 'blind',
            							modal: true,
            							autoOpen: true,
            							open: function() {
            								ModificarContratoStaticService.doRequest(
            										{action:"dameClave.action",
            											request: Component.serialize(
            													{tipoTitulo: modificarContratoBusiness.grupo.tipoTitulo}, "tituloRequest"),
            													method: "GET",
            													callback: function(data) {
            														if (data) {
            															claveTitulo = data;
            															modificarContratoSelector.getInput("clave", "tituloPopUp").val(claveTitulo);
            															modificarContratoSelector.getInput("recontratacion", "tituloPopUp").val("N");
            															Component.disable($("#ModificarContratoEventId_tituloPopUp_recontratacion"));
            															Component.disable($("#ModificarContratoEventId_tituloPopUp_clave"));
            															if ($("#dameTitulos_funcionalidadAdicionalContainer :checked").val() === "TituloOriginal") {
            																modificarContratoSelector.getInput("tituloOriginal", "tituloPopUp").val($("#dameTitulos_inputBusquedaSituar").val());
            															} else {
            																modificarContratoSelector.getInput("tituloCastellano", "tituloPopUp").val($("#dameTitulos_inputBusquedaSituar").val());
            															}
            														}
            													},
            													responseObject: "claveTitulo"});
            								return;
            							},
            							close: function() {
            								$(this).dialog("close");
            								modificarContratoSelector.getPopUp("tituloPopUp").remove();
            							},
            							buttons: {
            								"Aceptar": function() {
            									var titulo = {};
            									titulo.tituloCastellano = modificarContratoSelector.getInput("tituloCastellano", "tituloPopUp").val();
            									titulo.tituloOriginal = modificarContratoSelector.getInput("tituloOriginal", "tituloPopUp").val();
            									titulo.actores1 = modificarContratoSelector.getInput("actores1", "tituloPopUp").val();
            									titulo.actores2 = modificarContratoSelector.getInput("actores2", "tituloPopUp").val();
            									titulo.calificacionOficial = modificarContratoSelector.getInput("calificacionOficial", "tituloPopUp").val();
            									titulo.fechaPosibleEntrega = modificarContratoSelector.getInput("fechaPosibleEntrega", "tituloPopUp").val();
            									titulo.observaciones = modificarContratoSelector.getInput("observaciones", "tituloPopUp").val();
            									titulo.recontratacion = modificarContratoSelector.getInput("recontratacion", "tituloPopUp").val();
            									titulo.clave = claveTitulo;
            									titulo.sinContrato = "";
            									titulo.origen = "";
            									titulo.idFicha = "";
            									if ($("#dameTitulos_popupSituar").length > 0) {
            										$("#dameTitulos_popupSituar").dialog("close");
            									}
            									if ((titulo.tituloCastellano.trim() === "") && (titulo.tituloOriginal.trim() === "")) {
            										MESSAGE.error("Debe ingresar al menos un titulo");
            									} else {
//            										console.log(1);
            										modificarContratoValidator.validarAltaTitulo(titulo);
            									}
            								}
            							}
            						});
            					});
            		});
            	}
            }
        };
        situarPopupEvent.doAfterDrawGrid = function() { 
            if (!situarPopupEvent.getPopup().dialog("isOpen")) {
                situarPopupEvent.getPopup().dialog("open");
            }
        };
        situarPopupEvent.acciones = [];
        situarPopupEvent.doAfterClose = function () { return; };
        situarPopupEvent.create(
                "dameTitulos.action", 
                function (row) {
                    Component.get(
                            "html/modificarContrato/PopUpTitulo.html",
                            function (comp) {
                                $("#popupContainer").empty().append(comp.replace(/{{id}}/g, modificarContratoDrawer.div.id));
                                var titulo = {};
                                modificarContratoSelector.getPopUp("tituloPopUp").dialog({
                                    title : 'Titulo',
                                    width: 450,
                                    show: 'blind',
                                    hide: 'blind',
                                    modal: true,
                                    autoOpen: true,
                                    open: function() {
                                        var origen;
                                        if (row.clave.length >= 6) {
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
                                                                clave: row.clave,
                                                                tipoTitulo: modificarContratoBusiness.grupo.tipoTitulo,
                                                                origen: origen,
                                                                familia: modificarContratoBusiness.resolverFamilia(modificarContratoBusiness.grupo.tipoTitulo),
                                                                sinContrato: row.sinContrato}, "tituloRequest"),
                                                                method: "GET",
                                                                callback: function(data) {
                                                                    if (data) {
                                                                        titulo = data;
                                                                        modificarContratoPopulator.populatePopUpTitulo(data);
                                                                    }
                                                                },
                                                                responseObject: "titulo"});
                                    },
                                    close: function() {
                                        $(this).dialog("close");
                                        modificarContratoSelector.getPopUp("tituloPopUp").remove();
                                    },
                                    buttons: {
                                        "Aceptar": function() {
                                            if ($("#dameTitulos_popupSituar").length > 0) {
                                                $("#dameTitulos_popupSituar").dialog("close");
                                            }

                                            titulo.observaciones = modificarContratoSelector.getInput("observaciones", "tituloPopUp").val();
                                            if (recontratacion) {
//                                            	console.log(2);
                                                modificarContratoValidator.validarAltaTitulo(
                                                        titulo, 
                                                        situarPopupEvent, 
                                                        self.ingresarTituloRecontratado,
                                                        {clave: row.clave, 
                                                        contrato: modificarContratoBusiness.grupo.contrato, 
                                                        grupo: modificarContratoBusiness.grupo.nroGrupo, 
                                                        senial: modificarContratoBusiness.grupo.senial});
                                            } else {
//                                            	console.log(3);
                                                modificarContratoValidator.validarAltaTitulo(titulo, situarPopupEvent);
                                            }
                                        }
                                    }
                                });
                            });
                    return true;
                }, 
                {"titulosRequest.tituloABuscar": "", 
                 "titulosRequest.tipoTitulo": tipoTitulo,
                 "titulosRequest.buscarPor": $("#dameTitulos_funcionalidadAdicionalContainer :checked").first().val(),
                 "titulosRequest.familia": modificarContratoBusiness.resolverFamilia(tipoTitulo), 
                 "titulosRequest.senial": modificarContratoBusiness.grupo.senial,
                 "titulosRequest.marcaRecontratacion": modificarContratoBusiness.grupo.recontratacion,
                 "titulosRequest.estrenoOrepeticion": modificarContratoBusiness.grupo.er
                 },
                 "titulos",
                 false);
    } else {
        if ($("#dameGruposConReemplazo_popupSituar").length > 0) {
            $("#dameGruposConReemplazo_popupSituar").remove();
            modificarContratoBusiness.gruposAReemplazar(modificarContratoBusiness.gruposAReemplazar.req);
        }
    }
};
ModificarContratoBusiness.prototype.eliminarTitulos = function(cantidad) {
    var self = modificarContratoBusiness;
    situarPopupEvent.reset();
    situarPopupEvent.nombreSituar = "Eliminar Titulos";
    situarPopupEvent.setColumns(
              ["Clave","Titulo Castellano","Programado","Confirmada","A Consultar","ReRun","Gastos"],
              [{name:"clave", index:"clave"},{name:"tituloCastellano", index:"tituloCastellano"},
               {name:"programado", index:"programado"},{name:"exhibicionConfirmada", index:"exhibicionConfirmada"},
               {name:"aConsultar", index:"aConsultar"},{name:"reRun", index:"reRun"},{name:"gastos", index:"gastos"}]);
    situarPopupEvent.doAfterDraw = function() {
        situarPopupEvent.getPopup().dialog({buttons: { "OK": function() { 
            var data = situarPopupEvent.getGrid().jqGrid('getGridParam','data'), item;
            var titulosArray = [];
            if (data) {
                for (var i = 0; i <= data.length; i++) {
                    item = data[i];
                    if (item && item.cb) {
                        titulosArray.push({clave: item.clave});
                    }
                }
            }
            var data = {data: {contrato: modificarContratoBusiness.grupo.contrato, grupo: modificarContratoBusiness.grupo.nroGrupo, senial: modificarContratoBusiness.grupo.senial, titulos: titulosArray}};
            jQuery.ajax({
                    type: 'POST', 
                    url: 'titulosABorrar.action', 
                    data: JSON.stringify(data),
                    dataType: 'json',
                    contentType: 'application/json; charset=utf-8',
                    success: function(data) {
                        if (data.validacionModificacionContrato && data.validacionModificacionContrato.length && data.validacionModificacionContrato.length > 0) {
                            var hayErrores = false;
                            var hayWarnings = false;
                            var warnings  = new Array();
                            var errores  = new Array();
                            $.each(data.validacionModificacionContrato, function(i, l) {
                                if (l["tipo"] === "E") {
                                    hayErrores = true;
                                    errores.push(l["descripcion"]);
                                } else if (l["tipo"] === "W") {
                                    hayWarnings = true;
                                    warnings.push(l["descripcion"]);
                                }
                            });
                            if (hayErrores) {
                                popupConfirmacionEvent.confirmar = function () {
                                    popupConfirmacionEvent.remove();
                                };
                                popupConfirmacionEvent.cancelar = function () {
                                    popupConfirmacionEvent.remove();
                                };
                                popupConfirmacionEvent.afterDraw = function () {
                                    this.getBotonConfimar().remove();
                                    this.getBotonCancelar().remove();
                                };
                                popupConfirmacionEvent.popTitle = 'ERRORES';
                                popupConfirmacionEvent.create("errorPopUp", errores.join("<br/>"));
                                delete modificarContratoBusiness.grupo;
                            } else if (hayWarnings){
                                popupConfirmacionEvent.confirmar = function () {
                                    popupConfirmacionEvent.remove();
                                };
                                popupConfirmacionEvent.cancelar = function () {
                                    popupConfirmacionEvent.remove();
                                };
                                popupConfirmacionEvent.afterDraw = function () {
                                    this.getBotonConfimar().remove();
                                    this.getBotonCancelar().remove();
                                };
                                popupConfirmacionEvent.popTitle = 'WARNINGS';
                                popupConfirmacionEvent.create("warningsPopUp", warnings.join("<br/>"));
                            } else {
                                MESSAGE.ok("Contrato actualizado");
                            }
                        }
                        situarPopupEvent.getPopup().dialog("close");
                    }
                });
            } } });
        
        return;
    };
    situarPopupEvent.doAfterDrawGrid = function() {
        situarPopupEvent.cantidadSeleccionada = 0;
        
        situarPopupEvent.getGrid().jqGrid('setGridParam', {beforeSelectRow: function(rowid, e) {$(this).jqGrid('setSelection', rowid, true);} });
        
        situarPopupEvent.getGrid().jqGrid('setGridParam', {onSelectRow: function(rowid, status){
            var baseIndex = ($(this).getGridParam('page') - 1) * 10;
            var p = this.p, item = p.data[baseIndex + parseInt(rowid) - 1];
            var row = $(this).jqGrid('getRowData', rowid);
            if (typeof (item.cb) === "undefined") {
                //primera vez
                if ( (situarPopupEvent.cantidadSeleccionada < cantidad) && ((row.programado === "N") || (row.programado === " ")) && ((row.exhibicionConfirmada === "N") || (row.programado === " ")) ) {
                    item.cb = true;
                    situarPopupEvent.cantidadSeleccionada = situarPopupEvent.cantidadSeleccionada + 1;
                } else {
                    situarPopupEvent.getGrid().jqGrid('setSelection', rowid, false);
                }
            } else {
                if (!(item.cb)) {
                    if ( (situarPopupEvent.cantidadSeleccionada < cantidad) && ((row.programado === "N") || (row.programado === " ")) && ((row.exhibicionConfirmada === "N") || (row.programado === " ")) ) {
                        situarPopupEvent.cantidadSeleccionada = situarPopupEvent.cantidadSeleccionada + 1;
                        item.cb = true;
                    } else {
                        situarPopupEvent.getGrid().jqGrid('setSelection', rowid, false);
                        item.cb = false;
                    }
                } else {
                    situarPopupEvent.cantidadSeleccionada = situarPopupEvent.cantidadSeleccionada - 1;
                    item.cb = false;
                }
            }
        }});
        situarPopupEvent.getGrid().jqGrid('setGridParam', {onSelectAll: function(){
            return;
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
        
        if (situarPopupEvent.multiselect) {
            var inputSelectAll = "#cb_"+situarPopupEvent.div.id+"_gridSituar";
            $(inputSelectAll).remove();
        }

        return;
    };
    situarPopupEvent.acciones = [];
    situarPopupEvent.doAfterClose = function () { return; };
    situarPopupEvent.create(
            "dameTitulosAEliminarPorContrato.action", 
            function (row) {
                return false;
            }, 
            {"titulosRequest.contrato": self.contratoConCabecera.contrato, 
            "titulosRequest.grupo": modificarContratoBusiness.grupo.nroGrupo, 
            "titulosRequest.senial": self.contratoConCabecera.senialElegida}, 
            "titulosContratados",
             true,
             true);
};
ModificarContratoBusiness.prototype.ingresarCapitulos = function(cantidad) {
    var self = modificarContratoBusiness;
    
    if ($("#dameTitulos_popupSituar").length > 0) {
        $("#dameTitulos_popupSituar").dialog("close");
    }
    
    situarPopupEvent.reset();
    situarPopupEvent.nombreSituar = "Ingresar Capitulos";
    situarPopupEvent.setColumns(
          ["NroCapitulo","Parte","Titulo Capitulo", ""],
          [{name:"nroCapitulo", index:"nroCapitulo"},{name:"parte", index:"parte"},{name:"tituloCapitulo", index:"tituloCapitulo"},{name:"clave", index:"clave", hidden:true}]);
    situarPopupEvent.doAfterDraw = function() {
        situarPopupEvent.getFuncionalidadAdicionalContainer().empty();
        situarPopupEvent.getFuncionalidadAdicionalContainer().append("<span>CONTRATO: " + modificarContratoBusiness.grupo.contrato +  " - GRUPO: " + modificarContratoBusiness.grupo.nroGrupo + " - SENIAL: " + modificarContratoBusiness.grupo.senial + "</span>");

        situarPopupEvent.getPopup().dialog(
         {buttons: { "OK": function(evt) {
            var buttonDomElement = evt.target;
            $(buttonDomElement).attr('disabled', true);
            
            var data = situarPopupEvent.getGrid().jqGrid('getGridParam','data'), item;
            if (!(data)) { return; }

            var capitulosPartesArray = [];
            if (data) {
                for (var i = 0; i <= data.length; i++) {
                    item = data[i];
                    if (item && item.cb) {
                        capitulosPartesArray.push({clave: item.clave, capitulo: item.nroCapitulo, parte: item.parte});
                    }
                }
            }
            var dataRequest = {skipWarnings: true, data: {contrato: parseInt(modificarContratoBusiness.grupo.contrato), grupo: modificarContratoBusiness.grupo.nroGrupo, senial: modificarContratoBusiness.grupo.senial, clave: capitulosPartesArray[0].clave, capitulosPartes: capitulosPartesArray}};
            jQuery.ajax({
                    type: 'POST', 
                    url: 'capitulosAAgregar.action', 
                    data: JSON.stringify(dataRequest),
                    dataType: 'json',
                    contentType: 'application/json; charset=utf-8',
                    success: function(data) {
                        if (data.validacionModificacionContrato && data.validacionModificacionContrato.length && data.validacionModificacionContrato.length > 0) {
                            var hayErrores = false;
                            var hayWarnings = false;
                            var warnings  = new Array();
                            var errores  = new Array();
                            var hayCapitulos = false;
                            var fechaProceso = "";
                            var horaProceso = "";
                            var tgUsuario = "";
                            var tgWork = "";
                            
                            $.each(data.validacionModificacionContrato, 
                                   function(i, l) {
                                        if (l["tipo"] === "E") {
                                            hayErrores = true;
                                            errores.push(l["descripcion"]);
                                        } else if (l["tipo"] === "W") {
                                            hayWarnings = true;
                                            warnings.push(l["descripcion"]);
                                        } else if (l["tipo"] === "F_HAY_CAPITULOS")  {
                                            hayCapitulos = l["descripcion"] === "S";
                                        } else if (l["tipo"] === "D_FECHA_PROC")  {
                                            fechaProceso = l["descripcion"];
                                        } else if (l["tipo"] === "D_HORA_PROC")  {
                                            horaProceso = l["descripcion"];
                                        } else if (l["tipo"] === "D_TG_USUARIO")  {
                                            tgUsuario = l["descripcion"];
                                        } else if (l["tipo"] === "D_TG_WORK")  {
                                            tgWork = l["descripcion"];
                                        }
                            });
                            if (hayErrores) {
                                MESSAGE.error(errores[0]);
                                delete modificarContratoBusiness.grupo;
                            } else if (hayWarnings) {
                                popupConfirmacionEvent.confirmar = function () {
                                    dataRequest.skipWarnings = true;
                                    dataRequest.data.fechaProceso = fechaProceso;
                                    dataRequest.data.horaProceso = parseInt(horaProceso);
                                    jQuery.ajax({
                                        type: 'POST', 
                                        url: 'capitulosAAgregar.action', 
                                        data: JSON.stringify(dataRequest),
                                        dataType: 'json',
                                        contentType: 'application/json; charset=utf-8',
                                        success: function(data) {
                                            MESSAGE.ok("Capitulos agregados");
                                            if (modificarContratoBusiness.ingresarCapitulosRecontratados.llamarReRun) {
                                                modificarContratoBusiness.trabajarReRun(situarPopupEvent.req.clave, 
                                                        modificarContratoBusiness.ingresarCapitulosRecontratados.modoReRun, 
                                                        modificarContratoBusiness.ingresarCapitulosRecontratados.procesoReRun);
                                            }
                                         } 
                                    });
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
                            } else if (hayCapitulos) {
                                //TODO-REMITOS
                                MESSAGE.alert("*REMITOS*");
                            } else {
                                dataRequest.skipWarnings = true;
                                dataRequest.data.fechaProceso = fechaProceso;
                                dataRequest.data.horaProceso = parseInt(horaProceso);
                                jQuery.ajax({
                                    type: 'POST', 
                                    url: 'capitulosAAgregar.action', 
                                    data: JSON.stringify(dataRequest),
                                    dataType: 'json',
                                    contentType: 'application/json; charset=utf-8',
                                    success: function(data) {
                                        MESSAGE.ok("Capitulos agregados");
                                        if (modificarContratoBusiness.ingresarCapitulosRecontratados.llamarReRun) {
                                            modificarContratoBusiness.trabajarReRun(situarPopupEvent.req.clave, 
                                                    modificarContratoBusiness.ingresarCapitulosRecontratados.modoReRun, 
                                                    modificarContratoBusiness.ingresarCapitulosRecontratados.procesoReRun);
                                        }
                                     } 
                                });
                            }
                        }
                        situarPopupEvent.getPopup().dialog("close");
                    }
                });
            } } });
        return;
    };
    situarPopupEvent.doAfterDrawGrid = function() {
        situarPopupEvent.cantidadSeleccionada = 0;
        
        situarPopupEvent.getGrid().jqGrid('setGridParam', {beforeSelectRow: function(rowid, e) {$(this).jqGrid('setSelection', rowid, true);} });
        
        situarPopupEvent.getGrid().jqGrid('setGridParam', {ondblClickRow: function(rowid) { return; } });

        situarPopupEvent.getGrid().jqGrid('setGridParam', {onSelectRow: function(rowid, status){
            var baseIndex = ($(this).getGridParam('page') - 1) * 10;
            var p = this.p, item = p.data[baseIndex + parseInt(rowid) - 1];
            if (typeof (item.cb) === "undefined") {
                //primera vez
                if (situarPopupEvent.cantidadSeleccionada < cantidad) {
                    item.cb = true;
                    situarPopupEvent.cantidadSeleccionada = situarPopupEvent.cantidadSeleccionada + 1;
                } else {
                    situarPopupEvent.getGrid().jqGrid('setSelection', rowid, false);
                }
            } else {
                if (!(item.cb)) {
                    if (situarPopupEvent.cantidadSeleccionada < cantidad) {
                        situarPopupEvent.cantidadSeleccionada = situarPopupEvent.cantidadSeleccionada + 1;
                        item.cb = true;
                    } else {
                        situarPopupEvent.getGrid().jqGrid('setSelection', rowid, false);
                        item.cb = false;
                    }
                } else {
                    situarPopupEvent.cantidadSeleccionada = situarPopupEvent.cantidadSeleccionada - 1;
                    item.cb = false;
                }
            }
        }});
        situarPopupEvent.getGrid().jqGrid('setGridParam', {onSelectAll: function(){
            return;
        }});
        situarPopupEvent.getGrid().jqGrid('setGridParam', {loadComplete: function() {
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
        
        if (situarPopupEvent.multiselect) {
            var inputSelectAll = "#cb_"+situarPopupEvent.div.id+"_gridSituar";
            $(inputSelectAll).remove();
        }

        return;
    };
    situarPopupEvent.acciones = [];
    situarPopupEvent.doAfterClose = function () { return; };
    situarPopupEvent.create(
            "dameCapitulosParaAgregar.action", 
            function (row) {
                return false;
            }, 
            {"titulosRequest.contrato": self.contratoConCabecera.contrato, 
             "titulosRequest.grupo": modificarContratoBusiness.grupo.nroGrupo, 
             "titulosRequest.senial": self.contratoConCabecera.senialElegida},
            "capitulos",
             true,
             true);
};
ModificarContratoBusiness.prototype.eliminarCapitulos = function(cantidad) {
    var self = modificarContratoBusiness;

    if ($("#dameTitulos_popupSituar").length > 0) {
        $("#dameTitulos_popupSituar").dialog("close");
    }

    situarPopupEvent.reset();
    situarPopupEvent.nombreSituar = "Eliminar Capitulos";
    situarPopupEvent.setColumns(
            ["NroCapitulo","Parte","Titulo Capitulo", "Conf. Exhibicion", "Programado", "Copia", "Original", "", ""],
            [{name:"nroCapitulo", index:"nroCapitulo"},{name:"parte", index:"parte"},{name:"tituloCapitulo", index:"tituloCapitulo"},
             {name:"confExhibicion", index:"confExhibicion"},{name:"programado", index:"programado"}, {name:"copia", index:"copia"}, {name:"original", index:"original"}, {name:"clave", index:"clave", hidden:true}, {name:"puedoBorrar", index:"puedoBorrar", hidden:true}]);
    situarPopupEvent.doAfterDraw = function() {
        situarPopupEvent.getPopup().dialog({buttons: { "OK": function() {
            var data = situarPopupEvent.getGrid().jqGrid('getGridParam','data'), item;
            if (!(data)) { return; }
            var capitulosPartesArray = [];
            for (var i = 0; i <= data.length; i++) {
                item = data[i];
                if (item && item.cb) {
                    capitulosPartesArray.push({clave: item.clave, capitulo: item.nroCapitulo, parte: item.parte});
                }
            }
            var dataRequest = {data: {contrato: modificarContratoBusiness.grupo.contrato, grupo: modificarContratoBusiness.grupo.nroGrupo, senial: modificarContratoBusiness.grupo.senial, capitulosPartes: capitulosPartesArray}};
            jQuery.ajax({
                    type: 'POST', 
                    url: 'capitulosABorrar.action', 
                    data: JSON.stringify(dataRequest),
                    dataType: 'json',
                    contentType: 'application/json; charset=utf-8',
                    success: function(data) {
                        if (data.validacionModificacionContrato) {
                            var hayErrores = false;
                            var hayWarnings = false;
                            var warnings  = new Array();
                            var errores  = new Array();
                            $.each(data.validacionModificacionContrato, function(i, l) {
                                if (l["tipo"] === "E") {
                                    hayErrores = true;
                                    errores.push(l["descripcion"]);
                                } else if (l["tipo"] === "W") {
                                    hayWarnings = true;
                                    warnings.push(l["descripcion"]);
                                }
                            });
                            if (hayErrores) {
                                popupConfirmacionEvent.confirmar = function () {
                                    popupConfirmacionEvent.remove();
                                };
                                popupConfirmacionEvent.cancelar = function () {
                                    popupConfirmacionEvent.remove();
                                };
                                popupConfirmacionEvent.afterDraw = function () {
                                    this.getBotonConfimar().remove();
                                    this.getBotonCancelar().remove();
                                };
                                popupConfirmacionEvent.popTitle = 'ERRORES';
                                popupConfirmacionEvent.create("errorPopUp", errores.join("<br/>"));
                                delete modificarContratoBusiness.grupo;
                            } else if (hayWarnings) {
                                popupConfirmacionEvent.confirmar = function () {
                                    dataRequest.skipWarnings = true;
                                    jQuery.ajax({
                                        type: 'POST', 
                                        url: 'capitulosABorrar.action', 
                                        data: JSON.stringify(dataRequest),
                                        dataType: 'json',
                                        contentType: 'application/json; charset=utf-8',
                                        success: function(data) {
                                            MESSAGE.ok("Capitulos eliminados");
                                            if (modificarContratoBusiness.ingresarCapitulosRecontratados.llamarReRun) {
                                                modificarContratoBusiness.trabajarReRun(situarPopupEvent.req.clave, 
                                                        modificarContratoBusiness.ingresarCapitulosRecontratados.modoReRun, 
                                                        modificarContratoBusiness.ingresarCapitulosRecontratados.procesoReRun);
                                            }
                                         } 
                                    });
                                    popupConfirmacionEvent.remove();
                                };
                                popupConfirmacionEvent.cancelar = function () {
                                    popupConfirmacionEvent.remove();
                                };
                                popupConfirmacionEvent.afterDraw = function () {
                                    this.getBotonConfimar().remove();
                                    this.getBotonCancelar().remove();
                                };
                                popupConfirmacionEvent.popTitle = 'WARNINGS';
                                popupConfirmacionEvent.create("warningsPopUp", warnings.join("<br/>"));
                                //modificarContratoBusiness.modificacionDeGrupo(grupo);
                            } else {
                                MESSAGE.ok("Capitulos eliminados");
                            }
                        }
                        situarPopupEvent.getPopup().dialog("close");
                    }
                });
            } } });
        return;
    };
    situarPopupEvent.doAfterDrawGrid = function() {
        situarPopupEvent.cantidadSeleccionada = 0;
        
        situarPopupEvent.getGrid().jqGrid('setGridParam', {beforeSelectRow: function(rowid, e) {$(this).jqGrid('setSelection', rowid, true);} });
        
        situarPopupEvent.getGrid().jqGrid('setGridParam', {ondblClickRow: function(rowid) { return; } });

        situarPopupEvent.getGrid().jqGrid('setGridParam', {onSelectRow: function(rowid, status){
            var baseIndex = ($(this).getGridParam('page') - 1) * 10;
            var p = this.p, item = p.data[baseIndex + parseInt(rowid) - 1];
            var row = $(this).jqGrid('getRowData', rowid);
            if (typeof (item.cb) === "undefined") {
                //primera vez
                if ((situarPopupEvent.cantidadSeleccionada < cantidad) && (row.puedoBorrar === "S")) {
                    item.cb = true;
                    situarPopupEvent.cantidadSeleccionada = situarPopupEvent.cantidadSeleccionada + 1;
                } else {
                    situarPopupEvent.getGrid().jqGrid('setSelection', rowid, false);
                }
            } else {
                if (!(item.cb)) {
                    //Estaba destildado
                    if ((situarPopupEvent.cantidadSeleccionada < cantidad)  && (row.puedoBorrar === "S")) {
                        situarPopupEvent.cantidadSeleccionada = situarPopupEvent.cantidadSeleccionada + 1;
                        item.cb = true;
                    } else {
                        situarPopupEvent.getGrid().jqGrid('setSelection', rowid, false);
                        item.cb = false;
                    }
                } else {
                    situarPopupEvent.cantidadSeleccionada = situarPopupEvent.cantidadSeleccionada - 1;
                    item.cb = false;
                }
            }
        }});
        situarPopupEvent.getGrid().jqGrid('setGridParam', {onSelectAll: function(){
            return;
        }});
        situarPopupEvent.getGrid().jqGrid('setGridParam', {loadComplete: function() {
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
        
        if (situarPopupEvent.multiselect) {
            var inputSelectAll = "#cb_"+situarPopupEvent.div.id+"_gridSituar";
            $(inputSelectAll).remove();
        }

        return;
    };
    situarPopupEvent.acciones = [];
    situarPopupEvent.doAfterClose = function () { return; };
    situarPopupEvent.create(
            "dameCapitulosParaEliminar.action", 
            function (row) {
                return false;
            }, 
            {"titulosRequest.contrato": self.contratoConCabecera.contrato, 
            "titulosRequest.grupo": modificarContratoBusiness.grupo.nroGrupo, 
            "titulosRequest.senial": self.contratoConCabecera.senialElegida}, 
            "capitulos",
             true,
             true);
};
ModificarContratoBusiness.prototype.ingresarTituloRecontratado = function(req) {
    var self = modificarContratoBusiness;
    if ($("#dameTitulos_popupSituar").length > 0) {
        $("#dameTitulos_popupSituar").dialog("close");
    }
    situarPopupEvent.getPopup().dialog("close");
    situarPopupEvent.reset();
    situarPopupEvent.nombreSituar = "Ingresar Titulo Recontratado";
    situarPopupEvent.setColumns(
            ["Contrato","Grupo","Clave","Titulo","Formula","","",""],
            [{name:"contrato", index:"contrato"},{name:"grupo", index:"grupo"},{name:"clave", index:"clave"},
             {name:"tituloCastellano", index:"tituloCastellano"},{name:"formula", index:"formula"},
             {name:"puedeElegir", index:"puedeElegir", hidden: true}, {name:"puedeElegirParcial", index:"puedeElegirParcial", hidden: true}, 
             {name:"puedeElegirTodos", index:"puedeElegirTodos", hidden: true}]);
    situarPopupEvent.doAfterDraw = function() {
        return;
    };
    situarPopupEvent.doAfterDrawGrid = function() {
        return;
    };
    situarPopupEvent.acciones = [
                                 {callback: function(rowData){
                                         situarPopupEvent.getPopup().dialog("close");
                                         modificarContratoBusiness.ingresarCapitulosRecontratados(
                                                {clave: rowData.clave,
                                                 senial: modificarContratoBusiness.grupo.senial,
                                                 grupo: rowData.grupo,
                                                 contrato: rowData.contrato,
                                                 puedeElegirParcial: "N"}, 
                                                 "Seleccion");
                                         }, 
                                  nombre: "Seleccion", 
                                  imagen: "bullet",
                                  habilitado: "puedeElegir"},

                                 {callback: function(rowData){
                                         situarPopupEvent.getPopup().dialog("close");
                                         modificarContratoBusiness.grabarTituloContratadoCCTodos(
                                                     {clave: rowData.clave,
                                                     senial: modificarContratoBusiness.grupo.senial,
                                                     grupo: rowData.grupo,
                                                     contrato: rowData.contrato,
                                                     contratoMod: modificarContratoBusiness.contratoConCabecera.contrato,
                                                     grupoMod: modificarContratoBusiness.grupo.nroGrupo,
                                                     senialMod: modificarContratoBusiness.grupo.senial
                                                     });
                                         } , 
                                  nombre: "Elegir Todos", 
                                  imagen: "bullet",
                                  habilitado: "puedeElegirTodos"}
                               ];
    situarPopupEvent.doAfterClose = function () { return; };
    situarPopupEvent.create(
            "dameTitulosARecontratar.action", 
            function (row) {
                return;
            }, 
            {"titulosRecontratadosRequest.clave": req.clave, 
             "titulosRecontratadosRequest.senial": req.senial,
             "titulosRecontratadosRequest.contrato": req.contrato,
             "titulosRecontratadosRequest.grupo": req.grupo},
             "titulosContratados",
             true);
};
ModificarContratoBusiness.prototype.ingresarCapitulosRecontratados = function(req, modo) {
    var self = modificarContratoBusiness;

    if ($("#dameTitulos_popupSituar").length > 0) {
        $("#dameTitulos_popupSituar").dialog("close");
    }
    situarPopupEvent.getPopup().dialog("close");
    situarPopupEvent.reset();
    situarPopupEvent.nombreSituar = "Ingresar Capitulo Recontratado";
    situarPopupEvent.req = req;
    situarPopupEvent.setColumns(
            ["Capitulo","Parte","Titulo", ""],
            [{name:"nroCapitulo", index:"nroCapitulo"}, {name:"parte", index:"parte"}, {name:"tituloCapitulo", index:"tituloCapitulo"}, 
             {name:"seleccionado", index:"seleccionado", hidden: true}]);
    situarPopupEvent.doAfterDraw = function() {
        situarPopupEvent.getPopup().dialog({buttons: { "OK": function() { 
            var data = situarPopupEvent.getGrid().jqGrid('getGridParam','data'), item;
            var capitulosPartesArray = [];
            if (data) {
                for (var i = 0; i <= data.length; i++) {
                    item = data[i];
                    if (item && item.cb) {
                        capitulosPartesArray.push({capitulo: item.nroCapitulo, parte: item.parte});
                    }
                }
            }
            var dataRequest = {skipWarnings: false, data: {contrato: parseInt(modificarContratoBusiness.grupo.contrato), grupo: modificarContratoBusiness.grupo.nroGrupo, senial: modificarContratoBusiness.grupo.senial, clave: situarPopupEvent.req.clave, capitulosPartes: capitulosPartesArray} };
            jQuery.ajax({
                    type: 'POST', 
                    url: 'altaTituloContratadoCC.action', 
                    data: JSON.stringify(dataRequest),
                    dataType: 'json',
                    contentType: 'application/json; charset=utf-8',
                    success: function(data) {
                        situarPopupEvent.getPopup().dialog("close");
                        if (data.validacionModificacionContrato && data.validacionModificacionContrato.length && data.validacionModificacionContrato.length > 0) {
                            var llamarReRun = false;
                            var modoReRun = null;
                            var procesoReRun = null;
                            
                            $.each(data.validacionModificacionContrato, function(i, l){
                                if ( (l["tipo"] === "RR") && (l["descripcion"] === "S") ) {
                                    llamarReRun = true;
                                } else if (l["tipo"] === "MRR") {
                                    modoReRun = l["descripcion"];
                                } else if (l["tipo"] === "PRR") {
                                    procesoReRun = l["descripcion"];
                                }
                            });
                            MESSAGE.ok("Capitulos Ingresados");
                            if (llamarReRun) {
                                modificarContratoBusiness.trabajarReRun(situarPopupEvent.req.clave, modoReRun, procesoReRun);
                            } else{
                                modificarContratoSelector.getPopUp("grupoPopUp").dialog("close");
                            }
                        }
                    }
                });
            } } });
        return;
    };
    situarPopupEvent.doAfterDrawGrid = function() {
        situarPopupEvent.cantidadSeleccionada = 0;
        
        situarPopupEvent.getGrid().jqGrid('setGridParam', {beforeSelectRow: function(rowid, e) {$(this).jqGrid('setSelection', rowid, true);} });
        
        situarPopupEvent.getGrid().jqGrid('setGridParam', {ondblClickRow: function(rowid) { return; } });

        situarPopupEvent.getGrid().jqGrid('setGridParam', {onSelectRow: function(rowid, status){
            var baseIndex = ($(this).getGridParam('page') - 1) * 10;
            var p = this.p, item = p.data[baseIndex + parseInt(rowid) - 1];
            if (typeof (item.cb) === "undefined") {
                //primera vez
                if (situarPopupEvent.cantidadSeleccionada < modificarContratoBusiness.grupo.cantTitulos) {
                    item.cb = true;
                    situarPopupEvent.cantidadSeleccionada = situarPopupEvent.cantidadSeleccionada + 1;
                } else {
                    situarPopupEvent.getGrid().jqGrid('setSelection', rowid, false);
                }
            } else {
                if (!(item.cb)) {
                    if (situarPopupEvent.cantidadSeleccionada < modificarContratoBusiness.grupo.cantTitulos) {
                        situarPopupEvent.cantidadSeleccionada = situarPopupEvent.cantidadSeleccionada + 1;
                        item.cb = true;
                    } else {
                        item.cb = false;
                        situarPopupEvent.getGrid().jqGrid('setSelection', rowid, false);
                    }
                } else {
                    situarPopupEvent.cantidadSeleccionada = situarPopupEvent.cantidadSeleccionada - 1;
                    item.cb = false;
                }
            }
        }});
        situarPopupEvent.getGrid().jqGrid('setGridParam', {onSelectAll: function(){
            return;
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
        
        if (situarPopupEvent.multiselect) {
            var inputSelectAll = "#cb_"+situarPopupEvent.div.id+"_gridSituar";
            $(inputSelectAll).remove();
        }

        return;
    };
    situarPopupEvent.acciones = [];
    situarPopupEvent.doAfterClose = function () { return; };
    situarPopupEvent.create(
            "dameCapitulosARecontratar.action", 
            function (row) {
                return false;
            }, 
            {"titulosRecontratadosRequest.clave":req.clave, 
             "titulosRecontratadosRequest.senial": req.senial,
             "titulosRecontratadosRequest.contrato": req.contrato,
             "titulosRecontratadosRequest.grupo": req.grupo,
             "titulosRecontratadosRequest.puedeElegirParcial": req.puedeElegirParcial},
             "capitulos",
             true,
             true);
};
ModificarContratoBusiness.prototype.grabarTituloContratadoCCTodos = function(req) {
    var data = {data: {contrato: req.contrato, grupo: req.grupo, senial: req.senial, clave: req.clave, contratoMod: req.contratoMod, grupoMod: req.grupoMod, senialMod: req.senialMod}};
    modificarContratoBusiness.req = req;
    jQuery.ajax({
            type: 'POST', 
            url: 'altaTituloContratadoCCTodos.action', 
            data: JSON.stringify(data),
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            success: function(data) {
                situarPopupEvent.getPopup().dialog("close");
                if (data.validacionModificacionContrato && data.validacionModificacionContrato.length != undefined && data.validacionModificacionContrato.length > 0) {
                    var llamarReRun = false;
                    var modoReRun = "";
                    var procesoReRun = "";
                    $.each(data.validacionModificacionContrato, function(i, l){
                        if ( (l["tipo"] === "RR") && (l["descripcion"] === "S") ) {
                            llamarReRun = true;
                        } else if (l["tipo"] === "MRR") {
                            modoReRun = l["descripcion"];
                        } else if (l["tipo"] === "PRR") {
                            procesoReRun = l["descripcion"];
                        }
                    });
                    if (llamarReRun) {
                        modificarContratoBusiness.trabajarReRun(modificarContratoBusiness.req.clave, modoReRun, procesoReRun);
                    } else{
                        MESSAGE.ok("Capitulos Ingresados");
                        modificarContratoSelector.getPopUp("grupoPopUp").dialog("close");
                    }
                }
            }
    });
};
ModificarContratoBusiness.prototype.validarEnlaceAnterior = function(request) {
    modificarContratoBusiness.rerunRequest = request;
    ModificarContratoStaticService.doRequest(
            {action:"validarEnlaceAnterior.action",
             request: Component.serialize(request, "rerunRequest"),
             method: "GET",
             callback: function(data) {
                 if (data && data.length && data.length > 0) {
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
                         MESSAGE.error(errores[0], 20000);
                     } else if (hayWarnings){
                         popupConfirmacionEvent.confirmar = function () {
                             ModificarContratoStaticService.doRequest(
                                     {action:"actualizarEnlaceAnterior.action",
                                      request: Component.serialize(modificarContratoBusiness.rerunRequest, "rerunRequest"),
                                      method: "GET",
                                      callback: function(data) {
                                          if (data) {
                                              situarPopupEvent.getPopup().dialog({
                                                  close: function(event, ui) {
                                                      situarPopupEvent.setColumns(undefined, undefined);
                                                      situarPopupEvent.getDiv().remove();
                                                      $(this).remove();
                                                      modificarContratoBusiness.ingresarTitulos(modificarContratoBusiness.cantTitulosAAgregar, modificarContratoBusiness.grupo.tipoTitulo);
                                                  }
                                              });
                                              
                                              var request = {"titulosRecontratadosRequest.contrato": modificarContratoBusiness.rerunRequest.contrato, 
                                               "titulosRecontratadosRequest.grupo": modificarContratoBusiness.rerunRequest.grupo,
                                               "titulosRecontratadosRequest.clave": modificarContratoBusiness.rerunRequest.clave,
                                               "titulosRecontratadosRequest.modo": modificarContratoBusiness.modo,
                                               "titulosRecontratadosRequest.senial": modificarContratoBusiness.rerunRequest.senial};
                                              
                                              situarPopupEvent.service.getList({data: request, action: "dameContratosConReRun.action", responseObject: "contratosConReRun", that: situarPopupEvent});
                                          } else {
                                              MESSAGE.error("Ocurrio un error al actualizar enlace anterior");
                                          }
                                      },
                                      responseObject: "rerunResponse"});
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
                     }
                 } else {
                     ModificarContratoStaticService.doRequest(
                            {action:"actualizarEnlaceAnterior.action",
                             request: Component.serialize(modificarContratoBusiness.rerunRequest, "rerunRequest"),
                             method: "GET",
                             callback: function(data) {
                                 if (data) {
                                     situarPopupEvent.getPopup().dialog({
                                         close: function(event, ui) {
                                             situarPopupEvent.setColumns(undefined, undefined);
                                             situarPopupEvent.getDiv().remove();
                                             $(this).remove();
                                             modificarContratoBusiness.ingresarTitulos(modificarContratoBusiness.cantTitulosAAgregar, modificarContratoBusiness.grupo.tipoTitulo);
                                         }
                                     });
                                     
                                     var request = {"titulosRecontratadosRequest.contrato": modificarContratoBusiness.rerunRequest.contrato, 
                                      "titulosRecontratadosRequest.grupo": modificarContratoBusiness.rerunRequest.grupo,
                                      "titulosRecontratadosRequest.clave": modificarContratoBusiness.rerunRequest.clave,
                                      "titulosRecontratadosRequest.modo": modificarContratoBusiness.modo,
                                      "titulosRecontratadosRequest.senial": modificarContratoBusiness.rerunRequest.senial};
                                     
                                     situarPopupEvent.service.getList({data: request, action: "dameContratosConReRun.action", responseObject: "contratosConReRun", that: situarPopupEvent});
                                 } else {
                                     MESSAGE.error("Ocurrio un error al actualizar enlace anterior");
                                 }
                             },
                             responseObject: "rerunResponse"});
              } },
              responseObject: "validacionModificacionContrato"});
};
ModificarContratoBusiness.prototype.validarEnlacePosterior = function(request) {
    modificarContratoBusiness.rerunRequest = request;
    ModificarContratoStaticService.doRequest(
            {action:"validarEnlacePosterior.action",
             request: Component.serialize(request, "rerunRequest"),
             method: "GET",
             callback: function(data) {
                 if (data && data.length && data.length > 0) {
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
                         MESSAGE.error(errores[0], 20000);
                     } else if (hayWarnings){
                         popupConfirmacionEvent.confirmar = function () {
                             ModificarContratoStaticService.doRequest(
                                     {action:"actualizarEnlacePosterior.action",
                                      request: Component.serialize(modificarContratoBusiness.rerunRequest, "rerunRequest"),
                                      method: "GET",
                                      callback: function(data) {
                                          if (data) {
                                              situarPopupEvent.getPopup().dialog({
                                                  close: function(event, ui) {
                                                      situarPopupEvent.setColumns(undefined, undefined);
                                                      situarPopupEvent.getDiv().remove();
                                                      $(this).remove();
                                                      modificarContratoBusiness.ingresarTitulos(modificarContratoBusiness.cantTitulosAAgregar, modificarContratoBusiness.grupo.tipoTitulo);
                                                  }
                                              });
                                              
                                              var request = {"titulosRecontratadosRequest.contrato": modificarContratoBusiness.rerunRequest.contrato, 
                                               "titulosRecontratadosRequest.grupo": modificarContratoBusiness.rerunRequest.grupo,
                                               "titulosRecontratadosRequest.clave": modificarContratoBusiness.rerunRequest.clave,
                                               "titulosRecontratadosRequest.modo": modificarContratoBusiness.modo,
                                               "titulosRecontratadosRequest.senial": modificarContratoBusiness.rerunRequest.senial};
                                              
                                              situarPopupEvent.service.getList({data: request, action: "dameContratosConReRun.action", responseObject: "contratosConReRun", that: situarPopupEvent});
                                          } else {
                                              MESSAGE.error("Ocurrio un error al actualizar enlace posterior");
                                          }
                                      },
                                      responseObject: "rerunResponse"});
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
                     }
                 } else {
                     ModificarContratoStaticService.doRequest(
                             {action:"actualizarEnlacePosterior.action",
                              request: Component.serialize(modificarContratoBusiness.rerunRequest, "rerunRequest"),
                              method: "GET",
                              callback: function(data) {
                                  if (data) {
                                      situarPopupEvent.getPopup().dialog({
                                          close: function(event, ui) {
                                              situarPopupEvent.setColumns(undefined, undefined);
                                              situarPopupEvent.getDiv().remove();
                                              $(this).remove();
                                              modificarContratoBusiness.ingresarTitulos(modificarContratoBusiness.cantTitulosAAgregar, modificarContratoBusiness.grupo.tipoTitulo);
                                          }
                                      });
                                      
                                      var request = {"titulosRecontratadosRequest.contrato": modificarContratoBusiness.rerunRequest.contrato, 
                                       "titulosRecontratadosRequest.grupo": modificarContratoBusiness.rerunRequest.grupo,
                                       "titulosRecontratadosRequest.clave": modificarContratoBusiness.rerunRequest.clave,
                                       "titulosRecontratadosRequest.modo": modificarContratoBusiness.modo,
                                       "titulosRecontratadosRequest.senial": modificarContratoBusiness.rerunRequest.senial};
                                      
                                      situarPopupEvent.service.getList({data: request, action: "dameContratosConReRun.action", responseObject: "contratosConReRun", that: situarPopupEvent});
                                  } else {
                                      MESSAGE.error("Ocurrio un error al actualizar enlace posterior");
                                  }
                              },
                              responseObject: "rerunResponse"});
                 }
              },
              responseObject: "validacionModificacionContrato"});
};
ModificarContratoBusiness.prototype.validarRerunDesenlace = function(request) {
    modificarContratoBusiness.rerunRequest = request;
    ModificarContratoStaticService.doRequest(
            {action:"validarRerunDesenlace.action",
             request: Component.serialize(request, "rerunRequest"),
             method: "GET",
             callback: function(data) {
                 if (data && data.length && data.length > 0) {
                     MESSAGE.error(data[0]["descripcion"], 20000);
                 } else {
                     ModificarContratoStaticService.doRequest(
                             {action:"actualizarRerunDesenlace.action",
                              request: Component.serialize(modificarContratoBusiness.rerunRequest, "rerunRequest"),
                              method: "GET",
                              callback: function(data) {
                                  if (data) {
                                      situarPopupEvent.getPopup().dialog({
                                          close: function(event, ui) {
                                              situarPopupEvent.setColumns(undefined, undefined);
                                              situarPopupEvent.getDiv().remove();
                                              $(this).remove();
                                              modificarContratoBusiness.ingresarTitulos(modificarContratoBusiness.cantTitulosAAgregar, modificarContratoBusiness.grupo.tipoTitulo);
                                          }
                                      });
                                      
                                      var request = {"titulosRecontratadosRequest.contrato": modificarContratoBusiness.rerunRequest.contrato, 
                                       "titulosRecontratadosRequest.grupo": modificarContratoBusiness.rerunRequest.grupo,
                                       "titulosRecontratadosRequest.clave": modificarContratoBusiness.rerunRequest.clave,
                                       "titulosRecontratadosRequest.modo": modificarContratoBusiness.modo,
                                       "titulosRecontratadosRequest.senial": modificarContratoBusiness.rerunRequest.senial};
                                      
                                      situarPopupEvent.service.getList({data: request, action: "dameContratosConReRun.action", responseObject: "contratosConReRun", that: situarPopupEvent});
                                  } else {
                                      MESSAGE.error("Ocurrio un error al actualizar enlace anterior");
                                  }
                              },
                              responseObject: "rerunResponse"});
                 }
              },
              responseObject: "validacionModificacionContrato"});
};
ModificarContratoBusiness.prototype.modificarTitulo = function(rowId) {
    if (rowId) {
        modificarContratoDrawer.drawPopUpTituloConGrupo.row = modificarContratoSelector.getGrillaTitulos().jqGrid('getRowData', rowId);
        Component.get("html/modificarContrato/PopUpTituloConGrupo.html", modificarContratoDrawer.drawPopUpTituloConGrupo);
    } else {
        MESSAGE.error("Debe seleccionar un grupo");
    }
    return;
};
ModificarContratoBusiness.prototype.verCapitulos = function(rowId) {
    var row = modificarContratoSelector.getGrillaTitulos().jqGrid('getRowData', rowId);
    
    situarPopupEvent.reset();
    situarPopupEvent.nombreSituar = "Capitulos";
    situarPopupEvent.setColumns(
        ["NroCapitulo","Parte","Titulo Capitulo", "Conf. Exhibicion", "Copia", "Original"],
        [{name:"nroCapitulo", index:"nroCapitulo"},{name:"parte", index:"parte"},{name:"tituloCapitulo", index:"tituloCapitulo"},
         {name:"confExhibicion", index:"confExhibicion"},{name:"copia", index:"copia"}, {name:"original", index:"original"}]);
    situarPopupEvent.doAfterDraw = function() {
        situarPopupEvent.getFuncionalidadAdicionalContainer().empty();
        situarPopupEvent.getFuncionalidadAdicionalContainer().append(
                "<span>CONTRATO: " + modificarContratoBusiness.grupo.contrato +  " - GRUPO: " + modificarContratoBusiness.grupo.nroGrupo + " - SEÑAL: " + modificarContratoBusiness.contratoConCabecera.senialElegida + " - CLAVE: " + row.clave + "</span>");
        return;
    };
    situarPopupEvent.doAfterDrawGrid = function() {
        return;
    };
    situarPopupEvent.acciones = [];
    situarPopupEvent.doAfterClose = function () { return; };
    situarPopupEvent.create(
            "dameCapitulosPorTituloContratado.action", 
            function (row) {
                return false;
            }, 
            {"titulosRecontratadosRequest.contrato": modificarContratoBusiness.contratoConCabecera.contrato, 
             "titulosRecontratadosRequest.grupo": modificarContratoBusiness.contratoConCabecera.grupoElegido,
             "titulosRecontratadosRequest.clave": row.clave,
             "titulosRecontratadosRequest.senial": modificarContratoBusiness.contratoConCabecera.senialElegida}, 
             "capitulos",
             true);
    return;
};

ModificarContratoBusiness.prototype.ampliarDerechos = function(request, skipWarnings) {
    modificarContratoBusiness.ampliarDerechos.request = request;
    popupConfirmacionEvent.confirmar = function () {
        if (Validator.isEmpty($("#fechaInput"), true)) {
            return;
        }
        var fechaHastaPayTV = $("#fechaInput").val();
        var observaciones = $("#observacionInput").val();
        modificarContratoBusiness.ampliarDerechos.request.fechaHastaPayTV = fechaHastaPayTV;
        modificarContratoBusiness.ampliarDerechos.request.observaciones = observaciones;
        ModificarContratoStaticService.doRequest(
                 {action:"ampliarDerechos.action",
                  request: Component.serialize(modificarContratoBusiness.ampliarDerechos.request, "vigenciaRequest")  + ( skipWarnings ? "&skipWarnings=" + skipWarnings : "" ),
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
                                  errores.push(l["descripcion"]);
                              } else if (l["tipo"] === "W") {
                                  hayWarnings = true;
                                  warnings.push(l["descripcion"]);
                              }
                          });
                          if (hayErrores) {
                              MESSAGE.error(errores[0], 20000);
                          } else if (hayWarnings){
                              popupConfirmacionEvent.confirmar = function () {
                                  modificarContratoBusiness.ampliarDerechos(modificarContratoBusiness.ampliarDerechos.request, true);
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
                          }
                      } else {
                          MESSAGE.error("Ocurrio un error al ampliar derechos");
                      }
                      modificarContratoBusiness.trabjarConVigencias(
                                                          modificarContratoBusiness.trabjarConVigencias.rowId,
                                                          modificarContratoBusiness.trabjarConVigencias.modo);
                  },
                  responseObject: "validacionModificacionContrato"});
        popupConfirmacionEvent.remove();
    };
    popupConfirmacionEvent.cancelar = function () {
        popupConfirmacionEvent.remove();
    };
    popupConfirmacionEvent.afterDraw = function () {
        //$("#fechaInput").datepicker({dateFormat: 'dd/mm/yy'}).mask("99/99/9999");
        Datepicker.picker($("#fechaInput"));
        popupConfirmacionEvent.getBotonConfimar().button({label: "OK"});
        popupConfirmacionEvent.getBotonCancelar().button({label: "NO"});
        return;
    };
    popupConfirmacionEvent.popTitle = 'Ampliar Derechos';
    popupConfirmacionEvent.create("ampliarDerechosPopUp", 
                                  "<label>Nueva fecha: </label><input id=\"fechaInput\" type=\"text\" /><br/><label>Observacion: </label><input id=\"observacionInput\" type=\"text\" />");
};

ModificarContratoBusiness.prototype.adelantarVencimiento = function(request, skipWarnings) {
    modificarContratoBusiness.adelantarVencimiento.request = request;
    popupConfirmacionEvent.confirmar = function () {
        if (Validator.isEmpty($("#fechaInput"), true)) {
            return;
        }
        var fechaHastaPayTV = $("#fechaInput").val();
        var observaciones = $("#observacionInput").val();
        modificarContratoBusiness.adelantarVencimiento.request.fechaHastaPayTV = fechaHastaPayTV;
        modificarContratoBusiness.adelantarVencimiento.request.observaciones = observaciones;
        ModificarContratoStaticService.doRequest(
                                 {action:"adelantarVencimiento.action",
                                  request: Component.serialize(modificarContratoBusiness.adelantarVencimiento.request, "vigenciaRequest") + ( skipWarnings ? "&skipWarnings=" + skipWarnings : "" ),
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
                                                  modificarContratoBusiness.adelantarVencimiento(modificarContratoBusiness.adelantarVencimiento.request, true);
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
                                              MESSAGE.ok("Vencimiento adelantado");
                                          }
                                      } else {
                                          MESSAGE.error("Ocurrio un error al actualizar enlace anterior");
                                      }
                                      modificarContratoBusiness.trabjarConVigencias(modificarContratoBusiness.trabjarConVigencias.rowId, modificarContratoBusiness.trabjarConVigencias.modo);
                                  },
                                  responseObject: "validacionModificacionContrato"});
        popupConfirmacionEvent.remove();
    };
    popupConfirmacionEvent.cancelar = function () {
        popupConfirmacionEvent.remove();
    };
    popupConfirmacionEvent.afterDraw = function () {
        //$("#fechaInput").datepicker({dateFormat: 'dd/mm/yy'}).mask("99/99/9999");
        Datepicker.picker($("#fechaInput"));
        popupConfirmacionEvent.getBotonConfimar().button({label: "OK"});
        popupConfirmacionEvent.getBotonCancelar().button({label: "NO"});
        return;
    };
    popupConfirmacionEvent.popTitle = 'Adelantar Vencimiento';
    popupConfirmacionEvent.create("adelantarVencimientoPopUp", "<label>Nueva fecha: </label><input id=\"fechaInput\" type=\"text\" /><br/><label>Observacion: </label><input id=\"observacionInput\" type=\"text\" />");
};
ModificarContratoBusiness.prototype.procesarPayTV = function(request, modo, skipWarnings) {
    modificarContratoBusiness.procesarPayTV.req = request;
    modificarContratoBusiness.procesarPayTV.modo = modo;
    ModificarContratoStaticService.doRequest(
            {action:"procesarPayTV.action",
             request: Component.serialize(
                     modificarContratoBusiness.procesarPayTV.req, 
                     "vigenciaRequest") + ( skipWarnings ? "&skipWarnings=" + skipWarnings : "" ),
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
                             //delete modificarContratoBusiness.grupo;
                         } else if (hayWarnings){
                             popupConfirmacionEvent.confirmar = function () {
                                 modificarContratoBusiness.procesarPayTV(modificarContratoBusiness.procesarPayTV.req, modificarContratoBusiness.procesarPayTV.req.modo, true);
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
                             modificarContratoBusiness.trabjarConVigencias(
                                     modificarContratoBusiness.trabjarConVigencias.rowId,
                                     modificarContratoBusiness.trabjarConVigencias.modo);
                         }
                     }
            },
            responseObject: "validacionModificacionContrato"});
};
ModificarContratoBusiness.prototype.crearPayTV = function(request, modo) {
    modificarContratoBusiness.crearPayTV.req = request;
    
    popupConfirmacionEvent.confirmar = function () {
        if (Validator.isEmpty($("#fechaDesdeInput"), true)) {
            return;
        }
        if (Validator.isEmpty($("#fechaHastaInput"), true)) {
            return;
        }
        var fechaDesdePayTV = $("#fechaDesdeInput").val();
        var fechaHastaPayTV = $("#fechaHastaInput").val();
        var observaciones = $("#observacionInput").val();
        modificarContratoBusiness.crearPayTV.req.fechaDesdePayTV = fechaDesdePayTV;
        modificarContratoBusiness.crearPayTV.req.fechaHastaPayTV = fechaHastaPayTV;
        modificarContratoBusiness.crearPayTV.req.observaciones = observaciones;
        modificarContratoBusiness.procesarPayTV(modificarContratoBusiness.crearPayTV.req, modo);
        popupConfirmacionEvent.remove();
    };
    popupConfirmacionEvent.cancelar = function () {
        popupConfirmacionEvent.remove();
    };
    popupConfirmacionEvent.afterDraw = function () {
        //$("#fechaDesdeInput").datepicker({dateFormat: 'dd/mm/yy'}).mask("99/99/9999");
        Datepicker.picker($("#fechaDesdeInput"));
        //$("#fechaHastaInput").datepicker({dateFormat: 'dd/mm/yy'}).mask("99/99/9999");
        Datepicker.picker($("#fechaHastaInput"));
        popupConfirmacionEvent.getBotonConfimar().button({label: "OK"});
        popupConfirmacionEvent.getBotonCancelar().button({label: "NO"});
        return;
    };
    popupConfirmacionEvent.popTitle = 'CREAR PayTV';
    popupConfirmacionEvent.create("crearPayTVPopUp", "<label>Nueva fecha desde: </label><input id=\"fechaDesdeInput\" type=\"text\" /><br/>"
                                                   + "<label>Nueva fecha hasta: </label><input id=\"fechaHastaInput\" type=\"text\" /><br/>"
                                                   + "<label>Observacion: </label><input id=\"observacionInput\" type=\"text\" />");
};
ModificarContratoBusiness.prototype.eliminarPayTV = function(request) {
    modificarContratoBusiness.eliminarPayTV.req = request;
    ModificarContratoStaticService.doRequest(
            {action:"damePayTV.action",
             request: Component.serialize(modificarContratoBusiness.eliminarPayTV.req, "vigenciaRequest"),
             method: "GET",
             callback: function(data) {
                 if (data) {
                      var fechaDesde = data.fechaDesdePay.substring(8,10) + "/" + data.fechaDesdePay.substring(5,7) + "/" + data.fechaDesdePay.substring(0,4);
                      var fechaHasta = data.fechaHastaPay.substring(8,10) + "/" + data.fechaHastaPay.substring(5,7) + "/" + data.fechaHastaPay.substring(0,4);
                      popupConfirmacionEvent.confirmar = function () {
                          modificarContratoBusiness.eliminarPayTV.req.fechaDesdePayTV = fechaDesde;
                          modificarContratoBusiness.eliminarPayTV.req.fechaHastaPayTV = fechaHasta;
                          modificarContratoBusiness.eliminarPayTV.req.observaciones = data.observaciones;
                          modificarContratoBusiness.procesarPayTV(modificarContratoBusiness.eliminarPayTV.req, modificarContratoBusiness.eliminarPayTV.req.modo);
                          popupConfirmacionEvent.remove();
                          return;
                      };
                      popupConfirmacionEvent.cancelar = function () {
                          popupConfirmacionEvent.remove();
                      };
                      popupConfirmacionEvent.afterDraw = function () {
                          return;
                      };
                      popupConfirmacionEvent.popTitle = 'PAY TV';
                      popupConfirmacionEvent.create("payTVPopUp", "- Fecha Desde: " + fechaDesde + " - Fecha hasta: " + fechaHasta + " <br/> - OBSERVACIONES: " + data.observaciones);
                 }
            },
            responseObject: "vigencia"});
};
ModificarContratoBusiness.prototype.trabjarConVigencias = function(rowId, modo) {
    modificarContratoBusiness.trabjarConVigencias.rowId = rowId;
    modificarContratoBusiness.trabjarConVigencias.modo = modo;
    if (rowId) {
        var row = modificarContratoSelector.getGrillaTitulos().jqGrid('getRowData', rowId);
        var claveTitulo = row.clave;
        
        situarPopupEvent.reset();
        situarPopupEvent.nombreSituar = "VIGENCIAS - CONTRATO: " + modificarContratoBusiness.grupo.contrato +  " - GRUPO: " + modificarContratoBusiness.contratoConCabecera.grupoElegido + " - SENIAL: " + modificarContratoBusiness.contratoConCabecera.senialElegida;
        situarPopupEvent.setColumns(["Vigencia","Descip.","Pay Desde", "Pay Hasta", "Pay Anulado", "Fecha Ant. Desde", "Fecha Ant. Hasta", "Fecha Nueva Desde", "Fecha Nueva Hasta", "", ""],
                                    [{name:"tipoModifVigencia", index:"tipoModifVigencia"},{name:"descripTipoModifVigencia", index:"descripTipoModifVigencia"},
                                     {name:"fechaDesdePay", index:"fechaDesdePay", formatter: 'date', formatoptions: {srcformat: "Y-m-d\\TH:i:s", newformat: 'd/m/Y'}}, {name:"fechaHastaPay", index:"fechaHastaPay", formatter: 'date', formatoptions: {srcformat: "Y-m-d\\TH:i:s", newformat: 'd/m/Y'}}, {name:"payAnulado", index:"payAnulado"}, 
                                     {name:"fechaAnteriorDesde", index:"fechaAnteriorDesde", formatter: 'date', formatoptions: {srcformat: 'Y-m-d\\TH:i:s', newformat: 'd/m/Y'}}, {name:"fechaAnteriorHasta", index:"fechaAnteriorHasta", formatter: 'date', formatoptions: {srcformat: 'Y-m-d\\TH:i:s', newformat: 'd/m/Y'}}, 
                                     {name:"fechaNuevaDesde", index:"fechaNuevaDesde", formatter: 'date', formatoptions: {srcformat: 'Y-m-d\\TH:i:s', newformat: 'd/m/Y'}}, {name:"fechaNuevaHasta", index:"fechaNuevaHasta", formatter: 'date', formatoptions: {srcformat: 'Y-m-d\\TH:i:s', newformat: 'd/m/Y'}}, 
                                     {name:"puedeBorrar", index:"puedeBorrar", hidden:true}, {name:"payTVId", index:"payTVId", hidden:true}]);
        if (modificarContratoBusiness.trabjarConVigencias.modo === "T") {
            situarPopupEvent.acciones = [
                                         {callback: function(rowData){
                                                 situarPopupEvent.getPopup().dialog("close");
                                                 modificarContratoBusiness.eliminarPayTV(
                                                         {clave: claveTitulo,
                                                          senial: modificarContratoBusiness.contratoConCabecera.senialElegida,
                                                          grupo: modificarContratoBusiness.contratoConCabecera.grupoElegido,
                                                          contrato: modificarContratoBusiness.contratoConCabecera.contrato,
                                                          payTVId: parseInt(rowData.payTVId),
                                                          modo: "B"});
                                          }, 
                                          nombre: "Eliminar PayTV", 
                                          imagen: "bullet",
                                          habilitado: "puedeBorrar"}
                                       ];
        }
        situarPopupEvent.doAfterDraw = function() {
            situarPopupEvent.getFuncionalidadAdicionalContainer().empty();
            situarPopupEvent.getPopup().dialog({width: 1000});
            
            var crearPayTVButton = $("<input>").attr({type: "button", id: "dameVigenciasPorTituloContratado_crearPayTV", value: "Crear PayTV", style:"display: block; float: left", name: "crearPayTV"});
            situarPopupEvent.getFuncionalidadAdicionalContainer().before(crearPayTVButton);
            $("#dameVigenciasPorTituloContratado_crearPayTV").button().click(function(event) {
                                                    if (modificarContratoBusiness.trabjarConVigencias.modo === "T") {
                                                            situarPopupEvent.getPopup().dialog("close");
                                                            var modo = "A";
                                                            modificarContratoBusiness.crearPayTV(
                                                                    {clave: claveTitulo,
                                                                     senial: modificarContratoBusiness.contratoConCabecera.senialElegida,
                                                                     grupo: modificarContratoBusiness.contratoConCabecera.grupoElegido,
                                                                     contrato: modificarContratoBusiness.contratoConCabecera.contrato,
                                                                     payTVId: 0},
                                                                     modo);
                                                    } else {
                                                        MESSAGE.error("Accion inhabilitada");
                                                    }
                                                    return;
            });
            
            
            var ampliarDerechosButton = $("<input>").attr({type: "button", id: "dameVigenciasPorTituloContratado_ampliarDerechos", value: "Ampliar Derechos", style:"display: block; float: left", name: "ampliarDerechos"});
            situarPopupEvent.getFuncionalidadAdicionalContainer().before(ampliarDerechosButton);
            $("#dameVigenciasPorTituloContratado_ampliarDerechos").button().click(function(event) {
                if (modificarContratoBusiness.trabjarConVigencias.modo === "T") {
                    situarPopupEvent.getPopup().dialog("close");
                    modificarContratoBusiness.ampliarDerechos(
                              {clave: claveTitulo,
                               senial: modificarContratoBusiness.contratoConCabecera.senialElegida,
                               grupo: modificarContratoBusiness.contratoConCabecera.grupoElegido,
                               contrato: modificarContratoBusiness.contratoConCabecera.contrato});
                }  else {
                    MESSAGE.error("Accion inhabilitada");
                }
                return;
            });
            
            var adelantarVencimientoButton = $("<input>").attr({type: "button", id: "dameVigenciasPorTituloContratado_adelantarVencimiento", value: "Adelantar Vencimiento", style:"display: block", name: "adelantarVencimiento"});
            situarPopupEvent.getFuncionalidadAdicionalContainer().before(adelantarVencimientoButton);
            $("#dameVigenciasPorTituloContratado_adelantarVencimiento").button().click(function(event) {
                if (modificarContratoBusiness.trabjarConVigencias.modo === "T") {
                    situarPopupEvent.getPopup().dialog("close");
                    modificarContratoBusiness.adelantarVencimiento(
                              {clave: claveTitulo,
                               senial: modificarContratoBusiness.contratoConCabecera.senialElegida,
                               grupo: modificarContratoBusiness.contratoConCabecera.grupoElegido,
                               contrato: modificarContratoBusiness.contratoConCabecera.contrato});
                } else {
                    MESSAGE.error("Accion inhabilitada");
                }
                return;
            });
            
            var obj = {htmlObject: "input", type: "radio", id:"dameVigenciasPorTituloContratado_vigenciasRadio", labels: ["ultimo", "todos"], values: ["U", "T"]};
            switch (obj.htmlObject) {
                case "input":
                    for (var j in obj.values) {
                        situarPopupEvent.getFuncionalidadAdicionalContainer().before($("<label>").text(obj.labels[j]));
                        var radio = $('<'+obj.htmlObject+'>').attr({type: obj.type, id: obj.id+"_"+obj.labels[j], value: obj.values[j], style:"display: inline-block", name: "buscarPor"});
                        situarPopupEvent.getFuncionalidadAdicionalContainer().before(radio);
                        radio.bind('click', function(e) {
                                situarPopupEvent.data = 
                                    {"titulosRecontratadosRequest.contrato": modificarContratoBusiness.contratoConCabecera.contrato, 
                                     "titulosRecontratadosRequest.grupo": modificarContratoBusiness.contratoConCabecera.grupoElegido,
                                     "titulosRecontratadosRequest.clave": claveTitulo, 
                                     "titulosRecontratadosRequest.senial": modificarContratoBusiness.contratoConCabecera.senialElegida,
                                     "titulosRecontratadosRequest.filtro": $(this).val(), 
                                     "titulosRecontratadosRequest.modo": modo};
                                situarPopupEvent.reloadGrid();
                                return;
                            }
                        );
                    }
                    $("#dameVigenciasPorTituloContratado_vigenciasRadio_ultimo").attr("checked", "checked");
                    break;
                default:
                    break;
            }
            return;
        };
        situarPopupEvent.doAfterDrawGrid = function() {
            return;
        };
        situarPopupEvent.doAfterClose = function () { return; };
        situarPopupEvent.create(
                "dameVigenciasPorTituloContratado.action", 
                function (row) {
                    return;
                }, 
                {"titulosRecontratadosRequest.contrato": modificarContratoBusiness.contratoConCabecera.contrato, 
                 "titulosRecontratadosRequest.grupo": modificarContratoBusiness.contratoConCabecera.grupoElegido,
                 "titulosRecontratadosRequest.clave": claveTitulo, 
                 "titulosRecontratadosRequest.senial": modificarContratoBusiness.contratoConCabecera.senialElegida,
                 "titulosRecontratadosRequest.filtro": "U", 
                 "titulosRecontratadosRequest.modo": modo},
                 "vigencias",
                 true);
    }
};
ModificarContratoBusiness.prototype.desmarcarTitulo = function(clave, llamarReRun, fechaProceso, horaProceso) {
    modificarContratoBusiness.desmarcarTitulo.clave = clave;
    modificarContratoBusiness.desmarcarTitulo.llamarReRun = llamarReRun;
    modificarContratoBusiness.desmarcarTitulo.fechaProceso = fechaProceso;
    modificarContratoBusiness.desmarcarTitulo.horaProceso = horaProceso;

    ModificarContratoStaticService.doRequest(
            {action:"desmarcarTitulo.action",
             request: Component.serialize(
                {contrato: modificarContratoBusiness.grupo.contrato,
                 grupo: modificarContratoBusiness.grupo.nroGrupo,
                 senial: modificarContratoBusiness.grupo.senial,
                 fechaProceso: modificarContratoBusiness.desmarcarTitulo.fechaProceso,
                 horaProceso: modificarContratoBusiness.desmarcarTitulo.horaProceso,
                 clave: clave}, "tituloRequest"),
             method: "GET",
             callback: function(data) {
                if (data) {
                    MESSAGE.ok("El titulo ha sido desmarcado");
                    if (modificarContratoBusiness.desmarcarTitulo.llamarReRun) {
                        modificarContratoBusiness.trabajarReRun(modificarContratoBusiness.desmarcarTitulo.clave, "A", "T");
                    }
                } else {
                    MESSAGE.error("No se pudo desmarcar el titulo");
                }
             },
             responseObject: "validacionModificacionContrato"});
};
ModificarContratoBusiness.prototype.resolverFamilia = function(arg0) {
    for (var key in this.tiposDeTitulo) {
        if (this.tiposDeTitulo[key].tipoTitulo == arg0) {
            return this.tiposDeTitulo[key].familiaTitulo;
        }
    }
};
ModificarContratoBusiness.prototype.updateContrato = function(contrato) {
    ModificarContratoStaticService.doRequest(
            {action:"updateContrato.action",
             request: Component.serialize(contrato, "contratoValidationRequest"),
             method: "POST",
             callback: function(data) {
                if (data) {
                    MESSAGE.ok("El contrato ha sido modificado");
                } else {
                    MESSAGE.error("Error al intentar modificar el contrato");
                }
             },
             responseObject: "contratoUpdated"});
};
ModificarContratoBusiness.prototype.actualizarContrato = function(contrato) {
    ModificarContratoStaticService.doRequest(
            {action:"actualizarContrato.action",
             request: Component.serialize(contrato, "contratoValidationRequest"),
             method: "POST",
             callback: function(data) {
                 if (data) {
                     modificarContratoSelector.getPopUp("contratoConCabeceraPopUp").dialog('open');
                     modificarContratoBusiness.recargarContratoConCabecera({contrato: contrato.contrato, distribuidor: contrato.distribuidor});
                     MESSAGE.ok("El contrato ha sido modificado");
                 } else {
                     MESSAGE.error("Error al intentar actualizar el contrato");
                 }
              },
              responseObject: "contratoUpdated"});
};
ModificarContratoBusiness.prototype.agregarSenial = function() {
    situarPopupEvent.reset();
    situarPopupEvent.nombreSituar = "Agregar Senial";
    situarPopupEvent.acciones = [];
    situarPopupEvent.setColumns(
            ["Codigo","Descripcion"],
            [{name:"codigo", index:"codigo"},{name:"descripcion", index:"descripcion"}]);
    situarPopupEvent.doAfterDraw = function() {
        return;
    };
    situarPopupEvent.doAfterDrawGrid = function() {
        return;
    };
    situarPopupEvent.doAfterClose = function () { return; };
    situarPopupEvent.create("retrieveSeniales.action", modificarContratoBusiness.modificarImporte, {}, "seniales", true);
};
ModificarContratoBusiness.prototype.modificarImporte = function(row) {
    popupConfirmacionEvent.confirmar = function () {
        var accion;
        if (row.importe) {
            accion = "modificarSenial.action";
        } else {
            accion = "altaSenial.action";
        }
        ModificarContratoStaticService.doRequest(
                {action:accion,
                 request: Component.serialize(
                         {contrato:modificarContratoBusiness.contratoConCabecera.contrato, 
                          codigoSenial:row.codigo, 
                          importe: $("#importeDeLaSenialInput").autoNumericGet()},
                          "senialImporteRequest"),
                 method: "POST",
                 callback: function(data) {
                  if (data && data.length && data.length > 0) {
                      MESSAGE.error(data[0]["tipo"]+" - "+data[0]["descripcion"]);
                  } else {
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
                  }
               },
               responseObject: "validacionModificacionContrato"});
        popupConfirmacionEvent.remove();
    };
    popupConfirmacionEvent.cancelar = function () {
        popupConfirmacionEvent.remove();
    };
    popupConfirmacionEvent.afterDraw = function () {
    	$("#importeDeLaSenialInput").autoNumeric({vMax: '999999999.99'});
    	$("#importeDeLaSenialInput").autoNumericSet("0");
    	
        if (row.importe) {
            $("#importeDeLaSenialInput").autoNumericSet(row.importe);
        }
    };
    popupConfirmacionEvent.popTitle = 'Modificacion de Señal - Contrato: ' + modificarContratoBusiness.contratoConCabecera.contrato;
    popupConfirmacionEvent.create("importeDeLaSenialPopUp", "<label>Ingrese Importe para " + row.codigo + ": </label><input id=\"importeDeLaSenialInput\" type=\"text\" />");
};
ModificarContratoBusiness.prototype.eliminarSenialImporte = function(rowId) {
    if (rowId) {
        popupConfirmacionEvent.confirmar = function () {
            modificarContratoDrawer.drawGrillaDeGruposEliminarSenial();
            var row = modificarContratoSelector.getGrillaSenialesEImportes().jqGrid('getRowData', rowId);
            ModificarContratoStaticService.doRequest(
                    {action:"eliminarSenial.action",
                     request: Component.serialize(
                             {contrato:modificarContratoBusiness.contratoConCabecera.contrato, 
                              codigoSenial:row.codigoSenial}, 
                              "senialImporteRequest"),
                     method: "POST",
                     callback: function(data) {
                      if (data && data.length && data.length > 0) {
                          MESSAGE.error(data[0]["tipo"]+" - "+data[0]["descripcion"]);
                      } else {
                          ModificarContratoStaticService.doRequest(
                                  {action:"dameSenialImporte.action",
                                   request: {claveContrato: modificarContratoBusiness.contratoConCabecera.contrato},
                                   method: "GET",
                                   callback: function(data) {
                                          if (data && data.length && data.length > 0) {
                                              modificarContratoBusiness.contratoConCabecera.senialesEimportes = data;
                                              modificarContratoPopulator.populateGrillaSenialesEImportes(modificarContratoBusiness.contratoConCabecera.senialesEimportes);
                                          } else {
                                              MESSAGE.alert("El contrato no tiene señales");
                                              modificarContratoSelector.getGrillaSenialesEImportes().clearGridData();
                                              modificarContratoSelector.getGrillaGrupos().clearGridData()
                                          }
                                   },
                                   responseObject: "senialesConImportes"});
                      }
                   },
                   responseObject: "validacionModificacionContrato"});
            popupConfirmacionEvent.remove();
        };
        popupConfirmacionEvent.cancelar = function () {
            popupConfirmacionEvent.remove();
        };
        popupConfirmacionEvent.afterDraw = function() {
            modificarContratoDrawer.drawGrillaDeGruposEliminarSenial();
            var row = modificarContratoSelector.getGrillaSenialesEImportes().jqGrid('getRowData', rowId);
            modificarContratoPopulator.populateGrillaDeGruposEliminarSenial(row.codigoSenial);
            $("#grillaDeGruposEliminarSenialContainer").before("<span>Grupos de la Señal: "+row.codigoSenial+"</span>");
            popupConfirmacionEvent.getBotonConfimar().button({label: "ELIMINAR SEÑAL"});
            popupConfirmacionEvent.getBotonCancelar().button({label: "CANCELAR"});
        };
        popupConfirmacionEvent.popTitle = 'ELIMINAR SEÑAL';
        popupConfirmacionEvent.create("elimiarSenialImportePopUp", "<div id=\"grillaDeGruposEliminarSenialContainer\">" +
                "<table id=\"grillaDeGruposEliminarSenial\"></table>" +
                "<div id=\"pagerGrillaDeGruposEliminarSenial\"></div>");
    }
};

ModificarContratoBusiness.prototype.altaDeGrupo = function(grupo) {
    var laAccion = "";
    if (modificarContratoBusiness.resolverFamilia(grupo.tipoTitulo) === ModificarContratoBusiness.CC) {
        laAccion = "altaGrupoConCap.action";
    } else {
        laAccion = "altaGrupoSinCap.action";
    }
//    grupo.modo = modo ? modo : "";
//    grupo.nroRelacionante = nroRelacionante ? nroRelacionante : 0;
    modificarContratoBusiness.trabjarConVigencias.modo = "T";
    
    ModificarContratoStaticService.doRequest(
           {action: laAccion,
            request: Component.serialize(grupo, "grupoRequest"),
            method: "GET",
            callback: function(data) {
                var hayErrores = false;
                var hayWarnings = false;
                var warnings  = new Array();
                var errores  = new Array();
                if (data && data.length !== undefined && data.length > 0) {
                    $.each(data, function(i, l) {
                        if (l["tipo"] === "E") {
                            hayErrores = true;
                            errores.push(l["descripcion"]);
                            delete modificarContratoBusiness.grupo;
                        } else if (l["tipo"] === "W") {
                            hayWarnings = true;
                            warnings.push(l["descripcion"]);
                        }
                    });
                }
                if (hayErrores) {
                    MESSAGE.error(errores[0]);
                    return;
                } else if (hayWarnings) {
                    MESSAGE.alert(warnings[0]);
                }
                modificarContratoPopulator.populateGrillaDeGrupos(modificarContratoBusiness.grupo.senial);
                var recontratacion = 
                        (modificarContratoBusiness.resolverFamilia(grupo.tipoTitulo) === ModificarContratoBusiness.CC) && (grupo.recontratacion === "S") && (grupo.er === "R");
                
                if (recontratacion) {
                    //recontratacion
                    modificarContratoBusiness.ingresarTitulos(1, grupo.tipoTitulo, true);
                } else {
                    if (modificarContratoBusiness.resolverFamilia(grupo.tipoTitulo) === ModificarContratoBusiness.CC) {
                        //ingresar CC
                        modificarContratoBusiness.ingresarTitulos(1, grupo.tipoTitulo);
                    } else {
                        //ingresar SC
                        modificarContratoBusiness.ingresarTitulos(grupo.cantTitulos, grupo.tipoTitulo);
                    }
                }
            },
            responseObject: "validacionModificacionContrato"});
};
ModificarContratoBusiness.prototype.grabarTitulo = function(titulo, callback, reqObject) {
    modificarContratoBusiness.grabarTitulo.callback = callback;
    modificarContratoBusiness.grabarTitulo.reqObject = reqObject;
//	console.log(4);
    ModificarContratoStaticService.doRequest(
            {action: "altaTituloContratado.action",
             request: Component.serialize(
                    {contrato: modificarContratoBusiness.grupo.contrato,
                     grupo: modificarContratoBusiness.grupo.nroGrupo,
                     senial: modificarContratoBusiness.grupo.senial,
                     clave: titulo.clave,
                     origen: titulo.origen,
                     tituloOriginal: titulo.tituloOriginal,
                     tituloCastellano: titulo.tituloCastellano,
                     actores1: titulo.actores1,
                     actores2: titulo.actores2,
                     observaciones: titulo.observaciones,
                     recontratacion: titulo.recontratacion,
                     idFicha: titulo.idFicha,
                     sinContrato: titulo.sinContrato,
                     calificacion: titulo.calificacionOficial ? titulo.calificacionOficial : "",
                     modo: modificarContratoBusiness.grupo.modo,
                     nroRelacionante: modificarContratoBusiness.grupo.nroRelacionante,
                     //FIXME
                     respuesta: "N",
                     fechaProcesoTG: "",
                     horaProcesoTG: ""
                     }, 
                     "validarAltaTituloRequest"),
             method: "POST",
             callback: function(data) {
                 if (data && data.length && data.length > 0) {
                     var llamarReRun = false;
                     var modoReRun = "";
                     var procesoReRun = "";
                     $.each(data, function(i, l){
                         if ( (l["tipo"] === "RR") && (l["descripcion"] === "S") ) {
                             llamarReRun = true;
                         } else if (l["tipo"] === "MRR") {
                             modoReRun = l["descripcion"];
                         } else if (l["tipo"] === "PRR") {
                             procesoReRun = l["descripcion"];
                         }
                     });

                     modificarContratoBusiness.ingresarCapitulosRecontratados.llamarReRun = llamarReRun;
                     modificarContratoBusiness.ingresarCapitulosRecontratados.modoReRun = modoReRun; 
                     modificarContratoBusiness.ingresarCapitulosRecontratados.procesoReRun = procesoReRun;
                     
                     modificarContratoBusiness.titulosElegidos.push(titulo);
                     modificarContratoBusiness.cantTitulosAAgregar = modificarContratoBusiness.cantTitulosAAgregar - 1;
                     if (modificarContratoBusiness.grabarTitulo.callback) {
//                    	 console.log(5);
                         modificarContratoBusiness.grabarTitulo.callback(modificarContratoBusiness.grabarTitulo.reqObject);
                     } else {
                         if (llamarReRun) {
//                        	 console.log(6);
                             modificarContratoBusiness.trabajarReRun(titulo.clave, modoReRun, procesoReRun);
                         } else {
//                        	 console.log(7);
                             modificarContratoBusiness.ingresarTitulos(modificarContratoBusiness.cantTitulosAAgregar, modificarContratoBusiness.grupo.tipoTitulo);
                         }
                     }
                 }
             },
             responseObject: "validacionModificacionContrato"});
};
ModificarContratoBusiness.prototype.modificacionDeCantidadGrupo = function(grupo) {
    var laAccion = "";
    if (modificarContratoBusiness.resolverFamilia(grupo.tipoTitulo) === ModificarContratoBusiness.CC) {
        laAccion = "modificacionDeCantidadGrupoConCap.action";
    } else {
        laAccion = "modificacionDeCantidadGrupoSinCap.action";
    }
    ModificarContratoStaticService.doRequest(
            {action: laAccion,
             request: Component.serialize(grupo, "grupoRequest"),
             method: "GET",
             callback: function(data) {
                 if (data && data.length && data.length > 0) {
                     var ok = false;
                     var hayCapsABorrar = false;
                     var hayCapsAAgregar = false;
                     var capsAfectados = 0;
                     $.each(data, function(i, l){
                         if ( (l["tipo"] === "OK") && (l["descripcion"] === "S") ) {
                             ok = true;
                         } else if ((l["tipo"] === "D_HAY_CAPS_A_BORRAR") && (l["descripcion"] === "S")) {
                             hayCapsABorrar = true;
                         } else if ((l["tipo"] === "D_HAY_CAPS_A_AGREGAR") && (l["descripcion"] === "S")){
                             hayCapsAAgregar = true;
                         } else if (l["tipo"] === "D_CAPS_AFECTADOS") {
                             capsAfectados = l["nroAdvertencia"];
                         }
                         
                     });
                     if (ok) {
                         if (modificarContratoBusiness.modificacionDeCantidadGrupo.diff > 0) {
                             if (modificarContratoBusiness.resolverFamilia(modificarContratoSelector.getInput("tipoTitulo", "grupoPopUp").val()) === ModificarContratoBusiness.CC) {
                                 //agregar capitulos
                                 if (hayCapsAAgregar) {
                                     modificarContratoBusiness.ingresarCapitulos(capsAfectados);
                                 }
                             } else {
                                 //agregar titulos
                                 modificarContratoBusiness.ingresarTitulos(modificarContratoBusiness.modificacionDeCantidadGrupo.diff, 
                                         modificarContratoSelector.getInput("tipoTitulo", "grupoPopUp").val() ? modificarContratoSelector.getInput("tipoTitulo", "grupoPopUp").val() : grupo.tipoTitulo);
                             }
                         } else if (modificarContratoBusiness.modificacionDeCantidadGrupo.diff < 0) {
                             if (modificarContratoBusiness.resolverFamilia(modificarContratoSelector.getInput("tipoTitulo", "grupoPopUp").val()) === ModificarContratoBusiness.CC) {
                                 //elminar capitulos
                                 if (hayCapsABorrar) {
                                     modificarContratoBusiness.eliminarCapitulos(capsAfectados);
                                 }
                             } else {
                                 //eliminar titulos
                                 modificarContratoBusiness.eliminarTitulos(modificarContratoBusiness.modificacionDeCantidadGrupo.diff * (-1));
                             }
                         } else {
                             MESSAGE.alert("No hay diferencia en la cantidad de titulos");
                             return;
                         }
                     }
                     modificarContratoSelector.getPopUp("grupoPopUp").dialog("close");
                 }
             },
             responseObject: "validacionModificacionContrato"});
};
ModificarContratoBusiness.prototype.modificacionDeGrupo = function(grupo) {
    var laAccion = "";
    if (modificarContratoBusiness.resolverFamilia(grupo.tipoTitulo) === ModificarContratoBusiness.CC) {
        laAccion = "modificacionDeGrupoConCap.action";
    } else {
        laAccion = "modificacionDeGrupoSinCap.action";
    }
    ModificarContratoStaticService.doRequest(
           {action: laAccion,
            request: Component.serialize(grupo, "grupoRequest"),
            method: "GET",
            callback: function(data) {
                if (data && data.length && data.length > 0) {
                    var llamarReRun = false;
                    var modoReRun = "";
                    var procesoReRun = "";
                    var tieneTitulosReRun = false;
                    var recontratacion = false;
                    var clave = "";
                    var ok = false;
                    $.each(data, function(i, l) {
                        if ( (l["tipo"] === "RR") ) {
                            llamarReRun = l["descripcion"] === "S";
                        } else if (l["tipo"] === "MRR") {
                            modoReRun = l["descripcion"];
                        } else if (l["tipo"] === "PRR") {
                            procesoReRun = l["descripcion"];
                        } else if (l["tipo"] === "TRR") {
                            tieneTitulosReRun = l["descripcion"] === "S";
                        } else if (l["tipo"] === "D_CLAVE") {
                            clave = l["descripcion"];
                        } else if (l["tipo"] === "R") {
                            recontratacion = l["descripcion"] === "S";
                        } else if (l["tipo"] === "OK") {
                            ok = l["descripcion"] === "S";
                        }
                    });
                    if ((llamarReRun) && (tieneTitulosReRun)) {
                        //TODO-lista-re-run
                        modificarContratoSelector.getPopUp("grupoPopUp").dialog("close");
                        modificarContratoBusiness.trabajarReRun(clave, modoReRun, procesoReRun);
                    } else if (ok) {
                        MESSAGE.ok("Grupo modificado");
                        modificarContratoSelector.getPopUp("grupoPopUp").dialog("close");
                        return;
                    } else {
                        MESSAGE.alert("El grupo no pudo ser modificado");
                        return;
                    }
                }
            },
            responseObject: "validacionModificacionContrato"});
};
ModificarContratoBusiness.prototype.modificarTituloContratadoSC = function(tituloContratado, skipWarnings) {
    modificarContratoBusiness.modificarTituloContratadoSC.tituloContratado = tituloContratado;
    ModificarContratoStaticService.doRequest(
            {action: "modificarTituloContratadoSC.action",
             request: Component.serialize(tituloContratado, "tituloConGrupoRequest") + (skipWarnings !== undefined ? "&skipWarnings=true" : ""),
             method: "GET",
             callback: function(data) {
                 if (data && data.length != undefined && data.length > 0) {
                         var llamarReRun = false;
                         var modoReRun = "";
                         var procesoReRun = "";
                         var ok = false;
                         var hayWarnings = false;
                         var hayErrores = false;
                         var warnings  = new Array();
                         var errores  = new Array();
                         $.each(data, function(i, l) {
                             if ( (l["tipo"] === "RR") ) {
                                 llamarReRun = l["descripcion"] === "S";
                             } else if (l["tipo"] === "MRR") {
                                 modoReRun = l["descripcion"];
                             } else if (l["tipo"] === "PRR") {
                                 procesoReRun = l["descripcion"];
                             } else if (l["tipo"] === "OK") {
                                 ok = l["descripcion"] === "S";
                             } else if (l["tipo"] === "E") {
                                 hayErrores = true;
                                 errores.push(l["descripcion"]);
                             } else if (l["tipo"] === "W") {
                                 hayWarnings = true;
                                 warnings.push(l["descripcion"]);
                             }
                         });
                             
                         if (hayErrores) {
                             popupConfirmacionEvent.confirmar = function () {
                                 popupConfirmacionEvent.remove();
                             };
                             popupConfirmacionEvent.cancelar = function () {
                                 popupConfirmacionEvent.remove();
                             };
                             popupConfirmacionEvent.afterDraw = function () {
                                 this.getBotonConfimar().remove();
                                 this.getBotonCancelar().remove();
                             };
                             popupConfirmacionEvent.popTitle = 'ERRORES';
                             popupConfirmacionEvent.create("errorPopUp", errores.join("<br/>"));
                         } else if (hayWarnings){
                             popupConfirmacionEvent.confirmar = function () {
                                 modificarContratoBusiness.modificarTituloContratadoSC(modificarContratoBusiness.modificarTituloContratadoSC.tituloContratado, true);
                                 popupConfirmacionEvent.remove();
                             };
                             popupConfirmacionEvent.cancelar = function () {
                                 popupConfirmacionEvent.remove();
                             };
                             popupConfirmacionEvent.afterDraw = function () {
                                 return;
                             };
                             popupConfirmacionEvent.popTitle = 'WARNING';
                             popupConfirmacionEvent.create("warningsPopUp", warnings.join("<br/>"));
                         } else if (llamarReRun) {
                             modificarContratoBusiness.trabajarReRun(modificarContratoBusiness.modificarTituloContratadoSC.tituloContratado.clave, modoReRun, procesoReRun);
                         } else if (ok) {
                             MESSAGE.ok("Titulo contratado (SC) MODIFICADO");
                         }
                 }
             },
             responseObject: "validacionModificacionContrato"});};
ModificarContratoBusiness.prototype.modificarTituloContratadoCC = function(tituloContratado) {
    modificarContratoBusiness.modificarTituloContratadoCC.tituloContratado = tituloContratado;
    ModificarContratoStaticService.doRequest(
            {action: "modificarTituloContratadoCC.action",
             request: Component.serialize(tituloContratado, "tituloConGrupoRequest"),
             method: "GET",
             callback: function(data) {
                 if (data && data.length != undefined && data.length > 0) {
                     var llamarReRun = false;
                     var modoReRun = "";
                     var procesoReRun = "";
                     var ok = false;
                     $.each(data, function(i, l) {
                         if ( (l["tipo"] === "RR") ) {
                             llamarReRun = l["descripcion"] === "S";
                         } else if (l["tipo"] === "MRR") {
                             modoReRun = l["descripcion"];
                         } else if (l["tipo"] === "PRR") {
                             procesoReRun = l["descripcion"];
                         } else if (l["tipo"] === "OK") {
                             ok = l["descripcion"] === "S";
                         }
                     });
                     if (llamarReRun) {
                         modificarContratoBusiness.trabajarReRun(modificarContratoBusiness.modificarTituloContratadoCC.tituloContratado.clave, modoReRun, procesoReRun);
                     } else if (ok) {
                         MESSAGE.ok("Titulo contratado (CC)    -    MODIFICADO");
                     }
                 }
             },
             responseObject: "validacionModificacionContrato"});
};
ModificarContratoBusiness.prototype.trabajarReRun = function(claveTitulo, modo, proceso) {
    var self = modificarContratoBusiness;
    modificarContratoBusiness.claveTitulo = claveTitulo;
    modificarContratoBusiness.proceso = proceso;
    modificarContratoBusiness.modo = modo;

    var tituloCastellano = "";
    $.ajax({
        type : 'GET',
        url : "dameNombreDelTitulo.action",
        async: false,
        data : "claveTitulo=" + claveTitulo,
        success : function(response) {
            if (response.message) {
                tituloCastellano = response.message;
            }
        }
    });

    situarPopupEvent.reset();
    situarPopupEvent.nombreSituar = "RERUN ---- CONTRATO: " + modificarContratoBusiness.grupo.contrato +  " - GRUPO: " + modificarContratoBusiness.grupo.nroGrupo + " - TITULO: " + claveTitulo + " " + tituloCastellano;
    situarPopupEvent.setColumns(
        ["Contrato","Grupo", "Distribuidor", "Vigencia Desde", "Vigencia Hasta", "Enlazado Anterior", "Grupo Anterior", "Enlazado Posterior", "Grupo Posterior", "", "", ""],
        [{name:"contrato", index:"contrato"}, {name:"grupo", index:"grupo"}, {name:"distribuidor", index:"distribuidor"}, 
         {name:"vigenciaDesde", index:"vigenciaDesde"}, 
         {name:"vigenciaHasta", index:"vigenciaHasta"}, 
         {name:"enlazadoAnterior", index:"enlazadoAnterior"}, 
         {name:"grupoAnterior", index:"grupoAnterior"}, {name:"enlazadoPosterior", index:"enlazadoPosterior"}, {name:"grupoPosterior", index:"grupoPosterior"},
         {name:"puedeAnterior", index:"puedeAnterior", hidden: true}, {name:"puedePosterior", index:"puedePosterior", hidden: true}, {name:"puedeDesenlazar", index:"puedeDesenlazar", hidden: true}
        ]);
    situarPopupEvent.doAfterDraw = function() {
        situarPopupEvent.getFuncionalidadAdicionalContainer().empty();
            
        situarPopupEvent.getFuncionalidadAdicionalContainer().append("<span>CONTRATO: " + modificarContratoBusiness.grupo.contrato +  " - GRUPO: " + modificarContratoBusiness.grupo.nroGrupo + " - TITULO: " + claveTitulo + " " + tituloCastellano + "</span>");
        situarPopupEvent.getPopup().dialog({width: 950});
        if (modificarContratoBusiness.grupo.codigoDerechos === "R") {
            situarPopupEvent.getPopup().dialog({
                buttons: {
                    "Salir": function() {
                        MESSAGE.error("Debe enlazar el contrato antes de salir");
                    }
                }
            });
        }
        return;
    };
    situarPopupEvent.doAfterDrawGrid = function() {
        
        var ids = this.getGrid().jqGrid('getDataIDs');
        for (var i = 0; i < ids.length; i++) {
            var rowId = ids[i];
            var row = this.getGrid().jqGrid('getRowData', rowId);
            
            if (row.vigenciaDesde === "01/01/0001") {
                this.getGrid().jqGrid('setRowData', rowId, {vigenciaDesde: ""});
            }
            if (row.vigenciaHasta === "01/01/0001") {
                this.getGrid().jqGrid('setRowData', rowId, {vigenciaHasta: ""});
            }

        }
        
        this.getGrid().trigger('reloadGrid');

        return;
    };
    
    if (modificarContratoBusiness.modo === "T") {
        situarPopupEvent.acciones = [
                                {callback: function(rowData) {
                                    if (modificarContratoBusiness.trabjarConVigencias.modo === "T") {
                                        modificarContratoBusiness.validarEnlaceAnterior(
                                                {
                                                    contrato: parseInt(modificarContratoBusiness.grupo.contrato),
                                                    grupo: modificarContratoBusiness.grupo.nroGrupo,
                                                    senial: modificarContratoBusiness.grupo.senial,
                                                    clave: modificarContratoBusiness.claveTitulo,
                                                    rerunContrato: rowData.contrato,
                                                    rerunGrupo: rowData.grupo,
                                                    rerunEnlaceAnterior: rowData.enlazadoAnterior,
                                                    rerunGrupoAnterior: rowData.grupoAnterior,
                                                    rerunEnlacePosterior: rowData.enlazadoPosterior,
                                                    rerunGrupoPosterior: rowData.grupoPosterior,
                                                    rerunVigenciaDesde: rowData.vigenciaDesde === "" ? "01/01/0001" : rowData.vigenciaDesde,
                                                    rerunVigenciaHasta: rowData.vigenciaHasta === "" ? "01/01/0001" : rowData.vigenciaHasta,
                                                    proceso: modificarContratoBusiness.proceso
                                                });
                                    } else {
                                        MESSAGE.error("Accion inhabilitada, debe hacerlo desd el menu Trabajar Con Titulos");
                                    }
                                 },
                                 nombre: "Enlazar Anterior", 
                                 imagen: "bullet",
                                 closePopUp: false,
                                 habilitado: "puedeAnterior"},
                                
                                {callback: function(rowData) {
                                    if (modificarContratoBusiness.trabjarConVigencias.modo === "T") {
                                        modificarContratoBusiness.validarEnlacePosterior(
                                                {
                                                contrato: parseInt(modificarContratoBusiness.grupo.contrato),
                                                grupo: modificarContratoBusiness.grupo.nroGrupo,
                                                senial: modificarContratoBusiness.grupo.senial,
                                                clave: modificarContratoBusiness.claveTitulo,
                                                rerunContrato: rowData.contrato,
                                                rerunGrupo: rowData.grupo,
                                                rerunEnlaceAnterior: rowData.enlazadoAnterior,
                                                rerunGrupoAnterior: rowData.grupoAnterior,
                                                rerunEnlacePosterior: rowData.enlazadoPosterior,
                                                rerunGrupoPosterior: rowData.grupoPosterior,
                                                rerunVigenciaDesde: rowData.vigenciaDesde === "" ? "01/01/0001" : rowData.vigenciaDesde,
                                                rerunVigenciaHasta: rowData.vigenciaHasta === "" ? "01/01/0001" : rowData.vigenciaHasta,
                                                proceso: modificarContratoBusiness.proceso
                                                });
                                    } else {
                                        MESSAGE.error("Accion inhabilitada, debe hacerlo desd el menu Trabajar Con Titulos");
                                    }
                                 },
                                 nombre: "Enlazar Posterior", 
                                 imagen: "bullet",
                                 closePopUp: false,
                                 habilitado: "puedePosterior"},
                                
                                {callback: function(rowData) {
                                    if (modificarContratoBusiness.trabjarConVigencias.modo === "T") {
                                        modificarContratoBusiness.validarRerunDesenlace(
                                                {
                                                contrato: parseInt(modificarContratoBusiness.grupo.contrato),
                                                grupo: modificarContratoBusiness.grupo.nroGrupo,
                                                senial: modificarContratoBusiness.grupo.senial,
                                                clave: modificarContratoBusiness.claveTitulo,
                                                rerunContrato: rowData.contrato,
                                                rerunGrupo: modificarContratoBusiness.grupo.nroGrupo,
                                                rerunEnlaceAnterior: rowData.enlazadoAnterior,
                                                rerunGrupoAnterior: rowData.grupoAnterior,
                                                rerunEnlacePosterior: rowData.enlazadoPosterior,
                                                rerunGrupoPosterior: rowData.grupoPosterior,
                                                rerunVigenciaDesde: rowData.vigenciaDesde === "" ? "01/01/0001" : rowData.vigenciaDesde,
                                                rerunVigenciaHasta: rowData.vigenciaHasta === "" ? "01/01/0001" : rowData.vigenciaHasta,
                                                proceso: modificarContratoBusiness.proceso
                                                });
                                    } else {
                                        MESSAGE.error("Accion inhabilitada, debe hacerlo desd el menu Trabajar Con Titulos");
                                    }
                                 },
                                 nombre: "Desenlazar",
                                 imagen: "bullet",
                                 closePopUp: false,
                                 habilitado: "puedeDesenlazar"}
                                ];
    }
    situarPopupEvent.doAfterClose = function () {
                                    if (modificarContratoBusiness.cantTitulosAAgregar) {
                                        modificarContratoBusiness.ingresarTitulos(
                                                modificarContratoBusiness.cantTitulosAAgregar, 
                                                modificarContratoBusiness.grupo.tipoTitulo);
                                    }
                                    };
    situarPopupEvent.create(
            "dameContratosConReRun.action", 
            function (row) {
                return;
            }, 
            {"titulosRecontratadosRequest.contrato": self.contratoConCabecera.contrato, 
             "titulosRecontratadosRequest.grupo": self.grupo.nroGrupo,
             "titulosRecontratadosRequest.clave": claveTitulo,
             "titulosRecontratadosRequest.modo": self.modo,
             "titulosRecontratadosRequest.senial": self.contratoConCabecera.senialElegida}, 
             "contratosConReRun",
             true);
    return;
};
ModificarContratoBusiness.prototype.trabajarReRunDesdeChequeo = function(contrato, clave, grupo, senial, modo, proceso) {
    situarPopupEvent.reset();
    situarPopupEvent.nombreSituar = "RERUN ---- CONTRATO: " + contrato +  " - GRUPO: " + grupo + " - CLAVE: " + clave;
    situarPopupEvent.setColumns(
        ["Contrato","Grupo", "Distribuidor", "Vigencia Desde", "Vigencia Hasta", "Enlazado Anterior", "Grupo Anterior", "Enlazado Posterior", "Grupo Posterior", "", "", ""],
        [{name:"contrato", index:"contrato"}, {name:"grupo", index:"grupo"}, {name:"distribuidor", index:"distribuidor"}, 
         {name:"vigenciaDesde", index:"vigenciaDesde"}, 
         {name:"vigenciaHasta", index:"vigenciaHasta"}, {name:"enlazadoAnterior", index:"enlazadoAnterior"}, 
         {name:"grupoAnterior", index:"grupoAnterior"}, {name:"enlazadoPosterior", index:"enlazadoPosterior"}, {name:"grupoPosterior", index:"grupoPosterior"},
         {name:"puedeAnterior", index:"puedeAnterior", hidden: true}, {name:"puedePosterior", index:"puedePosterior", hidden: true}, {name:"puedeDesenlazar", index:"puedeDesenlazar", hidden: true}
        ]);
    situarPopupEvent.doAfterDraw = function() {
        situarPopupEvent.getFuncionalidadAdicionalContainer().empty();
        situarPopupEvent.getFuncionalidadAdicionalContainer().append("<span>CONTRATO: " + contrato +  " - GRUPO: " + grupo + " - CLAVE: " + clave + "</span>");
        situarPopupEvent.getPopup().dialog({width: 950});
        return;
    };
    situarPopupEvent.doAfterDrawGrid = function() {
        var ids = this.getGrid().jqGrid('getDataIDs');
        for (var i = 0; i < ids.length; i++) {
            var rowId = ids[i];
            var row = this.getGrid().jqGrid('getRowData', rowId);
            
            if (row.vigenciaDesde === "01/01/0001") {
                this.getGrid().jqGrid('setRowData', rowId, {vigenciaDesde: ""});
            }
            if (row.vigenciaHasta === "01/01/0001") {
                this.getGrid().jqGrid('setRowData', rowId, {vigenciaHasta: ""});
            }

        }
        this.getGrid().trigger('reloadGrid');
        return;
    };
    
    situarPopupEvent.acciones = [
                                {callback: function(rowData) {
                                    modificarContratoBusiness.validarEnlaceAnterior(
                                                  {
                                                  contrato: parseInt(contrato),
                                                  grupo: grupo,
                                                  senial: senial,
                                                  clave: clave,
                                                  rerunContrato: rowData.contrato,
                                                  rerunGrupo: rowData.grupo,
                                                  rerunEnlaceAnterior: rowData.enlazadoAnterior,
                                                  rerunGrupoAnterior: rowData.grupoAnterior,
                                                  rerunEnlacePosterior: rowData.enlazadoPosterior,
                                                  rerunGrupoPosterior: rowData.grupoPosterior,
                                                  rerunVigenciaDesde: rowData.vigenciaDesde === "" ? "01/01/0001" : rowData.vigenciaDesde,
                                                  rerunVigenciaHasta: rowData.vigenciaHasta === "" ? "01/01/0001" : rowData.vigenciaHasta,
                                                  proceso: proceso
                                                  });
                                 },
                                 nombre: "Enlazar Anterior", 
                                 imagen: "bullet",
                                 closePopUp: false,
                                 habilitado: "puedeAnterior"},
                                
                                {callback: function(rowData) {
                                    modificarContratoBusiness.validarEnlacePosterior(
                                            {
                                            contrato: parseInt(contrato),
                                            grupo: grupo,
                                            senial: senial,
                                            clave: clave,
                                            rerunContrato: rowData.contrato,
                                            rerunGrupo: rowData.grupo,
                                            rerunEnlaceAnterior: rowData.enlazadoAnterior,
                                            rerunGrupoAnterior: rowData.grupoAnterior,
                                            rerunEnlacePosterior: rowData.enlazadoPosterior,
                                            rerunGrupoPosterior: rowData.grupoPosterior,
                                            rerunVigenciaDesde: rowData.vigenciaDesde === "" ? "01/01/0001" : rowData.vigenciaDesde,
                                            rerunVigenciaHasta: rowData.vigenciaHasta === "" ? "01/01/0001" : rowData.vigenciaHasta,
                                            proceso: proceso
                                            });
                                 },
                                 nombre: "Enlazar Posterior", 
                                 imagen: "bullet",
                                 closePopUp: false,
                                 habilitado: "puedePosterior"},
                                
                                {callback: function(rowData) {
                                    modificarContratoBusiness.validarRerunDesenlace(
                                            {
                                            contrato: parseInt(contrato),
                                            grupo: grupo,
                                            senial: senial,
                                            clave: clave,
                                            rerunContrato: rowData.contrato,
                                            rerunGrupo: rowData.grupo,
                                            rerunEnlaceAnterior: rowData.enlazadoAnterior,
                                            rerunGrupoAnterior: rowData.grupoAnterior,
                                            rerunEnlacePosterior: rowData.enlazadoPosterior,
                                            rerunGrupoPosterior: rowData.grupoPosterior,
                                            rerunVigenciaDesde: rowData.vigenciaDesde === "" ? "01/01/0001" : rowData.vigenciaDesde,
                                            rerunVigenciaDesde: rowData.vigenciaDesde === "" ? "01/01/0001" : rowData.vigenciaDesde,
                                            proceso: proceso
                                            });
                                 },
                                 nombre: "Desenlazar",
                                 imagen: "bullet",
                                 closePopUp: false,
                                 habilitado: "puedeDesenlazar"}
                                ];
    situarPopupEvent.doAfterClose = function () { return; };
    situarPopupEvent.create(
            "dameContratosConReRun.action", 
            function (row) {
                return;
            }, 
            {"titulosRecontratadosRequest.contrato": contrato, 
             "titulosRecontratadosRequest.grupo": grupo,
             "titulosRecontratadosRequest.clave": clave,
             "titulosRecontratadosRequest.modo": modo,
             "titulosRecontratadosRequest.senial": senial}, 
             "contratosConReRun",
             true);
    return;
};
var modificarContratoBusiness = new ModificarContratoBusiness();