package com.artear.filmo.daos.impl.pl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.artear.filmo.bo.contratos.BajaContratoValidationResult;
import com.artear.filmo.daos.interfaces.BajaContratoDao;

@Repository("bajaContratoDao")
public class BajaContratoDaoPL implements BajaContratoDao {

    private SimpleJdbcCall validarBajaContratoCall;
    private SimpleJdbcCall darDeBajaContratoCall;

    @Autowired
    public BajaContratoDaoPL(DataSource dataSource) {
        super();
        this.validarBajaContratoCall = buildBajaContratoCall(dataSource);
        this.darDeBajaContratoCall = buildDarDeBajaContratoCall(dataSource);
    }

    private SimpleJdbcCall buildBajaContratoCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_BAJA_CONTRATO").withProcedureName("PR_VALIDACION_BAJA_CONTRATO")
                .returningResultSet("P_CURSOR_ERRORES", new RowMapper<String>() {
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString(1);
                    }
                });
    }

    private SimpleJdbcCall buildDarDeBajaContratoCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_BAJA_CONTRATO").withProcedureName("PR_ELIMINAR");
    }
    
    @Override
    public void elimiarContrato(Integer contrato) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", contrato);
        this.darDeBajaContratoCall.execute(in);
    }
    
    @SuppressWarnings({ "unchecked" })
    @Override
    public List<BajaContratoValidationResult> validarBajaContrato(Integer contrato) {
        List<BajaContratoValidationResult> ret = new ArrayList<BajaContratoValidationResult>();

        SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", contrato);
        
        Map<String, Object> out = validarBajaContratoCall.execute(in);
        
        String tipoDeError = (String)out.get("P_TIPO_ERROR");
        String mensaje = (String)out.get("P_MENSAJE");
        BigDecimal idReporte = (BigDecimal)out.get("P_ID_REPORTE");
        
        List<String> cursorDeErrores;
        if (out.get("P_CURSOR_ERRORES") != null) {
            cursorDeErrores = (List<String>) out.get("P_CURSOR_ERRORES");
        } else {
            cursorDeErrores = new ArrayList<String>();
        }
        
        if (StringUtils.equals(tipoDeError, "E")) {
            BajaContratoValidationResult bajaContratoValidationResult = new BajaContratoValidationResult();
            bajaContratoValidationResult.setIdReporte(idReporte);
            bajaContratoValidationResult.setDescripcion(mensaje);
            bajaContratoValidationResult.setTipo("E");
            ret.add(bajaContratoValidationResult);
        } else if (StringUtils.equals(tipoDeError, "W")) {
            BajaContratoValidationResult bajaContratoValidationResult = new BajaContratoValidationResult();
            bajaContratoValidationResult.setIdReporte(idReporte);
            bajaContratoValidationResult.setDescripcion(mensaje);
            bajaContratoValidationResult.setTipo("W");
            ret.add(bajaContratoValidationResult);
        } else if (StringUtils.equals(tipoDeError, "ED")) {
            for (String msj : cursorDeErrores) {
                BajaContratoValidationResult bajaContratoValidationResult = new BajaContratoValidationResult();
                bajaContratoValidationResult.setDescripcion(msj);
                bajaContratoValidationResult.setTipo("ED");
                ret.add(bajaContratoValidationResult);
            }
        } else if (StringUtils.equals(tipoDeError, "WD")) {
            for (String msj : cursorDeErrores) {
                BajaContratoValidationResult bajaContratoValidationResult = new BajaContratoValidationResult();
                bajaContratoValidationResult.setDescripcion(msj);
                bajaContratoValidationResult.setTipo("WD");
                ret.add(bajaContratoValidationResult);
            }
        }
        return ret;
    }
    
}
