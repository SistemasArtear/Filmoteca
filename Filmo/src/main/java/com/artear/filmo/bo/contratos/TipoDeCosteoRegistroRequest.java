package com.artear.filmo.bo.contratos;

import java.math.BigDecimal;

public class TipoDeCosteoRegistroRequest {

	private Integer contrato;
	private String senial;
    private Integer nroGrupo;
    private String tipoContrato;//canje, no canje, cabecera;
    private int mes;
    private int anio;
    private BigDecimal valor;
    private int minutoDesde;
    private int minutoHasta;
    private int ratingDesde;
    private int ratingHasta;
	public Integer getContrato() {
		return contrato;
	}
	public void setContrato(Integer contrato) {
		this.contrato = contrato;
	}
	public String getSenial() {
		return senial;
	}
	public void setSenial(String senial) {
		this.senial = senial;
	}
	public Integer getNroGrupo() {
		return nroGrupo;
	}
	public void setNroGrupo(Integer nroGrupo) {
		this.nroGrupo = nroGrupo;
	}
	public String getTipoContrato() {
		return tipoContrato;
	}
	public void setTipoContrato(String tipoContrato) {
		this.tipoContrato = tipoContrato;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public int getMinutoDesde() {
		return minutoDesde;
	}
	public void setMinutoDesde(int minutoDesde) {
		this.minutoDesde = minutoDesde;
	}
	public int getMinutoHasta() {
		return minutoHasta;
	}
	public void setMinutoHasta(int minutoHasta) {
		this.minutoHasta = minutoHasta;
	}
	public int getRatingDesde() {
		return ratingDesde;
	}
	public void setRatingDesde(int ratingDesde) {
		this.ratingDesde = ratingDesde;
	}
	public int getRatingHasta() {
		return ratingHasta;
	}
	public void setRatingHasta(int ratingHasta) {
		this.ratingHasta = ratingHasta;
	}
    

}