package com.artear.filmo.bo.contratos;

import java.math.BigDecimal;
import java.util.Date;

public class ModificacionGrupoSCRequest {

	private Integer contrato;
	private Integer grupo;
    private String senial;
    private String tipoTitulo;
    private String tipoImporte;
    private String idTipoDerecho;
    private Date fechaComienzoDerechos;
    private Integer cantidadTiempo;
    private String unidadTiempo;
    private String marcaPerpetuidad;
    private BigDecimal importeUnitario;
    private BigDecimal importeGrupo;
    private String pasaLibreria;
    private Integer cantidadTitulos;
    private Integer cantidadPasadas;
    private String nombreGrupo;
    private String er;
    private String derechosTerritoriales;
    private Integer cantidadTiempoExclusivo;
    private String unidadTiempoExclusivo;
    private String autorizacionAutor;
    private String pagaArgentores;
    private Date fechaComienzoLibreria;

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

	public Integer getCantidadTitulos() {
		return cantidadTitulos;
	}

	public void setCantidadTitulos(Integer cantidadTitulos) {
		this.cantidadTitulos = cantidadTitulos;
	}

	public Integer getCantidadPasadas() {
		return cantidadPasadas;
	}

	public void setCantidadPasadas(Integer cantidadPasadas) {
		this.cantidadPasadas = cantidadPasadas;
	}

    public String getNombreGrupo() {
        return this.nombreGrupo;
    }

    public String getER() {
        return this.er;
    }

    public String getDerechosTerritoriales() {
        return this.derechosTerritoriales;
    }

    public Integer getCantidadTiempoExclusivo() {
        return this.cantidadTiempoExclusivo;
    }

    public String getUnidadTiempoExclusivo() {
        return this.unidadTiempoExclusivo;
    }

    public String getAutorizacionAutor() {
        return this.autorizacionAutor;
    }

    public String getPagaArgentores() {
        return this.pagaArgentores;
    }

    public Date getFechaComienzoLibreria() {
        return this.fechaComienzoLibreria;
    }

    public void setAutorizacionAutor(String autorizacionAutor) {
        this.autorizacionAutor = autorizacionAutor;
    }
    
    public void setCantidadTiempoExclusivo(Integer cantidadTiempoExclusivo) {
        this.cantidadTiempoExclusivo = cantidadTiempoExclusivo;
    }
    
    public void setDerechosTerritoriales(String derechosTerritoriales) {
        this.derechosTerritoriales = derechosTerritoriales;
    }
    
    public void setER(String er) {
        this.er = er;
    }
    
    public void setFechaComienzoLibreria(Date fechaComienzoLibreria) {
        this.fechaComienzoLibreria = fechaComienzoLibreria;
    }
    
    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }
    
    public void setPagaArgentores(String pagaArgentores) {
        this.pagaArgentores = pagaArgentores;
    }
    
    public void setUnidadTiempoExclusivo(String unidadTiempoExclusivo) {
        this.unidadTiempoExclusivo = unidadTiempoExclusivo;
    }
}
