package com.artear.filmo.bo.chequeo.tecnico;

import java.util.Date;

public class Rollo {

	private String senial;
	private String soporte;
	private Integer copia;
	private Integer secuencia;
	private Integer rollo;
	private String origen;
	private Date fecha;
	private String sugerido;

	public String getSenial() {
		return senial;
	}

	public void setSenial(String senial) {
		this.senial = senial;
	}

	public String getSoporte() {
		return soporte;
	}

	public void setSoporte(String soporte) {
		this.soporte = soporte;
	}

	public Integer getCopia() {
		return copia;
	}

	public void setCopia(Integer copia) {
		this.copia = copia;
	}

	public Integer getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(Integer secuencia) {
		this.secuencia = secuencia;
	}

	public Integer getRollo() {
		return rollo;
	}

	public void setRollo(Integer rollo) {
		this.rollo = rollo;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getSugerido() {
		return sugerido;
	}

	public void setSugerido(String sugerido) {
		this.sugerido = sugerido;
	}

}
