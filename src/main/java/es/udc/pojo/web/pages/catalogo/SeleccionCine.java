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

public class SeleccionCine {

    HttpServletResponse response;
    HttpServletRequest  httpRequest;

    @Component
    private Form        selectCineForm;

    @InjectPage
    Index               index;

    @Inject
    Request             request;

    private String      nCine;

    private String      nProvincia;

    @Property
    private Provincia   provincia;

    @Property
    private Cine        selectCine;

    @Property
    private Provincia   selectProvincia;

    @Property
    private Cine        cine;

    @Inject
    SelectModelFactory  selectModelFactory;

    @Property
    private SelectModel provinciasSelectModel;

    @Property
    private SelectModel cinesSelectModel;

    @Inject
    CatalogoService     catalogoService;

    @Inject
    Cookies             cookies;

    public String getnProvincia() {

        return nProvincia;
    }

    public void setnProvincia(String aux) {

        this.nProvincia = aux;
    }

    public String getnCine() {

        return nCine;
    }

    @InjectComponent
    private Zone modelZoneProvincia;

    void onActivate() {
        List<Provincia> provincias = catalogoService.findProvincias();
        provinciasSelectModel = selectModelFactory.create(provincias,
                "nProvincia");

    }

    Object onValueChangedFromSelectProvincia(Provincia selectProvincia) {

        List<Cine> cines = new ArrayList<Cine>();
        cines.addAll(selectProvincia.getCines());
        cinesSelectModel = selectModelFactory.create(cines, "nCine");
        return modelZoneProvincia.getBody();
    }

    Object onSuccess() {

        CookiesManager.leaveCookieCinePref(cookies, cine.getIdCine());
        return index;
    }

    public ProvinciaEncoder getProvinciaEncoder() {
        return new ProvinciaEncoder(catalogoService);
    }

    public CineEncoder getCineEncoder() {
        return new CineEncoder(catalogoService);
    }

}
