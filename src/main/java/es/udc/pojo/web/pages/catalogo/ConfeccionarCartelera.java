package es.udc.pojo.web.pages.catalogo;

import java.text.DateFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.SelectModelFactory;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import es.udc.pojo.model.catalogoservice.CatalogoService;
import es.udc.pojo.model.cine.Cine;
import es.udc.pojo.model.pelicula.Pelicula;
import es.udc.pojo.model.sala.Sala;
import es.udc.pojo.model.sesion.Sesion;
import es.udc.pojo.model.sesion.SesionsDateComparator;
import es.udc.pojo.model.util.BadArgumentException;
import es.udc.pojo.model.util.CoincidentSesionsException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pojo.web.encoders.CineEncoder;
import es.udc.pojo.web.services.AuthenticationPolicy;
import es.udc.pojo.web.services.AuthenticationPolicyType;

/**
 * The Class ConfeccionarCartelera.
 */
@AuthenticationPolicy(AuthenticationPolicyType.ADMINISTRADOR)
public class ConfeccionarCartelera {

    // Definimos estas constantes para manejar los posibles botones
    // del formulario:
    /** The Constant ADD_ACTION. */
    private static final int       ADD_ACTION      = 1;

    /** The Constant FINISH_ACTION. */
    private static final int       FINISH_ACTION   = 2;

    /** The id cine. */
    private Long                   idCine;

    /** The cine. */
    @Property
    private Cine                   cine;

    /** The date in millis. */
    private long                   dateInMillis;

    /** The date. */
    private Date                   date;

    /** The sesions. */
    @Persist(PersistenceConstants.SESSION)
    private List<Sesion>           sesions;

    /** The sesion. */
    @Property
    private Sesion                 sesion;

    /** The catalogo service. */
    @Inject
    private CatalogoService        catalogoService;

    /** The salas. */
    private List<Sala>             salas;

    /** The peliculas. */
    private List<Pelicula>         peliculas;

    /** The locale. */
    @Inject
    private Locale                 locale;

    /** The message format. */
    @Property
    private MessageFormat          messageFormat;

    /** The table index. */
    @Property
    private int                    tableIndex;

    /** ************************* FORM ****************************. */

    @Inject
    private Request                request;

    // Nos permite recargar más de una zona a la vez
    /** The ajax response renderer. */
    @Inject
    private AjaxResponseRenderer   ajaxResponseRenderer;

    /** The form zone. */
    @InjectComponent
    private Zone                   formZone;

    /** The n sala. */
    @Property
    private String                 nSala;

    /** The sala. */
    @Property
    private Sala                   sala;

    /** The select sala. */
    @Property
    private Sala                   selectSala;

    /** The sala encoder. */
    @Property
    private SalaEncoder            salaEncoder     = new SalaEncoder();

    /** The salas select model. */
    @Property
    private SelectModel            salasSelectModel;

    /** The pelicula. */
    @Property
    private Pelicula               pelicula;

    /** The select pelicula. */
    @Property
    private Pelicula               selectPelicula;

    /** The pelicula encoder. */
    @Property
    private PeliculaEncoder        peliculaEncoder = new PeliculaEncoder();

    /** The peliculas select model. */
    @Property
    private SelectModel            peliculasSelectModel;

    /** The select model factory. */
    @Inject
    SelectModelFactory             selectModelFactory;

    /** The model zone tabla. */
    @InjectComponent
    private Zone                   modelZoneTabla;

    /** The hora. */
    @Property
    private String                 hora;

    /** The hora text field. */
    @Component(id = "hora")
    private TextField              horaTextField;

    /** The min. */
    @Property
    private String                 min;

    /** The min text field. */
    @Component(id = "min")
    private TextField              minTextField;

    /** The sesion form. */
    @Component
    private Form                   sesionForm;

    /** The cartelera confeccionada. */
    @InjectPage
    private CarteleraConfeccionada carteleraConfeccionada;

    /** The messages. */
    @Inject
    private Messages               messages;

    /*
     * define la accionon que se está llevando a cabo en el formulario de
     * creacion de sesiones, a saber: ADD o FININISH
     */
    /** The actual action. */
    private int                    actualAction;

    /**
     * *********************** CODE **********************.
     *
     * @param idCine
     *            the id cine
     * @param dateInMillis
     *            the date in millis
     */

    // Cargamos el cine, y las peliculas actuales
    void onActivate(Long idCine, long dateInMillis) {
        this.idCine = idCine;
        this.dateInMillis = dateInMillis;

        this.date = new Date();
        this.date.setTime(dateInMillis);

        this.sesions = getSesions();

        this.salas = getSalas();
        salasSelectModel = selectModelFactory.create(this.salas, "nSala");
        this.peliculas = getPeliculas();
        peliculasSelectModel = selectModelFactory.create(this.peliculas,
                "titulo");

    }

