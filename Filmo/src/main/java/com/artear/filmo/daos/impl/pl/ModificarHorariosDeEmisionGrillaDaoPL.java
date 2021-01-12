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

import com.artear.filmo.bo.programacion.HorarioEmisionGrilla;
import com.artear.filmo.bo.programacion.Programa;
import com.artear.filmo.daos.interfaces.ModificarHorariosEmisionGrillaDao;

/**
 * 
 * @author flvaldes
 * 
 */
@Repository("modificarHorariosEmisionGrillaDao")
public class ModificarHorariosDeEmisionGrillaDaoPL implements
		ModificarHorariosEmisionGrillaDao {

	private SimpleJdbcCall buscarProgramasPorDescCall;
	private SimpleJdbcCall obternerHorarioEmisionProgramaCall;
	private SimpleJdbcCall modificarHorarioEmisionProgramaCall;

	@Autowired
	public ModificarHorariosDeEmisionGrillaDaoPL(DataSource dataSource) {
		super();
		this.buscarProgramasPorDescCall = buildBuscarProgramasPorDescCall(dataSource);
		this.obternerHorarioEmisionProgramaCall = buildObternerHorarioEmisionProgramaCall(dataSource);
		this.modificarHorarioEmisionProgramaCall = buildModificarHorarioEmisionProgramaCall(dataSource);
	}

	private SimpleJdbcCall buildModificarHorarioEmisionProgramaCall(
			DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName("fil_pkg_fp370")
				.withProcedureName("pr_modifGT6001");
	}

	private SimpleJdbcCall buildObternerHorarioEmisionProgramaCall(
			DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName("fil_pkg_fp370")
				.withProcedureName("pr_consultaGT6001")
				.returningResultSet("P_PROGRAMA", new RowMapper<HorarioEmisionGrilla>() {
					@Override
					public HorarioEmisionGrilla mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						HorarioEmisionGrilla horario = new HorarioEmisionGrilla();
						horario.setFecha(rs.getString("FECHA_CHAR"));
						horario.setDesde(rs.getInt("DESDE"));
						horario.setHasta(rs.getInt("HASTA"));
						return horario;
					}
				});
	}

	private SimpleJdbcCall buildBuscarProgramasPorDescCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName("fil_pkg_fp370")
				.withProcedureName("pr_levantarPH6001_des")
				.returningResultSet("P_PROGRAMA", new RowMapper<Programa>() {
					@Override
					public Programa mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Programa codDesc = new Programa();
						codDesc.setCodigo(rs.getInt("CODIGO"));
						codDesc.setDescripcion(rs.getString("DESCRIPCION"));
						return codDesc;
					}
				});
	}

	@SuppressWarnings("unchecked")
    @Override
	public List<Programa> buscarProgramasPorDescripcion(String senial,
			String descripcion) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"p_descripcion", descripcion).addValue("p_senial", senial);
		Map<String, Object> out = this.buscarProgramasPorDescCall.execute(in);
		return (List<Programa>) out.get("P_PROGRAMA");
	}

	@SuppressWarnings("unchecked")
    @Override
	public HorarioEmisionGrilla obtenerHorarioEmisionPrograma(String senial,
			Integer codPrograma, Date fecha) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_fecha", fecha)
				.addValue("p_cod_prog", codPrograma)
				.addValue("p_senial", senial);
		Map<String, Object> out = this.obternerHorarioEmisionProgramaCall.execute(in);
		return ((List<HorarioEmisionGrilla>) out.get("P_PROGRAMA")).get(0);
	}

	@Override
	public void modificarHorarioEmisionPrograma(String senial,
			Integer codPrograma, Date fecha, Integer desde, Integer hasta) {
		SqlParameterSource in = new MapSqlParameterSource()
		.addValue("p_senial", senial)
		.addValue("p_cod_prog", codPrograma)
		.addValue("p_fecha", fecha)
		.addValue("p_desde", desde)
		.addValue("p_hasta", hasta);
		this.modificarHorarioEmisionProgramaCall.execute(in);
	}

}
