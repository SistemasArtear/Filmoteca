package com.artear.filmo.bo.trabajar.remitos;

import java.math.BigDecimal;
import java.util.Date;

public class EjecutarRemitoRequest {

	private Integer idRemito;
	private String senial;
	private Date fechaRemito;
	private String estadoImprimir;
	private String clave;
	private String capitulo;
	private String parte;
	private String motivo;
	private Integer codigoDistribuidor;
	private Integer diasPrestamo;
	private String destinatario;
	private BigDecimal numFedex;
	private String observaciones;
	
	public Integer getIdRemito() {
		return idRemito;
	}
	
	public void setIdRemito(Integer idRemito) {
		this.idRemito = idRemito;
	}
	
	public String getSenial() {
		return senial;
	}
	
	public void setSenial(String senial) {
		this.senial = senial;
	}
	
	public Date getFechaRemito() {
		return fechaRemito;
	}
	
	public void setFechaRemito(Date fechaRemito) {
		this.fechaRemito = fechaRemito;
	}
	
	public String getEstadoImprimir() {
		return estadoImprimir;
	}
	
	public void setEstadoImprimir(String estadoImprimir) {
		this.estadoImprimir = estadoImprimir;
	}
	
	public String getClave() {
		return clave;
	}
	
	public void setClave(String clave) {
		this.clave = clave;
	}
	
	public String getCapitulo() {
		return capitulo;
	}

	public void setCapitulo(String capitulo) {
		this.capitulo = capitulo;
	}

	public String getParte() {
		return parte;
	}

	public void setParte(String parte) {
		this.parte = parte;
	}

	public String getMotivo() {
		return motivo;
	}
	
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Integer getCodigoDistribuidor() {
		return codigoDistribuidor;
	}
	
	public void setCodigoDistribuidor(Integer codigoDistribuidor) {
		this.codigoDistribuidor = codigoDistribuidor;
	}
	
	public Integer getDiasPrestamo() {
		return diasPrestamo;
	}
	
	public void setDiasPrestamo(Integer diasPrestamo) {
		this.diasPrestamo = diasPrestamo;
	}
	
	public String getDestinatario() {
		return destinatario;
	}
	
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	
	public BigDecimal getNumFedex() {
		return numFedex;
	}
	
	public void setNumFedex(BigDecimal numFedex) {
		this.numFedex = numFedex;
	}
	
	public String getObservaciones() {
		return observaciones;
	}
	
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
}