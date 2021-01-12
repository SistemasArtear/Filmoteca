function SituarPopupService() {
};
SituarPopupService.prototype.getList = function(data) {
	BLOCK.showBlock("Se estan obteniendo los datos...");
	var that = data.that;
	var request = "";
	for ( var key in data.data) {
		request = request + key + "=" + data.data[key] + "&";
	}
	request = request.substring(0, request.length-1);
	$.ajax({
		type : 'GET',
		url : data.action,
		data : request,
		success : function(response) {
			BLOCK.hideBlock();
			if (response.message) {
				MESSAGE.error(response.message);
				return;
			}
			if (data.responseObject) {
			    that.dataResponse.call(that, response[data.responseObject]);
			}else{
			    that.dataResponse.call(that, response.situarResponse);
			}
		}
	});
};