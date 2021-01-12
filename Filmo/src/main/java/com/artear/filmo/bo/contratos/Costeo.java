/**
 * 
 */
package com.artear.filmo.bo.contratos;

import java.math.BigDecimal;

/**
 * @author sisloc
 * 
 */
public class Costeo {

//    private Integer contrato;
//    private String senial;
//    private Integer nroGrupo;
    private int anioMes;
    private BigDecimal valor;
	public int getAnioMes() {
		return anioMes;
	}
	public void setAnioMes(int anioMes) {
		this.anioMes = anioMes;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
    
}
