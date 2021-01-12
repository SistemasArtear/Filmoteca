package com.artear.filmo.bo.chequeo.tecnico;
import java.util.List;

import com.artear.filmo.bo.contratos.ErrorDesenlaceResponse;

public class VerificarDesenlaceResponse {
	
	private String ejecuta;
	private String mensaje;
	private String error;
	private String idReporte;
	private List<ErrorDesenlaceResponse> erroresDesenlace;
	private String ejecutaEnlace;
	private String contrato;
	private String grupo;
	private String senial;
	
	
	public VerificarDesenlaceResponse(){
		super();		
	}
	
	public VerificarDesenlaceResponse(String ejecuta, String mensaje, String error, String idReporte, List<ErrorDesenlaceResponse> errores, String ejecutaEnlace, String contrato, String grupo, String senial){
		super();
		this.ejecuta = ejecuta;
		this.mensaje = mensaje;
		this.error = error;
		this.idReporte = idReporte;
		this.erroresDesenlace = errores;
		this.ejecutaEnlace = ejecutaEnlace;
		this.contrato = contrato;
		this.grupo = grupo;
		this.senial = senial;
	}
	
	public String getEjecuta() {
		return ejecuta;
	}
	public void setEjecuta(String ejecuta) {
		this.ejecuta = ejecuta;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getIdReporte() {
		return idReporte;
	}
	public void setIdReporte(String idReporte) {
		this.idReporte = idReporte;
	}
	public List<ErrorDesenlaceResponse> getErroresDesenlace() {
		return erroresDesenlace;
	}
	public void setErroresDesenlace(List<ErrorDesenlaceResponse> erroresDesenlace) {
		this.erroresDesenlace = erroresDesenlace;
	}

	public String getEjecutaEnlace() {
		return ejecutaEnlace;
	}

	public void setEjecutaEnlace(String ejecutaEnlace) {
		this.ejecutaEnlace = ejecutaEnlace;
	}

	public String getContrato() {
		return contrato;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getSenial() {
		return senial;
	}

	public void setSenial(String senial) {
		this.senial = senial;
	}

	
	
	
	
	
	
	

}
