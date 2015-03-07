package es.udc.pojo.web.pages.compra;

import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.web.services.AuthenticationPolicy;
import es.udc.pojo.web.services.AuthenticationPolicyType;

/**
 * The Class CompraExitosa.
 */
@AuthenticationPolicy(AuthenticationPolicyType.ESPECTADOR)
public class CompraExitosa {

    /** The id compra. */
    @Persist(PersistenceConstants.FLASH)
    private Long   idCompra;

    /** The locale. */
    @Inject
    private Locale locale;

    /**
     * Gets the id compra.
     *
     * @return the id compra
     */
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
     * Gets the number format.
     *
     * @return the number format
     */
    public Format getNumberFormat() {
        return NumberFormat.getInstance(locale);
    }
}
