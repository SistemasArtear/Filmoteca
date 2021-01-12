/**
 * 
 */
package com.artear.filmo.bo.common;

public class ErrorResponse {

    private String tipoError;
    private String descripcion;

    public void setTipoError(String tipoError) {
        this.tipoError = tipoError;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoError() {
        return this.tipoError;
    }
    
    public String getDescripcion() {
        return this.descripcion;
    }
}
