package com.artear.filmo.bo.trabajar.titulos;

import java.math.BigDecimal;

public class TituloAmortizadoGrillaResponse {

	private String clave;
	private String titulo;
	private String contrato;
	private String distribuidor;
	private String per;
	private String rec;
	private BigDecimal porAmortizar;
	
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

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getDistribuidor() {
        return distribuidor;
    }

    public void setDistribuidor(String distribuidor) {
        this.distribuidor = distribuidor;
    }

    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }

    public String getRec() {
        return rec;
    }

    public void setRec(String rec) {
        this.rec = rec;
    }

    public BigDecimal getPorAmortizar() {
        return porAmortizar;
    }

    public void setPorAmortizar(BigDecimal porAmortizar) {
        this.porAmortizar = porAmortizar;
    }


}
