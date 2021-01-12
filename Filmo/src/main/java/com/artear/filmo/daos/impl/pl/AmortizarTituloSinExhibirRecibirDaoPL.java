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

import com.artear.filmo.bo.amortizar.titulossinexhibir.CapituloTituloSinExhRec;
import com.artear.filmo.bo.amortizar.titulossinexhibir.TituloSinExhibirRecibirGrillaResponse;
import com.artear.filmo.bo.amortizar.titulossinexhibir.TituloSinExhibirRecibirView;
import com.artear.filmo.daos.interfaces.AmortizarTituloSinExhibirRecibirDao;

@Repository("amortizarTituloSinExhibirRecibirDao")
public class AmortizarTituloSinExhibirRecibirDaoPL implements AmortizarTituloSinExhibirRecibirDao {
	
	private static final String TIPO_TITULO_CASTELLANO = "CAST";
	private static final String TIPO_TITULO_ORIGINAL = "ORIG";
	
	private SimpleJdbcCall buscarTitulosCastPorCodigoCall;
	private SimpleJdbcCall buscarTitulosOriginalPorCodigoCall;
	private SimpleJdbcCall buscarTitulosCastPorDescCall;
	private SimpleJdbcCall buscarTitulosOriginalPorDescCall;
	private SimpleJdbcCall buscarTituloConCapitulos;
	private SimpleJdbcCall amortizarTituloSinExhibirRecibirCall;
	
	@Autowired
	public AmortizarTituloSinExhibirRecibirDaoPL(DataSource dataSource) {
		super();
		this.buscarTitulosCastPorCodigoCall = this.buildBuscarTitulosCall(dataSource, "pr_FP18001Titulos");
		this.buscarTitulosOriginalPorCodigoCall = this.buildBuscarTitulosCall(dataSource, "pr_FP18001Titulos");
		this.buscarTitulosCastPorDescCall = this.buildBuscarTitulosCall(dataSource, "pr_FP18001Titulos");
		this.buscarTitulosOriginalPorDescCall = this.buildBuscarTitulosCall(dataSource, "pr_FP18001Titulos");
		this.buscarTituloConCapitulos = this.buildBuscarTituloConCapitulosCall(dataSource);
		this.amortizarTituloSinExhibirRecibirCall = this.buildAmortizarTituloSinExhibirRecibirCall(dataSource);
	}
	
