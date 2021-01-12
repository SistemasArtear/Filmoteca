function ModificarContratoStaticService() {
};
ModificarContratoStaticService.buildRequestObject = function (data) {
    return {action: data.action, 
            request: data.request,
            method: data.method,
            responseObject: data.responseObject, 
            callback: function(resp) {
                if (resp && resp.length && resp.length > 0) {
                    modificarContratoBusiness[data.responseObject] = resp;
                } else {
                    alert("ERROR");
                }
            }};
};
ModificarContratoStaticService.doRequest = function (data) {
    BLOCK.showBlock("Se estan obteniendo los datos...");
    $.ajax(
       {
        type : data.method,
        url : data.action,
        data : data.request,
        success : 
            function(response) {
                BLOCK.hideBlock();
                if (response.message) {
                    MESSAGE.error(response.message);
                    return;
                }
                
                if (data.responseObject) {
                    data.callback(response[data.responseObject]);
                } else {
                    data.callback(response.situarResponse);
                }
            }
    });
};