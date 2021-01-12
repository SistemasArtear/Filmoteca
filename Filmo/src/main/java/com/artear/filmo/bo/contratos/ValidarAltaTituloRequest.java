/**
 * 
 */
package com.artear.filmo.bo.contratos;


public class ValidarAltaTituloRequest {
    
    private Integer contrato;
    private Integer grupo;
    private String senial;
    private String clave;
    private String origen;
    private String sinContrato;
    private String tituloOriginal;
    private String tituloCastellano;
    private String actores1;
    private String actores2;
    private String observaciones;
    private String respuesta;
    private String fechaProcesoTG;
    private String horaProcesoTG;
    private String er;
    private String recontratacion;
    private Integer proveedor;
    private String idFicha;
    private String calificacion;
    private String modo;
    private Integer nroRelacionante;

    public final String getTituloOriginal() {
        return this.tituloOriginal;
    }
    public final void setTituloOriginal(String tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
    }
    public final String getTituloCastellano() {
        return this.tituloCastellano;
    }
    public final void setTituloCastellano(String tituloCastellano) {
        this.tituloCastellano = tituloCastellano;
    }
    public final String getEr() {
        return this.er;
    }
    public final void setEr(String er) {
        this.er = er;
    }
    public final String getRecontratacion() {
        return this.recontratacion;
    }
    public final void setRecontratacion(String recontratacion) {
        this.recontratacion = recontratacion;
    }
    public final Integer getProveedor() {
        return this.proveedor;
    }
    public final void setProveedor(Integer proveedor) {
        this.proveedor = proveedor;
    }
    public Integer getContrato() {
        return contrato;
    }
    public void setContrato(Integer contrato) {
        this.contrato = contrato;
    }
    public Integer getGrupo() {
        return grupo;
    }
    public void setGrupo(Integer grupo) {
        this.grupo = grupo;
    }
    public String getSenial() {
        return senial;
    }
    public void setSenial(String senial) {
        this.senial = senial;
    }
    public String getClave() {
        return clave;
    }
    public void setClave(String clave) {
        this.clave = clave;
    }
    public String getOrigen() {
        return origen;
    }
    public void setOrigen(String origen) {
        this.origen = origen;
    }
    public String getSinContrato() {
        return sinContrato;
    }
    public void setSinContrato(String sinContrato) {
        this.sinContrato = sinContrato;
    }
    
    public void setActores1(String actor1) {
        this.actores1 = actor1;
    }
    
    public String getActores1() {
        return this.actores1;
    }
    
    public void setActores2(String actores2) {
        this.actores2 = actores2;
    }
    
    public String getActores2() {
        return this.actores2;
    }
    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public void setFechaProcesoTG(String fechaProcesoTG) {
        this.fechaProcesoTG = fechaProcesoTG;
    }
    
    public String getFechaProcesoTG() {
        return this.fechaProcesoTG;
    }
    
    public void setHoraProcesoTG(String horaProcesoTG) {
        this.horaProcesoTG = horaProcesoTG;
    }
    
    public String getHoraProcesoTG() {
        return this.horaProcesoTG;
    }
    
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
    
    public String getRespuesta() {
        return this.respuesta;
    }
    
    public void setIdFicha(String idFicha) {
        this.idFicha = idFicha;
    }
    
    public String getIdFicha() {
        return this.idFicha;
    }

    public String getCalificacion() {
        return this.calificacion;
    }
    
    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public String getModo() {
        return modo;
    }

    public Integer getNroRelacionante() {
        return nroRelacionante;
    }
    
    public void setModo(String modo) {
        this.modo = modo;
    }
    
    public void setNroRelacionante(Integer nroRelacionante) {
        this.nroRelacionante = nroRelacionante;
    }
}
