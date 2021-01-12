package com.artear.filmo.interceptors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

import com.artear.filmo.services.interfaces.BasicService;

@Aspect
public class SetUserIdentifierAspect {

	@Autowired
	private BasicService basicService;
	
	@Before(value = "execution(* com.artear.filmo.daos.interfaces.*.*(..)) " +
				"&& !execution(* com.artear.filmo.daos.interfaces.BasicDaoPL.setUsuarioEnSession(..)))")
	public void setUserIdentifier(JoinPoint point) {
		basicService.setUsuarioEnSession();
	}
}