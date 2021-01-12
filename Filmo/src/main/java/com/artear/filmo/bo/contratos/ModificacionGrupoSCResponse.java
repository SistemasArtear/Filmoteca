package com.artear.filmo.bo.contratos;

import java.util.List;

public class ModificacionGrupoSCResponse {

	private boolean hayErrores;
	private List<ErrorModificacionGrupo> erroresRerun;
	private Integer idReporte;
	private String mensaje;

	public boolean isHayErrores() {
		return hayErrores;
	}

	public void setHayErrores(boolean hayErrores) {
		this.hayErrores = hayErrores;
	}

	public List<ErrorModificacionGrupo> getErroresRerun() {
		return erroresRerun;
	}

	public void setErroresRerun(List<ErrorModificacionGrupo> erroresRerun) {
		this.erroresRerun = erroresRerun;
	}
	
	public Integer getIdReporte() {
		return idReporte;
	}
	
	public void setIdReporte(Integer idReporte) {
		this.idReporte = idReporte;
	}
	
	public String getMensaje() {
		return mensaje;
	}
	
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
}