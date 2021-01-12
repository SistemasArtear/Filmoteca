package com.artear.filmo.bo.contratos;

import java.util.List;

public class DesenlaceContratoSenialResponse {

	private String error;
	private List<ErrorDesenlaceResponse> errores;
	private Integer idReporte;
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
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