package com.artear.filmo.services.interfaces;

import com.artear.filmo.bo.chequeoOperativo.ChequeoOperativoResponse;

public interface ChequeoOperativoService {

	public ChequeoOperativoResponse validarChequeoOperativo(String periodo);
	
	public ChequeoOperativoResponse confirmaChequeoOperativo(Integer mes, Integer anio);
	
	public void procesarChequeoOperativo(Integer mes, Integer anio);

	public ChequeoOperativoResponse generarReportes(String fecha, Integer mes, Integer anio);

    public boolean backUpCopiaMensual(String username);
}
