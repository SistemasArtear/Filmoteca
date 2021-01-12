function ArmadoDeProgramacionEvent(div) {
    this.div = div;
};
extend(ArmadoDeProgramacionEvent, Plugin);
ArmadoDeProgramacionEvent.prototype.create = function() {
    var self = armadoDeProgramacionEvent;
    
    self.service = new ArmadoDeProgramacionService();
    Component.get("html/armadoDeProgramacion/listadoProgramas.html", armadoDeProgramacionEvent.drawGrillaProgramas);
};
ArmadoDeProgramacionEvent.prototype.drawGrillaProgramas = function(comp) {
    var self = armadoDeProgramacionEvent;
    $("#viewContainer").empty().append(comp.replace(/{{id}}/g, self.div.id));
    
    self.idSenial = $("#senialDefaultUsuario").val();
    self.service.levantarListaDeProgramas({idSenial: self.idSenial});
    
    self.getAcordionListadoProgramas().accordion({
		collapsible: false,
		active: 0,
		autoHeight: false,
		icons: false
	});
    
    self.getButtonBuscarProgramas().button().click(self.buscarProgramas);
    self.getInputBusquedaProgramaPorCodigo().numeric();
    self.getInputBusquedaProgramaPorCodigo().keypress(function (event) {
          if (event.which == 13) {
              event.preventDefault();
              self.buscarProgramas();
              return;
          }
        });
    self.getInputBusquedaProgramaPorDescripcion().keypress(function (event) {
          if (event.which == 13) {
              event.preventDefault();
              self.buscarProgramas();
              return;
          }
        });
    self.getGrillaProgramas().jqGrid({
        height: 'auto',
        autowidth: true,
        datatype: 'local',
        colNames:['Clave','Titulo'],
        colModel:[ 
            {name:'codigo',index:'codigo', width:150, sortable:true}, 
            {name:'descripcion',index:'descripcion', width:300, sortable:true}
        ],
        rowNum: 10,
        rowList:[10,20,30],
        scrollOffset: 0,
        pager: '#ArmadoDeProgramacionEventId_pagerGridProgramas',
        viewrecords: true, 
        loadonce: true,
        editurl: 'clientArray', 
        caption: 'Programas',
        ondblClickRow: function(id){
            armadoDeProgramacionEvent.codigoDelPrograma = armadoDeProgramacionEvent.getGrillaProgramas().jqGrid('getRowData', id).codigo;
            armadoDeProgramacionEvent.descripcionDelPrograma = armadoDeProgramacionEvent.getGrillaProgramas().jqGrid('getRowData', id).descripcion;
            var date = new Date();
            armadoDeProgramacionEvent.fechaSituar = date.getUTCDate() + "-" + (date.getUTCMonth() + 1) + "-" + date.getUTCFullYear(); 
            
            Component.get("html/armadoDeProgramacion/grillaProgramada.html", armadoDeProgramacionEvent.drawGrillaTitulosProgramados);
         },
        gridComplete: function() {
            return;
        }
    });
    if (self.idSenial) {
        self.service.levantarListaDeProgramas({idSenial: self.idSenial});
    }
};

ArmadoDeProgramacionEvent.prototype.populateGrillaProgramas = function(programas){
    var self = armadoDeProgramacionEvent;
    self.getGrillaProgramas().clearGridData().setGridParam({data: programas}).trigger('reloadGrid');
};








