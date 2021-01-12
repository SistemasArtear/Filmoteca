package com.artear.filmo.bo.contratos;

import java.util.List;

public class ValidarRerunSCResponse {

	private Boolean hayErrores;
	private List<TituloConflicto> titulos;
	
	public Boolean getHayErrores() {
		return hayErrores;
	}

	public void setHayErrores(Boolean hayErrores) {
		this.hayErrores = hayErrores;
	}
	
	public List<TituloConflicto> getTitulos() {
		return titulos;
	}
	
	public void setTitulos(List<TituloConflicto> titulos) {
		this.titulos = titulos;
	}

}