package com.artear.filmo.bo.trabajar.titulos;

import java.util.List;

public class TituloView {
	
	private String codigoTipo;
	private String descripcionTipo;
	private String clave;
	private String tituloCastellano;
	private String tituloOriginal;
	private String codigoCalificacion;
	private String descripcionCalificacion;
	private List<Actor> actores;
	public String getCodigoTipo() {
		return codigoTipo;
	}
	public void setCodigoTipo(String codigoTipo) {
		this.codigoTipo = codigoTipo;
	}
	public String getDescripcionTipo() {
		return descripcionTipo;
	}
	public void setDescripcionTipo(String descripcionTipo) {
		this.descripcionTipo = descripcionTipo;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getTituloCastellano() {
		return tituloCastellano;
	}
	public void setTituloCastellano(String tituloCastellano) {
		this.tituloCastellano = tituloCastellano;
	}
	public String getTituloOriginal() {
		return tituloOriginal;
	}
	public void setTituloOriginal(String tituloOriginal) {
		this.tituloOriginal = tituloOriginal;
	}
	public String getCodigoCalificacion() {
		return codigoCalificacion;
	}
	public void setCodigoCalificacion(String codigoCalificacion) {
		this.codigoCalificacion = codigoCalificacion;
	}
	public String getDescripcionCalificacion() {
		return descripcionCalificacion;
	}
	public void setDescripcionCalificacion(String descripcionCalificacion) {
		this.descripcionCalificacion = descripcionCalificacion;
	}
	public List<Actor> getActores() {
		return actores;
	}
	public void setActores(List<Actor> actores) {
		this.actores = actores;
	}
	
}
