package com.artear.filmo.bo.contratos;

import java.util.List;
import java.util.Map;

public class ValidarModificacionGrupoCCResponse {

	private Boolean hayErrores;
	private List<Map<String, String>> errores;
	private Integer idReporte;

	public Boolean getHayErrores() {
		return hayErrores;
	}

	public void setHayErrores(Boolean hayErrores) {
		this.hayErrores = hayErrores;
	}

	public List<Map<String, String>> getErrores() {
		return errores;
	}
	
	public void setErrores(List<Map<String, String>> errores) {
		this.errores = errores;
	}

	public Integer getIdReporte() {
		return idReporte;
	}

	public void setIdReporte(Integer idReporte) {
		this.idReporte = idReporte;
	}
	
}