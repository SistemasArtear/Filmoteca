/**
 * 
 */
package com.artear.filmo.bo.programacion;

/**
 * @author sisloc
 *
 */
public class ValidationResult {
    
    private String tipo;
    private Integer nroAdvertencia;
    private String descripcion;
    private Integer nroContrato;
    
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
    
    
}
