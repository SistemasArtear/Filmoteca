/**
 * 
 */
package com.artear.filmo.bo.contratos;

import java.util.Date;

/**
 * @author sisloc
 * 
 */
public class Titulo {
    private String clave;
    private String titulo;
    private String actores;
    private String actores1;
    private String actores2;
    private String sinContrato;
    private String tituloCastellano;
    private String tituloOriginal;
    private String calificacionOficial;
    private String recontratacion;
    private String observaciones;
    private Date fechaPosibleEntrega;
    private String editaFechaPosibleEntrega;
    private String editaMarcaRecontratacion;
    private String origen;
    private String alertaTitulo;
    private String idFicha;
    
    public final String getTituloCastellano() {
        return this.tituloCastellano;
    }

    public final void setTituloCastellano(String tituloCastellano) {
        this.tituloCastellano = tituloCastellano;
    }

    public final String getTituloOriginal() {
        return this.tituloOriginal;
    }

    public final void setTituloOriginal(String tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
    }

    public final String getCalificacionOficial() {
        return this.calificacionOficial;
    }

    public final void setCalificacionOficial(String calificacionOficial) {
        this.calificacionOficial = calificacionOficial;
    }

    public final String getRecontratacion() {
        return this.recontratacion;
    }

    public final void setRecontratacion(String recontratacion) {
        this.recontratacion = recontratacion;
    }

    public final Date getFechaPosibleEntrega() {
        return this.fechaPosibleEntrega;
    }

    public final void setFechaPosibleEntrega(Date fechaPosibleEntrega) {
        this.fechaPosibleEntrega = fechaPosibleEntrega;
    }

    public final String getClave() {
        return this.clave;
    }

    public final void setClave(String clave) {
        this.clave = clave;
    }

    public final String getTitulo() {
        return this.titulo;
    }

    public final void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setActores(String actores) {
        this.actores = actores;
    }
    
    public String getActores() {
        return this.actores;
    }
    
    public final String getActores1() {
        return this.actores1;
    }

    public final void setActores1(String actores) {
        this.actores1 = actores;
    }

    public final String getActores2() {
        return this.actores2;
    }

    public final void setActores2(String actores) {
        this.actores2 = actores;
    }
    
    public final String getSinContrato() {
        return this.sinContrato;
    }

    public final void setSinContrato(String sinContrato) {
        this.sinContrato = sinContrato;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEditaMarcaRecontratacion() {
        return editaMarcaRecontratacion;
    }

    public void setEditaMarcaRecontratacion(String editaMarcaRecontratacion) {
        this.editaMarcaRecontratacion = editaMarcaRecontratacion;
    }

    public String getEditaFechaPosibleEntrega() {
        return editaFechaPosibleEntrega;
    }

    public void setEditaFechaPosibleEntrega(String editaFechaPosibleEntrega) {
        this.editaFechaPosibleEntrega = editaFechaPosibleEntrega;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getAlertaTitulo() {
        return alertaTitulo;
    }

    public void setAlertaTitulo(String alertaTitulo) {
        this.alertaTitulo = alertaTitulo;
    }

    public void setIdFicha(String idFicha) {
        this.idFicha = idFicha;
    }

    public String getIdFicha() {
        return this.idFicha;
    }
}
