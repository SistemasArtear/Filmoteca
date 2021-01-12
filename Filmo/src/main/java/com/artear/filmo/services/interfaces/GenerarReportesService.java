package com.artear.filmo.services.interfaces;

import java.sql.SQLException;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;




public interface GenerarReportesService {
	
	public byte[] generarReporte(Map<String, Object> parametros, String reporte, String nombreReporte) throws SQLException, JRException, Exception ;

}