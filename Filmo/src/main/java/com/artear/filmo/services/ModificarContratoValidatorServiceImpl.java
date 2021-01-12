package com.artear.filmo.services;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artear.filmo.bo.contratos.ContratoValidationRequest;
import com.artear.filmo.bo.contratos.ContratoValidationResult;
import com.artear.filmo.bo.contratos.GrupoRequest;
import com.artear.filmo.bo.contratos.GruposConReemplazoRequest;
import com.artear.filmo.bo.contratos.RerunRequest;
import com.artear.filmo.bo.contratos.TituloConGrupo;
import com.artear.filmo.bo.contratos.TituloRequest;
import com.artear.filmo.bo.contratos.ValidarAltaTituloRequest;
import com.artear.filmo.bo.contratos.VigenciaRequest;
import com.artear.filmo.daos.interfaces.ModificarContratoValidatorDao;
import com.artear.filmo.services.interfaces.ModificarContratoValidatorService;

@Transactional
@Service("modificarContratoValidatorService")
public class ModificarContratoValidatorServiceImpl implements ModificarContratoValidatorService {

    @Autowired
    private ModificarContratoValidatorDao modificarContratoValidatorDao;

    @Override
    public List<ContratoValidationResult> validarModificacionDeCabecera(ContratoValidationRequest contratoCabeceraValidationRequest) throws ParseException {
        return modificarContratoValidatorDao.validarModificacionDeCabecera(contratoCabeceraValidationRequest);
    }

    @Override
    public List<ContratoValidationResult> validarContratoConSeniales(ContratoValidationRequest contratoCabeceraValidationRequest) {
        return modificarContratoValidatorDao.validarContratoConSeniales(contratoCabeceraValidationRequest);    
    }

    @Override
    public List<ContratoValidationResult> validarCabeceraSeniales(ContratoValidationRequest contratoValidationRequest) throws ParseException {
        return modificarContratoValidatorDao.validarCabeceraSeniales(contratoValidationRequest);    
    }

    @Override
    public List<ContratoValidationResult> validarMontos(Integer claveContrato) {
        return modificarContratoValidatorDao.validarMontos(claveContrato);    
    }

    @Override
    public List<ContratoValidationResult> validarAltaTitulo(ValidarAltaTituloRequest validarAltaTituloRequest, String usuario) {
        return modificarContratoValidatorDao.validarAltaTitulo(validarAltaTituloRequest, usuario);
    }

    @Override
    public List<ContratoValidationResult> validarAltaGrupoSinCap(GrupoRequest grupoRequest) {
        return modificarContratoValidatorDao.validarAltaGrupoSinCap(grupoRequest);
    }

    @Override
    public List<ContratoValidationResult> validarAltaGrupoConCap(GrupoRequest grupoRequest) {
        return modificarContratoValidatorDao.validarAltaGrupoConCap(grupoRequest);
    }

    @Override
    public List<ContratoValidationResult> validarRerunDesenlace(RerunRequest rerunRequest, String usuario) {
        return modificarContratoValidatorDao.validarRerunDesenlace(rerunRequest, usuario);
    }

    @Override
    public List<ContratoValidationResult> validarEnlaceAnterior(RerunRequest rerunRequest, String usuario) throws ParseException {
        return modificarContratoValidatorDao.validarEnlaceAnterior(rerunRequest, usuario);
    }

    @Override
    public List<ContratoValidationResult> validarEnlacePosterior(RerunRequest rerunRequest, String usuario) throws ParseException {
        return modificarContratoValidatorDao.validarEnlacePosterior(rerunRequest, usuario);
    }
    
    @Override
    public List<ContratoValidationResult> validarModificaGrupoSinCap(GrupoRequest grupoRequest, String usuario) throws ParseException {
        return modificarContratoValidatorDao.validarModificaGrupoSinCap(grupoRequest, usuario);
    }

    @Override
    public List<ContratoValidationResult> validarModificaGrupoConCap(GrupoRequest grupoRequest, String usuario) throws ParseException {
        return modificarContratoValidatorDao.validarModificaGrupoConCap(grupoRequest, usuario);
    }

    @Override
    public List<ContratoValidationResult> validarModificaCantidadGrupoConCap(GrupoRequest grupoRequest, String usuario) {
        return modificarContratoValidatorDao.validarModificaCantidadGrupoConCap(grupoRequest, usuario);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<ContratoValidationResult> validarCapitulosAAgregar(Map data, String usuario) {
        return modificarContratoValidatorDao.validarCapitulosAAgregar(data, usuario);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<ContratoValidationResult> validarCapitulosABorrar(Map data, String usuario) {
        return modificarContratoValidatorDao.validarCapitulosABorrar(data, usuario);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<ContratoValidationResult> validarTitulosABorrar(Map data, String usuario) {
        return modificarContratoValidatorDao.validarTitulosABorrar(data, usuario);
    }

    @Override
    public List<ContratoValidationResult> validarDescarmarTitulo(TituloRequest tituloRequest, String usuario) {
        return modificarContratoValidatorDao.validarDescarmarTitulo(tituloRequest, usuario);
    }

    @Override
    public List<ContratoValidationResult> validarAmpliarDerechos(VigenciaRequest vigenciaRequest, String usuario) throws ParseException {
        return modificarContratoValidatorDao.validarAmpliarDerechos(vigenciaRequest, usuario);
    }

    @Override
    public List<ContratoValidationResult> validarAdelantarVencimiento(VigenciaRequest vigenciaRequest, String usuario) throws ParseException {
        return modificarContratoValidatorDao.validarAdelantarVencimiento(vigenciaRequest, usuario);
    }

    @Override
    public List<ContratoValidationResult> validarProcesarPayTV(VigenciaRequest vigenciaRequest, String usuario) throws ParseException {
        return modificarContratoValidatorDao.validarProcesarPayTV(vigenciaRequest, usuario);
    }

    @Override
    public List<ContratoValidationResult> validarModificarTituloContratadoSC(TituloConGrupo tituloConGrupoRequest, String usuario) {
        return modificarContratoValidatorDao.validarModificarTituloContratadoSC(tituloConGrupoRequest, usuario);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<ContratoValidationResult> validarReemplazarTitulos(Map data, String usuario) {
        return modificarContratoValidatorDao.validarReemplazarTitulos(data, usuario);
    }

    @Override
    public List<ContratoValidationResult> validarReemplazarGrupo(GruposConReemplazoRequest gruposConReemplazoRequest, String usuario) {
        return modificarContratoValidatorDao.validarReemplazarGrupo(gruposConReemplazoRequest, usuario);
    }

    @Override
    public List<ContratoValidationResult> validarExtenderChequeoTecnico(Integer claveContrato, String usuario) {
        return modificarContratoValidatorDao.validarExtenderChequeoTecnico(claveContrato, usuario);
    }

    @Override
    public List<ContratoValidationResult> validarEliminarGrupo(Integer claveContrato, Integer claveGrupo, String senial, String usuario) {
        return modificarContratoValidatorDao.validarEliminarGrupo(claveContrato, claveGrupo, senial, usuario);
    }

    @Override
    public List<ContratoValidationResult> validarCabeceraSenial(ContratoValidationRequest contratoValidationRequest) {
        return modificarContratoValidatorDao.validarCabeceraSenial(contratoValidationRequest);
    }
}
