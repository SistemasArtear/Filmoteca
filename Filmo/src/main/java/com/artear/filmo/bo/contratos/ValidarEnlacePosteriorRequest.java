package com.artear.filmo.bo.contratos;

import java.util.Date;

public class ValidarEnlacePosteriorRequest {

    private Integer contratoPosterior;
    private Integer grupoPosterior;
    private String clavePosterior;
    private String senialPosterior;
    private Integer contrato;
    private Integer grupo;
    private Integer numeroAnterior;
    private Integer grupoAnterior;
    private Integer numeroPos;
    private Integer grupoPos;
    private Date fechaDesde;
    private Date fechaHasta;

	public Integer getContratoPosterior() {
		return contratoPosterior;
	}

	public void setContratoPosterior(Integer contratoPosterior) {
		this.contratoPosterior = contratoPosterior;
	}

	public Integer getGrupoPosterior() {
		return grupoPosterior;
	}

	public void setGrupoPosterior(Integer grupoPosterior) {
		this.grupoPosterior = grupoPosterior;
	}

	public String getClavePosterior() {
		return clavePosterior;
	}

	public void setClavePosterior(String clavePosterior) {
		this.clavePosterior = clavePosterior;
	}

	public String getSenialPosterior() {
		return senialPosterior;
	}

	public void setSenialPosterior(String senialPosterior) {
		this.senialPosterior = senialPosterior;
	}

	public Integer getContrato() {
		return contrato;
	}

	public void setContrato(Integer contrato) {
		this.contrato = contrato;
	}

	public Integer getGrupo() {
		return grupo;
	}

	public void setGrupo(Integer grupo) {
		this.grupo = grupo;
	}

	public Integer getNumeroAnterior() {
		return numeroAnterior;
	}

	public void setNumeroAnterior(Integer numeroAnterior) {
		this.numeroAnterior = numeroAnterior;
	}

	public Integer getGrupoAnterior() {
		return grupoAnterior;
	}

	public void setGrupoAnterior(Integer grupoAnterior) {
		this.grupoAnterior = grupoAnterior;
	}

	public Integer getNumeroPos() {
		return numeroPos;
	}

	public void setNumeroPos(Integer numeroPos) {
		this.numeroPos = numeroPos;
	}

	public Integer getGrupoPos() {
		return grupoPos;
	}

	public void setGrupoPos(Integer grupoPos) {
		this.grupoPos = grupoPos;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	
}