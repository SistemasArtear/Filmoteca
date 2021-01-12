package com.artear.filmo.services.interfaces;

import java.util.List;

import com.artear.filmo.bo.trabajar.titulos.TituloAmortizadoGrillaResponse;

/**
 * 
 * @author flvaldes
 * 
 */
public interface TrabajarConTitulosAmortizadosService {
    List<TituloAmortizadoGrillaResponse> obtenerTitulosAmortizadosCastellanoPorDescripcion(String descripcion, String senial);

    List<TituloAmortizadoGrillaResponse> obtenerTitulosAmortizadosOriginalPorDescripcion(String descripcion, String senial);

    List<TituloAmortizadoGrillaResponse> obtenerTitulosAmortizadosCastellanoPorCodigo(String codigo, String senial);

    List<TituloAmortizadoGrillaResponse> obtenerTitulosAmortizadosOriginalPorCodigo(String codigo, String senial);

    List<Boolean> fueContabilizado(String codigo);

    List<Boolean> activarTitulo(String codigo);
}
