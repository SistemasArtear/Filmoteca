package com.artear.filmo.daos.interfaces;

import java.util.List;

import com.artear.filmo.bo.common.Senial;

public interface BasicDaoPL {

	List<Senial> getSeniales(String seniales);

	void setUsuarioEnSession(String usuario);
	
}
