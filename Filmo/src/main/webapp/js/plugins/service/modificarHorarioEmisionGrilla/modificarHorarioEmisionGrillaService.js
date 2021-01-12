function ModificarHorarioEmisionGrillaService() {
};
ModificarHorarioEmisionGrillaService.prototype.getHorarioEmisionGrilla = function(data) {
	BLOCK.showBlock("Se estan obteniendo los datos...");
	$.ajax({
		type : 'GET',
		url : 'obtenerHorarioEmisionPrograma.action',
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.horario) {
				modificarHorarioEmisionGrillaEvent.responseHorario(response.horario);
			}
		}
	});
};
ModificarHorarioEmisionGrillaService.prototype.guardarHorarioEmisionGrilla = function(data) {
	BLOCK.showBlock("Se estan guardando los datos...");
	$.ajax({
		type : 'GET',
		url : 'modificarHorarioEmisionPrograma.action',
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			MESSAGE.ok("Se guardo correctamente.");
			modificarHorarioEmisionGrillaEvent.volverAlFormInicial();
		}
	});
};