ArmadoDeProgramacionEvent.prototype.drawGrillaTitulosProgramados = function(comp){
    var self = armadoDeProgramacionEvent;

    $("#viewContainer").empty().append(comp.replace(/{{id}}/g, self.div.id).replace(/{{idSenial}}/g, self.idSenial).replace(/{{descripcionPrograma}}/g, self.descripcionDelPrograma));
    
    self.getButtonProgramarConCapitulos().button().click(
            function(){
                self.tituloQueDeseaProgramar = "CC";
                self.tipoOperacion = "A";
                Component.get("html/armadoDeProgramacion/popUpTituloProgramadoConCapitulo.html", armadoDeProgramacionEvent.drawPopUpTituloProgramadoConCapitulo);
            });
    self.getButtonProgramarSinCapitulos().button().click(
            function(){
                self.tituloQueDeseaProgramar = "SC";
                self.tipoOperacion = "A";
                Component.get("html/armadoDeProgramacion/popUpTituloProgramadoSinCapitulo.html", armadoDeProgramacionEvent.drawPopUpTituloProgramadoSinCapitulo);
            });
    self.getButtonVolverListaDeProgramas().button().click(
            function(){
                Component.get("html/armadoDeProgramacion/listadoProgramas.html", armadoDeProgramacionEvent.drawGrillaProgramas);
            });

    self.getInputSituarEnFecha()
    .datepicker({showOn : "both", buttonImage : "img/calendar.gif", buttonImageOnly : true, minDate: 0, dateFormat: 'dd-mm-yy'})
    .mask("99-99-9999");
    self.getInputSituarEnFecha().datepicker('setDate', new Date());
    self.getInputSituarEnFecha().keypress(function (event) {
        if (event.which == 13) {
            event.preventDefault();
            self.service.obtenerGrillaProgramada(
                    {idSenial: self.idSenial, 
                     programa: self.codigoDelPrograma,
                     fechaSituar: self.getInputSituarEnFecha().val()});
            return;
        }
    });
    
    self.getInputSituarEnClave().keypress(function (event) {
          if (event.which == 13) {
              event.preventDefault();
              
              if (self.getInputSituarEnClave().val() != "") {
                  var foo = self.getInputSituarEnClave().val();
                  var cantCerosAAgregar = 6 - (foo.length - 2);
                  var tipoTitulo = foo.substring(0,2);
                  var clave = foo.substring(2,8);
                  var ret = tipoTitulo.toUpperCase();
                  while (cantCerosAAgregar > 0) {
                      ret = ret + "0"; 
                      cantCerosAAgregar--;
                  }
                  ret = ret + clave;
                  self.getInputSituarEnClave().val(ret);
              }

              self.service.obtenerGrillaProgramada(
                      {idSenial: self.idSenial, 
                       programa: self.codigoDelPrograma,
                       fechaSituar: self.getInputSituarEnFecha().val()});
              return;
          }
     });
    
    self.getGrillaTitulosProgramados().jqGrid({
        height: 'auto',
        autowidth: true,
        datatype: 'local',
        colNames:['Dia','Fecha','Titulo Castellano','Clave','Cap','P','S','Fr','De','OR','VER','MODIF','ELIMIN'],
        colModel:[ 
            {name:'dia',index:'dia', width:10, sortable:false},
            {name:'fecha',index:'fecha', width:10, sortable:false},
            {name:'tituloCast',index:'tituloCast', width:35, sortable:false},
            {name:'clave',index:'clave', width:15, sortable:false},
            {name:'capitulo',index:'cap', width:15, sortable:false},
            {name:'parte',index:'p', width:5, sortable:false},
            {name:'segmento',index:'s', width:5, sortable:false},
            {name:'fraccion',index:'fr', width:5, sortable:false},
            {name:'totalDeFracciones',index:'de', width:5, sortable:false},
            {name:'ordenSalida',index:'or', width:5, sortable:false},
            {name:'visualizar',index:'visualizar', width:5, aling:'center', sortable:false},
            {name:'modificar',index:'modificar', width:5, aling:'center', sortable:false},
            {name:'eliminar',index:'eliminar', width:5, aling:'center', sortable:false}
        ],
        rowNum: 20,
        rowList:[10,20,30],
        scrollOffset: 0,
        pager: '#ArmadoDeProgramacionEventId_pagerGrillaProgramada',
        viewrecords: true, 
        loadonce: true,
        editurl: 'clientArray', 
        caption: 'Administracion de Grilla',
        gridComplete: function() {
            var ids = $(this).jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var rowId = ids[i];
                var visualizar = "<div onclick='armadoDeProgramacionEvent.visualizarTituloProgramdo("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-search visualizar conTooltip'>Visualizar</span></div>";
                $(this).jqGrid('setRowData', rowId, { visualizar: visualizar });
                var modificar = "<div onclick='armadoDeProgramacionEvent.modificarTituloProgramdo("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-pencil modificar conTooltip'>Modificar</span></div>";
                $(this).jqGrid('setRowData', rowId, { modificar: modificar });
                var eliminar = "<div onclick='armadoDeProgramacionEvent.eliminarTituloProgramdo("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-trash eliminar conTooltip'>Eliminar</span></div>";
                $(this).jqGrid('setRowData', rowId, { eliminar: eliminar });
            }
            return;
        }
    });
    armadoDeProgramacionEvent.fechaSituar = 
                self.getInputSituarEnFecha().datepicker('getDate').getUTCDate() 
        + "-" + (self.getInputSituarEnFecha().datepicker('getDate').getUTCMonth() + 1) 
        + "-" + self.getInputSituarEnFecha().datepicker('getDate').getUTCFullYear(); 
    
    self.service.obtenerGrillaProgramada(
               {idSenial: self.idSenial, 
                programa: self.codigoDelPrograma, 
                fechaSituar: self.fechaSituar});
};
ArmadoDeProgramacionEvent.prototype.visualizarTituloProgramdo = function(rowId) {
    this.tituloProgramadoSeleccionado = this.getRowData(this.getGrillaTitulosProgramados(), rowId);
    this.tituloProgramadoSeleccionado.senial = this.idSenial;
    this.tituloProgramadoSeleccionado.codigoDelPrograma = this.codigoDelPrograma;
    delete this.tituloProgramadoSeleccionado.visualizar;
    delete this.tituloProgramadoSeleccionado.modificar;
    delete this.tituloProgramadoSeleccionado.eliminar;
    delete this.tituloProgramadoSeleccionado.dia;
    delete this.tituloProgramadoSeleccionado.tituloCast;
    
    this.tipoOperacion = "V";
    Component.get("html/armadoDeProgramacion/popUpConfirmacionFinal.html", this.drawConfirmacionFinal);
};
ArmadoDeProgramacionEvent.prototype.eliminarTituloProgramdo = function(rowId) {
  this.tituloProgramadoSeleccionado = this.getRowData(this.getGrillaTitulosProgramados(), rowId);
  this.tituloProgramadoSeleccionado.senial = this.idSenial;
  this.tituloProgramadoSeleccionado.codigoDelPrograma = this.codigoDelPrograma;
  delete this.tituloProgramadoSeleccionado.visualizar;
  delete this.tituloProgramadoSeleccionado.modificar;
  delete this.tituloProgramadoSeleccionado.eliminar;
  delete this.tituloProgramadoSeleccionado.dia;
  delete this.tituloProgramadoSeleccionado.tituloCast;

  this.tipoOperacion = "B";
  Component.get("html/armadoDeProgramacion/popUpEliminarTituloProgramado.html", this.drawPopUpEliminarTituloProgramado);
};
ArmadoDeProgramacionEvent.prototype.modificarTituloProgramdo = function(rowId) {
  this.tituloProgramadoSeleccionado = this.getRowData(this.getGrillaTitulosProgramados(), rowId);
  this.tituloProgramadoSeleccionado.senial = this.idSenial;
  this.tituloProgramadoSeleccionado.codigoDelPrograma = this.codigoDelPrograma;
  delete this.tituloProgramadoSeleccionado.visualizar;
  delete this.tituloProgramadoSeleccionado.modificar;
  delete this.tituloProgramadoSeleccionado.eliminar;
  delete this.tituloProgramadoSeleccionado.dia;
  delete this.tituloProgramadoSeleccionado.tituloCast;

  this.tipoOperacion = "M";
  Component.get("html/armadoDeProgramacion/popUpModificarTituloProgramado.html", this.drawPopUpModificarTituloProgramado);
};
/** ***** DRAWERS ***** **/
ArmadoDeProgramacionEvent.prototype.drawPopUpEliminarTituloProgramado = function(comp){
    var self = armadoDeProgramacionEvent;
    
    if (self.getEliminarTituloProgramadoPopUp().length == 0) {
        $("#viewContainer").append(comp.replace(/{{id}}/g, self.div.id));
        self.getEliminarTituloProgramadoPopUp().dialog({
            title : 'Eliminar Titulo Programado',
            width: 600,
            show: 'blind',
            hide: 'blind',
            modal: true,
            autoOpen: true,
            open: function() {
                self.populatePopUpEliminarTituloProgramado(self.tituloProgramadoSeleccionado);
            },
            buttons: {
                Eliminar: function() {
                    if (self.service.validadorABM("validarBajaProgramacion", self.tituloProgramadoSeleccionado)) {
                        //$(this).dialog("close" );
                        $(this).dialog('destroy').remove();
                    };
                },
                Cancelar: function() {
                    //$(this).dialog("close" );
                    $(this).dialog('destroy').remove();
                }
            }
        });
    } else {
        self.getEliminarTituloProgramadoPopUp().dialog("open");
    }

};
ArmadoDeProgramacionEvent.prototype.drawPopUpModificarTituloProgramado = function(comp){
    var self = armadoDeProgramacionEvent;
    
    if (self.getModificarTituloProgramadoPopUp().length == 0) {
        $("#viewContainer").append(comp.replace(/{{id}}/g, self.div.id));
        self.getModificarTituloProgramadoPopUp().dialog({
            title : 'Modificar Titulo Programado',
            width: 600,
            show: 'blind',
            hide: 'blind',
            modal: true,
            autoOpen: true,
            open: function() {
                self.populatePopUpModificarTituloProgramado(self.tituloProgramadoSeleccionado);
                self.getInputModificarTituloProgramado("fraccion").numeric();
                self.getInputModificarTituloProgramado("de").numeric();
                self.getInputModificarTituloProgramado("orden").numeric();
            },
            buttons: {
                Modificar: function() {
                    
                    if (Validator.isEmpty(self.getInputModificarTituloProgramado("orden"), true)) {
                        return
                    };
                    
                    if (Validator.isEmpty(self.getInputModificarTituloProgramado("fraccion"), true)) {
                        return
                    };
                    
                    if (Validator.isEmpty(self.getInputModificarTituloProgramado("de"), true)) {
                        return
                    };
                    
                    if (Validator.isEmpty(self.getInputModificarTituloProgramado("orden"), true)) {
                        return
                    };

                    if (!Datepicker.isAValidateDate(self.getInputModificarTituloProgramado("fechaExhibicion").val())) {
                        MESSAGE.alert("Fecha de Exhibicion invalida");
                        self.getInputModificarTituloProgramado("fechaExhibicion").effect("highlight", {color:'yellow'}, 1500);
                        return
                    };

                    if (self.getInputModificarTituloProgramado("fraccion").val()
                            > self.getInputModificarTituloProgramado("de").val()) {
                        MESSAGE.alert("El campo fraccion total no puede ser menor que el campo fraccion");
                        self.getInputModificarTituloProgramado("fraccion")
                                    .effect("highlight", {color:'yellow'}, 1500);
                        self.getInputModificarTituloProgramado("fraccionTotal")
                                    .effect("highlight", {color:'yellow'}, 1500);
                    } else {
                        self.tituloProgramadoSeleccionadoModificado = {};
                        
                        self.tituloProgramadoSeleccionadoModificado.senial = self.tituloProgramadoSeleccionado.senial;
                        
                        self.tituloProgramadoSeleccionadoModificado.codigoDelPrograma = self.tituloProgramadoSeleccionado.codigoDelPrograma;
                        
                        self.tituloProgramadoSeleccionadoModificado.clave = self.tituloProgramadoSeleccionado.clave;

                        self.tituloProgramadoSeleccionadoModificado.capitulo = self.tituloProgramadoSeleccionado.capitulo;                
                                                
                        self.tituloProgramadoSeleccionadoModificado.parte = self.tituloProgramadoSeleccionado.parte;                

                        self.tituloProgramadoSeleccionadoModificado.segmento = self.tituloProgramadoSeleccionado.segmento;                

                        self.tituloProgramadoSeleccionadoModificado.fechaAnterior = self.tituloProgramadoSeleccionado.fecha;
                        self.tituloProgramadoSeleccionadoModificado.fecha = self.getInputModificarTituloProgramado("fechaExhibicion").val();
                        
                        self.tituloProgramadoSeleccionadoModificado.fraccionAnterior = self.tituloProgramadoSeleccionado.fraccion;
                        self.tituloProgramadoSeleccionadoModificado.fraccion = self.getInputModificarTituloProgramado("fraccion").val();
                        
                        self.tituloProgramadoSeleccionadoModificado.totalDeFraccionesAnterior = self.tituloProgramadoSeleccionado.totalDeFracciones;
                        self.tituloProgramadoSeleccionadoModificado.totalDeFracciones = self.getInputModificarTituloProgramado("de").val();
                        
                        self.tituloProgramadoSeleccionadoModificado.ordenSalidaAnterior = self.tituloProgramadoSeleccionado.ordenSalida;
                        self.tituloProgramadoSeleccionadoModificado.ordenSalida = self.getInputModificarTituloProgramado("orden").val();

                        if (self.service.validadorABM("validarModificaProgramacion", self.tituloProgramadoSeleccionadoModificado)) {
                            //$(this).dialog("close" );
                            $(this).dialog('destroy').remove();
                        };
                    }
                },
                Cancelar: function() {
                    //$(this).dialog("close" );
                    $(this).dialog('destroy').remove();
                }
            }
        });
    } else {
        self.getModificarTituloProgramadoPopUp().dialog("open");
    }

};
ArmadoDeProgramacionEvent.prototype.drawPopUpTituloProgramadoSinCapitulo = function(comp){
    var self = armadoDeProgramacionEvent;
    
    if (self.getTituloProgramadoSinCapituloPopUp().length == 0) {
        $("#viewContainer").append(comp.replace(/{{id}}/g, self.div.id));
        self.getTituloProgramadoSinCapituloPopUp().dialog({
            title : 'PROGRAMAR TITULO LA-LM-PE',
            width: 600,
            show: 'blind',
            hide: 'blind',
            modal: true,
            autoOpen: true,
            open: function() {

                $.each(
                        $("#ArmadoDeProgramacionEventId_tituloProgramadoSinCapitulo :input"), 
                        function(index, input) {
                            $(input).val("");
                        }
                );
                
                self.getInputTituloProgramadoSinCapitulo("clave").focusout(function (e) {
                    if (self.getInputTituloProgramadoSinCapitulo("clave").val() != "") {
                        var foo = self.getInputTituloProgramadoSinCapitulo("clave").val();
                        var cantCerosAAgregar = 6 - (foo.length - 2);
                        var tipoTitulo = foo.substring(0,2);
                        var clave = foo.substring(2,8);
                        var ret = tipoTitulo.toUpperCase();
                        while (cantCerosAAgregar > 0) {
                            ret = ret + "0"; 
                            cantCerosAAgregar--;
                        }
                        ret = ret + clave;
                        self.getInputTituloProgramadoSinCapitulo("clave").val(ret);
                    }
                });

                self.getInputTituloProgramadoSinCapitulo("parte").numeric();
                self.getInputTituloProgramadoSinCapitulo("parte").val(0);
                
                self.getInputTituloProgramadoSinCapitulo("segmento").numeric();
                self.getInputTituloProgramadoSinCapitulo("segmento").val(0);

                self.getInputTituloProgramadoSinCapitulo("fraccion").numeric();
                self.getInputTituloProgramadoSinCapitulo("fraccion").val(1);

                self.getInputTituloProgramadoSinCapitulo("fraccionTotal").numeric();
                self.getInputTituloProgramadoSinCapitulo("fraccionTotal").val(1);
                
                self.getInputTituloProgramadoSinCapitulo("orden").numeric();
                self.getInputTituloProgramadoSinCapitulo("orden").val(1);

                self.getInputTituloProgramadoSinCapitulo("fechaExhibicion")
                .datepicker({showOn: "button", buttonImage : "img/calendar.gif", buttonImageOnly : true, minDate: 0, dateFormat: 'dd-mm-yy'})
                .mask("99-99-9999");

                self.getButtonBuscarPorClave().button().click( 
                        function (e) {
                            e.preventDefault();
                            Component.get("html/armadoDeProgramacion/popUpSituarClavePorTitulo.html", self.drawSituarClavePorTitulo);
                        }
                );
            },
            close: function(){
                self.getTituloProgramadoSinCapituloPopUp().remove();
            },
            buttons: {
                Programar: function() {
                    if (Validator.isEmpty(self.getInputTituloProgramadoSinCapitulo("clave"), true)) {
                        return
                    };
                    
                    if (Validator.isEmpty(self.getInputTituloProgramadoSinCapitulo("fraccion"), true)) {
                        return
                    };
                    
                    if (Validator.isEmpty(self.getInputTituloProgramadoSinCapitulo("fraccionTotal"), true)) {
                        return
                    };
                    
                    if (Validator.isEmpty(self.getInputTituloProgramadoSinCapitulo("orden"), true)) {
                        return
                    };
                    
                    if (Validator.isEmpty(self.getInputTituloProgramadoSinCapitulo("fechaExhibicion"), true)) {
                        return
                    };
                    
                    if (Validator.esUnaFechaPasada(self.getInputTituloProgramadoSinCapitulo("fechaExhibicion"), true, "Fecha Exhibicion")) {
                        return
                    };
                    
                    if (!Datepicker.isAValidateDate(self.getInputTituloProgramadoSinCapitulo("fechaExhibicion").val())) {
                        MESSAGE.alert("Fecha de Exhibicion invalida");
                        self.getInputTituloProgramadoSinCapitulo("fechaExhibicion").effect("highlight", {color:'yellow'}, 1500);
                        return
                    };
                    
                    if (self.getInputTituloProgramadoSinCapitulo("fraccion").val()
                            > self.getInputTituloProgramadoSinCapitulo("fraccionTotal").val()) {
                        MESSAGE.alert("El campo fraccion total no puede ser menor que el campo fraccion");
                        self.getInputTituloProgramadoSinCapitulo("fraccion").effect("highlight", {color:'yellow'}, 1500);
                        self.getInputTituloProgramadoSinCapitulo("fraccionTotal").effect("highlight", {color:'yellow'}, 1500);
                    } else {
                        var titulo = 
                               {senial: self.idSenial,
                                codigoDelPrograma: self.codigoDelPrograma, 
                                fecha: self.getInputTituloProgramadoSinCapitulo("fechaExhibicion").val(), 
                                clave: self.getInputTituloProgramadoSinCapitulo("clave").val(), 
                                fraccion: self.getInputTituloProgramadoSinCapitulo("fraccion").val(), 
                                totalDeFracciones: self.getInputTituloProgramadoSinCapitulo("fraccionTotal").val(), 
                                ordenSalida: self.getInputTituloProgramadoSinCapitulo("orden").val(), 
                                familiaTitulo: "SC"};
                        
                        if (self.service.validadorABM("validarAltaProgramacion", titulo)) {
                            self.getInputTituloProgramadoSinCapitulo("clave").val("");
                            
                            self.getInputTituloProgramadoSinCapitulo("fraccion").numeric();
                            self.getInputTituloProgramadoSinCapitulo("fraccion").val(1);
                            
                            self.getInputTituloProgramadoSinCapitulo("fraccionTotal").numeric();
                            self.getInputTituloProgramadoSinCapitulo("fraccionTotal").val(1);
                            
                            self.getInputTituloProgramadoSinCapitulo("orden").numeric();
                            self.getInputTituloProgramadoSinCapitulo("orden").val(1);
                            

                            if (armadoDeProgramacionEvent.workingFromConfirmacionDeProgramacion) {
                                armadoDeProgramacionEvent.getInputTituloProgramadoSinCapitulo("fechaExhibicion").val(confirmarProgramacionEvent.getSelector("fecha").val());
                            } else {
                                armadoDeProgramacionEvent.getInputTituloProgramadoSinCapitulo("fechaExhibicion").val("");
                            }
                        };
                        
                    }
                },
                
//                "Ver Observaciones": function() {
//                    self.service.obtenerObservaciones(self.tituloProgramadoSeleccionado);
//                    return;
//                },

                Salir: function() {
                    $(this).dialog("close" );
                }
            }
        });
    } else {
        self.getTituloProgramadoSinCapituloPopUp().dialog("open");
    }

};
ArmadoDeProgramacionEvent.prototype.drawPopUpTituloProgramadoConCapitulo = function(comp){
    var self = armadoDeProgramacionEvent;
    
    if (self.getTituloProgramadoConCapituloPopUp().length == 0) {
        $("#viewContainer").append(comp.replace(/{{id}}/g, self.div.id));
        self.getTituloProgramadoConCapituloPopUp().dialog({
            title : 'PROGRAMAR TITULO SE-MS',
            width: 600,
            show: 'blind',
            hide: 'blind',
            modal: true,
            autoOpen: true,
            open: function() {
                $.each(
                        $("#ArmadoDeProgramacionEventId_tituloProgramadoConCapitulo :input"), 
                        function(index, input) {
                            $(input).val("");
                        }
                );
                self.getInputTituloProgramadoConCapitulo("clave").focusout(function (e) {
                    if (self.getInputTituloProgramadoConCapitulo("clave").val() != "") {
                        var foo = self.getInputTituloProgramadoConCapitulo("clave").val();
                        var cantCerosAAgregar = 6 - (foo.length - 2);
                        var tipoTitulo = foo.substring(0,2);
                        var clave = foo.substring(2,8);
                        var ret = tipoTitulo.toUpperCase();
                        while (cantCerosAAgregar > 0) {
                            ret = ret + "0"; 
                            cantCerosAAgregar--;
                        }
                        ret = ret + clave;
                        self.getInputTituloProgramadoConCapitulo("clave").val(ret);
                    }
                });

                self.getInputTituloProgramadoConCapitulo("capitulo").numeric();

                self.getInputTituloProgramadoConCapitulo("parte").numeric();
                self.getInputTituloProgramadoConCapitulo("parte").val(0);

                self.getInputTituloProgramadoConCapitulo("segmento").numeric();
                self.getInputTituloProgramadoConCapitulo("segmento").val(0);

                self.getInputTituloProgramadoConCapitulo("fraccion").numeric();
                self.getInputTituloProgramadoConCapitulo("fraccion").val(1);

                self.getInputTituloProgramadoConCapitulo("fraccionTotal").numeric();
                self.getInputTituloProgramadoConCapitulo("fraccionTotal").val(1);
                
                self.getInputTituloProgramadoConCapitulo("orden").numeric();
                self.getInputTituloProgramadoConCapitulo("orden").val(1);
                
                self.getInputTituloProgramadoConCapitulo("fechaExhibicion")
                .datepicker({showOn: "button", buttonImage : "img/calendar.gif", buttonImageOnly : true, minDate: 0, dateFormat: 'dd-mm-yy'})
                .mask("99-99-9999");

                self.getButtonBuscarPorClave().button().click(
                        function (e) {
                            e.preventDefault();
                            Component.get("html/armadoDeProgramacion/popUpSituarClavePorTitulo.html", self.drawSituarClavePorTitulo);
                        }
                );

                self.getButtonBuscarPorCapitulo().button().click( 
                        function (e) {
                            e.preventDefault();
                            if (self.getInputTituloProgramadoConCapitulo("clave") && self.getInputTituloProgramadoConCapitulo("clave").val() != "") {
                                self.service.esUnaClaveValida(self.getInputTituloProgramadoConCapitulo("clave").val());
                            }
                        }
                );

                self.getButtonBuscarPorParte().button().click(
                        function (e) {
                            e.preventDefault();
                            if (self.getInputTituloProgramadoConCapitulo("capitulo") && self.getInputTituloProgramadoConCapitulo("capitulo").val() != "") {
                                self.service.esUnCapituloValido(self.getInputTituloProgramadoConCapitulo("clave").val(), self.getInputTituloProgramadoConCapitulo("capitulo").val());
                            }
                        }
                );

                self.getButtonBuscarPorSegmento().button().click(
                        function (e) {
                            e.preventDefault();
                            if (self.getInputTituloProgramadoConCapitulo("capitulo") && self.getInputTituloProgramadoConCapitulo("capitulo").val() != "") {
                                if (self.service.esUnCapituloValido(self.getInputTituloProgramadoConCapitulo("clave").val(), self.getInputTituloProgramadoConCapitulo("capitulo").val())) {
                                    Component.get("html/armadoDeProgramacion/popUpListaParteSegmento.html", self.drawListadoParteSegmento);
                                } 
                            }
                        }
                );
            },
            close: function(){
                self.getTituloProgramadoConCapituloPopUp().remove();
            },
            buttons: {
                Programar: function() {
                    
                    if (Validator.isEmpty(self.getInputTituloProgramadoConCapitulo("clave"), true)) {
                        return
                    };
                    
                    if (Validator.isEmpty(self.getInputTituloProgramadoConCapitulo("capitulo"), true)) {
                        return
                    };

                    if (Validator.isEmpty(self.getInputTituloProgramadoConCapitulo("parte"), true)) {
                        return
                    };
                    
                    if (Validator.isEmpty(self.getInputTituloProgramadoConCapitulo("segmento"), true)) {
                        return
                    };

                    if (Validator.isEmpty(self.getInputTituloProgramadoConCapitulo("fraccion"), true)) {
                        return
                    };
                    
                    if (Validator.isEmpty(self.getInputTituloProgramadoConCapitulo("fraccionTotal"), true)) {
                        return
                    };
                    
                    if (Validator.isEmpty(self.getInputTituloProgramadoConCapitulo("orden"), true)) {
                        return
                    };
                    
                    if (Validator.isEmpty(self.getInputTituloProgramadoConCapitulo("fechaExhibicion"), true)) {
                        return
                    };
                    
                    if (Validator.esUnaFechaPasada(self.getInputTituloProgramadoConCapitulo("fechaExhibicion"), true, "Fecha Exhibicion")) {
                        return
                    };

                    if (!Datepicker.isAValidateDate(self.getInputTituloProgramadoConCapitulo("fechaExhibicion").val())) {
                        MESSAGE.alert("Fecha de Exhibicion invalida");
                        self.getInputTituloProgramadoConCapitulo("fechaExhibicion").effect("highlight", {color:'yellow'}, 1500);
                        return
                    };
                    
                    if (self.getInputTituloProgramadoConCapitulo("fraccion").val()
                            > self.getInputTituloProgramadoConCapitulo("fraccionTotal").val()) {
                        MESSAGE.alert("El campo fraccion total no puede ser mayor que el campo fraccion");
                        self.getInputTituloProgramadoConCapitulo("fraccion")
                                    .effect("highlight", {color:'yellow'}, 1500);
                        self.getInputTituloProgramadoConCapitulo("fraccionTotal")
                                    .effect("highlight", {color:'yellow'}, 1500);
                    } else {
                        var titulo = 
                               {senial: self.idSenial,
                                codigoDelPrograma: self.codigoDelPrograma, 
                                fecha: self.getInputTituloProgramadoConCapitulo("fechaExhibicion").val(), 
                                clave: self.getInputTituloProgramadoConCapitulo("clave").val(),
                                capitulo: self.getInputTituloProgramadoConCapitulo("capitulo").val(),
                                parte: self.getInputTituloProgramadoConCapitulo("parte").val(),
                                segmento: self.getInputTituloProgramadoConCapitulo("segmento").val(),
                                fraccion: self.getInputTituloProgramadoConCapitulo("fraccion").val(), 
                                totalDeFracciones: self.getInputTituloProgramadoConCapitulo("fraccionTotal").val(), 
                                ordenSalida: self.getInputTituloProgramadoConCapitulo("orden").val(), 
                                familiaTitulo: "CC"};
                        
                        if (self.service.validadorABM("validarAltaProgramacion", titulo)) {
                            self.getInputTituloProgramadoConCapitulo("clave").val("");

                            self.getInputTituloProgramadoConCapitulo("capitulo").numeric();
                            self.getInputTituloProgramadoConCapitulo("capitulo").val("");
                            
                            self.getInputTituloProgramadoConCapitulo("parte").numeric();
                            self.getInputTituloProgramadoConCapitulo("parte").val(0);
                            
                            self.getInputTituloProgramadoConCapitulo("segmento").numeric();
                            self.getInputTituloProgramadoConCapitulo("segmento").val(0);
                            
                            self.getInputTituloProgramadoConCapitulo("fraccion").numeric();
                            self.getInputTituloProgramadoConCapitulo("fraccion").val(1);
                            
                            self.getInputTituloProgramadoConCapitulo("fraccionTotal").numeric();
                            self.getInputTituloProgramadoConCapitulo("fraccionTotal").val(1);
                            
                            self.getInputTituloProgramadoConCapitulo("orden").numeric();
                            self.getInputTituloProgramadoConCapitulo("orden").val(1);
                            
                            if (armadoDeProgramacionEvent.workingFromConfirmacionDeProgramacion) {
                                armadoDeProgramacionEvent.getInputTituloProgramadoConCapitulo("fechaExhibicion").val(confirmarProgramacionEvent.getSelector("fecha").val());
                            } else {
                                armadoDeProgramacionEvent.getInputTituloProgramadoConCapitulo("fechaExhibicion").val("");
                            }
                        }
                    }
                },
                Salir: function() {
                    $(this).dialog("close");
                }
            }
        });
    } else {
        self.getTituloProgramadoConCapituloPopUp().dialog("open");
    }
};
ArmadoDeProgramacionEvent.prototype.drawSituarClavePorTitulo = function(comp) {
    var self = armadoDeProgramacionEvent;
    
    if (self.getSituarPorTituloCastellanoPopUp().length == 0) {
        $("#viewContainer").append(comp.replace(/{{id}}/g, self.div.id));
        self.getSituarPorTituloCastellanoPopUp().dialog({
            title : 'SITUAR CLAVE por Titulo',
            width: 600,
            show: 'blind',
            hide: 'blind',
            modal: true,
            autoOpen: true,
            open: function() {
            },
            buttons: {
                "Salir": function() {
                    $(this).dialog("close");
                }
            }
        });
        
        self.getButtonBuscarPorTitulo().button().click(
                function () {
                    self.service.obtenerListaTitulosProgramar(
                            {idSenial:self.idSenial, 
                             tituloCastellano: self.getInputSituarPorTituloCastellanoBusquedaPorTitulo().val().toUpperCase(), 
                             familiaTitulo: self.tituloQueDeseaProgramar});
                }
        );
        
        self.getGrillaDeTitulosAProgramar().jqGrid({
            height: 'auto',
            autowidth: true,
            datatype: 'local',
            colNames:['Clave','Titulo'],
            colModel:[ 
                      {name:'clave',index:'clave', width:15, aling:'center', sortable:false},
                      {name:'tituloCastellano',index:'tituloCastellano', width:75, aling:'center', sortable:false}
                      ],
                      rowNum: 20,
                      rowList:[10,20,30],
                      scrollOffset: 0,
                      pager: '#ArmadoDeProgramacionEventId_pagerGrillaDeTitulosAProgramar',
                      viewrecords: true, 
                      loadonce: true,
                      editurl: 'clientArray', 
                      caption: 'Titulos A Programar',
                      ondblClickRow: function(id){
                          var clave = self.getGrillaDeTitulosAProgramar().jqGrid('getRowData', id).clave;
                          var tituloCastellano = self.getGrillaDeTitulosAProgramar().jqGrid('getRowData', id).tituloCastellano;
                          
                          if (self.tituloQueDeseaProgramar && self.tituloQueDeseaProgramar === "CC") {
                              self.getInputTituloProgramadoConCapitulo("clave").val(clave);
                          } else if (self.tituloQueDeseaProgramar && self.tituloQueDeseaProgramar === "SC") {
                              self.getInputTituloProgramadoSinCapitulo("clave").val(clave);
                          }
                          self.getInputTituloProgramadoConCapitulo("capitulo").removeAttr('disabled');

                          self.getSituarPorTituloCastellanoPopUp().dialog("close");
                          return;
                      },
                      gridComplete: function() {
                          return;
                      }
        });
    } else {
        self.getSituarPorTituloCastellanoPopUp().dialog("open");
        self.getGrillaDeTitulosAProgramar().jqGrid("clearGridData");
        self.getInputSituarPorTituloCastellanoBusquedaPorTitulo().val("");
    }
    
};
ArmadoDeProgramacionEvent.prototype.drawListadoCapitulos = function(comp) {
    var self = armadoDeProgramacionEvent;

    if (self.getListadoCapitulosPopUp().length == 0) {
        $("#viewContainer").append(comp.replace(/{{id}}/g, self.div.id));
        
        self.getListadoCapitulosPopUp().dialog({
            title : 'Listado de Capitulos',
            width: 600,
            show: 'blind',
            hide: 'blind',
            modal: true,
            autoOpen: true,
            open: function() {
                    self.service.obtenerListadoCapitulos(
                            {claveTitulo:self.getInputTituloProgramadoConCapitulo("clave").val()});
            },
            buttons: {
                "Salir": function() {
                    $(this).dialog( "close" );
                }
            },
            close: 
                function() {
                    self.getListadoCapitulosPopUp().remove();
                }
             
        });
    
        
        self.getGrillaDeCapitulos().jqGrid({
            height: 'auto',
            autowidth: true,
            datatype: 'local',
            colNames:['Numero','Descripcion'],
              colModel:[ 
                   {name:'numero',index:'numero', width:15, aling:'center', sortable:false},
                   {name:'descripcion',index:'descripcion', width:75, aling:'center', sortable:false}
              ],
              rowNum: 20,
              rowList:[10,20,30],
              scrollOffset: 0,
              pager: '#ArmadoDeProgramacionEventId_pagerGrillaDeCapitulos',
              viewrecords: true, 
              loadonce: true,
              editurl: 'clientArray', 
              caption: 'Capitulos',
              ondblClickRow: function(id){
                    var numero = self.getGrillaDeCapitulos().jqGrid('getRowData', id).numero;
                    self.getInputTituloProgramadoConCapitulo("capitulo").val(numero);
                    self.getInputTituloProgramadoConCapitulo("parte").removeAttr('disabled');
                    self.getInputTituloProgramadoConCapitulo("segmento").removeAttr('disabled');
                    self.getInputTituloProgramadoConCapitulo("parte").val(0);
                    self.getInputTituloProgramadoConCapitulo("segmento").val(0);

                    self.getListadoCapitulosPopUp().dialog("close");
                    return;
               },
              gridComplete: function() {
                  return;
              }
        });
    } else {
        self.getListadoCapitulosPopUp().dialog("open");
    }
};
ArmadoDeProgramacionEvent.prototype.drawListadoParteSegmento = function(comp) {
    
    var self = armadoDeProgramacionEvent;
    
    if (self.getListadoParteSegmentoPopUp().length == 0) {
        $("#viewContainer").append(comp.replace(/{{id}}/g, self.div.id));
        
        self.getListadoParteSegmentoPopUp().dialog({
            title : 'Listado de Parte - Segmento',
            width: 600,
            show: 'blind',
            hide: 'blind',
            modal: true,
            autoOpen: true,
            open: function() {
                    self.service.obtenerListadoParteSegmento(
                            {claveTitulo:self.getInputTituloProgramadoConCapitulo("clave").val(),
                             nroCapitulo:self.getInputTituloProgramadoConCapitulo("capitulo").val()});
            },
            buttons: {
                "Salir": function() {
                    $(this).dialog( "close" );
                }
            },
            close: 
                function() {
                    self.getListadoParteSegmentoPopUp().remove();
                }
        });
    
        
        self.getGrillaDeParteSegmento().jqGrid({
            height: 'auto',
            autowidth: true,
            datatype: 'local',
            colNames:['Parte','Segmento'],
              colModel:[ 
                   {name:'parte',index:'parte', width:15, aling:'center', sortable:false},
                   {name:'segmento',index:'segmento', width:75, aling:'center', sortable:false}
              ],
              rowNum: 20,
              rowList:[10,20,30],
              scrollOffset: 0,
              pager: '#ArmadoDeProgramacionEventId_pagerGrillaDeParteSegmento',
              viewrecords: true, 
              loadonce: true,
              editurl: 'clientArray', 
              caption: 'Capitulos',
              ondblClickRow: function(id){
                    var parte = self.getGrillaDeParteSegmento().jqGrid('getRowData', id).parte;
                    self.getInputTituloProgramadoConCapitulo("parte").val(parte);
                    var segmento = self.getGrillaDeParteSegmento().jqGrid('getRowData', id).segmento;
                    self.getInputTituloProgramadoConCapitulo("segmento").val(segmento);
                    self.getListadoParteSegmentoPopUp().dialog("close");
                    return;
               },
              gridComplete: function() {
                  return;
              }
        });
    } else {
        self.getListadoParteSegmentoPopUp().dialog("open");
    }
};
ArmadoDeProgramacionEvent.prototype.drawConfirmacionFinal = function(comp) {
    var self = armadoDeProgramacionEvent;
    
    if (self.getConfirmacionFinalPopUp().length == 0) {
        $("#viewContainer").append(comp.replace(/{{id}}/g, self.div.id));
        
        self.getConfirmacionFinalPopUp().dialog({
            title : 'CONFIRMACION FINAL',
            width: 600,
            show: 'blind',
            hide: 'blind',
            modal: true,
            autoOpen: false,
            open: function() {
                self.getDivErroresYAdvertenciasABM().empty();
                self.service.obtenerDatosTitulo(self.tituloProgramadoSeleccionado);
                if (self.erroresYadvertenciasABM && self.erroresYadvertenciasABM.length > 0) {
                    $(".ui-dialog-buttonpane button:contains('Aceptar')").button("disable");
                    var activarBoton = true;
                    for (var i in self.erroresYadvertenciasABM) {
                        self.getDivErroresYAdvertenciasABM().append("<div style='color: red'>"+self.erroresYadvertenciasABM[i].tipo+" - "+self.erroresYadvertenciasABM[i].descripcion+"</div>");
                        if (activarBoton && (self.erroresYadvertenciasABM[i].tipo === "E")) {
                            activarBoton = false;
                        }
                    }
                    if (activarBoton) {
                        $(".ui-dialog-buttonpane button:contains('Aceptar')").button("enable");
                    }
                } else {
                    $(".ui-dialog-buttonpane button:contains('Aceptar')").button("enable");
                }
                delete self.erroresYadvertenciasABM;
            },
            buttons: {
                "Ver Observaciones": function() {
                    self.service.obtenerObservaciones(self.tituloProgramadoSeleccionado);
                    return;
                },
                "Aceptar": function() {
                    if (self.tipoOperacion === "A") {
                        self.service.ABMProgramacion("alta", self.tituloProgramadoSeleccionado);
                    } else if (self.tipoOperacion === "B") {
                        self.service.ABMProgramacion("baja", self.tituloProgramadoSeleccionado);
                    } else if (self.tipoOperacion === "M") {
                        self.service.ABMProgramacion("modifica", self.tituloProgramadoSeleccionado);
                    }
                    $( this ).dialog( "close" );
                },
                "Cancelar": function () {
                    $(this).dialog( "close" );
                    $("#confimacionFinalObservacion").remove();
                }
            }
        });
    }
    self.getConfirmacionFinalPopUp().dialog("open");
};


