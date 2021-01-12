function ModificarContratoValidator(div) {
    this.div = div;
};
extend(ModificarContratoValidator, Plugin);

ModificarContratoValidator.prototype.validateEliminarGrupo = function(claveContrato, nroGrupo, senial) {
    ModificarContratoStaticService.doRequest(
        {action:"validarEliminarGrupo.action",
         request: {claveContrato: claveContrato, claveGrupo: nroGrupo, senial: senial},
         method: "GET",
         callback: function(data) {
             if (data && data.length != undefined && data.length > 0) {
                 var hayErrores = false;
                 var hayWarnings = false;
                 var hayInfo = false;
                 var warnings  = new Array();
                 var errores  = new Array();
                 var info  = new Array();
                 $.each(data, function(i, l) {
                     if (l["tipo"] === "E") {
                         hayErrores = true;
                         errores.push(l["descripcion"]);
                     } else if (l["tipo"] === "W") {
                         hayWarnings = true;
                         warnings.push(l["descripcion"]);
                     } else if (l["tipo"] === "I") {
                         hayinfo = true;
                         info.push(l["descripcion"]);
                     }
                 });
                 if (hayErrores) {
                     MESSAGE.error(errores[0], 10000);
                 } else if (hayWarnings){
                     popupConfirmacionEvent.confirmar = function () {
                         popupConfirmacionEvent.remove();
                         modificarContratoBusiness.borrarGrupo(claveContrato, nroGrupo, senial);
                     };
                     popupConfirmacionEvent.cancelar = function () {
                         popupConfirmacionEvent.remove();
                     };
                     popupConfirmacionEvent.popTitle = 'WARNINGs';
                     popupConfirmacionEvent.afterDraw = function() { return; };
                     popupConfirmacionEvent.create("warningsPopUp", warnings.join("<br/>"));
                 } else if (hayInfo) {
                     MESSAGE.alert(info[0]);
                     modificarContratoBusiness.borrarGrupo(claveContrato, nroGrupo, senial);
                 }
             } else {
                 modificarContratoBusiness.borrarGrupo(claveContrato, nroGrupo, senial);
             }
         },
         responseObject: "validacionModificacionContrato"});
    return
};
ModificarContratoValidator.prototype.validarExtenderChequeoTecnico = function(row) {
    modificarContratoValidator.validarExtenderChequeoTecnico.row = row;
    ModificarContratoStaticService.doRequest(
            {action:"validarExtenderChequeoTecnico.action",
             request: {claveContrato: row.contrato},
             method: "GET",
             callback: function(data) {
                if (data && data.length != undefined && data.length > 0) {
                    if ( (data[0].tipo === "P_OK") && (data[0].descripcion === "S") ){
                        MESSAGE.error("No se puede extender el chequeo tecnico para el contrato seleccionado");
                    } else{
                        ModificarContratoStaticService.doRequest(
                                {action:"dameSenialesChequeo.action",
                                 request: {claveContrato: row.contrato},
                                 method: "GET",
                                 callback: function(data) {
                                     if (data && data.length != undefined && data.length > 0) {
                                         modificarContratoDrawer.drawSenialesExtenderChequeo(modificarContratoValidator.validarExtenderChequeoTecnico.row);
                                     } else {
                                         MESSAGE.error("No hay señales para extender chequeo");
                                         ModificarContratoStaticService.doRequest(
                                                 {action:"desbloquearContrato.action",
                                                     request: {claveContrato: row.contrato},
                                                     method: "GET",
                                                     callback: function() {
                                                         return;
                                                     } 
                                                 });
                                     } },
                                 responseObject: "senialesAsignadas"});
                    }
                }
             },
             responseObject: "validacionModificacionContrato"});
};
ModificarContratoValidator.prototype.validateGrupo = function() {
    if (Validator.isEmpty(modificarContratoSelector.getInput("tipoTitulo", "grupoPopUp"), true)) {
        return false;
    }
    if (Validator.isEmpty(modificarContratoSelector.getInput("nombreGrupo", "grupoPopUp"), true)) {
        return false;
    }
    if (Validator.isEmpty(modificarContratoSelector.getInput("nombreGrupo", "grupoPopUp"), true)) {
        return false;
    }
    if (Validator.isEmpty(modificarContratoSelector.getInput("nroGrupo", "grupoPopUp"), true)) {
        return false;
    }
    if (Validator.isEmpty(modificarContratoSelector.getInput("cantTitulos", "grupoPopUp"), true)) {
        return false;
    }
    if (Validator.isEmpty(modificarContratoSelector.getInput("pasaLibreria", "grupoPopUp"), true)) {
        return false;
    }
    if (Validator.isEmpty(modificarContratoSelector.getInput("er", "grupoPopUp"), true)) {
        return false;
    }
    if (modificarContratoSelector.getInput("marcaPerpetuidad", "grupoPopUp").val() == "N") {
        var esCC = (modificarContratoBusiness.resolverFamilia(modificarContratoSelector.getInput("tipoTitulo", "grupoPopUp").val()) === ModificarContratoBusiness.CC);
        var leyenda = esCC ? "cantidad de repeticiones" : "cantidad de pasadas";
        var cantPasadasVacio = (modificarContratoSelector.getInput("cantPasadas", "grupoPopUp").val().trim() == "");
        var cantPesadasIgualAZero = (modificarContratoSelector.getInput("cantPasadas", "grupoPopUp").val().trim() == 0);
//        var cantPesadasMayorAZero = (modificarContratoSelector.getInput("cantPasadas", "grupoPopUp").val().trim() > 0);
        
        if ( (!esCC && (cantPasadasVacio || cantPesadasIgualAZero)) ) {
            MESSAGE.alert("Debe informar " + leyenda);
            return false;
        }
        if (Validator.isEmpty(modificarContratoSelector.getInput("cantTiempo", "grupoPopUp"), true, "Cantidad De Tiempo")) {
            return false;
        }
        if (Validator.isEmpty(modificarContratoSelector.getInput("unidadDeTiempo", "grupoPopUp"), true, "Unidad De Tiempo")) {
            return false;
        }
        if (modificarContratoSelector.getInput("pasaLibreria", "grupoPopUp").val() == "S") {
            modificarContratoSelector.getInput("pasaLibreria", "grupoPopUp").effect("highlight", {color : 'yellow'},1500);
            MESSAGE.alert("Pasa a Libreria NO puede ser 'S'");
            return false;
        }
    } else if (modificarContratoSelector.getInput("marcaPerpetuidad", "grupoPopUp").val() == "S") {
        if ( (modificarContratoSelector.getInput("cantTiempo", "grupoPopUp").val() !== "") && (modificarContratoSelector.getInput("cantTiempo", "grupoPopUp").val().trim() != 0) ) {
            modificarContratoSelector.getInput("cantTiempo", "grupoPopUp").effect("highlight", {color : 'yellow'}, 1500);
            MESSAGE.alert("La Cantidad De Tiempo debe estar vacia");
            return false;
        } else if (modificarContratoSelector.getInput("unidadDeTiempo", "grupoPopUp").val() !== "") {
            modificarContratoSelector.getInput("unidadDeTiempo", "grupoPopUp").effect("highlight", {color : 'yellow'}, 1500);
            MESSAGE.alert("La Unidad De Tiempo debe estar vacia");
            return false;
        } else if ( (modificarContratoSelector.getInput("cantPasadas", "grupoPopUp").val() !== "") && (modificarContratoSelector.getInput("cantPasadas", "grupoPopUp").val().trim() != 0) ) {
            modificarContratoSelector.getInput("cantPasadas", "grupoPopUp").effect("highlight", {color : 'yellow'}, 1500);
            var leyenda = (modificarContratoBusiness.resolverFamilia(modificarContratoSelector.getInput("tipoTitulo", "grupoPopUp").val()) === ModificarContratoBusiness.CC) ? "cantidad de repeticiones" : "cantidad de pasadas";
            MESSAGE.alert("La " + leyenda + " debe estar vacia");
            return false;
        }
    } else {
        if (Validator.isEmpty(modificarContratoSelector.getInput("unidadDeTiempo", "grupoPopUp"), true)) {
            return false;
        }

        if (Validator.isEmpty(modificarContratoSelector.getInput("cantTiempo", "grupoPopUp"), true)) { 
            return false;
        }

    }
    
    if (modificarContratoBusiness.resolverFamilia(modificarContratoSelector.getInput("tipoTitulo", "grupoPopUp").val()) === ModificarContratoBusiness.CC) {
        if (modificarContratoSelector.getInput("recontratacion", "grupoPopUp").val() == "S") {
            if (modificarContratoSelector.getInput("er", "grupoPopUp").val() == "E") {
                modificarContratoSelector.getInput("er", "grupoPopUp").effect("highlight", {color : 'yellow'}, 1500);
                MESSAGE.alert("Si es recontratacion debe ser Repeticion (R)");
                return false;
            }
        } else if ( (modificarContratoBusiness.resolverFamilia(modificarContratoSelector.getInput("tipoTitulo", "grupoPopUp").val()) === ModificarContratoBusiness.CC) &&
                Validator.isEmpty(modificarContratoSelector.getInput("recontratacion", "grupoPopUp"), true) ) {
            return false;
        }
    }

    if (Validator.isEmpty(modificarContratoSelector.getInput("tipoImporte", "grupoPopUp"), true)) {
        return false;
    }
    if (modificarContratoSelector.getInput("codigoDerechos", "grupoPopUp").val() == "F") {
        if (Validator.isEmpty(modificarContratoSelector.getInput("fechaDerechos", "grupoPopUp"), true)) {
            return false;
        }
    } else {
        if (modificarContratoSelector.getInput("fechaDerechos", "grupoPopUp").val() !== "") {
            modificarContratoSelector.getInput("fechaDerechos", "grupoPopUp").effect("highlight", {color : 'yellow'}, 1500);
            MESSAGE.alert("Fecha de Derechos debe estar vacio");
            return false;
        }
    }
    if (Validator.isEmpty(modificarContratoSelector.getInput("codigoDerechos", "grupoPopUp"), true)) {
        return false;
    }
    if (Validator.isEmpty(modificarContratoSelector.getInput("codigoDerechosTerritoriales", "grupoPopUp"), true)) {
        return false;
    }
    if (modificarContratoSelector.getInput("costoTotal", "grupoPopUp").val() === 0) {
        if (modificarContratoSelector.getInput("tipoImporte", "grupoPopUp").val() === "SCA") {
            modificarContratoSelector.getInput("costoTotal", "grupoPopUp").effect("highlight", {color : 'yellow'}, 1500);
            MESSAGE.alert("Costo/Importe unitario no puede ser cero");
            return false;
        }
    }
    if ( (modificarContratoSelector.getInput("tipoImporte", "grupoPopUp").val() === "SCA") || (modificarContratoSelector.getInput("tipoImporte", "grupoPopUp").val() === "VAR") ) {
        if (modificarContratoSelector.getInput("costoTotal", "grupoPopUp").val() != 0) {
            modificarContratoSelector.getInput("costoTotal", "grupoPopUp").effect("highlight", {color : 'yellow'}, 1500);
            MESSAGE.alert("Costo/Importe unitario debe ser cero");
            return false;
        }
    }
    if (Validator.isEmpty(modificarContratoSelector.getInput("costoTotal", "grupoPopUp"), true, "Costo")) {
        return false;
    }
    if (modificarContratoSelector.getInput("cantTiempoExclusivo", "grupoPopUp").val().trim() !== "") {
        if (Validator.isEmpty(modificarContratoSelector.getInput("unidadTiempoExclusivo", "grupoPopUp"), true)) {
            return false;
        }
    }
    if (modificarContratoSelector.getInput("unidadTiempoExclusivo", "grupoPopUp").val().trim() !== "") {
        if (Validator.isEmpty(modificarContratoSelector.getInput("cantTiempoExclusivo", "grupoPopUp"), true)) {
            return false;
        }
    }
    return true;
};


