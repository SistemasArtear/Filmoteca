package com.artear.filmo.services.interfaces;

import java.util.List;

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

/**
 * 
 * @author flvaldes
 * 
 */
public interface TrabajarConCopiasDeTitulosService {
	
	List<TituloListado> buscarTitulos(BuscarTitulosRequest request);
	
	List<CopiaListado> buscarDatosCopias(String senial, String clave);
	
	void altaMasterRollos(AltaRequest altaRequest);

	ValidarResponse validarAlta(AltaRequest altaRequest);

	ValidarResponse validarBaja(ValidarRequest validarRequest);

	void bajaMasterRollos(BajaRequest bajaRequest);
	
	List<AyudaSituar> buscarSoportes(String descripcion);

	List<Capitulo> buscarCapitulos(String clave);
	
	String verificarMasterSoporte(MasterCC master);
	
	String verificarRechazoCVR(String clave, String senial, String soporte);
	
	String chequearMaterialesCopiaCall(String clave, String senial);
	
	void modificarCopiaMaster(ModifRequest request);

}
