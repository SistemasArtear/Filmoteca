package com.artear.filmo.services.interfaces;

import java.util.List;

import com.artear.filmo.bo.programacion.ListarProgramacionRequest;
import com.artear.filmo.bo.programacion.ListarProgramacionResponse;
import com.artear.filmo.bo.programacion.confirmar.BuscarContratosRequest;
import com.artear.filmo.bo.programacion.confirmar.BuscarContratosResponse;
import com.artear.filmo.bo.programacion.confirmar.ProcesarConfirmacionRequest;
import com.artear.filmo.bo.programacion.confirmar.ProcesarConfirmacionResponse;

public interface ConfirmarProgramacionService {

	public List<ListarProgramacionResponse> listarProgramacion(ListarProgramacionRequest listarProgramacionRequest);

	public ProcesarConfirmacionResponse procesarConfirmacion(ProcesarConfirmacionRequest procesarConfirmacionRequest);
	
	public ProcesarConfirmacionResponse procesarConfirmacionGrillaProgramacion(List<ProcesarConfirmacionRequest> procesarConfirmacionRequest) throws Exception;

	public List<BuscarContratosResponse> buscarContratosParaTitulo(BuscarContratosRequest buscarContratosRequest);

}
