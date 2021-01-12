package com.artear.filmo.daos.impl.pl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.artear.filmo.bo.trabajar.titulos.TituloAmortizadoGrillaResponse;
import com.artear.filmo.daos.interfaces.TrabajarConTitulosAmortizadosDao;

/**
 * 
 * @author flvaldes
 * 
 */
@Repository("trabajarConTitulosAmortizadosDao")
public class TrabajarConTitulosAmortizadosDaoPL implements TrabajarConTitulosAmortizadosDao {

    private SimpleJdbcCall buscarTitulosAmortizadosCastCall;
    private SimpleJdbcCall buscarTitulosAmortizadosOriginalCall;
    private SimpleJdbcCall activarTituloCall;
    private SimpleJdbcCall fueContabilizadoCall;

    @Autowired
    public TrabajarConTitulosAmortizadosDaoPL(DataSource dataSource) {
        super();
        this.buscarTitulosAmortizadosCastCall = buildBuscarTitulosCall(dataSource, "pr_grillaFP19001");
        this.buscarTitulosAmortizadosOriginalCall = buildBuscarTitulosCall(dataSource, "pr_grillaFP19002");
        this.fueContabilizadoCall = buildFunctionCall(dataSource, "pr_habilitaFP19003");
        this.activarTituloCall = buildFunctionCall(dataSource, "pr_confirmaFP19003");
    }

    private SimpleJdbcCall buildFunctionCall(DataSource dataSource, String procedureName) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_FP190").withProcedureName(procedureName).returningResultSet("P_CURSOR", new RowMapper<Boolean>() {
            @Override
            public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getBoolean("VALOR");
            }
        });
    }

    private SimpleJdbcCall buildBuscarTitulosCall(DataSource dataSource, String procedureName) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_FP190").withProcedureName(procedureName).returningResultSet("P_CURSOR", new RowMapper<TituloAmortizadoGrillaResponse>() {
            @Override
            public TituloAmortizadoGrillaResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                TituloAmortizadoGrillaResponse titulo = new TituloAmortizadoGrillaResponse();
                titulo.setClave(rs.getString("CLAVE"));
                titulo.setTitulo(rs.getString("TITULO"));
                titulo.setContrato(rs.getString("CONTRATO"));
                titulo.setDistribuidor(rs.getString("DISTRIBUIDOR"));
                titulo.setPer(rs.getString("PER"));
                titulo.setRec(rs.getString("REC"));
                titulo.setPorAmortizar(rs.getBigDecimal("PORAMORTIZAR"));
                return titulo;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public List<TituloAmortizadoGrillaResponse> obtenerTitulosAmortizadosCastellanoPorDescripcion(String descripcion, String senial) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("p_descripcion", descripcion).addValue("p_senial", senial).addValue("p_clave", null);
        Map<String, Object> out = this.buscarTitulosAmortizadosCastCall.execute(in);
        return (List<TituloAmortizadoGrillaResponse>) out.get("P_CURSOR");
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TituloAmortizadoGrillaResponse> obtenerTitulosAmortizadosOriginalPorDescripcion(String descripcion, String senial) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("p_descripcion", descripcion).addValue("p_senial", senial).addValue("p_clave", null);
        Map<String, Object> out = this.buscarTitulosAmortizadosOriginalCall.execute(in);
        return (List<TituloAmortizadoGrillaResponse>) out.get("P_CURSOR");
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TituloAmortizadoGrillaResponse> obtenerTitulosAmortizadosCastellanoPorCodigo(String codigo, String senial) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("p_descripcion", null).addValue("p_senial", senial).addValue("p_clave", codigo);
        Map<String, Object> out = this.buscarTitulosAmortizadosCastCall.execute(in);
        return (List<TituloAmortizadoGrillaResponse>) out.get("P_CURSOR");
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TituloAmortizadoGrillaResponse> obtenerTitulosAmortizadosOriginalPorCodigo(String codigo, String senial) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("p_descripcion", null).addValue("p_senial", senial).addValue("p_clave", codigo);
        Map<String, Object> out = this.buscarTitulosAmortizadosOriginalCall.execute(in);
        return (List<TituloAmortizadoGrillaResponse>) out.get("P_CURSOR");
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Boolean> fueContabilizado(String codigo) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("p_clave", codigo);
        Map<String, Object> out = fueContabilizadoCall.execute(in);
        return (List<Boolean>) out.get("P_CURSOR");
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Boolean> activarTitulo(String codigo) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("p_clave", codigo);
        Map<String, Object> out = activarTituloCall.execute(in);
        return (List<Boolean>) out.get("P_CURSOR");
    }

}
