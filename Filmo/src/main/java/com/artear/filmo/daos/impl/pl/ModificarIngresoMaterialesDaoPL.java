package com.artear.filmo.daos.impl.pl;

import java.math.BigDecimal;
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

import com.artear.filmo.bo.common.Capitulo;
import com.artear.filmo.bo.common.Distribuidor;
import com.artear.filmo.bo.common.MotivoIngreso;
import com.artear.filmo.bo.common.Soporte;
import com.artear.filmo.bo.common.Titulo;
import com.artear.filmo.bo.common.ValidarExhibicionesRow;
import com.artear.filmo.bo.contratos.ContratoValidationResult;
import com.artear.filmo.bo.ingresar.materiales.CabeceraRemitoABM;
import com.artear.filmo.bo.ingresar.materiales.DesenlaceResponse;
import com.artear.filmo.bo.ingresar.materiales.DetalleRemitoABM;
import com.artear.filmo.bo.ingresar.materiales.RemitoGrillaResponse;
import com.artear.filmo.bo.ingresar.materiales.TituloGrillaResponse;
import com.artear.filmo.bo.ingresar.materiales.ValidarEliminarDetalleRemitoResponse;
import com.artear.filmo.bo.ingresar.materiales.ValidarEliminarRemitoRequest;
import com.artear.filmo.bo.ingresar.materiales.ValidarEliminarRemitoResponse;
import com.artear.filmo.bo.ingresar.materiales.ValidarModificarEliminarDetalleRequest;
import com.artear.filmo.daos.interfaces.ModificarIngresoMaterialesDao;

@Repository("modificarIngresoMaterialesDao")
public class ModificarIngresoMaterialesDaoPL implements ModificarIngresoMaterialesDao {

	/* Búsquedas */
	private SimpleJdbcCall buscarRemitosPorNumeroRemitoCall;
	private SimpleJdbcCall buscarRemitosPorGuiaCall;
	private SimpleJdbcCall buscarRemitosPorRazonSocialCall;
	private SimpleJdbcCall buscarRemitosPorFechaCall;
	private SimpleJdbcCall buscarDistribuidoresCall;
	private SimpleJdbcCall buscarTitulosPorDescripcionCall;
	private SimpleJdbcCall buscarCapitulosCall;
	private SimpleJdbcCall buscarPartesCall;
	/* Modificacion cabecera remito */
	private SimpleJdbcCall validarExhibicionesCall;
	private SimpleJdbcCall modificarCabeceraRemitoCall;
	/* Eliminar remito */
	private SimpleJdbcCall validarCabeceraRemitoCall;
	private SimpleJdbcCall validarContabilizadoCabeceraRemitoCall;
	private SimpleJdbcCall validarDesenlaceCall;
	private SimpleJdbcCall eliminarRemitoCall;
	/* Trabajar con títulos */
	private SimpleJdbcCall buscarTitulosRemitoCall;
	private SimpleJdbcCall buscarSoportesCall;
	private SimpleJdbcCall validarSoporteCall;
	private SimpleJdbcCall buscarMotivosIngresoCall;
	private SimpleJdbcCall validarMotivoIngresoCall;
	/* Modificación detalle remito */
	private SimpleJdbcCall validarModificarDetalleRemitoCall;
	private SimpleJdbcCall modificarDetalleRemitoCall;
	private SimpleJdbcCall validarContabilizadoCall;
	private SimpleJdbcCall verificarCall;
	private SimpleJdbcCall validarRpfCall;
	/* Eliminar detalle remito */
	private SimpleJdbcCall eliminarDetalleRemitoCall;
	private SimpleJdbcCall eliminarDetalleRemitoDesdeCall;
	private SimpleJdbcCall desenlaceConConfirmacionCall;

	
	private static final String PACKAGE_NAME_MODIFICAR = "fil_pkg_mod_ingreso_mat";
	
