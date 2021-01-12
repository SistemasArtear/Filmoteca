/**
 * 
 */
package com.artear.filmo.bo.contratos;

public class RerunRequest {

    private Integer contrato;
    private Integer grupo;
    private String senial;
    private String clave;
    private Integer rerunContrato;
    private Integer rerunGrupo;
    private Integer rerunEnlaceAnterior;
    private Integer rerunGrupoAnterior;
    private Integer rerunEnlacePosterior;
    private Integer rerunGrupoPosterior;
    private String rerunVigenciaDesde;
    private String rerunVigenciaHasta;
    private String proceso;
    
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
    public final String getSenial() {
        return this.senial;
    }
    public final void setSenial(String senial) {
        this.senial = senial;
    }
    public final String getClave() {
        return this.clave;
    }
    public final void setClave(String clave) {
        this.clave = clave;
    }
    public final Integer getRerunContrato() {
        return this.rerunContrato;
    }
    public final void setRerunContrato(Integer rerunContrato) {
        this.rerunContrato = rerunContrato;
    }
    public final Integer getRerunGrupo() {
        return this.rerunGrupo;
    }
    public final void setRerunGrupo(Integer rerunGrupo) {
        this.rerunGrupo = rerunGrupo;
    }
    public final Integer getRerunEnlaceAnterior() {
        return this.rerunEnlaceAnterior;
    }
    public final void setRerunEnlaceAnterior(Integer rerunEnlaceAnterior) {
        this.rerunEnlaceAnterior = rerunEnlaceAnterior;
    }
    public final Integer getRerunGrupoAnterior() {
        return this.rerunGrupoAnterior;
    }
    public final void setRerunGrupoAnterior(Integer rerunGrupoAnterior) {
        this.rerunGrupoAnterior = rerunGrupoAnterior;
    }
    public final Integer getRerunEnlacePosterior() {
        return this.rerunEnlacePosterior;
    }
    public final void setRerunEnlacePosterior(Integer rerunEnlacePosterior) {
        this.rerunEnlacePosterior = rerunEnlacePosterior;
    }
    public final Integer getRerunGrupoPosterior() {
        return this.rerunGrupoPosterior;
    }
    public final void setRerunGrupoPosterior(Integer rerunGrupoPosterior) {
        this.rerunGrupoPosterior = rerunGrupoPosterior;
    }
    public final String getRerunVigenciaDesde() {
        return this.rerunVigenciaDesde;
    }
    public final void setRerunVigenciaDesde(String rerunVigenciaDesde) {
        this.rerunVigenciaDesde = rerunVigenciaDesde;
    }
    public final String getRerunVigenciaHasta() {
        return this.rerunVigenciaHasta;
    }
    public final void setRerunVigenciaHasta(String rerunVigenciaHasta) {
        this.rerunVigenciaHasta = rerunVigenciaHasta;
    }
    public void setProceso(String proceso) {
        this.proceso = proceso;
    }
    public String getProceso() {
        return this.proceso;
    }
}
