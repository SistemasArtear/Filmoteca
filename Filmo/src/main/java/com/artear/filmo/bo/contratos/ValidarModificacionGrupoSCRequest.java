package com.artear.filmo.bo.contratos;

import java.util.Date;

public class ValidarModificacionGrupoSCRequest {

	private Integer contrato;
    private Integer grupo;
    private String senial;
    private String tipoTitulo;
    private String tipoImporte;
    private String idTipoDerecho;
    private Date fechaComienzoDerechos;
    private Integer cantidadTiempo;
    private String unidadTiempo;
    private String marcaPerpetuidad;
    private Integer cantidadTitulos;
    private String enlazarNuevaVigencia;
    
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

	public String getTipoTitulo() {
		return tipoTitulo;
	}

	public void setTipoTitulo(String tipoTitulo) {
		this.tipoTitulo = tipoTitulo;
	}

	public String getTipoImporte() {
		return tipoImporte;
	}

	public void setTipoImporte(String tipoImporte) {
		this.tipoImporte = tipoImporte;
	}

	public String getIdTipoDerecho() {
		return idTipoDerecho;
	}

	public void setIdTipoDerecho(String idTipoDerecho) {
		this.idTipoDerecho = idTipoDerecho;
	}

	public Date getFechaComienzoDerechos() {
		return fechaComienzoDerechos;
	}

	public void setFechaComienzoDerechos(Date fechaComienzoDerechos) {
		this.fechaComienzoDerechos = fechaComienzoDerechos;
	}

	public Integer getCantidadTiempo() {
		return cantidadTiempo;
	}

	public void setCantidadTiempo(Integer cantidadTiempo) {
		this.cantidadTiempo = cantidadTiempo;
	}

	public String getUnidadTiempo() {
		return unidadTiempo;
	}

	public void setUnidadTiempo(String unidadTiempo) {
		this.unidadTiempo = unidadTiempo;
	}

	public String getMarcaPerpetuidad() {
		return marcaPerpetuidad;
	}

	public void setMarcaPerpetuidad(String marcaPerpetuidad) {
		this.marcaPerpetuidad = marcaPerpetuidad;
	}

	public Integer getCantidadTitulos() {
		return cantidadTitulos;
	}

	public void setCantidadTitulos(Integer cantidadTitulos) {
		this.cantidadTitulos = cantidadTitulos;
	}

	public String getEnlazarNuevaVigencia() {
		return enlazarNuevaVigencia;
	}

	public void setEnlazarNuevaVigencia(String enlazarNuevaVigencia) {
		this.enlazarNuevaVigencia = enlazarNuevaVigencia;
	}

}