	@Autowired
	public ModificarIngresoMaterialesDaoPL(DataSource dataSource) {
		super();
		/* Búsquedas */
		this.buscarRemitosPorNumeroRemitoCall = this.buildBuscarRemitosCall(dataSource, "pr_levantar_remitos_por_numre");
		this.buscarRemitosPorGuiaCall = this.buildBuscarRemitosCall(dataSource, "pr_levantar_remitos_por_guia");
		this.buscarRemitosPorRazonSocialCall = this.buildBuscarRemitosCall(dataSource, "pr_levantar_remitos_por_rs");
		this.buscarRemitosPorFechaCall = this.buildBuscarRemitosCall(dataSource, "pr_levantar_remitos_por_fecha");
		this.buscarDistribuidoresCall = this.buildBuscarDistribuidoresCall(dataSource);
		this.buscarTitulosPorDescripcionCall = this.buildBuscarTitulosPorDescripcionCall(dataSource);
		this.buscarCapitulosCall = this.buildBuscarCapitulosCall(dataSource);
		this.buscarPartesCall = this.buildBuscarPartesCall(dataSource);
		/* Modificacion cabecera remito */
		this.validarExhibicionesCall = this.buildValidarExhibicionesCall(dataSource);
		this.modificarCabeceraRemitoCall = this.buildModificarCabeceraRemitoCall(dataSource);
		/* Eliminar remito */
		this.validarCabeceraRemitoCall = this.buildValidarCabeceraRemitoCall(dataSource);
		this.validarContabilizadoCabeceraRemitoCall = this.buildValidarContabilizadoCabeceraRemitoCall(dataSource);
		this.validarDesenlaceCall = this.buildValidarDesenlaceCall(dataSource);
		this.eliminarRemitoCall = this.buildEliminarRemitoCall(dataSource); 
		/* Trabajar con títulos */
		this.buscarTitulosRemitoCall = this.buildBuscarTitulosRemitoCall(dataSource);
		this.buscarSoportesCall = this.buildBuscarSoportesCall(dataSource);
		this.validarSoporteCall = this.buildValidarSoporteCall(dataSource);
		this.buscarMotivosIngresoCall = this.buildBuscarMotivosIngresoCall(dataSource);
		this.validarMotivoIngresoCall = this.buildValidarMotivoIngresoCall(dataSource);
		/* Modificación detalle remito */
		this.validarModificarDetalleRemitoCall = this.buildValidarModificarDetalleRemitoCall(dataSource);
		this.modificarDetalleRemitoCall = this.buildModificarDetalleRemitoCall(dataSource);
		this.validarContabilizadoCall = this.builValidarContabilizadoCall(dataSource);
		this.verificarCall = this.buildVerificarCall(dataSource);
		this.validarRpfCall = this.buildValidarRpfCall(dataSource);
		/* Eliminar detalle remito */
		this.eliminarDetalleRemitoCall = this.buildEliminarDetalleRemitoCall(dataSource);
		this.eliminarDetalleRemitoDesdeCall = this.buildEliminarDetalleRemitoDesdeCall(dataSource);
		this.desenlaceConConfirmacionCall = this.buildDesenlaceConConfirmacionCall(dataSource);
	}

