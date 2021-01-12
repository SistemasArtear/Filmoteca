package com.artear.filmo.daos.impl.pl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

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

@Repository("modificarContratoABMDao")
public class ModificarContratoABMDaoPL implements ModificarContratoABMDao {
    private static final String OCURRIO_UN_ERROR_DURANTE_EL_ALTA_DE_UN_GRUPO_SIN_CAP = "Ocurrio un error durante el alta de un grupo sin cap";
    private static final String OCURRIO_UN_ERROR_DURANTE_LA_ELIMINACION_DE_LA_SENIAL = "Ocurrio un error durante la eliminacion de la senial";
    private static final String NO_SE_PUDO_DAR_DE_ALTA_LA_SENIAL = "No se pudo dar de alta la senial";
    private static final String TIPO_ERROR = "E";
    private static final String TIPO_RERUN = "RR";
    private static final String TIPO_MODO_RERUN = "MRR";
    private static final String TIPO_PROCESO_RERUN = "PRR";
    private static final String TIPO_OK = "OK";
    private static final String TIPO_RECONTRATACION = "R";
    private static final String TIPO_TITULOS_RERUN = "TRR";
    private static final String TIPO_DATA_CLAVE = "D_CLAVE";
    private static final String OCURRIO_UN_ERROR_DURANTE_MODIF_GRUPO = "Ocurrio un error durante la modificacion";
    private static final String TIPO_DATA_HAY_CAPS_A_BORRAR = "D_HAY_CAPS_A_BORRAR";
    private static final String TIPO_DATA_HAY_CAPS_A_AGREGAR = "D_HAY_CAPS_A_AGREGAR";
    private static final String TIPO_DATA_CAPS_AFECTADOS = "D_CAPS_AFECTADOS";

    
    private SimpleJdbcCall actualizarContratoCall;
    private SimpleJdbcCall grabarSenialCall;
    private SimpleJdbcCall modificarSenialCall;
    private SimpleJdbcCall eliminarSenialCall;
    private SimpleJdbcCall validarSenialCall;
    private SimpleJdbcCall validarEliminarSenialCall;
    private SimpleJdbcCall updateContratoMontoSenialCall;
    private SimpleJdbcCall altaGrupoSinCapCall;
    private SimpleJdbcCall altaGrupoConCapCall;
    private SimpleJdbcCall altaTituloContratadoCall;
    private SimpleJdbcCall insertarCapituloParteCall;
    private SimpleJdbcCall altaTituloContratadoCCCall;
    private SimpleJdbcCall altaTituloContratadoCCTodosCall;
    private SimpleJdbcCall deleteCapituloParteCall;
    private SimpleJdbcCall eliminarGrupoCall;
    private SimpleJdbcCall actualizarEnlacePosteriorCall;
    private SimpleJdbcCall actualizarRerunDesenlaceCall;
    private SimpleJdbcCall actualizarEnlaceAnteriorCall;
    private SimpleJdbcCall modificacionDeGrupoConCap;
    private SimpleJdbcCall modificacionDeGrupoSinCap;
    private SimpleJdbcCall modificacionDeCantidadGrupoSinCap;
    private SimpleJdbcCall modificacionDeCantidadGrupoConCap;
    private SimpleJdbcCall capitulosABorrar;
    private SimpleJdbcCall capitulosAAgregar;
    private SimpleJdbcCall titulosABorrar;
    private SimpleJdbcCall desmarcarTitulo;
    private SimpleJdbcCall insertarTituloReemplazoCall;
    private SimpleJdbcCall titulosReemplazoCall;
    private SimpleJdbcCall deleteTitulosReemplazoCall;
    private SimpleJdbcCall deleteSenialesChequeoCall;
    private SimpleJdbcCall insertarSenialesChequeoCall;
    private SimpleJdbcCall extenderChequeoCall;
    private SimpleJdbcCall procesarPayTV;
    private SimpleJdbcCall ampliarDerechos;
    private SimpleJdbcCall adelantarVencimiento;
    private SimpleJdbcCall modificarTituloContratadoSC;
    private SimpleJdbcCall reemplazarGrupo;
    private SimpleJdbcCall actualizarTG;
    private SimpleJdbcCall modificarTituloContratadoCC;
    private SimpleJdbcCall cleanTitulosConReRun;
    private SimpleJdbcCall actualizarImporteSenialAutomaticamente;
    
    @Autowired
    public ModificarContratoABMDaoPL(DataSource dataSource) {
        super();
        this.actualizarContratoCall = buildActualizarContratoCall(dataSource);
        this.validarSenialCall = buildValidarSenialCall(dataSource);
        this.grabarSenialCall = buildGrabaSenialCall(dataSource);
        this.validarEliminarSenialCall = buildValidarEliminarSenialCall(dataSource);
        this.eliminarSenialCall = buildEliminarSenialCall(dataSource);
        this.modificarSenialCall = buildModificarSenialCall(dataSource);
        this.updateContratoMontoSenialCall = buildUpdateContratoMontoSenialCall(dataSource);
        this.altaGrupoSinCapCall = buildAltaGrupoSinCapCall(dataSource);
        this.altaGrupoConCapCall = buildAltaGrupoConCapCall(dataSource);
        this.altaTituloContratadoCall = buildAltaTituloContratadoCall(dataSource);
        this.insertarCapituloParteCall = buildInsertarCapituloParteCall(dataSource);
        this.altaTituloContratadoCCCall = buildAltaTituloContratadoCCCall(dataSource);
        this.altaTituloContratadoCCTodosCall = buildAltaTituloContratadoCCTodosCall(dataSource);
        this.actualizarEnlacePosteriorCall = buildActualizarEnlacePosteriorCall(dataSource);
        this.actualizarRerunDesenlaceCall = buildActualizarRerunDesenlaceCall(dataSource);
        this.actualizarEnlaceAnteriorCall = buildActualizarEnlaceAnteriorCall(dataSource);
        this.modificacionDeGrupoConCap = buildModificacionDeGrupoConCapCall(dataSource);
        this.modificacionDeGrupoSinCap = buildModificacionDeGrupoSinCapCall(dataSource);
        this.modificacionDeCantidadGrupoSinCap = buildModificacionDeCantidadGrupoSinCapCall(dataSource);
        this.modificacionDeCantidadGrupoConCap = buildModificacionDeCantidadGrupoConCapCall(dataSource);
        this.deleteCapituloParteCall = buildDeleteCapitulosParteCall(dataSource);
        this.capitulosABorrar = buildCapitulosABorarrCall(dataSource);
        this.capitulosAAgregar = buildCapitulosAAgregarCall(dataSource);
        this.titulosABorrar = buildTitulosABorrarCall(dataSource);
        this.desmarcarTitulo = buildDesmarcarTituloCall(dataSource);
        this.insertarTituloReemplazoCall = buildInsertarTituloReemplazoCall(dataSource);
        this.titulosReemplazoCall = buildTitulosReemplazoCall(dataSource);
        this.deleteTitulosReemplazoCall = buildDeleteTitulosReemplazoCall(dataSource);
        this.deleteSenialesChequeoCall = buildDeleteSenialesChequeoCall(dataSource);
        this.insertarSenialesChequeoCall = buildInsertarSenialesChequeoCall(dataSource);
        this.extenderChequeoCall = buildExtenderChequeoCall(dataSource);
        this.eliminarGrupoCall = buildEliminarGrupoCall(dataSource);
        this.procesarPayTV = buildProcesarPayTVCall(dataSource);
        this.ampliarDerechos = buildAmpliarDerechosCall(dataSource);
        this.adelantarVencimiento = buildAdelantarVencimiento(dataSource);
        this.modificarTituloContratadoSC = buildModificarTituloContratadoSC(dataSource);
        this.reemplazarGrupo = buildReemplazarGrupo(dataSource);
        this.procesarPayTV = buildProcesarPayTVCall(dataSource);
        this.actualizarTG = buildActualizarTGCall(dataSource);
        this.modificarTituloContratadoCC = buildModificarTituloContratadoCC(dataSource);
        this.cleanTitulosConReRun = buildCleanTitulosConReRun(dataSource);
        this.actualizarImporteSenialAutomaticamente = buildActualizarImporteSenialAutomaticamente(dataSource);
    }

    private SimpleJdbcCall buildCleanTitulosConReRun(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_1X").withProcedureName("PR_DEL_TITULOS_RERUN");
    }

    private SimpleJdbcCall buildModificarTituloContratadoCC(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_1X").withProcedureName("PR_MODIFICA_TITULO_CC");
    }

    private SimpleJdbcCall buildReemplazarGrupo(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X").withProcedureName("PR_BORRA_REEMPLAZO_GRUPO");
    }

