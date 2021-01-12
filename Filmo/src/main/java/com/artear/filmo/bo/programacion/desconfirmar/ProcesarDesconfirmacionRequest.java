package com.artear.filmo.bo.programacion.desconfirmar;

import java.util.Date;

public class ProcesarDesconfirmacionRequest {

	private Integer codigo;
	private String tipoTitulo;
	private Integer numeroTitulo;
	private Integer capitulo;
	private Integer parte;
	private Integer segmento;
	private Integer contrato;
	private Integer grupo;
	private String senial;
	private String senialCon;
	private Date fecha;
	private String respuestaWarning;
	private String respuestaConfirmacion;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getTipoTitulo() {
		return tipoTitulo;
	}

	public void setTipoTitulo(String tipoTitulo) {
		this.tipoTitulo = tipoTitulo;
	}

	public Integer getNumeroTitulo() {
		return numeroTitulo;
	}

	public void setNumeroTitulo(Integer numeroTitulo) {
		this.numeroTitulo = numeroTitulo;
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

	public String getSenial() {
		return senial;
	}

	public void setSenial(String senial) {
		this.senial = senial;
	}

	public String getSenialCon() {
		return senialCon;
	}

	public void setSenialCon(String senialCon) {
		this.senialCon = senialCon;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getRespuestaWarning() {
		return respuestaWarning;
	}
	
	public void setRespuestaWarning(String respuestaWarning) {
		this.respuestaWarning = respuestaWarning;
	}
	
	public String getRespuestaConfirmacion() {
		return respuestaConfirmacion;
	}

	public void setRespuestaConfirmacion(String respuestaConfirmacion) {
		this.respuestaConfirmacion = respuestaConfirmacion;
	}
	
}