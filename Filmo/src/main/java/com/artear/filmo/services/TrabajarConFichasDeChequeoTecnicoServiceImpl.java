package com.artear.filmo.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artear.filmo.bo.chequeo.tecnico.AltaModifFichaRequest;
import com.artear.filmo.bo.chequeo.tecnico.AltaModifRolloRequest;
import com.artear.filmo.bo.chequeo.tecnico.AsociarAContrato;
import com.artear.filmo.bo.chequeo.tecnico.BusquedaFichasRequest;
import com.artear.filmo.bo.chequeo.tecnico.BusquedaFichasResponse;
import com.artear.filmo.bo.chequeo.tecnico.Chequeo;
import com.artear.filmo.bo.chequeo.tecnico.Ficha;
import com.artear.filmo.bo.chequeo.tecnico.FichaCompleta;
import com.artear.filmo.bo.chequeo.tecnico.GuardarActoresSinopsisObservRequest;
import com.artear.filmo.bo.chequeo.tecnico.GuardarSegmentoRequest;
import com.artear.filmo.bo.chequeo.tecnico.GuardarSenialesOkFilmRequest;
import com.artear.filmo.bo.chequeo.tecnico.ProgramaSituarResponse;
import com.artear.filmo.bo.chequeo.tecnico.Rollo;
import com.artear.filmo.bo.chequeo.tecnico.Segmento;
import com.artear.filmo.bo.chequeo.tecnico.SenialOkFilm;
import com.artear.filmo.bo.chequeo.tecnico.TitulosConContratosExhibidosResponse;
import com.artear.filmo.bo.chequeo.tecnico.VerificarDesenlaceRequest;
import com.artear.filmo.bo.chequeo.tecnico.VerificarDesenlaceResponse;
import com.artear.filmo.bo.chequeo.tecnico.VigenciaContrato;
import com.artear.filmo.bo.chequeo.tecnico.VisualizarFichaRequest;
import com.artear.filmo.bo.common.AyudaSituar;
import com.artear.filmo.bo.common.ErrorResponse;
import com.artear.filmo.bo.common.Senial;
import com.artear.filmo.constants.TipoBusquedaTitulo;
import com.artear.filmo.daos.interfaces.TrabajarConFichasDeChequeoTecnicoDao;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.exceptions.util.ErrorFilmo;
import com.artear.filmo.exceptions.util.ErrorUtils;
import com.artear.filmo.services.interfaces.TrabajarConFichasDeChequeoTecnicoService;

/**
 * 
 * @author flvaldes
 * 
 */
