package com.artear.filmo.web.action;

import static com.artear.filmo.constants.ApplicationConstants.ERROR_INESPERADO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artear.filmo.bo.programacion.BuscarTitulosProgramasRequest;
import com.artear.filmo.bo.programacion.DesconfirmarRequest;
import com.artear.filmo.bo.programacion.ProgramaClaveResponse;
import com.artear.filmo.bo.programacion.ProgramaCodigoResponse;
import com.artear.filmo.bo.programacion.TituloPrograma;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.services.interfaces.DesconfirmarSinAmortizacionService;
import com.opensymphony.xwork2.ActionSupport;

@Service
public class DesconfirmarSinAmortizacionAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	// IN
	private String descripcion;
	private String senial;
	private BuscarTitulosProgramasRequest titulosProgramasRequest;
	private DesconfirmarRequest desconfirmarRequest;
	// OUT
	private String message;
	private List<ProgramaCodigoResponse> programasCodigo;
	private List<ProgramaClaveResponse> programasClave;
	private List<TituloPrograma> titulosProgramas;

	@Autowired
	private DesconfirmarSinAmortizacionService desconfirmarSinAmortizacionService;

	public String buscarProgramasCodigo() {
		try {
			programasCodigo = desconfirmarSinAmortizacionService
					.buscarProgramasCodigo(descripcion , senial);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String buscarProgramasClave() {
		try {
			programasClave = desconfirmarSinAmortizacionService
					.buscarProgramasClave(descripcion);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String buscarTitulosProgramas() {
		try {
			titulosProgramas = desconfirmarSinAmortizacionService
					.buscarTitulosProgramas(titulosProgramasRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String desconfirmar() {
		try {
			desconfirmarSinAmortizacionService
					.desconfirmar(desconfirmarRequest);
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

	public void setTitulosProgramasRequest(
			BuscarTitulosProgramasRequest titulosProgramasRequest) {
		this.titulosProgramasRequest = titulosProgramasRequest;
	}

	public DesconfirmarRequest getDesconfirmarRequest() {
		return desconfirmarRequest;
	}

	public void setDesconfirmarRequest(DesconfirmarRequest desconfirmarRequest) {
		this.desconfirmarRequest = desconfirmarRequest;
	}

	public String getSenial() {
		return senial;
	}

	public void setSenial(String senial) {
		this.senial = senial;
	}

	
}