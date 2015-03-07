package es.udc.pojo.web.pages.compra;

import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.model.cine.Cine;
import es.udc.pojo.model.compra.Compra;
import es.udc.pojo.model.compraservice.CompraBlock;
import es.udc.pojo.model.compraservice.CompraService;
import es.udc.pojo.model.sala.Sala;
import es.udc.pojo.web.services.AuthenticationPolicy;
import es.udc.pojo.web.services.AuthenticationPolicyType;
import es.udc.pojo.web.util.UserSession;

/**
 * The Class MostrarCompras.
 */
@AuthenticationPolicy(AuthenticationPolicyType.ESPECTADOR)
public class MostrarCompras {

    /** The Constant COMPRAS_PER_PAGE. */
    private final static int COMPRAS_PER_PAGE = 5;

    /** The id compra. */
    private Long             idCompra;

    /** The start index. */
    private int              startIndex       = 0;

    /** The compra block. */
    private CompraBlock      compraBlock;

    /** The compra. */
    @Property
    private Compra           compra;

    /** The sala. */
    @Property
    private Sala             sala;

    /** The cine. */
    @Property
    private Cine             cine;

    /** The compra service. */
    @Inject
    private CompraService    compraService;

    /** The locale. */
    @Inject
    private Locale           locale;

    /** The user session. */
    @Property
    @SessionState(create = false)
    private UserSession      userSession;

    /**
     * Gets the id compra.
     *
     * @return the id compra
     */
    public Long getidCompra() {
        return idCompra;
    }

    /**
     * Sets the id compra.
     *
     * @param idCompra
     *            the new id compra
     */
    public void setidCompra(Long idCompra) {
        this.idCompra = idCompra;
    }

    /**
     * Gets the compras.
     *
     * @return the compras
     */
    public List<Compra> getCompras() {
        return compraBlock.getCompras();
    }

    /**
     * Sets the account.
     *
     * @param compra
     *            the new account
     */
    public void setAccount(Compra compra) {
        this.compra = compra;
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
     * Gets the date format.
     *
     * @return the date format
     */
    public Format getDateFormat() {
        return DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
    }

    /**
     * Gets the previous link context.
     *
     * @return the previous link context
     */
    public Object[] getPreviousLinkContext() {

        if (startIndex - COMPRAS_PER_PAGE >= 0) {
            return new Object[] { startIndex - COMPRAS_PER_PAGE };
        } else {
            return null;
        }

    }

    /**
     * Gets the next link context.
     *
     * @return the next link context
     */
    public Object[] getNextLinkContext() {

        if (compraBlock.getExistMoreCompras()) {
            return new Object[] { startIndex + COMPRAS_PER_PAGE };
        } else {
            return null;
        }

    }

    /**
     * On activate.
     *
     * @param startIndex
     *            the start index
     */
    void onActivate(int startIndex) {
        this.startIndex = startIndex;

        compraBlock = compraService.findCompras(userSession.getUserProfileId(),
                startIndex, COMPRAS_PER_PAGE);
    }

    /**
     * On passivate.
     *
     * @return the object[]
     */
    Object[] onPassivate() {
        return new Object[] { startIndex };
    }

}