package com.artear.filmo.bo.contratos;

import java.util.List;

public class ValidarDesenlaceSCResponse {

	private boolean hayErrores;
	private List<ErrorDesenlaceResponse> erroresDesenlace;
	private Integer idReporte;

	public boolean isHayErrores() {
		return hayErrores;
	}

	public void setHayErrores(boolean hayErrores) {
		this.hayErrores = hayErrores;
	}
	
	public List<ErrorDesenlaceResponse> getErroresDesenlace() {
		return erroresDesenlace;
	}
	
	public void setErroresDesenlace(List<ErrorDesenlaceResponse> erroresDesenlace) {
		this.erroresDesenlace = erroresDesenlace;
	}
	
	public Integer getIdReporte() {
		return idReporte;
	}

	public void setIdReporte(Integer idReporte) {
		this.idReporte = idReporte;
	}

}