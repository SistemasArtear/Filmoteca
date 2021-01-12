package com.artear.filmo.daos.impl.pl;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.artear.filmo.bo.common.Distribuidor;
import com.artear.filmo.bo.common.Soporte;
import com.artear.filmo.bo.ingresar.materiales.SoporteTituloABM;
import com.artear.filmo.bo.ingresar.materiales.TituloMaterialesGrillaResponse;
import com.artear.filmo.daos.interfaces.IngresarMaterialesDao;
import com.artear.filmo.utils.StringUtils;

@Repository("ingresarMaterialesDao")
public class IngresarMaterialesDaoPL implements IngresarMaterialesDao {
	
	private SimpleJdbcCall buscarDistribuidoresCall;
	private SimpleJdbcCall buscarTitulosCastCall;
	private SimpleJdbcCall buscarTitulosOriginalCall;
	private SimpleJdbcCall determinarFamiliaTituloCall;
	private SimpleJdbcCall buscarSoportesCall;
	private SimpleJdbcCall altaSoporteTituloCall;
	
	@Autowired
	public IngresarMaterialesDaoPL(DataSource dataSource) {
		super();
		this.buscarDistribuidoresCall = this.buildBuscarDistribuidoresCall(dataSource);
		this.buscarTitulosCastCall = this.buildBuscarTitulosCall(dataSource, "pr_grillaFP21003");
		this.buscarTitulosOriginalCall = this.buildBuscarTitulosCall(dataSource, "pr_grillaFP21005");
		this.determinarFamiliaTituloCall = this.buildDeterminarFamiliaTituloCall(dataSource);
		this.buscarSoportesCall = this.buildBuscarSoportesCall(dataSource);
		this.altaSoporteTituloCall = this.buildAltaSoporteTituloCall(dataSource);
	}
	
