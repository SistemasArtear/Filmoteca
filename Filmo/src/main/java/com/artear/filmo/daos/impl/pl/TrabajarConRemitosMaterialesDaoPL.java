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

import com.artear.filmo.bo.common.Capitulo;
import com.artear.filmo.bo.common.Distribuidor;
import com.artear.filmo.bo.common.Titulo;
import com.artear.filmo.bo.trabajar.remitos.ActualizarCantidadRequest;
import com.artear.filmo.bo.trabajar.remitos.EjecutarRemitoRequest;
import com.artear.filmo.bo.trabajar.remitos.EjecutarRemitoResponse;
import com.artear.filmo.bo.trabajar.remitos.ImprimirRemitoRequest;
import com.artear.filmo.bo.trabajar.remitos.ImprimirRemitoResponse;
import com.artear.filmo.bo.trabajar.remitos.ValidarNuevaSenialRequest;
import com.artear.filmo.bo.trabajar.remitos.ValidarNuevaSenialResponse;
import com.artear.filmo.bo.trabajar.remitos.ValidarNuevaSenialView;
import com.artear.filmo.daos.interfaces.TrabajarConRemitosMaterialesDao;

@Repository("trabajarConRemitosMaterialesDao")
public class TrabajarConRemitosMaterialesDaoPL implements TrabajarConRemitosMaterialesDao {

	private SimpleJdbcCall buscarDistribuidoresParaRemitosSalidaCall;
	private SimpleJdbcCall buscarTitulosParaCargaCall;
	private SimpleJdbcCall buscarCapitulosParaCargaCall;
	private SimpleJdbcCall validarDistribuidorCall;
	private SimpleJdbcCall validarFechaSenialCall;
	private SimpleJdbcCall validarNuevaSenialCall;
	private SimpleJdbcCall actualizarCantidadCall;
	private SimpleJdbcCall ejecutarRemitoCall;
	private SimpleJdbcCall imprimirRemitoCall;
	
	private static final String PACKAGE_NAME = "fil_pkg_fp240";
	
	@Autowired
	public TrabajarConRemitosMaterialesDaoPL(DataSource dataSource) {
		super();
		this.buscarDistribuidoresParaRemitosSalidaCall = this.buildBuscarDistribuidoresParaRemitosSalidaCall(dataSource);
		this.buscarTitulosParaCargaCall = this.buildBuscarTitulosParaCargaCall(dataSource);
		this.buscarCapitulosParaCargaCall = this.buildBuscarCapitulosParaCargaCall(dataSource);
		this.validarDistribuidorCall = this.builValidarDistribuidorCall(dataSource);
		this.validarFechaSenialCall = this.builValidarFechaSenialCall(dataSource);
		this.validarNuevaSenialCall = this.buildValidarNuevaSenialCall(dataSource);
		this.actualizarCantidadCall = this.buildActualizarCantidadCall(dataSource);
		this.ejecutarRemitoCall = this.buildEjecutarRemitoCall(dataSource);
		this.imprimirRemitoCall = this.buildImprimirRemitoCall(dataSource);
	}
	
