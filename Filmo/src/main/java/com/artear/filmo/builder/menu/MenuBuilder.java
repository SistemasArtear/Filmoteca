package com.artear.filmo.builder.menu;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.artear.filmo.bo.menu.Menu;
import com.artear.filmo.bo.menu.SubMenu;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Component
public class MenuBuilder {
	
	
	private @Value("menu.xml") String urlMenuXML;
	
	/**
	 * En base a los roles que tenga el usuario el metodo devolverá las opciones
	 * del menú que le correspondan.
	 * @param roles
	 * @return
	 */
	public Menu obtenerMenuPorRol(List<String> roles) {
		Menu menuResult = new Menu();
		Menu menuCompleto = obtenerMenuCompleto();
		List<SubMenu> subMenuResult = obtenerSubMenuPorRol(menuCompleto.getSubMenu(), roles);
		menuResult.setSubMenu(subMenuResult);
		return menuResult;
	}
	
	
	/*-- METODOS PRIVADOS --*/
	/**
	 * Devuelve una lista de SubMenues que utilizara el rol correspondiente
	 * @return
	 */
	private List<SubMenu> obtenerSubMenuPorRol(List<SubMenu> subMenuList, List<String> roles) {
		List<SubMenu> subMenuResult = new ArrayList<SubMenu>();
		List<SubMenu> subMenuAuxList = new ArrayList<SubMenu>();
		SubMenu subMenuAux = null;
		//recorro el nivel 1 de opciones
		for (SubMenu subMenu : subMenuList) {			
			if (esVisible(subMenu.getRoles(), roles)) {
				//si tiene submenues hago la recursiva
				if (subMenu.getSubMenu() != null && subMenu.getSubMenu().size() > 0) {
					subMenuAuxList = obtenerSubMenuPorRol(subMenu.getSubMenu(), roles);
					subMenuAux = copiarSubMenuConSubMenu(subMenu, subMenuAuxList);
				} else {
					subMenuAux = copiarSubMenuSinSubMenu(subMenu);
				}	
				subMenuResult.add(subMenuAux);
			}	
		}
		return subMenuResult;
	}
	
	/**
	 * Devuelve el menu completo que lee desde el XML
	 * @return
	 */
	private Menu obtenerMenuCompleto() {
		Menu menu = null;
		XStream xstream = new XStream(new DomDriver("UTF-8"));
		InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(urlMenuXML);
		menu = (Menu) xstream.fromXML(resourceAsStream);
		return menu;
	}

	/**
	 * pregunta si la opcion es visible para dicho rol
	 * @param rolesMenu
	 * @param roles
	 * @return
	 */
	private boolean esVisible(List<String> rolesMenu, List<String> roles) {
		for (String miRol : roles) {
			for (String rol : rolesMenu) {
				if (StringUtils.equals(miRol, rol))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Copia los datos de los links que se encuentran en la punta de las ramas 
	 * del arbol del menu
	 * @param subMenu
	 * @return
	 */
	private SubMenu copiarSubMenuSinSubMenu(SubMenu subMenu) {
		SubMenu result = new SubMenu();
		result.setKey(subMenu.getKey());
		result.setTitle(subMenu.getTitle());
		result.setRoles(subMenu.getRoles());
		result.setAction(subMenu.getAction());
		return result;
	}

	/**
	 * Copia los datos de los links y a sus hijos
	 * @param subMenu
	 * @param subMenuList
	 * @return
	 */
	private SubMenu copiarSubMenuConSubMenu(SubMenu subMenu, List<SubMenu> subMenuList) {
		SubMenu result = new SubMenu();
		result = copiarSubMenuSinSubMenu(subMenu);
		result.setSubMenu(subMenuList);
		return result;
	}
	
	public String getNombreArchivoXML() {
		return urlMenuXML;
	}

	public void setNombreArchivoXML(String nombreArchivoXML) {
		this.urlMenuXML = nombreArchivoXML;
	}
}
