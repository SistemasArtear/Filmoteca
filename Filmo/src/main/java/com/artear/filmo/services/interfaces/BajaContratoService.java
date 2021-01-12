package com.artear.filmo.services.interfaces;

import java.util.List;

import com.artear.filmo.bo.contratos.BajaContratoValidationResult;


/**
 * 
 * @author mtito
 * 
 */
public interface BajaContratoService {

    List<BajaContratoValidationResult> validarBajaContrato(Integer contrato);
    
    void eliminarContrato(Integer contrato);

}
