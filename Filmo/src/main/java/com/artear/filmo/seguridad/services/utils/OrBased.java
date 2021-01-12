package com.artear.filmo.seguridad.services.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.core.Authentication;

/**
 * 
 * Toma la decision del control final de acceso (autorizacion).
 *
 */
public class OrBased extends AbstractAccessDecisionManager {
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) throws AccessDeniedException {

		List<ConfigAttribute> singleAttributeList = new ArrayList<ConfigAttribute>(1);
		singleAttributeList.add(null);

		for (ConfigAttribute attribute : attributes) {
			singleAttributeList.set(0, attribute);

			for (AccessDecisionVoter voter : getDecisionVoters()) {
				int result = voter.vote(authentication, object, singleAttributeList);

				if (logger.isDebugEnabled()) {
					logger.debug("Voter: " + voter + ", returned: " + result);
				}

				if (result == AccessDecisionVoter.ACCESS_GRANTED) {
					return;
				}

			}
		}

		throw new AccessDeniedException(messages.getMessage("AbstractAccessDecisionManager.accessDenied", "Access is denied"));
	}
}
