package com.artear.filmo.bo.contratos;

import java.math.BigDecimal;

public class TipoDeCosteoEliminarRequest {

	private Integer contrato;
	private String senial;
    private Integer nroGrupo;
    private String tipoContrato;//canje, no canje, cabecera;
    private Integer valor1;
    private Integer valor2;//para los del tipo cfm y mixto debe ir null
    private BigDecimal valor3;
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
	public Integer getValor1() {
		return valor1;
	}
	public void setValor1(Integer valor1) {
		this.valor1 = valor1;
	}
	public Integer getValor2() {
		return valor2;
	}
	public void setValor2(Integer valor2) {
		this.valor2 = valor2;
	}
	public BigDecimal getValor3() {
		return valor3;
	}
	public void setValor3(BigDecimal valor3) {
		this.valor3 = valor3;
	}

}