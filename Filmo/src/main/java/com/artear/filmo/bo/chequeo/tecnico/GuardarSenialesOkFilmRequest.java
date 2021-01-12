package com.artear.filmo.bo.chequeo.tecnico;

import java.util.List;

public class GuardarSenialesOkFilmRequest {

	private Integer nroFicha;
	private List<SenialOkFilm> seniales;

	public Integer getNroFicha() {
		return nroFicha;
	}

	public void setNroFicha(Integer nroFicha) {
		this.nroFicha = nroFicha;
	}

	public List<SenialOkFilm> getSeniales() {
		return seniales;
	}

	public void setSeniales(List<SenialOkFilm> seniales) {
		this.seniales = seniales;
	}

}
