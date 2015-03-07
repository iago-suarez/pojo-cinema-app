package es.udc.pojo.web.pages.compra;

import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.model.compra.Compra;
import es.udc.pojo.model.compraservice.CompraService;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

/**
 * The Class CompraDetails.
 */
public class CompraDetails {

    /** The id compra. */
    private Long          idCompra;

    /** The compra. */
    private Compra        compra;

    /** The compra service. */
    @Inject
    private CompraService compraService;

    /** The locale. */
    @Inject
    private Locale        locale;

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
     * Gets the format.
     *
     * @return the format
     */
    public Format getFormat() {
        return NumberFormat.getInstance(locale);
    }

    /**
     * On activate.
     *
     * @param idCompra
     *            the id compra
     */
    void onActivate(Long idCompra) {

        this.idCompra = idCompra;

        try {
            compra = compraService.findCompra(idCompra);
        } catch (InstanceNotFoundException e) {
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

}