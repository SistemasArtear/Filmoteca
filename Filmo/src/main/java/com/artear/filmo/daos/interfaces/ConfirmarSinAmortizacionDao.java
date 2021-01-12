package com.artear.filmo.daos.interfaces;

import java.util.List;

import com.artear.filmo.bo.programacion.BuscarTitulosProgramasRequest;
import com.artear.filmo.bo.programacion.ConfirmarRequest;
import com.artear.filmo.bo.programacion.ProgramaClaveResponse;
import com.artear.filmo.bo.programacion.ProgramaCodigoResponse;
import com.artear.filmo.bo.programacion.TituloPrograma;

public interface ConfirmarSinAmortizacionDao {

    List<ProgramaClaveResponse> buscarProgramasClave(String descripcion);

    List<ProgramaCodigoResponse> buscarProgramasCodigo(String descripcion, String senial);

    List<TituloPrograma> buscarTitulosProgramas(BuscarTitulosProgramasRequest request);

    void confirmar(ConfirmarRequest request);

}
