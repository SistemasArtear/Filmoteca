/**
 * 
 */
package com.artear.filmo.bo.contratos;

/**
 * @author sisloc
 *
 */
public class GruposConReemplazoRequest {
    private Integer contrato;
    private Integer mayorGrupo;
    private String senial;
    private Integer importeReemplazo;
    private Integer nroRelacionante;
    
    public final Integer getContrato() {
        return this.contrato;
    }
    public final void setContrato(Integer contrato) {
        this.contrato = contrato;
    }
    public final Integer getMayorGrupo() {
        return this.mayorGrupo;
    }
    public final void setMayorGrupo(Integer mayorGrupo) {
        this.mayorGrupo = mayorGrupo;
    }
    public final String getSenial() {
        return this.senial;
    }
    public final void setSenial(String senial) {
        this.senial = senial;
    }
    public final Integer getImporteReemplazo() {
        return this.importeReemplazo;
    }
    public final void setImporteReemplazo(Integer importeReemplazo) {
        this.importeReemplazo = importeReemplazo;
    }
    public final Integer getNroRelacionante() {
        return this.nroRelacionante;
    }
    public final void setNroRelacionante(Integer nroRelacionante) {
        this.nroRelacionante = nroRelacionante;
    }
}
