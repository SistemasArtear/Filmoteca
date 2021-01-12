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

import com.artear.filmo.bo.activar.titulo.vencido.BuscarTitulosRequest;
import com.artear.filmo.bo.activar.titulo.vencido.ContratoParaTitulo;
import com.artear.filmo.bo.activar.titulo.vencido.Titulo;
import com.artear.filmo.bo.activar.titulo.vencido.VisualizarModificacionContratosResponse;
import com.artear.filmo.daos.interfaces.ActivarTituloVencidoDao;

@Repository("activarTituloVencidoDao")
public class ActivarTituloVencidoDaoPL implements ActivarTituloVencidoDao {

	private static final String PKG = "FIL_PKG_FP170";
	private SimpleJdbcCall buscarContratosParaTituloCall;
	private SimpleJdbcCall buscarTitulosCall;
	private SimpleJdbcCall validarContratoCall;
	private SimpleJdbcCall visualizarModificacionContratosCall;

	@Autowired
	public ActivarTituloVencidoDaoPL(DataSource dataSource) {
		super();
		this.buscarTitulosCall = this.buildBuscarTitulosCall(dataSource);
		this.buscarContratosParaTituloCall = this.buildBuscarContratosParaTituloCall(dataSource);
		this.validarContratoCall = this.builValidarContratoCall(dataSource);
		this.visualizarModificacionContratosCall = this.buildVisualizarModificacionContratosCall(dataSource);
	}

	private SimpleJdbcCall buildBuscarTitulosCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PKG)
			.withProcedureName("pr_listado_principal")
			.returningResultSet("P_CURSOR", new RowMapper<Titulo>() {
				@Override
				public Titulo mapRow(ResultSet rs, int rowNum) throws SQLException {
					Titulo response = new Titulo();
					response.setActores(rs.getString("actores"));
					response.setClave(rs.getString("clave"));
					response.setSigno(rs.getString("signo"));
					response.setTituloCastellano(rs.getString("titulo_cast"));
					response.setTituloOriginal(rs.getString("titulo_orig"));
					return response;
				}
			});
	}

	private SimpleJdbcCall buildBuscarContratosParaTituloCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PKG)
			.withProcedureName("pr_lista_contra_titulo")
			.returningResultSet("P_CURSOR", new RowMapper<ContratoParaTitulo>() {
				@Override
				public ContratoParaTitulo mapRow(ResultSet rs, int rowNum) throws SQLException {
					ContratoParaTitulo response = new ContratoParaTitulo();
					response.setEstado(rs.getString("estado"));
					response.setGrupo(rs.getInt("grupo"));
					response.setFecha(rs.getString("fecha_contrato"));
					response.setVigenciaDesde(rs.getString("fecha_vig_desde"));
					response.setVigenciaHasta(rs.getString("fecha_vig_hasta"));
					response.setNumero(rs.getInt("contrato"));
					response.setCodigoDistribuidor(rs.getInt("proveedor"));
					response.setRazonSocialDistribuidor(rs.getString("razon_social"));
					response.setObservaciones(rs.getString("observaciones"));
					return response;
				}
			});
	}

	private SimpleJdbcCall builValidarContratoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PKG).withFunctionName("fn_valida_contrato_selec");
	}
	
	private SimpleJdbcCall buildVisualizarModificacionContratosCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PKG)
			.withProcedureName("pr_visual_mod_contrato")
			.returningResultSet("P_CURSOR", new RowMapper<VisualizarModificacionContratosResponse>() {
				@Override
				public VisualizarModificacionContratosResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
					VisualizarModificacionContratosResponse response = new VisualizarModificacionContratosResponse();
					response.setContrato(rs.getInt("contrato"));
					response.setFechaRealDesde(rs.getDate("fecha_real_desde"));
					response.setFechaRealHasta(rs.getDate("fecha_real_hasta"));
					response.setEstadoStandBy(rs.getString("estado_standby"));
					response.setRazonSocial(rs.getString("razon_social"));
					response.setClave(rs.getString("clave"));
					response.setSenial(rs.getString("senial"));
					response.setGrupo(rs.getInt("grupo"));
					response.setTituloCast(rs.getString("titulo_cast"));
					response.setTituloOrig(rs.getString("titulo_orig"));
					response.setTipoDerecho(rs.getString("tipo_derecho"));
					response.setIdModVigencia(rs.getInt("id_mod_vigencia"));
					response.setTipoModifVigencia(rs.getString("tipo_modif_vigencia"));
					response.setDescModifVigencia(rs.getString("desc_modif_vigencia"));
					response.setFechaDesdePaytv(rs.getDate("fecha_desde_paytv"));
					response.setFechaHastaPaytv(rs.getDate("fecha_hasta_paytv"));
					response.setIdPaytvAnulado(rs.getInt("id_paytv_anulado"));
					response.setFechaAntDesde(rs.getDate("fecha_ant_desde"));
					response.setFechaAntHasta(rs.getDate("fecha_ant_hasta"));
					response.setFechaNuevaDesde(rs.getDate("fecha_nueva_desde"));
					response.setFechaNuevaHasta(rs.getDate("fecha_nueva_hasta"));
					response.setObservaciones(rs.getString("observaciones"));
					return response;
				}
			});
		}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Titulo> buscarTitulos(BuscarTitulosRequest request) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_senial", request.getSenial())
														   .addValue("p_clave", request.getClave())
														   .addValue("p_tipo_busqueda",StringUtils.isBlank(request.getClave()) ? request.getTipoBusqueda() : "K")
														   .addValue("p_descripcion", request.getDescripcion())
														   .addValue("p_clave", request.getClave());
		
		Map<String, Object> out = this.buscarTitulosCall.execute(in);
		return (List<Titulo>) out.get("P_CURSOR");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ContratoParaTitulo> buscarContratosParaElTitulo(String senial, String clave) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_senial", senial)
														   .addValue("p_clave", clave);
		
		Map<String, Object> out = this.buscarContratosParaTituloCall.execute(in);
		return (List<ContratoParaTitulo>) out.get("P_CURSOR");
	}

	@Override
	public String validarContrato(String senial, String clave, Integer contrato) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_senial", senial)
														   .addValue("p_clave", clave)
														   .addValue("p_id_contrato", contrato);
		
		String out = this.validarContratoCall.executeFunction(String.class, in);
		return out;
	}
	
	@SuppressWarnings("unchecked")
	public List<VisualizarModificacionContratosResponse> visualizarModificacionContratos(String senial, String clave, Integer contrato) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_senial", senial)
														   .addValue("p_clave", clave)
														   .addValue("p_id_contrato", contrato);

		Map<String, Object> out = this.visualizarModificacionContratosCall.execute(in);
		return (List<VisualizarModificacionContratosResponse>) out.get("P_CURSOR");
	}

}