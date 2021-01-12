package com.artear.filmo.daos.interfaces;

import java.util.List;

import com.artear.filmo.bo.chequeo.tecnico.AltaModifFichaRequest;
import com.artear.filmo.bo.chequeo.tecnico.AltaModifRolloRequest;
import com.artear.filmo.bo.chequeo.tecnico.AsociarAContrato;
import com.artear.filmo.bo.chequeo.tecnico.BusquedaFichasRequest;
import com.artear.filmo.bo.chequeo.tecnico.BusquedaFichasResponse;
import com.artear.filmo.bo.chequeo.tecnico.Chequeo;
import com.artear.filmo.bo.chequeo.tecnico.Ficha;
import com.artear.filmo.bo.chequeo.tecnico.FichaCompleta;
import com.artear.filmo.bo.chequeo.tecnico.GuardarActoresSinopsisObservRequest;
import com.artear.filmo.bo.chequeo.tecnico.GuardarSegmentoRequest;
import com.artear.filmo.bo.chequeo.tecnico.GuardarSenialesOkFilmRequest;
import com.artear.filmo.bo.chequeo.tecnico.ProgramaSituarResponse;
import com.artear.filmo.bo.chequeo.tecnico.Rollo;
import com.artear.filmo.bo.chequeo.tecnico.SenialOkFilm;
import com.artear.filmo.bo.chequeo.tecnico.TitulosConContratosExhibidosResponse;
import com.artear.filmo.bo.chequeo.tecnico.VerificarDesenlaceRequest;
import com.artear.filmo.bo.chequeo.tecnico.VerificarDesenlaceResponse;
import com.artear.filmo.bo.chequeo.tecnico.VigenciaContrato;
import com.artear.filmo.bo.chequeo.tecnico.VisualizarFichaRequest;
import com.artear.filmo.bo.common.AyudaSituar;
import com.artear.filmo.bo.common.ErrorResponse;
import com.artear.filmo.bo.common.Senial;
import com.artear.filmo.constants.TipoBusquedaTitulo;

/**
 * 
 * @author flvaldes
 * 
 */
public interface TrabajarConFichasDeChequeoTecnicoDao {

	List<BusquedaFichasResponse> buscarFichas(BusquedaFichasRequest request);

	FichaCompleta visualizarFicha(VisualizarFichaRequest request);

	List<ProgramaSituarResponse> buscarProgramasPorTitulo(String titulo,
			TipoBusquedaTitulo tipoBusqueda);

	Chequeo obtenerInfoChequeoPorPrograma(String clave, String capitulo);

	void altaFicha(AltaModifFichaRequest request);

	void modificarFicha(AltaModifFichaRequest request);

	List<ErrorResponse> eliminarFicha(Integer nroFicha, String clave);

	void inactivarFicha(Integer nroFicha, String clave);

	List<VigenciaContrato> infoVigenciaContratos(String clave, Integer ficha);

	String validarTituloEnCanal(String clave, String senial, String capitulo, String parte);

	List<AyudaSituar> buscarGeneros(String descripcion);

	List<AyudaSituar> buscarCalifArtisticas(String descripcion);

	List<AyudaSituar> buscarCalifAutocontrol(String descripcion);

	List<AyudaSituar> buscarCalifOficial(String descripcion);

	void eliminarSegmentos(Integer nroFicha);

	void guardarSegmentos(GuardarSegmentoRequest segmentoRequest);

	void guardarActoresSinopsisYObservaciones(
			GuardarActoresSinopsisObservRequest request);

	void validarChequeoDetalle(Ficha ficha);

	void guardarChequeoDetalle(Ficha ficha);

	void guardarSenialesOkFilm(GuardarSenialesOkFilmRequest request);

	List<SenialOkFilm> buscarSenialesOkFiml(Integer nroFicha, String film);

	/*TitulosConContratosExhibidosResponse popupTitulosConContratosExhibidos(
			TitulosConContratosExhibidosRequest request);*/
	
	TitulosConContratosExhibidosResponse popupTitulosConContratosExhibidos(
			Integer nroFicha, String film);
	
	TitulosConContratosExhibidosResponse popupTitulosConContratosExhibidosSN(
			Integer nroFicha, String film);
	
	String popupTitulosConContratosExhibidosRechazo(Integer nroFicha);
	
	VerificarDesenlaceResponse popupVerificarDesenlace( VerificarDesenlaceRequest request );
	
	VerificarDesenlaceResponse validarPayTV( Integer ficha, String clave ); 
	
	String ejecutarDesenlace(VerificarDesenlaceRequest request);
	
	void actualizarPY6001(Integer nroFicha);

	List<AyudaSituar> buscarSoportes(String descripcion);

	List<Senial> buscarSeniales(Integer nroFicha, String descripcion);

	List<Rollo> buscarRollos(String senial, Integer nroFicha);

	void altaDeRollos(AltaModifRolloRequest request);

	void modificacionDeRollos(AltaModifRolloRequest request);

	void registrarMaster(String senial, String clave, Integer nroFicha);

	List<AsociarAContrato> buscarContratosParaAsociarLaCopia(String senial,
			String clave, Integer nroFicha);

}
