package com.artear.filmo.daos.interfaces;

import java.util.List;

import com.artear.filmo.bo.common.AyudaSituar;
import com.artear.filmo.bo.ficha.cinematografica.AyudaFichas;
import com.artear.filmo.bo.ficha.cinematografica.FichaCompleta;
import com.artear.filmo.bo.ficha.cinematografica.FichaListadoResponse;
import com.artear.filmo.constants.TipoBusquedaTitulo;

/**
 * 
 * @author flvaldes
 * 
 */
public interface FichaCinematograficaDao {

	List<FichaListadoResponse> buscarFichas(TipoBusquedaTitulo tipo,
			String titulo);

	boolean eliminarFicha(Integer idFicha);

	FichaCompleta obtenerFichaCompleta(Integer idFicha);

	boolean modificarFicha(FichaCompleta ficha, String usuario);

	boolean guardarFicha(FichaCompleta ficha, String usuario);

	List<AyudaSituar> ayudaSituar(String descripcion, AyudaFichas ayudaFichas);

}
