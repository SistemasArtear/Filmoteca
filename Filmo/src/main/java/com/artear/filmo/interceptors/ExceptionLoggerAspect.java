package com.artear.filmo.interceptors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.artear.filmo.exceptions.DataBaseException;

@Aspect
public class ExceptionLoggerAspect {

	private static final Log LOGGER = LogFactory.getLog(ExceptionLoggerAspect.class);

	@Pointcut(value="execution(* com.artear.filmo.services.interfaces.*.*(..))")
	public void anyServiceMethod() {
	}
	
	@Around("anyServiceMethod()")
	public Object logException(ProceedingJoinPoint pjp) throws Throwable {
		try {
			return pjp.proceed();
		} catch (DataBaseException e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		}
	}

}