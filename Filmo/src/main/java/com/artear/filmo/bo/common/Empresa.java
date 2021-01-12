package com.artear.filmo.bo.common;

import java.io.Serializable;

public class Empresa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -414308061023114311L;
	
	private Short idEmpresa;
	private String descripcionCompleta;
	private String descripcionCorta;
	private String estadoEmpresa;
	private String cuit;
	private String razonSocial;
	private String nroIIBB;
	
	/**
	 * <p>Enumerado correspondiente a las columnas de la tabla 
	 * Maestro::MAEMPRESA.</p>
	 * @since 15/02/2013
 	 * @version 1.0
	 *
	 */
	public enum EmpresaColumns{
		MAEMKEMPRESA, MAEMDDESCRI, MAEMADESCORTA, MAEMESTADO, MAEMCUIT, MAEMRAZSOC, MAEMNNROIIBB,	
	}

	//Getters and Setters
	public Short getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Short idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getDescripcionCompleta() {
		return descripcionCompleta;
	}

	public void setDescripcionCompleta(String descripcionCompleta) {
		this.descripcionCompleta = descripcionCompleta;
	}

	public String getDescripcionCorta() {
		return descripcionCorta;
	}

	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}

	public String getEstadoEmpresa() {
		return estadoEmpresa;
	}

	public void setEstadoEmpresa(String estadoEmpresa) {
		this.estadoEmpresa = estadoEmpresa;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNroIIBB() {
		return nroIIBB;
	}

	public void setNroIIBB(String nroIIBB) {
		this.nroIIBB = nroIIBB;
	}

}