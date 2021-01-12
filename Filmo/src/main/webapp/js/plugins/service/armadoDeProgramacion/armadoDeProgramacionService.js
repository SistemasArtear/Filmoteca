function ArmadoDeProgramacionService() {
};

ArmadoDeProgramacionService.prototype.levantarListaDeProgramas = function(data) {
    BLOCK.showBlock("Se estan obteniendo los datos...");
    var request = null;
    if (data && data.idSenial) {
        request = "idSenial="+data.idSenial;
    }
    $.ajax({
        type : 'GET',
        url : 'levantarListaDeProgramas.action',
        data : request,
        cache: false,
        success : function(response) {
            BLOCK.hideBlock();
            if (response.message) {
                MESSAGE.error(response.message);
                return;
            }
            if (response.programas) {
                armadoDeProgramacionEvent.populateGrillaProgramas(response.programas);
            }
        }
    });
};
ArmadoDeProgramacionService.prototype.obtenerGrillaProgramada = function(data) {
    BLOCK.showBlock("Se estan obteniendo los datos...");
    var request = null;
    if (data && data.idSenial && data.programa && data.fechaSituar) {
        request = "idSenial="+data.idSenial+"&codPrograma="+data.programa+"&fechaSituar="+data.fechaSituar;
    }
    $.ajax({
        type : 'GET',
        url : 'obtenerGrillaProgramada.action',
        data : request,
        cache: false,
        success : function(response) {
            BLOCK.hideBlock();
            if (response.message) {
                MESSAGE.error(response.message);
                return; 
            }

            if (response.titulosProgramados) {
                armadoDeProgramacionEvent.populateGrillaTitulosProgramados(response.titulosProgramados);
                
                if (armadoDeProgramacionEvent.getInputSituarEnClave()) {
                    var filtros = {groupOp:"OR",rules:[]};
                    filtros.rules.push({field:"clave",op:"cn",data:armadoDeProgramacionEvent.getInputSituarEnClave().val().toUpperCase()});
                    armadoDeProgramacionEvent.getGrillaTitulosProgramados()[0].p.search = true;
                    $.extend(armadoDeProgramacionEvent.getGrillaTitulosProgramados()[0].p.postData,{filters:JSON.stringify(filtros)});
                };
                armadoDeProgramacionEvent.getGrillaTitulosProgramados().trigger("reloadGrid");
            }
        }
    });
};
ArmadoDeProgramacionService.prototype.obtenerDatosTitulo = function(tituloProgramadoSeleccionado) {
    BLOCK.showBlock("Se estan obteniendo los datos...");
    var request = "";
    if (tituloProgramadoSeleccionado) {
        if (tituloProgramadoSeleccionado.clave) {
            request += "claveTitulo="+tituloProgramadoSeleccionado.clave;
        } 
        if (tituloProgramadoSeleccionado.capitulo) {
            request += "&nroCapitulo="+tituloProgramadoSeleccionado.capitulo;
        }
        if (tituloProgramadoSeleccionado.parte) {
            request += "&parte="+tituloProgramadoSeleccionado.parte;
        }
        if (tituloProgramadoSeleccionado.segmento) {
            request += "&segmento="+tituloProgramadoSeleccionado.segmento;
        }
    }
    $.ajax({
        type : 'GET',
        url : 'obtenerDatosTitulo.action',
        data : request,
        cache: false,
        success : function(response) {
            BLOCK.hideBlock();
            if (response.message) {
                MESSAGE.error(response.message);
                return;
            }
            if (response.titulo) {
                armadoDeProgramacionEvent.populatePopUpConfirmacionFinal(response.titulo);
            }
        }
    });
};
ArmadoDeProgramacionService.prototype.obtenerListaTitulosProgramar = function(data) {
    BLOCK.showBlock("Se estan obteniendo los datos...");
    var request = null;
    if (data && data.idSenial && data.tituloCastellano && data.familiaTitulo) {
        request = "idSenial="+data.idSenial+"&tituloCast="+data.tituloCastellano+"&familiaTitulo="+data.familiaTitulo;
    }
    $.ajax({
        type : 'GET',
        url : 'obtenerListaTitulosProgramar.action',
        data : request,
        cache: false,
        success : function(response) {
            BLOCK.hideBlock();
            if (response.message) {
                MESSAGE.error(response.message);
                return;
            }
            if (response.listaTitulosProgramar) {
                armadoDeProgramacionEvent.populateGrillaDeTitulosAProgramar(response.listaTitulosProgramar);
            }
        }
    });
};
ArmadoDeProgramacionService.prototype.obtenerListadoCapitulos = function(data) {
    BLOCK.showBlock("Se estan obteniendo los datos...");
    var request = null;
    if (data && data.claveTitulo) {
        request = "claveTitulo="+data.claveTitulo;
    }
    $.ajax({
        type : 'GET',
        url : 'obtenerCapitulos.action',
        data : request,
        cache: false,
        success : function(response) {
            BLOCK.hideBlock();
            if (response.message) {
                MESSAGE.error(response.message);
                return;
            }
            if (response.capitulos) {
                armadoDeProgramacionEvent.populateGrillaDeCapitulos(response.capitulos);
            }
        }
    });
};
ArmadoDeProgramacionService.prototype.obtenerListadoParteSegmento = function(data) {
    BLOCK.showBlock("Se estan obteniendo los datos...");
    var request = null;
    if (data && data.claveTitulo && data.nroCapitulo) {
        request = "claveTitulo="+data.claveTitulo+"&nroCapitulo="+data.nroCapitulo;
    }
    $.ajax({
        type : 'GET',
        url : 'obtenerParteSegmento.action',
        data : request,
        cache: false,
        success : function(response) {
            BLOCK.hideBlock();
            if (response.message) {
                MESSAGE.error(response.message);
                return;
            }
            if (response.listaParteSegmento) {
                armadoDeProgramacionEvent.populateGrillaDeParteSegmento(response.listaParteSegmento);
            }
        }
    });
};
ArmadoDeProgramacionService.prototype.validadorABM = function(action, titulo) {
    titulo.fecha = titulo.fecha.replace(/\//g, "-");
    if (titulo.fechaAnterior) {
        titulo.fechaAnterior = titulo.fechaAnterior.replace(/\//g, "-");
    }
    action = action + ".action";
    var ret = false; 
    $.ajax({
        type : 'GET',
        url : action,
        cache: false,
        async: false,
        data : Component.serialize(titulo, "tituloRequest"),
        success : function(response) {
            if (response.message) {
                MESSAGE.error(response.message);
                ret = false;
                return;
            } else {
                $("#ArmadoDeProgramacionEventId_erroresYadvertenciasABM").empty();

                armadoDeProgramacionEvent.erroresYadvertenciasABM = response.listaResultadosValidacion;

                if (armadoDeProgramacionEvent.erroresYadvertenciasABM) {
                    var noHayErrores = true;
                    var noHayAdvertencias = true;
                    for (var i in armadoDeProgramacionEvent.erroresYadvertenciasABM) {
                        if (noHayErrores && (armadoDeProgramacionEvent.erroresYadvertenciasABM[i].tipo === "E")) {
                            armadoDeProgramacionEvent.getDivErroresYAdvertenciasABM().append("<div style='color: red'>"+armadoDeProgramacionEvent.erroresYadvertenciasABM[i].tipo+" - "+armadoDeProgramacionEvent.erroresYadvertenciasABM[i].descripcion+"</div>");
                            noHayErrores = false;
                        } else if (armadoDeProgramacionEvent.erroresYadvertenciasABM[i].tipo === "A") {
                            armadoDeProgramacionEvent.getDivErroresYAdvertenciasABM().append("<div style='color: red'>"+armadoDeProgramacionEvent.erroresYadvertenciasABM[i].tipo+" - "+armadoDeProgramacionEvent.erroresYadvertenciasABM[i].descripcion+"</div>");
                            noHayAdvertencias = false;
                        }
                    }
                    if (noHayErrores) {
                        //armadoDeProgramacionEvent.service.obtenerObservaciones(armadoDeProgramacionEvent.tituloProgramadoSeleccionado);
                        $(".ui-dialog-buttonpane button:contains('Programar')").fadeOut();
                        $(".ui-dialog-buttonpane button:contains('Salir')").fadeOut();

                        armadoDeProgramacionEvent.tituloProgramadoSeleccionado = titulo;
                        if (!noHayAdvertencias) {
                            //hay adevertencias
                            ret = true;
                            setTimeout(
                                    function(){
                                        $("#ArmadoDeProgramacionEventId_erroresYadvertenciasABM").empty();
                                        if (armadoDeProgramacionEvent.tipoOperacion === "A") {
                                            armadoDeProgramacionEvent.service.ABMProgramacion("alta", armadoDeProgramacionEvent.tituloProgramadoSeleccionado);
                                        } else if (armadoDeProgramacionEvent.tipoOperacion === "B") {
                                            armadoDeProgramacionEvent.service.ABMProgramacion("baja", armadoDeProgramacionEvent.tituloProgramadoSeleccionado);
                                        } else if (armadoDeProgramacionEvent.tipoOperacion === "M") {
                                            armadoDeProgramacionEvent.service.ABMProgramacion("modifica", armadoDeProgramacionEvent.tituloProgramadoSeleccionado);
                                        }
//                                        $.each(
//                                                $("#ArmadoDeProgramacionEventId_tituloProgramadoConCapitulo :input"), 
//                                                function(index, input) {
//                                                    $(input).val("");
//                                                }
//                                        );
                                        $(".ui-dialog-buttonpane button:contains('Programar')").fadeIn();
                                        $(".ui-dialog-buttonpane button:contains('Salir')").fadeIn();
                                        return;
                                    },
                                    3000);
                        } else {
                            //ni errores ni adevertencias
                            $(".ui-dialog-buttonpane button:contains('Programar')").fadeIn();
                            $(".ui-dialog-buttonpane button:contains('Salir')").fadeIn();

                            if (armadoDeProgramacionEvent.tipoOperacion === "A") {
                                armadoDeProgramacionEvent.service.ABMProgramacion("alta", armadoDeProgramacionEvent.tituloProgramadoSeleccionado);
                            } else if (armadoDeProgramacionEvent.tipoOperacion === "B") {
                                armadoDeProgramacionEvent.service.ABMProgramacion("baja", armadoDeProgramacionEvent.tituloProgramadoSeleccionado);
                            } else if (armadoDeProgramacionEvent.tipoOperacion === "M") {
                                armadoDeProgramacionEvent.service.ABMProgramacion("modifica", armadoDeProgramacionEvent.tituloProgramadoSeleccionado);
                            }
//                            $.each(
//                                    $("#ArmadoDeProgramacionEventId_tituloProgramadoConCapitulo :input"), 
//                                    function(index, input) {
//                                        $(input).val("");
//                                    }
//                            );
                            ret = true;
                        }
                        return;
                    } else {
                        ret = false;
                        return;
                    }
                }
                delete armadoDeProgramacionEvent.erroresYadvertenciasABM;
            }
        }
    });
    return ret;
};
ArmadoDeProgramacionService.prototype.ABMProgramacion = function(action, titulo) {
    titulo.fecha = titulo.fecha.replace(/\//g, "-");
    if (titulo.fechaAnterior) {
        titulo.fechaAnterior = titulo.fechaAnterior.replace(/\//g, "-");
    }
    var actionStruts = action + "Programacion.action";
    $.ajax({
        type : 'POST',
        url : actionStruts,
        cache: false,
        data : Component.serialize(titulo, "tituloRequest"),
        success : function(response) {
            if (response.message) {
                MESSAGE.error(response.message);
                return;
            } else {
//                MESSAGE.ok(action+" - OK!");
                armadoDeProgramacionEvent.service.obtenerGrillaProgramada(
                                    {idSenial: armadoDeProgramacionEvent.idSenial, 
                                     programa: armadoDeProgramacionEvent.codigoDelPrograma,
                                     fechaSituar: armadoDeProgramacionEvent.getInputSituarEnFecha().val()});
                return;
            }
        }
    });
};

ArmadoDeProgramacionService.prototype.obtenerObservaciones = function(titulo) {
    if (titulo.fecha) { titulo.fecha = titulo.fecha.replace(/\//g, "-"); }
    $.ajax({
        type : 'GET',
        url : "obtenerObservaciones.action",
        cache: false,
        data : Component.serialize(titulo, "tituloRequest"),
        success : function(response) {
            if (response.message) {
                MESSAGE.error(response.message);
                return;
            } else {
                if ($("#confimacionFinalObservacion").length == 0) {
                    $("#ArmadoDeProgramacionEventId_erroresYadvertenciasABM").append(
                            "<h2 id='confimacionFinalObservacion'>"+response.observaciones+"</h2>");
                }
            }
        }
    });
};

ArmadoDeProgramacionService.prototype.esUnaClaveValida = function (clave) {
    $.ajax({
        type : 'GET',
        url : "esUnaClaveValida.action",
        data : {claveTitulo: clave},
        cache: false,
        success : function(response) {
            if (response.esUnaClaveValida) {
                Component.get("html/armadoDeProgramacion/popUpListaCapitulos.html", armadoDeProgramacionEvent.drawListadoCapitulos);
            } else {
                MESSAGE.error("Clave inexistente");
            }
            return;
        }
    });
};

ArmadoDeProgramacionService.prototype.esUnCapituloValido = function (clave, capitulo) {
    $.ajax({
        type : 'GET',
        url : "esUnCapituloValido.action",
        data : {claveTitulo: clave, nroCapitulo: capitulo},
        cache: false,
        success : function(response) {
            if (response.esUnCapituloValido) {
                Component.get("html/armadoDeProgramacion/popUpListaParteSegmento.html", armadoDeProgramacionEvent.drawListadoParteSegmento);
            } else {
                MESSAGE.error("Capitulo inexistente");
            }
            return true;
        }
    });
};
