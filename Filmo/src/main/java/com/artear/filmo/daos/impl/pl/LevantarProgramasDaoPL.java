package com.artear.filmo.daos.impl.pl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.artear.filmo.bo.programacion.LevantarExhibicionesRequest;
import com.artear.filmo.bo.programacion.TituloProgramado;
import com.artear.filmo.bo.programacion.ValidacionLevantarExhibicionResponse;
import com.artear.filmo.daos.interfaces.LevantarProgramasDao;

/**
 * 
 * @author flvaldes
 * 
 */
@Repository("levantarProgramasDao")
public class LevantarProgramasDaoPL implements LevantarProgramasDao {

	private SimpleJdbcCall obtenerMayorFechaProgCall;
	private SimpleJdbcCall validacionLevantarExhibicionCall;
	private SimpleJdbcCall levantarExhibicionCall;

	@Autowired
	public LevantarProgramasDaoPL(DataSource dataSource) {
		super();
		this.obtenerMayorFechaProgCall = buildObtenerMayorFechaProg(dataSource);
		this.validacionLevantarExhibicionCall = buildValidacionLevantarExhibicion(dataSource);
		this.levantarExhibicionCall = buildLevantarExhibicion(dataSource);

	}

	private SimpleJdbcCall buildLevantarExhibicion(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(
				"FIL_PKG_LEVANTAR_PROGRAMAS").withProcedureName(
						"PR_LEVANTAR_EXHIBICIONES");
	}

	private SimpleJdbcCall buildValidacionLevantarExhibicion(
			DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(
				"FIL_PKG_LEVANTAR_PROGRAMAS").withProcedureName(
				"PR_VALID_LEVANTAR_EXHIBICIONES");
	}

	private SimpleJdbcCall buildObtenerMayorFechaProg(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(
				"FIL_PKG_LEVANTAR_PROGRAMAS").withProcedureName(
				"PR_OBT_MAYOR_FECHA_PROGRAMADA");
	}

	@Override
	public Date obtenerMayorFechaProgramada(String senial, Integer codPrograma) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"P_SENIAL", senial).addValue("P_PROGRAMA", codPrograma);
		Map<String, Object> out = obtenerMayorFechaProgCall.execute(in);
		return (Date) out.get("P_MAYOR_FECHA");
	}

	@Override
	public List<ValidacionLevantarExhibicionResponse> validacionLevantarExhibiciones(
			LevantarExhibicionesRequest request) throws Exception {
		List<ValidacionLevantarExhibicionResponse> response = new ArrayList<ValidacionLevantarExhibicionResponse>();
		for (TituloProgramado tituloProgramado : request.getTitulos()) {
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("P_SENIAL", request.getSenial())
					.addValue("P_PROGRAMA", request.getCodPrograma())
					.addValue("P_FECHA_EXHIB", new SimpleDateFormat("dd/MM/yyyy").parse(tituloProgramado.getFecha()))
					.addValue("P_CLAVE", tituloProgramado.getClave())
					.addValue("P_CAPITULO", tituloProgramado.getCapitulo())
					.addValue("P_PARTE", tituloProgramado.getParte())
					.addValue("P_SEGMENTO", tituloProgramado.getSegmento());
			Map<String, Object> out = validacionLevantarExhibicionCall.execute(in);
			response.add(new ValidacionLevantarExhibicionResponse(
					tituloProgramado, (String) out.get("P_OK")));
		}
		return response;
	}

	@Override
	public void levantarExhibiciones(
			LevantarExhibicionesRequest request) throws Exception {
		for (TituloProgramado tituloProgramado : request.getTitulos()) {
			SqlParameterSource in = new MapSqlParameterSource()
			.addValue("P_SENIAL", request.getSenial())
			.addValue("P_PROGRAMA", request.getCodPrograma())
			.addValue("P_FECHA_EXHIB", new SimpleDateFormat("dd/MM/yyyy").parse(tituloProgramado.getFecha()))
			.addValue("P_CLAVE", tituloProgramado.getClave())
			.addValue("P_CAPITULO", tituloProgramado.getCapitulo())
			.addValue("P_PARTE", tituloProgramado.getParte())
			.addValue("P_SEGMENTO", tituloProgramado.getSegmento());
			levantarExhibicionCall.execute(in);
		}
	}

}
