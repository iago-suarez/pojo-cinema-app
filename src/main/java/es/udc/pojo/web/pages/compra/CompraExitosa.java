package es.udc.pojo.web.pages.compra;

import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.web.services.AuthenticationPolicy;
import es.udc.pojo.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.ESPECTADOR)
public class CompraExitosa {

    @Persist(PersistenceConstants.FLASH)
    private Long   idCompra;

    @Inject
    private Locale locale;

    public Long getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Long idCompra) {
        this.idCompra = idCompra;
    }

    public Format getNumberFormat() {
        return NumberFormat.getInstance(locale);
    }
}
