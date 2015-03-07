package es.udc.pojo.model.pelicula;

import java.util.Calendar;
import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

/**
 * The Interface PeliculaDao.
 */
public interface PeliculaDao extends GenericDao<Pelicula, Long> {

    /**
     * Find peliculas by cine.
     *
     * @param idCine
     *            the id cine
     * @param fecha
     *            the fecha
     * @return the list
     * @throws InstanceNotFoundException
     *             the instance not found exception
     */
    public List<Pelicula> findPeliculasByCine(Long idCine, Calendar fecha)
            throws InstanceNotFoundException;

    /**
     * Find peliculas activas a dia.
     *
     * @param dia
     *            the dia
     * @return the list
     */
    public List<Pelicula> findPeliculasActivasADia(Calendar dia);
}