ModificarContratoValidator.prototype.validateTituloConGrupo = function() {
    if (Validator.isEmpty(modificarContratoSelector.getInput("pasaLibreria", "tituloConGrupoPopUp"), true)) {
        return false;
    }

    if (modificarContratoSelector.getInput("marcaPerpetuidad", "tituloConGrupoPopUp").val() == "N") {
        var leyenda = (modificarContratoBusiness.resolverFamilia(modificarContratoSelector.getInput("tipoTitulo", "tituloConGrupoPopUp").val()) === ModificarContratoBusiness.CC) ? "Cantidad De Repeticiones" : "Cantidad de Pasadas";
        if (Validator.isEmpty(modificarContratoSelector.getInput("cantPasadas", "tituloConGrupoPopUp"), true, leyenda) || (modificarContratoSelector.getInput("cantPasadas", "tituloConGrupoPopUp").val().trim() == 0)) {
            MESSAGE.alert("Si NO es perpetuo debe informar cantidad de pasadas");
            return false;
        }
        if (Validator.isEmpty(modificarContratoSelector.getInput("cantTiempo", "tituloConGrupoPopUp"), true, "Cantidad De Tiempo")) {
            return false;
        }
        if (Validator.isEmpty(modificarContratoSelector.getInput("unidadDeTiempo", "tituloConGrupoPopUp"), true, "Unidad De Tiempo")) {
            return false;
        }
        if (modificarContratoSelector.getInput("pasaLibreria", "tituloConGrupoPopUp").val() == "S") {
            modificarContratoSelector.getInput("pasaLibreria", "tituloConGrupoPopUp").effect("highlight", {color : 'yellow'},1500);
            MESSAGE.alert("Pasa a Libreria NO puede ser 'S'");
            return false;
        }
    } else if (modificarContratoSelector.getInput("marcaPerpetuidad", "tituloConGrupoPopUp").val() == "S") {
        if ( (modificarContratoSelector.getInput("cantTiempo", "tituloConGrupoPopUp").val() !== "") && (modificarContratoSelector.getInput("cantTiempo", "tituloConGrupoPopUp").val().trim() != 0) ) {
            modificarContratoSelector.getInput("cantTiempo", "grupoPopUp").effect("highlight", {color : 'yellow'}, 1500);
            MESSAGE.alert("La Cantidad De Tiempo debe estar vacia");
            return false;
        } else if (modificarContratoSelector.getInput("unidadDeTiempo", "tituloConGrupoPopUp").val() !== "") {
            modificarContratoSelector.getInput("unidadDeTiempo", "tituloConGrupoPopUp").effect("highlight", {color : 'yellow'}, 1500);
            MESSAGE.alert("La Unidad De Tiempo debe estar vacia");
            return false;
        } else if ( (modificarContratoSelector.getInput("cantPasadas", "tituloConGrupoPopUp").val() !== "") && (modificarContratoSelector.getInput("cantPasadas", "tituloConGrupoPopUp").val().trim() != 0) ) {
            modificarContratoSelector.getInput("cantPasadas", "tituloConGrupoPopUp").effect("highlight", {color : 'yellow'}, 1500);
            var leyenda = (modificarContratoBusiness.resolverFamilia(modificarContratoSelector.getInput("tipoTitulo", "tituloConGrupoPopUp").val()) === ModificarContratoBusiness.CC) ? "Cantidad de Repeticiones" : "Cantidad de Pasadas";
            MESSAGE.alert("La " + leyenda + "debe estar vacia");
            return false;
        }
    } else {
        if (Validator.isEmpty(modificarContratoSelector.getInput("unidadDeTiempo", "tituloConGrupoPopUp"), true)) {
            return false;
        }

        if (Validator.isEmpty(modificarContratoSelector.getInput("cantTiempo", "tituloConGrupoPopUp"), true)) { 
            return false;
        }

    }
    if (modificarContratoSelector.getInput("recontratacion", "tituloConGrupoPopUp").val() == "S") {
        if (modificarContratoSelector.getInput("er", "tituloConGrupoPopUp").val() == "E") {
            modificarContratoSelector.getInput("er", "tituloConGrupoPopUp").effect("highlight", {color : 'yellow'}, 1500);
            MESSAGE.alert("Si es recontratacion debe ser Repeticion (R)");
            return false;
        }
    }

    if (modificarContratoSelector.getInput("codigoDerechos", "tituloConGrupoPopUp").val() == "F") {
        if (Validator.isEmpty(modificarContratoSelector.getInput("fechaDerechos", "tituloConGrupoPopUp"), true)) {
            return false;
        }
    } else {
        if (modificarContratoSelector.getInput("fechaDerechos", "tituloConGrupoPopUp").val() !== "") {
            modificarContratoSelector.getInput("fechaDerechos", "tituloConGrupoPopUp").effect("highlight", {color : 'yellow'}, 1500);
            MESSAGE.alert("Fecha de Derechos debe estar vacio");
            return false;
        }
    }
    if (Validator.isEmpty(modificarContratoSelector.getInput("codigoDerechos", "tituloConGrupoPopUp"), true)) {
        return false;
    }
    if (Validator.isEmpty(modificarContratoSelector.getInput("codigoDerechosTerritoriales", "tituloConGrupoPopUp"), true)) {
        return false;
    }
    if (modificarContratoSelector.getInput("costoTotal", "tituloConGrupoPopUp").val() === 0) {
        if (modificarContratoSelector.getInput("tipoImporte", "tituloConGrupoPopUp").val() === "SCA") {
            modificarContratoSelector.getInput("costoTotal", "tituloConGrupoPopUp").effect("highlight", {color : 'yellow'}, 1500);
            MESSAGE.alert("Costo/Importe unitario no puede ser cero");
            return false;
        }
    }

    if (Validator.isEmpty(modificarContratoSelector.getInput("costoTotal", "tituloConGrupoPopUp"), true, "Costo")) {
        return false;
    }
    
    if (modificarContratoSelector.getInput("cantTiempoExclusivo", "tituloConGrupoPopUp").val().trim() !== "") {
        if (Validator.isEmpty(modificarContratoSelector.getInput("unidadTiempoExclusivo", "grupoPopUp"), true)) {
            return false;
        }
    }
    if (modificarContratoSelector.getInput("unidadTiempoExclusivo", "tituloConGrupoPopUp").val().trim() !== "") {
        if (Validator.isEmpty(modificarContratoSelector.getInput("cantTiempoExclusivo", "grupoPopUp"), true)) {
            return false;
        }
    }
    return true;
};


