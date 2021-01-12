package com.artear.filmo.web.action;

import static com.artear.filmo.constants.ApplicationConstants.ERROR_INESPERADO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.artear.filmo.bo.trabajar.titulos.OperacionTitulo;
import com.artear.filmo.bo.trabajar.titulos.TituloAmortizadoGrillaResponse;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.services.interfaces.TrabajarConTitulosAmortizadosService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class TrabajarConTitulosAmortizadosAction extends ActionSupport {

    private static final long serialVersionUID = -3477878624376919717L;

    // OUT
    private List<TituloAmortizadoGrillaResponse> titulosAmortizadosGrilla;
    private String message;
    private boolean fueContabilizado;
    private boolean tituloActivado;

    // IN
    private String descripcion;
    private OperacionTitulo operacion;
    private String clave;
    private String senial;

    @Autowired
    private TrabajarConTitulosAmortizadosService trabajarConTitulosAmortizadosService;

    public void setTitulosAmortizadosGrilla(List<TituloAmortizadoGrillaResponse> titulosAmortizadosGrilla) {
        this.titulosAmortizadosGrilla = titulosAmortizadosGrilla;
    }

    public List<TituloAmortizadoGrillaResponse> getTitulosAmortizadosGrilla() {
        return this.titulosAmortizadosGrilla;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isFueContabilizado() {
        return fueContabilizado;
    }

    public void setFueContabilizado(boolean fueContabilizado) {
        this.fueContabilizado = fueContabilizado;
    }

    public boolean isTituloActivado() {
        return tituloActivado;
    }

    public void setTituloActivado(boolean tituloActivado) {
        this.tituloActivado = tituloActivado;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public OperacionTitulo getOperacion() {
        return operacion;
    }

    public void setOperacion(OperacionTitulo operacion) {
        this.operacion = operacion;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getSenial() {
		return senial;
	}
    
    public void setSenial(String senial) {
		this.senial = senial;
	}
    
    public String obtenerTitulosAmortizadosCastellanoPorDescripcion() throws Exception {
        try {
            titulosAmortizadosGrilla = trabajarConTitulosAmortizadosService.obtenerTitulosAmortizadosCastellanoPorDescripcion(descripcion, senial);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    /** FIXME: sacar los parámetros IN del JSONresponse **/
    public String obtenerTitulosAmortizadosOriginalPorDescripcion() throws Exception {
        try {
            titulosAmortizadosGrilla = trabajarConTitulosAmortizadosService.obtenerTitulosAmortizadosOriginalPorDescripcion(descripcion, senial);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    /** FIXME: sacar los parámetros IN del JSONresponse **/
    public String obtenerTitulosAmortizadosCastellanoPorCodigo() throws Exception {
        try {
            titulosAmortizadosGrilla = trabajarConTitulosAmortizadosService.obtenerTitulosAmortizadosCastellanoPorCodigo(clave, senial);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    /** FIXME: sacar los parámetros IN del JSONresponse **/
    public String obtenerTitulosAmortizadosOriginalPorCodigo() throws Exception {
        try {
            titulosAmortizadosGrilla = trabajarConTitulosAmortizadosService.obtenerTitulosAmortizadosOriginalPorCodigo(clave, senial);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    /** FIXME: sacar los parámetros IN del JSONresponse **/
    public String fueContabilizado() throws Exception {
        try {
            /* Un comentario aqui, si el PL retorna false, entonces fue contabilizado */
            setFueContabilizado(!trabajarConTitulosAmortizadosService.fueContabilizado(clave).get(0));
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    /** FIXME: sacar los parámetros IN del JSONresponse **/
    public String activarTitulo() throws Exception {
        try {
            setTituloActivado(trabajarConTitulosAmortizadosService.activarTitulo(clave).get(0));
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
}