package com.artear.filmo.seguridad.authentication;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;

import com.artear.filmo.seguridad.services.ldap.LDAPService;

public class LDAPPasswordCallbackHandler implements CallbackHandler{

	private LDAPService ldapService;
	
	public void handle(Callback[] arg0) throws IOException, UnsupportedCallbackException {

		final WSPasswordCallback pc = (WSPasswordCallback) arg0[0];

		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectclass", "person" )).and(new EqualsFilter("cn", ""+ pc.getIdentifier() ));

		if(! ldapService.authenticate(DistinguishedName.EMPTY_PATH, filter.toString(), ""+ pc.getPassword() ))
			throw new RuntimeException("Las credenciales no son correctas");
		
	}

	public void setLdapService(LDAPService ldapService) {
		this.ldapService = ldapService;
	}

}
