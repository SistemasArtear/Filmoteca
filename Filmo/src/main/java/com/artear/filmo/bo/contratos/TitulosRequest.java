/**
 * 
 */
package com.artear.filmo.bo.contratos;


/**
 * @author sisloc
 *
 */
public class TitulosRequest {
    
    private String tituloABuscar;
    private String tipoTitulo;
    private String familia;
    
    private Integer contrato;
    private Integer grupo;
    private String senial;
    private String marcaRecontratacion;
    private String estrenoOrepeticion;
    private String buscarPor;
    
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
    public final String getTituloABuscar() {
        return this.tituloABuscar;
    }
    public final void setTituloABuscar(String tituloABuscar) {
        this.tituloABuscar = tituloABuscar;
    }
    public final String getTipoTitulo() {
        return this.tipoTitulo;
    }
    public final void setTipoTitulo(String tipoTitulo) {
        this.tipoTitulo = tipoTitulo;
    }
    public final String getFamilia() {
        return this.familia;
    }
    public final void setFamilia(String familia) {
        this.familia = familia;
    }
    public String getEstrenoOrepeticion() {
        return estrenoOrepeticion;
    }
    public void setEstrenoOrepeticion(String estrenoOrepeticion) {
        this.estrenoOrepeticion = estrenoOrepeticion;
    }
    public String getMarcaRecontratacion() {
        return marcaRecontratacion;
    }
    public void setMarcaRecontratacion(String marcaRecontratacion) {
        this.marcaRecontratacion = marcaRecontratacion;
    }
    public String getBuscarPor() {
        return buscarPor;
    }
    public void setBuscarPor(String buscarPor) {
        this.buscarPor = buscarPor;
    }
}
