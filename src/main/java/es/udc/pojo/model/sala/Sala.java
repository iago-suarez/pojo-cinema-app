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

@Entity
@BatchSize(size = 5)
public class Sala {

    private Long idSala;
    private int  nSala;
    private int  capacidad;
    private Cine cine;

    public Sala() {
    }

    public Sala(int nSala, int capacidad, Cine cine) {

        this.nSala = nSala;
        this.capacidad = capacidad;
        this.cine = cine;
    }

    @SequenceGenerator(name = "SalaIdGenerator", sequenceName = "SalaSeq")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "SalaIdGenerator")
    public Long getIdSala() {
        return idSala;
    }

    public void setIdSala(Long idSala) {
        this.idSala = idSala;
    }

    public int getnSala() {
        return nSala;
    }

    public void setnSala(int nSala) {
        this.nSala = nSala;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "idCine")
    public Cine getCine() {
        return cine;
    }

    public void setCine(Cine cine) {
        this.cine = cine;
    }

    @Override
    public String toString() {
        return "Sala [idSala=" + idSala + ", nSala=" + nSala + ", capacidad="
                + capacidad + ", cine=" + cine + "]";
    }
}
