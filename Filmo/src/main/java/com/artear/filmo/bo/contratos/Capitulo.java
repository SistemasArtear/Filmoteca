/**
 * 
 */
package com.artear.filmo.bo.contratos;

/**
 * @author sisloc
 *
 */
public class Capitulo {
    private String clave;
    private Integer nroCapitulo;
    private Integer parte;
    private String tituloCapitulo;
    private String contRec;
    private String programado;
    private String confExhibicion;
    private String seleccionado;
    private String copia;
    private String original;
    private String puedoBorrar;

    public final Integer getNroCapitulo() {
        return this.nroCapitulo;
    }
    public final void setNroCapitulo(Integer nroCapitulo) {
        this.nroCapitulo = nroCapitulo;
    }
    public final Integer getParte() {
        return this.parte;
    }
    public final void setParte(Integer parte) {
        this.parte = parte;
    }
    public final String getTituloCapitulo() {
        return this.tituloCapitulo;
    }
    public final void setTituloCapitulo(String tituloCapitulo) {
        this.tituloCapitulo = tituloCapitulo;
    }
    public final String getContRec() {
        return this.contRec;
    }
    public final void setContRec(String contRec) {
        this.contRec = contRec;
    }
    public final String getProgramado() {
        return this.programado;
    }
    public final void setProgramado(String programado) {
        this.programado = programado;
    }
    public final String getConfExhibicion() {
        return this.confExhibicion;
    }
    public final void setConfExhibicion(String confExhibicion) {
        this.confExhibicion = confExhibicion;
    }

    public void setSeleccionado(String seleccionado) {
        this.seleccionado = seleccionado;
    }

    public String getSeleccionado() {
        return this.seleccionado;
    }
    public String getClave() {
        return clave;
    }
    public void setClave(String clave) {
        this.clave = clave;
    }
    public String getCopia() {
        return copia;
    }
    public void setCopia(String copia) {
        this.copia = copia;
    }
    public String getOriginal() {
        return original;
    }
    public void setOriginal(String original) {
        this.original = original;
    }

    public void setPuedoBorrar(String puedoBorrar) {
        this.puedoBorrar = puedoBorrar;
    }
    
    public String getPuedoBorrar() {
        return this.puedoBorrar;
    }
}