    private SimpleJdbcCall buildModificarTituloContratadoSC(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_1X").withProcedureName("PR_MODIFICA_TITULO_SC");
    }

    private SimpleJdbcCall buildAdelantarVencimiento(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_1X").withProcedureName("PR_PROCESAR_ADELANTAR_VENC");
    }

    private SimpleJdbcCall buildAmpliarDerechosCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_1X").withProcedureName("PR_AMPLIACION_DERECHOS");
    }

    private SimpleJdbcCall buildActualizarTGCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X").withProcedureName("PR_ACTUALIZA_TG");
    }

    private SimpleJdbcCall buildProcesarPayTVCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_1X").withProcedureName("PR_PROCESAR_PAYTV");
    }

    private SimpleJdbcCall buildEliminarGrupoCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X").withProcedureName("PR_ELIMINA_GRUPO_SC");
    }

    private SimpleJdbcCall buildExtenderChequeoCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_1X").withProcedureName("PR_EXTENDER_CHEQUEO");
    }

    private SimpleJdbcCall buildInsertarSenialesChequeoCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_1X").withProcedureName("PR_INS_SENIALES");
    }

    private SimpleJdbcCall buildDeleteSenialesChequeoCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_1X").withProcedureName("PR_DEL_SENIALES");
    }

    private SimpleJdbcCall buildDeleteTitulosReemplazoCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X").withProcedureName("PR_DEL_TIT_REEMPLAZO");
    }

    private SimpleJdbcCall buildTitulosReemplazoCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X")
                .withProcedureName("PR_TITULOS_REEMPLAZO")
                .returningResultSet("P_ERRORES", new RowMapper<ContratoValidationResult>() {
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(rs.getString("TIPO"));
                        ret.setNroAdvertencia(rs.getInt("NRO_ADVERTENCIA"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
//                        ret.setNroContrato(rs.getInt("NRO_CONTRATO"));
//                        ret.setIdReporteGc(rs.getInt("REPORTE_GC"));
//                        ret.setIdReporteGt(rs.getInt("REPORTE_GT"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildInsertarTituloReemplazoCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X").withProcedureName("PR_INS_TIT_REEMPLAZO");
    }

    private SimpleJdbcCall buildDesmarcarTituloCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_1X").withProcedureName("PR_DESMARCAR");
    }

    private SimpleJdbcCall buildTitulosABorrarCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X").withProcedureName("PR_BORRAR_TITULOS_SC");
    }

    private SimpleJdbcCall buildDeleteCapitulosParteCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X").withProcedureName("PR_DEL_CAP_PARTE");
    }

    private SimpleJdbcCall buildModificacionDeCantidadGrupoSinCapCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X").withProcedureName("PR_MODIF_CANT_TITULOS_SC");
    }
    
    private SimpleJdbcCall buildModificacionDeCantidadGrupoConCapCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X").withProcedureName("PR_MODIF_CANT_ORIG_CC");
    }

    private SimpleJdbcCall buildCapitulosABorarrCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X").withProcedureName("PR_CAPITULOS_A_BORRAR");
    }

    private SimpleJdbcCall buildCapitulosAAgregarCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X").withProcedureName("PR_CAPITULOS_A_AGREGAR");
    }

    private SimpleJdbcCall buildModificacionDeGrupoSinCapCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_1X").withProcedureName("PR_MODIFICA_GRUPO_SC_GRABAR");
    }

    private SimpleJdbcCall buildModificacionDeGrupoConCapCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_1X").withProcedureName("PR_MODIFICA_GRUPO_CC_GRABAR");
    }

    private SimpleJdbcCall buildActualizarEnlaceAnteriorCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X").withProcedureName("PR_ENLACE_ANTERIOR_ACTUALIZA");
    }

    private SimpleJdbcCall buildActualizarRerunDesenlaceCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X").withProcedureName("PR_RERUN_DESENLACE_GRABA");
    }

    private SimpleJdbcCall buildActualizarEnlacePosteriorCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X").withProcedureName("PR_ENLACE_POSTERIOR_ACTUALIZA");
    }

    private SimpleJdbcCall buildInsertarCapituloParteCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X").withProcedureName("PR_INS_CAP_PARTE");
    }

    private SimpleJdbcCall buildAltaTituloContratadoCCTodosCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X").withProcedureName("PR_GRABA_TIT_CONT_CC_TODOS");
    }

    private SimpleJdbcCall buildAltaTituloContratadoCCCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X").withProcedureName("PR_GRABA_TITULO_CONTRATADO_CC");
    }

    private SimpleJdbcCall buildAltaTituloContratadoCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X").withProcedureName("PR_GRABA_TITULO_CONTRATO");
    }

    private SimpleJdbcCall buildAltaGrupoConCapCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X").withProcedureName("PR_ALTA_GRUPO_CC");
    }

    private SimpleJdbcCall buildAltaGrupoSinCapCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X").withProcedureName("PR_ALTA_GRUPO_SC");
    }

    private SimpleJdbcCall buildEliminarSenialCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X")
                .withProcedureName("PR_ELIMINA_SENIAL_CONTRATO");
    }

    
    private SimpleJdbcCall buildValidarEliminarSenialCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X")
                .withProcedureName("PR_VALIDAR_ELIMINA_SENIAL_CO")
                .returningResultSet("P_ERRORES", new RowMapper<ContratoValidationResult>() {
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(rs.getString("TIPO"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        ret.setIdReporte(rs.getInt("ID_REPORTE"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildValidarSenialCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X").withProcedureName("PR_VALIDA_SENIAL");
    }

    private SimpleJdbcCall buildUpdateContratoMontoSenialCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS")
                .withProcedureName("PR_UPD_06_MONTO_SENIAL")
                .returningResultSet("P_ERRORES", new RowMapper<ContratoValidationResult>() {
                    @Override
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(rs.getString("TIPO"));
                        ret.setNroAdvertencia(rs.getInt("NRO_ADVERTENCIA"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
//                        ret.setNroContrato(rs.getInt("NRO_CONTRATO"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildModificarSenialCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS")
                .withProcedureName("PR_FP05_FIN");
    }

    private SimpleJdbcCall buildGrabaSenialCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X")
                .withProcedureName("PR_GRABA_SENIAL");
    }

    private SimpleJdbcCall buildActualizarContratoCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource)
            .withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X").withProcedureName("PR_ACTUALIZA_CONTRATO");
    }

    private SimpleJdbcCall buildActualizarImporteSenialAutomaticamente(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X").withProcedureName("PR_GRABAR_AUTOM_CAB_SENIAL");
    }
    
    private Date toDate(String fecha) {
        try {
            if (fecha != null) {
                return new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
            } else {
                return null;
            }
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public ContratoValidationResult altaSenial(SenialImporte senialImporteRequest) {
        ContratoValidationResult ret = new ContratoValidationResult();
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", senialImporteRequest.getContrato())
            .addValue("P_SENIAL", senialImporteRequest.getCodigoSenial());
        Map<String, Object> out = this.validarSenialCall.execute(in);
        if (out.get("P_MSJ_ERROR") == null) {
            in = new MapSqlParameterSource()
                .addValue("P_CONTRATO", senialImporteRequest.getContrato())
                .addValue("P_SENIAL", senialImporteRequest.getCodigoSenial())
                .addValue("P_MONTO", senialImporteRequest.getImporte())
                .addValue("P_USUARIO", senialImporteRequest.getUsuario());
            out = this.grabarSenialCall.execute(in);
            if (((String) out.get("P_OK")).equals("S")) {
                ret = null;
            } else {
                ret.setTipo(TIPO_ERROR);
                ret.setDescripcion(NO_SE_PUDO_DAR_DE_ALTA_LA_SENIAL);
            }
        } else {
            ret.setTipo(TIPO_ERROR);
            ret.setDescripcion((String) out.get("P_MSJ_ERROR"));
        }
        return ret;
    }

    @Override
    public void modificarSenial(SenialImporte senialImporteRequest) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", senialImporteRequest.getContrato())
            .addValue("P_SENIAL", senialImporteRequest.getCodigoSenial())
            .addValue("P_MONTO", senialImporteRequest.getImporte())
            .addValue("P_USUARIO", senialImporteRequest.getUsuario())
            .addValue("P_FUN", "M");
        this.modificarSenialCall.execute(in);
    }

    @Override
    public Boolean actualizarContrato(ContratoValidationRequest contratoCabeceraValidationRequest)  throws ParseException {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", contratoCabeceraValidationRequest.getContrato())
            .addValue("P_MONEDA", contratoCabeceraValidationRequest.getMoneda())
            .addValue("P_USUARIO", contratoCabeceraValidationRequest.getUsuario())
            .addValue("P_CANJE", contratoCabeceraValidationRequest.getTipoContrato());
        Map<String, Object> out = this.actualizarContratoCall.execute(in);
        return ((String) out.get("P_RESULTADO")).equals("S");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ContratoValidationResult> updateContratoMontoSenial(SenialImporte senialImporteRequest) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", senialImporteRequest.getContrato())
            .addValue("P_SENIAL", senialImporteRequest.getCodigoSenial())
            .addValue("P_USUARIO", senialImporteRequest.getUsuario());
        Map<String, Object> out = this.updateContratoMontoSenialCall.execute(in);
        return (List<ContratoValidationResult>) out.get("P_ERRORES");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ContratoValidationResult> eliminarSenial(SenialImporte senialImporteRequest) {
        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", senialImporteRequest.getContrato())
            .addValue("P_SENIAL", senialImporteRequest.getCodigoSenial());
        Map<String, Object> out = this.validarEliminarSenialCall.execute(in);
        ret = ((List<ContratoValidationResult>) out.get("P_ERRORES"));
        if (ret.isEmpty()) {
            in = new MapSqlParameterSource()
                .addValue("P_CONTRATO", senialImporteRequest.getContrato())
                .addValue("P_SENIAL", senialImporteRequest.getCodigoSenial())
                .addValue("P_USUARIO", senialImporteRequest.getUsuario());
            out = this.eliminarSenialCall.execute(in);
            if (((String) out.get("P_OK")).equals("S")) {
                return ret;
            } else {
                ContratoValidationResult eliminarSenialResult = new ContratoValidationResult();
                eliminarSenialResult.setTipo(TIPO_ERROR);
                eliminarSenialResult.setDescripcion(OCURRIO_UN_ERROR_DURANTE_LA_ELIMINACION_DE_LA_SENIAL);
                ret.add(eliminarSenialResult);
                return ret;
            }
        } else {
            return ret;
        }
    }

    @Override
    public List<ContratoValidationResult> altaGrupoSinCap(GrupoRequest grupoRequest, String usuario) {
        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();
        SqlParameterSource in = new MapSqlParameterSource()
                            .addValue("P_CONTRATO", grupoRequest.getContrato())
                            .addValue("P_GRUPO", grupoRequest.getNroGrupo())
                            .addValue("P_SENIAL", grupoRequest.getSenial())
                            .addValue("P_TIPO_TITULO", grupoRequest.getTipoTitulo())
                            .addValue("P_SENIALES_HER", grupoRequest.getSenialHeredada())
                            .addValue("P_NOM_GRUPO", grupoRequest.getNombreGrupo())
                            .addValue("P_CANT_TIT", grupoRequest.getCantTitulos())
                            .addValue("P_CANT_PASADAS", grupoRequest.getCantPasadas())
                            .addValue("P_TIPO_IMPORTE", grupoRequest.getTipoImporte())
                            .addValue("P_IMPORTE_UNI", grupoRequest.getCostoTotal())
                            .addValue("P_EST_REP", grupoRequest.getEr())
                            .addValue("P_FEC_COM_DER", toDate(grupoRequest.getFechaDerechos()))
                            .addValue("P_TIPO_DER", grupoRequest.getCodigoDerechos())
                            .addValue("P_CANT_TIEMPO", grupoRequest.getCantTiempo())
                            .addValue("P_UNI_TIEMPO", grupoRequest.getUnidadDeTiempo())
                            .addValue("P_DER_TER", grupoRequest.getCodigoDerechosTerritoriales())
                            .addValue("P_MAR_PER", grupoRequest.getMarcaPerpetuidad())
                            .addValue("P_PLAN_ENTREGA", grupoRequest.getPlanEntrega())
                            .addValue("P_CANT_TIE_EXCL", grupoRequest.getCantTiempoExclusivo())
                            .addValue("P_UNI_TIE_EXCL", grupoRequest.getUnidadTiempoExclusivo())
                            .addValue("P_UNI_TIEMPO", grupoRequest.getUnidadDeTiempo())
                            .addValue("P_AUT_AUT", grupoRequest.getAutorizacionAutor())
                            .addValue("P_PAGA_ARGENT", grupoRequest.getPagaArgentores())
                            .addValue("P_PASA_LIB", grupoRequest.getPasaLibreria())
                            .addValue("P_FEC_COM_DER_LIB", toDate(grupoRequest.getFechaComienzoDerechosLibreria()))
                            
                            .addValue("P_MODO", grupoRequest.getModo())
                            .addValue("P_NRO_REL", grupoRequest.getNroRelacionante())

                            .addValue("P_USUARIO", usuario);
        Map<String, Object> out = this.altaGrupoSinCapCall.execute(in);
        if (((String) out.get("P_OK")).equals("S")) {
            return ret;
        } else {
            ContratoValidationResult altaGrupoResult = new ContratoValidationResult();
            altaGrupoResult.setTipo(TIPO_ERROR);
            altaGrupoResult.setDescripcion(OCURRIO_UN_ERROR_DURANTE_EL_ALTA_DE_UN_GRUPO_SIN_CAP);
            ret.add(altaGrupoResult);
            return ret;
        }
    }
    
    @Override
    public List<ContratoValidationResult> altaGrupoConCap(GrupoRequest grupoRequest, String usuario) {
        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();
        MapSqlParameterSource in = new MapSqlParameterSource()
                            .addValue("P_CONTRATO", grupoRequest.getContrato())
                            .addValue("P_GRUPO", grupoRequest.getNroGrupo())
                            .addValue("P_SENIAL", grupoRequest.getSenial())
                            .addValue("P_TIPO_TITULO", grupoRequest.getTipoTitulo())
                            .addValue("P_SENIALES_HER", grupoRequest.getSenialHeredada())
                            .addValue("P_NOM_GRUPO", grupoRequest.getNombreGrupo())
                            
                            .addValue("P_CANT_ORIG", grupoRequest.getCantTitulos())
                            .addValue("P_CANT_REP", grupoRequest.getCantPasadas())
                            .addValue("P_TIPO_IMPORTE", grupoRequest.getTipoImporte())
                            .addValue("P_IMPORTE_GRUPO", grupoRequest.getCostoTotal())
                            .addValue("P_EST_REP", grupoRequest.getEr())
                            .addValue("P_FEC_COM_DER", toDate(grupoRequest.getFechaDerechos()))
                            .addValue("P_FEC_POS_ENTR", toDate(grupoRequest.getFechaEntrega()))
                            
                            .addValue("P_TIPO_DER", grupoRequest.getCodigoDerechos())
                            .addValue("P_CANT_TIEMPO", grupoRequest.getCantTiempo())
                            .addValue("P_UNI_TIEMPO", grupoRequest.getUnidadDeTiempo())
                            .addValue("P_DER_TER", grupoRequest.getCodigoDerechosTerritoriales())
                            .addValue("P_MAR_PER", grupoRequest.getMarcaPerpetuidad())
                            .addValue("P_PLAN_ENTREGA", grupoRequest.getPlanEntrega())
                            .addValue("P_MAR_RECONT", grupoRequest.getRecontratacion())
                            .addValue("P_CANT_TIE_EXCL", grupoRequest.getCantTiempoExclusivo())

                            .addValue("P_UNI_TIE_EXCL", grupoRequest.getUnidadTiempoExclusivo())
                            .addValue("P_AUT_AUT", grupoRequest.getAutorizacionAutor())
                            .addValue("P_PAGA_ARGENT", grupoRequest.getPagaArgentores())
                            .addValue("P_PASA_LIB", grupoRequest.getPasaLibreria())
                            .addValue("P_FEC_COM_DER_LIB", toDate(grupoRequest.getFechaComienzoDerechosLibreria()))
                            
                            .addValue("P_MODO", grupoRequest.getModo())
                            .addValue("P_NRO_REL", grupoRequest.getNroRelacionante())
                            
                            .addValue("P_USUARIO", usuario);
        Map<String, Object> out = this.altaGrupoConCapCall.execute(in);
        if (((String) out.get("P_OK")).equals("S")) {
            return ret;
        } else {
            ContratoValidationResult altaGrupoResult = new ContratoValidationResult();
            altaGrupoResult.setTipo(TIPO_ERROR);
            altaGrupoResult.setDescripcion(OCURRIO_UN_ERROR_DURANTE_EL_ALTA_DE_UN_GRUPO_SIN_CAP);
            ret.add(altaGrupoResult);
            return ret;
        }
    }

    @Override
    public List<ContratoValidationResult> altaTituloContratado(ValidarAltaTituloRequest validarAltaTituloRequest, String usuario) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", validarAltaTituloRequest.getContrato())
            .addValue("P_GRUPO", validarAltaTituloRequest.getGrupo())
            .addValue("P_SENIAL", validarAltaTituloRequest.getSenial())
            .addValue("P_CLAVE", validarAltaTituloRequest.getClave())
            .addValue("P_ORIGEN", validarAltaTituloRequest.getOrigen())
            .addValue("P_SIN_CONT", validarAltaTituloRequest.getSinContrato())
            .addValue("P_ACTOR_1", validarAltaTituloRequest.getActores1())
            .addValue("P_ACTOR_2", validarAltaTituloRequest.getActores2())
            .addValue("P_OBSERVACIONES", validarAltaTituloRequest.getObservaciones())
            .addValue("P_TIT_ORI", validarAltaTituloRequest.getTituloOriginal())
            .addValue("P_TIT_CAS", validarAltaTituloRequest.getTituloCastellano())
            .addValue("P_ID_FICHA", validarAltaTituloRequest.getIdFicha())
            .addValue("P_MARCA_RECONT", validarAltaTituloRequest.getRecontratacion())
            .addValue("P_CALIF_ART", validarAltaTituloRequest.getCalificacion())
            .addValue("P_RESPUESTA", validarAltaTituloRequest.getRespuesta())
            .addValue("P_USUARIO", usuario)
            .addValue("P_FEC_PROCESO_TG", validarAltaTituloRequest.getFechaProcesoTG())
            .addValue("P_HORA_PROCESO_TG", validarAltaTituloRequest.getHoraProcesoTG())
            .addValue("P_MODO", validarAltaTituloRequest.getModo())
            .addValue("P_NRO_REL", validarAltaTituloRequest.getNroRelacionante());
        Map<String, Object> out = this.altaTituloContratadoCall.execute(in);
        
        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();

        String reRun = (String) out.get("P_RERUN");
        ContratoValidationResult foo = new ContratoValidationResult();
        foo.setTipo(TIPO_RERUN);
        foo.setDescripcion(reRun);
        ret.add(foo);
        
        String modoReRun = (String) out.get("P_MODO_RERUN");
        foo = new ContratoValidationResult();
        foo.setTipo(TIPO_MODO_RERUN);
        foo.setDescripcion(modoReRun);
        ret.add(foo);

        String procesoReRun = (String) out.get("P_PROCESO_RERUN");
        foo = new ContratoValidationResult();
        foo.setTipo(TIPO_PROCESO_RERUN);
        foo.setDescripcion(procesoReRun);
        ret.add(foo);
        
        return ret;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public List<ContratoValidationResult> altaTituloContratadoCC(Map data, String usuario) {
        
        Long contrato = (Long) data.get("contrato");
        Long grupo = (Long) data.get("grupo");
        String senial = (String)data.get("senial");
        String clave = (String)data.get("clave");
        List capitulosPartes = (List)data.get("capitulosPartes");

        MapSqlParameterSource in = new MapSqlParameterSource()
                                    .addValue("P_CONTRATO", contrato)
                                    .addValue("P_GRUPO", grupo)
                                    .addValue("P_USUARIO", usuario)
                                    .addValue("P_SENIAL", senial);
        deleteCapituloParteCall.execute(in);
        
        for (Object object : capitulosPartes) {
            in.addValue("P_CONTRATO", contrato)
              .addValue("P_GRUPO", grupo)
              .addValue("P_SENIAL", senial)
              .addValue("P_CLAVE", (String)((Map)object).get("clave"))
              .addValue("P_CAPITULO", (Long)((Map)object).get("capitulo"))
              .addValue("P_PARTE", (Long)((Map)object).get("parte"));
            insertarCapituloParteCall.execute(in);
        }

        in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", contrato)
            .addValue("P_GRUPO", grupo)
            .addValue("P_SENIAL", senial)
            .addValue("P_CLAVE", clave)
            .addValue("P_USUARIO", usuario)
            //FIXME
            .addValue("P_FEC_PROC", null)
            .addValue("P_HOR_PROC", 1);
        Map<String, Object> out = this.altaTituloContratadoCCCall.execute(in);

        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();

        String reRun = (String) out.get("P_RERUN");
        ContratoValidationResult foo = new ContratoValidationResult();
        foo.setTipo(TIPO_RERUN);
        foo.setDescripcion(reRun);
        ret.add(foo);
        
        String modoReRun = (String) out.get("P_MODO_RERUN");
        foo = new ContratoValidationResult();
        foo.setTipo(TIPO_MODO_RERUN);
        foo.setDescripcion(modoReRun);
        ret.add(foo);

        String procesoReRun = (String) out.get("P_PROCESO_RERUN");
        foo = new ContratoValidationResult();
        foo.setTipo(TIPO_PROCESO_RERUN);
        foo.setDescripcion(procesoReRun);
        ret.add(foo);
        
        in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", contrato)
            .addValue("P_GRUPO", grupo)
            .addValue("P_USUARIO", usuario)
            .addValue("P_SENIAL", senial);
        deleteCapituloParteCall.execute(in);
        
        return ret;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public List<ContratoValidationResult> altaTituloContratadoCCTodos(Map data, String usuario) {
        Integer contrato = new Integer(((String)data.get("contrato")).trim());
        Integer grupo = new Integer(((String)data.get("grupo")).trim());
        String senial = (String)data.get("senial");
        String clave = (String)data.get("clave");
        Long contratoMod = (((Long)data.get("contratoMod")));
        Long grupoMod = (((Long)data.get("grupoMod")));
        String senialMod = ((String)data.get("senialMod")).trim();
        
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", contrato)
            .addValue("P_GRUPO", grupo)
            .addValue("P_SENIAL", senial)
            .addValue("P_CLAVE", clave)
            .addValue("P_USUARIO", usuario)
            .addValue("P_CONTRATO_MOD", contratoMod)
            .addValue("P_GRUPO_MOD", grupoMod)
            .addValue("P_SENIAL_MOD", senialMod)
            //FIXME
            .addValue("P_FEC_PROC", null)
            .addValue("P_HOR_PROC", 1);
        Map<String, Object> out = this.altaTituloContratadoCCTodosCall.execute(in);

        
        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();

        String reRun = (String) out.get("P_RERUN");
        ContratoValidationResult foo = new ContratoValidationResult();
        foo.setTipo(TIPO_RERUN);
        foo.setDescripcion(reRun);
        ret.add(foo);
        
        String modoReRun = (String) out.get("P_MODO_RERUN");
        foo = new ContratoValidationResult();
        foo.setTipo(TIPO_MODO_RERUN);
        foo.setDescripcion(modoReRun);
        ret.add(foo);   

        String procesoReRun = (String) out.get("P_PROCESO_RERUN");
        foo = new ContratoValidationResult();
        foo.setTipo(TIPO_PROCESO_RERUN);
        foo.setDescripcion(procesoReRun);
        ret.add(foo);
        
        return ret;
    }
    
    @Override
    public Boolean actualizarEnlaceAnterior(RerunRequest rerunRequest, String usuario) throws ParseException {
        SqlParameterSource in = new MapSqlParameterSource()
          .addValue("P_CONTRATO", rerunRequest.getContrato())
          .addValue("P_GRUPO", rerunRequest.getGrupo())
          .addValue("P_SENIAL", rerunRequest.getSenial())
          .addValue("P_CLAVE", rerunRequest.getClave())
          .addValue("P_RERUN_CONTRATO", rerunRequest.getRerunContrato())
          .addValue("P_RERUN_GRUPO", rerunRequest.getRerunGrupo())
          .addValue("P_RERUN_ENL_ANT", rerunRequest.getRerunEnlaceAnterior())
          .addValue("P_RERUN_ENL_POS", rerunRequest.getRerunEnlacePosterior())
          .addValue("P_RERUN_GRUPO_ANT", rerunRequest.getRerunGrupoAnterior())
          .addValue("P_RERUN_GRUPO_POS", rerunRequest.getRerunGrupoPosterior())
          .addValue("P_RERUN_VIG_DESDE", new SimpleDateFormat("dd/MM/yyyy").parse(rerunRequest.getRerunVigenciaDesde()))
          .addValue("P_RERUN_VIG_HASTA", new SimpleDateFormat("dd/MM/yyyy").parse(rerunRequest.getRerunVigenciaHasta()))
          .addValue("P_PROCESO", rerunRequest.getProceso());
        this.actualizarEnlaceAnteriorCall.execute(in);
        return Boolean.TRUE;
    }
    
    @Override
    public Boolean actualizarEnlacePosterior(RerunRequest rerunRequest, String usuario) throws ParseException {
        SqlParameterSource in = new MapSqlParameterSource()
          .addValue("P_CONTRATO", rerunRequest.getContrato())
          .addValue("P_GRUPO", rerunRequest.getGrupo())
          .addValue("P_SENIAL", rerunRequest.getSenial())
          .addValue("P_CLAVE", rerunRequest.getClave())
          .addValue("P_RERUN_CONTRATO", rerunRequest.getRerunContrato())
          .addValue("P_RERUN_GRUPO", rerunRequest.getRerunGrupo())
          .addValue("P_RERUN_ENL_ANT", rerunRequest.getRerunEnlaceAnterior())
          .addValue("P_RERUN_ENL_POS", rerunRequest.getRerunEnlacePosterior())
          .addValue("P_RERUN_GRUPO_ANT", rerunRequest.getRerunGrupoAnterior())
          .addValue("P_RERUN_GRUPO_POS", rerunRequest.getRerunGrupoPosterior())
          .addValue("P_RERUN_VIG_DESDE", new SimpleDateFormat("dd/MM/yyyy").parse(rerunRequest.getRerunVigenciaDesde()))
          .addValue("P_RERUN_VIG_HASTA", new SimpleDateFormat("dd/MM/yyyy").parse(rerunRequest.getRerunVigenciaHasta()))
          .addValue("P_PROCESO", rerunRequest.getProceso());
        this.actualizarEnlacePosteriorCall.execute(in);
        return Boolean.TRUE;
    }
    
    @Override
    public Boolean actualizarRerunDesenlace(RerunRequest rerunRequest, String usuario) {
        SqlParameterSource in = new MapSqlParameterSource()
          .addValue("P_CONTRATO", rerunRequest.getContrato())
          .addValue("P_GRUPO", rerunRequest.getGrupo())
          .addValue("P_SENIAL", rerunRequest.getSenial())
          .addValue("P_CLAVE", rerunRequest.getClave());
        this.actualizarRerunDesenlaceCall.execute(in);
        return Boolean.TRUE;
    }
    
    public List<ContratoValidationResult> modificacionDeGrupoSinCap(GrupoRequest grupoRequest, String usuario) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", grupoRequest.getContrato())
            .addValue("P_GRUPO", grupoRequest.getNroGrupo())
            .addValue("P_SENIAL", grupoRequest.getSenial())
            .addValue("P_SENIAL_HER", grupoRequest.getSenialHeredada())
            .addValue("P_IMP_UNI_MOD", grupoRequest.getCostoTotal())
            .addValue("P_CANT_PASADAS_MOD", grupoRequest.getCantPasadas())
            .addValue("P_FEC_COM_DER_MOD", toDate(grupoRequest.getFechaDerechos()))
            .addValue("P_TIPO_DER_MOD", grupoRequest.getCodigoDerechos())
            .addValue("P_CANT_TIE_MOD", grupoRequest.getCantTiempo())
            .addValue("P_UNI_TIE_MOD", grupoRequest.getUnidadDeTiempo())
            .addValue("P_DER_TER_MOD", grupoRequest.getCodigoDerechosTerritoriales())
            .addValue("P_NOMBRE_GRUPO_MOD", grupoRequest.getNombreGrupo())
            .addValue("P_MAR_PER_MOD", grupoRequest.getMarcaPerpetuidad())
            .addValue("P_PAS_LIB_MOD", grupoRequest.getPasaLibreria())
            .addValue("P_FEC_COM_LIB_MOD", toDate(grupoRequest.getFechaComienzoDerechosLibreria()))
            .addValue("P_TIPO_IMPORTE_MOD", grupoRequest.getTipoImporte())
            .addValue("P_AUT_AUT", grupoRequest.getAutorizacionAutor())
            .addValue("P_PAG_ARG", grupoRequest.getPagaArgentores())
            
            .addValue("P_MODO", grupoRequest.getModo())
            .addValue("P_USUARIO", usuario)
            .addValue("P_FEC_PROC", toDate(grupoRequest.getFechaProceso()))
            .addValue("P_HOR_PROC", grupoRequest.getHoraProceso());

        Map<String, Object> out = this.modificacionDeGrupoSinCap.execute(in);

        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();

        String reRun = (String) out.get("P_FLAG_RERUN");
        ContratoValidationResult foo = new ContratoValidationResult();
        foo.setTipo(TIPO_RERUN);
        foo.setDescripcion(reRun);
        ret.add(foo);
      
        String titulosRerun = (String) out.get("P_FLAG_TITULOS_RERUN");
        foo = new ContratoValidationResult();
        foo.setTipo(TIPO_TITULOS_RERUN);
        foo.setDescripcion(titulosRerun);
        ret.add(foo);

        String modoReRun = (String) out.get("P_FLAG_MODO_RERUN");
        foo = new ContratoValidationResult();
        foo.setTipo(TIPO_MODO_RERUN);
        foo.setDescripcion(modoReRun);
        ret.add(foo);

        String procesoReRun = (String) out.get("P_FLAG_PROC_RERUN");
        foo = new ContratoValidationResult();
        foo.setTipo(TIPO_PROCESO_RERUN);
        foo.setDescripcion(procesoReRun);
        ret.add(foo);
      
        String ok = (String) out.get("P_OK");
        foo = new ContratoValidationResult();
        foo.setTipo(TIPO_OK);
        foo.setDescripcion(ok);
        ret.add(foo);

        return ret;
    }
    
    public List<ContratoValidationResult> modificacionDeGrupoConCap(GrupoRequest grupoRequest, String usuario) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_CONTRATO", grupoRequest.getContrato())
                .addValue("P_GRUPO", grupoRequest.getNroGrupo())
                .addValue("P_SENIAL", grupoRequest.getSenial())
                .addValue("P_SENIAL_HER", grupoRequest.getSenialHeredada())
                .addValue("P_FEC_COM_DER_MOD", toDate(grupoRequest.getFechaDerechos()))
                .addValue("P_TIPO_DER_MOD", grupoRequest.getCodigoDerechos())
                .addValue("P_CANT_TIE_MOD", grupoRequest.getCantTiempo())
                .addValue("P_UNI_TIE_MOD", grupoRequest.getUnidadDeTiempo())
                .addValue("P_IMP_GRUPO_MOD", grupoRequest.getCostoTotal())
                .addValue("P_DER_TER_MOD", grupoRequest.getCodigoDerechosTerritoriales())
                .addValue("P_MAR_PER_MOD", grupoRequest.getMarcaPerpetuidad())
                .addValue("P_CANT_EXCL_MOD", grupoRequest.getCantTiempoExclusivo())
                .addValue("P_UNI_EXCL_MOD", grupoRequest.getUnidadTiempoExclusivo())
                .addValue("P_AUT_AUT_MOD", grupoRequest.getAutorizacionAutor())
                .addValue("P_PAGA_ARG_MOD", grupoRequest.getPagaArgentores())
                .addValue("P_PAS_LIB_MOD", grupoRequest.getPasaLibreria())
                .addValue("P_FEC_COM_LIB_MOD", toDate(grupoRequest.getFechaComienzoDerechosLibreria()))
                .addValue("P_NOM_GRUPO_MOD", grupoRequest.getNombreGrupo())
                .addValue("P_TIPO_IMPORTE_MOD", grupoRequest.getTipoImporte())
                .addValue("P_RECONT", grupoRequest.getRecontratacion())
                .addValue("P_EST_REP_MOD", grupoRequest.getEr())
                .addValue("P_PLAN_ENTREGA_MOD", grupoRequest.getPlanEntrega())
                .addValue("P_CANT_ORI_MOD", grupoRequest.getCantTitulos())
                .addValue("P_CANT_REP_MOD", grupoRequest.getCantPasadas())
                .addValue("P_MODO", grupoRequest.getModo())
                .addValue("P_USUARIO", usuario);

        Map<String, Object> out = this.modificacionDeGrupoConCap.execute(in);

        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();

        String reRun = (String) out.get("P_FLAG_RERUN");
        ContratoValidationResult foo = new ContratoValidationResult();
        foo.setTipo(TIPO_RERUN);
        foo.setDescripcion(reRun);
        ret.add(foo);
        
        String modoReRun = (String) out.get("P_FLAG_MODO_RERUN");
        foo = new ContratoValidationResult();
        foo.setTipo(TIPO_MODO_RERUN);
        foo.setDescripcion(modoReRun);
        ret.add(foo);   

        String procesoReRun = (String) out.get("P_FLAG_PROC_RERUN");
        foo = new ContratoValidationResult();
        foo.setTipo(TIPO_PROCESO_RERUN);
        foo.setDescripcion(procesoReRun);
        ret.add(foo);
        
        String recontratacion = (String) out.get("P_FLAG_RECONT");
        foo = new ContratoValidationResult();
        foo.setTipo(TIPO_RECONTRATACION);
        foo.setDescripcion(recontratacion);
        ret.add(foo);

        String titulosRerun = (String) out.get("P_FLAG_TITULOS_RERUN");
        foo = new ContratoValidationResult();
        foo.setTipo(TIPO_TITULOS_RERUN);
        foo.setDescripcion(titulosRerun);
        ret.add(foo);

        String clave = (String) out.get("P_CLAVE");
        foo = new ContratoValidationResult();
        foo.setTipo(TIPO_DATA_CLAVE);
        foo.setDescripcion(clave);
        ret.add(foo);
        
        String ok = (String) out.get("P_OK");
        foo = new ContratoValidationResult();
        foo.setTipo(TIPO_OK);
        foo.setDescripcion(ok);
        ret.add(foo);

        return ret;
    }

    @Override
    public List<ContratoValidationResult> modificacionDeCantidadGrupoSinCap(GrupoRequest grupoRequest, String usuario) {
        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();
        SqlParameterSource in = new MapSqlParameterSource()
                                            .addValue("P_CONTRATO", grupoRequest.getContrato())
                                            .addValue("P_GRUPO", grupoRequest.getNroGrupo())
                                            .addValue("P_SENIAL", grupoRequest.getSenial())
                                            .addValue("P_USUARIO", usuario)
                                            .addValue("P_CANT_TIT", grupoRequest.getCantTitulos());
        Map<String, Object> out = this.modificacionDeCantidadGrupoSinCap.execute(in);
        if (((String) out.get("P_OK")).equals("S")) {
            String ok = (String) out.get("P_OK");
            ContratoValidationResult foo = new ContratoValidationResult();
            foo.setTipo(TIPO_OK);
            foo.setDescripcion(ok);
            ret.add(foo);
        } else {
            ContratoValidationResult result = new ContratoValidationResult();
            result.setTipo(TIPO_ERROR);
            result.setDescripcion(OCURRIO_UN_ERROR_DURANTE_MODIF_GRUPO);
            ret.add(result);
        }
        return ret;
    }

    @Override
    public List<ContratoValidationResult> modificacionDeCantidadGrupoConCap(GrupoRequest grupoRequest, String usuario) {
        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();
        SqlParameterSource in = new MapSqlParameterSource()
                            .addValue("P_CONTRATO", grupoRequest.getContrato())
                            .addValue("P_GRUPO", grupoRequest.getNroGrupo())
                            .addValue("P_SENIAL", grupoRequest.getSenial())
                            .addValue("P_USUARIO", usuario)
                            .addValue("P_CANT_PASADAS", grupoRequest.getCantPasadas())
                            .addValue("P_CANT_ORIG", grupoRequest.getCantTitulos());
        Map<String, Object> out = this.modificacionDeCantidadGrupoConCap.execute(in);
        
        String ok = (String) out.get("P_OK");
        ContratoValidationResult foo = new ContratoValidationResult();
        foo.setTipo(TIPO_OK);
        foo.setDescripcion(ok);
        ret.add(foo);

        ContratoValidationResult result;
        String hayCapsABorrar = ((String) out.get("P_HAY_CAPS_A_BORRAR"));
        result = new ContratoValidationResult();
        result.setTipo(TIPO_DATA_HAY_CAPS_A_BORRAR);
        result.setDescripcion(hayCapsABorrar);
        ret.add(result);

        String hayCapsAAgregar = ((String) out.get("P_HAY_CAPS_A_AGREGAR"));
        result = new ContratoValidationResult();
        result.setTipo(TIPO_DATA_HAY_CAPS_A_AGREGAR);
        result.setDescripcion(hayCapsAAgregar);
        ret.add(result);

        Integer capitulosAfectados = ((BigDecimal) out.get("P_CAPS_AFECTADOS")).intValue();
        result = new ContratoValidationResult();
        result.setTipo(TIPO_DATA_CAPS_AFECTADOS);
        result.setNroAdvertencia(capitulosAfectados);
        ret.add(result);
        
        return ret;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<ContratoValidationResult> capitulosAAgregar(Map data, String usuario) {
        Long contrato = (Long) data.get("contrato");
        Long grupo = (Long) data.get("grupo");
        String senial = (String) data.get("senial");
        String fechaProceso = (String) data.get("fechaProceso");
        Long horaProceso = (Long) data.get("horaProceso");
        String clave = (String) data.get("clave");
        
        MapSqlParameterSource in = new MapSqlParameterSource()
                                    .addValue("P_CONTRATO", contrato)
                                    .addValue("P_GRUPO", grupo)
                                    .addValue("P_USUARIO", usuario)
                                    .addValue("P_SENIAL", senial);
        deleteCapituloParteCall.execute(in);
        
        List capitulosPartes = (List) data.get("capitulosPartes");
        for (Object object : capitulosPartes) {
            in.addValue("P_CONTRATO", contrato)
              .addValue("P_GRUPO", grupo)
              .addValue("P_SENIAL", senial)
              .addValue("P_USUARIO", usuario)
              .addValue("P_CLAVE", (String)((Map)object).get("clave"))
              .addValue("P_CAPITULO", (Long)((Map)object).get("capitulo"))
              .addValue("P_PARTE", (Long)((Map)object).get("parte"));
            insertarCapituloParteCall.execute(in);
        }

        in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", contrato)
            .addValue("P_GRUPO", grupo)
            .addValue("P_SENIAL", senial)
            .addValue("P_CLAVE", clave)
            .addValue("P_FEC_PROC", fechaProceso)
            .addValue("P_HORA_PROC", horaProceso)
            .addValue("P_USUARIO", usuario);
        this.capitulosAAgregar.execute(in);

        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();

        in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", contrato)
            .addValue("P_GRUPO", grupo)
            .addValue("P_USUARIO", usuario)
            .addValue("P_SENIAL", senial);
        deleteCapituloParteCall.execute(in);

        return ret;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<ContratoValidationResult> capitulosABorrar(Map data, String usuario) {
        Long contrato = (Long) data.get("contrato");
        Long grupo = (Long) data.get("grupo");
        String senial = (String)data.get("senial");
        List capitulosPartes = (List)data.get("capitulosPartes");
        String clave = null;
        if (capitulosPartes.size() > 0) {
            clave =  (String)(((Map)capitulosPartes.get(0)).get("clave"));
        }
        MapSqlParameterSource in = new MapSqlParameterSource()
                                    .addValue("P_CONTRATO", contrato)
                                    .addValue("P_GRUPO", grupo)
                                    .addValue("P_USUARIO", usuario)
                                    .addValue("P_SENIAL", senial);
        deleteCapituloParteCall.execute(in);
        
        for (Object object : capitulosPartes) {
            in.addValue("P_CONTRATO", contrato)
              .addValue("P_GRUPO", grupo)
              .addValue("P_SENIAL", senial)
              .addValue("P_USUARIO", usuario)
              .addValue("P_CLAVE", (String)((Map)object).get("clave"))
              .addValue("P_CAPITULO", (Long)((Map)object).get("capitulo"))
              .addValue("P_PARTE", (Long)((Map)object).get("parte"));
            insertarCapituloParteCall.execute(in);
        }

        in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", contrato)
            .addValue("P_GRUPO", grupo)
            .addValue("P_SENIAL", senial)
            .addValue("P_CLAVE", clave)
            .addValue("P_USUARIO", usuario);
        this.capitulosABorrar.execute(in);

        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();

        in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", contrato)
            .addValue("P_GRUPO", grupo)
            .addValue("P_USUARIO", usuario)
            .addValue("P_SENIAL", senial);
        deleteCapituloParteCall.execute(in);

        return ret;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<ContratoValidationResult> titulosABorrar(Map data, String usuario) {
        Long contrato = ((Long)data.get("contrato"));
        Long grupo = (Long) data.get("grupo");
        String senial = (String)data.get("senial");
        List titulos = (List)data.get("titulos");

        MapSqlParameterSource in = new MapSqlParameterSource()
                                    .addValue("P_CONTRATO", contrato)
                                    .addValue("P_GRUPO", grupo)
                                    .addValue("P_USUARIO", usuario)
                                    .addValue("P_SENIAL", senial);
        deleteCapituloParteCall.execute(in);

        for (Object object : titulos) {
            in.addValue("P_USUARIO", usuario)
              .addValue("P_CONTRATO", contrato)
              .addValue("P_GRUPO", grupo)
              .addValue("P_SENIAL", senial)
              .addValue("P_CAPITULO", null)
              .addValue("P_PARTE", null)
              .addValue("P_CLAVE", (String)((Map)object).get("clave"));
            insertarCapituloParteCall.execute(in);
        }

        in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", contrato)
            .addValue("P_GRUPO", grupo)
            .addValue("P_SENIAL", senial)
            .addValue("P_USUARIO", usuario);
        Map<String, Object> out = this.titulosABorrar.execute(in);

        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();
        String ok = (String) out.get("P_OK");
        ContratoValidationResult foo = new ContratoValidationResult();
        foo.setTipo(TIPO_OK);
        foo.setDescripcion(ok);
        ret.add(foo);

        in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", contrato)
            .addValue("P_GRUPO", grupo)
            .addValue("P_USUARIO", usuario)
            .addValue("P_SENIAL", senial);
        deleteCapituloParteCall.execute(in);

        return ret;
    }

    @Override
    public List<ContratoValidationResult> desmarcarTitulo(TituloRequest tituloRequest, String usuario) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                    .addValue("P_USUARIO", usuario)
                    .addValue("P_CONTRATO", tituloRequest.getContrato())
                    .addValue("P_GRUPO", tituloRequest.getGrupo())
                    .addValue("P_CLAVE", tituloRequest.getClave())
                    .addValue("P_FEC_PROC", null)
                    .addValue("P_HOR_PROC", null)
                    .addValue("P_SENIAL", tituloRequest.getSenial());

        Map<String, Object> out = this.desmarcarTitulo.execute(in);
        
        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();
        String success = (String) out.get("P_OK");
        ContratoValidationResult contratoValidationResult;
        contratoValidationResult = new ContratoValidationResult();
        contratoValidationResult.setTipo(TIPO_OK);
        contratoValidationResult.setDescripcion(success);
        ret.add(contratoValidationResult);
        
        return ret;
    }

    @Override
    public List<ContratoValidationResult> procesarPayTV(VigenciaRequest vigenciaRequest, String usuario) throws ParseException {
        MapSqlParameterSource in = new MapSqlParameterSource()
        .addValue("P_CONTRATO", vigenciaRequest.getContrato())
        .addValue("P_GRUPO", vigenciaRequest.getGrupo())
        .addValue("P_SENIAL", vigenciaRequest.getSenial())
        .addValue("P_CLAVE", vigenciaRequest.getClave())
        .addValue("P_PAYTV", vigenciaRequest.getPayTVId())
        .addValue("P_FEC_HASTA_PAYTV", new SimpleDateFormat("dd/MM/yyyy").parse(vigenciaRequest.getFechaHastaPayTV()))
        .addValue("P_FEC_DESDE_PAYTV", new SimpleDateFormat("dd/MM/yyyy").parse(vigenciaRequest.getFechaDesdePayTV()))
        .addValue("P_OBSERVACIONES", vigenciaRequest.getObservaciones())
        .addValue("P_MODO", vigenciaRequest.getModo())
        .addValue("P_USUARIO", usuario);

        Map<String, Object> out = this.procesarPayTV.execute(in);
        
        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();
        String ok = (String) out.get("P_OK");
        ContratoValidationResult foo = new ContratoValidationResult();
        foo.setTipo("P_OK");
        foo.setDescripcion(ok);
        ret.add(foo);
        
        return ret;
    }

    @Override
    public List<ContratoValidationResult> ampliarDerechos(VigenciaRequest vigenciaRequest, String usuario) throws ParseException {
        MapSqlParameterSource in = new MapSqlParameterSource()
        .addValue("P_CONTRATO", vigenciaRequest.getContrato())
        .addValue("P_GRUPO", vigenciaRequest.getGrupo())
        .addValue("P_SENIAL", vigenciaRequest.getSenial())
        .addValue("P_CLAVE", vigenciaRequest.getClave())
        .addValue("P_FEC_HASTA_PAYTV", new SimpleDateFormat("dd/MM/yyyy").parse(vigenciaRequest.getFechaHastaPayTV()))
        .addValue("P_OBSERVACIONES", vigenciaRequest.getObservaciones())
        .addValue("P_USUARIO", usuario);

        this.ampliarDerechos.execute(in);
        
        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();
        ContratoValidationResult foo = new ContratoValidationResult();
        foo.setTipo("P_OK");
        foo.setDescripcion("S");
        ret.add(foo);
        
        return ret;
    }

    @Override
    public List<ContratoValidationResult> adelantarVencimiento(VigenciaRequest vigenciaRequest, String usuario) throws ParseException {
        MapSqlParameterSource in = new MapSqlParameterSource()
        .addValue("P_CONTRATO", vigenciaRequest.getContrato())
        .addValue("P_GRUPO", vigenciaRequest.getGrupo())
        .addValue("P_SENIAL", vigenciaRequest.getSenial())
        .addValue("P_CLAVE", vigenciaRequest.getClave())
        .addValue("P_FEC_HASTA_PAYTV", new SimpleDateFormat("dd/MM/yyyy").parse(vigenciaRequest.getFechaHastaPayTV()))
        .addValue("P_OBSERVACIONES", vigenciaRequest.getObservaciones())
        .addValue("P_USUARIO", usuario);
        
        this.adelantarVencimiento.execute(in);

        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();
        ContratoValidationResult foo = new ContratoValidationResult();
        foo.setTipo("P_OK");
        foo.setDescripcion("S");
        ret.add(foo);

        return ret;
    }

    @Override
    public List<ContratoValidationResult> modificarTituloContratadoSC(TituloConGrupo tituloConGrupoRequest, String usuario) {
        MapSqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", tituloConGrupoRequest.getContrato())
            .addValue("P_GRUPO", tituloConGrupoRequest.getNroGrupo())
            .addValue("P_SENIAL", tituloConGrupoRequest.getSenial())
            .addValue("P_SENIAL_HER", tituloConGrupoRequest.getSenialHeredada())
            .addValue("P_CLAVE", tituloConGrupoRequest.getClave())
            .addValue("P_FEC_POS_ENT", tituloConGrupoRequest.getFechaEntrega())
            .addValue("P_CANT_TIE", tituloConGrupoRequest.getCantTiempo())
            .addValue("P_UNI_TIE", tituloConGrupoRequest.getUnidadDeTiempo())
            .addValue("P_DER_TER", tituloConGrupoRequest.getCodigoDerechosTerritoriales())
            .addValue("P_MAR_PER", tituloConGrupoRequest.getMarcaPerpetuidad())
            .addValue("P_OBSERV", tituloConGrupoRequest.getObservaciones())
            .addValue("P_RECONT", tituloConGrupoRequest.getRecontratacion())
            .addValue("P_TIE_EXCL", tituloConGrupoRequest.getCantTiempoExclusivo())
            .addValue("P_MAR_PER", tituloConGrupoRequest.getMarcaPerpetuidad())
            .addValue("P_AUT_AUT", tituloConGrupoRequest.getAutorizacionAutor())
            .addValue("P_PAGA_ARG", tituloConGrupoRequest.getPagaArgentores())
            .addValue("P_PAS_LIB", tituloConGrupoRequest.getPasaLibreria())
            .addValue("P_FEC_COM_LIB", tituloConGrupoRequest.getFechaComienzoDerechosLibreria())
            .addValue("P_CANT_PAS", tituloConGrupoRequest.getCantPasadas())
            .addValue("P_TIPO_DER", tituloConGrupoRequest.getCodigoDerechos())
            .addValue("P_FEC_COM_DER", tituloConGrupoRequest.getFechaDerechos())
            .addValue("P_TIPO_IMPORTE", tituloConGrupoRequest.getTipoImporte())
            .addValue("P_IMP_UNI", tituloConGrupoRequest.getCostoTotal())
            .addValue("P_USUARIO", usuario);

        Map<String, Object> out = this.modificarTituloContratadoSC.execute(in);
        
        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();
        String reRun = (String) out.get("P_RERUN");
        ContratoValidationResult foo = new ContratoValidationResult();
        foo.setTipo(TIPO_RERUN);
        foo.setDescripcion(reRun);
        ret.add(foo);
      
        String modoReRun = (String) out.get("P_FLAG_MODO");
        foo = new ContratoValidationResult();
        foo.setTipo(TIPO_MODO_RERUN);
        foo.setDescripcion(modoReRun);
        ret.add(foo);
    
        String procesoReRun = (String) out.get("P_FLAG_PROC");
        foo = new ContratoValidationResult();
        foo.setTipo(TIPO_PROCESO_RERUN);
        foo.setDescripcion(procesoReRun);
        ret.add(foo);

        return ret;
    }

    @Override
    public List<ContratoValidationResult> modificarTituloContratadoCC(TituloConGrupo tituloConGrupoRequest, String usuario) {
        MapSqlParameterSource in = new MapSqlParameterSource()
        .addValue("P_CONTRATO", tituloConGrupoRequest.getContrato())
        .addValue("P_GRUPO", tituloConGrupoRequest.getNroGrupo())
        .addValue("P_SENIAL", tituloConGrupoRequest.getSenialHeredada())
        .addValue("P_CLAVE", tituloConGrupoRequest.getClave())
        .addValue("P_FEC_POS_ENT", tituloConGrupoRequest.getFechaEntrega())
        .addValue("P_DER_TER", tituloConGrupoRequest.getCodigoDerechos())
        .addValue("P_OBSERV", tituloConGrupoRequest.getObservaciones())
        .addValue("P_AUT_AUT", tituloConGrupoRequest.getAutorizacionAutor())
        .addValue("P_PAGA_ARG", tituloConGrupoRequest.getPagaArgentores())
        .addValue("P_UNI_TIE_EXCL", tituloConGrupoRequest.getUnidadTiempoExclusivo())
        .addValue("P_CANT_TIE_EXCL", tituloConGrupoRequest.getCantTiempoExclusivo())
        .addValue("P_USUARIO", usuario);

        Map<String, Object> out = this.modificarTituloContratadoCC.execute(in);
        
        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();
        String ok = (String) out.get("P_OK");
        ContratoValidationResult foo = new ContratoValidationResult();
        foo.setTipo("P_OK");
        foo.setDescripcion(ok);
        ret.add(foo);
      
        return ret;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<ContratoValidationResult> reemplazarTitulos(Map data, String usuario) {
        Long contrato = ((Long)data.get("contrato"));
        Long grupo = (Long) data.get("grupo");
        String senial = (String)data.get("senial");
        List titulos = (List)data.get("titulos");

        MapSqlParameterSource in = new MapSqlParameterSource()
                                    .addValue("P_USUARIO", usuario)
                                    .addValue("P_CONTRATO", contrato)
                                    .addValue("P_GRUPO", grupo)
                                    .addValue("P_SENIAL", senial);
        deleteTitulosReemplazoCall.execute(in);

        for (Object object : titulos) {
            in.addValue("P_USUARIO", usuario)
              .addValue("P_CONTRATO", contrato)
              .addValue("P_GRUPO", grupo)
              .addValue("P_SENIAL", senial)
              .addValue("P_CLAVE", (String)((Map)object).get("clave"));
            insertarTituloReemplazoCall.execute(in);
        }

        in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", contrato)
            .addValue("P_GRUPO", grupo)
            .addValue("P_SENIAL", senial)
            .addValue("P_USUARIO", usuario);
        Map<String, Object> out = titulosReemplazoCall.execute(in);

        Integer importeReemplazo = ((BigDecimal) out.get("P_IMPORTE_REEMP")).intValue();
        Integer nroRelacionante = ((BigDecimal) out.get("P_NRO_REL")).intValue();
        Integer grupoReemplazo = ((BigDecimal) out.get("P_GRUPOS_REEMP")).intValue();
        
        in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", contrato)
            .addValue("P_GRUPO", grupo)
            .addValue("P_USUARIO", usuario)
            .addValue("P_SENIAL", senial);
        deleteTitulosReemplazoCall.execute(in);
        
        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();
        ContratoValidationResult foo = new ContratoValidationResult();
        foo.setTipo("D_NRO_RELACIONANTE");
        foo.setNroAdvertencia(nroRelacionante);
        ret.add(foo);
        
        ContratoValidationResult foo2 = new ContratoValidationResult();
        foo2.setTipo("D_IMPORTE_REEMP");
        foo2.setNroAdvertencia(importeReemplazo);
        ret.add(foo2);

        ContratoValidationResult foo3 = new ContratoValidationResult();
        foo3.setTipo("D_GRUPOS_REEMP");
        foo3.setNroAdvertencia(grupoReemplazo);
        ret.add(foo3);

        return ret;
    }

    @Override
    public List<ContratoValidationResult> reemplazarGrupo(GruposConReemplazoRequest gruposConReemplazoRequest, String usuario) {
        SqlParameterSource in = new MapSqlParameterSource()
                        .addValue("P_CONTRATO", gruposConReemplazoRequest.getContrato())
                        .addValue("P_GRUPO", gruposConReemplazoRequest.getMayorGrupo())
                        .addValue("P_SENIAL", gruposConReemplazoRequest.getSenial())
                        .addValue("P_USUARIO", usuario);

        Map<String, Object> out = this.reemplazarGrupo.execute(in);
        
        Integer importeReemplazo = ((BigDecimal) out.get("P_IMPORTE_REEMP")).intValue();
        
        Integer nroRelacionante = ((BigDecimal) out.get("P_NRO_REL")).intValue();

        Integer mayorGrupo = ((BigDecimal) out.get("P_MAYOR_GRUPO")).intValue();

        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();
        ContratoValidationResult foo = new ContratoValidationResult();
        foo.setTipo("D_NRO_RELACIONANTE");
        foo.setNroAdvertencia(nroRelacionante);
        ret.add(foo);
        
        ContratoValidationResult foo2 = new ContratoValidationResult();
        foo2.setTipo("D_IMPORTE_REEMP");
        foo2.setNroAdvertencia(importeReemplazo);
        ret.add(foo2);

        ContratoValidationResult foo3 = new ContratoValidationResult();
        foo3.setTipo("D_MAYOR_GRUPO");
        foo3.setNroAdvertencia(mayorGrupo);
        ret.add(foo3);
        
        return ret;

    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<ContratoValidationResult> extenderChequeo(Map data, String usuario) {
        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();

        Long contrato = new Long((String)data.get("contrato"));
        List seniales = (List)data.get("seniales");

        MapSqlParameterSource in = new MapSqlParameterSource()
                                    .addValue("P_USUARIO", usuario)
                                    .addValue("P_CONTRATO", contrato);
        
        deleteSenialesChequeoCall.execute(in);

        for (Object object : seniales) {
            in.addValue("P_CONTRATO", contrato)
              .addValue("P_SENIAL", (String)((Map)object).get("clave"))
              .addValue("P_USUARIO", usuario);
            insertarSenialesChequeoCall.execute(in);
        }

        in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", contrato)
            .addValue("P_USUARIO", usuario);
        extenderChequeoCall.execute(in);

        in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", contrato)
            .addValue("P_USUARIO", usuario);
        deleteSenialesChequeoCall.execute(in);
        
        ContratoValidationResult foo = new ContratoValidationResult();
        foo.setTipo("P_OK");
        foo.setDescripcion("S");
        ret.add(foo);
        
        return ret;
    }

    @Override
    public List<ContratoValidationResult> eliminarGrupo(Integer claveContrato, Integer claveGrupo, String senial, String usuario) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                                                        .addValue("P_CONTRATO", claveContrato)
                                                        .addValue("P_GRUPO", claveGrupo)
                                                        .addValue("P_USUARIO", usuario)
                                                        .addValue("P_SENIAL", senial);

        this.eliminarGrupoCall.execute(in);

        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();
        ContratoValidationResult foo = new ContratoValidationResult();
        foo.setTipo("P_OK");
        foo.setDescripcion("S");
        ret.add(foo);

        return ret;
    }

    @Override
    public List<ContratoValidationResult> actualizarTG(ActualizarTGRequest actualizarTGRequest, String usuario) {
//      PROCEDURE PR_ACTUALIZA_TG (
//      P_USUARIO        IN VARCHAR2,
//      P_FECHA          IN VARCHAR2,
//      P_HORA           IN VARCHAR2,
//      P_RENGLON        IN NUMBER,
//      P_OK             OUT VARCHAR2
//      )

        MapSqlParameterSource in = new MapSqlParameterSource()
                        .addValue("P_USUARIO", actualizarTGRequest.getUsuario())
                        .addValue("P_FECHA", actualizarTGRequest.getFecha())
                        .addValue("P_HORA", actualizarTGRequest.getHora())
                        .addValue("P_RENGLON", actualizarTGRequest.getRenglon());

        this.actualizarTG.execute(in);

        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();
        ContratoValidationResult ok = new ContratoValidationResult();
        ok.setTipo("P_OK");
        ok.setDescripcion("S");
        ret.add(ok);
        
        return ret;
    }

//    PROCEDURE PR_DEL_TITULOS_RERUN (  P_CONTRATO      IN NUMBER,
//                    P_GRUPO         IN NUMBER,
//                    P_SENIAL        IN VARCHAR2 );
    @Override
    public void cleanTitulosConReRun(TitulosRequest titulosRequest, String usuario) {
        MapSqlParameterSource in = new MapSqlParameterSource()
        .addValue("P_CONTRATO", titulosRequest.getContrato())
        .addValue("P_GRUPO", titulosRequest.getGrupo())
        .addValue("P_SENIAL", titulosRequest.getSenial());

        this.cleanTitulosConReRun.execute(in);
    }

    @Override
    public void actualizarImporteSenialAutomaticamente(ContratoValidationRequest contratoValidationRequest) {
        MapSqlParameterSource in = new MapSqlParameterSource()
        .addValue("P_CONTRATO", contratoValidationRequest.getContrato())
        .addValue("P_SENIAL", contratoValidationRequest.getSenial());

        this.actualizarImporteSenialAutomaticamente.execute(in);
    }
}