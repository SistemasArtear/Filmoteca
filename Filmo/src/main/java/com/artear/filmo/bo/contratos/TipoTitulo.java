package com.artear.filmo.bo.contratos;

public class TipoTitulo {
    private String tipoTitulo;
    private String descripcionTitulo;
    private String familiaTitulo;
    private Integer ultimoNumero;

    public TipoTitulo() {
    }
    
    public TipoTitulo(String tipoTitulo, String descripcionTitulo, String familiaTitulo, Integer ultimoNumero) {
        this.tipoTitulo = tipoTitulo;
        this.descripcionTitulo = descripcionTitulo;
        this.familiaTitulo = familiaTitulo;
        this.ultimoNumero = ultimoNumero;
    }

    public final String getTipoTitulo() {
        return this.tipoTitulo;
    }

    public final void setTipoTitulo(String tipoTitulo) {
        this.tipoTitulo = tipoTitulo;
    }

    public final String getDescripcionTitulo() {
        return this.descripcionTitulo;
    }

    public final void setDescripcionTitulo(String descripcionTitulo) {
        this.descripcionTitulo = descripcionTitulo;
    }

    public final String getFamiliaTitulo() {
        return this.familiaTitulo;
    }

    public final void setFamiliaTitulo(String familiaTitulo) {
        this.familiaTitulo = familiaTitulo;
    }

    public final Integer getUltimoNumero() {
        return this.ultimoNumero;
    }

    public final void setUltimoNumero(Integer ultimoNumero) {
        this.ultimoNumero = ultimoNumero;
    }

}