	private SimpleJdbcCall buildBuscarRemitosCall(DataSource dataSource, String procedureName) {
		return new SimpleJdbcCall(dataSource)
			.withCatalogName(PACKAGE_NAME_MODIFICAR)
			.withProcedureName(procedureName)
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
				@Override
				public RemitoGrillaResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
					RemitoGrillaResponse remito = new RemitoGrillaResponse();
					remito.setIdRemito(rs.getBigDecimal("rtkidrem"));
					remito.setRazonSocialDistribuidor(rs.getString("prdrazsoc"));
					remito.setCodigoDistribuidor(rs.getInt("prkpro"));
					remito.setFechaRemitoGuia(rs.getDate("rtfremgui"));
					remito.setNumeroRemito(rs.getString("rtnnumrem"));
					remito.setNumeroGuia(rs.getString("rtnnumgui"));
					remito.setFechaMovimiento(rs.getDate("rtffecmov"));
					return remito;
				}
			});
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
	
	private SimpleJdbcCall buildBuscarTitulosPorDescripcionCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
		.withCatalogName(PACKAGE_NAME_MODIFICAR)
		.withProcedureName("pr_visualizar_titulos")
		.returningResultSet("P_CURSOR", new RowMapper<Object>() {
			@Override
			public Titulo mapRow(ResultSet rs, int rowNum) throws SQLException {
				Titulo titulo = new Titulo();
				titulo.setClave(rs.getString("clave"));
				titulo.setTitulo(rs.getString("descripcion"));
                return titulo;
            }
		});
	}
	
	private SimpleJdbcCall buildBuscarCapitulosCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
		.withCatalogName(PACKAGE_NAME_MODIFICAR)
		.withProcedureName("pr_visualizar_capitulos")
		.returningResultSet("P_CURSOR", new RowMapper<Object>() {
			@Override
			public Capitulo mapRow(ResultSet rs, int rowNum) throws SQLException {
				Capitulo capitulo =  new Capitulo();
				capitulo.setNumeroCapitulo(rs.getInt("num_cap"));
                return capitulo;
            }
		});
	}
	
	private SimpleJdbcCall buildBuscarPartesCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
		.withCatalogName(PACKAGE_NAME_MODIFICAR)
		.withProcedureName("pr_visualizar_parte")
		.returningResultSet("P_CURSOR", new RowMapper<Object>() {
			@Override
			public Capitulo mapRow(ResultSet rs, int rowNum) throws SQLException {
				Capitulo parte = new Capitulo();
				parte.setNumeroParte(rs.getInt("parte"));
                return parte;
            }
		});
	}
	
	private SimpleJdbcCall buildValidarExhibicionesCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_MODIFICAR).withProcedureName("pr_validar_exhibiciones")
			.returningResultSet("P_RESPUESTA", new RowMapper<Object>() {
				@Override
				public ValidarExhibicionesRow mapRow(ResultSet rs, int rowNum) throws SQLException {
					ValidarExhibicionesRow veRow = new ValidarExhibicionesRow();
					veRow.setClave(rs.getString("clave"));
					veRow.setTitulo(rs.getString("titulo"));
					veRow.setNumeroCapitulo(rs.getInt("capitulo"));
					veRow.setNumeroParte(rs.getInt("parte"));
	                return veRow;
	            }
			});
	}
	
	private SimpleJdbcCall buildModificarCabeceraRemitoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_MODIFICAR).withProcedureName("pr_modificar_cabecera_remito");
	}
	
	private SimpleJdbcCall buildValidarCabeceraRemitoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_MODIFICAR).withProcedureName("pr_validar_cabecera");
	}
	
	private SimpleJdbcCall buildValidarContabilizadoCabeceraRemitoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_MODIFICAR).withProcedureName("pr_validar_contabilizado_cab");
	}
	
	private SimpleJdbcCall buildValidarDesenlaceCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
			.withCatalogName(PACKAGE_NAME_MODIFICAR)
			.withProcedureName("pr_validar_desenlace_cab")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					String mensajes = new String();
					mensajes = rs.getString("column_value");
					return mensajes;
				}
			});
	}
	
	private SimpleJdbcCall buildEliminarRemitoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_MODIFICAR).withProcedureName("pr_eliminar_cabecera");
	}
	
	private SimpleJdbcCall buildBuscarTitulosRemitoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
			.withCatalogName(PACKAGE_NAME_MODIFICAR)
			.withProcedureName("pr_busqueda_titulo")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
			@Override
			public TituloGrillaResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
				TituloGrillaResponse titulo = new TituloGrillaResponse();
				titulo.setTitulo(rs.getString("titulo_cast"));
                titulo.setClave(rs.getString("clave"));
                titulo.setCapitulo(rs.getInt("capitulio"));
                titulo.setParte(rs.getInt("parte"));
                titulo.setCodSoporte(rs.getString("soporte"));
                titulo.setDescSoporte(rs.getString("desc_soporte"));
                titulo.setRollos(rs.getInt("cant_rollos"));
                titulo.setCodMotivoIngreso(rs.getString("motivo_ing"));
                titulo.setDescMotivoIngreso(rs.getString("mmddescri"));
                titulo.setCarrete(rs.getInt("carrete"));
                titulo.setLata(rs.getInt("lata"));
                titulo.setTorta(rs.getInt("torta"));
                titulo.setEstrenoRepeticion(rs.getString("repetido"));
                return titulo;
            }
		});
	}
	
	private SimpleJdbcCall buildBuscarSoportesCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
		.withCatalogName(PACKAGE_NAME_MODIFICAR)
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

	private SimpleJdbcCall buildValidarSoporteCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_MODIFICAR).withProcedureName("pr_validar_soporte");
	}
	
	private SimpleJdbcCall buildBuscarMotivosIngresoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
			.withCatalogName(PACKAGE_NAME_MODIFICAR)
			.withProcedureName("pr_busqueda_motivo")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
				@Override
				public MotivoIngreso mapRow(ResultSet rs, int rowNum) throws SQLException {
					MotivoIngreso motivoIngreso = new MotivoIngreso();
					motivoIngreso.setCodigo(rs.getString("codigo"));
					motivoIngreso.setDescripcion(rs.getString("descripcion"));
	                return motivoIngreso;
	            }
			});
	}
	
	private SimpleJdbcCall buildValidarMotivoIngresoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_MODIFICAR).withProcedureName("pr_validar_motivo");
	}
	
	private SimpleJdbcCall buildValidarModificarDetalleRemitoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_MODIFICAR).withProcedureName("pr_validar_modificar");
	}
	
	private SimpleJdbcCall buildModificarDetalleRemitoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_MODIFICAR).withProcedureName("pr_modificar_remito");
	}
	
	private SimpleJdbcCall builValidarContabilizadoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_MODIFICAR).withProcedureName("pr_validar_contabilizado");
	}
	
	private SimpleJdbcCall buildVerificarCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_MODIFICAR).withProcedureName("pr_verificar");
	}
	
	private SimpleJdbcCall buildValidarRpfCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_MODIFICAR).withProcedureName("pr_validar_rpf22022")
		        .returningResultSet("P_ERRORES", new RowMapper<ContratoValidationResult>() {
                    @Override
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(rs.getString("TIPO")); ret.setDescripcion(rs.getString("DESCRIPCION")); 
                        return ret;
                    }
                });
	}

	private SimpleJdbcCall buildEliminarDetalleRemitoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_MODIFICAR).withProcedureName("pr_eliminar");
	}
	
	private SimpleJdbcCall buildEliminarDetalleRemitoDesdeCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_MODIFICAR).withProcedureName("pr_eliminar_desde_rm6001");
	}

	private SimpleJdbcCall buildDesenlaceConConfirmacionCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
			.withCatalogName(PACKAGE_NAME_MODIFICAR)
			.withProcedureName("pr_desenlace")
			.returningResultSet("P_CURSOR_ERRORES", new RowMapper<Object>() {
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					String mensajes = new String();
					mensajes = rs.getString("column_value");
					return mensajes;
				}
			});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RemitoGrillaResponse> buscarRemitosPorNumeroRemito(String nroRemito, String senial) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_id", nroRemito)
		                                                   .addValue("p_senial", senial);
			   
		Map<String, Object> out = this.buscarRemitosPorNumeroRemitoCall.execute(in);
		return (List<RemitoGrillaResponse>) out.get("P_CURSOR");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RemitoGrillaResponse> buscarRemitosPorGuia(String nroGuia, String senial) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_guia", nroGuia)
	                                                       .addValue("p_senial", senial);   
		
		Map<String, Object> out = this.buscarRemitosPorGuiaCall.execute(in);
		return (List<RemitoGrillaResponse>) out.get("P_CURSOR");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RemitoGrillaResponse> buscarRemitosPorRazonSocial(String razonSocial, String clave, Integer capitulo, Integer parte, String senial) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_razon_social", razonSocial)
														   .addValue("p_clave", clave)
														   .addValue("p_capitulo", capitulo)
														   .addValue("p_parte", parte)
		                                                   .addValue("p_senial", senial);
		   
		Map<String, Object> out = this.buscarRemitosPorRazonSocialCall.execute(in);
		return (List<RemitoGrillaResponse>) out.get("P_CURSOR");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RemitoGrillaResponse> buscarRemitosPorFecha(Date fecha, String senial) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_fecha", fecha)
		                                                   .addValue("p_senial", senial);
		   
		Map<String, Object> out = this.buscarRemitosPorFechaCall.execute(in);
		return (List<RemitoGrillaResponse>) out.get("P_CURSOR");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Distribuidor> buscarDistribuidoresParaRemitos(Integer codigoDistribuidor, String razonSocialDistribuidor) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_codigo", codigoDistribuidor)
														   .addValue("p_razon", razonSocialDistribuidor);

		Map<String, Object> out = this.buscarDistribuidoresCall.execute(in);
		return (List<Distribuidor>) out.get("P_CURSOR");
	}
	
	@SuppressWarnings("unchecked")
	public List<Titulo> buscarTitulosPorDescripcion(String descripcionTituloCast, String descripcionTituloOrig) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_descripcion_cast", descripcionTituloCast)
				   										   .addValue("p_descripcion_ori", descripcionTituloOrig);

		Map<String, Object> out = this.buscarTitulosPorDescripcionCall.execute(in);
		return (List<Titulo>) out.get("P_CURSOR");
	}
	
	@SuppressWarnings("unchecked")
	public List<Capitulo> buscarCapitulos(String clave) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_clave", clave);

		Map<String, Object> out = this.buscarCapitulosCall.execute(in);
		return (List<Capitulo>) out.get("P_CURSOR");
	}
	
	@SuppressWarnings("unchecked")
	public List<Capitulo> buscarPartes(String clave, Integer capitulo) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_clave", clave)
														   .addValue("p_capitulo", capitulo);

		Map<String, Object> out = this.buscarPartesCall.execute(in);
		return (List<Capitulo>) out.get("P_CURSOR");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ValidarExhibicionesRow> validarExhibiciones(Integer codigoDistribuidor, String senial, String idRemito, String numeroGuia) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_proveedor",codigoDistribuidor)
														   .addValue("p_nroRto", idRemito) //el parametro que se le pasa es el id de remito, por  mas que en le PL se llame nroRto
														   .addValue("p_nroGuia", numeroGuia)
														   .addValue("p_senial", senial);
		/* p_respuesta */
		Map<String, Object> out = this.validarExhibicionesCall.execute(in);
		return (List<ValidarExhibicionesRow>) out.get("P_RESPUESTA");
	}
	
	@Override
	public Boolean modificarCabeceraRemito(CabeceraRemitoABM cabeceraRemitoABM) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_id_remito", cabeceraRemitoABM.getIdRemito())
														   .addValue("p_fecha_ingreso", cabeceraRemitoABM.getFechaIngreso())
														   .addValue("p_fecha_remito", cabeceraRemitoABM.getFechaRemito())
														   .addValue("p_numero_remito", cabeceraRemitoABM.getNumeroRemito())
														   .addValue("p_numero_guia", cabeceraRemitoABM.getNumeroGuia());
		 Map<String, Object> out = this.modificarCabeceraRemitoCall.execute(in);
		 return (Boolean) "OK".equals(out.get("P_RESPUESTA"));
	}

	public ValidarEliminarRemitoResponse validarCabeceraRemito(ValidarEliminarRemitoRequest validarEliminarRemitoRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_remito", validarEliminarRemitoRequest.getIdRemito());
										
		Map<String, Object> out = this.validarCabeceraRemitoCall.execute(in);
		
		ValidarEliminarRemitoResponse response = new ValidarEliminarRemitoResponse();
		response.setTipo(((String) out.get("P_TIPO")));
		response.setMensaje(((String) out.get("P_MENSAJE")));
		return response;
	}
	
	public ValidarEliminarRemitoResponse validarContabilizadoCabeceraRemito(ValidarEliminarRemitoRequest validarEliminarRemitoRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_remito", validarEliminarRemitoRequest.getIdRemito());
										
		Map<String, Object> out = this.validarContabilizadoCabeceraRemitoCall.execute(in);
		
		ValidarEliminarRemitoResponse response = new ValidarEliminarRemitoResponse();
		response.setContabiliza(((String) out.get("P_CONTABILIZA")));
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DesenlaceResponse validarDesenlaceRemito(BigDecimal idRemito) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_id_remito", idRemito);
		
		Map<String, Object> out = this.validarDesenlaceCall.execute(in);
		Boolean hasErrors = "E".equals((String) out.get("P_TIPO")) || "W".equals((String) out.get("P_TIPO"));
		
		DesenlaceResponse response = new DesenlaceResponse();
		response.setHayErrores(hasErrors);
		response.setErrores((List<String>) out.get("P_CURSOR_ERRORES"));

		return response;
	}

	@Override
	public Boolean eliminarRemito(BigDecimal idRemito) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_remito", idRemito);
		
		this.eliminarRemitoCall.execute(in);
		return Boolean.TRUE;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<TituloGrillaResponse> buscarTitulosRemito(BigDecimal idRemito, String descripcionTituloCast, String descripcionTituloOrig, String tipoMaterial, String senial) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_remito", idRemito)
														   .addValue("p_descripcion_ori", descripcionTituloOrig)
														   .addValue("p_descripcion_cast", descripcionTituloCast)
														   .addValue("p_senial", senial)
														   .addValue("p_tiene_contrato", tipoMaterial);
										
		Map<String, Object> out = this.buscarTitulosRemitoCall.execute(in);
		return (List<TituloGrillaResponse>) out.get("P_CURSOR");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Soporte> buscarSoportes(String codigoSoporte) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_descripcion", codigoSoporte);

		Map<String, Object> out = this.buscarSoportesCall.execute(in);
		return (List<Soporte>) out.get("P_CURSOR");
	}
	
	@Override
	public Boolean validarSoporte(String codigoSoporte) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_codigo", codigoSoporte);

		Map<String, Object> out = this.validarSoporteCall.execute(in);
		return (Boolean) "S".equals(out.get("P_RESPUESTA"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MotivoIngreso> buscarMotivosIngreso(String descripcionMotivoIngreso) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_descripcion", descripcionMotivoIngreso);

		Map<String, Object> out = this.buscarMotivosIngresoCall.execute(in);
		return (List<MotivoIngreso>) out.get("P_CURSOR");
	}

	@Override
	public Boolean validarMotivoIngreso(String codigoMotivoIngreso) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_codigo", codigoMotivoIngreso);

		Map<String, Object> out = this.validarMotivoIngresoCall.execute(in);
		return (Boolean) "S".equals(out.get("P_RESPUESTA"));
	}
	
	@Override
	public String validarModificarDetalleRemito(ValidarModificarEliminarDetalleRequest validarModificarDetalleRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_remito", validarModificarDetalleRequest.getIdRemito())
														   .addValue("p_tipo_titulo", validarModificarDetalleRequest.getTipoTitulo())
														   .addValue("p_numero_titulo", validarModificarDetalleRequest.getNumeroTitulo())
														   .addValue("p_capitulo", validarModificarDetalleRequest.getCapitulo())
														   .addValue("p_parte", validarModificarDetalleRequest.getParte());
		
		Map<String, Object> out = this.validarModificarDetalleRemitoCall.execute(in);
		return (String) out.get("P_RESPUESTA");
	}
	
	@Override
	public Boolean modificarDetalleRemito(DetalleRemitoABM detalleRemitoABM) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_remito", detalleRemitoABM.getIdRemito())
														   .addValue("p_tipo_titulo", detalleRemitoABM.getTipoTitulo())
														   .addValue("p_numero_titulo", detalleRemitoABM.getNumeroTitulo())
														   .addValue("p_capitulo", detalleRemitoABM.getCapitulo())
														   .addValue("p_parte", detalleRemitoABM.getParte())
														   .addValue("p_soporte", detalleRemitoABM.getCodSoporte())
														   .addValue("p_tipo_motivo", detalleRemitoABM.getTipoMotivo())
														   .addValue("p_carrete", detalleRemitoABM.getCarrete())
														   .addValue("p_lata", detalleRemitoABM.getLata())
														   .addValue("p_torta", detalleRemitoABM.getTorta())
														   .addValue("p_cantidad_rollos", detalleRemitoABM.getCantRollos());
		this.modificarDetalleRemitoCall.execute(in);
		return Boolean.TRUE;
	}
	
	@Override
	public ValidarEliminarDetalleRemitoResponse validarContabilizado(ValidarModificarEliminarDetalleRequest validarEliminarDetalleRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_remito", validarEliminarDetalleRequest.getIdRemito())
														   .addValue("p_tipo_titulo", validarEliminarDetalleRequest.getTipoTitulo())
														   .addValue("p_numero_titulo", validarEliminarDetalleRequest.getNumeroTitulo())
														   .addValue("p_capitulo", validarEliminarDetalleRequest.getCapitulo())
														   .addValue("p_parte", validarEliminarDetalleRequest.getParte());
		
		Map<String, Object> out = this.validarContabilizadoCall.execute(in);
		
		ValidarEliminarDetalleRemitoResponse response = new ValidarEliminarDetalleRemitoResponse();
		response.setTipo(((String) out.get("P_TIPO")));
		response.setRespuesta(((String) out.get("P_RESPUESTA")));
		return response;			
	}
	
	@Override
	public ValidarEliminarDetalleRemitoResponse verificar(ValidarModificarEliminarDetalleRequest validarEliminarDetalleRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_remito", validarEliminarDetalleRequest.getIdRemito())
														   .addValue("p_tipo_titulo", validarEliminarDetalleRequest.getTipoTitulo())
														   .addValue("p_numero_titulo", validarEliminarDetalleRequest.getNumeroTitulo())
														   .addValue("p_capitulo", validarEliminarDetalleRequest.getCapitulo())
														   .addValue("p_parte", validarEliminarDetalleRequest.getParte())
														   .addValue("p_contabiliza", validarEliminarDetalleRequest.getContabiliza());
		
		Map<String, Object> out = this.verificarCall.execute(in);
		
		ValidarEliminarDetalleRemitoResponse response = new ValidarEliminarDetalleRemitoResponse();
		response.setTipo(((String) out.get("P_TIPO")));
		response.setRespuesta(((String) out.get("P_RESPUESTA")));
		response.setBorroCh(((String) out.get("P_BORRO_CH")));
		response.setFlagChsn(((String) out.get("P_FLAG_CHSN")));
		return response;			
	}
	
	@SuppressWarnings("unchecked")
    @Override
	public List<ContratoValidationResult> validarRpf(ValidarModificarEliminarDetalleRequest validarEliminarDetalleRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_remito", validarEliminarDetalleRequest.getIdRemito())
				   										   .addValue("p_tipo_titulo", validarEliminarDetalleRequest.getTipoTitulo())
				   										   .addValue("p_numero_titulo", validarEliminarDetalleRequest.getNumeroTitulo())
				   										   .addValue("p_capitulo", validarEliminarDetalleRequest.getCapitulo())
				   										   .addValue("p_parte", validarEliminarDetalleRequest.getParte());

		Map<String, Object> out = this.validarRpfCall.execute(in);
        List<ContratoValidationResult> ret = ((List<ContratoValidationResult>) out.get("P_ERRORES"));
        return ret;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DesenlaceResponse desenlace(ValidarModificarEliminarDetalleRequest validarEliminarDetalleRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_id_remito", validarEliminarDetalleRequest.getIdRemito())
														   .addValue("p_tipo_titulo", validarEliminarDetalleRequest.getTipoTitulo())
														   .addValue("p_numero_titulo", validarEliminarDetalleRequest.getNumeroTitulo());
		
		Map<String, Object> out = this.desenlaceConConfirmacionCall.execute(in);
		Boolean hasErrors = "S".equals((String) out.get("P_ERROR"));
		
		DesenlaceResponse response = new DesenlaceResponse();
		response.setHayErrores(hasErrors);
		response.setErrores((List<String>) out.get("P_CURSOR_ERRORES"));
		
		return response;
	}

	@Override
	public Boolean eliminarDetalleRemito(ValidarModificarEliminarDetalleRequest validarEliminarDetalleRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_remito", validarEliminarDetalleRequest.getIdRemito())
														   .addValue("p_tipo_titulo", validarEliminarDetalleRequest.getTipoTitulo())
														   .addValue("p_numero_titulo", validarEliminarDetalleRequest.getNumeroTitulo())
														   .addValue("p_capitulo", validarEliminarDetalleRequest.getCapitulo())
														   .addValue("p_parte", validarEliminarDetalleRequest.getParte())
														   .addValue("p_contabiliza", validarEliminarDetalleRequest.getContabiliza())
														   .addValue("p_borro_ch", validarEliminarDetalleRequest.getBorroCh());

		this.eliminarDetalleRemitoCall.execute(in);
		return Boolean.TRUE;
	}
	
	@Override
	public Boolean eliminarDetalleRemitoDesde(ValidarModificarEliminarDetalleRequest validarEliminarDetalleRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_remito", validarEliminarDetalleRequest.getIdRemito())
														   .addValue("p_tipo_titulo", validarEliminarDetalleRequest.getTipoTitulo())
														   .addValue("p_numero_titulo", validarEliminarDetalleRequest.getNumeroTitulo())
														   .addValue("p_capitulo", validarEliminarDetalleRequest.getCapitulo())
														   .addValue("p_parte", validarEliminarDetalleRequest.getParte());

		this.eliminarDetalleRemitoDesdeCall.execute(in);
		return Boolean.TRUE;
	}

}