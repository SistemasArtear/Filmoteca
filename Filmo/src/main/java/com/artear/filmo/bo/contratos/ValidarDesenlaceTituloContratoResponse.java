package com.artear.filmo.bo.contratos;

import java.util.List;

public class ValidarDesenlaceTituloContratoResponse {

	private boolean hayErrores;
	private List<ErrorDesenlaceResponse> errores;
	private Integer idReporte;

	public boolean isHayErrores() {
		return hayErrores;
	}

	public void setHayErrores(boolean hayErrores) {
		this.hayErrores = hayErrores;
	}
	
	public List<ErrorDesenlaceResponse> getErrores() {
		return errores;
	}
	
	public void setErrores(List<ErrorDesenlaceResponse> errores) {
		this.errores = errores;
	}

	public Integer getIdReporte() {
		return idReporte;
	}

	public void setIdReporte(Integer idReporte) {
		this.idReporte = idReporte;
	}
	
}