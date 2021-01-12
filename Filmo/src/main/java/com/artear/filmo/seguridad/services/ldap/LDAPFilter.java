package com.artear.filmo.seguridad.services.ldap;

import java.util.HashMap;
import java.util.Map;

import com.artear.filmo.constants.LDAPConstants;


public class LDAPFilter {

	
	
	private Map<String, String> filtro=new HashMap<String, String>();
	
	public LDAPFilter(String key, String value, String condicion) {
		filtro.put(LDAPConstants.CONDICION, condicion);
		filtro.put(LDAPConstants.KEY,key);
		filtro.put(LDAPConstants.VALUE,value);
	}
	public Map<String, String> getFiltro() {
		return filtro;
	}
	public void setFiltro(Map<String, String> filtro) {
		this.filtro = filtro;
	}
	
	public String getCondicion(){
		return filtro.get(LDAPConstants.CONDICION);
	}
	public String getkey(){
		return filtro.get(LDAPConstants.KEY);
	}
	public String getValue(){
		return filtro.get(LDAPConstants.VALUE);
	}
	
}
