package com.artear.filmo.bo.contratos;

import java.math.BigDecimal;
import java.util.Date;

public class GuardarDatosCabeceraContratoRequest {

	private Integer proveedor;
	private String moneda;
	private Date fechaContrato;
	private Date fechaAprobacion;
	private BigDecimal montoTotal;
	private String canje;
	private String observaciones;

	public Integer getProveedor() {
		return proveedor;
	}

	public void setProveedor(Integer proveedor) {
		this.proveedor = proveedor;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public Date getFechaContrato() {
		return fechaContrato;
	}

	public void setFechaContrato(Date fechaContrato) {
		this.fechaContrato = fechaContrato;
	}

	public Date getFechaAprobacion() {
		return fechaAprobacion;
	}

	public void setFechaAprobacion(Date fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}
	
	public BigDecimal getMontoTotal() {
		return montoTotal;
	}
	
	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}

	public String getCanje() {
		return canje;
	}

	public void setCanje(String canje) {
		this.canje = canje;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
}