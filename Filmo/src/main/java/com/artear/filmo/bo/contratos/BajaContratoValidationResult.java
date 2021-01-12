/**
 * 
 */
package com.artear.filmo.bo.contratos;

import java.math.BigDecimal;

/**
 * @author sisloc
 * 
 */
public class BajaContratoValidationResult {
    private String tipo;
    private Integer nroAdvertencia;
    private String descripcion;
    private Integer nroContrato;
    private BigDecimal idReporte;

    public BajaContratoValidationResult() {
    }

    public final String getTipo() {
        return this.tipo;
    }

    public final void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public final Integer getNroAdvertencia() {
        return this.nroAdvertencia;
    }

    public final void setNroAdvertencia(Integer nroAdvertencia) {
        this.nroAdvertencia = nroAdvertencia;
    }

    public final String getDescripcion() {
        return this.descripcion;
    }

    public final void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public final Integer getNroContrato() {
        return this.nroContrato;
    }

    public final void setNroContrato(Integer nroContrato) {
        this.nroContrato = nroContrato;
    }

    public BigDecimal getIdReporte() {
        return this.idReporte;
    }
    
    public void setIdReporte(BigDecimal idReporte) {
        this.idReporte = idReporte;
    }
}
