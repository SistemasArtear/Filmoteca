function BajaDeContratoBusiness() {
};
extend(BajaDeContratoBusiness, Plugin);
BajaDeContratoBusiness.CC = "CC";
BajaDeContratoBusiness.SC = "SC";

BajaDeContratoBusiness.prototype.editarContratoConCabecera = function(row) {
    var self = bajaDeContratoBusiness;
    if (row) {
        BajaContratoStaticService.doRequest(
                {action:"estaBloqueado.action",
                    request: {claveContrato: row.contrato},
                    method: "GET",
                    callback: function(estaBloqueado) {
                        if (!estaBloqueado) {
                            BajaContratoStaticService.doRequest(
                                    {action:"bloquearContrato.action",
                                        request: {claveContrato: row.contrato},
                                        method: "GET",
                                        callback: function(data) {
                                            BajaContratoStaticService.doRequest(
                                                    {action:"dameContratoConCabecera.action",
                                                        request: {claveContrato: row.contrato, claveDistribuidor: row.distribuidor},
                                                        method: "GET",
                                                        callback: function(data) {
                                                            if (data && data.length != undefined && data.length > 0) {
                                                                bajaDeContratoBusiness.contratoConCabecera = data[0];
                                                                Component.get("html/modificarContrato/PopUpContrato.html", bajaDeContratoDrawer.drawPopUpContratoConCabecera);
                                                                bajaDeContratoSelector.getPopUp("contratoConCabeceraPopUp").dialog('open');
                                                                
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

BajaDeContratoBusiness.prototype.trabajarConGrupos = function(rowId) {
    var self = bajaDeContratoBusiness;
    if (rowId) {
        var row = bajaDeContratoSelector.getGrillaSenialesEImportes().jqGrid('getRowData', rowId);
        bajaDeContratoDrawer.drawGrillaDeGrupos();
        self.contratoConCabecera.senialElegida = row.codigoSenial;
        bajaDeContratoPopulator.populateGrillaDeGrupos(self.contratoConCabecera.senialElegida);
    } else {
        MESSAGE.alert("Debe seleccionar un grupo");
    }
};
BajaDeContratoBusiness.prototype.resolverFamilia = function(arg0) {
    for (var key in this.tiposDeTitulo) {
        if (this.tiposDeTitulo[key].tipoTitulo == arg0) {
            return this.tiposDeTitulo[key].familiaTitulo;
        }
    }
};
BajaDeContratoBusiness.prototype.validarBajaDeContrato = function(idContrato, popUpDialog) {
    bajaDeContratoBusiness.idContrato = idContrato;
    BajaContratoStaticService.doRequestSync(
            {action:"validarBajaContrato.action",
             request: {contrato: bajaDeContratoBusiness.idContrato},
             method: "GET",
             callback: function(data , popUpDialog) {
                 if (data && data.length && data.length > 0) {
                     //Tiene Errores
                     popupConfirmacionEvent.confirmar = function () {
                         popupConfirmacionEvent.remove();
                     };
                     popupConfirmacionEvent.cancelar = function () {
                         popupConfirmacionEvent.remove();
                     };
                     popupConfirmacionEvent.afterDraw = function() {
                         $.each(data, function(index, value) {
                             if ((data[index].tipo == "E") || (data[index].tipo == "ED")) {
                                 popupConfirmacionEvent.getBotonConfimar().hide();
                                 popupConfirmacionEvent.getBotonCancelar().hide();
                             } else {
                                 popupConfirmacionEvent.getBotonConfimar().show();
                                 popupConfirmacionEvent.getBotonCancelar().show();
                             }
                         });
                     };
                     popupConfirmacionEvent.create("validacionDarDeBajaContratoPopUp", bajaDeContratoBusiness.buildMsj(data));
                 } else {
                     //Entonces NO tiene Errores
                     popupConfirmacionEvent.confirmar = function () {
                         popupConfirmacionEvent.remove();
                         var id = bajaDeContratoBusiness.idContrato;
                         BajaContratoStaticService.doRequestSync(
                                 {action:"eliminarContrato.action",
                                  request: {contrato: id},
                                  method: "POST",
                                  callback: function(data, popUpDialog) {
                                      popupConfirmacionEvent.remove();
                                      Component.get("html/bajaDeContrato/ListadoContratos.html", bajaDeContratoDrawer.draw);
                                      popUpDialog.dialog("close");
                                      return;
                                  },
                                  responseObject: "bajaContratoValidationResult"}, popUpDialog);
                     };
                     popupConfirmacionEvent.cancelar = function () {
                         popupConfirmacionEvent.remove();
                     };
                     popupConfirmacionEvent.afterDraw = function() {
                         return;
                     };
                     popupConfirmacionEvent.create("darDeBajaContratoPopUp", "Se eliminara el contrato, ¿desea continuar?");

                 }
             },
             responseObject: "bajaContratoValidationResult"}, popUpDialog);
};

BajaDeContratoBusiness.prototype.buildMsj = function(data) {
    var ret = "";
    $.each(data, function(index, value) {
        ret = ret+'<div style="color: red">'+data[index].tipo+" - "+data[index].descripcion;
        if (data[index].idReporte){
            ret = ret + " - IDreporte: " + data[index].idReporte; 
        }
        ret = ret + "</div>";
    });
    return ret;
};

var bajaDeContratoBusiness = new BajaDeContratoBusiness();