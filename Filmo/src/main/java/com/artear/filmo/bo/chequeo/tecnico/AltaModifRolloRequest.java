package com.artear.filmo.bo.chequeo.tecnico;

import java.util.Date;
import java.util.List;

public class AltaModifRolloRequest {

	private String clave;
	private Integer capitulo;
	private Integer parte;
	private Integer nroFicha;
	private Date fechaEmision;
	private String origen;
	private String senial;
	private List<Rollo> rollos;

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

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

	public Integer getNroFicha() {
		return nroFicha;
	}

	public void setNroFicha(Integer nroFicha) {
		this.nroFicha = nroFicha;
	}

	public List<Rollo> getRollos() {
		return rollos;
	}

	public void setRollos(List<Rollo> rollos) {
		this.rollos = rollos;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getSenial() {
		return senial;
	}

	public void setSenial(String senial) {
		this.senial = senial;
	}

}
