package com.artear.filmo.services;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artear.filmo.bo.contratos.ActualizarTGRequest;
import com.artear.filmo.bo.contratos.ContratoValidationRequest;
import com.artear.filmo.bo.contratos.ContratoValidationResult;
import com.artear.filmo.bo.contratos.GrupoRequest;
import com.artear.filmo.bo.contratos.GruposConReemplazoRequest;
import com.artear.filmo.bo.contratos.RerunRequest;
import com.artear.filmo.bo.contratos.SenialImporte;
import com.artear.filmo.bo.contratos.TituloConGrupo;
import com.artear.filmo.bo.contratos.TituloRequest;
import com.artear.filmo.bo.contratos.TitulosRequest;
import com.artear.filmo.bo.contratos.ValidarAltaTituloRequest;
import com.artear.filmo.bo.contratos.VigenciaRequest;
import com.artear.filmo.daos.interfaces.ModificarContratoABMDao;
import com.artear.filmo.services.interfaces.ModificarContratoABMService;

@Transactional
@Service("modificarContratoABMService")
public class ModificarContratoABMServiceImpl implements ModificarContratoABMService {

    @Autowired
    private ModificarContratoABMDao modificarContratoABMDao;

    @Override
    public ContratoValidationResult altaSenial(SenialImporte senialImporteRequest) {
        return modificarContratoABMDao.altaSenial(senialImporteRequest);
    }

    @Override
    public List<ContratoValidationResult> altaGrupoSinCap(GrupoRequest grupoRequest, String usuario) {
        return modificarContratoABMDao.altaGrupoSinCap(grupoRequest, usuario);
    }

    @Override
    public List<ContratoValidationResult> altaGrupoConCap(GrupoRequest grupoRequest, String usuario) {
        return modificarContratoABMDao.altaGrupoConCap(grupoRequest, usuario);
    }

    @Override
    public Boolean actualizarContrato(ContratoValidationRequest contratoCabeceraValidationRequest) throws ParseException {
         return modificarContratoABMDao.actualizarContrato(contratoCabeceraValidationRequest);
    }

    @Override
    public List<ContratoValidationResult> updateContratoMontoSenial(SenialImporte senialImporteRequest) {
        return modificarContratoABMDao.updateContratoMontoSenial(senialImporteRequest);
    }

    @Override
    public void modificarSenial(SenialImporte senialImporteRequest) {
        modificarContratoABMDao.modificarSenial(senialImporteRequest);
    }

    @Override
    public List<ContratoValidationResult> eliminarSenial(SenialImporte senialImporteRequest) {
        return modificarContratoABMDao.eliminarSenial(senialImporteRequest);
    }

