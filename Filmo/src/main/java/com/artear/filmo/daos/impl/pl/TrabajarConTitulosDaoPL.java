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

import com.artear.filmo.bo.trabajar.titulos.Actor;
import com.artear.filmo.bo.trabajar.titulos.CodDesc;
import com.artear.filmo.bo.trabajar.titulos.Contrato;
import com.artear.filmo.bo.trabajar.titulos.ContratoView;
import com.artear.filmo.bo.trabajar.titulos.DetalleContrato;
import com.artear.filmo.bo.trabajar.titulos.FichaCinematograficaView;
import com.artear.filmo.bo.trabajar.titulos.OperacionTitulo;
import com.artear.filmo.bo.trabajar.titulos.TituloABM;
import com.artear.filmo.bo.trabajar.titulos.TituloGrillaResponse;
import com.artear.filmo.bo.trabajar.titulos.TituloView;
import com.artear.filmo.daos.interfaces.TrabajarConTitulosDao;
import com.artear.filmo.seguridad.services.ServiciosSesionUsuario;

/**
 * 
 * @author flvaldes
 * 
 */
@Repository("trabajarConTitulosDao")
public class TrabajarConTitulosDaoPL implements TrabajarConTitulosDao {

	private SimpleJdbcCall buscarTitulosCastPorDescCall;
	private SimpleJdbcCall buscarTitulosCastPorCodigoCall;
	private SimpleJdbcCall buscarTitulosOriginalPorDescCall;
	private SimpleJdbcCall buscarTitulosOriginalPorCodigoCall;
	private SimpleJdbcCall ABMTituloCall;
	private SimpleJdbcCall altaActoresCall;
	private SimpleJdbcCall cargarTituloCall;
	private SimpleJdbcCall buscarActoresCall;
	private SimpleJdbcCall buscarContratosCall;
	private SimpleJdbcCall cargarContratoCall;
	private SimpleJdbcCall buscarTiposDeTitulosCall;
	private SimpleJdbcCall buscarCalificacionesOfialesCall;
	private SimpleJdbcCall buscarFichasCinematograficasCastCall;
	private SimpleJdbcCall buscarFichasCinematograficasOriginalCall;
	private SimpleJdbcCall cargarFichaCinematograficaCall;
	private SimpleJdbcCall cargarDetalleContratoCall;

	@Autowired
	private ServiciosSesionUsuario serviciosSesionUsuario;

	@Autowired
	public TrabajarConTitulosDaoPL(DataSource dataSource) {
		super();
		this.buscarTitulosCastPorDescCall = buildBuscarTitulosCall(dataSource,
				"pr_visualizar_titulos_cast");
		this.buscarTitulosCastPorCodigoCall = buildBuscarTitulosCall(
				dataSource, "pr_visualizar_codigo_cast");
		this.buscarTitulosOriginalPorDescCall = buildBuscarTitulosCall(
				dataSource, "pr_visualizar_titulos_ori");
		this.buscarTitulosOriginalPorCodigoCall = buildBuscarTitulosCall(
				dataSource, "pr_visualizar_codigo_ori");
		this.ABMTituloCall = buildABMTituloCall(dataSource);
		this.altaActoresCall = buildAltaActoresCall(dataSource);
		this.cargarTituloCall = buildBuscarTituloCall(dataSource);
		this.buscarActoresCall = buildBuscarActoresCall(dataSource);
		this.buscarContratosCall = buildBuscarContratosCall(dataSource);
		this.cargarContratoCall = buildBuscarContratoCall(dataSource);
		this.buscarTiposDeTitulosCall = buildBuscarTipoDeTitulosCall(dataSource);
		this.buscarCalificacionesOfialesCall = buildCodDescCall(dataSource,
				"pr_calificacion_oficial");
		this.buscarFichasCinematograficasCastCall = buildBuscarFichasCinematograficasCastCall(dataSource);
		this.buscarFichasCinematograficasOriginalCall = buildBuscarFichasCinematograficasOriginalCall(dataSource);
		this.cargarFichaCinematograficaCall = buildCargarFichaCinematograficaCall(dataSource);
		this.cargarDetalleContratoCall = buildCargarDetalleContratoCall(dataSource);
	}

