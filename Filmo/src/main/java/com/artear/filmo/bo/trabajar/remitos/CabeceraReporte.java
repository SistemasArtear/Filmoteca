package com.artear.filmo.bo.trabajar.remitos;

import java.math.BigDecimal;
import java.util.Date;

public class CabeceraReporte {

	private Integer puntoVenta;
	private String numeroRemito;
	private Date fechaRemito;
	private String tipoSalida;
	private String razonSocial;
	private String direccion;
	private BigDecimal cuit;
	private String cai;
	
	public Integer getPuntoVenta() {
		return puntoVenta;
	}
	
	public void setPuntoVenta(Integer puntoVenta) {
		this.puntoVenta = puntoVenta;
	}
	
	public String getNumeroRemito() {
		return numeroRemito;
	}
	
	public void setNumeroRemito(String numeroRemito) {
		this.numeroRemito = numeroRemito;
	}
	
	public Date getFechaRemito() {
		return fechaRemito;
	}
	
	public void setFechaRemito(Date fechaRemito) {
		this.fechaRemito = fechaRemito;
	}
	
	public String getTipoSalida() {
		return tipoSalida;
	}
	
	public void setTipoSalida(String tipoSalida) {
		this.tipoSalida = tipoSalida;
	}
	
	public String getRazonSocial() {
		return razonSocial;
	}
	
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	
	public String getDireccion() {
		return direccion;
	}
	
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public BigDecimal getCuit() {
		return cuit;
	}
	
	public void setCuit(BigDecimal cuit) {
		this.cuit = cuit;
	}
	
	public String getCai() {
		return cai;
	}
	
	public void setCai(String cai) {
		this.cai = cai;
	}
   
}