var seniales;
function BasicService() {
};
BasicService.getSeniales = function() {
	$.ajax({
		type : 'GET',
		url : 'retrieveSeniales.action',
		success : function(response) {
			seniales = response.seniales;
			Component.populateSelect($("#senialDefaultUsuario"), seniales, "codigo", "descripcion", false);
			$("#senialDefaultUsuario")[0].removeChild($("#senialDefaultUsuario")[0].options[0]);
			$("#senialDefaultUsuario").change(function() {
				$("#senialDefaultUsuario").attr('disabled','disabled');
			});
			variablesSesionUsuarioEvent.setSenialDefaultUsuario();
		},
		error : function() {
			MESSAGE.error("No se pudo obtener las señales. Intente m&aacute;s tarde.");
		}
	});
};
