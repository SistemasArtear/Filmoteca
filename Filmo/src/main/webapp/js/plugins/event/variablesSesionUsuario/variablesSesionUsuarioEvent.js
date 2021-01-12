function VariablesSesionUsuarioEvent() {
	this.senialDefaultUsuario;
};

var variablesSesionUsuarioService;

/* Popup de variables de sesión */
VariablesSesionUsuarioEvent.prototype.drawDialog = function() {
	variablesSesionUsuarioService = new VariablesSesionUsuarioService();
	BasicService.getSeniales();
	
	variablesSesionUsuarioService.getUsuarioEmpresaLogueo();
	$("#buttonVariablesSesion").button().click(variablesSesionUsuarioEvent.verVariablesSesion);
	$("#buttonInicio").button().click(variablesSesionUsuarioEvent.redirectHome);
	$("#buttonCambiarSenial").button().click(function() {
		if ($(".inicio")[0] != undefined) {
			$("#senialDefaultUsuario").attr('disabled', false);
			setTimeout(function() {
				$("#senialDefaultUsuario").attr('disabled', 'disabled');
			}, 4000);
		}
	});

	$("#variablesSesionPop").dialog({
		autoOpen: false, 
		width: 800,
		modal: true, 
		position: 'top',
		closeOnEscape: true,
		resizable: false,
		buttons: {
			Cerrar: function() {
				$(this).dialog("close");
			}
		}
	});	
};

VariablesSesionUsuarioEvent.prototype.verVariablesSesion = function() {
	$("#variablesSesionPop").dialog("open");
	variablesSesionUsuarioService.getDatosSesionUsuario();
};

VariablesSesionUsuarioEvent.prototype.dameSenialSeleccionada = function() {
    var indiceOpcionElejida = $("#senialDefaultUsuario")[0].selectedIndex;
    return $("#senialDefaultUsuario")[0].options[indiceOpcionElejida].value;
};

VariablesSesionUsuarioEvent.prototype.setSenialDefaultUsuario = function() {
	var datosSesionUsuario = variablesSesionUsuarioService.retrieveDatosSesionUsuario();
	if (datosSesionUsuario) {
		if (datosSesionUsuario.senal.indexOf("C13,") != -1){
			variablesSesionUsuarioEvent.senialDefaultUsuario = "C13";
		}
		else {
			variablesSesionUsuarioEvent.senialDefaultUsuario = datosSesionUsuario.senal.substring(1,4);
		}
		$("#senialDefaultUsuario").val(variablesSesionUsuarioEvent.senialDefaultUsuario);
	}
};

VariablesSesionUsuarioEvent.prototype.redirectHome = function() {
	window.open("/Filmo", "_self", null, false);
//	window.open("/FilmoUDN4", "_self", null, false);
};

var variablesSesionUsuarioEvent = new VariablesSesionUsuarioEvent();
