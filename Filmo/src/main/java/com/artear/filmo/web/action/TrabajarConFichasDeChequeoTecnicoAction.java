package com.artear.filmo.web.action;

import static com.artear.filmo.constants.ApplicationConstants.ERROR_INESPERADO;
import static com.artear.filmo.constants.TipoBusquedaTitulo.CASTELLANO;
import static com.artear.filmo.constants.TipoBusquedaTitulo.ORIGINAL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

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
import com.artear.filmo.bo.chequeo.tecnico.SenialOkFilm;
import com.artear.filmo.bo.chequeo.tecnico.TitulosConContratosExhibidosRequest;
import com.artear.filmo.bo.chequeo.tecnico.TitulosConContratosExhibidosResponse;
import com.artear.filmo.bo.chequeo.tecnico.VerificarDesenlaceRequest;
import com.artear.filmo.bo.chequeo.tecnico.VerificarDesenlaceResponse;
import com.artear.filmo.bo.chequeo.tecnico.VigenciaContrato;
import com.artear.filmo.bo.chequeo.tecnico.VisualizarFichaRequest;
import com.artear.filmo.bo.common.AyudaSituar;
import com.artear.filmo.bo.common.ErrorResponse;
import com.artear.filmo.bo.common.Senial;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.seguridad.userdetails.ExtUserDetails;
import com.artear.filmo.services.interfaces.BasicService;
import com.artear.filmo.services.interfaces.GenerarReportesService;
import com.artear.filmo.services.interfaces.TrabajarConFichasDeChequeoTecnicoService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class TrabajarConFichasDeChequeoTecnicoAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	// OUT
	private String message;
	private String mensaje;
	private List<BusquedaFichasResponse> busquedaFichasResponse;
	private FichaCompleta visualizarFichaResponse;
	private List<ProgramaSituarResponse> programas;
	private List<Senial> seniales;
	private Chequeo chequeo;
	private List<VigenciaContrato> vigenciaContratos;
	private String resultadoValidacion;
	private Boolean resultadoValidacionSegmentos;
	private Ficha fichaRequest;
	private List<SenialOkFilm> senialesOkFilm;
	private List<AyudaSituar> resultadoSituar;
	private TitulosConContratosExhibidosResponse resultadoPopup;
	private VerificarDesenlaceResponse verificarDesenlaceResponse;
	private List<Senial> resultadoSeniales;
	private List<Rollo> rollos;
	private List<AsociarAContrato> contratosParaAsociar;
    private List<ErrorResponse> errores;

	// IN
	private BusquedaFichasRequest busquedaFichasRequest;
	private VisualizarFichaRequest visualizarFichasRequest;
	private AltaModifFichaRequest altaModifFichaRequest;
	private String titulo;
	private String descripcion;
	private String clave;
	private Integer nroFicha;
	private String senial;
	private String film;
	private String capitulo;
	private String parte;
	private GuardarSegmentoRequest segmentosRequest;
	private GuardarActoresSinopsisObservRequest actoresSinopsisObservRequest;
	private GuardarSenialesOkFilmRequest senialesFilmRequest;
	private TitulosConContratosExhibidosRequest contratosExhibidosRequest;
	private AltaModifRolloRequest altaModifRollosRequest;
	@Autowired
	private GenerarReportesService generarReportesService;
	private VerificarDesenlaceRequest verificarDesenlaceRequest;
	private VerificarDesenlaceResponse validarPayTV;

	@Autowired
	private TrabajarConFichasDeChequeoTecnicoService trabajarConFichasDeChequeoTecnicoService;
	@Autowired
	private BasicService basicService;

	public String generarReporteFichaTecnica() throws Exception {
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("p_fichaChequeo", Integer.toString(nroFicha));
			ExtUserDetails extUserDetails = (ExtUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			params.put("usuario", extUserDetails.getUsername());
			params.put("idReporte", "RFP23017");
			String pathPropuesta = System.getProperty("Filmo")+"reportes/trabajarConFichasDeChequeoTecnico/";
			params.put("SUBREPORT_DIR", pathPropuesta);
			String pathReporte = new String("reportes/trabajarConFichasDeChequeoTecnico/fichaChequeoTecnico.jasper");
			String nombreReporte = "fichaChequeoTecnico";
			generarReportesService.generarReporte(params, pathReporte, nombreReporte); 
		}catch (Exception e){
			message = "Error Generacion de Reporte";
		}
		return SUCCESS;
	}
	
	public String buscarFichas() throws Exception {
		try {
			busquedaFichasResponse = trabajarConFichasDeChequeoTecnicoService
					.buscarFichas(busquedaFichasRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String visualizarFicha() throws Exception {
		try {
			visualizarFichaResponse = trabajarConFichasDeChequeoTecnicoService
					.visualizarFicha(visualizarFichasRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String buscarProgramasPorTituloOriginal() throws Exception {
		try {
			programas = trabajarConFichasDeChequeoTecnicoService
					.buscarProgramasPorTitulo(titulo, ORIGINAL);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String buscarProgramasPorTituloCastellano() throws Exception {
		try {
			programas = trabajarConFichasDeChequeoTecnicoService
					.buscarProgramasPorTitulo(titulo, CASTELLANO);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String buscarSeniales() throws Exception {
		try {
			seniales = basicService.getSeniales();
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String obtenerInfoChequeoPrograma() throws Exception {
		try {
			String cap = capitulo;
			if (cap == null || "".equals(cap)){
				cap = "0";
			}
			chequeo = trabajarConFichasDeChequeoTecnicoService
					.obtenerInfoChequeoPorPrograma(clave,cap);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String altaCabeceraFicha() throws Exception {
		try {
			trabajarConFichasDeChequeoTecnicoService
					.altaFicha(altaModifFichaRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String modificarCabeceraFicha() throws Exception {
		try {
			trabajarConFichasDeChequeoTecnicoService
					.modificarFicha(altaModifFichaRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String eliminarFicha() throws Exception {
		try {
			this.setErrores(trabajarConFichasDeChequeoTecnicoService.eliminarFicha(nroFicha, clave));
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String inactivarFicha() throws Exception {
		try {
			trabajarConFichasDeChequeoTecnicoService.inactivarFicha(nroFicha,
					clave);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String infoVigenciaContratos() throws Exception {
		try {
			vigenciaContratos = trabajarConFichasDeChequeoTecnicoService
					.infoVigenciaContratos(clave, nroFicha);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String validarTituloEnCanal() throws Exception {
		try {
			resultadoValidacion = trabajarConFichasDeChequeoTecnicoService
					.validarTituloEnCanal(clave, senial, capitulo, parte);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String buscarGeneros() throws Exception {
		try {
			resultadoSituar = trabajarConFichasDeChequeoTecnicoService
					.buscarGeneros(descripcion);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String buscarCalifArtistica() throws Exception {
		try {
			resultadoSituar = trabajarConFichasDeChequeoTecnicoService
					.buscarCalifArtisticas(descripcion);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String buscarCalifAutocontrol() throws Exception {
		try {
			resultadoSituar = trabajarConFichasDeChequeoTecnicoService
					.buscarCalifAutocontrol(descripcion);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String buscarCalifOficial() throws Exception {
		try {
			resultadoSituar = trabajarConFichasDeChequeoTecnicoService
					.buscarCalifOficial(descripcion);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String validarSegmentos() throws Exception {
		try {
			resultadoValidacionSegmentos = trabajarConFichasDeChequeoTecnicoService
					.validarSegmentos(segmentosRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String eliminarSegmentos() throws Exception {
		try {
			trabajarConFichasDeChequeoTecnicoService
					.eliminarSegmentos(nroFicha);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String guardarSegmentos() throws Exception {
		try {
			trabajarConFichasDeChequeoTecnicoService
					.guardarSegmentos(segmentosRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String guardarActoresSinopsisYObservaciones() throws Exception {
		try {
			trabajarConFichasDeChequeoTecnicoService
					.guardarActoresSinopsisYObservaciones(actoresSinopsisObservRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String validarChequeoDetalle() throws Exception {
		try {
			trabajarConFichasDeChequeoTecnicoService
					.validarChequeoDetalle(fichaRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String guardarChequeoDetalle() throws Exception {
		try {
			if (fichaRequest.getTituloOff() == null || "".equals(fichaRequest.getTituloOff()))
				fichaRequest.setTituloOff(" ");
			trabajarConFichasDeChequeoTecnicoService
					.guardarChequeoDetalle(fichaRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String buscarSenialesOkFilm() throws Exception {
		try {
			senialesOkFilm = trabajarConFichasDeChequeoTecnicoService
					.buscarSenialesOkFiml(nroFicha, film);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String guardarSenialesOkFilm() throws Exception {
		try {
			trabajarConFichasDeChequeoTecnicoService
					.guardarSenialesOkFilm(senialesFilmRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String popupTitulosConContratosExhibidos() throws Exception {
		try {
			resultadoPopup = trabajarConFichasDeChequeoTecnicoService
					.popupTitulosConContratosExhibidos(nroFicha, film);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	public String popupTitulosConContratosExhibidosSN() throws Exception {
		try {
			resultadoPopup = trabajarConFichasDeChequeoTecnicoService
					.popupTitulosConContratosExhibidosSN(nroFicha, film);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	public String popupVerificarDesenlace() throws Exception {
		try {
			verificarDesenlaceResponse = trabajarConFichasDeChequeoTecnicoService
					.popupVerificarDesenlace(verificarDesenlaceRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	public String validarPayTV() throws Exception {
		try {
			validarPayTV = trabajarConFichasDeChequeoTecnicoService
					.validarPayTV(nroFicha, clave);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	public String ejecutarDesenlace() throws Exception {
		try {
			mensaje = trabajarConFichasDeChequeoTecnicoService
					.ejecutarDesenlace(verificarDesenlaceRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	public String actualizarPY6001() throws Exception {
		try {
			trabajarConFichasDeChequeoTecnicoService
					.actualizarPY6001(nroFicha);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	public String popupTitulosConContratosExhibidosRechazo() throws Exception {
		try {
			mensaje = trabajarConFichasDeChequeoTecnicoService
					.popupTitulosConContratosExhibidosRechazo(nroFicha);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String buscarSoportes() throws Exception {
		try {
			resultadoSituar = trabajarConFichasDeChequeoTecnicoService
					.buscarSoportes(descripcion);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String buscarSenialesAltaCopia() throws Exception {
		try {
			resultadoSeniales = trabajarConFichasDeChequeoTecnicoService.buscarSeniales(nroFicha, descripcion);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String buscarRollos() throws Exception {
		try {
			rollos = trabajarConFichasDeChequeoTecnicoService.buscarRollos(
					senial, nroFicha);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String altaDeRollos() throws Exception {
		try {
			trabajarConFichasDeChequeoTecnicoService
					.altaDeRollos(altaModifRollosRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String altaOModificacionDeRollos() throws Exception {
		try {
			trabajarConFichasDeChequeoTecnicoService
					.altaOModificacionDeRollos(altaModifRollosRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String buscarContratosParaAsociarLaCopia() throws Exception {
		try {
			contratosParaAsociar = trabajarConFichasDeChequeoTecnicoService
					.buscarContratosParaAsociarLaCopia(senial, clave, nroFicha);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String registrarMaster() throws Exception {
		try {
			trabajarConFichasDeChequeoTecnicoService.registrarMaster(senial,
					clave, nroFicha);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

    public List<ErrorResponse> getErrores() {
        return errores;
    }

    public void setErrores(List<ErrorResponse> errores) {
        this.errores = errores;
    }
    
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<BusquedaFichasResponse> getBusquedaFichasResponse() {
		return busquedaFichasResponse;
	}

	public void setBusquedaFichasResponse(
			List<BusquedaFichasResponse> busquedaFichasResponse) {
		this.busquedaFichasResponse = busquedaFichasResponse;
	}

	public BusquedaFichasRequest getBusquedaFichasRequest() {
		return busquedaFichasRequest;
	}

	public void setBusquedaFichasRequest(
			BusquedaFichasRequest busquedaFichasRequest) {
		this.busquedaFichasRequest = busquedaFichasRequest;
	}

	public FichaCompleta getVisualizarFichaResponse() {
		return visualizarFichaResponse;
	}

	public void setVisualizarFichaResponse(FichaCompleta visualizarFichaResponse) {
		this.visualizarFichaResponse = visualizarFichaResponse;
	}

	public VisualizarFichaRequest getVisualizarFichasRequest() {
		return visualizarFichasRequest;
	}

	public void setVisualizarFichasRequest(
			VisualizarFichaRequest visualizarFichasRequest) {
		this.visualizarFichasRequest = visualizarFichasRequest;
	}

	public List<ProgramaSituarResponse> getProgramas() {
		return programas;
	}

	public void setProgramas(List<ProgramaSituarResponse> programas) {
		this.programas = programas;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<Senial> getSeniales() {
		return seniales;
	}

	public void setSeniales(List<Senial> seniales) {
		this.seniales = seniales;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Chequeo getChequeo() {
		return chequeo;
	}

	public void setChequeo(Chequeo chequeo) {
		this.chequeo = chequeo;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Integer getNroFicha() {
		return nroFicha;
	}

	public void setNroFicha(Integer nroFicha) {
		this.nroFicha = nroFicha;
	}

	public AltaModifFichaRequest getAltaModifFichaRequest() {
		return altaModifFichaRequest;
	}

	public void setAltaModifFichaRequest(
			AltaModifFichaRequest altaModifFichaRequest) {
		this.altaModifFichaRequest = altaModifFichaRequest;
	}

	public List<VigenciaContrato> getVigenciaContratos() {
		return vigenciaContratos;
	}

	public void setVigenciaContratos(List<VigenciaContrato> vigenciaContratos) {
		this.vigenciaContratos = vigenciaContratos;
	}

	public String getResultadoValidacion() {
		return resultadoValidacion;
	}

	public void setResultadoValidacion(String resultadoValidacion) {
		this.resultadoValidacion = resultadoValidacion;
	}

	public String getSenial() {
		return senial;
	}

	public void setSenial(String senial) {
		this.senial = senial;
	}

	public List<AyudaSituar> getResultadoSituar() {
		return resultadoSituar;
	}

	public void setResultadoSituar(List<AyudaSituar> resultadoSituar) {
		this.resultadoSituar = resultadoSituar;
	}

	public Boolean getResultadoValidacionSegmentos() {
		return resultadoValidacionSegmentos;
	}

	public void setResultadoValidacionSegmentos(
			Boolean resultadoValidacionSegmentos) {
		this.resultadoValidacionSegmentos = resultadoValidacionSegmentos;
	}

	public GuardarSegmentoRequest getSegmentosRequest() {
		return segmentosRequest;
	}

	public void setSegmentosRequest(GuardarSegmentoRequest segmentosRequest) {
		this.segmentosRequest = segmentosRequest;
	}

	public GuardarActoresSinopsisObservRequest getActoresSinopsisObservRequest() {
		return actoresSinopsisObservRequest;
	}

	public void setActoresSinopsisObservRequest(
			GuardarActoresSinopsisObservRequest actoresSinopsisObservRequest) {
		this.actoresSinopsisObservRequest = actoresSinopsisObservRequest;
	}

	public Ficha getFichaRequest() {
		return fichaRequest;
	}

	public void setFichaRequest(Ficha fichaRequest) {
		this.fichaRequest = fichaRequest;
	}

	public List<SenialOkFilm> getSenialesOkFilm() {
		return senialesOkFilm;
	}

	public void setSenialesOkFilm(List<SenialOkFilm> senialesOkFilm) {
		this.senialesOkFilm = senialesOkFilm;
	}

	public GuardarSenialesOkFilmRequest getSenialesFilmRequest() {
		return senialesFilmRequest;
	}

	public void setSenialesFilmRequest(
			GuardarSenialesOkFilmRequest senialesFilmRequest) {
		this.senialesFilmRequest = senialesFilmRequest;
	}

	public String getFilm() {
		return film;
	}

	public void setFilm(String film) {
		this.film = film;
	}

	public TitulosConContratosExhibidosResponse getResultadoPopup() {
		return resultadoPopup;
	}

	public void setResultadoPopup(
			TitulosConContratosExhibidosResponse resultadoPopup) {
		this.resultadoPopup = resultadoPopup;
	}

	public TitulosConContratosExhibidosRequest getContratosExhibidosRequest() {
		return contratosExhibidosRequest;
	}

	public void setContratosExhibidosRequest(
			TitulosConContratosExhibidosRequest contratosExhibidosRequest) {
		this.contratosExhibidosRequest = contratosExhibidosRequest;
	}

	public List<Senial> getResultadoSeniales() {
		return resultadoSeniales;
	}

	public void setResultadoSeniales(List<Senial> resultadoSeniales) {
		this.resultadoSeniales = resultadoSeniales;
	}

	public List<Rollo> getRollos() {
		return rollos;
	}

	public void setRollos(List<Rollo> rollos) {
		this.rollos = rollos;
	}

	public AltaModifRolloRequest getAltaModifRollosRequest() {
		return altaModifRollosRequest;
	}

	public void setAltaModifRollosRequest(
			AltaModifRolloRequest altaModifRollosRequest) {
		this.altaModifRollosRequest = altaModifRollosRequest;
	}

	public List<AsociarAContrato> getContratosParaAsociar() {
		return contratosParaAsociar;
	}

	public void setContratosParaAsociar(
			List<AsociarAContrato> contratosParaAsociar) {
		this.contratosParaAsociar = contratosParaAsociar;
	}

	public String getCapitulo() {
		return capitulo;
	}

	public void setCapitulo(String capitulo) {
		this.capitulo = capitulo;
	}

	public String getParte() {
		return parte;
	}

	public void setParte(String parte) {
		this.parte = parte;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public VerificarDesenlaceRequest getVerificarDesenlaceRequest() {
		return verificarDesenlaceRequest;
	}

	public void setVerificarDesenlaceRequest(
			VerificarDesenlaceRequest verificarDesenlaceRequest) {
		this.verificarDesenlaceRequest = verificarDesenlaceRequest;
	}

	public VerificarDesenlaceResponse getVerificarDesenlaceResponse() {
		return verificarDesenlaceResponse;
	}

	public void setVerificarDesenlaceResponse(
			VerificarDesenlaceResponse verificarDesenlaceResponse) {
		this.verificarDesenlaceResponse = verificarDesenlaceResponse;
	}

	public VerificarDesenlaceResponse getValidarPayTV() {
		return validarPayTV;
	}

	public void setValidarPayTV(VerificarDesenlaceResponse validarPayTV) {
		this.validarPayTV = validarPayTV;
	}
}
