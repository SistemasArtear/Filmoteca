package com.artear.filmo.bo.programacion.modificarRatingExcedente;

import java.util.Date;

public class BusquedaTitulosRatingExcedenteResponse {
	private String tipoNroTitulo;
	private Integer capNro;
	private Integer grupo;
	private Integer contrato;
	private Date fechaExhibicion;
	private Double valorRatingExcedente;
	private String codigoDesPrograma;
	private String tipoRatingExcedente;
	private String mensaje;
	
	public String getTipoNroTitulo() {
		return tipoNroTitulo;
	}
	public void setTipoNroTitulo(String tipoNroTitulo) {
		this.tipoNroTitulo = tipoNroTitulo;
	}
	public Integer getCapitulo() {
		return capNro;
	}
	public void setCapitulo(Integer capNro) {
		this.capNro = capNro;
	}
	public Integer getGrupo() {
		return grupo;
	}
	public void setGrupo(Integer grupo) {
		this.grupo = grupo;
	}
	public Integer getContrato() {
		return contrato;
	}
	public void setContrato(Integer contrato) {
		this.contrato = contrato;
	}
	public Date getFechaExhibicion() {
		return fechaExhibicion;
	}
	public void setFechaExhibicion(Date fechaExhibicion) {
		this.fechaExhibicion = fechaExhibicion;
	}
	public Double getValorRatingExcedente() {
		return valorRatingExcedente;
	}
	public void setValorRatingExcedente(Double valorRatingExcedente) {
		this.valorRatingExcedente = valorRatingExcedente;
	}
	public String getCodigoDesPrograma() {
		return codigoDesPrograma;
	}
	public void setCodigoDesPrograma(String codigoDesPrograma) {
		this.codigoDesPrograma = codigoDesPrograma;
	}
	public String getTipoRatingExcedente() {
		return tipoRatingExcedente;
	}
	public void setTipoRatingExcedente(String tipoRatingExcedente) {
		this.tipoRatingExcedente = tipoRatingExcedente;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
}
