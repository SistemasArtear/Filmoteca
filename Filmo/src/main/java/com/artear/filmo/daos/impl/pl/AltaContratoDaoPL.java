package com.artear.filmo.daos.impl.pl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.artear.filmo.bo.common.Distribuidor;
import com.artear.filmo.bo.common.Moneda;
import com.artear.filmo.bo.contratos.*;
import com.artear.filmo.daos.interfaces.AltaContratoDao;
import com.artear.filmo.utils.StringUtils;

@Repository("altaContratoDao")
public class AltaContratoDaoPL implements AltaContratoDao {
	
	private SimpleJdbcCall buscarDistribuidoresCall;
	private SimpleJdbcCall buscarMonedasCall;
	private SimpleJdbcCall buscarTiposTituloCall;
	private SimpleJdbcCall guardarDatosCabeceraContratoCall;
	private SimpleJdbcCall guardarContratoSenialImporteCall;
	private SimpleJdbcCall buscarSenialesImportesCall;
	private SimpleJdbcCall validarExistenciaGruposCall;
	private SimpleJdbcCall modificarContratoSenialImporteCall;
	private SimpleJdbcCall desenlaceContratoSenialCall;
	private SimpleJdbcCall eliminarMontoSenialCall;
	private SimpleJdbcCall validarSumaGruposCall;
	private SimpleJdbcCall actualizarCabeceraEliminadaCall;
	private SimpleJdbcCall confirmarSenialesContratoCall;
	private SimpleJdbcCall buscarTiposImporteCall;
	private SimpleJdbcCall buscarTiposDerechoCall;
	private SimpleJdbcCall buscarTiposDerechoTerritorialCall;
	private SimpleJdbcCall buscarNumeroGrupoCall;
	private SimpleJdbcCall buscarSenialHeredadaCCCall;
	private SimpleJdbcCall validarRecontratacionCall;
	private SimpleJdbcCall validarAmortizacionCCCall;
	private SimpleJdbcCall altaGrupoCCCall;
	private SimpleJdbcCall validarModificacionGrupoCCCall;
	private SimpleJdbcCall eliminarConsultarCCCall;
	private SimpleJdbcCall modificacionGrupoCCCall;
	private SimpleJdbcCall buscarSenialesHeredadasCall;
	private SimpleJdbcCall validarAsignacionSenialHeredadaCCCall;
	private SimpleJdbcCall asignarSenialHeredadaCCCall;
	private SimpleJdbcCall desasignarSenialHeredadaCCCall;
	private SimpleJdbcCall buscarSenialHeredadaSCCall;
	private SimpleJdbcCall validarAmortizacionSCCall;
	private SimpleJdbcCall altaGrupoSCCall;
	private SimpleJdbcCall validarCambioTipoImporteSCCall;
	private SimpleJdbcCall validarPasaLibreriaNaSSCCall;
	private SimpleJdbcCall validarCantidadPasadasContratadasSCCall;
	private SimpleJdbcCall validarRerunSCCall;
	private SimpleJdbcCall validarDesenlaceSCCall;
	private SimpleJdbcCall validarModificacionGrupoSCCall;
	private SimpleJdbcCall eliminarConsultarSCCall;
	private SimpleJdbcCall modificacionGrupoSCCall;
	private SimpleJdbcCall validarAsignacionSenialHeredadaSCCall;
	private SimpleJdbcCall asignarSenialHeredadaSCCall;
	private SimpleJdbcCall desasignarSenialHeredadaSCCall;
	private SimpleJdbcCall buscarGruposContratoCall;
	private SimpleJdbcCall getTipoCosteoByGrupoCall;
	private SimpleJdbcCall desenlaceContratoGrupoCall;
	private SimpleJdbcCall recuperarDatosGrupoCall;
	private SimpleJdbcCall eliminarGrupoContratoCall;
	private SimpleJdbcCall buscarTitulosGrupoCastellanoCall;
	private SimpleJdbcCall buscarTitulosGrupoOriginalCall;
	private SimpleJdbcCall validarPerpetuidadTituloCall;
	private SimpleJdbcCall obtenerNuevaClaveTituloCall;
	private SimpleJdbcCall buscarCalificacionesOficialesCall;
	private SimpleJdbcCall validacionExistenciaTituloCall;
	private SimpleJdbcCall validacionRecepcionMasterCall;
	private SimpleJdbcCall confirmarSeleccionTituloCall;
	private SimpleJdbcCall buscarTitulosRecontratacionCall;
	private SimpleJdbcCall validarRemitoSNCCall;
	private SimpleJdbcCall validarMasterTituloCall;
	private SimpleJdbcCall confirmarSeleccionCapitulosTituloRecontratacionCall;
	private SimpleJdbcCall buscarCapitulosTituloRecontratacionCall;
	private SimpleJdbcCall validarRemitoSNCCapituloRecontratacionCall;
	private SimpleJdbcCall validarRemitoNoSNCCall;
	private SimpleJdbcCall confirmarSeleccionCapituloTituloRecontratacionCall;
	private SimpleJdbcCall contratosEnlazadosTituloRecontratacionCall;
	private SimpleJdbcCall validarDesenlaceTituloContratoCall;
	private SimpleJdbcCall ejecutarDesenlaceTituloContratoCall;
	private SimpleJdbcCall validarEnlacePosteriorCall;
	private SimpleJdbcCall ejecutarEnlacePosteriorCall;
	private SimpleJdbcCall visualizarModificacionesVigenciaCall;
	private SimpleJdbcCall buscarCapitulosEliminacionCall;
	private SimpleJdbcCall validarCantidadCapitulosBajaCall;
	private SimpleJdbcCall primerValidacionRerunBajaCapitulosCall;
	private SimpleJdbcCall segundaValidacionRerunBajaCapitulosCall;
	private SimpleJdbcCall confirmarBajaCapitulosCall;
	private SimpleJdbcCall buscarCapitulosAdicionCall;
	private SimpleJdbcCall validarRemitoCapitulosSNCCall;
	private SimpleJdbcCall confirmarAltaCapitulosCall;
	private SimpleJdbcCall confirmarAltaCapitulosAutomaticoCall;
	private SimpleJdbcCall buscarTitulosEliminarContratoCall;
	private SimpleJdbcCall validarEliminacionTituloContratoCall;
	private SimpleJdbcCall confirmarEliminacionTituloContratoCall;
	private SimpleJdbcCall buscarTitulosGrupoReadOnlyCall;
	private SimpleJdbcCall modificarCabeceraContratoCall;
	
	private static final String PACKAGE_NAME_ALTA_CONTRATOS_1 = "fil_pkg_alta_contratos";
	private static final String PACKAGE_NAME_ALTA_CONTRATOS_2 = "fil_pkg_alta_contratos_2";
	private static final String PACKAGE_NAME_ALTA_CONTRATOS_3 = "fil_pkg_alta_contratos_3";
	private static final String PACKAGE_NAME_ALTA_COSTEOS = "FIL_PKG_ALTA_COSTEOS";
	
	@Autowired
	public AltaContratoDaoPL(DataSource dataSource) {
		super();
		this.buscarDistribuidoresCall = this.buildBuscarDistribuidoresCall(dataSource);
		this.buscarMonedasCall = this.buildBuscarMonedasCall(dataSource);
		this.buscarTiposTituloCall = this.buildBuscarTiposTituloCall(dataSource);
		this.guardarDatosCabeceraContratoCall = this.buildGuardarDatosCabeceraContratoCall(dataSource);
		this.guardarContratoSenialImporteCall = this.buildGuardarContratoSenialImporteCall(dataSource);
		this.buscarSenialesImportesCall = this.buildBuscarSenialesImportesCall(dataSource);
		this.validarExistenciaGruposCall = this.buildValidarExistenciaGruposCall(dataSource);
		this.modificarContratoSenialImporteCall = this.buildModificarContratoSenialImporteCall(dataSource);
		this.desenlaceContratoSenialCall = this.buildDesenlaceContratoSenialCall(dataSource);
		this.eliminarMontoSenialCall = this.buildEliminarMontoSenialCall(dataSource);
		this.validarSumaGruposCall = this.buildValidarSumaGruposCall(dataSource);
		this.actualizarCabeceraEliminadaCall = this.buildActualizarCabeceraEliminadaCall(dataSource);
		this.confirmarSenialesContratoCall = this.buildConfirmarSenialesContratoCall(dataSource);
		this.buscarTiposImporteCall = this.buildBuscarTiposImporteCall(dataSource);
		this.buscarTiposDerechoCall = this.buildBuscarTiposDerechoCall(dataSource);
		this.buscarTiposDerechoTerritorialCall = this.buildBuscarTiposDerechoTerritorialCall(dataSource);
		this.buscarNumeroGrupoCall = this.buildBuscarNumeroGrupoCall(dataSource);
		this.buscarSenialHeredadaCCCall = this.buildBuscarSenialHeredadaCCCall(dataSource);
		this.validarRecontratacionCall = this.buildValidarRecontratacionCall(dataSource);
		this.validarAmortizacionCCCall = this.buildValidarAmortizacionCCCall(dataSource);
		this.altaGrupoCCCall = this.buildAltaGrupoCCCall(dataSource);
		this.validarModificacionGrupoCCCall = this.buildValidarModificacionGrupoCCCall(dataSource);
		this.eliminarConsultarCCCall = this.buildEliminarConsultarCCCall(dataSource);
		this.modificacionGrupoCCCall = this.buildModificacionGrupoCCCall(dataSource);
		this.buscarSenialesHeredadasCall = this.buildBuscarSenialesHeredadasCall(dataSource);
		this.validarAsignacionSenialHeredadaCCCall = this.buildValidarAsignacionSenialHeredadaCCCall(dataSource);
		this.asignarSenialHeredadaCCCall = this.buildAsignarSenialHeredadaCCCall(dataSource);
		this.desasignarSenialHeredadaCCCall = this.buildDesasignarSenialHeredadaCCCall(dataSource);
		this.buscarSenialHeredadaSCCall = this.buildBuscarSenialHeredadaSCCall(dataSource);
		this.validarAmortizacionSCCall = this.buildValidarAmortizacionSCCall(dataSource);
		this.altaGrupoSCCall = this.buildAltaGrupoSCCall(dataSource);
		this.validarCambioTipoImporteSCCall = this.buildValidarCambioTipoImporteSCCall(dataSource);
		this.validarPasaLibreriaNaSSCCall = this.buildValidarPasaLibreriaNaSSCCall(dataSource);
		this.validarCantidadPasadasContratadasSCCall = this.buildValidarCantidadPasadasContratadasSCCall(dataSource);
		this.validarRerunSCCall = this.buildValidarRerunSCCall(dataSource);
		this.validarDesenlaceSCCall = this.buildValidarDesenlaceSCCall(dataSource);
		this.validarModificacionGrupoSCCall = this.buildValidarModificacionGrupoSCCall(dataSource);
		this.eliminarConsultarSCCall = this.buildEliminarConsultarSCCall(dataSource);
		this.modificacionGrupoSCCall = this.buildModificacionGrupoSCCall(dataSource);
		this.validarAsignacionSenialHeredadaSCCall = this.buildValidarAsignacionSenialHeredadaSCCall(dataSource);
		this.asignarSenialHeredadaSCCall = this.buildAsignarSenialHeredadaSCCall(dataSource);
		this.desasignarSenialHeredadaSCCall = this.buildDesasignarSenialHeredadaSCCall(dataSource);
		this.buscarGruposContratoCall = this.buildBuscarGruposContratoCall(dataSource);
		this.getTipoCosteoByGrupoCall = this.buildGetTipoCosteoByGrupoCall(dataSource);
		this.desenlaceContratoGrupoCall = this.buildDesenlaceContratoGrupoCall(dataSource);
		this.recuperarDatosGrupoCall = this.buildRecuperarDatosGrupoCall(dataSource);
		this.eliminarGrupoContratoCall = this.buildEliminarGrupoContratoCall(dataSource);
		this.buscarTitulosGrupoCastellanoCall = this.buildBuscarTitulosGrupoCastellanoCall(dataSource);
		this.buscarTitulosGrupoOriginalCall = this.buildBuscarTitulosGrupoOriginalCall(dataSource);
		this.validarPerpetuidadTituloCall = this.buildValidarPerpetuidadTituloCall(dataSource);
		this.obtenerNuevaClaveTituloCall = this.buidObtenerNuevaClaveTituloCall(dataSource);
		this.buscarCalificacionesOficialesCall = this.buildBuscarCalificacionesOficialesCall(dataSource);
		this.validacionExistenciaTituloCall = this.buildValidacionExistenciaTituloCall(dataSource);
		this.validacionRecepcionMasterCall = this.buildValidacionRecepcionMasterCall(dataSource);
		this.confirmarSeleccionTituloCall = this.buildConfirmarSeleccionTituloCall(dataSource);
		this.buscarTitulosRecontratacionCall = this.buildBuscarTitulosRecontratacionCall(dataSource);
		this.validarRemitoSNCCall = this.buildValidarRemitoSNCCall(dataSource);
		this.validarMasterTituloCall = this.buildValidarMasterTituloCall(dataSource);
		this.confirmarSeleccionCapitulosTituloRecontratacionCall = this.buildConfirmarSeleccionCapitulosTituloRecontratacionCall(dataSource);
		this.buscarCapitulosTituloRecontratacionCall = this.buildBuscarCapitulosTituloRecontratacion(dataSource);
		this.validarRemitoSNCCapituloRecontratacionCall = this.buildValidarRemitoSNCCapituloRecontratacionCall(dataSource);
		this.validarRemitoNoSNCCall = this.buildValidarRemitoNoSNCCall(dataSource);
		this.confirmarSeleccionCapituloTituloRecontratacionCall = this.buildConfirmarSeleccionCapituloTituloRecontratacionCall(dataSource);
		this.contratosEnlazadosTituloRecontratacionCall = this.buildContratosEnlazadosTituloRecontratacionCall(dataSource);
		this.validarDesenlaceTituloContratoCall = this.buildValidarDesenlaceTituloContratoCall(dataSource);
		this.ejecutarDesenlaceTituloContratoCall = this.buildEjecutarDesenlaceTituloContratoCall(dataSource);
		this.validarEnlacePosteriorCall = this.buildValidarEnlacePosteriorCall(dataSource);
		this.ejecutarEnlacePosteriorCall = this.buildEjecutarEnlacePosteriorCall(dataSource);
		this.visualizarModificacionesVigenciaCall = this.buildVisualizarModificacionesVigenciaCall(dataSource);
		this.buscarCapitulosEliminacionCall = this.buildBuscarCapitulosEliminacionCall(dataSource);
		this.validarCantidadCapitulosBajaCall = this.buildValidarCantidadCapitulosBajaCall(dataSource);
		this.primerValidacionRerunBajaCapitulosCall = this.buildPrimerValidacionRerunBajaCapitulosCall(dataSource);
		this.segundaValidacionRerunBajaCapitulosCall = this.buildSegundaValidacionRerunBajaCapitulosCall(dataSource);
		this.confirmarBajaCapitulosCall = this.buildConfirmarBajaCapitulosCall(dataSource);
		this.buscarCapitulosAdicionCall = this.buildBuscarCapitulosAdicionCall(dataSource);
		this.validarRemitoCapitulosSNCCall = this.buildValidarRemitoCapitulosSNCCall(dataSource);
		this.confirmarAltaCapitulosCall = this.buildConfirmarAltaCapitulosCall(dataSource);
		this.confirmarAltaCapitulosAutomaticoCall = this.buildConfirmarAltaCapitulosAutomaticoCall(dataSource);
		this.buscarTitulosEliminarContratoCall = this.buildBuscarTitulosEliminarContratoCall(dataSource);
		this.validarEliminacionTituloContratoCall = this.buildValidarEliminacionTituloContratoCall(dataSource);
		this.confirmarEliminacionTituloContratoCall = this.buildConfirmarEliminacionTituloContratoCall(dataSource);
		this.buscarTitulosGrupoReadOnlyCall = this.buildBuscarTitulosGrupoReadOnlyCall(dataSource);
		this.modificarCabeceraContratoCall = this.buildModificarCabeceraContratoCall(dataSource);
	}