	private SimpleJdbcCall buildBuscarTitulosCall(DataSource dataSource, String procedureName) {
		return new SimpleJdbcCall(dataSource)
			.withCatalogName("fil_pkg_fp180")
			.withProcedureName(procedureName)
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
				@Override
				public TituloSinExhibirRecibirGrillaResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
					TituloSinExhibirRecibirGrillaResponse titulo = new TituloSinExhibirRecibirGrillaResponse();
					titulo.setClave(rs.getString("clave"));
					titulo.setTitulo(rs.getString("titulo"));
					titulo.setContrato(rs.getInt("contrato"));
					titulo.setDistribuidor(rs.getString("distribuidor"));
					titulo.setPerpetuo(rs.getString("PER"));
					titulo.setRecibido(rs.getString("REC"));
					titulo.setPorAmortizar(rs.getBigDecimal("porAmortizar"));
					titulo.setGrupo(rs.getInt("grupo"));
					return titulo;
				}
			});
	}
	
	private SimpleJdbcCall buildBuscarTituloConCapitulosCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
			.withCatalogName("fil_pkg_fp180")
			.withProcedureName("pr_FP18003Capitulos")
			.returningResultSet("p_cursorEncabezado", new RowMapper<Object>() {
				@Override
				public TituloSinExhibirRecibirView mapRow(ResultSet rs, int rowNum) throws SQLException {
					TituloSinExhibirRecibirView tituloSinExhibirRecibir = new TituloSinExhibirRecibirView();
					tituloSinExhibirRecibir.setTituloCastellano(rs.getString("tituloCastellano"));
					tituloSinExhibirRecibir.setContrato(rs.getInt("Contrato"));
					tituloSinExhibirRecibir.setTitulo(rs.getString("titulo"));
					tituloSinExhibirRecibir.setTotalCapitulos(rs.getInt("TotalCapitulos"));
					tituloSinExhibirRecibir.setPorAmortizar(rs.getBigDecimal("porAmortizar"));
					return tituloSinExhibirRecibir;
				}
			})
			.returningResultSet("p_cursorCuerpo", new RowMapper<Object>() {
				@Override
				public CapituloTituloSinExhRec mapRow(ResultSet rs, int rowNum) throws SQLException {
					CapituloTituloSinExhRec capitulo = new CapituloTituloSinExhRec();
					capitulo.setNroCapitulo(rs.getInt("nroCapitulo")); 
					capitulo.setDescripcion(rs.getString("descripcion")); 
					capitulo.setRecibido(rs.getString("recibido")); 
					capitulo.setConfExhibicion(rs.getString("confExhibicion"));
					return capitulo;
				}
			});
	}
	
	private SimpleJdbcCall buildAmortizarTituloSinExhibirRecibirCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
			.withCatalogName("fil_pkg_fp180")
			.withProcedureName("pr_FP18004Confirma")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
				@Override
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					Integer error = rs.getInt("valor");
					return error;
				}
			});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TituloSinExhibirRecibirGrillaResponse> obtenerTitulosCastellanoPorCodigo(String senial, String clave) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_senial", senial)
														   .addValue("p_clave", clave)
														   .addValue("p_descripcion", null)
														   .addValue("p_tipoTitulo", TIPO_TITULO_CASTELLANO);
														   
		Map<String, Object> out = this.buscarTitulosCastPorCodigoCall.execute(in);
		return (List<TituloSinExhibirRecibirGrillaResponse>) out.get("P_CURSOR");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TituloSinExhibirRecibirGrillaResponse> obtenerTitulosOriginalPorCodigo(String senial, String clave) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_senial", senial)
														   .addValue("p_clave", clave)
														   .addValue("p_descripcion", null)
														   .addValue("p_tipoTitulo", TIPO_TITULO_ORIGINAL);
				   
		Map<String, Object> out = this.buscarTitulosOriginalPorCodigoCall.execute(in);
		return (List<TituloSinExhibirRecibirGrillaResponse>) out.get("P_CURSOR");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TituloSinExhibirRecibirGrillaResponse> obtenerTitulosCastellanoPorDescripcion(String senial, String descripcion) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_senial", senial)
														   .addValue("p_clave", null)
														   .addValue("p_descripcion", descripcion)
														   .addValue("p_tipoTitulo", TIPO_TITULO_CASTELLANO);
				   
		Map<String, Object> out = this.buscarTitulosCastPorDescCall.execute(in);
		return (List<TituloSinExhibirRecibirGrillaResponse>) out.get("P_CURSOR");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TituloSinExhibirRecibirGrillaResponse> obtenerTitulosOriginalPorDescripcion(String senial, String descripcion) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_senial", senial)
														   .addValue("p_clave", null)
				   										   .addValue("p_descripcion", descripcion)
				   										   .addValue("p_tipoTitulo", TIPO_TITULO_ORIGINAL);
				   
		Map<String, Object> out = this.buscarTitulosOriginalPorDescCall.execute(in);
		return (List<TituloSinExhibirRecibirGrillaResponse>) out.get("P_CURSOR");
	}

	@SuppressWarnings("unchecked")
	@Override
	public TituloSinExhibirRecibirView obtenerTituloSinExhibirRecibirConCapitulos(String senial, Integer contrato, String clave) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_senial", senial)
														   .addValue("p_contrato", contrato)
														   .addValue("p_clave", clave);
		
		Map<String, Object> out = this.buscarTituloConCapitulos.execute(in);
		TituloSinExhibirRecibirView tituloSinExhibirRecibir = ((List<TituloSinExhibirRecibirView>) out.get("p_cursorEncabezado")).get(0);
		tituloSinExhibirRecibir.setCapitulos((List<CapituloTituloSinExhRec>) out.get("p_cursorCuerpo"));
		
		return tituloSinExhibirRecibir;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Boolean amortizarTituloSinExhibirRecibir(String senial, Integer contrato, String clave, Integer grupo) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_senial", senial)
														   .addValue("p_contrato", contrato)
														   .addValue("p_clave", clave)
														   .addValue("p_grupo", grupo);
		 Map<String, Object> out = this.amortizarTituloSinExhibirRecibirCall.execute(in);
		 Integer result = ((List<Integer>) out.get("P_CURSOR")).get(0);
		 return Integer.valueOf(1).equals(result);
	}
	
}