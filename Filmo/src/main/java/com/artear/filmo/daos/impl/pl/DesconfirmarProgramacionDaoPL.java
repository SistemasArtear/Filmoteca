package com.artear.filmo.daos.impl.pl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.artear.filmo.bo.common.Titulo;
import com.artear.filmo.bo.programacion.ListarProgramacionRequest;
import com.artear.filmo.bo.programacion.ListarProgramacionResponse;
import com.artear.filmo.bo.programacion.Programa;
import com.artear.filmo.bo.programacion.desconfirmar.ProcesarDesconfirmacionRequest;
import com.artear.filmo.bo.programacion.desconfirmar.ProcesarDesconfirmacionResponse;
import com.artear.filmo.daos.interfaces.DesconfirmarProgramacionDao;

@Repository("desconfirmarProgramacionDao")
public class DesconfirmarProgramacionDaoPL implements DesconfirmarProgramacionDao {

	private SimpleJdbcCall listarProgramacionCall;
	private SimpleJdbcCall buscarProgramasDesconfirmarCall;
	private SimpleJdbcCall buscarTitulosDesconfirmarCall;
	private SimpleJdbcCall procesarDesconfirmacionCall;
	private SimpleJdbcCall obtenerPasadasPorSegmentoCall;
	
	private static final String PACKAGE_NAME_DESCONFIRMAR = "fil_pkg_desconfirmar_prog";
	
	@Autowired
	public DesconfirmarProgramacionDaoPL(DataSource dataSource) {
		super();
		this.listarProgramacionCall = this.buildListarProgramacionCall(dataSource);
		this.buscarProgramasDesconfirmarCall = this.buildBuscarProgramasDesconfirmarCall(dataSource);
		this.buscarTitulosDesconfirmarCall = this.buildBuscarTitulosDesconfirmarCall(dataSource);
		this.procesarDesconfirmacionCall = this.buildProcesarDesconfirmacionCall(dataSource);
		this.obtenerPasadasPorSegmentoCall = this.buildObtenerPasadasPorSegmentoCall(dataSource);
	}
	
