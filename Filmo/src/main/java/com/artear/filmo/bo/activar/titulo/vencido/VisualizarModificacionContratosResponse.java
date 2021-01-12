package com.artear.filmo.bo.activar.titulo.vencido;

import java.util.Date;

public class VisualizarModificacionContratosResponse {

	/* Datos cabecera */
    private Integer contrato;
    private Date fechaRealDesde;
    private Date fechaRealHasta;
    private String estadoStandBy;
    private String razonSocial;
    private String clave;
    private String tituloCast;
    private String tituloOrig;
    private String tipoDerecho;
    /* Datos cuerpo */
    private Integer idModVigencia;
    private String tipoModifVigencia;
    private String descModifVigencia;
    private Date fechaDesdePaytv;
    private Date fechaHastaPaytv;
    private Integer idPaytvAnulado;
    private Date fechaAntDesde;
    private Date fechaAntHasta;
    private Date fechaNuevaDesde;
    private Date fechaNuevaHasta;
    private String observaciones;
    private String senial;
    private int grupo;

	public Integer getContrato() {
		return contrato;
	}

	public void setContrato(Integer contrato) {
		this.contrato = contrato;
	}

	public Date getFechaRealDesde() {
		return fechaRealDesde;
	}

	public void setFechaRealDesde(Date fechaRealDesde) {
		this.fechaRealDesde = fechaRealDesde;
	}

	public Date getFechaRealHasta() {
		return fechaRealHasta;
	}

	public void setFechaRealHasta(Date fechaRealHasta) {
		this.fechaRealHasta = fechaRealHasta;
	}

	public String getEstadoStandBy() {
		return estadoStandBy;
	}

	public void setEstadoStandBy(String estadoStandBy) {
		this.estadoStandBy = estadoStandBy;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getTituloCast() {
		return tituloCast;
	}

	public void setTituloCast(String tituloCast) {
		this.tituloCast = tituloCast;
	}

	public String getTituloOrig() {
		return tituloOrig;
	}

	public void setTituloOrig(String tituloOrig) {
		this.tituloOrig = tituloOrig;
	}

	public String getTipoDerecho() {
		return tipoDerecho;
	}
	
	public void setTipoDerecho(String tipoDerecho) {
		this.tipoDerecho = tipoDerecho;
	}
	
	public Integer getIdModVigencia() {
		return idModVigencia;
	}

	public void setIdModVigencia(Integer idModVigencia) {
		this.idModVigencia = idModVigencia;
	}

	public String getTipoModifVigencia() {
		return tipoModifVigencia;
	}

	public void setTipoModifVigencia(String tipoModifVigencia) {
		this.tipoModifVigencia = tipoModifVigencia;
	}

	public String getDescModifVigencia() {
		return descModifVigencia;
	}

	public void setDescModifVigencia(String descModifVigencia) {
		this.descModifVigencia = descModifVigencia;
	}

	public Date getFechaDesdePaytv() {
		return fechaDesdePaytv;
	}

	public void setFechaDesdePaytv(Date fechaDesdePaytv) {
		this.fechaDesdePaytv = fechaDesdePaytv;
	}

	public Date getFechaHastaPaytv() {
		return fechaHastaPaytv;
	}

	public void setFechaHastaPaytv(Date fechaHastaPaytv) {
		this.fechaHastaPaytv = fechaHastaPaytv;
	}

	public Integer getIdPaytvAnulado() {
		return idPaytvAnulado;
	}

	public void setIdPaytvAnulado(Integer idPaytvAnulado) {
		this.idPaytvAnulado = idPaytvAnulado;
	}

	public Date getFechaAntDesde() {
		return fechaAntDesde;
	}

	public void setFechaAntDesde(Date fechaAntDesde) {
		this.fechaAntDesde = fechaAntDesde;
	}

	public Date getFechaAntHasta() {
		return fechaAntHasta;
	}

	public void setFechaAntHasta(Date fechaAntHasta) {
		this.fechaAntHasta = fechaAntHasta;
	}

	public Date getFechaNuevaDesde() {
		return fechaNuevaDesde;
	}

	public void setFechaNuevaDesde(Date fechaNuevaDesde) {
		this.fechaNuevaDesde = fechaNuevaDesde;
	}

	public Date getFechaNuevaHasta() {
		return fechaNuevaHasta;
	}

	public void setFechaNuevaHasta(Date fechaNuevaHasta) {
		this.fechaNuevaHasta = fechaNuevaHasta;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

    public void setSenial(String senial) {
        this.senial = senial;
    }
    
    public String getSenial() {
        return this.senial;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }
    
    public int getGrupo() {
        return this.grupo;
    }
}