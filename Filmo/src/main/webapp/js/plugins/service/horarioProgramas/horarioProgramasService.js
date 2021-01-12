function HorarioProgramasService() {
};

HorarioProgramasService.prototype.getProgramasPhPorCodigo = function(data) {
	BLOCK.showBlock("Se estan obteniendo los datos...");
	var request = "senial="+data.senial;
	if (data && data.codigo) {
		request = request + "&codPrograma="+data.codigo;
	}
	$.ajax({
		type : 'GET',
		url : 'buscarProgramasConHorariosPorCodigo.action',
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.programasConHorarios) {
				horarioProgramasEvent.drawGridProgramasPH(response.programasConHorarios);
			}
		}
	});
};

HorarioProgramasService.prototype.getProgramasPhPorDescripcion = function(data) {
	BLOCK.showBlock("Se estan obteniendo los datos...");
	var request = "senial="+data.senial;
	if (data && data.codigo) {
		request = request + "&codPrograma="+data.codigo;
	}
	$.ajax({
		type : 'GET',
		url : 'buscarProgramasConHorariosPorDescripcion.action',
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.programasConHorarios) {
				horarioProgramasEvent.popupSitualenProgramasPorDesc(response.programasConHorarios, false);
			}
		}
	});
};

HorarioProgramasService.prototype.getProgramasMPPorCodigo = function(data) {
	BLOCK.showBlock("Se estan obteniendo los datos...");
	var request = 'paginationRequest.offset='+data.offset+'&paginationRequest.limit='+data.limit;
	if (data.codigo) {
		request = request+"&codPrograma="+data.codigo;
	}
	$.ajax({
		type : 'GET',
		url : 'buscarMaestroProgramasPorCodigo.action',
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message || !response.programasPagination || !response.programasPagination.programas.length) {
				MESSAGE.error(response.message);
				return;
			}
			response.programasPagination.pagination.codigo = data.codigo;
			horarioProgramasEvent.drawGridMaestroProgramas(response.programasPagination);
		}
	});
};

HorarioProgramasService.prototype.getProgramasMPPorDescripcion = function() {
	BLOCK.showBlock("Se estan obteniendo los datos...");
	var data = horarioProgramasEvent.paginationMP;
	var request = 'paginationRequest.offset='+data.offset+'&paginationRequest.limit='+data.limit;
	if (data.descripcion) {
		request = request+"&descPrograma="+data.descripcion;
	}
	$.ajax({
		type : 'GET',
		url : 'buscarMaestroProgramasPorDescripcion.action',
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message || !response.programasPagination || !response.programasPagination.programas.length) {
				MESSAGE.error(response.message);
				return;
			}
			horarioProgramasEvent.popupSitualenProgramasPorDesc(response.programasPagination, true);
		}
	});
};

HorarioProgramasService.prototype.getHorariosPrograma = function(data, callback) {
	var request = "senial=" + data.senial + "&codPrograma=" + data.codPrograma;
	$.ajax({
		type : 'GET',
		url : 'obtenerHorariosPrograma.action',
		data : request,
		success : function(response) {
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.horariosPrograma) {
				callback(response.horariosPrograma);
			}
		}
	});
};

HorarioProgramasService.prototype.eliminarPrograma = function(data) {
	var request = "senial="+data.senial+"&codPrograma="+data.codPrograma;
	$.ajax({
		type : 'POST',
		url : 'eliminarHorariosDeProgramacion.action',
		data : request,
		success : function(response) {
			if (response) {
				MESSAGE.error(response);
				return;
			}
			MESSAGE.ok("El programa se elimino correctamente");
			horarioProgramasEvent.volverHorarioPH();
			horarioProgramasEvent.buscarProgramasPHPorCodigo();
		}
	});
};

HorarioProgramasService.prototype.modificarPrograma = function(data) {
	$.ajax({
		type : 'POST',
		url : 'modificarHorariosPrograma.action',
		data : data,
		success : function(response) {
			if (response) {
				MESSAGE.error(response);
				return;
			}
			MESSAGE.ok("El programa se modifico correctamente.");
			horarioProgramasEvent.volverHorarioPH();
			horarioProgramasEvent.buscarProgramasPHPorCodigo();
		}
	});
};

HorarioProgramasService.prototype.altaPrograma = function(data) {
	$.ajax({
		type : 'POST',
		url : 'altaHorariosPrograma.action',
		data : data,
		success : function(response) {
			if (response) {
				MESSAGE.error(response);
				return;
			}
			MESSAGE.ok("El programa se dio de alta correctamente.");
			horarioProgramasEvent.volverHorarioPH();
			horarioProgramasEvent.buscarProgramasPHPorCodigo();
		}
	});
};

HorarioProgramasService.prototype.validarFechaParaModificarHorarios = function(data, callback) {
	$.ajax({
		type : 'GET',
		url : 'horariosProgramacionValidarFechaParaModificarHorarios.action',
		data : data,
		success : function(response) {
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			callback(response.resultadoValidacion);
		}
	});
};

HorarioProgramasService.prototype.modificarNuevaFecha = function(data) {
	var request = data.serialize + "&nuevaFecha=" + data.nuevaFecha;
	$.ajax({
		type : 'POST',
		url : 'horariosProgramacionModificarNuevafecha.action',
		data : request,
		success : function(response) {
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			MESSAGE.ok("El programa se modifico correctamente.");
			horarioProgramasEvent.volverHorarioPH();
			horarioProgramasEvent.buscarProgramasPHPorCodigo();
		}
	});
};

HorarioProgramasService.prototype.validarFechaParaEliminarHorarios = function(data, callback) {
	$.ajax({
		type : 'GET',
		url : 'horariosProgramacionValidarFechaParaEliminarHorarios.action',
		data : data,
		success : function(response) {
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			callback(response.resultadoValidacion);
		}
	});
};

HorarioProgramasService.prototype.eliminarNuevaFecha = function(data) {
	var request = data.serialize + "&nuevaFecha=" + data.nuevaFecha;
	$.ajax({
		type : 'POST',
		url : 'horariosProgramacionEliminarNuevafecha.action',
		data : request,
		success : function(response) {
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			MESSAGE.ok("El programa se elimino correctamente.");
			horarioProgramasEvent.volverHorarioPH();
			horarioProgramasEvent.buscarProgramasPHPorCodigo();
		}
	});
};
