package com.artear.filmo.daos.impl.pl;

import java.math.BigDecimal;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.artear.filmo.bo.chequeoOperativo.ChequeoOperativoResponse;
import com.artear.filmo.daos.interfaces.ChequeoOperativoDao;

@Repository("chequeoOperativoDao")
public class ChequeoOperativoDaoPL implements ChequeoOperativoDao{

	private SimpleJdbcCall validarChequeoCall;
	private SimpleJdbcCall altaEnLibreriaCall;
	private SimpleJdbcCall confirmaChequeoOperativoCall;
	private SimpleJdbcCall exhibicionCall;
	private SimpleJdbcCall procesarChequeoOperativoCall;
	private SimpleJdbcCall backUpCopiaMensual;
	
	private static final String PACKAGE_NAME_CIERRE_FAC = "fil_pkg_cierre_facturacion";
	
	@Autowired
	public ChequeoOperativoDaoPL(DataSource dataSource){
		super();
		validarChequeoCall = this.buildValidarChequeoCall(dataSource);
		altaEnLibreriaCall = this.buildAltaEnLibreriaCall(dataSource);
		confirmaChequeoOperativoCall = this.buildConfirmaChequeoOperativoCall(dataSource);
		exhibicionCall = this.buildExhibicionCall(dataSource);
		procesarChequeoOperativoCall = this.buildProcesarChequeoOperativoCall(dataSource);
		backUpCopiaMensual = this.buildBackUpCopiaMensualCall(dataSource);
	}
	
    private SimpleJdbcCall buildBackUpCopiaMensualCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource)
                .withSchemaName("FILMOBACKUP")
                .withCatalogName("PKG_FILMO_BACKUP")
                .withProcedureName("PR_COPIA_MENSUAL");
    }

    private SimpleJdbcCall buildProcesarChequeoOperativoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_CIERRE_FAC).withProcedureName("pr_procesa_chqueo_operativo");
	}

	private SimpleJdbcCall buildValidarChequeoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_CIERRE_FAC).withProcedureName("pr_validar_chequeo");
	}

	private SimpleJdbcCall buildAltaEnLibreriaCall(DataSource dataSource){
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_CIERRE_FAC).withProcedureName("pr_alta_en_libreria");
	}
	
	private SimpleJdbcCall buildConfirmaChequeoOperativoCall(DataSource dataSource){
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_CIERRE_FAC).withProcedureName("pr_confirma_chequeo_operativo");
	}
	
	private SimpleJdbcCall buildExhibicionCall(DataSource dataSource){
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_CIERRE_FAC).withProcedureName("pr_exhibicion");
	}
	
	@Override
	public ChequeoOperativoResponse validarChequeoOperativo(String periodo) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_PERIODO", periodo);
														  
		Map<String, Object> out = this.validarChequeoCall.execute(in);
		
		ChequeoOperativoResponse response = new ChequeoOperativoResponse();
		BigDecimal codError = (BigDecimal)out.get("P_ERROR");
		if (codError == null || "".equals(codError) || codError.intValue() == 0 ){
			response.setHayError(false);
		} 
		else {
			response.setHayError(true);
			response.setCodigoError(codError);
			response.setMensajeError((String)out.get("P_MENSAJE"));
		}
		return response;
		
	}

	@Override
	public ChequeoOperativoResponse altaEnLibreria(String periodo) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_PERIODO", periodo);
		Map<String, Object> out = this.altaEnLibreriaCall.execute(in);
		ChequeoOperativoResponse response = new ChequeoOperativoResponse();
		BigDecimal encontroReg = (BigDecimal) out.get("P_ENCONTRO");
		response.setHayRegistrosLibreria(encontroReg.intValue() == 0?false: true);
		response.setIdReporteLibreria((BigDecimal)out.get("P_ID"));
		return response;
	}

	@Override
	public ChequeoOperativoResponse confirmaChequeoOperativo(Integer mes, Integer anio) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_MES", mes)
				   .addValue("P_ANIO", anio);
		Map<String, Object> out = this.confirmaChequeoOperativoCall.execute(in);

		ChequeoOperativoResponse response = new ChequeoOperativoResponse();
		BigDecimal codError = (BigDecimal)out.get("P_ERROR");
		if (codError == null || "".equals(codError)){
			response.setHayError(false);
		}
		else {
			response.setHayError(true);
			response.setCodigoError(codError);
			response.setMensajeError((String)out.get("P_MENSAJE"));
		}
		return response;
	}


	@Override
	public void procesarChequeoOperativo(Integer mes, Integer anio) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_MES", mes)
															.addValue("p_ANIO", anio);
		this.procesarChequeoOperativoCall.execute(in);
		
	}

	@Override
	public ChequeoOperativoResponse exhibicion(Integer mes, Integer anio) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("p_mes", mes)
														   .addValue("p_anio", anio);
		Map<String, Object> out = this.exhibicionCall.execute(in);
		ChequeoOperativoResponse response = new ChequeoOperativoResponse();
		BigDecimal encontroReg = (BigDecimal) out.get("P_ENCONTRO");
		response.setHayRegistrosExhibicion(encontroReg.intValue() == 0?false: true);
		response.setIdReporteEhibicion((BigDecimal)out.get("P_ID"));
		return response;
	}

    @Override
    public boolean backUpCopiaMensual(String username) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("P_USUARIO", username);
        this.backUpCopiaMensual.execute(in);
        return true;
    }
}
