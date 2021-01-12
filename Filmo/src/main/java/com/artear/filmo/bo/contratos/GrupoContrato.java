package com.artear.filmo.bo.contratos;

import java.math.BigDecimal;

public class GrupoContrato {

    private Integer nroGrupo;
    private String tipoTitulo;
    private String nombreGrupo;
    private BigDecimal importeGrupo;
    private String estrenoRepeticion;
    private String senialHeredada;

	public Integer getNroGrupo() {
		return nroGrupo;
	}

	public void setNroGrupo(Integer nroGrupo) {
		this.nroGrupo = nroGrupo;
	}

	public String getTipoTitulo() {
		return tipoTitulo;
	}

	public void setTipoTitulo(String tipoTitulo) {
		this.tipoTitulo = tipoTitulo;
	}

	public String getNombreGrupo() {
		return nombreGrupo;
	}

	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

	public BigDecimal getImporteGrupo() {
		return importeGrupo;
	}

	public void setImporteGrupo(BigDecimal importeGrupo) {
		this.importeGrupo = importeGrupo;
	}

	public String getEstrenoRepeticion() {
		return estrenoRepeticion;
	}

	public void setEstrenoRepeticion(String estrenoRepeticion) {
		this.estrenoRepeticion = estrenoRepeticion;
	}

	public String getSenialHeredada() {
		return senialHeredada;
	}

	public void setSenialHeredada(String senialHeredada) {
		this.senialHeredada = senialHeredada;
	}
    
}