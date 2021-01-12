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

import com.artear.filmo.bo.programacion.modificarRatingExcedente.BusquedaTitulosRatingExcedenteRequest;
import com.artear.filmo.bo.programacion.modificarRatingExcedente.BusquedaTitulosRatingExcedenteResponse;
import com.artear.filmo.bo.programacion.modificarRatingExcedente.ModificarRatingExcedenteRequest;
import com.artear.filmo.daos.interfaces.RatingExcedenteDao;

@Repository("buscarRatingExcedenteDao")
public class RatingExcedenteDaoPL implements RatingExcedenteDao {
	private SimpleJdbcCall prGrillaPorCosteos;
	private SimpleJdbcCall prUpdateExcedente;
	private SimpleJdbcCall prUpdateRating;
	
	@Autowired
	public RatingExcedenteDaoPL(DataSource dataSource){
		this.prGrillaPorCosteos = buildPrGrillaPorCosteosCall(dataSource);
		this.prUpdateExcedente = buildPrUpdateExcedente(dataSource);
		this.prUpdateRating = buildPrUpdateRating(dataSource);
	}
	
	private SimpleJdbcCall buildPrGrillaPorCosteosCall(DataSource dataSource){
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_MODIF_RATING_EXCEDENTES").withProcedureName("PR_GRILLAS_POR_COSTEOS")
                .returningResultSet("P_DATOS", new RowMapper<BusquedaTitulosRatingExcedenteResponse>() {
                    public BusquedaTitulosRatingExcedenteResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                    	BusquedaTitulosRatingExcedenteResponse response = new BusquedaTitulosRatingExcedenteResponse();
                    	response.setTipoNroTitulo(rs.getString("TIPONROTIT"));
                    	response.setCapitulo(rs.getInt("NRO_CAP"));
                    	response.setGrupo(rs.getInt("GRUPO"));
                    	response.setContrato(rs.getInt("CONTRATO"));
                    	response.setFechaExhibicion(rs.getDate("F_EXH"));
                    	response.setValorRatingExcedente(rs.getDouble("VALORRATINGEXCE"));
                    	response.setCodigoDesPrograma(rs.getString("DESCRIP"));
                    	response.setTipoRatingExcedente(rs.getString("TIPORATINGEXCE"));
                    	return response;
                    }
                });
	}
	
	private SimpleJdbcCall buildPrUpdateExcedente(DataSource dataSource){
		return new SimpleJdbcCall(dataSource).withCatalogName("FIL_MODIF_RATING_EXCEDENTES").withProcedureName("PR_UPD_EXCEDENTE");
	}
	
	private SimpleJdbcCall buildPrUpdateRating(DataSource dataSource){
		return new SimpleJdbcCall(dataSource).withCatalogName("FIL_MODIF_RATING_EXCEDENTES").withProcedureName("PR_UPD_RATING");
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BusquedaTitulosRatingExcedenteResponse> buscarRatingExcedentes(BusquedaTitulosRatingExcedenteRequest request) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_FECHA_EXCFIL", request.getFechaExhibicion())
															.addValue("P_NOMBRE_PROGRAMAFIL", request.getPrograma().getDescripcion())
															.addValue("P_TIPO_NRO_TITULOFI", request.getTipoNroTitulo())
															.addValue("P_SENIAL", request.getidSenial());
		
		Map<String, Object> out = this.prGrillaPorCosteos.execute(in);
		
		return (List<BusquedaTitulosRatingExcedenteResponse>) out.get("P_DATOS");
	}

	@Override
	public void modificarExcedente(ModificarRatingExcedenteRequest request) throws Exception {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_FECHA_EXC", request.getFechaExhibicion())
															.addValue("P_COD_PROGRAMA", request.getPrograma().getCodigo())
															.addValue("P_TIPO_TITULO", request.getTipoTitulo())
															.addValue("P_NRO_TITULO", request.getNumeroTitulo())
															.addValue("P_NRO_CAPITULO", request.getNroCapitulo())
															.addValue("P_CONTRATO", request.getContrato())
															.addValue("P_SENIAL", request.getIdSenial())
															.addValue("P_VALOR_EXCEDENTE", request.getValorRatingExcedente())
															.addValue("P_GRUPO", request.getGrupo());
		
		Map<String, Object> out = this.prUpdateExcedente.execute(in);
		
		if(out.get("P_ERROR") != null)
			throw new Exception(out.get("P_ERROR").toString());
		
	}

	@Override
	public void modificarRating(ModificarRatingExcedenteRequest request) throws Exception {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_FECHA_EXC", request.getFechaExhibicion())
															.addValue("P_COD_PROGRAMA", request.getPrograma().getCodigo())
															.addValue("P_TIPO_TITULO", request.getTipoTitulo())
															.addValue("P_NRO_TITULO", request.getNumeroTitulo())
															.addValue("P_NRO_CAPITULO", request.getNroCapitulo())
															.addValue("P_CONTRATO", request.getContrato())
															.addValue("P_SENIAL", request.getIdSenial())
															.addValue("P_RATING", request.getValorRatingExcedente())
															.addValue("P_GRUPO", request.getGrupo());

		Map<String, Object> out = this.prUpdateRating.execute(in);
		
		if(out.get("P_ERROR") != null)
			throw new Exception(out.get("P_ERROR").toString());
	}
}
