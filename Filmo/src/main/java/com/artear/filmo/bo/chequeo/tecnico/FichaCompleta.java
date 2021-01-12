package com.artear.filmo.bo.chequeo.tecnico;

import java.util.List;

import com.artear.filmo.bo.common.Senial;

public class FichaCompleta {

	private Ficha ficha;
	private List<Senial> seniales;
	private List<Actor> actores;
	private List<Segmento> segmentos;
	private List<Observacion> observaciones;
	private List<Sinopsis> sinopsis;
	private List<SenialOkFilm> senialesOkFilm;

	public Ficha getFicha() {
		return ficha;
	}

	public void setFicha(Ficha ficha) {
		this.ficha = ficha;
	}

	public List<Senial> getSeniales() {
		return seniales;
	}

	public void setSeniales(List<Senial> seniales) {
		this.seniales = seniales;
	}

	public List<Actor> getActores() {
		return actores;
	}

	public void setActores(List<Actor> actores) {
		this.actores = actores;
	}

	public List<Segmento> getSegmentos() {
		return segmentos;
	}

	public void setSegmentos(List<Segmento> segmentos) {
		this.segmentos = segmentos;
	}

	public List<Observacion> getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(List<Observacion> observaciones) {
		this.observaciones = observaciones;
	}

	public List<Sinopsis> getSinopsis() {
		return sinopsis;
	}

	public void setSinopsis(List<Sinopsis> sinopsis) {
		this.sinopsis = sinopsis;
	}

	public List<SenialOkFilm> getSenialesOkFilm() {
		return senialesOkFilm;
	}

	public void setSenialesOkFilm(List<SenialOkFilm> senialesOkFilm) {
		this.senialesOkFilm = senialesOkFilm;
	}

}
