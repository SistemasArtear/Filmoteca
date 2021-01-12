package com.artear.filmo.web.action;

import static com.artear.filmo.constants.ApplicationConstants.ERROR_INESPERADO;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artear.filmo.bo.programacion.LevantarExhibicionesRequest;
import com.artear.filmo.bo.programacion.TituloProgramado;
import com.artear.filmo.bo.programacion.ValidacionLevantarExhibicionResponse;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.services.interfaces.ArmadoDeProgramacionService;
import com.artear.filmo.services.interfaces.LevantarProgramasService;
import com.opensymphony.xwork2.ActionSupport;

@Service
public class LevantarProgramasAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	// IN
	private String senial;
	private Integer codPrograma;
	private Date fechaDesde;
	private Date fechaHasta;

	// OUT
	private String message;
	private Date mayorFecha;
	private List<ValidacionLevantarExhibicionResponse> validacionLevantarExhibicionesResponse;

	// IN/OUT
	private List<TituloProgramado> titulos;

	@Autowired
	private LevantarProgramasService levantarProgramasService;

	@Autowired
	private ArmadoDeProgramacionService armadoDeProgramacionService;

	public String obtenerMayorFechaProgramada() {
		try {
			mayorFecha = levantarProgramasService.obtenerMayorFechaProgramada(
					senial, codPrograma);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String obtenerGrillaProgramada() {
		try {
			titulos = armadoDeProgramacionService.obtenerGrillaProgramada(
					senial, codPrograma, fechaDesde, fechaHasta);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String validacionLevantarExhibiciones() {
		try {
			validacionLevantarExhibicionesResponse = levantarProgramasService
					.validacionLevantarExhibiciones(new LevantarExhibicionesRequest(
							titulos, codPrograma, senial));
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String levantarExhibiciones() {
		try {
			levantarProgramasService
					.levantarExhibiciones(new LevantarExhibicionesRequest(
							titulos, codPrograma, senial));
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String getSenial() {
		return senial;
	}

	public void setSenial(String senial) {
		this.senial = senial;
	}

	public Integer getCodPrograma() {
		return codPrograma;
	}

	public void setCodPrograma(Integer codPrograma) {
		this.codPrograma = codPrograma;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getMayorFecha() {
		return mayorFecha;
	}

	public void setMayorFecha(Date mayorFecha) {
		this.mayorFecha = mayorFecha;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public List<TituloProgramado> getTitulos() {
		return titulos;
	}

	public void setTitulos(List<TituloProgramado> titulos) {
		this.titulos = titulos;
	}

	public List<ValidacionLevantarExhibicionResponse> getValidacionLevantarExhibicionesResponse() {
		return validacionLevantarExhibicionesResponse;
	}

	public void setValidacionLevantarExhibicionesResponse(
			List<ValidacionLevantarExhibicionResponse> validacionLevantarExhibicionesResponse) {
		this.validacionLevantarExhibicionesResponse = validacionLevantarExhibicionesResponse;
	}

}