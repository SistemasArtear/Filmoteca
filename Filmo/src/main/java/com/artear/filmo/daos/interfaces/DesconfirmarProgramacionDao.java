package com.artear.filmo.daos.interfaces;

import java.math.BigDecimal;
import java.util.List;

import com.artear.filmo.bo.common.Titulo;
import com.artear.filmo.bo.programacion.ListarProgramacionRequest;
import com.artear.filmo.bo.programacion.ListarProgramacionResponse;
import com.artear.filmo.bo.programacion.Programa;
import com.artear.filmo.bo.programacion.desconfirmar.ProcesarDesconfirmacionRequest;
import com.artear.filmo.bo.programacion.desconfirmar.ProcesarDesconfirmacionResponse;

public interface DesconfirmarProgramacionDao {

	public List<ListarProgramacionResponse> listarProgramacion(ListarProgramacionRequest listarProgramacionRequest);

	public List<Programa> buscarProgramasDesconfirmar(String descripcionPrograma, String senial);

	public List<Titulo> buscarTitulosDesconfirmar(String descripcionTitulo);
	
	public ProcesarDesconfirmacionResponse procesarDesconfirmacion(ProcesarDesconfirmacionRequest procesarDesconfirmacionRequest, BigDecimal pasadas);
	
	public BigDecimal obtenerPasadasPorSegmento(ProcesarDesconfirmacionRequest procesarDesconfirmacionRequest);
	
}
