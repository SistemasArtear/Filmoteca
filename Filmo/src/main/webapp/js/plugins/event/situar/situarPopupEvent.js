function SituarPopupEvent() {
	this.service = new SituarPopupService();
	this.nombreSituar = "Situar";
	/* Este objecto es para mostrar acciones en el row. Esta opcion deshabilita "onSelectRow"
	 * Atributos: callback, nombre, imagen
	 */
	this.acciones = [];
};
extend(SituarPopupEvent, Plugin);


SituarPopupEvent.HTML = '<div id="{{id}}_popupSituar"><label id="{{id}}_situarLabel">{{nombreSituar}}:</label><input type="text" name="descPrograma" id="{{id}}_inputBusquedaSituar"/><button id="{{id}}_buttonBusquedaSituar">Buscar</button><div id="{{id}}_funcionalidadAdicionalContainer"></div><br><table id="{{id}}_gridSituar"></table><div id="{{id}}_pagerGridSituar"></div></div>';

SituarPopupEvent.prototype.create = function(action, callback, data, responseObject, hideSituarOptions, multiselect) {
    var self = this;
	if (!action) {
		//console.log("Falta el action para el situar");
		return;
	}
	this.div = new DivDefinition(action.split(".")[0]);
	this.action = action;
	this.callback = callback;
	this.data = data;
	this.responseObject = responseObject;
	this.hideSituarOptions = hideSituarOptions;
	if (multiselect) {
	    this.multiselect = multiselect;
	} else {
	    this.multiselect = false;
	}
	    
	self.drawPopupSituar.call(self, SituarPopupEvent.HTML);
	
	if (data) {
		this.service.getList({data: data, action: action, responseObject: responseObject, that: this});
	};
};
SituarPopupEvent.prototype.reloadGrid = function() {
    var self = this;
    if (self.data) {
        self.service.getList({data: self.data, action: self.action, responseObject: self.responseObject, that: self});
    }
};
SituarPopupEvent.prototype.doAfterClose = function() {
    return;
};
SituarPopupEvent.prototype.drawPopupSituar = function(comp) {
	var self = this;
	$("#dialog").empty().append(comp.replace(/{{id}}/g, self.div.id).replace(/{{nombreSituar}}/g, self.nombreSituar));
	self.getButton().button().click(function() {self.buscar.call(self);});
	self.getInput().val("");
	self.getInput().keypress(function (event) {
		if (event.which == 13) {
			event.preventDefault();
			self.buscar();
		}
	});
	self.getPopup().dialog({
		title : self.nombreSituar,
		width: 600,
		show: 'blind',
		hide: 'blind',
		modal: true,
		autoOpen: false,
		buttons: {
			"Salir": function() {
				$( this ).dialog( "close" );
			}
		},
		close: function(event, ui) {
		    self.doAfterClose();
			self.getDiv().remove();
			$( this ).remove();
    	}
	});
	if (self.hideSituarOptions) {
	    self.hideSearchOptions();
	}
	self.doAfterDraw();
};
SituarPopupEvent.prototype.reset = function() {
    var self = this;
    self.colNames = undefined;
    self.colModel = undefined;
    self.doAfterDraw = function() { return; };
    self.doAfterDrawGrid = function() { return; };
    self.doAfterClose = function() { return; };
    self.nombreSituar = "Situar";
    self.acciones = [];
};
SituarPopupEvent.prototype.hideSearchOptions = function() {
    var self = this;
    self.getButton().hide();
    self.getInput().hide();
    self.getLabel().hide();
};
SituarPopupEvent.prototype.showSearchOptions = function() {
    var self = this;
    self.getButton().show();
    self.getInput().show();
    self.getLabel().show();
};
SituarPopupEvent.prototype.doAfterDraw = function() {
    this.getInput().focus();
    return;
};

