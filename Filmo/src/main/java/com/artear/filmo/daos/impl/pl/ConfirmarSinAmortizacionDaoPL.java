package com.artear.filmo.daos.impl.pl;

import java.sql.ResultSet;
import java.sql.SQLException;
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

import com.artear.filmo.bo.programacion.BuscarTitulosProgramasRequest;
import com.artear.filmo.bo.programacion.ConfirmarRequest;
import com.artear.filmo.bo.programacion.ProgramaClaveResponse;
import com.artear.filmo.bo.programacion.ProgramaCodigoResponse;
import com.artear.filmo.bo.programacion.TituloPrograma;
import com.artear.filmo.daos.interfaces.ConfirmarSinAmortizacionDao;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.exceptions.util.ErrorFilmo;

/**
 * 
 * @author mtito
 * 
 */
@Repository("confirmarSinAmortizacionDao")
public class ConfirmarSinAmortizacionDaoPL implements ConfirmarSinAmortizacionDao {

    private SimpleJdbcCall buscarProgramasClaveCall;
    private SimpleJdbcCall buscarProgramasCodigoCall;
    private SimpleJdbcCall listadoParaConfirmarCall;
    private SimpleJdbcCall listadoParaConfirmarFiltroCall;
    private SimpleJdbcCall confirmarCall;

    @Autowired
    public ConfirmarSinAmortizacionDaoPL(DataSource dataSource) {
        super();
        this.buscarProgramasClaveCall = buildBuscarProgramasClave(dataSource);
        this.buscarProgramasCodigoCall = buildBuscarProgramasCodigo(dataSource);
        this.listadoParaConfirmarCall = buildListadoParaConfirmar(dataSource);
        this.listadoParaConfirmarFiltroCall = buildListadoParaConfirmarFiltro(dataSource);
        this.confirmarCall = buildConfirmar(dataSource);
    }

    private SimpleJdbcCall buildListadoParaConfirmarFiltro(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_CONF_SIN_AMORT")
                .withProcedureName("PR_TITULOS_FILTRO")
                .returningResultSet("P_DATOS", new RowMapper<TituloPrograma>() {
            @Override
            public TituloPrograma mapRow(ResultSet rs, int rowNum) throws SQLException {
                TituloPrograma result = new TituloPrograma();
                result.setHora(rs.getString("HORA"));
                result.setPrograma(rs.getInt("PROGRAMA"));
                result.setDescripcion(rs.getString("DESCRIPCION_PROGRAMA"));
                result.setTitulo(rs.getString("TITULO_CASTELLANO"));
                result.setClave(rs.getString("CLAVE"));
                result.setCapitulo(rs.getInt("NRO_CAPITULO"));
                result.setParte(rs.getInt("PARTE"));
                result.setSegmento(rs.getInt("SEGMENTO"));
                result.setFraccion(rs.getInt("FRACCION"));
                result.setDe(rs.getInt("DESDE"));
                result.setOrden(rs.getInt("ORDEN"));
                return result;
            }
        });
    }

    private SimpleJdbcCall buildConfirmar(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_CONF_SIN_AMORT")
                .withProcedureName("PR_CONFIRMAR");
    }

    private SimpleJdbcCall buildListadoParaConfirmar(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_CONF_SIN_AMORT")
                .withProcedureName("PR_TITULOS")
                .returningResultSet("P_DATOS", new RowMapper<TituloPrograma>() {
            @Override
            public TituloPrograma mapRow(ResultSet rs, int rowNum) throws SQLException {
                TituloPrograma result = new TituloPrograma();
                result.setHora(rs.getString("HORA"));
                result.setPrograma(rs.getInt("PROGRAMA"));
                result.setDescripcion(rs.getString("DESCRIPCION_PROGRAMA"));
                result.setTitulo(rs.getString("TITULO_CASTELLANO"));
                result.setClave(rs.getString("CLAVE"));
                result.setCapitulo(rs.getInt("NRO_CAPITULO"));
                result.setParte(rs.getInt("PARTE"));
                result.setSegmento(rs.getInt("SEGMENTO"));
                result.setFraccion(rs.getInt("FRACCION"));
                result.setDe(rs.getInt("DESDE"));
                result.setOrden(rs.getInt("ORDEN"));
                return result;
            }
        });
    }

