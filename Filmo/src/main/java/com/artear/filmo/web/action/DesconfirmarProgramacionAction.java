package com.artear.filmo.web.action;

import static com.artear.filmo.constants.ApplicationConstants.ERROR_INESPERADO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.artear.filmo.bo.common.Titulo;
import com.artear.filmo.bo.programacion.ListarProgramacionRequest;
import com.artear.filmo.bo.programacion.ListarProgramacionResponse;
import com.artear.filmo.bo.programacion.Programa;
import com.artear.filmo.bo.programacion.desconfirmar.ProcesarDesconfirmacionRequest;
import com.artear.filmo.bo.programacion.desconfirmar.ProcesarDesconfirmacionResponse;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.seguridad.userdetails.ExtUserDetails;
import com.artear.filmo.services.interfaces.DesconfirmarProgramacionService;
import com.artear.filmo.services.interfaces.GenerarReportesService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class DesconfirmarProgramacionAction extends ActionSupport {

	private static final long serialVersionUID = 7715623545660274055L;
	
	private String message;
	private List<ListarProgramacionResponse> programasGrilla;
	private List<Programa> programas;
	private List<Titulo> titulos;
	private ProcesarDesconfirmacionResponse procesarDesconfirmacionResponse;
	
	private ListarProgramacionRequest listarProgramacionRequest;
	private String descripcionPrograma;
	private String descripcionTitulo;
	private String senial;
	private ProcesarDesconfirmacionRequest procesarDesconfirmacionRequest;
	
	@Autowired
	private GenerarReportesService generarReportesService;
	
	@Autowired
	private DesconfirmarProgramacionService desconfirmarProgramacionService;
		
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ListarProgramacionResponse> getProgramasGrilla() {
		return programasGrilla;
	}

	public void setProgramasGrilla(List<ListarProgramacionResponse> programasGrilla) {
		this.programasGrilla = programasGrilla;
	}

	public List<Programa> getProgramas() {
		return programas;
	}

	public void setProgramas(List<Programa> programas) {
		this.programas = programas;
	}

	public List<Titulo> getTitulos() {
		return titulos;
	}

	public void setTitulos(List<Titulo> titulos) {
		this.titulos = titulos;
	}
	
	public ProcesarDesconfirmacionResponse getProcesarDesconfirmacionResponse() {
		return procesarDesconfirmacionResponse;
	}
	
	public void setProcesarDesconfirmacionResponse(ProcesarDesconfirmacionResponse procesarDesconfirmacionResponse) {
		this.procesarDesconfirmacionResponse = procesarDesconfirmacionResponse;
	}

	public ListarProgramacionRequest getListarProgramacionRequest() {
		return listarProgramacionRequest;
	}

	public void setListarProgramacionRequest(ListarProgramacionRequest listarProgramacionRequest) {
		this.listarProgramacionRequest = listarProgramacionRequest;
	}

	public String getDescripcionPrograma() {
		return descripcionPrograma;
	}

	public void setDescripcionPrograma(String descripcionPrograma) {
		this.descripcionPrograma = descripcionPrograma;
	}

	public String getDescripcionTitulo() {
		return descripcionTitulo;
	}

	public void setDescripcionTitulo(String descripcionTitulo) {
		this.descripcionTitulo = descripcionTitulo;
	}
	
	public ProcesarDesconfirmacionRequest getProcesarDesconfirmacionRequest() {
		return procesarDesconfirmacionRequest;
	}
	
	public void setProcesarDesconfirmacionRequest(ProcesarDesconfirmacionRequest procesarDesconfirmacionRequest) {
		this.procesarDesconfirmacionRequest = procesarDesconfirmacionRequest;
	}

    public void setSenial(String senial) {
        this.senial = senial;
    }
    
    public String getSenial() {
        return this.senial;
    }
    
	public String generarListadoExhibicionesLibrerias() throws Exception {
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("idListado", " ");
			ExtUserDetails extUserDetails = (ExtUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			params.put("usuario", extUserDetails.getUsername());
			params.put("p_senial", procesarDesconfirmacionRequest.getSenial());
			params.put("p_tipoTitulo", procesarDesconfirmacionRequest.getTipoTitulo());
			params.put("p_nroTitulo", procesarDesconfirmacionRequest.getNumeroTitulo().toString());
			params.put("p_capitulo", procesarDesconfirmacionRequest.getCapitulo().toString());
			params.put("p_parte", procesarDesconfirmacionRequest.getParte().toString());
			params.put("p_contrato", procesarDesconfirmacionRequest.getContrato().toString());
			params.put("p_grupo", procesarDesconfirmacionRequest.getGrupo().toString());
			
			String pathReporte = new String("reportes/exhibicionesLibreria/ExhibicionesLibrerias.jasper");
			String nombreReporte = "exhibicionesEnLibrerias";
			generarReportesService.generarReporte(params, pathReporte, nombreReporte); 
		}catch (Exception e){
			message = "Error Generacion de Reporte";
		}
		return SUCCESS;
	}
	
	
	/**
     * Búsqueda de la grilla de programación
     * @author cetorres
     * 
     * @useCase FP350 - Desconfirmar programación
     */
	public String listarProgramacion() throws Exception {
		try {
			programasGrilla = desconfirmarProgramacionService.listarProgramacion(listarProgramacionRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;		
	}
	
	/**
     * Situar programas por descripción
     * @author cetorres
     * 
     * @useCase FP350 - Desconfirmar programación
     */
	public String buscarProgramasDesconfirmar() throws Exception {
		try {
			programas = desconfirmarProgramacionService.buscarProgramasDesconfirmar(descripcionPrograma, senial);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * Situar títulos por descripción
     * @author cetorres
     * 
     * @useCase FP350 - Desconfirmar programación
     */
	public String buscarTitulosDesconfirmar() throws Exception {
		try {
			titulos = desconfirmarProgramacionService.buscarTitulosDesconfirmar(descripcionTitulo);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * Desconfirmar título de grilla
     * @author cetorres
     * 
     * @useCase FP350 - Desconfirmar programación
     */
	public String procesarDesconfirmacion() throws Exception {
		try {
			procesarDesconfirmacionResponse = desconfirmarProgramacionService.procesarDesconfirmacion(procesarDesconfirmacionRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
}