package com.artear.filmo.bo.trabajar.remitos;

public class ActualizarCantidadRequest {

	private String clave;
	private Integer capitulo;
	private Integer parte;

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
	
}