package com.artear.filmo.services.interfaces;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.artear.filmo.bo.contratos.ContratoValidationRequest;
import com.artear.filmo.bo.contratos.ContratoValidationResult;
import com.artear.filmo.bo.contratos.GrupoRequest;
import com.artear.filmo.bo.contratos.GruposConReemplazoRequest;
import com.artear.filmo.bo.contratos.RerunRequest;
import com.artear.filmo.bo.contratos.TituloConGrupo;
import com.artear.filmo.bo.contratos.TituloRequest;
import com.artear.filmo.bo.contratos.ValidarAltaTituloRequest;
import com.artear.filmo.bo.contratos.VigenciaRequest;

/**
 * 
 * @author mtito
 * 
 */
public interface ModificarContratoValidatorService {

    List<ContratoValidationResult> validarModificacionDeCabecera(ContratoValidationRequest contratoCabeceraValidationRequest) throws ParseException;

    List<ContratoValidationResult> validarContratoConSeniales(ContratoValidationRequest contratoCabeceraValidationRequest);

    List<ContratoValidationResult> validarMontos(Integer claveContrato);

    List<ContratoValidationResult> validarCabeceraSeniales(ContratoValidationRequest contratoValidationRequest) throws ParseException;

    List<ContratoValidationResult> validarAltaTitulo(ValidarAltaTituloRequest validarAltaTituloRequest, String usuario);

    List<ContratoValidationResult> validarAltaGrupoSinCap(GrupoRequest grupoRequest);

    List<ContratoValidationResult> validarAltaGrupoConCap(GrupoRequest grupoRequest);

    List<ContratoValidationResult> validarRerunDesenlace(RerunRequest rerunRequest, String usuario);

    List<ContratoValidationResult> validarEnlaceAnterior(RerunRequest rerunRequest, String usuario) throws ParseException;

    List<ContratoValidationResult> validarEnlacePosterior(RerunRequest rerunRequest, String usuario) throws ParseException;
    
    List<ContratoValidationResult> validarModificaGrupoSinCap(GrupoRequest grupoRequest, String usuario) throws ParseException;

    List<ContratoValidationResult> validarModificaGrupoConCap(GrupoRequest grupoRequest, String usuario) throws ParseException;

    List<ContratoValidationResult> validarModificaCantidadGrupoConCap(GrupoRequest grupoRequest, String usuario);

    @SuppressWarnings("rawtypes")
    List<ContratoValidationResult> validarCapitulosAAgregar(Map data, String usuario);

    @SuppressWarnings("rawtypes")
    List<ContratoValidationResult> validarCapitulosABorrar(Map data, String usuario);

    @SuppressWarnings("rawtypes")
    List<ContratoValidationResult> validarTitulosABorrar(Map data, String usuario);

    List<ContratoValidationResult> validarDescarmarTitulo(TituloRequest tituloRequest, String usuario);

    List<ContratoValidationResult> validarAmpliarDerechos(VigenciaRequest vigenciaRequest, String usuario) throws ParseException;

    List<ContratoValidationResult> validarAdelantarVencimiento(VigenciaRequest vigenciaRequest, String usuario) throws ParseException;

    List<ContratoValidationResult> validarProcesarPayTV(VigenciaRequest vigenciaRequest, String usuario) throws ParseException;

    List<ContratoValidationResult> validarModificarTituloContratadoSC(TituloConGrupo tituloConGrupoRequest, String usuario);

    @SuppressWarnings("rawtypes")
    List<ContratoValidationResult> validarReemplazarTitulos(Map data, String usuario);

    List<ContratoValidationResult> validarReemplazarGrupo(GruposConReemplazoRequest gruposConReemplazoRequest, String usuario);

    List<ContratoValidationResult> validarExtenderChequeoTecnico(Integer claveContrato, String usuario);

    List<ContratoValidationResult> validarEliminarGrupo(Integer claveContrato, Integer claveGrupo, String senial, String usuario);

    List<ContratoValidationResult> validarCabeceraSenial(ContratoValidationRequest contratoValidationRequest);
}