/** ***** POPULADORES ***** **/
ArmadoDeProgramacionEvent.prototype.populatePopUpConfirmacionFinal = function (titulo) {
    this.getInputConfirmacionFinal("clave").val(titulo.clave).prop('disabled', true);
    this.getInputConfirmacionFinal("capitulo").val(titulo.nroCapitulo).prop('disabled', true);
    this.getInputConfirmacionFinal("parte").val(titulo.parte).prop('disabled', true);
    this.getInputConfirmacionFinal("segmento").val(titulo.segmento).prop('disabled', true);
    this.getInputConfirmacionFinal("tituloOrig").val(titulo.tituloOriginal).prop('disabled', true);
    this.getInputConfirmacionFinal("tituloParaProgramar").val(titulo.tituloOFF).prop('disabled', true);
    this.getInputConfirmacionFinal("interpretes").val(titulo.interpretes).prop('disabled', true);
    this.getInputConfirmacionFinal("descripcion").val(titulo.descripcionCapitulo).prop('disabled', true);
    return;
};
ArmadoDeProgramacionEvent.prototype.populatePopUpEliminarTituloProgramado = function (titulo) {
    armadoDeProgramacionEvent.getInputEliminarTituloProgramado("clave").val(titulo.clave).prop('disabled', true);
    armadoDeProgramacionEvent.getInputEliminarTituloProgramado("capitulo").val(titulo.capitulo).prop('disabled', true);
    armadoDeProgramacionEvent.getInputEliminarTituloProgramado("parte").val(titulo.parte).prop('disabled', true);
    armadoDeProgramacionEvent.getInputEliminarTituloProgramado("segmento").val(titulo.segmento).prop('disabled', true);
    
    if (!esTituloConCapitulos(titulo.clave.substring(0,2))) {
        armadoDeProgramacionEvent.getInputEliminarTituloProgramado("capitulo").hide();
        $("label[for='"+armadoDeProgramacionEvent.getInputEliminarTituloProgramado("capitulo").attr('id')+"']").hide();

        armadoDeProgramacionEvent.getInputEliminarTituloProgramado("parte").hide();
        $("label[for='"+armadoDeProgramacionEvent.getInputEliminarTituloProgramado("parte").attr('id')+"']").hide();

        armadoDeProgramacionEvent.getInputEliminarTituloProgramado("segmento").hide();
        $("label[for='"+armadoDeProgramacionEvent.getInputEliminarTituloProgramado("segmento").attr('id')+"']").hide();
    }

    armadoDeProgramacionEvent.getInputEliminarTituloProgramado("fechaExhibicion").val(titulo.fecha).prop('disabled', true);
    armadoDeProgramacionEvent.getInputEliminarTituloProgramado("fraccion").val(titulo.fraccion).prop('disabled', true);
    armadoDeProgramacionEvent.getInputEliminarTituloProgramado("de").val(titulo.totalDeFracciones).prop('disabled', true);
    armadoDeProgramacionEvent.getInputEliminarTituloProgramado("orden").val(titulo.ordenSalida).prop('disabled', true);
    return;
};

