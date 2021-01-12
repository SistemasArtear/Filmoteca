package com.artear.filmo.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artear.filmo.bo.common.Capitulo;
import com.artear.filmo.bo.common.Distribuidor;
import com.artear.filmo.bo.common.Titulo;
import com.artear.filmo.bo.trabajar.remitos.ActualizarCantidadRequest;
import com.artear.filmo.bo.trabajar.remitos.EjecutarRemitoRequest;
import com.artear.filmo.bo.trabajar.remitos.EjecutarRemitoResponse;
import com.artear.filmo.bo.trabajar.remitos.ImprimirRemitoRequest;
import com.artear.filmo.bo.trabajar.remitos.ImprimirRemitoResponse;
import com.artear.filmo.bo.trabajar.remitos.ValidarNuevaSenialRequest;
import com.artear.filmo.bo.trabajar.remitos.ValidarNuevaSenialResponse;
import com.artear.filmo.daos.interfaces.TrabajarConRemitosMaterialesDao;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.exceptions.util.ErrorFilmo;
import com.artear.filmo.exceptions.util.ErrorUtils;
import com.artear.filmo.services.interfaces.TrabajarConRemitosMaterialesService;

@Transactional
@Service("trabajarConRemitosMaterialesService")
public class TrabajarConRemitosMaterialesServiceImpl implements TrabajarConRemitosMaterialesService {

	@Autowired
	private TrabajarConRemitosMaterialesDao trabajarConRemitosMaterialesDao;
	
	@Override
	public List<Distribuidor> buscarDistribuidoresParaRemitosSalida(String razonSocialDistribuidor) {
		try {
			return trabajarConRemitosMaterialesDao.buscarDistribuidoresParaRemitosSalida(razonSocialDistribuidor);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<Titulo> buscarTitulosParaCarga(Integer codigoDistribuidor, String descripcionTitulo) {
		try {
			return trabajarConRemitosMaterialesDao.buscarTitulosParaCarga(codigoDistribuidor, descripcionTitulo);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<Capitulo> buscarCapitulosParaCarga(String claveTitulo, Integer capitulo) {
		try {
			return trabajarConRemitosMaterialesDao.buscarCapitulosParaCarga(claveTitulo, capitulo);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public Boolean validarDistribuidor(Integer codigoDistribuidor) {
		try {
			return trabajarConRemitosMaterialesDao.validarDistribuidor(codigoDistribuidor);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public Boolean validarFechaSenial(Date fechaRemito, String senial) {
		try {
			return trabajarConRemitosMaterialesDao.validarFechaSenial(fechaRemito, senial);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public ValidarNuevaSenialResponse validarNuevaSenial(ValidarNuevaSenialRequest validarNuevaSenialRequest) {
		try {
			return trabajarConRemitosMaterialesDao.validarNuevaSenial(validarNuevaSenialRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public void actualizarCantidad(ActualizarCantidadRequest actualizarCantidadRequest) {
		try {
			trabajarConRemitosMaterialesDao.actualizarCantidad(actualizarCantidadRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public EjecutarRemitoResponse ejecutarRemito(EjecutarRemitoRequest ejecutarRemitoRequest) {
		try {
			return trabajarConRemitosMaterialesDao.ejecutarRemito(ejecutarRemitoRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<ImprimirRemitoResponse> imprimirRemito(ImprimirRemitoRequest imprimirRemitoRequest) {
		try {
			return trabajarConRemitosMaterialesDao.imprimirRemito(imprimirRemitoRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

}