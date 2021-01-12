/**
 * 
 */
package com.artear.filmo.bo.contratos;

public class VigenciaRequest {
  //PROCEDURE PR_VALIDA_PAYTV( P_CONTRATO          IN NUMBER,
  //P_GRUPO            IN NUMBER,
  //P_SENIAL            IN VARCHAR2,
  //P_TIPO_TITULO     IN VARCHAR2,
  //P_NRO_TITULO     IN NUMBER,
  //P_PAYTV_ID        IN NUMBER,
  //P_FEC_DESDE_PAYTV IN DATE,
  //P_FEC_HASTA_PAYTV IN DATE,
  //P_OBSERVACIONES   IN VARCHAR2,
  //P_MODO                IN VARCHAR2,
  //P_ERRORES         OUT SYS_REFCURSOR )
  //PROCEDURE PR_PROCESAR_PAYTV ( P_CONTRATO       IN NUMBER,
  //P_GRUPO             IN NUMBER,
  //P_SENIAL             IN VARCHAR2,
  //P_TIPO_TITULO     IN VARCHAR2,
  //P_NRO_TITULO      IN NUMBER,
  //P_PAYTV                IN NUMBER,
  //P_FEC_DESDE_PAYTV  IN DATE,
  //P_FEC_HASTA_PAYTV  IN DATE,
  //P_OBSERVACIONES    IN VARCHAR2,
  //P_MODO             IN VARCHAR2,
  //P_USUARIO         IN VARCHAR2 );

    private Integer contrato;
    private Integer grupo;
    private String senial;
    private String clave;
    private Integer payTVId;
    private String fechaDesdePayTV;
    private String fechaHastaPayTV;
    private String observaciones;
    private String modo;
    
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
    public String getFechaHastaPayTV() {
        return fechaHastaPayTV;
    }
    public void setFechaHastaPayTV(String fechaHastaPayTV) {
        this.fechaHastaPayTV = fechaHastaPayTV;
    }
    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    public Integer getPayTVId() {
        return payTVId;
    }
    public void setPayTVId(Integer payTVId) {
        this.payTVId = payTVId;
    }
    public String getFechaDesdePayTV() {
        return fechaDesdePayTV;
    }
    public void setFechaDesdePayTV(String fechaDesdePayTV) {
        this.fechaDesdePayTV = fechaDesdePayTV;
    }

    public String getModo() {
        return modo;
    }
    
    public String setModo(String modo) {
        return this.modo = modo;
    }

}
