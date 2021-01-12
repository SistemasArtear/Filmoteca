package com.artear.filmo.web.action;

import static com.artear.filmo.constants.ApplicationConstants.ERROR_INESPERADO;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.artear.filmo.bo.programacion.Capitulo;
import com.artear.filmo.bo.programacion.Pagination;
import com.artear.filmo.bo.programacion.ParteSegmento;
import com.artear.filmo.bo.programacion.Programa;
import com.artear.filmo.bo.programacion.ProgramasPagination;
import com.artear.filmo.bo.programacion.Titulo;
import com.artear.filmo.bo.programacion.TituloProgramado;
import com.artear.filmo.bo.programacion.TituloRequest;
import com.artear.filmo.bo.programacion.ValidationResult;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.seguridad.services.ServiciosSesionUsuario;
import com.artear.filmo.services.interfaces.ArmadoDeProgramacionService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class ArmadoDeProgramacionAction extends ActionSupport {

    private static final long serialVersionUID = -3477878624376919717L;

    // OUT
    private List<Programa> programas;
    private List<TituloProgramado> titulosProgramados;
    private String message;
    private ProgramasPagination programasPagination;
    private Titulo titulo;
    private List<Titulo> listaTitulosProgramar;
    private List<Capitulo> capitulos;
    private List<ParteSegmento> listaParteSegmento;
    private List<ValidationResult> listaResultadosValidacion;
    private Boolean altaTitulo;
    private Boolean bajaTitulo;
    private Boolean modifTitulo;
    private String observaciones;
    private Boolean esUnCapituloValido;
    private Boolean esUnaClaveValida;
    
    // IN
    private Integer codPrograma;
    private String descPrograma;
    private String idSenial;
    private String fechaSituar;
    private String claveTitulo;
    private Integer parte;
    private Integer segmento;
    private String tituloCast;
    private String familiaTitulo;
    private Integer nroCapitulo;
    private Pagination paginationRequest;
    private TituloRequest tituloRequest;
    
    @Autowired
    private ArmadoDeProgramacionService armadoDeProgramacionService;

    @Autowired
    private ServiciosSesionUsuario serviciosSesionUsuario;

    public String esUnaClaveValida() {
        try {
            esUnaClaveValida = armadoDeProgramacionService.esUnaClaveValida(claveTitulo);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String esUnCapituloValido() {
        try {
            esUnCapituloValido = armadoDeProgramacionService.esUnCapituloValido(claveTitulo, nroCapitulo);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String levantarListaDeProgramas() {
        try {
            programas = armadoDeProgramacionService.levantarListaDeProgramas(idSenial);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String obtenerGrillaProgramada() {
        try {
            setTitulosProgramados(armadoDeProgramacionService.obtenerGrillaProgramada(idSenial, codPrograma, new SimpleDateFormat("dd-MM-yyyy").parse(getFechaSituar()), null));
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String obtenerCapitulos() {
        try {
            this.setCapitulos(armadoDeProgramacionService.obtenerCapitulos(claveTitulo));
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String obtenerListaTitulosProgramar() {
        try {
            this.setListaTitulosProgramar(armadoDeProgramacionService.obtenerListaTitulosProgramar(idSenial, tituloCast, familiaTitulo));
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String obtenerParteSegmento() {
        try {
            this.setListaParteSegmento(armadoDeProgramacionService.obtenerParteSegmento(claveTitulo, nroCapitulo));
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    public String obtenerDatosTitulo() {
        try {
            this.setTitulo(armadoDeProgramacionService.obtenerDatosTitulo(claveTitulo, nroCapitulo, parte, segmento));
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    public String validarAltaProgramacion() {
        try {
            this.setListaResultadosValidacion(armadoDeProgramacionService.validarAltaProgramacion(tituloRequest, serviciosSesionUsuario.getUsuario()));
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    public String validarBajaProgramacion() {
        try {
            this.setListaResultadosValidacion(armadoDeProgramacionService.validarBajaProgramacion(tituloRequest));
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String validarModificaProgramacion() {
        try {
            this.setListaResultadosValidacion(armadoDeProgramacionService.validarModificaProgramacion(tituloRequest, serviciosSesionUsuario.getUsuario()));
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String altaProgramacion() {
        try {
            this.setAltaTitulo(armadoDeProgramacionService.altaProgramacion(tituloRequest, serviciosSesionUsuario.getUsuario()));
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String bajaProgramacion() {
        try {
            this.setBajaTitulo(armadoDeProgramacionService.bajaProgramacion(tituloRequest));
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String modificaProgramacion() {
        try {
            this.setModifTitulo(armadoDeProgramacionService.modificaProgramacion(tituloRequest, serviciosSesionUsuario.getUsuario()));
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String obtenerObservaciones() {
        try {
            this.observaciones = armadoDeProgramacionService.obtenerObservaciones(tituloRequest, serviciosSesionUsuario.getUsuario());
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

    public List<Programa> getProgramas() {
        return programas;
    }

    public void setProgramas(List<Programa> programas) {
        this.programas = programas;
    }

    public Integer getCodPrograma() {
        return codPrograma;
    }

    public void setCodPrograma(Integer codPrograma) {
        this.codPrograma = codPrograma;
    }

    public ProgramasPagination getProgramasPagination() {
        return programasPagination;
    }

    public void setProgramasPagination(ProgramasPagination programasPagination) {
        this.programasPagination = programasPagination;
    }

    public Pagination getPaginationRequest() {
        return paginationRequest;
    }

    public void setPaginationRequest(Pagination paginationRequest) {
        this.paginationRequest = paginationRequest;
    }

    public String getDescPrograma() {
        return descPrograma;
    }

    public void setDescPrograma(String descPrograma) {
        this.descPrograma = descPrograma;
    }

    public List<TituloProgramado> getTitulosProgramados() {
        return titulosProgramados;
    }

    public void setTitulosProgramados(List<TituloProgramado> titulosProgramados) {
        this.titulosProgramados = titulosProgramados;
    }

    public String getFechaSituar() {
        return fechaSituar;
    }

    public void setFechaSituar(String fechaSituar) {
        this.fechaSituar = fechaSituar;
    }

    public String getClaveTitulo() {
        return claveTitulo;
    }

    public void setClaveTitulo(String claveTitulo) {
        this.claveTitulo = claveTitulo;
    }

    public Integer getNroCapitulo() {
        return nroCapitulo;
    }

    public void setNroCapitulo(Integer nroCapitulo) {
        this.nroCapitulo = nroCapitulo;
    }

    public String getFamiliaTitulo() {
        return familiaTitulo;
    }

    public void setFamiliaTitulo(String familiaTitulo) {
        this.familiaTitulo = familiaTitulo;
    }

    public String getTituloCast() {
        return tituloCast;
    }

    public void setTituloCast(String tituloCast) {
        this.tituloCast = tituloCast;
    }

    public List<Capitulo> getCapitulos() {
        return capitulos;
    }

    public void setCapitulos(List<Capitulo> capitulos) {
        this.capitulos = capitulos;
    }

    public List<Titulo> getListaTitulosProgramar() {
        return listaTitulosProgramar;
    }

    public void setListaTitulosProgramar(List<Titulo> listaTitulosProgramar) {
        this.listaTitulosProgramar = listaTitulosProgramar;
    }

    public List<ParteSegmento> getListaParteSegmento() {
        return listaParteSegmento;
    }

    public void setListaParteSegmento(List<ParteSegmento> listaParteSegmento) {
        this.listaParteSegmento = listaParteSegmento;
    }

    public Integer getParte() {
        return parte;
    }

    public void setParte(Integer parte) {
        this.parte = parte;
    }

    public Integer getSegmento() {
        return segmento;
    }

    public void setSegmento(Integer segmento) {
        this.segmento = segmento;
    }

    public Titulo getTitulo() {
        return titulo;
    }

    public void setTitulo(Titulo titulo) {
        this.titulo = titulo;
    }

    public String getIdSenial() {
        return idSenial;
    }

    public void setIdSenial(String idSenial) {
        this.idSenial = idSenial;
    }

    public List<ValidationResult> getListaResultadosValidacion() {
        return listaResultadosValidacion;
    }

    public void setListaResultadosValidacion(List<ValidationResult> listaResultadosValidacion) {
        this.listaResultadosValidacion = listaResultadosValidacion;
    }

    public Boolean getAltaTitulo() {
        return altaTitulo;
    }

    public void setAltaTitulo(Boolean altaTitulo) {
        this.altaTitulo = altaTitulo;
    }

    public Boolean getBajaTitulo() {
        return bajaTitulo;
    }

    public void setBajaTitulo(Boolean bajaTitulo) {
        this.bajaTitulo = bajaTitulo;
    }

    public Boolean getModifTitulo() {
        return modifTitulo;
    }

    public void setModifTitulo(Boolean modifTitulo) {
        this.modifTitulo = modifTitulo;
    }

    public TituloRequest getTituloRequest() {
        return tituloRequest;
    }

    public void setTituloRequest(TituloRequest tituloRequest) {
        this.tituloRequest = tituloRequest;
    }
    
    public String getObservaciones() {
        return this.observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Boolean getEsUnaClaveValida() {
        return esUnaClaveValida;
    }

    public void setEsUnaClaveValida(Boolean esUnaClaveValida) {
        this.esUnaClaveValida = esUnaClaveValida;
    }

    public Boolean getEsUnCapituloValido() {
        return esUnCapituloValido;
    }

    public void setEsUnCapituloValido(Boolean esUnCapituloValido) {
        this.esUnCapituloValido = esUnCapituloValido;
    }
}