ArmadoDeProgramacionEvent.prototype.populatePopUpModificarTituloProgramado = function (titulo) {
    this.getInputModificarTituloProgramado("clave").val(titulo.clave).prop('disabled', true);
    this.getInputModificarTituloProgramado("capitulo").val(titulo.capitulo).prop('disabled', true);
    this.getInputModificarTituloProgramado("parte").val(titulo.parte).prop('disabled', true);
    this.getInputModificarTituloProgramado("segmento").val(titulo.segmento).prop('disabled', true);
    
    if (!esTituloConCapitulos(titulo.clave.substring(0,2))) {
        armadoDeProgramacionEvent.getInputModificarTituloProgramado("capitulo").hide();
        $("label[for='"+armadoDeProgramacionEvent.getInputModificarTituloProgramado("capitulo").attr('id')+"']").hide();

        armadoDeProgramacionEvent.getInputModificarTituloProgramado("parte").hide();
        $("label[for='"+armadoDeProgramacionEvent.getInputModificarTituloProgramado("parte").attr('id')+"']").hide();

        armadoDeProgramacionEvent.getInputModificarTituloProgramado("segmento").hide();
        $("label[for='"+armadoDeProgramacionEvent.getInputModificarTituloProgramado("segmento").attr('id')+"']").hide();
    }
    
    this.getInputModificarTituloProgramado("fechaExhibicion")
    .datepicker({showOn: "button", buttonImage : "img/calendar.gif", buttonImageOnly : true, minDate: 0, dateFormat: 'dd-mm-yy'})
    .mask("99-99-9999");
    this.getInputModificarTituloProgramado("fechaExhibicion").datepicker('setDate', titulo.fecha.replace("/", "-").replace("/", "-"));
    this.getInputModificarTituloProgramado("fechaExhibicion").datepicker({minDate: 0, dateFormat: 'dd-mm-yy'});

    this.getInputModificarTituloProgramado("fraccion").val(titulo.fraccion);
    this.getInputModificarTituloProgramado("de").val(titulo.totalDeFracciones);
    this.getInputModificarTituloProgramado("orden").val(titulo.ordenSalida);
    return;
};
ArmadoDeProgramacionEvent.prototype.populateGrillaDeTitulosAProgramar = function (titulos) {
    var self = armadoDeProgramacionEvent;
    self.getGrillaDeTitulosAProgramar().clearGridData().setGridParam({data: titulos}).trigger('reloadGrid');
};
ArmadoDeProgramacionEvent.prototype.populateGrillaDeCapitulos = function (capitulos) {
    var self = armadoDeProgramacionEvent;
    self.getGrillaDeCapitulos().clearGridData().setGridParam({data: capitulos}).trigger('reloadGrid');
};
ArmadoDeProgramacionEvent.prototype.populateGrillaDeParteSegmento = function (parteSegmento) {
    var self = armadoDeProgramacionEvent;
    self.getGrillaDeParteSegmento().clearGridData().setGridParam({data: parteSegmento}).trigger('reloadGrid');
};
ArmadoDeProgramacionEvent.prototype.populateGrillaTitulosProgramados = function(titulosProgramados){
    var self = armadoDeProgramacionEvent;
    self.getGrillaTitulosProgramados().clearGridData().setGridParam({data: titulosProgramados}).trigger('reloadGrid');
};





