package com.artear.filmo.bo.contratos;

public class BuscarTipoDeCosteoRequest {

	private Integer contrato;
	private String senial;
    private Integer nroGrupo;
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

}