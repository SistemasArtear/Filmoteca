function VariablesSesionUsuarioService() {
};

VariablesSesionUsuarioService.prototype.getUsuarioEmpresaLogueo = function() {
	$.ajax({
		type : 'GET',
		url : 'retrieveEncabezado.action',
		success : function(response) {
			$("#userEmpresa").html(response.usuarioEmpresaLogueo);
		},
		error : function() {
			MESSAGE.error("No se pudo obtener los datos de sesión del usuario. Intente más tarde.");
		}
	});
};

VariablesSesionUsuarioService.prototype.getDatosSesionUsuario = function() {
	BLOCK.showBlock("Aguarde un momento...");
	$.ajax({
		async: false,
		type : 'GET',
		url : 'retrieveDatosSesionUsuario.action',
		success : function(response) {
			var table = "<table border='0' cellpadding='4' cellspacing='4'>";
			$.each(response.datosSesionUsuario, function(key, value) {
				table +=  "<tr><td><strong>" + key + "</strong></td><td>" + value + "</td></tr>";
			});
			table += "</table>";
			$("#variablesSesionPop").empty().append(table);
			BLOCK.hideBlock();
		},
		error : function() {
			MESSAGE.error("No se pudo obtener los datos de sesión del usuario. Intente más tarde.");
			BLOCK.hideBlock();
		}
	});
};

VariablesSesionUsuarioService.prototype.retrieveDatosSesionUsuario = function() {
	var datosSesionUsuario = new Object();
	$.ajax({
		async: false,
		type : 'GET',
		url : 'retrieveDatosSesionUsuario.action',
		success : function(response) {
			datosSesionUsuario = response.datosSesionUsuario;
		},
		error : function() {
			MESSAGE.error("No se pudo obtener los datos de sesión del usuario. Intente más tarde.");
		}
	});
	return datosSesionUsuario;
};

VariablesSesionUsuarioService.prototype.getServiceProperties = function() {
	var serviceProperties = null;
	
	$.ajax({
		async: false,
		type : 'GET',
		url : 'retrieveServiceProperties.action',
		success : function(response) {
			serviceProperties = response;
		},
		error : function() {
			MESSAGE.error("No se pudo obtener los datos de sesión del usuario. Intente más tarde.");
			BLOCK.hideBlock();
		}
	});
	
	return serviceProperties;
};

