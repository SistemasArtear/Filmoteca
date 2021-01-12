function BajaDeContratoSelector(div) {
    this.div = div;
};
extend(BajaDeContratoSelector, Plugin);

/** GETTERs **/
BajaDeContratoSelector.prototype.getInput = function(elementId, popUpId) {
    if (!popUpId) {
        return $("#" + this.div.id + "_" + elementId);
    } else {
        return $("#" + this.div.id + "_" + popUpId + "_" + elementId);
    }
};
BajaDeContratoSelector.prototype.getLabel = function(elementId, popUpId) {
    return $("#" + this.div.id + "_" + popUpId + "_label_" + elementId);
};
BajaDeContratoSelector.prototype.getButton = function(id) {
    return $("#" + this.div.id + "_" + id);
};
BajaDeContratoSelector.prototype.getGrilla = function(id) {
    return $("#" + this.div.id + "_" + id);
};
BajaDeContratoSelector.prototype.getGrillaSenialesEImportes = function() {
    return $("#"+this.div.id+"_contratoConCabeceraPopUp_grillaSenialesEimportes");
};
BajaDeContratoSelector.prototype.getGrillaGrupos = function() {
    return $("#"+this.div.id+"_contratoConCabeceraPopUp_grillaGrupos");
};
BajaDeContratoSelector.prototype.getGrillaGruposContainer = function() {
    return $("#"+this.div.id+"_contratoConCabeceraPopUp_grillaGruposContainer");
};
BajaDeContratoSelector.prototype.getGrillaTitulos = function() {
    return $("#"+this.div.id+"_grupoPopUp_grillaDeTitulos");
};
BajaDeContratoSelector.prototype.getGrillaTitulosContainer = function() {
    return $("#"+this.div.id+"_grupoPopUp_grillaDeTitulosContainer");
};
BajaDeContratoSelector.prototype.getPopUp = function(id) {
    return $("#" + this.div.id + "_" + id);
};
var bajaDeContratoSelector = new BajaDeContratoSelector(new DivDefinition('BajaDeContratoEventId'));