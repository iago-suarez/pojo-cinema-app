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

@AuthenticationPolicy(AuthenticationPolicyType.ESPECTADOR)
public class MostrarCompras {

    private final static int COMPRAS_PER_PAGE = 5;

    private Long             idCompra;
    private int              startIndex       = 0;
    private CompraBlock      compraBlock;
    @Property
    private Compra           compra;
    @Property
    private Sala             sala;
    @Property
    private Cine             cine;

    @Inject
    private CompraService    compraService;

    @Inject
    private Locale           locale;

    @Property
    @SessionState(create = false)
    private UserSession      userSession;

    public Long getidCompra() {
        return idCompra;
    }

    public void setidCompra(Long idCompra) {
        this.idCompra = idCompra;
    }

    public List<Compra> getCompras() {
        return compraBlock.getCompras();
    }

    public void setAccount(Compra compra) {
        this.compra = compra;
    }

    public Format getFormat() {
        return NumberFormat.getInstance(locale);
    }

    public Format getDateFormat() {
        return DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
    }

    public Object[] getPreviousLinkContext() {

        if (startIndex - COMPRAS_PER_PAGE >= 0) {
            return new Object[] { startIndex - COMPRAS_PER_PAGE };
        } else {
            return null;
        }

    }

    public Object[] getNextLinkContext() {

        if (compraBlock.getExistMoreCompras()) {
            return new Object[] { startIndex + COMPRAS_PER_PAGE };
        } else {
            return null;
        }

    }

    void onActivate(int startIndex) {
        this.startIndex = startIndex;

        compraBlock = compraService.findCompras(userSession.getUserProfileId(),
                startIndex, COMPRAS_PER_PAGE);
    }

    Object[] onPassivate() {
        return new Object[] { startIndex };
    }

}