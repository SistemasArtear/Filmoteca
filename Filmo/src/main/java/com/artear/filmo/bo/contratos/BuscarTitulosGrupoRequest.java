package com.artear.filmo.bo.contratos;

public class BuscarTitulosGrupoRequest {

	private Integer contrato;
	private Integer grupo;
	private String senial;
	private String descripcion;
	private String tipoBusqueda;
	private String tipoTitulo;

	public Integer getContrato() {
		return contrato;
	}
	
	public void setContrato(Integer contrato) {
		this.contrato = contrato;
	}
	
	public Integer getGrupo() {
		return grupo;
	}
	
	public void setGrupo(Integer grupo) {
		this.grupo = grupo;
	}
	
	public String getSenial() {
		return senial;
	}

	public void setSenial(String senial) {
		this.senial = senial;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getTipoBusqueda() {
		return tipoBusqueda;
	}
	
	public void setTipoBusqueda(String tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}
	
	public String getTipoTitulo() {
		return tipoTitulo;
	}
	
	public void setTipoTitulo(String tipoTitulo) {
		this.tipoTitulo = tipoTitulo;
	}
	
}