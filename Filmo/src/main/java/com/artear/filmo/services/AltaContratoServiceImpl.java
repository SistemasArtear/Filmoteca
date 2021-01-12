package com.artear.filmo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artear.filmo.bo.common.Distribuidor;
import com.artear.filmo.bo.common.Moneda;
import com.artear.filmo.bo.contratos.*;
import com.artear.filmo.daos.interfaces.AltaContratoDao;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.exceptions.util.ErrorFilmo;
import com.artear.filmo.exceptions.util.ErrorUtils;
import com.artear.filmo.seguridad.services.ServiciosSesionUsuario;
import com.artear.filmo.services.interfaces.AltaContratoService;

@Transactional
@Service("altaContratoService")
public class AltaContratoServiceImpl implements AltaContratoService {

	@Autowired
	private AltaContratoDao altaContratoDao;
	
	@Autowired
	private ServiciosSesionUsuario serviciosSesionUsuario;


	@Override
	public List<Distribuidor> buscarDistribuidores(String descripcion) {
		try {
			return altaContratoDao.buscarDistribuidores(descripcion);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<Moneda> buscarMonedas(String descripcion) {
		try {
			return altaContratoDao.buscarMonedas(descripcion);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public List<TipoTitulo> buscarTiposTitulo() {
		try {
			return altaContratoDao.buscarTiposTitulo();
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public GuardarDatosCabeceraContratoResponse guardarDatosCabeceraContrato(GuardarDatosCabeceraContratoRequest guardarDatosCabeceraContratoRequest) {
		try {
			return altaContratoDao.guardarDatosCabeceraContrato(guardarDatosCabeceraContratoRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public Boolean guardarContratoSenialImporte(GuardarContratoSenialImporteRequest guardarContratoSenialImporteRequest) {
		try {
			return altaContratoDao.guardarContratoSenialImporte(guardarContratoSenialImporteRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<SenialImporte> buscarSenialesImportes(BuscarSenialesImportesRequest buscarSenialesImportesRequest) {
		try {
			return altaContratoDao.buscarSenialesImportes(buscarSenialesImportesRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public Boolean validarExistenciaGrupos(ValidarExistenciaGruposRequest validarExistenciaGruposRequest) {
		try {
			return altaContratoDao.validarExistenciaGrupos(validarExistenciaGruposRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public Boolean modificarContratoSenialImporte(GuardarContratoSenialImporteRequest guardarContratoSenialImporteRequest) {
		try {
			return altaContratoDao.modificarContratoSenialImporte(guardarContratoSenialImporteRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public DesenlaceContratoSenialResponse desenlaceContratoSenial(DesenlaceContratoSenialRequest desenlaceContratoSenialRequest) {
		try {
			return altaContratoDao.desenlaceContratoSenial(desenlaceContratoSenialRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public Boolean eliminarMontoSenial(EliminarMontoSenialRequest eliminarMontoSenialRequest) {
		try {
			return altaContratoDao.eliminarMontoSenial(eliminarMontoSenialRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public ValidarSumaGruposResponse validarSumaGrupos(ValidarSumaGruposRequest validarSumaGruposRequest) {
		try {
			return altaContratoDao.validarSumaGrupos(validarSumaGruposRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public Boolean actualizarCabeceraEliminada(ActualizarCabeceraEliminadaRequest actualizarCabeceraEliminadaRequest) {
		try {
			return altaContratoDao.actualizarCabeceraEliminada(actualizarCabeceraEliminadaRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public ConfirmarSenialesContratoResponse confirmarSenialesContrato(ConfirmarSenialesContratoRequest confirmarSenialesContratoRequest) {
		try {
			return altaContratoDao.confirmarSenialesContrato(confirmarSenialesContratoRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public ConfirmarSenialesContratoResponse modificarCabeceraContrato(ConfirmarSenialesContratoRequest confirmarSenialesContratoRequest) {
		try {
			return altaContratoDao.modificarCabeceraContrato(confirmarSenialesContratoRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public List<TipoImporte> buscarTiposImporte() {
		try {
			return altaContratoDao.buscarTiposImporte();
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<TipoDerecho> buscarTiposDerecho() {
		try {
			return altaContratoDao.buscarTiposDerecho();
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<TipoDerechoTerritorial> buscarTiposDerechoTerritorial() {
		try {
			return altaContratoDao.buscarTiposDerechoTerritorial();
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public Integer buscarNumeroGrupo(BuscarNumeroGrupoRequest buscarNumeroGrupoRequest) {
		try {
			return altaContratoDao.buscarNumeroGrupo(buscarNumeroGrupoRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public String buscarSenialHeredadaCC(BuscarSenialesHeredadasRequest buscarSenialesHeredadasRequest) {
		try {
			return altaContratoDao.buscarSenialHeredadaCC(buscarSenialesHeredadasRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public Boolean validarRecontratacion(ValidarRecontratacionRequest validarRecontratacionRequest) {
		try {
			return altaContratoDao.validarRecontratacion(validarRecontratacionRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public ValidarAmortizacionResponse validarAmortizacionCC(ValidarAmortizacionRequest validarAmortizacionRequest) {
		try {
			return altaContratoDao.validarAmortizacionCC(validarAmortizacionRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public Boolean altaGrupoCC(AltaGrupoCCRequest altaGrupoCCRequest) {
		try {
			altaGrupoCCRequest.setNombreGrupo(altaGrupoCCRequest.getNombreGrupo().toUpperCase());
			return altaContratoDao.altaGrupoCC(altaGrupoCCRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public ValidarModificacionGrupoCCResponse validarModificacionGrupoCC(ValidarModificacionGrupoCCRequest validarModificacionGrupoCCRequest) {
		try {
			return altaContratoDao.validarModificacionGrupoCC(validarModificacionGrupoCCRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public Boolean eliminarConsultarCC(EliminarConsultarCCRequest eliminarConsultarCCRequest) {
		try {
			return altaContratoDao.eliminarConsultarCC(eliminarConsultarCCRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public ModificacionGrupoCCResponse modificacionGrupoCC(ModificacionGrupoCCRequest modificacionGrupoCCRequest) {
		try {
			return altaContratoDao.modificacionGrupoCC(modificacionGrupoCCRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public List<SenialHeredada> buscarSenialesHeredadas(BuscarSenialesHeredadasRequest buscarSenialesHeredadasRequest) {
		try {
			ArrayList<String> seniales = new ArrayList<String>(serviciosSesionUsuario.getSeniales());
			String s = seniales.get(seniales.indexOf(buscarSenialesHeredadasRequest.getSenial()));
			if (s != null  && !s.equals("")){
				seniales.remove(s);
			}

			String sh = StringUtils.join(seniales.iterator(), ",");
			buscarSenialesHeredadasRequest.setSeniales(sh);
			return altaContratoDao.buscarSenialesHeredadas(buscarSenialesHeredadasRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<ValidarAsignacionSenialHeredadaResponse> validarAsignacionSenialHeredadaCC(ValidarAsignacionSenialHeredadaRequest validarAsignacionSenialHeredadaRequest) {
		try {
			return altaContratoDao.validarAsignacionSenialHeredadaCC(validarAsignacionSenialHeredadaRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public Boolean asignarSenialHeredadaCC(AsignarSenialHeredadaRequest asignarSenialHeredadaRequest) {
		try {
			return altaContratoDao.asignarSenialHeredadaCC(asignarSenialHeredadaRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public Boolean desasignarSenialHeredadaCC(DesasignarSenialHeredadaRequest desasignarSenialHeredadaRequest) {
		try {
			return altaContratoDao.desasignarSenialHeredadaCC(desasignarSenialHeredadaRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public String buscarSenialHeredadaSC(BuscarSenialesHeredadasRequest buscarSenialesHeredadasRequest) {
		try {
			return altaContratoDao.buscarSenialHeredadaSC(buscarSenialesHeredadasRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public ValidarAmortizacionResponse validarAmortizacionSC(ValidarAmortizacionRequest validarAmortizacionRequest) {
		try {
			return altaContratoDao.validarAmortizacionSC(validarAmortizacionRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public Boolean altaGrupoSC(AltaGrupoSCRequest altaGrupoSCRequest) {
		try {
			return altaContratoDao.altaGrupoSC(altaGrupoSCRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public ValidarCambioTipoImporteSCResponse validarCambioTipoImporteSC(ValidarCambioTipoImporteSCRequest validarCambioTipoImporteSCRequest) {
		try {
			return altaContratoDao.validarCambioTipoImporteSC(validarCambioTipoImporteSCRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public ValidarPasaLibreriaNaSSCResponse validarPasaLibreriaNaSSC(ValidarPasaLibreriaNaSSCRequest validarPasaLibreriaNaSSCRequest) {
		try {
			return altaContratoDao.validarPasaLibreriaNaSSC(validarPasaLibreriaNaSSCRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public ValidarCantidadPasadasContratadasSCResponse validarCantidadPasadasContratadasSC(ValidarCantidadPasadasContratadasSCRequest validarCantidadPasadasContratadasSCRequest) {
		try {
			return altaContratoDao.validarCantidadPasadasContratadasSC(validarCantidadPasadasContratadasSCRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public ValidarRerunSCResponse validarRerunSC(ValidarRerunSCRequest validarRerunSCRequest) {
		try {
			return altaContratoDao.validarRerunSC(validarRerunSCRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public ValidarDesenlaceSCResponse validarDesenlaceSC(ValidarDesenlaceSCRequest validarDesenlaceSCRequest) {
		try {
			return altaContratoDao.validarDesenlaceSC(validarDesenlaceSCRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public ValidarModificacionGrupoSCResponse validarModificacionGrupoSC(ValidarModificacionGrupoSCRequest validarModificacionGrupoSCRequest) {
		try {
			return altaContratoDao.validarModificacionGrupoSC(validarModificacionGrupoSCRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public Boolean eliminarConsultarSC(EliminarConsultarSCRequest eliminarConsultarSCRequest) {
		try {
			return altaContratoDao.eliminarConsultarSC(eliminarConsultarSCRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public ModificacionGrupoSCResponse modificacionGrupoSC(ModificacionGrupoSCRequest modificacionGrupoSCRequest) {
		try {
			return altaContratoDao.modificacionGrupoSC(modificacionGrupoSCRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public List<ValidarAsignacionSenialHeredadaResponse> validarAsignacionSenialHeredadaSC(ValidarAsignacionSenialHeredadaRequest validarAsignacionSenialHeredadaRequest) {
		try {
			return altaContratoDao.validarAsignacionSenialHeredadaSC(validarAsignacionSenialHeredadaRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public Boolean asignarSenialHeredadaSC(AsignarSenialHeredadaRequest asignarSenialHeredadaRequest) {
		try {
			return altaContratoDao.asignarSenialHeredadaSC(asignarSenialHeredadaRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public Boolean desasignarSenialHeredadaSC(DesasignarSenialHeredadaRequest desasignarSenialHeredadaRequest) {
		try {
			return altaContratoDao.desasignarSenialHeredadaSC(desasignarSenialHeredadaRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}	
	
	@Override
	public List<GrupoContrato> buscarGruposContrato(BuscarGruposContratoRequest buscarGruposContratoRequest) {
		try {
			return altaContratoDao.buscarGruposContrato(buscarGruposContratoRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	@Override
	public DesenlaceContratoGrupoResponse desenlaceContratoGrupo(DesenlaceContratoGrupoRequest desenlaceContratoGrupoRequest) {
		try {
			return altaContratoDao.desenlaceContratoGrupo(desenlaceContratoGrupoRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public RecuperarDatosGrupoResponse recuperarDatosGrupo(RecuperarDatosGrupoRequest recuperarDatosGrupoRequest) {
		try {
			return altaContratoDao.recuperarDatosGrupo(recuperarDatosGrupoRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public EliminarGrupoContratoResponse eliminarGrupoContrato(EliminarGrupoContratoRequest eliminarGrupoContratoRequest) {
		try {
			return altaContratoDao.eliminarGrupoContrato(eliminarGrupoContratoRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public List<TituloGrupo> buscarTitulosGrupo(BuscarTitulosGrupoRequest buscarTitulosGrupoRequest) {
		try {
			return altaContratoDao.buscarTitulosGrupo(buscarTitulosGrupoRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public Boolean validarPerpetuidadTitulo(ValidarPerpetuidadTituloRequest validarPerpetuidadTituloRequest) {
		try {
			return altaContratoDao.validarPerpetuidadTitulo(validarPerpetuidadTituloRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public Integer obtenerNuevaClaveTitulo(ObtenerNuevaClaveTituloRequest obtenerNuevaClaveTituloRequest) {
		try {
			return altaContratoDao.obtenerNuevaClaveTitulo(obtenerNuevaClaveTituloRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public Map<String, List<CalificacionOficial>> buscarCalificacionesOficiales(String clave) {
		try {
			return altaContratoDao.buscarCalificacionesOficiales(clave);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public ValidacionExistenciaTituloResponse validacionExistenciaTitulo(ValidacionExistenciaTituloRequest validacionExistenciaTituloRequest) {
		try {
			return altaContratoDao.validacionExistenciaTitulo(validacionExistenciaTituloRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public ValidacionRecepcionMasterResponse validacionRecepcionMaster(ValidacionRecepcionMasterRequest validacionRecepcionMasterRequest) {
		try {
			return altaContratoDao.validacionRecepcionMaster(validacionRecepcionMasterRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public ConfirmarSeleccionTituloResponse confirmarSeleccionTitulo(ConfirmarSeleccionTituloRequest confirmarSeleccionTituloRequest) {
		try {
			if (confirmarSeleccionTituloRequest.getTituloCastellano() != null)
				confirmarSeleccionTituloRequest.setTituloCastellano(confirmarSeleccionTituloRequest.getTituloCastellano().toUpperCase());
			if (confirmarSeleccionTituloRequest.getTituloOriginal() != null)
				confirmarSeleccionTituloRequest.setTituloOriginal(confirmarSeleccionTituloRequest.getTituloOriginal().toUpperCase());
			return altaContratoDao.confirmarSeleccionTitulo(confirmarSeleccionTituloRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public ConfirmarSeleccionTituloResponse confirmarSeleccionTituloRecontratacion(ConfirmarSeleccionTituloRequest confirmarSeleccionTituloRequest) {
		try {
			return altaContratoDao.confirmarSeleccionTituloRecontratacion(confirmarSeleccionTituloRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	
	@Override
	public List<TituloRecontratacion> buscarTitulosRecontratacion(BuscarTitulosRecontratacionRequest buscarTitulosRecontratacionRequest) {
		try {
			return altaContratoDao.buscarTitulosRecontratacion(buscarTitulosRecontratacionRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public List<ValidarRemitoSNCResponse> validarRemitoSNC(ValidarRemitoSNCRequest validarRemitoSNCRequest) {
		try {
			return altaContratoDao.validarRemitoSNC(validarRemitoSNCRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<ValidarMasterTituloResponse> validarMasterTitulo(ValidarMasterTituloRequest validarMasterTituloRequest) {
		try {
			return altaContratoDao.validarMasterTitulo(validarMasterTituloRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public ConfirmarSeleccionCapitulosResponse confirmarSeleccionCapitulosTituloRecontratacion(ConfirmarSeleccionCapitulosRequest confirmarSeleccionCapitulosRequest) {
		try {
			return altaContratoDao.confirmarSeleccionCapitulosTituloRecontratacion(confirmarSeleccionCapitulosRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public List<CapituloTituloRecontratacion> buscarCapitulosTituloRecontratacion(BuscarCapitulosTituloRecontratacionRequest buscarCapitulosTituloRecontratacionRequest) {
		try {
			return altaContratoDao.buscarCapitulosTituloRecontratacion(buscarCapitulosTituloRecontratacionRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<ValidarRemitoSNCCapituloRecontratacionResponse> validarRemitoSNCCapituloRecontratacion(ValidarRemitoSNCCapituloRecontratacionRequest validarRemitoSNCCapituloRecontratacionRequest) {
		try {
			return altaContratoDao.validarRemitoSNCCapituloRecontratacion(validarRemitoSNCCapituloRecontratacionRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public List<ValidarRemitoNoSNCResponse> validarRemitoNoSNC(ValidarRemitoNoSNCRequest validarRemitoNoSNCRequest) {
		try {
			return altaContratoDao.validarRemitoNoSNC(validarRemitoNoSNCRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public ConfirmarSeleccionCapitulosResponse confirmarSeleccionCapituloTituloRecontratacion(ConfirmarSeleccionCapitulosRequest confirmarSeleccionCapitulosRequest) {
		try {
			return altaContratoDao.confirmarSeleccionCapituloTituloRecontratacion(confirmarSeleccionCapitulosRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public List<ContratoEnlazadoTituloRecontratacion> contratosEnlazadosTituloRecontratacion(ContratosEnlazadosTituloRecontratacionRequest contratosEnlazadosTituloRecontratacionRequest) {
		try {
			return altaContratoDao.contratosEnlazadosTituloRecontratacion(contratosEnlazadosTituloRecontratacionRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public ValidarDesenlaceTituloContratoResponse validarDesenlaceTituloContrato(ValidarDesenlaceTituloContratoRequest validarDesenlaceTituloContratoRequest) {
		try {
			return altaContratoDao.validarDesenlaceTituloContrato(validarDesenlaceTituloContratoRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public EjecutarDesenlaceTituloContratoResponse ejecutarDesenlaceTituloContrato(EjecutarDesenlaceTituloContratoRequest ejecutarDesenlaceTituloContratoRequest) {
		try {
			return altaContratoDao.ejecutarDesenlaceTituloContrato(ejecutarDesenlaceTituloContratoRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public ValidarEnlacePosteriorResponse validarEnlacePosterior(ValidarEnlacePosteriorRequest validarEnlacePosteriorRequest) {
		try {
			return altaContratoDao.validarEnlacePosterior(validarEnlacePosteriorRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public EjecutarEnlacePosteriorResponse ejecutarEnlacePosterior(EjecutarEnlacePosteriorRequest ejecutarEnlacePosteriorRequest) {
		try {
			return altaContratoDao.ejecutarEnlacePosterior(ejecutarEnlacePosteriorRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public List<ModificacionVigencia> visualizarModificacionesVigencia(VisualizarModificacionesVigenciaRequest visualizarModificacionesVigenciaRequest) {
		try {
			return altaContratoDao.visualizarModificacionesVigencia(visualizarModificacionesVigenciaRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public List<CapituloTituloRecontratacion> buscarCapitulosEliminacion(BuscarCapitulosEliminacionRequest buscarCapitulosEliminacionRequest) {
		try {
			return altaContratoDao.buscarCapitulosEliminacion(buscarCapitulosEliminacionRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public String validarCantidadCapitulosBaja(ValidarCantidadCapitulosBajaRequest validarCantidadCapitulosBajaRequest) {
		try {
			return altaContratoDao.validarCantidadCapitulosBaja(validarCantidadCapitulosBajaRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public PrimerValidacionRerunBajaCapitulosResponse primerValidacionRerunBajaCapitulos(PrimerValidacionRerunBajaCapitulosRequest primerValidacionRerunBajaCapitulosRequest) {
		try {
			return altaContratoDao.primerValidacionRerunBajaCapitulos(primerValidacionRerunBajaCapitulosRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public SegundaValidacionRerunBajaCapitulosResponse segundaValidacionRerunBajaCapitulos(SegundaValidacionRerunBajaCapitulosRequest segundaValidacionRerunBajaCapitulosRequest) {
		try {
			return altaContratoDao.segundaValidacionRerunBajaCapitulos(segundaValidacionRerunBajaCapitulosRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public ConfirmarBajaCapitulosResponse confirmarBajaCapitulos(ConfirmarBajaCapitulosRequest confirmarBajaCapitulosRequest) {
		try {
			return altaContratoDao.confirmarBajaCapitulos(confirmarBajaCapitulosRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public List<CapituloTituloRecontratacion> buscarCapitulosAdicion(BuscarCapitulosAdicionRequest buscarCapitulosAdicionRequest) {
		try {
			return altaContratoDao.buscarCapitulosAdicion(buscarCapitulosAdicionRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public List<ValidarRemitoCapitulosSNCResponse> validarRemitoCapitulosSNC(ValidarRemitoCapitulosSNCRequest validarRemitoCapitulosSNCRequest) {
		try {
			return altaContratoDao.validarRemitoCapitulosSNC(validarRemitoCapitulosSNCRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public ConfirmarAltaCapitulosResponse confirmarAltaCapitulos(ConfirmarAltaCapitulosRequest confirmarAltaCapitulosRequest) {
		try {
			return altaContratoDao.confirmarAltaCapitulos(confirmarAltaCapitulosRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public List<TituloEliminarContrato> buscarTitulosEliminarContrato(BuscarTitulosEliminarContratoRequest buscarTitulosEliminarContratoRequest) {
		try {
			return altaContratoDao.buscarTitulosEliminarContrato(buscarTitulosEliminarContratoRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public ValidarEliminacionTituloContratoResponse validarEliminacionTituloContrato(ValidarEliminacionTituloContratoRequest validarEliminacionTituloContratoRequest) {
		try {
			return altaContratoDao.validarEliminacionTituloContrato(validarEliminacionTituloContratoRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public ConfirmarEliminacionTituloContratoResponse confirmarEliminacionTituloContrato(ConfirmarEliminacionTituloContratoRequest confirmarEliminacionTituloContratoRequest) {
		try {
			return altaContratoDao.confirmarEliminacionTituloContrato(confirmarEliminacionTituloContratoRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public List<TituloGrupo> buscarTitulosGrupoReadOnly(BuscarTitulosGrupoRequest buscarTitulosGrupoRequest) {
		try {
			return altaContratoDao.buscarTitulosGrupoReadOnly(buscarTitulosGrupoRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

}