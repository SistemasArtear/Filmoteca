package com.artear.filmo.web.action;

import static com.artear.filmo.constants.ApplicationConstants.ERROR_INESPERADO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.artear.filmo.bo.trabajar.titulos.CodDesc;
import com.artear.filmo.bo.trabajar.titulos.Contrato;
import com.artear.filmo.bo.trabajar.titulos.ContratoView;
import com.artear.filmo.bo.trabajar.titulos.DetalleContrato;
import com.artear.filmo.bo.trabajar.titulos.FichaCinematograficaView;
import com.artear.filmo.bo.trabajar.titulos.OperacionTitulo;
import com.artear.filmo.bo.trabajar.titulos.TituloABM;
import com.artear.filmo.bo.trabajar.titulos.TituloGrillaResponse;
import com.artear.filmo.bo.trabajar.titulos.TituloView;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.services.interfaces.TrabajarConTitulosService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class TrabajarConTitulosAction extends ActionSupport {

	private static final long serialVersionUID = -3477878624376919717L;

	// OUT
	private List<TituloGrillaResponse> titulosGrilla;
	private String message;
	private String stackTrace;
	private TituloView tituloView;
	private ContratoView contratoView;
	private List<Contrato> contratos;
	private List<CodDesc> tiposDeTitulos;
	private List<CodDesc> calificacionesOficiales;
	private List<CodDesc> fichasCinematograficas;
	private FichaCinematograficaView fichaCinematograficaView;
	private DetalleContrato detalleContrato;

	// IN
	private String descripcion;
	private TituloABM titulo;
	private OperacionTitulo operacion;
	private String clave;
	private Integer codigoContrato;
	private String codigoFicha;
	private String senial;

	@Autowired
	private TrabajarConTitulosService trabajarConTitulosService;

	public String obtenerTitulosCastellanoPorDescripcion() throws Exception {
		try {
			titulosGrilla = trabajarConTitulosService
					.obtenerTitulosCastellanoPorDescripcion(senial, descripcion);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String obtenerTitulosOriginalPorDescripcion() throws Exception {
		try {
			titulosGrilla = trabajarConTitulosService
					.obtenerTitulosOriginalPorDescripcion(senial, descripcion);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String obtenerTitulosCastellanoPorCodigo() throws Exception {
		try {
			titulosGrilla = trabajarConTitulosService
					.obtenerTitulosCastellanoPorCodigo(senial, clave);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
			stackTrace = e.getMessage() + e.getCause().getMessage();
		}
		return SUCCESS;
	}

	public String obtenerTitulosOriginalPorCodigo() throws Exception {
		try {
			titulosGrilla = trabajarConTitulosService
					.obtenerTitulosOriginalPorCodigo(senial, clave);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
			stackTrace = e.getMessage() + e.getCause().getMessage();
		}
		return SUCCESS;
	}

	public String abmTitulo() throws Exception {
		try {
			titulo.setCalificacionOficial(titulo.getCalificacionOficial().toUpperCase());
			titulo.setTituloCastellano(titulo.getTituloCastellano().toUpperCase());
			titulo.setTituloOriginal(titulo.getTituloOriginal().toUpperCase());
			trabajarConTitulosService.abmTitulo(titulo, operacion);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String cargarTitulo() throws Exception {
		try {
			tituloView = trabajarConTitulosService.cargarTitulo(senial, clave);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String cargarContrato() throws Exception {
		try {
			contratoView = trabajarConTitulosService.cargarContrato(senial,
					clave, codigoContrato);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String obtenerContratos() throws Exception {
		try {
			contratos = trabajarConTitulosService.obtenerContratos(senial,
					clave, codigoContrato);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String obtenerTiposDeTitulos() throws Exception {
		try {
			tiposDeTitulos = trabajarConTitulosService
					.obtenerTiposDeTitulos(descripcion);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String obtenerCalificacionesOficiales() throws Exception {
		try {
			calificacionesOficiales = trabajarConTitulosService
					.obtenerCalificacionesOficiales(descripcion);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String obtenerFichasCinematograficasCast() throws Exception {
		try {
			fichasCinematograficas = trabajarConTitulosService
					.obtenerFichasCinematograficasCast(descripcion);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String obtenerFichasCinematograficasOriginal() throws Exception {
		try {
			fichasCinematograficas = trabajarConTitulosService
					.obtenerFichasCinematograficasOriginal(descripcion);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String cargarFichaCinematografica() throws Exception {
		try {
			fichaCinematograficaView = trabajarConTitulosService
					.cargarFichaCinematografica(codigoFicha);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String cargarDetalleContrato() throws Exception {
		try {
			detalleContrato = trabajarConTitulosService.cargarDetalleContrato(
					clave, codigoContrato, senial);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public List<TituloGrillaResponse> getTitulosGrilla() {
		return titulosGrilla;
	}

	public void setTitulosGrilla(List<TituloGrillaResponse> titulosGrilla) {
		this.titulosGrilla = titulosGrilla;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public TituloABM getTitulo() {
		return titulo;
	}

	public void setTitulo(TituloABM titulo) {
		this.titulo = titulo;
	}

	public OperacionTitulo getOperacion() {
		return operacion;
	}

	public void setOperacion(OperacionTitulo operacion) {
		this.operacion = operacion;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public ContratoView getContratoView() {
		return contratoView;
	}

	public void setContratoView(ContratoView contratoView) {
		this.contratoView = contratoView;
	}

	public Integer getCodigoContrato() {
		return codigoContrato;
	}

	public void setCodigoContrato(Integer codigoContrato) {
		this.codigoContrato = codigoContrato;
	}

	public TituloView getTituloView() {
		return tituloView;
	}

	public void setTituloView(TituloView tituloView) {
		this.tituloView = tituloView;
	}

	public List<Contrato> getContratos() {
		return contratos;
	}

	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
	}

	public List<CodDesc> getTiposDeTitulos() {
		return tiposDeTitulos;
	}

	public void setTiposDeTitulos(List<CodDesc> tiposDeTitulos) {
		this.tiposDeTitulos = tiposDeTitulos;
	}

	public List<CodDesc> getCalificacionesOficiales() {
		return calificacionesOficiales;
	}

	public void setCalificacionesOficiales(List<CodDesc> calificacionesOficiales) {
		this.calificacionesOficiales = calificacionesOficiales;
	}

	public List<CodDesc> getFichasCinematograficas() {
		return fichasCinematograficas;
	}

	public void setFichasCinematograficas(List<CodDesc> fichasCinematograficas) {
		this.fichasCinematograficas = fichasCinematograficas;
	}

	public FichaCinematograficaView getFichaCinematograficaView() {
		return fichaCinematograficaView;
	}

	public void setFichaCinematograficaView(
			FichaCinematograficaView fichaCinematograficaView) {
		this.fichaCinematograficaView = fichaCinematograficaView;
	}

	public String getCodigoFicha() {
		return codigoFicha;
	}

	public void setCodigoFicha(String codigoFicha) {
		this.codigoFicha = codigoFicha;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public DetalleContrato getDetalleContrato() {
		return detalleContrato;
	}

	public void setDetalleContrato(DetalleContrato detalleContrato) {
		this.detalleContrato = detalleContrato;
	}

	public String getSenial() {
		return senial;
	}

	public void setSenial(String senial) {
		this.senial = senial;
	}

}
