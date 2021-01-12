package com.artear.filmo.bo.ingresar.materiales;

import java.math.BigDecimal;

public class DetalleRemitoABM {

	private BigDecimal idRemito; 
	private String tipoTitulo;
	private Integer numeroTitulo;
	private Integer capitulo;
	private Integer parte;
	private String codSoporte;
	private String tipoMotivo;
    private Integer carrete;
    private Integer lata;
    private Integer torta;
    private Integer cantRollos;
	
    public BigDecimal getIdRemito() {
		return idRemito;
	}
	
    public void setIdRemito(BigDecimal idRemito) {
		this.idRemito = idRemito;
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
	
    public String getCodSoporte() {
		return codSoporte;
	}
	
    public void setCodSoporte(String codSoporte) {
		this.codSoporte = codSoporte;
	}
	
    public String getTipoMotivo() {
		return tipoMotivo;
	}
	
    public void setTipoMotivo(String tipoMotivo) {
		this.tipoMotivo = tipoMotivo;
	}
	
    public Integer getCarrete() {
		return carrete;
	}
	
    public void setCarrete(Integer carrete) {
		this.carrete = carrete;
	}
	
    public Integer getLata() {
		return lata;
	}
	
    public void setLata(Integer lata) {
		this.lata = lata;
	}
	
    public Integer getTorta() {
		return torta;
	}
	
    public void setTorta(Integer torta) {
		this.torta = torta;
	}
	
    public Integer getCantRollos() {
		return cantRollos;
	}
	
    public void setCantRollos(Integer cantRollos) {
		this.cantRollos = cantRollos;
	} 
	
}