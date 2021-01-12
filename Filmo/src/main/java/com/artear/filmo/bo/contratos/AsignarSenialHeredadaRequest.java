package com.artear.filmo.bo.contratos;

public class AsignarSenialHeredadaRequest {

	private Integer contrato;
	private Integer grupo;
	private String senial;
	private String senialHeredada;
	private String usuario;
	
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

	public String getSenial() {
		return senial;
	}

	public void setSenial(String senial) {
		this.senial = senial;
	}

	public String getSenialHeredada() {
		return senialHeredada;
	}

	public void setSenialHeredada(String senialHeredada) {
		this.senialHeredada = senialHeredada;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
}