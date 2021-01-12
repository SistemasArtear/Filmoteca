/**
 * 
 */
package com.artear.filmo.bo.contratos;

import java.math.BigDecimal;

/**
 * @author sisloc
 * 
 */
public class SenialImporte {

    private Integer contrato;
    private String codigoSenial;
    private String descripcionSenial;
    private BigDecimal importe;
    private String usuario;
    
    public Integer getContrato() {
        return contrato;
    }

    public void setContrato(Integer contrato) {
        this.contrato = contrato;
    }

    public String getCodigoSenial() {
        return codigoSenial;
    }

    public void setCodigoSenial(String codigoSenial) {
        this.codigoSenial = codigoSenial;
    }

    public String getDescripcionSenial() {
        return descripcionSenial;
    }

    public void setDescripcionSenial(String descripcionSenial) {
        this.descripcionSenial = descripcionSenial;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

}
