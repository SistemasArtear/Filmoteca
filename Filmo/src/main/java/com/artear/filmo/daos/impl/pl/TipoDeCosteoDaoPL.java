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

import com.artear.filmo.bo.contratos.TipoDeCosteoRegistroRequest;
import com.artear.filmo.bo.contratos.TipoDeCosteoValidarSeleccionRequest;
import com.artear.filmo.bo.contratos.BuscarTipoDeCosteoRequest;
import com.artear.filmo.bo.contratos.Costeo;
import com.artear.filmo.bo.contratos.CosteoExcedente;
import com.artear.filmo.bo.contratos.CosteoRating;
import com.artear.filmo.bo.contratos.SenialHeredada;
import com.artear.filmo.bo.contratos.TipoDeCosteoABMExcedenteRequest;
import com.artear.filmo.bo.contratos.TipoDeCosteoABMRatingRequest;
import com.artear.filmo.bo.contratos.TipoDeCosteoABMRequest;
import com.artear.filmo.bo.contratos.TipoDeCosteoEliminarRequest;
import com.artear.filmo.daos.interfaces.TipoDeCosteoDao;

@Repository("tipoDeCosteoDao")
public class TipoDeCosteoDaoPL implements TipoDeCosteoDao {
	
	private SimpleJdbcCall getTipoCosteoByGrupoCall;
	private SimpleJdbcCall getListaCFM;
	private SimpleJdbcCall getLista;//modificacion de contrato, lista de cfm y mixto
	private SimpleJdbcCall agregarCosteoCFM;
	private SimpleJdbcCall agregarCosteoExcedente;
	private SimpleJdbcCall agregarCosteoRating;
	private SimpleJdbcCall agregarCosteoMixto;
	private SimpleJdbcCall getListaMixto;
	private SimpleJdbcCall getListaExcedente;
	private SimpleJdbcCall getListaRating;
	private SimpleJdbcCall getListaExcedenteABM;
	private SimpleJdbcCall getListaRatingABM;
	private SimpleJdbcCall eliminarRegistro;
	private SimpleJdbcCall abmRegistroExcedente;
	private SimpleJdbcCall abmRegistroRating;
	private SimpleJdbcCall abmRegistroCFM;
	private SimpleJdbcCall abmRegistroMixto;
	private SimpleJdbcCall validarSeleccion;
	private SimpleJdbcCall buscarDescripcionCanje;
	
	
	private static final String PACKAGE_NAME_ALTA_COSTEOS = "FIL_PKG_ALTA_COSTEOS";
	private static final String PACKAGE_NAME_MODIFICACION_COSTEOS = "FIL_PKG_MODIF_TIPO_COSTEO";
	
