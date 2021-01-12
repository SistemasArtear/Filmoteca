package com.artear.filmo.utils;

import java.io.BufferedReader;
import java.sql.Clob;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class StringUtils extends org.apache.commons.lang.StringUtils {

	static Log logger = LogFactory.getLog(StringUtils.class);
	
	/**
	 * Concatena los valores que vienen como parametros en el vector.
	 * 
	 * @param un
	 *            vector de argumentos.
	 * @return un string que corresponde a los argumentos concatenados.
	 */
	static public String concatenate(Object[] args) {
		StringBuffer strs = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			strs.append(args[i]);
		}
		return strs.toString();
	}

	/**
	 * Convierte el contenido de un Clob a un String 
	 * 
	 * @param un clob
	 * @return el clob convertido a String
	 */
	static public String CLOBToString(Clob cl) {
		if (cl == null)
			return "";

		StringBuffer strOut = new StringBuffer();
		try{
			String aux;
			BufferedReader br = new BufferedReader(cl.getCharacterStream());
	
			// buscamos el separador por defecto del sistema operativo donde nos estamos ejecutanco
			String lineSeparator = System.getProperty("line.separator"); 
			if(lineSeparator == null || lineSeparator == "") // si no hay nada
				lineSeparator = "\n"; // usamos el de unix por defecto
			
			while ((aux = br.readLine()) != null){
				strOut.append(aux + lineSeparator); // le concatenamos un nueva linea porque estamos leyendo cada linea del CLOB. 
													// Sino concatena todo en una misma linea y no respecta como se guarda
			}
		}catch(Exception e){
			logger.error("Error convirtiendo un Clob a String", e);		
		}
		return strOut.toString();
	}
}


