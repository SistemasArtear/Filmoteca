package com.artear.filmo.web.action;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.opensymphony.xwork2.ActionSupport;

@Repository
public class ServicePropertiesAction extends ActionSupport {

	private static final long serialVersionUID = -8158394672882458414L;
	
	@Value("${desa.ipservice.filmoteca}")
	private String desaIpServiceFilmoteca;
	@Value("${artear.entorno}")
	private String artearEntorno;
	@Value("${desa.url.LDAP.int}")
	private String desaUrlLDAPInt;
	@Value("${desa.url.LDAP.ext}")
	private String desaUrlLDAPExt;
	@Value("${directorio.temp}")
	private String directorioTemp;
	@Value("${genexus.filmo.server}")
//	@Value("${genexus.filmoudn4.server}")
	private String genexusServer;
	@Value("${domino.server}")
	private String dominoServer;
	@Value("${trazas.email.envio.habilitado}")
	private String trazasEmailEnvioHabilitado;
	@Value("${trazas.email.to}")
	private String trazasEmailTo;
	@Value("${trazas.email.from}")
	private String trazasEmailFrom;
	@Value("${correo.url}")
	private String correoUrl;
	@Value("${correo.puerto}")
	private String correoPuerto;
	
	public String getDesaIpServiceFilmoteca() {
		return desaIpServiceFilmoteca;
	}

	public void setDesaIpServiceFilmoteca(String desaIpServiceFilmoteca) {
		this.desaIpServiceFilmoteca = desaIpServiceFilmoteca;
	}
	
	public String getArtearEntorno() {
		return artearEntorno;
	}
	
	public void setArtearEntorno(String artearEntorno) {
		this.artearEntorno = artearEntorno;
	}
	
	public String getDesaUrlLDAPInt() {
		return desaUrlLDAPInt;
	}
	
	public void setDesaUrlLDAPInt(String desaUrlLDAPInt) {
		this.desaUrlLDAPInt = desaUrlLDAPInt;
	}
	
	public String getDesaUrlLDAPExt() {
		return desaUrlLDAPExt;
	}
	
	public void setDesaUrlLDAPExt(String desaUrlLDAPExt) {
		this.desaUrlLDAPExt = desaUrlLDAPExt;
	}
	
	public String getDirectorioTemp() {
		return directorioTemp;
	}
	
	public void setDirectorioTemp(String directorioTemp) {
		this.directorioTemp = directorioTemp;
	}
	
	public String getGenexusServer() {
		return genexusServer;
	}
	
	public void setGenexuServer(String genexusServer) {
		this.genexusServer = genexusServer;
	}
	
	public String getDominoServer() {
		return dominoServer;
	}
	
	public void setDominoServer(String dominoServer) {
		this.dominoServer = dominoServer;
	}
	
	public String getTrazasEmailEnvioHabilitado() {
		return trazasEmailEnvioHabilitado;
	}
	
	public void setTrazasEmailEnvioHabilitado(String trazasEmailEnvioHabilitado) {
		this.trazasEmailEnvioHabilitado = trazasEmailEnvioHabilitado;
	}
	
	public String getTrazasEmailTo() {
		return trazasEmailTo;
	}
	
	public void setTrazasEmailTo(String trazasEmailTo) {
		this.trazasEmailTo = trazasEmailTo;
	}
	
	public String getTrazasEmailFrom() {
		return trazasEmailFrom;
	}
	
	public void setTrazasEmailFrom(String trazasEmailFrom) {
		this.trazasEmailFrom = trazasEmailFrom;
	}
	
	public String getCorreoUrl() {
		return correoUrl;
	}
	
	public void setCorreoUrl(String correoUrl) {
		this.correoUrl = correoUrl;
	}
	
	public String getCorreoPuerto() {
		return correoPuerto;
	}
	
	public void setCorreoPuerto(String correoPuerto) {
		this.correoPuerto = correoPuerto;
	}
	
	public String execute() {
		return SUCCESS;
	}
	
}