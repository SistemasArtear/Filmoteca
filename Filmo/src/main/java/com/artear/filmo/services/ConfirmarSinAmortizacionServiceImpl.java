package com.artear.filmo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artear.filmo.bo.programacion.BuscarTitulosProgramasRequest;
import com.artear.filmo.bo.programacion.ConfirmarRequest;
import com.artear.filmo.bo.programacion.ProgramaClaveResponse;
import com.artear.filmo.bo.programacion.ProgramaCodigoResponse;
import com.artear.filmo.bo.programacion.TituloPrograma;
import com.artear.filmo.daos.interfaces.ConfirmarSinAmortizacionDao;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.exceptions.util.ErrorFilmo;
import com.artear.filmo.exceptions.util.ErrorUtils;
import com.artear.filmo.services.interfaces.ConfirmarSinAmortizacionService;

/**
 * 
 * @author flvaldes
 * 
 */
@Transactional
@Service("confirmarSinAmortizacionService")
public class ConfirmarSinAmortizacionServiceImpl implements ConfirmarSinAmortizacionService {

    @Autowired
    private ConfirmarSinAmortizacionDao confirmarSinAmortizacionDao;

    @Override
    public List<ProgramaClaveResponse> buscarProgramasClave(String descripcion) {
        try {
            return confirmarSinAmortizacionDao.buscarProgramasClave(descripcion);
        } catch (RuntimeException e) {
            ErrorFilmo error = ErrorUtils.processMessageError(e);
            throw new DataBaseException(error);
        }
    }

    @Override
    public List<ProgramaCodigoResponse> buscarProgramasCodigo(String descripcion, String senial) {
        try {
            return confirmarSinAmortizacionDao.buscarProgramasCodigo(descripcion, senial);
        } catch (RuntimeException e) {
            ErrorFilmo error = ErrorUtils.processMessageError(e);
            throw new DataBaseException(error);
        }
    }

    @Override
    public List<TituloPrograma> buscarTitulosProgramas(BuscarTitulosProgramasRequest request) {
        try {
            return confirmarSinAmortizacionDao.buscarTitulosProgramas(request);
        } catch (RuntimeException e) {
            ErrorFilmo error = ErrorUtils.processMessageError(e);
            throw new DataBaseException(error);
        }
    }

    @Override
    public void confirmar(ConfirmarRequest request) {
        try {
            confirmarSinAmortizacionDao.confirmar(request);
        } catch (DataBaseException e) {
        	throw e;
        } catch (RuntimeException e) {
            ErrorFilmo error = ErrorUtils.processMessageError(e);
            throw new DataBaseException(error);
        }
    }

}
