package com.artear.filmo.reporte;


import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.artear.filmo.seguridad.services.ServiciosSesionUsuario;
import com.artear.filmo.services.interfaces.GenerarReportesService;

@Service
public class GeneradorDeReportes implements GenerarReportesService {

	
	private JdbcTemplate jdbcTemplate;
	private static final Log logger = LogFactory.getLog(GeneradorDeReportes.class);
	@Autowired
	private ServiciosSesionUsuario serviciosSesionUsuario;
	
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	
	/**
	 * Genera un tipo de reporte.
	 */
	public byte[] generarReporte (Map<String, Object> parametros, String pathJasper, String nombreReporte) throws SQLException, JRException, Exception {
		logger.info("GeneradorDeReportes.generarReporte");
		
		Validate.notNull(this.jdbcTemplate);
		
		byte[] arrayByte = null;
		String pathFilmo = System.getProperty("Filmo");
		String pathCarpetaReportes = pathFilmo + pathJasper;
		logger.info("pathFilmo+pathReporteJasper: "+pathCarpetaReportes);
		Connection connection = this.jdbcTemplate.getDataSource().getConnection();			
		try {			
			JasperPrint jasperPrint = JasperFillManager.fillReport(pathCarpetaReportes, parametros, connection);
			removeEmptyPages(jasperPrint);			
			arrayByte = JasperExportManager.exportReportToPdf(jasperPrint);	
			logger.info("jboss.server.home.dir: " + System.getProperty("jboss.server.home.dir"));
			String pathReports = System.getProperty("jboss.server.home.dir") + "/reports/";

			SimpleDateFormat personalFormat = new SimpleDateFormat("ddMMyy_HHmmss");
	        String timestamp = personalFormat.format(new Date());
	        String pathAndName = pathReports + serviciosSesionUsuario.getUsuario().toUpperCase() + "_" +  nombreReporte + timestamp +".pdf";
	        JasperExportManager.exportReportToPdfFile(jasperPrint, pathAndName);
		} catch (JRException j) {			
			logger.info("Error al generar el reporte. Error: " +j.getMessage());
			throw j;
		} 
		finally{
			connection.close();
		}
		
		return arrayByte;
	}

	/**
	 * Remueve las paginas en blanco del reporte
	 * @param jasperPrint
	 */
	private void removeEmptyPages(JasperPrint jasperPrint) {
		@SuppressWarnings("unchecked")
		List<JRPrintPage> paginas = (List<JRPrintPage>) jasperPrint.getPages();
		List<JRPrintPage> paginasToRemove = new ArrayList<JRPrintPage>();

		for (JRPrintPage pagina : paginas) {
			if (pagina.getElements().size() == 0) {
				paginasToRemove.add(pagina);
			}
		}
		paginas.removeAll(paginasToRemove);
	}

}
