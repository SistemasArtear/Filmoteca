package com.artear.filmo.bo.programacion;

import java.util.List;

public class LevantarExhibicionesRequest {

	private List<TituloProgramado> titulos;
	private Integer codPrograma;
	private String senial;

	public LevantarExhibicionesRequest() {
		super();
	}

	public LevantarExhibicionesRequest(List<TituloProgramado> titulos,
			Integer codPrograma, String senial) {
		super();
		this.titulos = titulos;
		this.codPrograma = codPrograma;
		this.senial = senial;
	}

	public List<TituloProgramado> getTitulos() {
		return titulos;
	}

	public void setTitulos(List<TituloProgramado> titulos) {
		this.titulos = titulos;
	}

	public Integer getCodPrograma() {
		return codPrograma;
	}

	public void setCodPrograma(Integer codPrograma) {
		this.codPrograma = codPrograma;
	}

	public String getSenial() {
		return senial;
	}

	public void setSenial(String senial) {
		this.senial = senial;
	}

}
