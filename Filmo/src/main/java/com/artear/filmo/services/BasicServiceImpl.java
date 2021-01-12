package com.artear.filmo.services;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artear.filmo.bo.common.Empresa;
import com.artear.filmo.bo.common.Senial;
import com.artear.filmo.daos.interfaces.BasicDao;
import com.artear.filmo.daos.interfaces.BasicDaoPL;
import com.artear.filmo.daos.interfaces.EmpresaDao;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.exceptions.util.ErrorFilmo;
import com.artear.filmo.exceptions.util.ErrorUtils;
import com.artear.filmo.seguridad.services.ServiciosSesionUsuario;
import com.artear.filmo.services.interfaces.BasicService;

/**
 * 
 * @author flvaldes
 * 
 */
@Service("basicService")
public class BasicServiceImpl implements BasicService {

	private static final Log logger = LogFactory.getLog(BasicServiceImpl.class);
	
	@Autowired
	private BasicDaoPL basicDaoPL;
	@Autowired
	private BasicDao basicDao;
	@Autowired
	private EmpresaDao empresaDao;
	@Autowired
	private ServiciosSesionUsuario serviciosSesionUsuario;

	@Override
	public List<Senial> getSeniales() {
		try {
			List<String> seniales = serviciosSesionUsuario.getSeniales();
			String request = StringUtils.join(seniales.iterator(), ",");
			return basicDaoPL.getSeniales(request);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public String getUsuarioEmpresaLogeo() {
		String usuarioEmpresa = "\t";
		try {
			if (serviciosSesionUsuario.getUsuario() != null) {
				usuarioEmpresa = serviciosSesionUsuario.getNombreCompleto();
			}
			if (serviciosSesionUsuario.getEmpresa() != null) {
				usuarioEmpresa += " | " +serviciosSesionUsuario.getEmpresa();
			} else {
				List<Empresa> responseEmpresaUsuario = empresaDao.retrieveById(serviciosSesionUsuario.getIdEmpresa());
				if (responseEmpresaUsuario != null && responseEmpresaUsuario.size() > 0) {
					usuarioEmpresa += " | " + responseEmpresaUsuario.get(0).getDescripcionCompleta();
				}
			}
			if (serviciosSesionUsuario.getSeniales() != null && serviciosSesionUsuario.getSeniales().size() > 0) {
				String request = serviciosSesionUsuario.getSeniales().get(0);
				List<Senial> senialesResponse = basicDaoPL.getSeniales(request);
				usuarioEmpresa += " | " +senialesResponse.get(0).getDescripcion() + "\t";
			}
		} catch (Exception e) {
			//no se pudo obtener el usuario y la empresa
			return usuarioEmpresa;
		}
		return usuarioEmpresa;
	}
	
	@Override
	public Map<String, Object> retrieveDatosSesionUsuario() {
		return serviciosSesionUsuario.retrieveAllAttributes();
	}

	@Override
	public void setUsuarioEnSession() {
		try {
			String usuario = serviciosSesionUsuario.getUsuario();
			basicDaoPL.setUsuarioEnSession(usuario);
		} catch (Exception e) {
			logger.error("No se pudo setear el usuario de sesion en Oracle.", e);
		}
	}

	@Override
	public String getEmpresaDescripcion(short codigoEmpresa) {
		try {
			return basicDao.getEmpresaDescripcion(codigoEmpresa);
		} catch (Exception e) {
			logger.error("No se pudo obtener la Empresa con código: "
					+ codigoEmpresa, e);
		}
		return StringUtils.EMPTY;
	}

}