	private SimpleJdbcCall buildBuscarDistribuidoresCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
			.withCatalogName("fil_pkg_validacion_materiales")
			.withProcedureName("pr_busqueda_distribuidor")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
				@Override
				public Distribuidor mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Distribuidor distribuidor = new Distribuidor();
                    distribuidor.setCodigo(rs.getInt("PRKPRO"));
                    distribuidor.setRazonSocial(rs.getString("PRDRAZSOC"));
                    return distribuidor;
                }
			});
	}
	
	private SimpleJdbcCall buildBuscarSoportesCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
			.withCatalogName("fil_pkg_fp210")
			.withProcedureName("pr_busqueda_soporte")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
				@Override
				public Soporte mapRow(ResultSet rs, int rowNum) throws SQLException {
					Soporte soporte = new Soporte();
					soporte.setCodigo(rs.getString("codigo"));
					soporte.setDescripcion(rs.getString("descripcion"));
                    return soporte;
                }
			});
	}
	
	private SimpleJdbcCall buildBuscarTitulosCall(DataSource dataSource, final String procedureName) {
		return new SimpleJdbcCall(dataSource)
			.withCatalogName("fil_pkg_fp210")
			.withProcedureName(procedureName)
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
				@Override
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					TituloMaterialesGrillaResponse titulo = new TituloMaterialesGrillaResponse();
					titulo.setTitulo("pr_grillaFP21003".equals(procedureName) ? rs.getString("tituloCastellano") : rs.getString("tituloOriginal"));
					titulo.setContrato(rs.getInt("contrato"));
					titulo.setMas(rs.getInt("mas"));
					titulo.setClave(rs.getString("clave"));
					titulo.setEstrenoRepeticion(rs.getString("estrenoRepeticion"));
					titulo.setFormula(rs.getString("formula"));
					titulo.setGrupo(rs.getInt("grupo"));
					return titulo;
				}
			});
	}
	
	private SimpleJdbcCall buildDeterminarFamiliaTituloCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
			.withCatalogName("fil_pkg_fp210")
			.withProcedureName("pr_seleccionarFP21003")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					String pantalla = rs.getString("pantalla");
					return pantalla;
				}
			});
	}
	
	private SimpleJdbcCall buildAltaSoporteTituloCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
		.withCatalogName("fil_pkg_fp210")
		.withProcedureName("pr_F01AltaFP21003")
		.returningResultSet("P_CURSORERRORES", new RowMapper<Object>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String error = rs.getString("mensaje");
				return error;
			}
		})
		.returningResultSet("P_CURSOR", new RowMapper<Object>() {
			@Override
			public Map<BigDecimal, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
				String motivoEntrada = rs.getString("motivoEntrada");
				BigDecimal idRemito = rs.getBigDecimal("nroRtoInterno");
				Map<BigDecimal, String> remitoMotivoEntrada = new HashMap<BigDecimal, String>();
				remitoMotivoEntrada.put(idRemito, motivoEntrada);
				return remitoMotivoEntrada;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Distribuidor> buscarDistribuidoresParaMateriales(Integer codigoDistribuidor, String razonSocialDistribuidor) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_codigo", codigoDistribuidor)
														   .addValue("p_razon", razonSocialDistribuidor);

		Map<String, Object> out = this.buscarDistribuidoresCall.execute(in);
		return (List<Distribuidor>) out.get("P_CURSOR");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TituloMaterialesGrillaResponse> buscarTitulosCastellanoParaMateriales(String senial, String tipoMaterial, Integer codigoDistribuidor, String descripcionTitulo) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_descripcion", descripcionTitulo)
														   .addValue("p_senial", senial)
														   .addValue("p_V02MatCont", tipoMaterial)
														   .addValue("p_codigoDist", codigoDistribuidor);
		
		Map<String, Object> out = this.buscarTitulosCastCall.execute(in);
		return (List<TituloMaterialesGrillaResponse>) out.get("P_CURSOR");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TituloMaterialesGrillaResponse> buscarTitulosOriginalParaMateriales(String senial, String tipoMaterial, Integer codigoDistribuidor, String descripcionTitulo) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_descripcion", descripcionTitulo)
														   .addValue("p_senial", senial)
														   .addValue("p_V02MatCont", tipoMaterial)
														   .addValue("p_codigoDist", codigoDistribuidor);
		
		Map<String, Object> out = this.buscarTitulosOriginalCall.execute(in);
		return (List<TituloMaterialesGrillaResponse>) out.get("P_CURSOR");
	}

	@SuppressWarnings("unchecked")
	@Override
	public String determinarFamiliaTitulo(String tipoTituloSeleccionado) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_tipoTitulo", tipoTituloSeleccionado);

		Map<String, Object> out = this.determinarFamiliaTituloCall.execute(in);
		return ((List<String>) out.get("P_CURSOR")).get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Soporte> buscarSoportes(String codigoSoporte) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_codigo", codigoSoporte);
		
		Map<String, Object> out = this.buscarSoportesCall.execute(in);
		return (List<Soporte>) out.get("P_CURSOR");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void altaSoporteTitulo(SoporteTituloABM soporteTitulo) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_contrato", soporteTitulo.getContrato())
														   .addValue("p_grupo", soporteTitulo.getGrupo())
														   .addValue("p_senial", soporteTitulo.getSenial())
														   .addValue("p_clave", soporteTitulo.getClave())
														   .addValue("p_nroCap", soporteTitulo.getNroCap())
														   .addValue("p_nroParte", soporteTitulo.getNroParte())
														   .addValue("p_idProveedor", soporteTitulo.getIdProveedor())
														   .addValue("p_nroRemito", soporteTitulo.getNroRemito())
														   .addValue("p_nroGuia", soporteTitulo.getNroGuia())
														   .addValue("p_fechaRtoGuia", new Date(soporteTitulo.getFechaRtoGuia().getTime()))
														   .addValue("p_fechaMov", new Date(soporteTitulo.getFechaMov().getTime()))
														   .addValue("p_codSoporte", soporteTitulo.getCodSoporte())
														   .addValue("p_cantRollos", soporteTitulo.getCantRollos())
														   .addValue("p_requiereChequeo", soporteTitulo.getRequiereChequeo())
														   .addValue("p_carrete", soporteTitulo.getCarrete())
														   .addValue("p_lata", soporteTitulo.getLata())
														   .addValue("p_torta", soporteTitulo.getTorta())
														   .addValue("p_V02MatCont", soporteTitulo.getTipoMaterial())
														   .addValue("p_idRtoInterno", soporteTitulo.getIdRemito());

		Map<String, Object> out = this.altaSoporteTituloCall.execute(in);
		List<String> errores = ((List<String>) out.get("P_CURSORERRORES"));
		if (errores != null && errores.size() > 0 && errores.get(0) != null) {
			soporteTitulo.setErrores(errores.get(0));
		} else {
			soporteTitulo.setErrores(StringUtils.EMPTY);
			List<Map<BigDecimal, String>> listRemitoMotivo = ((List<Map<BigDecimal, String>>) out.get("P_CURSOR"));
			if (listRemitoMotivo != null && listRemitoMotivo.size() > 0) {
			    soporteTitulo.setMotivoIngreso(listRemitoMotivo.get(0).values().iterator().next());
			    soporteTitulo.setIdRemito(listRemitoMotivo.get(0).keySet().iterator().next());
			}
		}
	}

}