@Transactional
@Service("trabajarConFichasDeChequeoTecnicoService")
public class TrabajarConFichasDeChequeoTecnicoServiceImpl implements
		TrabajarConFichasDeChequeoTecnicoService {

	@Autowired
	private TrabajarConFichasDeChequeoTecnicoDao trabajarConFichasDeChequeoTecnicoDao;

	@Override
	public List<BusquedaFichasResponse> buscarFichas(
			BusquedaFichasRequest request) {
		try {
			return trabajarConFichasDeChequeoTecnicoDao.buscarFichas(request);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public FichaCompleta visualizarFicha(VisualizarFichaRequest request) {
		try {
			return trabajarConFichasDeChequeoTecnicoDao
					.visualizarFicha(request);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<ProgramaSituarResponse> buscarProgramasPorTitulo(String titulo,
			TipoBusquedaTitulo tipoBusqueda) {
		try {
			return trabajarConFichasDeChequeoTecnicoDao
					.buscarProgramasPorTitulo(titulo, tipoBusqueda);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public Chequeo obtenerInfoChequeoPorPrograma(String clave, String capitulo) {
		try {
			return trabajarConFichasDeChequeoTecnicoDao
					.obtenerInfoChequeoPorPrograma(clave, capitulo);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public void modificarFicha(AltaModifFichaRequest request) {
		try {
			trabajarConFichasDeChequeoTecnicoDao.modificarFicha(request);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public void altaFicha(AltaModifFichaRequest request) {
		try {
			trabajarConFichasDeChequeoTecnicoDao.altaFicha(request);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<ErrorResponse> eliminarFicha(Integer nroFicha, String clave) {
		try {
			return trabajarConFichasDeChequeoTecnicoDao.eliminarFicha(nroFicha, clave);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public void inactivarFicha(Integer nroFicha, String clave) {
		try {
			trabajarConFichasDeChequeoTecnicoDao
					.inactivarFicha(nroFicha, clave);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<VigenciaContrato> infoVigenciaContratos(String clave,
			Integer ficha) {
		try {
			return trabajarConFichasDeChequeoTecnicoDao.infoVigenciaContratos(
					clave, ficha);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public String validarTituloEnCanal(String clave, String senial, String capitulo, String parte) {
		try {
			return trabajarConFichasDeChequeoTecnicoDao.validarTituloEnCanal(
					clave, senial, capitulo, parte);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<AyudaSituar> buscarGeneros(String descripcion) {
		try {
			return trabajarConFichasDeChequeoTecnicoDao
					.buscarGeneros(descripcion);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<AyudaSituar> buscarCalifArtisticas(String descripcion) {
		try {
			return trabajarConFichasDeChequeoTecnicoDao
					.buscarCalifArtisticas(descripcion);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<AyudaSituar> buscarCalifAutocontrol(String descripcion) {
		try {
			return trabajarConFichasDeChequeoTecnicoDao
					.buscarCalifAutocontrol(descripcion);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<AyudaSituar> buscarCalifOficial(String descripcion) {
		try {
			return trabajarConFichasDeChequeoTecnicoDao
					.buscarCalifOficial(descripcion);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public Boolean validarSegmentos(
			GuardarSegmentoRequest validarSegmentosRequest) {
		Integer totalDuracion = 0;
		Integer totalSegmentos = 0;
		if(validarSegmentosRequest.getSegmentos()!=null)
		for (Segmento s : validarSegmentosRequest.getSegmentos()) {
			if (StringUtils.isNotBlank(s.getTitulo())
					&& s.getDuracion() != null) {
				totalDuracion = totalDuracion + s.getDuracion();
				totalSegmentos++;
			}
		}
		if (totalDuracion == validarSegmentosRequest.getDuracionArtistica()
				&& totalSegmentos == validarSegmentosRequest
						.getCantidadSegmentos()) {
			return true;
		}
		return false;
	}

	@Override
	public void eliminarSegmentos(Integer nroFicha) {
		try {
			trabajarConFichasDeChequeoTecnicoDao.eliminarSegmentos(nroFicha);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public void guardarSegmentos(GuardarSegmentoRequest segmentosRequest) {
		try {
			trabajarConFichasDeChequeoTecnicoDao
					.guardarSegmentos(segmentosRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public void guardarActoresSinopsisYObservaciones(
			GuardarActoresSinopsisObservRequest request) {
		try {
			trabajarConFichasDeChequeoTecnicoDao
					.guardarActoresSinopsisYObservaciones(request);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public void validarChequeoDetalle(Ficha ficha) {
		try {
			trabajarConFichasDeChequeoTecnicoDao.validarChequeoDetalle(ficha);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}

	}

	@Override
	public void guardarChequeoDetalle(Ficha ficha) {
		try {
			trabajarConFichasDeChequeoTecnicoDao.guardarChequeoDetalle(ficha);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<SenialOkFilm> buscarSenialesOkFiml(Integer nroFicha, String film) {
		try {
			return trabajarConFichasDeChequeoTecnicoDao.buscarSenialesOkFiml(
					nroFicha, film);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public void guardarSenialesOkFilm(GuardarSenialesOkFilmRequest request) {
		try {
			trabajarConFichasDeChequeoTecnicoDao.guardarSenialesOkFilm(request);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	/*public TitulosConContratosExhibidosResponse popupTitulosConContratosExhibidos(
			TitulosConContratosExhibidosRequest request) {
		try {
			return trabajarConFichasDeChequeoTecnicoDao
					.popupTitulosConContratosExhibidos(request);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}*/
	public TitulosConContratosExhibidosResponse popupTitulosConContratosExhibidos(
			Integer nroFicha, String film) {
		try {
			return trabajarConFichasDeChequeoTecnicoDao
					.popupTitulosConContratosExhibidos(nroFicha, film);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	

	public TitulosConContratosExhibidosResponse popupTitulosConContratosExhibidosSN(
			Integer nroFicha, String film) {
		try {
			return trabajarConFichasDeChequeoTecnicoDao
					.popupTitulosConContratosExhibidosSN(nroFicha, film);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	public VerificarDesenlaceResponse popupVerificarDesenlace(
			VerificarDesenlaceRequest request) {
		try {
			return trabajarConFichasDeChequeoTecnicoDao
					.popupVerificarDesenlace(request);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	
	public VerificarDesenlaceResponse validarPayTV( Integer nroFicha, String clave) {
		try {
			return trabajarConFichasDeChequeoTecnicoDao
					.validarPayTV(nroFicha, clave);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	public String ejecutarDesenlace(
			VerificarDesenlaceRequest request) {
		try {
			return trabajarConFichasDeChequeoTecnicoDao
					.ejecutarDesenlace(request);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	public void actualizarPY6001(
			Integer nroFicha) {
		try {
			trabajarConFichasDeChequeoTecnicoDao
					.actualizarPY6001(nroFicha);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	public String popupTitulosConContratosExhibidosRechazo(
			Integer nroFicha) {
		try {
			return trabajarConFichasDeChequeoTecnicoDao
					.popupTitulosConContratosExhibidosRechazo(nroFicha);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<AyudaSituar> buscarSoportes(String descripcion) {
		try {
			return trabajarConFichasDeChequeoTecnicoDao
					.buscarSoportes(descripcion);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<Senial> buscarSeniales(Integer nroFicha, String descripcion) {
		try {
			return trabajarConFichasDeChequeoTecnicoDao.buscarSeniales(nroFicha, descripcion);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<Rollo> buscarRollos(String senial, Integer nroFicha) {
		try {
			return trabajarConFichasDeChequeoTecnicoDao.buscarRollos(senial,
					nroFicha);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public void altaDeRollos(AltaModifRolloRequest request) {
		try {
			trabajarConFichasDeChequeoTecnicoDao.altaDeRollos(request);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public void altaOModificacionDeRollos(AltaModifRolloRequest request) {
		try {
			List<Rollo> rollos = request.getRollos();
			List<Rollo> rollosAlta = new ArrayList<Rollo>();
			List<Rollo> rollosModificacion = new ArrayList<Rollo>();
			for (Rollo rollo : rollos) {
				if (rollo.getSecuencia() == 0 && rollo.getCopia() == 0) {
					rollosAlta.add(rollo);
				} else {
					rollosModificacion.add(rollo);
				}
			}
			if (CollectionUtils.isNotEmpty(rollosAlta)) {
				request.setRollos(rollosAlta);
				trabajarConFichasDeChequeoTecnicoDao.altaDeRollos(request);
			}
			if (CollectionUtils.isNotEmpty(rollosModificacion)) {
				request.setRollos(rollosModificacion);
				trabajarConFichasDeChequeoTecnicoDao
						.modificacionDeRollos(request);
			}
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public void registrarMaster(String senial, String clave, Integer nroFicha) {
		try {
			trabajarConFichasDeChequeoTecnicoDao.registrarMaster(senial, clave,
					nroFicha);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<AsociarAContrato> buscarContratosParaAsociarLaCopia(
			String senial, String clave, Integer nroFicha) {
		try {
			return trabajarConFichasDeChequeoTecnicoDao
					.buscarContratosParaAsociarLaCopia(senial, clave, nroFicha);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	

}
