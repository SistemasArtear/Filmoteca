/**
 * 
 */
package com.artear.filmo.bo.contratos;

import java.math.BigDecimal;

/**
 * @author sisloc
 * 
 */
public class CosteoExcedente {

    private Integer contrato;
    private String senial;
    private Integer nroGrupo;
    private int minutoDesde;
    private int minutoHasta;
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
	public int getMinutoDesde() {
		return minutoDesde;
	}
	public void setMinutoDesde(int minutoDesde) {
		this.minutoDesde = minutoDesde;
	}
	public int getMinutoHasta() {
		return minutoHasta;
	}
	public void setMinutoHasta(int minutoHasta) {
		this.minutoHasta = minutoHasta;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
    
    
}
