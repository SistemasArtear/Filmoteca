function BajaDeContratoDrawer(div) {
    this.div = div;
};
extend(BajaDeContratoDrawer, Plugin);

/** DRAWERs **/
BajaDeContratoDrawer.prototype.draw = function(comp) {
    var self = bajaDeContratoDrawer;
    $("#viewContainer").empty().append(comp.replace(/{{id}}/g, self.div.id));
    bajaDeContratoSelector.getButton("buscarContratoPorClave").button().click(bajaDeContratoPopulator.populateGrillaContratosPorClave);
    bajaDeContratoSelector.getButton("buscarContratoPorDistribuidor").button().click(bajaDeContratoPopulator.populateGrillaContratosPorDistribuidor);
    /*bajaDeContratoSelector.getButton("bajaDeContrato").button().click(function () {
        var rowId = bajaDeContratoSelector.getGrilla("grillaDeContratos").jqGrid('getGridParam', 'selrow');
        if (rowId) {
            var row = bajaDeContratoSelector.getGrilla("grillaDeContratos").jqGrid('getRowData', rowId);
            bajaDeContratoBusiness.editarContratoConCabecera(row);
        } else {
            MESSAGE.alert("Debe seleccionar un contrato");
        }
    });*/
    bajaDeContratoSelector.getInput("busquedaContratoPorClave").keypress(function (event) {
        if (event.which == 13) {
            event.preventDefault();
            bajaDeContratoPopulator.populateGrillaContratosPorClave();
            return;
        }
    });

   self.getSelector("popupBusquedaContratoDistribuidor").click(
            function() {
                bajaDeContratoSelector.getInput("busquedaContratoPorClave").val("");
                situarPopupEvent.nombreSituar = "Seleccion de Distribuidor";
                situarPopupEvent.setColumns(
                        ["Codigo","Razon Social"],
                        [{name:"codigo", index:"codigo"}, {name:"razonSocial", index:"razonSocial"}]);
                situarPopupEvent.doAfterDraw = function() {
                    return;
                };
                situarPopupEvent.doAfterDrawGrid = function() {
                    return;
                };
                situarPopupEvent.acciones = [];
                situarPopupEvent.create("dameDistribuidoresPorNombre.action", 
                        function(row){
                            bajaDeContratoSelector.getInput("busquedaPorDistribuidor").val(row.codigo+" - "+row.razonSocial);
                            bajaDeContratoSelector.getInput("busquedaContratoPorClave").val("");
                        }, 
                        {nombreDistribuidor: ""}, 
                        "distribuidores");
            }
    );
    bajaDeContratoSelector.getGrilla("grillaDeContratos").jqGrid({
        height: 'auto',
        autowidth: true,
        datatype: 'local',
        colNames:['Contrato','Distribuidor','Razon Social','Monto Total','Moneda','Fecha Contrato','Señal','Estado', ' '],
        colModel:[ 
            {name:'contrato',index:'contrato', width:100, sortable:false, align:"center"}, 
            {name:'distribuidor',index:'distribuidor', width:100, sortable:false, align:"center"},
            {name:'razonSocial',index:'razonSocial', width:500, sortable:false, align:"center"},
            {name:'montoTotal',index:'montoTotal', width:300, sortable:false, align:"center", formatter:'number'},
            {name:'moneda',index:'moneda', width:150, sortable:false, align:"center"},
            {name:'fechaContrato',index:'fechaContrato', width:300, sortable:false, formatter: 'date', formatoptions: { srcformat: 'Y/m/d', newformat: 'd/m/Y'}},
            {name:'senial',index:'senial', width:150, sortable:false, align:"center"},
            {name:'estado',index:'estado', width:100, sortable:false, align:"center"},
            {name:'DarDeBaja',index:'DarDeBaja', width:30, sortable:false, align:"center"}
        ],
        rowNum: 10,
        rowList:[10,20,30],
        scrollOffset: 0,
        pager: '#BajaDeContratoEventId_pagerGrillaDeContratos',
        viewrecords: true, 
        loadonce: true,
        editurl: 'clientArray', 
        caption: 'Contratos',
        gridComplete: function() {
        	var ids = $(this).jqGrid('getDataIDs');
        	for (var i in ids) {
        		var rowId = ids[i];
       			var debaja = "<div onclick='bajaDeContratoDrawer.darDeBajaSeleccion("+rowId+")' style='cursor:pointer' ><span class='ui-icon ui-icon-radio-on DarDeBaja conTooltip'  align:'center'>Dar de Baja</span></div>";
       			$(this).jqGrid('setRowData', rowId, { DarDeBaja: debaja });
				
        	}
	    }
    });
    
    Accordion.getInstance(self.getSelector("filtroBusquedaContratos"));
    $("#"+self.div.id+"_grillaDeContratosContainer").attr("style","display: none");
};

