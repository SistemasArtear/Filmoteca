function ModificarRatingExcedenteService(){
};

ModificarRatingExcedenteService.prototype.ejecutarBusqueda = function(data,callback){
    BLOCK.showBlock("Buscando títulos con rating/excedente...");
    var request = "tipoNroTitulo="+data["tipoNroTitulo"]+
    				"&fechaExhibicion="+data["fechaExhibicion"]+
    				"&codPrograma="+data["programa"].codigo+
    				"&descPrograma="+data["programa"].descripcion+
    				"&idSenial="+data["idSenial"];
    $.ajax({
        type : 'GET',
        url : 'buscarTitulosRatingExcedente.action',
        data : request,
        contentType : "application/json",
        success : function(response) {
            BLOCK.hideBlock();
        	callback(response);
        }
    });
};

ModificarRatingExcedenteService.prototype.modificarValorRatingExcedente = function(data,callback){
    BLOCK.showBlock("Modificando valor de rating/excedente...");
    var request = "tipoNroTitulo="+data["tipoNroTitulo"]+
    				"&fechaExhibicion="+data["fechaExhibicion"]+
    				"&codPrograma="+data["codPrograma"]+
    				"&idSenial="+data["idSenial"]+
    				"&nroCapitulo="+data["nroCapitulo"]+
    				"&tipoRatingExcedente="+data["tipoRatingExcedente"]+
    				"&valorNuevoRatingExcedente="+data["valorNuevoRatingExcedente"]+
    				"&grupo="+data["grupo"]+
    				"&contrato="+data["contrato"];
    $.ajax({
        type : 'GET',
        url : 'modificarRatingExcedente.action',
        data : request,
        contentType : "application/json",
        success : function(response) {
        	BLOCK.hideBlock();
        	
        	if(!response.responseModificarRatingExcedente.ok)
        		MESSAGE.alert(response.responseModificarRatingExcedente.mensaje,4000);
        	else if (response.responseModificarRatingExcedente.ok)
        		MESSAGE.ok(response.responseModificarRatingExcedente.mensaje,4000);
        	
        	callback(response); 
        }
    });
};