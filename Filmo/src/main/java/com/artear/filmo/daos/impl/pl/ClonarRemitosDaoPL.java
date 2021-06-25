package com.artear.filmo.daos.impl.pl;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.artear.filmo.bo.clonar.remitos.ClonarGrillaResponse;
import com.artear.filmo.bo.clonar.remitos.ClonarRemitosRequest;
import com.artear.filmo.daos.interfaces.ClonarRemitosDao;

@Repository("clonarRemitosDao")
public class ClonarRemitosDaoPL implements ClonarRemitosDao {
	
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall clonarRemitosSinContratoCall;
	private SimpleJdbcCall clonarRemitosConContratoCall;
	
	private static final String GET_REMITOS_SIN_CONTRATO_POR_TIPO_NRO = "select distinct rt.RTNNUMREM, rm.TTKTPOTIT , rm.MTKNROTIT, mt.MTDTITCAS, mt.MTDTITORI, rt.PRKPRO, pr.PRDRAZSOC from rm6001 rm "
			+ "left join MT6001 mt on rm.TTKTPOTIT = mt.TTKTPOTIT and rm.MTKNROTIT = mt.MTKNROTIT "
			+ " inner join RT6001 rt on rm.RTKIDREM = rt.RTKIDREM "
			+ "left join PR6001 pr on rt.PRKPRO = pr.PRKPRO "
			+ "where rm.TTKTPOTIT = ? and rm.mtknrotit = ?";
	
	private static final String GET_REMITOS_CON_CONTRATO_POR_TIPO_NRO = "select distinct rt.RTNNUMREM, rc.TTKTPOTIT , rc.MTKNROTIT,rc.RMKNUMCAP, mt.MTDTITCAS, mt.MTDTITORI, rt.PRKPRO, pr.PRDRAZSOC, rc.RCKNUMCON, rc.RCKNROGRP from rc6001 rc "
			+ "left join MT6001 mt on rc.TTKTPOTIT = mt.TTKTPOTIT and rc.MTKNROTIT = mt.MTKNROTIT "
			+ " inner join RT6001 rt on rc.RTKIDREM = rt.RTKIDREM "
			+ "left join PR6001 pr on rt.PRKPRO = pr.PRKPRO "
			+ "where rc.TTKTPOTIT = ? and rc.mtknrotit = ? "
			+ "and rc.RCKNUMCON = ? and rc.RCKNROGRP = ?";
	
	private static final String GET_REMITOS_SIN_CONTRATO_POR_TITULO_ORIGINAL = "select distinct rt.RTNNUMREM, rm.TTKTPOTIT , rm.MTKNROTIT, mt.MTDTITCAS, mt.MTDTITORI, rt.PRKPRO, pr.PRDRAZSOC from rm6001 rm "
			+ "left join MT6001 mt on rm.TTKTPOTIT = mt.TTKTPOTIT and rm.MTKNROTIT = mt.MTKNROTIT "
			+ " inner join RT6001 rt on rm.RTKIDREM = rt.RTKIDREM "
			+ "left join PR6001 pr on rt.PRKPRO = pr.PRKPRO "
			+ "where UPPER(mt.MTDTITORI) LIKE ";
	
	private static final String GET_REMITOS_CON_CONTRATO_POR_TITULO_ORIGINAL = "select distinct rt.RTNNUMREM, rc.TTKTPOTIT , rc.MTKNROTIT,rc.RMKNUMCAP, mt.MTDTITCAS, mt.MTDTITORI, rt.PRKPRO, pr.PRDRAZSOC, rc.RCKNUMCON, rc.RCKNROGRP from rc6001 rc "
			+ "left join MT6001 mt on rc.TTKTPOTIT = mt.TTKTPOTIT and rc.MTKNROTIT = mt.MTKNROTIT "
			+ " inner join RT6001 rt on rc.RTKIDREM = rt.RTKIDREM "
			+ "left join PR6001 pr on rt.PRKPRO = pr.PRKPRO "
			+ "where UPPER(mt.MTDTITORI) LIKE ";
	
