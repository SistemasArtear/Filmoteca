package com.artear.filmo.daos.interfaces;

import java.util.List;
import java.util.Map;

import com.artear.filmo.bo.common.Distribuidor;
import com.artear.filmo.bo.common.Moneda;
import com.artear.filmo.bo.contratos.*;

public interface AltaContratoDao {

	public List<Distribuidor> buscarDistribuidores(String descripcion);

	public List<Moneda> buscarMonedas(String descripcion);

	public List<TipoTitulo> buscarTiposTitulo();
	
	public GuardarDatosCabeceraContratoResponse guardarDatosCabeceraContrato(GuardarDatosCabeceraContratoRequest guardarDatosCabeceraContratoRequest);
	
	public Boolean guardarContratoSenialImporte(GuardarContratoSenialImporteRequest guardarContratoSenialImporteRequest);

	public List<SenialImporte> buscarSenialesImportes(BuscarSenialesImportesRequest buscarSenialesImportesRequest);

	public Boolean validarExistenciaGrupos(ValidarExistenciaGruposRequest validarExistenciaGruposRequest);

	public Boolean modificarContratoSenialImporte(GuardarContratoSenialImporteRequest guardarContratoSenialImporteRequest);

	public DesenlaceContratoSenialResponse desenlaceContratoSenial(DesenlaceContratoSenialRequest desenlaceContratoSenialRequest);
	
	public Boolean eliminarMontoSenial(EliminarMontoSenialRequest eliminarMontoSenialRequest);

	public ValidarSumaGruposResponse validarSumaGrupos(ValidarSumaGruposRequest validarSumaGruposRequest);

	public Boolean actualizarCabeceraEliminada(ActualizarCabeceraEliminadaRequest actualizarCabeceraEliminadaRequest);

	public ConfirmarSenialesContratoResponse confirmarSenialesContrato(ConfirmarSenialesContratoRequest confirmarSenialesContratoRequest);
	
	public List<TipoImporte> buscarTiposImporte();

	public List<TipoDerecho> buscarTiposDerecho();

	public List<TipoDerechoTerritorial> buscarTiposDerechoTerritorial();

	public Integer buscarNumeroGrupo(BuscarNumeroGrupoRequest buscarNumeroGrupoRequest);
	
	public String buscarSenialHeredadaCC(BuscarSenialesHeredadasRequest buscarSenialesHeredadasRequest);
	
	public Boolean validarRecontratacion(ValidarRecontratacionRequest validarRecontratacionRequest);

	public ValidarAmortizacionResponse validarAmortizacionCC(ValidarAmortizacionRequest validarAmortizacionRequest);
	
	public Boolean altaGrupoCC(AltaGrupoCCRequest altaGrupoCCRequest);

	public ValidarModificacionGrupoCCResponse validarModificacionGrupoCC(ValidarModificacionGrupoCCRequest validarModificacionGrupoCCRequest);
	
	public Boolean eliminarConsultarCC(EliminarConsultarCCRequest eliminarConsultarCCRequest);
	
	public ModificacionGrupoCCResponse modificacionGrupoCC(ModificacionGrupoCCRequest modificacionGrupoCCRequest);
	
	public List<SenialHeredada> buscarSenialesHeredadas(BuscarSenialesHeredadasRequest buscarSenialesHeredadasRequest);

	public List<ValidarAsignacionSenialHeredadaResponse> validarAsignacionSenialHeredadaCC(ValidarAsignacionSenialHeredadaRequest validarAsignacionSenialHeredadaRequest);

	public Boolean asignarSenialHeredadaCC(AsignarSenialHeredadaRequest asignarSenialHeredadaRequest);

	public Boolean desasignarSenialHeredadaCC(DesasignarSenialHeredadaRequest desasignarSenialHeredadaRequest);

	public String buscarSenialHeredadaSC(BuscarSenialesHeredadasRequest buscarSenialesHeredadasRequest);
	
	public ValidarAmortizacionResponse validarAmortizacionSC(ValidarAmortizacionRequest validarAmortizacionRequest);
	
	public Boolean altaGrupoSC(AltaGrupoSCRequest altaGrupoSCRequest);
	
