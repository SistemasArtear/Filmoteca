package com.artear.filmo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artear.filmo.bo.programacion.BuscarTitulosProgramasRequest;
import com.artear.filmo.bo.programacion.DesconfirmarRequest;
import com.artear.filmo.bo.programacion.ProgramaClaveResponse;
import com.artear.filmo.bo.programacion.ProgramaCodigoResponse;
import com.artear.filmo.bo.programacion.TituloPrograma;
import com.artear.filmo.daos.interfaces.DesconfirmarSinAmortizacionDao;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.exceptions.util.ErrorFilmo;
import com.artear.filmo.exceptions.util.ErrorUtils;
import com.artear.filmo.services.interfaces.DesconfirmarSinAmortizacionService;

/**
 * 
 * @author flvaldes
 * 
 */
@Transactional
@Service("desconfirmarSinAmortizacionService")
public class DesconfirmarSinAmortizacionServiceImpl implements
		DesconfirmarSinAmortizacionService {

	@Autowired
	private DesconfirmarSinAmortizacionDao desconfirmarSinAmortizacionDao;

	@Override
	public List<ProgramaClaveResponse> buscarProgramasClave(String descripcion) {
		try {
			return desconfirmarSinAmortizacionDao
					.buscarProgramasClave(descripcion);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<ProgramaCodigoResponse> buscarProgramasCodigo(String descripcion, String senial) {
		try {
			return desconfirmarSinAmortizacionDao
					.buscarProgramasCodigo(descripcion, senial);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<TituloPrograma> buscarTitulosProgramas(
			BuscarTitulosProgramasRequest request) {
		try {
			return desconfirmarSinAmortizacionDao
					.buscarTitulosProgramas(request);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public void desconfirmar(DesconfirmarRequest request) {
		try {
			desconfirmarSinAmortizacionDao.desconfirmar(request);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

}
