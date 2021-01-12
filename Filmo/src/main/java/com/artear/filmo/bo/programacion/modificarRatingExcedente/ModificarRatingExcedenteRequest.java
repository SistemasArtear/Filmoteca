package com.artear.filmo.bo.programacion.modificarRatingExcedente;

import java.util.Date;

import com.artear.filmo.bo.programacion.Programa;

public class ModificarRatingExcedenteRequest {
	private Date fechaExhibicion;
	private Programa programa;
	private String tipoTitulo;
	private Integer numeroTitulo;
	private Integer nroCapitulo;
	private String idSenial;
	private String tipoRatingExcedente;
	private Double valorRatingExcedente;
	private Integer grupo;
	private Integer contrato;
	
	public Date getFechaExhibicion() {
		return fechaExhibicion;
	}
	public void setFechaExhibicion(Date fechaExhibicion) {
		this.fechaExhibicion = fechaExhibicion;
	}
	public Programa getPrograma() {
		return programa;
	}
	public void setPrograma(Programa programa) {
		this.programa = programa;
	}
	public String getTipoTitulo() {
		return tipoTitulo;
	}
	public void setTipoTitulo(String tipoTitulo) {
		this.tipoTitulo = tipoTitulo;
	}
	public Integer getNumeroTitulo() {
		return numeroTitulo;
	}
	public void setNumeroTitulo(Integer numeroTitulo) {
		this.numeroTitulo = numeroTitulo;
	}
	public Integer getNroCapitulo() {
		return nroCapitulo;
	}
	public void setNroCapitulo(Integer nroCapitulo) {
		this.nroCapitulo = nroCapitulo;
	}
	public String getIdSenial() {
		return idSenial;
	}
	public void setIdSenial(String idSenial) {
		this.idSenial = idSenial;
	}
	public String getTipoRatingExcedente() {
		return tipoRatingExcedente;
	}
	public void setTipoRatingExcedente(String tipoRatingExcedente) {
		this.tipoRatingExcedente = tipoRatingExcedente;
	}
	public Double getValorRatingExcedente() {
		return valorRatingExcedente;
	}
	public void setValorRatingExcedente(Double valorRatingExcedente) {
		this.valorRatingExcedente = valorRatingExcedente;
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
	
}
