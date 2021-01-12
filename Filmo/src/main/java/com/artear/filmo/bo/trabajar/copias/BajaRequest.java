package com.artear.filmo.bo.trabajar.copias;

import java.util.Date;

public class BajaRequest {

	private String clave;
	private String senial;
	private Integer copia;
	private Integer rollo;
	private String soporte;
	private Integer capitulo;
	private Integer parte;
	private Integer segmento;
	private boolean master;
	private Integer secuencia;
	private Date fecha;

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getSenial() {
		return senial;
	}

	public void setSenial(String senial) {
		this.senial = senial;
	}

	public Integer getCopia() {
		return copia;
	}

	public void setCopia(Integer copia) {
		this.copia = copia;
	}

	public Integer getRollo() {
		return rollo;
	}

	public void setRollo(Integer rollo) {
		this.rollo = rollo;
	}

	public String getSoporte() {
		return soporte;
	}

	public void setSoporte(String soporte) {
		this.soporte = soporte;
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

	public boolean isMaster() {
		return master;
	}

	public void setMaster(boolean master) {
		this.master = master;
	}

	public Integer getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(Integer secuencia) {
		this.secuencia = secuencia;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
