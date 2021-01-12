package com.artear.filmo.bo.trabajar.remitos;

import java.util.Date;

public class ValidarNuevaSenialView {

	private Date fechaDesde;
	private Date fechaHasta;
    private String clave;
    private Integer contrato;
    private Integer grupo;
    private String exhibicion;

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

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

	public String getExhibicion() {
		return exhibicion;
	}

	public void setExhibicion(String exhibicion) {
		this.exhibicion = exhibicion;
	}
	
}