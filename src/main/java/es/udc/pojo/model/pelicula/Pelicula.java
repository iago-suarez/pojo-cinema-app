package es.udc.pojo.model.pelicula;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The Class Pelicula.
 */
@Entity
public class Pelicula {

    /** The id pelicula. */
    private Long     idPelicula;

    /** The titulo. */
    private String   titulo;

    /** The fin. */
    private Calendar fFin;

    /** The inicio. */
    private Calendar fInicio;

    /** The duracion. */
    private int      duracion;

    /** The resumen. */
    private String   resumen;

    /**
     * Instantiates a new pelicula.
     */
    public Pelicula() {
    }

    /**
     * Instantiates a new pelicula.
     *
     * @param titulo
     *            the titulo
     * @param fInicio
     *            the f inicio
     * @param fFin
     *            the f fin
     * @param duracion
     *            the duracion
     * @param resumen
     *            the resumen
     */
    public Pelicula(String titulo, Calendar fInicio, Calendar fFin,
            int duracion, String resumen) {

        /**
         * NOTE: "idPelicula" *must* be left as "null" since its value is
         * automatically generated.
         */

        this.titulo = titulo;
        this.fFin = fFin;
        this.fInicio = fInicio;
        this.duracion = duracion;
        this.resumen = resumen;
    }

    /**
     * Gets the id pelicula.
     *
     * @return the id pelicula
     */
    @SequenceGenerator(name = "PeliculaIdGenerator",
            sequenceName = "PeliculaSeq")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "PeliculaIdGenerator")
    public Long getIdPelicula() {
        return idPelicula;
    }

    /**
     * Sets the id pelicula.
     *
     * @param idPelicula
     *            the new id pelicula
     */
    public void setIdPelicula(Long idPelicula) {
        this.idPelicula = idPelicula;
    }

    /**
     * Gets the titulo.
     *
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Sets the titulo.
     *
     * @param titulo
     *            the new titulo
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Gets the f fin.
     *
     * @return the f fin
     */
    @Temporal(TemporalType.DATE)
    public Calendar getfFin() {
        return fFin;
    }

    /**
     * Sets the f fin.
     *
     * @param fFin
     *            the new f fin
     */
    public void setfFin(Calendar fFin) {
        this.fFin = fFin;
    }

    /**
     * Gets the f inicio.
     *
     * @return the f inicio
     */
    @Temporal(TemporalType.DATE)
    public Calendar getfInicio() {
        return fInicio;
    }

    /**
     * Sets the f inicio.
     *
     * @param fInicio
     *            the new f inicio
     */
    public void setfInicio(Calendar fInicio) {
        this.fInicio = fInicio;
    }

    /**
     * Gets the duracion.
     *
     * @return the duracion
     */
    public int getDuracion() {
        return duracion;
    }

    /**
     * Sets the duracion.
     *
     * @param duracion
     *            the new duracion
     */
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    /**
     * Gets the resumen.
     *
     * @return the resumen
     */
    public String getResumen() {
        return resumen;
    }

    /**
     * Sets the resumen.
     *
     * @param resumen
     *            the new resumen
     */
    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Pelicula [idPelicula=" + idPelicula + ", titulo=" + titulo
                + ", fFin=" + fFin.getTime() + ", fInicio=" + fInicio.getTime()
                + ", duracion=" + duracion + ", resumen=" + resumen + "]";
    }
}
