package com.artear.filmo.bo.contratos;

public class ConfirmarEliminacionTituloContratoRequest {

    private Integer contrato;
    private Integer grupo;
    private String senial;
    private String clave;
    private String s20Prog;

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

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getS20Prog() {
		return s20Prog;
	}

	public void setS20Prog(String s20Prog) {
		this.s20Prog = s20Prog;
	}
	
}