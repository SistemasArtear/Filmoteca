package com.artear.filmo.web.action;

import static com.artear.filmo.constants.ApplicationConstants.ERROR_INESPERADO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.artear.filmo.bo.common.AyudaSituar;
import com.artear.filmo.bo.ficha.cinematografica.AyudaFichas;
import com.artear.filmo.bo.ficha.cinematografica.FichaCompleta;
import com.artear.filmo.bo.ficha.cinematografica.FichaListadoResponse;
import com.artear.filmo.constants.TipoBusquedaTitulo;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.services.interfaces.FichaCinematograficaService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author flvaldes
 * 
 */
@Controller
public class FichaCinematograficaAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	// IN
	private TipoBusquedaTitulo tipo;
	private String titulo;
	private Integer idFicha;
	private String descripcion;
	private AyudaFichas ayudaFichas;

	// OUT
	private String message;
	private List<FichaListadoResponse> fichasListado;
	private boolean ok;
	private FichaCompleta fichaCompleta;
	private List<AyudaSituar> listaAyuda;

	@Autowired
	protected FichaCinematograficaService fichaCinematograficaService;

	public String buscarFichas() {
		try {
			fichasListado = fichaCinematograficaService.buscarFichas(tipo,
					titulo);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String eliminarFicha() {
		try {
			ok = fichaCinematograficaService.eliminarFicha(idFicha);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String obtenerFichaCompleta() {
		try {
			fichaCompleta = fichaCinematograficaService
					.obtenerFichaCompleta(idFicha);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String guardarFicha() {
		try {
			ok = fichaCinematograficaService.guardarFicha(fichaCompleta);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String modificarFicha() {
		try {
			ok = fichaCinematograficaService.modificarFicha(fichaCompleta);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String ayudaSituar() {
		try {
			listaAyuda = fichaCinematograficaService.ayudaSituar(descripcion,
					ayudaFichas);
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

	public TipoBusquedaTitulo getTipo() {
		return tipo;
	}

	public void setTipo(TipoBusquedaTitulo tipo) {
		this.tipo = tipo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getIdFicha() {
		return idFicha;
	}

	public void setIdFicha(Integer idFicha) {
		this.idFicha = idFicha;
	}

	public List<FichaListadoResponse> getFichasListado() {
		return fichasListado;
	}

	public void setFichasListado(List<FichaListadoResponse> fichasListado) {
		this.fichasListado = fichasListado;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public FichaCompleta getFichaCompleta() {
		return fichaCompleta;
	}

	public void setFichaCompleta(FichaCompleta fichaCompleta) {
		this.fichaCompleta = fichaCompleta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public AyudaFichas getAyudaFichas() {
		return ayudaFichas;
	}

	public void setAyudaFichas(AyudaFichas ayudaFichas) {
		this.ayudaFichas = ayudaFichas;
	}

	public List<AyudaSituar> getListaAyuda() {
		return listaAyuda;
	}

	public void setListaAyuda(List<AyudaSituar> listaAyuda) {
		this.listaAyuda = listaAyuda;
	}

}