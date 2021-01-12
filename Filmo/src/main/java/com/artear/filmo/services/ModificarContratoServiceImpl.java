package com.artear.filmo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.artear.filmo.daos.interfaces.ModificarContratoDao;
import com.artear.filmo.services.interfaces.ModificarContratoService;

@Transactional
@Service("modificarContratoService")
public class ModificarContratoServiceImpl implements ModificarContratoService {

    @Autowired
    private ModificarContratoDao modificarContratoDao;

    @Override
    public List<Contrato> dameContrato(Integer contrato) {
        return modificarContratoDao.dameContrato(contrato);
    }

    @Override
    public List<Contrato> dameContratosPorDistribuidor(Integer claveDistribuidor) {
        return modificarContratoDao.dameContratosPorDistribuidor(claveDistribuidor);
    }

    @Override
    public List<Distribuidor> dameDistribuidoresPorNombre(String nombre) {
        return modificarContratoDao.dameDistribuidoresPorNombre(nombre);
    }

    @Override
    public List<Contrato> dameContratoConCabecera(Integer contrato, Integer distribuidor) {
        return modificarContratoDao.dameContratoConCabecera(contrato, distribuidor);
    }

    @Override
    public List<ObservacionDePago> dameObservacionesDePago(Integer contrato) {
        return modificarContratoDao.dameObservacionesDePago(contrato);
    }

    @Override
    public List<Moneda> dameMonedas() {
        return modificarContratoDao.dameMonedas();
    }

    @Override
    public List<SenialImporte> dameSenialImporte(Integer contrato) {
        return modificarContratoDao.dameSenialImporte(contrato);
    }

    @Override
    public List<Grupo> dameGrupos(Integer claveContrato, String senial) {
        return modificarContratoDao.dameGrupos(claveContrato, senial);
    }

    @Override
    public Grupo dameGrupo(Integer claveContrato, String senial, Integer claveGrupo) {
        return modificarContratoDao.dameGrupo(claveContrato, senial, claveGrupo);
    }

    @Override
    public List<Senial> dameSenialesHeredadasAsignadas(Integer claveContrato) {
        return modificarContratoDao.dameSenialesHeredadasAsignadas(claveContrato);
    }

    @Override
    public List<TipoImporte> dameTiposDeImporte() {
        return modificarContratoDao.dameTiposDeImporte();
    }

    @Override
    public List<TipoDerecho> dameTiposDeDerecho() {
        return modificarContratoDao.dameTiposDeDerecho();
    }

    @Override
    public List<TipoDerechoTerritorial> dameTiposDeDerechoTerritorial() {
        return modificarContratoDao.dameTiposDeDerechoTerritorial();
    }

    @Override
    public List<TipoTitulo> dameTiposDeTitulo() {
        return modificarContratoDao.dameTiposDeTitulo();
    }

    @Override
    public List<Titulo> dameTitulos(TitulosRequest titulosRequest) {
        return modificarContratoDao.dameTitulos(titulosRequest);
    }

    @Override
    public List<TituloContratado> dameTitulosAEliminarPorContrato(TitulosRequest titulosRequest) {
        return modificarContratoDao.dameTitulosAEliminarPorContrato(titulosRequest);
    }

    @Override
    public List<TituloContratado> dameTitulosAReemplazarPorContrato(TitulosRequest titulosRequest) {
        return modificarContratoDao.dameTitulosAReemplazarPorContrato(titulosRequest);
    }

    @Override
    public List<Capitulo> dameCapitulosParaEliminar(TitulosRequest titulosRequest) {
        return modificarContratoDao.dameCapitulosParaEliminar(titulosRequest);
    }

    @Override
    public List<Capitulo> dameCapitulosParaAgregar(TitulosRequest titulosRequest) {
        return modificarContratoDao.dameCapitulosParaAgregar(titulosRequest);
    }

    @Override
    public List<TituloContratado> dameTitulosPorContrato(TitulosRequest titulosRequest) {
        return modificarContratoDao.dameTitulosPorContrato(titulosRequest);
    }

    @Override
    public List<TituloContratado> dameTitulosARecontratar(TitulosRecontratadosRequest titulosRecontratadosRequest) {
        return modificarContratoDao.dameTitulosARecontratar(titulosRecontratadosRequest);
    }

    @Override
    public List<Capitulo> dameCapitulosARecontratar(TitulosRecontratadosRequest titulosRecontratadosRequest) {
        return modificarContratoDao.dameCapitulosARecontratar(titulosRecontratadosRequest);
    }

    @Override
    public List<ContratoConReRun> dameContratosConReRun(TitulosRecontratadosRequest titulosRecontratadosRequest) {
        return modificarContratoDao.dameContratosConReRun(titulosRecontratadosRequest);
    }

    @Override
    public List<Capitulo> dameCapitulosPorTituloContratado(TitulosRecontratadosRequest titulosRecontratadosRequest) {
        return modificarContratoDao.dameCapitulosPorTituloContratado(titulosRecontratadosRequest);
    }

    @Override
    @Transactional(readOnly = false)
    public Titulo dameTitulo(TituloRequest tituloRequest) {
        return modificarContratoDao.dameTitulo(tituloRequest);
    }
    
    @Override
    public List<Vigencia> dameVigenciasPorTituloContratado(TitulosRecontratadosRequest titulosRecontratadosRequest) {
        return modificarContratoDao.dameVigenciasPorTituloContratado(titulosRecontratadosRequest);    
    }

    @Override
    public List<Senial> dameSenialesHeredadas(String senial) {
        return modificarContratoDao.dameSenialesHeredadas(senial);
    }

    @Override
    public Integer dameSiguienteNumeroGrupo(Integer claveContrato, String senial) {
        return modificarContratoDao.dameSiguienteNumeroGrupo(claveContrato, senial);
    }

    @Override
    public TituloConGrupo dameTituloContratado(TituloRequest tituloRequest, String usuario) {
        return modificarContratoDao.dameTituloContratado(tituloRequest, usuario);
    }

    @Override
    public Vigencia damePayTV(VigenciaRequest vigenciaRequest, String usuario) {
        return modificarContratoDao.damePayTV(vigenciaRequest, usuario);
    }

    @Override
    public List<Grupo> dameGruposConReemplazo(GruposConReemplazoRequest gruposConReemplazoRequest) {
        return modificarContratoDao.dameGruposConReemplazo(gruposConReemplazoRequest);
    }

    @Override
    public List<Senial> dameSenialesChequeo(Integer claveContrato, String usuario) {
        return modificarContratoDao.dameSenialesChequeo(claveContrato, usuario);
    }

    @Override
    public String dameClave(String tipoTitulo) {
        return modificarContratoDao.dameClave(tipoTitulo);
    }

    @Override
    public List<DameTGResponse> dameTG(DameTGRequest dameTGRequest, String usuario) {
        return modificarContratoDao.dameTG(dameTGRequest, usuario);
    }

    @Override
    public List<TituloContratado> dameTitulosConReRun(TitulosRequest titulosRequest, String usuario) {
        return modificarContratoDao.dameTitulosConReRun(titulosRequest, usuario);
    }

    @Override
    public String dameNombreDelTitulo(String claveTitulo) {
        return modificarContratoDao.dameNombreDelTitulo(claveTitulo);
    }
}
