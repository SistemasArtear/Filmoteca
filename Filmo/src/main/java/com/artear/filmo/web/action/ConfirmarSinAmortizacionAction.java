package com.artear.filmo.web.action;

import static com.artear.filmo.constants.ApplicationConstants.ERROR_INESPERADO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artear.filmo.bo.programacion.BuscarTitulosProgramasRequest;
import com.artear.filmo.bo.programacion.ConfirmarRequest;
import com.artear.filmo.bo.programacion.ProgramaClaveResponse;
import com.artear.filmo.bo.programacion.ProgramaCodigoResponse;
import com.artear.filmo.bo.programacion.TituloPrograma;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.seguridad.services.ServiciosSesionUsuario;
import com.artear.filmo.services.interfaces.ConfirmarSinAmortizacionService;
import com.opensymphony.xwork2.ActionSupport;

@Service
public class ConfirmarSinAmortizacionAction extends ActionSupport {

    private static final long serialVersionUID = 1L;
    // IN
    private String descripcion;
    private String senial;
    private BuscarTitulosProgramasRequest titulosProgramasRequest;
    private ConfirmarRequest confirmarRequest;
    // OUT
    private String message;
    private List<ProgramaCodigoResponse> programasCodigo;
    private List<ProgramaClaveResponse> programasClave;
    private List<TituloPrograma> titulosProgramas;

    @Autowired
    private ConfirmarSinAmortizacionService confirmarSinAmortizacionService;

    @Autowired
    private ServiciosSesionUsuario serviciosSesionUsuario;

    public String buscarProgramasCodigo() {
        try {
            programasCodigo = confirmarSinAmortizacionService.buscarProgramasCodigo(descripcion, senial);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String buscarProgramasClave() {
        try {
            programasClave = confirmarSinAmortizacionService.buscarProgramasClave(descripcion);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String buscarTitulosProgramas() {
        try {
            titulosProgramas = confirmarSinAmortizacionService.buscarTitulosProgramas(titulosProgramasRequest);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String confirmar() {
        try {
            confirmarRequest.setUsuario(serviciosSesionUsuario.getUsuario());
            confirmarSinAmortizacionService.confirmar(confirmarRequest);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ProgramaCodigoResponse> getProgramasCodigo() {
        return programasCodigo;
    }

    public void setProgramasCodigo(List<ProgramaCodigoResponse> programasCodigo) {
        this.programasCodigo = programasCodigo;
    }

    public List<ProgramaClaveResponse> getProgramasClave() {
        return programasClave;
    }

    public void setProgramasClave(List<ProgramaClaveResponse> programasClave) {
        this.programasClave = programasClave;
    }

    public List<TituloPrograma> getTitulosProgramas() {
        return titulosProgramas;
    }

    public void setTitulosProgramas(List<TituloPrograma> titulosProgramas) {
        this.titulosProgramas = titulosProgramas;
    }

    public BuscarTitulosProgramasRequest getTitulosProgramasRequest() {
        return titulosProgramasRequest;
    }

    public void setTitulosProgramasRequest(BuscarTitulosProgramasRequest titulosProgramasRequest) {
        this.titulosProgramasRequest = titulosProgramasRequest;
    }

    public ConfirmarRequest getConfirmarRequest() {
        return confirmarRequest;
    }

    public void setConfirmarRequest(ConfirmarRequest confirmarRequest) {
        this.confirmarRequest = confirmarRequest;
    }

	public String getSenial() {
		return senial;
	}

	public void setSenial(String senial) {
		this.senial = senial;
	}

    

}