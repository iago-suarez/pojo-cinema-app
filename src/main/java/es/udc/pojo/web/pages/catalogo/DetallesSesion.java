package es.udc.pojo.web.pages.catalogo;

import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.model.catalogoservice.CatalogoService;
import es.udc.pojo.model.cine.Cine;
import es.udc.pojo.model.sala.Sala;
import es.udc.pojo.model.sesion.Sesion;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pojo.web.pages.compra.ComprarEntradas;
import es.udc.pojo.web.util.UserSession;

public class DetallesSesion {

    private Sesion          sesion;

    private Sala            sala;
    private Cine            cine;

    private Long            idSesion;
    private int             locdisp;

    @Inject
    private CatalogoService catalogoService;

    @Inject
    private Locale          locale;

    @Property
    @SessionState(create = false)
    private UserSession     userSession;

    @InjectPage
    private ComprarEntradas comprarEntradas;

    public void setIdSesion(Long idSesion) {
        this.idSesion = idSesion;
    }

    public int getLocdisp() {
        return locdisp;
    }

    public Sesion getSesion() {
        return sesion;
    }

    public Sala getSala() {
        return sala;
    }

    public Cine getCine() {
        return cine;
    }

    public Format getNumberFormat() {
        return NumberFormat.getInstance(locale);
    }

    Long onPassivate() {
        return idSesion;
    }

    void onActivate(Long idSesion) {
        this.idSesion = idSesion;
        try {
            sesion = catalogoService.findSesion(idSesion);

        } catch (InstanceNotFoundException e) {

        }

        locdisp = sesion.getSala().getCapacidad() - sesion.getnAsistentes();

    }

    Object onActionFromComprar() {
        comprarEntradas.setId(sesion.getIdSesion());
        return comprarEntradas;
    }
}
