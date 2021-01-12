package com.artear.filmo.bo.contratos;

import java.math.BigDecimal;
import java.util.Date;

public class Contrato {

    private Integer contrato;
    private Integer distribuidor;
    private String razonSocial;
    private BigDecimal montoTotal;
    private String moneda;
    private String descripcionMoneda;
    private Date fechaContrato;
    private Date fechaAprobacion;
    private String tipoContrato;
    private String senial;
    private String estado;
    
    
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
    public final BigDecimal getMontoTotal() {
        return this.montoTotal;
    }
    public final void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }
    public final String getMoneda() {
        return this.moneda;
    }
    public final void setMoneda(String moneda) {
        this.moneda = moneda;
    }
    public final Date getFechaContrato() {
        return this.fechaContrato;
    }
    public final void setFechaContrato(Date fechaContrato) {
        this.fechaContrato = fechaContrato;
    }
    public final String getSenial() {
        return this.senial;
    }
    public final void setSenial(String senial) {
        this.senial = senial;
    }
    public final String getEstado() {
        return this.estado;
    }
    public final void setEstado(String estado) {
        this.estado = estado;
    }
    public Date getFechaAprobacion() {
        return fechaAprobacion;
    }
    public void setFechaAprobacion(Date fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }
    public String getTipoContrato() {
        return tipoContrato;
    }
    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }
    public String getDescripcionMoneda() {
        return descripcionMoneda;
    }
    public void setDescripcionMoneda(String descripcionMoneda) {
        this.descripcionMoneda = descripcionMoneda;
    }
}
