function ConsultasListadosEvent() {
};

extend(ConsultasListadosEvent, Plugin);

/***********************************************************************************		
 ********************			  GX MENU CONTABLE				********************
 ***********************************************************************************/
/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hfp45001 			Cierre Contable */
ConsultasListadosEvent.CIERRE_CONTABLE = "hfp45001";
ConsultasListadosEvent.prototype.createCierreContable = function() {
	consultasListadosEvent.open(ConsultasListadosEvent.CIERRE_CONTABLE);
};

/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hfp65001			Generacion Interfaz a SAP */
ConsultasListadosEvent.GENERACION_INTERFAZ_SAP = "hfp65001";
ConsultasListadosEvent.prototype.createGeneracionInterfazSap = function() {
	consultasListadosEvent.open(ConsultasListadosEvent.GENERACION_INTERFAZ_SAP);
};

/***********************************************************************************		
 ********************		   GX MANTENIMIENTO ARCHIVOS		********************
 ***********************************************************************************/
/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hfpw61601 		Trabajar con seteo de cuentas contables */
ConsultasListadosEvent.TRABAJAR_SETEO_CTAS_CONTABLES = "hfpw61601";
ConsultasListadosEvent.prototype.createTrabajarSeteoCtasContables = function() {
	consultasListadosEvent.open(ConsultasListadosEvent.TRABAJAR_SETEO_CTAS_CONTABLES);
};

/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hfpw61701 		Trabajar con Cuentas Contables */
ConsultasListadosEvent.TRABAJAR_CTAS_CONTABLES = "hfpw61701";
ConsultasListadosEvent.prototype.createTrabajarCtasContables = function() {
	consultasListadosEvent.open(ConsultasListadosEvent.TRABAJAR_CTAS_CONTABLES);
};

/***********************************************************************************		
 ******************** 				GX CONSULTAS			    ********************
 ***********************************************************************************/
/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hfcw11001 		Consulta Histórica de títulos */
ConsultasListadosEvent.CONSULTA_HISTORICA_TITULOS = "hfcw11001";
ConsultasListadosEvent.prototype.createConsultaHistoricaTitulos = function() {
	consultasListadosEvent.open(ConsultasListadosEvent.CONSULTA_HISTORICA_TITULOS);
};

/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hfcw12001			Consulta de títulos vigentes */
ConsultasListadosEvent.CONSULTA_TITULOS_VIGENTES = "hfcw12001";
ConsultasListadosEvent.prototype.createConsultaTitulosVigentes = function() {
	consultasListadosEvent.open(ConsultasListadosEvent.CONSULTA_TITULOS_VIGENTES);
};

/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hfcw13001			Consulta de títulos para programar SE - MS */
ConsultasListadosEvent.CONSULTA_TITULOS_PROGRAMAR_SEMS = "hfcw13001";
ConsultasListadosEvent.prototype.createConsultaTitulosProgramarSEMS = function() {
	consultasListadosEvent.open(ConsultasListadosEvent.CONSULTA_TITULOS_PROGRAMAR_SEMS);
};

/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hfcw14001			Consulta de títulos para programar LA - LM - PE */
ConsultasListadosEvent.CONSULTAR_TITULOS_PROGRAMAR_LALMPE = "hfcw14001";
ConsultasListadosEvent.prototype.createConsultarTitulosProgramarLALMPE = function() {
	consultasListadosEvent.open(ConsultasListadosEvent.CONSULTAR_TITULOS_PROGRAMAR_LALMPE);
};

/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hfcw15001			Consulta de contratos */   
ConsultasListadosEvent.CONSULTA_CONTRATOS = "hfcw15001";
ConsultasListadosEvent.prototype.createConsultaContratos = function() {
	consultasListadosEvent.open(ConsultasListadosEvent.CONSULTA_CONTRATOS);
};

/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hfcw16001			Consulta histórica ingresos - devoluciones */
ConsultasListadosEvent.CONSULTA_HISTORICA_INGRESOS_DEVOLUCIONES = "hfcw16001";
ConsultasListadosEvent.prototype.createConsultaHistoricaIngresosDevoluciones = function() {
	consultasListadosEvent.open(ConsultasListadosEvent.CONSULTA_HISTORICA_INGRESOS_DEVOLUCIONES);
};

/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hfcw17001			Reporte de transmisión */
ConsultasListadosEvent.CONSULTA_TRANSMISION = "hfcw17001";
ConsultasListadosEvent.prototype.createConsultaTransmision = function() {
	consultasListadosEvent.open(ConsultasListadosEvent.CONSULTA_TRANSMISION);
};

