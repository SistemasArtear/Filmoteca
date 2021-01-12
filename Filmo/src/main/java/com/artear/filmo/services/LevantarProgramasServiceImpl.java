package com.artear.filmo.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artear.filmo.bo.programacion.LevantarExhibicionesRequest;
import com.artear.filmo.bo.programacion.ValidacionLevantarExhibicionResponse;
import com.artear.filmo.daos.interfaces.LevantarProgramasDao;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.exceptions.util.ErrorFilmo;
import com.artear.filmo.exceptions.util.ErrorUtils;
import com.artear.filmo.services.interfaces.LevantarProgramasService;

/**
 * 
 * @author flvaldes
 * 
 */
@Transactional
@Service("levantarProgramasService")
public class LevantarProgramasServiceImpl implements LevantarProgramasService {

	@Autowired
	private LevantarProgramasDao levantarProgramasDao;

	@Override
	public Date obtenerMayorFechaProgramada(String senial, Integer codPrograma) {
		try {
			Date fecha = levantarProgramasDao.obtenerMayorFechaProgramada(
					senial, codPrograma);
			if (fecha == null || fecha.before(Calendar.getInstance().getTime())) {
				Calendar cal = Calendar.getInstance();
				cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
						cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				fecha = cal.getTime();
			}
			return fecha;
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<ValidacionLevantarExhibicionResponse> validacionLevantarExhibiciones(
			LevantarExhibicionesRequest request) throws Exception {
		try {
			return levantarProgramasDao.validacionLevantarExhibiciones(request);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public void levantarExhibiciones(LevantarExhibicionesRequest request)
			throws Exception {
		try {
			levantarProgramasDao.levantarExhibiciones(request);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

}
