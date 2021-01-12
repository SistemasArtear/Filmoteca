package com.artear.filmo.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artear.filmo.bo.programacion.ListarProgramacionRequest;
import com.artear.filmo.bo.programacion.ListarProgramacionResponse;
import com.artear.filmo.bo.programacion.confirmar.BuscarContratosRequest;
import com.artear.filmo.bo.programacion.confirmar.BuscarContratosResponse;
import com.artear.filmo.bo.programacion.confirmar.ProcesarConfirmacionRequest;
import com.artear.filmo.bo.programacion.confirmar.ProcesarConfirmacionResponse;
import com.artear.filmo.daos.interfaces.ConfirmarProgramacionDao;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.exceptions.util.ErrorFilmo;
import com.artear.filmo.exceptions.util.ErrorUtils;
import com.artear.filmo.services.interfaces.ConfirmarProgramacionService;

@Transactional
@Service("confirmarProgramacionService")
public class ConfirmarProgramacionServiceImpl implements ConfirmarProgramacionService {

	@Autowired
	private ConfirmarProgramacionDao confirmarProgramacionDao;
	
	@Override
	public List<ListarProgramacionResponse> listarProgramacion(ListarProgramacionRequest listarProgramacionRequest) {
		try {
			return confirmarProgramacionDao.listarProgramacion(listarProgramacionRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public ProcesarConfirmacionResponse procesarConfirmacion(ProcesarConfirmacionRequest procesarConfirmacionRequest) {
		try {
			return confirmarProgramacionDao.procesarConfirmacion(procesarConfirmacionRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public ProcesarConfirmacionResponse procesarConfirmacionGrillaProgramacion(List<ProcesarConfirmacionRequest> listaProcesarConfirmacionRequest) throws Exception {
		try {
			for (ProcesarConfirmacionRequest procesarConfirmacionRequest : listaProcesarConfirmacionRequest) {
				this.confirmarProgramacionDao.procesarRegistrosGrillaProgramacion(procesarConfirmacionRequest);
			}
			
			ProcesarConfirmacionRequest procesarConfirmacionGrilla = new ProcesarConfirmacionRequest();
			procesarConfirmacionGrilla.setSenial(listaProcesarConfirmacionRequest.get(0) != null ? listaProcesarConfirmacionRequest.get(0).getSenial() : null);
			procesarConfirmacionGrilla.setFecha(listaProcesarConfirmacionRequest.get(0) != null ? listaProcesarConfirmacionRequest.get(0).getFecha() : null);
			
			return this.confirmarProgramacionDao.procesarConfirmacionGrillaProgramacion(procesarConfirmacionGrilla);
			
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		} catch (Exception e){
			throw e;
		}
	}

	@Override
	public List<BuscarContratosResponse> buscarContratosParaTitulo(BuscarContratosRequest buscarContratosRequest) {
		try {
			return confirmarProgramacionDao.buscarContratosParaTitulo(buscarContratosRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
}