	private SimpleJdbcCall buildCargarDetalleContratoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_TITULOS")
				.withProcedureName("pr_visualiza_detalle_contrato")
				.returningResultSet("P_CURSOR",
						new RowMapper<DetalleContrato>() {
							@Override
							public DetalleContrato mapRow(ResultSet rs,
									int rowNum) throws SQLException {
								DetalleContrato contrato = new DetalleContrato();
								contrato.setActor(rs.getString("actor"));
								contrato.setCalificacionOficial(rs
										.getString("calif_oficial"));
								contrato.setClave(rs.getString("clave"));
								contrato.setCodigo(rs.getInt("codigo"));
								contrato.setDescTipoTitulo(rs
										.getString("detalle_tipo_titulo"));
								contrato.setDistribuidor(rs
										.getString("id_prove_razon_soc"));
								contrato.setFecha(rs.getString("fecha"));
								contrato.setGrupo(rs.getInt("grupo"));
								contrato.setMonto(rs.getString("monto"));
								contrato.setNombreGrupo(rs
										.getString("nombre_grupo"));
								contrato.setObservacion(rs
										.getString("observacion"));
								contrato.setRecontratacion(rs
										.getString("recontratacion"));
								contrato.setSenial(rs.getString("senial"));
								contrato.setTipoTitulo(rs
										.getString("tipo_titulo"));
								contrato.setTituloCastellano(rs
										.getString("titulo_castellano"));
								contrato.setTituloOriginal(rs
										.getString("titulo_original"));
								return contrato;
							}
						});
	}

	private SimpleJdbcCall buildAltaActoresCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_TITULOS").withProcedureName(
						"pr_alta_actores_personajes");
	}

	private SimpleJdbcCall buildCargarFichaCinematograficaCall(
			DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_TITULOS")
				.withProcedureName("pr_obtener_ficha")
				.returningResultSet("P_CURSOR1",
						new RowMapper<FichaCinematograficaView>() {
							@Override
							public FichaCinematograficaView mapRow(
									ResultSet rs, int rowNum)
									throws SQLException {
								FichaCinematograficaView ficha = new FichaCinematograficaView();
								ficha.setTituloCastellano(rs
										.getString("TITULO_CAST"));
								ficha.setTituloOriginal(rs
										.getString("TITULO_ORI"));
								ficha.setCodigoCalificacion(rs
										.getString("CALIFICACION"));
								ficha.setDescripcionCalificacion(rs
										.getString("DESCRIPCION_CAL"));
								return ficha;
							}
						})
				.returningResultSet("P_CURSOR2", new RowMapper<Actor>() {
					@Override
					public Actor mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Actor actor = new Actor();
						actor.setActor(rs.getString("ACTORES"));
						actor.setPersonaje(rs.getString("PERSONAJES"));
						return actor;
					}
				});
	}

	private SimpleJdbcCall buildBuscarFichasCinematograficasOriginalCall(
			DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_TITULOS")
				.withProcedureName("pr_obtener_titulo_ficha_to")
				.returningResultSet("P_CURSOR", new RowMapper<CodDesc>() {
					@Override
					public CodDesc mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						CodDesc codDesc = new CodDesc();
						codDesc.setCodigo(rs.getString("ID"));
						codDesc.setDescripcion(rs.getString("TITULO_ORI"));
						return codDesc;
					}
				});
	}

	private SimpleJdbcCall buildBuscarFichasCinematograficasCastCall(
			DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_TITULOS")
				.withProcedureName("pr_obtener_titulo_ficha_tc")
				.returningResultSet("P_CURSOR", new RowMapper<CodDesc>() {
					@Override
					public CodDesc mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						CodDesc codDesc = new CodDesc();
						codDesc.setCodigo(rs.getString("ID"));
						codDesc.setDescripcion(rs.getString("TITULO_CAST"));
						return codDesc;
					}
				});
	}

	private SimpleJdbcCall buildCodDescCall(DataSource dataSource,
			String procedureName) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_TITULOS")
				.withProcedureName(procedureName)
				.returningResultSet("P_CURSOR", new RowMapper<CodDesc>() {
					@Override
					public CodDesc mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						CodDesc codDesc = new CodDesc();
						codDesc.setCodigo(rs.getString("CODIGO"));
						codDesc.setDescripcion(rs.getString("DESCRIPCION"));
						return codDesc;
					}
				});
	}

	private SimpleJdbcCall buildBuscarTipoDeTitulosCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_TITULOS")
				.withProcedureName("pr_listar_tipo_titulo")
				.returningResultSet("P_CURSOR", new RowMapper<CodDesc>() {
					@Override
					public CodDesc mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						CodDesc tipoTitulo = new CodDesc();
						tipoTitulo.setCodigo(rs.getString("TITULO"));
						tipoTitulo.setDescripcion(rs.getString("DESCRIPCION"));
						return tipoTitulo;
					}
				});
	}

	private SimpleJdbcCall buildBuscarContratoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_TITULOS")
				.withProcedureName("pr_visualizar_titulo_contra")
				.returningResultSet("P_CURSOR", new RowMapper<ContratoView>() {
					@Override
					public ContratoView mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						ContratoView contrato = new ContratoView();
						contrato.setCodigo(rs.getString("CONTRATO"));
						contrato.setProveedor(rs.getString("PROVEEDOR"));
						contrato.setRazonSocial(rs.getString("RAZON_SOCIAL"));
						contrato.setImporteTotal(rs.getString("IMPORTE_TOTAL"));
						contrato.setGrupo(rs.getString("GRUPO"));
						contrato.setCodTipoTitulo(rs.getString("TIPO_TITULO"));
						contrato.setDescTipoTitulo(rs
								.getString("DESC_TIPO_TITULO"));
						contrato.setNombreGrupo(rs.getString("NOM_GRUPO"));
						contrato.setClave(rs.getString("CLAVE"));
						contrato.setTituloOriginal(rs.getString("TIT_ORI"));
						contrato.setTituloCastellano(rs.getString("TIT_CAS"));
						contrato.setActores(rs.getString("ACTORES"));
						contrato.setCodCalificacionOficial(rs
								.getString("CALIF_OFICIAL"));
						contrato.setDescCalificacionOficial(rs
								.getString("CALIF_DESC"));
						contrato.setObservaciones(rs.getString("OBSERVACIONES"));
						contrato.setRecontrataciones(rs
								.getString("RECONTRATACIONES"));
						contrato.setPerpetuidad(rs.getString("PERPETUIDAD"));
						contrato.setSenial_cont(rs.getString("SENIAL_CONT"));
						
						return contrato;
					}
				});
	}

	private SimpleJdbcCall buildBuscarTituloCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_TITULOS")
				.withProcedureName("pr_visualizar_titulo")
				.returningResultSet("P_CURSOR", new RowMapper<TituloView>() {
					@Override
					public TituloView mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						TituloView titulo = new TituloView();
						titulo.setCodigoTipo(rs.getString("TIPO_TITULO"));
						titulo.setDescripcionTipo(rs.getString("DESCRIPCION"));
						titulo.setClave(rs.getString("CLAVE"));
						titulo.setTituloOriginal(rs.getString("TITULO_ORIG"));
						titulo.setTituloCastellano(rs.getString("TITULO_CAS"));
						titulo.setCodigoCalificacion(rs
								.getString("CODIGO_CALIFICACION"));
						titulo.setDescripcionCalificacion(rs
								.getString("DESCRIPCION_CALIFICACION"));
						return titulo;
					}
				});
	}

	private SimpleJdbcCall buildBuscarContratosCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_TITULOS")
				.withProcedureName("pr_visualizar_titulo_cont")
				.returningResultSet("P_CURSOR", new RowMapper<Contrato>() {
					@Override
					public Contrato mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Contrato contrato = new Contrato();
						contrato.setCodigo(rs.getInt("CODIGO"));
						contrato.setGrupo(rs.getInt("GRUPO"));
						contrato.setFecha(rs.getString("FECHA"));
						contrato.setSenial(rs.getString("SENIAL"));
						return contrato;
					}
				});
	}

	private SimpleJdbcCall buildBuscarActoresCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_TITULOS")
				.withProcedureName("pr_listar_actores")
				.returningResultSet("P_CURSOR", new RowMapper<Actor>() {
					@Override
					public Actor mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Actor actor = new Actor();
						actor.setActor(rs.getString("ACTORES"));
						actor.setPersonaje(rs.getString("PERSONAJES"));
						return actor;
					}
				});
	}

	private SimpleJdbcCall buildABMTituloCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_TITULOS")
				.withProcedureName("pr_grabar_titulo");
	}

	private SimpleJdbcCall buildBuscarTitulosCall(DataSource dataSource,
			String procedureName) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_TITULOS")
				.withProcedureName(procedureName)
				.returningResultSet("P_CURSOR",
						new RowMapper<TituloGrillaResponse>() {
							@Override
							public TituloGrillaResponse mapRow(ResultSet rs,
									int rowNum) throws SQLException {
								TituloGrillaResponse titulo = new TituloGrillaResponse();
								titulo.setClave(rs.getString("CLAVE"));
								titulo.setTitulo(rs.getString("TITULO"));
								titulo.setActores(rs.getString("ACTORES"));
								titulo.setSigno(rs.getString("SIGNO"));
								titulo.setContrato(rs
										.getString("EXISTE_TITULO"));
								titulo.setRecibido(rs.getString("RECIBIDO"));
								return titulo;
							}
						});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TituloGrillaResponse> obtenerTitulosCastellanoPorDescripcion(
			String senial, String descripcion) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"p_descripcion", descripcion).addValue("p_senial", senial);
		Map<String, Object> out = this.buscarTitulosCastPorDescCall.execute(in);
		return (List<TituloGrillaResponse>) out.get("P_CURSOR");
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TituloGrillaResponse> obtenerTitulosOriginalPorDescripcion(
			String senial, String descripcion) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"p_descripcion", descripcion).addValue("p_senial", senial);
		Map<String, Object> out = this.buscarTitulosOriginalPorDescCall
				.execute(in);
		return (List<TituloGrillaResponse>) out.get("P_CURSOR");
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TituloGrillaResponse> obtenerTitulosCastellanoPorCodigo(
			String senial, String codigo) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"p_descripcion", codigo).addValue("p_senial", senial);
		Map<String, Object> out = this.buscarTitulosCastPorCodigoCall
				.execute(in);
		return (List<TituloGrillaResponse>) out.get("P_CURSOR");
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TituloGrillaResponse> obtenerTitulosOriginalPorCodigo(
			String senial, String codigo) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"p_descripcion", codigo).addValue("p_senial", senial);
		Map<String, Object> out = this.buscarTitulosOriginalPorCodigoCall
				.execute(in);
		return (List<TituloGrillaResponse>) out.get("P_CURSOR");
	}

	@Override
	public void abmTitulo(TituloABM titulo, OperacionTitulo operacion) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("P_PARAM", operacion.getDescripcion())
				.addValue("P_SENIAL", titulo.getSenial())
				.addValue("P_TIPO_TITULO", titulo.getTipoTitulo())
				.addValue("P_CLAVE", titulo.getClave())
				.addValue("P_TITULO_OR", titulo.getTituloOriginal())
				.addValue("P_TITULO_CAS", titulo.getTituloCastellano())
				.addValue("P_CALIFICACION_OF", titulo.getCalificacionOficial())
				.addValue("P_ACTOR1", titulo.getActor1())
				.addValue("P_ACTOR2", titulo.getActor2())
				.addValue("P_PERSONAJE1", titulo.getPersonaje1())
				.addValue("P_PERSONAJE2", titulo.getPersonaje2())
				.addValue("P_USER", serviciosSesionUsuario.getUsuario());
		Map<String, Object> out = this.ABMTituloCall.execute(in);
		if (operacion.equals(OperacionTitulo.ALTA)) {
			titulo.setClave((String) out.get("P_NUEVA_CLAVE"));
		}
		this.guardarActores(titulo);
	}

	private void guardarActores(TituloABM titulo) {
		List<Actor> actores = titulo.getFixActores();
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("P_CLAVE", titulo.getClave())
				.addValue("P_ACTORES", titulo.getActoresConcatenados(actores))
				.addValue("P_PERSONAJES",
						titulo.getPersonajesConcatenados(actores));
		this.altaActoresCall.execute(in);
	}

	@SuppressWarnings("unchecked")
	private List<Actor> buscarActores(String clave) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CLAVE",
				clave);
		Map<String, Object> out = this.buscarActoresCall.execute(in);
		return (List<Actor>) out.get("P_CURSOR");
	}

	@Override
	@SuppressWarnings("unchecked")
	public TituloView cargarTitulo(String senial, String clave) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CLAVE",
				clave).addValue("P_SENIAL", senial);
		Map<String, Object> out = this.cargarTituloCall.execute(in);
		TituloView titulo = ((List<TituloView>) out.get("P_CURSOR")).get(0);
		titulo.setActores(buscarActores(titulo.getClave()));
		return titulo;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Contrato> obtenerContratos(String senial, String clave,
			Integer contrato) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("P_CLAVE", clave).addValue("P_CONTRATO", contrato)
				.addValue("P_SENIAL", senial);
		Map<String, Object> out = this.buscarContratosCall.execute(in);
		return (List<Contrato>) out.get("P_CURSOR");
	}

	@SuppressWarnings("unchecked")
	@Override
	public ContratoView cargarContrato(String senial, String clave,
			Integer contrato) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("P_CLAVE", clave).addValue("P_CONTRATO", contrato)
				.addValue("P_SENIAL", senial);
		Map<String, Object> out = this.cargarContratoCall.execute(in);
		return ((List<ContratoView>) out.get("P_CURSOR")).get(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CodDesc> obtenerTiposDeTitulos(String descripcion) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"P_DESCRIPCION", descripcion);
		Map<String, Object> out = this.buscarTiposDeTitulosCall.execute(in);
		return (List<CodDesc>) out.get("P_CURSOR");
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CodDesc> obtenerCalificacionesOficiales(String descripcion) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"P_DESCRIPCION", descripcion);
		Map<String, Object> out = this.buscarCalificacionesOfialesCall
				.execute(in);
		return (List<CodDesc>) out.get("P_CURSOR");
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CodDesc> obtenerFichasCinematograficasCast(String descripcion) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"P_DESCRIPCION", descripcion);
		Map<String, Object> out = this.buscarFichasCinematograficasCastCall
				.execute(in);
		return (List<CodDesc>) out.get("P_CURSOR");
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CodDesc> obtenerFichasCinematograficasOriginal(
			String descripcion) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"P_DESCRIPCION", descripcion);
		Map<String, Object> out = this.buscarFichasCinematograficasOriginalCall
				.execute(in);
		return (List<CodDesc>) out.get("P_CURSOR");
	}

	@SuppressWarnings("unchecked")
	@Override
	public FichaCinematograficaView cargarFichaCinematografica(String codigo) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_FICHA",
				codigo);
		Map<String, Object> out = this.cargarFichaCinematograficaCall
				.execute(in);
		FichaCinematograficaView ficha = ((List<FichaCinematograficaView>) out
				.get("P_CURSOR1")).get(0);
		ficha.setActores((List<Actor>) out.get("P_CURSOR2"));
		return ficha;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DetalleContrato cargarDetalleContrato(String clave,
			Integer contrato, String senial) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_clave", clave).addValue("p_contrato", contrato)
				.addValue("p_senial", senial);
		Map<String, Object> out = this.cargarDetalleContratoCall.execute(in);
		return ((List<DetalleContrato>) out.get("P_CURSOR")).get(0);
	}
}
