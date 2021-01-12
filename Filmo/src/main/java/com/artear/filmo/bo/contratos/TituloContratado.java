/**
 * 
 */
package com.artear.filmo.bo.contratos;


/**
 * @author sisloc
 * 
 */
public class TituloContratado {
    private String clave;
    private String tituloCastellano;
    private String programado;
    private Integer contrato;
    private String exhibicionConfirmada;
    private String aConsultar;
    private String reRun;
    private String gastos;
    private Integer grupo;
    private String recibido;
    private String standby;
    private String formula;
    private String puedeElegirTodos;
    private String puedeElegirParcial;
    private String puedeElegir;
    private String puedeHacaerReRun;
    private String senial;
    private String tipoCosteo;
    private String canje;
    
    public void setRecibido(String recibido) {
        this.recibido = recibido;
    }
    
    public String getRecibido() {
        return this.recibido;
    }
    
    public final String getReRun() {
        return this.reRun;
    }

    public final void setReRun(String reRun) {
        this.reRun = reRun;
    }

    public final String getGastos() {
        return this.gastos;
    }

    public final void setGastos(String gastos) {
        this.gastos = gastos;
    }

    public final String getProgramado() {
        return this.programado;
    }

    public final void setProgramado(String programado) {
        this.programado = programado;
    }

    public final String getExhibicionConfirmada() {
        return this.exhibicionConfirmada;
    }

    public final void setExhibicionConfirmada(String exhibicionConfirmada) {
        this.exhibicionConfirmada = exhibicionConfirmada;
    }

    public final String getaConsultar() {
        return this.aConsultar;
    }

    public final void setaConsultar(String aConsultar) {
        this.aConsultar = aConsultar;
    }

    public final String getTituloCastellano() {
        return this.tituloCastellano;
    }

    public final void setTituloCastellano(String tituloCastellano) {
        this.tituloCastellano = tituloCastellano;
    }

    public final String getClave() {
        return this.clave;
    }

    public final void setClave(String clave) {
        this.clave = clave;
    }

    public Integer getGrupo() {
        return grupo;
    }

    public void setGrupo(Integer grupo) {
        this.grupo = grupo;
    }

    public String getStandby() {
        return standby;
    }

    public void setStandby(String standby) {
        this.standby = standby;
    }

    public Integer getContrato() {
        return contrato;
    }

    public void setContrato(Integer contrato) {
        this.contrato = contrato;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public void setPuedeElegirTodos(String puedeElegirTodos) {
        this.puedeElegirTodos = puedeElegirTodos;
    }

    public void setPuedeElegirParcial(String puedeElegirParcial) {
        this.puedeElegirParcial = puedeElegirParcial;
    }

    public void setPuedeElegir(String puedeElegir) {
        this.puedeElegir = puedeElegir;
    }

    public String getPuedeElegir() {
        return this.puedeElegir;
    }
    
    public String getPuedeElegirParcial() {
        return this.puedeElegirParcial;
    }
    
    public String getPuedeElegirTodos() {
        return this.puedeElegirTodos;
    }

    public String getPuedeHacaerReRun() {
        return puedeHacaerReRun;
    }

    public void setPuedeHacaerReRun(String puedeHacaerReRun) {
        this.puedeHacaerReRun = puedeHacaerReRun;
    }

    public void setSenial(String senial) {
        this.senial = senial;
    }

    public String getSenial() {
        return this.senial;
    }

	public String getTipoCosteo() {
		return tipoCosteo;
	}

	public void setTipoCosteo(String tipoCosteo) {
		this.tipoCosteo = tipoCosteo;
	}

	public String getCanje() {
		return canje;
	}

	public void setCanje(String canje) {
		this.canje = canje;
	}
    
}
