package com.artear.filmo.bo.contratos;

import java.math.BigDecimal;
import java.util.Date;

public class RecuperarDatosGrupoResponse {

	private String tipoDerecho;
	private String derechoTerrirorial;
	private String tipoImporte;
	private String nombreGrupo;
	private String marcaPerpetuidad;
	private String estrenoRepeticion;
	private String autorizacionAutor;
	private Date fechaComienzoDerechos;
	private Integer cantidadTiempo;
	private String unidadTiempo;
	private String planEntrega;
	private String pasaLibreria;
	private Date fechaComienzoDerechosLibreria;
	private Integer cantidadTiempoExclusividad;
	private String unidadTiempoExclusividad;
	private String pagaArgentores;
	/* Con capítulos */
	private Integer cantidadOriginales;
	private Integer cantidadRepeticiones;
	private BigDecimal costoTotal;
	private Date fechaPosibleEntrega;
	private String recontratacion;
	/* Sin capítulos */
	private Integer cantidadTitulos;
	private Integer cantidadPasadasContratadas;
	private BigDecimal costoUnitario;

	public String getTipoDerecho() {
		return tipoDerecho;
	}

	public void setTipoDerecho(String tipoDerecho) {
		this.tipoDerecho = tipoDerecho;
	}

	public String getDerechoTerrirorial() {
		return derechoTerrirorial;
	}

	public void setDerechoTerrirorial(String derechoTerrirorial) {
		this.derechoTerrirorial = derechoTerrirorial;
	}

	public String getTipoImporte() {
		return tipoImporte;
	}

	public void setTipoImporte(String tipoImporte) {
		this.tipoImporte = tipoImporte;
	}

	public String getNombreGrupo() {
		return nombreGrupo;
	}

	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

	public String getMarcaPerpetuidad() {
		return marcaPerpetuidad;
	}

	public void setMarcaPerpetuidad(String marcaPerpetuidad) {
		this.marcaPerpetuidad = marcaPerpetuidad;
	}

	public String getEstrenoRepeticion() {
		return estrenoRepeticion;
	}

	public void setEstrenoRepeticion(String estrenoRepeticion) {
		this.estrenoRepeticion = estrenoRepeticion;
	}

	public String getAutorizacionAutor() {
		return autorizacionAutor;
	}

	public void setAutorizacionAutor(String autorizacionAutor) {
		this.autorizacionAutor = autorizacionAutor;
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

	public String getPlanEntrega() {
		return planEntrega;
	}

	public void setPlanEntrega(String planEntrega) {
		this.planEntrega = planEntrega;
	}

	public String getPasaLibreria() {
		return pasaLibreria;
	}

	public void setPasaLibreria(String pasaLibreria) {
		this.pasaLibreria = pasaLibreria;
	}

	public Date getFechaComienzoDerechosLibreria() {
		return fechaComienzoDerechosLibreria;
	}

	public void setFechaComienzoDerechosLibreria(Date fechaComienzoDerechosLibreria) {
		this.fechaComienzoDerechosLibreria = fechaComienzoDerechosLibreria;
	}

	public Integer getCantidadTiempoExclusividad() {
		return cantidadTiempoExclusividad;
	}

	public void setCantidadTiempoExclusividad(Integer cantidadTiempoExclusividad) {
		this.cantidadTiempoExclusividad = cantidadTiempoExclusividad;
	}

	public String getUnidadTiempoExclusividad() {
		return unidadTiempoExclusividad;
	}

	public void setUnidadTiempoExclusividad(String unidadTiempoExclusividad) {
		this.unidadTiempoExclusividad = unidadTiempoExclusividad;
	}

	public String getPagaArgentores() {
		return pagaArgentores;
	}

	public void setPagaArgentores(String pagaArgentores) {
		this.pagaArgentores = pagaArgentores;
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

	public BigDecimal getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(BigDecimal costoTotal) {
		this.costoTotal = costoTotal;
	}

	public Date getFechaPosibleEntrega() {
		return fechaPosibleEntrega;
	}

	public void setFechaPosibleEntrega(Date fechaPosibleEntrega) {
		this.fechaPosibleEntrega = fechaPosibleEntrega;
	}

	public String getRecontratacion() {
		return recontratacion;
	}

	public void setRecontratacion(String recontratacion) {
		this.recontratacion = recontratacion;
	}

	public Integer getCantidadTitulos() {
		return cantidadTitulos;
	}

	public void setCantidadTitulos(Integer cantidadTitulos) {
		this.cantidadTitulos = cantidadTitulos;
	}

	public Integer getCantidadPasadasContratadas() {
		return cantidadPasadasContratadas;
	}

	public void setCantidadPasadasContratadas(Integer cantidadPasadasContratadas) {
		this.cantidadPasadasContratadas = cantidadPasadasContratadas;
	}

	public BigDecimal getCostoUnitario() {
		return costoUnitario;
	}

	public void setCostoUnitario(BigDecimal costoUnitario) {
		this.costoUnitario = costoUnitario;
	}
	
}