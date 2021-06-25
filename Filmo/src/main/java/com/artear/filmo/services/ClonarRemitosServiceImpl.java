package com.artear.filmo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artear.filmo.bo.clonar.remitos.ClonarGrillaResponse;
import com.artear.filmo.bo.clonar.remitos.ClonarRemitosRequest;
import com.artear.filmo.daos.interfaces.ClonarRemitosDao;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.exceptions.util.ErrorFilmo;
import com.artear.filmo.exceptions.util.ErrorUtils;
import com.artear.filmo.services.interfaces.ClonarRemitosService;

@Transactional
@Service("clonarRemitosService")
public class ClonarRemitosServiceImpl implements ClonarRemitosService {

	@Autowired
	private ClonarRemitosDao ClonarRemitosDao;
	
	@Override
	public List<ClonarGrillaResponse> buscarRemitosSinContratoPorClave(String tipoTitulo, Integer numeroTitulo) {
		return this.ClonarRemitosDao.buscarRemitosSinContratoPorClave(tipoTitulo, numeroTitulo);
	}
	
	@Override
	public List<ClonarGrillaResponse> buscarRemitosSinContratoPorTituloOriginal(String tituloOriginal) {
		return this.ClonarRemitosDao.buscarRemitosSinContratoPorTituloOriginal(tituloOriginal);
	}

	@Override
	public List<ClonarGrillaResponse> buscarRemitosSinContratoPorTituloCastellano(String tituloCastellano) {
		return this.ClonarRemitosDao.buscarRemitosSinContratoPorTituloCastellano(tituloCastellano);
	}
	
	@Override
	public List<ClonarGrillaResponse> buscarRemitosConContratoPorClave(String tipoTitulo, Integer numeroTitulo, Integer contratoAnterior, Integer grupoAnterior) {
		return this.ClonarRemitosDao.buscarRemitosConContratoPorClave(tipoTitulo, numeroTitulo, contratoAnterior, grupoAnterior);
	}
	
	@Override
	public List<ClonarGrillaResponse> buscarRemitosConContratoPorTituloOriginal(String tituloOriginal) {
		return this.ClonarRemitosDao.buscarRemitosConContratoPorTituloOriginal(tituloOriginal);
	}

	@Override
	public List<ClonarGrillaResponse> buscarRemitosConContratoPorTituloCastellano(String tituloCastellano) {
		return this.ClonarRemitosDao.buscarRemitosConContratoPorTituloCastellano(tituloCastellano);
	}
	
	@Override
	public void clonarRemitosSinContrato(ClonarRemitosRequest request) {
		try {
			this.ClonarRemitosDao.clonarRemitosSinContrato(request);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public void clonarRemitosConContrato(ClonarRemitosRequest request) {
		try {
			this.ClonarRemitosDao.clonarRemitosConContrato(request);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

}
