package com.artear.filmo.bo.programacion;

import java.util.List;

public class ProgramasPagination {

	private List<Programa> programas;
	private Pagination pagination;

	public ProgramasPagination() {
		super();
	}

	public ProgramasPagination(List<Programa> programas, Pagination pagination) {
		super();
		this.programas = programas;
		this.pagination = pagination;
	}

	public List<Programa> getProgramas() {
		return programas;
	}

	public void setProgramas(List<Programa> programas) {
		this.programas = programas;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

}
