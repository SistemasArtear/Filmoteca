package com.artear.filmo.bo.contratos;

public class TipoDeCosteoValidarSeleccionRequest {

	private Integer contrato;
	private String senial;
    private Integer nroGrupo;
    private int idSeleccion;
/*
1 - usuario selecciono rating 
2 - usuario selecciono mixto 
3 - usuario selecciono cfm
4 - usuario selecciono excedente
 */
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
	public int getIdSeleccion() {
		return idSeleccion;
	}
	public void setIdSeleccion(int idSeleccion) {
		this.idSeleccion = idSeleccion;
	}
}