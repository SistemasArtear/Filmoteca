package com.artear.filmo.bo.ingresar.materiales;

import java.math.BigDecimal;

public class ValidarModificarEliminarDetalleRequest {

	private BigDecimal idRemito; 
	private String tipoTitulo;
	private Integer numeroTitulo;
	private Integer capitulo;
	private Integer parte;
	private String senial;
	private Integer grupo;
	private Integer contrato;
	private String contabiliza;
	private String borroCh;
	
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
    
    public String getSenial() {
		return senial;
	}

	public void setSenial(String senial) {
		this.senial = senial;
	}

	public Integer getGrupo() {
		return grupo;
	}

	public void setGrupo(Integer grupo) {
		this.grupo = grupo;
	}

	public Integer getContrato() {
		return contrato;
	}

	public void setContrato(Integer contrato) {
		this.contrato = contrato;
	}

	public String getContabiliza() {
		return contabiliza;
	}
    
    public void setContabiliza(String contabiliza) {
		this.contabiliza = contabiliza;
	}
    
    public String getBorroCh() {
		return borroCh;
	}
    
    public void setBorroCh(String borroCh) {
		this.borroCh = borroCh;
	}
	
}