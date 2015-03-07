package es.udc.pojo.model.provincia;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import es.udc.pojo.model.cine.Cine;

/**
 * The Class Provincia.
 */
@Entity
public class Provincia {

    /** The id provincia. */
    private Long      idProvincia;

    /** The n provincia. */
    private String    nProvincia;

    /** The cines. */
    private Set<Cine> cines = new HashSet<Cine>();

    /**
     * Instantiates a new provincia.
     */
    public Provincia() {
    }

    /**
     * Instantiates a new provincia.
     *
     * @param nProvincia
     *            the n provincia
     * @param cines
     *            the cines
     */
    public Provincia(String nProvincia, Set<Cine> cines) {
        /**
         * NOTE: "idProvincia" *must* be left as "null" since its value is
         * automatically generated.
         */
        this.nProvincia = nProvincia;
        this.cines = cines;
    }

    /**
     * Gets the id provincia.
     *
     * @return the id provincia
     */
    @Column(name = "idProvincia")
    @SequenceGenerator( // It only takes effect for
            name = "IdProvinciaGenerator", // databases providing identifier
            sequenceName = "ProvinciaSeq")
    // generators.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "IdProvinciaGenerator")
    public Long getIdProvincia() {
        return idProvincia;
    }

    /**
     * Sets the id provincia.
     *
     * @param idProvincia
     *            the new id provincia
     */
    public void setIdProvincia(Long idProvincia) {
        this.idProvincia = idProvincia;
    }

    /**
     * Gets the n provincia.
     *
     * @return the n provincia
     */
    public String getnProvincia() {
        return nProvincia;
    }

    /**
     * Sets the n provincia.
     *
     * @param nProvincia
     *            the new n provincia
     */
    public void setnProvincia(String nProvincia) {
        this.nProvincia = nProvincia;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Provincia [idProvincia=" + idProvincia + ", nProvincia="
                + nProvincia + "]";
    }

    /**
     * Gets the cines.
     *
     * @return the cines
     */
    @OneToMany(mappedBy = "provincia")
    public Set<Cine> getCines() {
        return cines;
    }

    /**
     * Sets the cines.
     *
     * @param cines
     *            the new cines
     */
    public void setCines(Set<Cine> cines) {
        this.cines = cines;
    }

}
