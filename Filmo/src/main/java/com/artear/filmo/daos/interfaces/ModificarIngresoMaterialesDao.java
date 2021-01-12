package com.artear.filmo.daos.interfaces;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.artear.filmo.bo.common.Capitulo;
import com.artear.filmo.bo.common.Distribuidor;
import com.artear.filmo.bo.common.MotivoIngreso;
import com.artear.filmo.bo.common.Soporte;
import com.artear.filmo.bo.common.Titulo;
import com.artear.filmo.bo.common.ValidarExhibicionesRow;
import com.artear.filmo.bo.contratos.ContratoValidationResult;
import com.artear.filmo.bo.ingresar.materiales.CabeceraRemitoABM;
import com.artear.filmo.bo.ingresar.materiales.DesenlaceResponse;
import com.artear.filmo.bo.ingresar.materiales.DetalleRemitoABM;
import com.artear.filmo.bo.ingresar.materiales.RemitoGrillaResponse;
import com.artear.filmo.bo.ingresar.materiales.TituloGrillaResponse;
import com.artear.filmo.bo.ingresar.materiales.ValidarEliminarDetalleRemitoResponse;
import com.artear.filmo.bo.ingresar.materiales.ValidarEliminarRemitoRequest;
import com.artear.filmo.bo.ingresar.materiales.ValidarEliminarRemitoResponse;
import com.artear.filmo.bo.ingresar.materiales.ValidarModificarEliminarDetalleRequest;

public interface ModificarIngresoMaterialesDao {

	public List<RemitoGrillaResponse> buscarRemitosPorNumeroRemito(String nroRemito, String senial);
	
	public List<RemitoGrillaResponse> buscarRemitosPorGuia(String nroGuia, String senial);
	
	public List<RemitoGrillaResponse> buscarRemitosPorRazonSocial(String razonSocial, String clave, Integer capitulo, Integer parte, String senial);
	
	public List<RemitoGrillaResponse> buscarRemitosPorFecha(Date fecha, String senial);

	public List<Distribuidor> buscarDistribuidoresParaRemitos(Integer codigoDistribuidor, String razonSocialDistribuidor);
	
	public List<Titulo> buscarTitulosPorDescripcion(String descripcionTituloCast, String descripcionTituloOrig);
	
	public List<Capitulo> buscarCapitulos(String clave);
	
	public List<Capitulo> buscarPartes(String clave, Integer capitulo);
	
	public List<ValidarExhibicionesRow> validarExhibiciones(Integer codigoDistribuidor, String senial, String idRemito, String numeroGuia);
	
	public Boolean modificarCabeceraRemito(CabeceraRemitoABM cabeceraRemitoABM);
	
	public ValidarEliminarRemitoResponse validarCabeceraRemito(ValidarEliminarRemitoRequest validarEliminarRemitoRequest);
	
	public ValidarEliminarRemitoResponse validarContabilizadoCabeceraRemito(ValidarEliminarRemitoRequest validarEliminarRemitoRequest);
	
	public DesenlaceResponse validarDesenlaceRemito(BigDecimal idRemito);

	public Boolean eliminarRemito(BigDecimal idRemito);
	
	public List<TituloGrillaResponse> buscarTitulosRemito(BigDecimal idRemito, String descripcionTituloCast, String descripcionTituloOrig, String tipoMaterial, String senial);
	
	public List<Soporte> buscarSoportes(String codigoSoporte);

	public Boolean validarSoporte(String codigoSoporte);
	
	public List<MotivoIngreso> buscarMotivosIngreso(String descripcionMotivoIngreso);
	
	public Boolean validarMotivoIngreso(String codigoMotivoIngreso);
	
	public String validarModificarDetalleRemito(ValidarModificarEliminarDetalleRequest validarModificarDetalleRequest);
	
	public Boolean modificarDetalleRemito(DetalleRemitoABM detalleRemitoABM);
	
	public ValidarEliminarDetalleRemitoResponse validarContabilizado(ValidarModificarEliminarDetalleRequest validarEliminarDetalleRequest);
	
	public ValidarEliminarDetalleRemitoResponse verificar(ValidarModificarEliminarDetalleRequest validarEliminarDetalleRequest);
	
	public List<ContratoValidationResult> validarRpf(ValidarModificarEliminarDetalleRequest validarEliminarDetalleRequest);
	
	public DesenlaceResponse desenlace(ValidarModificarEliminarDetalleRequest validarEliminarDetalleRequest);  
	
	public Boolean eliminarDetalleRemito(ValidarModificarEliminarDetalleRequest validarEliminarDetalleRequest); 
	
	public Boolean eliminarDetalleRemitoDesde(ValidarModificarEliminarDetalleRequest validarEliminarDetalleRequest);

}
