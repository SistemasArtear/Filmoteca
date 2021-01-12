package com.artear.filmo.bo.programacion.confirmar;

public class ProcesarConfirmacionResponse {

    private String tipo;
    private Integer idReporte;
    private String mensaje;
	
    public String getTipo() {
		return tipo;
	}
	
    public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
    public Integer getIdReporte() {
		return idReporte;
	}
	
    public void setIdReporte(Integer idReporte) {
		this.idReporte = idReporte;
	}
    
    public String getMensaje() {
		return mensaje;
	}
    
    public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
    
}