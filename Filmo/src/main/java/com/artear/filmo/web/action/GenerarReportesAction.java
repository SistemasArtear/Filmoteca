package com.artear.filmo.web.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.artear.filmo.services.interfaces.GenerarReportesService;
import com.opensymphony.xwork2.ActionSupport;



//@Controller
public class GenerarReportesAction extends ActionSupport {

	private static final Log logger = LogFactory.getLog(GenerarReportesAction.class);
	
	@Autowired
	private GenerarReportesService reporteService;
	@Autowired
	//private  ServiciosSesionUsuarioImpl serviciosSesionUsuario;
	
	private static final long serialVersionUID = 2594301479157750226L;

	private static final String REPORTE_RESULT = "reporteStream";	
	private InputStream fileStream;
	private String fechaDesde;
	private String fechaHasta;
	private String pathReporte;
	private String mensaje;
	private String periodo;

	
	/**
	 * Visualiza el reporte.
	 */
	public String generarReporteRemitoSalida() throws Exception {
			
			Map<String, Object> params = new HashMap<String, Object>();
			byte[] pdfReporte = reporteService.generarReporte( params,  pathReporte, "nombre" );
			this.fileStream = new ByteArrayInputStream(pdfReporte);
			return REPORTE_RESULT;
	}
	
	
	
	/*private byte[] generar() throws Exception {		
		logger.info("ReporteAction.generar");
		 Map<String, Object> params = new HashMap<String, Object>();
		return reporteService.generarReporte( params,  tipoReporte  );
	}*/
	
	/*private Map<String, Object> getParams() {
		 Map<String, Object> params = new HashMap<String, Object>();
		 params.put (EnumParametroReporte.FECHA_REPORT.getVal(), this.getFechaDelReporte());
		 params.put (EnumParametroReporte.FECHA_HASTA.getVal(), fechaHasta);
		 params.put (EnumParametroReporte.FECHA_DESDE.getVal(), fechaDesde);	
		 if(periodo != null && !"".equals(periodo))
			 params.put(EnumParametroReporte.PERIODO.getVal() , periodo);
		 params.put (EnumParametroReporte.USUARIO.getVal(), serviciosSesionUsuario.getUsuario());
		 logger.info("ReporteAction.getParams");
		 return params;	
	}*/
	
	

	public GenerarReportesService getReporteService() {
		return reporteService;
	}

	public void setFileStream(InputStream fileStream) {
		this.fileStream = fileStream;
	}

	public String getPathReporte() {
		return pathReporte;
	}

	public void setPathReporte(String pathReporte) {
		this.pathReporte = pathReporte;
	}

	public void setReporteService(GenerarReportesService reporteService) {
		this.reporteService = reporteService;
	}

	public InputStream getFileStream() {
		return fileStream;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}


	public static String getReporteResult() {
		return REPORTE_RESULT;
	}


	public String getMensaje() {
		return mensaje;
	}


	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}	
	
}
