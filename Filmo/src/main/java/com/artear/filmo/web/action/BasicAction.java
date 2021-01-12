package com.artear.filmo.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.artear.filmo.bo.common.Senial;
import com.artear.filmo.seguridad.userdetails.ExtUserDetails;
import com.artear.filmo.services.interfaces.BasicService;
import com.opensymphony.xwork2.ActionSupport;

@Service
public class BasicAction extends ActionSupport {

	private static final long serialVersionUID = -9157140142952429831L;
	
	private List<Senial> seniales;
	private Map<String, String> datosSesionUsuario;
	private String usuarioEmpresaLogueo;
	
	@Autowired
	private BasicService basicService;

	public List<Senial> getSeniales() {
		return seniales;
	}
	
	public void setSeniales(List<Senial> seniales) {
		this.seniales = seniales;
	}
	
	public Map<String, String> getDatosSesionUsuario() {
		return datosSesionUsuario;
	}

	public void setDatosSesionUsuario(Map<String, String> datosSesionUsuario) {
		this.datosSesionUsuario = datosSesionUsuario;
	}
	
	public String getUsuarioEmpresaLogueo() {
		return usuarioEmpresaLogueo;
	}

	public void setUsuarioEmpresaLogueo(String usuarioEmpresaLogueo) {
		this.usuarioEmpresaLogueo = usuarioEmpresaLogueo;
	}

	public String retrieveSeniales() {
		this.seniales = basicService.getSeniales();
		//MOCK
		if (CollectionUtils.isEmpty(seniales)) {
			seniales = new ArrayList<Senial>();
			seniales.add(new Senial("C13", "CANAL 13"));
			seniales.add(new Senial("CAB", "CABLIN"));
		}
		return SUCCESS;
	}
	
	public String retrieveEncabezado() {
		this.usuarioEmpresaLogueo = basicService.getUsuarioEmpresaLogeo();
		return SUCCESS;
	}

	/**
	 * metodo que arma en formato html el listado de todas las variables que se obtienen de LDAP
	 * cuando un usuario se loguea a una aplicacion
	 */
	public String retrieveDatosSesionUsuario() throws Exception {
		ExtUserDetails extUserDetails = (ExtUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Map<String, Object> map = this.basicService.retrieveDatosSesionUsuario();
		Map<String, String> datos = new HashMap<String, String>(); 
		for (Entry<String, Object> entry : map.entrySet()) {
			datos.put(entry.getKey(), entry.getValue()!=null ? entry.getValue().toString() : "");
		}
		datos.put("DN", extUserDetails.getDn());
		datos.put("Usuario", extUserDetails.getUsername());
		datos.put("Descripción empresa", extUserDetails.getEmpresa());
		this.setDatosSesionUsuario(datos);
		
		return SUCCESS;
	}
	
}