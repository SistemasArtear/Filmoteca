package com.artear.filmo.bo.contratos;

import java.util.List;

public class SegundaValidacionRerunBajaCapitulosResponse {

	private boolean hayErrores;
	private List<ErrorDesenlaceResponse> erroresDesenlace;
	private List<ErrorVigenciaResponse> erroresVigencia;
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

	public List<ErrorVigenciaResponse> getErroresVigencia() {
		return erroresVigencia;
	}

	public void setErroresVigencia(List<ErrorVigenciaResponse> erroresVigencia) {
		this.erroresVigencia = erroresVigencia;
	}

	public Integer getIdReporte() {
		return idReporte;
	}

	public void setIdReporte(Integer idReporte) {
		this.idReporte = idReporte;
	}
	
}