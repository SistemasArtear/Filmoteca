package com.artear.filmo.bo.trabajar.copias;

import java.util.Date;

public class ValidarRequest {

	private Integer capitulo;
	private Integer parte;
	private Integer segmento;
	private String senial;
	private String clave;
	private String soporte;
	private Date fecha;
	private String masterOCopia;
	private Integer secuencia;

	public ValidarRequest() {
		super();
	}

	public ValidarRequest(MasterSC masterSC, String masterOCopia) {
		this.clave = masterSC.getClave();
		this.senial = masterSC.getSenial();
		this.fecha = masterSC.getFecha();
		this.soporte = masterSC.getSoporte();
		this.masterOCopia = masterOCopia;
	}

	public ValidarRequest(MasterCC masterCC, String masterOCopia) {
		this((MasterSC) masterCC, masterOCopia);
		this.capitulo = masterCC.getCapitulo();
		this.parte = masterCC.getParte();
		this.segmento = masterCC.getSegmento();
	}

	public ValidarRequest(RolloCC rolloCC, String masterOCopia) {
		this((MasterCC) rolloCC, masterOCopia);
	}

	public ValidarRequest(RolloSC rolloSC, String masterOCopia) {
		this((MasterSC) rolloSC, masterOCopia);
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

	public Integer getSegmento() {
		return segmento;
	}

	public void setSegmento(Integer segmento) {
		this.segmento = segmento;
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

	public String getSoporte() {
		return soporte;
	}

	public void setSoporte(String soporte) {
		this.soporte = soporte;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getMasterOCopia() {
		return masterOCopia;
	}

	public void setMasterOCopia(String masterOCopia) {
		this.masterOCopia = masterOCopia;
	}

	public Integer getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(Integer secuencia) {
		this.secuencia = secuencia;
	}

}
