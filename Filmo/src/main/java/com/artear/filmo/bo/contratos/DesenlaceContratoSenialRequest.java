package com.artear.filmo.bo.contratos;

public class DesenlaceContratoSenialRequest {

	private Integer contrato;
	private String senial;
	private Integer grupo;
	private String tipoTitulo;
	private Integer nroTitulo;

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

	public Integer getGrupo() {
		return grupo;
	}

	public void setGrupo(Integer grupo) {
		this.grupo = grupo;
	}

	public String getTipoTitulo() {
		return tipoTitulo;
	}

	public void setTipoTitulo(String tipoTitulo) {
		this.tipoTitulo = tipoTitulo;
	}

	public Integer getNroTitulo() {
		return nroTitulo;
	}

	public void setNroTitulo(Integer nroTitulo) {
		this.nroTitulo = nroTitulo;
	}
	
}