ModificarContratoValidator.prototype.validarContrato = function(contrato) {
    ModificarContratoStaticService.doRequest(
            {action:"validarMoficacionDeCabecera.action",
             request: Component.serialize(contrato, "contratoValidationRequest"),
             method: "GET",
             callback: function(data) {
                if (data && data.length && data.length > 0) {
                    MESSAGE.error(data[0].descripcion, 15000);
                } else {
                    modificarContratoValidator.validarContratoConSeniales(contrato);
                }
             },
             responseObject: "validacionModificacionContrato"});
};
ModificarContratoValidator.prototype.validarContratoConSeniales = function(contrato) {
    ModificarContratoStaticService.doRequest(
            {action:"validarContratoConSeniales.action",
             request: Component.serialize(contrato, "contratoValidationRequest"),
             method: "GET",
             callback: function(data) {
                if (data && data.length && data.length > 0) {
                    MESSAGE.error(data[0].descripcion, 15000);
                } else {
                    modificarContratoValidator.validarMontos(contrato);
                }
             },
             responseObject: "validacionModificacionContrato"});
};
ModificarContratoValidator.prototype.validarMontos = function(contrato) {
    ModificarContratoStaticService.doRequest(
            {action:"validarMontos.action",
             request: "claveContrato="+contrato.contrato,
             method: "GET",
             callback: function(data) {
                if (data && data.length && data.length > 0) {
                    MESSAGE.error(data[0].descripcion, 15000);
                } else {
                    modificarContratoBusiness.updateContrato(contrato);
                }
             },
             responseObject: "validacionModificacionContrato"});
};
ModificarContratoValidator.prototype.validarCabeceraSeniales = function(contrato, actualizarContrato) {
    ModificarContratoStaticService.doRequest(
            {action:"validarCabeceraSeniales.action",
             request: Component.serialize(contrato, "contratoValidationRequest"),
             method: "GET",
             callback: function(data) {
                var bloqueante = false;
                if (data && data.length && data.length > 0) {
                    var errores = [];
                    $.each(data, function() {
                        errores.push(this["tipo"]+" - "+this["descripcion"]);
                        if (this["tipo"] === "E") {
                            bloqueante = true;
                        }
                    });
                    popupConfirmacionEvent.confirmar = function () {
                        modificarContratoBusiness.actualizarContrato(contrato);
                        popupConfirmacionEvent.remove();
                    };
                    popupConfirmacionEvent.cancelar = function () {
                        popupConfirmacionEvent.remove();
                    };
                    if (bloqueante) {
                        popupConfirmacionEvent.popTitle = 'ERRORES';
                    } else {
                        popupConfirmacionEvent.popTitle = '¿ CONFIRMA ACTUALIZACION IMPORTE/MONTO DE CONTRATO ?';
                    }
                    popupConfirmacionEvent.afterDraw = function () {
                        if (bloqueante) {
                            this.getBotonConfimar().remove();
                            this.getBotonCancelar().remove();
                        }
                    };
                    popupConfirmacionEvent.create("errorPopUp", errores.join("<br/>"));
                } else if (actualizarContrato) {
                    modificarContratoBusiness.actualizarContrato(contrato);
                } else {
                    modificarContratoSelector.getPopUp("contratoConCabeceraPopUp").dialog("close");
                }
             },
             responseObject: "validacionModificacionContrato"});
};

