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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

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

@Repository("modificarContratoValidatorDao")
public class ModificarContratoValidatorDaoPL implements ModificarContratoValidatorDao {

    private static final String TIPO_DATA_FECHA_PROCESO = "D_FECHA_PROC";
    private static final String TIPO_DATA_HORA_PROCESO = "D_HORA_PROC";
    private static final String TIPO_DATA_TG_USUARIO = "D_TG_USUARIO";
    private static final String TIPO_DATA_TG_WORK = "D_TG_WORK";
    private static final String TIPO_DATA_DIF_TITULOS = "D_DIF_TIT";
    private static final String TIPO_FLAG_HAY_CAPITULOS = "F_HAY_CAPITULOS";
    private static final String TIPO_FLAG_NO_MODIFICA = "F_NO_MODIF";
    private static final String TIPO_FLAG_DIF_TITULOS = "F_DIF_TIT";
    private static final String TIPO_RERUN = "RR";
    
    private SimpleJdbcCall validarContratoConSenialesCall;
    private SimpleJdbcCall validarMontosCall;
    private SimpleJdbcCall validarModificacionDeCabeceraCall;
    private SimpleJdbcCall validarCabeceraSenialesCall;
    private SimpleJdbcCall validarCabeceraSenialCall;
    private SimpleJdbcCall validarAltaTituloContratoCall;
    private SimpleJdbcCall validarAltaGrupoSinCapCall;
    private SimpleJdbcCall validarAltaGrupoConCapCall;
    private SimpleJdbcCall validarEnlaceAnteriorCall;
    private SimpleJdbcCall validarEnlacePosteriorCall;
    private SimpleJdbcCall validarRerunDesenlaceCall;
    private SimpleJdbcCall validarModificaGrupoSinCap;
    private SimpleJdbcCall validarModificaGrupoConCap;
    private SimpleJdbcCall dameDiferenciaTitulosSC;
    private SimpleJdbcCall validarModificaCantidadGrupoConCap;
    private SimpleJdbcCall insertarCapituloParteCall;
    private SimpleJdbcCall deleteCapituloParteCall;
    private SimpleJdbcCall validarCapitulosABorrar;
    private SimpleJdbcCall validarCapitulosParaAgregar;
    private SimpleJdbcCall validarTitulosABorrar;
    private SimpleJdbcCall validarDescarmarTitulo;
    private SimpleJdbcCall validarEliminarGrupoCall;
    private SimpleJdbcCall deleteTitulosReemplazoCall;
    private SimpleJdbcCall insertarTituloReemplazoCall;
    private SimpleJdbcCall validarTitulosReemplazo;
    private SimpleJdbcCall validarExtenderChequeoTecnicoCall;
    private SimpleJdbcCall validarAdelantarVencimiento;
    private SimpleJdbcCall validarProcesarPayTV;
    private SimpleJdbcCall validarModificarTituloContratadoSC;
    private SimpleJdbcCall validarAmpliarDerechos;
    private SimpleJdbcCall validarReemplazarGrupo;

    
    @Autowired
    public ModificarContratoValidatorDaoPL(DataSource dataSource) {
        super();
        this.validarModificacionDeCabeceraCall = buildValidarModificacionDeCabeceraCall(dataSource);
        this.validarContratoConSenialesCall = buildValidarContratoConSenialesCall(dataSource);
        this.validarMontosCall = buildValidarMontosCall(dataSource);
        this.validarCabeceraSenialesCall = buildValidarCabeceraSenialesCall(dataSource);
        this.validarCabeceraSenialCall = buildValidarCabeceraSenialCall(dataSource);
        this.validarAltaTituloContratoCall = buildValidarAltaTituloContratoCall(dataSource);
        this.validarAltaGrupoSinCapCall = buildValidarAltaGrupoSinCapCall(dataSource);
        this.validarAltaGrupoConCapCall = buildValidarAltaGrupoConCapCall(dataSource);
        this.validarEnlaceAnteriorCall = buildValidarEnlaceAnteriorCall(dataSource);
        this.validarEnlacePosteriorCall = buildValidarEnlacePosteriorCall(dataSource);
        this.validarRerunDesenlaceCall = buildValidarRerunDesenlaceCall(dataSource);
        this.validarModificaGrupoSinCap = buildValidarModificaGrupoSinCapCall(dataSource);
        this.validarModificaGrupoConCap = buildValidarModificaGrupoConCapCall(dataSource);
        this.dameDiferenciaTitulosSC = buildDameDiferenciaTitulosSCCall(dataSource);
        this.validarModificaCantidadGrupoConCap = buildValidarModificaCantidadGrupoConCapCall(dataSource);
        
        this.insertarCapituloParteCall = buildInsertarCapituloParteCall(dataSource);
        this.deleteCapituloParteCall = buildDeleteCapitulosParteCall(dataSource);

        this.validarCapitulosABorrar = buildCapitulosABorrarValCall(dataSource);
        this.validarCapitulosParaAgregar = buildCapitulosParaAgregarValCall(dataSource);
        this.validarTitulosABorrar = buildTitulosABorrarValCall(dataSource);
        this.validarDescarmarTitulo = buildValidarDescarmarTituloCall(dataSource);
        
        this.validarEliminarGrupoCall = buildValidarEliminarGrupo(dataSource);
        
        this.deleteTitulosReemplazoCall = buildDeleteTitulosReemplazoCall(dataSource);
        this.insertarTituloReemplazoCall = buildInsertarTituloReemplazoCall(dataSource);
        this.validarTitulosReemplazo = buildValidarTitulosReemplazo(dataSource);
        
        this.validarExtenderChequeoTecnicoCall = buildValidarExtenderChequeoTecnicoCall(dataSource);
        
        this.validarAdelantarVencimiento = buildValidarAdelantarVencimiento(dataSource);
        this.validarAmpliarDerechos = buildValidarAmpliarDerechosCall(dataSource);
        this.validarProcesarPayTV = buildValidarProcesarPayTVCall(dataSource);
        this.validarModificarTituloContratadoSC = buildValidarModificarTituloContratadoSCCall(dataSource);

        this.validarReemplazarGrupo = buildValidarReemplazarGrupo(dataSource);
    }
    
