function BajaDeContratoPopulator(div) {
    this.div = div;
};
extend(BajaDeContratoPopulator, Plugin);

BajaDeContratoPopulator.prototype.populateGrillaContratosPorDistribuidor = function() {
    if (bajaDeContratoSelector.getInput("busquedaPorDistribuidor").val().length > 0) {
        bajaDeContratoSelector.getInput("busquedaContratoPorClave").val("");
        BajaContratoStaticService.doRequest(
                {action:"dameContrato.action", 
                 request: {claveDistribuidor: bajaDeContratoSelector.getInput("busquedaPorDistribuidor").val().split(" - ")[0].trim()},
                 method: "GET",
                 callback: function(data) {
                     if (data && data.length) {
                         bajaDeContratoSelector.getGrilla("grillaDeContratos").clearGridData().setGridParam({data: data}).trigger('reloadGrid');
                     } else {
                         bajaDeContratoSelector.getGrilla("grillaDeContratos").clearGridData();
                     }
                 },
                 responseObject: "contratos"});
    }
    $("#"+bajaDeContratoPopulator.div.id+"_grillaDeContratosContainer").attr("style","display: ");
};
BajaDeContratoPopulator.prototype.populateGrillaContratosPorClave = function() {
    if (bajaDeContratoSelector.getInput("busquedaContratoPorClave").val().length > 0) {
        bajaDeContratoSelector.getInput("busquedaPorDistribuidor").val("");
        BajaContratoStaticService.doRequest(
                {action:"dameContrato.action",
                 request: {claveContrato: bajaDeContratoSelector.getInput("busquedaContratoPorClave").val().trim()},
                 method: "GET",
                 callback: function(data) {
                     if (data && data.length) {
                         bajaDeContratoSelector.getGrilla("grillaDeContratos").clearGridData().setGridParam({data: data}).trigger('reloadGrid');
                     }else{
                         bajaDeContratoSelector.getGrilla("grillaDeContratos").clearGridData();
                     }
                 },
                 responseObject: "contratos"});
    }
    $("#"+bajaDeContratoPopulator.div.id+"_grillaDeContratosContainer").attr("style","display: ");
};
BajaDeContratoPopulator.prototype.populatePopUpContratoConCabecera = function(contrato) {
    if (contrato) {
        Component.disable($("#"+bajaDeContratoDrawer.div.id+"_contratoConCabeceraPopUp_cabecera :input"));
        Component.disable($("#"+bajaDeContratoDrawer.div.id+"_contratoConCabeceraPopUp_observaciones :input"));
        Component.populateSelect(bajaDeContratoSelector.getInput("moneda", "contratoConCabeceraPopUp"), bajaDeContratoBusiness.monedas, "codigo", "abreviatura");
        bajaDeContratoSelector.getInput("contrato", "contratoConCabeceraPopUp").val(contrato.contrato);
        bajaDeContratoSelector.getInput("distribuidor", "contratoConCabeceraPopUp").val(contrato.distribuidor);
        bajaDeContratoSelector.getInput("fechaContrato", "contratoConCabeceraPopUp").val(contrato.fechaContrato);
        bajaDeContratoSelector.getInput("fechaAprobacion", "contratoConCabeceraPopUp").val(contrato.fechaAprobacion);
        bajaDeContratoSelector.getInput("tipoContrato", "contratoConCabeceraPopUp").val(contrato.tipoContrato);
        bajaDeContratoSelector.getInput("montoTotal", "contratoConCabeceraPopUp").val(contrato.montoTotal);
        bajaDeContratoSelector.getInput("moneda", "contratoConCabeceraPopUp").val(contrato.moneda);
        bajaDeContratoSelector.getInput("fechaContrato", "contratoConCabeceraPopUp").datepicker({maxDate: 0, dateFormat: 'dd-mm-yy'});
        if (bajaDeContratoBusiness.contratoConCabecera.fechaContrato) {
            bajaDeContratoSelector.getInput("fechaContrato", "contratoConCabeceraPopUp").datepicker('setDate', new Date(bajaDeContratoBusiness.contratoConCabecera.fechaContrato));
        }
        bajaDeContratoSelector.getInput("fechaAprobacion", "contratoConCabeceraPopUp").datepicker({maxDate: 0, dateFormat: 'dd-mm-yy'});
        if (bajaDeContratoBusiness.contratoConCabecera.fechaAprobacion) {
            bajaDeContratoSelector.getInput("fechaAprobacion", "contratoConCabeceraPopUp").datepicker('setDate', new Date(bajaDeContratoBusiness.contratoConCabecera.fechaAprobacion));
        }
        bajaDeContratoSelector.getInput("distribuidor", "contratoConCabeceraPopUp").click(
                function() {
                    situarPopupEvent.reset();
                    situarPopupEvent.create("dameDistribuidoresPorNombre.action", 
                            function(row){
                                bajaDeContratoSelector.getInput("distribuidor", "contratoConCabeceraPopUp").val(row.codigo);
                            }, 
                            {nombreDistribuidor: ""}, 
                            "distribuidores");
                }
        );
        this.populateObservacionesDePago(contrato);
    } else {
        MESSAGE.alert("Fallo la obtencion de la cabecera del contrato");
    }
};
BajaDeContratoPopulator.prototype.populateObservacionesDePago = function(contrato) {
    BajaContratoStaticService.doRequest(
            {action:"dameObservacionesDePago.action",
                request: {claveContrato: contrato.contrato},
                method: "GET",
                callback: function(data) {
                    if (data && data.length) {
                        bajaDeContratoBusiness.contratoConCabecera.observacionesDePago = data;
                        $.each(data, function(index, value) {
                            bajaDeContratoSelector.getInput("renglon"+(index+1), "contratoConCabeceraPopUp").val(value.observacionDePago);
                        });
                    }
                },
                responseObject: "observacionesDePago"});
};
BajaDeContratoPopulator.prototype.populateGrillaSenialesEImportes = function(senialesEimportes) {
    bajaDeContratoSelector.getGrillaSenialesEImportes().clearGridData().setGridParam({data: senialesEimportes}).trigger('reloadGrid');
};
BajaDeContratoPopulator.prototype.populateGrillaDeGrupos = function(senial) {
    BajaContratoStaticService.doRequest(
            {action:"dameGrupos.action",
                request: {claveContrato: bajaDeContratoBusiness.contratoConCabecera.contrato, senial: senial},
                method: "GET",
                callback: function(data) {
                    if (data && data.length) {
                        bajaDeContratoSelector.getGrillaGrupos().clearGridData().setGridParam({data: data}).trigger('reloadGrid');
                        bajaDeContratoSelector.getGrillaGruposContainer().show();
                    }else{
                        MESSAGE.alert("No tiene grupos");
                    }
                },
                responseObject: "grupos"});
};
var bajaDeContratoPopulator = new BajaDeContratoPopulator(new DivDefinition('BajaDeContratoEventId'));