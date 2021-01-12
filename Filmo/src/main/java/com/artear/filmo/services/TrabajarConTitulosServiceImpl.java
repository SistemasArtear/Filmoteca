package com.artear.filmo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artear.filmo.bo.trabajar.titulos.CodDesc;
import com.artear.filmo.bo.trabajar.titulos.Contrato;
import com.artear.filmo.bo.trabajar.titulos.ContratoView;
import com.artear.filmo.bo.trabajar.titulos.DetalleContrato;
import com.artear.filmo.bo.trabajar.titulos.FichaCinematograficaView;
import com.artear.filmo.bo.trabajar.titulos.OperacionTitulo;
import com.artear.filmo.bo.trabajar.titulos.TituloABM;
import com.artear.filmo.bo.trabajar.titulos.TituloGrillaResponse;
import com.artear.filmo.bo.trabajar.titulos.TituloView;
import com.artear.filmo.daos.interfaces.TrabajarConTitulosDao;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.exceptions.util.ErrorFilmo;
import com.artear.filmo.exceptions.util.ErrorUtils;
import com.artear.filmo.services.interfaces.TrabajarConTitulosService;

/**
 * 
 * @author flvaldes
 * 
 */
@Transactional
@Service("trabajarConTitulosService")
public class TrabajarConTitulosServiceImpl implements TrabajarConTitulosService {

	@Autowired
	private TrabajarConTitulosDao trabajarConTitulosDao;

	@Override
	public List<TituloGrillaResponse> obtenerTitulosCastellanoPorDescripcion(
			String senial, String descripcion) {
		try {
			return trabajarConTitulosDao
					.obtenerTitulosCastellanoPorDescripcion(senial, descripcion);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<TituloGrillaResponse> obtenerTitulosOriginalPorDescripcion(
			String senial, String descripcion) {
		try {
			return trabajarConTitulosDao.obtenerTitulosOriginalPorDescripcion(
					senial, descripcion);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<TituloGrillaResponse> obtenerTitulosCastellanoPorCodigo(
			String senial, String codigo) {
		try {
			return trabajarConTitulosDao.obtenerTitulosCastellanoPorCodigo(
					senial, codigo);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<TituloGrillaResponse> obtenerTitulosOriginalPorCodigo(
			String senial, String codigo) {
		try {
			return trabajarConTitulosDao.obtenerTitulosOriginalPorCodigo(
					senial, codigo);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public void abmTitulo(TituloABM titulo, OperacionTitulo operacion) {
		try {
			trabajarConTitulosDao.abmTitulo(titulo, operacion);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public TituloView cargarTitulo(String senial, String clave) {
		try {
			return trabajarConTitulosDao.cargarTitulo(senial, clave);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<Contrato> obtenerContratos(String senial, String clave,
			Integer contrato) {
		try {
			return trabajarConTitulosDao.obtenerContratos(senial, clave,
					contrato);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public ContratoView cargarContrato(String senial, String clave,
			Integer contrato) {
		try {
			return trabajarConTitulosDao
					.cargarContrato(senial, clave, contrato);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<CodDesc> obtenerTiposDeTitulos(String descripcion) {
		try {
			return trabajarConTitulosDao.obtenerTiposDeTitulos(descripcion);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<CodDesc> obtenerCalificacionesOficiales(String descripcion) {
		try {
			return trabajarConTitulosDao
					.obtenerCalificacionesOficiales(descripcion);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<CodDesc> obtenerFichasCinematograficasCast(String descripcion) {
		try {
			return trabajarConTitulosDao
					.obtenerFichasCinematograficasCast(descripcion);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<CodDesc> obtenerFichasCinematograficasOriginal(
			String descripcion) {
		try {
			return trabajarConTitulosDao
					.obtenerFichasCinematograficasOriginal(descripcion);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public FichaCinematograficaView cargarFichaCinematografica(String codigo) {
		try {
			return trabajarConTitulosDao.cargarFichaCinematografica(codigo);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public DetalleContrato cargarDetalleContrato(String clave,
			Integer contrato, String senial) {
		try {
			return trabajarConTitulosDao.cargarDetalleContrato(clave, contrato,
					senial);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

}
