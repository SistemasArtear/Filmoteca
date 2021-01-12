package com.artear.filmo.daos.interfaces;

import java.util.List;

import com.artear.filmo.bo.programacion.modificarRatingExcedente.BusquedaTitulosRatingExcedenteRequest;
import com.artear.filmo.bo.programacion.modificarRatingExcedente.BusquedaTitulosRatingExcedenteResponse;
import com.artear.filmo.bo.programacion.modificarRatingExcedente.ModificarRatingExcedenteRequest;

public interface RatingExcedenteDao {
	List<BusquedaTitulosRatingExcedenteResponse> buscarRatingExcedentes(BusquedaTitulosRatingExcedenteRequest request);
	
	void modificarExcedente(ModificarRatingExcedenteRequest request) throws Exception;
	
	void modificarRating(ModificarRatingExcedenteRequest request) throws Exception;
}
