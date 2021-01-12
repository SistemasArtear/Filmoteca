package com.artear.filmo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artear.filmo.bo.contratos.BajaContratoValidationResult;
import com.artear.filmo.daos.interfaces.BajaContratoDao;
import com.artear.filmo.services.interfaces.BajaContratoService;

@Transactional
@Service("bajaContratoService")
public class BajaContratoServiceImpl implements BajaContratoService {

    @Autowired
    private BajaContratoDao bajaContratoDao;

    @Override
    public List<BajaContratoValidationResult> validarBajaContrato(Integer contrato) {
        return bajaContratoDao.validarBajaContrato(contrato); 
    }

    @Override
    public void eliminarContrato(Integer contrato) {
        bajaContratoDao.elimiarContrato(contrato); 
    }

}
