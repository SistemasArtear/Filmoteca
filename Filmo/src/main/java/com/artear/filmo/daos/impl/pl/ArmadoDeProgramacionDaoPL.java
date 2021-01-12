package com.artear.filmo.daos.impl.pl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.artear.filmo.bo.programacion.Capitulo;
import com.artear.filmo.bo.programacion.ParteSegmento;
import com.artear.filmo.bo.programacion.Programa;
import com.artear.filmo.bo.programacion.Titulo;
import com.artear.filmo.bo.programacion.TituloProgramado;
import com.artear.filmo.bo.programacion.TituloRequest;
import com.artear.filmo.bo.programacion.ValidationResult;
import com.artear.filmo.daos.interfaces.ArmadoDeProgramacionDao;

/**
 * 
 * @author flvaldes
 * 
 */
@Repository("armadoDeProgramacionDao")
public class ArmadoDeProgramacionDaoPL implements ArmadoDeProgramacionDao {

    private SimpleJdbcCall levantarListaDeProgramasCall;
    private SimpleJdbcCall obtenerGrillaProgramadaCall;

    private SimpleJdbcCall obtenerListaTitulosProgramarCall;
    private SimpleJdbcCall obtenerCapitulosCall;
    private SimpleJdbcCall obtenerParteSegmentoCall;

    private SimpleJdbcCall obtenerDatosTituloCall;

    private SimpleJdbcCall validarAltaProgramacionCall;
    private SimpleJdbcCall validarBajaProgramacionCall;
    private SimpleJdbcCall validarModificaProgramacionCall;

    private SimpleJdbcCall altaProgramacionCall;
    private SimpleJdbcCall bajaProgramacionCall;
    private SimpleJdbcCall modificaProgramacionCall;

    private SimpleJdbcCall obtenerObservacionesCall;
    
    private SimpleJdbcCall esUnaClaveValidaCall;
    private SimpleJdbcCall esUnCapituloValidoCall;
    
    @Autowired
    public ArmadoDeProgramacionDaoPL(DataSource dataSource) {
        super();
        this.levantarListaDeProgramasCall = buildLevantarListaDeProgramasCall(dataSource);
        this.obtenerGrillaProgramadaCall = buildObtenerGrillaProgramadaCall(dataSource);
        this.obtenerListaTitulosProgramarCall = buildObtenerListaTitulosProgramarCall(dataSource);
        this.obtenerCapitulosCall = buildObtenerCapitulosCall(dataSource);
        this.obtenerParteSegmentoCall = buildObtenerParteSegmentoCall(dataSource);
        this.obtenerDatosTituloCall = buildObtenerDatosTituloCall(dataSource);

        this.validarAltaProgramacionCall = buildValidarProgramacionCall(dataSource, "PR_VALIDAR_ALTA_PROGRAMACION");
        this.validarBajaProgramacionCall = buildValidarProgramacionCall(dataSource, "PR_VALIDAR_BAJA_PROGRAMACION");
        this.validarModificaProgramacionCall = buildValidarProgramacionCall(dataSource, "PR_VALIDAR_MODIF_PROGRAMACION");

        this.altaProgramacionCall = buildABMProgramacionCall(dataSource, "PR_ALTA_PROGRAMACION");
        this.bajaProgramacionCall = buildABMProgramacionCall(dataSource, "PR_BAJA_PROGRAMACION");
        this.modificaProgramacionCall = buildABMProgramacionCall(dataSource, "PR_MODIF_PROGRAMACION");
        
        this.obtenerObservacionesCall = buildObtenerObservacionesCall(dataSource);
        
        this.esUnaClaveValidaCall = buildEsUnaClaveValidaCall(dataSource);
        this.esUnCapituloValidoCall  = buildEsUnCapituloValidoCall(dataSource);
    }

