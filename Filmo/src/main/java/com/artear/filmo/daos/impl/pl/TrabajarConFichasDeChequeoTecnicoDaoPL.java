package com.artear.filmo.daos.impl.pl;

import static com.artear.filmo.constants.TipoBusquedaTitulo.ORIGINAL;

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

import com.artear.filmo.bo.chequeo.tecnico.Actor;
import com.artear.filmo.bo.chequeo.tecnico.AltaModifFichaRequest;
import com.artear.filmo.bo.chequeo.tecnico.AltaModifRolloRequest;
import com.artear.filmo.bo.chequeo.tecnico.AsociarAContrato;
import com.artear.filmo.bo.chequeo.tecnico.BusquedaFichasRequest;
import com.artear.filmo.bo.chequeo.tecnico.BusquedaFichasResponse;
import com.artear.filmo.bo.chequeo.tecnico.Chequeo;
import com.artear.filmo.bo.chequeo.tecnico.ContratoExhibido;
import com.artear.filmo.bo.chequeo.tecnico.Ficha;
import com.artear.filmo.bo.chequeo.tecnico.FichaCompleta;
import com.artear.filmo.bo.chequeo.tecnico.GuardarActoresSinopsisObservRequest;
import com.artear.filmo.bo.chequeo.tecnico.GuardarSegmentoRequest;
import com.artear.filmo.bo.chequeo.tecnico.GuardarSenialesOkFilmRequest;
import com.artear.filmo.bo.chequeo.tecnico.InfoAdicionalPrograma;
import com.artear.filmo.bo.chequeo.tecnico.Observacion;
import com.artear.filmo.bo.chequeo.tecnico.Programa;
import com.artear.filmo.bo.chequeo.tecnico.ProgramaSituarResponse;
import com.artear.filmo.bo.chequeo.tecnico.Rollo;
import com.artear.filmo.bo.chequeo.tecnico.Segmento;
import com.artear.filmo.bo.chequeo.tecnico.SenialOkFilm;
import com.artear.filmo.bo.chequeo.tecnico.Sinopsis;
import com.artear.filmo.bo.chequeo.tecnico.TitulosConContratosExhibidosResponse;
import com.artear.filmo.bo.chequeo.tecnico.VerificarDesenlaceRequest;
import com.artear.filmo.bo.chequeo.tecnico.VerificarDesenlaceResponse;
import com.artear.filmo.bo.chequeo.tecnico.VigenciaContrato;
import com.artear.filmo.bo.chequeo.tecnico.VisualizarFichaRequest;
import com.artear.filmo.bo.common.AyudaSituar;
import com.artear.filmo.bo.common.ErrorResponse;
import com.artear.filmo.bo.common.Senial;
import com.artear.filmo.bo.contratos.ErrorDesenlaceResponse;
import com.artear.filmo.constants.TipoBusquedaTitulo;
import com.artear.filmo.daos.interfaces.TrabajarConFichasDeChequeoTecnicoDao;
import com.artear.filmo.utils.StringUtils;

/**
 * 
 * @author flvaldes
 * 
 */
