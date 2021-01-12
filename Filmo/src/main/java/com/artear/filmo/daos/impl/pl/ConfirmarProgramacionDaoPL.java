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

import com.artear.filmo.bo.programacion.ListarProgramacionRequest;
import com.artear.filmo.bo.programacion.ListarProgramacionResponse;
import com.artear.filmo.bo.programacion.confirmar.BuscarContratosRequest;
import com.artear.filmo.bo.programacion.confirmar.BuscarContratosResponse;
import com.artear.filmo.bo.programacion.confirmar.ProcesarConfirmacionRequest;
import com.artear.filmo.bo.programacion.confirmar.ProcesarConfirmacionResponse;
import com.artear.filmo.daos.interfaces.ConfirmarProgramacionDao;

@Repository("confirmarProgramacionDao")
public class ConfirmarProgramacionDaoPL implements ConfirmarProgramacionDao {

	private SimpleJdbcCall listarProgramacionCall;
	private SimpleJdbcCall procesarConfirmacionCall;
	private SimpleJdbcCall procesarRegistrosGrillaProgramacionCall;
	private SimpleJdbcCall procesarConfirmacionGrillaProgramacionCall;
	private SimpleJdbcCall listarContratosCall;
	
	private static final String PACKAGE_NAME_CONFIRMAR = "fil_pkg_confirmar_prog";
	
	@Autowired
	public ConfirmarProgramacionDaoPL(DataSource dataSource) {
		super();
		this.listarProgramacionCall = this.buildListarProgramacionCall(dataSource);
		this.procesarConfirmacionCall = this.buildProcesarConfirmacionCall(dataSource);
		this.procesarRegistrosGrillaProgramacionCall = this.buildProcesarRegistrosGrillaProgramacionCall(dataSource);
		this.procesarConfirmacionGrillaProgramacionCall = this.buildProcesarConfirmacionGrillaProgramacionCall(dataSource);
		this.listarContratosCall = this.buildBuscarContratosParaTituloCall(dataSource);
	}

