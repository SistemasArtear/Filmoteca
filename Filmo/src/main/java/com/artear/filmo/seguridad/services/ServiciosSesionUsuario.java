package com.artear.filmo.seguridad.services;

import java.util.List;
import java.util.Map;

public interface ServiciosSesionUsuario {

	public Map<String, Object> retrieveAllAttributes();
	
	public List<String> getRoles();

	public String getUsuario();

	public String getNombreCompleto();

	public String getTelefono();

	public String getCelular();

	public String getFechaNacimiento();

	public String getJerarquia();

	public String getLegajo();

	public String getPosicion();

	public String getFuncion();

	public String getMail();

	public String getFTPServer();

	public Integer getIdEmpresa();

	public String getEmpresa();

	public String getTiempoExpiracion();

	public String getCentroCosto();

	public List<String> getGeneradores();
	
	public List<String> getSeniales();

	public void setIdEmpresa(short idEmpresa);

	public void setEmpresa(String empresa);
}
