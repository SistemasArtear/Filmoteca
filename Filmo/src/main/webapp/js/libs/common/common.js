function Component(){
}
Component.get = function(url, callback){
	$.get(url, callback);
};
Component.serialize = function(obj, prefix) {
    var str = [];
    for(var p in obj) {
       if (obj[p] !== null) {
           str.push(prefix+"."+encodeURIComponent(p)+"="+encodeURIComponent(obj[p]));
       }
    }
    return str.join("&");
};
Component.populateSelect = function(comp, data, codigo, descripcion, showCode) {
    comp.empty();
    var option = $("<option />");
    option.val("");
    option.text("Seleccionar...");
    comp.append(option);
    for (var i in data) {
        var option = $("<option />");
        option.val((data[i])[codigo]);
        if (showCode === undefined || showCode === null || showCode === true) {
            option.text((data[i])[codigo] + " - " + (data[i])[descripcion]);
        } else {
            option.text((data[i])[descripcion]);
        }
        comp.append(option);
    }
};
Component.populateCheckBoxes = function(comp, data, codigo, descripcion, idCheck) {
    comp.empty();
    for (var i in data) {
        var check = $("<input id=\""+idCheck+(data[i])[codigo]+"\" type=\"checkbox\"/>");
        check.val((data[i])[codigo]);
        comp.append(check);
        check.before("<label>"+(data[i])[descripcion].trim()+"</label>");
    }
};
Component.disable = function(obj) {
    if (obj instanceof jQuery) {
        return obj.each(function() {
            if (typeof this.disabled != "undefined") {
            	this.disabled = true;
            	obj.datepicker('disable');
            }
        });
    } else {
        console.error(obj+" no es un objeto jQuery");
    }
};
Component.enable = function(obj) {
    if (obj instanceof jQuery) {
        return obj.each(function() {
            if (typeof this.disabled != "undefined") {
                this.disabled = false;
            }
        });
    } else {
        console.error(obj+" no es un objeto jQuery");
    }
};

Component.resizableGrid = function(grid) {
	$(window).bind('resize', function() {
	    /* Obtengo el ancho del contenedor principal */
	    var width = $("#viewContainer")[0].clientWidth;
	    if (width == null || width < 1){
	        /* Para IE, usar offsetWidth si fuese necesario */
	        width = $("#viewContainer")[0].offsetWidth;
	    }
	    width = width - 2;  /* Factor para prevenir el scroll horizontal */
	    /* Only resize if new width exceeds a minimal threshold.
         * Fixes IE issue with in-place resizing when mousing-over frame bars */
	    if (width > 0 && Math.abs(width - grid.width()) > 5) {
	        grid.setGridWidth(width);
	    }
	}).trigger('resize');	
};

Component.trimWhitespace = function(texto) {
	return texto.replace(/^[ \t]+|[ \t]+$/, "");
};

function Datepicker(object, date) {
	this.selector = object;
	this.date = date;
};
Datepicker.getInstance = function(object, date){
	var instance = new Datepicker(object, date);
	instance.create();
	instance.bindFullYear();
	if (date) {
		instance.setDate(date);
	}
	return instance;
};
Datepicker.prototype.create = function(){
	Datepicker.picker(this.selector);
	return this;
};
Datepicker.prototype.setDate = function(date){
	this.selector.datepicker('setDate',date);
	return this;
};
Datepicker.prototype.bindFullYear = function(){
	Datepicker.fullYearDatepicker(this.selector);
	return this;
};
Datepicker.prototype.bindPeriod = function(){
	Datepicker.periodoDatepicker(this.selector);
	return this;
};
Datepicker.picker = function(object) {
	object.datepicker({
		showOn : "button",
		dateFormat : "dd/mm/yy",
		buttonImage : "img/calendar.gif",
		buttonImageOnly : true,
		changeMonth : true,
		changeYear : true
	}).mask("99/99/9999");
};
Datepicker.fullYearDatepicker = function(datepicker) {
	datepicker.each(function() {
		datepicker.bind('blur', function() {
			/^(((0[1-9]|[12][0-9]|3[01])[-\/.](0[13578]|1[02])|(0[1-9]|[12][0-9]|30)[-\/.](0[469]|11)|(0[1-9]|1\d|2[0-8])[-\/.]02)[-\/.]\d{4}|29[-\/.]02[-\/.](\d{2}(0[48]|[2468][048]|[13579][26])|([02468][048]|[1359][26])00))$/.test(datepicker.val()) 
				? "" : datepicker.val("");
		});
	});
};
Datepicker.periodoDatepicker = function(datepicker) {
	datepicker.each(function() {
		datepicker.bind('blur', function() {
			/^(0[1-9]|1[012])[-\/.](19|20)\d\d$/.test(datepicker.val()) ? "" : datepicker.val("");
		});
	});
};
Datepicker.isAValidateDate = function (dateString) {
    try {
        var ret = false;
        if (dateString.indexOf("-") != -1) {
            $.datepicker.parseDate('dd-mm-yy', dateString);
            ret = true;
        } else if ((dateString.indexOf("/") != -1)) {
            $.datepicker.parseDate('dd/mm/yy', dateString);
            ret = true;
        }
        return ret;
    } catch (e) {
        return false;
    }
};


