/**
 * 
 */
package com.artear.filmo.bo.contratos;

import java.math.BigDecimal;

/**
 * @author sisloc
 *
 */
public class ContratoValidationRequest {
    private Integer contrato;
    private Integer distribuidor;
    private String moneda;
    private String descripcionMoneda;
    private String fechaContrato;
    private String fechaAprobacion;

    private String senial;
    
    private String tipoContrato;
    private BigDecimal monto;
    
    private String usuario;
    
    public ContratoValidationRequest() {

    }
    
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
    public final String getMoneda() {
        return this.moneda;
    }
    public final void setMoneda(String moneda) {
        this.moneda = moneda;
    }
    public final String getDescripcionMoneda() {
        return this.descripcionMoneda;
    }
    public final void setDescripcionMoneda(String descripcionMoneda) {
        this.descripcionMoneda = descripcionMoneda;
    }
    public final String getFechaContrato() {
        return this.fechaContrato;
    }
    public final void setFechaContrato(String fechaContrato) {
        this.fechaContrato = fechaContrato;
    }
    public final String getFechaAprobacion() {
        return this.fechaAprobacion;
    }
    public final void setFechaAprobacion(String fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }
    public final String getTipoContrato() {
        return this.tipoContrato;
    }
    public final void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }
    public final BigDecimal getMonto() {
        return this.monto;
    }
    public final void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenial() {
        return senial;
    }

    public void setSenial(String senial) {
        this.senial = senial;
    }
}
