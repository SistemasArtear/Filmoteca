package com.artear.filmo.daos.impl.pl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.artear.filmo.bo.common.Distribuidor;
import com.artear.filmo.bo.common.Moneda;
import com.artear.filmo.bo.common.Senial;
import com.artear.filmo.bo.contratos.Capitulo;
import com.artear.filmo.bo.contratos.Contrato;
import com.artear.filmo.bo.contratos.ContratoConReRun;
import com.artear.filmo.bo.contratos.DameTGRequest;
import com.artear.filmo.bo.contratos.DameTGResponse;
import com.artear.filmo.bo.contratos.Grupo;
import com.artear.filmo.bo.contratos.GruposConReemplazoRequest;
import com.artear.filmo.bo.contratos.ObservacionDePago;
import com.artear.filmo.bo.contratos.SenialImporte;
import com.artear.filmo.bo.contratos.TipoDerecho;
import com.artear.filmo.bo.contratos.TipoDerechoTerritorial;
import com.artear.filmo.bo.contratos.TipoImporte;
import com.artear.filmo.bo.contratos.TipoTitulo;
import com.artear.filmo.bo.contratos.Titulo;
import com.artear.filmo.bo.contratos.TituloConGrupo;
import com.artear.filmo.bo.contratos.TituloContratado;
import com.artear.filmo.bo.contratos.TituloRequest;
import com.artear.filmo.bo.contratos.TitulosRecontratadosRequest;
import com.artear.filmo.bo.contratos.TitulosRequest;
import com.artear.filmo.bo.contratos.Vigencia;
import com.artear.filmo.bo.contratos.VigenciaRequest;
import com.artear.filmo.daos.interfaces.ModificarContratoDao;

@Repository("modificarContratoDao")
public class ModificarContratoDaoPL implements ModificarContratoDao {

    private SimpleJdbcCall dameContratoCall;
    private SimpleJdbcCall dameDistribuidoresPorNombreCall;
    private SimpleJdbcCall dameContratoPorDistribuidorCall;
    private SimpleJdbcCall dameCabeceraCall;
    private SimpleJdbcCall dameObservacionesDePagoCall;
    private SimpleJdbcCall dameMonedasCall;
    private SimpleJdbcCall dameSenialImporteCall;
    private SimpleJdbcCall dameGruposCall;
    private SimpleJdbcCall dameGrupoCall;
    private SimpleJdbcCall dameSenialesHeredadasAsignadasCall;
    private SimpleJdbcCall dameTiposDeImporteCall;
    private SimpleJdbcCall dameTiposDeDerechosCall;
    private SimpleJdbcCall dameTiposDeDerechosTerritorialesCall;
    private SimpleJdbcCall dameTiposDeTitulosCall;
    private SimpleJdbcCall dameTitulosCastellanoCall;
    private SimpleJdbcCall dameTitulosOriginalCall;
    private SimpleJdbcCall dameTitulosAEliminarPorContratoCall;
    private SimpleJdbcCall dameTitulosAReemplazarPorContratoCall;
    private SimpleJdbcCall dameCapitulosParaEliminar;
    private SimpleJdbcCall dameCapitulosParaAgregar;
    private SimpleJdbcCall dameNombreDelTituloCall;
    private SimpleJdbcCall dameTitulosPorContratoCall;
    private SimpleJdbcCall dameTitulosARecontratarCall;
    private SimpleJdbcCall dameCapitulosARecontratarCall;
    private SimpleJdbcCall dameContratosConReRunCall;
    private SimpleJdbcCall dameCapitulosPorTituloContratadoCall;
    private SimpleJdbcCall dameTituloCall;
    private SimpleJdbcCall dameVigenciasPorTituloContratadoCall;
    private SimpleJdbcCall dameSenialesHeredadasCall;
    private SimpleJdbcCall dameSiguienteNumeroGrupoCall;
    private SimpleJdbcCall dameTituloContratadoCall;
    private SimpleJdbcCall dameGruposConReemplazoCall;
    private SimpleJdbcCall dameClaveCall;
    private SimpleJdbcCall damePayTVCall;
    private SimpleJdbcCall dameTGCall;
    private SimpleJdbcCall dameTitulosConReRun;
    private SimpleJdbcCall dameSenialesChequeo;
    
    @Autowired
    public ModificarContratoDaoPL(DataSource dataSource) {
        super();
        this.dameContratoCall = buildDameContratoCall(dataSource);
        this.dameContratoPorDistribuidorCall = buildDameContratoPorDistribuidorCall(dataSource);
        this.dameDistribuidoresPorNombreCall = buildDameDistribuidoresPorNombreCall(dataSource);
        this.dameCabeceraCall = buildDameCabeceraCall(dataSource);
        this.dameObservacionesDePagoCall = buildDameObservacionesDePagoCall(dataSource);
        this.dameMonedasCall = buildDameMonedasCall(dataSource);
        this.dameSenialImporteCall = buildDameSenialImporteCall(dataSource);
        this.dameGruposCall = buildDameGruposCall(dataSource);
        this.dameGrupoCall = buildDameGrupoCall(dataSource);
        this.dameSenialesHeredadasAsignadasCall = buildDameSenialesHeredadasAsignadasCall(dataSource);
        this.dameTiposDeImporteCall = buildDameTiposDeImporteCall(dataSource);
        this.dameTiposDeDerechosCall = buildDameTiposDeDerechosCall(dataSource);
        this.dameTiposDeDerechosTerritorialesCall = buildDameTiposDeDerechosTerritorialesCall(dataSource);
        this.dameTiposDeTitulosCall = buildDameTiposDeTitulosCall(dataSource);
        this.dameTitulosCastellanoCall = buildDameTitulosCastellanoCall(dataSource);
        this.dameTitulosOriginalCall = buildDameTitulosOriginalCall(dataSource);
        this.dameTitulosAEliminarPorContratoCall = buildDameTitulosAEliminarPorContratoCall(dataSource);
        this.dameTitulosAReemplazarPorContratoCall = buildDameTitulosAReemplazarPorContratoCall(dataSource);
        this.dameCapitulosParaEliminar = buildDameCapitulosParaEliminarCall(dataSource);
        this.dameCapitulosParaAgregar = buildDameCapitulosParaAgregarCall(dataSource);
        this.dameTitulosPorContratoCall = buildDameTitulosPorContratoCall(dataSource);
        this.dameTitulosARecontratarCall = buildDameTitulosARecontratarCall(dataSource);
        this.dameCapitulosARecontratarCall = buildDameCapitulosARecontratarCall(dataSource);
        this.dameContratosConReRunCall = buildDameContratosConReRunCall(dataSource);
        this.dameCapitulosPorTituloContratadoCall = buildDameCapitulosPorTituloContratadoCall(dataSource);
        this.dameTituloCall = builDameTituloCall(dataSource);
        this.dameVigenciasPorTituloContratadoCall = buildDameVigenciasPorTituloContratadoCall(dataSource);
        this.dameSenialesHeredadasCall = buildDameSenialesHeredadasCall(dataSource);
        this.dameSiguienteNumeroGrupoCall = buildDameSiguienteNumeroGrupoCall(dataSource);
        this.dameTituloContratadoCall = buildTituloContratadoCall(dataSource);
        this.dameGruposConReemplazoCall = buildDameGruposConReemplazo(dataSource);
        this.dameClaveCall = buildDameClaveCall(dataSource);
        this.damePayTVCall = buildDamePayTVCall(dataSource);
        this.dameTGCall = buildDameTGCall(dataSource);
        this.dameTitulosConReRun = buildDameTitulosConReRunCall(dataSource);
        this.dameSenialesChequeo = buildDameSenialesChequeo(dataSource);
        this.dameNombreDelTituloCall = buildDameNombreDelTitulo(dataSource);
    }

