package com.artear.filmo.bo.contratos;

import java.util.List;

public class ValidacionRecepcionMasterResponse {

	private ErrorValidacionRecepcionMaster errores;
	private List<DatosRemitoRecepcionMaster> datosRemito;
	private List<DatosSoporteRecepcionMaster> datosSoporte;
	private boolean estaEnSNC;
	private boolean recOrig;
	private boolean recCopia;
	
	public ErrorValidacionRecepcionMaster getErrores() {
		return errores;
	}
	
	public void setErrores(ErrorValidacionRecepcionMaster errores) {
		this.errores = errores;
	}

	public List<DatosRemitoRecepcionMaster> getDatosRemito() {
		return datosRemito;
	}
	
	public void setDatosRemito(List<DatosRemitoRecepcionMaster> datosRemito) {
		this.datosRemito = datosRemito;
	}
	
	public List<DatosSoporteRecepcionMaster> getDatosSoporte() {
		return datosSoporte;
	}
	
	public void setDatosSoporte(List<DatosSoporteRecepcionMaster> datosSoporte) {
		this.datosSoporte = datosSoporte;
	}
	
	public boolean isEstaEnSNC() {
		return estaEnSNC;
	}
	
	public void setEstaEnSNC(boolean estaEnSNC) {
		this.estaEnSNC = estaEnSNC;
	}

	public boolean isRecOrig() {
		return recOrig;
	}

	public void setRecOrig(boolean recOrig) {
		this.recOrig = recOrig;
	}

	public boolean isRecCopia() {
		return recCopia;
	}

	public void setRecCopia(boolean recCopia) {
		this.recCopia = recCopia;
	}
	
}