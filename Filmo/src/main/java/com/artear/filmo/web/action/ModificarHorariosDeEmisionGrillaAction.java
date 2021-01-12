package com.artear.filmo.web.action;

import static com.artear.filmo.constants.ApplicationConstants.ERROR_INESPERADO;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.artear.filmo.bo.programacion.HorarioEmisionGrilla;
import com.artear.filmo.bo.programacion.Programa;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.services.interfaces.ModificarHorariosDeEmisionGrillaService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class ModificarHorariosDeEmisionGrillaAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	// OUT
	private String message;
	private List<Programa> programas;
	private HorarioEmisionGrilla horario;
	// IN
	private String senial;
	private String descripcion;
	private Integer codPrograma;
	private Date fecha;
	private Integer desde;
	private Integer hasta;

	@Autowired
	private ModificarHorariosDeEmisionGrillaService modificarHorariosDeEmisionGrillaService;

	public String buscarProgramasPorDescripcion() throws Exception {
		try {
			programas = modificarHorariosDeEmisionGrillaService
					.buscarProgramasPorDescripcion(senial, descripcion);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String obtenerHorarioEmisionPrograma() throws Exception {
		try {
			horario = modificarHorariosDeEmisionGrillaService
					.obtenerHorarioEmisionPrograma(senial, codPrograma, fecha);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String modificarHorarioEmisionPrograma() throws Exception {
		try {
			if (!validarHorarios()) {
				message = "LOS HORARIOS SON INCORRECTOS";
				return SUCCESS;
			}
			modificarHorariosDeEmisionGrillaService
					.modificarHorarioEmisionPrograma(senial, codPrograma,
							fecha, desde, hasta);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public boolean validarHorarios() {
		if ((desde > hasta) || (desde > 475900) || (hasta > 475900)) {
			return false;
		}
		return true;
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

	public List<Programa> getProgramas() {
		return programas;
	}

	public void setProgramas(List<Programa> programas) {
		this.programas = programas;
	}

	public Integer getCodPrograma() {
		return codPrograma;
	}

	public void setCodPrograma(Integer codPrograma) {
		this.codPrograma = codPrograma;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public HorarioEmisionGrilla getHorario() {
		return horario;
	}

	public void setHorario(HorarioEmisionGrilla horario) {
		this.horario = horario;
	}

	public Integer getDesde() {
		return desde;
	}

	public void setDesde(Integer desde) {
		this.desde = desde;
	}

	public Integer getHasta() {
		return hasta;
	}

	public void setHasta(Integer hasta) {
		this.hasta = hasta;
	}

	public String getSenial() {
		return senial;
	}

	public void setSenial(String senial) {
		this.senial = senial;
	}

}