    private SimpleJdbcCall buildBuscarProgramasCodigo(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_FP39001")
                .withProcedureName("PR_LISTARMAESTROIDPROGRAMA")
                .returningResultSet("P_CURSOR", new RowMapper<ProgramaCodigoResponse>() {
                    @Override
                    public ProgramaCodigoResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ProgramaCodigoResponse programa = new ProgramaCodigoResponse();
                        programa.setCodigo(rs.getInt("ID_PROGRAMA"));
                        programa.setPrograma(rs.getString("DESCRIPCION"));
                        return programa;
                    }
                });
    }

    private SimpleJdbcCall buildBuscarProgramasClave(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_FP39001")
                .withProcedureName("PR_LISTARMAESTROTITULOS")
                .returningResultSet("P_CURSOR", new RowMapper<ProgramaClaveResponse>() {
                    @Override
                    public ProgramaClaveResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ProgramaClaveResponse programa = new ProgramaClaveResponse();
                        programa.setClave(rs.getString("CLAVE"));
                        programa.setTitulo(rs.getString("TITULO_ORIG"));
                        return programa;
                    }
                });
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ProgramaClaveResponse> buscarProgramasClave(String descripcion) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("P_DESCRIPCION", descripcion);
        Map<String, Object> out = buscarProgramasClaveCall.execute(in);
        return (List<ProgramaClaveResponse>) out.get("P_CURSOR");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ProgramaCodigoResponse> buscarProgramasCodigo(String descripcion, String senial) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("P_DESCRIPCION", descripcion)
        		.addValue("P_SENIAL", senial);
        Map<String, Object> out = buscarProgramasCodigoCall.execute(in);
        return (List<ProgramaCodigoResponse>) out.get("P_CURSOR");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TituloPrograma> buscarTitulosProgramas(BuscarTitulosProgramasRequest request) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_FECHA", request.getFecha())
            .addValue("P_SENIAL", request.getSenial());
        boolean sw = false; 
        if (niNuloNiVacio(request.getClave())) {
            sw = true;
            ((MapSqlParameterSource)in).addValue("P_CLAVE", request.getClave());
        }
        else {
        	((MapSqlParameterSource)in).addValue("P_CLAVE", null);
        }
        if (niNuloNiVacio(request.getCodigo())) {
            sw= true;
            ((MapSqlParameterSource)in).addValue("P_PROGRAMA", request.getCodigo());
        }
        else {
        	((MapSqlParameterSource)in).addValue("P_PROGRAMA", null);
        }
        if (sw) {
            return ((List<TituloPrograma>)listadoParaConfirmarFiltroCall.execute(in).get("P_DATOS"));
        } else {
            return ((List<TituloPrograma>)listadoParaConfirmarCall.execute(in).get("P_DATOS"));
        }
    }

    private boolean niNuloNiVacio(Object arg0) {
        boolean ret = arg0 != null;
        if (arg0 instanceof String) {
            ret &= !(StringUtils.isEmpty((String) arg0));
        }
        return ret;
    }

    @Override
    public void confirmar(ConfirmarRequest request) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_CLAVE", request.getClave())
                .addValue("P_SENIAL", request.getSenial())
                .addValue("P_FECHA", request.getFecha())
                .addValue("P_PROGRAMA", request.getPrograma())
                .addValue("P_CAPITULO", request.getNumCapitulo())
                .addValue("P_USUARIO", request.getUsuario());
		Map<String, Object> rtas = confirmarCall.execute(in);
		
		String respuesta = (String)rtas.get("P_OK");
		if(respuesta != null && !respuesta.isEmpty() && "E".equals(respuesta)){
			String mensajeError = (String)rtas.get("P_MENSAJE");
			ErrorFilmo error = new ErrorFilmo(null, mensajeError);
			throw new DataBaseException(error);
		}
    }

}
