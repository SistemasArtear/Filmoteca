package com.artear.filmo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artear.filmo.bo.activar.titulo.vencido.BuscarTitulosRequest;
import com.artear.filmo.bo.activar.titulo.vencido.ContratoParaTitulo;
import com.artear.filmo.bo.activar.titulo.vencido.Titulo;
import com.artear.filmo.bo.activar.titulo.vencido.VisualizarModificacionContratosResponse;
import com.artear.filmo.daos.interfaces.ActivarTituloVencidoDao;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.exceptions.util.ErrorFilmo;
import com.artear.filmo.exceptions.util.ErrorUtils;
import com.artear.filmo.services.interfaces.ActivarTituloVencidoService;

/**
 * 
 * @author flvaldes
 * 
 */
@Transactional
@Service("activarTituloVencidoService")
public class ActivarTituloVencidoServiceImpl implements	ActivarTituloVencidoService {

	@Autowired
	private ActivarTituloVencidoDao activarTituloVencidoDao;

	@Override
	public List<ContratoParaTitulo> buscarContratosParaElTitulo(String senial, String clave) {
		try {
			return activarTituloVencidoDao.buscarContratosParaElTitulo(senial, clave);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<Titulo> buscarTitulos(BuscarTitulosRequest request) {
		try {
			return activarTituloVencidoDao.buscarTitulos(request);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public String validarContrato(String senial, String clave, Integer contrato) {
		try {
			return activarTituloVencidoDao.validarContrato(senial, clave, contrato);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<VisualizarModificacionContratosResponse> visualizarModificacionContratos(String senial, String clave, Integer contrato) {
		try {
			return activarTituloVencidoDao.visualizarModificacionContratos(senial, clave, contrato);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

}