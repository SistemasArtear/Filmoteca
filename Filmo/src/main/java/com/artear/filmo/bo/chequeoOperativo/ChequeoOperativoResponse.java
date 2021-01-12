package com.artear.filmo.bo.chequeoOperativo;

import java.math.BigDecimal;

public class ChequeoOperativoResponse {

	private String mensajeError;
	private boolean hayError = false;
	private boolean hayRegistrosExhibicion = false;
	private boolean hayRegistrosLibreria = false ;
	private BigDecimal codigoError;
	private BigDecimal idReporteLibreria;
	private BigDecimal idReporteEhibicion;
	
	
	public String getMensajeError() {
		return mensajeError;
	}
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}
	public boolean isHayError() {
		return hayError;
	}
	public void setHayError(boolean hayError) {
		this.hayError = hayError;
	}
	public BigDecimal getCodigoError() {
		return codigoError;
	}
	public void setCodigoError(BigDecimal codigoError) {
		this.codigoError = codigoError;
	}
	public BigDecimal getIdReporteLibreria() {
		return idReporteLibreria;
	}
	public void setIdReporteLibreria(BigDecimal idReporteLibreria) {
		this.idReporteLibreria = idReporteLibreria;
	}
	public BigDecimal getIdReporteEhibicion() {
		return idReporteEhibicion;
	}
	public void setIdReporteEhibicion(BigDecimal idReporteEhibicion) {
		this.idReporteEhibicion = idReporteEhibicion;
	}
	public boolean isHayRegistrosExhibicion() {
		return hayRegistrosExhibicion;
	}
	public void setHayRegistrosExhibicion(boolean hayRegistrosExhibicion) {
		this.hayRegistrosExhibicion = hayRegistrosExhibicion;
	}
	public boolean isHayRegistrosLibreria() {
		return hayRegistrosLibreria;
	}
	public void setHayRegistrosLibreria(boolean hayRegistrosLibreria) {
		this.hayRegistrosLibreria = hayRegistrosLibreria;
	}

	
	
	
}