    @Override
    public List<ContratoValidationResult> altaTituloContratado(ValidarAltaTituloRequest validarAltaTituloRequest, String usuario) {
        return modificarContratoABMDao.altaTituloContratado(validarAltaTituloRequest, usuario);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<ContratoValidationResult> altaTituloContratadoCC(Map data, String usuario) {
        return modificarContratoABMDao.altaTituloContratadoCC(data, usuario);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<ContratoValidationResult> altaTituloContratadoCCTodos(Map data, String usuario) {
        return modificarContratoABMDao.altaTituloContratadoCCTodos(data, usuario);
    }

    @Override
    public Boolean actualizarRerunDesenlace(RerunRequest rerunRequest, String usuario) {
        return modificarContratoABMDao.actualizarRerunDesenlace(rerunRequest, usuario);
    }

    @Override
    public Boolean actualizarEnlaceAnterior(RerunRequest rerunRequest, String usuario) throws ParseException {
        return modificarContratoABMDao.actualizarEnlaceAnterior(rerunRequest, usuario);
    }

    @Override
    public Boolean actualizarEnlacePosterior(RerunRequest rerunRequest, String usuario) throws ParseException {
        return modificarContratoABMDao.actualizarEnlacePosterior(rerunRequest, usuario);
    }

    @Override
    public List<ContratoValidationResult> modificacionDeGrupoConCap(GrupoRequest grupoRequest, String usuario) {
        return modificarContratoABMDao.modificacionDeGrupoConCap(grupoRequest, usuario);
    }

    @Override
    public List<ContratoValidationResult> modificacionDeGrupoSinCap(GrupoRequest grupoRequest, String usuario) {
        return modificarContratoABMDao.modificacionDeGrupoSinCap(grupoRequest, usuario);
    }

    @Override
    public List<ContratoValidationResult> modificacionDeCantidadGrupoSinCap(GrupoRequest grupoRequest, String usuario) {
        return modificarContratoABMDao.modificacionDeCantidadGrupoSinCap(grupoRequest, usuario);
    }

    @Override
    public List<ContratoValidationResult> modificacionDeCantidadGrupoConCap(GrupoRequest grupoRequest, String usuario) {
        return modificarContratoABMDao.modificacionDeCantidadGrupoConCap(grupoRequest, usuario);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<ContratoValidationResult> capitulosAAgregar(Map data, String usuario) {
        return modificarContratoABMDao.capitulosAAgregar(data, usuario);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<ContratoValidationResult> capitulosABorrar(Map data, String usuario) {
        return modificarContratoABMDao.capitulosABorrar(data, usuario);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<ContratoValidationResult> titulosABorrar(Map data, String usuario) {
        return modificarContratoABMDao.titulosABorrar(data, usuario);
    }

    @Override
    public List<ContratoValidationResult> desmarcarTitulo(TituloRequest tituloRequest, String usuario) {
        return modificarContratoABMDao.desmarcarTitulo(tituloRequest, usuario);
    }

    @Override
    public List<ContratoValidationResult> procesarPayTV(VigenciaRequest vigenciaRequest, String usuario) throws ParseException {
        return modificarContratoABMDao.procesarPayTV(vigenciaRequest, usuario);
    }
    
    @Override
    public List<ContratoValidationResult> adelantarVencimiento(VigenciaRequest vigenciaRequest, String usuario) throws ParseException {
        return modificarContratoABMDao.adelantarVencimiento(vigenciaRequest, usuario);
    }

    @Override
    public List<ContratoValidationResult> ampliarDerechos(VigenciaRequest vigenciaRequest, String usuario) throws ParseException {
        return modificarContratoABMDao.ampliarDerechos(vigenciaRequest, usuario);
    }

    @Override
    public List<ContratoValidationResult> modificarTituloContratadoSC(TituloConGrupo tituloConGrupoRequest, String usuario) {
        return modificarContratoABMDao.modificarTituloContratadoSC(tituloConGrupoRequest, usuario);

    }

    @Override
    public List<ContratoValidationResult> modificarTituloContratadoCC(TituloConGrupo tituloConGrupoRequest, String usuario) {
        return modificarContratoABMDao.modificarTituloContratadoCC(tituloConGrupoRequest, usuario);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<ContratoValidationResult> reemplazarTitulos(Map data, String usuario) {
        return modificarContratoABMDao.reemplazarTitulos(data, usuario);
    }

    @Override
    public List<ContratoValidationResult> reemplazarGrupo(GruposConReemplazoRequest gruposConReemplazoRequest, String usuario) {
        return modificarContratoABMDao.reemplazarGrupo(gruposConReemplazoRequest, usuario);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<ContratoValidationResult> extenderChequeo(Map data, String usuario) {
        return modificarContratoABMDao.extenderChequeo(data, usuario);
    }

    @Override
    public List<ContratoValidationResult> eliminarGrupo(Integer claveContrato, Integer claveGrupo, String senial, String usuario) {
        return modificarContratoABMDao.eliminarGrupo(claveContrato, claveGrupo, senial, usuario);
    }

    @Override
    public List<ContratoValidationResult> actualizarTG(ActualizarTGRequest actualizarTGRequest, String usuario) {
        return modificarContratoABMDao.actualizarTG(actualizarTGRequest, usuario);
    }

    @Override
    public void cleanTitulosConReRun(TitulosRequest titulosRequest, String usuario) {
        modificarContratoABMDao.cleanTitulosConReRun(titulosRequest, usuario);
    }
    
    @Override
    public void actualizarImporteSenialAutomaticamente(ContratoValidationRequest contratoValidationRequest) {
        modificarContratoABMDao.actualizarImporteSenialAutomaticamente(contratoValidationRequest);
    }
}
