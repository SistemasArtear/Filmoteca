package com.artear.filmo.bo.trabajar.remitos;

import java.util.List;

public class ValidarNuevaSenialResponse {

	private String mensaje;
	private List<ValidarNuevaSenialView> registros;

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<ValidarNuevaSenialView> getRegistros() {
		return registros;
	}

	public void setRegistros(List<ValidarNuevaSenialView> registros) {
		this.registros = registros;
	}
    
}