function BLOCK() {
}
BLOCK.showElementBlock = function(elementId, message) {
	$('#'+elementId).block({
		message : '<strong> ' + message
				+ '</strong><br/><br/><img src="img/upload_loader.gif" />',
		css : {
			border : 'none',
			padding : '15px',
			backgroundColor : '#000',
			'-webkit-border-radius' : '10px',
			'-moz-border-radius' : '10px',
			'border-radius' : '10px',
			opacity : .5,
			color : '#fff'
		},
	});
};
BLOCK.hideElementBlock = function(elementId) {
	if(elementId)  
		$('#'+elementId).unblock();	
};

BLOCK.showBlock = function(message) {
	$.blockUI({
		message : '<strong> ' + message
				+ '</strong><br/><br/><img src="img/upload_loader.gif" />',
		css : {
			border : 'none',
			padding : '15px',
			backgroundColor : '#000',
			'-webkit-border-radius' : '10px',
			'-moz-border-radius' : '10px',
			'border-radius' : '10px',
			opacity : .5,
			color : '#fff'
		},
		baseZ: 4000	
	});
};
BLOCK.hideBlock = function(message) {
	$.unblockUI();
};

function MESSAGE(){
	this.isShow = false;
	this.request_timer = false;
}

MESSAGE.error = function(mensaje, timeOut) {
	$('#globalMess').removeClass();
	$('#globalMess').addClass('mensajesErr');
	$('#tituloMess').html("ERROR");
	$('#globalMessImgClose').click(message.closeMessage);
	message.showMessage(mensaje, timeOut);
};
MESSAGE.ok = function(mensaje, timeOut) {
	$('#globalMess').removeClass();
	$('#globalMess').addClass('mensajesOk');
	$('#tituloMess').html("OK");
	$('#globalMessImgClose').click(message.closeMessage);
	message.showMessage(mensaje, timeOut);
};
MESSAGE.alert = function(mensaje, timeOut) {
	$('#globalMess').removeClass();
	$('#globalMess').addClass('mensajesAlert');
	$('#tituloMess').html("ALERTA");
	$('#globalMessImgClose').click(message.closeMessage);
	message.showMessage(mensaje, timeOut);
};

MESSAGE.prototype.showMessage = function(mensaje, timeOut){
	var messageTimeOut = timeOut? timeOut : 8000;
	$('#textoMess').html(mensaje);
	message.centrar();
	if(!message.isShow){
		$('#globalMess').slideDown('fast');
		message.isShow = true;
	}
	
	// limpio timeout's pendientes, si es que hay!!
	if (message.request_timer) {
		clearTimeout(message.request_timer);
	}
	// utilizo un timer para ocultar el mensaje en un determinado tiempo (messageTimeOut)
	message.request_timer = setTimeout(message.closeMessage, messageTimeOut);
	
	//queremos que siempre se quede centrado horizontalmente y en la linea del scroll verticalmente
	$(window).scroll(message.centrar);
	$(window).resize(message.centrar);
};
MESSAGE.prototype.centrar = function(){
	var pagesize = message.getPageSize();
	var arrayPageScroll = message.getPageScrollTop();
	$("#globalMess")
	.css(
		{
			left: (arrayPageScroll[0] + (pagesize[0] - $('#globalMess').width())/2) + "px",
			top:  arrayPageScroll[1] + "px"
		});
};
MESSAGE.prototype.getPageSize = function(){
	var de = document.documentElement;
	var w = window.innerWidth || self.innerWidth || (de&&de.clientWidth) || document.body.clientWidth;
	var h = window.innerHeight || self.innerHeight || (de&&de.clientHeight) || document.body.clientHeight;
	var arrayPageSize = new Array(w,h);
	return arrayPageSize;
};
MESSAGE.prototype.getPageScrollTop = function(){
	var yScrolltop = new Number();
	var xScrollleft = new Number();
	if (self.pageYOffset || self.pageXOffset) {
		yScrolltop = self.pageYOffset;
		xScrollleft = self.pageXOffset;
	} else if (document.documentElement && document.documentElement.scrollTop || document.documentElement.scrollLeft ){	 // Explorer 6 Strict
		yScrolltop = document.documentElement.scrollTop;
		xScrollleft = document.documentElement.scrollLeft;
	} else if (document.body) {// all other Explorers
		yScrolltop = document.body.scrollTop;
		xScrollleft = document.body.scrollLeft;
	}
	var arrayPageScroll = new Array(xScrollleft,yScrolltop);
	return arrayPageScroll;
};
MESSAGE.prototype.closeMessage = function(){
	setTimeout(function(){$('#globalMess').fadeOut();}, 100);
	message.isShow = false;
};
var message = new MESSAGE();

