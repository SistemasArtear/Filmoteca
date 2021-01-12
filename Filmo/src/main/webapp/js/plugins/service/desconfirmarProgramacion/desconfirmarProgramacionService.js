/**
 * FP350 - Desconfirmar programación
 * @author cetorres
 */
function DesconfirmarProgramacionService() {
};

DesconfirmarProgramacionService.prototype.listarProrgamacion = function(data) {
	BLOCK.showBlock("Buscando programación...");

	$.ajax({
		type : 'GET',
		url : "listarProgramacionDesconfirmar.action",
		data : Component.serialize(data, "listarProgramacionRequest"),
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			desconfirmarProgramacionEvent.fillGrid(response.programasGrilla);
		}
	});
};

DesconfirmarProgramacionService.prototype.listadoLibreria = function(data) {
	BLOCK.showBlock("Generando Listado Exhibiciones en Librerias...");

	$.ajax({
		type : 'POST',
		url : "generarListadoExhibicionesLibrerias.action",
		data : Component.serialize(data, "procesarDesconfirmacionRequest"),
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}	
			MESSAGE.ok("Se ha generado el Listado con éxito");
			
		}
	});
};

DesconfirmarProgramacionService.prototype.procesarDesconfirmacion = function(data, ejecucionFinal) {
	BLOCK.showBlock("Procesando...");

	$.ajax({
		type : 'POST',
		url : "procesarDesconfirmacion.action",
		data : Component.serialize(data, "procesarDesconfirmacionRequest"),
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (!ejecucionFinal) {
				if (response.procesarDesconfirmacionResponse.tipoRespuesta == 'W') {
					desconfirmarProgramacionEvent.listadoLibreria(data);
					desconfirmarProgramacionEvent.abrirAdvertenciaPopupDesconfirmar(data, response.procesarDesconfirmacionResponse.respuesta);
				} else if (response.procesarDesconfirmacionResponse.tipoRespuesta == 'E'){
					desconfirmarProgramacionEvent.listadoLibreria(data);
					MESSAGE.alert(response.procesarDesconfirmacionResponse.respuesta);
				} else {
					MESSAGE.ok("Se ha desconfirmado el título con éxito");
					desconfirmarProgramacionEvent.buscarProgramacion();
				}
			} else {
				MESSAGE.ok("Se ha desconfirmado el título con éxito");
				desconfirmarProgramacionEvent.buscarProgramacion();
			}
		}
	});
};