package com.artear.filmo.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artear.filmo.bo.common.Titulo;
import com.artear.filmo.bo.programacion.ListarProgramacionRequest;
import com.artear.filmo.bo.programacion.ListarProgramacionResponse;
import com.artear.filmo.bo.programacion.Programa;
import com.artear.filmo.bo.programacion.desconfirmar.ProcesarDesconfirmacionRequest;
import com.artear.filmo.bo.programacion.desconfirmar.ProcesarDesconfirmacionResponse;
import com.artear.filmo.daos.interfaces.DesconfirmarProgramacionDao;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.exceptions.util.ErrorFilmo;
import com.artear.filmo.exceptions.util.ErrorUtils;
import com.artear.filmo.services.interfaces.DesconfirmarProgramacionService;

@Transactional
@Service("desconfirmarProgramacionService")
public class DesconfirmarProgramacionServiceImpl implements DesconfirmarProgramacionService {

	@Autowired
	private DesconfirmarProgramacionDao desconfirmarProgramacionDao;
	
	@Override
	public List<ListarProgramacionResponse> listarProgramacion(ListarProgramacionRequest listarProgramacionRequest) {
		try {
			return desconfirmarProgramacionDao.listarProgramacion(listarProgramacionRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<Programa> buscarProgramasDesconfirmar(String descripcionPrograma, String senial) {
		try {
			return desconfirmarProgramacionDao.buscarProgramasDesconfirmar(descripcionPrograma, senial);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<Titulo> buscarTitulosDesconfirmar(String descripcionTitulo) {
		try {
			return desconfirmarProgramacionDao.buscarTitulosDesconfirmar(descripcionTitulo);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public ProcesarDesconfirmacionResponse procesarDesconfirmacion(ProcesarDesconfirmacionRequest procesarDesconfirmacionRequest) {
		try {
			BigDecimal pasadas = desconfirmarProgramacionDao.obtenerPasadasPorSegmento(procesarDesconfirmacionRequest);
			
			return desconfirmarProgramacionDao.procesarDesconfirmacion(procesarDesconfirmacionRequest, pasadas);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

}