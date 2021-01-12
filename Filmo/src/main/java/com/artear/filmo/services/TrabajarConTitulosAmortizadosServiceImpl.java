package com.artear.filmo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artear.filmo.bo.trabajar.titulos.TituloAmortizadoGrillaResponse;
import com.artear.filmo.daos.interfaces.TrabajarConTitulosAmortizadosDao;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.exceptions.util.ErrorFilmo;
import com.artear.filmo.exceptions.util.ErrorUtils;
import com.artear.filmo.services.interfaces.TrabajarConTitulosAmortizadosService;

/**
 * 
 * @author flvaldes
 * 
 */
@Transactional
@Service("trabajarConTitulosAmortizadosService")
public class TrabajarConTitulosAmortizadosServiceImpl implements TrabajarConTitulosAmortizadosService {

    @Autowired
    private TrabajarConTitulosAmortizadosDao trabajarConTitulosAmortizadosDao;

    @Override
    public List<TituloAmortizadoGrillaResponse> obtenerTitulosAmortizadosCastellanoPorDescripcion(String descripcion, String senial) {
        try {
            return trabajarConTitulosAmortizadosDao.obtenerTitulosAmortizadosCastellanoPorDescripcion(descripcion, senial);
        } catch (RuntimeException e) {
            ErrorFilmo error = ErrorUtils.processMessageError(e);
            throw new DataBaseException(error);
        }
    }

    @Override
    public List<TituloAmortizadoGrillaResponse> obtenerTitulosAmortizadosOriginalPorDescripcion(String descripcion,String senial) {
        try {
            return trabajarConTitulosAmortizadosDao.obtenerTitulosAmortizadosOriginalPorDescripcion(descripcion, senial);
        } catch (RuntimeException e) {
            ErrorFilmo error = ErrorUtils.processMessageError(e);
            throw new DataBaseException(error);
        }
    }

    @Override
    public List<TituloAmortizadoGrillaResponse> obtenerTitulosAmortizadosCastellanoPorCodigo(String codigo, String senial) {
        try {
            return trabajarConTitulosAmortizadosDao.obtenerTitulosAmortizadosCastellanoPorCodigo(codigo, senial);
        } catch (RuntimeException e) {
            ErrorFilmo error = ErrorUtils.processMessageError(e);
            throw new DataBaseException(error);
        }
    }

    @Override
    public List<TituloAmortizadoGrillaResponse> obtenerTitulosAmortizadosOriginalPorCodigo(String codigo, String senial) {
        try {
            return trabajarConTitulosAmortizadosDao.obtenerTitulosAmortizadosOriginalPorCodigo(codigo, senial);
        } catch (RuntimeException e) {
            ErrorFilmo error = ErrorUtils.processMessageError(e);
            throw new DataBaseException(error);
        }
    }

    @Override
    public List<Boolean> fueContabilizado(String codigo) {
        try {
            return trabajarConTitulosAmortizadosDao.fueContabilizado(codigo);
        } catch (RuntimeException e) {
            ErrorFilmo error = ErrorUtils.processMessageError(e);
            throw new DataBaseException(error);
        }
    }

    @Override
    public List<Boolean> activarTitulo(String codigo) {
        try {
            return trabajarConTitulosAmortizadosDao.activarTitulo(codigo);
        } catch (RuntimeException e) {
            ErrorFilmo error = ErrorUtils.processMessageError(e);
            throw new DataBaseException(error);
        }
    }

}
