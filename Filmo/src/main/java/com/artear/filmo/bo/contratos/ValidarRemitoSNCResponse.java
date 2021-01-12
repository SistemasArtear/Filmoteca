package com.artear.filmo.bo.contratos;

import java.util.Date;

public class ValidarRemitoSNCResponse {

    private Integer capitulo;
    private Integer parte;
    private String nroRemito;
    private String nroGuia;
    private Date fechaIngreso;
    private Date fechaRemito;
    private String chequeoVolver;

	public Integer getCapitulo() {
		return capitulo;
	}

	public void setCapitulo(Integer capitulo) {
		this.capitulo = capitulo;
	}

	public Integer getParte() {
		return parte;
	}

	public void setParte(Integer parte) {
		this.parte = parte;
	}

	public String getNroRemito() {
		return nroRemito;
	}

	public void setNroRemito(String nroRemito) {
		this.nroRemito = nroRemito;
	}

	public String getNroGuia() {
		return nroGuia;
	}

	public void setNroGuia(String nroGuia) {
		this.nroGuia = nroGuia;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Date getFechaRemito() {
		return fechaRemito;
	}

	public void setFechaRemito(Date fechaRemito) {
		this.fechaRemito = fechaRemito;
	}

	public String getChequeoVolver() {
		return chequeoVolver;
	}

	public void setChequeoVolver(String chequeoVolver) {
		this.chequeoVolver = chequeoVolver;
	}
    
}