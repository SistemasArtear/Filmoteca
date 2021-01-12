package com.artear.filmo.bo.ingresar.materiales;

import java.math.BigDecimal;
import java.util.Date;

public class SoporteTituloABM {

	private BigDecimal idRemito;
	private Integer contrato;    
	private Integer grupo;
    private String senial;
    private String clave;
    private Integer nroCap;
    private Integer nroParte;
    private Integer idProveedor;
    private String nroRemito;
    private String nroGuia;
    private Date fechaRtoGuia;
    private Date fechaMov;
    private String codSoporte; 
    private Integer cantRollos; 
    private String requiereChequeo;
    private Integer carrete;
    private Integer lata;
    private Integer torta;
    private String tipoMaterial;
	private String errores;
	private String motivoIngreso; 
	
	public BigDecimal getIdRemito() {
		return idRemito;
	}
	
	public void setIdRemito(BigDecimal idRemito) {
		this.idRemito = idRemito;
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
	
    public String getClave() {
		return clave;
	}
	
    public void setClave(String clave) {
		this.clave = clave;
	}
	
    public Integer getNroCap() {
		return nroCap;
	}
	
    public void setNroCap(Integer nroCap) {
		this.nroCap = nroCap;
	}
	
    public Integer getNroParte() {
		return nroParte;
	}
	
    public void setNroParte(Integer nroParte) {
		this.nroParte = nroParte;
	}
	
    public Integer getIdProveedor() {
		return idProveedor;
	}
	
    public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
	}
	
    public String getNroRemito() {
		return nroRemito;
	}
	
    public void setNroRemito(String nroRemito) {
		this.nroRemito = nroRemito;
	}

    public String getNroGuia() {
		return nroGuia;
	}
	
    public void setNroGuia(String nroGuia) {
		this.nroGuia = nroGuia;
	}
	
    public Date getFechaRtoGuia() {
		return fechaRtoGuia;
	}
	
    public void setFechaRtoGuia(Date fechaRtoGuia) {
		this.fechaRtoGuia = fechaRtoGuia;
	}
	
    public Date getFechaMov() {
		return fechaMov;
	}
	
    public void setFechaMov(Date fechaMov) {
		this.fechaMov = fechaMov;
	}
	
    public String getCodSoporte() {
		return codSoporte;
	}
	
    public void setCodSoporte(String codSoporte) {
		this.codSoporte = codSoporte;
	}
	
    public Integer getCantRollos() {
		return cantRollos;
	}
	
    public void setCantRollos(Integer cantRollos) {
		this.cantRollos = cantRollos;
	}
	
    public String getRequiereChequeo() {
		return requiereChequeo;
	}
	
    public void setRequiereChequeo(String requiereChequeo) {
		this.requiereChequeo = requiereChequeo;
	}
	
    public Integer getCarrete() {
		return carrete;
	}
	
    public void setCarrete(Integer carrete) {
		this.carrete = carrete;
	}
	
    public Integer getLata() {
		return lata;
	}
	
    public void setLata(Integer lata) {
		this.lata = lata;
	}
	
    public Integer getTorta() {
		return torta;
	}
	
    public void setTorta(Integer torta) {
		this.torta = torta;
	}
	
    public String getTipoMaterial() {
		return tipoMaterial;
	}
	
    public void setTipoMaterial(String tipoMaterial) {
		this.tipoMaterial = tipoMaterial;
	}

	public String getErrores() {
		return errores;
	}

	public void setErrores(String errores) {
		this.errores = errores;
	}

	public String getMotivoIngreso() {
		return motivoIngreso;
	}

	public void setMotivoIngreso(String motivoIngreso) {
		this.motivoIngreso = motivoIngreso;
	}
    
}