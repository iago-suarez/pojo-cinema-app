package es.udc.pojo.model.compra;

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

import es.udc.pojo.model.sesion.Sesion;
import es.udc.pojo.model.userprofile.UserProfile;

@Entity
public class Compra {

    private Long        idCompra;
    private int         numLocalidades;
    private String      numTarjeta;
    private Calendar    fExpiracion;
    private boolean     entregada;
    private Calendar    fechaCompra;
    private UserProfile userprofile;
    private Sesion      sesion;

    public Compra() {
    }

    public Compra(int numLocalidades, String numTarjeta, Calendar fExpiracion,
            boolean entregada, Calendar fechaCompra, UserProfile userprofile,
            Sesion sesion) {

        this.numLocalidades = numLocalidades;
        this.numTarjeta = numTarjeta;
        this.fExpiracion = fExpiracion;
        this.entregada = false;
        this.fechaCompra = fechaCompra;
        this.userprofile = userprofile;
        this.sesion = sesion;
    }

    @SequenceGenerator(name = "CompraIdGenerator", sequenceName = "CompraSeq")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "CompraIdGenerator")
    public Long getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Long idCompra) {
        this.idCompra = idCompra;
    }

    public int getNumLocalidades() {
        return numLocalidades;
    }

    public void setNumLocalidades(int numLocalidades) {
        this.numLocalidades = numLocalidades;
    }

    public String getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(String numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Calendar getfExpiracion() {
        return fExpiracion;
    }

    public void setfExpiracion(Calendar fExpiracion) {
        this.fExpiracion = fExpiracion;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usrId")
    public UserProfile getUserprofile() {
        return userprofile;
    }

    public void setUserprofile(UserProfile userprofile) {
        this.userprofile = userprofile;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "idSesion")
    public Sesion getSesion() {
        return sesion;
    }

    public void setSesion(Sesion sesion) {
        this.sesion = sesion;
    }

    public boolean isEntregada() {
        return entregada;
    }

    public void setEntregada(boolean entregada) {
        this.entregada = entregada;
    }

    @Temporal(TemporalType.DATE)
    public Calendar getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Calendar fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    @Override
    public String toString() {
        return "Compra [idCompra=" + idCompra + ", numLocalidades="
                + numLocalidades + ", numTarjeta=" + numTarjeta
                + ", fExpiracion=" + fExpiracion.getTime() + ", entregada="
                + entregada + ", fechaCompra=" + fechaCompra.getTime() + "]";
    }

}