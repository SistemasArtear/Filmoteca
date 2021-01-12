package com.artear.filmo.bo.chequeo.tecnico;

import java.util.Date;

public class BusquedaFichasRequest {

	private String senial;
	private String clave;
	private Integer capitulo;
	private Integer parte;
	private String tituloCastellano;
	private String tituloOriginal;
	private String fichasActivas;
	private String chequeo;
	private Date fechaDesde;
	private Date fechaHasta;
	private String orden;

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

	public String getTituloCastellano() {
		return tituloCastellano;
	}

	public void setTituloCastellano(String tituloCastellano) {
		this.tituloCastellano = tituloCastellano;
	}

	public String getTituloOriginal() {
		return tituloOriginal;
	}

	public void setTituloOriginal(String tituloOriginal) {
		this.tituloOriginal = tituloOriginal;
	}

	public String getFichasActivas() {
		return fichasActivas;
	}

	public void setFichasActivas(String fichasActivas) {
		this.fichasActivas = fichasActivas;
	}

	public String getChequeo() {
		return chequeo;
	}

	public void setChequeo(String chequeo) {
		this.chequeo = chequeo;
	}

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

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

}
