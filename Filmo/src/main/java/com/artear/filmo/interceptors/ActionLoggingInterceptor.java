package com.artear.filmo.interceptors;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.artear.filmo.constants.SessionConstants;
import com.artear.filmo.seguridad.userdetails.ExtUserDetails;
import com.artear.filmo.services.interfaces.BasicService;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class ActionLoggingInterceptor implements Interceptor {

	@Autowired
	private BasicService basicService;
	private static final long serialVersionUID = 1L;
	private static final String LOGGER_PATTERN = "Metodo:[%1$s] Parametros:(%2$s) [%3$s ms.]";
	private static final String FORMATO = " [%s]";
	private static final int MAX_CARACTERES = 100;	
		
	
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		Log logger = LogFactory.getLog(actionInvocation.getAction().getClass().getName());

		ExtUserDetails extUserDetails = (ExtUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
		if (extUserDetails.getEmpresa() == null || extUserDetails.getEmpresa().length() == 0) {
			if (extUserDetails.getAttributes().get(SessionConstants.EMPRESA) != null) {					
				String empresa = basicService.getEmpresaDescripcion(Short.valueOf(
						extUserDetails.getAttributes().get(SessionConstants.EMPRESA).toString()));
				extUserDetails.setEmpresa(empresa);
			}
		}	
		
		//Establezco usuario y empresa para el Logger (Trazas).
		MDC.put("u", String.format(FORMATO, extUserDetails.getUsername()));
		MDC.put("e", String.format(FORMATO, extUserDetails.getEmpresa()));

		final long initialTime = System.currentTimeMillis();				
		String result = actionInvocation.invoke();	
		final long finalTime = System.currentTimeMillis();
		
		String args = getArguments(actionInvocation);
		String metodo = ((ActionMapping)ServletActionContext.getActionMapping()).getName();
		logger.info(String.format(LOGGER_PATTERN, metodo, args, String.valueOf(finalTime - initialTime)));
		
		MDC.remove("u");
		MDC.remove("e");
		return result;
	}
	
	private String getArguments(ActionInvocation actionInvocation) {		
		StringBuilder result = new StringBuilder();
		Map<String, Object> actionParams = actionInvocation.getInvocationContext().getParameters();
		for (Iterator<String> it = actionParams.keySet().iterator(); it.hasNext();) {
			String param = (String) it.next();
			String[] values = (String[]) (actionParams.get(param));
			if (values.length > 0) {
				result.append(param);
				result.append("=");
				result.append(StringUtils.abbreviate(values[0], MAX_CARACTERES));
				if(it.hasNext())
					result.append(", ");						
			}	
		}
		return result.toString();
	}
	
	@Override
	public void destroy() {
	}

	@Override
	public void init() {
	}

}