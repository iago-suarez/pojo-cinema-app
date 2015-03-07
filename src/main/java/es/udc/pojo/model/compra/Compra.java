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

/**
 * The Class Compra.
 */
@Entity
public class Compra {

    /** The id compra. */
    private Long        idCompra;

    /** The num localidades. */
    private int         numLocalidades;

    /** The num tarjeta. */
    private String      numTarjeta;

    /** The expiracion. */
    private Calendar    fExpiracion;

    /** The entregada. */
    private boolean     entregada;

    /** The fecha compra. */
    private Calendar    fechaCompra;

    /** The userprofile. */
    private UserProfile userprofile;

    /** The sesion. */
    private Sesion      sesion;

    /**
     * Instantiates a new compra.
     */
    public Compra() {
    }

    /**
     * Instantiates a new compra.
     *
     * @param numLocalidades
     *            the num localidades
     * @param numTarjeta
     *            the num tarjeta
     * @param fExpiracion
     *            the f expiracion
     * @param entregada
     *            the entregada
     * @param fechaCompra
     *            the fecha compra
     * @param userprofile
     *            the userprofile
     * @param sesion
     *            the sesion
     */
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

    /**
     * Gets the id compra.
     *
     * @return the id compra
     */
    @SequenceGenerator(name = "CompraIdGenerator", sequenceName = "CompraSeq")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "CompraIdGenerator")
    public Long getIdCompra() {
        return idCompra;
    }

    /**
     * Sets the id compra.
     *
     * @param idCompra
     *            the new id compra
     */
    public void setIdCompra(Long idCompra) {
        this.idCompra = idCompra;
    }

    /**
     * Gets the num localidades.
     *
     * @return the num localidades
     */
    public int getNumLocalidades() {
        return numLocalidades;
    }

    /**
     * Sets the num localidades.
     *
     * @param numLocalidades
     *            the new num localidades
     */
    public void setNumLocalidades(int numLocalidades) {
        this.numLocalidades = numLocalidades;
    }

    /**
     * Gets the num tarjeta.
     *
     * @return the num tarjeta
     */
    public String getNumTarjeta() {
        return numTarjeta;
    }

    /**
     * Sets the num tarjeta.
     *
     * @param numTarjeta
     *            the new num tarjeta
     */
    public void setNumTarjeta(String numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    /**
     * Gets the f expiracion.
     *
     * @return the f expiracion
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Calendar getfExpiracion() {
        return fExpiracion;
    }

    /**
     * Sets the f expiracion.
     *
     * @param fExpiracion
     *            the new f expiracion
     */
    public void setfExpiracion(Calendar fExpiracion) {
        this.fExpiracion = fExpiracion;
    }

    /**
     * Gets the userprofile.
     *
     * @return the userprofile
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usrId")
    public UserProfile getUserprofile() {
        return userprofile;
    }

    /**
     * Sets the userprofile.
     *
     * @param userprofile
     *            the new userprofile
     */
    public void setUserprofile(UserProfile userprofile) {
        this.userprofile = userprofile;
    }

    /**
     * Gets the sesion.
     *
     * @return the sesion
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "idSesion")
    public Sesion getSesion() {
        return sesion;
    }

    /**
     * Sets the sesion.
     *
     * @param sesion
     *            the new sesion
     */
    public void setSesion(Sesion sesion) {
        this.sesion = sesion;
    }

    /**
     * Checks if is entregada.
     *
     * @return true, if is entregada
     */
    public boolean isEntregada() {
        return entregada;
    }

    /**
     * Sets the entregada.
     *
     * @param entregada
     *            the new entregada
     */
    public void setEntregada(boolean entregada) {
        this.entregada = entregada;
    }

    /**
     * Gets the fecha compra.
     *
     * @return the fecha compra
     */
    @Temporal(TemporalType.DATE)
    public Calendar getFechaCompra() {
        return fechaCompra;
    }

    /**
     * Sets the fecha compra.
     *
     * @param fechaCompra
     *            the new fecha compra
     */
    public void setFechaCompra(Calendar fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Compra [idCompra=" + idCompra + ", numLocalidades="
                + numLocalidades + ", numTarjeta=" + numTarjeta
                + ", fExpiracion=" + fExpiracion.getTime() + ", entregada="
                + entregada + ", fechaCompra=" + fechaCompra.getTime() + "]";
    }

}