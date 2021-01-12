package com.artear.filmo.constants;

public enum TipoBusquedaTitulo {

	ORIGINAL("O", "Original"), CASTELLANO("C", "Castellano");

	private String cod;
	private String desc;

	private TipoBusquedaTitulo(String cod, String desc) {
		this.cod = cod;
		this.desc = desc;
	}

	public String getCod() {
		return cod;
	}

	public String getDesc() {
		return desc;
	}

}
