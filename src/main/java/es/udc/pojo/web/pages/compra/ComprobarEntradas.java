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

@AuthenticationPolicy(AuthenticationPolicyType.TAQUILLERO)
public class ComprobarEntradas {

    @Property
    private Long            idCompra;

    private Compra          compra;

    @Property
    private boolean         sesionPasada;

    @Inject
    private CompraService   compraService;

    @Inject
    private Locale          locale;

    @InjectPage
    private CompraEntregada compraEntregada;

    Object onActionFromEntregar() {
        try {

            compraService.entregarEntradas(compra.getIdCompra());

        } catch (InstanceNotFoundException | CompraYaEntregadaException
                | SesionPasadaException e) {
        }
        compraEntregada.setIdCompra(idCompra);
        return compraEntregada;
    }

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

    Long onPassivate() {
        return idCompra;
    }

    public void setCompraId(Long idCompra) {
        this.idCompra = idCompra;
    }

    public Compra getCompra() {
        return compra;
    }

    public Format getNumberFormat() {
        return NumberFormat.getInstance(locale);
    }

    public DateFormat getDateFormat() {
        return DateFormat.getDateInstance(DateFormat.SHORT, locale);
    }

    public Format getTimeFormat() {
        SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat
                .getDateInstance();
        dateFormat.applyPattern("HH:mm");
        return dateFormat;
    }
}