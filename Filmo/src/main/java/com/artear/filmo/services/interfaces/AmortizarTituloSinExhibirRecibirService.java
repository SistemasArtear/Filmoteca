package com.artear.filmo.services.interfaces;

import java.util.List;

import com.artear.filmo.bo.amortizar.titulossinexhibir.TituloSinExhibirRecibirGrillaResponse;
import com.artear.filmo.bo.amortizar.titulossinexhibir.TituloSinExhibirRecibirView;

public interface AmortizarTituloSinExhibirRecibirService {

	public List<TituloSinExhibirRecibirGrillaResponse> obtenerTitulosCastellanoPorCodigo(String senial, String clave);

	public List<TituloSinExhibirRecibirGrillaResponse> obtenerTitulosOriginalPorCodigo(String senial, String clave);

	public List<TituloSinExhibirRecibirGrillaResponse> obtenerTitulosCastellanoPorDescripcion(String senial, String descripcion);

	public List<TituloSinExhibirRecibirGrillaResponse> obtenerTitulosOriginalPorDescripcion(String senial, String descripcion);
	
	public TituloSinExhibirRecibirView obtenerTituloSinExhibirRecibirConCapitulos(String senial, Integer contrato, String clave);

	public Boolean amortizarTituloSinExhibirRecibir(String senial, Integer contrato, String clave, Integer grupo);
	
}
