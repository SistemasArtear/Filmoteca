package com.artear.filmo.web.action;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.artear.filmo.bo.chequeoOperativo.ChequeoOperativoResponse;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.seguridad.userdetails.ExtUserDetails;
import com.artear.filmo.services.interfaces.ChequeoOperativoService;
import com.artear.filmo.services.interfaces.GenerarReportesService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

@Controller
public class ChequeoOperativoAction extends ActionSupport{

    @Value("${artear.entorno}")
    private String artearEntorno;
    
	//OUT
	private String message;
	private String resultado;
	private boolean hayRegistrosReporteExhibicion;
	private boolean hayRegistrosReporteLibreria;
	private BigDecimal idReporteLibreria;
	private BigDecimal idReporteExhibicion;
	
	// IN
	private String fecha;

	/**
	 * 
	 */
	private static final long serialVersionUID = 8271767020873455074L; 
	
	private static final Logger log = LoggerFactory.getLogger(ChequeoOperativoAction.class);
	
	@Autowired
	private ChequeoOperativoService chequeoOperativoService;
	@Autowired
	private GenerarReportesService generarReportesService;
	
	public String validarChequeoOperativo() throws Exception{
		
		ChequeoOperativoResponse resp = chequeoOperativoService.validarChequeoOperativo(getPeriodoDeFecha());
		message = resp.getMensajeError();
		if(resp.isHayError()){
			resultado = "error";
		}
		else {
			resultado = "ok";
		}
		return SUCCESS;
	}
	
	public class BackUpCopiaMensualRunnable implements Runnable {
	    private String artearEntorno;
	    private String username;
	    
        public BackUpCopiaMensualRunnable(String artearEntorno, String username) {
            this.artearEntorno = artearEntorno;
            this.username = username;
        }
	    public void run() {
	        if (artearEntorno.toLowerCase().equals("produccion")) {
	            chequeoOperativoService.backUpCopiaMensual(username);
	        } else {
	            log.error("BackUpCopiaMensual debe ser ejecutado desde el entorno de produccion");
	        }
	    }
	}
	
	public String confirmarChequeoOperativo() throws Exception {
		ChequeoOperativoResponse resp = chequeoOperativoService.confirmaChequeoOperativo(getMesDeFecha(), getAnioDeFecha());
		if (resp.isHayError()) {
			resultado = "error";
		}
		else {
			resultado = "ok";
	        ExtUserDetails extUserDetails = (ExtUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Thread thread = new Thread(new BackUpCopiaMensualRunnable(artearEntorno, extUserDetails.getUsername()));
			thread.start();
		}
		return SUCCESS;
	}
	public String procesarChequeoOperativo() throws Exception {
		chequeoOperativoService.procesarChequeoOperativo(getMesDeFecha(), getAnioDeFecha())	;	
		resultado = "ok";
		return SUCCESS;
	}
	
	public String generarReportes() throws Exception {
		try {
			ChequeoOperativoResponse resp = chequeoOperativoService.generarReportes(getPeriodoDeFecha().toString() , getMesDeFecha(), getAnioDeFecha());
			this.hayRegistrosReporteExhibicion = resp.isHayRegistrosExhibicion();
			this.hayRegistrosReporteLibreria = resp.isHayRegistrosLibreria();
			this.idReporteExhibicion = resp.getIdReporteEhibicion();
			this.idReporteLibreria = resp.getIdReporteLibreria();
			if (hayRegistrosReporteExhibicion){
				generarReporteExhibicion(resp.getIdReporteEhibicion(), getMesDeFecha(), getAnioDeFecha());
			}
			if (hayRegistrosReporteLibreria){
				generarReporteLibreria(resp.getIdReporteLibreria(), getMesDeFecha(),getAnioDeFecha());
			}
		
			if (resp.isHayError()) {
				resultado = "error";
			}
			else {
				resultado = "ok";
			}
		} catch(DataBaseException e){
			message = e.getError().getMensaje();
		}
		return SUCCESS;
	}

	private String generarReporteExhibicion(BigDecimal idExhibicion, Integer mes, Integer anio) throws SQLException, JRException, Exception{
		
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("idListado", " ");
			params.put("p_mes", mes);
			params.put("p_anio", anio);
			params.put("p_id", this.getIdReporteExhibicion());
			ExtUserDetails extUserDetails = (ExtUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			params.put("usuario", extUserDetails.getUsername());
			String pathFilmo = System.getProperty("Filmo")+"reportes/chequeoOperativo/";
			params.put("SUBREPORT_DIR", pathFilmo);
			String pathReporte = new String("reportes/chequeoOperativo/reporteErroresExhibicion.jasper");
			String nombreReporte = "reporteErroresExhibicion";
			generarReportesService.generarReporte(params, pathReporte, nombreReporte); 
		
		
		return SUCCESS;
	}
	
	private String generarReporteLibreria(BigDecimal idLibreria, Integer mes, Integer anio) throws SQLException, JRException, Exception{
	
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("idListado", " ");
			params.put("p_mes", mes);
			params.put("p_anio", anio);
			params.put("p_id", this.getIdReporteLibreria());
			ExtUserDetails extUserDetails = (ExtUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			params.put("usuario", extUserDetails.getUsername());
			String pathFilmo = System.getProperty("Filmo")+"reportes/chequeoOperativo/";
			params.put("SUBREPORT_DIR", pathFilmo);
			String pathReporte = new String("reportes/chequeoOperativo/reporteErroresLibreria.jasper");
			String nombreReporte = "reporteErroresLibreria";
			generarReportesService.generarReporte(params, pathReporte, nombreReporte); 
		
		
		return SUCCESS;
	}
	
	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	
	public String getPeriodoDeFecha(){
		String periodoStr = fecha.substring(0,2) + fecha.substring(3);
		return periodoStr;
	}
	
	public Integer getAnioDeFecha(){
		String anioStr = fecha.substring(3);
		return Integer.valueOf(anioStr);
	}
	
	public Integer getMesDeFecha(){
		String mesStr = fecha.substring(0,2);
		return Integer.valueOf(mesStr);
	}

	public boolean isHayRegistrosReporteExhibicion() {
		return hayRegistrosReporteExhibicion;
	}

	public void setHayRegistrosReporteExhibicion(boolean hayRegistrosReporteExhibicion) {
		this.hayRegistrosReporteExhibicion = hayRegistrosReporteExhibicion;
	}

	public boolean isHayRegistrosReporteLibreria() {
		return hayRegistrosReporteLibreria;
	}

	public void setHayRegistrosReporteLibreria(boolean hayRegistrosReporteLibreria) {
		this.hayRegistrosReporteLibreria = hayRegistrosReporteLibreria;
	}

	public BigDecimal getIdReporteLibreria() {
		return idReporteLibreria;
	}

	public void setIdReporteLibreria(BigDecimal idReporteLibreria) {
		this.idReporteLibreria = idReporteLibreria;
	}

	public BigDecimal getIdReporteExhibicion() {
		return idReporteExhibicion;
	}

	public void setIdReporteExhibicion(BigDecimal idReporteExhibicion) {
		this.idReporteExhibicion = idReporteExhibicion;
	}
    public String getArtearEntorno() {
        return artearEntorno;
    }
    public void setArtearEntorno(String artearEntorno) {
        this.artearEntorno = artearEntorno;
    }
}
