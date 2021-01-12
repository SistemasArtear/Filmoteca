package com.artear.filmo.web.action;

import static com.artear.filmo.constants.ApplicationConstants.ERROR_INESPERADO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.artear.filmo.bo.common.AyudaSituar;
import com.artear.filmo.bo.trabajar.copias.AltaRequest;
import com.artear.filmo.bo.trabajar.copias.BajaRequest;
import com.artear.filmo.bo.trabajar.copias.BuscarTitulosRequest;
import com.artear.filmo.bo.trabajar.copias.Capitulo;
import com.artear.filmo.bo.trabajar.copias.CopiaListado;
import com.artear.filmo.bo.trabajar.copias.MasterCC;
import com.artear.filmo.bo.trabajar.copias.ModifRequest;
import com.artear.filmo.bo.trabajar.copias.TituloListado;
import com.artear.filmo.bo.trabajar.copias.ValidarRequest;
import com.artear.filmo.bo.trabajar.copias.ValidarResponse;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.services.interfaces.TrabajarConCopiasDeTitulosService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class TrabajarConCopiasDeTitulosAction extends ActionSupport {

	private static final long serialVersionUID = -3477878624376919717L;

	// OUT
	private String message;
	private List<TituloListado> listadoTitulos;
	private List<CopiaListado> listadoCopias;
	private List<AyudaSituar> soportes;
	private List<Capitulo> capitulos;
	private String resultadoVerificacion;
	private ValidarResponse resultadoValidacionAlta;
	private ValidarResponse resultadoValidacionBaja;

	// IN
	private BuscarTitulosRequest busquedaTitulosRequest;
	private String senial;
	private String clave;
	private AltaRequest altaRequest;
	private ModifRequest modifRequest;
	private BajaRequest bajaRequest;
	private String descripcion;
	private MasterCC master;
	private String soporte;
	private ValidarRequest validarBajaRequest;

	@Autowired
	private TrabajarConCopiasDeTitulosService trabajarConCopiasDeTitulosService;

	public String buscarTitulos() {
		try {
			listadoTitulos = trabajarConCopiasDeTitulosService
					.buscarTitulos(busquedaTitulosRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String buscarDatosCopias() {
		try {
			listadoCopias = trabajarConCopiasDeTitulosService
					.buscarDatosCopias(senial, clave);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String altaMasterRollos() {
		try {
			trabajarConCopiasDeTitulosService.altaMasterRollos(altaRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String bajaMasterRollos() {
		try {
			trabajarConCopiasDeTitulosService.bajaMasterRollos(bajaRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String buscarSoportes() {
		try {
			soportes = trabajarConCopiasDeTitulosService
					.buscarSoportes(descripcion);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String buscarCapitulos() {
		try {
			capitulos = trabajarConCopiasDeTitulosService
					.buscarCapitulos(clave);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String verificarMasterSoporte() {
		try {
			resultadoVerificacion = trabajarConCopiasDeTitulosService
					.verificarMasterSoporte(master);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String verificarRechazoCVR() {
		try {
			resultadoVerificacion = trabajarConCopiasDeTitulosService
					.verificarRechazoCVR(clave, senial, soporte);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String chequearMaterialesCopia() {
		try {
			resultadoVerificacion = trabajarConCopiasDeTitulosService
					.chequearMaterialesCopiaCall(clave, senial);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String validarAlta() {
		try {
			resultadoValidacionAlta = trabajarConCopiasDeTitulosService
					.validarAlta(altaRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String validarBaja() {
		try {
			resultadoValidacionBaja = trabajarConCopiasDeTitulosService
					.validarBaja(validarBajaRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String modifMasterRollo() {
		try {
			trabajarConCopiasDeTitulosService
					.modificarCopiaMaster(modifRequest);
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

	public List<TituloListado> getListadoTitulos() {
		return listadoTitulos;
	}

	public void setListadoTitulos(List<TituloListado> listadoTitulos) {
		this.listadoTitulos = listadoTitulos;
	}

	public BuscarTitulosRequest getBusquedaTitulosRequest() {
		return busquedaTitulosRequest;
	}

	public void setBusquedaTitulosRequest(
			BuscarTitulosRequest busquedaTitulosRequest) {
		this.busquedaTitulosRequest = busquedaTitulosRequest;
	}

	public List<CopiaListado> getListadoCopias() {
		return listadoCopias;
	}

	public void setListadoCopias(List<CopiaListado> listadoCopias) {
		this.listadoCopias = listadoCopias;
	}

	public String getSenial() {
		return senial;
	}

	public void setSenial(String senial) {
		this.senial = senial;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public AltaRequest getAltaRequest() {
		return altaRequest;
	}

	public void setAltaRequest(AltaRequest altaRequest) {
		this.altaRequest = altaRequest;
	}

	public BajaRequest getBajaRequest() {
		return bajaRequest;
	}

	public void setBajaRequest(BajaRequest bajaRequest) {
		this.bajaRequest = bajaRequest;
	}

	public ModifRequest getModifRequest() {
		return modifRequest;
	}

	public void setModifRequest(ModifRequest modifRequest) {
		this.modifRequest = modifRequest;
	}

	public List<AyudaSituar> getSoportes() {
		return soportes;
	}

	public void setSoportes(List<AyudaSituar> soportes) {
		this.soportes = soportes;
	}

	public List<Capitulo> getCapitulos() {
		return capitulos;
	}

	public void setCapitulos(List<Capitulo> capitulos) {
		this.capitulos = capitulos;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public MasterCC getMaster() {
		return master;
	}

	public void setMaster(MasterCC master) {
		this.master = master;
	}

	public String getResultadoVerificacion() {
		return resultadoVerificacion;
	}

	public void setResultadoVerificacion(String resultadoVerificacion) {
		this.resultadoVerificacion = resultadoVerificacion;
	}

	public ValidarResponse getResultadoValidacionAlta() {
		return resultadoValidacionAlta;
	}

	public void setResultadoValidacionAlta(
			ValidarResponse resultadoValidacionAlta) {
		this.resultadoValidacionAlta = resultadoValidacionAlta;
	}

	public String getSoporte() {
		return soporte;
	}

	public void setSoporte(String soporte) {
		this.soporte = soporte;
	}

	public ValidarResponse getResultadoValidacionBaja() {
		return resultadoValidacionBaja;
	}

	public void setResultadoValidacionBaja(
			ValidarResponse resultadoValidacionBaja) {
		this.resultadoValidacionBaja = resultadoValidacionBaja;
	}

	public ValidarRequest getValidarBajaRequest() {
		return validarBajaRequest;
	}

	public void setValidarBajaRequest(ValidarRequest validarBajaRequest) {
		this.validarBajaRequest = validarBajaRequest;
	}

}
