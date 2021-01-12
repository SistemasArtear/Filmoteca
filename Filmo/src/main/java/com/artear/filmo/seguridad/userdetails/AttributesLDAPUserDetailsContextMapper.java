package com.artear.filmo.seguridad.userdetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;

public class AttributesLDAPUserDetailsContextMapper extends LdapUserDetailsMapper {

	/**
	 * Vector con los atributos a recuperar del LDap
	 */
	private String[] attributesToPopulate = new String[] {};

	/**
	 * Contexto utilizado por Spring security
	 */
	private DefaultSpringSecurityContextSource springSecurityContextSource;

	/**
	 * Metodo para setear el contexto de spring
	 * 
	 * @param springSecurityContextSource
	 *            contexto de spring
	 */
	public void setSpringSecurityContextSource(DefaultSpringSecurityContextSource springSecurityContextSource) {
		this.springSecurityContextSource = springSecurityContextSource;
	}

	/**
	 * DN del usuario logueado
	 */
	private String dnUser = null;

	/**
	 * Arbol del Ldap al que pertenece el usuario
	 */
	private String ldapTree = null;

	public void setLdapTree(String ldapTree) {
		this.ldapTree = ldapTree;
	}

	/**
	 * Conexion al arbol de Ldap
	 */
	private LdapTemplate ldapTemplate;

	/**
	 * Metodo get de attributesToPopulate
	 * 
	 * @return String[] Vector con los atributos a recuperar del LDap
	 */
	public String[] getAttributesToPopulate() {
		return attributesToPopulate;
	}

	/**
	 * Metodo set de attributesToPopulate
	 * 
	 * @param attributesToPopulate
	 *            Vector con los atributos a recuperar del LDap
	 */
	public void setAttributesToPopulate(String[] attributesToPopulate) {
		this.attributesToPopulate = attributesToPopulate;
	}

	/**
	 * Mapeo del usuario logueado
	 */
	@Override
	public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<GrantedAuthority> authorities) {

		Log logger = LogFactory.getLog(AttributesLDAPUserDetailsContextMapper.class);
		// Logger logger = LoggerFactory
		// .getLogger(AttributesLDAPUserDetailsContextMapper.class);

		logger.info("ok " + AttributesLDAPUserDetailsContextMapper.class.toString());
		dnUser = ctx.getNameInNamespace();
		ExtUserDetails.Essence essence = new ExtUserDetails.Essence();
		essence.setDn(dnUser);
		essence.setAuthorities((List<GrantedAuthority>) authorities);
		essence.addAttribute("tipodeusuario", ldapTree);

		LdapContextSource contextSource = (LdapContextSource) springSecurityContextSource;

		ldapTemplate = new LdapTemplate(contextSource);

		essence.setUsername(username);

		DirContextAdapter dca = (DirContextAdapter) ctx;

		// Recorre la lista de los atributos y obtiene los valores guardandolos
		// en un mapa
		for (int i = 0; i < attributesToPopulate.length; i++) {

			if (attributesToPopulate[i].equals("roles")) {
				if (dca.getStringAttributes("roles") != null) {
					List<String> rolesList = Arrays.asList(dca.getStringAttributes("roles"));
					essence.addAttribute(attributesToPopulate[i], rolesList);
				}
			} else if (attributesToPopulate[i].equals("generador")) {
				if (dca.getStringAttributes("generador") != null) {
					List<String> generadoresList = Arrays.asList(dca.getStringAttributes("generador"));
					essence.addAttribute(attributesToPopulate[i], generadoresList);
				}
			} else if (attributesToPopulate[i].equals("grupojerarquico")) {
				essence.addAttribute(attributesToPopulate[i], getAttribute(attributesToPopulate[i]));
			} else if (attributesToPopulate[i].equals("gruposfuncionales")) {
				essence.addAttribute(attributesToPopulate[i], getAttribute(attributesToPopulate[i]));

			} else if (attributesToPopulate[i].equals("codigocliente")) {
				essence.addAttribute(attributesToPopulate[i], getAttribute(attributesToPopulate[i]));

			} else if (attributesToPopulate[i].equals("senal")) {
				if (dca.getStringAttributes("senal") != null) {
					List<String> senalList = Arrays.asList(dca.getStringAttributes("senal"));
					essence.addAttribute(attributesToPopulate[i], senalList);
				}
			} else {
				String attribute = dca.getStringAttribute(attributesToPopulate[i]);
				essence.addAttribute(attributesToPopulate[i], attribute);
			}

		}

		return essence.createUserDetails();

	}

	/**
	 * Obtiene los valores de atributos complejos
	 * 
	 * @param attributeName
	 *            nombre del atributo a obtener
	 * @return List<String> Valoer del atributo obtenido
	 */
	@SuppressWarnings("unchecked")
	public List<String> getAttribute(String attributeName) {
		List<String> list = null;
		ContextMapper mapper = new LdapContextMapper();
		String filter = null;
		if (attributeName.equals("grupojerarquico")) {
			filter = "(&(uniqueMember=" + dnUser + ")&((objectClass=groupOfNames)(o=Jerarquico)))";

		} else if (attributeName.equals("gruposfuncionales") || attributeName.equals("codigocliente")) {
			filter = "(&(uniqueMember=" + dnUser + ")(objectClass=groupOfNames))";
		}

		if (filter != null) {
			List<DirContextAdapter> resultQuery = (List<DirContextAdapter>) ldapTemplate.search("", filter, mapper);
			if (resultQuery != null && !resultQuery.isEmpty()) {
				list = new ArrayList<String>();
				for (DirContextAdapter resultQueryObject : resultQuery) {
					if (attributeName.equals("codigocliente")) {
						if (resultQueryObject.getStringAttribute("codigocliente") != null) {
							list.add(resultQueryObject.getStringAttribute("codigocliente"));
						}
					} else {
						list.add(resultQueryObject.getNameInNamespace());
					}
				}

			}
		}
		return list;
	}

}
