package com.artear.filmo.bo.ingresar.materiales;

public class ValidarEliminarRemitoResponse {

	private String tipo;
	private String mensaje;
	private String contabiliza;
	private boolean errorValidarCabeceraRemito;
	private boolean confirmacionValidarCabeceraRemito;
	private boolean confirmacionContabiliza;
	private boolean eliminarRemito;
	
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
	
	public String getContabiliza() {
		return contabiliza;
	}
	
	public void setContabiliza(String contabiliza) {
		this.contabiliza = contabiliza;
	}

	public boolean isErrorValidarCabeceraRemito() {
		return errorValidarCabeceraRemito;
	}

	public void setErrorValidarCabeceraRemito(boolean errorValidarCabeceraRemito) {
		this.errorValidarCabeceraRemito = errorValidarCabeceraRemito;
	}

	public boolean isConfirmacionValidarCabeceraRemito() {
		return confirmacionValidarCabeceraRemito;
	}
	
	public void setConfirmacionValidarCabeceraRemito(boolean confirmacionValidarCabeceraRemito) {
		this.confirmacionValidarCabeceraRemito = confirmacionValidarCabeceraRemito;
	}
	
	public boolean isConfirmacionContabiliza() {
		return confirmacionContabiliza;
	}

	public void setConfirmacionContabiliza(boolean confirmacionContabiliza) {
		this.confirmacionContabiliza = confirmacionContabiliza;
	}

	public boolean isEliminarRemito() {
		return eliminarRemito;
	}

	public void setEliminarRemito(boolean eliminarRemito) {
		this.eliminarRemito = eliminarRemito;
	}
	
}