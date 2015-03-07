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

/**
 * The Class ComprarEntradas.
 */
@AuthenticationPolicy(AuthenticationPolicyType.ESPECTADOR)
public class ComprarEntradas {

    /** The id sesion. */
    private Long          idSesion;

    /** The id compra. */
    private Long          idCompra;

    /** The num entradas as int. */
    private int           numEntradasAsInt;

    /** The id. */
    @Persist(PersistenceConstants.SESSION)
    private Long          id;

    /** The user session. */
    @Property
    @SessionState(create = false)
    private UserSession   userSession;

    /** The numero tarjeta credito. */
    @Property
    private String        numeroTarjetaCredito;

    /** The expiracion tarjeta credito. */
    @Property
    private String        expiracionTarjetaCredito;

    /** The numero de entradas. */
    @Property
    private String        numeroDeEntradas;

    /** The compra form. */
    @Component
    private Form          compraForm;

    /** The ntc text field. */
    @Component(id = "numeroTarjetaCredito")
    private TextField     ntcTextField;

    /** The etc text field. */
    @Component(id = "expiracionTarjetaCredito")
    private TextField     etcTextField;

    /** The n entradas text field. */
    @Component(id = "numeroDeEntradas")
    private TextField     nEntradasTextField;

    /** The compra service. */
    @Inject
    private CompraService compraService;

    /** The locale. */
    @Inject
    private Locale        locale;

    /** The messages. */
    @Inject
    private Messages      messages;

    /** The compra exitosa. */
    @InjectPage
    private CompraExitosa compraExitosa;

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public void setId(Long id) {
        this.id = id;
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
     * Gets the id sesion.
     *
     * @return the id sesion
     */
    public Long getIdSesion() {
        return idSesion;
    }

    /**
     * Sets the id sesion.
     *
     * @param idSesion
     *            the new id sesion
     */
    public void setIdSesion(Long idSesion) {
        this.idSesion = idSesion;
    }

    /**
     * On passivate.
     *
     * @return the long
     */
    Long onPassivate() {
        return idSesion;
    }

    /**
     * On activate.
     *
     * @param idSesion
     *            the id sesion
     */
    void onActivate(Long idSesion) {
        this.idSesion = idSesion;
    }

    /**
     * On validate from compra form.
     */
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

    /**
     * On success.
     *
     * @return the object
     */
    Object onSuccess() {
        compraExitosa.setIdCompra(idCompra);
        return compraExitosa;
    }
}
