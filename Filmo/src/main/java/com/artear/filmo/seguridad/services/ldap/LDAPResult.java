package com.artear.filmo.seguridad.services.ldap;

public class LDAPResult {
	
	private Object result;
	
	public LDAPResult(Object object) {
		result = object;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
}
