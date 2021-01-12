function ModificarContratoInit() {
};
extend(ModificarContratoInit, Plugin);

ModificarContratoInit.prototype.create = function() {
    modificarContratoInit.init();
    Component.get("html/modificarContrato/ListadoContratos.html", modificarContratoDrawer.draw);
};
ModificarContratoInit.prototype.init = function() {
    ModificarContratoStaticService.doRequest(ModificarContratoStaticService.buildRequestObject(
            {action:"dameMonedas.action", request: {}, method: "GET", responseObject: "monedas"}));
    
    ModificarContratoStaticService.doRequest(ModificarContratoStaticService.buildRequestObject(
            {action:"dameTiposDeImporte.action", request: {}, method: "GET", responseObject: "tiposDeImporte"}));
    
    ModificarContratoStaticService.doRequest(ModificarContratoStaticService.buildRequestObject(
            {action:"dameTiposDeDerecho.action", request: {}, method: "GET", responseObject: "tiposDeDerecho"}));

    ModificarContratoStaticService.doRequest(ModificarContratoStaticService.buildRequestObject(
            {action:"dameTiposDeDerechoTerritorial.action", request: {}, method: "GET", responseObject: "tiposDeDerechoTerritorial"}));

    ModificarContratoStaticService.doRequest(ModificarContratoStaticService.buildRequestObject(
            {action:"dameTiposDeTitulo.action", request: {}, method: "GET", responseObject: "tiposDeTitulo"}));
};
var modificarContratoInit = new ModificarContratoInit();