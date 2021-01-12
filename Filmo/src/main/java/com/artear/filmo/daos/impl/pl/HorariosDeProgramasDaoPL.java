package com.artear.filmo.daos.impl.pl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.artear.filmo.bo.programacion.Horario;
import com.artear.filmo.bo.programacion.Pagination;
import com.artear.filmo.bo.programacion.Programa;
import com.artear.filmo.bo.programacion.ProgramasPagination;
import com.artear.filmo.daos.interfaces.HorariosDeProgramasDao;
import com.artear.filmo.utils.ListUtil;

/**
 * 
 * @author flvaldes
 * 
 */
@Repository("horariosDeProgramasDao")
public class HorariosDeProgramasDaoPL implements HorariosDeProgramasDao {

	private SimpleJdbcCall programasConHorariosPorCodigoCall;
	private SimpleJdbcCall programasConHorariosPorDescripcionCall;
	private SimpleJdbcCall maestroProgramasPorCodigoCall;
	private SimpleJdbcCall maestroProgramasPorDescripcionCall;
	private SimpleJdbcCall horariosDeProgramasCall;
	private SimpleJdbcCall eliminarHorariosDeProgramacionCall;
	private SimpleJdbcCall modificarHorariosDeProgramacionCall;
	private SimpleJdbcCall altaHorariosDeProgramacionCall;
	private SimpleJdbcCall validarModificarProgramaCall;
	private SimpleJdbcCall modificarNuevaFechaCall;
	private SimpleJdbcCall validarEliminarProgramaCall;
	private SimpleJdbcCall eliminarNuevaFechaCall;

	@Autowired
	public HorariosDeProgramasDaoPL(DataSource dataSource) {
		super();
		this.programasConHorariosPorCodigoCall = buildProgramasConHorariosPorCodCall(dataSource);
		this.programasConHorariosPorDescripcionCall = buildProgramasConHorariosPorDescCall(dataSource);
		this.maestroProgramasPorCodigoCall = buildMaestroProgramasPorCodCall(dataSource);
		this.maestroProgramasPorDescripcionCall = buildMaestroProgramasPorDescCall(dataSource);
		this.horariosDeProgramasCall = buildHorariosDeProgramasCall(dataSource);
		this.eliminarHorariosDeProgramacionCall = buildEliminarHorariosDeProgramacionCall(dataSource);
		this.modificarHorariosDeProgramacionCall = buildModificarHorariosDeProgramacionCall(dataSource);
		this.altaHorariosDeProgramacionCall = buildAltaHorariosDeProgramacionCall(dataSource);
		this.validarModificarProgramaCall = buildValidarModificarProgramaCall(dataSource);
		this.modificarNuevaFechaCall = buildModificarNuevaFechaCall(dataSource);
		this.validarEliminarProgramaCall = buildValidarEliminarProgramaCall(dataSource);
		this.eliminarNuevaFechaCall = buildEliminarNuevaFechaCall(dataSource);
	}

