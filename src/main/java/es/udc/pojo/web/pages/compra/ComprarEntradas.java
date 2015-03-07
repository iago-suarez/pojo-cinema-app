package es.udc.pojo.web.pages.compra;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.model.compraservice.CompraService;
import es.udc.pojo.model.util.SesionLlenaException;
import es.udc.pojo.model.util.SesionPasadaException;
import es.udc.pojo.model.util.TarjetaCaducadaException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pojo.web.services.AuthenticationPolicy;
import es.udc.pojo.web.services.AuthenticationPolicyType;
import es.udc.pojo.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.ESPECTADOR)
public class ComprarEntradas {

    private Long          idSesion;
    private Long          idCompra;
    private int           numEntradasAsInt;

    @Persist(PersistenceConstants.SESSION)
    private Long          id;

    @Property
    @SessionState(create = false)
    private UserSession   userSession;

    @Property
    private String        numeroTarjetaCredito;

    @Property
    private String        expiracionTarjetaCredito;

    @Property
    private String        numeroDeEntradas;

    @Component
    private Form          compraForm;

    @Component(id = "numeroTarjetaCredito")
    private TextField     ntcTextField;

    @Component(id = "expiracionTarjetaCredito")
    private TextField     etcTextField;

    @Component(id = "numeroDeEntradas")
    private TextField     nEntradasTextField;

    @Inject
    private CompraService compraService;

    @Inject
    private Locale        locale;

    @Inject
    private Messages      messages;

    @InjectPage
    private CompraExitosa compraExitosa;

    public void setId(Long id) {
        this.id = id;
    }

    public void setIdCompra(Long idCompra) {
        this.idCompra = idCompra;
    }

    public Long getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(Long idSesion) {
        this.idSesion = idSesion;
    }

    Long onPassivate() {
        return idSesion;
    }

    void onActivate(Long idSesion) {
        this.idSesion = idSesion;
    }

    void onValidateFromCompraForm() {

        if (!compraForm.isValid()) {
            return;
        }

        NumberFormat numberFormatter = NumberFormat.getInstance(locale);
        ParsePosition position = new ParsePosition(0);
        Number numEntradas = numberFormatter.parse(numeroDeEntradas, position);

        if (position.getIndex() != numeroDeEntradas.length()) {
            compraForm.recordError(nEntradasTextField, messages.format(
                    "error-incorrectNumberFormat", numeroDeEntradas));
        }

        numEntradasAsInt = numEntradas.intValue();

        if (numEntradasAsInt < 1 || numEntradasAsInt > 10) {
            compraForm.recordError(nEntradasTextField,
                    messages.format("error-incorrectNumber", numeroDeEntradas));
        }

        DateFormat dateFormatter = new SimpleDateFormat("MM/yyyy");
        position = new ParsePosition(0);
        Date fechaExpiracion = dateFormatter.parse(expiracionTarjetaCredito,
                position);

        if (fechaExpiracion == null
                || position.getIndex() != expiracionTarjetaCredito.length()) {
            compraForm.recordError(etcTextField, messages.format(
                    "error-incorrectDateFormat", expiracionTarjetaCredito));
            return;
        }

        Calendar fExp = Calendar.getInstance();
        fExp.setTime(fechaExpiracion);

        try {

            idCompra = compraService.createCompra(
                    userSession.getUserProfileId(), numEntradasAsInt,
                    numeroTarjetaCredito, fExp, id); // TODO
        } catch (InstanceNotFoundException e) {
            compraForm.recordError(messages.format("error-sesionNotFound", id));
        } catch (SesionLlenaException e) {
            compraForm.recordError(nEntradasTextField,
                    messages.format("error-FullSession", numeroDeEntradas));
        } catch (TarjetaCaducadaException e) {
            compraForm.recordError(etcTextField, messages.format(
                    "error-incorrectCreditCardExpirationDate",
                    expiracionTarjetaCredito));
        } catch (SesionPasadaException e) {
            compraForm.recordError(messages.format("error-expiredSesion", id));
        }

    }

    Object onSuccess() {
        compraExitosa.setIdCompra(idCompra);
        return compraExitosa;
    }
}