/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hfcw18001			Consulta de ficha cinematográfica */	
ConsultasListadosEvent.CONSULTAR_FICHA_CINEMATOGRAFICA = "hfcw18001";
ConsultasListadosEvent.prototype.createFichaCinematografica = function() {
	consultasListadosEvent.open(ConsultasListadosEvent.CONSULTAR_FICHA_CINEMATOGRAFICA);
};

/***********************************************************************************		
 ******************** 				GX LISTADOS				    ********************
 ***********************************************************************************/

/* Http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hflw11000         L1100 - Listado de argentores con y sin capitulos */
ConsultasListadosEvent.LISTADO_ARGENTORES_CS_CAPITULOS = "hflw11000";
ConsultasListadosEvent.prototype.createListadoArgentoresCsCapitulos = function() {
	consultasListadosEvent.open(ConsultasListadosEvent.LISTADO_ARGENTORES_CS_CAPITULOS);
};

/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hflw12001         L1200 - Listado de Distribuidores */
ConsultasListadosEvent.LISTADO_DISTRIBUIDORES = "hflw12001";
ConsultasListadosEvent.prototype.createListadoDistribuidores = function() {
	consultasListadosEvent.open(ConsultasListadosEvent.LISTADO_DISTRIBUIDORES);
};

/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hflw13001         L1300 - Listado contratos vigentes */
ConsultasListadosEvent.LISTADO_CONTRATOS_VIGENTES = "hflw13001";
ConsultasListadosEvent.prototype.createListadoContratosVigentes = function() {
	consultasListadosEvent.open(ConsultasListadosEvent.LISTADO_CONTRATOS_VIGENTES);
};

/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hflw14001         L1400 - Listado Títulos por Programa */
ConsultasListadosEvent.LISTADO_TITULOS_PROGRAMA = "hflw14001";
ConsultasListadosEvent.prototype.createListadoTitulosPrograma = function() {
	consultasListadosEvent.open(ConsultasListadosEvent.LISTADO_TITULOS_PROGRAMA);
};

/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hflw15001 		L1500 - Listado de títulos exhibidos sin vencer */
ConsultasListadosEvent.LISTADO_TITULOS_EXHIBIDOS_SIN_VENCER = "hflw15001";
ConsultasListadosEvent.prototype.createListadoTitulosExhibidosSinVencer = function() {
	consultasListadosEvent.open(ConsultasListadosEvent.LISTADO_TITULOS_EXHIBIDOS_SIN_VENCER);
};

/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hflw16001			L1600 - Listado de títulos vencidos en un período */
ConsultasListadosEvent.LISTADO_TITULOS_VENCIDOS_PERIODO = "hflw16001";
ConsultasListadosEvent.prototype.createListadoTitulosVencidosPeriodo = function() {
	consultasListadosEvent.open(ConsultasListadosEvent.LISTADO_TITULOS_VENCIDOS_PERIODO);
};

/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hflw17001 		L1700 - Listado material recibido y no chequeado */
ConsultasListadosEvent.LISTADO_MATERIAL_RECIBIDO_NO_CHEQUEADO = "hflw17001";
ConsultasListadosEvent.prototype.createListadoMaterialRecibidoNoChequeado = function() {
	consultasListadosEvent.open(ConsultasListadosEvent.LISTADO_MATERIAL_RECIBIDO_NO_CHEQUEADO);
};

/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hflw18001 		L1800 - Listado disponibilidad de  LA - LM - PE */
ConsultasListadosEvent.LISTADO_DISPONIBILIDAD_LALMPE = "hflw18001";
ConsultasListadosEvent.prototype.createListadoDisponibilidadLALMPE = function() {
	consultasListadosEvent.open(ConsultasListadosEvent.LISTADO_DISPONIBILIDAD_LALMPE);
};

/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hflw19001 		L1900 - Listado disponibilidad de SE-MS */
ConsultasListadosEvent.LISTADO_DISPONIBILIDAD_SEMS = "hflw19001";
ConsultasListadosEvent.prototype.createListadoDisponibilidadSEMS = function() {
	consultasListadosEvent.open(ConsultasListadosEvent.LISTADO_DISPONIBILIDAD_SEMS);
};

/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hflw20001			L2000 - Listado resumen de fichas cinematográficas */
ConsultasListadosEvent.LISTADO_RESUMEN_FICHAS_CINEMATOGRAFICAS = "hflw20001";
ConsultasListadosEvent.prototype.createListadoResumenFichasCinematograficas = function() {
	consultasListadosEvent.open(ConsultasListadosEvent.LISTADO_RESUMEN_FICHAS_CINEMATOGRAFICAS);
};

/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hflw21001 		L2100 - Listado títulos originales */
ConsultasListadosEvent.LISTADO_TITULOS_ORIGINALES = "hflw21001";
ConsultasListadosEvent.prototype.createListadoTitulosOriginales = function() {
    consultasListadosEvent.open(ConsultasListadosEvent.LISTADO_TITULOS_ORIGINALES);
};