ArmadoDeProgramacionEvent.prototype.buscarProgramas = function() {
    var self = armadoDeProgramacionEvent;
    var data = {
        codigo : self.getInputBusquedaProgramaPorCodigo().val(),
        descripcion : self.getInputBusquedaProgramaPorDescripcion().val()
    };
    var filtros = {groupOp:"OR",rules:[]};
    if (data && data.codigo) {
        filtros.rules.push({field:"codigo",op:"cn",data:data.codigo});
    };
    if (data && data.descripcion) {
        filtros.rules.push({field:"descripcion",op:"cn",data:data.descripcion.toUpperCase()});
    };
    armadoDeProgramacionEvent.getGrillaProgramas()[0].p.search = true;
    $.extend(armadoDeProgramacionEvent.getGrillaProgramas()[0].p.postData,{filters:JSON.stringify(filtros)});
    armadoDeProgramacionEvent.getGrillaProgramas().trigger("reloadGrid");
};


/** GETTERS **/
ArmadoDeProgramacionEvent.prototype.getRowData = function(grid, rowId) {
    var rowData = grid.jqGrid('getRowData', rowId);
    return rowData;
};
ArmadoDeProgramacionEvent.prototype.getConfirmacionFinalPopUp = function() {
    return $("#" + this.div.id + "_confirmacionFinalPopUp");
};
ArmadoDeProgramacionEvent.prototype.getTituloProgramadoConCapituloPopUp = function() {
    return $("#" + this.div.id + "_tituloProgramadoConCapitulo");
};
ArmadoDeProgramacionEvent.prototype.getTituloProgramadoSinCapituloPopUp = function() {
    return $("#" + this.div.id + "_tituloProgramadoSinCapitulo");
};
ArmadoDeProgramacionEvent.prototype.getSituarPorTituloCastellanoPopUp = function() {
    return $("#" + this.div.id + "_popupSituarPorTituloCastellano");
};
ArmadoDeProgramacionEvent.prototype.getListadoCapitulosPopUp = function() {
    return $("#" + this.div.id + "_popupListaCapitulos");
};
ArmadoDeProgramacionEvent.prototype.getListadoParteSegmentoPopUp = function() {
    return $("#" + this.div.id + "_popupListaParteSegmento");
};
ArmadoDeProgramacionEvent.prototype.getEliminarTituloProgramadoPopUp = function() {
    return $("#" + this.div.id + "_eliminarTituloProgramado");
};
ArmadoDeProgramacionEvent.prototype.getModificarTituloProgramadoPopUp = function() {
    return $("#" + this.div.id + "_modificarTituloProgramado");
};
ArmadoDeProgramacionEvent.prototype.getGrillaProgramas = function() {
    return $("#" + this.div.id + "_gridProgramas");
};
ArmadoDeProgramacionEvent.prototype.getGrillaTitulosProgramados = function() {
    return $("#" + this.div.id + "_grillaProgramadaContainerTable");
};
ArmadoDeProgramacionEvent.prototype.getGrillaDeTitulosAProgramar = function() {
    return $("#" + this.div.id + "_grillaDeTitulosAProgramar");
};
ArmadoDeProgramacionEvent.prototype.getGrillaDeCapitulos = function() {
    return $("#" + this.div.id + "_grillaDeCapitulos");
};
ArmadoDeProgramacionEvent.prototype.getGrillaDeParteSegmento = function() {
    return $("#" + this.div.id + "_grillaDeParteSegmento");
};
ArmadoDeProgramacionEvent.prototype.getDivListadoProgramas = function() {
    return $("#" + this.div.id + "_listadoProgramas");
};
ArmadoDeProgramacionEvent.prototype.getDivErroresYAdvertenciasABM = function() {
    return $("#" + this.div.id + "_erroresYadvertenciasABM");
};
ArmadoDeProgramacionEvent.prototype.getInputBusquedaProgramaPorCodigo = function() {
    return $("#" + this.div.id + "_busquedaProgramaPorCodigo");
};
ArmadoDeProgramacionEvent.prototype.getInputBusquedaProgramaPorDescripcion = function() {
    return $("#" + this.div.id + "_busquedaProgramaPorDescripcion");
};
ArmadoDeProgramacionEvent.prototype.getInputSituarEnFecha = function() {
    return $("#" + this.div.id + "_situarEnFechaGrillaProgramada");
};
ArmadoDeProgramacionEvent.prototype.getInputSituarEnClave = function() {
    return $("#" + this.div.id + "_situarEnClaveGrillaProgramada");
};
ArmadoDeProgramacionEvent.prototype.getInputConfirmacionFinal = function(inputId) {
    return $("#" + this.div.id + "_confirmacionFinalPopUp_" + inputId);
};
ArmadoDeProgramacionEvent.prototype.getInputTituloProgramadoSinCapitulo = function(inputId) {
    return $("#" + this.div.id + "_tituloProgramadoSinCapitulo_" + inputId);
};
ArmadoDeProgramacionEvent.prototype.getInputTituloProgramadoConCapitulo = function(inputId) {
    return $("#" + this.div.id + "_tituloProgramadoConCapitulo_" + inputId);
};
ArmadoDeProgramacionEvent.prototype.getInputSituarPorTituloCastellanoBusquedaPorTitulo = function() {
    return $("#" + this.div.id + "_popupSituarPorTituloCastellano_busquedaPorTitulo");
};
ArmadoDeProgramacionEvent.prototype.getInputEliminarTituloProgramado = function(inputId) {
    return $("#" + this.div.id + "_eliminarTituloProgramado_" + inputId);
};
ArmadoDeProgramacionEvent.prototype.getInputModificarTituloProgramado = function(inputId) {
    return $("#" + this.div.id + "_modificarTituloProgramado_" + inputId);
};
ArmadoDeProgramacionEvent.prototype.getButtonBuscarProgramas = function() {
    return $("#" + this.div.id + "_buscarProgramas");
};
ArmadoDeProgramacionEvent.prototype.getButtonProgramarConCapitulos = function() {
    return $("#" + this.div.id + "_programarConCapitulos");
};
ArmadoDeProgramacionEvent.prototype.getButtonVolverListaDeProgramas = function() {
    return $("#" + this.div.id + "_volverListaDeProgramas");
};
ArmadoDeProgramacionEvent.prototype.getButtonProgramarSinCapitulos = function() {
    return $("#" + this.div.id + "_programarSinCapitulos");
};
ArmadoDeProgramacionEvent.prototype.getButtonBuscarPorTitulo = function() {
    return $("#" + this.div.id + "_popupSituarPorTituloCastellano_buscarPorTitulo");
};
ArmadoDeProgramacionEvent.prototype.getButtonBuscarPorClave = function() {
    return $("#" + this.div.id + "_buscarClave");
};
ArmadoDeProgramacionEvent.prototype.getButtonBuscarPorCapitulo = function() {
    return $("#" + this.div.id + "_buscarCapitulo");
};
ArmadoDeProgramacionEvent.prototype.getButtonBuscarPorParte = function() {
    return $("#" + this.div.id + "_buscarParte");
};
ArmadoDeProgramacionEvent.prototype.getButtonBuscarPorSegmento = function() {
    return $("#" + this.div.id + "_buscarSegmento");
};
ArmadoDeProgramacionEvent.prototype.getAcordionListadoProgramas = function() {
    return $("#" + this.div.id + "_accordionListadoProgramas");
};
ArmadoDeProgramacionEvent.prototype.getComboDeSeniales = function() {
    return $("#" + this.div.id + "_comboDeSeniales");
};


var armadoDeProgramacionEvent = new ArmadoDeProgramacionEvent(new DivDefinition('ArmadoDeProgramacionEventId'));