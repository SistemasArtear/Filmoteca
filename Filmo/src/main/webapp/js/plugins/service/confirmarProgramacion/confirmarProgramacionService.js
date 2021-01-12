/**
 * FP340 - Confirmar programación
 * @author cetorres
 */
function ConfirmarProgramacionService() {
};

ConfirmarProgramacionService.prototype.listarProgramacion = function(data) {
	BLOCK.showBlock("Buscando programación...");

	$.ajax({
		type : 'GET',
		url : "listarProgramacionConfirmar.action",
		data : Component.serialize(data, "listarProgramacionRequest"),
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			confirmarProgramacionEvent.fillGrid(response.programasGrilla);
		}
	});
};

ConfirmarProgramacionService.prototype.listarContratos = function(data) {
	BLOCK.showBlock("Buscando contratos...");

	$.ajax({
		type : 'GET',
		url : "buscarContratosParaTitulo.action",
		data : Component.serialize(data, "buscarContratosRequest"),
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			confirmarProgramacionEvent.fillGridContratos(response.buscarContratosResponse);
		}
	});
};

ConfirmarProgramacionService.prototype.procesarConfirmacion = function(data) {
	BLOCK.showBlock("Procesando...");

	$.ajax({
		type : 'POST',
		url : "procesarConfirmacion.action",
		data : Component.serialize(data, "procesarConfirmacionRequest"),
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.procesarConfirmacionResponse.mensaje && response.procesarConfirmacionResponse.idReporte == null) {
				MESSAGE.alert(response.procesarConfirmacionResponse.mensaje);
			}else{
				if (response.procesarConfirmacionResponse.mensaje && response.procesarConfirmacionResponse.idReporte) {
					MESSAGE.alert(response.procesarConfirmacionResponse.mensaje + "<br>" + "Se ha emitido un listado con advertencias. Se ha generado el Listado con éxito");
				}else{
					if (response.procesarConfirmacionResponse.mensaje == null && response.procesarConfirmacionResponse.idReporte) {
//						MESSAGE.alert("Se ha emitido un listado con advertencias", 1000000);
						MESSAGE.alert("Se ha emitido un listado con advertencias. Se ha generado el Listado con éxito");
						//confirmarProgramacionEvent.generarListadoENC(response.procesarConfirmacionResponse.idReporte); 
					} else {
						MESSAGE.ok("Se ha confirmado el título con éxito");
					}
				}
			}
			confirmarProgramacionEvent.buscarProgramacion();
		}
	});
};



ConfirmarProgramacionService.prototype.procesarConfirmacionGrillaProgramacion = function(data) {
	BLOCK.showBlock("Procesando...");

	$.ajax({
		type : 'POST',
		url : "procesarConfirmacionGrillaProgramacion.action",
		data : JSON.stringify(data),
		contentType : "application/json",
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.procesarConfirmacionResponse.tipo) {
				if (response.procesarConfirmacionResponse.mensaje != null) {
					MESSAGE.alert(response.procesarConfirmacionResponse.mensaje);
				}
				else {
					if (response.procesarConfirmacionResponse.tipo == "W"){
						MESSAGE.alert("Se genero una lista de advertencias.");
					}
					else {
						MESSAGE.alert("Se genero una lista de errores.");
					}
					
				}
				
			} else {
				MESSAGE.ok("Se ha confirmado los títulos con éxito");
			}
			confirmarProgramacionEvent.buscarProgramacion();
		}
	});
};