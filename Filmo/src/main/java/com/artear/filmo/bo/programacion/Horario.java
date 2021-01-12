package com.artear.filmo.bo.programacion;

public class Horario {

	private Integer dia;
	private Integer desde;
	private Integer hasta;

	public Horario(Integer dia, Integer desde, Integer hasta) {
		super();
		this.dia = dia;
		this.desde = desde;
		this.hasta = hasta;
	}

	public Horario() {
		super();
	}

	public Integer getDia() {
		return dia;
	}

	public void setDia(Integer dia) {
		this.dia = dia;
	}

	public Integer getDesde() {
		return desde;
	}

	public void setDesde(Integer desde) {
		this.desde = desde;
	}

	public Integer getHasta() {
		return hasta;
	}

	public void setHasta(Integer hasta) {
		this.hasta = hasta;
	}

	public boolean isNotNull() {
		return desde != null && hasta != null;
	}

}