	private SimpleJdbcCall buildBuscarDistribuidoresParaRemitosSalidaCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
			.withCatalogName(PACKAGE_NAME)
			.withProcedureName("pr_listar_proveedores")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
				@Override
				public Distribuidor mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Distribuidor distribuidor = new Distribuidor();
                    distribuidor.setCodigo(rs.getInt("id_proveedor"));
                    distribuidor.setRazonSocial(rs.getString("razon_social"));
                    return distribuidor;
                }
			});
	}
	
	private SimpleJdbcCall buildBuscarTitulosParaCargaCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
			.withCatalogName(PACKAGE_NAME)
			.withProcedureName("pr_seleccionarFP24003")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
				@Override
				public Titulo mapRow(ResultSet rs, int rowNum) throws SQLException {
					Titulo titulo = new Titulo();
					titulo.setClave(rs.getString("clave"));
					titulo.setTitulo(rs.getString("titulo"));
                    return titulo;
                }
			});
	}
	
	private SimpleJdbcCall buildBuscarCapitulosParaCargaCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
			.withCatalogName(PACKAGE_NAME)
			.withProcedureName("pr_listar_capitulo_prog")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
				@Override
				public Capitulo mapRow(ResultSet rs, int rowNum) throws SQLException {
					Capitulo capitulo = new Capitulo();
					capitulo.setNumeroCapitulo(rs.getInt("toknrocap"));
					capitulo.setNumeroParte(rs.getInt("tokparte"));
                    return capitulo;
                }
			});
	}
	
	private SimpleJdbcCall builValidarDistribuidorCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME).withFunctionName("fn_valida_exist_prov");
	}
	
	private SimpleJdbcCall builValidarFechaSenialCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME).withFunctionName("fn_valida_fecha_senial");
	}
	
	private SimpleJdbcCall buildValidarNuevaSenialCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME).withProcedureName("pr_validar_nueva_senial")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
				@Override
				public ValidarNuevaSenialView mapRow(ResultSet rs, int rowNum) throws SQLException {
					ValidarNuevaSenialView response = new ValidarNuevaSenialView();
					response.setFechaDesde(rs.getDate("fecha_desde"));
					response.setFechaHasta(rs.getDate("fecha_hasta"));
					response.setClave(rs.getString("clave"));
					response.setContrato(rs.getInt("contrato"));
					response.setGrupo(rs.getInt("grupo"));
					response.setExhibicion(rs.getString("exhibicion"));
	                return response;
	            }
			});
	}
	
	private SimpleJdbcCall buildActualizarCantidadCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME).withProcedureName("pr_actualiza_cantidad");
	}
	
	private SimpleJdbcCall buildEjecutarRemitoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
			.withCatalogName(PACKAGE_NAME)
			.withProcedureName("pr_confirmarFP24001")
			.returningResultSet("P_CURSORERROR", new RowMapper<Object>() {
				@Override
				public EjecutarRemitoResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
					EjecutarRemitoResponse response = new EjecutarRemitoResponse();
					response.setMensaje(rs.getString("mensaje"));
					response.setIdRemito(rs.getInt("idRtoInterno"));
	                return response;
	            }
			});
	}
	
	private SimpleJdbcCall buildImprimirRemitoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
			.withCatalogName(PACKAGE_NAME)
			.withProcedureName("pr_generarRemito")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
				@Override
				public ImprimirRemitoResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
					ImprimirRemitoResponse response = new ImprimirRemitoResponse();
					response.setTipoTitulo(rs.getString("tipoTitulo")); 
					response.setNroTitulo(rs.getInt("nroTitulo")); 
					response.setNroCapitulo(rs.getInt("nroCapitulo"));
					response.setNroParte(rs.getInt("nroParte"));
					response.setSoporte(rs.getString("soporte"));
	                return response;
	            }
			});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Distribuidor> buscarDistribuidoresParaRemitosSalida(String razonSocialDistribuidor) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_description", razonSocialDistribuidor);
		
		Map<String, Object> out = this.buscarDistribuidoresParaRemitosSalidaCall.execute(in);
		return (List<Distribuidor>) out.get("P_CURSOR");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Titulo> buscarTitulosParaCarga(Integer codigoDistribuidor, String descripcionTitulo) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_idProveedor", codigoDistribuidor)
														   .addValue("p_descripcion", descripcionTitulo);
		
		Map<String, Object> out = this.buscarTitulosParaCargaCall.execute(in);
		return (List<Titulo>) out.get("P_CURSOR");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Capitulo> buscarCapitulosParaCarga(String claveTitulo, Integer capitulo) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_clave", claveTitulo)
														   .addValue("p_capitulo",capitulo);
		
		Map<String, Object> out = this.buscarCapitulosParaCargaCall.execute(in);
		return (List<Capitulo>) out.get("P_CURSOR");
	}

	@Override
	public Boolean validarDistribuidor(Integer codigoDistribuidor) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_id_proveedor", codigoDistribuidor);

		String out = this.validarDistribuidorCall.executeFunction(String.class, in);
		return "S".equals(out);
	}

	@Override
	public Boolean validarFechaSenial(Date fechaRemito, String senial) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_fecha_remito", fechaRemito)
														   .addValue("p_senial", senial);
		
		String out = this.validarFechaSenialCall.executeFunction(String.class, in);
		return "S".equals(out);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ValidarNuevaSenialResponse validarNuevaSenial(ValidarNuevaSenialRequest validarNuevaSenialRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_senial", validarNuevaSenialRequest.getSenial())
														   .addValue("p_clave", validarNuevaSenialRequest.getClave())
														   .addValue("p_capitulo", validarNuevaSenialRequest.getCapitulo())
														   .addValue("p_parte", validarNuevaSenialRequest.getParte());
				
		Map<String, Object> out = this.validarNuevaSenialCall.execute(in);
		
		ValidarNuevaSenialResponse response = new ValidarNuevaSenialResponse();
		response.setMensaje((String) out.get("P_MENSAJE"));
		response.setRegistros((List<ValidarNuevaSenialView>) out.get("P_CURSOR"));
		
		return response;
	}
	
	@Override
	public void actualizarCantidad(ActualizarCantidadRequest actualizarCantidadRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_clave", actualizarCantidadRequest.getClave())
				   										   .addValue("p_capitulo", actualizarCantidadRequest.getCapitulo())
				   										   .addValue("p_parte", actualizarCantidadRequest.getParte());

		this.actualizarCantidadCall.execute(in);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public EjecutarRemitoResponse ejecutarRemito(EjecutarRemitoRequest ejecutarRemitoRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_nroRemito", ejecutarRemitoRequest.getIdRemito())
														   .addValue("p_senial", ejecutarRemitoRequest.getSenial())
														   .addValue("p_fechaRemito", ejecutarRemitoRequest.getFechaRemito())
														   .addValue("p_estadoImprimir", ejecutarRemitoRequest.getEstadoImprimir())
														   .addValue("p_clave", ejecutarRemitoRequest.getClave())
														   .addValue("p_capitulo", ejecutarRemitoRequest.getCapitulo())
														   .addValue("p_parte", ejecutarRemitoRequest.getParte())
														   .addValue("p_tipoMovimiento", ejecutarRemitoRequest.getMotivo())
														   .addValue("p_proveedor", ejecutarRemitoRequest.getCodigoDistribuidor())
														   .addValue("p_prestamoDia", ejecutarRemitoRequest.getDiasPrestamo())
														   .addValue("p_destinatario", ejecutarRemitoRequest.getDestinatario())
														   .addValue("p_numFedex", ejecutarRemitoRequest.getNumFedex())
														   .addValue("p_observaciones", ejecutarRemitoRequest.getObservaciones());     
           
		Map<String, Object> out = this.ejecutarRemitoCall.execute(in);
		List<EjecutarRemitoResponse> responseMensaje = (List<EjecutarRemitoResponse>) out.get("P_CURSORERROR");
		
		EjecutarRemitoResponse ejecutarRemitoResponse = responseMensaje.get(0);
		return ejecutarRemitoResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ImprimirRemitoResponse> imprimirRemito(ImprimirRemitoRequest imprimirRemitoRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_idRtoInterno", imprimirRemitoRequest.getIdRemito());
		
		Map<String, Object> out = this.imprimirRemitoCall.execute(in);
		return (List<ImprimirRemitoResponse>) out.get("P_CURSOR");
	}

}