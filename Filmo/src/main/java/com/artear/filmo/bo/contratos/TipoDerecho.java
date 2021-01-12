package com.artear.filmo.bo.contratos;

public class TipoDerecho {

	private String codigo;
	private String descripcion;

	public TipoDerecho() {
		super();
	}

	public TipoDerecho(String codigo, String descripcion) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
