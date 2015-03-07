package es.udc.pojo.model.sesion;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

/**
 * The Class SesionDaoHibernate.
 */
@Repository("sesionDao")
public class SesionDaoHibernate extends GenericDaoHibernate<Sesion, Long>
        implements SesionDao {

    /*
     * (non-Javadoc)
     * 
     * @see es.udc.pojo.model.sesion.SesionDao#findCartelera(java.lang.Long,
     * java.util.Calendar, java.util.Calendar)
     */
    @SuppressWarnings("unchecked")
    public List<Sesion> findCartelera(Long idCine, Calendar fechaInicio,
            Calendar fechaFin) {

        return getSession()
                .createQuery(
                        "SELECT s FROM Sesion s "
                                + "WHERE s.sala.cine.idCine = :idCine "
                                + "AND s.horayFecha BETWEEN :fechaInicio AND :fechaFin "
                                + "ORDER BY s.pelicula.titulo, s.horayFecha")
                .setParameter("idCine", idCine)
                .setCalendar("fechaInicio", fechaInicio)
                .setCalendar("fechaFin", fechaFin).list();

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.udc.pojo.model.sesion.SesionDao#countByDateAndCine(java.util.Calendar,
     * java.util.Calendar, java.lang.Long)
     */
    public int countByDateAndCine(Calendar fechaInicio, Calendar fechaFin,
            Long idCine) {
        long number = (Long) getSession()
                .createQuery(
                        "SELECT COUNT(s) FROM Sesion s "
                                + "WHERE s.sala.cine.idCine = :idCine "
                                + "AND s.horayFecha BETWEEN :fechaInicio AND :fechaFin "
                                + "ORDER BY s.pelicula.titulo")
                .setCalendar("fechaInicio", fechaInicio)
                .setCalendar("fechaFin", fechaFin)
                .setParameter("idCine", idCine).uniqueResult();
        return (int) number;
    }
}
