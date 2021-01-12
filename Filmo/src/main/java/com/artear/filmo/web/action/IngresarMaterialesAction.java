package com.artear.filmo.web.action;

import static com.artear.filmo.constants.ApplicationConstants.ERROR_INESPERADO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.artear.filmo.bo.common.Distribuidor;
import com.artear.filmo.bo.common.Soporte;
import com.artear.filmo.bo.ingresar.materiales.SoporteTituloABM;
import com.artear.filmo.bo.ingresar.materiales.TituloMaterialesGrillaResponse;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.services.interfaces.IngresarMaterialesService;
import com.artear.filmo.utils.StringUtils;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class IngresarMaterialesAction extends ActionSupport {

	private static final long serialVersionUID = -1555348501552846689L;

	private String message;
	private List<Distribuidor> distribuidores;
	private List<TituloMaterialesGrillaResponse> titulosMateriales;
	private List<Soporte> soportes;
	private String familiaTitulo;
	private String responseAltaRemito;
	
	private Integer codigoDistribuidor;
	private String razonSocialDistribuidor;
	private String descripcionTitulo;
	private String tipoMaterial;
	private String senial;
	private String tipoTituloSeleccionado;
	private String codigoSoporte;
	private SoporteTituloABM soporteTitulo;
	
	@Autowired
	private IngresarMaterialesService ingresarMaterialesService;
	
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

	public List<TituloMaterialesGrillaResponse> getTitulosMateriales() {
		return titulosMateriales;
	}

	public void setTitulosMateriales(List<TituloMaterialesGrillaResponse> titulosMateriales) {
		this.titulosMateriales = titulosMateriales;
	}
	
	public List<Soporte> getSoportes() {
		return soportes;
	}

	public void setSoportes(List<Soporte> soportes) {
		this.soportes = soportes;
	}

	public String getFamiliaTitulo() {
		return familiaTitulo;
	}

	public void setFamiliaTitulo(String familiaTitulo) {
		this.familiaTitulo = familiaTitulo;
	}
	
	public String getResponseAltaRemito() {
		return responseAltaRemito;
	}
	
	public void setResponseAltaRemito(String responseAltaRemito) {
		this.responseAltaRemito = responseAltaRemito;
}
	
	public Integer getCodigoDistribuidor() {
		return codigoDistribuidor;
	}
	
	public void setCodigoDistribuidor(Integer codigoDistribuidor) {
		this.codigoDistribuidor = codigoDistribuidor;
	}
	
	public String getRazonSocialDistribuidor() {
		return razonSocialDistribuidor;
	}

	public void setRazonSocialDistribuidor(String razonSocialDistribuidor) {
		this.razonSocialDistribuidor = razonSocialDistribuidor;
	}

	public String getDescripcionTitulo() {
		return descripcionTitulo;
	}

	public void setDescripcionTitulo(String descripcionTitulo) {
		this.descripcionTitulo = descripcionTitulo;
	}
	
	public String getTipoMaterial() {
		return tipoMaterial;
	}

	public void setTipoMaterial(String tipoMaterial) {
		this.tipoMaterial = tipoMaterial;
	}
	
	public String getSenial() {
		return senial;
	}
	
	public void setSenial(String senial) {
		this.senial = senial;
	}

	public String getTipoTituloSeleccionado() {
		return tipoTituloSeleccionado;
	}

	public void setTipoTituloSeleccionado(String tipoTituloSeleccionado) {
		this.tipoTituloSeleccionado = tipoTituloSeleccionado;
	}
	
	public String getCodigoSoporte() {
		return codigoSoporte;
	}

	public void setCodigoSoporte(String codigoSoporte) {
		this.codigoSoporte = codigoSoporte;
	}

	public SoporteTituloABM getSoporteTitulo() {
		return soporteTitulo;
	}

	public void setSoporteTitulo(SoporteTituloABM soporteTitulo) {
		this.soporteTitulo = soporteTitulo;
	}
	
	/**
     * Búsqueda de distribuidores por código o nombre
     * @author cetorres
     * 
     * @useCase FP210 - Ingreso de materiales
     */
	public String buscarDistribuidoresParaMateriales() throws Exception {
		try {
			distribuidores = ingresarMaterialesService.buscarDistribuidoresParaMateriales(codigoDistribuidor, razonSocialDistribuidor);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;		
	}
	
	/**
     * Búsqueda de titulos en castellano por código o nombre
     * @author cetorres
     * 
     * @useCase FP210 - Ingreso de materiales
     */
	public String buscarTitulosCastellanoParaMateriales() throws Exception {
		try {
			titulosMateriales = ingresarMaterialesService.buscarTitulosCastellanoParaMateriales(senial, tipoMaterial, codigoDistribuidor, descripcionTitulo);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;		
	}
	
	/**
     * Búsqueda de titulos original por código o nombre
     * @author cetorres
     * 
     * @useCase FP210 - Ingreso de materiales
     */
	public String buscarTitulosOriginalParaMateriales() throws Exception {
		try {
			titulosMateriales = ingresarMaterialesService.buscarTitulosOriginalParaMateriales(senial, tipoMaterial, codigoDistribuidor, descripcionTitulo);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;		
	}
	
	/**
     * Búsqueda que determina la familia de un título seleccionado
     * @author cetorres
     * 
     * @useCase FP210 - Ingreso de materiales
     */
	public String determinarFamiliaTitulo() throws Exception {
		try {
			familiaTitulo = ingresarMaterialesService.determinarFamiliaTitulo(tipoTituloSeleccionado);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;		
	}

	/**
     * Alta de soporte de un título
     * @author cetorres
     * 
     * @useCase FP210 - Ingreso de materiales
     */
	public String altaSoporteTitulo() throws Exception {
		try {
			ingresarMaterialesService.altaSoporteTitulo(soporteTitulo);
			if (StringUtils.isEmpty(soporteTitulo.getErrores())) {
				responseAltaRemito = SUCCESS;
			} else {
				message = soporteTitulo.getErrores();
			}
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;		
	}
	
	/**
     * Búsqueda de soportes por código o nombre
     * @author cetorres
     * 
     * @useCase FP210 - Ingreso de materiales
     */
	public String buscarSoportesParaMateriales() throws Exception {
		try {
			soportes = ingresarMaterialesService.buscarSoportes(codigoSoporte);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;		
	}
	
}