    /**
     * On passivate.
     *
     * @return the object[]
     */
    Object[] onPassivate() {

        return new Object[] { idCine, dateInMillis };
    }

    // FORM HANDLERS

    /**
     * On value changed from select sala.
     *
     * @param selectSala
     *            the select sala
     * @return the object
     */
    Object onValueChangedFromSelectSala(Sala selectSala) {
        this.selectSala = selectSala;
        return null;
    }

    /**
     * On value changed from select pelicula.
     *
     * @param selectPelicula
     *            the select pelicula
     * @return the object
     */
    Object onValueChangedFromSelectPelicula(Pelicula selectPelicula) {
        this.selectPelicula = selectPelicula;
        return null;
    }

    /**
     * On validate from sesion form.
     */
    void onValidateFromSesionForm() {
        Sesion newSesion = null;
        System.out.println("--> Validamosss! ");

        if (!sesionForm.isValid())
            return;

        // Si se ha pulsado el boton de anadir, anadimos la sesion
        if (actualAction == ADD_ACTION) {
            newSesion = createSesionFromForm();
            sesions.add(newSesion);
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        try {
            catalogoService.validarSesions(sesions, c, idCine);
            System.out.println("----> Validacion correcta ");

        } catch (Exception e) {
            if (actualAction == ADD_ACTION) {
                sesions.remove(newSesion);
            }
            try {
                throw e;
            } catch (BadArgumentException e2) {
                // Los parámetros de la URL han sido probablemente modificados
                // Ya que esto se comprueba en la página anterior SeleCineConfec
                if (c.before(Calendar.getInstance())) {
                    sesionForm.recordError(messages.format("date-pased"));
                } else {
                    sesionForm.recordError(messages
                            .format("date-whith-sesions"));
                }
            } catch (CoincidentSesionsException e2) {
                Format formater = getDateFormatHour();
                String initHour = formater.format(e2.getS1().getHorayFecha()
                        .getTime());
                // Calculamos la hora a la que acaba la sesion
                Calendar finishSesionCal = (Calendar) e2.getS1()
                        .getHorayFecha().clone();
                finishSesionCal.add(Calendar.MINUTE, e2.getS1().getPelicula()
                        .getDuracion());
                String finishHour = formater.format(finishSesionCal.getTime());

                sesionForm.recordError(messages.format(
                        "error-CoincidentSesionsException", initHour,
                        finishHour));
            }

        }
    }

    /**
     * Save as add action.
     */
    @OnEvent(component = "anadir", value = EventConstants.SELECTED)
    void saveAsAddAction() {
        actualAction = ADD_ACTION;
    }

    /**
     * Save as finish action.
     */
    @OnEvent(component = "finish", value = EventConstants.SELECTED)
    void saveAsFinishAction() {
        actualAction = FINISH_ACTION;
    }

    /**
     * onSuccess se llama tras la fase de validación si todos los campos han
     * sido alidados de forma correcta, con el objetivo de manejar los botones
     * anadir y finish.
     * 
     * @return la zona AJAX de la tabla si manejamos el boton de anadir y la
     *         página de carteleraConfeccionada si manejamos el boton finish.
     */
    Object onSuccess() {

        switch (actualAction) {
        /*
         * Si se ha clicado el boton de anadir y la validacion ha sido exitosa
         * entonces solo ordenamos la lista de elementos y recargamos la zona.
         */
        case ADD_ACTION:
            System.out.println("--> Añadiendo");

            Collections.sort(sesions, new SesionsDateComparator());

            // Gestionamos la recarga de las zonas AJAX
            ajaxResponseRenderer.addRender(formZone).addRender(modelZoneTabla);
            return null;
            /*
             * Si se ha clicado el boton de finish se envía la lista de sesiones
             * al modelo, se vacia la lista de la página, y se redirige a
             * carteleraConfeccionada.
             */
        case FINISH_ACTION:
            System.out.println("--> Finalizando");
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            try {
                catalogoService.createSesions(sesions, c, idCine);
            } catch (BadArgumentException | CoincidentSesionsException e) {
                // Caso inalcanzable, estes errores han sido validados en
                // onValidate
                e.printStackTrace();
            }
            sesions.clear();

            carteleraConfeccionada.setDateInMillis(dateInMillis);
            carteleraConfeccionada.setIdCine(idCine);
            return carteleraConfeccionada;

        default: // Caso no contemplado
            return null;
        }
    }

    /**
     * On failure.
     */
    void onFailure() {
        ajaxResponseRenderer.addRender(formZone);
    }

    /**
     * Crea una nueva sesion en base a los datos del formulario PreCND: Los
     * datos del formulario han de ser válidos.
     *
     * @return the sesion
     */
    private Sesion createSesionFromForm() {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hora));
        c.set(Calendar.MINUTE, Integer.parseInt(min));