ModificarContratoValidator.prototype.validarAltaDeGrupo = function(grupo) {
    var laAccion = "";
    if (modificarContratoBusiness.resolverFamilia(grupo.tipoTitulo) === ModificarContratoBusiness.CC) {
        laAccion = "validarAltaGrupoConCap.action";
    } else {
        laAccion = "validarAltaGrupoSinCap.action";
    }
    ModificarContratoStaticService.doRequest(
           {action: laAccion,
            request: Component.serialize(grupo, "grupoRequest"),
            method: "GET",
            callback: function(data) {
                if (data && data.length && data.length > 0) {
                    var hayErrores = false;
                    var errores = new Array();
                    var hayWarnings = false;
                    var warnings = new Array();
                    var hayInfo = false;
                    var info = new Array();
                    $.each(data, function(i, l) {
                        if (l["tipo"] === "E") {
                            hayErrores = true;
                            errores.push(l["descripcion"]);
                        } else if (l["tipo"] === "W") {
                            hayWarnings = true;
                            warnings.push(l["descripcion"]);
                        } else if (l["tipo"] === "I") {
                            hayInfo = true;
                            info.push(l["descripcion"]);
                        }
                    });
                    if (hayErrores) {
                        popupConfirmacionEvent.confirmar = function () {
                            popupConfirmacionEvent.remove();
                        };
                        popupConfirmacionEvent.cancelar = function () {
                            popupConfirmacionEvent.remove();
                        };
                        popupConfirmacionEvent.popTitle = 'ERRORES';
                        popupConfirmacionEvent.afterDraw = function() { 
                            this.getBotonConfimar().remove();
                            this.getBotonCancelar().remove();
                        };
                        popupConfirmacionEvent.create("erroresPopUp", errores.join("<br/>"));
                        delete modificarContratoBusiness.grupo;
                    } else if (hayWarnings) {
                        popupConfirmacionEvent.confirmar = function () {
                            popupConfirmacionEvent.remove();
                            modificarContratoSelector.getPopUp("grupoPopUp").dialog("close");
                            modificarContratoBusiness.altaDeGrupo(grupo);
                        };
                        popupConfirmacionEvent.cancelar = function () {
                            popupConfirmacionEvent.remove();
                        };
                        popupConfirmacionEvent.popTitle = 'WARNING';
                        popupConfirmacionEvent.afterDraw = function() { return; };
                        popupConfirmacionEvent.create("warningsPopUp", warnings.join("<br/>"));
                    } else if (hayInfo){
                        MESSAGE.alert(info[0]);
                        modificarContratoSelector.getPopUp("grupoPopUp").dialog("close");
                        modificarContratoBusiness.altaDeGrupo(grupo);
                    } else {
                        modificarContratoBusiness.altaDeGrupo(grupo);
                        modificarContratoSelector.getPopUp("grupoPopUp").dialog("close");
                    }
                } else {
                    modificarContratoBusiness.altaDeGrupo(grupo);
                    modificarContratoSelector.getPopUp("grupoPopUp").dialog("close");
                }
            },
            responseObject: "validacionModificacionContrato"});
};

