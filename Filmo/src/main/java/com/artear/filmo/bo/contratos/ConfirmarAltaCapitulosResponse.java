package com.artear.filmo.bo.contratos;

import java.util.List;

public class ConfirmarAltaCapitulosResponse {

	private List<DatosMaster> datosMaster;
	private String mensaje;

	public List<DatosMaster> getDatosMaster() {
		return datosMaster;
	}

	public void setDatosMaster(List<DatosMaster> datosMaster) {
		this.datosMaster = datosMaster;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
}