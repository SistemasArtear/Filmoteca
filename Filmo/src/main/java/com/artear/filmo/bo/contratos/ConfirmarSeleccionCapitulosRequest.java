package com.artear.filmo.bo.contratos;


public class ConfirmarSeleccionCapitulosRequest {

    private Integer contrato;
    private Integer grupo;
    private String senial;
    private String clave;
    private Integer capitulo;
    private Integer parte;
    private String recCopia;
    private String fechaCopia;
    private Integer proveedor;

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

	public String getRecCopia() {
		return recCopia;
	}

	public void setRecCopia(String recCopia) {
		this.recCopia = recCopia;
	}

	public String getFechaCopia() {
		return fechaCopia;
	}

	public void setFechaCopia(String fechaCopia) {
		this.fechaCopia = fechaCopia;
	}
	
	public Integer getProveedor() {
		return proveedor;
	}
	
	public void setProveedor(Integer proveedor) {
		this.proveedor = proveedor;
	}
    
}