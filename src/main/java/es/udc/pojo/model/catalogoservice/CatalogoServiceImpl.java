package es.udc.pojo.model.catalogoservice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pojo.model.cine.Cine;
import es.udc.pojo.model.cine.CineDao;
import es.udc.pojo.model.pelicula.Pelicula;
import es.udc.pojo.model.pelicula.PeliculaDao;
import es.udc.pojo.model.provincia.Provincia;
import es.udc.pojo.model.provincia.ProvinciaDao;
import es.udc.pojo.model.sala.SalaDao;
import es.udc.pojo.model.sesion.Sesion;
import es.udc.pojo.model.sesion.SesionDao;
import es.udc.pojo.model.util.BadArgumentException;
import es.udc.pojo.model.util.CoincidentSesionsException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

/**
 * The Class CatalogoServiceImpl.
 */
@Service("catalogoService")
@Transactional
public class CatalogoServiceImpl implements CatalogoService {

    /** The provincia dao. */
    @Autowired
    private ProvinciaDao provinciaDao;

    /** The cine dao. */
    @Autowired
    private CineDao      cineDao;

    /** The pelicula dao. */
    @Autowired
    private PeliculaDao  peliculaDao;

    /** The sesion dao. */
    @Autowired
    private SesionDao    sesionDao;

    /** The sala dao. */
    @Autowired
    private SalaDao      salaDao;