	private static final String GET_REMITOS_SIN_CONTRATO_POR_TITULO_CASTELLANO = "select distinct rt.RTNNUMREM, rm.TTKTPOTIT , rm.MTKNROTIT, mt.MTDTITCAS, mt.MTDTITORI, rt.PRKPRO, pr.PRDRAZSOC from rm6001 rm "
			+ "left join MT6001 mt on rm.TTKTPOTIT = mt.TTKTPOTIT and rm.MTKNROTIT = mt.MTKNROTIT "
			+ " inner join RT6001 rt on rm.RTKIDREM = rt.RTKIDREM "
			+ "left join PR6001 pr on rt.PRKPRO = pr.PRKPRO "
			+ "where UPPER(mt.MTDTITCAS) LIKE ";
	
	private static final String GET_REMITOS_CON_CONTRATO_POR_TITULO_CASTELLANO = "select distinct rt.RTNNUMREM, rc.TTKTPOTIT , rc.MTKNROTIT,rc.RMKNUMCAP, mt.MTDTITCAS, mt.MTDTITORI, rt.PRKPRO, pr.PRDRAZSOC, rc.RCKNUMCON, rc.RCKNROGRP from rc6001 rc "
			+ "left join MT6001 mt on rc.TTKTPOTIT = mt.TTKTPOTIT and rc.MTKNROTIT = mt.MTKNROTIT "
			+ " inner join RT6001 rt on rc.RTKIDREM = rt.RTKIDREM "
			+ "left join PR6001 pr on rt.PRKPRO = pr.PRKPRO "
			+ "where UPPER(mt.MTDTITCAS) LIKE ";

	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Autowired
	public ClonarRemitosDaoPL(DataSource dataSource) {
		super();
		this.clonarRemitosSinContratoCall = buildClonarRemitosSinContrato(dataSource);
		this.clonarRemitosConContratoCall = buildClonarRemitosConContrato(dataSource);
	}
	
	@Override
	public List<ClonarGrillaResponse> buscarRemitosSinContratoPorClave(String tipoTitulo, Integer numeroTitulo) {
		List<ClonarGrillaResponse> listaRemitos = new ArrayList<ClonarGrillaResponse>();
		listaRemitos = (List<ClonarGrillaResponse>) jdbcTemplate.query(GET_REMITOS_SIN_CONTRATO_POR_TIPO_NRO, new Object[] { tipoTitulo, numeroTitulo }, new RowMapper<ClonarGrillaResponse>() {
			@Override
			public ClonarGrillaResponse mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
				ClonarGrillaResponse remito = new ClonarGrillaResponse();
				remito.setNroRemito(rs.getString("RTNNUMREM").trim());
				remito.setTipoTitulo(rs.getString("TTKTPOTIT"));
				remito.setNroTitulo(rs.getInt("MTKNROTIT"));
				remito.setTituloCastellano(rs.getString("MTDTITCAS"));
				remito.setTituloOriginal(rs.getString("MTDTITORI"));
				remito.setIdProveedor(rs.getInt("PRKPRO"));
				remito.setRazonSocialProveedor(rs.getString("PRDRAZSOC"));
				return remito;
			}
		});
		
