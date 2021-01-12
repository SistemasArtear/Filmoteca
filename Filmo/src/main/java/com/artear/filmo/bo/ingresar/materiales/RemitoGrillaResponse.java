package com.artear.filmo.bo.ingresar.materiales;

import java.math.BigDecimal;
import java.util.Date;

public class RemitoGrillaResponse {

	private BigDecimal idRemito;
	private String razonSocialDistribuidor;
	private Integer codigoDistribuidor;
	private Date fechaRemitoGuia;
	private String numeroRemito;
	private String numeroGuia;
	private Date fechaMovimiento;
	
	public BigDecimal getIdRemito() {
		return idRemito;
	}
	
	public void setIdRemito(BigDecimal idRemito) {
		this.idRemito = idRemito;
	}
	
	public String getRazonSocialDistribuidor() {
		return razonSocialDistribuidor;
	}
	
	public void setRazonSocialDistribuidor(String razonSocialDistribuidor) {
		this.razonSocialDistribuidor = razonSocialDistribuidor;
	}
	
	public Integer getCodigoDistribuidor() {
		return codigoDistribuidor;
	}
	
	public void setCodigoDistribuidor(Integer codigoDistribuidor) {
		this.codigoDistribuidor = codigoDistribuidor;
	}
	
	public Date getFechaRemitoGuia() {
		return fechaRemitoGuia;
	}

	public void setFechaRemitoGuia(Date fechaRemitoGuia) {
		this.fechaRemitoGuia = fechaRemitoGuia;
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
	
	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}
	
	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}
	
}