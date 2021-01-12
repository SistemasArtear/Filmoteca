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

import com.artear.filmo.bo.programacion.BuscarTitulosProgramasRequest;
import com.artear.filmo.bo.programacion.DesconfirmarRequest;
import com.artear.filmo.bo.programacion.ProgramaClaveResponse;
import com.artear.filmo.bo.programacion.ProgramaCodigoResponse;
import com.artear.filmo.bo.programacion.TituloPrograma;
import com.artear.filmo.daos.interfaces.DesconfirmarSinAmortizacionDao;

/**
 * 
 * @author flvaldes
 * 
 */
@Repository("desconfirmarSinAmortizacionDao")
public class DesconfirmarSinAmortizacionDaoPL implements
		DesconfirmarSinAmortizacionDao {

	private SimpleJdbcCall buscarProgramasClaveCall;
	private SimpleJdbcCall buscarProgramasCodigoCall;
	private SimpleJdbcCall listadoParaDesconfirmarCall;
	private SimpleJdbcCall desconfirmarCall;

	@Autowired
	public DesconfirmarSinAmortizacionDaoPL(DataSource dataSource) {
		super();
		this.buscarProgramasClaveCall = buildBuscarProgramasClave(dataSource);
		this.buscarProgramasCodigoCall = buildBuscarProgramasCodigo(dataSource);
		this.listadoParaDesconfirmarCall = buildListadoParaDesconfirmar(dataSource);
		this.desconfirmarCall = buildDesconfirmar(dataSource);
	}

	private SimpleJdbcCall buildDesconfirmar(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_FP39001").withProcedureName(
						"pr_Desconfirmar");
	}

	private SimpleJdbcCall buildListadoParaDesconfirmar(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_FP39001")
				.withProcedureName("pr_IngresoValidacionFecha")
				.returningResultSet("P_CURSOR",
						new RowMapper<TituloPrograma>() {
							@Override
							public TituloPrograma mapRow(ResultSet rs,
									int rowNum) throws SQLException {
								TituloPrograma result = new TituloPrograma();
								result.setCapitulo(rs.getInt("NUM_DE_CAPITULO"));
								result.setDe(rs.getInt("DE"));
								result.setFraccion(rs.getInt("FRACCION"));
								result.setHoraDesde(rs.getInt("HORA_DESDE"));
								result.setOrden(rs.getInt("ORDEN"));
								result.setParte(rs.getInt("PARTE"));
								result.setPrograma(rs.getInt("ID_PROGRAMA"));
								result.setSegmento(rs.getInt("SEGMENTO"));
								result.setContrato(rs.getInt("CONTRATO"));
								result.setClave(rs.getString("CLAVE"));
								result.setTitulo(rs
										.getString("TITULO_ORIGINAL"));
								result.setDescripcion(rs.getString("DESCRIP"));
								return result;
							}
						});
	}

	private SimpleJdbcCall buildBuscarProgramasCodigo(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_FP39001")
				.withProcedureName("pr_ListarMaestroIDPrograma")
				.returningResultSet("P_CURSOR",
						new RowMapper<ProgramaCodigoResponse>() {
							@Override
							public ProgramaCodigoResponse mapRow(ResultSet rs,
									int rowNum) throws SQLException {
								ProgramaCodigoResponse programa = new ProgramaCodigoResponse();
								programa.setCodigo(rs.getInt("ID_PROGRAMA"));
								programa.setPrograma(rs
										.getString("DESCRIPCION"));
								return programa;
							}
						});
	}

	private SimpleJdbcCall buildBuscarProgramasClave(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_FP39001")
				.withProcedureName("pr_ListarMaestroTitulos")
				.returningResultSet("P_CURSOR",
						new RowMapper<ProgramaClaveResponse>() {
							@Override
							public ProgramaClaveResponse mapRow(ResultSet rs,
									int rowNum) throws SQLException {
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
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"P_DESCRIPCION", descripcion);
		Map<String, Object> out = buscarProgramasClaveCall.execute(in);
		return (List<ProgramaClaveResponse>) out.get("P_CURSOR");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProgramaCodigoResponse> buscarProgramasCodigo(String descripcion, String senial) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"P_DESCRIPCION", descripcion)
				.addValue("P_SENIAL", senial);
		Map<String, Object> out = buscarProgramasCodigoCall.execute(in);
		return (List<ProgramaCodigoResponse>) out.get("P_CURSOR");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TituloPrograma> buscarTitulosProgramas(
			BuscarTitulosProgramasRequest request) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("P_FECHA", request.getFecha())
				.addValue("P_SENIAL", request.getSenial())
				.addValue("P_ID_PROGRAMA", request.getCodigo())
				.addValue("P_CLAVE", request.getClave());
		Map<String, Object> out = listadoParaDesconfirmarCall.execute(in);
		return (List<TituloPrograma>) out.get("P_CURSOR");
	}

	@Override
	public void desconfirmar(DesconfirmarRequest request) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("P_CLAVE", request.getClave())
				.addValue("P_SENIAL", request.getSenial())
				.addValue("P_ID_PROGRAMA", request.getPrograma())
				.addValue("P_FECHA", request.getFecha())
				.addValue("P_NUM_CAPITULO", request.getNumCapitulo())
				.addValue("P_PARTE", request.getParte())
				.addValue("P_SEGMENTO", request.getSegmento())
				.addValue("P_FRACCION", request.getFraccion());
		desconfirmarCall.execute(in);
	}

}