    /**
     * Gets the next day init.
     *
     * @param today
     *            the today
     * @return the next day init
     * @Receive: Dia del que deseamos calcular el siguiente Return : Calendar
     *           con el momento en el comienza el proximo dia
     */
    private Calendar getNextDayInit(Calendar today) {

        Calendar tomorrow = Calendar.getInstance();
        tomorrow.set(Calendar.YEAR, today.get(Calendar.YEAR));
        tomorrow.set(Calendar.MONTH, today.get(Calendar.MONTH));

        if (today.get(Calendar.HOUR_OF_DAY) < 5) {
            tomorrow.set(Calendar.DAY_OF_MONTH,
                    today.get(Calendar.DAY_OF_MONTH));
        } else {
            tomorrow.set(Calendar.DAY_OF_MONTH,
                    today.get(Calendar.DAY_OF_MONTH) + 1);
        }

        /*
         * Si el dia en que buscamos no es el de hoy querremos recuperar todas
         * las peliculas del dia logico
         */

        if (today.get(Calendar.DAY_OF_MONTH) != Calendar.getInstance().get(
                Calendar.DAY_OF_MONTH)) {
            today.set(Calendar.HOUR_OF_DAY, 5);
        }

        /* Establecemos 4:59 como limite para las sesiones de un dia */
        tomorrow.set(Calendar.HOUR_OF_DAY, 4);
        tomorrow.set(Calendar.MINUTE, 59);

        return tomorrow;
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.udc.pojo.model.catalogoservice.CatalogoService#findProvincias()
     */
    @Transactional(readOnly = true)
    public List<Provincia> findProvincias() {

        return provinciaDao.findAll();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.udc.pojo.model.catalogoservice.CatalogoService#findCine(java.lang.
     * Long)
     */
    @Transactional(readOnly = true)
    public Cine findCine(Long idCine) throws InstanceNotFoundException {
        return cineDao.find(idCine);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.udc.pojo.model.catalogoservice.CatalogoService#findPeliculas(java.
     * lang.Long, java.util.Calendar)
     */
    @Transactional(readOnly = true)
    public List<PeliculaSesionDto> findPeliculas(Long idCine, Calendar fecha) {

        List<PeliculaSesionDto> L = new ArrayList<PeliculaSesionDto>();

        // sesiones de ese dia para ese cine ordenadas por el titulo de la
        // pelicula

        Calendar diaSiguiente = getNextDayInit(fecha);

        // Con esto traemos las sesiones que están etre las 5:00 del dia deseado
        // y las 4:59 de mañana, que como dia logico sigue siendo hoy.
        List<Sesion> sesiones = sesionDao.findCartelera(idCine, fecha,
                diaSiguiente);

        Pelicula previa = null;
        int index = 0;
        for (Sesion s : sesiones) {
            Pelicula actual = s.getPelicula();

            if (actual == previa) { // si la pelicula coincide con la
                                    // anterior, se añade a su lista de
                                    // sesiones, esto se permite por
                                    // estar las sesiones ordenadas por
                                    // pelicula
                L.get(index).getSesiones().add(s);
            } else {
                List<Sesion> aux = new ArrayList<Sesion>(); // sino añadimos un
                                                            // nuevo elemento a
                                                            // la lista de dtos
                aux.add(s);
                PeliculaSesionDto p = new PeliculaSesionDto(actual, aux);
                L.add(p);
                if (previa != null)
                    index++; // La primera vez que entra en el bucle la pelicula
                             // nunca coincide, pero no debemos avanzar el
                             // cursor

            }

            previa = actual;
        }

        return L;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.udc.pojo.model.catalogoservice.CatalogoService#createSesions(java.
     * util.List, java.util.Calendar, java.lang.Long)
     */
    public void createSesions(List<Sesion> sesiones, Calendar fecha, Long idCine)
            throws BadArgumentException, CoincidentSesionsException {
        validarSesions(sesiones, fecha, idCine);
        /* Comprobamos que las sesiones tienen la misma fecha */
        for (Sesion s : sesiones) {
            sesionDao.save(s);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.udc.pojo.model.catalogoservice.CatalogoService#validarSesions(java
     * .util.List, java.util.Calendar, java.lang.Long)
     */
    @Override
    public void validarSesions(List<Sesion> sesiones, Calendar fecha,
            Long idCine) throws BadArgumentException,
            CoincidentSesionsException {

        Calendar f = (Calendar) fecha.clone();

        if (f.before(Calendar.getInstance()))
            throw new BadArgumentException(
                    "La fecha ha de ser posterior a la fecha actual");

        // sesiones de ese dia para ese cine ordenadas por el titulo de la
        // pelicula
        f.set(Calendar.HOUR_OF_DAY, 5);// Establecemos el inicio lógico de
                                       // un dia = 5:00
        f.set(Calendar.MINUTE, 0);

        Calendar nextDay = getNextDayInit(f);

        /* Comprobamos que en esa fecha y para ese cine no hay sesiones añadidas */
        int sesdia = sesionDao.countByDateAndCine(f, nextDay, idCine);
        if (sesdia > 0)
            throw new BadArgumentException("Ya existen sesiones en esa fecha.");

        /* Comprobamos que las sesiones tienen la misma fecha y el mismo cine */
        for (Sesion s1 : sesiones) {
            if (s1.getHorayFecha().after(nextDay)
                    || s1.getHorayFecha().before(fecha))
                throw new BadArgumentException(
                        "Las sesiones no son del mismo dia.");
            if (!s1.getSala().getCine().getIdCine().equals(idCine))
                throw new BadArgumentException(
                        "Las sesiones no son del mismo cine.");

            /* Comprbamos que ambas sesiones no se pisen en tiempo y sala */
            for (Sesion s2 : sesiones) {
                // Si es la misma sala, y la sala que estamos observando no es
                // ella misma
                if ((s1.getSala().getIdSala() == s2.getSala().getIdSala())
                        && (s1 != s2)) {
                    if (s2.getHorayFecha().after(s1.getHorayFecha())) {
                        /* Tenemos que ver si s2 comienza antes de que acabe s1 */
                        Calendar finS1 = (Calendar) s1.getHorayFecha().clone();
                        finS1.add(Calendar.MINUTE, s1.getPelicula()
                                .getDuracion());
                        if (s2.getHorayFecha().before(finS1))
                            throw new CoincidentSesionsException(s1, s2);
                    } else {
                        /* Tenemos que ver si s1 comienza antes de que acabe s2 */
                        Calendar finS2 = (Calendar) s2.getHorayFecha().clone();
                        finS2.add(Calendar.MINUTE, s2.getPelicula()
                                .getDuracion());
                        if (s1.getHorayFecha().before(finS2))
                            throw new CoincidentSesionsException(s1, s2);
                    }
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.udc.pojo.model.catalogoservice.CatalogoService#findPelicula(java.lang
     * .Long)
     */
    @Override
    @Transactional(readOnly = true)
    public Pelicula findPelicula(Long idPelicula)
            throws InstanceNotFoundException {
        return peliculaDao.find(idPelicula);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.udc.pojo.model.catalogoservice.CatalogoService#findSesion(java.lang
     * .Long)
     */
    @Override
    @Transactional(readOnly = true)
    public Sesion findSesion(Long idSesion) throws InstanceNotFoundException {
        return sesionDao.find(idSesion);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.udc.pojo.model.catalogoservice.CatalogoService#findProvincia(java.
     * lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public Provincia findProvincia(Long idProvincia)
            throws InstanceNotFoundException {
        return provinciaDao.find(idProvincia);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.udc.pojo.model.catalogoservice.CatalogoService#findPeliculasActivasADia
     * (java.util.Calendar)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Pelicula> findPeliculasActivasADia(Calendar day) {
        return peliculaDao.findPeliculasActivasADia(day);
    }

}
