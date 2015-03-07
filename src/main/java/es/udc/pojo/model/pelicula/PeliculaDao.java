package es.udc.pojo.model.pelicula;

import java.util.Calendar;
import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public interface PeliculaDao extends GenericDao<Pelicula, Long> {

    public List<Pelicula> findPeliculasByCine(Long idCine, Calendar fecha)
            throws InstanceNotFoundException;

    public List<Pelicula> findPeliculasActivasADia(Calendar dia);
}
