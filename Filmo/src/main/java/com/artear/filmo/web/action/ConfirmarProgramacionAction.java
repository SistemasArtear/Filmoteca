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
import com.artear.filmo.bo.programacion.confirmar.BuscarContratosRequest;
import com.artear.filmo.bo.programacion.confirmar.BuscarContratosResponse;
import com.artear.filmo.bo.programacion.confirmar.ProcesarConfirmacionRequest;
import com.artear.filmo.bo.programacion.confirmar.ProcesarConfirmacionResponse;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.seguridad.userdetails.ExtUserDetails;
import com.artear.filmo.services.interfaces.ConfirmarProgramacionService;
import com.artear.filmo.services.interfaces.GenerarReportesService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class ConfirmarProgramacionAction extends ActionSupport {

	private static final long serialVersionUID = 699395201439755697L;

	private String message;
	private List<ListarProgramacionResponse> programasGrilla;
	private List<Programa> programas;
	private List<Titulo> titulos;
	private ProcesarConfirmacionResponse procesarConfirmacionResponse;
	private List<BuscarContratosResponse> buscarContratosResponse;
	
	private ListarProgramacionRequest listarProgramacionRequest;
	private String descripcionPrograma;
	private String descripcionTitulo;
	private List<ProcesarConfirmacionRequest> listaProcesarConfirmacionRequest;
	private ProcesarConfirmacionRequest procesarConfirmacionRequest;
	private BuscarContratosRequest buscarContratosRequest;
	
	@Autowired
	private GenerarReportesService generarReportesService;
	
	
	@Autowired
	private ConfirmarProgramacionService confirmarProgramacionService;
	
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
	
	public ProcesarConfirmacionResponse getProcesarConfirmacionResponse() {
		return procesarConfirmacionResponse;
	}
	
	public void setProcesarConfirmacionResponse(ProcesarConfirmacionResponse procesarConfirmacionResponse) {
		this.procesarConfirmacionResponse = procesarConfirmacionResponse;
	}
	
	public List<BuscarContratosResponse> getBuscarContratosResponse() {
		return buscarContratosResponse;
	}
	
	public void setBuscarContratosResponse(List<BuscarContratosResponse> buscarContratosResponse) {
		this.buscarContratosResponse = buscarContratosResponse;
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

	public List<ProcesarConfirmacionRequest> getListaProcesarConfirmacionRequest() {
		return listaProcesarConfirmacionRequest;
	}

	public void setListaProcesarConfirmacionRequest(List<ProcesarConfirmacionRequest> listaProcesarConfirmacionRequest) {
		this.listaProcesarConfirmacionRequest = listaProcesarConfirmacionRequest;
	}

	public ProcesarConfirmacionRequest getProcesarConfirmacionRequest() {
		return procesarConfirmacionRequest;
	}
	
	public void setProcesarConfirmacionRequest(ProcesarConfirmacionRequest procesarConfirmacionRequest) {
		this.procesarConfirmacionRequest = procesarConfirmacionRequest;
	}
	
	public BuscarContratosRequest getBuscarContratosRequest() {
		return buscarContratosRequest;
	}
	
	public void setBuscarContratosRequest(BuscarContratosRequest buscarContratosRequest) {
		this.buscarContratosRequest = buscarContratosRequest;
	}
	
	/**
     * Búsqueda de la grilla de programación
     * @author cetorres
     * 
     * @useCase FP340 - Confirmar programación
     */
	public String listarProgramacion() throws Exception {
		try {
			programasGrilla = confirmarProgramacionService.listarProgramacion(listarProgramacionRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;		
	}
	
	public String generarListadoExhibicionesNoConfirmadas(boolean warnings) throws Exception {
		try{
			Map<String, Object> params = new HashMap<String, Object>();
		
			ExtUserDetails extUserDetails = (ExtUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			params.put("usuario", extUserDetails.getUsername());
			params.put("idListado", "RFP34098");
			params.put("ID_REPORTE" , procesarConfirmacionResponse.getIdReporte()  );
			params.put("warning", warnings);
			String pathPropuesta = System.getProperty("Filmo")+"reportes/exhibicionesNoConfirmadas/";
			params.put("SUBREPORT_DIR", pathPropuesta);
			String pathReporte = new String("reportes/exhibicionesNoConfirmadas/Exhibiciones.jasper");
			String nombreReporte = "ListadoExhibicionesNoConfirmadas";
			generarReportesService.generarReporte(params, pathReporte, nombreReporte); 
		}catch (Exception e){
			message = "Error Generacion de Reporte";
		}
		return SUCCESS;
	}

	/**
     * Confirmación de un título de la grilla de programación
     * @author cetorres
     * 
     * @useCase FP340 - Confirmar programación
     */
	public String procesarConfirmacion() throws Exception {
		try {
			procesarConfirmacionResponse = confirmarProgramacionService.procesarConfirmacion(procesarConfirmacionRequest);
			if (procesarConfirmacionResponse.getIdReporte() != null){
				message = null;
				this.generarListadoExhibicionesNoConfirmadas(procesarConfirmacionResponse.getTipo().equals("W") ? true : false);
				if(message != null){
					procesarConfirmacionResponse.setMensaje(message);
					message = null;
				}
			}
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * Confirmación de la grilla de programación
     * @author cetorres
     * 
     * @useCase FP340 - Confirmar programación
     */
	public String procesarConfirmacionGrillaProgramacion() throws Exception {
		try {
			procesarConfirmacionResponse = confirmarProgramacionService.procesarConfirmacionGrillaProgramacion(listaProcesarConfirmacionRequest);
			if (procesarConfirmacionResponse.getIdReporte() != null){
				this.generarListadoConfirmarTodo();
			}
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	private void generarListadoConfirmarTodo() {
		try{
			Map<String, Object> params = new HashMap<String, Object>();
		
			ExtUserDetails extUserDetails = (ExtUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			params.put("usuario", extUserDetails.getUsername());
			params.put("idListado", "RFP34098");
			params.put("ID_REPORTE" , procesarConfirmacionResponse.getIdReporte()  );
			String pathPropuesta = System.getProperty("Filmo")+"reportes/confirmarTodo/";
			params.put("SUBREPORT_DIR", pathPropuesta);
			String pathReporte = new String("reportes/confirmarTodo/confirmarTodo.jasper");
			String nombreReporte = "ListadoConfirmarTodo";
			generarReportesService.generarReporte(params, pathReporte, nombreReporte); 
		}catch (Exception e){
			message = "Error Generacion de Reporte";
		}
		
	}

	/**
     * Búsqueda de contrato-grupo-señal para un título
     * @author cetorres
     * 
     * @useCase FP340 - Confirmar programación
     */
	public String buscarContratosParaTitulo() throws Exception {
		try {
			buscarContratosResponse = confirmarProgramacionService.buscarContratosParaTitulo(buscarContratosRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
}