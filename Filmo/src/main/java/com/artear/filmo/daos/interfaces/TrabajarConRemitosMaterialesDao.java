package com.artear.filmo.daos.interfaces;

import java.util.Date;
import java.util.List;

import com.artear.filmo.bo.common.Capitulo;
import com.artear.filmo.bo.common.Distribuidor;
import com.artear.filmo.bo.common.Titulo;
import com.artear.filmo.bo.trabajar.remitos.ActualizarCantidadRequest;
import com.artear.filmo.bo.trabajar.remitos.EjecutarRemitoRequest;
import com.artear.filmo.bo.trabajar.remitos.EjecutarRemitoResponse;
import com.artear.filmo.bo.trabajar.remitos.ImprimirRemitoRequest;
import com.artear.filmo.bo.trabajar.remitos.ImprimirRemitoResponse;
import com.artear.filmo.bo.trabajar.remitos.ValidarNuevaSenialRequest;
import com.artear.filmo.bo.trabajar.remitos.ValidarNuevaSenialResponse;

public interface TrabajarConRemitosMaterialesDao {

	public List<Distribuidor> buscarDistribuidoresParaRemitosSalida(String razonSocialDistribuidor);

	public List<Titulo> buscarTitulosParaCarga(Integer codigoDistribuidor, String descripcionTitulo);

	public List<Capitulo> buscarCapitulosParaCarga(String claveTitulo, Integer Capitulo);

	public Boolean validarDistribuidor(Integer codigoDistribuidor);

	public Boolean validarFechaSenial(Date fechaRemito, String senial);
	
	public ValidarNuevaSenialResponse validarNuevaSenial(ValidarNuevaSenialRequest validarNuevaSenialRequest);
	
	public void actualizarCantidad(ActualizarCantidadRequest actualizarCantidadRequest);
	
	public EjecutarRemitoResponse ejecutarRemito(EjecutarRemitoRequest ejecutarRemitoRequest);

	public List<ImprimirRemitoResponse> imprimirRemito(ImprimirRemitoRequest imprimirRemitoRequest);

}