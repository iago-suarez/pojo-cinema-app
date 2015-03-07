package es.udc.pojo.web.pages.catalogo;

import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.model.catalogoservice.CatalogoService;
import es.udc.pojo.model.cine.Cine;
import es.udc.pojo.model.sala.Sala;
import es.udc.pojo.model.sesion.Sesion;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pojo.web.pages.compra.ComprarEntradas;
import es.udc.pojo.web.util.UserSession;

/**
 * The Class DetallesSesion.
 */
public class DetallesSesion {

    /** The sesion. */
    private Sesion          sesion;

    /** The sala. */
    private Sala            sala;

    /** The cine. */
    private Cine            cine;

    /** The id sesion. */
    private Long            idSesion;

    /** The locdisp. */
    private int             locdisp;

    /** The catalogo service. */
    @Inject
    private CatalogoService catalogoService;

    /** The locale. */
    @Inject
    private Locale          locale;

    /** The user session. */
    @Property
    @SessionState(create = false)
    private UserSession     userSession;

    /** The comprar entradas. */
    @InjectPage
    private ComprarEntradas comprarEntradas;

    /**
     * Sets the id sesion.
     *
     * @param idSesion
     *            the new id sesion
     */
    public void setIdSesion(Long idSesion) {
        this.idSesion = idSesion;
    }

    /**
     * Gets the locdisp.
     *
     * @return the locdisp
     */
    public int getLocdisp() {
        return locdisp;
    }

    /**
     * Gets the sesion.
     *
     * @return the sesion
     */
    public Sesion getSesion() {
        return sesion;
    }

    /**
     * Gets the sala.
     *
     * @return the sala
     */
    public Sala getSala() {
        return sala;
    }

    /**
     * Gets the cine.
     *
     * @return the cine
     */
    public Cine getCine() {
        return cine;
    }

    /**
     * Gets the number format.
     *
     * @return the number format
     */
    public Format getNumberFormat() {
        return NumberFormat.getInstance(locale);
    }

    /**
     * On passivate.
     *
     * @return the long
     */
    Long onPassivate() {
        return idSesion;
    }

    /**
     * On activate.
     *
     * @param idSesion
     *            the id sesion
     */
    void onActivate(Long idSesion) {
        this.idSesion = idSesion;
        try {
            sesion = catalogoService.findSesion(idSesion);

        } catch (InstanceNotFoundException e) {

        }

        locdisp = sesion.getSala().getCapacidad() - sesion.getnAsistentes();

    }

    /**
     * On action from comprar.
     *
     * @return the object
     */
    Object onActionFromComprar() {
        comprarEntradas.setId(sesion.getIdSesion());
        return comprarEntradas;
    }
}
