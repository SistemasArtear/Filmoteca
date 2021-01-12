package com.artear.filmo.bo.programacion;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class HorariosProgramaRequest {

	private Integer codPrograma;
	private String senial;
	private HorarioRequest lunes;
	private HorarioRequest martes;
	private HorarioRequest miercoles;
	private HorarioRequest jueves;
	private HorarioRequest viernes;
	private HorarioRequest sabado;
	private HorarioRequest domingo;

	public String getSenial() {
		return senial;
	}

	public void setSenial(String senial) {
		this.senial = senial;
	}

	public Integer getCodPrograma() {
		return codPrograma;
	}

	public void setCodPrograma(Integer codPrograma) {
		this.codPrograma = codPrograma;
	}

	public HorarioRequest getLunes() {
		return lunes;
	}

	public void setLunes(HorarioRequest lunes) {
		this.lunes = lunes;
	}

	public HorarioRequest getMartes() {
		return martes;
	}

	public void setMartes(HorarioRequest martes) {
		this.martes = martes;
	}

	public HorarioRequest getMiercoles() {
		return miercoles;
	}

	public void setMiercoles(HorarioRequest miercoles) {
		this.miercoles = miercoles;
	}

	public HorarioRequest getJueves() {
		return jueves;
	}

	public void setJueves(HorarioRequest jueves) {
		this.jueves = jueves;
	}

	public HorarioRequest getViernes() {
		return viernes;
	}

	public void setViernes(HorarioRequest viernes) {
		this.viernes = viernes;
	}

	public HorarioRequest getSabado() {
		return sabado;
	}

	public void setSabado(HorarioRequest sabado) {
		this.sabado = sabado;
	}

	public HorarioRequest getDomingo() {
		return domingo;
	}

	public void setDomingo(HorarioRequest domingo) {
		this.domingo = domingo;
	}

	public List<Horario> getHorarios() {
		List<Horario> horarios = new ArrayList<Horario>();
		horarios.add(newInstanceHorario(1, lunes));
		horarios.add(newInstanceHorario(2, martes));
		horarios.add(newInstanceHorario(3, miercoles));
		horarios.add(newInstanceHorario(4, jueves));
		horarios.add(newInstanceHorario(5, viernes));
		horarios.add(newInstanceHorario(6, sabado));
		horarios.add(newInstanceHorario(7, domingo));
		return horarios;
	}

	private Horario newInstanceHorario(int dia, HorarioRequest horario) {
		return new Horario(dia, formatHorario(horario.getDesde()),
				formatHorario(horario.getHasta()));
	}

	private Integer formatHorario(String horario) {
		if (StringUtils.isBlank(horario)) {
			return null;
		}
		return Integer.valueOf(horario.replace(":", "") + "00");
	}

}
