package com.artear.filmo.bo.contratos;


public class ContratoConReRun {
    private Integer contrato;
    private Integer grupo;
    private Integer distribuidor;
    private String vigenciaDesde;
    private String vigenciaHasta;
    private Integer enlazadoAnterior;
    private Integer grupoAnterior;
    private Integer enlazadoPosterior;
    private Integer grupoPosterior;
    private String puedeAnterior;
    private String puedePosterior;
    private String puedeDesenlazar;

    public final Integer getContrato() {
        return this.contrato;
    }
    public final void setContrato(Integer contrato) {
        this.contrato = contrato;
    }
    public final Integer getGrupo() {
        return this.grupo;
    }
    public final void setGrupo(Integer grupo) {
        this.grupo = grupo;
    }
    public final Integer getDistribuidor() {
        return this.distribuidor;
    }
    public final void setDistribuidor(Integer distribuidor) {
        this.distribuidor = distribuidor;
    }
    public final String getVigenciaDesde() {
        return this.vigenciaDesde;
    }
    public final void setVigenciaDesde(String vigenciaDesde) {
        this.vigenciaDesde = vigenciaDesde;
    }
    public final String getVigenciaHasta() {
        return this.vigenciaHasta;
    }
    public final void setVigenciaHasta(String vigenciaHasta) {
        this.vigenciaHasta = vigenciaHasta;
    }
    public final Integer getEnlazadoAnterior() {
        return this.enlazadoAnterior;
    }
    public final void setEnlazadoAnterior(Integer enlazadoAnterior) {
        this.enlazadoAnterior = enlazadoAnterior;
    }
    public final Integer getGrupoAnterior() {
        return this.grupoAnterior;
    }
    public final void setGrupoAnterior(Integer grupoAnterior) {
        this.grupoAnterior = grupoAnterior;
    }
    public final Integer getEnlazadoPosterior() {
        return this.enlazadoPosterior;
    }
    public final void setEnlazadoPosterior(Integer enlazadoPosterior) {
        this.enlazadoPosterior = enlazadoPosterior;
    }
    public final Integer getGrupoPosterior() {
        return this.grupoPosterior;
    }
    public final void setGrupoPosterior(Integer grupoPosterior) {
        this.grupoPosterior = grupoPosterior;
    }

    public void setPuedeAnterior(String puedeAnterior) {
        this.puedeAnterior = puedeAnterior;
        
    }

    public void setPuedePosterior(String puedePosterior) {
        this.puedePosterior = puedePosterior;
        
    }

    public void setPuedeDesenlazar(String puedeDesenlazar) {
        this.puedeDesenlazar = puedeDesenlazar;
    }

    public String getPuedeAnterior() {
        return this.puedeAnterior;
    }
    

    public String getPuedeDesenlazar() {
        return this.puedeDesenlazar;
    }
    
    public String getPuedePosterior() {
        return this.puedePosterior;
    }
}
