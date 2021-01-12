package com.artear.filmo.seguridad.services.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

import com.artear.filmo.seguridad.userdetails.ExtUserDetails;


/**
 * 
 * Responsable de "votar" en decisiones de autorizacion
 * 
 * En el metodo vote se verifican todas las opciones y determina si otorga o no autorizacion. 
 * La autorizacion final la otorga el AbstractAccessDecisionManager (ver OrBased)
 *
 */
public class RoleVoter implements AccessDecisionVoter {

	//~ Instance fields ================================================================================================

    private String rolePrefix = "ROLE_";

    //~ Methods ========================================================================================================

    public String getRolePrefix() {
        return rolePrefix;
    }

    /**
     * Allows the default role prefix of <code>ROLE_</code> to be overridden.
     * May be set to an empty value, although this is usually not desirable.
     *
     * @param rolePrefix the new prefix
     */
    public void setRolePrefix(String rolePrefix) {
        this.rolePrefix = rolePrefix;
    }

    public boolean supports(ConfigAttribute attribute) {
        if ((attribute.getAttribute() != null) && attribute.getAttribute().startsWith(getRolePrefix())) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * This implementation supports any type of class, because it does not query
     * the presented secure object.
     *
     * @param clazz the secure object
     *
     * @return always <code>true</code>
     */
    public boolean supports(Class<?> clazz) {
        return true;
    }

    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        int result = ACCESS_ABSTAIN;
        List<String> roles = extractRoles(authentication);

        for (ConfigAttribute attribute : attributes) {
            if (this.supports(attribute)) {
                result = ACCESS_DENIED;

                // Attempt to find a matching granted authority
                for (String rol : roles) {
                    if (attribute.getAttribute().equals(this.rolePrefix + rol)) {
                        return ACCESS_GRANTED;
                    }
                }
            }
        }

        return result;
    }

	@SuppressWarnings("unchecked")
	List<String> extractRoles(Authentication authentication) {
		List<String> roles = new ArrayList<String>();
    	Object principal = authentication.getPrincipal();
    	if (principal instanceof String) {
    		
    	} else {
    		ExtUserDetails extUserDetails = (ExtUserDetails)principal;
    		roles = (List<String>)extUserDetails.getAttributes().get("roles");
    	}
        return roles;
    }
    
}