	private SimpleJdbcCall buildEliminarNuevaFechaCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_FP320")
				.withProcedureName("pr_baja_prog_gt");
	}

	private SimpleJdbcCall buildValidarEliminarProgramaCall(
			DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_FP320")
				.withFunctionName("fn_validar_eliminacion");
	}

	private SimpleJdbcCall buildModificarNuevaFechaCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_FP320")
				.withProcedureName("pr_UpdateNuevaFecha");
	}

	private SimpleJdbcCall buildValidarModificarProgramaCall(
			DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_FP320")
				.withFunctionName("fn_existe_senial_prog");
	}

	private SimpleJdbcCall buildAltaHorariosDeProgramacionCall(
			DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_FP320")
				.withProcedureName("pr_altaPH6001");
	}

	private SimpleJdbcCall buildModificarHorariosDeProgramacionCall(
			DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_FP320")
				.withProcedureName("pr_modifPH6001");
	}

	private SimpleJdbcCall buildEliminarHorariosDeProgramacionCall(
			DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_FP320")
				.withProcedureName("pr_bajaPH6001");
	}

	private SimpleJdbcCall buildProgramasConHorariosPorCodCall(
			DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_FP320")
				.withProcedureName("pr_levantarPH6001")
				.returningResultSet("P_PROGRAMA", new RowMapper<Programa>() {
					@Override
					public Programa mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Programa programa = new Programa();
						programa.setCodigo(rs.getInt("CODIGO"));
						programa.setDescripcion(rs.getString("DESCRIPCION"));
						return programa;
					}
				});
	}

	private SimpleJdbcCall buildProgramasConHorariosPorDescCall(
			DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_FP320")
				.withProcedureName("pr_levantarPH6001_des")
				.returningResultSet("P_PROGRAMA", new RowMapper<Programa>() {
					@Override
					public Programa mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Programa programa = new Programa();
						programa.setCodigo(rs.getInt("CODIGO"));
						programa.setDescripcion(rs.getString("DESCRIPCION"));
						return programa;
					}
				});
	}

	private SimpleJdbcCall buildMaestroProgramasPorCodCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_FP320")
				.withProcedureName("pr_listaprogramas")
				.returningResultSet("P_PROGRAMA", new RowMapper<Programa>() {
					@Override
					public Programa mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Programa programa = new Programa();
						programa.setCodigo(rs.getInt("CODIGO"));
						programa.setDescripcion(rs.getString("DESCRIPCION"));
						return programa;
					}
				});
	}

	private SimpleJdbcCall buildMaestroProgramasPorDescCall(
			DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_FP320")
				.withProcedureName("pr_listaprogramas_des")
				.returningResultSet("P_PROGRAMA", new RowMapper<Programa>() {
					@Override
					public Programa mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Programa programa = new Programa();
						programa.setCodigo(rs.getInt("CODIGO"));
						programa.setDescripcion(rs.getString("DESCRIPCION"));
						return programa;
					}
				});
	}

	private SimpleJdbcCall buildHorariosDeProgramasCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_FP320")
				.withProcedureName("pr_consultaPH6001")
				.returningResultSet("P_PROGRAMA", new RowMapper<Horario>() {
					@Override
					public Horario mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Horario horario = new Horario();
						horario.setDia(rs.getInt("DIA"));
						horario.setDesde(rs.getInt("DESDE"));
						horario.setHasta(rs.getInt("HASTA"));
						return horario;
					}
				});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Programa> obtenerProgramasConHorariosPor(String idSenial,
			Integer codPrograma) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"p_senial", idSenial).addValue("p_codigo", codPrograma);
		Map<String, Object> out = this.programasConHorariosPorCodigoCall
				.execute(in);
		return (List<Programa>) out.get("P_PROGRAMA");
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Programa> obtenerProgramasConHorariosPor(String idSenial,
			String descripcion) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"p_senial", idSenial).addValue("p_descripcion", descripcion);
		Map<String, Object> out = this.programasConHorariosPorDescripcionCall
				.execute(in);
		return (List<Programa>) out.get("P_PROGRAMA");
	}

	@Override
	@SuppressWarnings("unchecked")
	public ProgramasPagination obtenerMaestroProgramasPor(Integer codPrograma,
			Pagination paginationRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"p_codigo", codPrograma);
		Map<String, Object> out = this.maestroProgramasPorCodigoCall
				.execute(in);
		List<Programa> programas = (List<Programa>) out.get("P_PROGRAMA");
		ListUtil.paging(programas, paginationRequest);
		return new ProgramasPagination(programas, paginationRequest);
	}

	@Override
	@SuppressWarnings("unchecked")
	public ProgramasPagination obtenerMaestroProgramasPor(String descripcion,
			Pagination paginationRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"p_descripcion", descripcion);
		Map<String, Object> out = this.maestroProgramasPorDescripcionCall
				.execute(in);
		List<Programa> programas = (List<Programa>) out.get("P_PROGRAMA");
		// ListUtil.paging(programas, paginationRequest);
		return new ProgramasPagination(programas, paginationRequest);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Horario> obtenerHorariosDePrograma(String idSenial,
			Integer codPrograma) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"p_senial", idSenial).addValue("p_cod_prog", codPrograma);
		Map<String, Object> out = this.horariosDeProgramasCall.execute(in);
		return (List<Horario>) out.get("P_PROGRAMA");
	}

	@Override
	public void eliminarHorariosDeProgramacion(String idSenial,
			Integer codPrograma) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"p_senial", idSenial).addValue("p_cod_prog", codPrograma);
		this.eliminarHorariosDeProgramacionCall.execute(in);
	}

	@Override
	public void modificarHorariosDeProgramacion(String idSenial,
			Integer codPrograma, List<Horario> horarios) {
		for (Horario horario : horarios) {
			SqlParameterSource in = llenarMapAltaModificacion(idSenial,
					codPrograma, horario);
			this.modificarHorariosDeProgramacionCall.execute(in);
		}
	}

	@Override
	public void altaHorariosDeProgramacion(String idSenial,
			Integer codPrograma, List<Horario> horarios) {
		for (Horario horario : horarios) {
			SqlParameterSource in = llenarMapAltaModificacion(idSenial,
					codPrograma, horario);
			this.altaHorariosDeProgramacionCall.execute(in);
		}
	}

	private SqlParameterSource llenarMapAltaModificacion(String idSenial,
			Integer codPrograma, Horario horario) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_senial", idSenial)
				.addValue("p_cod_prog", codPrograma)
				.addValue("p_dia", horario.getDia())
				.addValue("P_desde", horario.getDesde())
				.addValue("p_hasta", horario.getHasta());
		return in;
	}

	@Override
	public Boolean validarFechaParaModificarHorariosPrograma(String idSenial,
			Integer codPrograma, List<Horario> horarios) {
		for (Horario horario : horarios) {
			SqlParameterSource in = llenarMapAltaModificacion(idSenial,
					codPrograma, horario);
			String result = this.validarModificarProgramaCall.executeFunction(
					String.class, in);
			if (result.equals("N")) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void modificarNuevaFecha(String idSenial, Integer codPrograma,
			List<Horario> horarios, Date nuevaFecha) {
		for (Horario horario : horarios) {
			MapSqlParameterSource in = (MapSqlParameterSource) llenarMapAltaModificacion(
					idSenial, codPrograma, horario);
			in.addValue("p_new_date", nuevaFecha);
			this.modificarNuevaFechaCall.execute(in);
		}
	}

	@Override
	public Boolean validarFechaParaEliminarHorariosPrograma(String idSenial,
			Integer codPrograma) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"p_senial", idSenial).addValue("p_cod_prog", codPrograma);
		String result = this.validarEliminarProgramaCall.executeFunction(
				String.class, in);
		return result.equals("N") ? false : true;
	}

	@Override
	public void eliminarNuevaFecha(String idSenial, Integer codPrograma,
			Date fecha) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_senial", idSenial)
				.addValue("p_cod_prog", codPrograma).addValue("p_fecha", fecha);
		this.eliminarNuevaFechaCall.execute(in);
	}
}