	private SimpleJdbcCall buildObtenerPasadasPorSegmentoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
			.withCatalogName(PACKAGE_NAME_DESCONFIRMAR)
			.withFunctionName("fn_pasadas_por_segmento");
	}

	private SimpleJdbcCall buildListarProgramacionCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
			.withCatalogName(PACKAGE_NAME_DESCONFIRMAR)
			.withProcedureName("pr_listar_programacion")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
				@Override
				public ListarProgramacionResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
					ListarProgramacionResponse programa = new ListarProgramacionResponse();
					programa.setClave(rs.getString("clave"));
					programa.setHora(rs.getString("hora"));
					programa.setCodigoPrograma(rs.getInt("codigo"));
					programa.setDescripcionPrograma(rs.getString("descripcion"));
					programa.setTitulo(rs.getString("tit_cast"));
					programa.setContrato(rs.getInt("contrato"));
					programa.setSenial(rs.getString("senial"));
					programa.setSenialCon(rs.getString("senial_con"));
					programa.setGrupo(rs.getInt("grupo"));
					programa.setCapitulo(rs.getInt("capitulo"));
					programa.setParte(rs.getInt("parte"));
					programa.setSegmento(rs.getInt("segmento"));
					programa.setFraccion(rs.getInt("fraccion"));
					programa.setDe(rs.getInt("de"));
					programa.setOrden(rs.getInt("orden"));
                    return programa;
                }
		});
	}
	
	private SimpleJdbcCall buildBuscarProgramasDesconfirmarCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
			.withCatalogName(PACKAGE_NAME_DESCONFIRMAR)
			.withProcedureName("pr_listar_programa")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
				@Override
				public Programa mapRow(ResultSet rs, int rowNum) throws SQLException {
					Programa programa = new Programa();
					programa.setCodigo(rs.getInt("codigo"));
					programa.setDescripcion(rs.getString("descripcion"));
					return programa;
				}
		});
	}
	
	private SimpleJdbcCall buildBuscarTitulosDesconfirmarCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
			.withCatalogName(PACKAGE_NAME_DESCONFIRMAR)
			.withProcedureName("pr_listar_titulo")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
				@Override
				public Titulo mapRow(ResultSet rs, int rowNum) throws SQLException {
					Titulo titulo = new Titulo();
					titulo.setClave(rs.getString("clave"));
					titulo.setTitulo(rs.getString("descripcion"));
					return titulo;
				}
		});
	}
	
	private SimpleJdbcCall buildProcesarDesconfirmacionCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_DESCONFIRMAR).withProcedureName("pr_procesar_desconfirmacion");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ListarProgramacionResponse> listarProgramacion(ListarProgramacionRequest listarProgramacionRequest) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Buenos_Aires"));
		calendar.setTime(listarProgramacionRequest.getFecha());
		/* Corrección - Devuelve 1 para lunes */
		calendar.setFirstDayOfWeek(Calendar.MONDAY); 
		Integer dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 0) { 
			dayOfWeek = 7;
		}
		
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_senial", listarProgramacionRequest.getSenial())
														   .addValue("p_fecha",listarProgramacionRequest.getFecha())
														   .addValue("p_tipo_orden", listarProgramacionRequest.getTipoOrden())
														   .addValue("p_clave", listarProgramacionRequest.getCodigoTitulo())
														   .addValue("p_cod_programa", listarProgramacionRequest.getCodigoPrograma())
														   .addValue("p_dia", dayOfWeek);
		
		Map<String, Object> out = this.listarProgramacionCall.execute(in);
		return (List<ListarProgramacionResponse>) out.get("P_CURSOR");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Programa> buscarProgramasDesconfirmar(String descripcionPrograma, String senial) {
		SqlParameterSource in = new MapSqlParameterSource()
		                                        .addValue("p_senial", senial)
		                                        .addValue("p_descripcion", descripcionPrograma);

		Map<String, Object> out = this.buscarProgramasDesconfirmarCall.execute(in);
		return (List<Programa>) out.get("P_CURSOR");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Titulo> buscarTitulosDesconfirmar(String descripcionTitulo) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_descripcion", descripcionTitulo);

		Map<String, Object> out = this.buscarTitulosDesconfirmarCall.execute(in);
		return (List<Titulo>) out.get("P_CURSOR");
	}

	@Override
	public ProcesarDesconfirmacionResponse procesarDesconfirmacion(ProcesarDesconfirmacionRequest procesarDesconfirmacionRequest, BigDecimal pasadas) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_codigo", procesarDesconfirmacionRequest.getCodigo())
														   .addValue("p_tipo_titulo", procesarDesconfirmacionRequest.getTipoTitulo())
														   .addValue("p_numero_titulo", procesarDesconfirmacionRequest.getNumeroTitulo())
														   .addValue("p_capitulo", procesarDesconfirmacionRequest.getCapitulo())
														   .addValue("p_parte", procesarDesconfirmacionRequest.getParte())
														   .addValue("p_segmento", procesarDesconfirmacionRequest.getSegmento())
														   .addValue("p_contrato", procesarDesconfirmacionRequest.getContrato())
														   .addValue("p_grupo", procesarDesconfirmacionRequest.getGrupo())
														   .addValue("p_senial", procesarDesconfirmacionRequest.getSenial())
														   .addValue("p_senial_con", procesarDesconfirmacionRequest.getSenialCon())
														   .addValue("p_fecha", procesarDesconfirmacionRequest.getFecha())
														   .addValue("p_advirtio", procesarDesconfirmacionRequest.getRespuestaWarning())
														   .addValue("p_rta_user", procesarDesconfirmacionRequest.getRespuestaConfirmacion())
														   .addValue("p_pasadas", pasadas);
		
		Map<String, Object> out = this.procesarDesconfirmacionCall.execute(in);
														   
		ProcesarDesconfirmacionResponse response = new ProcesarDesconfirmacionResponse();
		response.setTipoRespuesta(((String) out.get("P_TIPO")));
		response.setRespuesta(((String) out.get("P_RESPUESTA")));
		response.setIdReporte(((Integer) out.get("P_ID_REPORTE")));
		return response;
	}
	
	@Override
	public BigDecimal obtenerPasadasPorSegmento(ProcesarDesconfirmacionRequest procesarDesconfirmacionRequest){
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_tipo_titulo", procesarDesconfirmacionRequest.getTipoTitulo())
				   .addValue("p_numero_titulo", procesarDesconfirmacionRequest.getNumeroTitulo())
				   .addValue("p_capitulo", procesarDesconfirmacionRequest.getCapitulo())
				   .addValue("p_parte", procesarDesconfirmacionRequest.getParte())
				   .addValue("p_segmento", procesarDesconfirmacionRequest.getSegmento())
				   .addValue("p_contrato", procesarDesconfirmacionRequest.getContrato())
				   .addValue("p_grupo", procesarDesconfirmacionRequest.getGrupo())
				   .addValue("p_senial", procesarDesconfirmacionRequest.getSenial())
				   .addValue("p_senial_con", procesarDesconfirmacionRequest.getSenialCon())
				   .addValue("p_fecha", procesarDesconfirmacionRequest.getFecha());
				   
		
		return this.obtenerPasadasPorSegmentoCall.executeFunction(BigDecimal.class, in);
	}

}