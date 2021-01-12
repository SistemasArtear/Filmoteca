package com.artear.filmo.exceptions.util;

import java.io.Serializable;

public class ErrorFilmo implements Serializable {

	private static final long serialVersionUID = -2988706579287747397L;
	private String codigo;
	private String mensaje;

	public ErrorFilmo() {
	}

	public ErrorFilmo(String codigo, String mensaje) {
		super();
		this.codigo = codigo;
		this.mensaje = mensaje;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	@Override
	public String toString() {
		return "ListErrors [codigo=" + codigo + ", mensaje=" + mensaje + "]";
	}

}
