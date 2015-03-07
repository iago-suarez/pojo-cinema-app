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

@Entity
@BatchSize(size = 5)
public class Cine {

    private Long      idCine;
    private String    nCine;
    private Float     precioEntrada;
    private Provincia provincia;
    private Set<Sala> salas = new HashSet<Sala>();

    public Cine() {
    }

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

    public String getnCine() {
        return nCine;
    }

    public void setnCine(String nCine) {
        this.nCine = nCine;
    }

    public Float getPrecioEntrada() {
        return precioEntrada;
    }

    public void setPrecioEntrada(Float precioEntrada) {
        this.precioEntrada = precioEntrada;
    }

    public void setIdCine(Long idCine) {
        this.idCine = idCine;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "idProvincia")
    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    @Override
    public String toString() {
        return "Cine [idCine=" + idCine + ", nCine=" + nCine
                + ", precioEntrada=" + precioEntrada + ", provincia="
                + provincia + "]";
    }

    @OneToMany(mappedBy = "cine")
    public Set<Sala> getSalas() {
        return salas;
    }

    public void setSalas(Set<Sala> salas) {
        this.salas = salas;
    }

}
