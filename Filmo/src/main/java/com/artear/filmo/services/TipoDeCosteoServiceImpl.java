package com.artear.filmo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.artear.filmo.daos.interfaces.TipoDeCosteoDao;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.exceptions.util.ErrorFilmo;
import com.artear.filmo.exceptions.util.ErrorUtils;
import com.artear.filmo.seguridad.services.ServiciosSesionUsuario;
import com.artear.filmo.services.interfaces.TipoDeCosteoService;

@Transactional
@Service("tipoDeCosteoService")
public class TipoDeCosteoServiceImpl implements TipoDeCosteoService {

	@Autowired
	private TipoDeCosteoDao tipoDeCosteoDao;
	
	@Autowired
	private ServiciosSesionUsuario serviciosSesionUsuario;


	@Override
	public String buscarTipoCosteoByGrupo(BuscarTipoDeCosteoRequest request) {
		String r = "";
		try {
			r = tipoDeCosteoDao.getTipoCosteoByGrupo(request);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
		return r;
	}
	@Override
	public String buscarDescripcionCanje(BuscarTipoDeCosteoRequest request) {
		String r = "";
		try {
			r = tipoDeCosteoDao.buscarDescripcionCanje(request);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
		return r;
	}


	@Override
	public List<Costeo> obtenerRegistrosCFM(BuscarTipoDeCosteoRequest request) {
		return this.tipoDeCosteoDao.obtenerRegistrosCFM(request);
	}
	@Override
	public List<Costeo> obtenerRegistrosABM(TipoDeCosteoABMRequest request) {
		return this.tipoDeCosteoDao.obtenerRegistrosABM(request);
	}
	@Override
	public String agregarRegistrosCFM(TipoDeCosteoRegistroRequest request) {
		return this.tipoDeCosteoDao.agregarRegistrosCFM(request);
	}
	@Override
	public String eliminarRegistrosCFM(TipoDeCosteoEliminarRequest request) {
//		P_TIPO_COSTEO = 4 THEN    --  TITULO CFM
		return this.tipoDeCosteoDao.eliminarRegistro(request, 4);
	}
	@Override
	public List<CosteoExcedente> obtenerRegistrosExcedente(BuscarTipoDeCosteoRequest request) {
		return this.tipoDeCosteoDao.obtenerRegistrosExcedente(request);
	}
	@Override
	public String agregarRegistrosExcedente(TipoDeCosteoRegistroRequest request) {
		return this.tipoDeCosteoDao.agregarRegistrosExcedente(request);
	}
	@Override
	public String eliminarRegistrosExcedente(TipoDeCosteoEliminarRequest request) {
//    	ELSIF P_TIPO_COSTEO = 2 THEN 	--SI ES TITULO EXCEDENTE 
		return this.tipoDeCosteoDao.eliminarRegistro(request, 2);
	}
	public String abmRegistroExcedente(TipoDeCosteoABMExcedenteRequest request) {
		return this.tipoDeCosteoDao.abmRegistroExcedente(request);
	}
	public String abmRegistroRating(TipoDeCosteoABMRatingRequest request) {
		return this.tipoDeCosteoDao.abmRegistroRating(request);
	}
	public String abmRegistroCFM(TipoDeCosteoABMRequest request) {
		return this.tipoDeCosteoDao.abmRegistroCFM(request);
	}
	public String abmRegistroMixto(TipoDeCosteoABMRequest request) {
		return this.tipoDeCosteoDao.abmRegistroMixto(request);
	}
	@Override
	public List<CosteoRating> obtenerRegistrosRating(BuscarTipoDeCosteoRequest request) {
		return this.tipoDeCosteoDao.obtenerRegistrosRating(request);
	}
	@Override
	public String agregarRegistrosRating(TipoDeCosteoRegistroRequest request) {
		return this.tipoDeCosteoDao.agregarRegistrosRating(request);
	}
	@Override
	public String eliminarRegistrosRating(TipoDeCosteoEliminarRequest request) {
//		IF P_TIPO_COSTEO = 1  	THEN  	--SI ES TITULO_RATING
		return this.tipoDeCosteoDao.eliminarRegistro(request, 1);
	}
	@Override
	public List<Costeo> obtenerRegistrosMixto(BuscarTipoDeCosteoRequest request) {
		return this.tipoDeCosteoDao.obtenerRegistrosMixto(request);
	}
	@Override
	public String agregarRegistrosMixto(TipoDeCosteoRegistroRequest request) {
		return this.tipoDeCosteoDao.agregarRegistrosMixto(request);
	}
	@Override
	public String eliminarRegistrosMixto(TipoDeCosteoEliminarRequest request) {
//		ELSIF P_TIPO_COSTEO = 3 THEN	--SI ES TITULO MIXTO
		return this.tipoDeCosteoDao.eliminarRegistro(request, 3);
	}
	@Override
	public String validarSeleccion(TipoDeCosteoValidarSeleccionRequest request) {
		return this.tipoDeCosteoDao.validarSeleccion(request);
	}
	@Override
	public List<CosteoExcedente> obtenerRegistrosExcedenteABM(TipoDeCosteoABMExcedenteRequest request) {
		return this.tipoDeCosteoDao.obtenerRegistrosExcedenteABM(request);
	}
	@Override
	public List<CosteoRating> obtenerRegistrosRatingABM(TipoDeCosteoABMRatingRequest request) {
		return this.tipoDeCosteoDao.obtenerRegistrosRatingABM(request);
	}

}