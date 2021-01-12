/**
 * 
 */
package com.artear.filmo.bo.contratos;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author sisloc
 *
 */
public class Grupo {
    private Integer contrato;
    private Integer distribuidor;
    private String razonSocial;
    private String senial;
    private BigDecimal montoSenial;
    private String moneda;
    private Integer nroGrupo;
    private String tipoTitulo;
    private String senialHeredada;
    private String nombreGrupo;
    private Integer cantTitulos;
    private Integer cantPasadas;
    private String tipoImporte;
    private BigDecimal costoTotal;
    private String er;
    private Date fechaDerechos;
    private Date fechaEntrega;
    private String codigoDerechos;
    private Integer cantTiempo;
    private String unidadDeTiempo;
    private String codigoDerechosTerritoriales;
    private String descripcionDerechosTerritoriales;
    private String marcaPerpetuidad;
    private String planEntrega;
    private String recontratacion;
    private Integer cantTiempoExclusivo;
    private String unidadTiempoExclusivo;
    private String autorizacionAutor;
    private String pagaArgentores;
    private String pasaLibreria;
    private Date fechaComienzoDerechosLibreria;
    
    private String puedeReemplazar;
    private String flagOutput;
    
    public final Integer getContrato() {
        return this.contrato;
    }
    public final void setContrato(Integer contrato) {
        this.contrato = contrato;
    }
    public final Integer getDistribuidor() {
        return this.distribuidor;
    }
    public final void setDistribuidor(Integer distribuidor) {
        this.distribuidor = distribuidor;
    }
    public final String getRazonSocial() {
        return this.razonSocial;
    }
    public final void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
    public final String getSenial() {
        return this.senial;
    }
    public final void setSenial(String senial) {
        this.senial = senial;
    }
    public final BigDecimal getMontoSenial() {
        return this.montoSenial;
    }
    public final void setMontoSenial(BigDecimal montoSenial) {
        this.montoSenial = montoSenial;
    }
    public final String getMoneda() {
        return this.moneda;
    }
    public final void setMoneda(String moneda) {
        this.moneda = moneda;
    }
    public final Integer getCantTitulos() {
        return this.cantTitulos;
    }
    public final void setCantTitulos(Integer cantTitulos) {
        this.cantTitulos = cantTitulos;
    }
    public final Integer getCantPasadas() {
        return this.cantPasadas;
    }
    public final void setCantPasadas(Integer cantPasadas) {
        this.cantPasadas = cantPasadas;
    }
    public final String getTipoImporte() {
        return this.tipoImporte;
    }
    public final void setTipoImporte(String tipoImporte) {
        this.tipoImporte = tipoImporte;
    }
    public final BigDecimal getCostoTotal() {
        return this.costoTotal;
    }
    public final void setCostoTotal(BigDecimal costoTotal) {
        this.costoTotal = costoTotal;
    }
    public final Date getFechaDerechos() {
        return this.fechaDerechos;
    }
    public final void setFechaDerechos(Date fechaDerechos) {
        this.fechaDerechos = fechaDerechos;
    }
    public final Date getFechaEntrega() {
        return this.fechaEntrega;
    }
    public final void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
    public final String getCodigoDerechos() {
        return this.codigoDerechos;
    }
    public final void setCodigoDerechos(String codigoDerechos) {
        this.codigoDerechos = codigoDerechos;
    }
    public final Integer getCantTiempo() {
        return this.cantTiempo;
    }
    public final void setCantTiempo(Integer cantTiempo) {
        this.cantTiempo = cantTiempo;
    }
    public final String getUnidadDeTiempo() {
        return this.unidadDeTiempo;
    }
    public final void setUnidadDeTiempo(String unidadDeTiempo) {
        this.unidadDeTiempo = unidadDeTiempo;
    }
    public final String getCodigoDerechosTerritoriales() {
        return this.codigoDerechosTerritoriales;
    }
    public final void setCodigoDerechosTerritoriales(String codigoDerechosTerritoriales) {
        this.codigoDerechosTerritoriales = codigoDerechosTerritoriales;
    }
    public final String getDescripcionDerechosTerritoriales() {
        return this.descripcionDerechosTerritoriales;
    }
    public final void setDescripcionDerechosTerritoriales(String descripcionDerechosTerritoriales) {
        this.descripcionDerechosTerritoriales = descripcionDerechosTerritoriales;
    }
    public final String getMarcaPerpetuidad() {
        return this.marcaPerpetuidad;
    }
    public final void setMarcaPerpetuidad(String marcaPerpetuidad) {
        this.marcaPerpetuidad = marcaPerpetuidad;
    }
    public final String getPlanEntrega() {
        return this.planEntrega;
    }
    public final void setPlanEntrega(String planEntrega) {
        this.planEntrega = planEntrega;
    }
    public final String getRecontratacion() {
        return this.recontratacion;
    }
    public final void setRecontratacion(String recontratacion) {
        this.recontratacion = recontratacion;
    }
    public final Integer getCantTiempoExclusivo() {
        return this.cantTiempoExclusivo;
    }
    public final void setCantTiempoExclusivo(Integer cantTiempoExclusivo) {
        this.cantTiempoExclusivo = cantTiempoExclusivo;
    }
    public final String getUnidadTiempoExclusivo() {
        return this.unidadTiempoExclusivo;
    }
    public final void setUnidadTiempoExclusivo(String unidadTiempoExclusivo) {
        this.unidadTiempoExclusivo = unidadTiempoExclusivo;
    }
    public final String getAutorizacionAutor() {
        return this.autorizacionAutor;
    }
    public final void setAutorizacionAutor(String autorizacionAutor) {
        this.autorizacionAutor = autorizacionAutor;
    }
    public final String getPagaArgentores() {
        return this.pagaArgentores;
    }
    public final void setPagaArgentores(String pagaArgentores) {
        this.pagaArgentores = pagaArgentores;
    }
    public final String getPasaLibreria() {
        return this.pasaLibreria;
    }
    public final void setPasaLibreria(String pasaLibreria) {
        this.pasaLibreria = pasaLibreria;
    }
    public final Date getFechaComienzoDerechosLibreria() {
        return this.fechaComienzoDerechosLibreria;
    }
    public final void setFechaComienzoDerechosLibreria(Date fechaComienzoDerechosLibreria) {
        this.fechaComienzoDerechosLibreria = fechaComienzoDerechosLibreria;
    }

    public final Integer getNroGrupo() {
        return this.nroGrupo;
    }
    public final void setNroGrupo(Integer nroGrupo) {
        this.nroGrupo = nroGrupo;
    }
    public final String getTipoTitulo() {
        return this.tipoTitulo;
    }
    public final void setTipoTitulo(String tipoTitulo) {
        this.tipoTitulo = tipoTitulo;
    }
    public final String getNombreGrupo() {
        return this.nombreGrupo;
    }
    public final void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }
    public final String getEr() {
        return this.er;
    }
    public final void setEr(String er) {
        this.er = er;
    }
    public final String getSenialHeredada() {
        return this.senialHeredada;
    }
    public final void setSenialHeredada(String senialHeredada) {
        this.senialHeredada = senialHeredada;
    }
    public String getPuedeReemplazar() {
        return puedeReemplazar;
    }
    public void setPuedeReemplazar(String puedeReemplazar) {
        this.puedeReemplazar = puedeReemplazar;
    }
    
    public void setFlagOutput(String flagOutput) {
        this.flagOutput = flagOutput;
    }
    
    public String getFlagOutput() {
        return this.flagOutput;
    }
}
