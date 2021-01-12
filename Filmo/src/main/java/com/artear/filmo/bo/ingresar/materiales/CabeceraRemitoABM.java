package com.artear.filmo.bo.ingresar.materiales;

import java.math.BigDecimal;
import java.util.Date;

public class CabeceraRemitoABM {

	private BigDecimal idRemito;
	private Date fechaIngreso;
	private Date fechaRemito;
	private String numeroRemito;
	private String numeroGuia;
	
	public BigDecimal getIdRemito() {
		return idRemito;
	}
	
	public void setIdRemito(BigDecimal idRemito) {
		this.idRemito = idRemito;
	}
	
	public Date getFechaIngreso() {
		return fechaIngreso;
	}
	
	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	
	public Date getFechaRemito() {
		return fechaRemito;
	}
	
	public void setFechaRemito(Date fechaRemito) {
		this.fechaRemito = fechaRemito;
	}
	
	public String getNumeroRemito() {
		return numeroRemito;
	}
	
	public void setNumeroRemito(String numeroRemito) {
		this.numeroRemito = numeroRemito;
	}
	
	public String getNumeroGuia() {
		return numeroGuia;
	}
	
	public void setNumeroGuia(String numeroGuia) {
		this.numeroGuia = numeroGuia;
	}
	
}