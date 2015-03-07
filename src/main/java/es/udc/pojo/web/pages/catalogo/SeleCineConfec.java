package es.udc.pojo.web.pages.catalogo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.DateField;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;

import es.udc.pojo.model.catalogoservice.CatalogoService;
import es.udc.pojo.model.cine.Cine;
import es.udc.pojo.model.provincia.Provincia;
import es.udc.pojo.model.sesion.Sesion;
import es.udc.pojo.model.util.BadArgumentException;
import es.udc.pojo.model.util.CoincidentSesionsException;
import es.udc.pojo.web.encoders.CineEncoder;
import es.udc.pojo.web.encoders.ProvinciaEncoder;
import es.udc.pojo.web.services.AuthenticationPolicy;
import es.udc.pojo.web.services.AuthenticationPolicyType;

/**
 * The Class SeleCineConfec.
 */
@AuthenticationPolicy(AuthenticationPolicyType.ADMINISTRADOR)
public class SeleCineConfec {

    /** The response. */
    HttpServletResponse   response;

    /** The http request. */
    HttpServletRequest    httpRequest;

    /** The confeccionar cartelera. */
    @InjectPage
    ConfeccionarCartelera confeccionarCartelera;

    /** The catalogo service. */
    @Inject
    CatalogoService       catalogoService;

    /** The messages. */
    @Inject
    private Messages      messages;

    /** The date. */
    @Property
    private Date          date;

    /** The provincia. */
    @Property
    private Provincia     provincia;

    /** The n provincia. */
    @Property
    private String        nProvincia;

    /** The select provincia. */
    @Property
    private Provincia     selectProvincia;

    /** The cine. */
    @Property
    private Cine          cine;

    /** The n cine. */
    @Property
    private String        nCine;

    /** The select cine. */
    @Property
    private Cine          selectCine;

    /*
     * SelectModel es un componente que nos permite crear una serie de valores
     * para un componente select, a partir de una lista de objetos
     */
    /** The cines select model. */
    @Property
    private SelectModel   cinesSelectModel;

    // Esta clase se emplea para crear los SelectModel del Cine y la Prov
    /** The select model factory. */
    @Inject
    SelectModelFactory    selectModelFactory;

    /** The select cine form. */
    @Component
    private Form          selectCineForm;

    /** The date field. */
    @Component(id = "date")
    private DateField     dateField;

    /** The model zone cine. */
    @InjectComponent
    private Zone          modelZoneCine;

    // CODE

    /**
     * On activate.
     */
    public void onActivate() {
        if (selectProvincia == null)
            cinesSelectModel = selectModelFactory.create(new ArrayList<Cine>(),
                    "nCine");
    }

    /**
     * Este método es llamado por la TML para obtener el selector de provincias.
     *
     * @return Un select model creado a partir de las provincias recuperadas
     */
    public SelectModel getProvinciasSelectModel() {
        List<Provincia> provincias = catalogoService.findProvincias();
        return selectModelFactory.create(provincias, "nProvincia");
    }

    /**
     * On value changed from select provincia.
     *
     * @param selectProvincia
     *            Provincia seleccionada actualmente
     * @return la Zona AJAX que contiene los cines de la provincia que se
     *         insetarán en el select del cine.
     */
    Object onValueChangedFromSelectProvincia(Provincia selectProvincia) {
        List<Cine> cines = new ArrayList<Cine>();
        cines.addAll(selectProvincia.getCines());
        cinesSelectModel = selectModelFactory.create(cines, "nCine");
        return modelZoneCine.getBody();
    }

    /**
     * On success.
     *
     * @return the object
     */
    Object onSuccess() {

        confeccionarCartelera.setIdCine(cine.getIdCine());
        confeccionarCartelera.setDateInMillis(date.getTime());

        return confeccionarCartelera;
    }

    /**
     * On validate from select cine form.
     */
    void onValidateFromSelectCineForm() {
        Calendar c = Calendar.getInstance();
        System.out.println("Date -> \n" + date);
        c.setTime(date);
        try {
            catalogoService.validarSesions(new ArrayList<Sesion>(), c,
                    cine.getIdCine());
        } catch (BadArgumentException e) {
            if (c.before(Calendar.getInstance())) {
                selectCineForm.recordError(dateField,
                        messages.format("date-pased"));
            } else {
                selectCineForm.recordError(dateField,
                        messages.format("date-whith-sesions"));
            }
        } catch (CoincidentSesionsException e) {
            // Inalcanzable, el array que pasamos está vacio
            e.printStackTrace();
        }

    }

    /**
     * Gets the provincia encoder.
     *
     * @return the provincia encoder
     */
    public ProvinciaEncoder getProvinciaEncoder() {
        return new ProvinciaEncoder(catalogoService);
    }

    /**
     * Gets the cine encoder.
     *
     * @return the cine encoder
     */
    public CineEncoder getCineEncoder() {
        return new CineEncoder(catalogoService);
    }
}
