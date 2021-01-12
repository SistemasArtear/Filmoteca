function TrabajarConFichaCinematograficaService() {
};

TrabajarConFichaCinematograficaService.prototype.getFichas = function(data, callback) {
	BLOCK.showBlock("Buscando fichas cinematográficas...");
	$.ajax({
		type : "GET",
		url : "trabajarFichaCinematograficaBuscarFichas.action",
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.fichasListado) {
				callback(response.fichasListado);
			}
		}
	});
};

TrabajarConFichaCinematograficaService.prototype.getFicha = function(idFicha, callback) {
	BLOCK.showBlock("Obteniendo ficha cinematográfica...");
	$.ajax({
		type : "GET",
		url : "trabajarFichaCinematograficaObtenerFichaCompleta.action",
		data : "idFicha="+idFicha,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.fichaCompleta) {
				callback(response.fichaCompleta);
			}
		}
	});
};

TrabajarConFichaCinematograficaService.prototype.eliminarFicha = function(idFicha, callback) {
	BLOCK.showBlock("Eliminando ficha cinematográfica...");
	$.ajax({
		type : "POST",
		url : "trabajarFichaCinematograficaEliminarFicha.action",
		data : "idFicha="+idFicha,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.ok) {
				MESSAGE.ok("La ficha se elimino correctamente.");
				callback();
			}
		}
	});
};

TrabajarConFichaCinematograficaService.prototype.guardarFicha = function(data, callback) {
	BLOCK.showBlock("Guardando ficha cinematográfica...");
	$.ajax({
		type : "POST",
		url : "trabajarFichaCinematograficaGuardarFicha.action",
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.ok) {
				MESSAGE.ok("La ficha se guardo correctamente.");
				callback();
			}
		}
	});
};

TrabajarConFichaCinematograficaService.prototype.modificarFicha = function(data, callback) {
	BLOCK.showBlock("Modificando ficha cinematográfica...");
	$.ajax({
		type : "POST",
		url : "trabajarFichaCinematograficaModificarFicha.action",
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.ok) {
				MESSAGE.ok("La ficha se modificó correctamente.");
				callback();
			}
		}
	});
};