		return listaRemitos;
	}
	
	@Override
	public List<ClonarGrillaResponse> buscarRemitosSinContratoPorTituloOriginal(String tituloOriginal) {
		List<ClonarGrillaResponse> listaRemitos = new ArrayList<ClonarGrillaResponse>();
		String strLike = "UPPER('%" + tituloOriginal.toUpperCase() + "%')";
		listaRemitos = (List<ClonarGrillaResponse>) jdbcTemplate.query(GET_REMITOS_SIN_CONTRATO_POR_TITULO_ORIGINAL + strLike, new RowMapper<ClonarGrillaResponse>() {

			@Override
			public ClonarGrillaResponse mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
				ClonarGrillaResponse remito = new ClonarGrillaResponse();
				remito.setNroRemito(rs.getString("RTNNUMREM").trim());
				remito.setTipoTitulo(rs.getString("TTKTPOTIT"));
				remito.setNroTitulo(rs.getInt("MTKNROTIT"));
				remito.setTituloCastellano(rs.getString("MTDTITCAS"));
				remito.setTituloOriginal(rs.getString("MTDTITORI"));
				remito.setIdProveedor(rs.getInt("PRKPRO"));
				remito.setRazonSocialProveedor(rs.getString("PRDRAZSOC"));
				return remito;
			}
		});

		return listaRemitos;
	}
	
	@Override
	public List<ClonarGrillaResponse> buscarRemitosSinContratoPorTituloCastellano(String tituloCastellano) {
		List<ClonarGrillaResponse> listaRemitos = new ArrayList<ClonarGrillaResponse>();
		String strLike = "UPPER('%" + tituloCastellano.toUpperCase() + "%')";
		listaRemitos = (List<ClonarGrillaResponse>) jdbcTemplate.query(GET_REMITOS_SIN_CONTRATO_POR_TITULO_CASTELLANO + strLike, new RowMapper<ClonarGrillaResponse>() {
			
			@Override
			public ClonarGrillaResponse mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
				ClonarGrillaResponse remito = new ClonarGrillaResponse();
				remito.setNroRemito(rs.getString("RTNNUMREM").trim());
				remito.setTipoTitulo(rs.getString("TTKTPOTIT"));
				remito.setNroTitulo(rs.getInt("MTKNROTIT"));
				remito.setTituloCastellano(rs.getString("MTDTITCAS"));
				remito.setTituloOriginal(rs.getString("MTDTITORI"));
				remito.setIdProveedor(rs.getInt("PRKPRO"));
				remito.setRazonSocialProveedor(rs.getString("PRDRAZSOC"));
				return remito;
			}
		});
		
		return listaRemitos;
	}
	
	@Override
	public List<ClonarGrillaResponse> buscarRemitosConContratoPorClave(String tipoTitulo, Integer numeroTitulo, Integer contratoAnterior, Integer grupoAnterior) {
		List<ClonarGrillaResponse> listaRemitos = new ArrayList<ClonarGrillaResponse>();
		listaRemitos = (List<ClonarGrillaResponse>) jdbcTemplate.query(GET_REMITOS_CON_CONTRATO_POR_TIPO_NRO, new Object[] { tipoTitulo, numeroTitulo, contratoAnterior, grupoAnterior }, new RowMapper<ClonarGrillaResponse>() {
			@Override
			public ClonarGrillaResponse mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
				ClonarGrillaResponse remito = new ClonarGrillaResponse();
				remito.setNroRemito(rs.getString("RTNNUMREM").trim());
				remito.setTipoTitulo(rs.getString("TTKTPOTIT"));
				remito.setNroTitulo(rs.getInt("MTKNROTIT"));
				remito.setTituloCastellano(rs.getString("MTDTITCAS"));
				remito.setTituloOriginal(rs.getString("MTDTITORI"));
				remito.setIdProveedor(rs.getInt("PRKPRO"));
				remito.setRazonSocialProveedor(rs.getString("PRDRAZSOC"));
				remito.setCapitulo(rs.getInt("RMKNUMCAP"));
				remito.setContrato(rs.getInt("RCKNUMCON"));
				remito.setGrupo(rs.getInt("RCKNROGRP"));
				return remito;
			}
		});
		return listaRemitos;
	}
	
	@Override
	public List<ClonarGrillaResponse> buscarRemitosConContratoPorTituloOriginal(String tituloOriginal) {
		List<ClonarGrillaResponse> listaRemitos = new ArrayList<ClonarGrillaResponse>();
		String strLike = "UPPER('%" + tituloOriginal.toUpperCase() + "%')";
		listaRemitos = (List<ClonarGrillaResponse>) jdbcTemplate.query(GET_REMITOS_CON_CONTRATO_POR_TITULO_ORIGINAL + strLike, new RowMapper<ClonarGrillaResponse>() {

			@Override
			public ClonarGrillaResponse mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
				ClonarGrillaResponse remito = new ClonarGrillaResponse();
				remito.setNroRemito(rs.getString("RTNNUMREM").trim());
				remito.setTipoTitulo(rs.getString("TTKTPOTIT"));
				remito.setNroTitulo(rs.getInt("MTKNROTIT"));
				remito.setTituloCastellano(rs.getString("MTDTITCAS"));
				remito.setTituloOriginal(rs.getString("MTDTITORI"));
				remito.setIdProveedor(rs.getInt("PRKPRO"));
				remito.setRazonSocialProveedor(rs.getString("PRDRAZSOC"));
				remito.setCapitulo(rs.getInt("RMKNUMCAP"));
				remito.setContrato(rs.getInt("RCKNUMCON"));
				remito.setGrupo(rs.getInt("RCKNROGRP"));
				return remito;
			}
		});

		return listaRemitos;
	}

	@Override
	public List<ClonarGrillaResponse> buscarRemitosConContratoPorTituloCastellano(String tituloCastellano) {
		List<ClonarGrillaResponse> listaRemitos = new ArrayList<ClonarGrillaResponse>();
		String strLike = "UPPER('%" + tituloCastellano.toUpperCase() + "%')";
		listaRemitos = (List<ClonarGrillaResponse>) jdbcTemplate.query(GET_REMITOS_CON_CONTRATO_POR_TITULO_CASTELLANO + strLike, new RowMapper<ClonarGrillaResponse>() {
			
			@Override
			public ClonarGrillaResponse mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
				ClonarGrillaResponse remito = new ClonarGrillaResponse();
				remito.setNroRemito(rs.getString("RTNNUMREM").trim());
				remito.setTipoTitulo(rs.getString("TTKTPOTIT"));
				remito.setNroTitulo(rs.getInt("MTKNROTIT"));
				remito.setTituloCastellano(rs.getString("MTDTITCAS"));
				remito.setTituloOriginal(rs.getString("MTDTITORI"));
				remito.setIdProveedor(rs.getInt("PRKPRO"));
				remito.setRazonSocialProveedor(rs.getString("PRDRAZSOC"));
				remito.setCapitulo(rs.getInt("RMKNUMCAP"));
				remito.setContrato(rs.getInt("RCKNUMCON"));
				remito.setGrupo(rs.getInt("RCKNROGRP"));
				return remito;
			}
		});
		
		return listaRemitos;
	}
	
	private SimpleJdbcCall buildClonarRemitosSinContrato(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withSchemaName("FILMO")
//				.withSchemaName("FILMOUDN4")
				.withProcedureName("PR_CLONAR_TIT_SC")
				.withoutProcedureColumnMetaDataAccess()
				.declareParameters(
						new SqlInOutParameter("p_tipotit", Types.VARCHAR),
						new SqlInOutParameter("p_nrotit", Types.NUMERIC),
						new SqlInOutParameter("p_proveedorv", Types.NUMERIC),
						new SqlInOutParameter("p_proveedorn", Types.NUMERIC)
						);
	}
	
	private SimpleJdbcCall buildClonarRemitosConContrato(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource)
				.withSchemaName("FILMO")
