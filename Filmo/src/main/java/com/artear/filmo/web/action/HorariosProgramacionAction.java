package com.artear.filmo.web.action;

import static com.artear.filmo.constants.ApplicationConstants.ERROR_INESPERADO;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.artear.filmo.bo.programacion.Horario;
import com.artear.filmo.bo.programacion.HorariosProgramaRequest;
import com.artear.filmo.bo.programacion.Pagination;
import com.artear.filmo.bo.programacion.Programa;
import com.artear.filmo.bo.programacion.ProgramasPagination;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.services.interfaces.HorariosDeProgramasService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class HorariosProgramacionAction extends ActionSupport {

	private static final long serialVersionUID = -3477878624376919717L;

	// OUT
	private List<Programa> programasConHorarios;
	private List<Programa> maestroProgramas;
	private List<Horario> horariosPrograma;
	private String message;
	private ProgramasPagination programasPagination;
	private Boolean resultadoValidacion;

	// IN
	private String senial;
	private Integer codPrograma;
	private String descPrograma;
	private HorariosProgramaRequest horariosProgramaRequest;
	private Pagination paginationRequest;
	private Date nuevaFecha;

	@Autowired
	private HorariosDeProgramasService horariosDeProgramasService;

	public String buscarProgramasConHorariosPorCodigo() {
		try {
			programasConHorarios = horariosDeProgramasService
					.obtenerProgramasConHorariosPor(senial, codPrograma);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String buscarProgramasConHorariosPorDescripcion() {
		try {
			programasConHorarios = horariosDeProgramasService
					.obtenerProgramasConHorariosPor(senial, descPrograma);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String buscarMaestroProgramasPorCodigo() {
		try {
			programasPagination = horariosDeProgramasService
					.obtenerMaestroProgramasPor(codPrograma, paginationRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String buscarMaestroProgramasPorDescripcion() {
		try {
			maestroProgramas = horariosDeProgramasService
					.obtenerMaestroProgramasPor(descPrograma, paginationRequest)
					.getProgramas();
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String obtenerHorariosPrograma() {
		try {
			horariosPrograma = horariosDeProgramasService
					.obtenerHorariosPrograma(senial, codPrograma);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String validarFechaParaEliminarHorarios() {
		try {
			resultadoValidacion = horariosDeProgramasService
					.validarFechaParaEliminarHorariosPrograma(horariosProgramaRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	public String eliminarNuevafecha() {
		try {
			horariosDeProgramasService.eliminarNuevaFecha(
					horariosProgramaRequest, nuevaFecha);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	public String eliminarHorariosDeProgramacion() {
		try {
			horariosDeProgramasService.eliminarHorariosDeProgramacion(senial,
					codPrograma);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String validarFechaParaModificarHorarios() {
		try {
			resultadoValidacion = horariosDeProgramasService
					.validarFechaParaModificarHorariosPrograma(horariosProgramaRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String modificarNuevafecha() {
		try {
			horariosDeProgramasService.modificarNuevaFecha(
					horariosProgramaRequest, nuevaFecha);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String modificarHorariosProgramas() {
		try {
			horariosDeProgramasService
					.modificarHorariosDeProgramacion(horariosProgramaRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String altaHorariosProgramas() {
		try {
			horariosDeProgramasService
					.altaHorariosDeProgramacion(horariosProgramaRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public List<Horario> getHorariosPrograma() {
		return horariosPrograma;
	}

	public HorariosProgramaRequest getHorariosProgramaRequest() {
		return horariosProgramaRequest;
	}

	public void setHorariosProgramaRequest(
			HorariosProgramaRequest horariosProgramaRequest) {
		this.horariosProgramaRequest = horariosProgramaRequest;
	}

	public void setHorariosPrograma(List<Horario> horariosPrograma) {
		this.horariosPrograma = horariosPrograma;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Programa> getProgramasConHorarios() {
		return programasConHorarios;
	}

	public void setProgramasConHorarios(List<Programa> programasConHorarios) {
		this.programasConHorarios = programasConHorarios;
	}

	public Integer getCodPrograma() {
		return codPrograma;
	}

	public void setCodPrograma(Integer codPrograma) {
		this.codPrograma = codPrograma;
	}

	public ProgramasPagination getProgramasPagination() {
		return programasPagination;
	}

	public void setProgramasPagination(ProgramasPagination programasPagination) {
		this.programasPagination = programasPagination;
	}

	public Pagination getPaginationRequest() {
		return paginationRequest;
	}

	public void setPaginationRequest(Pagination paginationRequest) {
		this.paginationRequest = paginationRequest;
	}

	public String getDescPrograma() {
		return descPrograma;
	}

	public void setDescPrograma(String descPrograma) {
		this.descPrograma = descPrograma;
	}

	public List<Programa> getMaestroProgramas() {
		return maestroProgramas;
	}

	public void setMaestroProgramas(List<Programa> maestroProgramas) {
		this.maestroProgramas = maestroProgramas;
	}

	public String getSenial() {
		return senial;
	}

	public void setSenial(String senial) {
		this.senial = senial;
	}

	public Date getNuevaFecha() {
		return nuevaFecha;
	}

	public void setNuevaFecha(Date nuevaFecha) {
		this.nuevaFecha = nuevaFecha;
	}

	public Boolean getResultadoValidacion() {
		return resultadoValidacion;
	}

	public void setResultadoValidacion(Boolean resultadoValidacion) {
		this.resultadoValidacion = resultadoValidacion;
	}

}
