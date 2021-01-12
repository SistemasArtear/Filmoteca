/**
 * 
 */
package com.artear.filmo.bo.contratos;

import java.util.Date;

/**
 * @author sisloc
 * 
 */
public class ContratoValidationResult {
    private String tipo;
    private Integer nroAdvertencia;
    private String descripcion;
    private Date fecha;
//    private Integer nroContrato;
//    private Integer idReporteGt;
//    private Integer idReporteGc;
    private Integer idReporte;

    public ContratoValidationResult() {
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

//    public final Integer getNroContrato() {
//        return this.nroContrato;
//    }
//
//    public final void setNroContrato(Integer nroContrato) {
//        this.nroContrato = nroContrato;
//    }
//
//    public final Integer getIdReporteGt() {
//        return this.idReporteGt;
//    }
//
//    public final void setIdReporteGt(Integer idReporteGt) {
//        this.idReporteGt = idReporteGt;
//    }
//
//    public final Integer getIdReporteGc() {
//        return this.idReporteGc;
//    }
//
//    public final void setIdReporteGc(Integer idReporteGc) {
//        this.idReporteGc = idReporteGc;
//    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public void setIdReporte(Integer idReporte) {
        this.idReporte = idReporte;
    }
    
    public Integer getIdReporte() {
        return this.idReporte;
    }

}
