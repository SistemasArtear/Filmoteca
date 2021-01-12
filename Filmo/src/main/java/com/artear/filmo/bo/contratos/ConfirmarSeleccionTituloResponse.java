package com.artear.filmo.bo.contratos;

public class ConfirmarSeleccionTituloResponse {

	private String rerun;
	private String tipoError;
	private String descripcion;
	private String coincideMonto;

	public String getRerun() {
		return rerun;
	}
	
	public void setRerun(String rerun) {
		this.rerun = rerun;
	}
	
	public String getTipoError() {
		return tipoError;
	}

	public void setTipoError(String tipoError) {
		this.tipoError = tipoError;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCoincideMonto() {
		return coincideMonto;
	}

	public void setCoincideMonto(String coincideMonto) {
		this.coincideMonto = coincideMonto;
	}
	
	
}