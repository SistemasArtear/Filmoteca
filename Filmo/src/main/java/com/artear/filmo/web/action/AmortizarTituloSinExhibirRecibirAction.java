package com.artear.filmo.web.action;

import static com.artear.filmo.constants.ApplicationConstants.ERROR_INESPERADO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.artear.filmo.bo.amortizar.titulossinexhibir.TituloSinExhibirRecibirGrillaResponse;
import com.artear.filmo.bo.amortizar.titulossinexhibir.TituloSinExhibirRecibirView;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.seguridad.services.ServiciosSesionUsuario;
import com.artear.filmo.services.interfaces.AmortizarTituloSinExhibirRecibirService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class AmortizarTituloSinExhibirRecibirAction extends ActionSupport {

	private static final long serialVersionUID = -8797323887112762233L;
	
	private String message;
	private List<TituloSinExhibirRecibirGrillaResponse> titulosGrilla;
	private TituloSinExhibirRecibirView tituloSinExhibirRecibirView;

	private String senial;
	private String clave;
	private String descripcion;
	private Integer contrato;
    private Integer grupo;
    
	@Autowired
	protected ServiciosSesionUsuario servicioSesionUsuario;
	@Autowired
	private AmortizarTituloSinExhibirRecibirService amortizarTituloSinExhibirRecibirService;
	
    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<TituloSinExhibirRecibirGrillaResponse> getTitulosGrilla() {
		return titulosGrilla;
	}
	
	public void setTitulosGrilla(List<TituloSinExhibirRecibirGrillaResponse> titulosGrilla) {
		this.titulosGrilla = titulosGrilla;
	}
	
	public TituloSinExhibirRecibirView getTituloSinExhibirRecibirView() {
		return tituloSinExhibirRecibirView;
	}

	public void setTituloSinExhibirRecibirView(TituloSinExhibirRecibirView tituloSinExhibirRecibirView) {
		this.tituloSinExhibirRecibirView = tituloSinExhibirRecibirView;
	}

	public String getSenial() {
		return senial;
	}
	
	public void setSenial(String senial) {
		this.senial = senial;
	}
	
	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Integer getContrato() {
		return contrato;
	}

	public void setContrato(Integer contrato) {
		this.contrato = contrato;
	}

    public Integer getGrupo() {
		return grupo;
	}
    
    public void setGrupo(Integer grupo) {
		this.grupo = grupo;
	}
    
	/**
     * Búsqueda de títulos castellano por código
     * @author cetorres
     * 
     * @useCase FP180 - Amortizar título sin exhibir / recibir
     */
	public String obtenerTitulosCastellanoPorCodigo() throws Exception {
		try {
			titulosGrilla = amortizarTituloSinExhibirRecibirService.obtenerTitulosCastellanoPorCodigo(senial, clave);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

    /**
     * Búsqueda de títulos original por código
     * @author cetorres
     * 
     * @useCase FP180 - Amortizar título sin exhibir / recibir
     */
	public String obtenerTitulosOriginalPorCodigo() throws Exception {
		try {
			titulosGrilla = amortizarTituloSinExhibirRecibirService.obtenerTitulosOriginalPorCodigo(senial, clave);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
    /**
     * Búsqueda de títulos castellano por descripción
     * @author cetorres
     * 
     * @useCase FP180 - Amortizar título sin exhibir / recibir
     */
	public String obtenerTitulosCastellanoPorDescripcion() throws Exception {
		try {
			titulosGrilla = amortizarTituloSinExhibirRecibirService.obtenerTitulosCastellanoPorDescripcion(senial, descripcion);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

    /**
     * Búsqueda de títulos original por descripción
     * @author cetorres
     * 
     * @useCase FP180 - Amortizar título sin exhibir / recibir
     */
	public String obtenerTitulosOriginalPorDescripcion() throws Exception {
		try {
			titulosGrilla = amortizarTituloSinExhibirRecibirService.obtenerTitulosOriginalPorDescripcion(senial, descripcion);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	public String obtenerTituloSinExhibirRecibirConCapitulos() throws Exception {
		try {
			tituloSinExhibirRecibirView = amortizarTituloSinExhibirRecibirService.obtenerTituloSinExhibirRecibirConCapitulos(senial, contrato, clave);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	public String amortizarTituloSinExhibirRecibir() throws Exception {
		try {
			Boolean result = this.amortizarTituloSinExhibirRecibirService.amortizarTituloSinExhibirRecibir(senial, contrato, clave, grupo);
			if (!result) {
				message = ERROR_INESPERADO;
			}
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

}