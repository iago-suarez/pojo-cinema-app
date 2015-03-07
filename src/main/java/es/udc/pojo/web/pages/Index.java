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

/**
 * The Class Index.
 */
public class Index {

    /** The catalogo service. */
    @Inject
    private CatalogoService   catalogoService;

    /** The Constant NUMBER_OF_DAYS. */
    private final static int  NUMBER_OF_DAYS = 6;

    /** The pelicula sesion. */
    private PeliculaSesionDto peliculaSesion;

    /** The sesion. */
    private Sesion            sesion;

    /** The amount. */
    private Integer           amount;

    /** The offset. */
    private int               offset         = 0;

    /** The dia. */
    private Calendar          dia;

    /** The user session. */
    @SessionState(create = false)
    private UserSession       userSession;

    /** The cookies. */
    @Inject
    private Cookies           cookies;

    /** The locale. */
    @Inject
    private Locale            locale;

    /** The seleccion cine. */
    @InjectPage
    private SeleccionCine     seleccionCine;

    /** The day. */
    private Calendar          day;

    /**
     * Gets the n cine.
     *
     * @return the n cine
     */
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

    /**
     * Gets the offset.
     *
     * @return the offset
     */
    public int getOffset() {
        return offset++;
    }

    /**
     * Gets the days.
     *
     * @return the days
     */
    public List<Calendar> getDays() {
        List<Calendar> days = new ArrayList<Calendar>();
        for (int i = 1; i <= NUMBER_OF_DAYS; i++) {
            Calendar d = Calendar.getInstance();
            d.add(Calendar.DAY_OF_MONTH, i);
            days.add(d);
        }
        return days;
    }

    /**
     * Gets the dia.
     *
     * @return the dia
     */
    public Calendar getDia() {
        return dia;
    }

    /**
     * Gets the amount.
     *
     * @return the amount
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Sets the amount.
     *
     * @param a
     *            the new amount
     */
    public void setAmount(Integer a) {
        amount = a;
    }

    /**
     * Gets the day.
     *
     * @return the day
     */
    public Calendar getDay() {
        return day;
    }

    /**
     * Sets the dia.
     *
     * @param dia
     *            the new dia
     */
    public void setDia(Calendar dia) {
        this.dia = dia;
    }

    /**
     * Sets the day.
     *
     * @param day
     *            the new day
     */
    public void setDay(Calendar day) {
        this.day = day;
    }

    /**
     * Gets the pelicula sesion.
     *
     * @return the pelicula sesion
     */
    public PeliculaSesionDto getPeliculaSesion() {
        return peliculaSesion;
    }

    /**
     * Sets the pelicula sesion.
     *
     * @param p
     *            the new pelicula sesion
     */
    public void setPeliculaSesion(PeliculaSesionDto p) {
        this.peliculaSesion = p;
    }

    /**
     * Gets the sesion.
     *
     * @return the sesion
     */
    public Sesion getSesion() {
        return sesion;
    }

    /**
     * Sets the sesion.
     *
     * @param s
     *            the new sesion
     */
    public void setSesion(Sesion s) {
        this.sesion = s;
    }

    /**
     * Gets the number format.
     *
     * @return the number format
     */
    public Format getNumberFormat() {
        return NumberFormat.getInstance(locale);
    }

    /**
     * Gets the date format hour.
     *
     * @return the date format hour
     */
    public Format getDateFormatHour() {
        SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat
                .getDateInstance();
        dateFormat.applyPattern("HH:mm");
        return dateFormat;
    }

    /**
     * Gets the date format long.
     *
     * @return the date format long
     */
    public Format getDateFormatLong() {
        return DateFormat.getDateInstance(DateFormat.LONG, locale);
    }

    /**
     * Gets the date format short.
     *
     * @return the date format short
     */
    public Format getDateFormatShort() {
        return DateFormat.getDateInstance(DateFormat.SHORT, locale);
    }

    /**
     * On passivate.
     *
     * @return the integer
     */
    Integer onPassivate() {
        return amount;
    }

    /**
     * On activate.
     *
     * @return the object
     */
    Object onActivate() {
        if (CookiesManager.getCinePreferido(cookies) == null)
            return seleccionCine;
        else {
            return null;
        }
    }

    /**
     * On activate.
     *
     * @param amount
     *            the amount
     * @return the object
     */
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

    /**
     * Gets the pelicula sesiones.
     *
     * @return the pelicula sesiones
     */
    public List<PeliculaSesionDto> getPeliculaSesiones() {

        if (day == null) {
            day = Calendar.getInstance();
        }

        return catalogoService.findPeliculas(
                CookiesManager.getCinePreferido(cookies), day);
    }

    /**
     * On action from otro cine.
     *
     * @return the object
     */
    public Object onActionFromOtroCine() {
        CookiesManager.removeCinePreferidoCookies(cookies);
        return seleccionCine;
    }

}
