package com.artear.filmo.services.interfaces;

import java.util.List;
import java.util.Map;

import com.artear.filmo.bo.common.Senial;

/**
 * 
 * @author flvaldes
 * 
 */
public interface BasicService {

	List<Senial> getSeniales();

	String getUsuarioEmpresaLogeo();
	
	Map<String, Object> retrieveDatosSesionUsuario();
	
	void setUsuarioEnSession();
	
	String getEmpresaDescripcion(short codigoEmpresa);
}
