package com.artear.filmo.bo.activar.titulo.vencido;

public class ContratoParaTitulo {

	private int numero;
	private int grupo;
	private String fecha;
	private String vigenciaDesde;
	private String vigenciaHasta;
	private String estado;
	private Integer codigoDistribuidor;
	private String razonSocialDistribuidor;
	private String observaciones;
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getVigenciaDesde() {
		return vigenciaDesde;
	}

	public void setVigenciaDesde(String vigenciaDesde) {
		this.vigenciaDesde = vigenciaDesde;
	}

	public String getVigenciaHasta() {
		return vigenciaHasta;
	}

	public void setVigenciaHasta(String vigenciaHasta) {
		this.vigenciaHasta = vigenciaHasta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getCodigoDistribuidor() {
		return codigoDistribuidor;
	}

	public void setCodigoDistribuidor(Integer codigoDistribuidor) {
		this.codigoDistribuidor = codigoDistribuidor;
	}

	public String getRazonSocialDistribuidor() {
		return razonSocialDistribuidor;
	}

	public void setRazonSocialDistribuidor(String razonSocialDistribuidor) {
		this.razonSocialDistribuidor = razonSocialDistribuidor;
	}

	public String getObservaciones() {
		return observaciones;
	}
	
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

    public int getGrupo() {
        return grupo;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }
	
}