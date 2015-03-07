package es.udc.pojo.web.pages.catalogo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Cookies;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.SelectModelFactory;

import es.udc.pojo.model.catalogoservice.CatalogoService;
import es.udc.pojo.model.cine.Cine;
import es.udc.pojo.model.provincia.Provincia;
import es.udc.pojo.web.encoders.CineEncoder;
import es.udc.pojo.web.encoders.ProvinciaEncoder;
import es.udc.pojo.web.pages.Index;
import es.udc.pojo.web.util.CookiesManager;

/**
 * The Class SeleccionCine.
 */
public class SeleccionCine {

    /** The response. */
    HttpServletResponse response;

    /** The http request. */
    HttpServletRequest  httpRequest;

    /** The select cine form. */
    @Component
    private Form        selectCineForm;

    /** The index. */
    @InjectPage
    Index               index;

    /** The request. */
    @Inject
    Request             request;

    /** The n cine. */
    private String      nCine;

    /** The n provincia. */
    private String      nProvincia;

    /** The provincia. */
    @Property
    private Provincia   provincia;

    /** The select cine. */
    @Property
    private Cine        selectCine;

    /** The select provincia. */
    @Property
    private Provincia   selectProvincia;

    /** The cine. */
    @Property
    private Cine        cine;

    /** The select model factory. */
    @Inject
    SelectModelFactory  selectModelFactory;

    /** The provincias select model. */
    @Property
    private SelectModel provinciasSelectModel;

    /** The cines select model. */
    @Property
    private SelectModel cinesSelectModel;

    /** The catalogo service. */
    @Inject
    CatalogoService     catalogoService;

    /** The cookies. */
    @Inject
    Cookies             cookies;

    /**
     * Gets the n provincia.
     *
     * @return the n provincia
     */
    public String getnProvincia() {

        return nProvincia;
    }

    /**
     * Sets the n provincia.
     *
     * @param aux
     *            the new n provincia
     */
    public void setnProvincia(String aux) {

        this.nProvincia = aux;
    }

    /**
     * Gets the n cine.
     *
     * @return the n cine
     */
    public String getnCine() {

        return nCine;
    }

    /** The model zone provincia. */
    @InjectComponent
    private Zone modelZoneProvincia;

    /**
     * On activate.
     */
    void onActivate() {
        List<Provincia> provincias = catalogoService.findProvincias();
        provinciasSelectModel = selectModelFactory.create(provincias,
                "nProvincia");

    }

    /**
     * On value changed from select provincia.
     *
     * @param selectProvincia
     *            the select provincia
     * @return the object
     */
    Object onValueChangedFromSelectProvincia(Provincia selectProvincia) {

        List<Cine> cines = new ArrayList<Cine>();
        cines.addAll(selectProvincia.getCines());
        cinesSelectModel = selectModelFactory.create(cines, "nCine");
        return modelZoneProvincia.getBody();
    }

    /**
     * On success.
     *
     * @return the object
     */
    Object onSuccess() {

        CookiesManager.leaveCookieCinePref(cookies, cine.getIdCine());
        return index;
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
