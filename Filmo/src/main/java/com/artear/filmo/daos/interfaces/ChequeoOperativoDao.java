package com.artear.filmo.daos.interfaces;

import com.artear.filmo.bo.chequeoOperativo.ChequeoOperativoResponse;

public interface ChequeoOperativoDao {
	public ChequeoOperativoResponse validarChequeoOperativo(String periodo);
	
	public ChequeoOperativoResponse altaEnLibreria(String periodo);
	
	public ChequeoOperativoResponse confirmaChequeoOperativo(Integer mes, Integer anio);
	
	public ChequeoOperativoResponse exhibicion(Integer mes, Integer anio);
	
	public void procesarChequeoOperativo(Integer mes, Integer anio);

    public boolean backUpCopiaMensual(String username);
}
