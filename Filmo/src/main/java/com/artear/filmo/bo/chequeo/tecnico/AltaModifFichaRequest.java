package com.artear.filmo.bo.chequeo.tecnico;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class AltaModifFichaRequest {

	private String senial;
	private Integer nroFicha;
	private String clave;
	private Integer capitulo;
	private Integer parte;
	private String soporte;
	private String sello;
	private String autor;
	private String director;
	private String productor;
	private Date fechaSolicitud;
	private List<String> actores;
	private List<String> emisoras;

	public String getSenial() {
		return senial;
	}

	public void setSenial(String senial) {
		this.senial = senial;
	}

	public Integer getNroFicha() {
		return nroFicha;
	}

	public void setNroFicha(Integer nroFicha) {
		this.nroFicha = nroFicha;
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

	public String getSoporte() {
		return soporte;
	}

	public void setSoporte(String soporte) {
		this.soporte = soporte;
	}

	public String getSello() {
		return sello;
	}

	public void setSello(String sello) {
		this.sello = sello;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getProductor() {
		return productor;
	}

	public void setProductor(String productor) {
		this.productor = productor;
	}

	public List<String> getActores() {
		return actores;
	}

	public void setActores(List<String> actores) {
		this.actores = actores;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public List<String> getEmisoras() {
		return emisoras;
	}

	public void setEmisoras(List<String> emisoras) {
		this.emisoras = emisoras;
	}

	public String getActoresJoined() {
		return StringUtils.join(actores.iterator(), ",");
	}

	public String getEmisorasJoined() {
		return StringUtils.join(emisoras.iterator(), ",");
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

}
