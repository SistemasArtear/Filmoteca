package com.artear.filmo.services.interfaces;

import java.util.List;

import com.artear.filmo.bo.common.Distribuidor;
import com.artear.filmo.bo.common.Soporte;
import com.artear.filmo.bo.ingresar.materiales.SoporteTituloABM;
import com.artear.filmo.bo.ingresar.materiales.TituloMaterialesGrillaResponse;

public interface IngresarMaterialesService {

	public List<Distribuidor> buscarDistribuidoresParaMateriales(Integer codigoDistribuidor, String razonSocialDistribuidor);
	
	public List<TituloMaterialesGrillaResponse> buscarTitulosCastellanoParaMateriales(String senial, String tipoMaterial, Integer codigoDistribuidor, String descripcionTitulo);
	
	public List<TituloMaterialesGrillaResponse> buscarTitulosOriginalParaMateriales(String senial, String tipoMaterial, Integer codigoDistribuidor, String descripcionTitulo);

	public String determinarFamiliaTitulo(String tipoTituloSeleccionado);
	
	public List<Soporte> buscarSoportes(String codigoSoporte);
	
	public void altaSoporteTitulo(SoporteTituloABM soporteTitulo);
	
}
