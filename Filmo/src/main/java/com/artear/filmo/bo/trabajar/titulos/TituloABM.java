package com.artear.filmo.bo.trabajar.titulos;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class TituloABM {

	private String tipoTitulo;
	private String senial;
	private String clave;
	private String tituloOriginal;
	private String tituloCastellano;
	private String calificacionOficial;
	private String actor1;
	private String actor2;
	private String personaje1;
	private String personaje2;
	private List<Actor> actores;

	public String getSenial() {
		return senial;
	}

	public void setSenial(String senial) {
		this.senial = senial;
	}

	public String getTipoTitulo() {
		return tipoTitulo;
	}

	public void setTipoTitulo(String tipoTitulo) {
		this.tipoTitulo = tipoTitulo;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getTituloOriginal() {
		return tituloOriginal;
	}

	public void setTituloOriginal(String tituloOriginal) {
		this.tituloOriginal = tituloOriginal;
	}

	public String getTituloCastellano() {
		return tituloCastellano;
	}

	public void setTituloCastellano(String tituloCastellano) {
		this.tituloCastellano = tituloCastellano;
	}

	public String getCalificacionOficial() {
		return calificacionOficial;
	}

	public void setCalificacionOficial(String calificacionOficial) {
		this.calificacionOficial = calificacionOficial;
	}

	public String getActor1() {
		return actor1;
	}

	public void setActor1(String actor1) {
		this.actor1 = actor1;
	}

	public String getActor2() {
		return actor2;
	}

	public void setActor2(String actor2) {
		this.actor2 = actor2;
	}

	public String getPersonaje1() {
		return personaje1;
	}

	public void setPersonaje1(String personaje1) {
		this.personaje1 = personaje1;
	}

	public String getPersonaje2() {
		return personaje2;
	}

	public void setPersonaje2(String personaje2) {
		this.personaje2 = personaje2;
	}

	public List<Actor> getActores() {
		return actores;
	}

	public void setActores(List<Actor> actores) {
		this.actores = actores;
	}

	public List<Actor> getFixActores() {
		List<Actor> resultList = new ArrayList<Actor>();
		if (StringUtils.isNotBlank(actor1)) {
			resultList.add(new Actor(actor1, personaje1));
		}
		if (StringUtils.isNotBlank(actor2)) {
			resultList.add(new Actor(actor2, personaje2));
		}
		if (CollectionUtils.isNotEmpty(actores)) {
			int contActores = 0;
			int contPersonajes = 0;
			List<String> act = new ArrayList<String>();
			List<String> pers = new ArrayList<String>();
			for (Actor a : actores) {
				if (a.getActor() != null) {
					act.add(a.getActor());
					contActores++;
				}else if(a.getPersonaje() != null && contActores >= contPersonajes){
					pers.add(a.getPersonaje());
					contPersonajes++;
				}
			}
			for (int i = 0; i < contActores; i++) {
				if (StringUtils.isNotBlank(act.get(i))) {
					resultList.add(new Actor(act.get(i), pers.get(i)));
				}
			}
		}
		return resultList;
	}

	public String getActoresConcatenados(List<Actor> actores) {
		String result = "";
		for (int i = 0; i < actores.size(); i++) {
			Actor actor = actores.get(i);
			if (i == 0) {
				result += actor.getActor();
				continue;
			}
			result += ";" + actor.getActor();
		}
		return result;
	}

	public String getPersonajesConcatenados(List<Actor> actores) {
		String result = "";
		for (int i = 0; i < actores.size(); i++) {
			Actor actor = actores.get(i);
			if (i == 0) {
				result += StringUtils.isEmpty(actor.getPersonaje()) ? " "
						: actor.getPersonaje();
				continue;
			}
			result += ";"
					+ (StringUtils.isEmpty(actor.getPersonaje()) ? " " : actor
							.getPersonaje());
		}
		return result;
	}

}
