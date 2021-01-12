package com.artear.filmo.bo.contratos;

import java.sql.Date;

public class DameTGResponse {

    private String usuario;
    private String workstation;
    private Date fecha;
    private Integer hora;
    private Integer renglon;
    private String capitulo;
    private String parte;
    private String confirmaRemito;
    private String confirmaCopia;
    private String numeroRemito;
    private String numeroGuia;
    private String fechaMovimiento;
    private String fechaRemitoGuia;
    private String chequeoVolver;
    private String copiaVolver;
    
    public final String getUsuario() {
        return this.usuario;
    }
    public final void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public final String getWorkstation() {
        return this.workstation;
    }
    public final void setWorkstation(String workstation) {
        this.workstation = workstation;
    }
    public final Date getFecha() {
        return this.fecha;
    }
    public final void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public final Integer getHora() {
        return this.hora;
    }
    public final void setHora(Integer hora) {
        this.hora = hora;
    }
    public final Integer getRenglon() {
        return this.renglon;
    }
    public final void setRenglon(Integer renglon) {
        this.renglon = renglon;
    }
    public final String getCapitulo() {
        return this.capitulo;
    }
    public final void setCapitulo(String capitulo) {
        this.capitulo = capitulo;
    }
    public final String getParte() {
        return this.parte;
    }
    public final void setParte(String parte) {
        this.parte = parte;
    }
    public final String getConfirmaRemito() {
        return this.confirmaRemito;
    }
    public final void setConfirmaRemito(String confirmaRemito) {
        this.confirmaRemito = confirmaRemito;
    }
    public final String getConfirmaCopia() {
        return this.confirmaCopia;
    }
    public final void setConfirmaCopia(String confirmaCopia) {
        this.confirmaCopia = confirmaCopia;
    }
    public final String getNumeroRemito() {
        return this.numeroRemito;
    }
    public final void setNumeroRemito(String numeroRemito) {
        this.numeroRemito = numeroRemito;
    }
    public final String getNumeroGuia() {
        return this.numeroGuia;
    }
    public final void setNumeroGuia(String numeroGuia) {
        this.numeroGuia = numeroGuia;
    }
    public final String getFechaMovimiento() {
        return this.fechaMovimiento;
    }
    public final void setFechaMovimiento(String fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }
    public final String getFechaRemitoGuia() {
        return this.fechaRemitoGuia;
    }
    public final void setFechaRemitoGuia(String fechaRemitoGuia) {
        this.fechaRemitoGuia = fechaRemitoGuia;
    }
    public final String getChequeoVolver() {
        return this.chequeoVolver;
    }
    public final void setChequeoVolver(String chequeoVolver) {
        this.chequeoVolver = chequeoVolver;
    }
    public final String getCopiaVolver() {
        return this.copiaVolver;
    }
    public final void setCopiaVolver(String copiaVolver) {
        this.copiaVolver = copiaVolver;
    }
}
