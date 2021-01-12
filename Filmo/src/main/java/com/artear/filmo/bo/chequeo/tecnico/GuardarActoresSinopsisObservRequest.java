package com.artear.filmo.bo.chequeo.tecnico;

import java.util.List;

import org.apache.commons.lang.StringUtils;

public class GuardarActoresSinopsisObservRequest {

	private String clave;
	private Integer nroFicha;
	private Integer capitulo;
	private Integer parte;
	private List<Actor> actores;
	private List<String> observaciones;
	private List<String> sinopsis;

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Integer getCapitulo() {
		return capitulo;
	}

	public void setCapitulo(Integer capitulo) {
		this.capitulo = capitulo;
	}

	public Integer getParte() {
		return parte;
	}

	public void setParte(Integer parte) {
		this.parte = parte;
	}

	public List<Actor> getActores() {
		return actores;
	}

	public void setActores(List<Actor> actores) {
		this.actores = actores;
	}

	public List<String> getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(List<String> observaciones) {
		this.observaciones = observaciones;
	}

	public List<String> getSinopsis() {
		return sinopsis;
	}

	public void setSinopsis(List<String> sinopsis) {
		this.sinopsis = sinopsis;
	}

	public Integer getNroFicha() {
		return nroFicha;
	}

	public void setNroFicha(Integer nroFicha) {
		this.nroFicha = nroFicha;
	}

	public String getActoresJoined() {
		String resultado = "";
		for (Actor actor : actores) {
			if (StringUtils.isNotBlank(actor.getActor())) {
				resultado += actor.getActor() + ",";
			}
		}
		return StringUtils.isBlank(resultado) ? " " : resultado.substring(0,
				resultado.length() - 1);
	}

	public String getPersonajesJoined() {
		String resultado = "";
		for (Actor actor : actores) {
			if (StringUtils.isNotBlank(actor.getActor())) {
				resultado += actor.getPersonaje() + ",";
			}
		}
		return StringUtils.isBlank(resultado) ? " " : resultado.substring(0,
				resultado.length() - 1);
	}

	public String getSinopsisJoined() {
		String resultado = "";
		for (String s : sinopsis) {
			if (StringUtils.isNotBlank(s)) {
				resultado += s + "|";
			}
		}
		return StringUtils.isBlank(resultado) ? " " : resultado.substring(0,
				resultado.length() - 1);
	}

	public String getObservacionesJoined() {
		String resultado = "";
		for (String o : observaciones) {
			if (StringUtils.isNotBlank(o)) {
				resultado += o + "|";
			}
		}
		return StringUtils.isBlank(resultado) ? " " : resultado.substring(0,
				resultado.length() - 1);
	}

}
