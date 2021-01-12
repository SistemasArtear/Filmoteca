package com.artear.filmo.bo.chequeo.tecnico;

import java.math.BigDecimal;
import java.util.List;

public class TitulosConContratosExhibidosResponse {

	private String mensaje;
	private BigDecimal tipo;
	private List<ContratoExhibido> contratosExhibidos;

	public TitulosConContratosExhibidosResponse() {
		super();
	}

	public TitulosConContratosExhibidosResponse(String mensaje, BigDecimal tipo,
			List<ContratoExhibido> contratosExhibidos) {
		super();
		this.mensaje = mensaje;
		this.tipo = tipo;
		this.contratosExhibidos = contratosExhibidos;
	}
	
	public TitulosConContratosExhibidosResponse(String mensaje, 
			List<ContratoExhibido> contratosExhibidos) {
		super();
		this.mensaje = mensaje;
	    this.contratosExhibidos = contratosExhibidos;
	}
	
	public TitulosConContratosExhibidosResponse(List<ContratoExhibido> contratosExhibidos) {
		super();
		this.contratosExhibidos = contratosExhibidos;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public BigDecimal getTipo() {
		return tipo;
	}

	public void setTipo(BigDecimal tipo) {
		this.tipo = tipo;
	}

	public List<ContratoExhibido> getContratosExhibidos() {
		return contratosExhibidos;
	}

	public void setContratosExhibidos(List<ContratoExhibido> contratosExhibidos) {
		this.contratosExhibidos = contratosExhibidos;
	}

}
