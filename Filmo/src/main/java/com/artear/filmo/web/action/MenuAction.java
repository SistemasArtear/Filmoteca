package com.artear.filmo.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artear.filmo.bo.menu.Menu;
import com.artear.filmo.builder.menu.MenuBuilder;
import com.artear.filmo.seguridad.services.ServiciosSesionUsuario;
import com.opensymphony.xwork2.ActionSupport;

@Service
public class MenuAction extends ActionSupport {

	private static final long serialVersionUID = -9157140142952429831L;
	private Menu menu;

	@Autowired
	private MenuBuilder menuBuilder;
	@Autowired
	private ServiciosSesionUsuario serviciosSesionUsuario;

	/**
	 * Este metodo de prueba trae en una lista todos los eventos
	 * 
	 * @return
	 */
	public String returnMenu() {
		List<String> roles = serviciosSesionUsuario.getRoles();
		menu = menuBuilder.obtenerMenuPorRol(roles);
		return SUCCESS;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}
