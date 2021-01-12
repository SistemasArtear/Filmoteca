package com.artear.filmo.seguridad.services.ldap;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.artear.filmo.constants.LDAPConstants;
import com.artear.filmo.utils.StringUtils;

public class LDAPQuery {
	
	private List<LDAPFilter> filters = new ArrayList<LDAPFilter>();
	
	public void agregarFiltro(String key,String value, String condicion){
		filters.add(new LDAPFilter(key, value, condicion));
	}

	public List<LDAPFilter> getKeys(){
		return this.filters;
	}

	public String queryString() throws Exception{
		StringBuilder str = new StringBuilder();
		
		if(filters.size() > 0){
			for (Iterator<LDAPFilter> iterator = filters.iterator(); iterator.hasNext();) {
				LDAPFilter _filtro = iterator.next();
				str.append("(");
				str.append(getQuery(_filtro.getkey(), _filtro.getValue(), _filtro.getCondicion()));
				str.append(")");
			}
		}
		
		return getKeys().size()>1? "(&"+str.toString()+")" : str.toString();
	}
	
	private String getQuery(String key, String value, String condicion) throws UnsupportedEncodingException{
		String result = ""; 
		result = StringUtils.replace("%key%%condicion%%value%", LDAPConstants.KEY_PATTERN, key);
		result = StringUtils.replace(result, LDAPConstants.VALUE_PATTERN, value);
		result = StringUtils.replace(result, LDAPConstants.CONDICION_PATTERN, condicion);
		return result;
	}

	public void agregarFiltro(LDAPFilter ldapFilter) {
		filters.add(ldapFilter);
	}

	public static LDAPQuery getInstance() {
		return new LDAPQuery();
	}
}