	public ValidarCambioTipoImporteSCResponse validarCambioTipoImporteSC(ValidarCambioTipoImporteSCRequest validarCambioTipoImporteSCRequest);

	public ValidarPasaLibreriaNaSSCResponse validarPasaLibreriaNaSSC(ValidarPasaLibreriaNaSSCRequest validarPasaLibreriaNaSSCRequest);

	public ValidarCantidadPasadasContratadasSCResponse validarCantidadPasadasContratadasSC(ValidarCantidadPasadasContratadasSCRequest validarCantidadPasadasContratadasSCRequest);

	public ValidarRerunSCResponse validarRerunSC(ValidarRerunSCRequest validarRerunSCRequest);
	
	public ValidarDesenlaceSCResponse validarDesenlaceSC(ValidarDesenlaceSCRequest validarDesenlaceSCRequest);
	
	public ValidarModificacionGrupoSCResponse validarModificacionGrupoSC(ValidarModificacionGrupoSCRequest validarModificacionGrupoSCRequest);
	
	public Boolean eliminarConsultarSC(EliminarConsultarSCRequest eliminarConsultarSCRequest);
	
	public ModificacionGrupoSCResponse modificacionGrupoSC(ModificacionGrupoSCRequest modificacionGrupoSCRequest);
	
	public List<ValidarAsignacionSenialHeredadaResponse> validarAsignacionSenialHeredadaSC(ValidarAsignacionSenialHeredadaRequest validarAsignacionSenialHeredadaRequest);

	public Boolean asignarSenialHeredadaSC(AsignarSenialHeredadaRequest asignarSenialHeredadaRequest);

	public Boolean desasignarSenialHeredadaSC(DesasignarSenialHeredadaRequest desasignarSenialHeredadaRequest);	
	
	public List<GrupoContrato> buscarGruposContrato(BuscarGruposContratoRequest buscarGruposContratoRequest);

	public DesenlaceContratoGrupoResponse desenlaceContratoGrupo(DesenlaceContratoGrupoRequest desenlaceContratoGrupoRequest);

	public RecuperarDatosGrupoResponse recuperarDatosGrupo(RecuperarDatosGrupoRequest recuperarDatosGrupoRequest);
	
	public EliminarGrupoContratoResponse eliminarGrupoContrato(EliminarGrupoContratoRequest eliminarGrupoContratoRequest);
	
	public List<TituloGrupo> buscarTitulosGrupo(BuscarTitulosGrupoRequest buscarTitulosGrupoRequest);
	
	public Boolean validarPerpetuidadTitulo(ValidarPerpetuidadTituloRequest validarPerpetuidadTituloRequest);
	
	public Integer obtenerNuevaClaveTitulo(ObtenerNuevaClaveTituloRequest obtenerNuevaClaveTituloRequest);
	
	public Map<String, List<CalificacionOficial>> buscarCalificacionesOficiales(String clave);
	
	public ValidacionExistenciaTituloResponse validacionExistenciaTitulo(ValidacionExistenciaTituloRequest validacionExistenciaTituloRequest);

	public ValidacionRecepcionMasterResponse validacionRecepcionMaster(ValidacionRecepcionMasterRequest validacionRecepcionMasterRequest);

	public ConfirmarSeleccionTituloResponse confirmarSeleccionTitulo(ConfirmarSeleccionTituloRequest confirmarSeleccionTituloRequest);
	
	public ConfirmarSeleccionTituloResponse confirmarSeleccionTituloRecontratacion(ConfirmarSeleccionTituloRequest confirmarSeleccionTituloRequest);
	
	public List<TituloRecontratacion> buscarTitulosRecontratacion(BuscarTitulosRecontratacionRequest buscarTitulosRecontratacionRequest);
	
	public List<ValidarRemitoSNCResponse> validarRemitoSNC(ValidarRemitoSNCRequest validarRemitoSNCRequest);
	
	public List<ValidarMasterTituloResponse> validarMasterTitulo(ValidarMasterTituloRequest validarMasterTituloRequest);

	public ConfirmarSeleccionCapitulosResponse confirmarSeleccionCapitulosTituloRecontratacion(ConfirmarSeleccionCapitulosRequest confirmarSeleccionCapitulosRequest);
	
