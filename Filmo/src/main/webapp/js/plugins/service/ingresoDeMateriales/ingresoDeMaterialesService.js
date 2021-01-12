function IngresoDeMaterialesService() {
};

IngresoDeMaterialesService.prototype.getDatosDistribuidor = function(data) {
	var result = false;
	$.ajax({
		async: false,
		type : 'GET',
		url : "buscarDistribuidoresParaMateriales.action",
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.distribuidores && response.distribuidores.length == 1) {
				ingresoDeMaterialesEvent.getInputCodigoDistribuidor().val(response.distribuidores[0].codigo);
				ingresoDeMaterialesEvent.getInputRazonSocialDistribuidor().val(response.distribuidores[0].razonSocial);
				result = false; 
			} else {
				Validator.focus(ingresoDeMaterialesEvent.getInputRazonSocialDistribuidor(), 'El código o razón social ingresados no existen.');
				result = true; 
			}
		}
	});
	return result;
};

IngresoDeMaterialesService.prototype.getTitulosPorDescripcion = function(data, tipoBusqueda) {
	BLOCK.showBlock("Buscando títulos...");
	var url = tipoBusqueda === IngresoDeMaterialesEvent.CASTELLANO ? "buscarTitulosCastellanoParaMateriales.action" : "buscarTitulosOriginalParaMateriales.action";
	$.ajax({
		type : 'GET',
		url : url,
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.titulosMateriales) {
				ingresoDeMaterialesEvent.fillGridTitulos(response.titulosMateriales);
			}
		}
	});
};

IngresoDeMaterialesService.prototype.determinarFamiliaTitulo = function(data) {
	BLOCK.showBlock("Abriendo...");
	$.ajax({
		type : 'GET',
		url : "determinarFamiliaTitulo.action",
		data : data,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (response.familiaTitulo == "FP021017") {
				ingresoDeMaterialesEvent.drawFormularioCargaConCapitulos();
			} else {
				ingresoDeMaterialesEvent.drawFormularioCargaSinCapitulos();
			}
		}
	});
};

IngresoDeMaterialesService.prototype.validarSoporte = function(data) {
	var isValid = false;
	$.ajax({
		async: false,
		type : 'GET',
		url : "buscarSoportesParaMateriales.action",
		data : data,
		success : function(response) {
			if (response.message) {
				MESSAGE.error(response.message);
				isValid = false;
				return;
			}
			if (response.soportes && response.soportes.length == 1) {
				ingresoDeMaterialesEvent.getInputSoporteCC().val(response.soportes[0].codigo);
				isValid = true;
			}
		}
	});
	return isValid;
};


IngresoDeMaterialesService.prototype.altaSoporteTitulo = function(data, conCapitulos) {
    BLOCK.showBlock("Guardando remito...");
    var url = "altaSoporteTitulo.action";
    $.ajax({
        type : 'POST',
        url : url,
        async: false,
        data : Component.serialize(data.soporteTitulo, "soporteTitulo"),
        success : function(response) {
        	BLOCK.hideBlock();
            if (response.message) {
                MESSAGE.error(response.message);
                return;
            }
            if (response.responseAltaRemito) {
                MESSAGE.ok("Alta de remito de título exitosa");
                if (conCapitulos) {
	                ingresoDeMaterialesEvent.agregarCapitulo(data.soporteTitulo, response.soporteTitulo.motivoIngreso);
                } else {
                	ingresoDeMaterialesEvent.volverSoporteTitulo();
                }
                ingresoDeMaterialesEvent.material.cabecera.idRemito = response.soporteTitulo.idRemito;
            } else {
                MESSAGE.error("No se pudo dar de alta el remito de título");
            }
        }
    });
};