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

import com.artear.filmo.bo.common.AyudaSituar;
import com.artear.filmo.bo.trabajar.copias.BajaRequest;
import com.artear.filmo.bo.trabajar.copias.BuscarTitulosRequest;
import com.artear.filmo.bo.trabajar.copias.Capitulo;
import com.artear.filmo.bo.trabajar.copias.CopiaListado;
import com.artear.filmo.bo.trabajar.copias.MasterCC;
import com.artear.filmo.bo.trabajar.copias.MasterSC;
import com.artear.filmo.bo.trabajar.copias.ModifRequest;
import com.artear.filmo.bo.trabajar.copias.RolloCC;
import com.artear.filmo.bo.trabajar.copias.RolloSC;
import com.artear.filmo.bo.trabajar.copias.TituloListado;
import com.artear.filmo.bo.trabajar.copias.ValidarRequest;
import com.artear.filmo.bo.trabajar.copias.ValidarResponse;
import com.artear.filmo.daos.interfaces.TrabajarConCopiasDeTitulosDao;

/**
 * 
 * @author flvaldes
 * 
 */
@Repository("trabajarConCopiasDeTitulosDao")
public class TrabajarConCopiasDeTitulosDaoPL implements
		TrabajarConCopiasDeTitulosDao {

	private SimpleJdbcCall buscarTitulosCall;
	private SimpleJdbcCall buscarDatosCopiasCall;
	private SimpleJdbcCall altaRolloCCCall;
	private SimpleJdbcCall altaRolloSCCall;
	private SimpleJdbcCall altaMasterCCCall;
	private SimpleJdbcCall altaMasterSCCall;
	private SimpleJdbcCall eliminarCall;
	private SimpleJdbcCall buscarSoportesCall;
	private SimpleJdbcCall buscarCapitulosCall;
	private SimpleJdbcCall verificarMasterSoporteCCCall; // FIXME: borrar
	private SimpleJdbcCall verificarMasterSoporteSCCall; // FIXME: borrar
	private SimpleJdbcCall verificarRechazoCVRSCCall; // FIXME: borrar
	private SimpleJdbcCall chequearMaterialesCopiaCall;
	private SimpleJdbcCall validarAltaCall;
	private SimpleJdbcCall modificarCall;
	private SimpleJdbcCall validarBajaCall;

	@Autowired
	public TrabajarConCopiasDeTitulosDaoPL(DataSource dataSource) {
		super();
		this.buscarTitulosCall = buildBuscarTitulosCall(dataSource);
		this.buscarDatosCopiasCall = buildBuscarDatosCopiasCall(dataSource);
		this.altaRolloCCCall = buildBasicCall(dataSource, "pr_alta_rollo_cc");
		this.altaRolloSCCall = buildBasicCall(dataSource, "pr_alta_rollo_sc");
		this.altaMasterCCCall = buildBasicCall(dataSource,
				"pr_alta_con_capitulo_master");
		this.altaMasterSCCall = buildBasicCall(dataSource,
				"pr_alta_sin_capitulo_master");
		this.eliminarCall = buildBasicCall(dataSource, "pr_eliminar");
		this.buscarSoportesCall = buildBuscarSoportesCall(dataSource);
		this.buscarCapitulosCall = buildBuscarCapitulosCall(dataSource);
		// this.verificarMasterSoporteCCCall = buildBasicCall(dataSource,
		// "pr_verificar_master_soporte_cc");
		// this.verificarMasterSoporteSCCall = buildBasicCall(dataSource,
		// "pr_verificar_master_soporte_sc");
		// this.verificarRechazoCVRSCCall = buildBasicCall(dataSource,
		// "pr_verifcar_rechazo_cvr");
		this.chequearMaterialesCopiaCall = buildBasicCall(dataSource,
				"pr_chequear_materiales_copia");
		this.validarAltaCall = buildBasicCall(dataSource, "pr_validar_alta");
		this.modificarCall = buildBasicCall(dataSource, "pr_modificar");
		this.validarBajaCall = buildBasicCall(dataSource, "pr_validar_eliminar");
	}

	private SimpleJdbcCall buildBuscarCapitulosCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName("fil_pkg_copia")
				.withProcedureName("pr_listar_capitulos")
				.returningResultSet("p_cursor", new RowMapper<Capitulo>() {
					@Override
					public Capitulo mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Capitulo response = new Capitulo();
						response.setCapitulo(rs.getInt("capitulo"));
						response.setParte(rs.getInt("parte"));
						response.setSegmento(rs.getInt("segmento"));
						return response;
					}
				});
	}

	private SimpleJdbcCall buildBuscarSoportesCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName("fil_pkg_copia")
				.withProcedureName("pr_busqueda_soporte")
				.returningResultSet("p_cursor", new RowMapper<AyudaSituar>() {
					@Override
					public AyudaSituar mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						AyudaSituar response = new AyudaSituar();
						response.setCodigo(rs.getString("codigo"));
						response.setDescripcion(rs.getString("descripcion"));
						return response;
					}
				});
	}

	private SimpleJdbcCall buildBasicCall(DataSource dataSource, String name) {
		return new SimpleJdbcCall(dataSource).withCatalogName("fil_pkg_copia")
				.withProcedureName(name);
	}

	private SimpleJdbcCall buildBuscarDatosCopiasCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName("fil_pkg_copia")
				.withProcedureName("pr_consuta_datos_copia")
				.returningResultSet("p_cursor", new RowMapper<CopiaListado>() {
					@Override
					public CopiaListado mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						CopiaListado response = new CopiaListado();
						response.setCapitulo(rs.getInt("capitulo"));
						response.setParte(rs.getInt("parte"));
						response.setSegmento(rs.getInt("segmento"));
						response.setCopia(rs.getInt("copia"));
						response.setFecha(rs.getDate("fecha"));
						response.setMaster(rs.getString("origen_master"));
						response.setRollo(rs.getInt("rollo"));
						response.setSoporte(rs.getString("soporte"));
						response.setSecuencia(rs.getInt("secuencia"));
						return response;
					}
				});
	}

	private SimpleJdbcCall buildBuscarTitulosCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName("fil_pkg_copia")
				.withProcedureName("pr_listar_cabecera")
				.returningResultSet("p_cursor", new RowMapper<TituloListado>() {
					@Override
					public TituloListado mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						TituloListado response = new TituloListado();
						response.setClave(rs.getString("clave"));
						response.setTituloOriginal(rs
								.getString("titulo_original"));
						response.setTituloCastellano(rs
								.getString("titulo_castellano"));
						response.setActores(rs.getString("actores"));
						response.setTieneCopia(rs.getString("tiene_copias"));
						response.setVigente(rs.getString("vigencia"));
						response.setSigno(rs.getString("signo"));
						return response;
					}
				});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TituloListado> buscarTitulos(BuscarTitulosRequest request) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_senial", request.getSenial())
				.addValue("p_vigentes", request.getVigente())
				.addValue("p_copia", request.getTieneCopia())
				.addValue("p_clave", request.getClaveOrNull())
				.addValue("p_tipo", request.getTipoBusqueda().getCod())
				.addValue("p_descripcion", request.getTitulo());
		Map<String, Object> out = this.buscarTitulosCall.execute(in);
		return (List<TituloListado>) out.get("p_cursor");
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CopiaListado> buscarDatosCopias(String senial, String clave) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"p_senial", senial).addValue("p_clave", clave);
		Map<String, Object> out = this.buscarDatosCopiasCall.execute(in);
		return (List<CopiaListado>) out.get("p_cursor");
	}

	@Override
	public void altaCopiasRolloSC(RolloSC rollo) {
		MapSqlParameterSource in = null;
		for (Integer nroRollo : rollo.getRollos()) {
			if (nroRollo != null) {
				in = fillMapMaster(rollo);
				in.addValue("p_numero_rollo", nroRollo);
				this.altaRolloSCCall.execute(in);
			}
		}
	}

	@Override
	public void altaCopiasRolloCC(RolloCC rollo) {
		MapSqlParameterSource in = null;
		for (Integer nroRollo : rollo.getRollos()) {
			if (nroRollo != null) {
				in = fillMapMaster(rollo);
				in.addValue("p_numero_rollo", nroRollo)
						.addValue("p_capitulo", rollo.getCapitulo())
						.addValue("p_parte", rollo.getParte())
						.addValue("p_sgto", rollo.getSegmento());
				this.altaRolloCCCall.execute(in);
			}
		}
	}

	@Override
	public void altaMasterSC(MasterSC masterSC) {
		SqlParameterSource in = fillMapMaster(masterSC);
		this.altaMasterSCCall.execute(in);
	}

	@Override
	public void altaMasterCC(MasterCC master) {
		MapSqlParameterSource in = fillMapMaster(master);
		in.addValue("p_capitulo", master.getCapitulo())
				.addValue("p_parte", master.getParte())
				.addValue("p_sgto", master.getSegmento());
		this.altaMasterCCCall.execute(in);
	}

	private MapSqlParameterSource fillMapMaster(MasterSC masterSC) {
		return new MapSqlParameterSource()
				.addValue("p_senial", masterSC.getSenial())
				.addValue("p_clave", masterSC.getClave())
				.addValue("p_codigo_pantalla", masterSC.getSoporte())
				.addValue("p_fecha", masterSC.getFecha());
	}

	@Override
	public String bajaCopiaMaster(BajaRequest request) {
		SqlParameterSource in = new MapSqlParameterSource()
		.addValue("p_senial", request.getSenial())
		.addValue("p_clave", request.getClave())
		.addValue("p_numero_copia", request.getCopia())
		.addValue("p_numero_rollo", request.getRollo())
		.addValue("p_capitulo", request.getCapitulo())
		.addValue("p_parte", request.getParte())
		.addValue("p_segmento", request.getSegmento())
		.addValue("p_numero_secuencia", request.getSecuencia())
		.addValue("p_fecha", request.getFecha())
		.addValue("p_codigo_pantalla", request.getSoporte());
		Map<String, Object> out = this.eliminarCall.execute(in);
		return (String) out.get("p_respuesta");
	}
	
	@Override
	public ValidarResponse validarBaja(ValidarRequest data) {
		MapSqlParameterSource in = new MapSqlParameterSource()
		.addValue("p_senial", data.getSenial())
		.addValue("p_clave", data.getClave())
		.addValue("p_numero_secuencia", data.getSecuencia())
		.addValue("p_numero_copia", data.getMasterOCopia())
		.addValue("p_capitulo", data.getCapitulo())
		.addValue("p_parte", data.getParte())
		.addValue("p_segmento", data.getSegmento());
		Map<String, Object> out = this.validarBajaCall.execute(in);
		return new ValidarResponse((String) out.get("P_TIPO"),
				(String) out.get("P_MENSAJE"));
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<AyudaSituar> buscarSoportes(String descripcion) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"p_descripcion", descripcion);
		Map<String, Object> out = this.buscarSoportesCall.execute(in);
		return (List<AyudaSituar>) out.get("p_cursor");
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Capitulo> buscarCapitulos(String clave) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_clave",
				clave);
		Map<String, Object> out = this.buscarCapitulosCall.execute(in);
		return (List<Capitulo>) out.get("p_cursor");
	}

	@Override
	public String chequearMaterialesCopia(String clave, String senial) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_clave",
				clave).addValue("p_senial", senial);
		Map<String, Object> out = this.chequearMaterialesCopiaCall.execute(in);
		return (String) out.get("P_RESPUESTA");
	}

	@Override
	public ValidarResponse validarAlta(ValidarRequest data) {
		MapSqlParameterSource in = new MapSqlParameterSource()
		.addValue("p_senial", data.getSenial())
		.addValue("p_clave", data.getClave())
		.addValue("p_codigo_pantalla", data.getSoporte())
		.addValue("p_fecha", data.getFecha())
		.addValue("p_copia_master", data.getMasterOCopia())
		.addValue("p_capitulo", data.getCapitulo())
		.addValue("p_parte", data.getParte())
		.addValue("p_segmento", data.getSegmento());
		Map<String, Object> out = this.validarAltaCall.execute(in);
		return new ValidarResponse((String) out.get("P_TIPO"),
				(String) out.get("P_MENSAJE"));
	}
	
	@Override
	public void modificarCopiaMaster(ModifRequest request) {
		MapSqlParameterSource in = new MapSqlParameterSource()
		.addValue("p_senial", request.getSenial())
		.addValue("p_clave", request.getClave())
		.addValue("p_fecha", request.getFecha())
		.addValue("p_numero_rollo", request.getRollo())
		.addValue("p_codigo_pantalla", request.getSoporte())
		.addValue("p_numero_copia", request.getCopia())
		.addValue("p_numero_secuencia", request.getSecuencia())
		.addValue("p_capitulo", request.getCapitulo())
		.addValue("p_parte", request.getParte())
		.addValue("p_segmento", request.getSegmento());
		this.modificarCall.execute(in);
	}
	
	// FIXME: borrar
	@Override
	public String verificarMasterSoporteSC(MasterSC master) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_clave", master.getClave())
				.addValue("p_senial", master.getSenial())
				.addValue("p_codigo", master.getSoporte());
		Map<String, Object> out = this.verificarMasterSoporteSCCall.execute(in);
		return (String) out.get("P_RESPUESTA");
	}

	@Override
	public String verificarMasterSoporteCC(MasterCC master) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_clave", master.getClave())
				.addValue("p_senial", master.getSenial())
				.addValue("p_codigo", master.getSoporte())
				.addValue("p_capitulo", master.getCapitulo())
				.addValue("p_parte", master.getParte())
				.addValue("p_sgto", master.getSegmento());
		Map<String, Object> out = this.verificarMasterSoporteCCCall.execute(in);
		return (String) out.get("P_RESPUESTA");
	}

	@Override
	public String verificarRechazoCVR(String clave, String senial,
			String soporte) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_clave", clave).addValue("p_senial", senial)
				.addValue("p_codigo", soporte);
		Map<String, Object> out = this.verificarRechazoCVRSCCall.execute(in);
		return (String) out.get("P_RESPUESTA");
	}

}