        return new Sesion(cine.getPrecioEntrada(), c, pelicula, sala, 0);

    }

    /**
     * On action from eliminar.
     *
     * @param tableIndex
     *            the table index
     * @return the object
     */
    Object onActionFromEliminar(int tableIndex) {
        sesions.remove(tableIndex);
        return modelZoneTabla.getBody();
    }

    // FORMATS

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
     * Gets the fomated date.
     *
     * @return the fomated date
     */
    public String getFomatedDate() {
        DateFormat f = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        return f.format(date);
    }

    /**
     * Gets the min date.
     *
     * @return the min date
     */
    public Date getMinDate() {
        return Calendar.getInstance().getTime();
    }

    // PROPERTIES GETTERS & SETTERS

    /**
     * Gets the date in millis.
     *
     * @return the date in millis
     */
    public long getDateInMillis() {
        return dateInMillis;
    }

    /**
     * Sets the date in millis.
     *
     * @param dateInMillis
     *            the new date in millis
     */
    public void setDateInMillis(long dateInMillis) {
        this.dateInMillis = dateInMillis;
    }

    /**
     * Gets the id cine.
     *
     * @return the id cine
     */
    public Long getIdCine() {
        return cine.getIdCine();
    }

    /**
     * Sets the id cine.
     *
     * @param idCine
     *            the new id cine
     */
    public void setIdCine(Long idCine) {
        this.idCine = idCine;
    }

    /**
     * Gets the n sala.
     *
     * @return the n sala
     */
    public String getnSala() {

        return nSala;
    }

    /**
     * Sets the n sala.
     *
     * @param aux
     *            the new n sala
     */
    public void setnSala(String aux) {

        this.nSala = aux;
    }

    /**
     * Gets the salas.
     *
     * @return the salas
     */
    private List<Sala> getSalas() {
        if (this.cine == null) {
            try {
                this.cine = catalogoService.findCine(idCine);
            } catch (InstanceNotFoundException e) {
                // En este caso es probable que hayan modificado la URL
                // Y que el cine que buscan no exista
                salas = new ArrayList<Sala>();
                return salas;
            }
        }

        if (salas == null) {
            salas = new ArrayList<Sala>();
            salas.addAll(cine.getSalas());
        }
        return salas;
    }

    /**
     * Gets the peliculas.
     *
     * @return the peliculas
     */
    private List<Pelicula> getPeliculas() {

        if (peliculas == null) {

            Calendar c = Calendar.getInstance();
            c.setTime(date);
            peliculas = catalogoService.findPeliculasActivasADia(c);
        }
        return peliculas;
    }

    /**
     * Gets the sesions.
     *
     * @return the sesions
     */
    public List<Sesion> getSesions() {

        if (sesions == null) {
            System.out.println("Generating Empthy sesions array");
            sesions = new ArrayList<Sesion>();
        }
        return sesions;
    }

    /**
     * Gets the cine encoder.
     *
     * @return the cine encoder
     */
    public CineEncoder getCineEncoder() {
        return new CineEncoder(catalogoService);
    }

    // ENCODERS

    /**
     * The Class SalaEncoder.
     */
    private class SalaEncoder implements ValueEncoder<Sala> {

        /*
         * (non-Javadoc)
         * 
         * @see org.apache.tapestry5.ValueEncoder#toClient(java.lang.Object)
         */
        @Override
        public String toClient(Sala sala) {
            return String.valueOf(sala.getIdSala());
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.apache.tapestry5.ValueEncoder#toValue(java.lang.String)
         */
        @Override
        public Sala toValue(String nSala) {
            List<Sala> salas = getSalas();
            for (Sala s : salas) {
                if (nSala.equals(String.valueOf(s.getIdSala())))
                    return s;
            }
            System.err.println("Sala Encoder: Error al parsear el string"
                    + nSala);
            return null;
        }
    }

    /**
     * The Class PeliculaEncoder.
     */
    private class PeliculaEncoder implements ValueEncoder<Pelicula> {

        /*
         * (non-Javadoc)
         * 
         * @see org.apache.tapestry5.ValueEncoder#toClient(java.lang.Object)
         */
        @Override
        public String toClient(Pelicula pelicula) {
            return String.valueOf(pelicula.getTitulo());
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.apache.tapestry5.ValueEncoder#toValue(java.lang.String)
         */
        @Override
        public Pelicula toValue(String titulo) {
            List<Pelicula> peliculas = getPeliculas();
            for (Pelicula p : peliculas) {
                if (titulo.equals(String.valueOf(p.getTitulo())))
                    return p;
            }
            System.err.println("Pelicula Encoder: Error al parsear el string"
                    + titulo);
            return null;
        }
    }

}