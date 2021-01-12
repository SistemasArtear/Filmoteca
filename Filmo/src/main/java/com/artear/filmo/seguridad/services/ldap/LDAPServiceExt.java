package com.artear.filmo.seguridad.services.ldap;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Name;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;

import com.artear.filmo.seguridad.exceptions.LDAPRuntimeException;
import com.artear.filmo.utils.StringUtils;



public class LDAPServiceExt {

	private Log logger = LogFactory.getLog(LDAPServiceExt.class);
	private LdapTemplate ldapTemplate;

	public LDAPResult search(LDAPQuery query, String atributoARetornar) throws Exception {

		String ldapQuery = query.queryString();

		if (logger.isDebugEnabled())
			logger.debug("QUERY LDAP : [" + ldapQuery + "]");

		List search = ldapTemplate.search("", ldapQuery,
				new ArtearAttributesMapper(atributoARetornar));
		return new LDAPResult(search);
	}

	public void setLdapTemplateExt(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	class ArtearAttributesMapper implements AttributesMapper {
		private String atributoARetornar;

		public ArtearAttributesMapper(String attrARetornar) {
			this.atributoARetornar = attrARetornar;
		}

		public Object mapFromAttributes(Attributes attrs)
		throws javax.naming.NamingException {
			try {
				if (StringUtils.isBlank(atributoARetornar))
					throw new LDAPRuntimeException(
							"El atributo a retornar no puede ser nulo ");

				Attribute attribute = attrs.get(atributoARetornar);

				if (attribute == null) {
					logger.error(" El usuario LDAP :'"
							+ attrs.get("cn").toString()
							+ "', no tiene cargado el atributo : '"
							+ atributoARetornar);
					logger.error("El atributo a retornar no existe en el resultado ["
							+ atributoARetornar + "]");
					return null;
				}

				List<Object> multivalor = new ArrayList<Object>(0);
				if (attribute != null && attribute.size() > 0) {
					for (int i = 0; i < attribute.size(); i++) {
						multivalor.add((String) attribute.get(i));
					}
					return multivalor;
				} else {
					return null;
				}

			} catch (Exception e) {
				logger.error("Error al intentar mapear atributos LDAP");
				logger.error(e.toString());
				return null;
			}
		}
	}

	public boolean authenticate(DistinguishedName emptyPath, String user,
			String pass) {
		return ldapTemplate.authenticate(emptyPath, user, pass);
	}

	public void update(Name dn,LDAPModificableAttribute modificableAttr) {
		List<String> newValuesList = modificableAttr.getNewValuesList();
		Attribute attr;

		if(newValuesList.isEmpty()) {
			attr = new BasicAttribute(modificableAttr.getName(), "");
		} else if (newValuesList.size() == 1) {
			attr = new BasicAttribute(modificableAttr.getName(), newValuesList.get(0));
		} else {
			attr = new BasicAttribute(modificableAttr.getName());
			for (String newValue : newValuesList) {
				attr.add(newValue);
			}
		}

		ModificationItem item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr);
		ldapTemplate.modifyAttributes(dn, new ModificationItem[] {item});
	}

	public void removeAttribute(Name dn, String attributeName) {	
		Attribute attr = new BasicAttribute(attributeName);
		ModificationItem item = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, attr);
		ldapTemplate.modifyAttributes(dn, new ModificationItem[] {item});
	}

}
