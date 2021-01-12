package com.artear.filmo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artear.filmo.bo.amortizar.titulossinexhibir.TituloSinExhibirRecibirGrillaResponse;
import com.artear.filmo.bo.amortizar.titulossinexhibir.TituloSinExhibirRecibirView;
import com.artear.filmo.daos.interfaces.AmortizarTituloSinExhibirRecibirDao;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.exceptions.util.ErrorFilmo;
import com.artear.filmo.exceptions.util.ErrorUtils;
import com.artear.filmo.services.interfaces.AmortizarTituloSinExhibirRecibirService;

@Transactional
@Service("amortizarTituloSinExhibirRecibirService")
public class AmortizarTituloSinExhibirRecibirServiceImpl implements AmortizarTituloSinExhibirRecibirService {

	@Autowired
	private AmortizarTituloSinExhibirRecibirDao amortizarTituloSinExhibirRecibirDao;
	
	@Override
	public List<TituloSinExhibirRecibirGrillaResponse> obtenerTitulosCastellanoPorCodigo(String senial, String clave) {
		try {
			return amortizarTituloSinExhibirRecibirDao.obtenerTitulosCastellanoPorCodigo(senial, clave);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<TituloSinExhibirRecibirGrillaResponse> obtenerTitulosOriginalPorCodigo(String senial, String clave) {
		try {
			return amortizarTituloSinExhibirRecibirDao.obtenerTitulosOriginalPorCodigo(senial, clave);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<TituloSinExhibirRecibirGrillaResponse> obtenerTitulosCastellanoPorDescripcion(String senial, String descripcion) {
		try {
			return amortizarTituloSinExhibirRecibirDao.obtenerTitulosCastellanoPorDescripcion(senial, descripcion);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<TituloSinExhibirRecibirGrillaResponse> obtenerTitulosOriginalPorDescripcion(String senial, String descripcion) {
		try {
			return amortizarTituloSinExhibirRecibirDao.obtenerTitulosOriginalPorDescripcion(senial, descripcion);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public TituloSinExhibirRecibirView obtenerTituloSinExhibirRecibirConCapitulos(String senial, Integer contrato,String clave) {
		try {
			return amortizarTituloSinExhibirRecibirDao.obtenerTituloSinExhibirRecibirConCapitulos(senial, contrato, clave);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public Boolean amortizarTituloSinExhibirRecibir(String senial, Integer contrato, String clave, Integer grupo) {
		try {
			return amortizarTituloSinExhibirRecibirDao.amortizarTituloSinExhibirRecibir(senial, contrato, clave, grupo);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

}