package com.artear.filmo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artear.filmo.bo.common.Distribuidor;
import com.artear.filmo.bo.common.Soporte;
import com.artear.filmo.bo.ingresar.materiales.SoporteTituloABM;
import com.artear.filmo.bo.ingresar.materiales.TituloMaterialesGrillaResponse;
import com.artear.filmo.daos.interfaces.IngresarMaterialesDao;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.exceptions.util.ErrorFilmo;
import com.artear.filmo.exceptions.util.ErrorUtils;
import com.artear.filmo.services.interfaces.IngresarMaterialesService;

@Transactional
@Service("ingresarMaterialesService")
public class IngresarMaterialesServiceImpl implements IngresarMaterialesService {

	@Autowired
	private IngresarMaterialesDao ingresarMaterialesDao;
	
	@Override
	public List<Distribuidor> buscarDistribuidoresParaMateriales(Integer codigoDistribuidor, String razonSocialDistribuidor) {
		try {
			return ingresarMaterialesDao.buscarDistribuidoresParaMateriales(codigoDistribuidor, razonSocialDistribuidor);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public List<TituloMaterialesGrillaResponse> buscarTitulosCastellanoParaMateriales(String senial, String tipoMaterial, Integer codigoDistribuidor, String descripcionTitulo) {
		try {
			return ingresarMaterialesDao.buscarTitulosCastellanoParaMateriales(senial, tipoMaterial, codigoDistribuidor, descripcionTitulo);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<TituloMaterialesGrillaResponse> buscarTitulosOriginalParaMateriales(String senial, String tipoMaterial, Integer codigoDistribuidor, String descripcionTitulo) {
		try {
			return ingresarMaterialesDao.buscarTitulosOriginalParaMateriales(senial, tipoMaterial, codigoDistribuidor, descripcionTitulo);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public String determinarFamiliaTitulo(String tipoTituloSeleccionado) {
		try {
			return ingresarMaterialesDao.determinarFamiliaTitulo(tipoTituloSeleccionado);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<Soporte> buscarSoportes(String codigoSoporte)  {
		try {
			return ingresarMaterialesDao.buscarSoportes(codigoSoporte);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public void altaSoporteTitulo(SoporteTituloABM soporteTitulo) {
		try {
			ingresarMaterialesDao.altaSoporteTitulo(soporteTitulo);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

}