	private SimpleJdbcCall buildListarProgramacionCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
			.withCatalogName(PACKAGE_NAME_CONFIRMAR)
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
					programa.setMas(rs.getString("cantidad_contratos"));
					programa.setSenial(rs.getString("senial"));
					programa.setSenialCon(rs.getString("senial_con"));
					programa.setGrupo(rs.getInt("grupo"));
					programa.setCapitulo(rs.getInt("capitulo"));
					programa.setParte(rs.getInt("parte"));
					programa.setSegmento(rs.getInt("segmento"));
					programa.setFraccion(rs.getInt("fraccion"));
					programa.setDe(rs.getInt("de"));
					programa.setOrden(rs.getInt("orden"));
					programa.setRating(rs.getString("rating"));
					programa.setExcedente(rs.getString("excedente"));
                    return programa;
                }
		});
	}
	
	private SimpleJdbcCall buildProcesarRegistrosGrillaProgramacionCall(DataSource dataSource){
		 return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_CONFIRMAR).withProcedureName("pr_tmp_confirmar");
	}
	
	private SimpleJdbcCall buildProcesarConfirmacionCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_CONFIRMAR).withProcedureName("pr_confirmar");
	}
	
	private SimpleJdbcCall buildProcesarConfirmacionGrillaProgramacionCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_CONFIRMAR).withProcedureName("pr_confirmar_todo");
	}
	
	private SimpleJdbcCall buildBuscarContratosParaTituloCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
			.withCatalogName(PACKAGE_NAME_CONFIRMAR)
			.withProcedureName("pr_listar_contratos")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
			@Override
			public BuscarContratosResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
				BuscarContratosResponse contrato = new BuscarContratosResponse();
				contrato.setContrato(rs.getInt("contrato"));
				contrato.setGrupo(rs.getInt("grupo"));
				contrato.setSenial(rs.getString("senial"));
                return contrato;
            }
	});
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
														   .addValue("p_fecha", listarProgramacionRequest.getFecha())
														   .addValue("p_tipo_orden", listarProgramacionRequest.getTipoOrden())
														   .addValue("p_clave", listarProgramacionRequest.getCodigoTitulo())
														   .addValue("p_cod_programa", listarProgramacionRequest.getCodigoPrograma())
														   .addValue("p_capitulo", listarProgramacionRequest.getCapitulo())
														   .addValue("p_parte", listarProgramacionRequest.getParte())
														   .addValue("p_segmento", listarProgramacionRequest.getSegmento())
														   .addValue("p_dia", dayOfWeek);
		
		Map<String, Object> out = this.listarProgramacionCall.execute(in);
		return (List<ListarProgramacionResponse>) out.get("P_CURSOR");
	}
	
	@Override
	public ProcesarConfirmacionResponse procesarConfirmacion(ProcesarConfirmacionRequest procesarConfirmacionRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_codigo", procesarConfirmacionRequest.getCodigo())
				   										   .addValue("p_tipo_titulo", procesarConfirmacionRequest.getTipoTitulo())
				   										   .addValue("p_numero_titulo", procesarConfirmacionRequest.getNumeroTitulo())
				   										   .addValue("p_fecha", procesarConfirmacionRequest.getFecha())
				   										   .addValue("p_senial", procesarConfirmacionRequest.getSenial())
				   										   .addValue("p_capitulo", procesarConfirmacionRequest.getCapitulo())
				   										   .addValue("p_parte", procesarConfirmacionRequest.getParte())
				   										   .addValue("p_segmento", procesarConfirmacionRequest.getSegmento())
				   										   .addValue("p_contrato", procesarConfirmacionRequest.getContrato())
				   										   .addValue("p_grupo", procesarConfirmacionRequest.getGrupo())
				   										   .addValue("p_senial_con", procesarConfirmacionRequest.getSenialCon())
				   										   .addValue("p_gtmfrac", procesarConfirmacionRequest.getFraccion())
				   										   .addValue("p_gtmde", procesarConfirmacionRequest.getDe())
				   										   .addValue("p_rating", procesarConfirmacionRequest.getTipoRatingExcedente() != null && procesarConfirmacionRequest.getTipoRatingExcedente().equals("RATING") ? procesarConfirmacionRequest.getValorNuevoRatingExcedente() : null)
				   										   .addValue("p_duracion", procesarConfirmacionRequest.getTipoRatingExcedente() != null && procesarConfirmacionRequest.getTipoRatingExcedente().equals("EXCEDENTE") ? procesarConfirmacionRequest.getValorNuevoRatingExcedente() : null)
				   										   .addValue("p_workstation", "CONF_PROG");
		
		Map<String, Object> out = this.procesarConfirmacionCall.execute(in);
		   
		ProcesarConfirmacionResponse response = new ProcesarConfirmacionResponse();
		response.setMensaje(((String) out.get("P_MENSAJE")));
		response.setTipo(((String) out.get("P_TIPO")));
		BigDecimal idReporte = ((BigDecimal) out.get("P_ID_REPORTE"));
		if (idReporte != null) {
			response.setIdReporte(idReporte.intValue());	
		}
		return response;
	}
	
	@Override
	public void procesarRegistrosGrillaProgramacion(ProcesarConfirmacionRequest procesarConfirmacionRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_tipo_titulo", procesarConfirmacionRequest.getTipoTitulo())
				   .addValue("p_numero_titulo", procesarConfirmacionRequest.getNumeroTitulo())
				   .addValue("p_fecha", procesarConfirmacionRequest.getFecha())
				   .addValue("p_senial", procesarConfirmacionRequest.getSenial())
				   .addValue("p_capitulo", procesarConfirmacionRequest.getCapitulo())
				   .addValue("p_codigo", procesarConfirmacionRequest.getCodigo())
				   .addValue("p_duracion", procesarConfirmacionRequest.getTipoRatingExcedente() != null && procesarConfirmacionRequest.getTipoRatingExcedente().equals("EXCEDENTE") ? procesarConfirmacionRequest.getValorNuevoRatingExcedente() : null)
				   .addValue("p_rating",procesarConfirmacionRequest.getTipoRatingExcedente() != null && procesarConfirmacionRequest.getTipoRatingExcedente().equals("RATING") ? procesarConfirmacionRequest.getValorNuevoRatingExcedente() : null);
		
		this.procesarRegistrosGrillaProgramacionCall.execute(in);
	}
	
	@Override
	public ProcesarConfirmacionResponse procesarConfirmacionGrillaProgramacion(ProcesarConfirmacionRequest procesarConfirmacionRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_fecha", procesarConfirmacionRequest.getFecha())
				   										   .addValue("p_senial", procesarConfirmacionRequest.getSenial())
				   										   .addValue("p_clave", procesarConfirmacionRequest.getClave())
				   										   .addValue("p_cod_programa", procesarConfirmacionRequest.getCodigo())
				   										   .addValue("p_capitulo", procesarConfirmacionRequest.getCapitulo())
				   										   .addValue("p_parte", procesarConfirmacionRequest.getParte())
				   										   .addValue("p_segmento",procesarConfirmacionRequest.getSegmento())
				   										   .addValue("p_workstation", "CONF_PROG");
		
		Map<String, Object> out = this.procesarConfirmacionGrillaProgramacionCall.execute(in);
		   
		ProcesarConfirmacionResponse response = new ProcesarConfirmacionResponse();
		response.setTipo(((String) out.get("P_TIPO")));
		BigDecimal idReporte = ((BigDecimal) out.get("P_ID_REPORTE"));
		if (idReporte != null) {
			response.setIdReporte(idReporte.intValue());	
		}
		response.setMensaje(((String) out.get("P_MENSAJE")));
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BuscarContratosResponse> buscarContratosParaTitulo(BuscarContratosRequest buscarContratosRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_codigo", buscarContratosRequest.getCodigo())
														   .addValue("p_tipo_titulo", buscarContratosRequest.getTipoTitulo())
														   .addValue("p_numero_titulo", buscarContratosRequest.getNumeroTitulo())
														   .addValue("p_fecha", buscarContratosRequest.getFecha())
														   .addValue("p_senial", buscarContratosRequest.getSenial())
														   .addValue("p_capitulo", buscarContratosRequest.getCapitulo())
														   .addValue("p_parte", buscarContratosRequest.getParte())
														   .addValue("p_segmento", buscarContratosRequest.getSegmento())
														   .addValue("p_contrato", buscarContratosRequest.getContrato())
														   .addValue("p_grupo", buscarContratosRequest.getGrupo())
														   .addValue("p_senial_con", buscarContratosRequest.getSenialCon());
		
		Map<String, Object> out = this.listarContratosCall.execute(in);
		return (List<BuscarContratosResponse>) out.get("P_CURSOR");
	}

	
}