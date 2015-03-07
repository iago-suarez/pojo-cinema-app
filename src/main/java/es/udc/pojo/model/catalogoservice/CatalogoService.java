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

/**
 * The Interface CatalogoService.
 */
public interface CatalogoService {

    /**
     * Find provincias.
     *
     * @return the list
     */
    public List<Provincia> findProvincias();

    /**
     * Find provincia.
     *
     * @param idProvincia
     *            the id provincia
     * @return the provincia
     * @throws InstanceNotFoundException
     *             the instance not found exception
     */
    public Provincia findProvincia(Long idProvincia)
            throws InstanceNotFoundException;

    /**
     * Find cine.
     *
     * @param idCine
     *            the id cine
     * @return the cine
     * @throws InstanceNotFoundException
     *             the instance not found exception
     */
    public Cine findCine(Long idCine) throws InstanceNotFoundException;

    /**
     * Find peliculas.
     *
     * @param idCine
     *            the id cine
     * @param fecha
     *            the fecha
     * @return the list
     */
    public List<PeliculaSesionDto> findPeliculas(Long idCine, Calendar fecha);

    /**
     * Creates the sesions.
     *
     * @param sesiones
     *            the sesiones
     * @param fecha
     *            the fecha
     * @param idCine
     *            the id cine
     * @throws BadArgumentException
     *             the bad argument exception
     * @throws CoincidentSesionsException
     *             the coincident sesions exception
     */
    public void createSesions(List<Sesion> sesiones, Calendar fecha, Long idCine)
            throws BadArgumentException, CoincidentSesionsException;

    /**
     * Validar sesions.
     *
     * @param sesiones
     *            the sesiones
     * @param fecha
     *            the fecha
     * @param idCine
     *            the id cine
     * @throws BadArgumentException
     *             the bad argument exception
     * @throws CoincidentSesionsException
     *             the coincident sesions exception
     */
    public void validarSesions(List<Sesion> sesiones, Calendar fecha,
            Long idCine) throws BadArgumentException,
            CoincidentSesionsException;

    /**
     * Find pelicula.
     *
     * @param idPelicula
     *            the id pelicula
     * @return the pelicula
     * @throws InstanceNotFoundException
     *             the instance not found exception
     */
    public Pelicula findPelicula(Long idPelicula)
            throws InstanceNotFoundException;

    /**
     * Find sesion.
     *
     * @param idSesion
     *            the id sesion
     * @return the sesion
     * @throws InstanceNotFoundException
     *             the instance not found exception
     */
    public Sesion findSesion(Long idSesion) throws InstanceNotFoundException;

    /**
     * Find peliculas activas a dia.
     *
     * @param day
     *            the day
     * @return the list
     */
    public List<Pelicula> findPeliculasActivasADia(Calendar day);
}
