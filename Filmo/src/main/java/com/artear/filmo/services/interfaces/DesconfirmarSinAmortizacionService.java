package com.artear.filmo.services.interfaces;

import java.util.List;

import com.artear.filmo.bo.programacion.BuscarTitulosProgramasRequest;
import com.artear.filmo.bo.programacion.DesconfirmarRequest;
import com.artear.filmo.bo.programacion.ProgramaClaveResponse;
import com.artear.filmo.bo.programacion.ProgramaCodigoResponse;
import com.artear.filmo.bo.programacion.TituloPrograma;

/**
 * 
 * @author flvaldes
 * 
 */
public interface DesconfirmarSinAmortizacionService {
	
	List<ProgramaClaveResponse> buscarProgramasClave(String descripcion);

	List<ProgramaCodigoResponse> buscarProgramasCodigo(String descripcion, String senial);
	
	List<TituloPrograma> buscarTitulosProgramas(
			BuscarTitulosProgramasRequest request);

	void desconfirmar(DesconfirmarRequest request);
}