BajaDeContratoDrawer.prototype.darDeBajaSeleccion = function(rowId) {

	if (rowId) {
        var row = bajaDeContratoSelector.getGrilla("grillaDeContratos").jqGrid('getRowData', rowId);
        bajaDeContratoBusiness.editarContratoConCabecera(row);
    } 
};

BajaDeContratoDrawer.prototype.drawPopUpContratoConCabecera = function(comp) {
    $("#popupContainer").empty().append(comp.replace(/{{id}}/g, bajaDeContratoDrawer.div.id));
    bajaDeContratoSelector.getPopUp("contratoConCabeceraPopUp").dialog({
        title : 'Cabecera Contrato',
        width: 900,
        show: 'blind',
        hide: 'blind',
        modal: true,
        autoOpen: true,
        open: function() {
            bajaDeContratoSelector.getGrillaGruposContainer().hide();
            bajaDeContratoPopulator.populatePopUpContratoConCabecera(bajaDeContratoBusiness.contratoConCabecera);
            BajaContratoStaticService.doRequest(
                    {action:"dameSenialImporte.action",
                        request: {claveContrato: bajaDeContratoBusiness.contratoConCabecera.contrato},
                        method: "GET",
                        callback: function(data) {
                            if (data && data.length && data.length > 0) {
                                bajaDeContratoBusiness.contratoConCabecera.senialesEimportes = data;
                                bajaDeContratoDrawer.drawGrillaDeSenialesEImportes();
                                bajaDeContratoPopulator.populateGrillaSenialesEImportes(bajaDeContratoBusiness.contratoConCabecera.senialesEimportes);
                            } else {
                                MESSAGE.alert("Fallo la obtencion de seniales e importes del contrato");
                            }
                        },
                        responseObject: "senialesConImportes"});
        },
        close: function() {
            $(this).dialog("destroy");
            ModificarContratoStaticService.doRequest(
                    {action:"desbloquearContrato.action",
                        request: {claveContrato: bajaDeContratoBusiness.contratoConCabecera.contrato},
                        method: "GET",
                        callback: function() {
                            MESSAGE.ok("Contrato desbloqueado");
                            bajaDeContratoSelector.getPopUp("contratoConCabeceraPopUp").remove();
                        } 
                     });

        },
        buttons: {
            "DAR DE BAJA": function() {
                var result = bajaDeContratoBusiness.validarBajaDeContrato(bajaDeContratoBusiness.contratoConCabecera.contrato,$(this) );
            },
            Cancelar: function() {
                $(this).dialog("close");
            }
        }
    });    
};
BajaDeContratoDrawer.prototype.drawGrillaDeSenialesEImportes = function() {
    bajaDeContratoSelector.getGrillaSenialesEImportes().jqGrid({
        height: 'auto',
        autowidth: true,
        datatype: 'local',
        colNames:['Codigo Senial','Descripcion','Importe','Grupos'],
        colModel:[ 
            {name:'codigoSenial',index:'codigoSenial', width:150, sortable:false}, 
            {name:'descripcionSenial',index:'descripcionSenial', width:300, sortable:false},
            {name:'importe',index:'importe', width:150, sortable:false},
            {name:'grupos',index:'grupos', width:50, sortable:false}
        ],
        rowNum: 10,
        rowList:[10,20,30],
        scrollOffset: 0,
        pager: '#BajaDeContratoEventId_contratoConCabeceraPopUp_pagerGrillaSenialesEimportes',
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
                var grupos = "<center><div onclick='bajaDeContratoBusiness.trabajarConGrupos("+rowId+")' style='cursor:pointer'><span class='ui-icon ui-icon-search visualizar conTooltip'>Visualizar</span></div></center>";
                $(this).jqGrid('setRowData', rowId, { grupos: grupos });
            }
            return;
        }
    });
};
BajaDeContratoDrawer.prototype.drawGrillaDeGrupos = function() {
    bajaDeContratoSelector.getGrillaGrupos().jqGrid({
        height: 'auto',
        width: 880,
        datatype: 'local',
        colNames:['Grupo','Tipo','Nombre','Importe','E/R'],
        colModel:[ 
            {name:'nroGrupo',index:'nroGrupo', sortable:false}, 
            {name:'tipoTitulo',index:'tipoTitulo', sortable:false},
            {name:'nombreGrupo',index:'nombreGrupo', sortable:false},
            {name:'importeGrupo',index:'importeGrupo', sortable:false, align: "right"},
            {name:'er',index:'er', sortable:false}
        ],
        rowNum: 10,
        rowList:[10,20,30],
        scrollOffset: 0,
        pager: '#BajaDeContratoEventId_contratoConCabeceraPopUp_pagerGrillaGrupos',
        viewrecords: true, 
        loadonce: true,
        editurl: 'clientArray', 
        caption: 'Grupos',
        ondblClickRow: function(id){
            return;
        }
    });
};
var bajaDeContratoDrawer = new BajaDeContratoDrawer(new DivDefinition('BajaDeContratoEventId'));