package com.artear.filmo.services;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artear.filmo.bo.programacion.Horario;
import com.artear.filmo.bo.programacion.HorariosProgramaRequest;
import com.artear.filmo.bo.programacion.Pagination;
import com.artear.filmo.bo.programacion.Programa;
import com.artear.filmo.bo.programacion.ProgramasPagination;
import com.artear.filmo.daos.interfaces.HorariosDeProgramasDao;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.exceptions.util.ErrorFilmo;
import com.artear.filmo.exceptions.util.ErrorUtils;
import com.artear.filmo.services.interfaces.HorariosDeProgramasService;

/**
 * 
 * @author flvaldes
 * 
 */
@Transactional
@Service("horariosDeProgramasService")
public class HorariosDeProgramasServiceImpl implements
		HorariosDeProgramasService {

	private static final String HORARIOS_VACIOS_ERROR_CODE = "HV";
	private static final String HORARIOS_VACIOS_ERROR_MESSAGE ="Todos los horarios estan vacios.";
	
	@Autowired
	private HorariosDeProgramasDao horariosDeProgramasDao;

	@Override
	public List<Programa> obtenerProgramasConHorariosPor(String idSenial,
			Integer codPrograma) {
		try {
			return horariosDeProgramasDao.obtenerProgramasConHorariosPor(
					idSenial, codPrograma);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<Programa> obtenerProgramasConHorariosPor(String idSenial,
			String descripcion) {
		try {
			return horariosDeProgramasDao.obtenerProgramasConHorariosPor(
					idSenial, descripcion);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<Horario> obtenerHorariosPrograma(String idSenial,
			Integer codPrograma) {
		try {
			return horariosDeProgramasDao.obtenerHorariosDePrograma(idSenial,
					codPrograma);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public void eliminarHorariosDeProgramacion(String idSenial,
			Integer codPrograma) {
		try {
			horariosDeProgramasDao.eliminarHorariosDeProgramacion(idSenial,
					codPrograma);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public void modificarHorariosDeProgramacion(HorariosProgramaRequest horarios) {
		try {
			horariosDeProgramasDao.modificarHorariosDeProgramacion(
					horarios.getSenial(), horarios.getCodPrograma(),
					horarios.getHorarios());
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public void altaHorariosDeProgramacion(HorariosProgramaRequest horarios) {
		try {
			horariosDeProgramasDao.altaHorariosDeProgramacion(
					horarios.getSenial(), horarios.getCodPrograma(),
					horarios.getHorarios());
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public ProgramasPagination obtenerMaestroProgramasPor(Integer codPrograma,
			Pagination paginationRequest) {
		try {
			return horariosDeProgramasDao.obtenerMaestroProgramasPor(
					codPrograma, paginationRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public ProgramasPagination obtenerMaestroProgramasPor(String descripcion,
			Pagination paginationRequest) {
		try {
			return horariosDeProgramasDao.obtenerMaestroProgramasPor(
					descripcion, paginationRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public Boolean validarFechaParaModificarHorariosPrograma(HorariosProgramaRequest horarios) {
		Iterator<Horario> iterHor = horarios.getHorarios().iterator();
		boolean horariosVacios = true;
		while(iterHor.hasNext() && horariosVacios){
			Horario horario = iterHor.next();
			if (!horario.getDesde().equals(horario.getHasta())){
				horariosVacios = false;
			}
		}
		try {
			if (horariosVacios){
				throw new RuntimeException(HORARIOS_VACIOS_ERROR_CODE);
			}
		
			return horariosDeProgramasDao.validarFechaParaModificarHorariosPrograma(
					horarios.getSenial(), horarios.getCodPrograma(),
					horarios.getHorarios());
		} catch (RuntimeException e) {
			ErrorFilmo error = new ErrorFilmo();
			if (e.getMessage().equals(HORARIOS_VACIOS_ERROR_CODE)){
				error.setMensaje(HORARIOS_VACIOS_ERROR_MESSAGE);
			}
			else {
				error = ErrorUtils.processMessageError(e);
			}
			
			throw new DataBaseException(error);
		}
	}

	@Override
	public void modificarNuevaFecha(HorariosProgramaRequest horarios,
			Date nuevaFecha) {
		try {
			horariosDeProgramasDao.modificarNuevaFecha(horarios.getSenial(),
					horarios.getCodPrograma(), horarios.getHorarios(),
					nuevaFecha);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public Boolean validarFechaParaEliminarHorariosPrograma(HorariosProgramaRequest horarios) {
		try {
			return horariosDeProgramasDao.validarFechaParaEliminarHorariosPrograma(
					horarios.getSenial(), horarios.getCodPrograma());
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public void eliminarNuevaFecha(HorariosProgramaRequest horarios,
			Date fecha) {
		try {
			horariosDeProgramasDao.eliminarNuevaFecha(horarios.getSenial(),
					horarios.getCodPrograma(), fecha);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

}
