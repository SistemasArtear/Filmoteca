package com.artear.filmo.web.action;

import static com.artear.filmo.constants.ApplicationConstants.ERROR_INESPERADO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.artear.filmo.bo.clonar.remitos.ClonarGrillaResponse;
import com.artear.filmo.bo.clonar.remitos.ClonarRemitosRequest;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.services.interfaces.ClonarRemitosService;
import com.opensymphony.xwork2.ActionSupport;

public class ClonarRemitosAction extends ActionSupport {

	private static final long serialVersionUID = 3115775787101401507L;

	// OUT
	private List<ClonarGrillaResponse> remitosGrilla;
	private String message;
	private String stackTrace;
	
	// IN
	private String tipoTitulo;
	private Integer numeroTitulo;
	private Integer idProveedorAnterior;
	private Integer idProveedorNuevo;
	private Integer contratoAnterior;
	private Integer contratoNuevo;
	private Integer grupoAnterior;
	private Integer grupoNuevo;
	private String tituloOriginal;
	private String tituloCastellano;

	@Autowired
	private ClonarRemitosService clonarRemitosService;

	public String buscarRemitosParaClonarSinContrato()  throws Exception {		
		try {
			if(this.tipoTitulo != null) { // busqueda por Tipo y Nro de titulo
				this.remitosGrilla = this.clonarRemitosService.buscarRemitosSinContratoPorClave(this.tipoTitulo, this.numeroTitulo);
			} else if(this.tituloOriginal != null) { // busqueda por descripcion de titulo original
				this.remitosGrilla = this.clonarRemitosService.buscarRemitosSinContratoPorTituloOriginal(this.tituloOriginal);
			} else if(this.tituloCastellano != null) {  // busqueda por descripcion de titulo castellano
				this.remitosGrilla = this.clonarRemitosService.buscarRemitosSinContratoPorTituloCastellano(this.tituloCastellano);
			}			
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
			stackTrace = e.getMessage() + e.getCause().getMessage();
		}
		return SUCCESS;	
	}
	
	public String buscarRemitosParaClonarConContrato()  throws Exception {	
		try {
			if(this.tipoTitulo != null) { // busqueda por Tipo, Nro de titulo, Contrato Anterior y Grupo Anterior
				this.remitosGrilla = this.clonarRemitosService.buscarRemitosConContratoPorClave(this.tipoTitulo, this.numeroTitulo, this.contratoAnterior, this.grupoAnterior);
			} else if(this.tituloOriginal != null) { // busqueda por descripcion de titulo original
				this.remitosGrilla = this.clonarRemitosService.buscarRemitosConContratoPorTituloOriginal(this.tituloOriginal);
			} else if(this.tituloCastellano != null) {  // busqueda por descripcion de titulo castellano
				this.remitosGrilla = this.clonarRemitosService.buscarRemitosConContratoPorTituloCastellano(this.tituloCastellano);
			}
						
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
			stackTrace = e.getMessage() + e.getCause().getMessage();
		}
		return SUCCESS;	
	}
	
	public String clonarRemitosSinContrato()  throws Exception {
		try {			
			ClonarRemitosRequest request = new ClonarRemitosRequest();
			request.setTipoTitulo(this.tipoTitulo);
			request.setNumeroTitulo(this.numeroTitulo);
			request.setIdProveedorAnterior(this.idProveedorAnterior);
			request.setIdProveedorNuevo(this.idProveedorNuevo);
			
			this.clonarRemitosService.clonarRemitosSinContrato(request);
			
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
			stackTrace = e.getMessage() + e.getCause().getMessage();
		}
		return SUCCESS;
	}
	
	public String clonarRemitosConContrato()  throws Exception {
		try {			
			ClonarRemitosRequest request = new ClonarRemitosRequest();
			request.setTipoTitulo(this.tipoTitulo);
			request.setNumeroTitulo(this.numeroTitulo);
			request.setIdProveedorAnterior(this.idProveedorAnterior);
			request.setIdProveedorNuevo(this.idProveedorNuevo);
			request.setContratoAnterior(this.contratoAnterior);
			request.setContratoNuevo(this.contratoNuevo);
			request.setGrupoAnterior(this.grupoAnterior);
			request.setGrupoNuevo(this.grupoNuevo);
			
			this.clonarRemitosService.clonarRemitosConContrato(request);
			
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
			stackTrace = e.getMessage() + e.getCause().getMessage();
		}
		return SUCCESS;
	}
	
	public String getTipoTitulo() {
		return tipoTitulo;
	}

	public void setTipoTitulo(String tipoTitulo) {
		this.tipoTitulo = tipoTitulo;
	}

	public Integer getNumeroTitulo() {
		return numeroTitulo;
	}

	public void setNumeroTitulo(Integer numeroTitulo) {
		this.numeroTitulo = numeroTitulo;
	}
	
	public Integer getIdProveedorAnterior() {
		return idProveedorAnterior;
	}

	public void setIdProveedorAnterior(Integer idProveedorAnterior) {
		this.idProveedorAnterior = idProveedorAnterior;
	}

	public Integer getIdProveedorNuevo() {
		return idProveedorNuevo;
	}

	public void setIdProveedorNuevo(Integer idProveedorNuevo) {
		this.idProveedorNuevo = idProveedorNuevo;
	}

	public Integer getContratoAnterior() {
		return contratoAnterior;
	}

	public void setContratoAnterior(Integer contratoAnterior) {
		this.contratoAnterior = contratoAnterior;
	}

	public Integer getGrupoAnterior() {
		return grupoAnterior;
	}

	public void setGrupoAnterior(Integer grupoAnterior) {
		this.grupoAnterior = grupoAnterior;
	}

	public Integer getContratoNuevo() {
		return contratoNuevo;
	}

	public void setContratoNuevo(Integer contratoNuevo) {
		this.contratoNuevo = contratoNuevo;
	}

	public Integer getGrupoNuevo() {
		return grupoNuevo;
	}

	public void setGrupoNuevo(Integer grupoNuevo) {
		this.grupoNuevo = grupoNuevo;
	}

	public String getTituloOriginal() {
		return tituloOriginal;
	}

	public void setTituloOriginal(String tituloOriginal) {
		this.tituloOriginal = tituloOriginal;
	}

	public String getTituloCastellano() {
		return tituloCastellano;
	}

	public void setTituloCastellano(String tituloCastellano) {
		this.tituloCastellano = tituloCastellano;
	}

	public List<ClonarGrillaResponse> getRemitosGrilla() {
		return remitosGrilla;
	}

	public void setRemitosGrilla(List<ClonarGrillaResponse> remitosGrilla) {
		this.remitosGrilla = remitosGrilla;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
	
}
