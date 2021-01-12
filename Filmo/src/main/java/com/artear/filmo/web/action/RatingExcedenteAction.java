package com.artear.filmo.web.action;

import java.util.Date;
import java.util.List;

import org.apache.velocity.runtime.directive.Parse;
import org.springframework.beans.factory.annotation.Autowired;

import com.artear.filmo.bo.programacion.Programa;
import com.artear.filmo.bo.programacion.modificarRatingExcedente.BusquedaTitulosRatingExcedenteRequest;
import com.artear.filmo.bo.programacion.modificarRatingExcedente.BusquedaTitulosRatingExcedenteResponse;
import com.artear.filmo.bo.programacion.modificarRatingExcedente.ModificarRatingExcedenteRequest;
import com.artear.filmo.bo.programacion.modificarRatingExcedente.ModificarRatingExcedenteResponse;
import com.artear.filmo.services.interfaces.RatingExcedenteService;
import com.opensymphony.xwork2.ActionSupport;

public class RatingExcedenteAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Busqueda de títulos para modificar ratings y excedentes
	private String tipoNroTitulo;
	private Date fechaExhibicion;
	private Integer codPrograma;
	private String descPrograma;
	private String idSenial;
	
	//Modificación de ratings/excedentes
	private Integer nroCapitulo;
	private String tipoRatingExcedente;
	private Double valorNuevoRatingExcedente;
	private Integer grupo;
	private Integer contrato;
	
	private List<BusquedaTitulosRatingExcedenteResponse> responseTitulosRatingExcedente;
	private ModificarRatingExcedenteResponse responseModificarRatingExcedente;
	
	@Autowired
	private RatingExcedenteService ratingExcedenteService;
	
	public String buscarTitulosRatingExcedente(){
		BusquedaTitulosRatingExcedenteRequest request = new BusquedaTitulosRatingExcedenteRequest();
		request.setTipoNroTitulo(this.tipoNroTitulo);
		request.setFechaExhibicion(this.fechaExhibicion);
		
		Programa programa = new Programa();
		programa.setCodigo(this.codPrograma);
		programa.setDescripcion(this.descPrograma);
		
		request.setPrograma(programa);
		request.setidSenial(this.idSenial);
		
		this.responseTitulosRatingExcedente = this.ratingExcedenteService.obtenerTitulosRatingExcedente(request);
		
		return SUCCESS;
	}
	
	public String modificarRatingExcedente(){
		ModificarRatingExcedenteRequest request = new ModificarRatingExcedenteRequest();
		
		String[] tipoNroTitulo = this.tipoNroTitulo.split(" ");
		request.setTipoTitulo(tipoNroTitulo[0]);
		request.setNumeroTitulo(Integer.parseInt(tipoNroTitulo[1]));
		
		request.setFechaExhibicion(this.fechaExhibicion);
		request.setGrupo(this.grupo);
		request.setContrato(this.contrato);
		request.setIdSenial(this.idSenial);
		request.setNroCapitulo(this.nroCapitulo);
		
		Programa programa = new Programa();
		programa.setCodigo(this.codPrograma);
		
		request.setPrograma(programa);
		request.setTipoRatingExcedente(this.tipoRatingExcedente);
		request.setValorRatingExcedente(this.valorNuevoRatingExcedente);
		
		this.responseModificarRatingExcedente = ratingExcedenteService.modificarRatingExcedente(request);
		
		return SUCCESS;
	}

	public String getTipoNroTitulo() {
		return tipoNroTitulo;
	}

	public void setTipoNroTitulo(String tipoNroTitulo) {
		this.tipoNroTitulo = tipoNroTitulo;
	}

	public Date getFechaExhibicion() {
		return fechaExhibicion;
	}

	public void setFechaExhibicion(Date fechaExhibicion) {
		this.fechaExhibicion = fechaExhibicion;
	}

	public Integer getCodPrograma() {
		return codPrograma;
	}

	public void setCodPrograma(Integer codPrograma) {
		this.codPrograma = codPrograma;
	}

	public String getDescPrograma() {
		return descPrograma;
	}

	public void setDescPrograma(String descPrograma) {
		this.descPrograma = descPrograma;
	}

	public String getIdSenial() {
		return idSenial;
	}

	public void setIdSenial(String idSenial) {
		this.idSenial = idSenial;
	}

	public List<BusquedaTitulosRatingExcedenteResponse> getResponseTitulosRatingExcedente() {
		return responseTitulosRatingExcedente;
	}

	public void setResponseTitulosRatingExcedente(List<BusquedaTitulosRatingExcedenteResponse> responseTitulosRatingExcedente) {
		this.responseTitulosRatingExcedente = responseTitulosRatingExcedente;
	}

	public ModificarRatingExcedenteResponse getResponseModificarRatingExcedente() {
		return responseModificarRatingExcedente;
	}

	public void setResponseModificarRatingExcedente(ModificarRatingExcedenteResponse responseModificarRatingExcedente) {
		this.responseModificarRatingExcedente = responseModificarRatingExcedente;
	}

	public Integer getNroCapitulo() {
		return nroCapitulo;
	}

	public void setNroCapitulo(Integer nroCapitulo) {
		this.nroCapitulo = nroCapitulo;
	}

	public String getTipoRatingExcedente() {
		return tipoRatingExcedente;
	}

	public void setTipoRatingExcedente(String tipoRatingExcedente) {
		this.tipoRatingExcedente = tipoRatingExcedente;
	}

	public Double getValorNuevoRatingExcedente() {
		return valorNuevoRatingExcedente;
	}

	public void setValorNuevoRatingExcedente(Double valorRatingExcedente) {
		this.valorNuevoRatingExcedente = valorRatingExcedente;
	}

	public Integer getGrupo() {
		return grupo;
	}

	public void setGrupo(Integer grupo) {
		this.grupo = grupo;
	}

	public Integer getContrato() {
		return contrato;
	}

	public void setContrato(Integer contrato) {
		this.contrato = contrato;
	}
}
