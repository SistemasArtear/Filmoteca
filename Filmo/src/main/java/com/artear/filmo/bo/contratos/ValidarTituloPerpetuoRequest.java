/**
 * 
 */
package com.artear.filmo.bo.contratos;


public class ValidarTituloPerpetuoRequest {
    
    private Integer contrato;
    private Integer grupo;
    private String senial;
    private String tipoTitulo;
    private Integer nroTitulo;
    
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
    public final String getTipoTitulo() {
        return this.tipoTitulo;
    }
    public final void setTipoTitulo(String tipoTitulo) {
        this.tipoTitulo = tipoTitulo;
    }
    public final Integer getNroTitulo() {
        return this.nroTitulo;
    }
    public final void setNroTitulo(Integer nroTitulo) {
        this.nroTitulo = nroTitulo;
    }
}
