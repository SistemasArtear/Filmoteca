package com.artear.filmo.services.interfaces;

import java.util.List;

import com.artear.filmo.bo.programacion.modificarRatingExcedente.BusquedaTitulosRatingExcedenteResponse;
import com.artear.filmo.bo.programacion.modificarRatingExcedente.ModificarRatingExcedenteRequest;
import com.artear.filmo.bo.programacion.modificarRatingExcedente.ModificarRatingExcedenteResponse;
import com.artear.filmo.bo.programacion.modificarRatingExcedente.BusquedaTitulosRatingExcedenteRequest;

public interface RatingExcedenteService {
	List<BusquedaTitulosRatingExcedenteResponse> obtenerTitulosRatingExcedente(BusquedaTitulosRatingExcedenteRequest request);
	
	ModificarRatingExcedenteResponse modificarRatingExcedente(ModificarRatingExcedenteRequest request);
	
}
