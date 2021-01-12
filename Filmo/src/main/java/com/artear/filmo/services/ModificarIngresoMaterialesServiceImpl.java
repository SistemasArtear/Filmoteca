package com.artear.filmo.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.artear.filmo.daos.interfaces.ModificarIngresoMaterialesDao;
import com.artear.filmo.exceptions.DataBaseException;
import com.artear.filmo.exceptions.util.ErrorFilmo;
import com.artear.filmo.exceptions.util.ErrorUtils;
import com.artear.filmo.services.interfaces.ModificarIngresoMaterialesService;

@Transactional
@Service("modificarIngresoMaterialesService")
public class ModificarIngresoMaterialesServiceImpl implements ModificarIngresoMaterialesService {

	@Autowired
	private ModificarIngresoMaterialesDao modificarIngresoMaterialesDao;

	@Override
	public List<RemitoGrillaResponse> buscarRemitosPorNumeroRemito(String nroRemito, String senial) {
		try {
			return modificarIngresoMaterialesDao.buscarRemitosPorNumeroRemito(nroRemito, senial);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<RemitoGrillaResponse> buscarRemitosPorGuia(String nroGuia, String senial) {
		try {
			return modificarIngresoMaterialesDao.buscarRemitosPorGuia(nroGuia, senial);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<RemitoGrillaResponse> buscarRemitosPorRazonSocial(String razonSocial, String clave, Integer capitulo, Integer parte, String senial) {
		try {
			return modificarIngresoMaterialesDao.buscarRemitosPorRazonSocial(razonSocial, clave, capitulo, parte, senial);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<RemitoGrillaResponse> buscarRemitosPorFecha(Date fecha, String senial) {
		try {
			return modificarIngresoMaterialesDao.buscarRemitosPorFecha(fecha, senial);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<Distribuidor> buscarDistribuidoresParaRemitos(Integer codigoDistribuidor, String razonSocialDistribuidor) {
		try {
			return modificarIngresoMaterialesDao.buscarDistribuidoresParaRemitos(codigoDistribuidor, razonSocialDistribuidor);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public List<Titulo> buscarTitulosPorDescripcion(String descripcionTituloCast, String descripcionTituloOrig) {
		try {
			return modificarIngresoMaterialesDao.buscarTitulosPorDescripcion(descripcionTituloCast, descripcionTituloOrig);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public List<Capitulo> buscarCapitulos(String clave) {
		try {
			return modificarIngresoMaterialesDao.buscarCapitulos(clave);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public List<Capitulo> buscarPartes(String clave, Integer capitulo) {
		try {
			return modificarIngresoMaterialesDao.buscarPartes(clave, capitulo);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<ValidarExhibicionesRow> validarExhibiciones(Integer codigoDistribuidor, String senial, String idRemito, String numeroGuia) {
		try {
			return modificarIngresoMaterialesDao.validarExhibiciones(codigoDistribuidor, senial, idRemito, numeroGuia);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public Boolean modificarCabeceraRemito(CabeceraRemitoABM cabeceraRemitoABM) {
		try {
			return modificarIngresoMaterialesDao.modificarCabeceraRemito(cabeceraRemitoABM);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public ValidarEliminarRemitoResponse validarEliminarRemito(ValidarEliminarRemitoRequest validarEliminarRemitoRequest) {
		try {
			ValidarEliminarRemitoResponse response = this.modificarIngresoMaterialesDao.validarCabeceraRemito(validarEliminarRemitoRequest);
			if ("E".equals(response.getTipo())) {
				response.setErrorValidarCabeceraRemito(true);
			} else if ("W".equals(response.getTipo())) {
				response.setConfirmacionValidarCabeceraRemito(true);
			} else {
				response = this.validarContabilizadoCabeceraRemito(validarEliminarRemitoRequest);
			}
			return response;
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public ValidarEliminarRemitoResponse validarContabilizadoCabeceraRemito(ValidarEliminarRemitoRequest validarEliminarRemitoRequest) {
		try {
			ValidarEliminarRemitoResponse response = this.modificarIngresoMaterialesDao.validarContabilizadoCabeceraRemito(validarEliminarRemitoRequest);
			if ("S".equals(response.getContabiliza())) {
				response.setConfirmacionContabiliza(true);
			} else {
				response.setEliminarRemito(true);
			}
			return response;
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	public DesenlaceResponse validarDesenlaceRemito(BigDecimal idRemito) {
		try {
			return modificarIngresoMaterialesDao.validarDesenlaceRemito(idRemito);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	public Boolean eliminarRemito(BigDecimal idRemito) {
		try {
			return modificarIngresoMaterialesDao.eliminarRemito(idRemito);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public List<TituloGrillaResponse> buscarTitulosRemito(BigDecimal idRemito, String descripcionTituloCast, String descripcionTituloOrig, String tipoMaterial, String senial) {
		try {
			return modificarIngresoMaterialesDao.buscarTitulosRemito(idRemito, descripcionTituloCast, descripcionTituloOrig, tipoMaterial, senial);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public List<Soporte> buscarSoportes(String codigoSoporte) {
		try {
			return modificarIngresoMaterialesDao.buscarSoportes(codigoSoporte);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public Boolean validarSoporte(String codigoSoporte) {
		try {
			return modificarIngresoMaterialesDao.validarSoporte(codigoSoporte);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public List<MotivoIngreso> buscarMotivosIngreso(String descripcionMotivoIngreso) {
		try {
			return modificarIngresoMaterialesDao.buscarMotivosIngreso(descripcionMotivoIngreso);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public Boolean validarMotivoIngreso(String codigoMotivoIngreso) {
		try {
			return modificarIngresoMaterialesDao.validarMotivoIngreso(codigoMotivoIngreso);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public String validarModificarDetalleRemito(ValidarModificarEliminarDetalleRequest validarModificarDetalleRequest) {
		try {
			return modificarIngresoMaterialesDao.validarModificarDetalleRemito(validarModificarDetalleRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public Boolean modificarDetalleRemito(DetalleRemitoABM detalleRemitoABM) {
		try {
			return modificarIngresoMaterialesDao.modificarDetalleRemito(detalleRemitoABM);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public ValidarEliminarDetalleRemitoResponse validarEliminarDetalleRemito(ValidarModificarEliminarDetalleRequest validarEliminarDetalleRequest) {
	    
//	    pr_validar_rpf22022
//	    E l_error := 'El titulo se encuentra confirmado en la grilla.';
//	    W l_error := 'El titulo esta contabilizado, desea continuar?.';
//	    E l_error := 'Existe una ficha de chequeo para el titulo.';
//	    E l_error := 'Fue contabilizada la recepción del titulo.';
	    
		try {
		    
		    
		    //consultar el PL
		    
		    
		    /* 
		          Estructura p_errores 
		          ---------------------------------
		          tipo              varchar2 
		          descripcion       varchar2 
		       */
		    ValidarEliminarDetalleRemitoResponse validarEliminarRemitoResponse = new ValidarEliminarDetalleRemitoResponse();
		    List<ContratoValidationResult> erroresYwarnings = this.modificarIngresoMaterialesDao.validarRpf(validarEliminarDetalleRequest);
            validarEliminarRemitoResponse.setEliminarRemitoDesde(true);
		    for (ContratoValidationResult contratoValidationResult : erroresYwarnings) {
                if (contratoValidationResult.getTipo().trim().equals("E")) {
                    validarEliminarRemitoResponse.setErrorValidarContabilizado(true);
                    validarEliminarRemitoResponse.setErrorVerificar(true);
                    validarEliminarRemitoResponse.setErrorValidarRPF22022(true);
                } else if (contratoValidationResult.getTipo().trim().equals("W")) {
                    validarEliminarRemitoResponse.setConfirmacionContabiliza(true);
                }
                validarEliminarRemitoResponse.setEliminarRemitoDesde(false);
                validarEliminarRemitoResponse.setRespuesta(contratoValidationResult.getDescripcion());
                break;
            }
		    return validarEliminarRemitoResponse;
		    
		    
		    
//			ValidarEliminarDetalleRemitoResponse validarEliminarRemitoResponse = modificarIngresoMaterialesDao.validarContabilizado(validarEliminarDetalleRequest);
//			if ("E".equals(validarEliminarRemitoResponse.getTipo())) {
//				validarEliminarRemitoResponse.setErrorValidarContabilizado(true);
//			} else {
//				validarEliminarDetalleRequest.setContabiliza("C".equals(validarEliminarRemitoResponse.getTipo()) ? "S" : "N"); 
//				validarEliminarRemitoResponse = this.modificarIngresoMaterialesDao.verificar(validarEliminarDetalleRequest);
//				
//				if ("S".equals(validarEliminarRemitoResponse.getFlagChsn())) {
//					validarEliminarRemitoResponse.setErrorVerificar(true);
//				} else {
//					String p_flagError = this.modificarIngresoMaterialesDao.validarRpf(validarEliminarDetalleRequest);
//					if ("S".equals(p_flagError)) {
//						validarEliminarRemitoResponse.setErrorValidarRPF22022(true);
//						validarEliminarRemitoResponse.setRespuesta("Existe una ficha de chequeo para el título");
//					} else {
//						if ("S".equals(validarEliminarDetalleRequest.getContabiliza())) {
//							validarEliminarRemitoResponse.setConfirmacionContabiliza(true);
//							validarEliminarRemitoResponse.setRespuesta("El capítulo esta contabilizado. ¿Desea continuar?");
//						} else {
//							validarEliminarRemitoResponse.setEliminarRemitoDesde(true);
//						}
//					}
//				}
//			}
//			return validarEliminarRemitoResponse;
		    
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public DesenlaceResponse desenlace(ValidarModificarEliminarDetalleRequest validarModificarEliminarDetalleRequest) {
		try {
			return modificarIngresoMaterialesDao.desenlace(validarModificarEliminarDetalleRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}
	
	@Override
	public Boolean eliminarDetalleRemito(ValidarModificarEliminarDetalleRequest validarModificarEliminarDetalleRequest) {
		try {
			return modificarIngresoMaterialesDao.eliminarDetalleRemito(validarModificarEliminarDetalleRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

	@Override
	public Boolean eliminarDetalleRemitoDesde(ValidarModificarEliminarDetalleRequest validarModificarEliminarDetalleRequest) {
		try {
			return modificarIngresoMaterialesDao.eliminarDetalleRemitoDesde(validarModificarEliminarDetalleRequest);
		} catch (RuntimeException e) {
			ErrorFilmo error = ErrorUtils.processMessageError(e);
			throw new DataBaseException(error);
		}
	}

}