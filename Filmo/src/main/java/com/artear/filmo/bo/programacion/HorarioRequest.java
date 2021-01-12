package com.artear.filmo.bo.programacion;

import org.apache.commons.lang.StringUtils;

public class HorarioRequest {

	private String desde;
	private String hasta;

	public String getDesde() {
		return desde;
	}

	public void setDesde(String desde) {
		this.desde = desde;
	}

	public String getHasta() {
		return hasta;
	}

	public void setHasta(String hasta) {
		this.hasta = hasta;
	}

	public boolean isNotNull() {
		return StringUtils.isNotBlank(desde) && StringUtils.isNotBlank(hasta);
	}

}
