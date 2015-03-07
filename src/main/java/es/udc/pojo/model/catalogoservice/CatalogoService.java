package es.udc.pojo.model.catalogoservice;

import java.util.Calendar;
import java.util.List;

import es.udc.pojo.model.cine.Cine;
import es.udc.pojo.model.pelicula.Pelicula;
import es.udc.pojo.model.provincia.Provincia;
import es.udc.pojo.model.sesion.Sesion;
import es.udc.pojo.model.util.BadArgumentException;
import es.udc.pojo.model.util.CoincidentSesionsException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public interface CatalogoService {

    public List<Provincia> findProvincias();

    public Provincia findProvincia(Long idProvincia)
            throws InstanceNotFoundException;

    public Cine findCine(Long idCine) throws InstanceNotFoundException;

    public List<PeliculaSesionDto> findPeliculas(Long idCine, Calendar fecha);

    public void createSesions(List<Sesion> sesiones, Calendar fecha, Long idCine)
            throws BadArgumentException, CoincidentSesionsException;

    public void validarSesions(List<Sesion> sesiones, Calendar fecha,
            Long idCine) throws BadArgumentException,
            CoincidentSesionsException;

    public Pelicula findPelicula(Long idPelicula)
            throws InstanceNotFoundException;

    public Sesion findSesion(Long idSesion) throws InstanceNotFoundException;

    public List<Pelicula> findPeliculasActivasADia(Calendar day);
}
