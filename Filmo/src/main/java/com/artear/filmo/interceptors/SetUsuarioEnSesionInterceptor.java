package com.artear.filmo.interceptors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.artear.filmo.services.interfaces.BasicService;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**Se deja de utilizar esta clase como interceptor ya que segenero un aspecto para el mismo funcionamiento
 * en la capa DAL
 **/
@Deprecated()
@Component
public class SetUsuarioEnSesionInterceptor implements Interceptor {

	private static final long serialVersionUID = 1L;

	private static final Log logger = LogFactory.getLog(SetUsuarioEnSesionInterceptor.class);

	@Autowired
	private BasicService basicService;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		logger.debug("Se esta seteando el usuario logueado de sesion en Oracle.");
		basicService.setUsuarioEnSession();
		return invocation.invoke();
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init() {
	}

}