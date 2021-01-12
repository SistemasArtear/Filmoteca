package com.artear.filmo.bo.contratos;

import java.math.BigDecimal;

public class TipoDeCosteoABMRequest {
	
	/*
PR_CFMENSUAL_AMB (Alta Modificación Baja CostoFijoMnsual(CFM))
Parámetros: 
p_tipo_titulo Tipo de Titulo
p_numero_titulo Nro de Titulo
p_mes Mes
p_anio Año
p_senial Señal
p_contrato Nro de contrato
p_grupo Grupo
p_accion Acción (I) Insertar, (U) Actualizar, (D) Eliminar
p_valor Monto a cambiar
p_canje Canje
	 */

	private String tipoTitulo;
	private String titulo;
	private String senial;
	private Integer contrato;
	private Integer nroGrupo;
	private String accion;
	private Integer mes;//desde
	private Integer anio;//hasta
	private BigDecimal valor;
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
	public Integer getMes() {
		return mes;
	}
	public void setMes(Integer mes) {
		this.mes = mes;
	}
	public Integer getAnio() {
		return anio;
	}
	public void setAnio(Integer anio) {
		this.anio = anio;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getCanje() {
		return canje;
	}
	public void setCanje(String canje) {
		this.canje = canje;
	}
	
}