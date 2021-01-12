package com.artear.filmo.bo.amortizar.titulossinexhibir;

import java.math.BigDecimal;
import java.util.List;

public class TituloSinExhibirRecibirView {

	private String tituloCastellano;
	private Integer contrato;
	private String titulo;
	private Integer totalCapitulos;
	private BigDecimal porAmortizar;
	private List<CapituloTituloSinExhRec> capitulos;
	
	public String getTituloCastellano() {
		return tituloCastellano;
	}
	
	public void setTituloCastellano(String tituloCastellano) {
		this.tituloCastellano = tituloCastellano;
	}
	
	public Integer getContrato() {
		return contrato;
	}
	
	public void setContrato(Integer contrato) {
		this.contrato = contrato;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public Integer getTotalCapitulos() {
		return totalCapitulos;
	}
	
	public void setTotalCapitulos(Integer totalCapitulos) {
		this.totalCapitulos = totalCapitulos;
	}
	
	public BigDecimal getPorAmortizar() {
		return porAmortizar;
	}
	
	public void setPorAmortizar(BigDecimal porAmortizar) {
		this.porAmortizar = porAmortizar;
	}

	public List<CapituloTituloSinExhRec> getCapitulos() {
		return capitulos;
	}
	
	public void setCapitulos(List<CapituloTituloSinExhRec> capitulos) {
		this.capitulos = capitulos;
	}

}