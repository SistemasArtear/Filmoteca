package com.artear.filmo.services.interfaces;

import java.util.List;

import com.artear.filmo.bo.clonar.remitos.ClonarGrillaResponse;
import com.artear.filmo.bo.clonar.remitos.ClonarRemitosRequest;

public interface ClonarRemitosService {
	public List<ClonarGrillaResponse> buscarRemitosSinContratoPorClave(String tipoTitulo, Integer numeroTitulo);
	
	public List<ClonarGrillaResponse> buscarRemitosSinContratoPorTituloOriginal(String tituloOriginal);
	
	public List<ClonarGrillaResponse> buscarRemitosSinContratoPorTituloCastellano(String tituloCastellano);
	
	public List<ClonarGrillaResponse> buscarRemitosConContratoPorClave(String tipoTitulo, Integer numeroTitulo, Integer contratoAnterior, Integer grupoAnterior);
	
	public List<ClonarGrillaResponse> buscarRemitosConContratoPorTituloOriginal(String tituloOriginal);
	
	public List<ClonarGrillaResponse> buscarRemitosConContratoPorTituloCastellano(String tituloCastellano);
	
	public void clonarRemitosSinContrato(ClonarRemitosRequest request);
	
	public void clonarRemitosConContrato(ClonarRemitosRequest request);
}
