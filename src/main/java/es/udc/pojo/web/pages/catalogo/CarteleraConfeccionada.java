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

@AuthenticationPolicy(AuthenticationPolicyType.ADMINISTRADOR)
public class CarteleraConfeccionada {

    private Long            idCine;

    @Property
    private Cine            cine;

    private long            dateInMillis;

    @Property
    private Date            date;

    @Inject
    private CatalogoService catalogoService;

    @Inject
    private Locale          locale;

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

    Object[] onPassivate() {

        return new Object[] { idCine, dateInMillis };
    }

    public long getDateInMillis() {
        return dateInMillis;
    }

    public void setDateInMillis(long dateInMillis) {
        this.dateInMillis = dateInMillis;
    }

    public Long getIdCine() {
        return cine.getIdCine();
    }

    public void setIdCine(Long idCine) {
        this.idCine = idCine;
    }

    public Format getDateFormatShort() {
        return DateFormat.getDateInstance(DateFormat.SHORT, locale);
    }
}