//				.withSchemaName("FILMOUDN4")
				.withProcedureName("PR_CLONAR_TIT_CC")
				.withoutProcedureColumnMetaDataAccess()
				.declareParameters(
						new SqlInOutParameter("p_tipotit", Types.VARCHAR),
						new SqlInOutParameter("p_nrotit", Types.NUMERIC),
						new SqlInOutParameter("p_contratov", Types.NUMERIC),
						new SqlInOutParameter("p_grupov", Types.NUMERIC),
						new SqlInOutParameter("p_proveedorv", Types.NUMERIC),
						new SqlInOutParameter("p_contraton", Types.NUMERIC),
						new SqlInOutParameter("p_grupon", Types.NUMERIC),
						new SqlInOutParameter("p_proveedorn", Types.NUMERIC)
						);
	}

	@Override
	public void clonarRemitosSinContrato(ClonarRemitosRequest request) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_tipotit", request.getTipoTitulo())
				.addValue("p_nrotit", request.getNumeroTitulo())
				.addValue("p_proveedorv", request.getIdProveedorAnterior())
				.addValue("p_proveedorn", request.getIdProveedorNuevo());
		
		this.clonarRemitosSinContratoCall.execute(in);
	}
	
	@Override
	public void clonarRemitosConContrato(ClonarRemitosRequest request) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_tipotit", request.getTipoTitulo())
				.addValue("p_nrotit", request.getNumeroTitulo())
				.addValue("p_contratov", request.getContratoAnterior())
				.addValue("p_grupov", request.getGrupoAnterior())
				.addValue("p_proveedorv", request.getIdProveedorAnterior())
				.addValue("p_contraton", request.getContratoNuevo())
				.addValue("p_grupon", request.getGrupoNuevo())
				.addValue("p_proveedorn", request.getIdProveedorNuevo());
		
		this.clonarRemitosConContratoCall.execute(in);
	}
	
}
