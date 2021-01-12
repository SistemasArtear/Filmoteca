package com.artear.filmo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artear.filmo.bo.chequeoOperativo.ChequeoOperativoResponse;
import com.artear.filmo.daos.interfaces.ChequeoOperativoDao;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.exceptions.util.ErrorFilmo;
import com.artear.filmo.exceptions.util.ErrorUtils;
import com.artear.filmo.services.interfaces.ChequeoOperativoService;

@Transactional
@Service("chequeoOperativoService")
public class ChequeoOperativoServiceImpl implements ChequeoOperativoService{

	@Autowired
	ChequeoOperativoDao chequeoOperativoDao;
	
	@Override
	public ChequeoOperativoResponse validarChequeoOperativo(String periodo) {
		try {
			return chequeoOperativoDao.validarChequeoOperativo(periodo);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	
	@Override
	public ChequeoOperativoResponse confirmaChequeoOperativo(Integer mes, Integer anio) {
		try {
		return chequeoOperativoDao.confirmaChequeoOperativo(mes, anio);
		}catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public void procesarChequeoOperativo(Integer mes, Integer anio) {
	    try{
		chequeoOperativoDao.procesarChequeoOperativo(mes, anio);	
	    }catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	
	@Override
	public ChequeoOperativoResponse generarReportes(String fecha, Integer mes, Integer anio) {
		try {
			ChequeoOperativoResponse exhibicionResp =chequeoOperativoDao.exhibicion(mes, anio);
			ChequeoOperativoResponse libreriaResp = chequeoOperativoDao.altaEnLibreria(fecha);
			libreriaResp.setHayRegistrosExhibicion(exhibicionResp.isHayRegistrosExhibicion());
			libreriaResp.setIdReporteEhibicion(exhibicionResp.getIdReporteEhibicion());
			return libreriaResp;
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

    @Override
    public boolean backUpCopiaMensual(String username) {
        try {
            return chequeoOperativoDao.backUpCopiaMensual(username);
        } catch (RuntimeException e) {
            ErrorFilmo error = ErrorUtils.processMessageError(e);
            throw new DataBaseException(error);
        }
    }
}
