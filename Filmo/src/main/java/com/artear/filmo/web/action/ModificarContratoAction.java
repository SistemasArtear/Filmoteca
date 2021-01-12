package com.artear.filmo.web.action;

import static com.artear.filmo.constants.ApplicationConstants.ERROR_INESPERADO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.artear.filmo.bo.common.Distribuidor;
import com.artear.filmo.bo.common.Moneda;
import com.artear.filmo.bo.common.Senial;
import com.artear.filmo.bo.contratos.ActualizarTGRequest;
import com.artear.filmo.bo.contratos.Capitulo;
import com.artear.filmo.bo.contratos.Contrato;
import com.artear.filmo.bo.contratos.ContratoConReRun;
import com.artear.filmo.bo.contratos.ContratoValidationRequest;
import com.artear.filmo.bo.contratos.ContratoValidationResult;
import com.artear.filmo.bo.contratos.DameTGRequest;
import com.artear.filmo.bo.contratos.DameTGResponse;
import com.artear.filmo.bo.contratos.Grupo;
import com.artear.filmo.bo.contratos.GrupoRequest;
import com.artear.filmo.bo.contratos.GruposConReemplazoRequest;
import com.artear.filmo.bo.contratos.ObservacionDePago;
import com.artear.filmo.bo.contratos.RerunRequest;
import com.artear.filmo.bo.contratos.SenialImporte;
import com.artear.filmo.bo.contratos.TipoDerecho;
import com.artear.filmo.bo.contratos.TipoDerechoTerritorial;
import com.artear.filmo.bo.contratos.TipoImporte;
import com.artear.filmo.bo.contratos.TipoTitulo;
import com.artear.filmo.bo.contratos.Titulo;
import com.artear.filmo.bo.contratos.TituloConGrupo;
import com.artear.filmo.bo.contratos.TituloContratado;
import com.artear.filmo.bo.contratos.TituloRequest;
import com.artear.filmo.bo.contratos.TitulosRecontratadosRequest;
import com.artear.filmo.bo.contratos.TitulosRequest;
import com.artear.filmo.bo.contratos.ValidarAltaTituloRequest;
import com.artear.filmo.bo.contratos.ValidarTituloPerpetuoRequest;
import com.artear.filmo.bo.contratos.Vigencia;
import com.artear.filmo.bo.contratos.VigenciaRequest;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.seguridad.services.ServiciosSesionUsuario;
import com.artear.filmo.seguridad.userdetails.ExtUserDetails;
import com.artear.filmo.services.interfaces.BloqueoContratoService;
import com.artear.filmo.services.interfaces.GenerarReportesService;
import com.artear.filmo.services.interfaces.ModificarContratoABMService;
import com.artear.filmo.services.interfaces.ModificarContratoService;
import com.artear.filmo.services.interfaces.ModificarContratoValidatorService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class ModificarContratoAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    // OUT
    private String message;
    private String claveTitulo;
    private List<Contrato> contratos;
    private List<Distribuidor> distribuidores;
    private List<ObservacionDePago> observacionesDePago;
    private List<Moneda> monedas;
    private List<SenialImporte> senialesConImportes;
    private List<Senial> senialesAsignadas;
    private List<Senial> senialesHeredadas;
    private List<Grupo> grupos;
    private Grupo grupo;
    private List<TipoDerechoTerritorial> tiposDeDerechoTerritorial;
    private List<TipoDerecho> tiposDeDerecho;
    private List<TipoImporte> tiposDeImporte;
    private List<TipoTitulo> tiposDeTitulo;
    private List<ContratoValidationResult> validacionModificacionContrato;
    private List<Titulo> titulos;
    private List<TituloContratado> titulosContratados;
    private Titulo titulo;
    private List<Capitulo> capitulos;
    private List<ContratoConReRun> contratosConReRun;
    private List<Vigencia> vigencias;
    private Vigencia vigencia;
    private Boolean contratoUpdated;
    private Boolean contratoConSeniales;
    private Integer nroGrupoResponse;
    private Boolean rerunResponse;
    private TituloConGrupo tituloConGrupo;
    private boolean estaBloqueado;

    // IN
    private Boolean skipWarnings = false;
    private Integer claveContrato;
    private Integer claveDistribuidor;
    private Integer claveGrupo;
    private String nombreDistribuidor;
    private String senial;
    private SenialImporte senialImporteRequest;
    private ContratoValidationRequest contratoValidationRequest;
    private TitulosRequest titulosRequest;
    private TituloRequest tituloRequest;
    private TitulosRecontratadosRequest titulosRecontratadosRequest;
    private GrupoRequest grupoRequest;
    private ValidarTituloPerpetuoRequest validarTituloPerpetuoRequest;
    private ValidarAltaTituloRequest validarAltaTituloRequest;
    private RerunRequest rerunRequest;
    private VigenciaRequest vigenciaRequest;
    private TituloConGrupo tituloConGrupoRequest;
    private GruposConReemplazoRequest gruposConReemplazoRequest; 
    private DameTGRequest dameTGRequest;
    private ActualizarTGRequest actualizarTGRequest;
    private List<DameTGResponse> dameTGResponse;

    @SuppressWarnings("rawtypes")
    private Map data = new HashMap();
    
    @Autowired
    private ModificarContratoService modificarContratoService;

    @Autowired
    private ModificarContratoValidatorService modificarContratoValidatorService;

    @Autowired
    private ModificarContratoABMService modificarContratoABMService;

    @Autowired
    private BloqueoContratoService bloqueoContratoService;
    
    @Autowired
    private ServiciosSesionUsuario serviciosSesionUsuario;

    @Autowired
    private GenerarReportesService generarReportesService;

    public String dameContrato() throws Exception {
        try {
            if (claveContrato != null) {
                contratos = modificarContratoService.dameContrato(claveContrato);
            } else if (claveDistribuidor != null) {
                contratos = modificarContratoService.dameContratosPorDistribuidor(claveDistribuidor);
            }
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String dameDistribuidoresPorNombre() throws Exception {
        try {
            distribuidores = modificarContratoService.dameDistribuidoresPorNombre(nombreDistribuidor);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String dameContratoConCabecera() throws Exception {
        try {
            contratos = modificarContratoService.dameContratoConCabecera(claveContrato, claveDistribuidor);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String dameObservacionesDePago() throws Exception {
        try {
            observacionesDePago = modificarContratoService.dameObservacionesDePago(claveContrato);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String dameMonedas() throws Exception {
        try {
            monedas = modificarContratoService.dameMonedas();
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String dameSenialImporte() throws Exception {
        try {
            setSenialesConImportes(modificarContratoService.dameSenialImporte(claveContrato));
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String dameGrupo() throws Exception {
        try {
            grupo = modificarContratoService.dameGrupo(claveContrato, senial, claveGrupo);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String dameGrupos() throws Exception {
        try {
            setGrupos(modificarContratoService.dameGrupos(claveContrato, senial));
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String dameSenialesHeredadasAsignadas() throws Exception {
        try {
            senialesAsignadas = modificarContratoService.dameSenialesHeredadasAsignadas(claveContrato);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String dameTiposDeImporte() throws Exception {
        try {
            tiposDeImporte = modificarContratoService.dameTiposDeImporte();
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String dameNombreDelTitulo() throws Exception {
        try {
            message = modificarContratoService.dameNombreDelTitulo(claveTitulo);
        } catch (DataBaseException e) {
            message = StringUtils.EMPTY;
        } catch (Exception e) {
            message = StringUtils.EMPTY;
        }
        return SUCCESS;
    }
    
    public String dameTiposDeDerecho() throws Exception {
        try {
            tiposDeDerecho = modificarContratoService.dameTiposDeDerecho();
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String dameTiposDeDerechoTerritorial() throws Exception {
        try {
            tiposDeDerechoTerritorial = modificarContratoService.dameTiposDeDerechoTerritorial();
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String dameTiposDeTitulo() throws Exception {
        try {
            tiposDeTitulo = modificarContratoService.dameTiposDeTitulo();
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String dameTitulos() throws Exception {
        try {
            if (!titulosRequest.getTituloABuscar().isEmpty()) {
                titulos = modificarContratoService.dameTitulos(titulosRequest);
            } else {
                titulos = new ArrayList<Titulo>();
            }
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String dameTitulosPorContrato() throws Exception {
        try {
            titulosContratados = modificarContratoService.dameTitulosPorContrato(titulosRequest);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String dameTitulosAReemplazarPorContrato() throws Exception {
        try {
            titulosContratados = modificarContratoService.dameTitulosAReemplazarPorContrato(titulosRequest);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String dameTitulosAEliminarPorContrato() throws Exception {
        try {
            titulosContratados = modificarContratoService.dameTitulosAEliminarPorContrato(titulosRequest);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String dameTitulosARecontratar() throws Exception {
        try {
            titulosContratados = modificarContratoService.dameTitulosARecontratar(titulosRecontratadosRequest);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String dameCapitulosARecontratar() throws Exception {
        try {
            capitulos = modificarContratoService.dameCapitulosARecontratar(titulosRecontratadosRequest);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String dameContratosConReRun() throws Exception {
        try {
            contratosConReRun = modificarContratoService.dameContratosConReRun(titulosRecontratadosRequest);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String dameCapitulosPorTituloContratado() throws Exception {
        try {
            capitulos = modificarContratoService.dameCapitulosPorTituloContratado(titulosRecontratadosRequest);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    public String dameTitulo() throws Exception {
        try {
            titulo = modificarContratoService.dameTitulo(tituloRequest);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String dameVigenciasPorTituloContratado() throws Exception {
        try {
            vigencias = modificarContratoService.dameVigenciasPorTituloContratado(titulosRecontratadosRequest);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    public String dameSenialesHeredadas() throws Exception {
        try {
            senialesHeredadas = modificarContratoService.dameSenialesHeredadas(senial);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String dameSiguienteNumeroGrupo() throws Exception {
        try {
            nroGrupoResponse = modificarContratoService.dameSiguienteNumeroGrupo(claveContrato, senial);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String validarMoficacionDeCabecera() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoValidatorService.validarModificacionDeCabecera(contratoValidationRequest);
        } catch (DataBaseException e) {
            message = e .getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String validarContratoConSeniales() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoValidatorService.validarContratoConSeniales(contratoValidationRequest);
        } catch (DataBaseException e) {
            message = e .getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String validarCabeceraSeniales() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoValidatorService.validarCabeceraSeniales(contratoValidationRequest);
            
            generarReporteTitulosADesconfirmar();
            
        } catch (DataBaseException e) {
            message = e .getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String validarCabeceraSenial() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoValidatorService.validarCabeceraSenial(contratoValidationRequest);
        } catch (DataBaseException e) {
            message = e .getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    public String actualizarImporteSenialAutomaticamente() throws Exception {
        try {
            modificarContratoABMService.actualizarImporteSenialAutomaticamente(contratoValidationRequest);
        } catch (DataBaseException e) {
            message = e .getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    public String validarMontos() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoValidatorService.validarMontos(claveContrato);
        } catch (DataBaseException e) {
            message = e .getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String validarAltaGrupoSinCap() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoValidatorService.validarAltaGrupoSinCap(grupoRequest);
        } catch (DataBaseException e) {
            message = e .getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String validarAltaGrupoConCap() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoValidatorService.validarAltaGrupoConCap(grupoRequest);
        } catch (DataBaseException e) {
            message = e .getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String validarAltaTitulo() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoValidatorService.validarAltaTitulo(validarAltaTituloRequest, serviciosSesionUsuario.getUsuario());
        } catch (DataBaseException e) {
            message = e .getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String altaSenial() throws Exception {
        try {
            senialImporteRequest.setUsuario(serviciosSesionUsuario.getUsuario());
            ContratoValidationResult result = modificarContratoABMService.altaSenial(senialImporteRequest);
            if (result != null) {
                validacionModificacionContrato = new ArrayList<ContratoValidationResult>();
                validacionModificacionContrato.add(result);
            }
        } catch (DataBaseException e) {
            message = e .getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String modificarSenial() throws Exception {
        try {
            senialImporteRequest.setUsuario(serviciosSesionUsuario.getUsuario());
            modificarContratoABMService.modificarSenial(senialImporteRequest);
        } catch (DataBaseException e) {
            message = e .getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String eliminarSenial() throws Exception {
        try {
            senialImporteRequest.setUsuario(serviciosSesionUsuario.getUsuario());
            validacionModificacionContrato = modificarContratoABMService.eliminarSenial(senialImporteRequest);
            
            generarReporteTitulosADesconfirmar();
            
        } catch (DataBaseException e) {
            message = e .getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String altaGrupoSinCap() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoABMService.altaGrupoSinCap(grupoRequest, serviciosSesionUsuario.getUsuario());
        } catch (DataBaseException e) {
            message = e .getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String altaGrupoConCap() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoABMService.altaGrupoConCap(grupoRequest, serviciosSesionUsuario.getUsuario());
        } catch (DataBaseException e) {
            message = e .getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String actualizarContrato() throws Exception {
        try {
            contratoValidationRequest.setUsuario(serviciosSesionUsuario.getUsuario());
            contratoUpdated = modificarContratoABMService.actualizarContrato(contratoValidationRequest);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String updateContratoMontoSenial() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoABMService.updateContratoMontoSenial(senialImporteRequest);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String altaTituloContratado() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoABMService.altaTituloContratado(validarAltaTituloRequest, serviciosSesionUsuario.getUsuario());
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String altaTituloContratadoCC() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoABMService.altaTituloContratadoCC(data, serviciosSesionUsuario.getUsuario());
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    public String altaTituloContratadoCCTodos() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoABMService.altaTituloContratadoCCTodos(data, serviciosSesionUsuario.getUsuario());
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    public String validarEnlaceAnterior() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoValidatorService.validarEnlaceAnterior(rerunRequest, serviciosSesionUsuario.getUsuario());
            
            generarReporteTitulosADesconfirmar();
            
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String actualizarEnlaceAnterior() throws Exception {
        try {
            rerunResponse = modificarContratoABMService.actualizarEnlaceAnterior(rerunRequest, serviciosSesionUsuario.getUsuario());
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String validarEnlacePosterior() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoValidatorService.validarEnlacePosterior(rerunRequest, serviciosSesionUsuario.getUsuario());
            
            generarReporteTitulosADesconfirmar();
            
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String actualizarEnlacePosterior() throws Exception {
        try {
            rerunResponse = modificarContratoABMService.actualizarEnlacePosterior(rerunRequest, serviciosSesionUsuario.getUsuario());
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String validarRerunDesenlace() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoValidatorService.validarRerunDesenlace(rerunRequest, serviciosSesionUsuario.getUsuario());
            
            generarReporteTitulosADesconfirmar();
            
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String actualizarRerunDesenlace() throws Exception {
        try {
            rerunResponse = modificarContratoABMService.actualizarRerunDesenlace(rerunRequest, serviciosSesionUsuario.getUsuario());
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String validarModificaGrupoSinCap() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoValidatorService.validarModificaGrupoSinCap(grupoRequest, serviciosSesionUsuario.getUsuario());
            
            generarReporteTitulosADesconfirmar();
            
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    public String validarModificaGrupoConCap() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoValidatorService.validarModificaGrupoConCap(grupoRequest, serviciosSesionUsuario.getUsuario());
            
            generarReporteTitulosADesconfirmar();
            
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String modificacionDeGrupoConCap() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoABMService.modificacionDeGrupoConCap(grupoRequest, serviciosSesionUsuario.getUsuario());
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String modificacionDeGrupoSinCap() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoABMService.modificacionDeGrupoSinCap(grupoRequest, serviciosSesionUsuario.getUsuario());
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    public String validarModificaCantidadGrupoConCap() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoValidatorService.validarModificaCantidadGrupoConCap(grupoRequest, serviciosSesionUsuario.getUsuario());
            
            generarReporteTitulosADesconfirmar();
            
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    private void generarReporteTitulosADesconfirmar() throws SQLException, JRException, Exception {
        Integer idReporte = buscarIDReporte(validacionModificacionContrato);
        if (validacionModificacionContrato.size() > 0 && idReporte != null) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("p_id_reporte", validacionModificacionContrato.get(0).getIdReporte());
            ExtUserDetails extUserDetails = (ExtUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            params.put("usuario", extUserDetails.getUsername());
            String pathReporte = new String("reportes/modificacionContrato/reporteTitulosADesconfirmar.jasper");
            String nombreReporte = "reporteTitulosADesconfirmar_ID" + validacionModificacionContrato.get(0).getIdReporte();
            generarReportesService.generarReporte(params, pathReporte, nombreReporte);
        }
    }

    private Integer buscarIDReporte(List<ContratoValidationResult> validacionModificacionContrato) {
        Integer ret = null;
        for (ContratoValidationResult contratoValidationResult : validacionModificacionContrato) {
            if (contratoValidationResult.getIdReporte() != null) {
                ret = contratoValidationResult.getIdReporte();
                break;
            }
        }
        return ret;
    }

    public String validarDesmarcarTitulo() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoValidatorService.validarDescarmarTitulo(tituloRequest, serviciosSesionUsuario.getUsuario());
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    public String desmarcarTitulo() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoABMService.desmarcarTitulo(tituloRequest, serviciosSesionUsuario.getUsuario());
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String modificacionDeCantidadGrupoConCap() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoABMService.modificacionDeCantidadGrupoConCap(grupoRequest, serviciosSesionUsuario.getUsuario());
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String modificacionDeCantidadGrupoSinCap() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoABMService.modificacionDeCantidadGrupoSinCap(grupoRequest, serviciosSesionUsuario.getUsuario());
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String dameCapitulosParaEliminar() throws Exception {
        try {
            setCapitulos(modificarContratoService.dameCapitulosParaEliminar(titulosRequest));
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String dameCapitulosParaAgregar() throws Exception {
        try {
            setCapitulos(modificarContratoService.dameCapitulosParaAgregar(titulosRequest));
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    public String titulosABorrar() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoValidatorService.validarTitulosABorrar(data, serviciosSesionUsuario.getUsuario());
            
            generarReporteTitulosADesconfirmar();
            
            if (validacionModificacionContrato.isEmpty() || skipWarnings) {
                validacionModificacionContrato = modificarContratoABMService.titulosABorrar(data, serviciosSesionUsuario.getUsuario());
            }
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String capitulosAAgregar() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoValidatorService.validarCapitulosAAgregar(data, serviciosSesionUsuario.getUsuario());
            if (validacionModificacionContrato.isEmpty() || skipWarnings) {
                validacionModificacionContrato = modificarContratoABMService.capitulosAAgregar(data, serviciosSesionUsuario.getUsuario());
            }
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    public String capitulosABorrar() throws Exception {
        try {
            //validacionModificacionContrato = modificarContratoValidatorService.validarCapitulosABorrar(data, serviciosSesionUsuario.getUsuario());
            //if (validacionModificacionContrato.isEmpty() || skipWarnings) {
                validacionModificacionContrato = modificarContratoABMService.capitulosABorrar(data, serviciosSesionUsuario.getUsuario());
            //}
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    public String dameTituloContratado() throws Exception {
        try {
            tituloConGrupo = modificarContratoService.dameTituloContratado(tituloRequest, serviciosSesionUsuario.getUsuario());
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    public String ampliarDerechos() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoValidatorService.validarAmpliarDerechos(vigenciaRequest, serviciosSesionUsuario.getUsuario());
            if (validacionModificacionContrato.isEmpty() || skipWarnings) {
                validacionModificacionContrato = modificarContratoABMService.ampliarDerechos(vigenciaRequest, serviciosSesionUsuario.getUsuario());
            }
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String adelantarVencimiento() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoValidatorService.validarAdelantarVencimiento(vigenciaRequest, serviciosSesionUsuario.getUsuario());
            if (validacionModificacionContrato.isEmpty() || skipWarnings) {
                validacionModificacionContrato = modificarContratoABMService.adelantarVencimiento(vigenciaRequest, serviciosSesionUsuario.getUsuario());
            }        
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    public String damePayTV() throws Exception {
        try {
            vigencia = modificarContratoService.damePayTV(vigenciaRequest, serviciosSesionUsuario.getUsuario());
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    public String procesarPayTV() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoValidatorService.validarProcesarPayTV(vigenciaRequest, serviciosSesionUsuario.getUsuario());
            
            generarReporteTitulosADesconfirmar();
            
            if (validacionModificacionContrato.isEmpty() || skipWarnings) {
                validacionModificacionContrato = modificarContratoABMService.procesarPayTV(vigenciaRequest, serviciosSesionUsuario.getUsuario());
            }
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    public String modificarTituloContratadoSC() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoValidatorService.validarModificarTituloContratadoSC(tituloConGrupoRequest, serviciosSesionUsuario.getUsuario());
            
            generarReporteTitulosADesconfirmar();
            
            if (validacionModificacionContrato.isEmpty() || skipWarnings) {
                validacionModificacionContrato = modificarContratoABMService.modificarTituloContratadoSC(tituloConGrupoRequest, serviciosSesionUsuario.getUsuario());
            }
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String modificarTituloContratadoCC() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoABMService.modificarTituloContratadoCC(tituloConGrupoRequest, serviciosSesionUsuario.getUsuario());
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String reemplazarTitulos() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoValidatorService.validarReemplazarTitulos(data, serviciosSesionUsuario.getUsuario());
            
            generarReporteTitulosADesconfirmar();
            
            if (validacionModificacionContrato.isEmpty() || skipWarnings) {
                validacionModificacionContrato = modificarContratoABMService.reemplazarTitulos(data, serviciosSesionUsuario.getUsuario());
            }
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    
    public String dameGruposConReemplazo() throws Exception {
        try {
            grupos = modificarContratoService.dameGruposConReemplazo(gruposConReemplazoRequest);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    public String reemplazarGrupo() throws Exception {
        try {

            validacionModificacionContrato = modificarContratoValidatorService.validarReemplazarGrupo(gruposConReemplazoRequest, serviciosSesionUsuario.getUsuario());
            
            generarReporteTitulosADesconfirmar();
            
            if (validacionModificacionContrato.isEmpty() || skipWarnings) {
                validacionModificacionContrato = modificarContratoABMService.reemplazarGrupo(gruposConReemplazoRequest, serviciosSesionUsuario.getUsuario());
            }
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    public String validarExtenderChequeoTecnico() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoValidatorService.validarExtenderChequeoTecnico(claveContrato, serviciosSesionUsuario.getUsuario());
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    public String dameSenialesChequeo() throws Exception {
        try {
            senialesAsignadas = modificarContratoService.dameSenialesChequeo(claveContrato, serviciosSesionUsuario.getUsuario());
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    public String extenderChequeoTecnico() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoABMService.extenderChequeo(data, serviciosSesionUsuario.getUsuario());
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    public String validarEliminarGrupo() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoValidatorService.validarEliminarGrupo(claveContrato, claveGrupo, senial, serviciosSesionUsuario.getUsuario());
            
            generarReporteTitulosADesconfirmar();
            
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    public String eliminarGrupo() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoABMService.eliminarGrupo(claveContrato, claveGrupo, senial, serviciosSesionUsuario.getUsuario());
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    public String dameClave() throws Exception {
        try {
            claveTitulo = modificarContratoService.dameClave(tituloRequest.getTipoTitulo());
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String dameTG() throws Exception {
        try {
            dameTGResponse = modificarContratoService.dameTG(dameTGRequest, serviciosSesionUsuario.getUsuario());
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String actualizarTG() throws Exception {
        try {
            validacionModificacionContrato = modificarContratoABMService.actualizarTG(actualizarTGRequest, serviciosSesionUsuario.getUsuario());
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    public String dameTitulosConReRun() throws Exception {
        try {
            titulosContratados = modificarContratoService.dameTitulosConReRun(titulosRequest, serviciosSesionUsuario.getUsuario());
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    public String cleanTitulosConReRun() throws Exception {
        try {
            modificarContratoABMService.cleanTitulosConReRun(titulosRequest, serviciosSesionUsuario.getUsuario());
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String bloquearContrato() throws Exception {
        try {
            String sessionId =  ServletActionContext.getRequest().getSession().getId();
            String user = serviciosSesionUsuario.getUsuario();
            bloqueoContratoService.bloquearContrato(claveContrato, sessionId, user);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String desbloquearContrato() throws Exception {
        try {
//            String sessionId =  ServletActionContext.getRequest().getSession().getId();
//            String user = serviciosSesionUsuario.getUsuario();
            bloqueoContratoService.desbloquearContrato(claveContrato);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String estaBloqueado() throws Exception {
        try {
//            String sessionId =  ServletActionContext.getRequest().getSession().getId();
//            String user = serviciosSesionUsuario.getUsuario();
            this.setEstaBloqueado(bloqueoContratoService.estaBloqueado(claveContrato));
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getClaveContrato() {
        return this.claveContrato;
    }

    public void setClaveContrato(Integer claveContrato) {
        this.claveContrato = claveContrato;
    }

    public List<Contrato> getContratos() {
        return this.contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    public void setDistribuidores(List<Distribuidor> distribuidores) {
        this.distribuidores = distribuidores;
    }

    public List<Distribuidor> getDistribuidores() {
        return this.distribuidores;
    }

    public void setNombreDistribuidor(String nombreDistribuidor) {
        this.nombreDistribuidor = nombreDistribuidor;
    }

    public String getNombreDistribuidor() {
        return this.nombreDistribuidor;
    }

    public Integer getClaveDistribuidor() {
        return claveDistribuidor;
    }

    public void setClaveDistribuidor(Integer claveDistribuidor) {
        this.claveDistribuidor = claveDistribuidor;
    }

    public List<ObservacionDePago> getObservacionesDePago() {
        return observacionesDePago;
    }

    public void setObservacionesDePago(List<ObservacionDePago> observacionesDePago) {
        this.observacionesDePago = observacionesDePago;
    }

    public List<Moneda> getMonedas() {
        return monedas;
    }

    public void setMonedas(List<Moneda> monedas) {
        this.monedas = monedas;
    }

    public List<SenialImporte> getSenialesConImportes() {
        return senialesConImportes;
    }

    public void setSenialesConImportes(List<SenialImporte> senialesConImportes) {
        this.senialesConImportes = senialesConImportes;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public void setSenial(String senial) {
        this.senial = senial;
    }

    public String getSenial() {
        return this.senial;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Integer getClaveGrupo() {
        return claveGrupo;
    }

    public void setClaveGrupo(Integer claveGrupo) {
        this.claveGrupo = claveGrupo;
    }

    public List<Senial> getSenialesAsignadas() {
        return senialesAsignadas;
    }

    public void setSenialesAsignadas(List<Senial> senialesAsignadas) {
        this.senialesAsignadas = senialesAsignadas;
    }

    public List<TipoDerechoTerritorial> getTiposDeDerechoTerritorial() {
        return tiposDeDerechoTerritorial;
    }

    public void setTiposDeDerechoTerritorial(List<TipoDerechoTerritorial> tiposDeDerechoTerritorial) {
        this.tiposDeDerechoTerritorial = tiposDeDerechoTerritorial;
    }

    public List<TipoDerecho> getTiposDeDerecho() {
        return tiposDeDerecho;
    }

    public void setTiposDeDerecho(List<TipoDerecho> tiposDeDerecho) {
        this.tiposDeDerecho = tiposDeDerecho;
    }

    public List<TipoImporte> getTiposDeImporte() {
        return tiposDeImporte;
    }

    public void setTiposDeImporte(List<TipoImporte> tiposDeImporte) {
        this.tiposDeImporte = tiposDeImporte;
    }

    public List<TipoTitulo> getTiposDeTitulo() {
        return tiposDeTitulo;
    }

    public void setTiposDeTitulo(List<TipoTitulo> tiposDeTitulo) {
        this.tiposDeTitulo = tiposDeTitulo;
    }

    public ContratoValidationRequest getContratoValidationRequest() {
        return contratoValidationRequest;
    }

    public void setContratoValidationRequest(ContratoValidationRequest contratoValidationRequest) {
        this.contratoValidationRequest = contratoValidationRequest;
    }

    public List<Titulo> getTitulos() {
        return titulos;
    }

    public void setTitulos(List<Titulo> titulos) {
        this.titulos = titulos;
    }

    public TitulosRequest getTitulosRequest() {
        return titulosRequest;
    }

    public void setTitulosRequest(TitulosRequest titulosRequest) {
        this.titulosRequest = titulosRequest;
    }

    public Titulo getTitulo() {
        return titulo;
    }

    public void setTitulo(Titulo titulo) {
        this.titulo = titulo;
    }

    public List<Capitulo> getCapitulos() {
        return capitulos;
    }

    public void setCapitulos(List<Capitulo> capitulos) {
        this.capitulos = capitulos;
    }

    public final List<TituloContratado> getTitulosContratados() {
        return this.titulosContratados;
    }

    public final void setTitulosContratados(List<TituloContratado> titulosAReemplazarOEliminar) {
        this.titulosContratados = titulosAReemplazarOEliminar;
    }

    public List<ContratoConReRun> getContratosConReRun() {
        return contratosConReRun;
    }

    public void setContratosConReRun(List<ContratoConReRun> contratosConReRun) {
        this.contratosConReRun = contratosConReRun;
    }

    public TitulosRecontratadosRequest getTitulosRecontratadosRequest() {
        return titulosRecontratadosRequest;
    }

    public void setTitulosRecontratadosRequest(TitulosRecontratadosRequest titulosRecontratadosRequest) {
        this.titulosRecontratadosRequest = titulosRecontratadosRequest;
    }

    public TituloRequest getTituloRequest() {
        return tituloRequest;
    }

    public void setTituloRequest(TituloRequest tituloRequest) {
        this.tituloRequest = tituloRequest;
    }

    public List<Vigencia> getVigencias() {
        return vigencias;
    }

    public void setVigencias(List<Vigencia> vigencias) {
        this.vigencias = vigencias;
    }

    public Boolean getContratoUpdated() {
        return contratoUpdated;
    }

    public void setContratoUpdated(Boolean contratoUpdated) {
        this.contratoUpdated = contratoUpdated;
    }

    public Boolean getContratoConSeniales() {
        return contratoConSeniales;
    }

    public void setContratoConSeniales(Boolean contratoConSeniales) {
        this.contratoConSeniales = contratoConSeniales;
    }

    public List<ContratoValidationResult> getValidacionModificacionContrato() {
        return validacionModificacionContrato;
    }

    public void setValidacionModificacionContrato(List<ContratoValidationResult> validacionModificacionContrato) {
        this.validacionModificacionContrato = validacionModificacionContrato;
    }

    public SenialImporte getSenialImporteRequest() {
        return senialImporteRequest;
    }

    public void setSenialImporteRequest(SenialImporte senialImporteRequest) {
        this.senialImporteRequest = senialImporteRequest;
    }

    public GrupoRequest getGrupoRequest() {
        return grupoRequest;
    }

    public void setGrupoRequest(GrupoRequest grupoRequest) {
        this.grupoRequest = grupoRequest;
    }

    public ValidarTituloPerpetuoRequest getValidarTituloPerpetuoRequest() {
        return validarTituloPerpetuoRequest;
    }

    public void setValidarTituloPerpetuoRequest(ValidarTituloPerpetuoRequest validarTituloPerpetuoRequest) {
        this.validarTituloPerpetuoRequest = validarTituloPerpetuoRequest;
    }

    public List<Senial> getSenialesHeredadas() {
        return senialesHeredadas;
    }

    public void setSenialesHeredadas(List<Senial> senialesHeredadas) {
        this.senialesHeredadas = senialesHeredadas;
    }

    public Integer getNroGrupoResponse() {
        return nroGrupoResponse;
    }

    public void setNroGrupoResponse(Integer nroGrupoResponse) {
        this.nroGrupoResponse = nroGrupoResponse;
    }

    public ValidarAltaTituloRequest getValidarAltaTituloRequest() {
        return validarAltaTituloRequest;
    }

    public void setValidarAltaTituloRequest(ValidarAltaTituloRequest validarAltaTituloRequest) {
        this.validarAltaTituloRequest = validarAltaTituloRequest;
    }
    
    @SuppressWarnings("rawtypes")
    public Map getData() {
        return data;
    }

    @SuppressWarnings("rawtypes")
    public void setData(Map data) {
        this.data = data;
    }

    public RerunRequest getRerunRequest() {
        return rerunRequest;
    }

    public void setRerunRequest(RerunRequest rerunRequest) {
        this.rerunRequest = rerunRequest;
    }

    public Boolean getRerunResponse() {
        return rerunResponse;
    }

    public void setRerunResponse(Boolean rerunResponse) {
        this.rerunResponse = rerunResponse;
    }

    public TituloConGrupo getTituloConGrupo() {
        return tituloConGrupo;
    }

    public void setTituloConGrupo(TituloConGrupo tituloConGrupo) {
        this.tituloConGrupo = tituloConGrupo;
    }

    public VigenciaRequest getVigenciaRequest() {
        return vigenciaRequest;
    }

    public void setVigenciaRequest(VigenciaRequest vigenciaRequest) {
        this.vigenciaRequest = vigenciaRequest;
    }

    public Vigencia getVigencia() {
        return vigencia;
    }

    public void setVigencia(Vigencia vigencia) {
        this.vigencia = vigencia;
    }

    public TituloConGrupo getTituloConGrupoRequest() {
        return tituloConGrupoRequest;
    }

    public void setTituloConGrupoRequest(TituloConGrupo tituloConGrupoRequest) {
        this.tituloConGrupoRequest = tituloConGrupoRequest;
    }

    public GruposConReemplazoRequest getGruposConReemplazoRequest() {
        return gruposConReemplazoRequest;
    }

    public void setGruposConReemplazoRequest(GruposConReemplazoRequest gruposConReemplazoRequest) {
        this.gruposConReemplazoRequest = gruposConReemplazoRequest;
    }

    public String getClaveTitulo() {
        return claveTitulo;
    }

    public void setClaveTitulo(String claveTitulo) {
        this.claveTitulo = claveTitulo;
    }

    public Boolean getSkipWarnings() {
        return skipWarnings;
    }

    public void setSkipWarnings(Boolean skipWarnings) {
        this.skipWarnings = skipWarnings;
    }

    public void setDameTGRequest(DameTGRequest dameTGRequest) {
        this.dameTGRequest = dameTGRequest;
    }
    
    public DameTGRequest getDameTGRequest() {
        return this.dameTGRequest;
    }
    
    public void setDameTGResponse(List<DameTGResponse> dameTGResponse) {
        this.dameTGResponse = dameTGResponse;
    }
    
    public List<DameTGResponse> getDameTGResponse() {
        return this.dameTGResponse;
    }
    
    public void setActualizarTGRequest(ActualizarTGRequest actualizarTGRequest) {
        this.actualizarTGRequest = actualizarTGRequest;
    }
    
    public ActualizarTGRequest getActualizarTGRequest() {
        return this.actualizarTGRequest;
    }

    public boolean isEstaBloqueado() {
        return estaBloqueado;
    }

    public void setEstaBloqueado(boolean estaBloqueado) {
        this.estaBloqueado = estaBloqueado;
    }
}
