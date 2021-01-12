package com.artear.filmo.bo.programacion;

import java.util.Date;

public class DesconfirmarRequest {

	private String clave;
	private Integer programa;
	private String senial;
	private Date fecha;
	private Integer numCapitulo;
	private Integer parte;
	private Integer segmento;
	private Integer fraccion;

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Integer getPrograma() {
		return programa;
	}

	public void setPrograma(Integer programa) {
		this.programa = programa;
	}

	public String getSenial() {
		return senial;
	}

	public void setSenial(String senial) {
		this.senial = senial;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getNumCapitulo() {
		return numCapitulo;
	}

	public void setNumCapitulo(Integer numCapitulo) {
		this.numCapitulo = numCapitulo;
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

	public Integer getFraccion() {
		return fraccion;
	}

	public void setFraccion(Integer fraccion) {
		this.fraccion = fraccion;
	}

}
