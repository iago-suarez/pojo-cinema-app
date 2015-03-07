package es.udc.pojo.model.sesion;

import java.util.Calendar;
import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;

/**
 * The Interface SesionDao.
 */
public interface SesionDao extends GenericDao<Sesion, Long> {

    /**
     * Find cartelera.
     *
     * @param idCine
     *            the id cine
     * @param fechaInicio
     *            the fecha inicio
     * @param fechaFin
     *            the fecha fin
     * @return the list
     */
    public List<Sesion> findCartelera(Long idCine, Calendar fechaInicio,
            Calendar fechaFin);

    /**
     * Devuleve el numero de sesiones entre fechaInicio y fechaFin para el cine
     * cine.
     *
     * @param fechaInicio
     *            the fecha inicio
     * @param fechaFin
     *            the fecha fin
     * @param idCine
     *            the id cine
     * @return the int
     */
    public int countByDateAndCine(Calendar fechaInicio, Calendar fechaFin,
            Long idCine);
}
