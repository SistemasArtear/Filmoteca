package com.artear.filmo.web.action;

import static com.artear.filmo.constants.ApplicationConstants.ERROR_INESPERADO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.artear.filmo.bo.common.Distribuidor;
import com.artear.filmo.bo.common.Moneda;
import com.artear.filmo.bo.contratos.*;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.seguridad.userdetails.ExtUserDetails;
import com.artear.filmo.services.interfaces.AltaContratoService;
import com.artear.filmo.services.interfaces.GenerarReportesService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class AltaContratoAction extends ActionSupport {

	private static final long serialVersionUID = -8020322479137390860L;
	
	private String message;
	private List<Distribuidor> distribuidores;
	private List<Moneda> monedas;
	private ConfirmarSenialesContratoResponse confirmarSenialesContratoResponse;
	private List<TipoTitulo> tiposTitulo;
	private List<SenialImporte> senialesImportes;
	private GuardarDatosCabeceraContratoResponse guardarDatosCabeceraContratoResponse; 
	private boolean resultado;
	private DesenlaceContratoSenialResponse desenlaceContratoSenialResponse;
	private ValidarSumaGruposResponse validarSumaGruposResponse; 
	private List<TipoImporte> tiposImporte;
	private List<TipoDerecho> tiposDerecho;
	private List<TipoDerechoTerritorial> tiposDerechoTerritorial;
	private Integer numeroGrupo;
	private String senialHeredada;
	private ValidarAmortizacionResponse validarAmortizacionResponse;
	private ValidarModificacionGrupoCCResponse validarModificacionGrupoCCResponse;
	private ModificacionGrupoCCResponse modificacionGrupoCCResponse;
	private List<SenialHeredada> senialesHeredadas;
	private ValidarCambioTipoImporteSCResponse validarCambioTipoImporteSCResponse;
	private ValidarPasaLibreriaNaSSCResponse validarPasaLibreriaNaSSCResponse;
	private ValidarCantidadPasadasContratadasSCResponse validarCantidadPasadasContratadasSCResponse;
	private ValidarRerunSCResponse validarRerunSCResponse;
	private ValidarDesenlaceSCResponse validarDesenlaceSCResponse;
	private ValidarModificacionGrupoSCResponse validarModificacionGrupoSCResponse;
	private ModificacionGrupoSCResponse modificacionGrupoSCResponse;
	private List<ValidarAsignacionSenialHeredadaResponse> validarAsignacionSenialHeredadaResponse;
	private List<GrupoContrato> gruposContrato;
	private DesenlaceContratoGrupoResponse desenlaceContratoGrupoResponse;
	private RecuperarDatosGrupoResponse recuperarDatosGrupoResponse;
	private EliminarGrupoContratoResponse eliminarGrupoContratoResponse;
	private Integer nuevaClaveTitulo;
	private Map<String, List<CalificacionOficial>> calificacionesOficiales;
	private ValidacionExistenciaTituloResponse validacionExistenciaTituloResponse;
	private ValidacionRecepcionMasterResponse validacionRecepcionMasterResponse;
	private ConfirmarSeleccionTituloResponse confirmarSeleccionTituloResponse;
	private List<TituloRecontratacion> titulosRecontratacion;
	private List<ValidarRemitoSNCResponse> validarRemitoSNCResponse;
	private List<ValidarMasterTituloResponse> validarMasterTituloResponse;
	private ConfirmarSeleccionCapitulosResponse confirmarSeleccionCapitulosResponse;
	private List<CapituloTituloRecontratacion> capitulosTituloRecontratacion;
	private List<ValidarRemitoSNCCapituloRecontratacionResponse> validarRemitoSNCCapituloRecontratacionResponse;
	private List<ValidarRemitoNoSNCResponse> validarRemitoNoSNCResponse;
	private List<ContratoEnlazadoTituloRecontratacion> contratosEnlazadosTituloRecontratacion;
	private ValidarDesenlaceTituloContratoResponse validarDesenlaceTituloContratoResponse;
	private EjecutarDesenlaceTituloContratoResponse ejecutarDesenlaceTituloContratoResponse;
	private ValidarEnlacePosteriorResponse validarEnlacePosteriorResponse;
	private EjecutarEnlacePosteriorResponse ejecutarEnlacePosteriorResponse;
	private List<ModificacionVigencia> modificacionesVigencia;
	private String mensajeCantidadCapitulosBaja;
	private PrimerValidacionRerunBajaCapitulosResponse primerValidacionRerunBajaCapitulosResponse;
	private SegundaValidacionRerunBajaCapitulosResponse segundaValidacionRerunBajaCapitulosResponse;
	private ConfirmarBajaCapitulosResponse confirmarBajaCapitulosResponse;
	private List<ValidarRemitoCapitulosSNCResponse> validarRemitoCapitulosSNCResponse;
	private ConfirmarAltaCapitulosResponse confirmarAltaCapitulosResponse;
	private List<TituloEliminarContrato> titulosEliminarContrato;
	private ValidarEliminacionTituloContratoResponse validarEliminacionTituloContratoResponse;
	private ConfirmarEliminacionTituloContratoResponse confirmarEliminacionTituloContratoResponse;
	private List<TituloGrupo> titulosGrupo;
	
	private String descripcion;
	private GuardarDatosCabeceraContratoRequest guardarDatosCabeceraContratoRequest; 
	private GuardarContratoSenialImporteRequest guardarContratoSenialImporteRequest;
	private BuscarSenialesImportesRequest buscarSenialesImportesRequest;
	private ValidarExistenciaGruposRequest validarExistenciaGruposRequest;
	private EliminarMontoSenialRequest eliminarMontoSenialRequest;
	private DesenlaceContratoSenialRequest desenlaceContratoSenialRequest;
	private ValidarSumaGruposRequest validarSumaGruposRequest;
	private ActualizarCabeceraEliminadaRequest actualizarCabeceraEliminadaRequest;
	private ConfirmarSenialesContratoRequest confirmarSenialesContratoRequest;
	private BuscarNumeroGrupoRequest buscarNumeroGrupoRequest;
	private ValidarRecontratacionRequest validarRecontratacionRequest;
	private ValidarAmortizacionRequest validarAmortizacionRequest;
	private AltaGrupoCCRequest altaGrupoCCRequest;
	private ValidarModificacionGrupoCCRequest validarModificacionGrupoCCRequest;
	private EliminarConsultarCCRequest eliminarConsultarCCRequest;
	private ModificacionGrupoCCRequest modificacionGrupoCCRequest;
	private BuscarSenialesHeredadasRequest buscarSenialesHeredadasRequest;
	private AltaGrupoSCRequest altaGrupoSCRequest;
	private ValidarCambioTipoImporteSCRequest validarCambioTipoImporteSCRequest;
	private ValidarPasaLibreriaNaSSCRequest validarPasaLibreriaNaSSCRequest;
	private ValidarCantidadPasadasContratadasSCRequest validarCantidadPasadasContratadasSCRequest;
	private ValidarRerunSCRequest validarRerunSCRequest;
	private ValidarDesenlaceSCRequest validarDesenlaceSCRequest;
	private ValidarModificacionGrupoSCRequest validarModificacionGrupoSCRequest;
	private EliminarConsultarSCRequest eliminarConsultarSCRequest;
	private ModificacionGrupoSCRequest modificacionGrupoSCRequest;
	private ValidarAsignacionSenialHeredadaRequest validarAsignacionSenialHeredadaRequest;
	private AsignarSenialHeredadaRequest asignarSenialHeredadaRequest;
	private DesasignarSenialHeredadaRequest desasignarSenialHeredadaRequest;
	private BuscarGruposContratoRequest buscarGruposContratoRequest;
	private DesenlaceContratoGrupoRequest desenlaceContratoGrupoRequest;
	private RecuperarDatosGrupoRequest recuperarDatosGrupoRequest;
	private EliminarGrupoContratoRequest eliminarGrupoContratoRequest;
	private ValidarPerpetuidadTituloRequest validarPerpetuidadTituloRequest;
	private ObtenerNuevaClaveTituloRequest obtenerNuevaClaveTituloRequest;
	private String clave;
	private ValidacionExistenciaTituloRequest validacionExistenciaTituloRequest;
	private ValidacionRecepcionMasterRequest validacionRecepcionMasterRequest;
	private ConfirmarSeleccionTituloRequest confirmarSeleccionTituloRequest;
	private BuscarTitulosRecontratacionRequest buscarTitulosRecontratacionRequest;
	private ValidarRemitoSNCRequest validarRemitoSNCRequest;
	private ValidarMasterTituloRequest validarMasterTituloRequest;
	private ConfirmarSeleccionCapitulosRequest confirmarSeleccionCapitulosRequest;
	private BuscarCapitulosTituloRecontratacionRequest buscarCapitulosTituloRecontratacionRequest;
	private ValidarRemitoSNCCapituloRecontratacionRequest validarRemitoSNCCapituloRecontratacionRequest;
	private ValidarRemitoNoSNCRequest validarRemitoNoSNCRequest;
	private ContratosEnlazadosTituloRecontratacionRequest contratosEnlazadosTituloRecontratacionRequest;
	private ValidarDesenlaceTituloContratoRequest validarDesenlaceTituloContratoRequest;
	private EjecutarDesenlaceTituloContratoRequest ejecutarDesenlaceTituloContratoRequest;
	private ValidarEnlacePosteriorRequest validarEnlacePosteriorRequest;
	private EjecutarEnlacePosteriorRequest ejecutarEnlacePosteriorRequest;
	private VisualizarModificacionesVigenciaRequest visualizarModificacionesVigenciaRequest;
	private BuscarCapitulosEliminacionRequest buscarCapitulosEliminacionRequest;
	private ValidarCantidadCapitulosBajaRequest validarCantidadCapitulosBajaRequest;
	private PrimerValidacionRerunBajaCapitulosRequest primerValidacionRerunBajaCapitulosRequest;
	private SegundaValidacionRerunBajaCapitulosRequest segundaValidacionRerunBajaCapitulosRequest;
	private ConfirmarBajaCapitulosRequest confirmarBajaCapitulosRequest;
	private BuscarCapitulosAdicionRequest buscarCapitulosAdicionRequest;
	private ValidarRemitoCapitulosSNCRequest validarRemitoCapitulosSNCRequest;
	private ConfirmarAltaCapitulosRequest confirmarAltaCapitulosRequest;
	private BuscarTitulosEliminarContratoRequest buscarTitulosEliminarContratoRequest;
	private ValidarEliminacionTituloContratoRequest validarEliminacionTituloContratoRequest;
	private ConfirmarEliminacionTituloContratoRequest confirmarEliminacionTituloContratoRequest;
	private BuscarTitulosGrupoRequest buscarTitulosGrupoRequest;
	private BuscarTipoDeCosteoRequest buscarTipoDeCosteoRequest;
	private String tipoDeCosteo;
	
	@Autowired
	private AltaContratoService altaContratoService; 
	
    @Autowired
    private GenerarReportesService generarReportesService;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Distribuidor> getDistribuidores() {
		return distribuidores;
	}

	public void setDistribuidores(List<Distribuidor> distribuidores) {
		this.distribuidores = distribuidores;
	}

	public List<Moneda> getMonedas() {
		return monedas;
	}

	public void setMonedas(List<Moneda> monedas) {
		this.monedas = monedas;
	}

	public ConfirmarSenialesContratoResponse getConfirmarSenialesContratoResponse() {
		return confirmarSenialesContratoResponse;
	}
	
	public void setConfirmarSenialesContratoResponse(ConfirmarSenialesContratoResponse confirmarSenialesContratoResponse) {
		this.confirmarSenialesContratoResponse = confirmarSenialesContratoResponse;
	}
	
	public List<TipoTitulo> getTiposTitulo() {
		return tiposTitulo;
	}
	
	public void setTiposTitulo(List<TipoTitulo> tiposTitulo) {
		this.tiposTitulo = tiposTitulo;
	}
	
	public List<SenialImporte> getSenialesImportes() {
		return senialesImportes;
	}
	
	public void setSenialesImportes(List<SenialImporte> senialesImportes) {
		this.senialesImportes = senialesImportes;
	}
	
	public GuardarDatosCabeceraContratoResponse getGuardarDatosCabeceraContratoResponse() {
		return guardarDatosCabeceraContratoResponse;
	}

	public void setGuardarDatosCabeceraContratoResponse(GuardarDatosCabeceraContratoResponse guardarDatosCabeceraContratoResponse) {
		this.guardarDatosCabeceraContratoResponse = guardarDatosCabeceraContratoResponse;
	}

	public boolean isResultado() {
		return resultado;
	}

	public void setResultado(boolean resultado) {
		this.resultado = resultado;
	}
	
	public DesenlaceContratoSenialResponse getDesenlaceContratoSenialResponse() {
		return desenlaceContratoSenialResponse;
	}
	
	public void setDesenlaceContratoSenialResponse(DesenlaceContratoSenialResponse desenlaceContratoSenialResponse) {
		this.desenlaceContratoSenialResponse = desenlaceContratoSenialResponse;
	}
	
	public ValidarSumaGruposResponse getValidarSumaGruposResponse() {
		return validarSumaGruposResponse;
	}
	
	public void setValidarSumaGruposResponse(ValidarSumaGruposResponse validarSumaGruposResponse) {
		this.validarSumaGruposResponse = validarSumaGruposResponse;
	}
	
	public List<TipoImporte> getTiposImporte() {
		return tiposImporte;
	}
	
	public void setTiposImporte(List<TipoImporte> tiposImporte) {
		this.tiposImporte = tiposImporte;
	}
	
	public List<TipoDerecho> getTiposDerecho() {
		return tiposDerecho;
	}
	
	public void setTiposDerecho(List<TipoDerecho> tiposDerecho) {
		this.tiposDerecho = tiposDerecho;
	}
	
	public List<TipoDerechoTerritorial> getTiposDerechoTerritorial() {
		return tiposDerechoTerritorial;
	}
	
	public void setTiposDerechoTerritorial(List<TipoDerechoTerritorial> tiposDerechoTerritorial) {
		this.tiposDerechoTerritorial = tiposDerechoTerritorial;
	}
	
	public List<SenialHeredada> getSenialesHeredadas() {
		return senialesHeredadas;
	}
	
	public void setSenialesHeredadas(List<SenialHeredada> senialesHeredadas) {
		this.senialesHeredadas = senialesHeredadas;
	}
	
	public ValidarCambioTipoImporteSCResponse getValidarCambioTipoImporteSCResponse() {
		return validarCambioTipoImporteSCResponse;
	}
	
	public void setValidarCambioTipoImporteSCResponse(ValidarCambioTipoImporteSCResponse validarCambioTipoImporteSCResponse) {
		this.validarCambioTipoImporteSCResponse = validarCambioTipoImporteSCResponse;
	}
	
	public ValidarPasaLibreriaNaSSCResponse getValidarPasaLibreriaNaSSCResponse() {
		return validarPasaLibreriaNaSSCResponse;
	}
	
	public void setValidarPasaLibreriaNaSSCResponse(ValidarPasaLibreriaNaSSCResponse validarPasaLibreriaNaSSCResponse) {
		this.validarPasaLibreriaNaSSCResponse = validarPasaLibreriaNaSSCResponse;
	}
	
	public ValidarCantidadPasadasContratadasSCResponse getValidarCantidadPasadasContratadasSCResponse() {
		return validarCantidadPasadasContratadasSCResponse;
	}
	
	public void setValidarCantidadPasadasContratadasSCResponse(ValidarCantidadPasadasContratadasSCResponse validarCantidadPasadasContratadasSCResponse) {
		this.validarCantidadPasadasContratadasSCResponse = validarCantidadPasadasContratadasSCResponse;
	}
	
	public ValidarRerunSCResponse getValidarRerunSCResponse() {
		return validarRerunSCResponse;
	}
	
	public void setValidarRerunSCResponse(ValidarRerunSCResponse validarRerunSCResponse) {
		this.validarRerunSCResponse = validarRerunSCResponse;
	}
	
	public ValidarDesenlaceSCResponse getValidarDesenlaceSCResponse() {
		return validarDesenlaceSCResponse;
	}
	
	public void setValidarDesenlaceSCResponse(ValidarDesenlaceSCResponse validarDesenlaceSCResponse) {
		this.validarDesenlaceSCResponse = validarDesenlaceSCResponse;
	}
	
	public ValidarModificacionGrupoSCResponse getValidarModificacionGrupoSCResponse() {
		return validarModificacionGrupoSCResponse;
	}
	
	public void setValidarModificacionGrupoSCResponse(ValidarModificacionGrupoSCResponse validarModificacionGrupoSCResponse) {
		this.validarModificacionGrupoSCResponse = validarModificacionGrupoSCResponse;
	}
	
	public List<ValidarAsignacionSenialHeredadaResponse> getValidarAsignacionSenialHeredadaResponse() {
		return validarAsignacionSenialHeredadaResponse;
	}
	
	public void setValidarAsignacionSenialHeredadaResponse(List<ValidarAsignacionSenialHeredadaResponse> validarAsignacionSenialHeredadaResponse) {
		this.validarAsignacionSenialHeredadaResponse = validarAsignacionSenialHeredadaResponse;
	}
	
	public ModificacionGrupoSCResponse getModificacionGrupoSCResponse() {
		return modificacionGrupoSCResponse;
	}
	
	public void setModificacionGrupoSCResponse(ModificacionGrupoSCResponse modificacionGrupoSCResponse) {
		this.modificacionGrupoSCResponse = modificacionGrupoSCResponse;
	}
	
	public Integer getNumeroGrupo() {
		return numeroGrupo;
	}
	
	public void setNumeroGrupo(Integer numeroGrupo) {
		this.numeroGrupo = numeroGrupo;
	}
	
	public String getSenialHeredada() {
		return senialHeredada;
	}
	
	public void setSenialHeredada(String senialHeredada) {
		this.senialHeredada = senialHeredada;
	}
	
	public ValidarAmortizacionResponse getValidarAmortizacionResponse() {
		return validarAmortizacionResponse;
	}
	
	public void setValidarAmortizacionResponse(ValidarAmortizacionResponse validarAmortizacionResponse) {
		this.validarAmortizacionResponse = validarAmortizacionResponse;
	}
	
	public ValidarModificacionGrupoCCResponse getValidarModificacionGrupoCCResponse() {
		return validarModificacionGrupoCCResponse;
	}
	
	public void setValidarModificacionGrupoCCResponse(ValidarModificacionGrupoCCResponse validarModificacionGrupoCCResponse) {
		this.validarModificacionGrupoCCResponse = validarModificacionGrupoCCResponse;
	}
	
	public ModificacionGrupoCCResponse getModificacionGrupoCCResponse() {
		return modificacionGrupoCCResponse;
	}
	
	public void setModificacionGrupoCCResponse(ModificacionGrupoCCResponse modificacionGrupoCCResponse) {
		this.modificacionGrupoCCResponse = modificacionGrupoCCResponse;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public List<GrupoContrato> getGruposContrato() {
		return gruposContrato;
	}
	
	public void setGruposContrato(List<GrupoContrato> gruposContrato) {
		this.gruposContrato = gruposContrato;
	}
	
	public DesenlaceContratoGrupoResponse getDesenlaceContratoGrupoResponse() {
		return desenlaceContratoGrupoResponse;
	}
	
	public void setDesenlaceContratoGrupoResponse(DesenlaceContratoGrupoResponse desenlaceContratoGrupoResponse) {
		this.desenlaceContratoGrupoResponse = desenlaceContratoGrupoResponse;
	}
	
	public RecuperarDatosGrupoResponse getRecuperarDatosGrupoResponse() {
		return recuperarDatosGrupoResponse;
	}
	
	public void setRecuperarDatosGrupoResponse(RecuperarDatosGrupoResponse recuperarDatosGrupoResponse) {
		this.recuperarDatosGrupoResponse = recuperarDatosGrupoResponse;
	}
	
	public EliminarGrupoContratoResponse getEliminarGrupoContratoResponse() {
		return eliminarGrupoContratoResponse;
	}
	
	public void setEliminarGrupoContratoResponse(EliminarGrupoContratoResponse eliminarGrupoContratoResponse) {
		this.eliminarGrupoContratoResponse = eliminarGrupoContratoResponse;
	}
	
	public Integer getNuevaClaveTitulo() {
		return nuevaClaveTitulo;
	}
	
	public void setNuevaClaveTitulo(Integer nuevaClaveTitulo) {
		this.nuevaClaveTitulo = nuevaClaveTitulo;
	}
	
	public Map<String, List<CalificacionOficial>> getCalificacionesOficiales() {
		return calificacionesOficiales;
	}
	
	public void setCalificacionesOficiales(Map<String, List<CalificacionOficial>> calificacionesOficiales) {
		this.calificacionesOficiales = calificacionesOficiales;
	}
	
	public ValidacionExistenciaTituloResponse getValidacionExistenciaTituloResponse() {
		return validacionExistenciaTituloResponse;
	}
	
	public void setValidacionExistenciaTituloResponse(ValidacionExistenciaTituloResponse validacionExistenciaTituloResponse) {
		this.validacionExistenciaTituloResponse = validacionExistenciaTituloResponse;
	}
	
	public ValidacionRecepcionMasterResponse getValidacionRecepcionMasterResponse() {
		return validacionRecepcionMasterResponse;
	}
	
	public void setValidacionRecepcionMasterResponse(ValidacionRecepcionMasterResponse validacionRecepcionMasterResponse) {
		this.validacionRecepcionMasterResponse = validacionRecepcionMasterResponse;
	}
	
	public ConfirmarSeleccionTituloResponse getConfirmarSeleccionTituloResponse() {
		return confirmarSeleccionTituloResponse;
	}
	
	public void setConfirmarSeleccionTituloResponse(ConfirmarSeleccionTituloResponse confirmarSeleccionTituloResponse) {
		this.confirmarSeleccionTituloResponse = confirmarSeleccionTituloResponse;
	}
	
	public List<TituloRecontratacion> getTitulosRecontratacion() {
		return titulosRecontratacion;
	}
	
	public void setTitulosRecontratacion(List<TituloRecontratacion> titulosRecontratacion) {
		this.titulosRecontratacion = titulosRecontratacion;
	}
	
	public List<ValidarRemitoSNCResponse> getValidarRemitoSNCResponse() {
		return validarRemitoSNCResponse;
	}
	
	public void setValidarRemitoSNCResponse(List<ValidarRemitoSNCResponse> validarRemitoSNCResponse) {
		this.validarRemitoSNCResponse = validarRemitoSNCResponse;
	}
	
	public List<ValidarMasterTituloResponse> getValidarMasterTituloResponse() {
		return validarMasterTituloResponse;
	}
	
	public void setValidarMasterTituloResponse(List<ValidarMasterTituloResponse> validarMasterTituloResponse) {
		this.validarMasterTituloResponse = validarMasterTituloResponse;
	}
	
	public ConfirmarSeleccionCapitulosResponse getConfirmarSeleccionCapitulosResponse() {
		return confirmarSeleccionCapitulosResponse;
	};
	
	public void setConfirmarSeleccionCapitulosResponse(ConfirmarSeleccionCapitulosResponse confirmarSeleccionCapitulosResponse) {
		this.confirmarSeleccionCapitulosResponse = confirmarSeleccionCapitulosResponse;
	};
	
	public List<CapituloTituloRecontratacion> getCapitulosTituloRecontratacion() {
		return capitulosTituloRecontratacion;
	}
	
	public void setCapitulosTituloRecontratacion(List<CapituloTituloRecontratacion> capitulosTituloRecontratacion) {
		this.capitulosTituloRecontratacion = capitulosTituloRecontratacion;
	}
	
	public List<ValidarRemitoSNCCapituloRecontratacionResponse> getValidarRemitoSNCCapituloRecontratacionResponse() {
		return validarRemitoSNCCapituloRecontratacionResponse;
	}
	
	public void setValidarRemitoSNCCapituloRecontratacionResponse(List<ValidarRemitoSNCCapituloRecontratacionResponse> validarRemitoSNCCapituloRecontratacionResponse) {
		this.validarRemitoSNCCapituloRecontratacionResponse = validarRemitoSNCCapituloRecontratacionResponse;
	}
	
	public List<ValidarRemitoNoSNCResponse> getValidarRemitoNoSNCResponse() {
		return validarRemitoNoSNCResponse;
	}
	
	public void setValidarRemitoNoSNCResponse(List<ValidarRemitoNoSNCResponse> validarRemitoNoSNCResponse) {
		this.validarRemitoNoSNCResponse = validarRemitoNoSNCResponse;
	}
	
	public List<ContratoEnlazadoTituloRecontratacion> getContratosEnlazadosTituloRecontratacion() {
		return contratosEnlazadosTituloRecontratacion;
	}
	
	public void setContratosEnlazadosTituloRecontratacion(List<ContratoEnlazadoTituloRecontratacion> contratosEnlazadosTituloRecontratacion) {
		this.contratosEnlazadosTituloRecontratacion = contratosEnlazadosTituloRecontratacion;
	}
	
	public ValidarDesenlaceTituloContratoResponse getValidarDesenlaceTituloContratoResponse() {
		return validarDesenlaceTituloContratoResponse;
	}
	
	public void setValidarDesenlaceTituloContratoResponse(ValidarDesenlaceTituloContratoResponse validarDesenlaceTituloContratoResponse) {
		this.validarDesenlaceTituloContratoResponse = validarDesenlaceTituloContratoResponse;
	}
	
	public EjecutarDesenlaceTituloContratoResponse getEjecutarDesenlaceTituloContratoResponse() {
		return ejecutarDesenlaceTituloContratoResponse;
	}
	
	public void setEjecutarDesenlaceTituloContratoResponse(EjecutarDesenlaceTituloContratoResponse ejecutarDesenlaceTituloContratoResponse) {
		this.ejecutarDesenlaceTituloContratoResponse = ejecutarDesenlaceTituloContratoResponse;
	}
	
	public ValidarEnlacePosteriorResponse getValidarEnlacePosteriorResponse() {
		return validarEnlacePosteriorResponse;
	}
	
	public void setValidarEnlacePosteriorResponse(ValidarEnlacePosteriorResponse validarEnlacePosteriorResponse) {
		this.validarEnlacePosteriorResponse = validarEnlacePosteriorResponse;
	}
	
	public EjecutarEnlacePosteriorResponse getEjecutarEnlacePosteriorResponse() {
		return ejecutarEnlacePosteriorResponse;
	}
	
	public void setEjecutarEnlacePosteriorResponse(EjecutarEnlacePosteriorResponse ejecutarEnlacePosteriorResponse) {
		this.ejecutarEnlacePosteriorResponse = ejecutarEnlacePosteriorResponse;
	}
	
	public List<ModificacionVigencia> getModificacionesVigencia() {
		return modificacionesVigencia;
	}
	
	public void setModificacionesVigencia(List<ModificacionVigencia> modificacionesVigencia) {
		this.modificacionesVigencia = modificacionesVigencia;
	}
	
	public String getMensajeCantidadCapitulosBaja() {
		return mensajeCantidadCapitulosBaja;
	}
	
	public void setMensajeCantidadCapitulosBaja(String mensajeCantidadCapitulosBaja) {
		this.mensajeCantidadCapitulosBaja = mensajeCantidadCapitulosBaja;
	}
	
	public PrimerValidacionRerunBajaCapitulosResponse getPrimerValidacionRerunBajaCapitulosResponse() {
		return primerValidacionRerunBajaCapitulosResponse;
	}
	
	public void setPrimerValidacionRerunBajaCapitulosResponse(PrimerValidacionRerunBajaCapitulosResponse primerValidacionRerunBajaCapitulosResponse) {
		this.primerValidacionRerunBajaCapitulosResponse = primerValidacionRerunBajaCapitulosResponse;
	}
	
	public SegundaValidacionRerunBajaCapitulosResponse getSegundaValidacionRerunBajaCapitulosResponse() {
		return segundaValidacionRerunBajaCapitulosResponse;
	}

	public void setSegundaValidacionRerunBajaCapitulosResponse(SegundaValidacionRerunBajaCapitulosResponse segundaValidacionRerunBajaCapitulosResponse) {
		this.segundaValidacionRerunBajaCapitulosResponse = segundaValidacionRerunBajaCapitulosResponse;
	}
	
	public ConfirmarBajaCapitulosResponse getConfirmarBajaCapitulosResponse() {
		return confirmarBajaCapitulosResponse;
	}
	
	public void setConfirmarBajaCapitulosResponse(ConfirmarBajaCapitulosResponse confirmarBajaCapitulosResponse) {
		this.confirmarBajaCapitulosResponse = confirmarBajaCapitulosResponse;
	}
	
	public List<ValidarRemitoCapitulosSNCResponse> getValidarRemitoCapitulosSNCResponse() {
		return validarRemitoCapitulosSNCResponse;
	}
	
	public void setValidarRemitoCapitulosSNCResponse(List<ValidarRemitoCapitulosSNCResponse> validarRemitoCapitulosSNCResponse) {
		this.validarRemitoCapitulosSNCResponse = validarRemitoCapitulosSNCResponse;
	}
	
	public ConfirmarAltaCapitulosResponse getConfirmarAltaCapitulosResponse() {
		return confirmarAltaCapitulosResponse;
	}
	
	public void setConfirmarAltaCapitulosResponse(ConfirmarAltaCapitulosResponse confirmarAltaCapitulosResponse) {
		this.confirmarAltaCapitulosResponse = confirmarAltaCapitulosResponse;
	}
	
	public List<TituloEliminarContrato> getTitulosEliminarContrato() {
		return titulosEliminarContrato;
	}
	
	public void setTitulosEliminarContrato(List<TituloEliminarContrato> titulosEliminarContrato) {
		this.titulosEliminarContrato = titulosEliminarContrato;
	}
	
	public ValidarEliminacionTituloContratoResponse getValidarEliminacionTituloContratoResponse() {
		return validarEliminacionTituloContratoResponse;
	}
	
	public void setValidarEliminacionTituloContratoResponse(ValidarEliminacionTituloContratoResponse validarEliminacionTituloContratoResponse) {
		this.validarEliminacionTituloContratoResponse = validarEliminacionTituloContratoResponse;
	}
	
	public ConfirmarEliminacionTituloContratoResponse getConfirmarEliminacionTituloContratoResponse() {
		return confirmarEliminacionTituloContratoResponse;
	}
	
	public void setConfirmarEliminacionTituloContratoResponse(ConfirmarEliminacionTituloContratoResponse confirmarEliminacionTituloContratoResponse) {
		this.confirmarEliminacionTituloContratoResponse = confirmarEliminacionTituloContratoResponse;
	}
	
	public List<TituloGrupo> getTitulosGrupo() {
		return titulosGrupo;
	}
	
	public void setTitulosGrupo(List<TituloGrupo> titulosGrupo) {
		this.titulosGrupo = titulosGrupo;
	}
	
	public GuardarDatosCabeceraContratoRequest getGuardarDatosCabeceraContratoRequest() {
		return guardarDatosCabeceraContratoRequest;
	}

	public void setGuardarDatosCabeceraContratoRequest(GuardarDatosCabeceraContratoRequest guardarDatosCabeceraContratoRequest) {
		this.guardarDatosCabeceraContratoRequest = guardarDatosCabeceraContratoRequest;
	}

	public GuardarContratoSenialImporteRequest getGuardarContratoSenialImporteRequest() {
		return guardarContratoSenialImporteRequest;
	}

	public void setGuardarContratoSenialImporteRequest(GuardarContratoSenialImporteRequest guardarContratoSenialImporteRequest) {
		this.guardarContratoSenialImporteRequest = guardarContratoSenialImporteRequest;
	}
	
    public BuscarSenialesImportesRequest getBuscarSenialesImportesRequest() {
		return buscarSenialesImportesRequest;
	}

	public void setBuscarSenialesImportesRequest(BuscarSenialesImportesRequest buscarSenialesImportesRequest) {
		this.buscarSenialesImportesRequest = buscarSenialesImportesRequest;
	}

	public ValidarExistenciaGruposRequest getValidarExistenciaGruposRequest() {
		return validarExistenciaGruposRequest;
	}
	
	public void setValidarExistenciaGruposRequest(ValidarExistenciaGruposRequest validarExistenciaGruposRequest) {
		this.validarExistenciaGruposRequest = validarExistenciaGruposRequest;
	}
	
	public EliminarMontoSenialRequest getEliminarMontoSenialRequest() {
		return eliminarMontoSenialRequest;
	}
	
	public void setEliminarMontoSenialRequest(EliminarMontoSenialRequest eliminarMontoSenialRequest) {
		this.eliminarMontoSenialRequest = eliminarMontoSenialRequest;
	}

	public DesenlaceContratoSenialRequest getDesenlaceContratoSenialRequest() {
		return desenlaceContratoSenialRequest;
	}
	
	public void setDesenlaceContratoSenialRequest(DesenlaceContratoSenialRequest desenlaceContratoSenialRequest) {
		this.desenlaceContratoSenialRequest = desenlaceContratoSenialRequest;
	}
	
	public ValidarSumaGruposRequest getValidarSumaGruposRequest() {
		return validarSumaGruposRequest;
	}
	
	public void setValidarSumaGruposRequest(ValidarSumaGruposRequest validarSumaGruposRequest) {
		this.validarSumaGruposRequest = validarSumaGruposRequest;
	}
	
	public ActualizarCabeceraEliminadaRequest getActualizarCabeceraEliminadaRequest() {
		return actualizarCabeceraEliminadaRequest;
	}
	
	public void setActualizarCabeceraEliminadaRequest(ActualizarCabeceraEliminadaRequest actualizarCabeceraEliminadaRequest) {
		this.actualizarCabeceraEliminadaRequest = actualizarCabeceraEliminadaRequest;
	}
	
	public ConfirmarSenialesContratoRequest getConfirmarSenialesContratoRequest() {
		return confirmarSenialesContratoRequest;
	}
	
	public void setConfirmarSenialesContratoRequest(ConfirmarSenialesContratoRequest confirmarSenialesContratoRequest) {
		this.confirmarSenialesContratoRequest = confirmarSenialesContratoRequest;
	}
	
	public BuscarNumeroGrupoRequest getBuscarNumeroGrupoRequest() {
		return buscarNumeroGrupoRequest;
	}
	
	public void setBuscarNumeroGrupoRequest(BuscarNumeroGrupoRequest buscarNumeroGrupoRequest) {
		this.buscarNumeroGrupoRequest = buscarNumeroGrupoRequest;
	}
	
	public ValidarRecontratacionRequest getValidarRecontratacionRequest() {
		return validarRecontratacionRequest;
	}
	
	public void setValidarRecontratacionRequest(ValidarRecontratacionRequest validarRecontratacionRequest) {
		this.validarRecontratacionRequest = validarRecontratacionRequest;
	}
	
	public ValidarAmortizacionRequest getValidarAmortizacionRequest() {
		return validarAmortizacionRequest;
	}
	
	public void setValidarAmortizacionRequest(ValidarAmortizacionRequest validarAmortizacionRequest) {
		this.validarAmortizacionRequest = validarAmortizacionRequest;
	}
	
	public AltaGrupoCCRequest getAltaGrupoCCRequest() {
		return altaGrupoCCRequest;
	}
	
	public void setAltaGrupoCCRequest(AltaGrupoCCRequest altaGrupoCCRequest) {
		this.altaGrupoCCRequest = altaGrupoCCRequest;
	}
	
	public ValidarModificacionGrupoCCRequest getValidarModificacionGrupoCCRequest() {
		return validarModificacionGrupoCCRequest;
	}
	
	public void setValidarModificacionGrupoCCRequest(ValidarModificacionGrupoCCRequest validarModificacionGrupoCCRequest) {
		this.validarModificacionGrupoCCRequest = validarModificacionGrupoCCRequest;
	}
	
	public EliminarConsultarCCRequest getEliminarConsultarCCRequest() {
		return eliminarConsultarCCRequest;
	}
	
	public void setEliminarConsultarCCRequest(EliminarConsultarCCRequest eliminarConsultarCCRequest) {
		this.eliminarConsultarCCRequest = eliminarConsultarCCRequest;
	}
	
	public ModificacionGrupoCCRequest getModificacionGrupoCCRequest() {
		return modificacionGrupoCCRequest;
	}
	
	public void setModificacionGrupoCCRequest(ModificacionGrupoCCRequest modificacionGrupoCCRequest) {
		this.modificacionGrupoCCRequest = modificacionGrupoCCRequest;
	}
	
	public BuscarSenialesHeredadasRequest getBuscarSenialesHeredadasRequest() {
		return buscarSenialesHeredadasRequest;
	}
	
	public void setBuscarSenialesHeredadasRequest(BuscarSenialesHeredadasRequest buscarSenialesHeredadasRequest) {
		this.buscarSenialesHeredadasRequest = buscarSenialesHeredadasRequest;
	}
	
	public AltaGrupoSCRequest getAltaGrupoSCRequest() {
		return altaGrupoSCRequest;
	}
	
	public void setAltaGrupoSCRequest(AltaGrupoSCRequest altaGrupoSCRequest) {
		this.altaGrupoSCRequest = altaGrupoSCRequest;
	}
	
	public ValidarCambioTipoImporteSCRequest getValidarCambioTipoImporteSCRequest() {
		return validarCambioTipoImporteSCRequest;
	}
	
	public void setValidarCambioTipoImporteSCRequest(ValidarCambioTipoImporteSCRequest validarCambioTipoImporteSCRequest) {
		this.validarCambioTipoImporteSCRequest = validarCambioTipoImporteSCRequest;
	}
	
	public ValidarPasaLibreriaNaSSCRequest getValidarPasaLibreriaNaSSCRequest() {
		return validarPasaLibreriaNaSSCRequest;
	}
	
	public void setValidarPasaLibreriaNaSSCRequest(ValidarPasaLibreriaNaSSCRequest validarPasaLibreriaNaSSCRequest) {
		this.validarPasaLibreriaNaSSCRequest = validarPasaLibreriaNaSSCRequest;
	}
	
	public ValidarCantidadPasadasContratadasSCRequest getValidarCantidadPasadasContratadasSCRequest() {
		return validarCantidadPasadasContratadasSCRequest;
	}
	
	public void setValidarCantidadPasadasContratadasSCRequest(ValidarCantidadPasadasContratadasSCRequest validarCantidadPasadasContratadasSCRequest) {
		this.validarCantidadPasadasContratadasSCRequest = validarCantidadPasadasContratadasSCRequest;
	}
	
	public ValidarRerunSCRequest getValidarRerunSCRequest() {
		return validarRerunSCRequest;
	}
	
	public void setValidarRerunSCRequest(ValidarRerunSCRequest validarRerunSCRequest) {
		this.validarRerunSCRequest = validarRerunSCRequest;
	}
	
	public ValidarDesenlaceSCRequest getValidarDesenlaceSCRequest() {
		return validarDesenlaceSCRequest;
	}
	
	public void setValidarDesenlaceSCRequest(ValidarDesenlaceSCRequest validarDesenlaceSCRequest) {
		this.validarDesenlaceSCRequest = validarDesenlaceSCRequest;
	}
	
	public ValidarModificacionGrupoSCRequest getValidarModificacionGrupoSCRequest() {
		return validarModificacionGrupoSCRequest;
	}
	
	public void setValidarModificacionGrupoSCRequest(ValidarModificacionGrupoSCRequest validarModificacionGrupoSCRequest) {
		this.validarModificacionGrupoSCRequest = validarModificacionGrupoSCRequest;
	}
	
	public EliminarConsultarSCRequest getEliminarConsultarSCRequest() {
		return eliminarConsultarSCRequest;
	}
	
	public void setEliminarConsultarSCRequest(EliminarConsultarSCRequest eliminarConsultarSCRequest) {
		this.eliminarConsultarSCRequest = eliminarConsultarSCRequest;
	}
	
	public ModificacionGrupoSCRequest getModificacionGrupoSCRequest() {
		return modificacionGrupoSCRequest;
	}
	
	public void setModificacionGrupoSCRequest(ModificacionGrupoSCRequest modificacionGrupoSCRequest) {
		this.modificacionGrupoSCRequest = modificacionGrupoSCRequest;
	}
	
	public ValidarAsignacionSenialHeredadaRequest getValidarAsignacionSenialHeredadaRequest() {
		return validarAsignacionSenialHeredadaRequest;
	}
	
	public void setValidarAsignacionSenialHeredadaRequest(ValidarAsignacionSenialHeredadaRequest validarAsignacionSenialHeredadaRequest) {
		this.validarAsignacionSenialHeredadaRequest = validarAsignacionSenialHeredadaRequest;
	}
	
	public AsignarSenialHeredadaRequest getAsignarSenialHeredadaRequest() {
		return asignarSenialHeredadaRequest;
	}
	
	public void setAsignarSenialHeredadaRequest(AsignarSenialHeredadaRequest asignarSenialHeredadaRequest) {
		this.asignarSenialHeredadaRequest = asignarSenialHeredadaRequest;
	}
	
	public DesasignarSenialHeredadaRequest getDesasignarSenialHeredadaRequest() {
		return desasignarSenialHeredadaRequest;
	}
	
	public void setDesasignarSenialHeredadaRequest(DesasignarSenialHeredadaRequest desasignarSenialHeredadaRequest) {
		this.desasignarSenialHeredadaRequest = desasignarSenialHeredadaRequest;
	}
	
	public BuscarGruposContratoRequest getBuscarGruposContratoRequest() {
		return buscarGruposContratoRequest;
	}
	
	public void setBuscarGruposContratoRequest(BuscarGruposContratoRequest buscarGruposContratoRequest) {
		this.buscarGruposContratoRequest = buscarGruposContratoRequest;
	}
	
	public DesenlaceContratoGrupoRequest getDesenlaceContratoGrupoRequest() {
		return desenlaceContratoGrupoRequest;
	}
	
	public void setDesenlaceContratoGrupoRequest(DesenlaceContratoGrupoRequest desenlaceContratoGrupoRequest) {
		this.desenlaceContratoGrupoRequest = desenlaceContratoGrupoRequest;
	}
	
	public RecuperarDatosGrupoRequest getRecuperarDatosGrupoRequest() {
		return recuperarDatosGrupoRequest;
	}
	
	public void setRecuperarDatosGrupoRequest(RecuperarDatosGrupoRequest recuperarDatosGrupoRequest) {
		this.recuperarDatosGrupoRequest = recuperarDatosGrupoRequest;
	}
	
	public EliminarGrupoContratoRequest getEliminarGrupoContratoRequest() {
		return eliminarGrupoContratoRequest;
	}
	
	public void setEliminarGrupoContratoRequest(EliminarGrupoContratoRequest eliminarGrupoContratoRequest) {
		this.eliminarGrupoContratoRequest = eliminarGrupoContratoRequest;
	}
	
	public ValidarPerpetuidadTituloRequest getValidarPerpetuidadTituloRequest() {
		return validarPerpetuidadTituloRequest;
	}
	
	public void setValidarPerpetuidadTituloRequest(ValidarPerpetuidadTituloRequest validarPerpetuidadTituloRequest) {
		this.validarPerpetuidadTituloRequest = validarPerpetuidadTituloRequest;
	}
	
	public ObtenerNuevaClaveTituloRequest getObtenerNuevaClaveTituloRequest() {
		return obtenerNuevaClaveTituloRequest;
	}
	
	public void setObtenerNuevaClaveTituloRequest(ObtenerNuevaClaveTituloRequest obtenerNuevaClaveTituloRequest) {
		this.obtenerNuevaClaveTituloRequest = obtenerNuevaClaveTituloRequest;
	}
	
	public String getClave() {
		return clave;
	}
	
	public void setClave(String clave) {
		this.clave = clave;
	}
	
	public ValidacionExistenciaTituloRequest getValidacionExistenciaTituloRequest() {
		return validacionExistenciaTituloRequest;
	}
	
	public void setValidacionExistenciaTituloRequest(ValidacionExistenciaTituloRequest validacionExistenciaTituloRequest) {
		this.validacionExistenciaTituloRequest = validacionExistenciaTituloRequest;
	}
	
	public ValidacionRecepcionMasterRequest getValidacionRecepcionMasterRequest() {
		return validacionRecepcionMasterRequest;
	}
	
	public void setValidacionRecepcionMasterRequest(ValidacionRecepcionMasterRequest validacionRecepcionMasterRequest) {
		this.validacionRecepcionMasterRequest = validacionRecepcionMasterRequest;
	}
	
	public ConfirmarSeleccionTituloRequest getConfirmarSeleccionTituloRequest() {
		return confirmarSeleccionTituloRequest;
	}
	
	public void setConfirmarSeleccionTituloRequest(ConfirmarSeleccionTituloRequest confirmarSeleccionTituloRequest) {
		this.confirmarSeleccionTituloRequest = confirmarSeleccionTituloRequest;
	}
	
	public BuscarTitulosRecontratacionRequest getBuscarTitulosRecontratacionRequest() {
		return buscarTitulosRecontratacionRequest;
	} 
	
	public void setBuscarTitulosRecontratacionRequest(BuscarTitulosRecontratacionRequest buscarTitulosRecontratacionRequest) {
		this.buscarTitulosRecontratacionRequest = buscarTitulosRecontratacionRequest;
	}
	
	public ValidarRemitoSNCRequest getValidarRemitoSNCRequest() {
		return validarRemitoSNCRequest;
	}
	
	public void setValidarRemitoSNCRequest(ValidarRemitoSNCRequest validarRemitoSNCRequest) {
		this.validarRemitoSNCRequest = validarRemitoSNCRequest;
	}
	
	public ValidarMasterTituloRequest getValidarMasterTituloRequest() {
		return validarMasterTituloRequest;
	}
	
	public void setValidarMasterTituloRequest(ValidarMasterTituloRequest validarMasterTituloRequest) {
		this.validarMasterTituloRequest = validarMasterTituloRequest;
	}
	
	public ConfirmarSeleccionCapitulosRequest getConfirmarSeleccionCapitulosRequest() {
		return confirmarSeleccionCapitulosRequest;
	}
	
	public void setConfirmarSeleccionCapitulosRequest(ConfirmarSeleccionCapitulosRequest confirmarSeleccionCapitulosRequest) {
		this.confirmarSeleccionCapitulosRequest = confirmarSeleccionCapitulosRequest;
	}
	
	public BuscarCapitulosTituloRecontratacionRequest getBuscarCapitulosTituloRecontratacionRequest() {
		return buscarCapitulosTituloRecontratacionRequest;
	}
	
	public void setBuscarCapitulosTituloRecontratacionRequest(BuscarCapitulosTituloRecontratacionRequest buscarCapitulosTituloRecontratacionRequest) {
		this.buscarCapitulosTituloRecontratacionRequest = buscarCapitulosTituloRecontratacionRequest;
	}
	
	public ValidarRemitoSNCCapituloRecontratacionRequest getValidarRemitoSNCCapituloRecontratacionRequest() {
		return validarRemitoSNCCapituloRecontratacionRequest;
	}
	
	public void setValidarRemitoSNCCapituloRecontratacionRequest(ValidarRemitoSNCCapituloRecontratacionRequest validarRemitoSNCCapituloRecontratacionRequest) {
		this.validarRemitoSNCCapituloRecontratacionRequest = validarRemitoSNCCapituloRecontratacionRequest;
	}
	
	public ValidarRemitoNoSNCRequest getValidarRemitoNoSNCRequest() {
		return validarRemitoNoSNCRequest;
	}
	
	public void setValidarRemitoNoSNCRequest(ValidarRemitoNoSNCRequest validarRemitoNoSNCRequest) {
		this.validarRemitoNoSNCRequest = validarRemitoNoSNCRequest;
	}
	
	public ContratosEnlazadosTituloRecontratacionRequest getContratosEnlazadosTituloRecontratacionRequest() {
		return contratosEnlazadosTituloRecontratacionRequest;
	}
	
	public void setContratosEnlazadosTituloRecontratacionRequest(ContratosEnlazadosTituloRecontratacionRequest contratosEnlazadosTituloRecontratacionRequest) {
		this.contratosEnlazadosTituloRecontratacionRequest = contratosEnlazadosTituloRecontratacionRequest;
	}
	
	public ValidarDesenlaceTituloContratoRequest getValidarDesenlaceTituloContratoRequest() {
		return validarDesenlaceTituloContratoRequest;
	}
	
	public void setValidarDesenlaceTituloContratoRequest(ValidarDesenlaceTituloContratoRequest validarDesenlaceTituloContratoRequest) {
		this.validarDesenlaceTituloContratoRequest = validarDesenlaceTituloContratoRequest;
	}
	
	public EjecutarDesenlaceTituloContratoRequest getEjecutarDesenlaceTituloContratoRequest() {
		return ejecutarDesenlaceTituloContratoRequest;
	}
	
	public void setEjecutarDesenlaceTituloContratoRequest(EjecutarDesenlaceTituloContratoRequest ejecutarDesenlaceTituloContratoRequest) {
		this.ejecutarDesenlaceTituloContratoRequest = ejecutarDesenlaceTituloContratoRequest;
	}
	
	public ValidarEnlacePosteriorRequest getValidarEnlacePosteriorRequest() {
		return validarEnlacePosteriorRequest;
	}
	
	public void setValidarEnlacePosteriorRequest(ValidarEnlacePosteriorRequest validarEnlacePosteriorRequest) {
		this.validarEnlacePosteriorRequest = validarEnlacePosteriorRequest;
	}
	
	public EjecutarEnlacePosteriorRequest getEjecutarEnlacePosteriorRequest() {
		return ejecutarEnlacePosteriorRequest;
	}
	
	public void setEjecutarEnlacePosteriorRequest(EjecutarEnlacePosteriorRequest ejecutarEnlacePosteriorRequest) {
		this.ejecutarEnlacePosteriorRequest = ejecutarEnlacePosteriorRequest;
	}
	
	public VisualizarModificacionesVigenciaRequest getVisualizarModificacionesVigenciaRequest() {
		return visualizarModificacionesVigenciaRequest;
	}
	
	public void setVisualizarModificacionesVigenciaRequest(VisualizarModificacionesVigenciaRequest visualizarModificacionesVigenciaRequest) {
		this.visualizarModificacionesVigenciaRequest = visualizarModificacionesVigenciaRequest;
	}
	
	public BuscarCapitulosEliminacionRequest getBuscarCapitulosEliminacionRequest() {
		return buscarCapitulosEliminacionRequest;
	}
	
	public void setBuscarCapitulosEliminacionRequest(BuscarCapitulosEliminacionRequest buscarCapitulosEliminacionRequest) {
		this.buscarCapitulosEliminacionRequest = buscarCapitulosEliminacionRequest;
	}
	
	public ValidarCantidadCapitulosBajaRequest getValidarCantidadCapitulosBajaRequest() {
		return validarCantidadCapitulosBajaRequest;
	}
	
	public void setValidarCantidadCapitulosBajaRequest(ValidarCantidadCapitulosBajaRequest validarCantidadCapitulosBajaRequest) {
		this.validarCantidadCapitulosBajaRequest = validarCantidadCapitulosBajaRequest;
	}
	
	public PrimerValidacionRerunBajaCapitulosRequest getPrimerValidacionRerunBajaCapitulosRequest() {
		return primerValidacionRerunBajaCapitulosRequest;
	}
	
	public void setPrimerValidacionRerunBajaCapitulosRequest(PrimerValidacionRerunBajaCapitulosRequest primerValidacionRerunBajaCapitulosRequest) {
		this.primerValidacionRerunBajaCapitulosRequest = primerValidacionRerunBajaCapitulosRequest;
	}
	
	public SegundaValidacionRerunBajaCapitulosRequest getSegundaValidacionRerunBajaCapitulosRequest() {
		return segundaValidacionRerunBajaCapitulosRequest;
	}
	
	public void setSegundaValidacionRerunBajaCapitulosRequest(SegundaValidacionRerunBajaCapitulosRequest segundaValidacionRerunBajaCapitulosRequest) {
		this.segundaValidacionRerunBajaCapitulosRequest = segundaValidacionRerunBajaCapitulosRequest;
	}
	
	public ConfirmarBajaCapitulosRequest getConfirmarBajaCapitulosRequest() {
		return confirmarBajaCapitulosRequest;
	}
	
	public void setConfirmarBajaCapitulosRequest(ConfirmarBajaCapitulosRequest confirmarBajaCapitulosRequest) {
		this.confirmarBajaCapitulosRequest = confirmarBajaCapitulosRequest;
	}
	
	public BuscarCapitulosAdicionRequest getBuscarCapitulosAdicionRequest() {
		return buscarCapitulosAdicionRequest;
	}
	
	public void setBuscarCapitulosAdicionRequest(BuscarCapitulosAdicionRequest buscarCapitulosAdicionRequest) {
		this.buscarCapitulosAdicionRequest = buscarCapitulosAdicionRequest;
	}
	
	public ValidarRemitoCapitulosSNCRequest getValidarRemitoCapitulosSNCRequest() {
		return validarRemitoCapitulosSNCRequest;
	}
	
	public void setValidarRemitoCapitulosSNCRequest(ValidarRemitoCapitulosSNCRequest validarRemitoCapitulosSNCRequest) {
		this.validarRemitoCapitulosSNCRequest = validarRemitoCapitulosSNCRequest;
	}
	
	public ConfirmarAltaCapitulosRequest getConfirmarAltaCapitulosRequest() {
		return confirmarAltaCapitulosRequest;
	}
	
	public void setConfirmarAltaCapitulosRequest(ConfirmarAltaCapitulosRequest confirmarAltaCapitulosRequest) {
		this.confirmarAltaCapitulosRequest = confirmarAltaCapitulosRequest;
	}
	
	public BuscarTitulosEliminarContratoRequest getBuscarTitulosEliminarContratoRequest() {
		return buscarTitulosEliminarContratoRequest;
	}
	
	public void setBuscarTitulosEliminarContratoRequest(BuscarTitulosEliminarContratoRequest buscarTitulosEliminarContratoRequest) {
		this.buscarTitulosEliminarContratoRequest = buscarTitulosEliminarContratoRequest;
	}
	
	public ValidarEliminacionTituloContratoRequest getValidarEliminacionTituloContratoRequest() {
		return validarEliminacionTituloContratoRequest;
	}
	
	public void setValidarEliminacionTituloContratoRequest(ValidarEliminacionTituloContratoRequest validarEliminacionTituloContratoRequest) {
		this.validarEliminacionTituloContratoRequest = validarEliminacionTituloContratoRequest;
	}
	
	public ConfirmarEliminacionTituloContratoRequest getConfirmarEliminacionTituloContratoRequest() {
		return confirmarEliminacionTituloContratoRequest;
	}
	
	public void setConfirmarEliminacionTituloContratoRequest(ConfirmarEliminacionTituloContratoRequest confirmarEliminacionTituloContratoRequest) {
		this.confirmarEliminacionTituloContratoRequest = confirmarEliminacionTituloContratoRequest;
	}
	
	public BuscarTitulosGrupoRequest getBuscarTitulosGrupoRequest() {
		return buscarTitulosGrupoRequest;
	}
	
	public void setBuscarTitulosGrupoRequest(BuscarTitulosGrupoRequest buscarTitulosGrupoRequest) {
		this.buscarTitulosGrupoRequest = buscarTitulosGrupoRequest;
	} 
	
	public BuscarTipoDeCosteoRequest getBuscarTipoDeCosteoRequest() {
		return buscarTipoDeCosteoRequest;
	}

	public void setBuscarTipoDeCosteoRequest(BuscarTipoDeCosteoRequest buscarTipoDeCosteoRequest) {
		this.buscarTipoDeCosteoRequest = buscarTipoDeCosteoRequest;
	}

	public String getTipoDeCosteo() {
		return tipoDeCosteo;
	}

	public void setTipoDeCosteo(String tipoDeCosteo) {
		this.tipoDeCosteo = tipoDeCosteo;
	}

	/**
     * FP9001 - Búsqueda de distribuidores por razón social 
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String buscarDistribuidores() {
		try {
			distribuidores = altaContratoService.buscarDistribuidores(descripcion);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP9016 - Búsqueda de monedas por descripción 
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String buscarMonedas() {
		try {
			monedas = altaContratoService.buscarMonedas(descripcion);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
    /**
     * FP11001 - Guardado de datos de la cabecera de contrato 
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String guardarDatosCabeceraContrato() {
		try {
			guardarDatosCabeceraContratoResponse = altaContratoService.guardarDatosCabeceraContrato(guardarDatosCabeceraContratoRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
    /**
     * FP11002 - Guardado de un importe y señal seleccionados para un contrato 
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String guardarContratoSenialImporte() {
		try {
			resultado = altaContratoService.guardarContratoSenialImporte(guardarContratoSenialImporteRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
    /**
     * FP11003 - Busqueda de señales e importes para un contrato 
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String buscarSenialesImportes() {
		try {
			senialesImportes = altaContratoService.buscarSenialesImportes(buscarSenialesImportesRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
    /**
     * FP11003 - Busqueda de señales e importes para un contrato 
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String validarExistenciaGrupos() {
		try {
			resultado = altaContratoService.validarExistenciaGrupos(validarExistenciaGruposRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
    /**
     * FP11003 - Modificación de importe y señal seleccionados para un contrato 
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String modificarContratoSenialImporte() {
		try {
			resultado = altaContratoService.modificarContratoSenialImporte(guardarContratoSenialImporteRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11003 - Validación de desenlace de señal-contrato 
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String desenlaceContratoSenial() {
		try {
			desenlaceContratoSenialResponse = altaContratoService.desenlaceContratoSenial(desenlaceContratoSenialRequest);
			generarReporteTitulosADesconfirmar(desenlaceContratoSenialResponse.getIdReporte());
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
    /**
     * FP11003 - Eliminación de importe y señal de un contrato 
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String eliminarMontoSenial() {	
		try {
			resultado = altaContratoService.eliminarMontoSenial(eliminarMontoSenialRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
    /**
     * FP11003 - Validación de la suma de los grupos contra contrato-señal si se cargaron grupos o 
     * validación de la cabecera del contrato contra la sumatoria de cabecera señal
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String validarSumaGrupos() {	
		try {
			validarSumaGruposResponse = altaContratoService.validarSumaGrupos(validarSumaGruposRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
    /**
     * FP11003 - Actualizacion de la cabecera del contrato luego de la elmininacion de una señal
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String actualizarCabeceraEliminada() {	
		try {
			resultado = altaContratoService.actualizarCabeceraEliminada(actualizarCabeceraEliminadaRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
    /**
     * FP11003 - Confirmación de las señales del contrato
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String confirmarSenialesContrato() {
		try {
			confirmarSenialesContratoResponse = altaContratoService.confirmarSenialesContrato(confirmarSenialesContratoRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	
	public String modificarCabeceraContrato() {
		try {
			confirmarSenialesContratoResponse = altaContratoService.modificarCabeceraContrato(confirmarSenialesContratoRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	
	/**
     * FP11004 - Búsqueda de tipos de título
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String buscarTiposTitulo() {
		try {
			tiposTitulo = altaContratoService.buscarTiposTitulo();
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11005 - Búsqueda de tipos de importe
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String buscarTiposImporte() {
		try {
			tiposImporte = altaContratoService.buscarTiposImporte();
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11005 - Búsqueda de tipos de derecho
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String buscarTiposDerecho() {
		try {
			tiposDerecho = altaContratoService.buscarTiposDerecho();
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	/**
     * FP11005 - Búsqueda de tipos de derecho territorial
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String buscarTiposDerechoTerritorial() {
		try {
			tiposDerechoTerritorial = altaContratoService.buscarTiposDerechoTerritorial();
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11005/7 - Búsqueda del próximo número de grupo a dar de alta
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String buscarNumeroGrupo() {
		try {
			numeroGrupo = altaContratoService.buscarNumeroGrupo(buscarNumeroGrupoRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11005 - Validación que determina si pueden heredarse señales
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String buscarSenialHeredadaCC() {
		try {
			senialHeredada = altaContratoService.buscarSenialHeredadaCC(buscarSenialesHeredadasRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11005 - Validación de recontratación
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String validarRecontratacion() {
		try {
			resultado = altaContratoService.validarRecontratacion(validarRecontratacionRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	/**
     * FP11005 - Validación de amortización
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String validarAmortizacionCC() {
		try {
			validarAmortizacionResponse = altaContratoService.validarAmortizacionCC(validarAmortizacionRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11005 - Alta de grupo con capitulos 
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String altaGrupoCC() {
		try {
			resultado = altaContratoService.altaGrupoCC(altaGrupoCCRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11005 - Validaciones previas a la modificación de grupo CC
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String validarModificacionGrupoCC() {
		try {
			validarModificacionGrupoCCResponse = altaContratoService.validarModificacionGrupoCC(validarModificacionGrupoCCRequest);
			generarReporteTitulosADesconfirmar(validarModificacionGrupoCCResponse.getIdReporte());
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	
	/**
     * FP11005 - Eliminar marca consultar
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String eliminarConsultarCC() {
		try {
			resultado = altaContratoService.eliminarConsultarCC(eliminarConsultarCCRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	/**
     * FP11005 - Modificación de grupo CC
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String modificacionGrupoCC() {
		try {
			modificacionGrupoCCResponse = altaContratoService.modificacionGrupoCC(modificacionGrupoCCRequest);
			generarReporteTitulosADesconfirmar(modificacionGrupoCCResponse.getIdReporte());
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11006 - Búsqueda de señales heredadas
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String buscarSenialesHeredadas() {
		try {
			senialesHeredadas = altaContratoService.buscarSenialesHeredadas(buscarSenialesHeredadasRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11006 - Validar asignación de señal heredada
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String validarAsignacionSenialHeredadaCC() {
		try {
			validarAsignacionSenialHeredadaResponse = altaContratoService.validarAsignacionSenialHeredadaCC(validarAsignacionSenialHeredadaRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11006 - Asignación de señal heredada
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String asignarSenialHeredadaCC() {
		try {
			resultado = altaContratoService.asignarSenialHeredadaCC(asignarSenialHeredadaRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11006 - Desasignación de señal heredada
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String desasignarSenialHeredadaCC() {
		try {
			resultado = altaContratoService.desasignarSenialHeredadaCC(desasignarSenialHeredadaRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	/**
     * FP11007 - Validación que determina si pueden heredarse señales
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String buscarSenialHeredadaSC() {
		try {
			senialHeredada = altaContratoService.buscarSenialHeredadaSC(buscarSenialesHeredadasRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11007 - Validación de amortización
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String validarAmortizacionSC() {
		try {
			validarAmortizacionResponse = altaContratoService.validarAmortizacionSC(validarAmortizacionRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11007 - Alta de grupo sin capítulos 
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String altaGrupoSC() {
		try {
			resultado = altaContratoService.altaGrupoSC(altaGrupoSCRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11007 - Valida el cambio del tipo de importe para el grupo 
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String validarCambioTipoImporteSC() {
		try {
			validarCambioTipoImporteSCResponse = altaContratoService.validarCambioTipoImporteSC(validarCambioTipoImporteSCRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11007 - Valida el cambio del campo pasa libreria de N a S
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String validarPasaLibreriaNaSSC() {
		try {
			validarPasaLibreriaNaSSCResponse = altaContratoService.validarPasaLibreriaNaSSC(validarPasaLibreriaNaSSCRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11007 - Valida el cambio de la cantidad de pasadas contratadas
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String validarCantidadPasadasContratadasSC() {
		try {
			validarCantidadPasadasContratadasSCResponse = altaContratoService.validarCantidadPasadasContratadasSC(validarCantidadPasadasContratadasSCRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11007 - Valida si debe ejecutarse rerun
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String validarRerunSC() {
		try {
			validarRerunSCResponse = altaContratoService.validarRerunSC(validarRerunSCRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11007 - Validacion de desenlace
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String validarDesenlaceSC() {
		try {
			validarDesenlaceSCResponse = altaContratoService.validarDesenlaceSC(validarDesenlaceSCRequest);
			generarReporteTitulosADesconfirmar(validarDesenlaceSCResponse.getIdReporte());
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11007 - Validaciones previas a la modificación de grupo 
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String validarModificacionGrupoSC() {
		try {
			validarModificacionGrupoSCResponse = altaContratoService.validarModificacionGrupoSC(validarModificacionGrupoSCRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11007 - Eliminar marca consultar
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String eliminarConsultarSC() {
		try {
			resultado = altaContratoService.eliminarConsultarSC(eliminarConsultarSCRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11007 - Modificación de grupo SC
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String modificacionGrupoSC() {
		try {
			modificacionGrupoSCResponse = altaContratoService.modificacionGrupoSC(modificacionGrupoSCRequest);
			generarReporteTitulosADesconfirmar(modificacionGrupoSCResponse.getIdReporte());
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11008 - Validar asignación de señal heredada
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String validarAsignacionSenialHeredadaSC() {
		try {
			validarAsignacionSenialHeredadaResponse = altaContratoService.validarAsignacionSenialHeredadaSC(validarAsignacionSenialHeredadaRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11008 - Asignación de señal heredada
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String asignarSenialHeredadaSC() {
		try {
			resultado = altaContratoService.asignarSenialHeredadaSC(asignarSenialHeredadaRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11008 - Desasignación de señal heredada
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String desasignarSenialHeredadaSC() {
		try {
			resultado = altaContratoService.desasignarSenialHeredadaSC(desasignarSenialHeredadaRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11009 - Busqueda de grupos que posee un contrato
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String buscarGruposContrato() {
		try {
			gruposContrato = altaContratoService.buscarGruposContrato(buscarGruposContratoRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
//	public String buscarTipoCosteoByGrupoContratoSenial() {
//		try {
//			tipoDeCosteo = altaContratoService.buscarTipoCosteoByGrupo(buscarTipoDeCosteoRequest);
//		} catch (DataBaseException e) {
//			message = e.getError().getMensaje();
//		} catch (Exception e) {
//			message = ERROR_INESPERADO;
//		}
//		return SUCCESS;
//	}
	
	/**
     * FP11009 - Validación de desenlace de grupo 
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String desenlaceContratoGrupo() {
		try {
			desenlaceContratoGrupoResponse = altaContratoService.desenlaceContratoGrupo(desenlaceContratoGrupoRequest);
			generarReporteTitulosADesconfirmar(desenlaceContratoGrupoResponse.getIdReporte());
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	/**
     * FP11009 - Recupero los datos del grupo seleccionado 
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String recuperarDatosGrupo() {
		try {
			recuperarDatosGrupoResponse = altaContratoService.recuperarDatosGrupo(recuperarDatosGrupoRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}	
	
	/**
     * FP11009 - Eliminar un grupo CC o SC del contrato 
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String eliminarGrupoContrato() {
		try {
			eliminarGrupoContratoResponse = altaContratoService.eliminarGrupoContrato(eliminarGrupoContratoRequest);
			generarReporteTitulosADesconfirmar(eliminarGrupoContratoResponse.getIdReporte());
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11010/11 - Búsqueda para selección de títulos de un grupo
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String buscarTitulosGrupo() {
		try {
			titulosGrupo = altaContratoService.buscarTitulosGrupo(buscarTitulosGrupoRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11010/11 - Valida la perpetuidad de un título seleccionado en pantalla
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String validarPerpetuidadTitulo() {
		try {
			resultado = altaContratoService.validarPerpetuidadTitulo(validarPerpetuidadTituloRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
    /**
	 * FP11012 - Retorna el próximo id disponible en la tabla de títulos
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String obtenerNuevaClaveTitulo() {
		try {
			nuevaClaveTitulo = altaContratoService.obtenerNuevaClaveTitulo(obtenerNuevaClaveTituloRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
    /**
	 * FP11012 - Búsqueda de calificaciones oficiales
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String buscarCalificacionesOficiales() {
		try {
			calificacionesOficiales = altaContratoService.buscarCalificacionesOficiales(clave);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
    /**
	 * FP11012 - Validación de existencia de título
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String validacionExistenciaTitulo() {
		try {
			validacionExistenciaTituloResponse = altaContratoService.validacionExistenciaTitulo(validacionExistenciaTituloRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
	 * FP11012 - Validación de recepción master
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String validacionRecepcionMaster() {
		try {
			validacionRecepcionMasterResponse = altaContratoService.validacionRecepcionMaster(validacionRecepcionMasterRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;	
	}
	
	/**
	 * FP11012 - Alta de título de un grupo	
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String confirmarSeleccionTitulo() {
		try {
			confirmarSeleccionTituloResponse = altaContratoService.confirmarSeleccionTitulo(confirmarSeleccionTituloRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;	
	}
	
	
	public String confirmarSeleccionTituloRecontratacion() {
		try {
			confirmarSeleccionTituloResponse = altaContratoService.confirmarSeleccionTituloRecontratacion(confirmarSeleccionTituloRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;	
	}
	
	/**
	 * FP11013 - Alta de título de un grupo	
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String buscarTitulosRecontratacion() {
		try {
			titulosRecontratacion = altaContratoService.buscarTitulosRecontratacion(buscarTitulosRecontratacionRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
	 * FP11013 - Validar remito	
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String validarRemitoSNC() {
		try {
			validarRemitoSNCResponse = altaContratoService.validarRemitoSNC(validarRemitoSNCRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
	 * FP11013 - Validación master de títulos	
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String validarMasterTitulo() {
		try {
			validarMasterTituloResponse = altaContratoService.validarMasterTitulo(validarMasterTituloRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
	 * FP11013 - Confirmación de selección de titulo de recontratación	
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String confirmarSeleccionCapitulosTituloRecontratacion() {
		try {
			confirmarSeleccionCapitulosResponse = altaContratoService.confirmarSeleccionCapitulosTituloRecontratacion(confirmarSeleccionCapitulosRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;		
	}
	
	/**
	 * FP11014 - Búsqueda de capítulos para el titulo a recontratación
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String buscarCapitulosTituloRecontratacion() {
		try {
			capitulosTituloRecontratacion = altaContratoService.buscarCapitulosTituloRecontratacion(buscarCapitulosTituloRecontratacionRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
	 * FP11014 - Validar remito	SNC
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String validarRemitoSNCCapituloRecontratacion() {
		try {
			validarRemitoSNCCapituloRecontratacionResponse = altaContratoService.validarRemitoSNCCapituloRecontratacion(validarRemitoSNCCapituloRecontratacionRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
	 * FP11014 - Validar remito	no SNC
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String validarRemitoNoSNC() {
		try {
			validarRemitoNoSNCResponse = altaContratoService.validarRemitoNoSNC(validarRemitoNoSNCRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
	 * FP11014 - Confirmación de selección de titulo de recontratación	
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String confirmarSeleccionCapituloTituloRecontratacion() {
		try {
			confirmarSeleccionCapitulosResponse = altaContratoService.confirmarSeleccionCapituloTituloRecontratacion(confirmarSeleccionCapitulosRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;		
	}
	
	/**
	 * FP11015 - Busqueda de contratos para grilla
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String contratosEnlazadosTituloRecontratacion() {
		try {
			contratosEnlazadosTituloRecontratacion = altaContratoService.contratosEnlazadosTituloRecontratacion(contratosEnlazadosTituloRecontratacionRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
	 * FP11015 - Validación de desenlace de título - contrato
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String validarDesenlaceTituloContrato() {
		try {
			validarDesenlaceTituloContratoResponse = altaContratoService.validarDesenlaceTituloContrato(validarDesenlaceTituloContratoRequest);
			generarReporteTitulosADesconfirmar(validarDesenlaceTituloContratoResponse.getIdReporte());
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
	 * FP11015 - Desenlace de título - contrato
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String ejecutarDesenlaceTituloContrato() {
		try {
			ejecutarDesenlaceTituloContratoResponse = altaContratoService.ejecutarDesenlaceTituloContrato(ejecutarDesenlaceTituloContratoRequest);
            generarReporteTitulosADesconfirmar(ejecutarDesenlaceTituloContratoResponse.getIdReporte());
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
	 * FP11015 - Validación de enlace posterior de título - contrato
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String validarEnlacePosterior() {
		try {
			validarEnlacePosteriorResponse = altaContratoService.validarEnlacePosterior(validarEnlacePosteriorRequest);
            generarReporteTitulosADesconfirmar(validarEnlacePosteriorResponse.getIdReporte());
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
	 * FP11015 - Enlace posterior de título - contrato
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String ejecutarEnlacePosterior() {
		try {
			ejecutarEnlacePosteriorResponse = altaContratoService.ejecutarEnlacePosterior(ejecutarEnlacePosteriorRequest);
            generarReporteTitulosADesconfirmar(ejecutarEnlacePosteriorResponse.getIdReporte());
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
	 * FP11016 - Visualización de todas las modificaciones de vigencia que sufrió un título en el contrato seleccionado en FP11015 
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String visualizarModificacionesVigencia() {
		try {
			modificacionesVigencia = altaContratoService.visualizarModificacionesVigencia(visualizarModificacionesVigenciaRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
	 * FP11018 - Búsqueda de capítulos a eliminar en un contrato 
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String buscarCapitulosEliminacion() {
		try {
			capitulosTituloRecontratacion = altaContratoService.buscarCapitulosEliminacion(buscarCapitulosEliminacionRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
	 * FP11018 - Validación que determina si la cantidad de capítulos a eliminar es correcta 
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String validarCantidadCapitulosBaja() {
		try {
			mensajeCantidadCapitulosBaja = altaContratoService.validarCantidadCapitulosBaja(validarCantidadCapitulosBajaRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
	 * FP11018 - Validación rerun nro. 1
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String primerValidacionRerunBajaCapitulos() {
		try {
			primerValidacionRerunBajaCapitulosResponse = altaContratoService.primerValidacionRerunBajaCapitulos(primerValidacionRerunBajaCapitulosRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
	 * FP11018 - Validación rerun nro. 2  
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String segundaValidacionRerunBajaCapitulos() {
		try {
			segundaValidacionRerunBajaCapitulosResponse = altaContratoService.segundaValidacionRerunBajaCapitulos(segundaValidacionRerunBajaCapitulosRequest);
			generarReporteTitulosADesconfirmar(segundaValidacionRerunBajaCapitulosResponse.getIdReporte());
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
	 * FP11018 - Confirmacíon de la eliminación de capítulos de un título  
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String confirmarBajaCapitulos() {
		try {
			confirmarBajaCapitulosResponse = altaContratoService.confirmarBajaCapitulos(confirmarBajaCapitulosRequest);
            generarReporteTitulosADesconfirmar(confirmarBajaCapitulosResponse.getIdReporte());
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
	 * FP11019 - Búsqueda de capítulos a agregar en un contrato 
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String buscarCapitulosAdicion() {
		try {
			capitulosTituloRecontratacion = altaContratoService.buscarCapitulosAdicion(buscarCapitulosAdicionRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
	 * FP11019 - Validación de remito para capítulos SNC
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String validarRemitoCapitulosSNC() {
		try {
			validarRemitoCapitulosSNCResponse = altaContratoService.validarRemitoCapitulosSNC(validarRemitoCapitulosSNCRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
	 * FP11019 - Confirmar alta de capitulos
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String confirmarAltaCapitulos() {
		try {
			confirmarAltaCapitulosResponse = altaContratoService.confirmarAltaCapitulos(confirmarAltaCapitulosRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
	 * FP11020 - Búsqueda de títulos a eliminar en un contrato
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String buscarTitulosEliminarContrato() {
		try {
			titulosEliminarContrato = altaContratoService.buscarTitulosEliminarContrato(buscarTitulosEliminarContratoRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	/**
	 * FP11020 - Validacion para eliminar titulo en un contrato
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String validarEliminacionTituloContrato() {
		try {
			validarEliminacionTituloContratoResponse = altaContratoService.validarEliminacionTituloContrato(validarEliminacionTituloContratoRequest);
			generarReporteTitulosADesconfirmar(validarEliminacionTituloContratoResponse.getIdReporte());
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
	 * FP11020 - Confirma la baja del titulo en un contrato
	 * @author cetorres
	 * 
	 * @useCase FP110 - Alta de contrato
	 */
	public String confirmarEliminacionTituloContrato() {
		try {
			confirmarEliminacionTituloContratoResponse = altaContratoService.confirmarEliminacionTituloContrato(confirmarEliminacionTituloContratoRequest);
			generarReporteTitulosADesconfirmar(confirmarEliminacionTituloContratoResponse.getIdReporte());
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	/**
     * FP11021 - Búsqueda de títulos pertenecientes a un grupo
     * @author cetorres
     * 
     * @useCase FP110 - Alta de contrato
     */
	public String buscarTitulosGrupoReadOnly() {
		try {
			titulosGrupo = altaContratoService.buscarTitulosGrupoReadOnly(buscarTitulosGrupoRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
    private void generarReporteTitulosADesconfirmar(Integer idReporte) throws SQLException, JRException, Exception {
        if (idReporte != null) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("p_id_reporte", idReporte);
            ExtUserDetails extUserDetails = (ExtUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            params.put("usuario", extUserDetails.getUsername());
            String pathReporte = new String("reportes/modificacionContrato/reporteTitulosADesconfirmar.jasper");
            String nombreReporte = "reporteTitulosADesconfirmar_ID" + idReporte;
            generarReportesService.generarReporte(params, pathReporte, nombreReporte);
        }
    }
}