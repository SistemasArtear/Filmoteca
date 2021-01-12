package com.artear.filmo.bo.trabajar.copias;

import org.apache.commons.lang.StringUtils;

import com.artear.filmo.constants.TipoBusquedaTitulo;

public class BuscarTitulosRequest {

	private String senial;
	private String clave;
	private TipoBusquedaTitulo tipoBusqueda;
	private String titulo;
	private String vigente;
	private String tieneCopia;

	public String getSenial() {
		return senial;
	}

	public void setSenial(String senial) {
		this.senial = senial;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public TipoBusquedaTitulo getTipoBusqueda() {
		return tipoBusqueda;
	}

	public void setTipoBusqueda(TipoBusquedaTitulo tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getVigente() {
		return vigente;
	}

	public void setVigente(String vigente) {
		this.vigente = vigente;
	}

	public String getTieneCopia() {
		return tieneCopia;
	}

	public void setTieneCopia(String tieneCopia) {
		this.tieneCopia = tieneCopia;
	}

	public String getClaveOrNull() {
		if (StringUtils.isBlank(clave)) {
			return null;
		}
		return clave;
	}
}
