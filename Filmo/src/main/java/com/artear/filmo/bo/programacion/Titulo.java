/**
 * 
 */
package com.artear.filmo.bo.programacion;

/**
 * @author sisloc
 *
 */
public class Titulo {
    
    private String clave;
    private String nroCapitulo;
    private String parte;
    private String segmento;
    private String tituloCastellano;
    private String tituloOriginal;
    private String tituloOFF;
    private String interpretes;
    private String descripcionCapitulo;

    public final String getClave() {
        return this.clave;
    }
    public final void setClave(String clave) {
        this.clave = clave;
    }
    public String getTituloCastellano() {
        return this.tituloCastellano;
    }
    public void setTituloCastellano(String tituloCastellano) {
        this.tituloCastellano = tituloCastellano;
    }
    public String getNroCapitulo() {
        return nroCapitulo;
    }
    public void setNroCapitulo(String nroCapitulo) {
        this.nroCapitulo = nroCapitulo;
    }
    public String getParte() {
        return parte;
    }
    public void setParte(String parte) {
        this.parte = parte;
    }
    public String getSegmento() {
        return segmento;
    }
    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }
    public String getTituloOriginal() {
        return tituloOriginal;
    }
    public void setTituloOriginal(String tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
    }
    public String getTituloOFF() {
        return tituloOFF;
    }
    public void setTituloOFF(String tituloOFF) {
        this.tituloOFF = tituloOFF;
    }
    public String getInterpretes() {
        return interpretes;
    }
    public void setInterpretes(String interpretes) {
        this.interpretes = interpretes;
    }
    public String getDescripcionCapitulo() {
        return descripcionCapitulo;
    }
    public void setDescripcionCapitulo(String descripcionCapitulo) {
        this.descripcionCapitulo = descripcionCapitulo;
    }
}
