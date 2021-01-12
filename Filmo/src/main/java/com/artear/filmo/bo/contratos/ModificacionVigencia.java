package com.artear.filmo.bo.contratos;

import java.util.Date;

public class ModificacionVigencia {

	private String tipoVigencia;
	private String descripcionVigencia;
	private Date fechaDesdePayTv;
	private Date fechaHastaPayTv;
	private Date fechaDesdeAnterior;
	private Date fechaHastaAnterior;
	private Date fechaDesdeNueva;
	private Date fechaHastaNueva;

	public String getTipoVigencia() {
		return tipoVigencia;
	}

	public void setTipoVigencia(String tipoVigencia) {
		this.tipoVigencia = tipoVigencia;
	}

	public String getDescripcionVigencia() {
		return descripcionVigencia;
	}

	public void setDescripcionVigencia(String descripcionVigencia) {
		this.descripcionVigencia = descripcionVigencia;
	}

	public Date getFechaDesdePayTv() {
		return fechaDesdePayTv;
	}

	public void setFechaDesdePayTv(Date fechaDesdePayTv) {
		this.fechaDesdePayTv = fechaDesdePayTv;
	}

	public Date getFechaHastaPayTv() {
		return fechaHastaPayTv;
	}

	public void setFechaHastaPayTv(Date fechaHastaPayTv) {
		this.fechaHastaPayTv = fechaHastaPayTv;
	}

	public Date getFechaDesdeAnterior() {
		return fechaDesdeAnterior;
	}

	public void setFechaDesdeAnterior(Date fechaDesdeAnterior) {
		this.fechaDesdeAnterior = fechaDesdeAnterior;
	}

	public Date getFechaHastaAnterior() {
		return fechaHastaAnterior;
	}

	public void setFechaHastaAnterior(Date fechaHastaAnterior) {
		this.fechaHastaAnterior = fechaHastaAnterior;
	}

	public Date getFechaDesdeNueva() {
		return fechaDesdeNueva;
	}

	public void setFechaDesdeNueva(Date fechaDesdeNueva) {
		this.fechaDesdeNueva = fechaDesdeNueva;
	}

	public Date getFechaHastaNueva() {
		return fechaHastaNueva;
	}

	public void setFechaHastaNueva(Date fechaHastaNueva) {
		this.fechaHastaNueva = fechaHastaNueva;
	}
	
}