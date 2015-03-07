package es.udc.pojo.web.pages;

import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Cookies;

import es.udc.pojo.model.catalogoservice.CatalogoService;
import es.udc.pojo.model.catalogoservice.PeliculaSesionDto;
import es.udc.pojo.model.sesion.Sesion;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pojo.web.pages.catalogo.SeleccionCine;
import es.udc.pojo.web.util.CookiesManager;
import es.udc.pojo.web.util.UserSession;

public class Index {

    @Inject
    private CatalogoService   catalogoService;

    private final static int  NUMBER_OF_DAYS = 6;
    private PeliculaSesionDto peliculaSesion;
    private Sesion            sesion;
    private Integer           amount;
    private int               offset         = 0;
    private Calendar          dia;

    @SessionState(create = false)
    private UserSession       userSession;

    @Inject
    private Cookies           cookies;

    @Inject
    private Locale            locale;

    @InjectPage
    private SeleccionCine     seleccionCine;

    private Calendar          day;

    public String getNCine() {
        String nCine = null;
        try {
            /*
             * Siempre habra un cine preferido, ya que si no lo hay se redirige
             * a seleccionCine en onActivate
             */
            nCine = catalogoService.findCine(
                    CookiesManager.getCinePreferido(cookies)).getnCine();
        } catch (InstanceNotFoundException e) {

        }
        return nCine;
    }

    public int getOffset() {
        return offset++;
    }

    public List<Calendar> getDays() {
        List<Calendar> days = new ArrayList<Calendar>();
        for (int i = 1; i <= NUMBER_OF_DAYS; i++) {
            Calendar d = Calendar.getInstance();
            d.add(Calendar.DAY_OF_MONTH, i);
            days.add(d);
        }
        return days;
    }

    public Calendar getDia() {
        return dia;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer a) {
        amount = a;
    }

    public Calendar getDay() {
        return day;
    }

    public void setDia(Calendar dia) {
        this.dia = dia;
    }

    public void setDay(Calendar day) {
        this.day = day;
    }

    public PeliculaSesionDto getPeliculaSesion() {
        return peliculaSesion;
    }

    public void setPeliculaSesion(PeliculaSesionDto p) {
        this.peliculaSesion = p;
    }

    public Sesion getSesion() {
        return sesion;
    }

    public void setSesion(Sesion s) {
        this.sesion = s;
    }

    public Format getNumberFormat() {
        return NumberFormat.getInstance(locale);
    }

    public Format getDateFormatHour() {
        SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat
                .getDateInstance();
        dateFormat.applyPattern("HH:mm");
        return dateFormat;
    }

    public Format getDateFormatLong() {
        return DateFormat.getDateInstance(DateFormat.LONG, locale);
    }

    public Format getDateFormatShort() {
        return DateFormat.getDateInstance(DateFormat.SHORT, locale);
    }

    Integer onPassivate() {
        return amount;
    }

    Object onActivate() {
        if (CookiesManager.getCinePreferido(cookies) == null)
            return seleccionCine;
        else {
            return null;
        }
    }

    Object onActivate(Integer amount) {
        if (CookiesManager.getCinePreferido(cookies) == null)
            return seleccionCine;
        else {

            this.amount = amount;
            day = Calendar.getInstance();
            day.add(Calendar.DAY_OF_MONTH, this.amount);
            return null;
        }
    }

    public List<PeliculaSesionDto> getPeliculaSesiones() {

        if (day == null) {
            day = Calendar.getInstance();
        }

        return catalogoService.findPeliculas(
                CookiesManager.getCinePreferido(cookies), day);
    }

    public Object onActionFromOtroCine() {
        CookiesManager.removeCinePreferidoCookies(cookies);
        return seleccionCine;
    }

}
