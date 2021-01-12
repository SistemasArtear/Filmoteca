package com.artear.filmo.services.interfaces;

import java.util.Date;
import java.util.List;

import com.artear.filmo.bo.programacion.Horario;
import com.artear.filmo.bo.programacion.HorariosProgramaRequest;
import com.artear.filmo.bo.programacion.Pagination;
import com.artear.filmo.bo.programacion.Programa;
import com.artear.filmo.bo.programacion.ProgramasPagination;

/**
 * 
 * @author flvaldes
 * 
 */
public interface HorariosDeProgramasService {

	List<Programa> obtenerProgramasConHorariosPor(String idSenial,
			Integer codPrograma);

	List<Programa> obtenerProgramasConHorariosPor(String idSenial,
			String descripcion);

	List<Horario> obtenerHorariosPrograma(String idSenial, Integer codPrograma);

	void eliminarHorariosDeProgramacion(String idSenial, Integer codPrograma);

	void modificarHorariosDeProgramacion(HorariosProgramaRequest horarios);

	void altaHorariosDeProgramacion(HorariosProgramaRequest horarios);

	ProgramasPagination obtenerMaestroProgramasPor(Integer codPrograma,
			Pagination paginationRequest);

	ProgramasPagination obtenerMaestroProgramasPor(String descripcion,
			Pagination paginationRequest);

	void modificarNuevaFecha(HorariosProgramaRequest horarios, Date nuevaFecha);

	Boolean validarFechaParaModificarHorariosPrograma(HorariosProgramaRequest horarios);

	Boolean validarFechaParaEliminarHorariosPrograma(
			HorariosProgramaRequest horarios);

	void eliminarNuevaFecha(HorariosProgramaRequest horarios, Date fecha);

}
