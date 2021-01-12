package com.artear.filmo.bo.programacion.modificarRatingExcedente;

import java.util.Date;

import com.artear.filmo.bo.programacion.Programa;

public class BusquedaTitulosRatingExcedenteRequest {
	private String tipoNroTitulo;
	private Date fechaExhibicion;
	private Programa programa;
	private String idSenial;
	
	public String getTipoNroTitulo() {
		return tipoNroTitulo;
	}
	public void setTipoNroTitulo(String tipoNroTitulo) {
		this.tipoNroTitulo = tipoNroTitulo;
	}
	public Date getFechaExhibicion() {
		return fechaExhibicion;
	}
	public void setFechaExhibicion(Date fechaExhibicion) {
		this.fechaExhibicion = fechaExhibicion;
	}
	public Programa getPrograma() {
		return programa;
	}
	public void setPrograma(Programa programa) {
		this.programa = programa;
	}
	public String getidSenial() {
		return idSenial;
	}
	public void setidSenial(String idSenial) {
		this.idSenial = idSenial;
	}
	
	
}
