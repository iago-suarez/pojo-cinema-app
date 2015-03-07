package es.udc.pojo.model.sesion;

import java.util.Calendar;
import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;

public interface SesionDao extends GenericDao<Sesion, Long> {

    public List<Sesion> findCartelera(Long idCine, Calendar fechaInicio,
            Calendar fechaFin);

    /**
     * Devuleve el numero de sesiones entre fechaInicio y fechaFin para el cine
     * cine
     * 
     * @param fechaInicio
     * @param fechaFin
     * @param idCine
     * @return
     */
    public int countByDateAndCine(Calendar fechaInicio, Calendar fechaFin,
            Long idCine);
}
