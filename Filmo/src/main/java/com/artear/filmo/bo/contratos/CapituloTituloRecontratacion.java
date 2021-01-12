package com.artear.filmo.bo.contratos;

public class CapituloTituloRecontratacion {

	private String clave;
	private Integer numeroCapitulo;
	private Integer parte;
	private String tituloCapitulo;
	private String sinContrato;

	public String getClave() {
		return clave;
	}
	
	public void setClave(String clave) {
		this.clave = clave;
	}
	
	public Integer getNumeroCapitulo() {
		return numeroCapitulo;
	}

	public void setNumeroCapitulo(Integer numeroCapitulo) {
		this.numeroCapitulo = numeroCapitulo;
	}

	public Integer getParte() {
		return parte;
	}

	public void setParte(Integer parte) {
		this.parte = parte;
	}

	public String getTituloCapitulo() {
		return tituloCapitulo;
	}

	public void setTituloCapitulo(String tituloCapitulo) {
		this.tituloCapitulo = tituloCapitulo;
	}
	
	public String getSinContrato() {
		return sinContrato;
	}
	
	public void setSinContrato(String sinContrato) {
		this.sinContrato = sinContrato;
	}
	
}