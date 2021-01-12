package com.artear.filmo.seguridad.services;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;

import com.artear.filmo.constants.SesionUsuarioConstantes;
import com.artear.filmo.seguridad.userdetails.ExtUserDetails;

public class ServiciosSesionUsuarioImpl implements ServiciosSesionUsuario {
	
	private ExtUserDetails getExtUserDetails(){
		return (ExtUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	private Object getAttributes(String key){
		return getExtUserDetails().getAttributes().get(key);
	}
	
	public Map<String, Object> retrieveAllAttributes() {
		return getExtUserDetails().getAttributes();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getRoles() {
		return (List<String>)getAttributes(SesionUsuarioConstantes.ROLES);
	}
	
	public String getUsuario(){
		return getExtUserDetails().getUsername(); 
	}
	
	public String getNombreCompleto() {
		return (String)getAttributes(SesionUsuarioConstantes.NOMBRE_COMPLETO);
	}
	
	public String getTelefono() {
		return (String)getAttributes(SesionUsuarioConstantes.TELEFONO);
	}
	
	public String getCelular() {
		return (String)getAttributes(SesionUsuarioConstantes.CELULAR);
	}
	
	public String getFechaNacimiento() {
		return (String)getAttributes(SesionUsuarioConstantes.FECHA_NACIMIENTO);
	}
	
	public String getJerarquia() {
		return (String)getAttributes(SesionUsuarioConstantes.JERARQUIA);
	}
	
	public String getLegajo() {
		return (String)getAttributes(SesionUsuarioConstantes.LEGAJO);
	}
	
	public String getPosicion() {
		return (String)getAttributes(SesionUsuarioConstantes.POSICION);
	}
	
	public String getFuncion() {
		return (String)getAttributes(SesionUsuarioConstantes.FUNCION);
	}
	
	public String getMail() {
		return (String)getAttributes(SesionUsuarioConstantes.EMAIL);
	}

	public String getFTPServer() {
		return (String)getAttributes(SesionUsuarioConstantes.FTP_SERVER);
	}
	
	public void setIdEmpresa(Integer idEmpresa) {
		getExtUserDetails().setIdEmpresa(idEmpresa);
	}
	
	public Integer getIdEmpresa() {
		return new Integer((String)getAttributes(SesionUsuarioConstantes.EMPRESA));
	}
	
	public String getEmpresa() {
		return this.getExtUserDetails().getEmpresa();
	}
	
	public String getTiempoExpiracion() {
		return (String)getAttributes(SesionUsuarioConstantes.TIEMPO_EXPIRACION);
	}
	
	public String getCentroCosto() {
		return (String)getAttributes(SesionUsuarioConstantes.CENTRO_COSTO);
	}

	@SuppressWarnings("unchecked")
	public List<String> getGeneradores() {
		return (List<String>)getAttributes(SesionUsuarioConstantes.GENERADORES);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getSeniales(){
		return (List<String>)getAttributes(SesionUsuarioConstantes.SENIALES);
	}

	public void setIdEmpresa(short idEmpresa) {
		this.getExtUserDetails().setIdEmpresa(new Integer(idEmpresa));
	}

	public void setEmpresa(String empresa) {
		this.getExtUserDetails().setEmpresa(empresa);
	}
	
}
