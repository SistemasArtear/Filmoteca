package com.artear.filmo.services.interfaces;


/**
 * 
 * @author mtito
 * 
 */
public interface BloqueoContratoService {

    void bloquearContrato(Integer claveContrato, String sessionId, String user);

    void desbloquearContrato(Integer claveContrato);

    boolean estaBloqueado(Integer claveContrato);

}
