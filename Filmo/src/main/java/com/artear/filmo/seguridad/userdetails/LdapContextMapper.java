package com.artear.filmo.seguridad.userdetails;

import org.springframework.ldap.core.ContextMapper;

public class LdapContextMapper implements ContextMapper {

	/**
	 * Objecto a retornar
	 */
	Object object;
	
	public Object mapFromContext(Object arg0) {
		object = arg0;
		return object;
	}

}