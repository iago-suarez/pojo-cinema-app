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

@Entity
@BatchSize(size = 5)
public class Sesion {

    private Long     idSesion;
    /*
     * Este precio es el precio al que sde ha cobrado la sesion y tiene como
     * abjetivo guardar el precio por si varia el de la sesion a lo largo del
     * tiempo
     */
    private Float    precio;
    private Calendar horayFecha;
    private Pelicula pelicula;
    private Sala     sala;
    private int      nAsistentes;
    private Long     version;

    public Sesion() {
    }

    public Sesion(Float precio, Calendar horayFecha, Pelicula pelicula,
            Sala sala, int nAsistentes) {

        this.precio = precio;
        this.horayFecha = horayFecha;
        this.pelicula = pelicula;
        this.sala = sala;
        this.nAsistentes = nAsistentes;
    }

    @SequenceGenerator(name = "SesionIdGenerator", sequenceName = "SesionSeq")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "SesionIdGenerator")
    public Long getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(Long idSesion) {
        this.idSesion = idSesion;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Calendar getHorayFecha() {
        return horayFecha;
    }

    public void setHorayFecha(Calendar horayFecha) {
        this.horayFecha = horayFecha;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "idPelicula")
    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "idSala")
    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public int getnAsistentes() {
        return nAsistentes;
    }

    public void setnAsistentes(int nAsistentes) {
        this.nAsistentes = nAsistentes;
    }

    public boolean estaLlena() {
        return sala.getCapacidad() < nAsistentes;
    }

    public void aumentarAsistentes(int plazas) {
        this.nAsistentes = nAsistentes + plazas;
    }

    @Version
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Sesion [idSesion=" + idSesion + ", precio=" + precio
                + ", horayFecha=" + horayFecha.getTime() + ", pelicula="
                + pelicula.getTitulo() + ", sala=" + sala.getIdSala()
                + ", nAsistentes=" + nAsistentes + "]";
    }

}
