package com.artear.filmo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artear.filmo.bo.common.AyudaSituar;
import com.artear.filmo.bo.ficha.cinematografica.AyudaFichas;
import com.artear.filmo.bo.ficha.cinematografica.FichaCompleta;
import com.artear.filmo.bo.ficha.cinematografica.FichaListadoResponse;
import com.artear.filmo.constants.TipoBusquedaTitulo;
import com.artear.filmo.daos.interfaces.FichaCinematograficaDao;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.exceptions.util.ErrorFilmo;
import com.artear.filmo.exceptions.util.ErrorUtils;
import com.artear.filmo.seguridad.services.ServiciosSesionUsuario;
import com.artear.filmo.services.interfaces.FichaCinematograficaService;

/**
 * 
 * @author flvaldes
 * 
 */
@Transactional
@Service("fichaCinematograficaService")
public class FichaCinematograficaServiceImpl implements
		FichaCinematograficaService {

	@Autowired
	private FichaCinematograficaDao fichaCinematograficaDao;

	@Autowired
	private ServiciosSesionUsuario serviciosSesionUsuario;

	@Override
	public List<FichaListadoResponse> buscarFichas(TipoBusquedaTitulo tipo,
			String titulo) {
		try {
			return fichaCinematograficaDao.buscarFichas(tipo, titulo);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public boolean eliminarFicha(Integer idFicha) {
		try {
			return fichaCinematograficaDao.eliminarFicha(idFicha);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public FichaCompleta obtenerFichaCompleta(Integer idFicha) {
		try {
			return fichaCinematograficaDao.obtenerFichaCompleta(idFicha);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public boolean modificarFicha(FichaCompleta ficha) {
		try {
			return fichaCinematograficaDao.modificarFicha(ficha,
					serviciosSesionUsuario.getUsuario());
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public boolean guardarFicha(FichaCompleta ficha) {
		try {
			return fichaCinematograficaDao.guardarFicha(ficha,
					serviciosSesionUsuario.getUsuario());
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<AyudaSituar> ayudaSituar(String descripcion,
			AyudaFichas ayudaFichas) {
		try {
			return fichaCinematograficaDao
					.ayudaSituar(descripcion, ayudaFichas);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

}