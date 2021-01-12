package com.artear.filmo.bo.programacion;

import java.util.Date;

public class ListarProgramacionRequest {

	private String senial;
	private Date fecha;
	private String tipoOrden;
	private Integer codigoPrograma;
	private String codigoTitulo;
	private Integer capitulo;
	private Integer parte;
	private Integer segmento;
	
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
	
	public String getTipoOrden() {
		return tipoOrden;
	}
	
	public void setTipoOrden(String tipoOrden) {
		this.tipoOrden = tipoOrden;
	}

	public Integer getCodigoPrograma() {
		return codigoPrograma;
	}

	public void setCodigoPrograma(Integer codigoPrograma) {
		this.codigoPrograma = codigoPrograma;
	}

	public String getCodigoTitulo() {
		return codigoTitulo;
	}

	public void setCodigoTitulo(String codigoTitulo) {
		this.codigoTitulo = codigoTitulo;
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
	
}