/**
 * 
 */
package com.artear.filmo.bo.contratos;

import java.math.BigDecimal;

/**
 * @author sisloc
 * 
 */
public class CosteoRating {

    private Integer contrato;
    private String senial;
    private Integer nroGrupo;
    private int ratingDesde;
    private int ratingHasta;
    private BigDecimal valor;
	public Integer getContrato() {
		return contrato;
	}
	public void setContrato(Integer contrato) {
		this.contrato = contrato;
	}
	public String getSenial() {
		return senial;
	}
	public void setSenial(String senial) {
		this.senial = senial;
	}
	public Integer getNroGrupo() {
		return nroGrupo;
	}
	public void setNroGrupo(Integer nroGrupo) {
		this.nroGrupo = nroGrupo;
	}
	public int getRatingDesde() {
		return ratingDesde;
	}
	public void setRatingDesde(int ratingDesde) {
		this.ratingDesde = ratingDesde;
	}
	public int getRatingHasta() {
		return ratingHasta;
	}
	public void setRatingHasta(int ratingHasta) {
		this.ratingHasta = ratingHasta;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
    
}
