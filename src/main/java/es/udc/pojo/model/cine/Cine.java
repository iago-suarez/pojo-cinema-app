package es.udc.pojo.model.cine;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.BatchSize;

import es.udc.pojo.model.provincia.Provincia;
import es.udc.pojo.model.sala.Sala;

/**
 * The Class Cine.
 */
@Entity
@BatchSize(size = 5)
public class Cine {

    /** The id cine. */
    private Long      idCine;

    /** The n cine. */
    private String    nCine;

    /** The precio entrada. */
    private Float     precioEntrada;

    /** The provincia. */
    private Provincia provincia;

    /** The salas. */
    private Set<Sala> salas = new HashSet<Sala>();

    /**
     * Instantiates a new cine.
     */
    public Cine() {
    }

    /**
     * Instantiates a new cine.
     *
     * @param nCine
     *            the n cine
     * @param precioEntrada
     *            the precio entrada
     * @param provincia
     *            the provincia
     * @param salas
     *            the salas
     */
    public Cine(String nCine, Float precioEntrada, Provincia provincia,
            Set<Sala> salas) {
        /**
         * NOTE: "idCine" *must* be left as "null" since its value is
         * automatically generated.
         */

        this.nCine = nCine;
        this.precioEntrada = precioEntrada;
        this.provincia = provincia;
        this.salas = salas;
    }

    /**
     * Gets the id cine.
     *
     * @return the id cine
     */
    @Column(name = "idCine")
    @SequenceGenerator( // It only takes effect for
            name = "IdCineGenerator", // databases providing identifier
            sequenceName = "CineSeq")
    // generators.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "IdCineGenerator")
    public Long getIdCine() {
        return idCine;
    }

    /**
     * Gets the n cine.
     *
     * @return the n cine
     */
    public String getnCine() {
        return nCine;
    }

    /**
     * Sets the n cine.
     *
     * @param nCine
     *            the new n cine
     */
    public void setnCine(String nCine) {
        this.nCine = nCine;
    }

    /**
     * Gets the precio entrada.
     *
     * @return the precio entrada
     */
    public Float getPrecioEntrada() {
        return precioEntrada;
    }

    /**
     * Sets the precio entrada.
     *
     * @param precioEntrada
     *            the new precio entrada
     */
    public void setPrecioEntrada(Float precioEntrada) {
        this.precioEntrada = precioEntrada;
    }

    /**
     * Sets the id cine.
     *
     * @param idCine
     *            the new id cine
     */
    public void setIdCine(Long idCine) {
        this.idCine = idCine;
    }

    /**
     * Gets the provincia.
     *
     * @return the provincia
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "idProvincia")
    public Provincia getProvincia() {
        return provincia;
    }

    /**
     * Sets the provincia.
     *
     * @param provincia
     *            the new provincia
     */
    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Cine [idCine=" + idCine + ", nCine=" + nCine
                + ", precioEntrada=" + precioEntrada + ", provincia="
                + provincia + "]";
    }

    /**
     * Gets the salas.
     *
     * @return the salas
     */
    @OneToMany(mappedBy = "cine")
    public Set<Sala> getSalas() {
        return salas;
    }

    /**
     * Sets the salas.
     *
     * @param salas
     *            the new salas
     */
    public void setSalas(Set<Sala> salas) {
        this.salas = salas;
    }

}
