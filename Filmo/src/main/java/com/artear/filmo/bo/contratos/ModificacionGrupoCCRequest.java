package com.artear.filmo.bo.contratos;

import java.math.BigDecimal;
import java.util.Date;

public class ModificacionGrupoCCRequest {

	private Integer contrato;
	private Integer grupo;
    private String senial;
    private String tipoTitulo;
    private String tipoImporte;
    private String idTipoDerecho;
    private Date fechaComienzoDerechos;
    private Date fechaEntrega;
    private Integer cantidadTiempo;
    private String unidadTiempo;
    private String marcaPerpetuidad;
    private String marcaRecontratacion;
    private BigDecimal importeUnitario;
    private BigDecimal importeGrupo;
    private String pasaLibreria;
    private Integer cantidadOriginales;
    private Integer cantidadRepeticiones;
    private String derechosTerritoriales;
    private Integer cantidadTiempoExclusivo;
    private String unidadTiempoExclusivo;
    private String autorizacionAutor;
    private String pagaArgentores;
    private Date fechaComienzoLibreria;

	public final Date getFechaComienzoLibreria() {
        return this.fechaComienzoLibreria;
    }

    public final void setFechaComienzoLibreria(Date fechaComienzoLibreria) {
        this.fechaComienzoLibreria = fechaComienzoLibreria;
    }

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

	public String getTipoTitulo() {
		return tipoTitulo;
	}

	public void setTipoTitulo(String tipoTitulo) {
		this.tipoTitulo = tipoTitulo;
	}

	public String getTipoImporte() {
		return tipoImporte;
	}

	public void setTipoImporte(String tipoImporte) {
		this.tipoImporte = tipoImporte;
	}

	public String getIdTipoDerecho() {
		return idTipoDerecho;
	}

	public void setIdTipoDerecho(String idTipoDerecho) {
		this.idTipoDerecho = idTipoDerecho;
	}

	public Date getFechaComienzoDerechos() {
		return fechaComienzoDerechos;
	}

	public void setFechaComienzoDerechos(Date fechaComienzoDerechos) {
		this.fechaComienzoDerechos = fechaComienzoDerechos;
	}

	public Integer getCantidadTiempo() {
		return cantidadTiempo;
	}

	public void setCantidadTiempo(Integer cantidadTiempo) {
		this.cantidadTiempo = cantidadTiempo;
	}

	public String getUnidadTiempo() {
		return unidadTiempo;
	}

	public void setUnidadTiempo(String unidadTiempo) {
		this.unidadTiempo = unidadTiempo;
	}

	public String getMarcaPerpetuidad() {
		return marcaPerpetuidad;
	}

	public void setMarcaPerpetuidad(String marcaPerpetuidad) {
		this.marcaPerpetuidad = marcaPerpetuidad;
	}

	public String getMarcaRecontratacion() {
		return marcaRecontratacion;
	}

	public void setMarcaRecontratacion(String marcaRecontratacion) {
		this.marcaRecontratacion = marcaRecontratacion;
	}

	public BigDecimal getImporteUnitario() {
		return importeUnitario;
	}

	public void setImporteUnitario(BigDecimal importeUnitario) {
		this.importeUnitario = importeUnitario;
	}

	public BigDecimal getImporteGrupo() {
		return importeGrupo;
	}

	public void setImporteGrupo(BigDecimal importeGrupo) {
		this.importeGrupo = importeGrupo;
	}

	public String getPasaLibreria() {
		return pasaLibreria;
	}

	public void setPasaLibreria(String pasaLibreria) {
		this.pasaLibreria = pasaLibreria;
	}

	public Integer getCantidadOriginales() {
		return cantidadOriginales;
	}

	public void setCantidadOriginales(Integer cantidadOriginales) {
		this.cantidadOriginales = cantidadOriginales;
	}

	public Integer getCantidadRepeticiones() {
		return cantidadRepeticiones;
	}

	public void setCantidadRepeticiones(Integer cantidadRepeticiones) {
		this.cantidadRepeticiones = cantidadRepeticiones;
	}

    public Date getFechaEntrega() {
        return fechaEntrega;
    }
	
    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public final String getDerechosTerritoriales() {
        return this.derechosTerritoriales;
    }

    public final void setDerechosTerritoriales(String derechosTerritoriales) {
        this.derechosTerritoriales = derechosTerritoriales;
    }

    public final Integer getCantidadTiempoExclusivo() {
        return this.cantidadTiempoExclusivo;
    }

    public final void setCantidadTiempoExclusivo(Integer cantidadTiempoExclusivo) {
        this.cantidadTiempoExclusivo = cantidadTiempoExclusivo;
    }

    public final String getUnidadTiempoExclusivo() {
        return this.unidadTiempoExclusivo;
    }

    public final void setUnidadTiempoExclusivo(String unidadTiempoExclusivo) {
        this.unidadTiempoExclusivo = unidadTiempoExclusivo;
    }

    public final String getAutorizacionAutor() {
        return this.autorizacionAutor;
    }

    public final void setAutorizacionAutor(String autorizacionAutor) {
        this.autorizacionAutor = autorizacionAutor;
    }

    public final String getPagaArgentores() {
        return this.pagaArgentores;
    }

    public final void setPagaArgentores(String pagaArgentores) {
        this.pagaArgentores = pagaArgentores;
    }

    
}