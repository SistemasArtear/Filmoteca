package com.artear.filmo.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artear.filmo.bo.programacion.HorarioEmisionGrilla;
import com.artear.filmo.bo.programacion.Programa;
import com.artear.filmo.daos.interfaces.ModificarHorariosEmisionGrillaDao;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.exceptions.util.ErrorFilmo;
import com.artear.filmo.exceptions.util.ErrorUtils;
import com.artear.filmo.services.interfaces.ModificarHorariosDeEmisionGrillaService;

/**
 * 
 * @author flvaldes
 * 
 */
@Transactional
@Service("modificarHorariosDeEmisionGrillaService")
public class ModificarHorariosDeEmisionGrillaServiceImpl implements
		ModificarHorariosDeEmisionGrillaService {

	@Autowired
	private ModificarHorariosEmisionGrillaDao modificarHorariosEmisionGrillaDao;

	@Override
	public List<Programa> buscarProgramasPorDescripcion(String senial,
			String descripcion) {
		try {
			return modificarHorariosEmisionGrillaDao
					.buscarProgramasPorDescripcion(senial, descripcion);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public HorarioEmisionGrilla obtenerHorarioEmisionPrograma(String senial,
			Integer codPrograma, Date fecha) {
		try {
			return modificarHorariosEmisionGrillaDao
					.obtenerHorarioEmisionPrograma(senial, codPrograma, fecha);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public void modificarHorarioEmisionPrograma(String senial,
			Integer codPrograma, Date fecha, Integer desde, Integer hasta) {
		try {
			modificarHorariosEmisionGrillaDao.modificarHorarioEmisionPrograma(
					senial, codPrograma, fecha, desde, hasta);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

}
