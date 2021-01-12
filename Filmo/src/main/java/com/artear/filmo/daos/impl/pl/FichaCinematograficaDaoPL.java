package com.artear.filmo.daos.impl.pl;

import java.math.BigDecimal;
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
import com.artear.filmo.bo.ficha.cinematografica.ActorResponse;
import com.artear.filmo.bo.ficha.cinematografica.AyudaFichas;
import com.artear.filmo.bo.ficha.cinematografica.FichaBasicaResponse;
import com.artear.filmo.bo.ficha.cinematografica.FichaCompleta;
import com.artear.filmo.bo.ficha.cinematografica.FichaListadoResponse;
import com.artear.filmo.bo.ficha.cinematografica.SinopsisResponse;
import com.artear.filmo.constants.TipoBusquedaTitulo;
import com.artear.filmo.daos.interfaces.FichaCinematograficaDao;

/**
 * 
 * @author flvaldes
 * 
 */
@Repository("fichaCinematograficaDao")
public class FichaCinematograficaDaoPL implements FichaCinematograficaDao {

	private SimpleJdbcCall buscarFichasCall;
	private SimpleJdbcCall obtenerFichaCall;
	private SimpleJdbcCall obtenerActoresCall;
	private SimpleJdbcCall obtenerSinopsisCall;
	private SimpleJdbcCall eliminarFichaCall;
	private SimpleJdbcCall actualizarFichaCall;
	private SimpleJdbcCall actualizarSinopsisCall;
	private SimpleJdbcCall insertarFichaCall;
	private SimpleJdbcCall insertarActoresCall;
	private SimpleJdbcCall ayudaCCCall;
	private SimpleJdbcCall ayudaCACall;
	private SimpleJdbcCall ayudaCMCall;
	private SimpleJdbcCall ayudaGECall;

	@Autowired
	public FichaCinematograficaDaoPL(DataSource dataSource) {
		super();
		this.buscarFichasCall = buildBuscarFichasCall(dataSource);
		this.obtenerFichaCall = buildObtenerFichaCall(dataSource);
		this.obtenerActoresCall = buildObtenerActoresCall(dataSource);
		this.obtenerSinopsisCall = buildObtenerSinopsisCall(dataSource);
		this.eliminarFichaCall = buildEliminarFichaCall(dataSource);
		this.actualizarFichaCall = buildActualizarFichaCall(dataSource);
		this.actualizarSinopsisCall = buildActualizarSinopsisCall(dataSource);
		this.insertarFichaCall = buildInsertarFichaCall(dataSource);
		this.insertarActoresCall = buildInsertarActoresCall(dataSource);
		this.ayudaCCCall = buildAyuda(dataSource, "PR_AYUDA_SITUAR_CC");
		this.ayudaCACall = buildAyuda(dataSource, "PR_AYUDA_SITUAR_CA");
		this.ayudaCMCall = buildAyuda(dataSource, "PR_AYUDA_SITUAR_CM");
		this.ayudaGECall = buildAyuda(dataSource, "PR_AYUDA_SITUAR_GE");
	}

