package com.artear.filmo.bo.trabajar.remitos;

public class EjecutarRemitoResponse {

	private CabeceraReporte cabeceraReporte;
	private CuerpoReporte cuerpoReporte;
	private String mensaje;
	private Integer idRemito;
	
	public CabeceraReporte getCabeceraReporte() {
		return cabeceraReporte;
	}
	
	public void setCabeceraReporte(CabeceraReporte cabeceraReporte) {
		this.cabeceraReporte = cabeceraReporte;
	}
	
	public CuerpoReporte getCuerpoReporte() {
		return cuerpoReporte;
	}
	
	public void setCuerpoReporte(CuerpoReporte cuerpoReporte) {
		this.cuerpoReporte = cuerpoReporte;
	}
	
	public String getMensaje() {
		return mensaje;
	}
	
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public Integer getIdRemito() {
		return idRemito;
	}
	
	public void setIdRemito(Integer idRemito) {
		this.idRemito = idRemito;
	}
	
}