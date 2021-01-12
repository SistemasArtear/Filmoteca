package com.artear.filmo.bo.amortizar.titulossinexhibir;

import java.math.BigDecimal;

public class TituloSinExhibirRecibirGrillaResponse {

	private String clave;
	private String titulo;
	private Integer contrato;
	private String distribuidor;
	private String perpetuo;
	private String recibido;
	private BigDecimal porAmortizar;
	private Integer grupo;
	
	public String getClave() {
		return clave;
	}
	
	public void setClave(String clave) {
		this.clave = clave;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public Integer getContrato() {
		return contrato;
	}
	
	public void setContrato(Integer contrato) {
		this.contrato = contrato;
	}
	
	public String getDistribuidor() {
		return distribuidor;
	}
	
	public void setDistribuidor(String distribuidor) {
		this.distribuidor = distribuidor;
	}
	
	public String getPerpetuo() {
		return perpetuo;
	}
	
	public void setPerpetuo(String perpetuo) {
		this.perpetuo = perpetuo;
	}
	
	public String getRecibido() {
		return recibido;
	}
	
	public void setRecibido(String recibido) {
		this.recibido = recibido;
	}
	
	public BigDecimal getPorAmortizar() {
		return porAmortizar;
	}
	
	public void setPorAmortizar(BigDecimal porAmortizar) {
		this.porAmortizar = porAmortizar;
	}
	
	public Integer getGrupo() {
		return grupo;
	}
	
	public void setGrupo(Integer grupo) {
		this.grupo = grupo;
	}
	
}