@Repository("trabajarConFichasDeChequeoTecnicoDao")
public class TrabajarConFichasDeChequeoTecnicoDaoPL implements
		TrabajarConFichasDeChequeoTecnicoDao {

	private SimpleJdbcCall buscarFichasCall;
	private SimpleJdbcCall visualizarFichasCall;
	private SimpleJdbcCall buscarProgPorTitOriginalCall;
	private SimpleJdbcCall buscarProgPorTitCastellanoCall;
	private SimpleJdbcCall obtenerInfoAdicinalFichaCall;
	private SimpleJdbcCall altaModifFichaCall;
	private SimpleJdbcCall bajaFichaCall;
	private SimpleJdbcCall inactivarFichaCall;
	private SimpleJdbcCall infoVigenciaContratosCall;
	private SimpleJdbcCall validarTituloEnCanal;
	private SimpleJdbcCall generoCall;
	private SimpleJdbcCall calificacionArtisticaCall;
	private SimpleJdbcCall calificacionAutocontrolCall;
	private SimpleJdbcCall calificacionOficialCall;
	private SimpleJdbcCall eliminarSegmentosCall;
	private SimpleJdbcCall guardarSegmentosCall;
	private SimpleJdbcCall guardarActoresCall;
	private SimpleJdbcCall guardarSinopsisCall;
	private SimpleJdbcCall guardarObservacionesCall;
	private SimpleJdbcCall validarChequeoDetalleCall;
	private SimpleJdbcCall guardarChequeoDetalleCall;
	private SimpleJdbcCall buscarSenialesOkFilmCall;
	private SimpleJdbcCall guardarSenialesOkFilmCall;
	private SimpleJdbcCall limpiarSegmentosCall;
	private SimpleJdbcCall popupTitulosConContratosExhibidosCall;
	private SimpleJdbcCall popupTitulosConContratosExhibidosSNCall;
	private SimpleJdbcCall popupTitulosConContratosExhibidosRechazoCall;
	private SimpleJdbcCall popupAsocCopiaContratosCall;
	private SimpleJdbcCall ayudaSenialesCall;
	private SimpleJdbcCall ayudaSoporteCall;
	private SimpleJdbcCall buscarRollosDeLaFichaCall;
	private SimpleJdbcCall altaDeRolloCall;
	private SimpleJdbcCall modificacionDeRolloCall;
	private SimpleJdbcCall registrarMasterCall;
	private SimpleJdbcCall verificarDesenlazeCall;
	private SimpleJdbcCall verificarPayTVCall;
	private SimpleJdbcCall actualizarPY6001Call;
	private SimpleJdbcCall ejecutarDesenlaceCall;

	@Autowired
	public TrabajarConFichasDeChequeoTecnicoDaoPL(DataSource dataSource) {
		super();
		this.buscarFichasCall = buildBuscarFichasCall(dataSource);
		this.visualizarFichasCall = buildVisualizarFichasCall(dataSource);
		this.buscarProgPorTitOriginalCall = buildBuscarProgPorTitOriginalCall(dataSource);
		this.buscarProgPorTitCastellanoCall = buildBuscarProgPorTitCastellanoCall(dataSource);
		this.obtenerInfoAdicinalFichaCall = buildCargarInfoAdicinalFichaCall(dataSource);
		this.altaModifFichaCall = buildAltaModifFichaCall(dataSource);
		this.bajaFichaCall = buildBajaFichaCall(dataSource);
		this.inactivarFichaCall = buildInactivarFichaCall(dataSource);
		this.infoVigenciaContratosCall = buildInfoVigenciaContratosCall(dataSource);
		this.validarTituloEnCanal = buildValidarTituloEnCanalCall(dataSource);
		this.buscarSenialesOkFilmCall = buildBuscarSenialesOkFilmCall(dataSource);
		this.buscarRollosDeLaFichaCall = buildBuscarRollosDeLaFichaCall(dataSource);
		this.popupTitulosConContratosExhibidosCall = buildPopupTitulosExhibidosCall(dataSource);
		this.popupTitulosConContratosExhibidosSNCall = buildPopupTitulosExhibidosSNCall(dataSource);
		this.popupTitulosConContratosExhibidosRechazoCall = buildPopupTitulosExhibidosRechazoCall(dataSource);
		this.ejecutarDesenlaceCall = buildEjecutarDesenlaceCall(dataSource);
		this.actualizarPY6001Call = buildActualizarPY6001Call(dataSource);
		this.verificarDesenlazeCall = buildPopupVerificarDesenlaceCall(dataSource);
		this.verificarPayTVCall = buildVerificarPayTVCall(dataSource);
		this.popupAsocCopiaContratosCall = buildPopupAsocCopiaContratosCall(dataSource);
		this.eliminarSegmentosCall = buildBasicCall(dataSource,
				"pr_EliminaSegmento");
		this.generoCall = buildAyudaSituarCall(dataSource, "pr_tipo_generos");
		this.calificacionArtisticaCall = buildAyudaSituarCall(dataSource,
				"pr_calif_artistica");
		this.calificacionOficialCall = buildAyudaSituarCall(dataSource,
				"pr_calificacion_oficial");
		this.calificacionAutocontrolCall = buildAyudaSituarCall(dataSource,
				"pr_calificacion_moral");
		this.ayudaSenialesCall = buildAyudaSenialesCall(dataSource);
		this.ayudaSoporteCall = buildAyudaSituarCall(dataSource, "pr_soporte");
		this.guardarSegmentosCall = buildBasicCall(dataSource,
				"pr_guardar_segmentos");
		this.guardarActoresCall = buildBasicCall(dataSource,
				"pr_guardar_actores");
		this.guardarSinopsisCall = buildBasicCall(dataSource,
				"pr_grabar_sinopsis");
		this.guardarObservacionesCall = buildBasicCall(dataSource,
				"pr_guardar_observaciones");
		this.validarChequeoDetalleCall = buildBasicCall(dataSource,
				"pr_validar_chequeo_detalle");
		this.guardarChequeoDetalleCall = buildBasicCall(dataSource,
				"p_guardar_chequeo_detalle");
		this.guardarSenialesOkFilmCall = buildBasicCall(dataSource,
				"pr_guardar_seniales_ok_filmo");
		this.limpiarSegmentosCall = buildBasicCall(dataSource,
				"pr_borrar_segmentos");
		this.altaDeRolloCall = buildBasicCall(dataSource,
				"pr_InsertaCargaRolloFicha");
		this.modificacionDeRolloCall = buildBasicCall(dataSource,
				"pr_ActualizaRolloFichaTecnica");
		this.registrarMasterCall = buildBasicCall(dataSource,
				"pr_registra_master");
	}

	private SimpleJdbcCall buildPopupAsocCopiaContratosCall(
			DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("fil_pkg_fp230")
				.withProcedureName("pr_popup_asoc_copia_contr")
				.returningResultSet("P_CURSOR",
						new RowMapper<AsociarAContrato>() {
							@Override
							public AsociarAContrato mapRow(ResultSet rs,
									int rowNum) throws SQLException {
								AsociarAContrato response = new AsociarAContrato();
								response.setSenial(rs.getString("senial"));
								response.setProveedor(rs.getString("proveedor"));
								response.setGrupo(rs.getInt("grupo"));
								response.setContrato(rs.getInt("contrato"));
								return response;
							}
						});
	}

	private SimpleJdbcCall buildBuscarRollosDeLaFichaCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName("fil_pkg_fp230")
				.withProcedureName("pr_ficha_rollos")
				.returningResultSet("P_DATOS", new RowMapper<Rollo>() {
					@Override
					public Rollo mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Rollo response = new Rollo();
						response.setSenial(rs.getString("senial"));
						response.setCopia(rs.getInt("nro_copia"));
						response.setOrigen(rs.getString("origen"));
						response.setRollo(rs.getInt("nro_rollo"));
						response.setSecuencia(rs.getInt("nro_secuencia"));
						response.setSoporte(rs.getString("soporte"));
						response.setSugerido(rs.getString("sugerido"));
						response.setFecha(rs.getDate("fecha"));
						return response;
					}
				});
	}

	/*private SimpleJdbcCall buildPopupTitulosExhibidosCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("fil_pkg_fp230")
				.withProcedureName("pr_popup_titulos_exh_conf")
				.returningResultSet("P_CURSOR",
						new RowMapper<ContratoExhibido>() {
							@Override
							public ContratoExhibido mapRow(ResultSet rs,
									int rowNum) throws SQLException {
								ContratoExhibido response = new ContratoExhibido();
								response.setSenial(rs.getString("senial"));
								response.setFecha(rs.getDate("fecha"));
								response.setGrupo(rs.getInt("grupo"));
								response.setContrato(rs.getInt("contrato"));
								return response;
							}
						});
	}*/
	
	private SimpleJdbcCall buildPopupTitulosExhibidosCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_CHEQUEO_TECNICO")
				.withProcedureName("pr_vg_validar_exhibicion")
				.returningResultSet("P_CURSOR",
						new RowMapper<ContratoExhibido>() {
							@Override
							public ContratoExhibido mapRow(ResultSet rs,
									int rowNum) throws SQLException {
								ContratoExhibido response = new ContratoExhibido();
								response.setSenial(rs.getString("senial"));
								response.setFecha(rs.getDate("fecha_exh"));
								response.setGrupo(rs.getInt("grupo"));
								response.setContrato(rs.getInt("contrato"));
								return response;
							}
						});
	}
	
	private SimpleJdbcCall buildPopupTitulosExhibidosSNCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_CHEQUEO_TECNICO")
				.withProcedureName("pr_vg_validar_exhibicion_conta")
				.returningResultSet("P_CURSOR",
						new RowMapper<ContratoExhibido>() {
							@Override
							public ContratoExhibido mapRow(ResultSet rs,
									int rowNum) throws SQLException {
								ContratoExhibido response = new ContratoExhibido();
								response.setSenial(rs.getString("senial"));
								response.setFecha(rs.getDate("fecha_exh"));
								response.setGrupo(rs.getInt("grupo"));
								response.setContrato(rs.getInt("contrato"));
								return response;
							}
						});
	}
	
	private SimpleJdbcCall buildPopupTitulosExhibidosRechazoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_CHEQUEO_TECNICO")
				.withProcedureName("pr_registrar_rechazo");
				
	}
	
	private SimpleJdbcCall buildEjecutarDesenlaceCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_CHEQUEO_TECNICO")
				.withProcedureName("pr_ejecutar_desenlace");
				
	}
	
	private SimpleJdbcCall buildActualizarPY6001Call(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_CHEQUEO_TECNICO")
				.withProcedureName("pr_py6001");
				
	}
	
	private SimpleJdbcCall buildPopupVerificarDesenlaceCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_CHEQUEO_TECNICO")
				.withProcedureName("pr_verificar_desenlace")
				.returningResultSet("P_CURSOR_ERROR",
						new RowMapper<ErrorDesenlaceResponse>() {							
							@Override
							public ErrorDesenlaceResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
							if (StringUtils.EMPTY.equals(rs.getString("tipoError").trim())) {
							return null;
							} else {
							ErrorDesenlaceResponse response = new ErrorDesenlaceResponse();
							response.setTipoError(rs.getString("tipoError"));
							response.setDescripcion(rs.getString("descripcion"));
							response.setContrato(rs.getInt("contrato"));
							response.setGrupo(rs.getInt("grupo"));
							response.setSenial(rs.getString("senial"));
							response.setTipoTitulo(rs.getString("tipoTitulo"));
							response.setNroTitulo(rs.getInt("nroTitulo"));
							response.setFechaDesde(rs.getDate("fechaDesde"));
							response.setTipoListado(rs.getString("tipoListado"));
							return response;
							}
							}
						});
	}
	
	private SimpleJdbcCall buildVerificarPayTVCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("FIL_PKG_CHEQUEO_TECNICO")
				.withProcedureName("pr_validar_pay_tv")
				.returningResultSet("P_CURSOR_ERROR",
						new RowMapper<ErrorDesenlaceResponse>() {							
							@Override
							public ErrorDesenlaceResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
							ErrorDesenlaceResponse response = new ErrorDesenlaceResponse();
							response.setTipoError(rs.getString("tipo"));
							response.setDescripcion(rs.getString("descripcion"));
							return response;
							
							}
						});
	}

	private SimpleJdbcCall buildBuscarSenialesOkFilmCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName("fil_pkg_fp230")
				.withProcedureName("pr_lista_estado_ok_filmo")
				.returningResultSet("P_CURSOR", new RowMapper<SenialOkFilm>() {
					@Override
					public SenialOkFilm mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						SenialOkFilm response = new SenialOkFilm();
						response.setSenial(rs.getString("senial"));
						response.setOkVTR(rs.getString("ok_vtr"));
						response.setOkFilm(rs.getString("ok_filmo"));
						return response;
					}
				});
	}

	private SimpleJdbcCall buildBasicCall(DataSource dataSource, String name) {
		return new SimpleJdbcCall(dataSource).withCatalogName("fil_pkg_fp230")
				.withProcedureName(name);
	}

	private SimpleJdbcCall buildAyudaSituarCall(DataSource dataSource,
			String name) {
		return new SimpleJdbcCall(dataSource).withCatalogName("fil_pkg_fp230")
				.withProcedureName(name)
				.returningResultSet("P_CURSOR", new RowMapper<AyudaSituar>() {
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

	private SimpleJdbcCall buildAyudaSenialesCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("fil_pkg_fp230")
				.withProcedureName("pr_senial_alta_rollo")
				.returningResultSet("P_CURSOR",
						new RowMapper<Senial>() {
							@Override
							public Senial mapRow(ResultSet rs,
									int rowNum) throws SQLException {
								Senial response = new Senial();
								response.setCodigo(rs.getString("codigo"));
								response.setDescripcion(rs.getString("descripcion"));
								return response;
							}
						});
	}

	private SimpleJdbcCall buildValidarTituloEnCanalCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName("fil_pkg_fp230")
				.withFunctionName("pr_titulo_onsite");
	}

	private SimpleJdbcCall buildInfoVigenciaContratosCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("fil_pkg_fp230")
				.withProcedureName("pr_Info_Pantalla_Contrato")
				.returningResultSet("p_cursor",
						new RowMapper<VigenciaContrato>() {
							@Override
							public VigenciaContrato mapRow(ResultSet rs,
									int rowNum) throws SQLException {
								VigenciaContrato response = new VigenciaContrato();
								response.setContrato(rs.getInt("contrato"));
								response.setGrupo(rs.getInt("grupo"));
								response.setProveedor(rs.getString("proveedor"));
								response.setSenial(rs.getString("senial"));
								response.setVigente(rs.getString("vigente"));
								return response;
							}
						});
	}

	private SimpleJdbcCall buildInactivarFichaCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName("fil_pkg_fp230")
				.withProcedureName("pr_InactivaFichaTecnica");
	}

	private SimpleJdbcCall buildBajaFichaCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName("fil_pkg_fp230")
				.withProcedureName("pr_BajaFichaTecnica")
                .returningResultSet("P_ERRORES",
                        new RowMapper<ErrorResponse>() {
                            @Override
                            public ErrorResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                            ErrorResponse response = new ErrorResponse();
                            response.setTipoError(rs.getString("tipo"));
                            response.setDescripcion(rs.getString("descripcion"));
                            return response;
                            
                            }
                        });
	}

	private SimpleJdbcCall buildAltaModifFichaCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName("fil_pkg_fp230")
				.withProcedureName("pr_ProcesoFichaTecnica");
	}

	private SimpleJdbcCall buildCargarInfoAdicinalFichaCall(
			DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("fil_pkg_fp230")
				.withProcedureName("pr_InfoAltaFicha")
				.returningResultSet("P_DATOS_HEADER",
						new RowMapper<Programa>() {
							@Override
							public Programa mapRow(ResultSet rs, int rowNum)
									throws SQLException {
								Programa response = new Programa();
								response.setCapitulo(rs.getInt("CAPITULO"));
								response.setParte(rs.getInt("PARTE"));
								response.setSoporte(rs.getString("SOPORTE"));
								response.setTituloOriginal(rs
										.getString("TIT_ORIG"));
								response.setTituloCastellano(rs
										.getString("TIT_CAST"));
								response.setTituloCastellano(rs
										.getString("TIT_OFF"));
								return response;
							}
						})
				.returningResultSet("P_DATOS_ADDICIONAL",
						new RowMapper<InfoAdicionalPrograma>() {
							@Override
							public InfoAdicionalPrograma mapRow(ResultSet rs,
									int rowNum) throws SQLException {
								InfoAdicionalPrograma response = new InfoAdicionalPrograma();
								response.setActores(rs.getString("ACTORES"));
								response.setSello(rs.getString("SELLO"));
								response.setAutor(rs.getString("AUTOR"));
								response.setDirector(rs.getString("DIRECTOR"));
								response.setProductora(rs
										.getString("PRODUCTORA"));
								return response;
							}
						});
	}

	private SimpleJdbcCall buildVisualizarFichasCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("fil_pkg_fp230")
				.withProcedureName("pr_visualizar")
				.returningResultSet("P_FICHA", new RowMapper<Ficha>() {
					@Override
					public Ficha mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Ficha response = new Ficha();
						response.setAudio(rs.getString("AUDIO"));
						response.setAutAutor(rs.getString("AUT_AUTOR"));
						response.setAutor(rs.getString("AUTOR"));
						response.setAnio(rs.getInt("ANIO"));
						response.setCalifArtistica(rs
								.getString("CALIF_ARTISTICA"));
						response.setCalifAutocontrol(rs
								.getString("CALIF_AUTOCONTROL"));
						response.setCalifMoralOficial(rs
								.getString("CALIF_MORAL_OFICIAL"));
						response.setCantSegmentos(rs.getInt("CANT_SEGMENTOS"));
						response.setCapitulo(rs.getInt("CAPITULO"));
						response.setCapSegmentado(rs
								.getString("CAP_SEGMENTADO"));
						response.setClave(rs.getString("CLAVE"));
						response.setColor(rs.getString("COLOR"));
						response.setDescCapitulo(rs.getString("DESC_CAPITULO"));
						response.setDirector(rs.getString("DIRECTOR"));
						response.setDuracionArtistica(rs
								.getInt("DURACION_ARTISTICA"));
						response.setEstadoCopia(rs.getString("ESTADO_COPIA"));
						if (rs.getDate("FECHA_CHEQUEO") != null){
							response.setFechaChequeo(rs.getDate("FECHA_CHEQUEO").toString().equals("0001-01-01")? null : rs.getDate("FECHA_CHEQUEO"));
						}
						if (rs.getDate("FECHA_SOLICITUD") != null){
						response.setFechaSolicitud(rs.getDate("FECHA_SOLICITUD").toString().equals("0001-01-01") ? null : rs.getDate("FECHA_SOLICITUD"));
						}
						response.setGenero(rs.getString("GENERO"));
						response.setGeneroDescripcion(rs.getString("GENERODESCRIPCION"));
						response.setImagen(rs.getString("IMAGEN"));
						response.setNacionalidad(rs.getString("NACIONALIDAD"));
						response.setOperadorVTR(rs.getString("OPERADORVTR"));
						response.setPagaArgentores(rs
								.getString("PAGA_ARGENTORES"));
						response.setPais(rs.getString("PAIS"));
						response.setParte(rs.getInt("PARTE"));
						response.setProductor(rs.getString("PRODUCTOR"));
						response.setSello(rs.getString("SELLO"));
						response.setSoporte(rs.getString("SOPORTE"));
						response.setSoporteDescripcion(rs
								.getString("SOPORTEDESCRIPCION"));
						response.setTipoAudio(rs.getString("TIPO_AUDIO"));
						response.setTituloCastellano(rs
								.getString("TIT_CASTELLANO"));
						response.setTituloOff(rs.getString("Titulo_en_Off"));
						response.setTituloOriginal(rs
								.getString("TITULO_ORIGINAL"));
						response.setFilm(rs.getString("OK_FILM"));
						response.setAceptadoVTR(rs.getString("OK_VTR"));
						response.setCredito(rs.getString("CREDITOS"));
						response.setCalifArtisticaDesc(rs.getString("DESCRIPCION_ART"));
						response.setCalifAutocontrolDesc(rs.getString("DESCRIPCION_AUTO"));
						response.setCalifMoralOficialDesc(rs.getString("DESCRIPCION_MORAL"));
						return response;
					}
				})
				.returningResultSet("P_SENIAL", new RowMapper<Senial>() {
					@Override
					public Senial mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Senial response = new Senial();
						response.setCodigo(rs.getString("SNKCOD"));
						response.setDescripcion(rs.getString("SNWDDES"));
						return response;
					}
				})
				.returningResultSet("P_ACTORES", new RowMapper<Actor>() {
					@Override
					public Actor mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Actor response = new Actor();
						response.setActor(rs.getString("NOMBRE"));
						response.setNroActor(rs.getInt("NROACTOR"));
						response.setPersonaje(rs.getString("PERSONAJE"));
						return response;
					}
				})
				.returningResultSet("P_SEGMENTOS", new RowMapper<Segmento>() {
					@Override
					public Segmento mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Segmento response = new Segmento();
						response.setChequeo(rs.getString("CHEQUEO_SEGMENTO"));
						response.setDuracion(rs.getInt("DURACION"));
						response.setNroFicha(rs.getInt("NRO_FICHA"));
						response.setNumero(rs.getInt("SEGMENTO"));
						response.setTitulo(rs.getString("TIT_SEGMENTO"));
						return response;
					}
				})
				.returningResultSet("P_OBSERVA", new RowMapper<Observacion>() {
					@Override
					public Observacion mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Observacion response = new Observacion();
						response.setNroFicha(rs.getInt("NRO_FICHA"));
						response.setObservacion(rs.getString("OBSERVACION"));
						response.setRenglon(rs.getInt("RENGLON"));
						return response;
					}
				}).returningResultSet("P_SINOPSIS", new RowMapper<Sinopsis>() {
					@Override
					public Sinopsis mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Sinopsis response = new Sinopsis();
						response.setSinopsis(rs.getString("DETALLE"));
						response.setNroFicha(rs.getInt("RENGLON"));
						return response;
					}
				}).returningResultSet("P_OK_SENIAL", new RowMapper<SenialOkFilm>() {
					@Override
					public SenialOkFilm mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						SenialOkFilm response = new SenialOkFilm();
						response.setSenial(rs.getString("SENIAL"));
						response.setOkVTR(rs.getString("OK_VTR"));
						response.setOkFilm(rs.getString("OK_FILMO"));
						return response;
					}
				});
	}

	private SimpleJdbcCall buildBuscarFichasCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("fil_pkg_fp230")
				.withProcedureName("pr_situar")
				.returningResultSet("P_CURSOR",
						new RowMapper<BusquedaFichasResponse>() {
							@Override
							public BusquedaFichasResponse mapRow(ResultSet rs,
									int rowNum) throws SQLException {
								BusquedaFichasResponse response = new BusquedaFichasResponse();
								response.setClave(rs.getString("CLAVE"));
								response.setCapitulo(rs.getInt("CAPITULO"));
								response.setParte(rs.getInt("PARTE"));
								response.setTitulo(rs.getString("TITULO"));
								response.setTituloCastellano(rs
										.getString("TITULO_CASTELLANO"));
								response.setNroFicha(rs.getInt("NUMEROFICHA"));
								if (rs.getDate("FECHA") != null){
									response.setFecha(rs.getDate("FECHA").toString().equals("0001-01-01") ? null : rs.getDate("FECHA"));
								}
								response.setSoporte(rs.getString("SOPORTE"));
								response.setEstado(rs.getString("E"));
								return response;
							}
						});
	}

	private SimpleJdbcCall buildBuscarProgPorTitCastellanoCall(
			DataSource dataSource) {
		return builbBuscarProgPorTitCall(dataSource, "pr_Prog_cast");
	}

	private SimpleJdbcCall buildBuscarProgPorTitOriginalCall(
			DataSource dataSource) {
		return builbBuscarProgPorTitCall(dataSource, "pr_Prog_original");
	}

	private SimpleJdbcCall builbBuscarProgPorTitCall(DataSource dataSource,
			String nombrePl) {
		return new SimpleJdbcCall(dataSource)
				.withCatalogName("fil_pkg_fp230")
				.withProcedureName(nombrePl)
				.returningResultSet("P_CURSOR",
						new RowMapper<ProgramaSituarResponse>() {
							@Override
							public ProgramaSituarResponse mapRow(ResultSet rs,
									int rowNum) throws SQLException {
								ProgramaSituarResponse response = new ProgramaSituarResponse();
								response.setClave(rs.getString("CLAVE"));
								response.setCapitulo(rs.getInt("CAPITULO"));
								response.setParte(rs.getInt("PARTE"));
								response.setTituloOriginal(rs
										.getString("TITULO_ORIGINAL"));
								response.setTituloCastellano(rs
										.getString("TITULO_CASTELLANO"));
								return response;
							}
						});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BusquedaFichasResponse> buscarFichas(
			BusquedaFichasRequest request) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_senial", request.getSenial())
				.addValue("p_clave", request.getClave())
				.addValue("p_capitulo", request.getCapitulo())
				.addValue("p_parte", request.getParte())
				.addValue(
						"p_titulo",
						request.getOrden().equals("O") ? request
								.getTituloOriginal() : request.getOrden()
								.equals("A") ? request.getTituloCastellano()
								: "")
				.addValue("p_fichas_Activas", request.getFichasActivas())
				.addValue("p_chequeo", request.getChequeo())
				.addValue("p_fecha_desde", request.getFechaDesde())
				.addValue("p_fecha_hasta", request.getFechaHasta())
				.addValue("p_orden", request.getOrden());
		Map<String, Object> out = this.buscarFichasCall.execute(in);
		return (List<BusquedaFichasResponse>) out.get("P_CURSOR");
	}

	@Override
	@SuppressWarnings("unchecked")
	public FichaCompleta visualizarFicha(VisualizarFichaRequest request) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"P_NROFICHA", request.getNroFicha());
		Map<String, Object> out = this.visualizarFichasCall.execute(in);
		FichaCompleta response = new FichaCompleta();
		response.setFicha(((List<Ficha>) out.get("P_FICHA")).get(0));
		response.setSeniales((List<Senial>) out.get("P_SENIAL"));
		response.setActores((List<Actor>) out.get("P_ACTORES"));
		response.setSegmentos((List<Segmento>) out.get("P_SEGMENTOS"));
		response.setObservaciones((List<Observacion>) out.get("P_OBSERVA"));
		response.setSinopsis((List<Sinopsis>) out.get("P_SINOPSIS"));
		response.setSenialesOkFilm((List<SenialOkFilm>) out.get("P_OK_SENIAL"));
		return response;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ProgramaSituarResponse> buscarProgramasPorTitulo(String titulo,
			TipoBusquedaTitulo tipoBusqueda) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"P_DESCRIPCION", titulo);
		Map<String, Object> out = tipoBusqueda.equals(ORIGINAL) ? this.buscarProgPorTitOriginalCall
				.execute(in) : this.buscarProgPorTitCastellanoCall.execute(in);
		return (List<ProgramaSituarResponse>) out.get("P_CURSOR");
	}

	@Override
	@SuppressWarnings("unchecked")
	public Chequeo obtenerInfoChequeoPorPrograma(String clave, String capitulo) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CLAVE",
				clave)
				.addValue("P_CAPITULO",capitulo);
		Map<String, Object> out = this.obtenerInfoAdicinalFichaCall.execute(in);
		Programa programa = ((List<Programa>) out.get("P_DATOS_HEADER")).get(0);
		List<InfoAdicionalPrograma> infoAdicionales = (List<InfoAdicionalPrograma>) out
				.get("P_DATOS_ADDICIONAL");
		return new Chequeo(infoAdicionales, programa);
	}

	@Override
	public void altaFicha(AltaModifFichaRequest request) {
		MapSqlParameterSource in = completarInParametersAltaModifFicha(request);
		in.addValue("P_TIPO_PR_FICHA_TECNICA", "A").addValue("P_FICHA_TECNICA",
				null);
		this.altaModifFichaCall.execute(in);
	}

	@Override
	public void modificarFicha(AltaModifFichaRequest request) {
		MapSqlParameterSource in = completarInParametersAltaModifFicha(request);
		in.addValue("P_TIPO_PR_FICHA_TECNICA", "M").addValue("P_FICHA_TECNICA",
				request.getNroFicha());
		this.altaModifFichaCall.execute(in);
	}

	@SuppressWarnings("unchecked")
    @Override
	public List<ErrorResponse> eliminarFicha(Integer nroFicha, String clave) {
		MapSqlParameterSource in = new MapSqlParameterSource().addValue(
				"p_id_ficha", nroFicha).addValue("p_clave", clave);
		Map<String, Object> out = this.bajaFichaCall.execute(in);
		return (List<ErrorResponse>) out.get("P_ERRORES");
	}

	private MapSqlParameterSource completarInParametersAltaModifFicha(
			AltaModifFichaRequest request) {
		MapSqlParameterSource in = new MapSqlParameterSource()
				.addValue("P_SENIAL", request.getSenial())
				.addValue("P_CLAVE", request.getClave())
				.addValue("P_NUM_CAPITULO", request.getCapitulo())
				.addValue("P_NUM_PARTE", request.getParte())
				.addValue("P_SOPORTE", request.getSoporte())
				.addValue("P_ACTORES", request.getActoresJoined())
				.addValue("P_SELLO", request.getSello())
				.addValue("P_AUTOR", request.getAutor())
				.addValue("P_DIRECTOR", request.getDirector())
				.addValue("P_PRODUCTOR", request.getProductor())
				.addValue("P_FECHA_SOL", request.getFechaSolicitud())
				.addValue("P_NUEVAS_SENIALES", request.getEmisorasJoined())
				.addValue("P_PERSONAJES", null)
				.addValue("P_NUM_SEGMENTO", 0);
		return in;
	}

	@Override
	public void inactivarFicha(Integer nroFicha, String clave) {
		MapSqlParameterSource in = new MapSqlParameterSource().addValue(
				"p_id_ficha", nroFicha).addValue("p_clave", clave);
		this.inactivarFichaCall.execute(in);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VigenciaContrato> infoVigenciaContratos(String clave,
			Integer ficha) {
		MapSqlParameterSource in = new MapSqlParameterSource().addValue(
				"p_id_ficha", ficha).addValue("p_clave", clave);
		Map<String, Object> out = this.infoVigenciaContratosCall.execute(in);
		return (List<VigenciaContrato>) out.get("p_cursor");
	}

	@Override
	public String validarTituloEnCanal(String clave, String senial, String capitulo, String parte) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_clave",
				clave).addValue("p_senial", senial).addValue("p_numero_capitulo", capitulo).addValue("p_numero_parte", parte);
		return this.validarTituloEnCanal.executeFunction(String.class, in);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AyudaSituar> buscarGeneros(String descripcion) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"p_descripcion", descripcion);
		Map<String, Object> out = this.generoCall.execute(in);
		return (List<AyudaSituar>) out.get("P_CURSOR");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AyudaSituar> buscarCalifArtisticas(String descripcion) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"p_descripcion", descripcion);
		Map<String, Object> out = this.calificacionArtisticaCall.execute(in);
		return (List<AyudaSituar>) out.get("P_CURSOR");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AyudaSituar> buscarCalifAutocontrol(String descripcion) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"p_descripcion", descripcion);
		Map<String, Object> out = this.calificacionAutocontrolCall.execute(in);
		return (List<AyudaSituar>) out.get("P_CURSOR");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AyudaSituar> buscarCalifOficial(String descripcion) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"p_descripcion", descripcion);
		Map<String, Object> out = this.calificacionOficialCall.execute(in);
		return (List<AyudaSituar>) out.get("P_CURSOR");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AyudaSituar> buscarSoportes(String descripcion) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"p_descripcion", descripcion);
		Map<String, Object> out = this.ayudaSoporteCall.execute(in);
		return (List<AyudaSituar>) out.get("P_DATOS");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Senial> buscarSeniales(Integer nroFicha,
			String descripcion) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_id_ficha", nroFicha)
														   .addValue("p_descripcion", StringUtils.EMPTY.equals(descripcion) ? null : descripcion);
		Map<String, Object> out = this.ayudaSenialesCall.execute(in);
		return (List<Senial>) out.get("P_CURSOR");
	}

	@Override
	public void eliminarSegmentos(Integer nroFicha) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"p_ficha_chequeo", nroFicha);
		this.eliminarSegmentosCall.execute(in);
	}

	@Override
	public void guardarSegmentos(GuardarSegmentoRequest segmentoRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"p_ficha_chequeo", segmentoRequest.getNroFicha());
		this.limpiarSegmentosCall.execute(in);
		for (Segmento segmento : segmentoRequest.getSegmentos()) {
			in = new MapSqlParameterSource()
					.addValue("p_ficha_chequeo", segmentoRequest.getNroFicha())
					.addValue("p_titulo_segment", segmento.getTitulo())
					.addValue("p_dura_semgment", segmento.getDuracion())
					.addValue("p_ok_segmento", segmento.getChequeo());
			this.guardarSegmentosCall.execute(in);
		}
	}

	@Override
	public void guardarActoresSinopsisYObservaciones(
			GuardarActoresSinopsisObservRequest request) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_clave", request.getClave())
				.addValue("p_actores", request.getActoresJoined())
				.addValue("p_personaje", request.getPersonajesJoined())
				.addValue("p_num_capitulo", request.getCapitulo())
				.addValue("p_num_parte", request.getParte())
				.addValue("p_sinopsis", request.getSinopsisJoined())
				.addValue("p_id_ficha", request.getNroFicha())
				.addValue("p_observacion", request.getObservacionesJoined())
				.addValue("p_num_segmento", 0);
		this.guardarActoresCall.execute(in);
		this.guardarSinopsisCall.execute(in);
		this.guardarObservacionesCall.execute(in);
	}

	@Override
	public void validarChequeoDetalle(Ficha ficha) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_clave", ficha.getClave())
				.addValue("p_id_ficha", ficha.getNroFicha())
				.addValue("p_color", ficha.getColor())
				.addValue("p_nacionalidad", ficha.getNacionalidad())
				.addValue("p_estado_copia", ficha.getEstadoCopia())
				.addValue("p_imagen", ficha.getImagen())
				.addValue("p_duracion", ficha.getDuracionArtistica())
				.addValue("p_estado_audio", ficha.getAudio())
				.addValue("p_tipo_audio", ficha.getTipoAudio())
				.addValue("p_autori_autor", ficha.getAutAutor())
				.addValue("p_paga_argento", ficha.getPagaArgentores())
				.addValue("p_cap_segment", ficha.getCapSegmentado())
				.addValue("p_cant_segment", ficha.getCantSegmentos())
				.addValue("p_fecha_chequeo", ficha.getFechaChequeo())
				.addValue("p_ok_vtr", ficha.getAceptadoVTR())
				.addValue("p_ok_filmoteca", ficha.getFilm())
				.addValue("p_id_genero", ficha.getGenero())
				.addValue("p_id_calif_oficial", ficha.getCalifAutocontrol())
				.addValue("p_id_calif_art", ficha.getCalifArtistica())
				.addValue("p_id_calif_moral", ficha.getCalifMoralOficial());
		this.validarChequeoDetalleCall.execute(in);
	}

	@Override
	public void guardarChequeoDetalle(Ficha ficha) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_clave", ficha.getClave())
				.addValue("p_senial", ficha.getSenial())
				.addValue("p_ficha_chequeo", ficha.getNroFicha())
				.addValue("p_cal_artistica", ficha.getCalifArtistica())
				.addValue("p_cal_moral", ficha.getCalifMoralOficial())
				.addValue("p_cal_autocontrol", ficha.getCalifAutocontrol())
				.addValue("p_id_genero", ficha.getGenero())
				.addValue("p_anio_prod", ficha.getAnio())
				.addValue("p_sello", ficha.getSello())
				.addValue("p_autor", ficha.getAutor())
				.addValue("p_director", ficha.getDirector())
				.addValue("p_productor", ficha.getProductor())
				.addValue("p_color", ficha.getColor())
				.addValue("p_cod_pais", ficha.getPais())
				.addValue("p_descrip_cap", ficha.getDescCapitulo())
				.addValue("p_creditos", safeEmptyString(ficha))
				.addValue("p_nacionalidad", ficha.getNacionalidad())
				.addValue("p_estado_copia", ficha.getEstadoCopia())
				.addValue("p_imagen", ficha.getImagen())
				.addValue("p_duracion", ficha.getDuracionArtistica())
				.addValue("p_estado_audio", ficha.getAudio())
				.addValue("p_tipo_audio", ficha.getTipoAudio())
				.addValue("p_autori_autor", ficha.getAutAutor())
				.addValue("p_paga_argento", ficha.getPagaArgentores())
				.addValue("p_cap_segment", ficha.getCapSegmentado())
				.addValue("p_cant_segment", ficha.getCantSegmentos())
				.addValue("p_ok_vtr", ficha.getAceptadoVTR())
				.addValue("p_ok_filmoteca", ficha.getFilm())
				.addValue("p_operador_vtr", ficha.getOperadorVTR())
				.addValue("p_confirma", ficha.getConfirma())
				.addValue("p_fecha_chequeo", ficha.getFechaChequeo())
				.addValue("p_tit_off", ficha.getTituloOff())
				.addValue("p_tit_cast", ficha.getTituloCastellano())
				.addValue("p_tit_ori", ficha.getTituloOriginal());
		
		this.guardarChequeoDetalleCall.execute(in);
	}

    private String safeEmptyString(Ficha ficha) {
        return ficha.getCredito().trim().equals(StringUtils.EMPTY) ? null : ficha.getCredito().trim();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<SenialOkFilm> buscarSenialesOkFiml(Integer nroFicha, String film) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"p_id_ficha", nroFicha).addValue("p_ok_filmo", film);
		Map<String, Object> out = this.buscarSenialesOkFilmCall.execute(in);
		return (List<SenialOkFilm>) out.get("P_CURSOR");
	}

	@Override
	public void guardarSenialesOkFilm(GuardarSenialesOkFilmRequest request) {
		for (SenialOkFilm senial : request.getSeniales()) {
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("p_id_ficha", request.getNroFicha())
					.addValue("p_senial", senial.getSenial())
					.addValue("p_ok_vtr", senial.getOkVTR())
					.addValue("p_ok_filmo", senial.getOkFilm());
			this.guardarSenialesOkFilmCall.execute(in);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public TitulosConContratosExhibidosResponse popupTitulosConContratosExhibidos(
			Integer nroFicha, String film) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_ficha", nroFicha)
				.addValue("p_filmo_ok", film);
		Map<String, Object> out = this.popupTitulosConContratosExhibidosCall
				.execute(in);
		//String mensaje = (String) out.get("P_MENSAJE");
		String mensaje = (String) out.get("P_MENSAJE");
		//BigDecimal tipo = (BigDecimal) out.get("P_TIPO_ERROR");
		List<ContratoExhibido> contratos = (List<ContratoExhibido>) out
				.get("P_CURSOR");
		return new TitulosConContratosExhibidosResponse(mensaje, contratos);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public TitulosConContratosExhibidosResponse popupTitulosConContratosExhibidosSN(
			Integer nroFicha, String film) {
		SqlParameterSource in = new MapSqlParameterSource()
		.addValue("p_ficha", nroFicha)
		.addValue("p_filmo_ok", film);
		Map<String, Object> out = this.popupTitulosConContratosExhibidosSNCall
				.execute(in);
		String mensaje = (String) out.get("P_MENSAJE");
		List<ContratoExhibido> contratos = (List<ContratoExhibido>) out
				.get("P_CURSOR");
		return new TitulosConContratosExhibidosResponse(mensaje, contratos);
	}
	
	@Override
	public String popupTitulosConContratosExhibidosRechazo(
			Integer nroFicha) {
		SqlParameterSource in = new MapSqlParameterSource()
		.addValue("p_ficha", nroFicha);
		Map<String, Object> out = this.popupTitulosConContratosExhibidosRechazoCall
				.execute(in);		
		String mensaje = (String) out.get("P_MENSAJE");
		return mensaje;
	}
	
	public String ejecutarDesenlace(VerificarDesenlaceRequest request) {
		SqlParameterSource in = new MapSqlParameterSource()
		.addValue("p_ficha", request.getNroFicha())
		.addValue("p_clave", request.getClave())
		.addValue("p_capitulo", request.getCapitulo())
		.addValue("p_parte", request.getParte());
		Map<String, Object> out = this.ejecutarDesenlaceCall.execute(in);		
		String mensaje = (String) out.get("P_MENSAJE");
		return mensaje;
	}
	
	public void actualizarPY6001(Integer nroFicha) {
		SqlParameterSource in = new MapSqlParameterSource()
		.addValue("p_ficha", nroFicha)
		.addValue("p_tipo_vig", "BM");
	    this.actualizarPY6001Call.execute(in);		
		
	}
	
	@SuppressWarnings("unchecked")

	public VerificarDesenlaceResponse popupVerificarDesenlace( VerificarDesenlaceRequest request ) {
		SqlParameterSource in = new MapSqlParameterSource()
		.addValue("p_ficha", request.getNroFicha())
		.addValue("p_clave", request.getClave())
		.addValue("p_capitulo", request.getCapitulo())
		.addValue("p_parte", request.getParte())
		.addValue("p_ok_filmo", request.getOkFilmo());
		Map<String, Object> out = this.verificarDesenlazeCall.execute(in);		
		String mensaje = (String) out.get("P_MENSAJE");
		String ejecuta = (String) out.get("P_EJECUTA_PY");
		String error = (String) out.get("P_ERROR");
		BigDecimal idReporteBD = (BigDecimal) out.get("P_ID_REPORTE");
		String idReporte = idReporteBD == null ? "" : idReporteBD.toString() ;
		String ejecutaEnlace = (String) out.get("P_EJECUTA_ENLACE");
		BigDecimal contratoBD = (BigDecimal) out.get("P_CONTRATO");
		String contrato = contratoBD == null ?  "" : contratoBD.toString();
		BigDecimal grupoBD = (BigDecimal) out.get("P_GRUPO");
		String grupo = grupoBD == null ? "" : grupoBD.toString();
		String senial = (String) out.get("P_SENIAL");
		List<ErrorDesenlaceResponse> errores = (List<ErrorDesenlaceResponse>) out
				.get("P_CURSOR_ERROR");
		return new VerificarDesenlaceResponse(ejecuta, mensaje, error, idReporte, errores, ejecutaEnlace, contrato, grupo, senial);

	}
	
	@SuppressWarnings("unchecked")
	public VerificarDesenlaceResponse validarPayTV( Integer ficha, String clave ) {
		SqlParameterSource in = new MapSqlParameterSource()
		.addValue("p_ficha", ficha)
		.addValue("p_clave", clave);
		Map<String, Object> out = this.verificarPayTVCall.execute(in);
		String error = (String) out.get("P_TIPO");
		List<ErrorDesenlaceResponse> cursorPayTV = (List<ErrorDesenlaceResponse>) out
				.get("P_CURSOR_ERROR");
		return new VerificarDesenlaceResponse("", "", error, "", cursorPayTV, "","","","");

	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Rollo> buscarRollos(String senial, Integer nroFicha) {
		SqlParameterSource in = new MapSqlParameterSource().addValue(
				"p_senial", senial).addValue("p_nroficha", nroFicha);
		Map<String, Object> out = this.buscarRollosDeLaFichaCall.execute(in);
		return (List<Rollo>) out.get("P_DATOS");
	}

	@Override
	public void altaDeRollos(AltaModifRolloRequest request) {
		for (Rollo rollo : request.getRollos()) {
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("p_clave", request.getClave())
					.addValue("p_senial", request.getSenial())
					.addValue("p_soporte", rollo.getSoporte())
					.addValue("p_num_rollo", rollo.getRollo())
					.addValue("p_origen", request.getOrigen())
					.addValue("p_fecha_emision", request.getFechaEmision())
					.addValue("p_sugerido_s_n", rollo.getSugerido())
					.addValue("p_num_capitulo", request.getCapitulo())
					.addValue("p_num_parte", request.getParte());
			this.altaDeRolloCall.execute(in);
		}
	}

	@Override
	public void modificacionDeRollos(AltaModifRolloRequest request) {
		for (Rollo rollo : request.getRollos()) {
			SqlParameterSource in = new MapSqlParameterSource()
					.addValue("p_clave", request.getClave())
					.addValue("p_senial", request.getSenial())
					.addValue("p_num_copia", rollo.getCopia())
					.addValue("p_soporte", rollo.getSoporte())
					.addValue("p_num_rollo", rollo.getRollo())
					.addValue("p_origen", request.getOrigen())
					.addValue("p_fecha_emision", request.getFechaEmision())
					.addValue("p_sugerido_s_n", rollo.getSugerido())
					.addValue("p_num_capitulo", request.getCapitulo())
					.addValue("p_num_parte", request.getParte())
					.addValue("p_secuencia", rollo.getSecuencia());
			this.modificacionDeRolloCall.execute(in);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AsociarAContrato> buscarContratosParaAsociarLaCopia(
			String senial, String clave, Integer nroFicha) {
		SqlParameterSource in = new MapSqlParameterSource()
			.addValue("p_senial", senial)
			.addValue("p_clave", clave)
			.addValue("p_id_ficha",	nroFicha);
		Map<String, Object> out = this.popupAsocCopiaContratosCall.execute(in);
		return (List<AsociarAContrato>) out.get("P_CURSOR");
	}

	@Override
	public void registrarMaster(String senial, String clave, Integer nroFicha) {
		SqlParameterSource in = new MapSqlParameterSource()
			.addValue("p_clave", senial)
			.addValue("p_senial", clave)
			.addValue("p_id_ficha",	nroFicha);
		this.registrarMasterCall.execute(in);
	}
}