    private SimpleJdbcCall buildValidarReemplazarGrupo(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X")
                .withProcedureName("PR_VALIDA_REEMPLAZO_GRUPO")
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

    private SimpleJdbcCall buildValidarAmpliarDerechosCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_1X").withProcedureName("PR_VALIDA_AMPLIACION_DERECHOS")
                .returningResultSet("P_ERRORES", new RowMapper<ContratoValidationResult>() {
                    @Override
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(rs.getString("TIPO"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildValidarModificarTituloContratadoSCCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_1X").withProcedureName("PR_MODIFICA_TITULO_SC_VAL")
                .returningResultSet("P_ERRORES", new RowMapper<ContratoValidationResult>() {
                    @Override
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(rs.getString("TIPO"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        ret.setIdReporte(rs.getInt("ID_REPORTE"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildValidarProcesarPayTVCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_1X").withProcedureName("PR_VALIDA_PAYTV")
                .returningResultSet("P_ERRORES", new RowMapper<ContratoValidationResult>() {
                    @Override
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(rs.getString("TIPO"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        ret.setIdReporte(rs.getInt("ID_REPORTE"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildValidarAdelantarVencimiento(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_1X").withProcedureName("PR_VALIDA_ADELANTAR_VENC")
                .returningResultSet("P_ERRORES", new RowMapper<ContratoValidationResult>() {
                    @Override
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(rs.getString("TIPO"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildValidarExtenderChequeoTecnicoCall(DataSource dataSource) {
            return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_1X").withProcedureName("PR_EXTENDER_CHEQUEO_VAL");
    }

    private SimpleJdbcCall buildValidarTitulosReemplazo(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X").withProcedureName("PR_TITULOS_REEMPLAZO_VAL")
                .returningResultSet("P_ERRORES", new RowMapper<ContratoValidationResult>() {
                    @Override
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(rs.getString("TIPO"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        ret.setIdReporte(rs.getInt("ID_REPORTE"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildInsertarTituloReemplazoCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X").withProcedureName("PR_INS_CAP_PARTE");
    }

    private SimpleJdbcCall buildDeleteTitulosReemplazoCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X").withProcedureName("PR_DEL_CAP_PARTE");
    }

    private SimpleJdbcCall buildValidarEliminarGrupo(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X").withProcedureName("PR_VALIDA_ELIMINA_GRUPO_SC")
                .returningResultSet("P_ERRORES", new RowMapper<ContratoValidationResult>() {
                    @Override
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(rs.getString("TIPO"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        ret.setIdReporte(rs.getInt("ID_REPORTE"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildValidarDescarmarTituloCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_1X").withProcedureName("PR_VALIDA_DESMARCAR")
                .returningResultSet("P_ERRORES_PREGUNTAS", new RowMapper<ContratoValidationResult>() {
                    @Override
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(rs.getString("TIPO"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildCapitulosABorrarValCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X").withProcedureName("PR_CAPITULOS_A_BORRAR_VAL")
                .returningResultSet("P_ERRORES", new RowMapper<ContratoValidationResult>() {
                    @Override
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(rs.getString("TIPO"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildCapitulosParaAgregarValCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X").withProcedureName("PR_CAPITULOS_PARA_AGREGAR_VAL")
                .returningResultSet("P_ERRORES", new RowMapper<ContratoValidationResult>() {
                    @Override
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(rs.getString("TIPO"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        return ret;
                    }
                });
    }
    

    private SimpleJdbcCall buildTitulosABorrarValCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X").withProcedureName("PR_TITULOS_SC_VALIDA_BORRAR")
                .returningResultSet("P_ERRORES", new RowMapper<ContratoValidationResult>() {
                    @Override
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(rs.getString("TIPO"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        ret.setIdReporte(rs.getInt("ID_REPORTE"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildInsertarCapituloParteCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X").withProcedureName("PR_INS_CAP_PARTE");
    }
    
    private SimpleJdbcCall buildDeleteCapitulosParteCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X").withProcedureName("PR_DEL_CAP_PARTE");
    }
    
    private SimpleJdbcCall buildValidarModificaCantidadGrupoConCapCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X")
                .withProcedureName("PR_MODIF_CANT_ORIG_CC_VAL")
                .returningResultSet("P_ERRORES", new RowMapper<ContratoValidationResult>() {
                    @Override
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(rs.getString("TIPO"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        ret.setIdReporte(rs.getInt("ID_REPORTE"));
                        return ret;
                    }
                });
    }
    
    private SimpleJdbcCall buildDameDiferenciaTitulosSCCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_1X")
                .withProcedureName("PR_DIFERENCIA_TITULOS_SC")
                .returningResultSet("P_DIFERENCIAS", new RowMapper<ContratoValidationResult>() {
                    @Override
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(TIPO_DATA_DIF_TITULOS);
                        ret.setDescripcion(rs.getString("CLAVE") + " - " + rs.getString("TITULO_CASTELLANO"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildValidarModificaGrupoConCapCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_1X")
                .withProcedureName("PR_MODIFICA_GRUPO_CC_VAL")
                .returningResultSet("P_ERRORES", new RowMapper<ContratoValidationResult>() {
                    @Override
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(rs.getString("TIPO"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        ret.setIdReporte(rs.getInt("ID_REPORTE"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildValidarModificaGrupoSinCapCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_1X")
                .withProcedureName("PR_MODIFICA_GRUPO_SC_VAL")
                .returningResultSet("P_ERRORES", new RowMapper<ContratoValidationResult>() {
                    @Override
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(rs.getString("TIPO"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        ret.setIdReporte(rs.getInt("ID_REPORTE"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildValidarRerunDesenlaceCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X")
                .withProcedureName("PR_RERUN_DESENLACE_VAL")
                .returningResultSet("P_ERRORES", new RowMapper<ContratoValidationResult>() {
                    @Override
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(rs.getString("TIPO"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        ret.setIdReporte(rs.getInt("ID_REPORTE"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildValidarEnlacePosteriorCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X")
                .withProcedureName("PR_ENLACE_POSTERIOR_VAL")
                .returningResultSet("P_ERRORES", new RowMapper<ContratoValidationResult>() {
                    @Override
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(rs.getString("TIPO"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        ret.setIdReporte(rs.getInt("ID_REPORTE"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildValidarEnlaceAnteriorCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X")
                .withProcedureName("PR_ENLACE_ANTERIOR_VAL")
                .returningResultSet("P_ERRORES", new RowMapper<ContratoValidationResult>() {
                    @Override
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(rs.getString("TIPO"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        ret.setIdReporte(rs.getInt("ID_REPORTE"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildValidarAltaGrupoConCapCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X")
                .withProcedureName("PR_VALIDAR_ALTA_GRUPO_CC")
                .returningResultSet("P_ERRORES", new RowMapper<ContratoValidationResult>() {
                    @Override
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(rs.getString("TIPO"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        return ret;
                    }
                });
    }
    
    private SimpleJdbcCall buildValidarAltaGrupoSinCapCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X")
                .withProcedureName("PR_VALIDAR_ALTA_GRUPO_SC")
                .returningResultSet("P_ERRORES", new RowMapper<ContratoValidationResult>() {
                    @Override
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(rs.getString("TIPO"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildValidarAltaTituloContratoCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X")
                .withProcedureName("PR_VALIDA_ALTA_TITULO_CONTRATO")
                .returningResultSet("P_ERRORES", new RowMapper<ContratoValidationResult>() {
                    @Override
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(rs.getString("TIPO"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildValidarMontosCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS")
                .withProcedureName("PR_FP06_CONFIRMAR")
                .returningResultSet("P_ERRORES", new RowMapper<ContratoValidationResult>() {
                    @Override
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(rs.getString("TIPO"));
                        ret.setNroAdvertencia(rs.getInt("NRO_ADVERTENCIA"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildValidarContratoConSenialesCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS")
                .withProcedureName("PR_FP04_CONFIRMAR")
                .returningResultSet("P_ERRORES", new RowMapper<ContratoValidationResult>() {
                    @Override
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(rs.getString("TIPO"));
                        ret.setNroAdvertencia(rs.getInt("NRO_ADVERTENCIA"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildValidarCabeceraSenialesCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X")
                .withProcedureName("PR_VALIDAR_CABECERA_SENIALES")
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
    

    private SimpleJdbcCall buildValidarCabeceraSenialCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X")
                .withProcedureName("PR_VALIDAR_CABECERA_SENIAL")
                .returningResultSet("P_ERRORES", new RowMapper<ContratoValidationResult>() {
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(rs.getString("TIPO"));
                        ret.setNroAdvertencia(rs.getInt("NRO_ADVERTENCIA"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        return ret;
                    }
                });
    }
    private SimpleJdbcCall buildValidarModificacionDeCabeceraCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS")
                .withProcedureName("PR_FP03_VAL")
                .returningResultSet("P_ERRORES", new RowMapper<ContratoValidationResult>() {
                    @Override
                    public ContratoValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoValidationResult ret = new ContratoValidationResult();
                        ret.setTipo(rs.getString("TIPO"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        return ret;
                    }
                });
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ContratoValidationResult> validarMontos(Integer claveContrato) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", claveContrato);
        Map<String, Object> out = this.validarMontosCall.execute(in);
        return (List<ContratoValidationResult>) out.get("P_ERRORES");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ContratoValidationResult> validarModificacionDeCabecera(ContratoValidationRequest contratoCabeceraValidationRequest) throws ParseException {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO_MODIF", contratoCabeceraValidationRequest.getContrato())
            .addValue("P_DISTRIB_MODIF", contratoCabeceraValidationRequest.getDistribuidor())
            .addValue("P_MONEDA_MODIF", contratoCabeceraValidationRequest.getMoneda())
            .addValue("P_FEC_CONT_MODIF", new SimpleDateFormat("dd-MM-yyyy").parse(contratoCabeceraValidationRequest.getFechaContrato()))
            .addValue("P_FEC_APR_CONT_MODIF", new SimpleDateFormat("dd-MM-yyyy").parse(contratoCabeceraValidationRequest.getFechaAprobacion()))
            .addValue("P_TIP_CONT_MODIF", contratoCabeceraValidationRequest.getTipoContrato())
            .addValue("P_MONTO_MODIF", contratoCabeceraValidationRequest.getMonto());
        Map<String, Object> out = this.validarModificacionDeCabeceraCall.execute(in);
        return (List<ContratoValidationResult>) out.get("P_ERRORES");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ContratoValidationResult> validarContratoConSeniales(ContratoValidationRequest contratoCabeceraValidationRequest) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", contratoCabeceraValidationRequest.getContrato());
        Map<String, Object> out = this.validarContratoConSenialesCall.execute(in);
        return (List<ContratoValidationResult>) out.get("P_ERRORES");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ContratoValidationResult> validarCabeceraSeniales(ContratoValidationRequest contratoValidationRequest) throws ParseException {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO_MODIF", contratoValidationRequest.getContrato())
            .addValue("P_DISTRIB_MODIF", contratoValidationRequest.getDistribuidor())
            .addValue("P_MONEDA_MODIF", contratoValidationRequest.getMoneda())
            .addValue("P_FEC_CONT_MODIF", new SimpleDateFormat("dd-MM-yyyy").parse(contratoValidationRequest.getFechaContrato()))
            .addValue("P_FEC_APR_CONT_MODIF", new SimpleDateFormat("dd-MM-yyyy").parse(contratoValidationRequest.getFechaAprobacion()))
            .addValue("P_TIP_CONT_MODIF", contratoValidationRequest.getTipoContrato())
            .addValue("P_MONTO_MODIF", contratoValidationRequest.getMonto());
        Map<String, Object> out = this.validarCabeceraSenialesCall.execute(in);
        return (List<ContratoValidationResult>) out.get("P_ERRORES");
    }

   
    
    @SuppressWarnings("unchecked")
    @Override
    public List<ContratoValidationResult> validarAltaTitulo(ValidarAltaTituloRequest validarAltaTituloRequest, String usuario) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", validarAltaTituloRequest.getContrato())
            .addValue("P_GRUPO", validarAltaTituloRequest.getGrupo())
            .addValue("P_SENIAL", validarAltaTituloRequest.getSenial())
            .addValue("P_CLAVE", validarAltaTituloRequest.getClave())
            .addValue("P_TIT_ORI", validarAltaTituloRequest.getTituloOriginal())
            .addValue("P_TIT_CAS", validarAltaTituloRequest.getTituloCastellano())
            .addValue("P_EST_REP", validarAltaTituloRequest.getEr())
            .addValue("P_RECONT", validarAltaTituloRequest.getRecontratacion())
            .addValue("P_ORIGEN", validarAltaTituloRequest.getOrigen())
            .addValue("P_SIN_CONTRATO", validarAltaTituloRequest.getSinContrato())
            .addValue("P_PROVEEDOR", validarAltaTituloRequest.getProveedor())
            .addValue("P_USUARIO", usuario);
        
        Map<String, Object> out = this.validarAltaTituloContratoCall.execute(in);

        List<ContratoValidationResult> ret;
        ret = (List<ContratoValidationResult>) out.get("P_ERRORES");
        
        String hayCapitulos = (String) out.get("P_FLAG_HAY_CAPITULOS");
        ContratoValidationResult foo;
        if ((hayCapitulos != null) && (hayCapitulos.equals("S"))) {
            foo = new ContratoValidationResult();
            foo.setTipo(TIPO_FLAG_HAY_CAPITULOS);
            foo.setDescripcion(hayCapitulos);
            ret.add(foo);
            
            String tgUsuario = (String) out.get("P_TG_USUARIO");
            foo = new ContratoValidationResult();
            foo.setTipo(TIPO_DATA_TG_USUARIO);
            foo.setDescripcion(tgUsuario);
            ret.add(foo);
            
            String tgWork = (String) out.get("P_TG_WORK");
            foo = new ContratoValidationResult();
            foo.setTipo(TIPO_DATA_TG_WORK);
            foo.setDescripcion(tgWork);
            ret.add(foo);
            
            String tgFecha = (String) out.get("P_TG_FECHA");
            foo = new ContratoValidationResult();
            foo.setTipo(TIPO_DATA_FECHA_PROCESO);
            foo.setDescripcion(tgFecha);
            ret.add(foo);
            
            String tgHora = (String) out.get("P_TG_HORA");
            foo = new ContratoValidationResult();
            foo.setTipo(TIPO_DATA_HORA_PROCESO);
            foo.setDescripcion(tgHora);
            ret.add(foo);
        }
        
        return ret;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<ContratoValidationResult> validarAltaGrupoSinCap(GrupoRequest grupoRequest) {
        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_SENIAL", grupoRequest.getSenial())
            .addValue("P_TIPO_TITULO", grupoRequest.getTipoTitulo())
            .addValue("P_TIPO_IMPORTE", grupoRequest.getTipoImporte())
            .addValue("P_PASA_LIB", grupoRequest.getPasaLibreria())
            .addValue("P_CANT_PASADAS", grupoRequest.getCantPasadas())
            .addValue("P_IMP_UNITARIO", grupoRequest.getCostoTotal())
            .addValue("P_MODO", grupoRequest.getModo())
            .addValue("P_CANT_PASADAS", grupoRequest.getCantPasadas())
            .addValue("P_MAR_PER", grupoRequest.getMarcaPerpetuidad());
        Map<String, Object> out = this.validarAltaGrupoSinCapCall.execute(in);
        ret = ((List<ContratoValidationResult>) out.get("P_ERRORES"));
        return ret;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<ContratoValidationResult> validarAltaGrupoConCap(GrupoRequest grupoRequest) {
        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_SENIAL", grupoRequest.getSenial())
            .addValue("P_TIPO_TITULO", grupoRequest.getTipoTitulo())
            .addValue("P_TIPO_IMPORTE", grupoRequest.getTipoImporte())
            .addValue("P_PASA_LIB", grupoRequest.getPasaLibreria())
            .addValue("P_CANT_REP", grupoRequest.getCantPasadas())
            .addValue("P_CANT_ORIG", grupoRequest.getCantTitulos())
            .addValue("P_MODO", grupoRequest.getModo())
            .addValue("P_MAR_PER", grupoRequest.getMarcaPerpetuidad());
        Map<String, Object> out = this.validarAltaGrupoConCapCall.execute(in);
        ret = ((List<ContratoValidationResult>) out.get("P_ERRORES"));
        return ret;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<ContratoValidationResult> validarEnlaceAnterior(RerunRequest rerunRequest, String usuario) throws ParseException {
        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", rerunRequest.getContrato())
            .addValue("P_GRUPO", rerunRequest.getGrupo())
            .addValue("P_SENIAL", rerunRequest.getSenial())
            .addValue("P_CLAVE", rerunRequest.getClave())
            .addValue("P_RERUN_CONTRATO", rerunRequest.getRerunContrato())
            .addValue("P_RERUN_GRUPO", rerunRequest.getRerunGrupo())
            .addValue("P_RERUN_ENL_ANT", rerunRequest.getRerunEnlaceAnterior())
            .addValue("P_RERUN_GRUPO_ANT", rerunRequest.getRerunGrupoAnterior())
            .addValue("P_RERUN_ENL_POS", rerunRequest.getRerunEnlacePosterior())
            .addValue("P_RERUN_GRUPO_POS", rerunRequest.getRerunGrupoPosterior())
            .addValue("P_RERUN_VIG_DESDE", new SimpleDateFormat("dd/MM/yyyy").parse(rerunRequest.getRerunVigenciaDesde()))
            .addValue("P_RERUN_VIG_HASTA", new SimpleDateFormat("dd/MM/yyyy").parse(rerunRequest.getRerunVigenciaHasta()))
            .addValue("P_PROCESO", rerunRequest.getProceso());
        Map<String, Object> out = this.validarEnlaceAnteriorCall.execute(in);
        ret = ((List<ContratoValidationResult>) out.get("P_ERRORES"));
        return ret;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<ContratoValidationResult> validarEnlacePosterior(RerunRequest rerunRequest, String usuario) throws ParseException {
        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", rerunRequest.getContrato())
            .addValue("P_GRUPO", rerunRequest.getGrupo())
            .addValue("P_SENIAL", rerunRequest.getSenial())
            .addValue("P_CLAVE", rerunRequest.getClave())
            .addValue("P_RERUN_CONTRATO", rerunRequest.getRerunContrato())
            .addValue("P_RERUN_GRUPO", rerunRequest.getRerunGrupo())
            .addValue("P_RERUN_ENL_ANT", rerunRequest.getRerunEnlaceAnterior())
            .addValue("P_RERUN_GRUPO_ANT", rerunRequest.getRerunGrupoAnterior())
            .addValue("P_RERUN_ENL_POS", rerunRequest.getRerunEnlacePosterior())
            .addValue("P_RERUN_GRUPO_POS", rerunRequest.getRerunGrupoPosterior())
            .addValue("P_RERUN_VIG_DESDE", new SimpleDateFormat("dd/MM/yyyy").parse(rerunRequest.getRerunVigenciaDesde()))
            .addValue("P_RERUN_VIG_HASTA", new SimpleDateFormat("dd/MM/yyyy").parse(rerunRequest.getRerunVigenciaHasta()))
            .addValue("P_PROCESO", rerunRequest.getProceso());
        Map<String, Object> out = this.validarEnlacePosteriorCall.execute(in);
        ret = ((List<ContratoValidationResult>) out.get("P_ERRORES"));
        return ret;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<ContratoValidationResult> validarRerunDesenlace(RerunRequest rerunRequest, String usuario) {
        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", rerunRequest.getContrato())
            .addValue("P_GRUPO", rerunRequest.getGrupo())
            .addValue("P_SENIAL", rerunRequest.getSenial())
            .addValue("P_CLAVE", rerunRequest.getClave());
        Map<String, Object> out = this.validarRerunDesenlaceCall.execute(in);
        ret = ((List<ContratoValidationResult>) out.get("P_ERRORES"));
        return ret;
    }

    
    @SuppressWarnings("unchecked")
    public List<ContratoValidationResult> validarModificaGrupoSinCap(GrupoRequest grupoRequest, String usuario) throws ParseException {
        MapSqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", grupoRequest.getContrato())
            .addValue("P_GRUPO", grupoRequest.getNroGrupo())
            .addValue("P_SENIAL_HER", grupoRequest.getSenialHeredada())
            .addValue("P_SENIAL", grupoRequest.getSenial())
            .addValue("P_IMP_UNI_MOD", grupoRequest.getCostoTotal())
            .addValue("P_CANT_PASADAS_MOD", grupoRequest.getCantPasadas())
            .addValue("P_FEC_COM_DER_MOD", parseDate(grupoRequest.getFechaDerechos(), "dd/MM/yyyy"))
            .addValue("P_TIPO_DER_MOD", grupoRequest.getCodigoDerechos())
            .addValue("P_CANT_TIE_MOD", grupoRequest.getCantTiempo())
            .addValue("P_UNI_TIE_MOD", grupoRequest.getUnidadDeTiempo())
            .addValue("P_DER_TER_MOD", grupoRequest.getCodigoDerechosTerritoriales())
            .addValue("P_MAR_PER_MOD", grupoRequest.getMarcaPerpetuidad())
            .addValue("P_CANT_EXCL_MOD", grupoRequest.getCantTiempoExclusivo())
            .addValue("P_UNI_EXCL_MOD", grupoRequest.getUnidadTiempoExclusivo())
            .addValue("P_AUT_AUT_MOD", grupoRequest.getAutorizacionAutor())
            .addValue("P_PAGA_ARG_MOD", grupoRequest.getPagaArgentores())
            .addValue("P_PAS_LIB_MOD", grupoRequest.getPasaLibreria())
            .addValue("P_FEC_COM_LIB_MOD", parseDate(grupoRequest.getFechaComienzoDerechosLibreria(), "dd/MM/yyyy"))
            .addValue("P_NOM_GRUPO_MOD", grupoRequest.getNombreGrupo())
            .addValue("P_TIPO_IMPORTE_MOD", grupoRequest.getTipoImporte())
            .addValue("P_EST_REP_MOD", grupoRequest.getEr())
            .addValue("P_PLAN_ENTREGA_MOD", grupoRequest.getPlanEntrega())
            .addValue("P_CANT_TIT_MOD", grupoRequest.getCantTitulos())
            .addValue("P_MODO", grupoRequest.getModo())
            .addValue("P_USUARIO", usuario);
        
        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();
        
        Map<String, Object> out = this.validarModificaGrupoSinCap.execute(in);
        ret = ((List<ContratoValidationResult>) out.get("P_ERRORES"));
        ContratoValidationResult contratoValidationResult;
        
        Date fechaProceso = (Date) out.get("P_FECHA_PROCESO");
        contratoValidationResult = new ContratoValidationResult();
        contratoValidationResult.setTipo(TIPO_DATA_FECHA_PROCESO);
        contratoValidationResult.setFecha(fechaProceso);
        ret.add(contratoValidationResult);

        Integer horaProceso = ((BigDecimal) out.get("P_HORA_PROCESO")).intValue();
        contratoValidationResult = new ContratoValidationResult();
        contratoValidationResult.setTipo(TIPO_DATA_HORA_PROCESO);
        contratoValidationResult.setNroAdvertencia(horaProceso);
        ret.add(contratoValidationResult);

        String difTitulos = (String) out.get("P_FLAG_DIF_TITULOS");
        contratoValidationResult = new ContratoValidationResult();
        contratoValidationResult.setTipo(TIPO_FLAG_DIF_TITULOS);
        contratoValidationResult.setDescripcion(difTitulos);
        ret.add(contratoValidationResult);

        String noModifica = (String) out.get("P_FLAG_NO_MODIFICA");
        contratoValidationResult = new ContratoValidationResult();
        contratoValidationResult.setTipo(TIPO_FLAG_NO_MODIFICA);
        contratoValidationResult.setDescripcion(noModifica);
        ret.add(contratoValidationResult);
        
        if (difTitulos.equalsIgnoreCase("S")) {
            in = new MapSqlParameterSource()
            .addValue("P_FEC_PROC", fechaProceso)
            .addValue("P_HOR_PROC", horaProceso)
            .addValue("P_USUARIO", usuario);
            
            out = this.dameDiferenciaTitulosSC.execute(in);
            ret.addAll((List<ContratoValidationResult>) out.get("P_DIFERENCIAS"));
        }

        return ret;
    }
    
    @SuppressWarnings("unchecked")
    public List<ContratoValidationResult> validarModificaGrupoConCap(GrupoRequest grupoRequest, String usuario) throws ParseException {
        MapSqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", grupoRequest.getContrato())
            .addValue("P_GRUPO", grupoRequest.getNroGrupo())
            .addValue("P_SENIAL", grupoRequest.getSenial())
            .addValue("P_SENIAL_HER", grupoRequest.getSenialHeredada())
            .addValue("P_FEC_COM_DER_MOD", parseDate(grupoRequest.getFechaDerechos(), "dd/MM/yyyy"))
            .addValue("P_TIPO_DER_MOD", grupoRequest.getCodigoDerechos())
            .addValue("P_CANT_TIE_MOD", grupoRequest.getCantTiempo())
            .addValue("P_UNI_TIE_MOD", grupoRequest.getUnidadDeTiempo())
            .addValue("P_DER_TER_MOD", grupoRequest.getCodigoDerechosTerritoriales())
            .addValue("P_MAR_PER_MOD", grupoRequest.getMarcaPerpetuidad())
            .addValue("P_CANT_EXCL_MOD", grupoRequest.getCantTiempoExclusivo())
            .addValue("P_UNI_EXCL_MOD", grupoRequest.getUnidadTiempoExclusivo())
            .addValue("P_AUT_AUT_MOD", grupoRequest.getAutorizacionAutor())
            .addValue("P_PAGA_ARG_MOD", grupoRequest.getPagaArgentores())
            .addValue("P_IMP_GRUPO_MOD", grupoRequest.getCostoTotal())
            .addValue("P_PAS_LIB_MOD", grupoRequest.getPasaLibreria())
            .addValue("P_FEC_COM_LIB_MOD", parseDate(grupoRequest.getFechaComienzoDerechosLibreria(), "dd/MM/yyyy"))
            .addValue("P_NOM_GRUPO_MOD", grupoRequest.getNombreGrupo())
            .addValue("P_TIPO_IMPORTE_MOD", grupoRequest.getTipoImporte())
            .addValue("P_EST_REP_MOD", grupoRequest.getEr())
            .addValue("P_RECONT", grupoRequest.getRecontratacion())
            .addValue("P_PLAN_ENTREGA_MOD", grupoRequest.getPlanEntrega())
            .addValue("P_CANT_ORI_MOD", grupoRequest.getCantTitulos())
            .addValue("P_CANT_REP_MOD", grupoRequest.getCantPasadas())
            .addValue("P_MODO", grupoRequest.getModo())
            .addValue("P_USUARIO", usuario);
        
        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();
        
        Map<String, Object> out = this.validarModificaGrupoConCap.execute(in);
        ret = (List<ContratoValidationResult>) out.get("P_ERRORES");
        ContratoValidationResult contratoValidationResult;
        
        Date fechaProceso = (Date) out.get("P_FECHA_PROCESO");
        contratoValidationResult = new ContratoValidationResult();
        contratoValidationResult.setTipo(TIPO_DATA_FECHA_PROCESO);
        contratoValidationResult.setFecha(fechaProceso);
        ret.add(contratoValidationResult);

        Integer horaProceso = ((BigDecimal) out.get("P_HORA_PROCESO")).intValue();
        contratoValidationResult = new ContratoValidationResult();
        contratoValidationResult.setTipo(TIPO_DATA_HORA_PROCESO);
        contratoValidationResult.setNroAdvertencia(horaProceso);
        ret.add(contratoValidationResult);

        return ret;
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<ContratoValidationResult> validarModificaCantidadGrupoConCap(GrupoRequest grupoRequest, String usuario) {
        MapSqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", grupoRequest.getContrato())
            .addValue("P_GRUPO", grupoRequest.getNroGrupo())
            .addValue("P_SENIAL", grupoRequest.getSenial())
            .addValue("P_USUARIO", usuario)
            .addValue("P_CANT_ORIG", grupoRequest.getCantTitulos());
        
        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();
        
        Map<String, Object> out = this.validarModificaCantidadGrupoConCap.execute(in);
        ret = ((List<ContratoValidationResult>) out.get("P_ERRORES"));
        return ret;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public List<ContratoValidationResult> validarCapitulosAAgregar(Map data, String usuario) {
        Long contrato = ((Long)data.get("contrato"));
        Long grupo = (Long) data.get("grupo");
        String senial = ((String)data.get("senial")).trim();
        String clave = ((String)data.get("clave")).trim();

        MapSqlParameterSource in = new MapSqlParameterSource()
                                    .addValue("P_CONTRATO", contrato)
                                    .addValue("P_GRUPO", grupo)
                                    .addValue("P_USUARIO", usuario)
                                    .addValue("P_SENIAL", senial);
        deleteCapituloParteCall.execute(in);
        
        List capitulosPartes = (List)data.get("capitulosPartes");
        int cantCapitulosPartes = 0;
        for (Object object : capitulosPartes) {
            in.addValue("P_CONTRATO", contrato)
              .addValue("P_GRUPO", grupo)
              .addValue("P_SENIAL", senial)
              .addValue("P_USUARIO", usuario)
              .addValue("P_CLAVE", clave)
              .addValue("P_CAPITULO", (Long)((Map)object).get("capitulo"))
              .addValue("P_PARTE", (Long)((Map)object).get("parte"));
            insertarCapituloParteCall.execute(in);
            cantCapitulosPartes++;
        }

        in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", contrato)
            .addValue("P_GRUPO", grupo)
            .addValue("P_SENIAL", senial)
            .addValue("P_CLAVE", clave)
            .addValue("P_CNT_CAP_SEL", cantCapitulosPartes)
            .addValue("P_USUARIO", usuario);
        Map<String, Object> out = this.validarCapitulosParaAgregar.execute(in);

        List<ContratoValidationResult> ret = ((List<ContratoValidationResult>) out.get("P_ERRORES"));

        Date fechaProceso = (Date) out.get("P_FECHA_PROC");
        ContratoValidationResult contratoValidationResult;
        contratoValidationResult = new ContratoValidationResult();
        contratoValidationResult.setTipo(TIPO_DATA_FECHA_PROCESO);
        contratoValidationResult.setFecha(fechaProceso);
        ret.add(contratoValidationResult);

        BigDecimal horaProceso = ((BigDecimal) out.get("P_HORA_PROC"));
        contratoValidationResult = new ContratoValidationResult();
        contratoValidationResult.setTipo(TIPO_DATA_HORA_PROCESO);
        if (horaProceso != null) {
            contratoValidationResult.setNroAdvertencia(horaProceso.intValue());
        } else {
            contratoValidationResult.setNroAdvertencia(null);
        }
        ret.add(contratoValidationResult);

        String hayCapitulos = (String) out.get("P_FLAG_HAY_CAPITULOS");
        contratoValidationResult = new ContratoValidationResult();
        contratoValidationResult.setTipo(TIPO_FLAG_HAY_CAPITULOS);
        contratoValidationResult.setDescripcion(hayCapitulos);
        ret.add(contratoValidationResult);

        in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", contrato)
            .addValue("P_GRUPO", grupo)
            .addValue("P_SENIAL", senial)
            .addValue("P_USUARIO", usuario);
        deleteCapituloParteCall.execute(in);
        
        return ret;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public List<ContratoValidationResult> validarCapitulosABorrar(Map data, String usuario) {
        Long contrato = ((Long)data.get("contrato"));
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
              .addValue("P_CLAVE", clave)
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
        Map<String, Object> out = this.validarCapitulosABorrar.execute(in);

        List<ContratoValidationResult> ret = ((List<ContratoValidationResult>) out.get("P_ERRORES"));
        
        in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", contrato)
            .addValue("P_GRUPO", grupo)
            .addValue("P_USUARIO", usuario)
            .addValue("P_CLAVE", clave)
            .addValue("P_SENIAL", senial);
        deleteCapituloParteCall.execute(in);
        
        return ret;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public List<ContratoValidationResult> validarTitulosABorrar(Map data, String usuario) {
        Long contrato = ((Long)data.get("contrato"));
        Long grupo = (Long) data.get("grupo");
        String senial = (String)data.get("senial");
        List titulos = (List)data.get("titulos");

        MapSqlParameterSource in = new MapSqlParameterSource()
                                    .addValue("P_USUARIO", usuario)
                                    .addValue("P_CONTRATO", contrato)
                                    .addValue("P_GRUPO", grupo)
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
        Map<String, Object> out = this.validarTitulosABorrar.execute(in);

        List<ContratoValidationResult> ret = ((List<ContratoValidationResult>) out.get("P_ERRORES"));

        in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", contrato)
            .addValue("P_GRUPO", grupo)
            .addValue("P_USUARIO", usuario)
            .addValue("P_SENIAL", senial);
        deleteCapituloParteCall.execute(in);

        return ret;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<ContratoValidationResult> validarDescarmarTitulo(TituloRequest tituloRequest, String usuario) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                                                            .addValue("P_USUARIO", usuario)
                                                            .addValue("P_CONTRATO", tituloRequest.getContrato())
                                                            .addValue("P_GRUPO", tituloRequest.getGrupo())
                                                            .addValue("P_CLAVE", tituloRequest.getClave())
                                                            .addValue("P_SENIAL", tituloRequest.getSenial());

        Map<String, Object> out = this.validarDescarmarTitulo.execute(in);

        List<ContratoValidationResult> ret = ((List<ContratoValidationResult>) out.get("P_ERRORES_PREGUNTAS"));

        Date fechaProceso = (Date) out.get("P_FEC_PROC");
        ContratoValidationResult contratoValidationResult;
        contratoValidationResult = new ContratoValidationResult();
        contratoValidationResult.setTipo(TIPO_DATA_FECHA_PROCESO);
        contratoValidationResult.setFecha(fechaProceso);
        ret.add(contratoValidationResult);

        BigDecimal horaProceso = ((BigDecimal) out.get("P_HOR_PROC"));
        contratoValidationResult = new ContratoValidationResult();
        contratoValidationResult.setTipo(TIPO_DATA_HORA_PROCESO);
        if (horaProceso != null) {
            contratoValidationResult.setNroAdvertencia(horaProceso.intValue());
        } else {
            contratoValidationResult.setNroAdvertencia(null);
        }
        ret.add(contratoValidationResult);

        String reRun = (String) out.get("P_RERUN");
        contratoValidationResult = new ContratoValidationResult();
        contratoValidationResult.setTipo(TIPO_RERUN);
        contratoValidationResult.setDescripcion(reRun);
        ret.add(contratoValidationResult);

        String hayCapitulos = (String) out.get("P_FLAG_HAY_CAPITULOS");
        contratoValidationResult = new ContratoValidationResult();
        contratoValidationResult.setTipo(TIPO_FLAG_HAY_CAPITULOS);
        contratoValidationResult.setDescripcion(hayCapitulos);
        ret.add(contratoValidationResult);

        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ContratoValidationResult> validarAdelantarVencimiento(VigenciaRequest vigenciaRequest, String usuario) throws ParseException {
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_CONTRATO", vigenciaRequest.getContrato())
                .addValue("P_GRUPO", vigenciaRequest.getGrupo())
                .addValue("P_CLAVE", vigenciaRequest.getClave())
                .addValue("P_SENIAL", vigenciaRequest.getSenial())
                .addValue("P_FEC_HASTA_PAYTV", new SimpleDateFormat("dd/MM/yyyy").parse(vigenciaRequest.getFechaHastaPayTV()))
                .addValue("P_OBSERVACIONES", vigenciaRequest.getObservaciones());
        
        Map<String, Object> out = this.validarAdelantarVencimiento.execute(in);
    
        List<ContratoValidationResult> ret = ((List<ContratoValidationResult>) out.get("P_ERRORES"));

        return ret;
    }

    @Override
    public List<ContratoValidationResult> validarProcesarPayTV(VigenciaRequest vigenciaRequest, String usuario) throws ParseException {
        MapSqlParameterSource in = new MapSqlParameterSource()
                    .addValue("P_CONTRATO", vigenciaRequest.getContrato())
                    .addValue("P_GRUPO", vigenciaRequest.getGrupo())
                    .addValue("P_SENIAL", vigenciaRequest.getSenial())
                    .addValue("P_CLAVE", vigenciaRequest.getClave())
                    .addValue("P_PAYTV_ID", vigenciaRequest.getPayTVId())
                    .addValue("P_FEC_HASTA_PAYTV", new SimpleDateFormat("dd/MM/yyyy").parse(vigenciaRequest.getFechaHastaPayTV()))
                    .addValue("P_FEC_DESDE_PAYTV", new SimpleDateFormat("dd/MM/yyyy").parse(vigenciaRequest.getFechaDesdePayTV()))
                    .addValue("P_OBSERVACIONES", vigenciaRequest.getObservaciones())
                    .addValue("P_MODO", vigenciaRequest.getModo())
                    .addValue("P_USUARIO", usuario);

        Map<String, Object> out = this.validarProcesarPayTV.execute(in);

        @SuppressWarnings("unchecked")
        List<ContratoValidationResult> ret = ((List<ContratoValidationResult>) out.get("P_ERRORES"));

        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ContratoValidationResult> validarModificarTituloContratadoSC(TituloConGrupo tituloConGrupoRequest, String usuario) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_CONTRATO", tituloConGrupoRequest.getContrato())
                .addValue("P_GRUPO", tituloConGrupoRequest.getNroGrupo())
                .addValue("P_SENIAL_HER", tituloConGrupoRequest.getSenialHeredada())
                .addValue("P_SENIAL", tituloConGrupoRequest.getSenial())
                .addValue("P_CLAVE", tituloConGrupoRequest.getClave())
                
                .addValue("P_PASLIB", tituloConGrupoRequest.getPasaLibreria())
                .addValue("P_FEC_COM_LIB", tituloConGrupoRequest.getFechaComienzoDerechosLibreria())
                .addValue("P_MARPER", tituloConGrupoRequest.getMarcaPerpetuidad())
                .addValue("P_TIPO_DER", tituloConGrupoRequest.getCodigoDerechos())
                .addValue("P_CANT_TIE", tituloConGrupoRequest.getCantTiempo())
                .addValue("P_UNI_TIE", tituloConGrupoRequest.getUnidadDeTiempo())
                .addValue("P_FEC_COM_DER", tituloConGrupoRequest.getFechaComienzoDerechosLibreria())
                .addValue("P_IMP_UNI", tituloConGrupoRequest.getCostoTotal())
                .addValue("P_CANT_PASADAS", tituloConGrupoRequest.getCantPasadas())
                .addValue("P_TIPO_IMPORTE", tituloConGrupoRequest.getTipoImporte());

        Map<String, Object> out = this.validarModificarTituloContratadoSC.execute(in);

        List<ContratoValidationResult> ret = ((List<ContratoValidationResult>) out.get("P_ERRORES"));

        return ret;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public List<ContratoValidationResult> validarReemplazarTitulos(Map data, String usuario) {
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
              .addValue("P_CAPITULO", 0)
              .addValue("P_PARTE", 0)
              .addValue("P_CLAVE", (String)((Map)object).get("clave"));
            insertarTituloReemplazoCall.execute(in);
        }

        in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", contrato)
            .addValue("P_GRUPO", grupo)
            .addValue("P_SENIAL", senial)
            .addValue("P_USUARIO", usuario)
            .addValue("P_SENIAL", senial);
        
        Map<String, Object> out = this.validarTitulosReemplazo.execute(in);

        List<ContratoValidationResult> ret = ((List<ContratoValidationResult>) out.get("P_ERRORES"));

        in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", contrato)
            .addValue("P_GRUPO", grupo)
            .addValue("P_USUARIO", usuario)
            .addValue("P_SENIAL", senial);
        deleteTitulosReemplazoCall.execute(in);

        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ContratoValidationResult> validarReemplazarGrupo(GruposConReemplazoRequest gruposConReemplazoRequest, String usuario) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", gruposConReemplazoRequest.getContrato())
            .addValue("P_GRUPO", gruposConReemplazoRequest.getMayorGrupo())
            .addValue("P_SENIAL", gruposConReemplazoRequest.getSenial())
            .addValue("P_USUARIO", usuario);

        Map<String, Object> out = this.validarReemplazarGrupo.execute(in);
        
        List<ContratoValidationResult> ret = (List<ContratoValidationResult>) out.get("P_ERRORES");

        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ContratoValidationResult> validarAmpliarDerechos(VigenciaRequest vigenciaRequest, String usuario) throws ParseException {
        MapSqlParameterSource in = new MapSqlParameterSource()
                        .addValue("P_CONTRATO", vigenciaRequest.getContrato())
                        .addValue("P_GRUPO", vigenciaRequest.getGrupo())
                        .addValue("P_SENIAL", vigenciaRequest.getSenial())
                        .addValue("P_CLAVE", vigenciaRequest.getClave())
                        .addValue("P_FEC_HASTA_PAYTV", new SimpleDateFormat("dd/MM/yyyy").parse(vigenciaRequest.getFechaHastaPayTV()))
                        .addValue("P_OBSERVACIONES", vigenciaRequest.getObservaciones())
                        .addValue("P_USUARIO", usuario);
        Map<String, Object> out = this.validarAmpliarDerechos.execute(in);
        
        List<ContratoValidationResult> ret = ((List<ContratoValidationResult>) out.get("P_ERRORES"));
        
        return ret;
    }
    
    @Override
    public List<ContratoValidationResult> validarExtenderChequeoTecnico(Integer claveContrato, String usuario) {
        MapSqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", claveContrato);

        Map<String, Object> out = this.validarExtenderChequeoTecnicoCall.execute(in);

        List<ContratoValidationResult> ret = new ArrayList<ContratoValidationResult>();

        String huboError = (String) out.get("P_ERROR");
        ContratoValidationResult foo = new ContratoValidationResult();
        foo.setTipo("P_OK");
        foo.setDescripcion(huboError);
        ret.add(foo);
        
        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ContratoValidationResult> validarEliminarGrupo(Integer claveContrato, Integer claveGrupo, String senial, String usuario) {
        MapSqlParameterSource in = new MapSqlParameterSource()
                                                    .addValue("P_CONTRATO", claveContrato)
                                                    .addValue("P_GRUPO", claveGrupo)
                                                    .addValue("P_SENIAL", senial);
        
        Map<String, Object> out = this.validarEliminarGrupoCall.execute(in);
    
        List<ContratoValidationResult> ret = ((List<ContratoValidationResult>) out.get("P_ERRORES"));
        
        return ret;
    }

    private Date parseDate(String fecha, String format) throws ParseException {
        if ( (fecha != null) && (!StringUtils.isEmpty(fecha)) ) {
            return new SimpleDateFormat(format).parse(fecha);
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ContratoValidationResult> validarCabeceraSenial(ContratoValidationRequest contratoValidationRequest) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", contratoValidationRequest.getContrato())
            .addValue("P_SENIAL", contratoValidationRequest.getSenial());
        Map<String, Object> out = this.validarCabeceraSenialCall.execute(in);
        return (List<ContratoValidationResult>) out.get("P_ERRORES");
    }

}