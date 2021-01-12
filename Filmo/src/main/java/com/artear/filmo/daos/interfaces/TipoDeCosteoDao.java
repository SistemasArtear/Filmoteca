package com.artear.filmo.daos.interfaces;

import java.util.List;

import com.artear.filmo.bo.contratos.TipoDeCosteoRegistroRequest;
import com.artear.filmo.bo.contratos.TipoDeCosteoValidarSeleccionRequest;
import com.artear.filmo.bo.contratos.BuscarTipoDeCosteoRequest;
import com.artear.filmo.bo.contratos.Costeo;
import com.artear.filmo.bo.contratos.CosteoExcedente;
import com.artear.filmo.bo.contratos.CosteoRating;
import com.artear.filmo.bo.contratos.TipoDeCosteoABMExcedenteRequest;
import com.artear.filmo.bo.contratos.TipoDeCosteoABMRatingRequest;
import com.artear.filmo.bo.contratos.TipoDeCosteoABMRequest;
import com.artear.filmo.bo.contratos.TipoDeCosteoEliminarRequest;

public interface TipoDeCosteoDao {

	public String getTipoCosteoByGrupo(BuscarTipoDeCosteoRequest request);
	public List<Costeo> obtenerRegistrosCFM(BuscarTipoDeCosteoRequest request);
	public List<Costeo> obtenerRegistrosMixto(BuscarTipoDeCosteoRequest request);
	public List<CosteoExcedente> obtenerRegistrosExcedente(BuscarTipoDeCosteoRequest request);
	public List<CosteoRating> obtenerRegistrosRating(BuscarTipoDeCosteoRequest request);
	public String agregarRegistrosCFM(TipoDeCosteoRegistroRequest request);
	public String eliminarRegistro(TipoDeCosteoEliminarRequest request, int p_tipo_costeo);
	public String agregarRegistrosExcedente(TipoDeCosteoRegistroRequest request);
	public String agregarRegistrosRating(TipoDeCosteoRegistroRequest request);
	public String agregarRegistrosMixto(TipoDeCosteoRegistroRequest request);
	public String validarSeleccion(TipoDeCosteoValidarSeleccionRequest request);
	public String buscarDescripcionCanje(BuscarTipoDeCosteoRequest request);
	public String abmRegistroExcedente(TipoDeCosteoABMExcedenteRequest request);
	public String abmRegistroRating(TipoDeCosteoABMRatingRequest request);
	public String abmRegistroCFM(TipoDeCosteoABMRequest request);
	public String abmRegistroMixto(TipoDeCosteoABMRequest request);
	public List<Costeo> obtenerRegistrosABM(TipoDeCosteoABMRequest request);
	public List<CosteoRating> obtenerRegistrosRatingABM(TipoDeCosteoABMRatingRequest request);
	public List<CosteoExcedente> obtenerRegistrosExcedenteABM(TipoDeCosteoABMExcedenteRequest request);

	

}