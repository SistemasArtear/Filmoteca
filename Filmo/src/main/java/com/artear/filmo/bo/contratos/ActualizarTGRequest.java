package com.artear.filmo.bo.contratos;

public class ActualizarTGRequest {

    private String usuario;
    private String fecha;
    private String hora;
    private Integer renglon;

    public String getUsuario() {
        return usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public Integer getRenglon() {
        return renglon;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setRenglon(Integer renglon) {
        this.renglon = renglon;
    }
}
