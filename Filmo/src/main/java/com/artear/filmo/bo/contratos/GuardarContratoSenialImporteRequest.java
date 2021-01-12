package com.artear.filmo.bo.contratos;

import java.math.BigDecimal;

public class GuardarContratoSenialImporteRequest {

	private Integer contrato;
	private String senial;
	private BigDecimal importe;

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

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}
	
}
