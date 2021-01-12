package com.artear.filmo.bo.contratos;

public class ValidarAmortizacionResponse {

	private Boolean exito;
	private String mensaje;
	private Integer primerIdAmortizacion;
	private String descripcionPrimerIdAmortizacion;
	private Integer segundoIdAmortizacion;
	private String descripcionSegundoIdAmortizacion;
	
	public Boolean getExito() {
		return exito;
	}

	public void setExito(Boolean exito) {
		this.exito = exito;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Integer getPrimerIdAmortizacion() {
		return primerIdAmortizacion;
	}

	public void setPrimerIdAmortizacion(Integer primerIdAmortizacion) {
		this.primerIdAmortizacion = primerIdAmortizacion;
	}

	public String getDescripcionPrimerIdAmortizacion() {
		return descripcionPrimerIdAmortizacion;
	}
	
	public void setDescripcionPrimerIdAmortizacion(String descripcionPrimerIdAmortizacion) {
		this.descripcionPrimerIdAmortizacion = descripcionPrimerIdAmortizacion;
	}
	
	public Integer getSegundoIdAmortizacion() {
		return segundoIdAmortizacion;
	}

	public void setSegundoIdAmortizacion(Integer segundoIdAmortizacion) {
		this.segundoIdAmortizacion = segundoIdAmortizacion;
	}
	
	public String getDescripcionSegundoIdAmortizacion() {
		return descripcionSegundoIdAmortizacion;
	}
	
	public void setDescripcionSegundoIdAmortizacion(String descripcionSegundoIdAmortizacion) {
		this.descripcionSegundoIdAmortizacion = descripcionSegundoIdAmortizacion;
	}
	
}