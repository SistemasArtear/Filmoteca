package com.artear.filmo.daos.interfaces;


public interface BloqueoContratoDao {

    void bloquearContrato(Integer claveContrato, String sessionId, String user);

    void desbloquearContrato(Integer claveContrato);

    boolean estaBloqueado(Integer claveContrato);

}
