package com.artear.filmo.web.action;

import static com.artear.filmo.constants.ApplicationConstants.ERROR_INESPERADO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.artear.filmo.bo.contratos.BuscarTipoDeCosteoRequest;
import com.artear.filmo.bo.contratos.Costeo;
import com.artear.filmo.bo.contratos.CosteoExcedente;
import com.artear.filmo.bo.contratos.CosteoRating;
import com.artear.filmo.bo.contratos.TipoDeCosteoABMExcedenteRequest;
import com.artear.filmo.bo.contratos.TipoDeCosteoABMRatingRequest;
import com.artear.filmo.bo.contratos.TipoDeCosteoABMRequest;
import com.artear.filmo.bo.contratos.TipoDeCosteoEliminarRequest;
import com.artear.filmo.bo.contratos.TipoDeCosteoRegistroRequest;
import com.artear.filmo.bo.contratos.TipoDeCosteoValidarSeleccionRequest;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.services.interfaces.TipoDeCosteoService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class TipoDeCosteoAction extends ActionSupport {

	private static final long serialVersionUID = -8020322479137390860L;
	
	private String message;
	private BuscarTipoDeCosteoRequest buscarTipoDeCosteoRequest;
	private TipoDeCosteoValidarSeleccionRequest validarSeleccion;
	private TipoDeCosteoRegistroRequest agregarRegistroRequest;
	private TipoDeCosteoEliminarRequest eliminarRegistroRequest;
	private TipoDeCosteoABMExcedenteRequest ABMExcedenteRequest;
	private TipoDeCosteoABMRatingRequest ABMRatingRequest;
	private TipoDeCosteoABMRequest ABMRequest;
	private String tipoDeCosteo;
	private List<Costeo> lista;//se esa tanto paara la carga del tipo cfm y mixto
	private List<CosteoExcedente> listaExcedente;
	private List<CosteoRating> listaRating;
	
	@Autowired
	private TipoDeCosteoService tipoDeCosteoService;
	
	public String buscarTipoCosteoByGrupoContratoSenial() {
		try {
			tipoDeCosteo = tipoDeCosteoService.buscarTipoCosteoByGrupo(buscarTipoDeCosteoRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	public String buscarDescripcionCanje() {
		try {
			message = tipoDeCosteoService.buscarDescripcionCanje(buscarTipoDeCosteoRequest);//solo usa el numero de contrato
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	public String obtenerRegistrosCFM() {
		try {
			lista = tipoDeCosteoService.obtenerRegistrosCFM(buscarTipoDeCosteoRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	public String obtenerRegistrosABM() {
		try {
			lista = tipoDeCosteoService.obtenerRegistrosABM(ABMRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	public String obtenerRegistrosRatingABM() {
		try {
			listaRating = tipoDeCosteoService.obtenerRegistrosRatingABM(ABMRatingRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	public String obtenerRegistrosExcedenteABM() {
		try {
			listaExcedente = tipoDeCosteoService.obtenerRegistrosExcedenteABM(ABMExcedenteRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	public String agregarRegistrosCFM() {
		try {
			message = tipoDeCosteoService.agregarRegistrosCFM(agregarRegistroRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String eliminarRegistrosCFM() {
		try {
			message = tipoDeCosteoService.eliminarRegistrosCFM(eliminarRegistroRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	public String obtenerRegistrosExcedente() {
		try {
			listaExcedente = tipoDeCosteoService.obtenerRegistrosExcedente(buscarTipoDeCosteoRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	public String agregarRegistrosExcedente() {
		try {
			message = tipoDeCosteoService.agregarRegistrosExcedente(agregarRegistroRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	public String eliminarRegistrosExcedente() {
		try {
			message = tipoDeCosteoService.eliminarRegistrosExcedente(eliminarRegistroRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	public String abmRegistroExcedente() {
		try {
			message = tipoDeCosteoService.abmRegistroExcedente(ABMExcedenteRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	public String abmRegistroRating() {
		try {
			message = tipoDeCosteoService.abmRegistroRating(ABMRatingRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	public String abmRegistroFijo() {
		try {
			message = tipoDeCosteoService.abmRegistroCFM(ABMRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	public String abmRegistroMixto() {
		try {
			message = tipoDeCosteoService.abmRegistroMixto(ABMRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	public String obtenerRegistrosRating() {
		try {
			listaRating = tipoDeCosteoService.obtenerRegistrosRating(buscarTipoDeCosteoRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	public String agregarRegistrosRating() {
		try {
			message = tipoDeCosteoService.agregarRegistrosRating(agregarRegistroRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	public String eliminarRegistrosRating() {
		try {
			message = tipoDeCosteoService.eliminarRegistrosRating(eliminarRegistroRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	public String obtenerRegistrosMixto() {
		try {
			lista = tipoDeCosteoService.obtenerRegistrosMixto(buscarTipoDeCosteoRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	public String agregarRegistrosMixto() {
		try {
			message = tipoDeCosteoService.agregarRegistrosMixto(agregarRegistroRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	public String eliminarRegistrosMixto() {
		try {
			message = tipoDeCosteoService.eliminarRegistrosMixto(eliminarRegistroRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	public String validarSeleccion() {
		try {
			message = tipoDeCosteoService.validarSeleccion(validarSeleccion);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BuscarTipoDeCosteoRequest getBuscarTipoDeCosteoRequest() {
		return buscarTipoDeCosteoRequest;
	}

	public void setBuscarTipoDeCosteoRequest(BuscarTipoDeCosteoRequest buscarTipoDeCosteoRequest) {
		this.buscarTipoDeCosteoRequest = buscarTipoDeCosteoRequest;
	}

	public String getTipoDeCosteo() {
		return tipoDeCosteo;
	}

	public void setTipoDeCosteo(String tipoDeCosteo) {
		this.tipoDeCosteo = tipoDeCosteo;
	}

	public List<Costeo> getLista() {
		return lista;
	}

	public void setLista(List<Costeo> lista) {
		this.lista = lista;
	}

	public List<CosteoExcedente> getListaExcedente() {
		return listaExcedente;
	}

	public void setListaExcedente(List<CosteoExcedente> listaExcedente) {
		this.listaExcedente = listaExcedente;
	}

	public List<CosteoRating> getListaRating() {
		return listaRating;
	}

	public void setListaRating(List<CosteoRating> listaRating) {
		this.listaRating = listaRating;
	}

	public TipoDeCosteoService getTipoDeCosteoService() {
		return tipoDeCosteoService;
	}

	public void setTipoDeCosteoService(TipoDeCosteoService tipoDeCosteoService) {
		this.tipoDeCosteoService = tipoDeCosteoService;
	}
	public TipoDeCosteoRegistroRequest getAgregarRegistroRequest() {
		return agregarRegistroRequest;
	}
	public void setAgregarRegistroRequest(TipoDeCosteoRegistroRequest agregarRegistroRequest) {
		this.agregarRegistroRequest = agregarRegistroRequest;
	}
	public TipoDeCosteoEliminarRequest getEliminarRegistroRequest() {
		return eliminarRegistroRequest;
	}
	public void setEliminarRegistroRequest(TipoDeCosteoEliminarRequest eliminarRegistroRequest) {
		this.eliminarRegistroRequest = eliminarRegistroRequest;
	}
	public TipoDeCosteoValidarSeleccionRequest getValidarSeleccion() {
		return validarSeleccion;
	}
	public void setValidarSeleccion(TipoDeCosteoValidarSeleccionRequest validarSeleccion) {
		this.validarSeleccion = validarSeleccion;
	}
	public TipoDeCosteoABMExcedenteRequest getABMExcedenteRequest() {
		return ABMExcedenteRequest;
	}
	public void setABMExcedenteRequest(TipoDeCosteoABMExcedenteRequest aBMExcedenteRequest) {
		ABMExcedenteRequest = aBMExcedenteRequest;
	}
	public TipoDeCosteoABMRatingRequest getABMRatingRequest() {
		return ABMRatingRequest;
	}
	public void setABMRatingRequest(TipoDeCosteoABMRatingRequest aBMRatingRequest) {
		ABMRatingRequest = aBMRatingRequest;
	}
	public TipoDeCosteoABMRequest getABMRequest() {
		return ABMRequest;
	}
	public void setABMRequest(TipoDeCosteoABMRequest aBMRequest) {
		ABMRequest = aBMRequest;
	}
}