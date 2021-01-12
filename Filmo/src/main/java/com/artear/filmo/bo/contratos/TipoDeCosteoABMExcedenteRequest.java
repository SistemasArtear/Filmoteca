package com.artear.filmo.bo.contratos;

import java.math.BigDecimal;

public class TipoDeCosteoABMExcedenteRequest {
	
	/*
PR_ESCALAEXC_AMB(p_tipo_titulo   IN TITULOEXCEDENTE.ttktpotit%TYPE,
                           p_numero_titulo IN TITULOEXCEDENTE.mtknrotit%TYPE,
                           p_senial        IN TITULOEXCEDENTE.snkcod%TYPE,
                           p_contrato      IN TITULOEXCEDENTE.cokcon%TYPE,
                           p_grupo         IN TITULOEXCEDENTE.grkgrupo%TYPE,
                           p_accion        IN VARCHAR2,
                           p_exc_desde     IN TITULOEXCEDENTE.MINEXCDESDE%TYPE, 
                           p_exc_hasta     IN TITULOEXCEDENTE.MINEXCHASTA%TYPE,
                           p_valor         IN TITULOEXCEDENTE.EXVALOR%TYPE,
                           p_canje         IN TITULOEXCEDENTE.EXCANJE%TYPE	 
	 */

	private String tipoTitulo;
	private String titulo;
	private String senial;
	private Integer contrato;
	private Integer nroGrupo;
	private String accion;
	private Integer valor1;//desde
	private Integer valor2;//hasta
	private BigDecimal valor3;
	private String canje;//canje, no canje
	public String getTipoTitulo() {
		return tipoTitulo;
	}
	public void setTipoTitulo(String tipoTitulo) {
		this.tipoTitulo = tipoTitulo;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getSenial() {
		return senial;
	}
	public void setSenial(String senial) {
		this.senial = senial;
	}
	public Integer getContrato() {
		return contrato;
	}
	public void setContrato(Integer contrato) {
		this.contrato = contrato;
	}
	public Integer getNroGrupo() {
		return nroGrupo;
	}
	public void setNroGrupo(Integer nroGrupo) {
		this.nroGrupo = nroGrupo;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public Integer getValor1() {
		return valor1;
	}
	public void setValor1(Integer valor1) {
		this.valor1 = valor1;
	}
	public Integer getValor2() {
		return valor2;
	}
	public void setValor2(Integer valor2) {
		this.valor2 = valor2;
	}
	public BigDecimal getValor3() {
		return valor3;
	}
	public void setValor3(BigDecimal valor3) {
		this.valor3 = valor3;
	}
	public String getCanje() {
		return canje;
	}
	public void setCanje(String canje) {
		this.canje = canje;
	}

	
}