package com.artear.filmo.bo.contratos;

import java.util.Date;

public class ConfirmarAltaCapitulosRequest {

	private Integer contrato;
	private Integer grupo;
	private String senial;
	private String clave;
	private Integer capitulo;
	private Integer parte;
	private Integer proveedor;
	private Date fechaVigencia;
	private String confRemi;
	private String confCopia;
	private String rerun;
	private Date desdeTitulo;
	private boolean automatico;

	public Integer getContrato() {
		return contrato;
	}

	public void setContrato(Integer contrato) {
		this.contrato = contrato;
	}

	public Integer getGrupo() {
		return grupo;
	}

	public void setGrupo(Integer grupo) {
		this.grupo = grupo;
	}

	public String getSenial() {
		return senial;
	}

	public void setSenial(String senial) {
		this.senial = senial;
	}

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

	public Integer getProveedor() {
		return proveedor;
	}

	public void setProveedor(Integer proveedor) {
		this.proveedor = proveedor;
	}

	public Date getFechaVigencia() {
		return fechaVigencia;
	}

	public void setFechaVigencia(Date fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}

	public String getConfRemi() {
		return confRemi;
	}

	public void setConfRemi(String confRemi) {
		this.confRemi = confRemi;
	}

	public String getConfCopia() {
		return confCopia;
	}

	public void setConfCopia(String confCopia) {
		this.confCopia = confCopia;
	}

	public String getRerun() {
		return rerun;
	}

	public void setRerun(String rerun) {
		this.rerun = rerun;
	}

	public Date getDesdeTitulo() {
		return desdeTitulo;
	}

	public void setDesdeTitulo(Date desdeTitulo) {
		this.desdeTitulo = desdeTitulo;
	}
	
	public boolean isAutomatico() {
		return automatico;
	}
	
	public void setAutomatico(boolean automatico) {
		this.automatico = automatico;
	}
	
}