    private SimpleJdbcCall buildDameNombreDelTitulo(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS").withProcedureName("PR_OBTENER_TITULO");
    }

    private SimpleJdbcCall buildDameSenialesChequeo(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_1X")
                .withProcedureName("PR_SENIALES_CHEQUEO")
                .returningResultSet("P_SENIALES", new RowMapper<Senial>() {
                    public Senial mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Senial ret = new Senial();
                        ret.setCodigo(rs.getString("CODIGO"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildDameTitulosConReRunCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_1X")
                .withProcedureName("PR_TITULOS_RERUN")
                .returningResultSet("P_TITULOS_RERUN", new RowMapper<TituloContratado>() {
                    public TituloContratado mapRow(ResultSet rs, int rowNum) throws SQLException {
                        TituloContratado ret = new TituloContratado();
                        ret.setContrato(rs.getInt("CONTRATO"));
                        ret.setSenial(rs.getString("SENIAL"));
                        ret.setGrupo(rs.getInt("GRUPO"));
                        ret.setClave(rs.getString("CLAVE"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildDameTGCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X")
                .withProcedureName("PR_LECTURA_TG")
                .returningResultSet("P_DATOS", new RowMapper<DameTGResponse>() {
                    public DameTGResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                        DameTGResponse ret = new DameTGResponse();
                        ret.setUsuario(rs.getString("USUARIO"));
                        ret.setWorkstation(rs.getString("WORKSTATION"));
                        ret.setFecha(rs.getDate("FECHA"));
                        ret.setHora(rs.getInt("HORA"));
                        ret.setRenglon(rs.getInt("RENGLON"));
                        ret.setCapitulo(rs.getString("CAPITULO"));
                        ret.setParte(rs.getString("PARTE"));
                        ret.setConfirmaRemito(rs.getString("CONF_REMI"));
                        ret.setConfirmaCopia(rs.getString("CONF_COPIA"));
                        ret.setNumeroRemito(rs.getString("NUM_REM"));
                        ret.setNumeroGuia(rs.getString("NUM_GUI"));
                        ret.setFechaMovimiento(rs.getString("FEC_MOV"));
                        ret.setFechaRemitoGuia(rs.getString("FEC_REM_GUI"));
                        ret.setChequeoVolver(rs.getString("CHEQUEO_VOLVER"));
                        ret.setCopiaVolver(rs.getString("COPIA_VOLVER"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildDamePayTVCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS")
                .withProcedureName("PR_DAME_PAYTV")
                .returningResultSet("P_DATOS", new RowMapper<Vigencia>() {
                    public Vigencia mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Vigencia ret = new Vigencia();
                        ret.setFechaDesdePay(rs.getDate("FECHA_DESDE"));
                        ret.setFechaHastaPay(rs.getDate("FECHA_HASTA"));
                        ret.setObservaciones(rs.getString("OBSERVACIONES"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildDameClaveCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X")
                .withProcedureName("PR_DAME_CLAVE");
    }

    private SimpleJdbcCall buildDameGruposConReemplazo(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X")
                .withProcedureName("PR_GRUPOS_CON_REEMPLAZO")
                .returningResultSet("P_GRUPOS", new RowMapper<Grupo>() {
                    @Override
                    public Grupo mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Grupo ret = new Grupo();
                        ret.setContrato(rs.getInt("CONTRATO"));
                        ret.setNroGrupo(rs.getInt("GRUPO"));
                        ret.setSenial(rs.getString("SENIAL"));
                        ret.setCostoTotal(rs.getBigDecimal("IMPORTE_GRUPO"));
                        ret.setPuedeReemplazar(rs.getString("PUEDE_REEMPLAZAR"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildTituloContratadoCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X")
                .withProcedureName("PR_DAME_TIT_CONTRATADO_SC_CC")
                .returningResultSet("P_DATOS", new RowMapper<TituloConGrupo>() {
                    @Override
                    public TituloConGrupo mapRow(ResultSet rs, int rowNum) throws SQLException {
                        TituloConGrupo tituloConGrupo = new TituloConGrupo();
                        tituloConGrupo.setContrato(rs.getInt("CONTRATO"));
                        tituloConGrupo.setDistribuidor(rs.getInt("DISTRIBUIDOR"));
                        tituloConGrupo.setNroGrupo(rs.getInt("GRUPO"));
                        tituloConGrupo.setRazonSocial(rs.getString("RAZON_SOCIAL"));
                        tituloConGrupo.setSenial(rs.getString("SENIAL"));
                        tituloConGrupo.setSenialHeredada(rs.getString("SENIALES_HEREDADAS"));
                        tituloConGrupo.setNombreGrupo(rs.getString("NOMBRE_GRUPO"));
                        tituloConGrupo.setFormula(rs.getString("FORMULA"));
                        tituloConGrupo.setCantPasadas(rs.getInt("CANT_PASADAS"));
                        tituloConGrupo.setPasadasPendientes(rs.getInt("PASADAS_PENDIENTES"));
                        tituloConGrupo.setTipoImporte(rs.getString("TIPO_IMPORTE"));
                        tituloConGrupo.setCostoTotal(rs.getBigDecimal("COSTO_UNITARIO"));
                        tituloConGrupo.setEr(rs.getString("ESTRENO_REPETICION"));
                        tituloConGrupo.setFechaDerechos(rs.getDate("FECHA_COM_DER"));
                        tituloConGrupo.setFechaEntrega(rs.getDate("FECHA_POS_ENTREGA"));
                        tituloConGrupo.setCodigoDerechos(rs.getString("TIPO_DER"));
                        tituloConGrupo.setCantTiempo(rs.getInt("CANT_TIEMPO"));
                        tituloConGrupo.setUnidadDeTiempo(rs.getString("UNI_TIEMPO"));
                        tituloConGrupo.setCodigoDerechosTerritoriales(rs.getString("DER_TER"));
                        tituloConGrupo.setDescripcionDerechosTerritoriales(rs.getString("DESCRIP_DER_TER"));
                        tituloConGrupo.setMarcaPerpetuidad(rs.getString("MARCA_PERPETUIDAD"));
                        tituloConGrupo.setRecontratacion(rs.getString("RECONTRATACION"));
                        tituloConGrupo.setCantTiempoExclusivo(rs.getInt("CANT_TIEMPO_EXCLUSIV"));
                        tituloConGrupo.setUnidadTiempoExclusivo(rs.getString("UNI_TIEMPO_EXCLUSIV"));
                        tituloConGrupo.setAutorizacionAutor(rs.getString("AUT_AUTOR"));
                        tituloConGrupo.setPagaArgentores(rs.getString("PAGA_ARGENTORES"));
                        tituloConGrupo.setPasaLibreria(rs.getString("PASA_LIBRERIA"));
                        tituloConGrupo.setFechaComienzoDerechosLibreria(rs.getDate("FECHA_COM_DER_LIB"));
                        tituloConGrupo.setTipoTitulo(rs.getString("TIPO_TITULO"));
                        tituloConGrupo.setFechaDesde(rs.getDate("FECHA_DESDE"));
                        tituloConGrupo.setFechaHasta(rs.getDate("FECHA_HASTA"));
                        tituloConGrupo.setObservaciones(rs.getString("OBSERVACIONES"));
                        tituloConGrupo.setClave(rs.getString("CLAVE"));
                        tituloConGrupo.setTituloOriginal(rs.getString("TITULO_ORIGINAL"));
                        tituloConGrupo.setTituloCastellano(rs.getString("TITULO_CASTELLANO"));
                        tituloConGrupo.setPermiteEditarPasaLibreria(rs.getString("EDITA_PASA_LIBRERIA").equals("S"));
                        tituloConGrupo.setPermiteRecontratar(rs.getString("EDITA_RECONTRATAR").equals("S"));
                        return tituloConGrupo;
                    }
                });
    }

    private SimpleJdbcCall buildDameSiguienteNumeroGrupoCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X").withProcedureName("PR_OBTENER_SIG_NRO_GRUPO");
    }

    private SimpleJdbcCall buildDameSenialesHeredadasCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X")
                .withProcedureName("PR_OBTENER_SH")
                .returningResultSet("P_DATOS", new RowMapper<Senial>() {
                    @Override
                    public Senial mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Senial ret = new Senial();
                        ret.setCodigo(rs.getString("SENIAL_HEREDADA"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildDameVigenciasPorTituloContratadoCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_1X")
                .withProcedureName("PR_DAME_VIGENCIAS")
                .returningResultSet("P_DATOS", new RowMapper<Vigencia>() {
                    public Vigencia mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Vigencia ret = new Vigencia();
                        ret.setTipoModifVigencia(rs.getString("TIPO_VIGENCIA"));
                        ret.setDescripTipoModifVigencia(rs.getString("OBSERVACIONES"));
                        try {
                            ret.setFechaDesdePay(rs.getDate("FECHA_DESDE_PAYTV").toString().equals("0001-01-01") ? null : convertDate(rs, "FECHA_DESDE_PAYTV"));
                            ret.setFechaHastaPay(rs.getDate("FECHA_HASTA_PAYTV").toString().equals("0001-01-01") ? null : convertDate(rs, "FECHA_HASTA_PAYTV"));
                            ret.setFechaAnteriorDesde(rs.getDate("FECHA_ANTERIOR").toString().equals("0001-01-01") ? null : convertDate(rs, "FECHA_ANTERIOR"));
                            ret.setFechaAnteriorHasta(rs.getDate("DESDE_HASTA_ANTERIOR").toString().equals("0001-01-01") ? null : convertDate(rs, "DESDE_HASTA_ANTERIOR"));
                            ret.setFechaNuevaDesde(rs.getDate("FECHA_NUEVA").toString().equals("0001-01-01") ? null : convertDate(rs, "FECHA_NUEVA"));
                            ret.setFechaNuevaHasta(rs.getDate("DESDE_HASTA_NUEVA").toString().equals("0001-01-01") ? null : convertDate(rs, "DESDE_HASTA_NUEVA"));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        ret.setPayAnulado(rs.getString("PAY_ANU"));
                        ret.setPuedeBorrar(rs.getString("PUEDE_BORRAR"));
                        ret.setPayTVId(rs.getInt("ID_PAYTV"));
                        return ret;
                    }

                    @SuppressWarnings("deprecation")
                    private Date convertDate(ResultSet rs, String field) throws ParseException, SQLException {
                        Date ret = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(rs.getDate(field).toString() + " 12:00:00");
                        ret.setHours(12);
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall builDameTituloCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X")
                .withProcedureName("PR_DAME_TITULO")
                .returningResultSet("P_DATOS", new RowMapper<Titulo>() {
                    public Titulo mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Titulo ret = new Titulo();
                        ret.setClave(rs.getString("CLAVE"));
                        ret.setTituloOriginal(rs.getString("TITULO_ORIGINAL"));
                        ret.setTituloCastellano(rs.getString("TITULO_CASTELLANO"));
                        ret.setCalificacionOficial(rs.getString("CALIFICACION_OFICIAL"));
                        ret.setActores1(rs.getString("ACTOR_1"));
                        ret.setActores2(rs.getString("ACTOR_2"));
                        ret.setRecontratacion(rs.getString("RECONTRATACION"));
                        ret.setEditaFechaPosibleEntrega(rs.getString("EDITA_FECHA_POSIBLE_ENTREGA"));
                        ret.setEditaMarcaRecontratacion(rs.getString("EDITA_MARCA_RECONTRATACION"));
                        ret.setOrigen(rs.getString("ORIGEN"));
                        ret.setSinContrato(rs.getString("SIN_CONTRATO"));
                        ret.setAlertaTitulo(rs.getString("ALERTA_TITULO"));
                        ret.setIdFicha(rs.getString("ID_FICHA"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildDameCapitulosPorTituloContratadoCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X")
                .withProcedureName("PR_DAME_CAPITULOS_TITULO")
                .returningResultSet("P_DATOS", new RowMapper<Capitulo>() {
                    public Capitulo mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Capitulo ret = new Capitulo();
                        ret.setNroCapitulo(rs.getInt("CAPITULO"));
                        ret.setParte(rs.getInt("PARTE"));
                        ret.setTituloCapitulo(rs.getString("TITULO_CAPITULO"));
                        ret.setConfExhibicion(rs.getString("EXH_CONFIRMADA"));
                        ret.setCopia(rs.getString("COPIA"));
                        ret.setOriginal(rs.getString("ORIGINAL"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildDameContratosConReRunCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X")
                .withProcedureName("PR_LEER_DATOS_GRILLA_RERUN")
                .returningResultSet("P_DATOS", new RowMapper<ContratoConReRun>() {
                    @Override
                    public ContratoConReRun mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ContratoConReRun ret = new ContratoConReRun();
                        ret.setContrato(rs.getInt("CONTRATO"));
                        ret.setGrupo(rs.getInt("GRUPO"));
                        ret.setDistribuidor(rs.getInt("DISTRIBUIDOR"));
                        ret.setVigenciaDesde(rs.getString("VIGENCIA_DESDE"));
                        ret.setVigenciaHasta(rs.getString("VIGENCIA_HASTA"));
                        ret.setEnlazadoAnterior(rs.getInt("ENLAZADO_ANTERIOR"));
                        ret.setGrupoAnterior(rs.getInt("GRUPO_ANTERIOR"));
                        ret.setEnlazadoPosterior(rs.getInt("ENLAZADO_POSTERIOR"));
                        ret.setGrupoPosterior(rs.getInt("GRUPO_POSTERIOR"));
                        ret.setPuedeAnterior(rs.getString("PUEDE_ANTERIOR"));
                        ret.setPuedePosterior(rs.getString("PUEDE_POSTERIOR"));
                        ret.setPuedeDesenlazar(rs.getString("PUEDE_DESENLAZAR"));
                        return ret;
                    }
                });
    }

    
    private SimpleJdbcCall buildDameCapitulosARecontratarCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X")
                .withProcedureName("PR_OBTENER_CAPITULOS")
                .returningResultSet("P_DATOS", new RowMapper<Capitulo>() {
                    @Override
                    public Capitulo mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Capitulo ret = new Capitulo();
                        ret.setNroCapitulo(rs.getInt("NRO_CAPITULO"));
                        ret.setParte(rs.getInt("PARTE"));
                        ret.setTituloCapitulo(rs.getString("TITULO_CAPITULO"));
                        ret.setSeleccionado(rs.getString("SELECCIONADO"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildDameTitulosARecontratarCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X")
                .withProcedureName("PR_OBTENER_TITULOS_CC")
                .returningResultSet("P_DATOS", new RowMapper<TituloContratado>() {
                    @Override
                    public TituloContratado mapRow(ResultSet rs, int rowNum) throws SQLException {
                        TituloContratado ret = new TituloContratado();
                        ret.setClave(rs.getString("CLAVE"));
                        ret.setContrato(rs.getInt("CONTRATO"));
                        ret.setGrupo(rs.getInt("GRUPO"));
                        ret.setFormula(rs.getString("FORMULA"));
                        //ret.setTituloCastellano(rs.getString("TITULO_ORIGINAL"));
                        ret.setPuedeElegirTodos(rs.getString("PUEDE_ELEGIR_TODOS"));
                        ret.setPuedeElegirParcial(rs.getString("PUEDE_ELEGIR_PARCIAL"));
                        ret.setPuedeElegir(rs.getString("PUEDE_ELEGIR"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildDameTitulosPorContratoCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X")
                .withProcedureName("PR_TITULOS_PARA_TRABAJAR")
                .returningResultSet("P_DATOS", new RowMapper<TituloContratado>() {
                    @Override
                    public TituloContratado mapRow(ResultSet rs, int rowNum) throws SQLException {
                        TituloContratado ret = new TituloContratado();
                        ret.setClave(rs.getString("CLAVE"));
                        ret.setTituloCastellano(rs.getString("TITULO_CASTELLANO"));
                        ret.setProgramado(rs.getString("PROGRAMADO"));
                        ret.setExhibicionConfirmada(rs.getString("EXH_CONFIRMADA"));
                        ret.setaConsultar(rs.getString("TITULO_A_CONSULTAR"));
                        ret.setReRun(rs.getString("RERUN"));
                        ret.setRecibido(rs.getString("RECIBIDO"));
                        ret.setStandby(rs.getString("STDBY"));
                        ret.setGastos(rs.getString("GASTOS"));
                        ret.setTipoCosteo(rs.getString("TIPO_COSTEO"));
                        ret.setCanje(rs.getString("CANJE"));
                        //ret.setPuedeHacerReRun(rs.getString("PUEDE_HACER_RERUN"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildDameCapitulosParaAgregarCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X")
                .withProcedureName("PR_CAPITULOS_PARA_AGREGAR")
                .returningResultSet("P_DATOS", new RowMapper<Capitulo>() {
                    @Override
                    public Capitulo mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Capitulo ret = new Capitulo();
                        ret.setClave(rs.getString("CLAVE"));
                        ret.setNroCapitulo(rs.getInt("NRO_CAPITULO"));
                        ret.setParte(rs.getInt("PARTE"));
                        ret.setTituloCapitulo(rs.getString("TITULO_CAPITULO"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildDameCapitulosParaEliminarCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X")
                .withProcedureName("PR_CAPITULOS_PARA_BORRAR")
                .returningResultSet("P_DATOS", new RowMapper<Capitulo>() {
                    @Override
                    public Capitulo mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Capitulo ret = new Capitulo();
                        ret.setClave(rs.getString("CLAVE"));
                        ret.setNroCapitulo(rs.getInt("CAPITULO"));
                        ret.setParte(rs.getInt("PARTE"));
                        ret.setTituloCapitulo(rs.getString("DESCRIP_CAPITULO"));
                        ret.setProgramado(rs.getString("PROGRAMADO"));
                        ret.setConfExhibicion(rs.getString("EXH_CONFIRMADA"));
                        ret.setContRec(rs.getString("RECEPCION_CONTAB"));
                        ret.setPuedoBorrar(rs.getString("PUEDO_BORRAR"));
                        return ret;
                    }
                });
    }

    

    private SimpleJdbcCall buildDameTitulosAReemplazarPorContratoCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X")
                .withProcedureName("PR_TITULOS_POSIBLE_REEMPLAZO")
                .returningResultSet("P_DATOS", new RowMapper<TituloContratado>() {
                    @Override
                    public TituloContratado mapRow(ResultSet rs, int rowNum) throws SQLException {
                        TituloContratado ret = new TituloContratado();
                        ret.setClave(rs.getString("CLAVE"));
                        ret.setTituloCastellano(rs.getString("TITULO_CASTELLANO"));
                        ret.setGrupo(rs.getInt("GRUPO"));
                        ret.setProgramado(rs.getString("PROGRAMADO"));
                        ret.setExhibicionConfirmada(rs.getString("EXH_CONFIRMADA"));
                        ret.setaConsultar(rs.getString("TITULO_A_CONSULTAR"));
                        //TODO
                        /* puede_reemplazar   VARCHAR2 */
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildDameTitulosAEliminarPorContratoCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_2X")
                .withProcedureName("PR_TITULOS_SC_PARA_BORRAR")
                .returningResultSet("P_DATOS", new RowMapper<TituloContratado>() {
                    @Override
                    public TituloContratado mapRow(ResultSet rs, int rowNum) throws SQLException {
                        TituloContratado ret = new TituloContratado();
                        ret.setClave(rs.getString("CLAVE"));
                        ret.setTituloCastellano(rs.getString("TITULO_CASTELLANO"));
                        ret.setProgramado(rs.getString("PROGRAMADO"));
                        ret.setExhibicionConfirmada(rs.getString("EXHIBICION_CONFIRMADA"));
                        ret.setaConsultar(rs.getString("TITULO_A_CONSULTAR"));
                        ret.setReRun(rs.getString("RERUN"));
                        ret.setGastos(rs.getString("GASTOS"));
                        //TODO el PUEDE BORRAR
                        //ret.setPuedeElegir(rs.getString("puedo_borrar"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildDameTitulosCastellanoCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X")
                .withProcedureName("PR_LISTA_TITULOS_CASTELLANO")
                .returningResultSet("P_DATOS", new RowMapper<Titulo>() {
                    @Override
                    public Titulo mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Titulo ret = new Titulo();
                        ret.setClave(rs.getString("CLAVE"));
                        ret.setActores(rs.getString("ACTORES"));
                        ret.setTitulo(rs.getString("TITULO_CASTELLANO"));
                        ret.setSinContrato(rs.getString("CONTRATO_SN"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildDameTitulosOriginalCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS_0X")
                .withProcedureName("PR_LISTA_TITULOS_ORIGINAL")
                .returningResultSet("P_DATOS", new RowMapper<Titulo>() {
                    @Override
                    public Titulo mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Titulo ret = new Titulo();
                        ret.setClave(rs.getString("CLAVE"));
                        ret.setActores(rs.getString("ACTORES"));
                        ret.setTitulo(rs.getString("TITULO_ORIGINAL"));
                        ret.setSinContrato(rs.getString("CONTRATO_SN"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildDameTiposDeTitulosCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS")
                .withProcedureName("PR_12040_TIPO_TITULOS")
                .returningResultSet("P_DATOS", new RowMapper<TipoTitulo>() {
                    @Override
                    public TipoTitulo mapRow(ResultSet rs, int rowNum) throws SQLException {
                        TipoTitulo ret = new TipoTitulo();
                        ret.setTipoTitulo(rs.getString("TIPO_TITULO"));
                        ret.setDescripcionTitulo(rs.getString("DESCRIP_TIPO_TITULO"));
                        ret.setFamiliaTitulo(rs.getString("FAMILIA_TITULO"));
                        ret.setUltimoNumero(rs.getInt("ULTIMO_NUMERO"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildDameTiposDeDerechosTerritorialesCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS")
                .withProcedureName("PR_DER_TERRITORIALES")
                .returningResultSet("P_DATOS", new RowMapper<TipoDerechoTerritorial>() {
                    @Override
                    public TipoDerechoTerritorial mapRow(ResultSet rs, int rowNum) throws SQLException {
                        TipoDerechoTerritorial ret = new TipoDerechoTerritorial();
                        ret.setCodigo(rs.getString("CODIGO"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildDameTiposDeDerechosCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS")
                .withProcedureName("PR_TIPO_DERECHO")
                .returningResultSet("P_DATOS", new RowMapper<TipoDerecho>() {
                    @Override
                    public TipoDerecho mapRow(ResultSet rs, int rowNum) throws SQLException {
                        TipoDerecho ret = new TipoDerecho();
                        ret.setCodigo(rs.getString("CODIGO"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildDameTiposDeImporteCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS")
                .withProcedureName("PR_TIPO_IMPORTE")
                .returningResultSet("P_DATOS", new RowMapper<TipoImporte>() {
                    @Override
                    public TipoImporte mapRow(ResultSet rs, int rowNum) throws SQLException {
                        TipoImporte ret = new TipoImporte();
                        ret.setCodigo(rs.getString("CODIGO"));
                        ret.setDescripcion(rs.getString("DESCRIPCION"));
                        return ret;
                    }
                });
    }

    private SimpleJdbcCall buildDameSenialesHeredadasAsignadasCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS")
                .withProcedureName("PR_FP12009")
                .returningResultSet("P_DATOS", new RowMapper<Senial>() {
                    @Override
                    public Senial mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Senial senial = new Senial();
                        senial.setCodigo(rs.getString("CODIGO"));
                        senial.setDescripcion(rs.getString("DESCRIPCION"));
                        return senial;
                    }
                });
    }

    private SimpleJdbcCall buildDameGrupoCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS")
                .withProcedureName("PR_FP12007_08")
                .returningResultSet("P_DATOS", new RowMapper<Grupo>() {
                    @Override
                    public Grupo mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Grupo grupo = new Grupo();
                        grupo.setContrato(rs.getInt("CONTRATO"));
                        grupo.setDistribuidor(rs.getInt("DISTRIBUIDOR"));
                        grupo.setRazonSocial(rs.getString("RAZON_SOCIAL"));
                        grupo.setSenial(rs.getString("SENIAL"));
                        grupo.setMontoSenial(rs.getBigDecimal("MONTO_SENIAL"));
                        grupo.setMoneda(rs.getString("MONEDA"));
                        grupo.setNroGrupo(rs.getInt("NRO_GRUPO"));
                        grupo.setTipoTitulo(rs.getString("TIPO_TITULO"));
                        grupo.setSenialHeredada(rs.getString("SENIAL_HEREDADA"));
                        grupo.setNombreGrupo(rs.getString("NOMBRE_GRUPO"));
                        grupo.setCantTitulos(rs.getInt("CANT_1"));
                        grupo.setCantPasadas(rs.getInt("CANT_2"));
                        grupo.setTipoImporte(rs.getString("TIPO_IMPORTE"));
                        grupo.setCostoTotal(rs.getBigDecimal("COSTO_TOTAL"));
                        grupo.setEr(rs.getString("ESTRENO_REPET"));
                        grupo.setFechaDerechos(rs.getDate("FECHA_COM_DERECHOS"));
                        grupo.setFechaEntrega(rs.getDate("FECHA_POSIBLE_ENTREGA"));
                        grupo.setCodigoDerechos(rs.getString("CODIGO_COM_DERECHOS"));
                        grupo.setCantTiempo(rs.getInt("CANT_TIEMPO"));
                        grupo.setUnidadDeTiempo(rs.getString("UNIDAD_TIEMPO"));
                        grupo.setCodigoDerechosTerritoriales(rs.getString("CODIGO_DERECHOS_TERR"));
                        grupo.setDescripcionDerechosTerritoriales(rs.getString("DESCRIP_DERECHOS_TERR"));
                        grupo.setMarcaPerpetuidad(rs.getString("MARCA_PERPETUIDAD"));
                        grupo.setPlanEntrega(rs.getString("PLAN_ENTREGA"));
                        grupo.setRecontratacion(rs.getString("RECONTRATACION"));
                        grupo.setCantTiempoExclusivo(rs.getInt("CANT_TIEMPO_EXCLUSIV"));
                        grupo.setUnidadTiempoExclusivo(rs.getString("UNIDAD_TIEMPO_EXCLUSIV"));
                        grupo.setAutorizacionAutor(rs.getString("AUTORIZACION_AUTOR"));
                        grupo.setPagaArgentores(rs.getString("PAGA_ARGENTORES"));
                        grupo.setPasaLibreria(rs.getString("PASA_LIBRERIA"));
                        grupo.setFechaComienzoDerechosLibreria(rs.getDate("FECHA_COMIENZO_DER_LIB"));
                        grupo.setFlagOutput(rs.getString("FLAG_OUTPUT"));
                        return grupo;
                    }
                });
    }

    private SimpleJdbcCall buildDameGruposCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS")
                .withProcedureName("PR_GRILLA_GRUPOS")
                .returningResultSet("P_DATOS", new RowMapper<Grupo>() {
                    @Override
                    public Grupo mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Grupo grupo = new Grupo();
                        grupo.setNroGrupo(rs.getInt("NRO_GRUPO"));
                        grupo.setNombreGrupo(rs.getString("NOMBRE_GRUPO"));
                        grupo.setTipoTitulo(rs.getString("TIPO_TITULO"));
                        grupo.setCostoTotal(rs.getBigDecimal("IMPORTE_GRUPO"));
                        grupo.setEr(rs.getString("E_R"));
                        grupo.setSenialHeredada(rs.getString("SENIAL_HEREDADA"));
                        return grupo;
                    }
                });
    }

    private SimpleJdbcCall buildDameSenialImporteCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS")
                .withProcedureName("PR_OBTENER_SENIAL_IMPORTE")
                .returningResultSet("P_DATOS", new RowMapper<SenialImporte>() {
                    @Override
                    public SenialImporte mapRow(ResultSet rs, int rowNum) throws SQLException {
                        SenialImporte senialImporte = new SenialImporte();
                        senialImporte.setCodigoSenial(rs.getString("CODIGO_SENIAL"));
                        senialImporte.setContrato(rs.getInt("CONTRATO"));
                        senialImporte.setDescripcionSenial(rs.getString("DESCRIPCION_SENIAL"));
                        senialImporte.setImporte(rs.getBigDecimal("IMPORTE"));
                        return senialImporte;
                    }
                });
    }

    private SimpleJdbcCall buildDameMonedasCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS")
                .withProcedureName("PR_MONEDAS")
                .returningResultSet("P_DATOS", new RowMapper<Moneda>() {
                    @Override
                    public Moneda mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Moneda moneda = new Moneda();
                        moneda.setCodigo(rs.getString("CODIGO"));
                        moneda.setDescripcion(rs.getString("DESCRIPCION"));
                        moneda.setAbreviatura(rs.getString("ABREVIATURA"));
                        return moneda;
                    }
                });
    }

    private SimpleJdbcCall buildDameCabeceraCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS")
                .withProcedureName("PR_OBTENER_DATOS_CABECERA")
                .returningResultSet("P_DATOS", new RowMapper<Contrato>() {
                    @Override
                    public Contrato mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Contrato contrato = new Contrato();
                        contrato.setContrato(rs.getInt("CONTRATO"));
                        contrato.setDistribuidor(rs.getInt("DISTRIBUIDOR"));
                        contrato.setMoneda(rs.getString("COD_MONEDA"));
                        contrato.setDescripcionMoneda(rs.getString("DESCRIPCION_MONEDA"));
                        contrato.setFechaContrato(rs.getDate("FECHA_CONTRATO"));
                        contrato.setFechaAprobacion(rs.getDate("FECHA_APROBACION"));
                        contrato.setMontoTotal(rs.getBigDecimal("MONTO_TOTAL"));
                        contrato.setTipoContrato(rs.getString("TIPO_CONTRATO"));
                        return contrato;
                    }
                });
    }

    private SimpleJdbcCall buildDameObservacionesDePagoCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS")
                .withProcedureName("PR_OBTENER_OBSERV_PAGO")
                .returningResultSet("P_DATOS", new RowMapper<ObservacionDePago>() {
                    @Override
                    public ObservacionDePago mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ObservacionDePago observacionDePago = new ObservacionDePago();
                        observacionDePago.setRenglon(rs.getInt("RENGLON"));
                        observacionDePago.setObservacionDePago(rs.getString("OBSERVACION_PAGO"));
                        return observacionDePago;
                    }
                });
    }

    private SimpleJdbcCall buildDameContratoCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS")
                .withProcedureName("PR_SITUAR_CONTRATO")
                .returningResultSet("P_DATOS", new RowMapper<Contrato>() {
                    @Override
                    public Contrato mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Contrato contrato = new Contrato();
                        contrato.setContrato(rs.getInt("CONTRATO"));
                        contrato.setDistribuidor(rs.getInt("DISTRIBUIDOR"));
                        contrato.setMoneda(rs.getString("MONEDA"));
                        contrato.setRazonSocial(rs.getString("RAZON_SOCIAL"));
                        contrato.setMontoTotal(rs.getBigDecimal("MONTO_TOTAL"));
                        contrato.setFechaContrato(rs.getDate("FECHA_CONTRATO"));
                        contrato.setSenial(rs.getString("SENIAL"));
                        contrato.setEstado(rs.getString("ESTADO"));
                        return contrato;
                    }
                });
    }
    
    private SimpleJdbcCall buildDameContratoPorDistribuidorCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS")
                .withProcedureName("PR_BUSQUEDA_POR_DISTRIBUIDOR")
                .returningResultSet("P_DATOS", new RowMapper<Contrato>() {
                    @Override
                    public Contrato mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Contrato contrato = new Contrato();
                        contrato.setContrato(rs.getInt("CONTRATO"));
                        contrato.setDistribuidor(rs.getInt("DISTRIBUIDOR"));
                        contrato.setRazonSocial(rs.getString("RAZON_SOCIAL"));
                        contrato.setMontoTotal(rs.getBigDecimal("MONTO_TOTAL"));
                        contrato.setFechaContrato(rs.getDate("FECHA_CONTRATO"));
                        contrato.setMoneda(rs.getString("MONEDA"));
                        contrato.setSenial(rs.getString("SENIAL"));
                        contrato.setEstado(rs.getString("ESTADO"));
                        return contrato;
                    }
                });
    }


    private SimpleJdbcCall buildDameDistribuidoresPorNombreCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_MODIF_CONTRATOS")
                .withProcedureName("PR_DISTRIBUIDOR_NOMBRE")
                .returningResultSet("P_DATOS", new RowMapper<Distribuidor>() {
                    @Override
                    public Distribuidor mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Distribuidor distribuidor = new Distribuidor();
                        distribuidor.setCodigo(rs.getInt("CODIGO"));
                        distribuidor.setRazonSocial(rs.getString("RAZON_SOCIAL"));
                        return distribuidor;
                    }
                });
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Contrato> dameContrato(Integer contrato) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", contrato);
        Map<String, Object> out = this.dameContratoCall.execute(in);
        return (List<Contrato>) out.get("P_DATOS");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Distribuidor> dameDistribuidoresPorNombre(String nombre) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("P_NOMBRE", nombre);
        Map<String, Object> out = this.dameDistribuidoresPorNombreCall.execute(in);
        return (List<Distribuidor>) out.get("P_DATOS");
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Contrato> dameContratosPorDistribuidor(Integer claveDistribuidor) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("P_DISTRIBUIDOR", claveDistribuidor);
        Map<String, Object> out = this.dameContratoPorDistribuidorCall.execute(in);
        return (List<Contrato>) out.get("P_DATOS");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Contrato> dameContratoConCabecera(Integer contrato, Integer distribuidor) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_DISTRIBUIDOR", distribuidor)
            .addValue("P_CONTRATO", contrato);
        Map<String, Object> out = this.dameCabeceraCall.execute(in);
        return (List<Contrato>) out.get("P_DATOS");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ObservacionDePago> dameObservacionesDePago(Integer contrato) {
        SqlParameterSource in = new MapSqlParameterSource()
                                            .addValue("P_CONTRATO", contrato);
        Map<String, Object> out = this.dameObservacionesDePagoCall.execute(in);
        return (List<ObservacionDePago>) out.get("P_DATOS");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Moneda> dameMonedas() {
        Map<String, Object> out = this.dameMonedasCall.execute();
        return (List<Moneda>) out.get("P_DATOS");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SenialImporte> dameSenialImporte(Integer contrato) {
        SqlParameterSource in = new MapSqlParameterSource()
                                                    .addValue("P_CONTRATO", contrato);
        Map<String, Object> out = this.dameSenialImporteCall.execute(in);
        return (List<SenialImporte>) out.get("P_DATOS");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Grupo> dameGrupos(Integer claveContrato, String senial) {
        SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("P_CONTRATO", claveContrato)
                    .addValue("P_SENIAL", senial);
        Map<String, Object> out = this.dameGruposCall.execute(in);
        return (List<Grupo>) out.get("P_DATOS");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Grupo dameGrupo(Integer claveContrato, String senial, Integer claveGrupo) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", claveContrato)
            .addValue("P_GRUPO", claveGrupo)
            .addValue("P_SENIAL", senial);
        Map<String, Object> out = this.dameGrupoCall.execute(in);
        
        if (((List<Grupo>) out.get("P_DATOS")).size() > 0) {
            return ((List<Grupo>) out.get("P_DATOS")).get(0);
        } else {
            return new Grupo();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Senial> dameSenialesHeredadasAsignadas(Integer claveContrato) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", claveContrato);
        Map<String, Object> out = this.dameSenialesHeredadasAsignadasCall.execute(in);
        return (List<Senial>) out.get("P_DATOS");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TipoImporte> dameTiposDeImporte() {
        Map<String, Object> out = this.dameTiposDeImporteCall.execute();
        return (List<TipoImporte>) out.get("P_DATOS");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TipoDerecho> dameTiposDeDerecho() {
        Map<String, Object> out = this.dameTiposDeDerechosCall.execute();
        return (List<TipoDerecho>) out.get("P_DATOS");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TipoDerechoTerritorial> dameTiposDeDerechoTerritorial() {
        Map<String, Object> out = this.dameTiposDeDerechosTerritorialesCall.execute();
        return (List<TipoDerechoTerritorial>) out.get("P_DATOS");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TipoTitulo> dameTiposDeTitulo() {
        Map<String, Object> out = this.dameTiposDeTitulosCall.execute();
        return (List<TipoTitulo>) out.get("P_DATOS");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Titulo> dameTitulos(TitulosRequest titulosRequest) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_SENIAL", titulosRequest.getSenial())
            .addValue("P_TIPO_TITULO", titulosRequest.getTipoTitulo())
            .addValue("P_DESCRIP_GRUPO", titulosRequest.getTituloABuscar())
            .addValue("P_EST_REP", titulosRequest.getEstrenoOrepeticion())
            .addValue("P_MARCA_RECONT", titulosRequest.getMarcaRecontratacion());
        Map<String, Object> out;
        if (titulosRequest.getBuscarPor().equals("TituloCastellano")) {
            out = this.dameTitulosCastellanoCall.execute(in);
        } else {
            out = this.dameTitulosOriginalCall.execute(in);
        }
        return (List<Titulo>) out.get("P_DATOS");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TituloContratado> dameTitulosAEliminarPorContrato(TitulosRequest titulosRequest) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", titulosRequest.getContrato())
            .addValue("P_SENIAL", titulosRequest.getSenial())
            .addValue("P_GRUPO", titulosRequest.getGrupo());
        Map<String, Object> out = this.dameTitulosAEliminarPorContratoCall.execute(in);
        return (List<TituloContratado>) out.get("P_DATOS");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TituloContratado> dameTitulosAReemplazarPorContrato(TitulosRequest titulosRequest) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", titulosRequest.getContrato())
            .addValue("P_SENIAL", titulosRequest.getSenial())
            .addValue("P_GRUPO", titulosRequest.getGrupo());
        Map<String, Object> out = this.dameTitulosAReemplazarPorContratoCall.execute(in);
        return (List<TituloContratado>) out.get("P_DATOS");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Capitulo> dameCapitulosParaEliminar(TitulosRequest titulosRequest) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", titulosRequest.getContrato())
            .addValue("P_SENIAL", titulosRequest.getSenial())
            .addValue("P_GRUPO", titulosRequest.getGrupo());
        Map<String, Object> out = this.dameCapitulosParaEliminar.execute(in);
        return (List<Capitulo>) out.get("P_DATOS");
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Capitulo> dameCapitulosParaAgregar(TitulosRequest titulosRequest) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", titulosRequest.getContrato())
            .addValue("P_SENIAL", titulosRequest.getSenial())
            .addValue("P_GRUPO", titulosRequest.getGrupo());
        Map<String, Object> out = this.dameCapitulosParaAgregar.execute(in);
        return (List<Capitulo>) out.get("P_DATOS");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TituloContratado> dameTitulosPorContrato(TitulosRequest titulosRequest) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", titulosRequest.getContrato())
            .addValue("P_GRUPO", titulosRequest.getGrupo())
            .addValue("P_SENIAL", titulosRequest.getSenial())
            .addValue("P_VIGENTES", titulosRequest.getBuscarPor().equals("vigentes") ? "S" : "N");
        
        if (!titulosRequest.getTituloABuscar().equals(StringUtils.EMPTY)) {
            ((MapSqlParameterSource) in).addValue("P_CLAVE", titulosRequest.getTituloABuscar());
        } else {
            ((MapSqlParameterSource) in).addValue("P_CLAVE", null);
        }
            
        Map<String, Object> out = this.dameTitulosPorContratoCall.execute(in);
        return (List<TituloContratado>) out.get("P_DATOS");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TituloContratado> dameTitulosARecontratar(TitulosRecontratadosRequest titulosRecontratadosRequest) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CLAVE", titulosRecontratadosRequest.getClave())
            .addValue("P_SENIAL", titulosRecontratadosRequest.getSenial())
            .addValue("P_GRUPO", titulosRecontratadosRequest.getGrupo())
            .addValue("P_CONTRATO", titulosRecontratadosRequest.getContrato());
        Map<String, Object> out = this.dameTitulosARecontratarCall.execute(in);
        return (List<TituloContratado>) out.get("P_DATOS");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Capitulo> dameCapitulosARecontratar(TitulosRecontratadosRequest titulosRecontratadosRequest) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CLAVE", titulosRecontratadosRequest.getClave())
            .addValue("P_SENIAL", titulosRecontratadosRequest.getSenial())
            .addValue("P_GRUPO", titulosRecontratadosRequest.getGrupo())
            .addValue("P_CONTRATO", titulosRecontratadosRequest.getContrato())
            .addValue("P_PUEDE_ELEGIR_PARCIAL", titulosRecontratadosRequest.getPuedeElegirParcial());
        Map<String, Object> out = this.dameCapitulosARecontratarCall.execute(in);
        return (List<Capitulo>) out.get("P_DATOS");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ContratoConReRun> dameContratosConReRun(TitulosRecontratadosRequest titulosRecontratadosRequest) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CLAVE", titulosRecontratadosRequest.getClave())
            .addValue("P_SENIAL", titulosRecontratadosRequest.getSenial())
            .addValue("P_GRUPO", titulosRecontratadosRequest.getGrupo())
            .addValue("P_MODO", titulosRecontratadosRequest.getModo())
            .addValue("P_CONTRATO", titulosRecontratadosRequest.getContrato());
        Map<String, Object> out = this.dameContratosConReRunCall.execute(in);
        return (List<ContratoConReRun>) out.get("P_DATOS");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Capitulo> dameCapitulosPorTituloContratado(TitulosRecontratadosRequest titulosRecontratadosRequest) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_SENIAL", titulosRecontratadosRequest.getSenial())
            .addValue("P_GRUPO", titulosRecontratadosRequest.getGrupo())
            .addValue("P_CLAVE", titulosRecontratadosRequest.getClave())
            .addValue("P_CONTRATO", titulosRecontratadosRequest.getContrato());
        Map<String, Object> out = this.dameCapitulosPorTituloContratadoCall.execute(in);
        return (List<Capitulo>) out.get("P_DATOS");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Titulo dameTitulo(TituloRequest tituloRequest) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", tituloRequest.getContrato())
            .addValue("P_GRUPO", tituloRequest.getGrupo())
            .addValue("P_SENIAL", tituloRequest.getSenial())
            .addValue("P_FAMILIA", tituloRequest.getFamilia())
            .addValue("P_TIPO_TITULO", tituloRequest.getTipoTitulo())
            .addValue("P_SIN_CONT", tituloRequest.getSinContrato())
            .addValue("P_CLAVE", tituloRequest.getClave())
            .addValue("P_ORIGEN", tituloRequest.getOrigen());
        Map<String, Object> out = this.dameTituloCall.execute(in);
        return ((List<Titulo>) out.get("P_DATOS")).get(0);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Vigencia> dameVigenciasPorTituloContratado(TitulosRecontratadosRequest titulosRecontratadosRequest) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", titulosRecontratadosRequest.getContrato())
            .addValue("P_CLAVE", titulosRecontratadosRequest.getClave())
            .addValue("P_SENIAL", titulosRecontratadosRequest.getSenial())
            .addValue("P_GRUPO", titulosRecontratadosRequest.getGrupo())
            .addValue("P_MODO", titulosRecontratadosRequest.getModo())
            .addValue("P_FILTRO", titulosRecontratadosRequest.getFiltro());
        Map<String, Object> out = this.dameVigenciasPorTituloContratadoCall.execute(in);
        
        return (List<Vigencia>) out.get("P_DATOS");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Senial> dameSenialesHeredadas(String senial) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("P_SENIAL", senial);
        
        Map<String, Object> out = this.dameSenialesHeredadasCall.execute(in);
        
        return ((List<Senial>) out.get("P_DATOS"));
    }


    @Override
    public Integer dameSiguienteNumeroGrupo(Integer claveContrato, String senial) {
        SqlParameterSource in = new MapSqlParameterSource()
                                    .addValue("P_CONTRATO", claveContrato)
                                    .addValue("P_SENIAL", senial);
        
        Map<String, Object> out = this.dameSiguienteNumeroGrupoCall.execute(in);
        return ((BigDecimal) out.get("P_GRUPO")).intValue();         
    }

    @SuppressWarnings("unchecked")
    @Override
    public TituloConGrupo dameTituloContratado(TituloRequest tituloRequest, String usuario) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", tituloRequest.getContrato())
            .addValue("P_GRUPO", tituloRequest.getGrupo())
            .addValue("P_CLAVE", tituloRequest.getClave())
            .addValue("P_SENIAL", tituloRequest.getSenial());
        Map<String, Object> out = this.dameTituloContratadoCall.execute(in);
        return ((List<TituloConGrupo>) out.get("P_DATOS")).get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Vigencia damePayTV(VigenciaRequest vigenciaRequest, String usuario) {
      SqlParameterSource in = new MapSqlParameterSource()
                                                  .addValue("P_CONTRATO", vigenciaRequest.getContrato())
                                                  .addValue("P_GRUPO", vigenciaRequest.getGrupo())
                                                  .addValue("P_SENIAL", vigenciaRequest.getSenial())
                                                  .addValue("P_CLAVE", vigenciaRequest.getClave())
                                                  .addValue("P_ID_PAYTV", vigenciaRequest.getPayTVId());

      Map<String, Object> out = this.damePayTVCall.execute(in);

      return ((List<Vigencia>) out.get("P_DATOS")).get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Grupo> dameGruposConReemplazo(GruposConReemplazoRequest gruposConReemplazoRequest) {
        SqlParameterSource in = new MapSqlParameterSource()
        .addValue("P_CONTRATO", gruposConReemplazoRequest.getContrato())
        .addValue("P_MAYOR_GRUPO", gruposConReemplazoRequest.getMayorGrupo())
        .addValue("P_SENIAL", gruposConReemplazoRequest.getSenial());

         Map<String, Object> out = this.dameGruposConReemplazoCall.execute(in);
  
        return (List<Grupo>) out.get("P_GRUPOS");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Senial> dameSenialesChequeo(Integer claveContrato, String usuario) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", claveContrato);

        Map<String, Object> out = this.dameSenialesChequeo.execute(in);
        
        return  (List<Senial>) out.get("P_SENIALES");
    }
    
    @Override
    public String dameClave(String tipoTitulo) {
        SqlParameterSource in = new MapSqlParameterSource()
                                        .addValue("P_TIPO_TITULO", tipoTitulo);

         Map<String, Object> out = this.dameClaveCall.execute(in);
  
        return (String) out.get("P_CLAVE");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DameTGResponse> dameTG(DameTGRequest dameTGRequest, String usuario) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_USUARIO", usuario)
            .addValue("P_FECHA", dameTGRequest.getFecha())
            .addValue("P_HORA", dameTGRequest.getHora());

        Map<String, Object> out = this.dameTGCall.execute(in);
  
        return (List<DameTGResponse>) out.get("P_DATOS");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TituloContratado> dameTitulosConReRun(TitulosRequest titulosRequest, String usuario) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_CONTRATO", titulosRequest.getContrato())
            .addValue("P_GRUPO", titulosRequest.getGrupo())
            .addValue("P_SENIAL", titulosRequest.getSenial());

        Map<String, Object> out = this.dameTitulosConReRun.execute(in);
    
        return (List<TituloContratado>) out.get("P_TITULOS_RERUN");
    }

    @Override
    public String dameNombreDelTitulo(String claveTitulo) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("P_CLAVE", claveTitulo);

        Map<String, Object> out = this.dameNombreDelTituloCall.execute(in);
        
        return (String) out.get("P_TITULO_CAST");
        

    }

}