ModificarContratoValidator.prototype.validarAltaTitulo = function(titulo, beforePopUp, callback, reqObject) {
    modificarContratoValidator.validarAltaTitulo.beforePopUp = beforePopUp;
    modificarContratoValidator.validarAltaTitulo.callback = callback;
    modificarContratoValidator.validarAltaTitulo.reqObject = reqObject;

    ModificarContratoStaticService.doRequest(
            {action:"validarAltaTitulo.action",
             request: Component.serialize(
                     {contrato: modificarContratoBusiness.grupo.contrato,
                      grupo: modificarContratoBusiness.grupo.nroGrupo,
                      senial: modificarContratoBusiness.grupo.senial,
                      clave: titulo.clave,
                      origen: titulo.origen,
                      tituloOriginal: titulo.tituloOriginal,
                      tituloCastellano: titulo.tituloCastellano,
                      er: modificarContratoBusiness.grupo.er,
                      recontratacion: titulo.recontratacion,
                      proveedor: modificarContratoBusiness.grupo.distribuidor,
                      calificacion: titulo.calificacionOficial ? titulo.calificacionOficial : "",
                      sinContrato: titulo.sinContrato}, "validarAltaTituloRequest"),
             method: "GET",
             callback: function(data) {
                 modificarContratoSelector.getPopUp("tituloPopUp").dialog("close");
                 if (data && data.length !== undefined && data.length > 0) {
                     var hayErrores = false;
                     var hayWarnings = false;
                     var warnings  = new Array();
                     var errores  = new Array();
                     
                     var hayCapitulos = false;
                     var fechaProceso = "";
                     var horaProceso = "";
                     var tgUsuario = "";
                     var tgWork = "";
                     
                     $.each(data, function(i, l){
                         if (l["tipo"] === "E") {
                             hayErrores = true;
                             errores.push(l["descripcion"]);
                         } else if (l["tipo"] === "W") {
                             hayWarnings = true;
                             warnings.push(l["descripcion"].trim());
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
                         popupConfirmacionEvent.confirmar = function () {
                             if (modificarContratoValidator.validarAltaTitulo.beforePopUp) {
                                 modificarContratoValidator.validarAltaTitulo.beforePopUp.getPopup().dialog("close");
                             }
                             popupConfirmacionEvent.remove();
                             modificarContratoBusiness.ingresarTitulos(modificarContratoBusiness.cantTitulosAAgregar, modificarContratoBusiness.grupo.tipoTitulo);
                         };
                         popupConfirmacionEvent.cancelar = function () {
                             if (modificarContratoValidator.validarAltaTitulo.beforePopUp) {
                                 modificarContratoValidator.validarAltaTitulo.beforePopUp.getPopup().dialog("close");
                             }
                             popupConfirmacionEvent.remove();
                             modificarContratoBusiness.ingresarTitulos(modificarContratoBusiness.cantTitulosAAgregar, modificarContratoBusiness.grupo.tipoTitulo);
                         };
                         
                         popupConfirmacionEvent.afterDraw = function() { 
                             this.getBotonCancelar().remove();
                         };
                         popupConfirmacionEvent.popTitle = 'ERRORES';
                         popupConfirmacionEvent.create("erroresPopUp", errores.join("<br/>"));
                     } else if (hayWarnings) {
                         popupConfirmacionEvent.confirmar = function () {
                             if (modificarContratoValidator.validarAltaTitulo.beforePopUp) {
                                 modificarContratoValidator.validarAltaTitulo.beforePopUp.getPopup().dialog("close");
                             }
                             popupConfirmacionEvent.remove();
                             modificarContratoBusiness.grabarTitulo(titulo, modificarContratoValidator.validarAltaTitulo.callback, modificarContratoValidator.validarAltaTitulo.reqObject);
                         };
                         popupConfirmacionEvent.cancelar = function () {
                             if (modificarContratoValidator.validarAltaTitulo.beforePopUp) {
                                 modificarContratoValidator.validarAltaTitulo.beforePopUp.getPopup().dialog("close");
                             }
                             popupConfirmacionEvent.remove();
                             modificarContratoBusiness.ingresarTitulos(modificarContratoBusiness.cantTitulosAAgregar, modificarContratoBusiness.grupo.tipoTitulo);
                         };
                         popupConfirmacionEvent.afterDraw = function() { return; };
                         popupConfirmacionEvent.popTitle = 'WARNING';
                         popupConfirmacionEvent.create("warningsPopUp", warnings.join("<br/>"));
                     } else if (hayCapitulos) {
                         MESSAGE.alert("Aca se deben mostrar los REMITOS");
                         modificarContratoBusiness.grabarTitulo(titulo, modificarContratoValidator.validarAltaTitulo.callback, modificarContratoValidator.validarAltaTitulo.reqObject);
                     } else {
                         if (modificarContratoValidator.validarAltaTitulo.beforePopUp) {
                             modificarContratoValidator.validarAltaTitulo.beforePopUp.getPopup().dialog("close");
                         }
                         modificarContratoBusiness.grabarTitulo(titulo, modificarContratoValidator.validarAltaTitulo.callback, modificarContratoValidator.validarAltaTitulo.reqObject);
                     } 
                 } else {
                     if (modificarContratoValidator.validarAltaTitulo.beforePopUp) {
                         modificarContratoValidator.validarAltaTitulo.beforePopUp.getPopup().dialog("close");
                     }
                     modificarContratoBusiness.grabarTitulo(titulo, modificarContratoValidator.validarAltaTitulo.callback, modificarContratoValidator.validarAltaTitulo.reqObject);
                 }
             },
             responseObject: "validacionModificacionContrato"});
};
ModificarContratoValidator.prototype.validarModificacionDeCantidadGrupo = function (grupo) {
    if (modificarContratoBusiness.resolverFamilia(grupo.tipoTitulo) === ModificarContratoBusiness.CC) {
        var laAccion = "validarModificaCantidadGrupoConCap.action";
        ModificarContratoStaticService.doRequest(
                {action: laAccion,
                    request: Component.serialize(grupo, "grupoRequest"),
                    method: "GET",
                    callback: function(data) {
                        if (data && (data.length !== undefined)) {
                            if (data.length > 0) {
                                var hayErrores = false;
                                var hayWarnings = false;
                                var warnings  = new Array();
                                var errores  = new Array();
                                $.each(data, function(i, l) {
                                    if (l["tipo"] === "E") {
                                        hayErrores = true;
                                        errores.push(l["descripcion"] + " **** ");
                                    } else if (l["tipo"] === "W") {
                                        hayWarnings = true;
                                        warnings.push(l["descripcion"] + " **** ");
                                    }
                                });
                                if (hayErrores) {
                                    MESSAGE.error(errores[0], 15000);
                                } else if (hayWarnings) {
                                    popupConfirmacionEvent.confirmar = function () {
                                        modificarContratoBusiness.modificacionDeCantidadGrupo(grupo);
                                        popupConfirmacionEvent.remove();
                                    };
                                    popupConfirmacionEvent.cancelar = function () {
                                        modificarContratoSelector.getPopUp("grupoPopUp").dialog("close");
                                        popupConfirmacionEvent.remove();
                                    };
                                    popupConfirmacionEvent.popTitle = 'WARNING';
                                    popupConfirmacionEvent.afterDraw = function() { return; };
                                    popupConfirmacionEvent.create("warningsPopUp", warnings.join("<br/>"));
                                }
                            } else {
                                modificarContratoBusiness.modificacionDeCantidadGrupo(grupo);
                            }
                        }
                    },
                    responseObject: "validacionModificacionContrato"});
    } else {
        modificarContratoBusiness.modificacionDeCantidadGrupo(grupo);
    }
};
ModificarContratoValidator.prototype.validarModificacionDeGrupo = function (grupo) {
    var laAccion = "";
    if (modificarContratoBusiness.resolverFamilia(grupo.tipoTitulo) === ModificarContratoBusiness.CC) {
        laAccion = "validarModificaGrupoConCap.action";
    } else {
        laAccion = "validarModificaGrupoSinCap.action";
    }
    ModificarContratoStaticService.doRequest(
           {action: laAccion,
            request: Component.serialize(grupo, "grupoRequest"),
            method: "GET",
            callback: function(data) {
                if (data && data.length && data.length > 0) {
                    var hayErrores = false;
                    var hayWarnings = false;
                    var fechaProceso = "";
                    var horaProceso;
                    var difTitulos = false;
                    var noModifica = false;
                    var diferencias = new Array();
                    var warnings  = new Array();
                    var errores  = new Array();
                    $.each(data, function(i, l){
                        if (l["tipo"] === "E") {
                            hayErrores = true;
                            errores.push("E - " + l["descripcion"]);
                        } else if (l["tipo"] === "W") {
                            hayWarnings = true;
                            errores.push("W - " + l["descripcion"].trim());
                            warnings.push("W - " + l["descripcion"].trim());
                        } else if (l["tipo"] === "D_FECHA_PROC")  {
                            //TODO remitos
                            fechaProceso = l["fecha"];
                            var dateSplit = fechaProceso.substring(0,10).split("-");
                            fechaProceso = dateSplit[2] + "/" + dateSplit[1] + "/" + dateSplit[0];
                            grupo.fechaProceso = fechaProceso;
                        } else if (l["tipo"] === "D_HORA_PROC")  {
                            //TODO remitos
                            horaProceso = l["nroAdvertencia"];
                            grupo.horaProceso = horaProceso;
                        } else if (l["tipo"] === "F_DIF_TIT")  {
                            difTitulos = l["descripcion"] === "S";
                        } else if (l["tipo"] === "F_NO_MODIF")  {
                            noModifica = l["descripcion"] === "S";
                        } else if (l["tipo"] === "D_DIF_TIT")  {
                            diferencias.push((l["descripcion"]).trim() + " --- ");
                        }
                    });
                    if (hayErrores) {
                        popupConfirmacionEvent.confirmar = function () {
                            popupConfirmacionEvent.remove();
                        };
                        popupConfirmacionEvent.cancelar = function () {
                            popupConfirmacionEvent.remove();
                        };
                        popupConfirmacionEvent.popTitle = 'ERRORES';
                        popupConfirmacionEvent.afterDraw = function() { 
                            this.getBotonConfimar().remove();
                            this.getBotonCancelar().remove();
                        };
                        popupConfirmacionEvent.create("erroresPopUp", errores.join("<br/>"));
                        //delete modificarContratoBusiness.grupo;
                    } else if (noModifica) {
                        if (difTitulos) {
                            popupConfirmacionEvent.confirmar = function () { popupConfirmacionEvent.remove(); };
                            popupConfirmacionEvent.cancelar = function () { return; };
                            popupConfirmacionEvent.afterDraw = function() {
                                this.getBotonConfimar().text("OK");
                                this.getBotonCancelar().remove();
                            };
                            popupConfirmacionEvent.popTitle = 'Titulos con Diff. en Grupo';
                            popupConfirmacionEvent.create("diferenciasPopUp", 
                                    "** NO QUEDAN TITULOS MODIFICABLES ** <br/><br/>TITULOS CON DIFF: " + diferencias.join("<br/>"));
                        }
                        //delete modificarContratoBusiness.grupo;
                    } else if (hayWarnings) {
                        popupConfirmacionEvent.confirmar = function () {
                            modificarContratoBusiness.modificacionDeGrupo(grupo);
                            popupConfirmacionEvent.remove();
                        };
                        popupConfirmacionEvent.cancelar = function () {
                            popupConfirmacionEvent.remove();
                        };
                        popupConfirmacionEvent.popTitle = 'WARNING';
                        popupConfirmacionEvent.afterDraw = function() { return; };
                        popupConfirmacionEvent.create("warningsPopUp", warnings.join("<br/>"));
                    } else if (difTitulos) {
                        popupConfirmacionEvent.confirmar = function () {
                            modificarContratoBusiness.modificacionDeGrupo(grupo);
                            popupConfirmacionEvent.remove();
                        };
                        popupConfirmacionEvent.cancelar = function () {
                            popupConfirmacionEvent.remove();r
                        };
                        popupConfirmacionEvent.afterDraw = function() { return; };
                        popupConfirmacionEvent.popTitle = 'Titulos con Diff. en Grupo';
                        popupConfirmacionEvent.create("diferenciasPopUp", "TITULOS CON DIFF: " + diferencias.join("<br/>") 
                                + "<br/>¿Confirma hacer el cambio solo en los titulos que coinciden con el grupo?");
                    } else {
                        modificarContratoBusiness.modificacionDeGrupo(grupo);
                    }
                }
            },
            responseObject: "validacionModificacionContrato"});

};
ModificarContratoValidator.prototype.validarDesmarcarTitulo = function(rowId) {
    if (rowId) {
        var row = modificarContratoSelector.getGrillaTitulos().jqGrid('getRowData', rowId);
        modificarContratoValidator.validarDesmarcarTitulo.clave = row.clave;
        ModificarContratoStaticService.doRequest(
                {action:"validarDesmarcarTitulo.action",
                    request: Component.serialize(
                           {contrato: modificarContratoBusiness.grupo.contrato,
                            grupo: modificarContratoBusiness.grupo.nroGrupo,
                            senial: modificarContratoBusiness.grupo.senial,
                            clave: modificarContratoValidator.validarDesmarcarTitulo.clave}, 
                            "tituloRequest"),
                            method: "GET",
                            callback: function(data) {
                                if (data) {
                                    var hayErrores = false;
                                    var hayWarnings = false;
                                    var warnings  = new Array();
                                    var errores  = new Array();
                                    
                                    var llamarReRun = false;
                                    var hayCapitulos = false;
                                    var fechaProceso;
                                    var horaProceso;
                                    
                                    $.each(data, function(i, l) {
                                        if (l["tipo"] === "E") {
                                            hayErrores = true;
                                            errores.push(l["descripcion"]);
                                        } else if (l["tipo"] === "W") {
                                            hayWarnings = true;
                                            warnings.push(l["descripcion"]);
                                        } else if ( (l["tipo"] === "RR") && (l["descripcion"] === "S") ) {
                                            llamarReRun = true;
                                        } else if (l["tipo"] === "D_FECHA_PROC") {
                                            fechaProceso = l["fecha"];
                                        } else if (l["tipo"] === "D_HORA_PROC") {
                                            horaProceso = l["nroAdvertencia"];
                                        } else if ( (l["tipo"] === "F_HAY_CAPITULOS") && (l["descripcion"] === "S") ) {
                                            hayCapitulos = true;
                                        }
                                        
                                    });
                                    
                                    if (hayErrores) {
                                        delete modificarContratoBusiness.grupo;
                                        popupConfirmacionEvent.confirmar = function () {
                                            popupConfirmacionEvent.remove();
                                        };
                                        popupConfirmacionEvent.cancelar = function () {
                                            popupConfirmacionEvent.remove();
                                        };
                                        popupConfirmacionEvent.popTitle = 'ERRORES';
                                        popupConfirmacionEvent.afterDraw = function() { 
                                            this.getBotonConfimar().remove();
                                            this.getBotonCancelar().remove();
                                        };
                                        popupConfirmacionEvent.create("erroresPopUp", errores.join("<br/>"));
                                    } else if (hayWarnings){
                                        popupConfirmacionEvent.confirmar = function () {
                                            modificarContratoBusiness.desmarcarTitulo(modificarContratoBusiness.validarDesmarcarTitulo.clave, llamarReRun, fechaProceso, horaProceso);
                                            popupConfirmacionEvent.remove();
                                        };
                                        popupConfirmacionEvent.cancelar = function () {
                                            popupConfirmacionEvent.remove();
                                        };
                                        popupConfirmacionEvent.popTitle = 'WARNING';
                                        popupConfirmacionEvent.afterDraw = function() { return; };
                                        popupConfirmacionEvent.create("warningsPopUp", warnings.join("<br/>"));
                                    } else if (hayCapitulos){
                                        //TODO REMITOS
                                        MESSAGE.alert("Existen REMITOS");
                                        modificarContratoBusiness.desmarcarTitulo(modificarContratoBusiness.validarDesmarcarTitulo.clave, llamarReRun, fechaProceso, horaProceso);
                                    } else {
                                        modificarContratoBusiness.desmarcarTitulo(modificarContratoBusiness.validarDesmarcarTitulo.clave, llamarReRun, fechaProceso, horaProceso);
                                    }
                                } else {
                                    MESSAGE.error("Error al intentar modificar el contrato");
                                }
                            },
                            responseObject: "validacionModificacionContrato"});
    }
};

var modificarContratoValidator = new ModificarContratoValidator(new DivDefinition('ModificarContratoEventId'));