	public List<CapituloTituloRecontratacion> buscarCapitulosTituloRecontratacion(BuscarCapitulosTituloRecontratacionRequest buscarCapitulosTituloRecontratacionRequest);

	public List<ValidarRemitoSNCCapituloRecontratacionResponse> validarRemitoSNCCapituloRecontratacion(ValidarRemitoSNCCapituloRecontratacionRequest validarRemitoSNCCapituloRecontratacionRequest);
	
	public List<ValidarRemitoNoSNCResponse> validarRemitoNoSNC(ValidarRemitoNoSNCRequest validarRemitoNoSNCRequest);
	
	public ConfirmarSeleccionCapitulosResponse confirmarSeleccionCapituloTituloRecontratacion(ConfirmarSeleccionCapitulosRequest confirmarSeleccionCapitulosRequest);
	
	public List<ContratoEnlazadoTituloRecontratacion> contratosEnlazadosTituloRecontratacion(ContratosEnlazadosTituloRecontratacionRequest contratosEnlazadosTituloRecontratacionRequest);
	
	public ValidarDesenlaceTituloContratoResponse validarDesenlaceTituloContrato(ValidarDesenlaceTituloContratoRequest validarDesenlaceTituloContratoRequest);

	public EjecutarDesenlaceTituloContratoResponse ejecutarDesenlaceTituloContrato(EjecutarDesenlaceTituloContratoRequest ejecutarDesenlaceTituloContratoRequest);

	public ValidarEnlacePosteriorResponse validarEnlacePosterior(ValidarEnlacePosteriorRequest validarEnlacePosteriorRequest);

	public EjecutarEnlacePosteriorResponse ejecutarEnlacePosterior(EjecutarEnlacePosteriorRequest ejecutarEnlacePosteriorRequest);

	public List<ModificacionVigencia> visualizarModificacionesVigencia(VisualizarModificacionesVigenciaRequest visualizarModificacionesVigenciaRequest);
	
	public List<CapituloTituloRecontratacion> buscarCapitulosEliminacion(BuscarCapitulosEliminacionRequest buscarCapitulosEliminacionRequest);
	
	public String validarCantidadCapitulosBaja(ValidarCantidadCapitulosBajaRequest validarCantidadCapitulosBajaRequest);
	
	public PrimerValidacionRerunBajaCapitulosResponse primerValidacionRerunBajaCapitulos(PrimerValidacionRerunBajaCapitulosRequest primerValidacionRerunBajaCapitulosRequest);

	public SegundaValidacionRerunBajaCapitulosResponse segundaValidacionRerunBajaCapitulos(SegundaValidacionRerunBajaCapitulosRequest segundaValidacionRerunBajaCapitulosRequest);
	
	public ConfirmarBajaCapitulosResponse confirmarBajaCapitulos(ConfirmarBajaCapitulosRequest confirmarBajaCapitulosRequest);
	
	public List<CapituloTituloRecontratacion> buscarCapitulosAdicion(BuscarCapitulosAdicionRequest buscarCapitulosAdicionRequest);
	
	public List<ValidarRemitoCapitulosSNCResponse> validarRemitoCapitulosSNC(ValidarRemitoCapitulosSNCRequest validarRemitoCapitulosSNCRequest);
	
	public ConfirmarAltaCapitulosResponse confirmarAltaCapitulos(ConfirmarAltaCapitulosRequest confirmarAltaCapitulosRequest);
	
	public List<TituloEliminarContrato> buscarTitulosEliminarContrato(BuscarTitulosEliminarContratoRequest buscarTitulosEliminarContratoRequest);
	
	public ValidarEliminacionTituloContratoResponse validarEliminacionTituloContrato(ValidarEliminacionTituloContratoRequest validarEliminacionTituloContratoRequest);
	
	public ConfirmarEliminacionTituloContratoResponse confirmarEliminacionTituloContrato(ConfirmarEliminacionTituloContratoRequest confirmarEliminacionTituloContratoRequest);
	
	public List<TituloGrupo> buscarTitulosGrupoReadOnly(BuscarTitulosGrupoRequest buscarTitulosGrupoRequest);

	public ConfirmarSenialesContratoResponse modificarCabeceraContrato(ConfirmarSenialesContratoRequest confirmarSenialesContratoRequest);

}