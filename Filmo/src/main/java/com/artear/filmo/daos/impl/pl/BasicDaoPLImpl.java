package com.artear.filmo.daos.impl.pl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.artear.filmo.bo.common.Senial;
import com.artear.filmo.daos.interfaces.BasicDaoPL;

/**
 * 
 * @author flvaldes
 * 
 */
@Repository("basicDaoPL")
public class BasicDaoPLImpl implements BasicDaoPL {

	private SimpleJdbcCall buscarDescripcionSenialesCall;
	private SimpleJdbcCall setearUsuarioEnSessionCall;

	@Autowired
	public BasicDaoPLImpl(DataSource dataSource) {
		super();
		this.setearUsuarioEnSessionCall = buildSetearUsuarioEnSessionCall(dataSource);
		this.buscarDescripcionSenialesCall = buildBuscarDescripcionSenialesCall(dataSource);
	}

	private SimpleJdbcCall buildSetearUsuarioEnSessionCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withSchemaName("FILMO")
//				.withSchemaName("FILMOUDN4")
				.withProcedureName("setear_identifier")
				.withoutProcedureColumnMetaDataAccess()
				.declareParameters(
						new SqlInOutParameter("PIV_USER", Types.VARCHAR))
				.declareParameters(
						new SqlInOutParameter("PORC_RESULTADO", Types.NUMERIC))
				.returningResultSet("PORC_RESULTADO", new RowMapper<Object>() {
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
						return rs.getObject(1);
					}
				});
	}

	private SimpleJdbcCall buildBuscarDescripcionSenialesCall(
			DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("fil_pkg_servicios_basicos")
				.withProcedureName("pr_levantar_seniales")
				.returningResultSet("P_CURSOR", new RowMapper<Senial>() {
					@Override
					public Senial mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Senial senial = new Senial();
						senial.setCodigo(rs.getString("CODIGO"));
						senial.setDescripcion(rs.getString("DESCRIPCION"));
						return senial;
					}
				});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Senial> getSeniales(String seniales) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"P_SENIALES", seniales);
		Map<String, Object> out = buscarDescripcionSenialesCall.execute(in);
		return (List<Senial>) out.get("P_CURSOR");
	}

	@Override
	public void setUsuarioEnSession(String usuario) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"PIV_USER", usuario);
		setearUsuarioEnSessionCall.execute(in);
	}

}