	@Autowired
	public TipoDeCosteoDaoPL(DataSource dataSource) {
		super();
		this.getTipoCosteoByGrupoCall = this.buildGetTipoCosteoByGrupoCall(dataSource);
		this.getListaCFM = this.buildGetListaCFM(dataSource);
		this.getLista = this.buildGetLista(dataSource);
		this.getListaRatingABM = this.buildGetListaRatingABM(dataSource);
		this.getListaExcedenteABM = this.buildGetListaExcedenteABM(dataSource);
		this.agregarCosteoCFM = this.buildAgregarCosteoCFM(dataSource);
		this.agregarCosteoExcedente = this.buildAgregarCosteoExcedente(dataSource);
		this.agregarCosteoRating = this.buildAgregarCosteoRating(dataSource);
		this.agregarCosteoMixto = this.buildAgregarCosteoMixto(dataSource);
		this.getListaMixto = this.buildGetListaMixto(dataSource);
		this.getListaExcedente = this.buildGetListaExcedente(dataSource);
		this.getListaRating = this.buildGetListaRating(dataSource);
		this.eliminarRegistro = this.buildEliminarRegistro(dataSource);
		this.validarSeleccion = this.buildValidarSeleccion(dataSource);
		this.buscarDescripcionCanje = this.buildBuscarDescripcionCanje(dataSource);
		this.abmRegistroExcedente = this.buildABMRegistroExcedente(dataSource);
		this.abmRegistroRating = this.buildABMRegistroRating(dataSource);
		this.abmRegistroCFM = this.buildABMRegistroCFM(dataSource);
		this.abmRegistroMixto = this.buildABMRegistroMixto(dataSource);
	}
	private SimpleJdbcCall buildGetTipoCosteoByGrupoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_COSTEOS).withFunctionName("getTipoCosteoByGrupo");
	}
	private SimpleJdbcCall buildBuscarDescripcionCanje(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_COSTEOS).withFunctionName("FN_OBTIENE_TIPO_CONTRATO_DESC");
	}
	private SimpleJdbcCall buildGetListaCFM(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_COSTEOS).withProcedureName("getListaCFM")
	            .returningResultSet("P_DATOS", new RowMapper<Object>() {
	            	@Override
	                public Costeo mapRow(ResultSet rs, int rowNum) throws SQLException {
	            		Costeo costeo = new Costeo();
	            		costeo.setAnioMes(rs.getInt("ANIOMES"));
	            		costeo.setValor(rs.getBigDecimal("valorcfm"));
	                    return costeo;
	                }
	            });
	}
	private SimpleJdbcCall buildGetLista(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_MODIFICACION_COSTEOS).withProcedureName("getListaCFM")//FIL_PKG_MODIF_TIPO_COSTEO
				.returningResultSet("P_DATOS", new RowMapper<Object>() {
					@Override
					public Costeo mapRow(ResultSet rs, int rowNum) throws SQLException {
						Costeo costeo = new Costeo();
						costeo.setAnioMes(rs.getInt("ANIOMES"));
						costeo.setValor(rs.getBigDecimal("valorcfm"));
						return costeo;
					}
				});
	}
	private SimpleJdbcCall buildGetListaRatingABM(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_MODIFICACION_COSTEOS).withProcedureName("getListaRating")//FIL_PKG_MODIF_TIPO_COSTEO
				.returningResultSet("P_DATOS", new RowMapper<Object>() {
					@Override
	                public CosteoRating mapRow(ResultSet rs, int rowNum) throws SQLException {
	            		CosteoRating costeo = new CosteoRating();
	            		costeo.setRatingDesde(rs.getInt("RATINGDESDE"));
	            		costeo.setRatingHasta(rs.getInt("RATINGHASTA"));
	            		costeo.setValor(rs.getBigDecimal("RAVALOR"));
	                    return costeo;
	                }
				});
	}
	private SimpleJdbcCall buildGetListaExcedenteABM(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_MODIFICACION_COSTEOS).withProcedureName("getListaExcedente")//FIL_PKG_MODIF_TIPO_COSTEO
				.returningResultSet("P_DATOS", new RowMapper<Object>() {
					@Override
	                public CosteoExcedente mapRow(ResultSet rs, int rowNum) throws SQLException {
	            		CosteoExcedente costeo = new CosteoExcedente();
	            		costeo.setMinutoDesde(rs.getInt("MINEXCDESDE"));
	            		costeo.setMinutoHasta(rs.getInt("MINEXCHASTA"));
	            		costeo.setValor(rs.getBigDecimal("EXVALOR"));
	                    return costeo;
	                }
				});
	}
	private SimpleJdbcCall buildAgregarCosteoCFM(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_COSTEOS).withProcedureName("PR_INS_COSTEO_CFM");
	}
	private SimpleJdbcCall buildAgregarCosteoExcedente(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_COSTEOS).withProcedureName("PR_INS_COSTEO_EXCED");
	}
	private SimpleJdbcCall buildAgregarCosteoRating(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_COSTEOS).withProcedureName("PR_INS_COSTEO_RATING");
	}
	private SimpleJdbcCall buildAgregarCosteoMixto(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_COSTEOS).withProcedureName("PR_INS_COSTEO_MIXTO");
	}
	private SimpleJdbcCall buildEliminarRegistro(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_COSTEOS).withProcedureName("PR_DEL_COSTEO_SEL");
	}
	private SimpleJdbcCall buildValidarSeleccion(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_COSTEOS).withFunctionName("FN_VALIDA_SELECCION");
	}
	private SimpleJdbcCall buildABMRegistroExcedente(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_MODIFICACION_COSTEOS).withProcedureName("PR_ESCALAEXC_AMB");
	}
	private SimpleJdbcCall buildABMRegistroRating(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_MODIFICACION_COSTEOS).withProcedureName("PR_ESCALARATING_AMB");
	}
	private SimpleJdbcCall buildABMRegistroCFM(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_MODIFICACION_COSTEOS).withProcedureName("PR_CFMENSUAL_AMB");
	}
	private SimpleJdbcCall buildABMRegistroMixto(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_MODIFICACION_COSTEOS).withProcedureName("PR_CFMIXTO_AMB");
	}
	private SimpleJdbcCall buildGetListaMixto(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_COSTEOS).withProcedureName("getListaMixto")
	            .returningResultSet("P_DATOS", new RowMapper<Object>() {
	            	@Override
	                public Costeo mapRow(ResultSet rs, int rowNum) throws SQLException {
	            		Costeo costeo = new Costeo();
	            		costeo.setAnioMes(rs.getInt("ANIOMES"));
	            		costeo.setValor(rs.getBigDecimal("valorcfm"));
	                    return costeo;
	                }
	            });
	}
	private SimpleJdbcCall buildGetListaExcedente(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_COSTEOS).withProcedureName("getListaExcedente")
	            .returningResultSet("P_DATOS", new RowMapper<Object>() {
	            	@Override
	                public CosteoExcedente mapRow(ResultSet rs, int rowNum) throws SQLException {
	            		CosteoExcedente costeo = new CosteoExcedente();
	            		costeo.setMinutoDesde(rs.getInt("MINEXCDESDE"));
	            		costeo.setMinutoHasta(rs.getInt("MINEXCHASTA"));
	            		costeo.setValor(rs.getBigDecimal("EXVALOR"));
	                    return costeo;
	                }
	            });
	}
	private SimpleJdbcCall buildGetListaRating(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_COSTEOS).withProcedureName("getListaRating")
	            .returningResultSet("P_DATOS", new RowMapper<Object>() {
	            	@Override
	                public CosteoRating mapRow(ResultSet rs, int rowNum) throws SQLException {
	            		CosteoRating costeo = new CosteoRating();
	            		costeo.setRatingDesde(rs.getInt("RATINGDESDE"));
	            		costeo.setRatingHasta(rs.getInt("RATINGHASTA"));
	            		costeo.setValor(rs.getBigDecimal("RAVALOR"));
	                    return costeo;
	                }
	            });
	}
	@Override
	public String getTipoCosteoByGrupo(BuscarTipoDeCosteoRequest request) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("contrato", request.getContrato())
				.addValue("senial", request.getSenial())
				.addValue("nroGrupo", request.getNroGrupo());
		
		String val = this.getTipoCosteoByGrupoCall.executeFunction(String.class, in);
