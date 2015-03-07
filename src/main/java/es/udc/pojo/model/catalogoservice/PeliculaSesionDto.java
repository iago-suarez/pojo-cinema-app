package es.udc.pojo.model.catalogoservice;

import java.util.List;

import es.udc.pojo.model.pelicula.Pelicula;
import es.udc.pojo.model.sesion.Sesion;

public class PeliculaSesionDto {

    private Pelicula     pelicula;
    private List<Sesion> sesiones;

    public PeliculaSesionDto(Pelicula pelicula, List<Sesion> sesiones) {

        this.pelicula = pelicula;
        this.sesiones = sesiones;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public List<Sesion> getSesiones() {
        return sesiones;
    }

    public void setSesiones(List<Sesion> sesiones) {
        this.sesiones = sesiones;
    }

}
