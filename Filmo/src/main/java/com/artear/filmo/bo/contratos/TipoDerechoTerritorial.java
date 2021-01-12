package com.artear.filmo.bo.contratos;

public class TipoDerechoTerritorial {

	private String codigo;
	private String descripcion;

	public TipoDerechoTerritorial() {
		super();
	}

	public TipoDerechoTerritorial(String codigo, String descripcion) {
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
