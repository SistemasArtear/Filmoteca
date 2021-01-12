package com.artear.filmo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artear.filmo.bo.common.AyudaSituar;
import com.artear.filmo.bo.trabajar.copias.AltaRequest;
import com.artear.filmo.bo.trabajar.copias.BajaRequest;
import com.artear.filmo.bo.trabajar.copias.BuscarTitulosRequest;
import com.artear.filmo.bo.trabajar.copias.Capitulo;
import com.artear.filmo.bo.trabajar.copias.CopiaListado;
import com.artear.filmo.bo.trabajar.copias.MasterCC;
import com.artear.filmo.bo.trabajar.copias.ModifRequest;
import com.artear.filmo.bo.trabajar.copias.TituloListado;
import com.artear.filmo.bo.trabajar.copias.ValidarRequest;
import com.artear.filmo.bo.trabajar.copias.ValidarResponse;
import com.artear.filmo.daos.interfaces.TrabajarConCopiasDeTitulosDao;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.exceptions.util.ErrorFilmo;
import com.artear.filmo.exceptions.util.ErrorUtils;
import com.artear.filmo.services.interfaces.TrabajarConCopiasDeTitulosService;

/**
 * 
 * @author flvaldes
 * 
 */
@Transactional
@Service("trabajarConCopiasDeTitulosService")
public class TrabajarConCopiasDeTitulosServiceImpl implements
		TrabajarConCopiasDeTitulosService {

	@Autowired
	private TrabajarConCopiasDeTitulosDao trabajarConCopiasDeTitulosDao;

	@Override
	public List<TituloListado> buscarTitulos(BuscarTitulosRequest request) {
		try {
			return trabajarConCopiasDeTitulosDao.buscarTitulos(request);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<CopiaListado> buscarDatosCopias(String senial, String clave) {
		try {
			return trabajarConCopiasDeTitulosDao.buscarDatosCopias(senial,
					clave);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public void altaMasterRollos(AltaRequest altaRequest) {
		try {
			if (altaRequest.getMasterCC() != null) {
				trabajarConCopiasDeTitulosDao.altaMasterCC(altaRequest
						.getMasterCC());
			} else if (altaRequest.getMasterSC() != null) {
				trabajarConCopiasDeTitulosDao.altaMasterSC(altaRequest
						.getMasterSC());
			} else if (altaRequest.getRolloCC() != null) {
				trabajarConCopiasDeTitulosDao.altaCopiasRolloCC(altaRequest
						.getRolloCC());
			} else if (altaRequest.getRolloSC() != null) {
				trabajarConCopiasDeTitulosDao.altaCopiasRolloSC(altaRequest
						.getRolloSC());
			}
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}

	}

	@Override
	public ValidarResponse validarAlta(AltaRequest altaRequest) {
		try {
			String masterOCopia = altaRequest.esCopiaOMaster();
			ValidarRequest request = null;
			if (altaRequest.getMasterCC() != null) {
				request = new ValidarRequest(altaRequest.getMasterCC(),
						masterOCopia);
			} else if (altaRequest.getMasterSC() != null) {
				request = new ValidarRequest(altaRequest.getMasterSC(),
						masterOCopia);
			} else if (altaRequest.getRolloCC() != null) {
				request = new ValidarRequest(altaRequest.getRolloCC(),
						masterOCopia);
			} else if (altaRequest.getRolloSC() != null) {
				request = new ValidarRequest(altaRequest.getRolloSC(),
						masterOCopia);
			}
			return trabajarConCopiasDeTitulosDao.validarAlta(request);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public void bajaMasterRollos(BajaRequest bajaRequest) {
		try {
			trabajarConCopiasDeTitulosDao.bajaCopiaMaster(bajaRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<AyudaSituar> buscarSoportes(String descripcion) {
		try {
			return trabajarConCopiasDeTitulosDao.buscarSoportes(descripcion);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<Capitulo> buscarCapitulos(String clave) {
		try {
			return trabajarConCopiasDeTitulosDao.buscarCapitulos(clave);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public String verificarMasterSoporte(MasterCC master) {
		String tipo = master.getClave().substring(0, 2);
		try {
			if (tipo.equals("SE") || tipo.equals("MS") || tipo.equals("SU")) {
				return trabajarConCopiasDeTitulosDao
						.verificarMasterSoporteCC(master);
			} else {
				return trabajarConCopiasDeTitulosDao
						.verificarMasterSoporteSC(master);
			}
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public String verificarRechazoCVR(String clave, String senial,
			String soporte) {
		try {
			return trabajarConCopiasDeTitulosDao.verificarRechazoCVR(clave,
					senial, soporte);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public String chequearMaterialesCopiaCall(String clave, String senial) {
		try {
			return trabajarConCopiasDeTitulosDao.chequearMaterialesCopia(clave,
					senial);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public void modificarCopiaMaster(ModifRequest request) {
		try {
			trabajarConCopiasDeTitulosDao.modificarCopiaMaster(request);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}

	}

	@Override
	public ValidarResponse validarBaja(ValidarRequest validarRequest) {
		try {
			return trabajarConCopiasDeTitulosDao.validarBaja(validarRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

}
