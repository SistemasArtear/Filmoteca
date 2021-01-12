package com.artear.filmo.daos.interfaces;

import java.util.Date;
import java.util.List;

import com.artear.filmo.bo.programacion.LevantarExhibicionesRequest;
import com.artear.filmo.bo.programacion.ValidacionLevantarExhibicionResponse;

public interface LevantarProgramasDao {

	Date obtenerMayorFechaProgramada(String senial, Integer codPrograma);

	List<ValidacionLevantarExhibicionResponse> validacionLevantarExhibiciones(
			LevantarExhibicionesRequest request) throws Exception;

	void levantarExhibiciones(LevantarExhibicionesRequest request)
			throws Exception;

}
