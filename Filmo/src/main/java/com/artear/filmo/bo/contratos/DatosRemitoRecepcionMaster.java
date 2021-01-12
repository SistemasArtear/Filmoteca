package com.artear.filmo.bo.contratos;

import java.util.Date;

public class DatosRemitoRecepcionMaster {
	
	private Integer nroRemito;
	private String nroGuia;
    private Date fechaIngreso;
    private Date fechaRtoGuia;
    private String chequeo;
    private String soporte;
    private Date fechaCopia;

	public Integer getNroRemito() {
		return nroRemito;
	}

	public void setNroRemito(Integer nroRemito) {
		this.nroRemito = nroRemito;
	}

	public String getNroGuia() {
		return nroGuia;
	}

	public void setNroGuia(String nroGuia) {
		this.nroGuia = nroGuia;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Date getFechaRtoGuia() {
		return fechaRtoGuia;
	}

	public void setFechaRtoGuia(Date fechaRtoGuia) {
		this.fechaRtoGuia = fechaRtoGuia;
	}

	public String getChequeo() {
		return chequeo;
	}
	
	public void setChequeo(String chequeo) {
		this.chequeo = chequeo;
	}
	
	public String getSoporte() {
		return soporte;
	}

	public void setSoporte(String soporte) {
		this.soporte = soporte;
	}

	public Date getFechaCopia() {
		return fechaCopia;
	}

	public void setFechaCopia(Date fechaCopia) {
		this.fechaCopia = fechaCopia;
	}
    
}