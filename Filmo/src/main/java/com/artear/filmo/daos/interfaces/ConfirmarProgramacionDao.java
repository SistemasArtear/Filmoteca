package com.artear.filmo.daos.interfaces;

import java.util.List;

import com.artear.filmo.bo.programacion.ListarProgramacionRequest;
import com.artear.filmo.bo.programacion.ListarProgramacionResponse;
import com.artear.filmo.bo.programacion.confirmar.BuscarContratosRequest;
import com.artear.filmo.bo.programacion.confirmar.BuscarContratosResponse;
import com.artear.filmo.bo.programacion.confirmar.ProcesarConfirmacionRequest;
import com.artear.filmo.bo.programacion.confirmar.ProcesarConfirmacionResponse;

public interface ConfirmarProgramacionDao {

	public List<ListarProgramacionResponse> listarProgramacion(ListarProgramacionRequest listarProgramacionRequest);

	public ProcesarConfirmacionResponse procesarConfirmacion(ProcesarConfirmacionRequest procesarConfirmacionRequest);
	
	public void procesarRegistrosGrillaProgramacion(ProcesarConfirmacionRequest procesarConfirmacionRequest);

	public ProcesarConfirmacionResponse procesarConfirmacionGrillaProgramacion(ProcesarConfirmacionRequest procesarConfirmacionRequest);

	public List<BuscarContratosResponse> buscarContratosParaTitulo(BuscarContratosRequest buscarContratosRequest);
	
}