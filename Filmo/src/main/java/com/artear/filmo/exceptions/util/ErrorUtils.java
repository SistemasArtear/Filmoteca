package com.artear.filmo.exceptions.util;

import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.artear.filmo.daos.impl.pl.constants.ConstantsPl;

public class ErrorUtils {

	public static ErrorFilmo processMessageError(RuntimeException ge) {

		// tratamiento de errores dependiendo si es un error en el negocio o un
		// error de sistema/base de datos
		ErrorFilmo error = new ErrorFilmo();
		if (ge.getCause() != null && ge.getCause() instanceof SQLException) {
			SQLException se = (SQLException) ge.getCause();

			if (se.getErrorCode() == new Integer(ConstantsPl.ERROR_PL_ORACLE)) {
				error.setMensaje(ge.getCause().toString());
				error.setCodigo(ConstantsPl.ERROR_PL_ORACLE);
			} else if (se.getErrorCode() == new Integer(
					ConstantsPl.ERROR_NEGOCIO)) {

				Pattern pattern = Pattern.compile("##*.*##");
				Matcher matcher = pattern.matcher(se.getMessage());
				if (matcher.find()) {
					error.setMensaje(matcher.group(0).replaceAll("#", "").toUpperCase());
				} else {
					StringTokenizer token = new StringTokenizer(
							se.getMessage(), ConstantsPl.PIPE);
					if (token != null) {
						if (token.hasMoreTokens()) {
							String cod = token.nextToken();
							error.setCodigo(cod.replace(
									ConstantsPl.ENCABEZADO_ERROR_PL_ORACLE,
									ConstantsPl.EMPTY));
						}
						if (token.hasMoreTokens()) {
							String msj = token.nextToken();
							error.setMensaje(msj.substring(0, msj
									.indexOf(ConstantsPl.ERROR_LINEA_PL_ORACLE)));
						}
					}
				}
			} else {
				throw ge;
			}
		} else {
			// error inesperado
			throw ge;
		}
		return error;

	}

}
