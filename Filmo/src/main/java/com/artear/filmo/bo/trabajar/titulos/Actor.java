package com.artear.filmo.bo.trabajar.titulos;

public class Actor {

	private String actor;
	private String personaje;

	public Actor(String actor, String personaje) {
		this.actor = actor;
		this.personaje = personaje;
	}

	public Actor() {
		super();
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getPersonaje() {
		return personaje;
	}

	public void setPersonaje(String personaje) {
		this.personaje = personaje;
	}

}
