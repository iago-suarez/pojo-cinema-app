package es.udc.pojo.web.pages.compra;

import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.model.compra.Compra;
import es.udc.pojo.model.compraservice.CompraService;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public class CompraDetails {

    private Long          idCompra;
    private Compra        compra;

    @Inject
    private CompraService compraService;

    @Inject
    private Locale        locale;

    public void setCompraId(Long idCompra) {
        this.idCompra = idCompra;
    }

    public Compra getCompra() {
        return compra;
    }

    public Format getFormat() {
        return NumberFormat.getInstance(locale);
    }

    void onActivate(Long idCompra) {

        this.idCompra = idCompra;

        try {
            compra = compraService.findCompra(idCompra);
        } catch (InstanceNotFoundException e) {
        }

    }

    Long onPassivate() {
        return idCompra;
    }

}