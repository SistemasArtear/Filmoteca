package com.artear.filmo.bo.chequeo.tecnico;

public class VerificarDesenlaceRequest {

	private String nroFicha;
	private String clave;
	private Integer capitulo;
	private Integer parte;
	private String okFilmo;
	
	
	public String getNroFicha() {
		return nroFicha;
	}
	public void setNroFicha(String nroFicha) {
		this.nroFicha = nroFicha;
	}
	
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public Integer getCapitulo() {
		return capitulo;
	}
	public void setCapitulo(Integer capitulo) {
		this.capitulo = capitulo;
	}
	public Integer getParte() {
		return parte;
	}
	public void setParte(Integer parte) {
		this.parte = parte;
	}
	public String getOkFilmo() {
		return okFilmo;
	}
	public void setOkFilmo(String okFilmo) {
		this.okFilmo = okFilmo;
	}	
	
	
	
	
}