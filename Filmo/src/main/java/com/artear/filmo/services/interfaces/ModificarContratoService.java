package com.artear.filmo.services.interfaces;

import java.util.List;

import com.artear.filmo.bo.common.Distribuidor;
import com.artear.filmo.bo.common.Moneda;
import com.artear.filmo.bo.common.Senial;
import com.artear.filmo.bo.contratos.Capitulo;
import com.artear.filmo.bo.contratos.Contrato;
import com.artear.filmo.bo.contratos.ContratoConReRun;
import com.artear.filmo.bo.contratos.DameTGRequest;
import com.artear.filmo.bo.contratos.DameTGResponse;
import com.artear.filmo.bo.contratos.Grupo;
import com.artear.filmo.bo.contratos.GruposConReemplazoRequest;
import com.artear.filmo.bo.contratos.ObservacionDePago;
import com.artear.filmo.bo.contratos.SenialImporte;
import com.artear.filmo.bo.contratos.TipoDerecho;
import com.artear.filmo.bo.contratos.TipoDerechoTerritorial;
import com.artear.filmo.bo.contratos.TipoImporte;
import com.artear.filmo.bo.contratos.TipoTitulo;
import com.artear.filmo.bo.contratos.Titulo;
import com.artear.filmo.bo.contratos.TituloConGrupo;
import com.artear.filmo.bo.contratos.TituloContratado;
import com.artear.filmo.bo.contratos.TituloRequest;
import com.artear.filmo.bo.contratos.TitulosRecontratadosRequest;
import com.artear.filmo.bo.contratos.TitulosRequest;
import com.artear.filmo.bo.contratos.Vigencia;
import com.artear.filmo.bo.contratos.VigenciaRequest;

/**
 * 
 * @author mtito
 * 
 */
public interface ModificarContratoService {

    List<Contrato> dameContrato(Integer contrato);

    List<Contrato> dameContratosPorDistribuidor(Integer claveDistribuir);

    List<Distribuidor> dameDistribuidoresPorNombre(String nombre);

    List<Contrato> dameContratoConCabecera(Integer contrato, Integer distribuidor);
    
    List<ObservacionDePago> dameObservacionesDePago(Integer contrato);

    List<Moneda> dameMonedas();
    
    List<SenialImporte> dameSenialImporte(Integer contrato);

    List<Grupo> dameGrupos(Integer claveContrato, String senial);

    Grupo dameGrupo(Integer claveContrato, String senial, Integer claveGrupo);

    List<Senial> dameSenialesHeredadasAsignadas(Integer claveContrato);

    List<TipoImporte> dameTiposDeImporte();

    List<TipoDerecho> dameTiposDeDerecho();

    List<TipoDerechoTerritorial> dameTiposDeDerechoTerritorial();

    List<TipoTitulo> dameTiposDeTitulo();

    List<Titulo> dameTitulos(TitulosRequest titulosRequest);

    List<TituloContratado> dameTitulosAEliminarPorContrato(TitulosRequest titulosRequest);

    List<TituloContratado> dameTitulosAReemplazarPorContrato(TitulosRequest titulosRequest);

    List<Capitulo> dameCapitulosParaEliminar(TitulosRequest titulosRequest);

    List<Capitulo> dameCapitulosParaAgregar(TitulosRequest titulosRequest);

    List<TituloContratado> dameTitulosPorContrato(TitulosRequest titulosRequest);

    List<TituloContratado> dameTitulosARecontratar(TitulosRecontratadosRequest contratosRequest);

    List<Capitulo> dameCapitulosARecontratar(TitulosRecontratadosRequest titulosRecontratadosRequest);

    List<ContratoConReRun> dameContratosConReRun(TitulosRecontratadosRequest titulosRecontratadosRequest);

    List<Capitulo> dameCapitulosPorTituloContratado(TitulosRecontratadosRequest titulosRecontratadosRequest);

    Titulo dameTitulo(TituloRequest tituloRequest);

    List<Senial> dameSenialesHeredadas(String senial);
    
    Integer dameSiguienteNumeroGrupo(Integer claveContrato, String senial);
    
    List<Vigencia> dameVigenciasPorTituloContratado(TitulosRecontratadosRequest titulosRecontratadosRequest);

    TituloConGrupo dameTituloContratado(TituloRequest tituloRequest, String usuario);

    Vigencia damePayTV(VigenciaRequest vigenciaRequest, String usuario);

    List<Grupo> dameGruposConReemplazo(GruposConReemplazoRequest gruposConReemplazoRequest);

    List<Senial> dameSenialesChequeo(Integer claveContrato, String usuario);

    String dameClave(String tipoTitulo);

    List<DameTGResponse> dameTG(DameTGRequest dameTGRequest, String usuario);

    List<TituloContratado> dameTitulosConReRun(TitulosRequest titulosRequest, String usuario);

    String dameNombreDelTitulo(String claveTitulo);
}
