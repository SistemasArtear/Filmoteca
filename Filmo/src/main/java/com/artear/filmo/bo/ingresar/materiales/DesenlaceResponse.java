package com.artear.filmo.bo.ingresar.materiales;

import java.util.List;

public class DesenlaceResponse {

	private boolean hayErrores;
	private List<String> errores;
	
	public boolean isHayErrores() {
		return hayErrores;
	}

	public void setHayErrores(boolean hayErrores) {
		this.hayErrores = hayErrores;
	}

	public List<String> getErrores() {
		return errores;
	}
	
	public void setErrores(List<String> errores) {
		this.errores = errores;
	}
	
}
