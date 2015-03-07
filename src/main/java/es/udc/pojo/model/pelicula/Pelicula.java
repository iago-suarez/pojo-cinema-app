package es.udc.pojo.model.pelicula;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Pelicula {

    private Long     idPelicula;
    private String   titulo;
    private Calendar fFin;
    private Calendar fInicio;
    private int      duracion;
    private String   resumen;

    public Pelicula() {
    }

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

    @SequenceGenerator(name = "PeliculaIdGenerator",
            sequenceName = "PeliculaSeq")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "PeliculaIdGenerator")
    public Long getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Long idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Temporal(TemporalType.DATE)
    public Calendar getfFin() {
        return fFin;
    }

    public void setfFin(Calendar fFin) {
        this.fFin = fFin;
    }

    @Temporal(TemporalType.DATE)
    public Calendar getfInicio() {
        return fInicio;
    }

    public void setfInicio(Calendar fInicio) {
        this.fInicio = fInicio;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    @Override
    public String toString() {
        return "Pelicula [idPelicula=" + idPelicula + ", titulo=" + titulo
                + ", fFin=" + fFin.getTime() + ", fInicio=" + fInicio.getTime()
                + ", duracion=" + duracion + ", resumen=" + resumen + "]";
    }
}
