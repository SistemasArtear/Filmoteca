package com.artear.filmo.bo.programacion;

public class ValidacionLevantarExhibicionResponse {

	private TituloProgramado tituloProgramado;
	private String ok;

	public ValidacionLevantarExhibicionResponse() {
		super();
	}

	public ValidacionLevantarExhibicionResponse(TituloProgramado tituloProgramado,
			String ok) {
		super();
		this.tituloProgramado = tituloProgramado;
		this.ok = ok;
	}

	public TituloProgramado getTituloProgramado() {
		return tituloProgramado;
	}

	public void setTituloProgramado(TituloProgramado tituloProgramado) {
		this.tituloProgramado = tituloProgramado;
	}

	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

}
