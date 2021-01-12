package com.artear.filmo.daos.interfaces;

import java.util.Date;
import java.util.List;

import com.artear.filmo.bo.programacion.Horario;
import com.artear.filmo.bo.programacion.Pagination;
import com.artear.filmo.bo.programacion.Programa;
import com.artear.filmo.bo.programacion.ProgramasPagination;

/**
 * 
 * @author flvaldes
 * 
 */
public interface HorariosDeProgramasDao {

	List<Programa> obtenerProgramasConHorariosPor(String idSenial,
			Integer codPrograma);

	List<Programa> obtenerProgramasConHorariosPor(String idSenial,
			String descripcion);

	List<Horario> obtenerHorariosDePrograma(String idSenial, Integer codPrograma);

	void eliminarHorariosDeProgramacion(String idSenial, Integer codPrograma);

	void modificarHorariosDeProgramacion(String idSenial, Integer codPrograma,
			List<Horario> horarios);

	void altaHorariosDeProgramacion(String idSenial, Integer codPrograma,
			List<Horario> horarios);

	ProgramasPagination obtenerMaestroProgramasPor(Integer codPrograma,
			Pagination paginationRequest);

	ProgramasPagination obtenerMaestroProgramasPor(String descripcion,
			Pagination paginationRequest);

	Boolean validarFechaParaModificarHorariosPrograma(String idSenial, Integer codPrograma,
			List<Horario> horarios);

	void modificarNuevaFecha(String idSenial, Integer codPrograma,
			List<Horario> horarios, Date nuevaFecha);

	Boolean validarFechaParaEliminarHorariosPrograma(String idSenial,
			Integer codPrograma);

	void eliminarNuevaFecha(String idSenial, Integer codPrograma, Date fecha);


}