    private SimpleJdbcCall buildEsUnCapituloValidoCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_ARMADO_PROGRAMACION").withProcedureName("PR_VALIDAR_CAPITULO");
    }

    private SimpleJdbcCall buildEsUnaClaveValidaCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_ARMADO_PROGRAMACION").withProcedureName("PR_VALIDAR_CLAVE");
    }

    private SimpleJdbcCall buildObtenerObservacionesCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_ARMADO_PROGRAMACION").withProcedureName("PR_OBTENER_OBSERVACIONES");
    }

    private SimpleJdbcCall buildABMProgramacionCall(DataSource dataSource, String procedureName) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_ARMADO_PROGRAMACION").withProcedureName(procedureName);
    }

    private SimpleJdbcCall buildValidarProgramacionCall(DataSource dataSource, String procedureName) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_ARMADO_PROGRAMACION").withProcedureName(procedureName)
                .returningResultSet("P_ADVERT_ERRORES", new RowMapper<ValidationResult>() {
                @Override
                public ValidationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                    ValidationResult validationResult = new ValidationResult();
                    validationResult.setTipo(rs.getString("TIPO"));
                    validationResult.setNroAdvertencia(rs.getInt("NRO_ADVERTENCIA"));
                    validationResult.setDescripcion(rs.getString("DESCRIPCION"));
                    //validationResult.setNroContrato(rs.getInt("NRO_CONTRATO"));
                    return validationResult;
                }
          });
    }

    private SimpleJdbcCall buildObtenerDatosTituloCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_ARMADO_PROGRAMACION").withProcedureName("PR_OBTENER_DATOS_TITULO");
    }

    private SimpleJdbcCall buildObtenerParteSegmentoCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_ARMADO_PROGRAMACION").withProcedureName("PR_OBTENER_PARTE_SEGMENTO")
                .returningResultSet("P_CAPITULOS", new RowMapper<ParteSegmento>() {
                    @Override
                    public ParteSegmento mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ParteSegmento parteSegmento = new ParteSegmento();
                        parteSegmento.setParte(rs.getString("PARTE"));
                        parteSegmento.setSegmento(rs.getString("SEGMENTO"));
                        return parteSegmento;
                    }
                });
    }

    private SimpleJdbcCall buildObtenerCapitulosCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_ARMADO_PROGRAMACION").withProcedureName("PR_OBTENER_CAPITULOS").returningResultSet("P_CAPITULOS", new RowMapper<Capitulo>() {
            @Override
            public Capitulo mapRow(ResultSet rs, int rowNum) throws SQLException {
                Capitulo capitulo = new Capitulo();
                capitulo.setDescripcion(rs.getString("DESCRIPCION_CASTELLANO"));
                capitulo.setNumero(rs.getString("NRO_CAPITULO"));
                return capitulo;
            }
        });
    }

    private SimpleJdbcCall buildObtenerListaTitulosProgramarCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_ARMADO_PROGRAMACION").withProcedureName("PR_OBT_LISTA_TITULOS_PROGRAMAR")
                .returningResultSet("P_TITULOS", new RowMapper<Titulo>() {
                    @Override
                    public Titulo mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Titulo titulo = new Titulo();
                        titulo.setClave(rs.getString("CLAVE"));
                        titulo.setTituloCastellano(rs.getString("TITULO_CASTELLANO"));
                        return titulo;
                    }
                });
    }

    private SimpleJdbcCall buildLevantarListaDeProgramasCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_ARMADO_PROGRAMACION").withProcedureName("PR_LEVANTAR_LISTA_PROGRAMAS")
                .returningResultSet("P_LISTA_PROGRAMAS", new RowMapper<Programa>() {
                    @Override
                    public Programa mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Programa programa = new Programa();
                        programa.setCodigo(rs.getInt("CODIGO"));
                        programa.setDescripcion(rs.getString("DESCRIPCION"));
                        return programa;
                    }
                });
    }

    private SimpleJdbcCall buildObtenerGrillaProgramadaCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName("FIL_PKG_ARMADO_PROGRAMACION").withProcedureName("PR_OBTENER_GRILLA_PROGRAMADA")
                .returningResultSet("P_GRILLA", new RowMapper<TituloProgramado>() {
                    @Override
                    public TituloProgramado mapRow(ResultSet rs, int rowNum) throws SQLException {
                        TituloProgramado tituloProgramado = new TituloProgramado();
                        tituloProgramado.setDia(rs.getString("DIA_SEMANA"));
                        tituloProgramado.setCapitulo(rs.getString("NRO_CAPITULO"));
                        tituloProgramado.setClave(rs.getString("CLAVE"));
                        tituloProgramado.setFecha(rs.getString("FECHA_EXHIBICION"));
                        tituloProgramado.setFraccion(rs.getString("FRACCION"));
                        tituloProgramado.setOrdenSalida(rs.getString("ORDEN"));
                        tituloProgramado.setParte(rs.getString("PARTE"));
                        tituloProgramado.setSegmento(rs.getString("SEGMENTO"));
                        tituloProgramado.setTituloCast(rs.getString("TITULO_CASTELLANO"));
                        tituloProgramado.setTotalDeFracciones(rs.getString("DE"));

                        return tituloProgramado;
                    }
                });
    }

    @SuppressWarnings("unchecked")
    public List<Programa> levantarListaDeProgramas(String idSenial) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("p_senial", idSenial);
        Map<String, Object> out = this.levantarListaDeProgramasCall.execute(in);
        return (List<Programa>) out.get("P_LISTA_PROGRAMAS");
    }

    @SuppressWarnings("unchecked")
    public List<TituloProgramado> obtenerGrillaProgramada(String idSenial, int programa, Date fechaSituar, Date fechaHasta) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("p_senial", idSenial)
            .addValue("p_programa", programa)
            .addValue("p_fecha_hasta", fechaHasta)
            .addValue("p_fecha_situar", fechaSituar);
        Map<String, Object> out = this.obtenerGrillaProgramadaCall.execute(in);
        return (List<TituloProgramado>) out.get("P_GRILLA");
    }

    @SuppressWarnings("unchecked")
    public List<Titulo> obtenerListaTitulosProgramar(String idSenial, String tituloCast, String familiaTitulo) {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_SENIAL", idSenial)
            .addValue("P_TITULO_CAST", tituloCast)
            .addValue("P_FAMILIA_TIT", familiaTitulo);
        Map<String, Object> out = this.obtenerListaTitulosProgramarCall.execute(in);
        return (List<Titulo>) out.get("P_TITULOS");
    }

    @SuppressWarnings("unchecked")
    public List<Capitulo> obtenerCapitulos(String clave) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("P_CLAVE", clave);
        Map<String, Object> out = this.obtenerCapitulosCall.execute(in);
        return (List<Capitulo>) out.get("P_CAPITULOS");
    }

    @SuppressWarnings("unchecked")
    public List<ParteSegmento> obtenerParteSegmento(String clave, int nroCapitulo) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("P_CLAVE", clave).addValue("P_NRO_CAPITULO", nroCapitulo);
        Map<String, Object> out = this.obtenerParteSegmentoCall.execute(in);
        return (List<ParteSegmento>) out.get("P_CAPITULOS");
    }

    public Titulo obtenerDatosTitulo(String clave, Integer nroCapitulo, Integer parte, Integer segmento) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("P_CLAVE", clave).addValue("P_NRO_CAPITULO", nroCapitulo).addValue("P_PARTE", parte).addValue("P_SEGMENTO", segmento);
        Map<String, Object> out = this.obtenerDatosTituloCall.execute(in);
        Titulo titulo = new Titulo();
        titulo.setClave(String.valueOf(clave));
        titulo.setParte(String.valueOf(parte));
        titulo.setSegmento(String.valueOf(segmento));
        titulo.setNroCapitulo(String.valueOf(nroCapitulo));
        titulo.setDescripcionCapitulo((String) out.get("P_DESC_CAP_CASTELLANO"));
        titulo.setTituloOriginal((String) out.get("P_TITULO_ORIGINAL"));
        titulo.setTituloOFF((String) out.get("P_TITULO_OFF"));
        titulo.setTituloCastellano((String) out.get("P_TITULO_CASTELLANO"));
        titulo.setInterpretes((String) out.get("P_INTERPRETES"));
        return titulo;
    }

    @Override
    public String obtenerObservaciones(TituloRequest tituloRequest, String usuario) throws ParseException {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_USUARIO", usuario)
            .addValue("P_SENIAL", tituloRequest.getSenial())
            .addValue("P_FEC_EXHIB", new SimpleDateFormat("dd-MM-yyyy").parse(tituloRequest.getFecha()))
            .addValue("P_CLAVE", tituloRequest.getClave())
            .addValue("P_NRO_CAPITULO", tituloRequest.getCapitulo())
            .addValue("P_PARTE", tituloRequest.getParte())
            .addValue("P_SEGMENTO", tituloRequest.getSegmento())
            .addValue("P_FRACCION", tituloRequest.getFraccion());
        Map<String, Object> out = this.obtenerObservacionesCall.execute(in);
        return (String) (out.get("P_OBSERVACIONES"));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ValidationResult> validarAltaProgramacion(TituloRequest titulo, String usuario) throws ParseException {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_SENIAL", titulo.getSenial())
            .addValue("P_PROGRAMA", titulo.getCodigoDelPrograma())
            .addValue("P_FEC_EXHIB", new SimpleDateFormat("dd-MM-yyyy").parse(titulo.getFecha()))
            .addValue("P_CLAVE", titulo.getClave())
            .addValue("P_CAPITULO", titulo.getCapitulo())
            .addValue("P_PARTE", titulo.getParte())
            .addValue("P_SEGMENTO", titulo.getSegmento())
            .addValue("P_FRACCION", titulo.getFraccion())
            .addValue("P_TOTAL_FRACCION", titulo.getTotalDeFracciones())
            .addValue("P_ORDEN", titulo.getOrdenSalida())
            .addValue("P_FAMILIA_TITULO", titulo.getFamiliaTitulo())
            .addValue("P_USUARIO", usuario);
        Map<String, Object> out = this.validarAltaProgramacionCall.execute(in);
        return (List<ValidationResult>) out.get("P_ADVERT_ERRORES");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ValidationResult> validarBajaProgramacion(TituloRequest titulo) throws ParseException {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_SENIAL", titulo.getSenial())
            .addValue("P_PROGRAMA", titulo.getCodigoDelPrograma())
            .addValue("P_FEC_EXHIB", new SimpleDateFormat("dd-MM-yyyy").parse(titulo.getFecha()))
            .addValue("P_CLAVE", titulo.getClave())
            .addValue("P_CAPITULO", titulo.getCapitulo())
            .addValue("P_PARTE", titulo.getParte())
            .addValue("P_SEGMENTO", titulo.getSegmento())
            .addValue("P_FRACCION", titulo.getFraccion())
            .addValue("P_TOTAL_FRACCION", titulo.getTotalDeFracciones())
            .addValue("P_ORDEN", titulo.getOrdenSalida());
        Map<String, Object> out = this.validarBajaProgramacionCall.execute(in);
        return (List<ValidationResult>) out.get("P_ADVERT_ERRORES");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ValidationResult> validarModificaProgramacion(TituloRequest titulo, String usuario) throws ParseException {
        SqlParameterSource in = new MapSqlParameterSource().addValue("P_SENIAL", titulo.getSenial()).addValue("P_PROGRAMA", titulo.getCodigoDelPrograma())
                .addValue("P_FEC_EXHIB_OLD", new SimpleDateFormat("dd-MM-yyyy").parse(titulo.getFechaAnterior()))
                .addValue("P_FEC_EXHIB_NEW", new SimpleDateFormat("dd-MM-yyyy").parse(titulo.getFecha()))
                .addValue("P_CLAVE", titulo.getClave())
                .addValue("P_CAPITULO", titulo.getCapitulo()).addValue("P_PARTE", titulo.getParte()).addValue("P_SEGMENTO", titulo.getSegmento())
                .addValue("P_FRACCION_OLD", titulo.getFraccionAnterior()).addValue("P_FRACCION_NEW", titulo.getFraccion()).addValue("P_TOTAL_FRACCION_OLD", titulo.getTotalDeFraccionesAnterior())
                .addValue("P_TOTAL_FRACCION_NEW", titulo.getTotalDeFracciones()).addValue("P_ORDEN_NEW", titulo.getOrdenSalida()).addValue("P_ORDEN_OLD", titulo.getOrdenSalidaAnterior())
                .addValue("P_USUARIO", usuario);
        Map<String, Object> out = this.validarModificaProgramacionCall.execute(in);
        return (List<ValidationResult>) out.get("P_ADVERT_ERRORES");
    }

    @Override
    public boolean altaProgramacion(TituloRequest titulo, String usuario) throws ParseException {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_SENIAL", titulo.getSenial())
                .addValue("P_PROGRAMA", titulo.getCodigoDelPrograma())
                .addValue("P_FEC_EXHIB", new SimpleDateFormat("dd-MM-yyyy").parse(titulo.getFecha()))
                .addValue("P_CLAVE", titulo.getClave())
                .addValue("P_CAPITULO", titulo.getCapitulo())
                .addValue("P_PARTE", titulo.getParte())
                .addValue("P_SEGMENTO", titulo.getSegmento())
                .addValue("P_FRACCION", titulo.getFraccion())
                .addValue("P_TOTAL_FRACCION", titulo.getTotalDeFracciones())
                .addValue("P_ORDEN", titulo.getOrdenSalida())
                .addValue("P_USUARIO", usuario);
        Map<String, Object> out = this.altaProgramacionCall.execute(in);
        return (Boolean) (out.get("P_OK").equals("S"));
    }

    @Override
    public boolean bajaProgramacion(TituloRequest titulo) throws ParseException {
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("P_SENIAL", titulo.getSenial())
            .addValue("P_PROGRAMA", titulo.getCodigoDelPrograma())
            .addValue("P_FEC_EXHIB", new SimpleDateFormat("dd-MM-yyyy").parse(titulo.getFecha()))
            .addValue("P_CLAVE", titulo.getClave())
            .addValue("P_CAPITULO", titulo.getCapitulo())
            .addValue("P_PARTE", titulo.getParte())
            .addValue("P_SEGMENTO", titulo.getSegmento())
            .addValue("P_FRACCION", titulo.getFraccion())
            .addValue("P_TOTAL_FRACCION", titulo.getTotalDeFraccionesAnterior()).addValue("P_ORDEN", titulo.getOrdenSalidaAnterior());
        Map<String, Object> out = this.bajaProgramacionCall.execute(in);
        return (Boolean) (out.get("P_OK").equals("S"));
    }

    @Override
    public boolean modificaProgramacion(TituloRequest titulo, String usuario) throws ParseException {
        SqlParameterSource in = new MapSqlParameterSource().addValue("P_SENIAL", titulo.getSenial()).addValue("P_PROGRAMA", titulo.getCodigoDelPrograma())
                .addValue("P_FEC_EXHIB_OLD", new SimpleDateFormat("dd-MM-yyyy").parse(titulo.getFechaAnterior()))
                .addValue("P_FEC_EXHIB_NEW", new SimpleDateFormat("dd-MM-yyyy").parse(titulo.getFecha()))
                .addValue("P_CLAVE", titulo.getClave())
                .addValue("P_CAPITULO", titulo.getCapitulo())
                .addValue("P_PARTE", titulo.getParte()).addValue("P_SEGMENTO", titulo.getSegmento())
                .addValue("P_FRACCION_OLD", titulo.getFraccionAnterior())
                .addValue("P_FRACCION_NEW", titulo.getFraccion())
                .addValue("P_TOTAL_FRACCION_OLD", titulo.getTotalDeFraccionesAnterior())
                .addValue("P_TOTAL_FRACCION_NEW", titulo.getTotalDeFracciones())
                .addValue("P_ORDEN_NEW", titulo.getOrdenSalida())
                .addValue("P_ORDEN_OLD", titulo.getOrdenSalidaAnterior())
                .addValue("P_USUARIO", usuario);
        Map<String, Object> out = this.modificaProgramacionCall.execute(in);
        return (Boolean) (out.get("P_OK").equals("S"));
    }

    @Override
    public Boolean esUnaClaveValida(String claveTitulo) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("P_CLAVE", claveTitulo);
        Map<String, Object> out = this.esUnaClaveValidaCall.execute(in);
        return (Boolean) (out.get("P_OK").equals("S"));
    }

    @Override
    public Boolean esUnCapituloValido(String claveTitulo, Integer nroCapitulo) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("P_CLAVE", claveTitulo)
                .addValue("P_CAPITULO", nroCapitulo);
        Map<String, Object> out = this.esUnCapituloValidoCall.execute(in);
        return (Boolean) (out.get("P_OK").equals("S"));
    }

}