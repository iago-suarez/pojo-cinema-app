package es.udc.pojo.model.pelicula;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@Repository("peliculaDao")
public class PeliculaDaoHibernate extends GenericDaoHibernate<Pelicula, Long>
        implements PeliculaDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<Pelicula> findPeliculasByCine(Long idCine, Calendar fecha)
            throws InstanceNotFoundException {

        return getSession()
                .createQuery(
                        "SELECT s.pelicula " + "FROM  Sesion s "
                                + "WHERE s.sala.cine.idCine = :idCine "
                                + "AND DATE(s.horayFecha) = :fecha ")
                .setParameter("idCine", idCine)
                .setParameter("fecha", fecha.getTime()).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Pelicula> findPeliculasActivasADia(Calendar dia) {

        return getSession()
                .createQuery(
                        "SELECT p " + "FROM Pelicula p "
                                + "WHERE :dia BETWEEN p.fInicio AND p.fFin "
                                + "ORDER BY p.titulo").setCalendar("dia", dia)
                .list();

    }
}