	/***********************************************************************************		
	 ********************				  Builders					********************
	 ***********************************************************************************/
	private SimpleJdbcCall buildBuscarDistribuidoresCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_01_AYUDA_PR")
            .returningResultSet("P_DATOS", new RowMapper<Object>() {
                @Override
                public Distribuidor mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Distribuidor distribuidor = new Distribuidor();
                    distribuidor.setCodigo(rs.getInt("codigo"));
                    distribuidor.setRazonSocial(rs.getString("razon_social"));
                    return distribuidor;
                }
            });
	}
	
    private SimpleJdbcCall buildBuscarMonedasCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_01_AYUDA_MKI")
            .returningResultSet("P_DATOS", new RowMapper<Object>() {
            	@Override
                public Moneda mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Moneda moneda = new Moneda();
                    moneda.setCodigo(rs.getString("codigo"));
                    moneda.setDescripcion(rs.getString("descripcion"));
                    moneda.setAbreviatura(rs.getString("abreviatura"));
                    return moneda;
                }
            });
    }	
	
    private SimpleJdbcCall buildBuscarTiposTituloCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_04_AYUDA_TT")
	        .returningResultSet("P_DATOS", new RowMapper<Object>() {
	        	@Override
	            public TipoTitulo mapRow(ResultSet rs, int rowNum) throws SQLException {
	        		TipoTitulo tipoTitulo = new TipoTitulo();
	        		tipoTitulo.setTipoTitulo(rs.getString("codigo"));
	        		tipoTitulo.setDescripcionTitulo(rs.getString("codigo") + " - " + rs.getString("descripcion"));
	                return tipoTitulo;
	            }
	        });
	}	
    
	private SimpleJdbcCall buildGuardarDatosCabeceraContratoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_01_FIN");
	}
	
	private SimpleJdbcCall buildGuardarContratoSenialImporteCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_02_FIN");
	}
	
	private SimpleJdbcCall buildBuscarSenialesImportesCall(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_03_LISTAR_DATOS")
	        .returningResultSet("P_DATOS", new RowMapper<Object>() {
	        	@Override
	            public SenialImporte mapRow(ResultSet rs, int rowNum) throws SQLException {
	        		SenialImporte senialImporte = new SenialImporte();
	        		senialImporte.setCodigoSenial(rs.getString("senial"));
	        		senialImporte.setDescripcionSenial(rs.getString("descrip_senial"));
	        		senialImporte.setImporte(rs.getBigDecimal("monto"));
	                return senialImporte;
	            }
	        });
	}		
	
	private SimpleJdbcCall buildValidarExistenciaGruposCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_03_VAL_01");
	}
	
	private SimpleJdbcCall buildModificarContratoSenialImporteCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_03_MOD_CAB");
	}
	
	private SimpleJdbcCall buildDesenlaceContratoSenialCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_03_VAL_02")
			.returningResultSet("P_ERR_DESENLACE", new RowMapper<Object>() {
                @Override
                public ErrorDesenlaceResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                	if ((rs.getString("tipoError") == null || StringUtils.EMPTY.equals(rs.getString("tipoError").trim()))) {
                		return null;
                	} else {
	                	ErrorDesenlaceResponse response = new ErrorDesenlaceResponse();
	                	response.setTipoError(rs.getString("tipoError"));
	                	response.setDescripcion(rs.getString("descripcion"));
//		                response.setContrato(rs.getInt("contrato"));
//		                response.setGrupo(rs.getInt("grupo"));
//	                	response.setSenial(rs.getString("senial"));
//	                	response.setTipoTitulo(rs.getString("tipoTitulo"));
//		                response.setNroTitulo(rs.getInt("nroTitulo"));
//		                response.setFechaDesde(rs.getDate("fechaDesde"));
//	                	response.setTipoListado(rs.getString("tipoListado"));
	                    return response;
                	}
                }
            });
	}
	
	private SimpleJdbcCall buildEliminarMontoSenialCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_03_BOR_CAB");
	}
	
	private SimpleJdbcCall buildValidarSumaGruposCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_03_VAL_04");
	}
	
	private SimpleJdbcCall buildActualizarCabeceraEliminadaCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_03_ACT_CAB");
	}
	
	private SimpleJdbcCall buildConfirmarSenialesContratoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_2).withProcedureName("PR_12CONFIRMA")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
				@Override
				 public ConfirmarSenialesContratoResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
					ConfirmarSenialesContratoResponse response = new ConfirmarSenialesContratoResponse();
					response.setContrato(rs.getInt("contrato"));
					response.setGrupo(rs.getInt("grupo"));
					response.setSenial(rs.getString("senial"));
					response.setMensaje(rs.getString("descripcion"));
					return response;
			     }
			});
	}
	
	private SimpleJdbcCall buildModificarCabeceraContratoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_2).withProcedureName("PR_12MODIFICACABECERA");
	}
	
	private SimpleJdbcCall buildBuscarTiposImporteCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_05_AYUDA_TI")
            .returningResultSet("P_DATOS", new RowMapper<Object>() {
            	@Override
                public TipoImporte mapRow(ResultSet rs, int rowNum) throws SQLException {
            		TipoImporte tipoImporte = new TipoImporte();
            		tipoImporte.setCodigo(rs.getString("codigo"));
            		tipoImporte.setDescripcion(rs.getString("descripcion"));
                    return tipoImporte;
                }
            });
	}
	
	private SimpleJdbcCall buildBuscarTiposDerechoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_05_AYUDA_TD")
            .returningResultSet("P_DATOS", new RowMapper<Object>() {
            	@Override
                public TipoDerecho mapRow(ResultSet rs, int rowNum) throws SQLException {
            		TipoDerecho tipoDerecho = new TipoDerecho();
            		tipoDerecho.setCodigo(rs.getString("codigo"));
            		tipoDerecho.setDescripcion(rs.getString("descripcion"));
                    return tipoDerecho;
                }
            });
	}
	
	private SimpleJdbcCall buildBuscarTiposDerechoTerritorialCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_05_AYUDA_DT")
            .returningResultSet("P_DATOS", new RowMapper<Object>() {
            	@Override
                public TipoDerechoTerritorial mapRow(ResultSet rs, int rowNum) throws SQLException {
            		TipoDerechoTerritorial tipoDerechoTerritorial = new TipoDerechoTerritorial();
            		tipoDerechoTerritorial.setCodigo(rs.getString("codigo"));
            		tipoDerechoTerritorial.setDescripcion(rs.getString("descripcion"));
                    return tipoDerechoTerritorial;
                }
            });
	}
	
	private SimpleJdbcCall buildBuscarNumeroGrupoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_GENERARNUMEROGRUPO");
	}
	
	private SimpleJdbcCall buildBuscarSenialHeredadaCCCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_05_BUSCAR_SH")
			 .returningResultSet("P_DATOS", new RowMapper<Object>() {
	         	@Override
	             public String mapRow(ResultSet rs, int rowNum) throws SQLException {
	         		return rs.getString("senial_heredada");
	             }
	         });
	}
	
	private SimpleJdbcCall buildValidarRecontratacionCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_05_VALIDAR_RECONT");
	}
	
	private SimpleJdbcCall buildValidarAmortizacionCCCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_05_VALIDAR_AMORT");
	}
	
	private SimpleJdbcCall buildAltaGrupoCCCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_07SUMGRU");
	}
	
	private SimpleJdbcCall buildValidarModificacionGrupoCCCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_05_MOD_GRUPO_VAL")
            .returningResultSet("P_NVECTORERROR", new RowMapper<Object>() {
            	@Override
                public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
                	if ((rs.getString("tipoError") == null || StringUtils.EMPTY.equals(rs.getString("tipoError").trim()))) {
                		return null;
                	} else {
		        		Map<String, String> mensaje = new HashMap<String, String>();
		        		mensaje.put(rs.getString("tipoError"), rs.getString("descripcion"));
		                return mensaje;
                	}
                }
            });
	}
	
	private SimpleJdbcCall buildEliminarConsultarCCCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_05_ELIMINA_CONSULTAR");
	}
	
	private SimpleJdbcCall buildModificacionGrupoCCCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_05_MOD_GRUPO_FIN")
			.returningResultSet("P_VECTORRERUN", new RowMapper<Object>() {
            	@Override
                public ErrorModificacionGrupo mapRow(ResultSet rs, int rowNum) throws SQLException {
            		ErrorModificacionGrupo error = new ErrorModificacionGrupo();
            		error.setTipoTitulo(rs.getString("ttktpotit"));
    				error.setNroTitulo(rs.getInt("mtknrotit"));
					error.setContrato(rs.getInt("cokcon"));
					error.setGrupo(rs.getInt("grkgrupo"));
                    error.setSenial(rs.getString("snkcod"));
                    return error;
                }
            });
	}
	
	private SimpleJdbcCall buildBuscarSenialesHeredadasCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_06_OBTENER_SH")
            .returningResultSet("P_DATOS", new RowMapper<Object>() {
            	@Override
                public SenialHeredada mapRow(ResultSet rs, int rowNum) throws SQLException {
            		SenialHeredada senialHeredada = new SenialHeredada();
            		senialHeredada.setAsignada(rs.getString("asig"));
            		senialHeredada.setCodigo(rs.getString("codigo"));
            		senialHeredada.setDescripcion(rs.getString("descripcion"));
                    return senialHeredada;
                }
            });
	}
	
	private SimpleJdbcCall buildValidarAsignacionSenialHeredadaCCCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_06_VALIDAR_ASIGNAR_SH")
            .returningResultSet("P_DATOS", new RowMapper<Object>() {
            	@Override
                public ValidarAsignacionSenialHeredadaResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            		ValidarAsignacionSenialHeredadaResponse response = new ValidarAsignacionSenialHeredadaResponse();
            		response.setTipoTitulo(rs.getString("tipo_titulo"));
            		response.setNumeroTitulo(rs.getInt("nro_titulo"));
                    return response;
                }
            });
	}
	
	private SimpleJdbcCall buildAsignarSenialHeredadaCCCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_06_ASIGNAR_SH");
	}
	
	private SimpleJdbcCall buildDesasignarSenialHeredadaCCCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_06_DESASIGNAR_SH");
	}
	
	private SimpleJdbcCall buildBuscarSenialHeredadaSCCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_07OBTENERSH")
			 .returningResultSet("P_DATOS", new RowMapper<Object>() {
	         	@Override
	             public String mapRow(ResultSet rs, int rowNum) throws SQLException {
	         		return rs.getString("senial");
	             }
	         });
	}
	
	private SimpleJdbcCall buildValidarAmortizacionSCCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_07VALIDARAMORTIZACION")
			.returningResultSet("P_DATOS", new RowMapper<Object>() {
				@Override
				 public Map<Integer, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
					Map<Integer, String> ids = new LinkedHashMap<Integer, String>();
					ids.put(rs.getInt("valorIdAmo"), rs.getString("descripcion_1"));
					ids.put(rs.getInt("valorIdAmo2"),  rs.getString("descripcion_2"));
			 		return ids;
			     }
			})
			.returningResultSet("P_NVECTORERROR", new RowMapper<Object>() {
				@Override
				 public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("mensaje");
			     }
			});
	}
	
	private SimpleJdbcCall buildAltaGrupoSCCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_07SUMGRU");
	}
	
	private SimpleJdbcCall buildValidarCambioTipoImporteSCCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_07VALIDACAMBIOIMPORTE")
			.returningResultSet("P_NVECTORERROR", new RowMapper<Object>() {
				@Override
				 public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("mensaje");
			     }
			});
	}
	
	private SimpleJdbcCall buildValidarPasaLibreriaNaSSCCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_07VALIDAPASALIBRERIANAS")
			.returningResultSet("P_NVECTORERROR", new RowMapper<Object>() {
				@Override
				 public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("mensaje");
			     }
			});	
	}
	
	private SimpleJdbcCall buildValidarCantidadPasadasContratadasSCCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_07VALIDACANTPASADAS")
			.returningResultSet("P_NVECTORERROR", new RowMapper<Object>() {
				@Override
				 public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("mensaje");
			     }
			});
	}
	
	private SimpleJdbcCall buildValidarRerunSCCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_07VALIDARERUN")
			.returningResultSet("P_TITULOSENCONFLICTO", new RowMapper<Object>() {
				@Override
				 public TituloConflicto mapRow(ResultSet rs, int rowNum) throws SQLException {
					TituloConflicto titulo = new TituloConflicto();
					titulo.setTipoTitulo(rs.getString("tipoTitulo"));
					titulo.setNumeroTitulo(rs.getInt("nroTitulo"));
					return titulo;
			     }
			});
	}
	
	private SimpleJdbcCall buildValidarDesenlaceSCCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_07VALIDADESENLACE")
			.returningResultSet("P_ERR_DESENLACE", new RowMapper<Object>() {
                @Override
                public ErrorDesenlaceResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                	if ((rs.getString("tipoError") == null || StringUtils.EMPTY.equals(rs.getString("tipoError").trim()))) {
                		return null;
                	} else {
	                	ErrorDesenlaceResponse response = new ErrorDesenlaceResponse();
	                	response.setTipoError(rs.getString("tipoError"));
	                	response.setDescripcion(rs.getString("descripcion"));
		                response.setContrato(rs.getInt("contrato"));
		                response.setGrupo(rs.getInt("grupo"));
	                	response.setSenial(rs.getString("senial"));
	                	response.setTipoTitulo(rs.getString("tipoTitulo"));
		                response.setNroTitulo(rs.getInt("nroTitulo"));
		                response.setFechaDesde(rs.getDate("fechaDesde"));
	                	response.setTipoListado(rs.getString("tipoListado"));
	                    return response;
                	}
                }
            });
	}
	
	private SimpleJdbcCall buildValidarModificacionGrupoSCCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_07MODIFICAGRUPOVAL")
            .returningResultSet("P_NVECTORERROR", new RowMapper<Object>() {
            	@Override
                public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
                	if ((rs.getString("tipoError") == null || StringUtils.EMPTY.equals(rs.getString("tipoError").trim()))) {
                		return null;
                	} else {
	            		Map<String, String> mensaje = new HashMap<String, String>();
	            		mensaje.put(rs.getString("tipoError"), rs.getString("descripcion"));
	                    return mensaje;
                	}
                }
            });
	}
	
	private SimpleJdbcCall buildEliminarConsultarSCCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_07ELIMINARCONSULTA");
	}
	
	private SimpleJdbcCall buildModificacionGrupoSCCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_07MODIFICAGRUPOFIN")
			.returningResultSet("P_VECTORRERUN", new RowMapper<Object>() {
            	@Override
                public ErrorModificacionGrupo mapRow(ResultSet rs, int rowNum) throws SQLException {
            		ErrorModificacionGrupo error = new ErrorModificacionGrupo();
            		error.setTipoTitulo(rs.getString("ttktpotit"));
    				error.setNroTitulo(rs.getInt("mtknrotit"));
					error.setContrato(rs.getInt("cokcon"));
					error.setGrupo(rs.getInt("grkgrupo"));
                    error.setSenial(rs.getString("snkcod"));
                    return error;
                }
            });
	}

	private SimpleJdbcCall buildValidarAsignacionSenialHeredadaSCCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_2).withProcedureName("PR_08VALIDOTITULOSSENIAL")
            .returningResultSet("P_DATOS", new RowMapper<Object>() {
            	@Override
                public ValidarAsignacionSenialHeredadaResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            		ValidarAsignacionSenialHeredadaResponse response = new ValidarAsignacionSenialHeredadaResponse();
            		response.setTipoTitulo(rs.getString("tipoTitulo"));
            		response.setNumeroTitulo(rs.getInt("nroTitulo"));
                    return response;
                }
            });
	}
	
	private SimpleJdbcCall buildAsignarSenialHeredadaSCCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_2).withProcedureName("PR_08_ASIGNAR_SH")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("mensajeError");
				}
			});
	}
	
	private SimpleJdbcCall buildDesasignarSenialHeredadaSCCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_2).withProcedureName("PR_08_DESASIGNAR_SH")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("mensajeError");
				}
			});
	}	
	
	private SimpleJdbcCall buildBuscarGruposContratoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_2).withProcedureName("PR_09GRILLA")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
				@Override
				public GrupoContrato mapRow(ResultSet rs, int rowNum) throws SQLException {
					GrupoContrato grupo = new GrupoContrato();
					grupo.setNroGrupo(rs.getInt("nroGrupo"));
					grupo.setTipoTitulo(rs.getString("tipoTitulo"));
					grupo.setNombreGrupo(rs.getString("nombreGrupo"));
					grupo.setImporteGrupo(rs.getBigDecimal("importeGrupo"));
					grupo.setEstrenoRepeticion(rs.getString("estRep"));
					grupo.setSenialHeredada(rs.getString("senialHeredada"));
					return grupo;
				}
			});
	}
	private SimpleJdbcCall buildGetTipoCosteoByGrupoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_COSTEOS).withFunctionName("getTipoCosteoByGrupo");
	}
	
	private SimpleJdbcCall buildDesenlaceContratoGrupoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_2).withProcedureName("PR_09VALIDADESENLACE")
			.returningResultSet("P_NVECTORERROR", new RowMapper<Object>() {
                @Override
                public ErrorDesenlaceResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                	if ((rs.getString("tipoError") == null || StringUtils.EMPTY.equals(rs.getString("tipoError").trim()))) {
                		return null;
                	} else {
	                	ErrorDesenlaceResponse response = new ErrorDesenlaceResponse();
	                	response.setTipoError(rs.getString("tipoError"));
	                	response.setDescripcion(rs.getString("descripcion"));
	                    return response;
                	}
                }
            });
	}
	
	private SimpleJdbcCall buildRecuperarDatosGrupoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_1).withProcedureName("PR_07GRUPO")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
				@Override
				public RecuperarDatosGrupoResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
					RecuperarDatosGrupoResponse grupo = new RecuperarDatosGrupoResponse();
					grupo.setTipoDerecho(rs.getString("CodComDer"));
					grupo.setDerechoTerrirorial(rs.getString("CodDerTerr"));
					grupo.setTipoImporte(rs.getString("TipoImporte"));
					grupo.setNombreGrupo(rs.getString("Nombre"));
					grupo.setMarcaPerpetuidad(rs.getString("MarcaPerp"));
					grupo.setEstrenoRepeticion(rs.getString("EstRep"));
					grupo.setAutorizacionAutor(rs.getString("AutorizacionAutor"));
					grupo.setFechaComienzoDerechos(rs.getDate("FechaComDer"));
					grupo.setCantidadTiempo(rs.getInt("CantTiempo"));
					grupo.setUnidadTiempo(rs.getString("unidadTiempo"));
					grupo.setPlanEntrega(rs.getString("PlanEntrega"));
					grupo.setPasaLibreria(rs.getString("PasaLibreria"));
					grupo.setFechaComienzoDerechosLibreria(rs.getDate("FechaComDerLib"));
					grupo.setCantidadTiempoExclusividad(rs.getInt("CantTiempoExcl"));
					grupo.setUnidadTiempoExclusividad(rs.getString("unidadTiempoExcl"));
					grupo.setPagaArgentores(rs.getString("PagaArgentores"));
					/* Con capítulos */ 
					grupo.setCantidadOriginales(rs.getInt("CantOriginales"));
					grupo.setCantidadRepeticiones(rs.getInt("CantRepeticiones"));
					grupo.setCostoTotal(rs.getBigDecimal("CostoTotal"));
					grupo.setFechaPosibleEntrega(rs.getDate("FechaPosEntr"));
					grupo.setRecontratacion(rs.getString("Recontratacion"));
					/* Sin capítulos */
					grupo.setCantidadTitulos(rs.getInt("CantTitulos"));
					grupo.setCantidadPasadasContratadas(rs.getInt("CantPasadas"));
					grupo.setCostoUnitario(rs.getBigDecimal("CostoUnitario"));
					return grupo;
				}
			});
	}
		
	private SimpleJdbcCall buildEliminarGrupoContratoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_2).withProcedureName("PR_09BORRGRU")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("msjError");
				}
			});
	}
	
	private SimpleJdbcCall buildBuscarTitulosGrupoCastellanoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_2).withProcedureName("PR_10CARGASU")
            .returningResultSet("P_CURSOR", new RowMapper<Object>() {
            	@Override
                public TituloGrupo mapRow(ResultSet rs, int rowNum) throws SQLException {
            		TituloGrupo tituloGrupo = new TituloGrupo();
            		tituloGrupo.setClave(rs.getString("clave"));
            		tituloGrupo.setTituloCastellano(rs.getString("tituloCastellano"));
            		tituloGrupo.setTituloOriginal(rs.getString("tituloOriginal"));
            		tituloGrupo.setPrimerActor(rs.getString("actor_1"));
            		tituloGrupo.setSegundoActor(rs.getString("actor_2"));
            		tituloGrupo.setSinContrato(rs.getString("c10SinCont"));
                    return tituloGrupo;
                }
            });				
	}
	
	private SimpleJdbcCall buildBuscarTitulosGrupoOriginalCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_2).withProcedureName("PR_11CARGASU")
            .returningResultSet("P_CURSOR", new RowMapper<Object>() {
            	@Override
                public TituloGrupo mapRow(ResultSet rs, int rowNum) throws SQLException {
            		TituloGrupo tituloGrupo = new TituloGrupo();
            		tituloGrupo.setClave(rs.getString("clave"));
            		tituloGrupo.setTituloCastellano(rs.getString("tituloCastellano"));
            		tituloGrupo.setTituloOriginal(rs.getString("tituloOriginal"));
            		tituloGrupo.setPrimerActor(rs.getString("actor_1"));
            		tituloGrupo.setSegundoActor(rs.getString("actor_2"));
            		tituloGrupo.setSinContrato(rs.getString("c10SinCont"));
                    return tituloGrupo;
                }
            });					
	}
	
	private SimpleJdbcCall buildValidarPerpetuidadTituloCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_2).withProcedureName("PR_VALIDARPERPTITULO");
	}
	
	private SimpleJdbcCall buidObtenerNuevaClaveTituloCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName("fil_pkg_titulos").withFunctionName("FN_NEXT_NUM_TITLE");
	}
	
	private SimpleJdbcCall buildBuscarCalificacionesOficialesCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_2).withProcedureName("PR_12LISTARCALIFOFICIAL")
            .returningResultSet("P_CURSOR", new RowMapper<Object>() {
            	@Override
                public CalificacionOficial mapRow(ResultSet rs, int rowNum) throws SQLException {
            		CalificacionOficial calificacionOficial = new CalificacionOficial();
            		calificacionOficial.setCalificacion(rs.getString("calificacion"));
            		calificacionOficial.setDescripcion(rs.getString("descripcion"));
                    return calificacionOficial;
                }
            });				
	}
	
	private SimpleJdbcCall buildValidacionExistenciaTituloCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_2).withProcedureName("PR_12VALIDAEXISTETITULO")
            .returningResultSet("P_NVECTORERROR", new RowMapper<Object>() {
            	@Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString("mensaje");
                }
            });				
	}

	private SimpleJdbcCall buildValidacionRecepcionMasterCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_2).withProcedureName("PR_12VALIDARECEPCIONMASTER")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
                @Override
                public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                	boolean sonDatosSoporte = rs.getMetaData().getColumnCount() == 2;
                	
                	if (sonDatosSoporte) {
                		DatosSoporteRecepcionMaster soporte = new DatosSoporteRecepcionMaster();
                		soporte.setSoporte(rs.getString("soporte"));
            			soporte.setFechaCopia(rs.getDate("fechaCopia"));
            			return soporte;
                	} else {
                		DatosRemitoRecepcionMaster remito = new DatosRemitoRecepcionMaster();
                		remito.setNroRemito(rs.getInt("nroRemito"));
                		remito.setNroGuia(rs.getString("nroGuia"));
                		remito.setChequeo(rs.getString("descripcion"));
                		remito.setFechaIngreso(rs.getDate("fechaIngreso"));
                		remito.setFechaRtoGuia(rs.getDate("fechaRtoGuia"));
                		remito.setSoporte(StringUtils.EMPTY);
    					try {
    						remito.setFechaCopia(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/0001"));	
    					} catch (ParseException e) {
    						e.printStackTrace();
    					}
    					return remito;
                	}
                    
                }
            })
			.returningResultSet("P_NVECTORERROR", new RowMapper<Object>() {
	            @Override
	            public ErrorValidacionRecepcionMaster mapRow(ResultSet rs, int rowNum) throws SQLException {
	            	ErrorValidacionRecepcionMaster response = new ErrorValidacionRecepcionMaster();
	            	response.setTipoError(rs.getString("tipoError"));
	            	response.setDescripcion(rs.getString("descripcion"));
	                return response;
	            }
			});
	}
	
	private SimpleJdbcCall buildConfirmarSeleccionTituloCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_2).withProcedureName("PR_12FIN")
			.returningResultSet("P_CURSORERRORES", new RowMapper<Object>() {
                @Override
                public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
                	Map<String, String> error = new HashMap<String, String>();
                	error.put(rs.getString("tipoError"), rs.getString("descripcionError"));
                    return error;
                }
            });
	}
	
	private SimpleJdbcCall buildBuscarTitulosRecontratacionCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_2).withProcedureName("PR_13CARGA")
			.returningResultSet("P_CURSORERRORES", new RowMapper<Object>() {
				@Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString("mensaje");
                }
			})
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
                @Override
                public TituloRecontratacion mapRow(ResultSet rs, int rowNum) throws SQLException {
                	TituloRecontratacion tituloRecontratacion = new TituloRecontratacion();
                	tituloRecontratacion.setClave(rs.getString("clave"));
                	tituloRecontratacion.setTitulo(rs.getString("titulo"));
                	tituloRecontratacion.setContrato(rs.getInt("contrato"));
                	tituloRecontratacion.setGrupo(rs.getInt("grupo"));
                	tituloRecontratacion.setFormula(rs.getString("formula"));
                	tituloRecontratacion.setCapitulos(rs.getInt("cap_recibidos"));
                    return tituloRecontratacion;
                }
            });
	}
	
	private SimpleJdbcCall buildValidarRemitoSNCCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_2).withProcedureName("PR_13VALIDARTOSNC")
            .returningResultSet("P_CURSOR", new RowMapper<Object>() {
            	@Override
                public ValidarRemitoSNCResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
	        		if (BigDecimal.ZERO.intValue() == rs.getInt("nroRemito")) {
	            		return null;
	            	} else {
	            		ValidarRemitoSNCResponse response = new ValidarRemitoSNCResponse();
	            		response.setCapitulo(rs.getInt("capitulo"));
	            	    response.setParte(rs.getInt("parte"));
	            	    response.setNroRemito(rs.getString("nroRemito"));
	            	    response.setNroGuia(rs.getString("nroGuia"));
	            	    response.setFechaIngreso(rs.getDate("fechaIngreso"));
	            	    response.setFechaRemito(rs.getDate("fechaRemito"));
	            	    response.setChequeoVolver(rs.getString("chequeoVolver"));
	            		return response;
	            	}
                }
            });					
	}

	private SimpleJdbcCall buildValidarMasterTituloCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_2).withProcedureName("PR_13VALIDAMASTERTITULO")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
            	@Override
                public ValidarMasterTituloResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            		ValidarMasterTituloResponse response = new ValidarMasterTituloResponse();
            		response.setSoporte(rs.getString("soporte"));
            		response.setFechaCopia(rs.getDate("fechaCopia"));
            		return response;
                }
            });					
	}

	private SimpleJdbcCall buildConfirmarSeleccionCapitulosTituloRecontratacionCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_2).withProcedureName("PR_13CONF");
	}
	
	private SimpleJdbcCall buildBuscarCapitulosTituloRecontratacion(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_2).withProcedureName("PR_14CARGA")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
            	@Override
                public CapituloTituloRecontratacion mapRow(ResultSet rs, int rowNum) throws SQLException {
            		CapituloTituloRecontratacion capitulo = new CapituloTituloRecontratacion();
            		capitulo.setNumeroCapitulo(rs.getInt("nroCapitulo"));
            		capitulo.setParte(rs.getInt("parte"));
            		capitulo.setTituloCapitulo(rs.getString("tituloCapitulo"));
            		capitulo.setSinContrato(rs.getString("sinContrato"));
            		return capitulo;
                }
            });					
	}
	
	private SimpleJdbcCall buildValidarRemitoSNCCapituloRecontratacionCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_2).withProcedureName("PR_14VALIDARTOSNC")
            .returningResultSet("P_CURSOR", new RowMapper<Object>() {
	        	@Override
	            public ValidarRemitoSNCCapituloRecontratacionResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
	        		if (BigDecimal.ZERO.intValue() == rs.getInt("nroRemito")) {
	            		return null;
	            	} else {
	            		ValidarRemitoSNCCapituloRecontratacionResponse response = new ValidarRemitoSNCCapituloRecontratacionResponse();
		        		response.setCapitulo(rs.getInt("capitulo"));
		        	    response.setParte(rs.getInt("parte"));
		        	    response.setNroRemito(rs.getString("nroRemito"));
		        	    response.setNroGuia(rs.getString("nroGuia"));
		        	    response.setFechaIngreso(rs.getDate("fechaIngreso"));
		        	    response.setFechaRemito(rs.getDate("fechaRemito"));
		        	    response.setChequeoVolver(rs.getString("chequeoVolver"));
		        		return response;
	            	}
	            }
	        });					
	}
	
	private SimpleJdbcCall buildValidarRemitoNoSNCCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_2).withProcedureName("PR_14VALIDARTONOSNC")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
            	@Override
                public ValidarRemitoNoSNCResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            		ValidarRemitoNoSNCResponse response = new ValidarRemitoNoSNCResponse();
            		response.setSoporte(rs.getString("soporte"));
            		response.setFechaCopia(rs.getDate("fechaCopia"));
            		return response;
                }
            });						
	}
	
	private SimpleJdbcCall buildConfirmarSeleccionCapituloTituloRecontratacionCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_2).withProcedureName("PR_14CONF");
	}
	
	private SimpleJdbcCall buildContratosEnlazadosTituloRecontratacionCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_3).withProcedureName("PR_15CARGA")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
				@Override
                public ContratoEnlazadoTituloRecontratacion	mapRow(ResultSet rs, int rowNum) throws SQLException {
					ContratoEnlazadoTituloRecontratacion contrato = new ContratoEnlazadoTituloRecontratacion();
					contrato.setContrato(rs.getInt("contrato"));
					contrato.setGrupo(rs.getInt("grupo"));
					contrato.setDistribuidor(rs.getString("distribuidor"));
					contrato.setVigenciaDesde(rs.getDate("vigenciaDesde"));
					contrato.setVigenciaHasta(rs.getDate("vigenciaHasta"));
					contrato.setEnlazadoAnterior(rs.getInt("enlazadoAnterior"));
					contrato.setGrupoAnterior(rs.getInt("grupoAnterior"));
					contrato.setEnlazadoPosterior(rs.getInt("enlazadoPosterior"));
					contrato.setGrupoPosterior(rs.getInt("grupoPosterior"));
					contrato.setModVigencia(rs.getString("modVigencia"));
					return contrato;
				}
            });
	}
	
	private SimpleJdbcCall buildValidarDesenlaceTituloContratoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_3).withProcedureName("PR_15VALIDADESENLACE")
			.returningResultSet("P_ERR_DESENLACE", new RowMapper<Object>() {
                @Override
                public ErrorDesenlaceResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                	if ((rs.getString("tipoError") == null || StringUtils.EMPTY.equals(rs.getString("tipoError").trim()))) {
                		return null;
                	} else {
	                	ErrorDesenlaceResponse response = new ErrorDesenlaceResponse();
	                	response.setTipoError(rs.getString("tipoError"));
	                	response.setDescripcion(rs.getString("descripcion"));
		                response.setContrato(rs.getInt("contrato"));
		                response.setGrupo(rs.getInt("grupo"));
	                	response.setSenial(rs.getString("senial"));
	                	response.setTipoTitulo(rs.getString("tipoTitulo"));
		                response.setNroTitulo(rs.getInt("nroTitulo"));
		                response.setFechaDesde(rs.getDate("fechaDesde"));
	                	response.setTipoListado(rs.getString("tipoListado"));
	                    return response;
                	}
                }
            });
	}
	
	private SimpleJdbcCall buildEjecutarDesenlaceTituloContratoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_3).withProcedureName("PR_15DESENLACE")
			.returningResultSet("P_ERR_DESENLACE", new RowMapper<Object>() {
                @Override
                public ErrorDesenlaceResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                	if ((rs.getString("tipoError") == null || StringUtils.EMPTY.equals(rs.getString("tipoError").trim()))) {
                		return null;
                	} else {
	                	ErrorDesenlaceResponse response = new ErrorDesenlaceResponse();
	                	response.setTipoError(rs.getString("tipoError"));
	                	response.setDescripcion(rs.getString("descripcion"));
		                response.setContrato(rs.getInt("contrato"));
		                response.setGrupo(rs.getInt("grupo"));
	                	response.setSenial(rs.getString("senial"));
	                	response.setTipoTitulo(rs.getString("tipoTitulo"));
		                response.setNroTitulo(rs.getInt("nroTitulo"));
		                response.setFechaDesde(rs.getDate("fechaDesde"));
	                	response.setTipoListado(rs.getString("tipoListado"));
	                    return response;
                	}
                }
            });
	}
	
	private SimpleJdbcCall buildValidarEnlacePosteriorCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_3).withProcedureName("PR_15VALIDAENLACEPOST")
			.returningResultSet("P_ERRORES", new RowMapper<Object>() {
                @Override
                public ErrorDesenlaceResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                	if ((rs.getString("tipo") == null || StringUtils.EMPTY.equals(rs.getString("tipo").trim()))) {
                		return null;
                	} else {
	                	ErrorDesenlaceResponse response = new ErrorDesenlaceResponse();
	                	response.setTipoError(rs.getString("tipo"));
	                	response.setDescripcion(rs.getString("descripcion"));
	                    return response;
                	}
                }
            });
	}

	private SimpleJdbcCall buildEjecutarEnlacePosteriorCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_3).withProcedureName("PR_15ENLACEPOST")
			.returningResultSet("P_NVECTORERROR", new RowMapper<Object>() {
                @Override
                public ErrorDesenlaceResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                	if ((rs.getString("tipoError") == null || StringUtils.EMPTY.equals(rs.getString("tipoError").trim()))) {
                		return null;
                	} else {
	                	ErrorDesenlaceResponse response = new ErrorDesenlaceResponse();
	                	response.setTipoError(rs.getString("tipoError"));
	                	response.setDescripcion(rs.getString("descripcion"));
		                response.setContrato(rs.getInt("contrato"));
		                response.setGrupo(rs.getInt("grupo"));
	                	response.setSenial(rs.getString("senial"));
	                	response.setTipoTitulo(rs.getString("tipoTitulo"));
		                response.setNroTitulo(rs.getInt("nroTitulo"));
		                response.setFechaDesde(rs.getDate("fechaDesde"));
	                	response.setTipoListado(rs.getString("tipoListado"));
	                    return response;
                	}
                }
            })
			.returningResultSet("P_NVECTORVIGENCIAS", new RowMapper<Object>() {
                @Override
                public ErrorVigenciaResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                	if (BigDecimal.ZERO.toString().equals(rs.getString("clave"))) {
                		return null;
                	} else {
	                	ErrorVigenciaResponse response = new ErrorVigenciaResponse();
			            response.setContrato(rs.getInt("contrato"));
			            response.setGrupo(rs.getInt("grupo"));
	                	response.setComienzo(rs.getDate("vigencia_desde"));
	                	response.setFin(rs.getDate("vigencia_hasta"));
	                	response.setClave(rs.getString("clave"));
	                    return response;
                	}
                }
            });
	}
	
	private SimpleJdbcCall buildVisualizarModificacionesVigenciaCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_3).withProcedureName("PR_16CARGA")
            .returningResultSet("P_CURSOR", new RowMapper<Object>() {
            	@Override
                public ModificacionVigencia mapRow(ResultSet rs, int rowNum) throws SQLException {
            		ModificacionVigencia modificacionVigencia = new ModificacionVigencia();
            		modificacionVigencia.setTipoVigencia(rs.getString("tipoVigencia"));
            		modificacionVigencia.setDescripcionVigencia(rs.getString("descripcionVigencia"));
            		modificacionVigencia.setFechaDesdePayTv(rs.getDate("fechaDesdePayTv"));
					modificacionVigencia.setFechaHastaPayTv(rs.getDate("fechaHastaPayTv"));
					modificacionVigencia.setFechaDesdeAnterior(rs.getDate("fechaDesdeAnterior"));
					modificacionVigencia.setFechaHastaAnterior(rs.getDate("fechaHastaAnterior"));
					modificacionVigencia.setFechaDesdeNueva(rs.getDate("fechaDesdeNueva"));
					modificacionVigencia.setFechaHastaNueva(rs.getDate("fechaHastaNueva"));
                    return modificacionVigencia;
                }
            });					
	}
	
	private SimpleJdbcCall buildBuscarCapitulosEliminacionCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_3).withProcedureName("PR_18CARGASFL")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
	        	@Override
	            public CapituloTituloRecontratacion mapRow(ResultSet rs, int rowNum) throws SQLException {
	        		CapituloTituloRecontratacion capitulo = new CapituloTituloRecontratacion();
	        		capitulo.setClave(rs.getString("clave"));
	        		capitulo.setNumeroCapitulo(rs.getInt("nroCapitulo"));
	        		capitulo.setParte(rs.getInt("nroParte"));
	        		capitulo.setTituloCapitulo(rs.getString("tituloCapitulo"));
	        		return capitulo;
	            }
	        });					
	}
	
	private SimpleJdbcCall buildValidarCantidadCapitulosBajaCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_3).withProcedureName("PR_18VALIDACANTCAP")
            .returningResultSet("P_CURSOR", new RowMapper<Object>() {
            	@Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString("mensaje");
                }
            });				
	}
	
	private SimpleJdbcCall buildPrimerValidacionRerunBajaCapitulosCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_3).withProcedureName("PR_18VALIDACIONRERUN01")
            .returningResultSet("P_CURSOR", new RowMapper<Object>() {
            	@Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString("mensaje");
                }
            });				
	}
		
	private SimpleJdbcCall buildSegundaValidacionRerunBajaCapitulosCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_3).withProcedureName("PR_18VALIDACIONRERUN02")
			.returningResultSet("P_ERR_DESENLACE", new RowMapper<Object>() {
                @Override
                public ErrorDesenlaceResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                	if ((rs.getString("tipoError") == null || StringUtils.EMPTY.equals(rs.getString("tipoError").trim()))) {
                		return null;
                	} else {
	                	ErrorDesenlaceResponse response = new ErrorDesenlaceResponse();
	                	response.setTipoError(rs.getString("tipoError"));
	                	response.setDescripcion(rs.getString("descripcion"));
		                response.setContrato(rs.getInt("contrato"));
		                response.setGrupo(rs.getInt("grupo"));
	                	response.setSenial(rs.getString("senial"));
	                	response.setTipoTitulo(rs.getString("tipoTitulo"));
		                response.setNroTitulo(rs.getInt("nroTitulo"));
		                response.setFechaDesde(rs.getDate("fechaDesde"));
	                	response.setTipoListado(rs.getString("tipoListado"));
	                    return response;
                	}
                }
            })
			.returningResultSet("P_VIGENCIAS", new RowMapper<Object>() {
                @Override
                public ErrorVigenciaResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                	if (BigDecimal.ZERO.toString().equals(rs.getString("clave"))) {
                		return null;
                	} else {
	                	ErrorVigenciaResponse response = new ErrorVigenciaResponse();
			            response.setContrato(rs.getInt("contrato"));
			            response.setGrupo(rs.getInt("grupo"));
	                	response.setComienzo(rs.getDate("comienzo"));
	                	response.setFin(rs.getDate("fin"));
	                	response.setClave(rs.getString("clave"));
	                    return response;
                	}
                }
            });
	}
	
	private SimpleJdbcCall buildConfirmarBajaCapitulosCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_3).withProcedureName("PR_18CONF");
	}
	
	private SimpleJdbcCall buildBuscarCapitulosAdicionCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_3).withProcedureName("PR_19CARGASFL")
			.returningResultSet("P_CURSOR", new RowMapper<Object>() {
	        	@Override
	            public CapituloTituloRecontratacion mapRow(ResultSet rs, int rowNum) throws SQLException {
	        		CapituloTituloRecontratacion capitulo = new CapituloTituloRecontratacion();
	        		capitulo.setClave(rs.getString("clave"));
	        		capitulo.setNumeroCapitulo(rs.getInt("nroCapitulo"));
	        		capitulo.setParte(rs.getInt("nroParte"));
	        		capitulo.setTituloCapitulo(rs.getString("tituloCapitulo"));
	        		return capitulo;
	            }
	        });	
	}
	
	private SimpleJdbcCall buildValidarRemitoCapitulosSNCCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_3).withProcedureName("PR_19VALIDARTOCAPSNC")
            .returningResultSet("P_CURSOR", new RowMapper<Object>() {
	        	@Override
	            public ValidarRemitoSNCCapituloRecontratacionResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
	        		ValidarRemitoSNCCapituloRecontratacionResponse response = new ValidarRemitoSNCCapituloRecontratacionResponse();
	        		response.setCapitulo(rs.getInt("capitulo"));
	        	    response.setParte(rs.getInt("parte"));
	        	    response.setNroRemito(rs.getString("nroRemito"));
	        	    response.setNroGuia(rs.getString("nroGuia"));
	        	    response.setFechaIngreso(rs.getDate("fechaIngreso"));
	        	    response.setFechaRemito(rs.getDate("fechaRemito"));
	        	    response.setChequeoVolver(rs.getString("chequeoVolver"));
	        		return response;
	            }
	        });					
	}
	
	private SimpleJdbcCall buildConfirmarAltaCapitulosCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_3).withProcedureName("PR_19CONF")
			.returningResultSet("P_CURSORMASTER", new RowMapper<Object>() {
            	@Override
                public DatosMaster mapRow(ResultSet rs, int rowNum) throws SQLException {
            		DatosMaster response = new DatosMaster();
            		response.setSoporte(rs.getString("soporte"));
            		response.setFechaCopia(rs.getDate("fechaCopia"));
            		return response;
                }
            })
			.returningResultSet("P_CURSORERROR", new RowMapper<Object>() {
	        	@Override
	            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
	                return rs.getString("mensaje");
	            }
			});
	}
	
	private SimpleJdbcCall buildConfirmarAltaCapitulosAutomaticoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_3).withProcedureName("PR_19CONFAUTOMATICO")
			.returningResultSet("P_CURSORMASTER", new RowMapper<Object>() {
            	@Override
                public DatosMaster mapRow(ResultSet rs, int rowNum) throws SQLException {
            		DatosMaster response = new DatosMaster();
            		response.setSoporte(rs.getString("soporte"));
            		response.setFechaCopia(rs.getDate("fechaCopia"));
            		return response;
                }
            })
			.returningResultSet("P_CURSORERROR", new RowMapper<Object>() {
	        	@Override
	            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
	                return rs.getString("mensaje");
	            }
			});
	}
	
	private SimpleJdbcCall buildBuscarTitulosEliminarContratoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_3).withProcedureName("PR_20CARGASFL")
            .returningResultSet("P_CURSOR", new RowMapper<Object>() {
            	@Override
                public TituloEliminarContrato mapRow(ResultSet rs, int rowNum) throws SQLException {
            		TituloEliminarContrato titulo = new TituloEliminarContrato();
            		titulo.setClave(rs.getString("clave"));
					titulo.setTituloCastellano(rs.getString("tituloCastellano"));
					titulo.setProg(rs.getString("Prog"));
					titulo.setConfExh(rs.getString("ConfExhib"));
					titulo.setaCons(rs.getString("aCons"));
					titulo.setRerun(rs.getString("reRun"));
                    return titulo;
                }
            });	
	}
	
	private SimpleJdbcCall buildValidarEliminacionTituloContratoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_3).withProcedureName("PR_20VALIDA")
			.returningResultSet("P_ERR_DESENLACE", new RowMapper<Object>() {
                @Override
                public ErrorDesenlaceResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                	if ((rs.getString("tipoError") == null || StringUtils.EMPTY.equals(rs.getString("tipoError").trim()))) {
                		return null;
                	} else {
	                	ErrorDesenlaceResponse response = new ErrorDesenlaceResponse();
	                	response.setTipoError(rs.getString("tipoError"));
	                	response.setDescripcion(rs.getString("descripcion"));
		                response.setContrato(rs.getInt("contrato"));
		                response.setGrupo(rs.getInt("grupo"));
	                	response.setSenial(rs.getString("senial"));
	                	response.setTipoTitulo(rs.getString("tipoTitulo"));
		                response.setNroTitulo(rs.getInt("nroTitulo"));
		                response.setFechaDesde(rs.getDate("fechaDesde"));
	                	response.setTipoListado(rs.getString("tipoListado"));
	                    return response;
                	}
                }
            });
	}
	
	private SimpleJdbcCall buildConfirmarEliminacionTituloContratoCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_3).withProcedureName("PR_20CONF")
			.returningResultSet("P_ERR_DESENLACE", new RowMapper<Object>() {
                @Override
                public ErrorDesenlaceResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                	if ((rs.getString("tipoError") == null || StringUtils.EMPTY.equals(rs.getString("tipoError").trim()))) {
                		return null;
                	} else {
	                	ErrorDesenlaceResponse response = new ErrorDesenlaceResponse();
	                	response.setTipoError(rs.getString("tipoError"));
	                	response.setDescripcion(rs.getString("descripcion"));
		                response.setContrato(rs.getInt("contrato"));
		                response.setGrupo(rs.getInt("grupo"));
	                	response.setSenial(rs.getString("senial"));
	                	response.setTipoTitulo(rs.getString("tipoTitulo"));
		                response.setNroTitulo(rs.getInt("nroTitulo"));
		                response.setFechaDesde(rs.getDate("fechaDesde"));
	                	response.setTipoListado(rs.getString("tipoListado"));
	                    return response;
                	}
                }
            });
	}
	
	private SimpleJdbcCall buildBuscarTitulosGrupoReadOnlyCall(DataSource dataSource) {
		return new SimpleJdbcCall(dataSource).withCatalogName(PACKAGE_NAME_ALTA_CONTRATOS_3).withProcedureName("PR_21CARGASU")
            .returningResultSet("P_CURSOR", new RowMapper<Object>() {
            	@Override
                public TituloGrupo mapRow(ResultSet rs, int rowNum) throws SQLException {
            		TituloGrupo tituloGrupo = new TituloGrupo();
            		tituloGrupo.setClave(rs.getString("clave"));
            		tituloGrupo.setTituloOriginal(rs.getString("tituloOriginal"));
            		tituloGrupo.setPrimerActor(rs.getString("actor_1"));
            		tituloGrupo.setSegundoActor(rs.getString("actor_2"));
                    return tituloGrupo;
                }
            });					
	}

	/***********************************************************************************		
	 ********************				  Validator 				********************
	 ***********************************************************************************/
	@SuppressWarnings("unchecked")
	private boolean validarCursorErrores(Object errores) {
		return ((List<Object>) errores) != null && ((List<Object>) errores).size() > 0 && ((List<Object>) errores).get(0) != null;
	}
	
	/***********************************************************************************		
	 ********************				  Procedures				********************
	 ***********************************************************************************/
	@SuppressWarnings("unchecked")
	public List<Distribuidor> buscarDistribuidores(String descripcion) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_DESCRIP", descripcion);
		
		Map<String, Object> out = this.buscarDistribuidoresCall.execute(in);
		return (List<Distribuidor>) out.get("P_DATOS");
	}
	
	@SuppressWarnings("unchecked")
	public List<Moneda> buscarMonedas(String descripcion) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_DESCRIP", descripcion);
		
		Map<String, Object> out = this.buscarMonedasCall.execute(in);
		return (List<Moneda>) out.get("P_DATOS");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoTitulo> buscarTiposTitulo() {
		Map<String, Object> out = this.buscarTiposTituloCall.execute();
		return (List<TipoTitulo>) out.get("P_DATOS");
	}
	
	@Override
	public GuardarDatosCabeceraContratoResponse guardarDatosCabeceraContrato(GuardarDatosCabeceraContratoRequest guardarDatosCabeceraContratoRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_PROVEEDOR", guardarDatosCabeceraContratoRequest.getProveedor())
														   .addValue("P_MONEDA", guardarDatosCabeceraContratoRequest.getMoneda())
														   .addValue("P_FEC_CONTRATO", guardarDatosCabeceraContratoRequest.getFechaContrato())
														   .addValue("P_FEC_CONTRATO_APR", guardarDatosCabeceraContratoRequest.getFechaAprobacion())
														   .addValue("P_MONTO_TOTAL", guardarDatosCabeceraContratoRequest.getMontoTotal())
														   .addValue("P_CANJE", guardarDatosCabeceraContratoRequest.getCanje())
														   .addValue("P_USUARIO", "DFLCLM")
														   .addValue("P_OBSERVACIONES", guardarDatosCabeceraContratoRequest.getObservaciones());
		
		Map<String, Object> out = this.guardarDatosCabeceraContratoCall.execute(in);

		GuardarDatosCabeceraContratoResponse response = new GuardarDatosCabeceraContratoResponse();
		response.setContrato(((BigDecimal) out.get("P_CONTRATO")).intValue());
		response.setResultado("S".equals((String) out.get("P_OK")));
		
		return response;
	}

	@Override
	public Boolean guardarContratoSenialImporte(GuardarContratoSenialImporteRequest guardarContratoSenialImporteRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", guardarContratoSenialImporteRequest.getContrato())
														   .addValue("P_SENIAL", guardarContratoSenialImporteRequest.getSenial())
														   .addValue("P_MONTO", guardarContratoSenialImporteRequest.getImporte())
														   .addValue("P_USUARIO", "DFLCLM");
														   
		Map<String, Object> out = this.guardarContratoSenialImporteCall.execute(in);
		return "S".equals((String) out.get("P_OK"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SenialImporte> buscarSenialesImportes(BuscarSenialesImportesRequest buscarSenialesImportesRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", buscarSenialesImportesRequest.getContrato());
		
		Map<String, Object> out = this.buscarSenialesImportesCall.execute(in);
		return (List<SenialImporte>) out.get("P_DATOS");
	}

	@Override
	public Boolean validarExistenciaGrupos(ValidarExistenciaGruposRequest validarExistenciaGruposRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", validarExistenciaGruposRequest.getContrato())
														   .addValue("P_SENIAL", validarExistenciaGruposRequest.getSenial());
		
		Map<String, Object> out = this.validarExistenciaGruposCall.execute(in);
		return "S".equals((String) out.get("P_GRUPOS"));
	}

	@Override
	public Boolean modificarContratoSenialImporte(GuardarContratoSenialImporteRequest guardarContratoSenialImporteRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", guardarContratoSenialImporteRequest.getContrato())
						   								   .addValue("P_SENIAL", guardarContratoSenialImporteRequest.getSenial())
						   								   .addValue("P_MONTO", guardarContratoSenialImporteRequest.getImporte());
						   
		Map<String, Object> out = this.modificarContratoSenialImporteCall.execute(in);
		return "S".equals((String) out.get("P_OK"));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DesenlaceContratoSenialResponse desenlaceContratoSenial(DesenlaceContratoSenialRequest desenlaceContratoSenialRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", desenlaceContratoSenialRequest.getContrato())
														   .addValue("P_SENIAL", desenlaceContratoSenialRequest.getSenial())
														   .addValue("P_GRUPO", desenlaceContratoSenialRequest.getGrupo())
														   .addValue("P_TIPO_TITULO", desenlaceContratoSenialRequest.getTipoTitulo())
														   .addValue("P_IDREPORTE", 0)
														   .addValue("P_NRO_TITULO", desenlaceContratoSenialRequest.getNroTitulo());
														   
		Map<String, Object> out = this.desenlaceContratoSenialCall.execute(in);
		
		DesenlaceContratoSenialResponse response = new DesenlaceContratoSenialResponse();
		response.setError((String) out.get("P_ERROR"));
		if (this.validarCursorErrores(out.get("P_ERR_DESENLACE"))) {
			response.setErrores((List<ErrorDesenlaceResponse>) out.get("P_ERR_DESENLACE"));
		}
		response.setIdReporte(((BigDecimal) out.get("P_IDREPORTE")).intValue());
		return response;
	}

	@Override
	public Boolean eliminarMontoSenial(EliminarMontoSenialRequest eliminarMontoSenialRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", eliminarMontoSenialRequest.getContrato())
														   .addValue("P_SENIAL", eliminarMontoSenialRequest.getSenial())
														   .addValue("P_USUARIO", "DFLCLM");
		
		Map<String, Object> out = this.eliminarMontoSenialCall.execute(in);
		return "S".equals((String) out.get("P_OK"));
	}

	@Override
	public ValidarSumaGruposResponse validarSumaGrupos(ValidarSumaGruposRequest validarSumaGruposRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", validarSumaGruposRequest.getContrato());
		
		Map<String, Object> out = this.validarSumaGruposCall.execute(in);
		
		ValidarSumaGruposResponse response = new ValidarSumaGruposResponse();
		response.setExito("S".equals((String) out.get("P_OK")));
		response.setMensaje((String) out.get("P_MSJ"));
		return response;
	}

	@Override
	public Boolean actualizarCabeceraEliminada(ActualizarCabeceraEliminadaRequest actualizarCabeceraEliminadaRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", actualizarCabeceraEliminadaRequest.getContrato());
		
		Map<String, Object> out = this.actualizarCabeceraEliminadaCall.execute(in);
		return "S".equals((String) out.get("P_OK"));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ConfirmarSenialesContratoResponse confirmarSenialesContrato(ConfirmarSenialesContratoRequest confirmarSenialesContratoRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", confirmarSenialesContratoRequest.getContrato());		
		
		Map<String, Object> out = this.confirmarSenialesContratoCall.execute(in);
		
		ConfirmarSenialesContratoResponse response = new ConfirmarSenialesContratoResponse();
		response = ((List<ConfirmarSenialesContratoResponse>) out.get("P_CURSOR")).get(0);
		
		return response;
	}
	
	@SuppressWarnings("unused")
    @Override
	public ConfirmarSenialesContratoResponse modificarCabeceraContrato(ConfirmarSenialesContratoRequest confirmarSenialesContratoRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", confirmarSenialesContratoRequest.getContrato());		
		
		Map<String, Object> out = this.modificarCabeceraContratoCall.execute(in);
		
		ConfirmarSenialesContratoResponse response = new ConfirmarSenialesContratoResponse();
	
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoImporte> buscarTiposImporte() {
		Map<String, Object> out = this.buscarTiposImporteCall.execute();
		return (List<TipoImporte>) out.get("P_DATOS");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoDerecho> buscarTiposDerecho() {
		Map<String, Object> out = this.buscarTiposDerechoCall.execute();
		return (List<TipoDerecho>) out.get("P_DATOS");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoDerechoTerritorial> buscarTiposDerechoTerritorial() {
		Map<String, Object> out = this.buscarTiposDerechoTerritorialCall.execute();
		return (List<TipoDerechoTerritorial>) out.get("P_DATOS");
	}

	@Override
	public Integer buscarNumeroGrupo(BuscarNumeroGrupoRequest buscarNumeroGrupoRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", buscarNumeroGrupoRequest.getContrato())
														   .addValue("P_SENIAL", buscarNumeroGrupoRequest.getSenial());
		
		Map<String, Object> out = this.buscarNumeroGrupoCall.execute(in);
		return ((BigDecimal) out.get("P_GRUPO")).intValue();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String buscarSenialHeredadaCC(BuscarSenialesHeredadasRequest buscarSenialesHeredadasRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_SENIAL", buscarSenialesHeredadasRequest.getSenial());

		Map<String, Object> out = this.buscarSenialHeredadaCCCall.execute(in);
		List<String> senialesHeredadas = ((List<String>) out.get("P_DATOS"));
		if (senialesHeredadas != null && senialesHeredadas.size() > 0) {
			return senialesHeredadas.get(0);
		} else {
			return StringUtils.EMPTY;
		}
	}
	
	@Override
	public Boolean validarRecontratacion(ValidarRecontratacionRequest validarRecontratacionRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", validarRecontratacionRequest.getContrato())
														   .addValue("P_GRUPO", validarRecontratacionRequest.getGrupo())										   
														   .addValue("P_SENIAL", validarRecontratacionRequest.getSenial());
														   
		Map<String, Object> out = this.validarRecontratacionCall.execute(in);
		return "S".equals((String) out.get("P_RECONT"));
	}
	
	@Override
	public ValidarAmortizacionResponse validarAmortizacionCC(ValidarAmortizacionRequest validarAmortizacionRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", validarAmortizacionRequest.getContrato())
														   .addValue("P_GRUPO", validarAmortizacionRequest.getGrupo())
														   .addValue("P_SENIAL", validarAmortizacionRequest.getSenial())
														   .addValue("P_TPOTITULO", validarAmortizacionRequest.getTipoTitulo())
														   .addValue("P_PASLIB", validarAmortizacionRequest.getPasaLibreria())
														   .addValue("P_MARCAPERP", validarAmortizacionRequest.getMarcaPerpetuidad())
														   .addValue("P_CANTPAS", validarAmortizacionRequest.getCantidadTitulos())
														   .addValue("P_CANTREP", validarAmortizacionRequest.getCantidadPasadasContratadas())
														   .addValue("P_CANT_ORIG", validarAmortizacionRequest.getCantidadOriginales());
														   
		Map<String, Object> out = this.validarAmortizacionCCCall.execute(in);
		
		ValidarAmortizacionResponse response = new ValidarAmortizacionResponse(); 
		response.setExito("S".equals((String) out.get("P_OK")));
		response.setMensaje((String) out.get("P_MSJ"));
		if (out.get("P_ID_AMO_1") != null) {
			response.setPrimerIdAmortizacion(((BigDecimal) out.get("P_ID_AMO_1")).intValue());
			response.setDescripcionPrimerIdAmortizacion((String) out.get("P_ID_AMO_DESC_1"));
		}
		if (out.get("P_ID_AMO_2") != null) {
			response.setSegundoIdAmortizacion(((BigDecimal) out.get("P_ID_AMO_2")).intValue());
			response.setDescripcionSegundoIdAmortizacion((String) out.get("P_ID_AMO_DESC_2"));
		}
		return response;
	}
	
	@Override
	public Boolean altaGrupoCC(AltaGrupoCCRequest altaGrupoCCRequest) {
		SqlParameterSource in = new MapSqlParameterSource()
														   /* Parámetros del alta de grupo CC */
														   .addValue("P_P05FUN", "A")
														   .addValue("P_C01COKCON", altaGrupoCCRequest.getContrato())
														   .addValue("P_C01GRKGRUPO", altaGrupoCCRequest.getNroGrupo())
														   .addValue("P_S02SNKCOD", altaGrupoCCRequest.getSenial())
														   .addValue("P_C05TDKTPODER", altaGrupoCCRequest.getTipoDerecho())
														   .addValue("P_C05DTKDERTER", altaGrupoCCRequest.getDerechoTerrirorial())
														   .addValue("P_VALORIDAMO", altaGrupoCCRequest.getPrimerIdAmortizacion())
														   .addValue("P_C05IDKTPOTIT", altaGrupoCCRequest.getTipoTitulo())
														   .addValue("P_C05TIKTPOIMP", altaGrupoCCRequest.getTipoImporte())
														   .addValue("P_VALORIDAMO2", altaGrupoCCRequest.getSegundoIdAmortizacion())
														   .addValue("P_C05GRDNOMGRP", altaGrupoCCRequest.getNombreGrupo())
														   .addValue("P_CANTTITULOS", BigDecimal.ZERO)
														   .addValue("P_C05GRNCNTORI", altaGrupoCCRequest.getCantidadOriginales())
														   .addValue("P_C05GRNCNTREP", altaGrupoCCRequest.getCantidadRepeticiones())
														   .addValue("P_C05GRMMRCPER", altaGrupoCCRequest.getMarcaPerpetuidad())
														   .addValue("P_C05GRNESTREP", altaGrupoCCRequest.getEstrenoRepeticion())
														   .addValue("P_C05GRNIMPGRU", altaGrupoCCRequest.getCostoTotal())
														   .addValue("P_C05GRMAUTAUT", altaGrupoCCRequest.getAutorizacionAutor())
														   .addValue("P_C05GRFCOMDER", altaGrupoCCRequest.getFechaComienzoDerechos())
														   .addValue("P_C05GRNCNTTIE", altaGrupoCCRequest.getCantidadTiempo())
														   .addValue("P_C05GRMUNITIE", altaGrupoCCRequest.getUnidadTiempo())
														   .addValue("P_C05GRDDESCR", altaGrupoCCRequest.getPlanEntrega())
														   .addValue("P_C05GRMPASLIB", altaGrupoCCRequest.getPasaLibreria())
														   .addValue("P_C05GRFCOMLIB", altaGrupoCCRequest.getFechaComienzoDerechosLibreria())
														   .addValue("P_C05GRFFCHENT", altaGrupoCCRequest.getFechaPosibleEntrega())
														   .addValue("P_C05GRNCNTEXC", altaGrupoCCRequest.getCantidadTiempoExclusividad())
														   .addValue("P_C05GRNUNEXC", altaGrupoCCRequest.getUnidadTiempoExclusividad())
														   .addValue("P_C05TNMMARREC", altaGrupoCCRequest.getRecontratacion())
														   .addValue("P_C05GRNTEMPO", BigDecimal.ZERO)
														   .addValue("P_C05GRMPAARG", altaGrupoCCRequest.getPagaArgentores())
														   /* Parámetros del alta de grupo SC */
														   .addValue("P_P07FUN", "N")
														   .addValue("P_C07GRNCNTPAS", null)
														   .addValue("P_C07TDKTPODER", null)
														   .addValue("P_C07DTKDERTER", null)
														   .addValue("P_C07AMKIDAMO", null)
														   .addValue("P_C07IDKTPOTIT", null)
														   .addValue("P_C07TIKTPOIMP", null)
														   .addValue("P_C07AMKIDAMO2", null)
														   .addValue("P_C07GRDNOMGRP", null)
														   .addValue("P_C07GRNCNTTIT", null)
														   .addValue("P_C07GRMMRCPER", null)
														   .addValue("P_C07GRNESTREP", null)
														   .addValue("P_C07GRNIMPUNI", null)
														   .addValue("P_C07GRMAUTAUT", null)
														   .addValue("P_C07COFFCHCO", null)
														   .addValue("P_C07GRNCNTTIE", null)
														   .addValue("P_C07GRMUNITIE", null)
														   .addValue("P_C07GRDDESCR", null)
														   .addValue("P_C07GRMPASLIB", null)
														   .addValue("P_C07GRFCOMLIB", null)
														   .addValue("P_C07GRFFCHENT", null)
														   .addValue("P_C07GRNCNTEXC", null)
														   .addValue("P_C07GRNUNEXC", null)
														   .addValue("P_C07TNMMARREC", null)
														   .addValue("P_C07GRNTEMPO", null)
														   .addValue("P_C07GRMPAARG", null);
		
		this.altaGrupoCCCall.execute(in);
		return Boolean.TRUE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ValidarModificacionGrupoCCResponse validarModificacionGrupoCC(ValidarModificacionGrupoCCRequest validarModificacionGrupoCCRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", validarModificacionGrupoCCRequest.getContrato())
														   .addValue("P_GRUPO", validarModificacionGrupoCCRequest.getGrupo())
														   .addValue("P_SENIAL", validarModificacionGrupoCCRequest.getSenial())
														   .addValue("P_TIPOTITULO", validarModificacionGrupoCCRequest.getTipoTitulo())
														   .addValue("P_TIPOIMPORTE", validarModificacionGrupoCCRequest.getTipoImporte())
														   .addValue("P_IDTPODER", validarModificacionGrupoCCRequest.getIdTipoDerecho())
														   .addValue("P_COMIENZODERECHOS", validarModificacionGrupoCCRequest.getFechaComienzoDerechos())
														   .addValue("P_CANTTIEMPO", validarModificacionGrupoCCRequest.getCantidadTiempo())
														   .addValue("P_UNITIEMPO", validarModificacionGrupoCCRequest.getUnidadTiempo())
														   .addValue("P_MARCAPERP", validarModificacionGrupoCCRequest.getMarcaPerpetuidad())
														   .addValue("P_PASLIB", validarModificacionGrupoCCRequest.getPasaLibreria())
														   .addValue("P_CANTORIGINAL", validarModificacionGrupoCCRequest.getCantidadOriginales())
														   .addValue("P_CANTREPETICIONES", validarModificacionGrupoCCRequest.getCantidadRepeticiones());
            
		Map<String, Object> out = this.validarModificacionGrupoCCCall.execute(in);
		
		ValidarModificacionGrupoCCResponse response = new ValidarModificacionGrupoCCResponse();
		
		String error = (String) out.get("P_NERROR");
		response.setHayErrores("S".equals(error));
		if (this.validarCursorErrores(out.get("P_NVECTORERROR"))) {
			response.setErrores((List<Map<String, String>>) out.get("P_NVECTORERROR"));
		}
		BigDecimal idReporte = (BigDecimal) out.get("P_IDREPORTE");
		if (idReporte != null) {
			response.setIdReporte(idReporte.intValue());	
		}

		return response;
	}
	
	@Override
	public Boolean eliminarConsultarCC(EliminarConsultarCCRequest eliminarConsultarCCRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", eliminarConsultarCCRequest.getContrato())
														   .addValue("P_GRUPO", eliminarConsultarCCRequest.getGrupo())										   
														   .addValue("P_SENIAL", eliminarConsultarCCRequest.getSenial())
														   .addValue("P_TIPO_TITULO", eliminarConsultarCCRequest.getTipoTitulo())
														   .addValue("P_NRO_TITULO", eliminarConsultarCCRequest.getNroTitulo());
				   
		Map<String, Object> out = this.eliminarConsultarCCCall.execute(in);
		return "S".equals((String) out.get("P_OK"));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ModificacionGrupoCCResponse modificacionGrupoCC(ModificacionGrupoCCRequest modificacionGrupoCCRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", modificacionGrupoCCRequest.getContrato())
														   .addValue("P_GRUPO", modificacionGrupoCCRequest.getGrupo())
														   .addValue("P_SENIAL", modificacionGrupoCCRequest.getSenial())
														   .addValue("P_TIPOTITULO", modificacionGrupoCCRequest.getTipoTitulo())
														   .addValue("P_TIPOIMPORTE", modificacionGrupoCCRequest.getTipoImporte())
														   .addValue("P_IDTPODER", modificacionGrupoCCRequest.getIdTipoDerecho())
														   .addValue("P_COMIENZODERECHOS", modificacionGrupoCCRequest.getFechaComienzoDerechos())
														   .addValue("P_CANTTIEMPO", modificacionGrupoCCRequest.getCantidadTiempo())
														   .addValue("P_UNITIEMPO", modificacionGrupoCCRequest.getUnidadTiempo())
														   .addValue("P_MARCAPERP", modificacionGrupoCCRequest.getMarcaPerpetuidad())
														   .addValue("P_MARCARECONTRATACION", modificacionGrupoCCRequest.getMarcaRecontratacion())
														   //.addValue("P_IMPORTEUNITARIO", modificacionGrupoCCRequest.getImporteUnitario())
														   .addValue("P_IMPORTEGRUPO", modificacionGrupoCCRequest.getImporteGrupo())
														   .addValue("P_FEC_ENTREGA", modificacionGrupoCCRequest.getFechaEntrega())
														   .addValue("P_PASLIB", modificacionGrupoCCRequest.getPasaLibreria())
														   .addValue("P_CANTORIGINAL", modificacionGrupoCCRequest.getCantidadOriginales())
														   .addValue("P_CANTREPETICIONES", modificacionGrupoCCRequest.getCantidadRepeticiones())
                                                           .addValue("P_DER_TER", modificacionGrupoCCRequest.getDerechosTerritoriales())
                                                           .addValue("P_CNT_TIE_EXCL", modificacionGrupoCCRequest.getCantidadTiempoExclusivo())
                                                           .addValue("P_UNI_TIE_EXCL", modificacionGrupoCCRequest.getUnidadTiempoExclusivo())
                                                           .addValue("P_AUT_AUT", modificacionGrupoCCRequest.getAutorizacionAutor())
                                                           .addValue("P_FEC_COM_LIB", modificacionGrupoCCRequest.getFechaComienzoLibreria())
                                                           .addValue("P_PAGA_ARG", modificacionGrupoCCRequest.getPagaArgentores());
		
		Map<String, Object> out = this.modificacionGrupoCCCall.execute(in);
		
		ModificacionGrupoCCResponse response = new ModificacionGrupoCCResponse();
		response.setHayErrores("S".equals((String) out.get("P_RERUN")));
		response.setErroresRerun((List<ErrorModificacionGrupo>) out.get("P_VECTORRERUN"));
		response.setMensaje((String) out.get("P_DESCRIPCIONERROR"));
		BigDecimal idReporte = (BigDecimal) out.get("P_IDREPORTE");
		if (idReporte != null) {
			response.setIdReporte(idReporte.intValue());	
		}
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SenialHeredada> buscarSenialesHeredadas(BuscarSenialesHeredadasRequest buscarSenialesHeredadasRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", buscarSenialesHeredadasRequest.getContrato())
						   								   .addValue("P_SENIAL", buscarSenialesHeredadasRequest.getSenial())
						   								   .addValue("P_GRUPO", buscarSenialesHeredadasRequest.getGrupo())
						   								   .addValue("P_SENIALES", buscarSenialesHeredadasRequest.getSeniales());
						   
		Map<String, Object> out = this.buscarSenialesHeredadasCall.execute(in);
		return (List<SenialHeredada>) out.get("P_DATOS");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ValidarAsignacionSenialHeredadaResponse> validarAsignacionSenialHeredadaCC(ValidarAsignacionSenialHeredadaRequest validarAsignacionSenialHeredadaRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", validarAsignacionSenialHeredadaRequest.getContrato())
														   .addValue("P_GRUPO", validarAsignacionSenialHeredadaRequest.getGrupo())
														   .addValue("P_SENIAL", validarAsignacionSenialHeredadaRequest.getSenial())
														   .addValue("P_SENIAL_HER", validarAsignacionSenialHeredadaRequest.getSenialHeredada());
		
		Map<String, Object> out = this.validarAsignacionSenialHeredadaCCCall.execute(in);
		return (List<ValidarAsignacionSenialHeredadaResponse>) out.get("P_DATOS");
	}

	@Override
	public Boolean asignarSenialHeredadaCC(AsignarSenialHeredadaRequest asignarSenialHeredadaRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", asignarSenialHeredadaRequest.getContrato())
														   .addValue("P_GRUPO", asignarSenialHeredadaRequest.getGrupo())
														   .addValue("P_SENIAL", asignarSenialHeredadaRequest.getSenial())
														   .addValue("P_SENIAL_HER", asignarSenialHeredadaRequest.getSenialHeredada())
														   .addValue("P_USUARIO", "DFLCLM");
		
		Map<String, Object> out = this.asignarSenialHeredadaCCCall.execute(in);
		return "S".equals((String) out.get("P_OK"));
	}

	@Override
	public Boolean desasignarSenialHeredadaCC(DesasignarSenialHeredadaRequest desasignarSenialHeredadaRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", desasignarSenialHeredadaRequest.getContrato())
														   .addValue("P_GRUPO", desasignarSenialHeredadaRequest.getGrupo())
														   .addValue("P_SENIAL", desasignarSenialHeredadaRequest.getSenial())
														   .addValue("P_SENIAL_HER", desasignarSenialHeredadaRequest.getSenialHeredada())
														   .addValue("P_USUARIO", "DFLCLM");

		Map<String, Object> out = this.desasignarSenialHeredadaCCCall.execute(in);
		return "S".equals((String) out.get("P_OK"));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String buscarSenialHeredadaSC(BuscarSenialesHeredadasRequest buscarSenialesHeredadasRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_SENIAL", buscarSenialesHeredadasRequest.getSenial());

		Map<String, Object> out = this.buscarSenialHeredadaSCCall.execute(in);
		List<String> senialesHeredadas = ((List<String>) out.get("P_DATOS"));
		if (senialesHeredadas != null && senialesHeredadas.size() > 0) {
			return senialesHeredadas.get(0);
		} else {
			return StringUtils.EMPTY;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ValidarAmortizacionResponse validarAmortizacionSC(ValidarAmortizacionRequest validarAmortizacionRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", validarAmortizacionRequest.getContrato())
														   .addValue("P_GRUPO", validarAmortizacionRequest.getGrupo())
														   .addValue("P_SENIAL", validarAmortizacionRequest.getSenial())
														   .addValue("P_TIPOTITULO", validarAmortizacionRequest.getTipoTitulo())
														   .addValue("P_MARCAPERP", validarAmortizacionRequest.getMarcaPerpetuidad())
														   .addValue("P_PASLIB", validarAmortizacionRequest.getPasaLibreria())
														   .addValue("P_CANTORIG", validarAmortizacionRequest.getCantidadTitulos())
														   .addValue("P_CANTREP", validarAmortizacionRequest.getCantidadPasadasContratadas())
														   .addValue("P_NERROR", "N");
		
		Map<String, Object> out = this.validarAmortizacionSCCall.execute(in);
		boolean exito = !"S".equals((String) out.get("P_NERROR"));
		
		ValidarAmortizacionResponse response = new ValidarAmortizacionResponse(); 
		
		response.setExito(exito);
		if (exito) {
			LinkedHashMap<Integer, String> ids = ((List<LinkedHashMap<Integer, String>>) out.get("P_DATOS")).get(0);
			
			
			Iterator<Entry<Integer, String>> iterador = ids.entrySet().iterator();
			Entry<Integer, String> entry = iterador.next();
			response.setPrimerIdAmortizacion(entry.getKey());
			response.setDescripcionPrimerIdAmortizacion(entry.getValue());
			
			if (iterador.hasNext()) {
			    entry = iterador.next();
			    response.setSegundoIdAmortizacion(entry.getKey());
			    response.setDescripcionSegundoIdAmortizacion(entry.getValue());	
			}
		} else {
			response.setMensaje(((List<String>) out.get("P_NVECTORERROR")).get(0));	
		}
		return response;
	}

	@Override
	public Boolean altaGrupoSC(AltaGrupoSCRequest altaGrupoSCRequest) {
		SqlParameterSource in = new MapSqlParameterSource()/* Parámetros del alta de grupo SC */
														   .addValue("P_P07FUN", "A")
														   .addValue("P_C01COKCON", altaGrupoSCRequest.getContrato())
														   .addValue("P_C01GRKGRUPO", altaGrupoSCRequest.getNroGrupo())
														   .addValue("P_S02SNKCOD", altaGrupoSCRequest.getSenial())
														   .addValue("P_C07GRNCNTPAS", altaGrupoSCRequest.getCantidadPasadasContratadas())
														   .addValue("P_C07TDKTPODER", altaGrupoSCRequest.getTipoDerecho())
														   .addValue("P_C07DTKDERTER", altaGrupoSCRequest.getDerechoTerrirorial())
														   .addValue("P_C07IDKTPOTIT", altaGrupoSCRequest.getTipoTitulo())
														   .addValue("P_C07TIKTPOIMP", altaGrupoSCRequest.getTipoImporte())
														   .addValue("P_C07AMKIDAMO", altaGrupoSCRequest.getPrimerIdAmortizacion())
														   .addValue("P_C07AMKIDAMO2", altaGrupoSCRequest.getSegundoIdAmortizacion())
														   .addValue("P_VALORIDAMO", altaGrupoSCRequest.getPrimerIdAmortizacion())
														   .addValue("P_VALORIDAMO2", altaGrupoSCRequest.getSegundoIdAmortizacion())
														   .addValue("P_C07GRDNOMGRP", altaGrupoSCRequest.getNombreGrupo())
														   .addValue("P_C07GRNCNTTIT", altaGrupoSCRequest.getCantidadTitulos())
														   .addValue("P_C07GRMMRCPER", altaGrupoSCRequest.getMarcaPerpetuidad())
														   .addValue("P_C07GRNESTREP", altaGrupoSCRequest.getEstrenoRepeticion())
														   .addValue("P_C07GRNIMPUNI", altaGrupoSCRequest.getCostoUnitario())
														   .addValue("P_C07GRMAUTAUT", altaGrupoSCRequest.getAutorizacionAutor())
														   .addValue("P_C07COFFCHCO", altaGrupoSCRequest.getFechaComienzoDerechos())
														   .addValue("P_C07GRNCNTTIE", altaGrupoSCRequest.getCantidadTiempo())
														   .addValue("P_C07GRMUNITIE", altaGrupoSCRequest.getUnidadTiempo())
														   .addValue("P_C07GRDDESCR", altaGrupoSCRequest.getPlanEntrega())
														   .addValue("P_C07GRMPASLIB", altaGrupoSCRequest.getPasaLibreria())
														   .addValue("P_C07GRFCOMLIB", altaGrupoSCRequest.getFechaComienzoDerechosLibreria())
														   .addValue("P_C07GRFFCHENT", altaGrupoSCRequest.getFechaPosibleEntrega())
														   .addValue("P_C07GRNCNTEXC", altaGrupoSCRequest.getCantidadTiempoExclusividad())
														   .addValue("P_C07GRNUNEXC", altaGrupoSCRequest.getUnidadTiempoExclusividad())
														   .addValue("P_C07GRMPAARG", altaGrupoSCRequest.getPagaArgentores())
														   .addValue("P_C07GRNTEMPO", BigDecimal.ZERO)
														   .addValue("P_C07TNMMARREC", " ")
														   /* Parámetros del alta de grupo CC */
														   .addValue("P_P05FUN", "N")
														   .addValue("P_C05TDKTPODER", null)
														   .addValue("P_C05DTKDERTER", null)
														   .addValue("P_C05IDKTPOTIT", null)
														   .addValue("P_C05TIKTPOIMP", null)
														   .addValue("P_C05GRDNOMGRP", null)
														   .addValue("P_CANTTITULOS", null)
														   .addValue("P_C05GRNCNTORI", null)
														   .addValue("P_C05GRNCNTREP", null)
														   .addValue("P_C05GRMMRCPER", null)
														   .addValue("P_C05GRNESTREP", null)
														   .addValue("P_C05GRNIMPGRU", null)
														   .addValue("P_C05GRMAUTAUT", null)
														   .addValue("P_C05GRFCOMDER", null)
														   .addValue("P_C05GRNCNTTIE", null)
														   .addValue("P_C05GRMUNITIE", null)
														   .addValue("P_C05GRDDESCR", null)
														   .addValue("P_C05GRMPASLIB", null)
														   .addValue("P_C05GRFCOMLIB", null)
														   .addValue("P_C05GRFFCHENT", null)
														   .addValue("P_C05GRNCNTEXC", null)
														   .addValue("P_C05GRNUNEXC", null)
														   .addValue("P_C05TNMMARREC", null)
														   .addValue("P_C05GRNTEMPO", null)
														   .addValue("P_C05GRMPAARG", null);
		
		this.altaGrupoSCCall.execute(in);
		return Boolean.TRUE;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ValidarCambioTipoImporteSCResponse validarCambioTipoImporteSC(ValidarCambioTipoImporteSCRequest validarCambioTipoImporteSCRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", validarCambioTipoImporteSCRequest.getContrato())
														   .addValue("P_GRUPO", validarCambioTipoImporteSCRequest.getGrupo())
														   .addValue("P_SENIAL", validarCambioTipoImporteSCRequest.getSenial())
														   .addValue("P_TIPOIMPORTE", validarCambioTipoImporteSCRequest.getTipoImporte())
														   .addValue("P_TIPOTITULO", validarCambioTipoImporteSCRequest.getTipoTitulo())
														   .addValue("P_MARCAPERP", validarCambioTipoImporteSCRequest.getMarcaPerpetuidad())
														   .addValue("P_CANTPASADAS", validarCambioTipoImporteSCRequest.getCantidadPasadas())
														   .addValue("P_PASLIB", validarCambioTipoImporteSCRequest.getPasaLibreria());
		
		Map<String, Object> out = this.validarCambioTipoImporteSCCall.execute(in);
		
		ValidarCambioTipoImporteSCResponse response = new ValidarCambioTipoImporteSCResponse();
		response.setHayErrores("S".equals((String) out.get("P_NERROR")));
		
		List<String> errores = (List<String>) out.get("P_NVECTORERROR");
		if (errores != null && errores.size() > 0) {
			response.setMensaje(errores.get(0));	
		}
		
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ValidarPasaLibreriaNaSSCResponse validarPasaLibreriaNaSSC(ValidarPasaLibreriaNaSSCRequest validarPasaLibreriaNaSSCRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", validarPasaLibreriaNaSSCRequest.getContrato())
														   .addValue("P_GRUPO", validarPasaLibreriaNaSSCRequest.getGrupo())
														   .addValue("P_SENIAL", validarPasaLibreriaNaSSCRequest.getSenial())
														   .addValue("P_TIPOTITULO", validarPasaLibreriaNaSSCRequest.getTipoTitulo())
														   .addValue("P_MARCAPERP", validarPasaLibreriaNaSSCRequest.getMarcaPerpetuidad())
														   .addValue("P_CANTPASADAS", validarPasaLibreriaNaSSCRequest.getCantidadPasadas())
														   .addValue("P_PASLIB", validarPasaLibreriaNaSSCRequest.getPasaLibreria());
		
		Map<String, Object> out = this.validarPasaLibreriaNaSSCCall.execute(in);
		
		ValidarPasaLibreriaNaSSCResponse response = new ValidarPasaLibreriaNaSSCResponse();
		response.setHayErrores("S".equals((String) out.get("P_NERROR")));
		
		List<String> errores = (List<String>) out.get("P_NVECTORERROR");
		if (errores != null && errores.size() > 0) {
			response.setMensaje(errores.get(0));	
		}
		
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ValidarCantidadPasadasContratadasSCResponse validarCantidadPasadasContratadasSC(ValidarCantidadPasadasContratadasSCRequest validarCantidadPasadasContratadasSCRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", validarCantidadPasadasContratadasSCRequest.getContrato())
														   .addValue("P_GRUPO", validarCantidadPasadasContratadasSCRequest.getGrupo())
														   .addValue("P_SENIAL", validarCantidadPasadasContratadasSCRequest.getSenial())
														   .addValue("P_TIPOIMPORTE", validarCantidadPasadasContratadasSCRequest.getTipoImporte())
														   .addValue("P_TIPOTITULO", validarCantidadPasadasContratadasSCRequest.getTipoTitulo())
														   .addValue("P_MARCAPERP", validarCantidadPasadasContratadasSCRequest.getMarcaPerpetuidad())
														   .addValue("P_CANTPASADAS", validarCantidadPasadasContratadasSCRequest.getCantidadPasadas())
														   .addValue("P_PASLIB", validarCantidadPasadasContratadasSCRequest.getPasaLibreria());
		
		Map<String, Object> out = this.validarCantidadPasadasContratadasSCCall.execute(in);
		
		ValidarCantidadPasadasContratadasSCResponse response = new ValidarCantidadPasadasContratadasSCResponse();
		response.setHayErrores("S".equals((String) out.get("P_NERROR")));
		
		List<String> errores = (List<String>) out.get("P_NVECTORERROR");
		if (errores != null && errores.size() > 0) {
			response.setMensaje(errores.get(0));	
		}
		
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ValidarRerunSCResponse validarRerunSC(ValidarRerunSCRequest validarRerunSCRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", validarRerunSCRequest.getContrato())
														   .addValue("P_GRUPO", validarRerunSCRequest.getGrupo())
														   .addValue("P_SENIAL", validarRerunSCRequest.getSenial());
				   
		Map<String, Object> out = this.validarRerunSCCall.execute(in);
		
		ValidarRerunSCResponse response = new ValidarRerunSCResponse();
		response.setHayErrores("S".equals((String) out.get("P_ERROR")));
		response.setTitulos((List<TituloConflicto>) out.get("P_TITULOSENCONFLICTO"));
		
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ValidarDesenlaceSCResponse validarDesenlaceSC(ValidarDesenlaceSCRequest validarDesenlaceSCRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", validarDesenlaceSCRequest.getContrato())
														   .addValue("P_GRUPO", validarDesenlaceSCRequest.getGrupo())
														   .addValue("P_SENIAL", validarDesenlaceSCRequest.getSenial());
		
		Map<String, Object> out = this.validarDesenlaceSCCall.execute(in);
		
		ValidarDesenlaceSCResponse response = new ValidarDesenlaceSCResponse();
		if (this.validarCursorErrores(out.get("P_ERR_DESENLACE"))) {
			response.setErroresDesenlace((List<ErrorDesenlaceResponse>) out.get("P_ERR_DESENLACE"));
		}
        if (out.get("P_IDREPORTE") != null) {
			response.setIdReporte(((BigDecimal) out.get("P_IDREPORTE")).intValue());	
		}
		response.setHayErrores("S".equals((String) out.get("P_ERROR")));
        
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ValidarModificacionGrupoSCResponse validarModificacionGrupoSC(ValidarModificacionGrupoSCRequest validarModificacionGrupoSCRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", validarModificacionGrupoSCRequest.getContrato())
														   .addValue("P_GRUPO", validarModificacionGrupoSCRequest.getGrupo())
														   .addValue("P_SENIAL", validarModificacionGrupoSCRequest.getSenial())
														   .addValue("P_TIPOTITULO", validarModificacionGrupoSCRequest.getTipoTitulo())
														   .addValue("P_TIPOIMPORTE", validarModificacionGrupoSCRequest.getTipoImporte())
														   .addValue("P_IDTPODER", validarModificacionGrupoSCRequest.getIdTipoDerecho())
														   .addValue("P_COMIENZODERECHOS", validarModificacionGrupoSCRequest.getFechaComienzoDerechos())
														   .addValue("P_CANTTIEMPO", validarModificacionGrupoSCRequest.getCantidadTiempo())
														   .addValue("P_UNITIEMPO", validarModificacionGrupoSCRequest.getUnidadTiempo())
														   .addValue("P_MARCAPERP", validarModificacionGrupoSCRequest.getMarcaPerpetuidad())
														   .addValue("P_CANTTIT", validarModificacionGrupoSCRequest.getCantidadTitulos())
														   .addValue("P_ENLAZARNUEVAVIGENCIA", validarModificacionGrupoSCRequest.getEnlazarNuevaVigencia());
		
		Map<String, Object> out = this.validarModificacionGrupoSCCall.execute(in);
		
		ValidarModificacionGrupoSCResponse response = new ValidarModificacionGrupoSCResponse();
		
		String error = (String) out.get("P_NERROR");
		response.setHayErrores("S".equals(error));
		if (this.validarCursorErrores(out.get("P_NVECTORERROR"))) {
			response.setErrores((List<Map<String, String>>) out.get("P_NVECTORERROR"));
		}
		BigDecimal idReporte = (BigDecimal) out.get("P_IDREPORTE");
		if (idReporte != null) {
			response.setIdReporte(idReporte.intValue());	
		}

		return response;
	}
	
	@Override
	public Boolean eliminarConsultarSC(EliminarConsultarSCRequest eliminarConsultarSCRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", eliminarConsultarSCRequest.getContrato())
														   .addValue("P_GRUPO", eliminarConsultarSCRequest.getGrupo())										   
														   .addValue("P_SENIAL", eliminarConsultarSCRequest.getSenial())
														   .addValue("P_TIPO_TITULO", eliminarConsultarSCRequest.getTipoTitulo())
														   .addValue("P_NRO_TITULO", eliminarConsultarSCRequest.getNroTitulo());
				   
		Map<String, Object> out = this.eliminarConsultarSCCall.execute(in);
		return "S".equals((String) out.get("P_OK"));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ModificacionGrupoSCResponse modificacionGrupoSC(ModificacionGrupoSCRequest modificacionGrupoSCRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", modificacionGrupoSCRequest.getContrato())
														   .addValue("P_GRUPO", modificacionGrupoSCRequest.getGrupo())
														   .addValue("P_SENIAL", modificacionGrupoSCRequest.getSenial())
														   .addValue("P_TIPOTITULO", modificacionGrupoSCRequest.getTipoTitulo())
														   .addValue("P_TIPOIMPORTE", modificacionGrupoSCRequest.getTipoImporte())
														   .addValue("P_IDTPODER", modificacionGrupoSCRequest.getIdTipoDerecho())
														   .addValue("P_COMIENZODERECHOS", modificacionGrupoSCRequest.getFechaComienzoDerechos())
														   .addValue("P_CANTTIEMPO", modificacionGrupoSCRequest.getCantidadTiempo())
														   .addValue("P_UNITIEMPO", modificacionGrupoSCRequest.getUnidadTiempo())
														   .addValue("P_MARCAPERP", modificacionGrupoSCRequest.getMarcaPerpetuidad())
														   .addValue("P_IMPORTEUNITARIO", modificacionGrupoSCRequest.getImporteUnitario())
														   .addValue("P_IMPORTEGRUPO", modificacionGrupoSCRequest.getImporteGrupo())
														   .addValue("P_PASLIB", modificacionGrupoSCRequest.getPasaLibreria())
														   .addValue("P_CANTTIT", modificacionGrupoSCRequest.getCantidadTitulos())
														   .addValue("P_CANTPAS", modificacionGrupoSCRequest.getCantidadPasadas())
														   
														   .addValue("P_NOM_GRUPO", modificacionGrupoSCRequest.getNombreGrupo())
														   .addValue("P_ESTRENO", modificacionGrupoSCRequest.getER())
														   .addValue("P_DER_TER", modificacionGrupoSCRequest.getDerechosTerritoriales())
														   .addValue("P_CANT_EXCL", modificacionGrupoSCRequest.getCantidadTiempoExclusivo())
														   .addValue("P_UNI_EXCL", modificacionGrupoSCRequest.getUnidadTiempoExclusivo())
														   .addValue("P_AUT_AUT", modificacionGrupoSCRequest.getAutorizacionAutor())
														   .addValue("P_PAGA_ARG", modificacionGrupoSCRequest.getPagaArgentores())
														   .addValue("P_FEC_COM_LIB", modificacionGrupoSCRequest.getFechaComienzoLibreria())
														   ;
		
//		P_NOM_GRUPO           IN VARCHAR2,
//		P_ESTRENO             IN VARCHAR2,
//		P_DER_TER             IN VARCHAR2,
//		P_CANT_EXCL           IN NUMBER,
//		P_UNI_EXCL            IN VARCHAR2,
//		P_AUT_AUT             IN VARCHAR2,
//		P_PAGA_ARG            IN VARCHAR2,
//		P_FEC_COM_DER         IN DATE,
//		P_FEC_COM_LIB         IN DATE,
		
		Map<String, Object> out = this.modificacionGrupoSCCall.execute(in);
		
		ModificacionGrupoSCResponse response = new ModificacionGrupoSCResponse();
		response.setHayErrores("S".equals((String) out.get("P_RERUN")));
		response.setErroresRerun((List<ErrorModificacionGrupo>) out.get("P_VECTORRERUN"));
		BigDecimal idReporte = (BigDecimal) out.get("P_IDREPORTE");
		response.setMensaje((String) out.get("P_DESCRIPCIONERROR"));
		if (idReporte != null) {
			response.setIdReporte(idReporte.intValue());	
		}
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ValidarAsignacionSenialHeredadaResponse> validarAsignacionSenialHeredadaSC(ValidarAsignacionSenialHeredadaRequest validarAsignacionSenialHeredadaRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", validarAsignacionSenialHeredadaRequest.getContrato())
														   .addValue("P_GRUPO", validarAsignacionSenialHeredadaRequest.getGrupo())
														   .addValue("P_SENIAL", validarAsignacionSenialHeredadaRequest.getSenial());
		
		Map<String, Object> out = this.validarAsignacionSenialHeredadaSCCall.execute(in);
		return (List<ValidarAsignacionSenialHeredadaResponse>) out.get("P_DATOS");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Boolean asignarSenialHeredadaSC(AsignarSenialHeredadaRequest asignarSenialHeredadaRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", asignarSenialHeredadaRequest.getContrato())
														   .addValue("P_GRUPO", asignarSenialHeredadaRequest.getGrupo())
														   .addValue("P_SENIAL", asignarSenialHeredadaRequest.getSenial())
														   .addValue("P_SENIAL_HER", asignarSenialHeredadaRequest.getSenialHeredada());
		
		Map<String, Object> out = this.asignarSenialHeredadaSCCall.execute(in);
		
		List<String> listRespuesta = (List<String>) out.get("P_CURSOR");
		return "OK".equals(listRespuesta.get(0));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Boolean desasignarSenialHeredadaSC(DesasignarSenialHeredadaRequest desasignarSenialHeredadaRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", desasignarSenialHeredadaRequest.getContrato())
														   .addValue("P_GRUPO", desasignarSenialHeredadaRequest.getGrupo())
														   .addValue("P_SENIAL", desasignarSenialHeredadaRequest.getSenial())
														   .addValue("P_SENIAL_HER", desasignarSenialHeredadaRequest.getSenialHeredada());

		Map<String, Object> out = this.desasignarSenialHeredadaSCCall.execute(in);
		
		List<String> listRespuesta = (List<String>) out.get("P_CURSOR");
		return "OK".equals(listRespuesta.get(0));
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GrupoContrato> buscarGruposContrato(BuscarGruposContratoRequest buscarGruposContratoRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", buscarGruposContratoRequest.getContrato())
					   									   .addValue("P_SENIAL", buscarGruposContratoRequest.getSenial());
	
		Map<String, Object> out = this.buscarGruposContratoCall.execute(in);
		return (List<GrupoContrato>) out.get("P_CURSOR");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DesenlaceContratoGrupoResponse desenlaceContratoGrupo(DesenlaceContratoGrupoRequest desenlaceContratoGrupoRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", desenlaceContratoGrupoRequest.getContrato())
														   .addValue("P_GRUPO", desenlaceContratoGrupoRequest.getGrupo())		   								   
														   .addValue("P_SENIAL", desenlaceContratoGrupoRequest.getSenial());
						   
		Map<String, Object> out = this.desenlaceContratoGrupoCall.execute(in);
		
		DesenlaceContratoGrupoResponse response = new DesenlaceContratoGrupoResponse();
		response.setError((String) out.get("P_NERROR"));
		if (this.validarCursorErrores(out.get("P_NVECTORERROR"))) {
			response.setErrores((List<ErrorDesenlaceResponse>) out.get("P_NVECTORERROR"));
		}
		if (out.get("P_IDREPORTE") != null) {
			response.setIdReporte(((BigDecimal) out.get("P_IDREPORTE")).intValue());
		}
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public RecuperarDatosGrupoResponse recuperarDatosGrupo(RecuperarDatosGrupoRequest recuperarDatosGrupoRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", recuperarDatosGrupoRequest.getContrato())
														   .addValue("P_SENIAL", recuperarDatosGrupoRequest.getSenial())
														   .addValue("P_GRUPO", recuperarDatosGrupoRequest.getGrupo());
		
		Map<String, Object> out = this.recuperarDatosGrupoCall.execute(in);
		List<RecuperarDatosGrupoResponse> list = (List<RecuperarDatosGrupoResponse>) out.get("P_CURSOR");
		
		RecuperarDatosGrupoResponse response = new RecuperarDatosGrupoResponse(); 
		if (list != null && list.size() > 0) {
			response = list.get(0);
		}
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public EliminarGrupoContratoResponse eliminarGrupoContrato(EliminarGrupoContratoRequest eliminarGrupoContratoRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", eliminarGrupoContratoRequest.getContrato())
														   .addValue("P_GRUPO", eliminarGrupoContratoRequest.getGrupo())
														   .addValue("P_SENIAL", eliminarGrupoContratoRequest.getSenial())
														   .addValue("P_TPOTITULO", eliminarGrupoContratoRequest.getTipoTitulo())
														   .addValue("P_NROTITULO", eliminarGrupoContratoRequest.getNroTitulo())
														   .addValue("P_DESENLACE", eliminarGrupoContratoRequest.isDesenlace());
				   
		Map<String, Object> out = this.eliminarGrupoContratoCall.execute(in);
		
		EliminarGrupoContratoResponse response = new EliminarGrupoContratoResponse();

		String respuesta = ((List<String>) out.get("P_CURSOR")).get(0);
		response.setResultado("OK".equals(respuesta));
		
		BigDecimal idReporte = ((BigDecimal) out.get("P_IDREPORTE"));
		if (idReporte != null) {
			response.setIdReporte(idReporte.intValue());	
		}
		
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TituloGrupo> buscarTitulosGrupo(BuscarTitulosGrupoRequest buscarTitulosGrupoRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_SENIAL", buscarTitulosGrupoRequest.getSenial())
														   .addValue("P_DESCRIPCION", buscarTitulosGrupoRequest.getDescripcion())
														   .addValue("P_TIPOTITULO", buscarTitulosGrupoRequest.getTipoTitulo());
		
		Map<String, Object> out;
		if ("CAST".equals(buscarTitulosGrupoRequest.getTipoBusqueda())) {
			out = this.buscarTitulosGrupoCastellanoCall.execute(in);
		} else {
			out = this.buscarTitulosGrupoOriginalCall.execute(in);
		}
		return (List<TituloGrupo>) out.get("P_CURSOR");
	}
	
	@Override
	public Boolean validarPerpetuidadTitulo(ValidarPerpetuidadTituloRequest validarPerpetuidadTituloRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", validarPerpetuidadTituloRequest.getContrato())
														   .addValue("P_GRUPO", validarPerpetuidadTituloRequest.getGrupo())
														   .addValue("P_SENIAL", validarPerpetuidadTituloRequest.getSenial())
														   .addValue("P_CLAVE", validarPerpetuidadTituloRequest.getClave());
		
		Map<String, Object> out = this.validarPerpetuidadTituloCall.execute(in);
		return "S".equals((String) out.get("P_PERPETUIDAD"));
	}
	
	@Override
	public Integer obtenerNuevaClaveTitulo(ObtenerNuevaClaveTituloRequest obtenerNuevaClaveTituloRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_TIPOTITULO", obtenerNuevaClaveTituloRequest.getTipoTitulo());
		
		return this.obtenerNuevaClaveTituloCall.executeFunction(BigDecimal.class, in).intValue();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<CalificacionOficial>> buscarCalificacionesOficiales(String clave) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CLAVE", clave);
		
		Map<String, Object> out = this.buscarCalificacionesOficialesCall.execute(in);
		
		Map<String, List<CalificacionOficial>> response = new HashMap<String, List<CalificacionOficial>>();
		String calificacionOficialDefault = (out.get("P_CALIF") != null ? (String) out.get("P_CALIF") : "");
		response.put(calificacionOficialDefault, (List<CalificacionOficial>) out.get("P_CURSOR"));
		
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ValidacionExistenciaTituloResponse validacionExistenciaTitulo(ValidacionExistenciaTituloRequest validacionExistenciaTituloRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", validacionExistenciaTituloRequest.getContrato())
													       .addValue("P_GRUPO", validacionExistenciaTituloRequest.getGrupo())
													       .addValue("P_SENIAL", validacionExistenciaTituloRequest.getSenial())
													       .addValue("P_CLAVE", validacionExistenciaTituloRequest.getClave());
		
		Map<String, Object> out = this.validacionExistenciaTituloCall.execute(in);
		
		ValidacionExistenciaTituloResponse response = new ValidacionExistenciaTituloResponse();
		response.setError((String) out.get("P_NERROR"));
		List<String> errores = (List<String>) out.get("P_NVECTORERROR");
		if (errores != null && errores.size() > 0) {
			response.setMensaje(errores.get(0));	
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ValidacionRecepcionMasterResponse validacionRecepcionMaster(ValidacionRecepcionMasterRequest validacionRecepcionMasterRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", validacionRecepcionMasterRequest.getContrato())
														   .addValue("P_GRUPO", validacionRecepcionMasterRequest.getGrupo())
														   .addValue("P_SENIAL", validacionRecepcionMasterRequest.getSenial())
														   .addValue("P_IDTPODER", validacionRecepcionMasterRequest.getTipoDerecho())
														   .addValue("P_CLAVE", validacionRecepcionMasterRequest.getClave());

		Map<String, Object> out = this.validacionRecepcionMasterCall.execute(in);

		ValidacionRecepcionMasterResponse response = new ValidacionRecepcionMasterResponse();
		response.setEstaEnSNC("S".equals((String) out.get("P_SNC")));
		response.setRecCopia("S".equals((String) out.get("P_RECCOPIA")));
		response.setRecOrig("S".equals((String) out.get("P_RECORIG")));
		
		response.setErrores(((List<ErrorValidacionRecepcionMaster>) out.get("P_NVECTORERROR")).get(0));
		if (this.validarCursorErrores(out.get("P_CURSOR"))) {
			if (((List<Object>) out.get("P_CURSOR")).get(0) instanceof DatosSoporteRecepcionMaster) {
				response.setDatosSoporte(((List<DatosSoporteRecepcionMaster>) out.get("P_CURSOR")));
			} else {
				response.setDatosRemito(((List<DatosRemitoRecepcionMaster>) out.get("P_CURSOR")));	
			}
		}
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ConfirmarSeleccionTituloResponse confirmarSeleccionTitulo(ConfirmarSeleccionTituloRequest confirmarSeleccionTituloRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", confirmarSeleccionTituloRequest.getContrato())
														   .addValue("P_GRUPO", confirmarSeleccionTituloRequest.getGrupo())
														   .addValue("P_SENIAL", confirmarSeleccionTituloRequest.getSenial())
														   .addValue("P_CLAVE", confirmarSeleccionTituloRequest.getClave())
														   .addValue("P_PROVEEDOR", confirmarSeleccionTituloRequest.getProveedor())
														   .addValue("P_ORIGEN", confirmarSeleccionTituloRequest.getOrigen())
														   .addValue("P_MARPERM", confirmarSeleccionTituloRequest.getMarPerm())
														   .addValue("P_IDTPODER", confirmarSeleccionTituloRequest.getTipoDerecho())
														   .addValue("P_CHEQUEO", confirmarSeleccionTituloRequest.getChequeo())
														   .addValue("P_C10SINCONT", confirmarSeleccionTituloRequest.getTituloCastSinContrato())
														   .addValue("P_C11SINCONT", confirmarSeleccionTituloRequest.getTituloOrigSinContrato())
														   .addValue("P_RECORIG", confirmarSeleccionTituloRequest.getRecOrig())
														   .addValue("P_RECCOPIA", confirmarSeleccionTituloRequest.getRecCopia())
														   .addValue("P_FECHACOPIA", confirmarSeleccionTituloRequest.getFechaCopia())
														   .addValue("P_NRORTOINTERNO", confirmarSeleccionTituloRequest.getNroRtoInterno())
														   .addValue("P_FECHAREM", confirmarSeleccionTituloRequest.getFechaRem())
														   .addValue("P_CALIFOFICIAL", confirmarSeleccionTituloRequest.getCalificacionOficial())
														   .addValue("P_TITORI", confirmarSeleccionTituloRequest.getTituloOriginal())
														   .addValue("P_TITCAST", confirmarSeleccionTituloRequest.getTituloCastellano())
														   .addValue("P_CLAVEFCH", confirmarSeleccionTituloRequest.getClaveFichaCinematografica())
														   .addValue("P_ACTOR1", confirmarSeleccionTituloRequest.getPrimerActor())
														   .addValue("P_ACTOR2", confirmarSeleccionTituloRequest.getSegundoActor())
														   .addValue("P_OBSERVACIONES", confirmarSeleccionTituloRequest.getObservaciones())
														   .addValue("P_RECONTRATACION","N");
		
		Map<String, Object> out = this.confirmarSeleccionTituloCall.execute(in);
		
		ConfirmarSeleccionTituloResponse response = new ConfirmarSeleccionTituloResponse();
		response.setRerun((String) out.get("P_RERUN"));
		response.setCoincideMonto((String) out.get("P_COINCIDE"));
		
		List<Map<String, String>> erroresResponse = (List<Map<String, String>>) out.get("P_CURSORERRORES");
		if (erroresResponse != null && erroresResponse.size() > 0 && erroresResponse.get(0).entrySet().iterator().next().getKey() != null) {
			response.setTipoError(erroresResponse.get(0).entrySet().iterator().next().getKey());
			response.setDescripcion(erroresResponse.get(0).entrySet().iterator().next().getValue());
		}
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ConfirmarSeleccionTituloResponse confirmarSeleccionTituloRecontratacion(ConfirmarSeleccionTituloRequest confirmarSeleccionTituloRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", confirmarSeleccionTituloRequest.getContrato())
														   .addValue("P_GRUPO", confirmarSeleccionTituloRequest.getGrupo())
														   .addValue("P_SENIAL", confirmarSeleccionTituloRequest.getSenial())
														   .addValue("P_CLAVE", confirmarSeleccionTituloRequest.getClave())
														   .addValue("P_PROVEEDOR", confirmarSeleccionTituloRequest.getProveedor())
														   .addValue("P_ORIGEN", confirmarSeleccionTituloRequest.getOrigen())
														   .addValue("P_MARPERM", confirmarSeleccionTituloRequest.getMarPerm())
														   .addValue("P_IDTPODER", confirmarSeleccionTituloRequest.getTipoDerecho())
														   .addValue("P_CHEQUEO", confirmarSeleccionTituloRequest.getChequeo())
														   .addValue("P_C10SINCONT", confirmarSeleccionTituloRequest.getTituloCastSinContrato())
														   .addValue("P_C11SINCONT", confirmarSeleccionTituloRequest.getTituloOrigSinContrato())
														   .addValue("P_RECORIG", confirmarSeleccionTituloRequest.getRecOrig())
														   .addValue("P_RECCOPIA", confirmarSeleccionTituloRequest.getRecCopia())
														   .addValue("P_FECHACOPIA", confirmarSeleccionTituloRequest.getFechaCopia())
														   .addValue("P_NRORTOINTERNO", confirmarSeleccionTituloRequest.getNroRtoInterno())
														   .addValue("P_FECHAREM", confirmarSeleccionTituloRequest.getFechaRem())
														   .addValue("P_CALIFOFICIAL", confirmarSeleccionTituloRequest.getCalificacionOficial())
														   .addValue("P_TITORI", confirmarSeleccionTituloRequest.getTituloOriginal())
														   .addValue("P_TITCAST", confirmarSeleccionTituloRequest.getTituloCastellano())
														   .addValue("P_CLAVEFCH", confirmarSeleccionTituloRequest.getClaveFichaCinematografica())
														   .addValue("P_ACTOR1", confirmarSeleccionTituloRequest.getPrimerActor())
														   .addValue("P_ACTOR2", confirmarSeleccionTituloRequest.getSegundoActor())
														   .addValue("P_OBSERVACIONES", confirmarSeleccionTituloRequest.getObservaciones())
														   .addValue("P_RECONTRATACION","S");
		
		Map<String, Object> out = this.confirmarSeleccionTituloCall.execute(in);
		
		ConfirmarSeleccionTituloResponse response = new ConfirmarSeleccionTituloResponse();
		response.setRerun((String) out.get("P_RERUN"));
		
		List<Map<String, String>> erroresResponse = (List<Map<String, String>>) out.get("P_CURSORERRORES");
		if (erroresResponse != null && erroresResponse.size() > 0 && erroresResponse.get(0).entrySet().iterator().next().getKey() != null) {
			response.setTipoError(erroresResponse.get(0).entrySet().iterator().next().getKey());
			response.setDescripcion(erroresResponse.get(0).entrySet().iterator().next().getValue());
		}
		return response;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TituloRecontratacion> buscarTitulosRecontratacion(BuscarTitulosRecontratacionRequest buscarTitulosRecontratacionRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CLAVE", buscarTitulosRecontratacionRequest.getClave())
				 										   .addValue("P_SENIAL", buscarTitulosRecontratacionRequest.getSenial())
														   .addValue("P_CONTRATO", buscarTitulosRecontratacionRequest.getContrato())
														   .addValue("P_GRUPO", buscarTitulosRecontratacionRequest.getGrupo());
		
		Map<String, Object> out = this.buscarTitulosRecontratacionCall.execute(in);
		List<TituloRecontratacion> response = new ArrayList<TituloRecontratacion>();
		if ("OK".equals(((List<String>) out.get("P_CURSORERRORES")).get(0))) {
			response = (List<TituloRecontratacion>) out.get("P_CURSOR");	
		} 
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ValidarRemitoSNCResponse> validarRemitoSNC(ValidarRemitoSNCRequest validarRemitoSNCRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", validarRemitoSNCRequest.getContrato())
														   .addValue("P_GRUPO", validarRemitoSNCRequest.getGrupo())
														   .addValue("P_SENIAL", validarRemitoSNCRequest.getSenial())
														   .addValue("P_CLAVE", validarRemitoSNCRequest.getClave());
		 
		Map<String, Object> out = this.validarRemitoSNCCall.execute(in);
		
		if (this.validarCursorErrores(out.get("P_CURSOR"))) {
			return (List<ValidarRemitoSNCResponse>) out.get("P_CURSOR");
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ValidarMasterTituloResponse> validarMasterTitulo(ValidarMasterTituloRequest validarMasterTituloRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CLAVE", validarMasterTituloRequest.getClave())
														   .addValue("P_SENIAL", validarMasterTituloRequest.getSenial())  
														   .addValue("P_CAPITULO", validarMasterTituloRequest.getCapitulo())
														   .addValue("P_PARTE", validarMasterTituloRequest.getParte())   
														   .addValue("P_PROVEEDOR", validarMasterTituloRequest.getProveedor());
		
		Map<String, Object> out = this.validarMasterTituloCall.execute(in);
		return (List<ValidarMasterTituloResponse>) out.get("P_CURSOR");
	}

	@Override
	public ConfirmarSeleccionCapitulosResponse confirmarSeleccionCapitulosTituloRecontratacion(ConfirmarSeleccionCapitulosRequest confirmarSeleccionCapitulosRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", confirmarSeleccionCapitulosRequest.getContrato())          
														   .addValue("P_GRUPO", confirmarSeleccionCapitulosRequest.getGrupo())             
														   .addValue("P_SENIAL", confirmarSeleccionCapitulosRequest.getSenial())            
														   .addValue("P_CLAVE", confirmarSeleccionCapitulosRequest.getClave())             
														   .addValue("P_CAPITULO", confirmarSeleccionCapitulosRequest.getCapitulo())          
														   .addValue("P_PARTE", confirmarSeleccionCapitulosRequest.getParte())             
														   .addValue("P_RECCOPIA", confirmarSeleccionCapitulosRequest.getRecCopia())          
														   .addValue("P_FECHACOPIA", confirmarSeleccionCapitulosRequest.getFechaCopia());
		
		Map<String, Object> out = this.confirmarSeleccionCapitulosTituloRecontratacionCall.execute(in);

		ConfirmarSeleccionCapitulosResponse response = new ConfirmarSeleccionCapitulosResponse();
		response.setRerun("S".equals((String) out.get("P_RERUN")));
		
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CapituloTituloRecontratacion> buscarCapitulosTituloRecontratacion(BuscarCapitulosTituloRecontratacionRequest buscarCapitulosTituloRecontratacionRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CLAVE", buscarCapitulosTituloRecontratacionRequest.getClave())
				   										   .addValue("P_CONTRATO", buscarCapitulosTituloRecontratacionRequest.getContrato())          
				   										   .addValue("P_GRUPO", buscarCapitulosTituloRecontratacionRequest.getGrupo())             
				   										   .addValue("P_SENIAL", buscarCapitulosTituloRecontratacionRequest.getSenial())
														   .addValue("P_CONTRATOPEND", buscarCapitulosTituloRecontratacionRequest.getContratoPendiente())
														   .addValue("P_GRUPOPEND", buscarCapitulosTituloRecontratacionRequest.getGrupoPendiente())
														   .addValue("P_SENIALPEND", buscarCapitulosTituloRecontratacionRequest.getSenialPendiente());
		
		Map<String, Object> out = this.buscarCapitulosTituloRecontratacionCall.execute(in);
		return (List<CapituloTituloRecontratacion>) out.get("P_CURSOR"); 
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ValidarRemitoSNCCapituloRecontratacionResponse> validarRemitoSNCCapituloRecontratacion(ValidarRemitoSNCCapituloRecontratacionRequest validarRemitoSNCCapituloRecontratacionRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", validarRemitoSNCCapituloRecontratacionRequest.getContrato())
														   .addValue("P_GRUPO", validarRemitoSNCCapituloRecontratacionRequest.getGrupo())
														   .addValue("P_SENIAL", validarRemitoSNCCapituloRecontratacionRequest.getSenial())
														   .addValue("P_PROVEEDOR", validarRemitoSNCCapituloRecontratacionRequest.getProveedor())
														   .addValue("P_CLAVE", validarRemitoSNCCapituloRecontratacionRequest.getClave());

		Map<String, Object> out = this.validarRemitoSNCCapituloRecontratacionCall.execute(in);

		if (this.validarCursorErrores(out.get("P_CURSOR"))) {
			return (List<ValidarRemitoSNCCapituloRecontratacionResponse>) out.get("P_CURSOR");
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ValidarRemitoNoSNCResponse> validarRemitoNoSNC(ValidarRemitoNoSNCRequest validarRemitoNoSNCRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CLAVE", validarRemitoNoSNCRequest.getClave())
														   .addValue("P_CAPITULO", validarRemitoNoSNCRequest.getCapitulo())
														   .addValue("P_PARTE", validarRemitoNoSNCRequest.getParte())   
														   .addValue("P_PROVEEDOR", validarRemitoNoSNCRequest.getProveedor())
														   .addValue("P_SENIAL", validarRemitoNoSNCRequest.getSenial());

		Map<String, Object> out = this.validarRemitoNoSNCCall.execute(in);
		return (List<ValidarRemitoNoSNCResponse>) out.get("P_CURSOR");
	}

	@Override
	public ConfirmarSeleccionCapitulosResponse confirmarSeleccionCapituloTituloRecontratacion(ConfirmarSeleccionCapitulosRequest confirmarSeleccionCapitulosRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", confirmarSeleccionCapitulosRequest.getContrato())          
						   								   .addValue("P_GRUPO", confirmarSeleccionCapitulosRequest.getGrupo())             
						   								   .addValue("P_SENIAL", confirmarSeleccionCapitulosRequest.getSenial())            
						   								   .addValue("P_CLAVE", confirmarSeleccionCapitulosRequest.getClave())             
						   								   .addValue("P_CAPITULO", confirmarSeleccionCapitulosRequest.getCapitulo())          
						   								   .addValue("P_PARTE", confirmarSeleccionCapitulosRequest.getParte())
						   								   .addValue("P_PROVEEDOR", confirmarSeleccionCapitulosRequest.getProveedor())
						   								   .addValue("P_RECCOPIA", confirmarSeleccionCapitulosRequest.getRecCopia())          
						   								   .addValue("P_FECHACOPIA", confirmarSeleccionCapitulosRequest.getFechaCopia());
		
		Map<String, Object> out = this.confirmarSeleccionCapituloTituloRecontratacionCall.execute(in);
		
		ConfirmarSeleccionCapitulosResponse response = new ConfirmarSeleccionCapitulosResponse();
		response.setRerun("S".equals((String) out.get("P_RERUN")));
		
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ContratoEnlazadoTituloRecontratacion> contratosEnlazadosTituloRecontratacion(ContratosEnlazadosTituloRecontratacionRequest contratosEnlazadosTituloRecontratacionRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", contratosEnlazadosTituloRecontratacionRequest.getContrato())
														   .addValue("P_GRUPO", contratosEnlazadosTituloRecontratacionRequest.getGrupo())
														   .addValue("P_SENIAL", contratosEnlazadosTituloRecontratacionRequest.getSenial())
														   .addValue("P_CLAVE", contratosEnlazadosTituloRecontratacionRequest.getClave());
		
		Map<String, Object> out = this.contratosEnlazadosTituloRecontratacionCall.execute(in);
		return (List<ContratoEnlazadoTituloRecontratacion>) out.get("P_CURSOR");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ValidarDesenlaceTituloContratoResponse validarDesenlaceTituloContrato(ValidarDesenlaceTituloContratoRequest validarDesenlaceTituloContratoRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", validarDesenlaceTituloContratoRequest.getContrato())
														   .addValue("P_SENIAL", validarDesenlaceTituloContratoRequest.getSenial())
														   .addValue("P_GRUPO", validarDesenlaceTituloContratoRequest.getGrupo())
														   .addValue("P_CLAVE", validarDesenlaceTituloContratoRequest.getClave());
        
        Map<String, Object> out = this.validarDesenlaceTituloContratoCall.execute(in);
        
        ValidarDesenlaceTituloContratoResponse response = new ValidarDesenlaceTituloContratoResponse();
        response.setHayErrores("S".equals((String) out.get("P_ERROR")));
        if (this.validarCursorErrores(out.get("P_ERR_DESENLACE"))) {
        	response.setErrores((List<ErrorDesenlaceResponse>) out.get("P_ERR_DESENLACE"));
        }
        BigDecimal idReporte = ((BigDecimal) out.get("P_IDREPORTE"));
		if (idReporte != null) {
			response.setIdReporte(idReporte.intValue());	
		}
        
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public EjecutarDesenlaceTituloContratoResponse ejecutarDesenlaceTituloContrato(EjecutarDesenlaceTituloContratoRequest ejecutarDesenlaceTituloContratoRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", ejecutarDesenlaceTituloContratoRequest.getContrato())
														   .addValue("P_SENIAL", ejecutarDesenlaceTituloContratoRequest.getSenial())
														   .addValue("P_GRUPO", ejecutarDesenlaceTituloContratoRequest.getGrupo())
														   .addValue("P_CLAVE", ejecutarDesenlaceTituloContratoRequest.getClave());

		Map<String, Object> out = this.ejecutarDesenlaceTituloContratoCall.execute(in);

		EjecutarDesenlaceTituloContratoResponse response = new EjecutarDesenlaceTituloContratoResponse();
		response.setHayErrores("S".equals((String) out.get("P_ERROR")));
		if (this.validarCursorErrores(out.get("P_ERR_DESENLACE"))) {
			response.setErrores((List<ErrorDesenlaceResponse>) out.get("P_ERR_DESENLACE"));
		}
		BigDecimal idReporte = ((BigDecimal) out.get("P_IDREPORTE"));
		if (idReporte != null) {
			response.setIdReporte(idReporte.intValue());	
		}

		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ValidarEnlacePosteriorResponse validarEnlacePosterior(ValidarEnlacePosteriorRequest validarEnlacePosteriorRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATOPOST", validarEnlacePosteriorRequest.getContratoPosterior())
														   .addValue("P_GRUPOPOST", validarEnlacePosteriorRequest.getGrupoPosterior())
														   .addValue("P_CLAVEPOST", validarEnlacePosteriorRequest.getClavePosterior())
														   .addValue("P_SENIALPOST", validarEnlacePosteriorRequest.getSenialPosterior())
														   .addValue("P_CONTRATO", validarEnlacePosteriorRequest.getContrato())
														   .addValue("P_GRUPO", validarEnlacePosteriorRequest.getGrupo())
														   .addValue("P_NUMANT", validarEnlacePosteriorRequest.getNumeroAnterior())
														   .addValue("P_GRUANT", validarEnlacePosteriorRequest.getGrupoAnterior())
														   .addValue("P_NUMPOS", validarEnlacePosteriorRequest.getNumeroPos())
														   .addValue("P_GRUPOS", validarEnlacePosteriorRequest.getGrupoPos())
														   .addValue("P_FECHADESDE", validarEnlacePosteriorRequest.getFechaDesde())
														   .addValue("P_FECHAHASTA", validarEnlacePosteriorRequest.getFechaHasta());
        
		Map<String, Object> out = this.validarEnlacePosteriorCall.execute(in);
		
		ValidarEnlacePosteriorResponse response = new ValidarEnlacePosteriorResponse();
		response.setHayErrores("S".equals((String) out.get("P_ERROR")));
		if (this.validarCursorErrores(out.get("P_ERRORES"))) {
			response.setErroresDesenlace((List<ErrorDesenlaceResponse>) out.get("P_ERRORES"));
		}
//		if (this.validarCursorErrores(out.get("P_NVECTORVIGENCIAS"))) {
//			response.setErroresVigencia((List<ErrorVigenciaResponse>) out.get("P_NVECTORVIGENCIAS"));
//		}
        BigDecimal idReporte = ((BigDecimal) out.get("P_IDREPORTE"));
		if (idReporte != null) {
			response.setIdReporte(idReporte.intValue());	
		}
		
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public EjecutarEnlacePosteriorResponse ejecutarEnlacePosterior(EjecutarEnlacePosteriorRequest ejecutarEnlacePosteriorRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATOPOST", ejecutarEnlacePosteriorRequest.getContratoPosterior())
														   .addValue("P_GRUPOPOST", ejecutarEnlacePosteriorRequest.getGrupoPosterior())
														   .addValue("P_CLAVEPOST", ejecutarEnlacePosteriorRequest.getClavePosterior())
														   .addValue("P_SENIALPOST", ejecutarEnlacePosteriorRequest.getSenialPosterior())
														   .addValue("P_CONTRATO", ejecutarEnlacePosteriorRequest.getContrato())
														   .addValue("P_GRUPO", ejecutarEnlacePosteriorRequest.getGrupo())
														   .addValue("P_NUMANT", ejecutarEnlacePosteriorRequest.getNumeroAnterior())
														   .addValue("P_GRUANT", ejecutarEnlacePosteriorRequest.getGrupoAnterior())
														   .addValue("P_NUMPOS", ejecutarEnlacePosteriorRequest.getNumeroPos())
														   .addValue("P_GRUPOS", ejecutarEnlacePosteriorRequest.getGrupoPos())
														   .addValue("P_FECHADESDE", ejecutarEnlacePosteriorRequest.getFechaDesde())
														   .addValue("P_FECHAHASTA", ejecutarEnlacePosteriorRequest.getFechaHasta());
		
		Map<String, Object> out = this.ejecutarEnlacePosteriorCall.execute(in);
		
		EjecutarEnlacePosteriorResponse response = new EjecutarEnlacePosteriorResponse();
		response.setHayErrores("S".equals((String) out.get("P_ERROR")));
		if (this.validarCursorErrores(out.get("P_NVECTORERROR"))) {
			response.setErroresDesenlace((List<ErrorDesenlaceResponse>) out.get("P_NVECTORERROR"));
		}
		if (this.validarCursorErrores(out.get("P_NVECTORVIGENCIAS"))) {
			response.setErroresVigencia((List<ErrorVigenciaResponse>) out.get("P_NVECTORVIGENCIAS"));
		}
        
        BigDecimal idReporte = ((BigDecimal) out.get("P_IDREPORTE"));
		if (idReporte != null) {
			response.setIdReporte(idReporte.intValue());	
		}
		
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ModificacionVigencia> visualizarModificacionesVigencia(VisualizarModificacionesVigenciaRequest visualizarModificacionesVigenciaRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", visualizarModificacionesVigenciaRequest.getContrato())
														   .addValue("P_GRUPO",visualizarModificacionesVigenciaRequest.getGrupo())
														   .addValue("P_SENIAL", visualizarModificacionesVigenciaRequest.getSenial())
														   .addValue("P_CLAVE", visualizarModificacionesVigenciaRequest.getClave());
		
		Map<String, Object> out = this.visualizarModificacionesVigenciaCall.execute(in);
		return (List<ModificacionVigencia>) out.get("P_CURSOR");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CapituloTituloRecontratacion> buscarCapitulosEliminacion(BuscarCapitulosEliminacionRequest buscarCapitulosEliminacionRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", buscarCapitulosEliminacionRequest.getContrato())
				   										   .addValue("P_GRUPO", buscarCapitulosEliminacionRequest.getGrupo())
				   										   .addValue("P_SENIAL", buscarCapitulosEliminacionRequest.getSenial());
		
		Map<String, Object> out = this.buscarCapitulosEliminacionCall.execute(in);
		return (List<CapituloTituloRecontratacion>) out.get("P_CURSOR");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String validarCantidadCapitulosBaja(ValidarCantidadCapitulosBajaRequest validarCantidadCapitulosBajaRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", validarCantidadCapitulosBajaRequest.getContrato())
				   										   .addValue("P_GRUPO", validarCantidadCapitulosBajaRequest.getGrupo())
				   										   .addValue("P_SENIAL", validarCantidadCapitulosBajaRequest.getSenial());
		
		Map<String, Object> out = this.validarCantidadCapitulosBajaCall.execute(in);
		
		if (out.get("P_CURSOR") != null) {
			return ((List<String>) out.get("P_CURSOR")).get(0);
		} else {
			return StringUtils.EMPTY;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public PrimerValidacionRerunBajaCapitulosResponse primerValidacionRerunBajaCapitulos(PrimerValidacionRerunBajaCapitulosRequest primerValidacionRerunBajaCapitulosRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", primerValidacionRerunBajaCapitulosRequest.getContrato())
														   .addValue("P_GRUPO", primerValidacionRerunBajaCapitulosRequest.getGrupo())
														   .addValue("P_SENIAL", primerValidacionRerunBajaCapitulosRequest.getSenial())
														   .addValue("P_CLAVE", primerValidacionRerunBajaCapitulosRequest.getClave())
														   .addValue("P_CANTCAP", primerValidacionRerunBajaCapitulosRequest.getCantidadCapitulos());
		
		Map<String, Object> out = this.primerValidacionRerunBajaCapitulosCall.execute(in);
		
		PrimerValidacionRerunBajaCapitulosResponse response = new PrimerValidacionRerunBajaCapitulosResponse();
		if (out.get("P_CURSOR") != null) {
			String mensaje = ((List<String>) out.get("P_CURSOR")).get(0);
			if ("OK".equals(mensaje.trim())) {
				mensaje = StringUtils.EMPTY;
			} 
			response.setMensaje(mensaje);
		} else {
			response.setMensaje(StringUtils.EMPTY);
		}
		
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SegundaValidacionRerunBajaCapitulosResponse segundaValidacionRerunBajaCapitulos(SegundaValidacionRerunBajaCapitulosRequest segundaValidacionRerunBajaCapitulosRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", segundaValidacionRerunBajaCapitulosRequest.getContrato())
														   .addValue("P_SENIAL", segundaValidacionRerunBajaCapitulosRequest.getSenial())
				   										   .addValue("P_GRUPO", segundaValidacionRerunBajaCapitulosRequest.getGrupo())
				   										   .addValue("P_CLAVE", segundaValidacionRerunBajaCapitulosRequest.getClave());
		
		Map<String, Object> out = this.segundaValidacionRerunBajaCapitulosCall.execute(in);
		
		SegundaValidacionRerunBajaCapitulosResponse response = new SegundaValidacionRerunBajaCapitulosResponse();
		response.setHayErrores("S".equals((String) out.get("P_ERROR")));
		
		if (this.validarCursorErrores(out.get("P_ERR_DESENLACE"))) {
			response.setErroresDesenlace((List<ErrorDesenlaceResponse>) out.get("P_ERR_DESENLACE"));
		}
		if (this.validarCursorErrores(out.get("P_VIGENCIAS"))) {
			response.setErroresVigencia((List<ErrorVigenciaResponse>) out.get("P_VIGENCIAS"));
		}
        if (out.get("P_IDREPORTE") != null) {
			response.setIdReporte(((BigDecimal) out.get("P_IDREPORTE")).intValue());	
		}
		
		return response;
	}
	
	@Override
	public ConfirmarBajaCapitulosResponse confirmarBajaCapitulos(ConfirmarBajaCapitulosRequest confirmarBajaCapitulosRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", confirmarBajaCapitulosRequest.getContrato())
														   .addValue("P_GRUPO", confirmarBajaCapitulosRequest.getGrupo())
														   .addValue("P_SENIAL", confirmarBajaCapitulosRequest.getSenial())
														   .addValue("P_CLAVE", confirmarBajaCapitulosRequest.getClave())
														   .addValue("P_DESENLACE", confirmarBajaCapitulosRequest.getDesenlace())
														   .addValue("P_CAPITULO", confirmarBajaCapitulosRequest.getCapitulo())
														   .addValue("P_PARTE", confirmarBajaCapitulosRequest.getParte());
		
		Map<String, Object> out = this.confirmarBajaCapitulosCall.execute(in);
		
		ConfirmarBajaCapitulosResponse response = new ConfirmarBajaCapitulosResponse();
		response.setRerun("S".equals((String) out.get("P_RERUN")));
	
		if (out.get("P_IDREPORTE") != null) {
			response.setIdReporte(((BigDecimal) out.get("P_IDREPORTE")).intValue());	
		}
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CapituloTituloRecontratacion> buscarCapitulosAdicion(BuscarCapitulosAdicionRequest buscarCapitulosAdicionRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", buscarCapitulosAdicionRequest.getContrato())
														   .addValue("P_SENIAL", buscarCapitulosAdicionRequest.getSenial())  
														   .addValue("P_GRUPO", buscarCapitulosAdicionRequest.getGrupo())
														   .addValue("P_PROVEEDOR", buscarCapitulosAdicionRequest.getProveedor());
				   

		Map<String, Object> out = this.buscarCapitulosAdicionCall.execute(in);
		return (List<CapituloTituloRecontratacion>) out.get("P_CURSOR");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ValidarRemitoCapitulosSNCResponse> validarRemitoCapitulosSNC(ValidarRemitoCapitulosSNCRequest validarRemitoCapitulosSNCRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", validarRemitoCapitulosSNCRequest.getContrato())
														   .addValue("P_GRUPO", validarRemitoCapitulosSNCRequest.getGrupo())
														   .addValue("P_SENIAL", validarRemitoCapitulosSNCRequest.getSenial())
														   .addValue("P_CLAVE", validarRemitoCapitulosSNCRequest.getClave())
														   .addValue("P_CAPITULO", validarRemitoCapitulosSNCRequest.getCapitulo())
														   .addValue("P_PARTE", validarRemitoCapitulosSNCRequest.getParte())
														   .addValue("P_PROVEEDOR", validarRemitoCapitulosSNCRequest.getProveedor());

		Map<String, Object> out = this.validarRemitoCapitulosSNCCall.execute(in);
		return (List<ValidarRemitoCapitulosSNCResponse>) out.get("P_CURSOR");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ConfirmarAltaCapitulosResponse confirmarAltaCapitulos(ConfirmarAltaCapitulosRequest confirmarAltaCapitulosRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", confirmarAltaCapitulosRequest.getContrato())
														   .addValue("P_GRUPO", confirmarAltaCapitulosRequest.getGrupo())
														   .addValue("P_SENIAL", confirmarAltaCapitulosRequest.getSenial())
				   										   .addValue("P_CLAVE", confirmarAltaCapitulosRequest.getClave())
				   										   .addValue("P_CAPITULO", confirmarAltaCapitulosRequest.getCapitulo())
				   										   .addValue("P_PARTE", confirmarAltaCapitulosRequest.getParte())
				   										   .addValue("P_PROVEEDOR", confirmarAltaCapitulosRequest.getProveedor())
				   										   .addValue("P_FECHAVIGENCIA", confirmarAltaCapitulosRequest.getProveedor())
				   										   .addValue("P_CONFREMI", confirmarAltaCapitulosRequest.getConfRemi()) 
				   										   .addValue("P_CONFCOPIA", confirmarAltaCapitulosRequest.getConfCopia())
				   										   .addValue("P_RERUN", confirmarAltaCapitulosRequest.getRerun())
				   										   .addValue("P_DESDETIT", confirmarAltaCapitulosRequest.getDesdeTitulo());
		Map<String, Object> out;
		if (confirmarAltaCapitulosRequest.isAutomatico()) {
			out = this.confirmarAltaCapitulosAutomaticoCall.execute(in);
		} else {
			out = this.confirmarAltaCapitulosCall.execute(in);
		}
		ConfirmarAltaCapitulosResponse response = new ConfirmarAltaCapitulosResponse();
		response.setDatosMaster((List<DatosMaster>) out.get("P_CURSORMASTER"));
		response.setMensaje(((List<String>) out.get("P_CURSORERROR")).get(0));
		
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TituloEliminarContrato> buscarTitulosEliminarContrato(BuscarTitulosEliminarContratoRequest buscarTitulosEliminarContratoRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", buscarTitulosEliminarContratoRequest.getContrato())
				   										   .addValue("P_GRUPO", buscarTitulosEliminarContratoRequest.getGrupo())
				   										   .addValue("P_SENIAL", buscarTitulosEliminarContratoRequest.getSenial());
		
		Map<String, Object> out = this.buscarTitulosEliminarContratoCall.execute(in);
		return (List<TituloEliminarContrato>) out.get("P_CURSOR");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ValidarEliminacionTituloContratoResponse validarEliminacionTituloContrato(ValidarEliminacionTituloContratoRequest validarEliminacionTituloContratoRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", validarEliminacionTituloContratoRequest.getContrato())
														   .addValue("P_SENIAL", validarEliminacionTituloContratoRequest.getSenial())
														   .addValue("P_GRUPO", validarEliminacionTituloContratoRequest.getGrupo())
														   .addValue("P_CLAVE", validarEliminacionTituloContratoRequest.getClave());
		
		Map<String, Object> out = this.validarEliminacionTituloContratoCall.execute(in);
		
		ValidarEliminacionTituloContratoResponse response = new ValidarEliminacionTituloContratoResponse();
		
		if (this.validarCursorErrores(out.get("P_ERR_DESENLACE"))) {
			response.setErroresDesenlace((List<ErrorDesenlaceResponse>) out.get("P_ERR_DESENLACE"));
		}
        if (out.get("P_IDREPORTE") != null) {
			response.setIdReporte(((BigDecimal) out.get("P_IDREPORTE")).intValue());	
		}
		response.setHayErrores("S".equals((String) out.get("P_ERROR")));
		response.setMensaje((String) out.get("P_MENSAJE"));
        
        return response;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ConfirmarEliminacionTituloContratoResponse confirmarEliminacionTituloContrato(ConfirmarEliminacionTituloContratoRequest confirmarEliminacionTituloContratoRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", confirmarEliminacionTituloContratoRequest.getContrato())
														   .addValue("P_SENIAL", confirmarEliminacionTituloContratoRequest.getSenial())
														   .addValue("P_GRUPO", confirmarEliminacionTituloContratoRequest.getGrupo())
														   .addValue("P_CLAVE", confirmarEliminacionTituloContratoRequest.getClave())
														   .addValue("P_S20PROG", confirmarEliminacionTituloContratoRequest.getS20Prog());
        
		Map<String, Object> out = this.confirmarEliminacionTituloContratoCall.execute(in);
		
        ConfirmarEliminacionTituloContratoResponse response = new ConfirmarEliminacionTituloContratoResponse();
		
		if (this.validarCursorErrores(out.get("P_ERR_DESENLACE"))) {
			response.setErroresDesenlace((List<ErrorDesenlaceResponse>) out.get("P_ERR_DESENLACE"));
		}
        if (out.get("P_IDREPORTE") != null) {
			response.setIdReporte(((BigDecimal) out.get("P_IDREPORTE")).intValue());	
		}
		response.setHayErrores("S".equals((String) out.get("P_ERROR")));
		response.setMensaje((String) out.get("P_MENSAJE"));
        
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TituloGrupo> buscarTitulosGrupoReadOnly(BuscarTitulosGrupoRequest buscarTitulosGrupoRequest) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("P_CONTRATO", buscarTitulosGrupoRequest.getContrato())
														   .addValue("P_GRUPO", buscarTitulosGrupoRequest.getGrupo())
														   .addValue("P_SENIAL", buscarTitulosGrupoRequest.getSenial());
		
		Map<String, Object> out = this.buscarTitulosGrupoReadOnlyCall.execute(in);
		return (List<TituloGrupo>) out.get("P_CURSOR");
	}

}