package com.artear.filmo.bo.contratos;

public class ConfirmarBajaCapitulosRequest {

	private Integer contrato;
	private Integer grupo;
	private String senial;
	private String clave;
	private String desenlace;
	private Integer capitulo;
	private Integer parte;

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

	public String getDesenlace() {
		return desenlace;
	}

	public void setDesenlace(String desenlace) {
		this.desenlace = desenlace;
	}

	public Integer getCapitulo() {
		return capitulo;
	}

	public void setCapitulo(Integer capitulo) {
		this.capitulo = capitulo;
	}

	public Integer getParte() {
		return parte;
	}

	public void setParte(Integer parte) {
		this.parte = parte;
	}
	
}