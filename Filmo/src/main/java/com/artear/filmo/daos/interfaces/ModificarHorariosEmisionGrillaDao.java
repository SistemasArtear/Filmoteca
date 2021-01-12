package com.artear.filmo.daos.interfaces;

import java.util.Date;
import java.util.List;

import com.artear.filmo.bo.programacion.HorarioEmisionGrilla;
import com.artear.filmo.bo.programacion.Programa;

/**
 * 
 * @author flvaldes
 * 
 */
public interface ModificarHorariosEmisionGrillaDao {

	List<Programa> buscarProgramasPorDescripcion(String senial,
			String descripcion);

	HorarioEmisionGrilla obtenerHorarioEmisionPrograma(String senial,
			Integer codPrograma, Date fecha);

	void modificarHorarioEmisionPrograma(String senial, Integer codPrograma,
			Date fecha, Integer desde, Integer hasta);

}
