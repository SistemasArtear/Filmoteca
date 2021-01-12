function LevantarProgramasService() {
};
LevantarProgramasService.prototype.levantarListaDeProgramas = function(data, callback) {
    BLOCK.showBlock("Se estan obteniendo los datos...");
    var	request = "idSenial="+data;
    $.ajax({
        type : 'GET',
        url : 'levantarListaDeProgramas.action',
        data : request,
        cache: false,
        success : function(response) {
            BLOCK.hideBlock();
            if (response.message) {
                MESSAGE.error(response.message);
                return;
            }
            if (response.programas) {
            	callback(response.programas);
            }
        }
    });
};
LevantarProgramasService.prototype.obtenerMayorFechaProgramada = function(data, callback) {
	BLOCK.showBlock("Se estan obteniendo los datos...");
	var	request = "senial="+data.senial+"&codPrograma="+data.codigo;
	$.ajax({
		type : 'GET',
		url : 'levantarProgramasObtenerMayorFechaProgramada.action',
		data : request,
		cache: false,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.mayorFecha) {
				callback(response.mayorFecha);
			}
		}
	});
};
LevantarProgramasService.prototype.obtenerGrillaProgramada = function(data, callback) {
	BLOCK.showBlock("Se estan obteniendo los datos...");
	var	request = "senial="+data.senial+"&codPrograma="+data.codigo+"&fechaDesde="+data.fechaDesde+"&fechaHasta="+data.fechaHasta;
	$.ajax({
		type : 'GET',
		url : 'levantarProgramasObtenerGrillaProgramada.action',
		data : request,
		cache: false,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.titulos) {
				callback(response.titulos);
			}
		}
	});
};
LevantarProgramasService.prototype.validacionLevantarExhibiciones = function(data, callback) {
	BLOCK.showBlock("Se estan obteniendo los datos...");
	var	request = "senial="+data.senial+"&codPrograma="+data.codPrograma;
	for ( var i in data.titulos) {
		for ( var key in data.titulos[i]) {
			if (key !== "tituloCast") {
				request = request + "&titulos["+i+"]." + key + "=" + data.titulos[i][key];
			}
		}
	}
	$.ajax({
		type : 'GET',
		url : 'levantarProgramasValidacionLevantarExhibiciones.action',
		data : request,
		cache: false,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.validacionLevantarExhibicionesResponse) {
				callback(response.validacionLevantarExhibicionesResponse);
			}
		}
	});
};
LevantarProgramasService.prototype.levantarExhibiciones = function(data, callback) {
	BLOCK.showBlock("Se estan obteniendo los datos...");
	var	request = "senial="+data.senial+"&codPrograma="+data.codPrograma;
	for ( var i in data.titulos) {
		for ( var key in data.titulos[i]) {
			request = request + "&titulos["+i+"]." + key + "=" + data.titulos[i][key];
		}
	}
	$.ajax({
		type : 'GET',
		url : 'levantarProgramasLevantarExhibiciones.action',
		data : request,
		cache: false,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			MESSAGE.ok("Se levant&oacute; correctamente.");
			callback();
		}
	});
};
