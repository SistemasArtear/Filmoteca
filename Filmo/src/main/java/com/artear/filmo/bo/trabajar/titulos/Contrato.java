package com.artear.filmo.bo.trabajar.titulos;

public class Contrato {

	private Integer codigo;
	private Integer grupo;
	private String fecha;
    private String senial;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getGrupo() {
		return grupo;
	}

	public void setGrupo(Integer grupo) {
		this.grupo = grupo;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

    public void setSenial(String senial) {
        this.senial = senial;
    }
    
    public String getSenial() {
        return this.senial;
    }
}
