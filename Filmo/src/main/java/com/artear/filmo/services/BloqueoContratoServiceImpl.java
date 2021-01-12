package com.artear.filmo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artear.filmo.daos.interfaces.BloqueoContratoDao;
import com.artear.filmo.services.interfaces.BloqueoContratoService;

/**
 * 
 * @author flvaldes
 * 
 */
@Transactional
@Service("bloqueoContratoService")
public class BloqueoContratoServiceImpl implements BloqueoContratoService {

    @Autowired
    private BloqueoContratoDao bloqueoContratoDao;

    @Override
    public void bloquearContrato(Integer claveContrato, String sessionId, String user) {
        bloqueoContratoDao.bloquearContrato(claveContrato, sessionId, user);
    }

    @Override
    public void desbloquearContrato(Integer claveContrato) {
        bloqueoContratoDao.desbloquearContrato(claveContrato);
    }

    @Override
    public boolean estaBloqueado(Integer claveContrato) {
        return bloqueoContratoDao.estaBloqueado(claveContrato);
    }


}
