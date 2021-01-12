package com.artear.filmo.seguridad.services.ldap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LDAPModificableAttribute implements Serializable{
	
	private static final long serialVersionUID = 1L;

	
	private String name;
	private List<String> newValuesList;
	
	
	/**
	 * Modificar un atributo de LDAP con valor  nico
	 */	
	public LDAPModificableAttribute(String name, String newValue) {
		super();
		this.name = name;
		List<String> newVals = new ArrayList<String>(0);
		newVals.add(newValue);
		this.newValuesList = newVals;
	}

	/**
	 * Constructor para modificar un atributo de LDAP con m ltiples valores
	 */
	public LDAPModificableAttribute(String name, List<String> newValuesList ) {
		super();
		this.name = name;
		this.newValuesList = newValuesList;
	}
	
	/**
	 * Nombre del atributo a modificar  
	 */
	public String getName() {
		return name;
	}
	public void setName(String _name) {
		this.name = _name;
	}
	
	/**
	 * Nuevo/s valor/es a asignar	 
	 */
	public List<String> getNewValuesList() {
		return newValuesList;
	}

	public void setNewValuesList(List<String> newValuesList) {
		this.newValuesList = newValuesList;
	}
	
}

