package com.artear.filmo.bo.trabajar.titulos;

import java.util.List;

public class FichaCinematograficaView {

	private String tituloCastellano;
	private String tituloOriginal;
	private String codigoCalificacion;
	private String descripcionCalificacion;
	private List<Actor> actores;

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
