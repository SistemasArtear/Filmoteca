package com.artear.filmo.bo.chequeo.tecnico;

import java.util.Date;

public class BusquedaFichasResponse {
	
	private String clave;
	private Integer capitulo;
	private Integer parte;
	private String titulo;
	private String tituloCastellano;
	private Integer nroFicha;
	private Date fecha;
	private String soporte;
	private String estado;
	
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
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
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Integer getNroFicha() {
		return nroFicha;
	}
	public void setNroFicha(Integer nroFicha) {
		this.nroFicha = nroFicha;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getSoporte() {
		return soporte;
	}
	public void setSoporte(String soporte) {
		this.soporte = soporte;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getTituloCastellano() {
		return tituloCastellano;
	}
	public void setTituloCastellano(String tituloCastellano) {
		this.tituloCastellano = tituloCastellano;
	}
	
	
}
