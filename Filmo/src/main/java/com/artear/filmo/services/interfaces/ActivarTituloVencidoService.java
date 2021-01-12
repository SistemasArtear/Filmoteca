package com.artear.filmo.services.interfaces;

import java.util.List;

import com.artear.filmo.bo.activar.titulo.vencido.BuscarTitulosRequest;
import com.artear.filmo.bo.activar.titulo.vencido.ContratoParaTitulo;
import com.artear.filmo.bo.activar.titulo.vencido.Titulo;
import com.artear.filmo.bo.activar.titulo.vencido.VisualizarModificacionContratosResponse;

/**
 * 
 * @author flvaldes
 * 
 */
public interface ActivarTituloVencidoService {
	
	public List<ContratoParaTitulo> buscarContratosParaElTitulo(String senial, String clave);
	
	public List<Titulo> buscarTitulos(BuscarTitulosRequest request);

	public String validarContrato(String senial, String clave, Integer contrato);
	
	public List<VisualizarModificacionContratosResponse> visualizarModificacionContratos(String senial, String clave, Integer contrato);

}
