package com.artear.filmo.web.action;

import static com.artear.filmo.constants.ApplicationConstants.ERROR_INESPERADO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.artear.filmo.bo.common.Capitulo;
import com.artear.filmo.bo.common.Distribuidor;
import com.artear.filmo.bo.common.Titulo;
import com.artear.filmo.bo.trabajar.remitos.ActualizarCantidadRequest;
import com.artear.filmo.bo.trabajar.remitos.EjecutarRemitoRequest;
import com.artear.filmo.bo.trabajar.remitos.EjecutarRemitoResponse;
import com.artear.filmo.bo.trabajar.remitos.ImprimirRemitoRequest;
import com.artear.filmo.bo.trabajar.remitos.ImprimirRemitoResponse;
import com.artear.filmo.bo.trabajar.remitos.ValidarNuevaSenialRequest;
import com.artear.filmo.bo.trabajar.remitos.ValidarNuevaSenialResponse;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.seguridad.userdetails.ExtUserDetails;
import com.artear.filmo.services.interfaces.GenerarReportesService;
import com.artear.filmo.services.interfaces.TrabajarConRemitosMaterialesService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class TrabajarConRemitosMaterialesAction extends ActionSupport {

	private static final long serialVersionUID = -6811034549880982457L;

	private String message;
	private String warning;
	
	private List<Distribuidor> distribuidores;
	private List<Titulo> titulos;
	private List<Capitulo> capitulos;
	private ValidarNuevaSenialResponse validarNuevaSenialResponse;
	private EjecutarRemitoResponse ejecutarRemitoResponse;
	private List<ImprimirRemitoResponse> imprimirRemitoResponse;
	private Integer capitulo;
	private Integer codigoDistribuidor;
	private String razonSocialDistribuidor;
	private String descripcionTitulo;
	private String claveTitulo;
	private String senial;
	private Date fechaRemito;
	private ValidarNuevaSenialRequest validarNuevaSenialRequest;
	private ActualizarCantidadRequest actualizarCantidadRequest;
	private EjecutarRemitoRequest ejecutarRemitoRequest;
	private ImprimirRemitoRequest imprimirRemitoRequest;
	
	@Autowired
	private GenerarReportesService generarReportesService;
	
	@Autowired
	private TrabajarConRemitosMaterialesService trabajarConRemitosMaterialesService;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getWarning() {
		return warning;
	}
	
	public void setWarning(String warning) {
		this.warning = warning;
	}
	
	public List<Distribuidor> getDistribuidores() {
		return distribuidores;
	}
	
	public void setDistribuidores(List<Distribuidor> distribuidores) {
		this.distribuidores = distribuidores;
	}
	
	public List<Titulo> getTitulos() {
		return titulos;
	}

	public void setTitulos(List<Titulo> titulos) {
		this.titulos = titulos;
	}

	public List<Capitulo> getCapitulos() {
		return capitulos;
	}

	public void setCapitulos(List<Capitulo> capitulos) {
		this.capitulos = capitulos;
	}
	
	public ValidarNuevaSenialResponse getValidarNuevaSenialResponse() {
		return validarNuevaSenialResponse;
	}
	
	public void setValidarNuevaSenialResponse(ValidarNuevaSenialResponse validarNuevaSenialResponse) {
		this.validarNuevaSenialResponse = validarNuevaSenialResponse;
	}
	
	public EjecutarRemitoResponse getEjecutarRemitoResponse() {
		return ejecutarRemitoResponse;
	}
	
	public void setEjecutarRemitoResponse(EjecutarRemitoResponse ejecutarRemitoResponse) {
		this.ejecutarRemitoResponse = ejecutarRemitoResponse;
	}
	
	public List<ImprimirRemitoResponse> getImprimirRemitoResponse() {
		return imprimirRemitoResponse;
	}
	
	public void setImprimirRemitoResponse(List<ImprimirRemitoResponse> imprimirRemitoResponse) {
		this.imprimirRemitoResponse = imprimirRemitoResponse;
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
	
	public String getClaveTitulo() {
		return claveTitulo;
	}
	
	public void setClaveTitulo(String claveTitulo) {
		this.claveTitulo = claveTitulo;
	}
	
	public String getSenial() {
		return senial;
	}

	public void setSenial(String senial) {
		this.senial = senial;
	}

	public Date getFechaRemito() {
		return fechaRemito;
	}

	public void setFechaRemito(Date fechaRemito) {
		this.fechaRemito = fechaRemito;
	}
	
	public ValidarNuevaSenialRequest getValidarNuevaSenialRequest() {
		return validarNuevaSenialRequest;
	}
	
	public void setValidarNuevaSenialRequest(ValidarNuevaSenialRequest validarNuevaSenialRequest) {
		this.validarNuevaSenialRequest = validarNuevaSenialRequest;
	}

	public ActualizarCantidadRequest getActualizarCantidadRequest() {
		return actualizarCantidadRequest;
	}
	
	public void setActualizarCantidadRequest(ActualizarCantidadRequest actualizarCantidadRequest) {
		this.actualizarCantidadRequest = actualizarCantidadRequest;
	}
	
	public EjecutarRemitoRequest getEjecutarRemitoRequest() {
		return ejecutarRemitoRequest;
	}
	
	public void setEjecutarRemitoRequest(EjecutarRemitoRequest ejecutarRemitoRequest) {
		this.ejecutarRemitoRequest = ejecutarRemitoRequest;
	}
	
	public ImprimirRemitoRequest getImprimirRemitoRequest() {
		return imprimirRemitoRequest;
	}
	
	public void setImprimirRemitoRequest(ImprimirRemitoRequest imprimirRemitoRequest) {
		this.imprimirRemitoRequest = imprimirRemitoRequest;
	}

	
	
	public Integer getCapitulo() {
		return capitulo;
	}

	public void setCapitulo(Integer capitulo) {
		this.capitulo = capitulo;
	}

	/**
	 * Generar Reporte de Remito de Salida
	 * @author RGodoy	
	 * 
	 * */
	
	public String generarReporteRemitoSalida() throws Exception {
		try{
			//Parametros de entrada
			ExtUserDetails extUserDetails = (ExtUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("p_remitoInterno", Integer.toString(imprimirRemitoRequest.getIdRemito()));
			String pathPropuesta = System.getProperty("Filmo")+"reportes/remitoSalida/";
			params.put("SUBREPORT_DIR", pathPropuesta);
			params.put("usuario", extUserDetails.getUsername());
			params.put("p_empresa", "1");
			params.put("p_puntoVenta","2");
			params.put("p_tipoComprobante", new String("R"));
			
			String pathReporte = new String("reportes/remitoSalida/remitoSalida.jasper");
			//Nombre del Reporte
			String nombreReporte = "remitoSalida";
			System.out.println("idRemito: "+imprimirRemitoRequest.getIdRemito());
			System.out.println("SUBREPORT_DIR: "+pathPropuesta);
			System.out.println("extUserDetails.getUsername(): "+extUserDetails.getUsername());
			
			generarReportesService.generarReporte(params, pathReporte, nombreReporte); 
		}catch (Exception e){
			message = "Error Generacion de Reporte: " + e.getCause();
		}
		return SUCCESS;
	}
	
	
	/**
     * Búsqueda de distribuidores por razón social
     * @author cetorres
     * 
     * @useCase FP240 - Trabajar con remitos de salida
     */
	public String buscarDistribuidoresParaRemitosSalida() throws Exception {
		try {
			distribuidores = trabajarConRemitosMaterialesService.buscarDistribuidoresParaRemitosSalida(razonSocialDistribuidor);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;		
	}
	
	/**
     * Búsqueda de titulos por distribuidor
     * @author cetorres
     * 
     * @useCase FP240 - Trabajar con remitos de salida
     */
	public String buscarTitulosParaCarga() throws Exception {
		try {
			titulos = trabajarConRemitosMaterialesService.buscarTitulosParaCarga(codigoDistribuidor, descripcionTitulo);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;		
	}
	
	/**
     * Búsqueda de capítulos por título
     * @author cetorres
     * 
     * @useCase FP240 - Trabajar con remitos de salida
     */
	public String buscarCapitulosParaCarga() throws Exception {
		try {
			capitulos = trabajarConRemitosMaterialesService.buscarCapitulosParaCarga(claveTitulo, capitulo);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;		
	}
	
	public String validarDistribuidor() throws Exception {
		try {
			Boolean existeDistribuidor = trabajarConRemitosMaterialesService.validarDistribuidor(codigoDistribuidor);
			if (!existeDistribuidor) {
				warning = "Código de distribuidor inexistente";
			}
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	public String validarFechaSenial() throws Exception {
		try {
			Boolean fechaSenialValida = trabajarConRemitosMaterialesService.validarFechaSenial(fechaRemito, senial);
			if (!fechaSenialValida) {
		        SimpleDateFormat personalFormat = new SimpleDateFormat("dd/MM/yyyy");
		        String timestamp = personalFormat.format(new Date());
				warning = "La fecha de remito debe ser mayor a " + timestamp;
			}
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	public String validarNuevaSenial() throws Exception {
		try {
			validarNuevaSenialResponse = trabajarConRemitosMaterialesService.validarNuevaSenial(validarNuevaSenialRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	public String actualizarCantidad() throws Exception {
		try {
			trabajarConRemitosMaterialesService.actualizarCantidad(actualizarCantidadRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	public String ejecutarRemito() throws Exception {
		try {
			ejecutarRemitoResponse = trabajarConRemitosMaterialesService.ejecutarRemito(ejecutarRemitoRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
	public String imprimirRemito() throws Exception {
		try {
			imprimirRemitoResponse = trabajarConRemitosMaterialesService.imprimirRemito(imprimirRemitoRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}
	
}