package com.artear.filmo.bo.contratos;

import java.util.List;

public class ValidarEliminacionTituloContratoResponse {

	private boolean hayErrores;
	private List<ErrorDesenlaceResponse> erroresDesenlace;
	private Integer idReporte;
	private String mensaje;

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
	
	public String getMensaje() {
		return mensaje;
	}
	
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
}