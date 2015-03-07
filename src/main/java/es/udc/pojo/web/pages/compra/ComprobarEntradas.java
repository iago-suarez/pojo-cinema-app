package es.udc.pojo.web.pages.compra;

import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.model.compra.Compra;
import es.udc.pojo.model.compraservice.CompraService;
import es.udc.pojo.model.util.CompraYaEntregadaException;
import es.udc.pojo.model.util.SesionPasadaException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pojo.web.services.AuthenticationPolicy;
import es.udc.pojo.web.services.AuthenticationPolicyType;

/**
 * The Class ComprobarEntradas.
 */
@AuthenticationPolicy(AuthenticationPolicyType.TAQUILLERO)
public class ComprobarEntradas {

    /** The id compra. */
    @Property
    private Long            idCompra;

    /** The compra. */
    private Compra          compra;

    /** The sesion pasada. */
    @Property
    private boolean         sesionPasada;

    /** The compra service. */
    @Inject
    private CompraService   compraService;

    /** The locale. */
    @Inject
    private Locale          locale;

    /** The compra entregada. */
    @InjectPage
    private CompraEntregada compraEntregada;

    /**
     * On action from entregar.
     *
     * @return the object
     */
    Object onActionFromEntregar() {
        try {

            compraService.entregarEntradas(compra.getIdCompra());

        } catch (InstanceNotFoundException | CompraYaEntregadaException
                | SesionPasadaException e) {
        }
        compraEntregada.setIdCompra(idCompra);
        return compraEntregada;
    }

    /**
     * On activate.
     *
     * @param idCompra
     *            the id compra
     */
    void onActivate(Long idCompra) {

        this.idCompra = idCompra;

        // Si estamos buscando una compra
        if (idCompra != null) {
            try {
                compra = compraService.findCompra(idCompra);
                sesionPasada = compra.getSesion().getHorayFecha()
                        .before(Calendar.getInstance());
            } catch (InstanceNotFoundException e) {
            }
        }
    }

    /**
     * On passivate.
     *
     * @return the long
     */
    Long onPassivate() {
        return idCompra;
    }

    /**
     * Sets the compra id.
     *
     * @param idCompra
     *            the new compra id
     */
    public void setCompraId(Long idCompra) {
        this.idCompra = idCompra;
    }

    /**
     * Gets the compra.
     *
     * @return the compra
     */
    public Compra getCompra() {
        return compra;
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
     * Gets the date format.
     *
     * @return the date format
     */
    public DateFormat getDateFormat() {
        return DateFormat.getDateInstance(DateFormat.SHORT, locale);
    }

    /**
     * Gets the time format.
     *
     * @return the time format
     */
    public Format getTimeFormat() {
        SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat
                .getDateInstance();
        dateFormat.applyPattern("HH:mm");
        return dateFormat;
    }
}