SituarPopupEvent.prototype.doAfterDrawGrid = function() {
    return;
};
SituarPopupEvent.prototype.buscar = function() {
    var self = this;
	var	value = self.getInput().val();
	for(var key in self.data) {
	    if(self.data.hasOwnProperty(key)) {
	    	self.data[key] = value;
	        break;
	    }
	}
	self.service.getList({data: self.data, action: self.action, responseObject: self.responseObject, that: this});
};
SituarPopupEvent.prototype.setColumns = function(colNames, colModel) {
    var self = this;
    self.colNames = colNames;
    self.colModel = colModel;
    return;
};
SituarPopupEvent.prototype.dataResponse = function(data) {
    var self = this;
	if (!self.getPopup().dialog("isOpen")) {
		self.getPopup().dialog("open");
	}
	if (data && data.length !== undefined && data.length > 0) {
	    if ((self.colNames === undefined) && (self.colModel === undefined)) {
    		var colNames = [];
    		var colModel = [];
    		for (var key in data[0]) {
    			var nombreColumna = key.charAt(0).toUpperCase() + key.substr(1);
    			nombreColumna = nombreColumna.split(/(?=[A-Z])/).join(" ");
    			colNames.push(nombreColumna);
    			colModel.push({name: key, index: key});
    		}
    		for ( var i in self.acciones) {
    			var accion = self.acciones[i];
    			colNames.push(accion.nombre);
    			colModel.push({name: accion.nombre, index: accion.nombre});
			}
    		self.drawGrig.call(self, data, colNames, colModel);
	    } else {
	        for ( var i in self.acciones) {
                var accion = self.acciones[i];
                self.colNames.push(accion.nombre);
                self.colModel.push({name: accion.nombre, index: accion.nombre});
            }
            self.drawGrig.call(self, data, self.colNames, self.colModel);
	    }
	} else {
		self.getGrid().clearGridData();
	}
	self.getInput().focus();
};
SituarPopupEvent.prototype.drawGrig = function(data, colNames, colModel) {
    var self = this;
	self.getGrid().jqGrid({
		height: 'auto',
		autowidth: true,
		datatype: 'local',
		colNames:colNames,
		colModel:colModel,
		rowNum: 10,
		rowList:[10,20,30],
	   	scrollOffset: 0,
		pager: '#'+self.div.id+'_pagerGridSituar',
		viewrecords: true, 
		loadonce: true,
		multiselect: self.multiselect,
		editurl: 'clientArray', 
		caption: self.nombreSituar,
		onSelectRow: function(id){ 
			if (!self.acciones.length) {
				var row = $(this).jqGrid('getRowData', id);
				var willClose = self.callback(row);
				if (willClose === undefined) {
					self.getPopup().dialog("close");
				}
			}
		},
		gridComplete: function() {
			if (self.acciones.length) {
				var ids = $(this).jqGrid('getDataIDs');
				for (var i = 0; i < ids.length; i++) {
					var rowId = ids[i];
					for (var j in self.acciones) {
						var accion = self.acciones[j];
						var row = $(this).jqGrid('getRowData', rowId);
						var html = "";
						if (accion.habilitado !== undefined) {
						    if (row[accion.habilitado] === "S") {
						        if (accion.closePopUp) {
						            html = "<center><div style='cursor:pointer' onclick='"+self.myName()+".getPopup().dialog(\"close\"); "+self.myName()+".callCallbackAction("+rowId+","+j+")'><span class='ui-icon ui-icon-" + accion.imagen + " conTooltip'>" + accion.nombre + "</span></div></center>";
						        } else {
						            html = "<center><div style='cursor:pointer' onclick='"+self.myName()+".callCallbackAction("+rowId+","+j+")'><span class='ui-icon ui-icon-" + accion.imagen + " conTooltip'>" + accion.nombre + "</span></div></center>";
						        }
						    }
						} else {
                            html = "<center><div style='cursor:pointer' onclick='"+self.myName()+".getPopup().dialog(\"close\"); "+self.myName()+".callCallbackAction("+rowId+","+j+")'><span class='ui-icon ui-icon-" + accion.imagen + " conTooltip'>" + accion.nombre + "</span></div></center>";
						}
						var json ={};
						json[accion.nombre] = html;
						$(this).jqGrid('setRowData', rowId, json);
					}
				}
			}
	    }
	});
	self.getGrid().clearGridData().setGridParam({data: data}).trigger('reloadGrid');
    self.doAfterDrawGrid();
};

SituarPopupEvent.prototype.global = this;

SituarPopupEvent.prototype.myName = function () { 
    for (var name in this.global) {
      if (this.global[name] == this) { 
        return name; 
      }
    }
};

SituarPopupEvent.prototype.callCallbackAction = function(rowId, i) {
    var self = this;
	var rowData =  self.getGrid().jqGrid('getRowData', rowId);
	self.acciones[i].callback(rowData);
};
SituarPopupEvent.prototype.getInput = function() {
	return $("#"+this.div.id+"_inputBusquedaSituar");
};
SituarPopupEvent.prototype.getFuncionalidadAdicionalContainer = function() {
    return $("#"+this.div.id+"_funcionalidadAdicionalContainer");
};
SituarPopupEvent.prototype.getLabel = function() {
    return $("#"+this.div.id+"_situarLabel");
};
SituarPopupEvent.prototype.getButton = function() {
	return $("#"+this.div.id+"_buttonBusquedaSituar");
};
SituarPopupEvent.prototype.getGrid = function() {
	return $("#"+this.div.id+"_gridSituar");
};
SituarPopupEvent.prototype.getPopup = function() {
	return $("#"+this.div.id+"_popupSituar");
};
var situarPopupEvent = new SituarPopupEvent();