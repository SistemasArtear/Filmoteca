package com.artear.filmo.interceptors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class ServiceLoggingAspect {

	private static final String LOGGER_PATTERN = "Metodo:[%1$s] Parametros:(%2$s)";

	@After("execution(* com.artear.filmo.services.interfaces.*.*(..))")
	public void logServiceInvocation(JoinPoint point) {
		Log logger = LogFactory.getLog(point.getTarget().getClass());
		if (logger.isDebugEnabled()) {
			logger.debug(String.format(LOGGER_PATTERN, point.getSignature().getName(), 
					StringUtils.join(point.getArgs(), ", ")));
		}	
	}
}