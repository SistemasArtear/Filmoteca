package com.artear.filmo.bo.ingresar.materiales;

public class ValidarEliminarDetalleRemitoResponse {

	private String borroCh;
	private String flagChsn;
	private String tipo;
	private String respuesta;
	private String contabiliza;
	private boolean errorValidarContabilizado;
	private boolean errorVerificar;
	private boolean errorValidarRPF22022;
	private boolean confirmacionContabiliza;
	private boolean eliminarRemitoDesde;
	
	public String getBorroCh() {
		return borroCh;
	}
	
	public void setBorroCh(String borroCh) {
		this.borroCh = borroCh;
	}
	
	public String getFlagChsn() {
		return flagChsn;
	}
	
	public void setFlagChsn(String flagChsn) {
		this.flagChsn = flagChsn;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getRespuesta() {
		return respuesta;
	}
	
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	
	public String getContabiliza() {
		return contabiliza;
	}
	
	public void setContabiliza(String contabiliza) {
		this.contabiliza = contabiliza;
	}

	public boolean isErrorValidarContabilizado() {
		return errorValidarContabilizado;
	}

	public void setErrorValidarContabilizado(boolean errorValidarContabilizado) {
		this.errorValidarContabilizado = errorValidarContabilizado;
	}

	public boolean isErrorVerificar() {
		return errorVerificar;
	}

	public void setErrorVerificar(boolean errorVerificar) {
		this.errorVerificar = errorVerificar;
	}

	public boolean isErrorValidarRPF22022() {
		return errorValidarRPF22022;
	}

	public void setErrorValidarRPF22022(boolean errorValidarRPF22022) {
		this.errorValidarRPF22022 = errorValidarRPF22022;
	}

	public boolean isConfirmacionContabiliza() {
		return confirmacionContabiliza;
	}

	public void setConfirmacionContabiliza(boolean confirmacionContabiliza) {
		this.confirmacionContabiliza = confirmacionContabiliza;
	}

	public boolean isEliminarRemitoDesde() {
		return eliminarRemitoDesde;
	}

	public void setEliminarRemitoDesde(boolean eliminarRemitoDesde) {
		this.eliminarRemitoDesde = eliminarRemitoDesde;
	}
	
}