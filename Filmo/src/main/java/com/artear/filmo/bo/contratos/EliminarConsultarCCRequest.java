package com.artear.filmo.bo.contratos;

public class EliminarConsultarCCRequest {
	
	private Integer contrato;
    private Integer grupo;
    private String senial;
    private String tipoTitulo;
    private Integer nroTitulo;

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