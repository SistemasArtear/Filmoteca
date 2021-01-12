package com.artear.filmo.bo.programacion.modificarRatingExcedente;

public class ModificarRatingExcedenteResponse {
	private boolean ok;
	private String mensaje;

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}
}
