package com.artear.filmo.bo.contratos;

import java.util.Date;

public class ContratoEnlazadoTituloRecontratacion {

	private Integer contrato;
	private Integer grupo;
	private String distribuidor;
	private Date vigenciaDesde;
	private Date vigenciaHasta;
	private Integer enlazadoAnterior;
	private Integer grupoAnterior;
	private Integer enlazadoPosterior;
	private Integer grupoPosterior;
	private String modVigencia;

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

	public String getDistribuidor() {
		return distribuidor;
	}

	public void setDistribuidor(String distribuidor) {
		this.distribuidor = distribuidor;
	}

	public Date getVigenciaDesde() {
		return vigenciaDesde;
	}

	public void setVigenciaDesde(Date vigenciaDesde) {
		this.vigenciaDesde = vigenciaDesde;
	}

	public Date getVigenciaHasta() {
		return vigenciaHasta;
	}

	public void setVigenciaHasta(Date vigenciaHasta) {
		this.vigenciaHasta = vigenciaHasta;
	}

	public Integer getEnlazadoAnterior() {
		return enlazadoAnterior;
	}

	public void setEnlazadoAnterior(Integer enlazadoAnterior) {
		this.enlazadoAnterior = enlazadoAnterior;
	}
	
	public Integer getGrupoAnterior() {
		return grupoAnterior;
	}
	
	public void setGrupoAnterior(Integer grupoAnterior) {
		this.grupoAnterior = grupoAnterior;
	}

	public Integer getEnlazadoPosterior() {
		return enlazadoPosterior;
	}

	public void setEnlazadoPosterior(Integer enlazadoPosterior) {
		this.enlazadoPosterior = enlazadoPosterior;
	}
	
	public Integer getGrupoPosterior() {
		return grupoPosterior;
	}
	
	public void setGrupoPosterior(Integer grupoPosterior) {
		this.grupoPosterior = grupoPosterior;
	}

	public String getModVigencia() {
		return modVigencia;
	}

	public void setModVigencia(String modVigencia) {
		this.modVigencia = modVigencia;
	}
	
}