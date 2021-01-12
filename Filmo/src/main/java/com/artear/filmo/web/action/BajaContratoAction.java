package com.artear.filmo.web.action;

import static com.artear.filmo.constants.ApplicationConstants.ERROR_INESPERADO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.artear.filmo.bo.contratos.BajaContratoValidationResult;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.seguridad.services.ServiciosSesionUsuario;
import com.artear.filmo.seguridad.userdetails.ExtUserDetails;
import com.artear.filmo.services.interfaces.BajaContratoService;
import com.artear.filmo.services.interfaces.GenerarReportesService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class BajaContratoAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    //IN
    private Integer contrato;
    private String senial;
    private Integer grupo;
    
    //OUT
    private List<BajaContratoValidationResult> bajaContratoValidationResult;
    private boolean validationResult;
    private boolean bajaContratoResult;
    private String message;
    
    @Autowired
	private GenerarReportesService generarReportesService;
    
    @Autowired
    private BajaContratoService bajaContratoService;

    @Autowired
    private ServiciosSesionUsuario serviciosSesionUsuario;

    public String validarBajaContrato() {
        try {
            bajaContratoValidationResult = bajaContratoService.validarBajaContrato(contrato);
            if (bajaContratoValidationResult != null && !bajaContratoValidationResult.isEmpty()){
            	if (!bajaContratoValidationResult.get(0).getIdReporte().equals(-1))
            		generarReporteDeBajaDeContratos(bajaContratoValidationResult.get(0).getIdReporte());
            }
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
        } catch (Exception e) {
            message = ERROR_INESPERADO;
        }
        return SUCCESS;
    }
    
    private void generarReporteDeBajaDeContratos(BigDecimal idReporte) throws SQLException, JRException, Exception {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("idListado", " ");
    	params.put("p_id", idReporte);
    	ExtUserDetails extUserDetails = (ExtUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	params.put("usuario", extUserDetails.getUsername());
		String pathFilmo = System.getProperty("Filmo")+"reportes/bajaContrato/";
		params.put("SUBREPORT_DIR", pathFilmo);
		String pathReporte = new String("reportes/bajaContrato/reporteErroresBajaContrato.jasper");
		String nombreReporte = "reporteErroresBajaContrato";
		generarReportesService.generarReporte(params, pathReporte, nombreReporte); 
		
	}

	public String eliminarContrato() {
        try {
            bajaContratoService.eliminarContrato(contrato);
        } catch (DataBaseException e) {
            message = e.getError().getMensaje();
            return ERROR;
        } catch (Exception e) {
            message = ERROR_INESPERADO;
            return ERROR;
        }
        return SUCCESS;
    }
    
    public void setContrato(Integer contrato) {
        this.contrato = contrato;
    }
    
    public Integer getContrato() {
        return this.contrato;
    }
    
    public void setSenial(String senial) {
        this.senial = senial;
    }
    
    public String getSenial() {
        return this.senial;
    }
    
    public void setGrupo(Integer grupo) {
        this.grupo = grupo;
    }
    
    public Integer getGrupo() {
        return this.grupo;
    }
    
    
    public void setValidationResult(boolean validationResult) {
        this.validationResult = validationResult;
    }
    
    public boolean getValidationResult() {
        return this.validationResult;
    }
    
    public void setBajaContratoResult(boolean bajaContratoResult) {
        this.bajaContratoResult = bajaContratoResult;
    }
    
    public boolean getBajaContratoResult() {
        return this.bajaContratoResult;
    }

    public List<BajaContratoValidationResult> getBajaContratoValidationResult() {
        return bajaContratoValidationResult;
    }

    public void setBajaContratoValidationResult(List<BajaContratoValidationResult> bajaContratoValidationResult) {
        this.bajaContratoValidationResult = bajaContratoValidationResult;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
