package com.artear.filmo.bo.contratos;

import java.util.Date;


public class Vigencia {
    private String tipoModifVigencia;
    private String descripTipoModifVigencia;
    private Date fechaDesdePay;
    private Date fechaHastaPay;
    private String payAnulado;
    private Date fechaAnteriorDesde;
    private Date fechaAnteriorHasta;
    private Date fechaNuevaDesde;
    private Date fechaNuevaHasta;
    private String puedeBorrar;
    private Integer payTVId;
    private String observaciones;
    
    public final String getTipoModifVigencia() {
        return this.tipoModifVigencia;
    }
    public final void setTipoModifVigencia(String tipoModifVigencia) {
        this.tipoModifVigencia = tipoModifVigencia;
    }
    public final String getDescripTipoModifVigencia() {
        return this.descripTipoModifVigencia;
    }
    public final void setDescripTipoModifVigencia(String descripTipoModifVigencia) {
        this.descripTipoModifVigencia = descripTipoModifVigencia;
    }
    public final Date getFechaDesdePay() {
        return this.fechaDesdePay;
    }
    public final void setFechaDesdePay(Date fechaDesdePay) {
        this.fechaDesdePay = fechaDesdePay;
    }
    public final Date getFechaHastaPay() {
        return this.fechaHastaPay;
    }
    public final void setFechaHastaPay(Date fechaHastaPay) {
        this.fechaHastaPay = fechaHastaPay;
    }
    public final String getPayAnulado() {
        return this.payAnulado;
    }
    public final void setPayAnulado(String payAnulado) {
        this.payAnulado = payAnulado;
    }
    public final Date getFechaAnteriorDesde() {
        return this.fechaAnteriorDesde;
    }
    public final void setFechaAnteriorDesde(Date fechaAnteriorDesde) {
        this.fechaAnteriorDesde = fechaAnteriorDesde;
    }
    public final Date getFechaAnteriorHasta() {
        return this.fechaAnteriorHasta;
    }
    public final void setFechaAnteriorHasta(Date fechaAnteriorHasta) {
        this.fechaAnteriorHasta = fechaAnteriorHasta;
    }
    public final Date getFechaNuevaDesde() {
        return this.fechaNuevaDesde;
    }
    public final void setFechaNuevaDesde(Date fechaNuevaDesde) {
        this.fechaNuevaDesde = fechaNuevaDesde;
    }
    public final Date getFechaNuevaHasta() {
        return this.fechaNuevaHasta;
    }
    public final void setFechaNuevaHasta(Date fechaNuevaHasta) {
        this.fechaNuevaHasta = fechaNuevaHasta;
    }

    public void setPuedeBorrar(String puedeBorrar) {
        this.puedeBorrar = puedeBorrar;
    }
    
    public String getPuedeBorrar() {
        return this.puedeBorrar;
    }

    public void setPayTVId(Integer payTVId) {
        this.payTVId = payTVId; 
    }
    
    public Integer getPayTVId() {
        return this.payTVId; 
    }
    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
    