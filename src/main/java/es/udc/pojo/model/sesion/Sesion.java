package es.udc.pojo.model.sesion;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.BatchSize;

import es.udc.pojo.model.pelicula.Pelicula;
import es.udc.pojo.model.sala.Sala;

/**
 * The Class Sesion.
 */
@Entity
@BatchSize(size = 5)
public class Sesion {

    /** The id sesion. */
    private Long     idSesion;
    /*
     * Este precio es el precio al que sde ha cobrado la sesion y tiene como
     * abjetivo guardar el precio por si varia el de la sesion a lo largo del
     * tiempo
     */
    /** The precio. */
    private Float    precio;

    /** The horay fecha. */
    private Calendar horayFecha;

    /** The pelicula. */
    private Pelicula pelicula;

    /** The sala. */
    private Sala     sala;

    /** The n asistentes. */
    private int      nAsistentes;

    /** The version. */
    private Long     version;

    /**
     * Instantiates a new sesion.
     */
    public Sesion() {
    }

    /**
     * Instantiates a new sesion.
     *
     * @param precio
     *            the precio
     * @param horayFecha
     *            the horay fecha
     * @param pelicula
     *            the pelicula
     * @param sala
     *            the sala
     * @param nAsistentes
     *            the n asistentes
     */
    public Sesion(Float precio, Calendar horayFecha, Pelicula pelicula,
            Sala sala, int nAsistentes) {

        this.precio = precio;
        this.horayFecha = horayFecha;
        this.pelicula = pelicula;
        this.sala = sala;
        this.nAsistentes = nAsistentes;
    }

    /**
     * Gets the id sesion.
     *
     * @return the id sesion
     */
    @SequenceGenerator(name = "SesionIdGenerator", sequenceName = "SesionSeq")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "SesionIdGenerator")
    public Long getIdSesion() {
        return idSesion;
    }

    /**
     * Sets the id sesion.
     *
     * @param idSesion
     *            the new id sesion
     */
    public void setIdSesion(Long idSesion) {
        this.idSesion = idSesion;
    }

    /**
     * Gets the precio.
     *
     * @return the precio
     */
    public Float getPrecio() {
        return precio;
    }

    /**
     * Sets the precio.
     *
     * @param precio
     *            the new precio
     */
    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    /**
     * Gets the horay fecha.
     *
     * @return the horay fecha
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Calendar getHorayFecha() {
        return horayFecha;
    }

    /**
     * Sets the horay fecha.
     *
     * @param horayFecha
     *            the new horay fecha
     */
    public void setHorayFecha(Calendar horayFecha) {
        this.horayFecha = horayFecha;
    }

    /**
     * Gets the pelicula.
     *
     * @return the pelicula
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "idPelicula")
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
     * Gets the sala.
     *
     * @return the sala
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "idSala")
    public Sala getSala() {
        return sala;
    }

    /**
     * Sets the sala.
     *
     * @param sala
     *            the new sala
     */
    public void setSala(Sala sala) {
        this.sala = sala;
    }

    /**
     * Gets the n asistentes.
     *
     * @return the n asistentes
     */
    public int getnAsistentes() {
        return nAsistentes;
    }

    /**
     * Sets the n asistentes.
     *
     * @param nAsistentes
     *            the new n asistentes
     */
    public void setnAsistentes(int nAsistentes) {
        this.nAsistentes = nAsistentes;
    }

    /**
     * Esta llena.
     *
     * @return true, if successful
     */
    public boolean estaLlena() {
        return sala.getCapacidad() < nAsistentes;
    }

    /**
     * Aumentar asistentes.
     *
     * @param plazas
     *            the plazas
     */
    public void aumentarAsistentes(int plazas) {
        this.nAsistentes = nAsistentes + plazas;
    }

    /**
     * Gets the version.
     *
     * @return the version
     */
    @Version
    public Long getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version
     *            the new version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Sesion [idSesion=" + idSesion + ", precio=" + precio
                + ", horayFecha=" + horayFecha.getTime() + ", pelicula="
                + pelicula.getTitulo() + ", sala=" + sala.getIdSala()
                + ", nAsistentes=" + nAsistentes + "]";
    }

}
