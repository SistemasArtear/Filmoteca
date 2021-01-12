package com.artear.filmo.bo.trabajar.remitos;

public class ImprimirRemitoResponse {

	private String tipoTitulo; 
	private Integer nroTitulo; 
	private Integer nroCapitulo;
	private Integer nroParte;
	private String soporte;

	public String getTipoTitulo() {
		return tipoTitulo;
	}

	public void setTipoTitulo(String tipoTitulo) {
		this.tipoTitulo = tipoTitulo;
	}

	public Integer getNroTitulo() {
		return nroTitulo;
	}

	public void setNroTitulo(Integer nroTitulo) {
		this.nroTitulo = nroTitulo;
	}

	public Integer getNroCapitulo() {
		return nroCapitulo;
	}

	public void setNroCapitulo(Integer nroCapitulo) {
		this.nroCapitulo = nroCapitulo;
	}

	public Integer getNroParte() {
		return nroParte;
	}

	public void setNroParte(Integer nroParte) {
		this.nroParte = nroParte;
	}

	public String getSoporte() {
		return soporte;
	}

	public void setSoporte(String soporte) {
		this.soporte = soporte;
	}
	
}