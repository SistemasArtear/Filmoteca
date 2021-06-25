package com.artear.filmo.bo.clonar.remitos;

public class ClonarRemitosRequest {
	String tipoTitulo;
	Integer numeroTitulo;
	Integer idProveedorAnterior;
	Integer idProveedorNuevo;
	Integer contratoAnterior;
	Integer contratoNuevo;
	Integer grupoAnterior;
	Integer grupoNuevo;

	public String getTipoTitulo() {
		return tipoTitulo;
	}

	public void setTipoTitulo(String tipoTitulo) {
		this.tipoTitulo = tipoTitulo;
	}

	public Integer getNumeroTitulo() {
		return numeroTitulo;
	}

	public void setNumeroTitulo(Integer numeroTitulo) {
		this.numeroTitulo = numeroTitulo;
	}

	public Integer getIdProveedorAnterior() {
		return idProveedorAnterior;
	}

	public void setIdProveedorAnterior(Integer idProveedorAnterior) {
		this.idProveedorAnterior = idProveedorAnterior;
	}

	public Integer getIdProveedorNuevo() {
		return idProveedorNuevo;
	}

	public void setIdProveedorNuevo(Integer idProveedorNuevo) {
		this.idProveedorNuevo = idProveedorNuevo;
	}

	public Integer getContratoAnterior() {
		return contratoAnterior;
	}

	public void setContratoAnterior(Integer contratoAnterior) {
		this.contratoAnterior = contratoAnterior;
	}

	public Integer getContratoNuevo() {
		return contratoNuevo;
	}

	public void setContratoNuevo(Integer contratoNuevo) {
		this.contratoNuevo = contratoNuevo;
	}

	public Integer getGrupoAnterior() {
		return grupoAnterior;
	}

	public void setGrupoAnterior(Integer grupoAnterior) {
		this.grupoAnterior = grupoAnterior;
	}

	public Integer getGrupoNuevo() {
		return grupoNuevo;
	}

	public void setGrupoNuevo(Integer grupoNuevo) {
		this.grupoNuevo = grupoNuevo;
	}
	
}
