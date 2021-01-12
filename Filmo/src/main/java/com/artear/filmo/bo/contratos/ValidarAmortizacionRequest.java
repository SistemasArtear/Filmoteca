package com.artear.filmo.bo.contratos;

public class ValidarAmortizacionRequest {
	
	private Integer contrato;
	private Integer grupo;
	private String senial;
	private String tipoTitulo;
    private String marcaPerpetuidad;
    private String pasaLibreria;
    private Integer cantidadTitulos;
    private Integer cantidadPasadasContratadas;
    private Integer cantidadOriginales;

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

	public String getMarcaPerpetuidad() {
		return marcaPerpetuidad;
	}

	public void setMarcaPerpetuidad(String marcaPerpetuidad) {
		this.marcaPerpetuidad = marcaPerpetuidad;
	}

	public String getPasaLibreria() {
		return pasaLibreria;
	}

	public void setPasaLibreria(String pasaLibreria) {
		this.pasaLibreria = pasaLibreria;
	}

	public Integer getCantidadTitulos() {
		return cantidadTitulos;
	}

	public void setCantidadTitulos(Integer cantidadTitulos) {
		this.cantidadTitulos = cantidadTitulos;
	}

	public Integer getCantidadPasadasContratadas() {
		return cantidadPasadasContratadas;
	}

	public void setCantidadPasadasContratadas(Integer cantidadPasadasContratadas) {
		this.cantidadPasadasContratadas = cantidadPasadasContratadas;
	}

	public Integer getCantidadOriginales() {
		return cantidadOriginales;
	}

	public void setCantidadOriginales(Integer cantidadOriginales) {
		this.cantidadOriginales = cantidadOriginales;
	}
	
	
	
}