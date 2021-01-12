package com.artear.filmo.bo.trabajar.titulos;

public enum OperacionTitulo {

	ALTA("A"), BAJA("B"), MODIFICACION("M"), VISUALIZAR("V");

	private String descripcion;

	private OperacionTitulo(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

}
