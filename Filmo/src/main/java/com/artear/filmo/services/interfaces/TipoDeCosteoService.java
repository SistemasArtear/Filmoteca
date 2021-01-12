package com.artear.filmo.services.interfaces;

import java.util.List;

import com.artear.filmo.bo.contratos.BuscarTipoDeCosteoRequest;
import com.artear.filmo.bo.contratos.Costeo;
import com.artear.filmo.bo.contratos.CosteoExcedente;
import com.artear.filmo.bo.contratos.CosteoRating;
import com.artear.filmo.bo.contratos.TipoDeCosteoABMExcedenteRequest;
import com.artear.filmo.bo.contratos.TipoDeCosteoABMRatingRequest;
import com.artear.filmo.bo.contratos.TipoDeCosteoABMRequest;
import com.artear.filmo.bo.contratos.TipoDeCosteoEliminarRequest;
import com.artear.filmo.bo.contratos.TipoDeCosteoRegistroRequest;
import com.artear.filmo.bo.contratos.TipoDeCosteoValidarSeleccionRequest;

public interface TipoDeCosteoService {

	public String buscarTipoCosteoByGrupo(BuscarTipoDeCosteoRequest request);
	public List<Costeo> obtenerRegistrosCFM(BuscarTipoDeCosteoRequest request);
	public List<Costeo> obtenerRegistrosMixto(BuscarTipoDeCosteoRequest request);
	public List<CosteoExcedente> obtenerRegistrosExcedente(BuscarTipoDeCosteoRequest request);
	public List<CosteoRating> obtenerRegistrosRating(BuscarTipoDeCosteoRequest request);
	public String agregarRegistrosCFM(TipoDeCosteoRegistroRequest request);
	public String eliminarRegistrosCFM(TipoDeCosteoEliminarRequest request);
	public String agregarRegistrosExcedente(TipoDeCosteoRegistroRequest request);
	public String eliminarRegistrosExcedente(TipoDeCosteoEliminarRequest request);
	public String agregarRegistrosRating(TipoDeCosteoRegistroRequest request);
	public String eliminarRegistrosRating(TipoDeCosteoEliminarRequest request);
	public String agregarRegistrosMixto(TipoDeCosteoRegistroRequest request);
	public String eliminarRegistrosMixto(TipoDeCosteoEliminarRequest request);
	public String validarSeleccion(TipoDeCosteoValidarSeleccionRequest request);
	public String buscarDescripcionCanje(BuscarTipoDeCosteoRequest request);
	public String abmRegistroExcedente(TipoDeCosteoABMExcedenteRequest request);
	public String abmRegistroRating(TipoDeCosteoABMRatingRequest request);
	public String abmRegistroCFM(TipoDeCosteoABMRequest request);
	public String abmRegistroMixto(TipoDeCosteoABMRequest request);
	public List<Costeo> obtenerRegistrosABM(TipoDeCosteoABMRequest request);
	public List<CosteoExcedente> obtenerRegistrosExcedenteABM(TipoDeCosteoABMExcedenteRequest request);
	public List<CosteoRating> obtenerRegistrosRatingABM(TipoDeCosteoABMRatingRequest request);

}