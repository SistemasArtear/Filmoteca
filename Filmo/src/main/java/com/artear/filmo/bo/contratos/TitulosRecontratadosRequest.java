/**
 * 
 */
package com.artear.filmo.bo.contratos;

/**
 * @author sisloc
 * 
 */
public class TitulosRecontratadosRequest {
    private String clave;
    private String senial;
    private Integer contrato;
    private Integer grupo;
    private String puedeElegirParcial;
    private String modo;
    private String filtro;
    
    public final String getClave() {
        return this.clave;
    }

    public final void setClave(String clave) {
        this.clave = clave;
    }

    public final String getSenial() {
        return this.senial;
    }

    public final void setSenial(String senial) {
        this.senial = senial;
    }

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

    public void setPuedeElegirParcial(String puedeElegirParcial) {
        this.puedeElegirParcial = puedeElegirParcial;
    }
    
    public final String getPuedeElegirParcial() {
        return this.puedeElegirParcial;
    }

    public final String getModo() {
        return this.modo;
    }
    
    public void setModo(String modo) {
        this.modo = modo;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }
}
