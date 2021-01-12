package com.artear.filmo.bo.contratos;

public class BuscarCapitulosTituloRecontratacionRequest {

    private Integer contrato;
    private Integer grupo;
    private String senial;
    private String clave;
    private Integer contratoPendiente;
    private Integer grupoPendiente;
    private String senialPendiente;
    
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

	public Integer getContratoPendiente() {
		return contratoPendiente;
	}

	public void setContratoPendiente(Integer contratoPendiente) {
		this.contratoPendiente = contratoPendiente;
	}

	public Integer getGrupoPendiente() {
		return grupoPendiente;
	}

	public void setGrupoPendiente(Integer grupoPendiente) {
		this.grupoPendiente = grupoPendiente;
	}

	public String getSenialPendiente() {
		return senialPendiente;
	}

	public void setSenialPendiente(String senialPendiente) {
		this.senialPendiente = senialPendiente;
	}
	
}