	private SimpleJdbcCall buildAyuda(DataSource dataSource, String procedure) {
		return new SimpleJdbcCall(dataSource).withCatalogName(
				"FIL_PKG_FICHA_CINEMA").withProcedureName(procedure).returningResultSet("P_DATOS",
				new RowMapper<AyudaSituar>() {
					@Override
					public AyudaSituar mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						AyudaSituar response = new AyudaSituar();
						response.setCodigo(rs.getString("ID"));
						response.setDescripcion(rs.getString("DESCRIPCION"));
						return response;
					}
				});
	}

	private SimpleJdbcCall buildInsertarFichaCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(
				"FIL_PKG_FICHA_CINEMA").withProcedureName(
				"PR_INSERTAR_FICHA_CINEMA");
	}

	private SimpleJdbcCall buildInsertarActoresCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(
				"FIL_PKG_FICHA_CINEMA")
				.withProcedureName("PR_INSERTAR_ACTORES");
	}

	private SimpleJdbcCall buildActualizarFichaCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(
				"FIL_PKG_FICHA_CINEMA").withProcedureName(
				"PR_ACTUALIZAR_FICHA_CINEMA");
	}

	private SimpleJdbcCall buildActualizarSinopsisCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(
				"FIL_PKG_FICHA_CINEMA").withProcedureName(
				"PR_ACTUALIZAR_SINOPSIS");
	}

	private SimpleJdbcCall buildEliminarFichaCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(
				"FIL_PKG_FICHA_CINEMA").withProcedureName(
				"PR_BORRAR_FICHA_CINEMA");
	}

	private SimpleJdbcCall buildObtenerSinopsisCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_FICHA_CINEMA")
				.withProcedureName("PR_OBTENER_SINOPSIS")
				.returningResultSet("P_DATOS",
						new RowMapper<SinopsisResponse>() {
							@Override
							public SinopsisResponse mapRow(ResultSet rs,
									int rowNum) throws SQLException {
								SinopsisResponse response = new SinopsisResponse();
								response.setRenglon(rs.getInt("ID_RENGLON"));
								response.setDetalle(rs.getString("DETALLE"));
								return response;
							}
						});
	}

	private SimpleJdbcCall buildObtenerActoresCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_FICHA_CINEMA")
				.withProcedureName("PR_OBTENER_ACTORES")
				.returningResultSet("P_DATOS", new RowMapper<ActorResponse>() {
					@Override
					public ActorResponse mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						ActorResponse response = new ActorResponse();
						response.setIdActor(rs.getInt("ID_ACTOR"));
						response.setActor(rs.getString("NOMBRE_ACTOR"));
						response.setPersonaje(rs.getString("NOMBRE_PERSONAJE"));
						return response;
					}
				});
	}

	private SimpleJdbcCall buildObtenerFichaCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_FICHA_CINEMA")
				.withProcedureName("PR_OBTENER_FICHA_CINEMA")
				.returningResultSet("P_DATOS",
						new RowMapper<FichaBasicaResponse>() {
							@Override
							public FichaBasicaResponse mapRow(ResultSet rs,
									int rowNum) throws SQLException {
								FichaBasicaResponse response = new FichaBasicaResponse();
								response.setAnioProduccion(rs
										.getInt("ANIO_PRODUCCION"));
								response.setAutor(rs.getString("AUTOR"));
								response.setCalifArtisticaDesc(rs
										.getString("descrip_artistica"));
								response.setCalifComercialDesc(rs
										.getString("descrip_comercial"));
								response.setCalifMoralDesc(rs
										.getString("descrip_moral"));
								response.setCalifArtisticaCod(rs
										.getString("id_calif_artistica"));
								response.setCalifComercialCod(rs
										.getString("id_calif_comercial"));
								response.setCalifMoralCod(rs
										.getString("id_calif_moral"));
								response.setColor(rs.getString("color"));
								response.setDirector(rs.getString("director"));
								response.setDuracion(rs.getInt("duracion"));
								response.setFechaEstrenoCine(rs
										.getDate("fecha_estreno_cine"));
								response.setFechaEstrenoVideo(rs
										.getDate("fecha_estreno_video"));
								response.setGeneroCod(rs.getInt("id_genero"));
								response.setGeneroDesc(rs
										.getString("descrip_genero"));
								response.setIdFicha(rs.getBigDecimal("id_ficha"));
								response.setPais(rs.getString("pais"));
								response.setProductor(rs.getString("productor"));
								response.setSelloProductor(rs
										.getString("sello_productor"));
								response.setTituloCastellano(rs
										.getString("titulo_castellano"));
								response.setTituloOriginal(rs
										.getString("titulo_original"));
								return response;
							}
						});
	}

	private SimpleJdbcCall buildBuscarFichasCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_FICHA_CINEMA")
				.withProcedureName("PR_MOSTRAR_LISTA_DETALLE")
				.returningResultSet("P_DATOS",
						new RowMapper<FichaListadoResponse>() {
							@Override
							public FichaListadoResponse mapRow(ResultSet rs,
									int rowNum) throws SQLException {
								FichaListadoResponse response = new FichaListadoResponse();
								response.setTituloCastellano(rs
										.getString("TITULO_CASTELLANO"));
								response.setTituloOriginal(rs
										.getString("TITULO_ORIGINAL"));
								response.setActor1(rs.getString("ACTOR1"));
								response.setActor2(rs.getString("ACTOR2"));
								response.setIdFicha(rs.getInt("ID_FICHA"));
								return response;
							}
						});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<FichaListadoResponse> buscarFichas(TipoBusquedaTitulo tipo,
			String titulo) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_TIPO",
				tipo.getCod()).addValue("P_TITULO", titulo);
		Map<String, Object> out = this.buscarFichasCall.execute(in);
		return (List<FichaListadoResponse>) out.get("P_DATOS");
	}

	@Override
	public boolean eliminarFicha(Integer idFicha) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"P_ID_FICHA", idFicha);
		return ((String) this.eliminarFichaCall.execute(in).get("P_OK"))
				.equals("S");
	}

	@Override
	@SuppressWarnings("unchecked")
	public FichaCompleta obtenerFichaCompleta(Integer idFicha) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"P_ID_FICHA", idFicha);
		FichaBasicaResponse ficha = ((List<FichaBasicaResponse>) this.obtenerFichaCall
				.execute(in).get("P_DATOS")).get(0);
		List<ActorResponse> actores = (List<ActorResponse>) this.obtenerActoresCall
				.execute(in).get("P_DATOS");
		List<SinopsisResponse> sinopsis = (List<SinopsisResponse>) this.obtenerSinopsisCall
				.execute(in).get("P_DATOS");
		return new FichaCompleta(ficha, actores, sinopsis);
	}

	@Override
	public boolean modificarFicha(FichaCompleta ficha, String usuario) {
		SqlParameterSource in = buildMapFicha(ficha, usuario);
		boolean outFicha = ((String) this.actualizarFichaCall.execute(in).get(
				"P_OK")).equals("S");

		in = buildMapActores(ficha);
		boolean outActores = ((String) this.insertarActoresCall.execute(in)
				.get("P_OK")).equals("S");

		in = buildMapSinopsis(ficha);
		boolean outSinopsis = ((String) this.actualizarSinopsisCall.execute(in)
				.get("P_OK")).equals("S");

		return outActores && outFicha && outSinopsis;
	}

	@Override
	public boolean guardarFicha(FichaCompleta ficha, String usuario) {
		SqlParameterSource in = buildMapFicha(ficha, usuario);
		
		Map<String, Object> out = this.insertarFichaCall.execute(in);
		boolean outFicha = ((String) out.get("P_OK")).equals("S");
		ficha.getFicha().setIdFicha((BigDecimal) out.get("P_ID_FICHA"));
		
		in = buildMapActores(ficha);
		boolean outActores = ((String) this.insertarActoresCall.execute(in)
				.get("P_OK")).equals("S");

		in = buildMapSinopsis(ficha);
		boolean outSinopsis = ((String) this.actualizarSinopsisCall.execute(in)
				.get("P_OK")).equals("S");

		return outActores && outFicha && outSinopsis;
	}

	private MapSqlParameterSource buildMapSinopsis(FichaCompleta ficha) {
		return new MapSqlParameterSource().addValue("P_ID_FICHA",
				ficha.getFicha().getIdFicha()).addValue("P_SINOPSIS",
				ficha.getSinopsisJoined());
	}

	private MapSqlParameterSource buildMapActores(FichaCompleta ficha) {
		return new MapSqlParameterSource()
				.addValue("P_ID_FICHA", ficha.getFicha().getIdFicha())
				.addValue("P_ACTORES", ficha.getActoresJoined())
				.addValue("P_PERSONAJES", ficha.getPersonajesJoined());
	}

	private MapSqlParameterSource buildMapFicha(FichaCompleta ficha,
			String usuario) {
		return new MapSqlParameterSource()
				.addValue("P_ID_FICHA", ficha.getFicha().getIdFicha())
				.addValue("P_ID_CALIF_COMERCIAL",
						ficha.getFicha().getCalifComercialCod())
				.addValue("P_ID_CALIF_ARTISTICA",
						ficha.getFicha().getCalifArtisticaCod())
				.addValue("P_ID_CALIF_MORAL",
						ficha.getFicha().getCalifMoralCod())
				.addValue("P_ID_GENERO", ficha.getFicha().getGeneroCod())
				.addValue("P_TITULO_ORIGINAL",
						ficha.getFicha().getTituloOriginal())
				.addValue("P_TITULO_CASTELLANO",
						ficha.getFicha().getTituloCastellano())
				.addValue("P_ANIO_PRODUCCION",
						ficha.getFicha().getAnioProduccion())
				.addValue("P_SELLO_PRODUCTOR",
						ficha.getFicha().getSelloProductor())
				.addValue("P_AUTOR", ficha.getFicha().getAutor())
				.addValue("P_DIRECTOR", ficha.getFicha().getDirector())
				.addValue("P_PRODUCTOR", ficha.getFicha().getProductor())
				.addValue("P_COLOR", ficha.getFicha().getColor())
				.addValue("P_DURACION", ficha.getFicha().getDuracion())
				.addValue("P_FECHA_ESTRENO_CINE",
						ficha.getFicha().getFechaEstrenoCine())
				.addValue("P_FECHA_ESTRENO_VIDEO",
						ficha.getFicha().getFechaEstrenoVideo())
				.addValue("P_PAIS", ficha.getFicha().getPais())
				.addValue("P_USUARIO", usuario);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AyudaSituar> ayudaSituar(String descripcion,
			AyudaFichas ayudaFichas) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"P_SITUAR", descripcion);
		Map<String, Object> out = null;
		if (ayudaFichas.equals(AyudaFichas.CC)) {
			out = ayudaCCCall.execute(in);
		}else if (ayudaFichas.equals(AyudaFichas.CA)) {
			out = ayudaCACall.execute(in);
		}else if (ayudaFichas.equals(AyudaFichas.CM)) {
			out = ayudaCMCall.execute(in);
		}else if (ayudaFichas.equals(AyudaFichas.GE)) {
			out = ayudaGECall.execute(in);
		}
		return (List<AyudaSituar>) out.get("P_DATOS");
	}

}