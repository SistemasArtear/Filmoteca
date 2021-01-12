package com.artear.filmo.web.action;

import static com.artear.filmo.constants.ApplicationConstants.ERROR_INESPERADO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.artear.filmo.bo.activar.titulo.vencido.BuscarTitulosRequest;
import com.artear.filmo.bo.activar.titulo.vencido.ContratoParaTitulo;
import com.artear.filmo.bo.activar.titulo.vencido.Titulo;
import com.artear.filmo.bo.activar.titulo.vencido.VisualizarModificacionContratosResponse;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.services.interfaces.ActivarTituloVencidoService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class ActivarTituloVencidoAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	// OUT
	private String message;
	private String warningValidarContrato;
	private List<ContratoParaTitulo> contratosParaTitulo;
	private List<Titulo> listadoTitulos;
	private List<VisualizarModificacionContratosResponse> visualizarModificacionContratosResponse;
	
	// IN
	private String senial;
	private String clave;
	private Integer contrato;
	private BuscarTitulosRequest buscarTitulosRequest;

	@Autowired
	private ActivarTituloVencidoService activarTituloVencidoService;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getWarningValidarContrato() {
		return warningValidarContrato;
	}
	
	public void setWarningValidarContrato(String warningValidarContrato) {
		this.warningValidarContrato = warningValidarContrato;
	}
	
	public List<ContratoParaTitulo> getContratosParaTitulo() {
		return contratosParaTitulo;
	}

	public void setContratosParaTitulo(List<ContratoParaTitulo> contratosParaTitulo) {
		this.contratosParaTitulo = contratosParaTitulo;
	}

	public List<Titulo> getListadoTitulos() {
		return listadoTitulos;
	}

	public void setListadoTitulos(List<Titulo> listadoTitulos) {
		this.listadoTitulos = listadoTitulos;
	}

	public List<VisualizarModificacionContratosResponse> getVisualizarModificacionContratosResponse() {
		return visualizarModificacionContratosResponse;
	}
	
	public void setVisualizarModificacionContratosResponse(List<VisualizarModificacionContratosResponse> visualizarModificacionContratosResponse) {
		this.visualizarModificacionContratosResponse = visualizarModificacionContratosResponse;
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
	
	public Integer getContrato() {
		return contrato;
	}
	
	public void setContrato(Integer contrato) {
		this.contrato = contrato;
	}

	public BuscarTitulosRequest getBuscarTitulosRequest() {
		return buscarTitulosRequest;
	}

	public void setBuscarTitulosRequest(BuscarTitulosRequest buscarTitulosRequest) {
		this.buscarTitulosRequest = buscarTitulosRequest;
	}

	public String buscarTitulos() {
		try {
			listadoTitulos = activarTituloVencidoService.buscarTitulos(buscarTitulosRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String buscarContratosParaElTitulo() {
		try {
			contratosParaTitulo = activarTituloVencidoService.buscarContratosParaElTitulo(senial, clave);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	public String validarContrato() {
		try {
			warningValidarContrato = activarTituloVencidoService.validarContrato(senial, clave, contrato);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	public String visualizarModificacionContratos() {
		try {
			visualizarModificacionContratosResponse = activarTituloVencidoService.visualizarModificacionContratos(senial, clave, contrato);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

}