/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hflw22001			L2200 - Listado de largometrajes por recibir */
ConsultasListadosEvent.LISTADO_LARGOMETRAJES_POR_RECIBIR = "hflw22001";
ConsultasListadosEvent.prototype.createListadoLargometrajesPorRecibir = function() {
    consultasListadosEvent.open(ConsultasListadosEvent.LISTADO_LARGOMETRAJES_POR_RECIBIR);
};

/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hflw23001 		L2300 - Listado de largometrajes para recalificar */
ConsultasListadosEvent.LISTADO_LARGOMETRAJES_PARA_RECALIFICAR = "hflw23001";
ConsultasListadosEvent.prototype.createListadoLargometrajesParaRecalificar = function() {
    consultasListadosEvent.open(ConsultasListadosEvent.LISTADO_LARGOMETRAJES_PARA_RECALIFICAR);
};

/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hflw24001			L2400 - Reporte de transmision */
ConsultasListadosEvent.LISTADO_REPORTE_TRANSMISION = "hflw24001";
ConsultasListadosEvent.prototype.createListadoReporteTransmision = function() {
    consultasListadosEvent.open(ConsultasListadosEvent.LISTADO_REPORTE_TRANSMISION);
};

/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hflw25001 		L2500 - Listado de títulos vencidos por soporte */
ConsultasListadosEvent.LISTADO_TITULOS_VENCIDOS_POR_SOPORTE = "hflw25001";
ConsultasListadosEvent.prototype.createListadoTitulosVencidosPorSoporte = function() {
    consultasListadosEvent.open(ConsultasListadosEvent.LISTADO_TITULOS_VENCIDOS_POR_SOPORTE);
};

/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hflw26001			L2600 - Listado de fílmicos por exhibir */
ConsultasListadosEvent.LISTADO_FILMICOS_POR_EXHIBIR = "hflw26001";
ConsultasListadosEvent.prototype.createListadoFilmicosPorExhibir = function() {
    consultasListadosEvent.open(ConsultasListadosEvent.LISTADO_FILMICOS_POR_EXHIBIR);
};

/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hflw2700          L2700 - Listado de exhibiciones sin contrato */
ConsultasListadosEvent.LISTADO_EXHIBICIONES_SIN_CONTRATO = "hflw2700";
ConsultasListadosEvent.prototype.createListadoExhibicionesSinContrato = function() {
	consultasListadosEvent.open(ConsultasListadosEvent.LISTADO_EXHIBICIONES_SIN_CONTRATO);
};

/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hflw28000			L2800 - Control Carga Listados Volver */
ConsultasListadosEvent.CONTROL_CARGA_LISTADOS_VOLVER = "hflw28000";
ConsultasListadosEvent.prototype.createControlCargaListadosVolver = function() {
	consultasListadosEvent.open(ConsultasListadosEvent.CONTROL_CARGA_LISTADOS_VOLVER);
};


/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hflw2900         L2900 Listado de Títulos sin Exhib. Ni Contrato */
ConsultasListadosEvent.TITULOS_SIN_EXHIBIR_NI_CONTRATO = "hflw2900";
ConsultasListadosEvent.prototype.createTitulosSinExhibirNiContrato = function() {
    consultasListadosEvent.open(ConsultasListadosEvent.TITULOS_SIN_EXHIBIR_NI_CONTRATO);
};

/* http://gxdes.artear:82/filmoweb_a/servlet/hiniciar?hflw30001 RFP400L2 - Contratos por amortizar*/
ConsultasListadosEvent.CONTRATOS_POR_AMORTIZAR = "hflw30001";
ConsultasListadosEvent.prototype.createContratosPorAmortizar = function() {
    consultasListadosEvent.open(ConsultasListadosEvent.CONTRATOS_POR_AMORTIZAR);
};

ConsultasListadosEvent.prototype.open = function(listado) {
	var serviceProperties = variablesSesionUsuarioService.getServiceProperties();
	consultasListadosEvent.abrirListado(serviceProperties, listado);
};

ConsultasListadosEvent.prototype.abrirListado = function(serviceProperties, listado) {
	if (serviceProperties) {
		var host = serviceProperties.genexusServer;
		var url = host + "?" + listado + "," + variablesSesionUsuarioEvent.dameSenialSeleccionada();
		$("#viewContainer").empty().append('<iframe frameborder="0" class="iframeContainer" src=\"' + url + '\"/>');
	} else {
		MESSAGE.error("En estos momentos no se puede realizar la operación. Intente más tarde.");
	}
};

var consultasListadosEvent = new ConsultasListadosEvent();