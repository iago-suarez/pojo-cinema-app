package es.udc.pojo.model.catalogoservice;

import java.util.List;

import es.udc.pojo.model.pelicula.Pelicula;
import es.udc.pojo.model.sesion.Sesion;

/**
 * The Class PeliculaSesionDto.
 */
public class PeliculaSesionDto {

    /** The pelicula. */
    private Pelicula     pelicula;

    /** The sesiones. */
    private List<Sesion> sesiones;

    /**
     * Instantiates a new pelicula sesion dto.
     *
     * @param pelicula
     *            the pelicula
     * @param sesiones
     *            the sesiones
     */
    public PeliculaSesionDto(Pelicula pelicula, List<Sesion> sesiones) {

        this.pelicula = pelicula;
        this.sesiones = sesiones;
    }

    /**
     * Gets the pelicula.
     *
     * @return the pelicula
     */
    public Pelicula getPelicula() {
        return pelicula;
    }

    /**
     * Sets the pelicula.
     *
     * @param pelicula
     *            the new pelicula
     */
    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    /**
     * Gets the sesiones.
     *
     * @return the sesiones
     */
    public List<Sesion> getSesiones() {
        return sesiones;
    }

    /**
     * Sets the sesiones.
     *
     * @param sesiones
     *            the new sesiones
     */
    public void setSesiones(List<Sesion> sesiones) {
        this.sesiones = sesiones;
    }

}