//		System.out.println(val);
//		Map<String, Object> out= null;
		return val;//out;
	}
	@Override
	public String buscarDescripcionCanje(BuscarTipoDeCosteoRequest request) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_contrato", request.getContrato());
		String val = this.buscarDescripcionCanje.executeFunction(String.class, in);
//		System.out.println(val);
//		Map<String, Object> out= null;
		return val;//out;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Costeo> obtenerRegistrosCFM(BuscarTipoDeCosteoRequest request) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("contrato", request.getContrato())
				.addValue("senial", request.getSenial())
				.addValue("nroGrupo", request.getNroGrupo());

		Map<String, Object> out = this.getListaCFM.execute(in);
		return (List<Costeo>) out.get("P_DATOS");
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Costeo> obtenerRegistrosABM(TipoDeCosteoABMRequest request) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_contrato", request.getContrato())
				.addValue("p_senial", request.getSenial())
				.addValue("p_nroGrupo", request.getNroGrupo())
				.addValue("p_tipo_titulo", request.getTipoTitulo())
				.addValue("p_numero_titulo", Integer.valueOf(request.getTitulo()));
		
		Map<String, Object> out = this.getLista.execute(in);
		return (List<Costeo>) out.get("P_DATOS");
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CosteoRating> obtenerRegistrosRatingABM(TipoDeCosteoABMRatingRequest request) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_contrato", request.getContrato())
				.addValue("p_senial", request.getSenial())
				.addValue("p_nroGrupo", request.getNroGrupo())
				.addValue("p_tipo_titulo", request.getTipoTitulo())
				.addValue("p_numero_titulo", Integer.valueOf(request.getTitulo()));
		
		Map<String, Object> out = this.getListaRatingABM.execute(in);
		return (List<CosteoRating>) out.get("P_DATOS");
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CosteoExcedente> obtenerRegistrosExcedenteABM(TipoDeCosteoABMExcedenteRequest request) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_contrato", request.getContrato())
				.addValue("p_senial", request.getSenial())
				.addValue("p_nroGrupo", request.getNroGrupo())
				.addValue("p_tipo_titulo", request.getTipoTitulo())
				.addValue("p_numero_titulo", Integer.valueOf(request.getTitulo()));
		
		Map<String, Object> out = this.getListaExcedenteABM.execute(in);
		return (List<CosteoExcedente>) out.get("P_DATOS");
	}
	@Override
	public String agregarRegistrosCFM(TipoDeCosteoRegistroRequest request) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_contrato", request.getContrato())
				.addValue("p_grupo", request.getNroGrupo())
				.addValue("p_senial", request.getSenial())
				.addValue("p_mes", request.getMes())
				.addValue("p_anio", request.getAnio())
				.addValue("p_valorcfm", request.getValor())
				.addValue("p_tipo_contrato", request.getTipoContrato());
		
		Map<String, Object> out = this.agregarCosteoCFM.execute(in);
		return (String) out.get("P_ERROR");
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CosteoExcedente> obtenerRegistrosExcedente(BuscarTipoDeCosteoRequest request) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("contrato", request.getContrato())
				.addValue("senial", request.getSenial())
				.addValue("nroGrupo", request.getNroGrupo());
		
		Map<String, Object> out = this.getListaExcedente.execute(in);
		return (List<CosteoExcedente>) out.get("P_DATOS");
	}
	@Override
	public String agregarRegistrosExcedente(TipoDeCosteoRegistroRequest request) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_contrato", request.getContrato())
				.addValue("p_grupo", request.getNroGrupo())
				.addValue("p_senial", request.getSenial())
				.addValue("P_MINEXCDDE", request.getMinutoDesde())
				.addValue("P_MINEXCHTA", request.getMinutoHasta())
				.addValue("P_VALOREXC", request.getValor())
				.addValue("p_tipo_contrato", request.getTipoContrato());
		
		Map<String, Object> out = this.agregarCosteoExcedente.execute(in);
		return (String) out.get("P_ERROR");
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CosteoRating> obtenerRegistrosRating(BuscarTipoDeCosteoRequest request) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("contrato", request.getContrato())
				.addValue("senial", request.getSenial())
				.addValue("nroGrupo", request.getNroGrupo());
		
		Map<String, Object> out = this.getListaRating.execute(in);
		return (List<CosteoRating>) out.get("P_DATOS");
	}
	@Override
	public String agregarRegistrosRating(TipoDeCosteoRegistroRequest request) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_contrato", request.getContrato())
				.addValue("p_grupo", request.getNroGrupo())
				.addValue("p_senial", request.getSenial())
				.addValue("P_RATING_DESDE", request.getRatingDesde())
				.addValue("P_RATING_HASTA", request.getRatingHasta())
				.addValue("P_VALOR_RATING", request.getValor())
				.addValue("p_tipo_contrato", request.getTipoContrato());
		
		Map<String, Object> out = this.agregarCosteoRating.execute(in);
		return (String) out.get("P_ERROR");
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Costeo> obtenerRegistrosMixto(BuscarTipoDeCosteoRequest request) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("contrato", request.getContrato())
				.addValue("senial", request.getSenial())
				.addValue("nroGrupo", request.getNroGrupo());
		
		Map<String, Object> out = this.getListaMixto.execute(in);
		return (List<Costeo>) out.get("P_DATOS");
	}
	@Override
	public String agregarRegistrosMixto(TipoDeCosteoRegistroRequest request) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_contrato", request.getContrato())
				.addValue("p_grupo", request.getNroGrupo())
				.addValue("p_senial", request.getSenial())
				.addValue("p_mes", request.getMes())
				.addValue("p_anio", request.getAnio())
				.addValue("p_valorcfm", request.getValor())
				.addValue("p_tipo_contrato", request.getTipoContrato());
		
		Map<String, Object> out = this.agregarCosteoMixto.execute(in);
		return (String) out.get("P_ERROR");
	}
	@Override
	public String eliminarRegistro(TipoDeCosteoEliminarRequest request, int p_tipo_costeo) {
		/*
		  	P_TIPO_COSTEO
		  	IF P_TIPO_COSTEO = 1  	THEN  	--SI ES TITULO_RATING 
        	ELSIF P_TIPO_COSTEO = 2 THEN 	--SI ES TITULO EXCEDENTE 
        	ELSIF P_TIPO_COSTEO = 3 THEN	--SI ES TITULO MIXTO
        	ELSIF P_TIPO_COSTEO = 4 THEN    --  TITULO CFM 
		 */
		
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_contrato", request.getContrato())
				.addValue("p_grupo", request.getNroGrupo())
				.addValue("p_senial", request.getSenial())
				.addValue("p_valor1", request.getValor1())
				.addValue("p_valor2", request.getValor2())
				.addValue("p_valor3", request.getValor3())
				.addValue("p_tipo_costeo", p_tipo_costeo);
		
		Map<String, Object> out = this.eliminarRegistro.execute(in);
		return (String) out.get("P_ERROR");
	}
	@Override
	public String abmRegistroExcedente(TipoDeCosteoABMExcedenteRequest request) {
		/*
PR_ESCALAEXC_AMB (Alta Modificación baja Escala Excedente)
Parámetros: p_tipo_titulo Tipo de Titulo
p_numero_titulo Nro de Titulo
p_senial Señal
p_contrato Nro de contrato
p_grupo Grupo
p_accion Acción (I) Insertar, (U) Actualizar, (D) Eliminar
p_exc_desde Excedente desde
p_exc_hasta Excedente hasta
p_valor Monto a cambiar
p_canje Canje
		 */
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_tipo_titulo", request.getTipoTitulo())
				.addValue("p_numero_titulo", Integer.valueOf(request.getTitulo()))
				.addValue("p_senial", request.getSenial())
				.addValue("p_contrato", request.getContrato())
				.addValue("p_grupo", request.getNroGrupo())
				.addValue("p_accion", request.getAccion())
				.addValue("p_exc_desde", request.getValor1())
				.addValue("p_exc_hasta", request.getValor2())
				.addValue("p_valor", request.getValor3())
				.addValue("p_canje", request.getCanje());
		
		Map<String, Object> out = this.abmRegistroExcedente.execute(in);
		return (String) out.get("P_ERROR");
	}
	@Override
	public String abmRegistroRating(TipoDeCosteoABMRatingRequest request) {
		/*
		PR_ESCALARATING_AMB (Alta/Modificación/Baja EscalaRating)
		Parámetros: p_tipo_titulo Tipo de Titulo
		p_numero_titulo Nro de Titulo
		p_senial Señal
		p_contrato Nro de contrato
		p_grupo Grupo
		p_accion Accion (I) Insertar, (U) Actualizar, (D) Eliminar
		p_rat_desde Rating desde
		p_rat_hasta Rating hasta
		p_valor Monto a cambiar
		p_canje Canje
		 */
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_tipo_titulo", request.getTipoTitulo())
				.addValue("p_numero_titulo", Integer.valueOf(request.getTitulo()))
				.addValue("p_senial", request.getSenial())
				.addValue("p_contrato", request.getContrato())
				.addValue("p_grupo", request.getNroGrupo())
				.addValue("p_accion", request.getAccion())
				.addValue("p_rat_desde", request.getValor1())
				.addValue("p_rat_hasta", request.getValor2())
				.addValue("p_valor", request.getValor3())
				.addValue("p_canje", request.getCanje());
		
		Map<String, Object> out = this.abmRegistroRating.execute(in);
		return (String) out.get("P_ERROR");
	}
	@Override
	public String abmRegistroCFM(TipoDeCosteoABMRequest request) {
		/*
		PR_CFMENSUAL_AMB (Alta Modificación Baja CostoFijoMnsual(CFM))
		Parámetros: p_tipo_titulo Tipo de Titulo
		p_numero_titulo Nro de Titulo
		p_mes Mes
		p_anio Año
		p_senial Señal
		p_contrato Nro de contrato
		p_grupo Grupo
		p_accion Acción (I) Insertar, (U) Actualizar, (D) Eliminar
		p_valor Monto a cambiar
		p_canje Canje
		 */
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_tipo_titulo", request.getTipoTitulo())
				.addValue("p_numero_titulo", Integer.valueOf(request.getTitulo()))
				.addValue("p_mes", request.getMes())
				.addValue("p_anio", request.getAnio())
				.addValue("p_senial", request.getSenial())
				.addValue("p_contrato", request.getContrato())
				.addValue("p_grupo", request.getNroGrupo())
				.addValue("p_accion", request.getAccion())
				.addValue("p_valor", request.getValor())
				.addValue("p_canje", request.getCanje());
		
		Map<String, Object> out = this.abmRegistroCFM.execute(in);
		return (String) out.get("P_ERROR");
	}
	@Override
	public String abmRegistroMixto(TipoDeCosteoABMRequest request) {
		/*
		PR_CFMIXTO_AMB (Alta Modificación Baja CF MIxto)
		Parámetros: p_tipo_titulo Tipo de Titulo
		p_numero_titulo Nro de Titulo
		p_mes Mes
		p_anio Año
		p_senial Señal
		p_contrato Nro de contrato
		p_grupo Grupo
		p_accion Accion (I) Insertar, (U) Actualizar, (D) Eliminar
		p_valor Monto a cambiar
		p_canje Canje
		 */
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_tipo_titulo", request.getTipoTitulo())
				.addValue("p_numero_titulo", Integer.valueOf(request.getTitulo()))
				.addValue("p_mes", request.getMes())
				.addValue("p_anio", request.getAnio())
				.addValue("p_senial", request.getSenial())
				.addValue("p_contrato", request.getContrato())
				.addValue("p_grupo", request.getNroGrupo())
				.addValue("p_accion", request.getAccion())
				.addValue("p_valor", request.getValor())
				.addValue("p_canje", request.getCanje());
		Map<String, Object> out = this.abmRegistroMixto.execute(in);
		return (String) out.get("P_ERROR");
	}
	@Override
	public String validarSeleccion(TipoDeCosteoValidarSeleccionRequest request) {
		/*
		  	P_TIPO_COSTEO
		  	IF P_TIPO_COSTEO = 1  	THEN  	--SI ES TITULO_RATING 
        	ELSIF P_TIPO_COSTEO = 2 THEN 	--SI ES TITULO EXCEDENTE 
        	ELSIF P_TIPO_COSTEO = 3 THEN	--SI ES TITULO MIXTO
        	ELSIF P_TIPO_COSTEO = 4 THEN    --  TITULO CFM 
		 */
		/*
		 * params:
		 * 
		 	P_CONTRATO GR6001.COKCON%TYPE , 
			P_GRUPO GR6001.GRKGRUPO%TYPE,
            P_SENIAL GR6001.SNKCOD%TYPE,  
            P_SELECCION IN NUMBER
		 */
		
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_contrato", request.getContrato())
				.addValue("p_grupo", request.getNroGrupo())
				.addValue("p_senial", request.getSenial())
				.addValue("p_seleccion", request.getIdSeleccion());

		String val = this.validarSeleccion.executeFunction(String.class, in);
		return val;
	}
}