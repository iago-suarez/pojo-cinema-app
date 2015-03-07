/**
 * 
 */
package es.udc.pojo.web.pages.catalogo;

import java.text.DateFormat;
import java.text.Format;
import java.util.Date;
import java.util.Locale;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.model.catalogoservice.CatalogoService;
import es.udc.pojo.model.cine.Cine;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pojo.web.services.AuthenticationPolicy;
import es.udc.pojo.web.services.AuthenticationPolicyType;

/**
 * The Class CarteleraConfeccionada.
 */
@AuthenticationPolicy(AuthenticationPolicyType.ADMINISTRADOR)
public class CarteleraConfeccionada {

    /** The id cine. */
    private Long            idCine;

    /** The cine. */
    @Property
    private Cine            cine;

    /** The date in millis. */
    private long            dateInMillis;

    /** The date. */
    @Property
    private Date            date;

    /** The catalogo service. */
    @Inject
    private CatalogoService catalogoService;

    /** The locale. */
    @Inject
    private Locale          locale;

    /**
     * On activate.
     *
     * @param idCine
     *            the id cine
     * @param dateInMillis
     *            the date in millis
     */
    void onActivate(Long idCine, long dateInMillis) {
        this.idCine = idCine;
        this.dateInMillis = dateInMillis;

        this.date = new Date();
        this.date.setTime(dateInMillis);

        try {
            System.err.println("idCine: " + idCine);
            this.cine = catalogoService.findCine(idCine);
        } catch (InstanceNotFoundException e) {
            // TODO Auto-generated catch block
        }
    }

    /**
     * On passivate.
     *
     * @return the object[]
     */
    Object[] onPassivate() {

        return new Object[] { idCine, dateInMillis };
    }

    /**
     * Gets the date in millis.
     *
     * @return the date in millis
     */
    public long getDateInMillis() {
        return dateInMillis;
    }

    /**
     * Sets the date in millis.
     *
     * @param dateInMillis
     *            the new date in millis
     */
    public void setDateInMillis(long dateInMillis) {
        this.dateInMillis = dateInMillis;
    }

    /**
     * Gets the id cine.
     *
     * @return the id cine
     */
    public Long getIdCine() {
        return cine.getIdCine();
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
     * Gets the date format short.
     *
     * @return the date format short
     */
    public Format getDateFormatShort() {
        return DateFormat.getDateInstance(DateFormat.SHORT, locale);
    }
}