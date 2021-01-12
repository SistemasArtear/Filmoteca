package com.artear.filmo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artear.filmo.bo.programacion.modificarRatingExcedente.BusquedaTitulosRatingExcedenteRequest;
import com.artear.filmo.bo.programacion.modificarRatingExcedente.BusquedaTitulosRatingExcedenteResponse;
import com.artear.filmo.bo.programacion.modificarRatingExcedente.ModificarRatingExcedenteRequest;
import com.artear.filmo.bo.programacion.modificarRatingExcedente.ModificarRatingExcedenteResponse;
import com.artear.filmo.daos.interfaces.RatingExcedenteDao;
import com.artear.filmo.services.interfaces.RatingExcedenteService;

@Transactional
@Service("modificarRatingExcedenteService")
public class RatingExcedenteServiceImpl implements RatingExcedenteService {

	@Autowired
	private RatingExcedenteDao ratingExcedentePL;
	
	@Override
	public List<BusquedaTitulosRatingExcedenteResponse> obtenerTitulosRatingExcedente(BusquedaTitulosRatingExcedenteRequest request) {
		if(request.getPrograma() != null && request.getPrograma().getDescripcion().equals("-"))
			request.getPrograma().setDescripcion(null);
		if(request.getTipoNroTitulo() != null && request.getTipoNroTitulo().isEmpty())
			request.setTipoNroTitulo(null);
		return this.ratingExcedentePL.buscarRatingExcedentes(request);
	}

	@Override
	public ModificarRatingExcedenteResponse modificarRatingExcedente(ModificarRatingExcedenteRequest request) {
		ModificarRatingExcedenteResponse response = new ModificarRatingExcedenteResponse();
		response.setOk(false);
		if (!this.tieneTodosLosDatosObligatorios(request))
			response.setMensaje("El registro no puede ser modificado, todos los valores de la grilla deben estar completos!");
		else if(request.getValorRatingExcedente() == null)
			response.setMensaje("Debe indicar un nuevo valor de rating/excedente!");
		else{
			if (request.getTipoRatingExcedente() != null){
				
				try {
					if (request.getTipoRatingExcedente().equals("Rating"))
						this.ratingExcedentePL.modificarRating(request);
					else if (request.getTipoRatingExcedente().equals("Excedente"))
						this.ratingExcedentePL.modificarExcedente(request);
					
					response.setOk(true);
					response.setMensaje("Modificación realizada correctamente!");
				} catch (Exception e) {
					response.setMensaje(e.getMessage());
				}
			}
		}
		return response;
	}
	
	private boolean tieneTodosLosDatosObligatorios(ModificarRatingExcedenteRequest request){
		boolean datosObligatoriosCompletos = false;
		
		if(
				(request.getTipoTitulo() != "" && request.getTipoTitulo() != null) &&
				(request.getTipoRatingExcedente() != "" && request.getTipoRatingExcedente() != null) &&
				(request.getPrograma() != null && request.getPrograma().getCodigo() != null) &&
				request.getNumeroTitulo() != null &&
				request.getNroCapitulo() != null &&
				request.getGrupo() != null &&
				request.getContrato() != null &&
				request.getFechaExhibicion() != null 
		)
			return true;
			
		return datosObligatoriosCompletos;
	}

}