function Validator(){};
Validator.isEmpty = function(campo, shouldShowAlert, nombreDelCampo){
    var ret;
	if (campo.val() == null || campo.val().trim() === '') {
		ret = true;
	} else {
		ret = false;
	}
	if ((shouldShowAlert) && ret){
	    campo.effect("highlight", {color:'yellow'}, 1500);
	    if (nombreDelCampo) {
	        MESSAGE.alert(nombreDelCampo + " NO puede ser vacio");
	    } else {
	        MESSAGE.alert("No puede ser vacio");
	    }
	}
	return ret;
};
Validator.isLessThanZero = function(campo, shouldShowAlert, nombreDelCampo){
    var ret;
    var number = new Number(campo.val().trim());
    if (number.valueOf() > 0) {
        ret = false;
    } else {
        ret = true;
    }
    if ((shouldShowAlert) && ret){
        campo.effect("highlight", {color:'yellow'}, 1500);
        if (nombreDelCampo) {
            MESSAGE.alert(nombreDelCampo + " debe ser mayor a cero");
        } else {
            MESSAGE.alert("Debe ser mayor a cero");
        }
    }
    return ret;
};
Validator.areAllEmpty = function(campos){
	var areAllEmpty = false;
	$.each(campos, function(index, campo) {
	    var result = (campo.val() == null || campo.val() == '');
	    if (result) {
	    	areAllEmpty = true;
	    	return false;
	    }	
	});
	return areAllEmpty;
};
Validator.fillOnlyOneField = function(campo1, campo2){
	if ((campo1.val() == null || campo1.val() === '') && ((campo2.val() != null && campo2.val() != ''))) {
		return false;
	} else if ((campo1.val() != null && campo1.val() != '') && ((campo2.val() == null || campo2.val() === ''))) {
		return false;
	} else {
		return true;
	}
};
Validator.fillAtLeastOneField = function(campo1, campo2){
	if ((campo1.val() != null && campo1.val() != '') || ((campo2.val() != null && campo2.val() != ''))) {
		return false;
	} else {
		return true;
	}
};
Validator.focus = function(campo, message){
	campo.effect("highlight", {color:'yellow'}, 3000);
	var enfocar = campo.get(0);
	enfocar.focus();
	MESSAGE.alert(message ? message : "Ingrese los campos obligatorios.");
};
Validator.focusOnly = function(campo){
	campo.effect("highlight", {color:'yellow'}, 3000);
	var enfocar = campo.get(0);
	enfocar.focus();
};
Validator.esUnaFechaPasada = function(campo, shouldShowAlert, nombreDelCampo){
    var ret;
    var hoy = (new Date()).setHours(0,0,0,0);
    var day = campo.val().split("-")[0];
    var month = campo.val().split("-")[1] - 1;
    var year = campo.val().split("-")[2];
    if (hoy > new Date(year, month, day).getTime()) {
        ret = true;
    } else {
        ret = false;
    }
    if ((shouldShowAlert) && ret){
        campo.effect("highlight", {color:'yellow'}, 1500);
        if (nombreDelCampo) {
            MESSAGE.alert(nombreDelCampo + " NO puede ser una fecha del pasado");
        } else {
            MESSAGE.alert("NO puede ser una fecha del pasado");
        }
    }
    return ret;
};
function Seniales(){}
Seniales.getHtmlSeniales = function() {
	var html = "";
	for ( var key in seniales) {
		html = html + '<option value="'+seniales[key].codigo+'" label="'+seniales[key].descripcion+'"></option>';
	}
	return html;
};
Seniales.formatearClave = function(valor) {
	if (valor != "") {
        var foo = valor;
        var cantCerosAAgregar = 6 - (foo.length - 2);
        var tipoTitulo = foo.substring(0,2);
        var clave = foo.substring(2);
        var ret = tipoTitulo.toUpperCase();
        while (cantCerosAAgregar > 0) {
            ret = ret + "0"; 
            cantCerosAAgregar--;
        }
        ret = ret + clave;
        return ret;
    }
	return "";
};

function Accordion(){}
Accordion.getInstance = function(object){
	object.accordion({
		collapsible: false,
		active: 0,
		autoHeight: false,
		icons: false
	});
};

esTituloConCapitulos = function(clave) {
	var tipo = clave.substring(0,2);
	if (tipo === "SE" || tipo === "MS" || tipo === "SU" || tipo === "CF") {
		return  true;
	}
	return false;
};
/*******************************************************************************
 * Pop up para confirmacion
 ******************************************************************************/
function PopUpConfirmacion(){
};

PopUpConfirmacion.show = function(texto, callback) {
	var TEMPLATE = '<div id="DialogConfirm-Container" class="FormContainer3c"><p>'
			+ '<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>{{texto}}</p>';
	$(TEMPLATE.replace("{{texto}}", texto)).dialog({
		resizable : false,
		height : 140,
		modal : true,
		appendTo : 'body',
		close : function() {
			$(this).remove();
		},
		buttons : {
			"Si" : function() {
				if (callback)
					callback(true);
				$(this).dialog("close");
			},
			"No" : function() {
				if (callback)
					callback(false);
				$(this).dialog("close");
			}
		}
	});
};

