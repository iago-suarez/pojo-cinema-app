package es.udc.pojo.model.sala;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.BatchSize;

import es.udc.pojo.model.cine.Cine;

/**
 * The Class Sala.
 */
@Entity
@BatchSize(size = 5)
public class Sala {

    /** The id sala. */
    private Long idSala;

    /** The n sala. */
    private int  nSala;

    /** The capacidad. */
    private int  capacidad;

    /** The cine. */
    private Cine cine;

    /**
     * Instantiates a new sala.
     */
    public Sala() {
    }

    /**
     * Instantiates a new sala.
     *
     * @param nSala
     *            the n sala
     * @param capacidad
     *            the capacidad
     * @param cine
     *            the cine
     */
    public Sala(int nSala, int capacidad, Cine cine) {

        this.nSala = nSala;
        this.capacidad = capacidad;
        this.cine = cine;
    }

    /**
     * Gets the id sala.
     *
     * @return the id sala
     */
    @SequenceGenerator(name = "SalaIdGenerator", sequenceName = "SalaSeq")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "SalaIdGenerator")
    public Long getIdSala() {
        return idSala;
    }

    /**
     * Sets the id sala.
     *
     * @param idSala
     *            the new id sala
     */
    public void setIdSala(Long idSala) {
        this.idSala = idSala;
    }

    /**
     * Gets the n sala.
     *
     * @return the n sala
     */
    public int getnSala() {
        return nSala;
    }

    /**
     * Sets the n sala.
     *
     * @param nSala
     *            the new n sala
     */
    public void setnSala(int nSala) {
        this.nSala = nSala;
    }

    /**
     * Gets the capacidad.
     *
     * @return the capacidad
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * Sets the capacidad.
     *
     * @param capacidad
     *            the new capacidad
     */
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    /**
     * Gets the cine.
     *
     * @return the cine
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "idCine")
    public Cine getCine() {
        return cine;
    }

    /**
     * Sets the cine.
     *
     * @param cine
     *            the new cine
     */
    public void setCine(Cine cine) {
        this.cine = cine;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Sala [idSala=" + idSala + ", nSala=" + nSala + ", capacidad="
                + capacidad + ", cine=" + cine + "]";
    }
}
