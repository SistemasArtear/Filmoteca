function AltaContratoService() {
};

AltaContratoService.prototype.doRequest = function (data) {
    $.ajax({
    	async: data.async,
        type : data.method,
        url : data.action,
        cache: false,
        data : data.request,
        success : function(response) {
            if (response.message) {
                MESSAGE.error(response.message);
                BLOCK.hideBlock();
                return;
            }
            if (data.responseObject) {
                data.callback(response[data.responseObject]);
            }
        }
    });
};
