package com.artear.filmo.daos.interfaces;

import java.util.List;

import com.artear.filmo.bo.common.AyudaSituar;
import com.artear.filmo.bo.trabajar.copias.BajaRequest;
import com.artear.filmo.bo.trabajar.copias.BuscarTitulosRequest;
import com.artear.filmo.bo.trabajar.copias.Capitulo;
import com.artear.filmo.bo.trabajar.copias.CopiaListado;
import com.artear.filmo.bo.trabajar.copias.MasterCC;
import com.artear.filmo.bo.trabajar.copias.MasterSC;
import com.artear.filmo.bo.trabajar.copias.ModifRequest;
import com.artear.filmo.bo.trabajar.copias.RolloCC;
import com.artear.filmo.bo.trabajar.copias.RolloSC;
import com.artear.filmo.bo.trabajar.copias.TituloListado;
import com.artear.filmo.bo.trabajar.copias.ValidarRequest;
import com.artear.filmo.bo.trabajar.copias.ValidarResponse;

/**
 * 
 * @author flvaldes
 * 
 */
public interface TrabajarConCopiasDeTitulosDao {

	List<TituloListado> buscarTitulos(BuscarTitulosRequest request);

	List<CopiaListado> buscarDatosCopias(String senial, String clave);

	void altaCopiasRolloSC(RolloSC rollo);

	void altaCopiasRolloCC(RolloCC rollo);

	void altaMasterSC(MasterSC masterSC);

	void altaMasterCC(MasterCC master);

	List<AyudaSituar> buscarSoportes(String descripcion);

	List<Capitulo> buscarCapitulos(String clave);

	String verificarMasterSoporteSC(MasterSC master);

	String verificarMasterSoporteCC(MasterCC master);

	String verificarRechazoCVR(String clave, String senial, String soporte);

	String chequearMaterialesCopia(String clave, String senial);

	ValidarResponse validarAlta(ValidarRequest data);

	void modificarCopiaMaster(ModifRequest request);

	String bajaCopiaMaster(BajaRequest request);

	ValidarResponse validarBaja(ValidarRequest data);

}
