package com.artear.filmo.web.action;

import static com.artear.filmo.constants.ApplicationConstants.ERROR_INESPERADO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.artear.filmo.bo.common.Capitulo;
import com.artear.filmo.bo.common.Distribuidor;
import com.artear.filmo.bo.common.MotivoIngreso;
import com.artear.filmo.bo.common.Soporte;
import com.artear.filmo.bo.common.Titulo;
import com.artear.filmo.bo.common.ValidarExhibicionesRow;
import com.artear.filmo.bo.ingresar.materiales.CabeceraRemitoABM;
import com.artear.filmo.bo.ingresar.materiales.DesenlaceResponse;
import com.artear.filmo.bo.ingresar.materiales.DetalleRemitoABM;
import com.artear.filmo.bo.ingresar.materiales.RemitoGrillaResponse;
import com.artear.filmo.bo.ingresar.materiales.TituloGrillaResponse;
import com.artear.filmo.bo.ingresar.materiales.ValidarEliminarDetalleRemitoResponse;
import com.artear.filmo.bo.ingresar.materiales.ValidarEliminarRemitoRequest;
import com.artear.filmo.bo.ingresar.materiales.ValidarEliminarRemitoResponse;
import com.artear.filmo.bo.ingresar.materiales.ValidarModificarEliminarDetalleRequest;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.services.interfaces.ModificarIngresoMaterialesService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class ModificarIngresoMaterialesAction extends ActionSupport {

	private static final long serialVersionUID = 3326604743597402323L;

	private String message;
	private String warning;
	private Boolean result;

	private List<RemitoGrillaResponse> remitos;
	private List<Distribuidor> distribuidores;
	private List<Titulo> titulos;
	private List<ValidarExhibicionesRow> exhibicionesRow;
	private List<Capitulo> capitulos;

	private List<TituloGrillaResponse> titulosRemito;
	private List<Soporte> soportes;
	private List<MotivoIngreso> motivosIngreso;

	private CabeceraRemitoABM cabeceraRemitoABM;
	private ValidarEliminarRemitoResponse validarEliminarRemitoResponse;
	private ValidarEliminarDetalleRemitoResponse validarEliminarDetalleRemitoResponse;

	private DetalleRemitoABM detalleRemitoABM;
	private ValidarEliminarRemitoRequest validarEliminarRemitoRequest;
	private ValidarModificarEliminarDetalleRequest validarModificarEliminarDetalleRequest;

	private String numeroRemito;
	private String numeroGuia;
	private Integer codigoDistribuidor;
	private String razonSocialDistribuidor;
	private Date fecha;

	private BigDecimal idRemito;
	private String senial;
	private String tipoMaterial;

	private String descripcionTituloOrig;
	private String descripcionTituloCast;
	private String clave;
	private Integer capitulo;
	private Integer parte;
	private String codigoSoporte;
	private String codigoMotivoIngreso;
	private String descripcionMotivoIngreso;

	@Autowired
	private ModificarIngresoMaterialesService modificarIngresoMaterialesService;

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

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public List<RemitoGrillaResponse> getRemitos() {
		return remitos;
	}

	public void setRemitos(List<RemitoGrillaResponse> remitos) {
		this.remitos = remitos;
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

	public List<TituloGrillaResponse> getTitulosRemito() {
		return titulosRemito;
	}

	public void setTitulosRemito(List<TituloGrillaResponse> titulosRemito) {
		this.titulosRemito = titulosRemito;
	}

	public List<Soporte> getSoportes() {
		return soportes;
	}

	public void setSoportes(List<Soporte> soportes) {
		this.soportes = soportes;
	}

	public List<MotivoIngreso> getMotivosIngreso() {
		return motivosIngreso;
	}

	public void setMotivosIngreso(List<MotivoIngreso> motivosIngreso) {
		this.motivosIngreso = motivosIngreso;
	}

	public CabeceraRemitoABM getCabeceraRemitoABM() {
		return cabeceraRemitoABM;
	}

	public void setCabeceraRemitoABM(CabeceraRemitoABM cabeceraRemitoABM) {
		this.cabeceraRemitoABM = cabeceraRemitoABM;
	}

	public ValidarEliminarRemitoResponse getValidarEliminarRemitoResponse() {
		return validarEliminarRemitoResponse;
	}

	public void setValidarEliminarRemitoResponse(
			ValidarEliminarRemitoResponse validarEliminarRemitoResponse) {
		this.validarEliminarRemitoResponse = validarEliminarRemitoResponse;
	}

	public ValidarEliminarDetalleRemitoResponse getValidarEliminarDetalleRemitoResponse() {
		return validarEliminarDetalleRemitoResponse;
	}

	public void setValidarEliminarDetalleRemitoResponse(
			ValidarEliminarDetalleRemitoResponse validarEliminarDetalleRemitoResponse) {
		this.validarEliminarDetalleRemitoResponse = validarEliminarDetalleRemitoResponse;
	}

	public DetalleRemitoABM getDetalleRemitoABM() {
		return detalleRemitoABM;
	}

	public void setDetalleRemitoABM(DetalleRemitoABM detalleRemitoABM) {
		this.detalleRemitoABM = detalleRemitoABM;
	}

	public ValidarEliminarRemitoRequest getValidarEliminarRemitoRequest() {
		return validarEliminarRemitoRequest;
	}

	public void setValidarEliminarRemitoRequest(
			ValidarEliminarRemitoRequest validarEliminarRemitoRequest) {
		this.validarEliminarRemitoRequest = validarEliminarRemitoRequest;
	}

	public ValidarModificarEliminarDetalleRequest getValidarModificarEliminarDetalleRequest() {
		return validarModificarEliminarDetalleRequest;
	}

	public void setValidarModificarEliminarDetalleRequest(
			ValidarModificarEliminarDetalleRequest validarModificarEliminarDetalleRequest) {
		this.validarModificarEliminarDetalleRequest = validarModificarEliminarDetalleRequest;
	}

	public String getNumeroRemito() {
		return numeroRemito;
	}

	public void setNumeroRemito(String numeroRemito) {
		this.numeroRemito = numeroRemito;
	}

	public String getNumeroGuia() {
		return numeroGuia;
	}

	public void setNumeroGuia(String numeroGuia) {
		this.numeroGuia = numeroGuia;
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getIdRemito() {
		return idRemito;
	}

	public void setIdRemito(BigDecimal idRemito) {
		this.idRemito = idRemito;
	}

	public String getSenial() {
		return senial;
	}

	public void setSenial(String senial) {
		this.senial = senial;
	}

	public String getTipoMaterial() {
		return tipoMaterial;
	}

	public void setTipoMaterial(String tipoMaterial) {
		this.tipoMaterial = tipoMaterial;
	}

	public String getDescripcionTituloOrig() {
		return descripcionTituloOrig;
	}

	public void setDescripcionTituloOrig(String descripcionTituloOrig) {
		this.descripcionTituloOrig = descripcionTituloOrig;
	}

	public String getDescripcionTituloCast() {
		return descripcionTituloCast;
	}

	public void setDescripcionTituloCast(String descripcionTituloCast) {
		this.descripcionTituloCast = descripcionTituloCast;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Integer getCapitulo() {
		return capitulo;
	}

	public void setCapitulo(Integer capitulo) {
		this.capitulo = capitulo;
	}

	public Integer getParte() {
		return parte;
	}

	public void setParte(Integer parte) {
		this.parte = parte;
	}

	public String getCodigoSoporte() {
		return codigoSoporte;
	}

	public void setCodigoSoporte(String codigoSoporte) {
		this.codigoSoporte = codigoSoporte;
	}

	public String getCodigoMotivoIngreso() {
		return codigoMotivoIngreso;
	}

	public void setCodigoMotivoIngreso(String codigoMotivoIngreso) {
		this.codigoMotivoIngreso = codigoMotivoIngreso;
	}

	public String getDescripcionMotivoIngreso() {
		return descripcionMotivoIngreso;
	}

	public void setDescripcionMotivoIngreso(String descripcionMotivoIngreso) {
		this.descripcionMotivoIngreso = descripcionMotivoIngreso;
	}
	
	

	public List<ValidarExhibicionesRow> getExhibicionesRow() {
		return exhibicionesRow;
	}

	public void setExhibicionesRow(List<ValidarExhibicionesRow> exhibicionesRow) {
		this.exhibicionesRow = exhibicionesRow;
	}

	/**
	 * Búsqueda de remitos por número de remito
	 * @author cetorres
	 * @useCase FP220 - Modificación ingreso de materiales
	 */
	public String buscarRemitosPorNumeroRemito() {
		try {
			remitos = this.modificarIngresoMaterialesService.buscarRemitosPorNumeroRemito(numeroRemito, senial);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	/**
	 * Búsqueda de remitos por número de guía
	 * @author cetorres
	 * @useCase FP220 - Modificación ingreso de materiales
	 */
	public String buscarRemitosPorGuia() {
		try {
			remitos = this.modificarIngresoMaterialesService.buscarRemitosPorGuia(numeroGuia, senial);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	/**
	 * Búsqueda de remitos por razon social del proveedor
	 * @author cetorres
	 * @useCase FP220 - Modificación ingreso de materiales
	 */
	public String buscarRemitosPorRazonSocial() {
		try {
			remitos = this.modificarIngresoMaterialesService.buscarRemitosPorRazonSocial(razonSocialDistribuidor, clave, capitulo, parte, senial);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	/**
	 * Búsqueda de remitos por fecha
	 * @author cetorres
	 * @useCase FP220 - Modificación ingreso de materiales
	 */
	public String buscarRemitosPorFecha() {
		try {
			remitos = this.modificarIngresoMaterialesService.buscarRemitosPorFecha(fecha, senial);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	/**
	 * Búsqueda de distribuidores por código o nombre
	 * @author cetorres
	 * @useCase FP220 - Modificación ingreso de materiales
	 */
	public String buscarDistribuidoresParaRemitos() throws Exception {
		try {
			distribuidores = this.modificarIngresoMaterialesService
					.buscarDistribuidoresParaRemitos(codigoDistribuidor, razonSocialDistribuidor);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	/**
	 * Situar títulos para búsqueda de remitos
	 * @author cetorres
	 * @useCase FP220 - Modificación ingreso de materiales
	 */
	public String buscarTitulosPorDescripcion() throws Exception {
		try {
			titulos = this.modificarIngresoMaterialesService.buscarTitulosPorDescripcion(
					descripcionTituloCast, descripcionTituloOrig);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	/**
	 * Situar capítulos para búsqueda de remitos
	 * @author cetorres
	 * @useCase FP220 - Modificación ingreso de materiales
	 */
	public String buscarCapitulos() throws Exception {
		try {
			capitulos = this.modificarIngresoMaterialesService.buscarCapitulos(clave);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	/**
	 * Situar partes para búsqueda de remitos
	 * @author cetorres
	 * @useCase FP220 - Modificación ingreso de materiales
	 */
	public String buscarPartes() throws Exception {
		try {
			capitulos = this.modificarIngresoMaterialesService.buscarPartes(clave, capitulo);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	/**
	 * Valida si no hay exhibiciones confirmadas para el material
	 * @author cetorres
	 * @useCase FP220 - Modificación ingreso de materiales
	 */
	public String validarExhibiciones() throws Exception {
		try {
			exhibicionesRow = this.modificarIngresoMaterialesService.validarExhibiciones(
					codigoDistribuidor, senial, idRemito.toPlainString(), numeroGuia);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	/**
	 * Modificacion de la cabecera del remito
	 * @author cetorres
	 * @useCase FP220 - Modificación ingreso de materiales
	 */
	public String modificarCabeceraRemito() throws Exception {
		try {
			Boolean result = this.modificarIngresoMaterialesService
					.modificarCabeceraRemito(cabeceraRemitoABM);
			if (!result) {
				message = ERROR_INESPERADO;
			}
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	/**
	 * Validaciones inherentes a la baja del remito
	 * @author cetorres
	 * @useCase FP220 - Modificación ingreso de materiales
	 */
	public String validarEliminarRemito() throws Exception {
		try {
			validarEliminarRemitoResponse = this.modificarIngresoMaterialesService
					.validarEliminarRemito(validarEliminarRemitoRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String validarContabilizadoCabeceraRemito() throws Exception {
		try {
			validarEliminarRemitoResponse = this.modificarIngresoMaterialesService
					.validarContabilizadoCabeceraRemito(validarEliminarRemitoRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	/**
	 * Confirmación de desenlace para el remito.
	 * @author cetorres
	 * @useCase FP220 - Modificación ingreso de materiales
	 */
	public String validarDesenlaceRemito() throws Exception {
		try {
			DesenlaceResponse response = this.modificarIngresoMaterialesService
					.validarDesenlaceRemito(idRemito);
			if (response.isHayErrores()) {
				message = "Hay errores por desenlazar el título-contrato.";
			} else if (response.getErrores() != null && response.getErrores().size() > 0) {
				warning = "Hay errores por desenlazar el título-contrato. ¿Desea continuar?";
			}
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	/**
	 * Baja del remito
	 * @author cetorres
	 * @useCase FP220 - Modificación ingreso de materiales
	 */
	public String eliminarRemito() throws Exception {
		try {
			Boolean result = this.modificarIngresoMaterialesService.eliminarRemito(idRemito);
			if (!result) {
				message = ERROR_INESPERADO;
			}
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	/**
	 * Búsqueda de títulos por remito
	 * @author cetorres
	 * @useCase FP220 - Modificación ingreso de materiales
	 */
	public String buscarTitulosRemito() throws Exception {
		try {
			titulosRemito = this.modificarIngresoMaterialesService.buscarTitulosRemito(idRemito,
					descripcionTituloCast, descripcionTituloOrig, tipoMaterial, senial);
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
	 * @useCase FP210 - Ingreso de materiales
	 */
	public String buscarSoportesParaRemitos() throws Exception {
		try {
			soportes = this.modificarIngresoMaterialesService.buscarSoportes(codigoSoporte);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String validarSoporte() throws Exception {
		try {
			result = this.modificarIngresoMaterialesService.validarSoporte(codigoSoporte);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	/**
	 * Búsqueda de motivos de ingreso por código o nombre
	 * @author cetorres
	 * @useCase FP210 - Ingreso de materiales
	 */
	public String buscarMotivosIngresoParaRemitos() throws Exception {
		try {
			motivosIngreso = this.modificarIngresoMaterialesService
					.buscarMotivosIngreso(descripcionMotivoIngreso);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String validarMotivoIngreso() throws Exception {
		try {
			result = this.modificarIngresoMaterialesService
					.validarMotivoIngreso(codigoMotivoIngreso);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String validarModificarDetalleRemito() throws Exception {
		try {
			warning = this.modificarIngresoMaterialesService
					.validarModificarDetalleRemito(validarModificarEliminarDetalleRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	/**
	 * Modificacion del detalle del remito
	 * @author cetorres
	 * @useCase FP220 - Modificación ingreso de materiales
	 */
	public String modificarDetalleRemito() throws Exception {
		try {
			Boolean result = this.modificarIngresoMaterialesService
					.modificarDetalleRemito(detalleRemitoABM);
			if (!result) {
				message = ERROR_INESPERADO;
			}
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String validarEliminarDetalleRemito() throws Exception {
		try {
			validarEliminarDetalleRemitoResponse = this.modificarIngresoMaterialesService
					.validarEliminarDetalleRemito(validarModificarEliminarDetalleRequest);
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	/**
	 * Confirmación de desenlace de contrato.
	 * @author cetorres
	 * @useCase FP220 - Modificación ingreso de materiales
	 */
	public String chequeoEliminarRemitoDesde() throws Exception {
		try {
			DesenlaceResponse response = this.modificarIngresoMaterialesService
					.desenlace(validarModificarEliminarDetalleRequest);
			if (response.isHayErrores()) {
				message = "Hay errores por desenlazar el título-contrato. ¿Desea generar un listado con las exhibiciones que habría que desconfirmar?";
			} else if (response.getErrores() != null && response.getErrores().size() > 0) {
				warning = "Hay errores por desenlazar el título-contrato. ¿Desea generar un listado con las exhibiciones que quedan sin contrato?";
			}
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String eliminarDetalleRemito() throws Exception {
		try {
			Boolean ok = this.modificarIngresoMaterialesService
					.eliminarDetalleRemito(validarModificarEliminarDetalleRequest);
			if (!ok) {
				message = "No se pudo eliminar el detalle del remito";
			}
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

	public String eliminarDetalleRemitoDesde() throws Exception {
		try {
			Boolean ok = this.modificarIngresoMaterialesService
					.eliminarDetalleRemitoDesde(validarModificarEliminarDetalleRequest);
			if (!ok) {
				message = "No se pudo eliminar el detalle del remito";
			}
		} catch (DataBaseException e) {
			message = e.getError().getMensaje();
		} catch (Exception e) {
			message = ERROR_INESPERADO;
		}
		return SUCCESS;
	}

}