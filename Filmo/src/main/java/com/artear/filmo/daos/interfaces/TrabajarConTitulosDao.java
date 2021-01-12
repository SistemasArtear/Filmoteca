package com.artear.filmo.daos.interfaces;

import java.util.List;

import com.artear.filmo.bo.trabajar.titulos.CodDesc;
import com.artear.filmo.bo.trabajar.titulos.Contrato;
import com.artear.filmo.bo.trabajar.titulos.ContratoView;
import com.artear.filmo.bo.trabajar.titulos.DetalleContrato;
import com.artear.filmo.bo.trabajar.titulos.FichaCinematograficaView;
import com.artear.filmo.bo.trabajar.titulos.OperacionTitulo;
import com.artear.filmo.bo.trabajar.titulos.TituloABM;
import com.artear.filmo.bo.trabajar.titulos.TituloGrillaResponse;
import com.artear.filmo.bo.trabajar.titulos.TituloView;

/**
 * 
 * @author flvaldes
 * 
 */
public interface TrabajarConTitulosDao {

	List<TituloGrillaResponse> obtenerTitulosCastellanoPorDescripcion(
			String senial, String descripcion);

	void abmTitulo(TituloABM titulo, OperacionTitulo operacion);

	List<CodDesc> obtenerTiposDeTitulos(String descripcion);

	List<CodDesc> obtenerCalificacionesOficiales(String descripcion);

	List<CodDesc> obtenerFichasCinematograficasCast(String descripcion);

	List<CodDesc> obtenerFichasCinematograficasOriginal(String descripcion);

	FichaCinematograficaView cargarFichaCinematografica(String codigo);

	List<TituloGrillaResponse> obtenerTitulosOriginalPorDescripcion(
			String senial, String descripcion);

	List<TituloGrillaResponse> obtenerTitulosCastellanoPorCodigo(String senial,
			String codigo);

	List<TituloGrillaResponse> obtenerTitulosOriginalPorCodigo(String senial,
			String codigo);

	List<Contrato> obtenerContratos(String senial, String clave, Integer contrato);

	ContratoView cargarContrato(String senial, String clave, Integer contrato);

	TituloView cargarTitulo(String senial, String clave);

	DetalleContrato cargarDetalleContrato(String clave, Integer contrato,
			String senial);

}
