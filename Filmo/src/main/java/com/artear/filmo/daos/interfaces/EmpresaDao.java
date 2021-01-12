package com.artear.filmo.daos.interfaces;

import java.util.List;

import com.artear.filmo.bo.common.Empresa;

public interface EmpresaDao {

	public List<Empresa> retrieveById(Integer idEmpresa);
	
}
