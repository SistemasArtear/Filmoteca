/**
 * 
 */
package com.artear.filmo.bo.contratos;


/**
 * @author sisloc
 *
 */
public class TituloRequest {
    
    private String clave;
    private String origen;
    private Integer contrato;
    private Integer grupo;
    private String senial;
    private String familia;
    private String sinContrato;
    private String tipoTitulo;
    
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
    public final String getFamilia() {
        return this.familia;
    }
    public final void setFamilia(String familia) {
        this.familia = familia;
    }
    public final String getSinContrato() {
        return this.sinContrato;
    }
    public final void setSinContrato(String sinContrato) {
        this.sinContrato = sinContrato;
    }
    public final String getClave() {
        return this.clave;
    }
    public final void setClave(String clave) {
        this.clave = clave;
    }
    public final String getOrigen() {
        return this.origen;
    }
    public final void setOrigen(String origen) {
        this.origen = origen;
    }

    public final String getTipoTitulo() {
        return this.tipoTitulo;
    }

    public void setTipoTitulo(String tipoTitulo) {
        this.tipoTitulo = tipoTitulo;
    }
}
