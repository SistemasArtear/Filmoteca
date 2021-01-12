package com.artear.filmo.bo.chequeo.tecnico;

import java.util.List;

public class Chequeo {

	private List<InfoAdicionalPrograma> infoAdicionales;
	private Programa programa;

	public Chequeo() {
		super();
	}

	public Chequeo(List<InfoAdicionalPrograma> infoAdicionales,
			Programa programa) {
		super();
		this.infoAdicionales = infoAdicionales;
		this.programa = programa;
	}

	public List<InfoAdicionalPrograma> getInfoAdicionales() {
		return infoAdicionales;
	}

	public void setInfoAdicionales(List<InfoAdicionalPrograma> infoAdicionales) {
		this.infoAdicionales = infoAdicionales;
	}

	public Programa getPrograma() {
		return programa;
	}

	public void setPrograma(Programa programa) {
		this.programa = programa;
	}

}
