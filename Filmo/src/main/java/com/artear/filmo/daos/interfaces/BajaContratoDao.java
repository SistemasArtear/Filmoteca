package com.artear.filmo.daos.interfaces;

import java.util.List;

import com.artear.filmo.bo.contratos.BajaContratoValidationResult;


public interface BajaContratoDao {

    List<BajaContratoValidationResult> validarBajaContrato(Integer contrato);
    
    void elimiarContrato(Integer contrato);

}
