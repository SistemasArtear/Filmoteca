package com.artear.filmo.bo.ficha.cinematografica;

import java.util.List;

import org.apache.commons.lang.StringUtils;

public class FichaCompleta {

	private FichaBasicaResponse ficha;
	private List<ActorResponse> actores;
	private List<SinopsisResponse> sinopsis;

	public FichaCompleta() {
		super();
	}

	public FichaCompleta(FichaBasicaResponse ficha,
			List<ActorResponse> actores, List<SinopsisResponse> sinopsis) {
		super();
		this.ficha = ficha;
		this.actores = actores;
		this.sinopsis = sinopsis;
	}

	public FichaBasicaResponse getFicha() {
		return ficha;
	}

	public void setFicha(FichaBasicaResponse ficha) {
		this.ficha = ficha;
	}

	public List<ActorResponse> getActores() {
		return actores;
	}

	public void setActores(List<ActorResponse> actores) {
		this.actores = actores;
	}

	public List<SinopsisResponse> getSinopsis() {
		return sinopsis;
	}

	public void setSinopsis(List<SinopsisResponse> sinopsis) {
		this.sinopsis = sinopsis;
	}
	
	public String getActoresJoined(){
		StringBuilder sb = new StringBuilder();
		for (ActorResponse actor : actores) {
			if (StringUtils.isNotBlank(actor.getActor())) {
				sb.append(actor.getActor());
				sb.append(";");
			}
		}
		return sb.toString();
	}

	public String getPersonajesJoined(){
		StringBuilder sb = new StringBuilder();
		for (ActorResponse actor : actores) {
			if (StringUtils.isNotBlank(actor.getActor())) {
				if (StringUtils.isNotBlank(actor.getPersonaje())) {
					sb.append(actor.getPersonaje());
				}
				sb.append(";");
			}
		}
		return sb.toString();
	}

	public String getSinopsisJoined(){
		StringBuilder sb = new StringBuilder();
		for (SinopsisResponse s : sinopsis) {
			if (StringUtils.isNotBlank(s.getDetalle())) {
				sb.append(s.getDetalle());
				sb.append(";");
			}
		}
		return sb.toString();
	}

}
