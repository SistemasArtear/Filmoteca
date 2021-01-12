function ModificarContratoSelector(div) {
    this.div = div;
};
extend(ModificarContratoSelector, Plugin);

/** GETTERs **/
ModificarContratoSelector.prototype.getInput = function(elementId, popUpId) {
    if (!popUpId) {
        return $("#" + this.div.id + "_" + elementId);
    } else {
        return $("#" + this.div.id + "_" + popUpId + "_" + elementId);
    }
};
ModificarContratoSelector.prototype.getLabel = function(elementId, popUpId) {
    return $("#" + this.div.id + "_" + popUpId + "_label_" + elementId);
};
ModificarContratoSelector.prototype.getButton = function(id) {
    return $("#" + this.div.id + "_" + id);
};
ModificarContratoSelector.prototype.getGrilla = function(id) {
    return $("#" + this.div.id + "_" + id);
};
ModificarContratoSelector.prototype.getGrillaSenialesEImportes = function() {
    return $("#"+this.div.id+"_contratoConCabeceraPopUp_grillaSenialesEimportes");
};
ModificarContratoSelector.prototype.getGrillaGrupos = function() {
    return $("#"+this.div.id+"_contratoConCabeceraPopUp_grillaGrupos");
};
ModificarContratoSelector.prototype.getGrillaGruposEliminarSenial = function() {
    return $("#grillaDeGruposEliminarSenial");
};
ModificarContratoSelector.prototype.getGrillaGruposContainer = function() {
    return $("#"+this.div.id+"_contratoConCabeceraPopUp_grillaGruposContainer");
};
ModificarContratoSelector.prototype.getGrillaTitulos = function() {
    return $("#"+this.div.id+"_grupoPopUp_grillaDeTitulos");
};
ModificarContratoSelector.prototype.getGrillaTitulosContainer = function() {
    return $("#"+this.div.id+"_grupoPopUp_grillaDeTitulosContainer");
};
ModificarContratoSelector.prototype.getPopUp = function(id) {
    return $("#" + this.div.id + "_" + id);
};
var modificarContratoSelector = new ModificarContratoSelector(new DivDefinition('ModificarContratoEventId'));