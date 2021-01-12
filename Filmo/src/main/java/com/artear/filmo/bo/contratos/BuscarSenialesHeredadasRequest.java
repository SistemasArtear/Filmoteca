package com.artear.filmo.bo.contratos;

public class BuscarSenialesHeredadasRequest {
	
	private Integer contrato;
	private Integer grupo;
	private String senial;
	private String seniales;

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

	public String getSeniales() {
		return seniales;
	}

	public void setSeniales(String seniales) {
		this.seniales = seniales;
	}
	
	
}