package com.artear.filmo.bo.chequeo.tecnico;

import java.util.List;

public class GuardarSegmentoRequest {

	private Integer duracionArtistica;
	private Integer cantidadSegmentos;
	private Integer nroFicha;
	private List<Segmento> segmentos;

	public Integer getDuracionArtistica() {
		return duracionArtistica;
	}

	public void setDuracionArtistica(Integer duracionArtistica) {
		this.duracionArtistica = duracionArtistica;
	}

	public Integer getCantidadSegmentos() {
		return cantidadSegmentos;
	}

	public void setCantidadSegmentos(Integer cantidadSegmentos) {
		this.cantidadSegmentos = cantidadSegmentos;
	}

	public List<Segmento> getSegmentos() {
		return segmentos;
	}

	public void setSegmentos(List<Segmento> segmentos) {
		this.segmentos = segmentos;
	}

	public Integer getNroFicha() {
		return nroFicha;
	}

	public void setNroFicha(Integer nroFicha) {
		this.nroFicha = nroFicha;
	}

}
