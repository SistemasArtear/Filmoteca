package com.artear.filmo.services.interfaces;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.artear.filmo.bo.programacion.Capitulo;
import com.artear.filmo.bo.programacion.ParteSegmento;
import com.artear.filmo.bo.programacion.Programa;
import com.artear.filmo.bo.programacion.Titulo;
import com.artear.filmo.bo.programacion.TituloProgramado;
import com.artear.filmo.bo.programacion.TituloRequest;
import com.artear.filmo.bo.programacion.ValidationResult;

/**
 * 
 * @author flvaldes
 * 
 */
public interface ArmadoDeProgramacionService {

    List<Programa> levantarListaDeProgramas(String idSenial);

    List<TituloProgramado> obtenerGrillaProgramada(String idSenial, int programa, Date fechaSituar, Date fechaHasta);

    List<ParteSegmento> obtenerParteSegmento(String clave, int nroCapitulo);

    List<Capitulo> obtenerCapitulos(String clave);

    List<Titulo> obtenerListaTitulosProgramar(String idSenial, String tituloCast, String familiaTitulo);

    Titulo obtenerDatosTitulo(String clave, Integer nroCapitulo, Integer parte, Integer segmento);

    List<ValidationResult> validarAltaProgramacion(TituloRequest titulo, String usuario) throws ParseException;
    
    List<ValidationResult> validarBajaProgramacion(TituloRequest titulo) throws ParseException;
    
    List<ValidationResult> validarModificaProgramacion(TituloRequest titulo, String usuario) throws ParseException;
    
    boolean altaProgramacion(TituloRequest titulo, String usuario) throws ParseException;
    
    boolean bajaProgramacion(TituloRequest titulo) throws ParseException;
    
    boolean modificaProgramacion(TituloRequest titulo, String usuario) throws ParseException;

    String obtenerObservaciones(TituloRequest tituloRequest, String usuario) throws ParseException;

    Boolean esUnaClaveValida(String claveTitulo);
    
    Boolean esUnCapituloValido(String claveTitulo, Integer nroCapitulo);
}
