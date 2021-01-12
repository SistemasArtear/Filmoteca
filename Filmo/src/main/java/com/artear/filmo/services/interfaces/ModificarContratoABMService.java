package com.artear.filmo.services.interfaces;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

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

/**
 * 
 * @author mtito
 * 
 */
public interface ModificarContratoABMService {

    Boolean actualizarContrato(ContratoValidationRequest contratoCabeceraValidationRequest) throws ParseException;

    ContratoValidationResult altaSenial(SenialImporte senialImporteRequest);

    void modificarSenial(SenialImporte senialImporteRequest);

    List<ContratoValidationResult> updateContratoMontoSenial(SenialImporte senialImporteRequest);

    List<ContratoValidationResult> eliminarSenial(SenialImporte senialImporteRequest);

    List<ContratoValidationResult> altaGrupoSinCap(GrupoRequest altaGrupoRequest, String usuario);

    List<ContratoValidationResult> altaGrupoConCap(GrupoRequest grupoRequest, String usuario);

    List<ContratoValidationResult> altaTituloContratado(ValidarAltaTituloRequest validarAltaTituloRequest, String usuario);

    @SuppressWarnings("rawtypes")
    List<ContratoValidationResult> altaTituloContratadoCC(Map data, String usuario);

    @SuppressWarnings("rawtypes")
    List<ContratoValidationResult> altaTituloContratadoCCTodos(Map data, String usuario);

    Boolean actualizarRerunDesenlace(RerunRequest rerunRequest, String usuario);

    Boolean actualizarEnlaceAnterior(RerunRequest rerunRequest, String usuario) throws ParseException;

    Boolean actualizarEnlacePosterior(RerunRequest rerunRequest, String usuario) throws ParseException;

    List<ContratoValidationResult> modificacionDeGrupoConCap(GrupoRequest grupoRequest, String usuario);

    List<ContratoValidationResult> modificacionDeGrupoSinCap(GrupoRequest grupoRequest, String usuario);

    List<ContratoValidationResult> modificacionDeCantidadGrupoSinCap(GrupoRequest grupoRequest, String usuario);

    List<ContratoValidationResult> modificacionDeCantidadGrupoConCap(GrupoRequest grupoRequest, String usuario);

    @SuppressWarnings("rawtypes")
    List<ContratoValidationResult> capitulosAAgregar(Map data, String usuario);

    @SuppressWarnings("rawtypes")
    List<ContratoValidationResult> capitulosABorrar(Map data, String usuario);

    @SuppressWarnings("rawtypes")
    List<ContratoValidationResult> titulosABorrar(Map data, String usuario);

    List<ContratoValidationResult> desmarcarTitulo(TituloRequest tituloRequest, String usuario);

    List<ContratoValidationResult> ampliarDerechos(VigenciaRequest vigenciaRequest, String usuario) throws ParseException;

    List<ContratoValidationResult> adelantarVencimiento(VigenciaRequest vigenciaRequest, String usuario) throws ParseException;

    List<ContratoValidationResult> procesarPayTV(VigenciaRequest vigenciaRequest, String usuario) throws ParseException;

    List<ContratoValidationResult> modificarTituloContratadoSC(TituloConGrupo tituloConGrupoRequest, String usuario);

    List<ContratoValidationResult> modificarTituloContratadoCC(TituloConGrupo tituloConGrupoRequest, String usuario);

    @SuppressWarnings("rawtypes")
    List<ContratoValidationResult> reemplazarTitulos(Map data, String usuario);

    List<ContratoValidationResult> reemplazarGrupo(GruposConReemplazoRequest gruposConReemplazoRequest, String usuario);

    @SuppressWarnings("rawtypes")
    List<ContratoValidationResult> extenderChequeo(Map data, String usuario);

    List<ContratoValidationResult> eliminarGrupo(Integer claveContrato, Integer claveGrupo, String senial, String usuario);

    List<ContratoValidationResult> actualizarTG(ActualizarTGRequest actualizarTGRequest, String usuario);

    void cleanTitulosConReRun(TitulosRequest titulosRequest, String usuario);

    void actualizarImporteSenialAutomaticamente(ContratoValidationRequest contratoValidationRequest);
}
