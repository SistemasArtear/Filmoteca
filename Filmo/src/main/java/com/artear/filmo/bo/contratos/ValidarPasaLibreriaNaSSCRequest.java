package com.artear.filmo.bo.contratos;

public class ValidarPasaLibreriaNaSSCRequest {

	private Integer contrato;
    private Integer grupo;
    private String senial;
    private String tipoTitulo;
    private String marcaPerpetuidad;
    private Integer cantidadPasadas;
    private String pasaLibreria;

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

	public Integer getCantidadPasadas() {
		return cantidadPasadas;
	}

	public void setCantidadPasadas(Integer cantidadPasadas) {
		this.cantidadPasadas = cantidadPasadas;
	}

	public String getPasaLibreria() {
		return pasaLibreria;
	}

	public void setPasaLibreria(String pasaLibreria) {
		this.pasaLibreria = pasaLibreria;
	}
	
}