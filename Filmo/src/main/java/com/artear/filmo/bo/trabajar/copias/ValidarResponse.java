package com.artear.filmo.bo.trabajar.copias;

public class ValidarResponse {

	private String tipo;
	private String mensaje;

	public ValidarResponse() {
		super();
	}

	public ValidarResponse(String tipo, String mensaje) {
		super();
		this.tipo = tipo;
		this.mensaje = mensaje;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
