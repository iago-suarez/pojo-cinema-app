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

@Entity
public class Provincia {

    private Long      idProvincia;
    private String    nProvincia;
    private Set<Cine> cines = new HashSet<Cine>();

    public Provincia() {
    }

    public Provincia(String nProvincia, Set<Cine> cines) {
        /**
         * NOTE: "idProvincia" *must* be left as "null" since its value is
         * automatically generated.
         */
        this.nProvincia = nProvincia;
        this.cines = cines;
    }

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

    public void setIdProvincia(Long idProvincia) {
        this.idProvincia = idProvincia;
    }

    public String getnProvincia() {
        return nProvincia;
    }

    public void setnProvincia(String nProvincia) {
        this.nProvincia = nProvincia;
    }

    @Override
    public String toString() {
        return "Provincia [idProvincia=" + idProvincia + ", nProvincia="
                + nProvincia + "]";
    }

    @OneToMany(mappedBy = "provincia")
    public Set<Cine> getCines() {
        return cines;
    }

    public void setCines(Set<Cine> cines) {
        this.cines = cines;
    }

}
