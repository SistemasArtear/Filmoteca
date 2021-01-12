package com.artear.filmo.services;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artear.filmo.bo.programacion.Capitulo;
import com.artear.filmo.bo.programacion.ParteSegmento;
import com.artear.filmo.bo.programacion.Programa;
import com.artear.filmo.bo.programacion.Titulo;
import com.artear.filmo.bo.programacion.TituloProgramado;
import com.artear.filmo.bo.programacion.TituloRequest;
import com.artear.filmo.bo.programacion.ValidationResult;
import com.artear.filmo.daos.interfaces.ArmadoDeProgramacionDao;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.exceptions.util.ErrorFilmo;
import com.artear.filmo.exceptions.util.ErrorUtils;
import com.artear.filmo.services.interfaces.ArmadoDeProgramacionService;

/**
 * 
 * @author flvaldes
 * 
 */
@Transactional
@Service("armadoDeProgramacionService")
public class ArmadoDeProgramacionServiceImpl implements ArmadoDeProgramacionService {

    @Autowired
    private ArmadoDeProgramacionDao armadoDeProgramacionDao;

    @Override
    public List<Programa> levantarListaDeProgramas(String idSenial) {
        try {
            return armadoDeProgramacionDao.levantarListaDeProgramas(idSenial);
        } catch (RuntimeException e) {
            ErrorFilmo error = ErrorUtils.processMessageError(e);
            throw new DataBaseException(error);
        }
    }

    @Override
    public List<TituloProgramado> obtenerGrillaProgramada(String idSenial, int programa, Date fechaSituar, Date fechaHasta) {
        try {
            return armadoDeProgramacionDao.obtenerGrillaProgramada(idSenial, programa, fechaSituar, fechaHasta);
        } catch (RuntimeException e) {
            ErrorFilmo error = ErrorUtils.processMessageError(e);
            throw new DataBaseException(error);
        }
    }

    @Override
    public List<ParteSegmento> obtenerParteSegmento(String clave, int nroCapitulo) {
        try {
            return armadoDeProgramacionDao.obtenerParteSegmento(clave, nroCapitulo);
        } catch (RuntimeException e) {
            ErrorFilmo error = ErrorUtils.processMessageError(e);
            throw new DataBaseException(error);
        }
    }

    @Override
    public List<Capitulo> obtenerCapitulos(String clave) {
        try {
            return armadoDeProgramacionDao.obtenerCapitulos(clave);
        } catch (RuntimeException e) {
            ErrorFilmo error = ErrorUtils.processMessageError(e);
            throw new DataBaseException(error);
        }
    }

    @Override
    public List<Titulo> obtenerListaTitulosProgramar(String idSenial, String tituloCast, String familiaTitulo) {
        try {
            return armadoDeProgramacionDao.obtenerListaTitulosProgramar(idSenial, tituloCast, familiaTitulo);
        } catch (RuntimeException e) {
            ErrorFilmo error = ErrorUtils.processMessageError(e);
            throw new DataBaseException(error);
        }
    }

    @Override
    public Titulo obtenerDatosTitulo(String clave, Integer nroCapitulo, Integer parte, Integer segmento) {
        try {
            return armadoDeProgramacionDao.obtenerDatosTitulo(clave, nroCapitulo, parte, segmento);
        } catch (RuntimeException e) {
            ErrorFilmo error = ErrorUtils.processMessageError(e);
            throw new DataBaseException(error);
        }
    }

    @Override
    public List<ValidationResult> validarAltaProgramacion(TituloRequest titulo, String usuario) throws ParseException {
        try {
            return armadoDeProgramacionDao.validarAltaProgramacion(titulo, usuario);
        } catch (RuntimeException e) {
            ErrorFilmo error = ErrorUtils.processMessageError(e);
            throw new DataBaseException(error);
        }
    }

    @Override
    public List<ValidationResult> validarBajaProgramacion(TituloRequest titulo) throws ParseException {
        try {
            return armadoDeProgramacionDao.validarBajaProgramacion(titulo);
        } catch (RuntimeException e) {
            ErrorFilmo error = ErrorUtils.processMessageError(e);
            throw new DataBaseException(error);
        }
    }

    @Override
    public List<ValidationResult> validarModificaProgramacion(TituloRequest titulo, String usuario) throws ParseException {
        try {
            return armadoDeProgramacionDao.validarModificaProgramacion(titulo, usuario);
        } catch (RuntimeException e) {
            ErrorFilmo error = ErrorUtils.processMessageError(e);
            throw new DataBaseException(error);
        }
    }

    @Override
    public boolean altaProgramacion(TituloRequest titulo, String usuario) throws ParseException {
        try {
            return armadoDeProgramacionDao.altaProgramacion(titulo, usuario);
        } catch (RuntimeException e) {
            ErrorFilmo error = ErrorUtils.processMessageError(e);
            throw new DataBaseException(error);
        }
    }

    @Override
    public boolean bajaProgramacion(TituloRequest titulo) throws ParseException {
        try {
            return armadoDeProgramacionDao.bajaProgramacion(titulo);
        } catch (RuntimeException e) {
            ErrorFilmo error = ErrorUtils.processMessageError(e);
            throw new DataBaseException(error);
        }
    }

    @Override
    public boolean modificaProgramacion(TituloRequest titulo, String usuario) throws ParseException {
        try {
            return armadoDeProgramacionDao.modificaProgramacion(titulo, usuario);
        } catch (RuntimeException e) {
            ErrorFilmo error = ErrorUtils.processMessageError(e);
            throw new DataBaseException(error);
        }
    }

    @Override
    public String obtenerObservaciones(TituloRequest tituloRequest, String usuario) throws ParseException {
        try {
            return armadoDeProgramacionDao.obtenerObservaciones(tituloRequest, usuario);
        } catch (RuntimeException e) {
            ErrorFilmo error = ErrorUtils.processMessageError(e);
            throw new DataBaseException(error);
        }
    }

    @Override
    public Boolean esUnaClaveValida(String claveTitulo) {
        try {
            return armadoDeProgramacionDao.esUnaClaveValida(claveTitulo);
        } catch (RuntimeException e) {
            ErrorFilmo error = ErrorUtils.processMessageError(e);
            throw new DataBaseException(error);
        }
    }
    
    @Override
    public Boolean esUnCapituloValido(String claveTitulo, Integer nroCapitulo) {
        try {
            return armadoDeProgramacionDao.esUnCapituloValido(claveTitulo, nroCapitulo);
        } catch (RuntimeException e) {
            ErrorFilmo error = ErrorUtils.processMessageError(e);
            throw new DataBaseException(error);
        }
    }
}
