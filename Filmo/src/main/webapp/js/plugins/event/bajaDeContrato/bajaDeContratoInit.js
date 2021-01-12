function BajaDeContratoInit() {
};
extend(BajaDeContratoInit, Plugin);

BajaDeContratoInit.prototype.create = function() {
    BajaContratoStaticService.doRequest(BajaContratoStaticService.buildRequestObject(
            {action:"dameMonedas.action", request: {}, method: "GET", responseObject: "monedas"}));
    
    BajaContratoStaticService.doRequest(BajaContratoStaticService.buildRequestObject(
            {action:"dameTiposDeImporte.action", request: {}, method: "GET", responseObject: "tiposDeImporte"}));
    
    BajaContratoStaticService.doRequest(BajaContratoStaticService.buildRequestObject(
            {action:"dameTiposDeDerecho.action", request: {}, method: "GET", responseObject: "tiposDeDerecho"}));

    BajaContratoStaticService.doRequest(BajaContratoStaticService.buildRequestObject(
            {action:"dameTiposDeDerechoTerritorial.action", request: {}, method: "GET", responseObject: "tiposDeDerechoTerritorial"}));

    BajaContratoStaticService.doRequest(BajaContratoStaticService.buildRequestObject(
            {action:"dameTiposDeTitulo.action", request: {}, method: "GET", responseObject: "tiposDeTitulo"}));

    Component.get("html/bajaDeContrato/ListadoContratos.html", bajaDeContratoDrawer.draw);
};
var bajaDeContratoInit = new BajaDeContratoInit();