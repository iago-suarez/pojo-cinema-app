package es.udc.pojo.web.pages.catalogo;

import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.model.catalogoservice.CatalogoService;
import es.udc.pojo.model.pelicula.Pelicula;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

/**
 * The Class PeliculaDetalles.
 */
public class PeliculaDetalles {

    /** The id pelicula. */
    private Long            idPelicula;

    /** The pelicula. */
    private Pelicula        pelicula;

    /** The catalogo service. */
    @Inject
    private CatalogoService catalogoService;

    /** The locale. */
    @Inject
    private Locale          locale;

    /**
     * Sets the id pelicula.
     *
     * @param idPelicula
     *            the new id pelicula
     */
    public void setIdPelicula(Long idPelicula) {
        this.idPelicula = idPelicula;
    }

    /**
     * Gets the pelicula.
     *
     * @return the pelicula
     */
    public Pelicula getPelicula() {
        return pelicula;
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
     * Gets the date format.
     *
     * @return the date format
     */
    public Format getDateFormat() {
        return DateFormat.getDateInstance(DateFormat.SHORT, locale);
    }

    /**
     * On passivate.
     *
     * @return the long
     */
    Long onPassivate() {

        return idPelicula;
    }

    /**
     * On activate.
     *
     * @param idPelicula
     *            the id pelicula
     */
    void onActivate(Long idPelicula) {

        this.idPelicula = idPelicula;
        try {
            pelicula = catalogoService.findPelicula(idPelicula);
        } catch (InstanceNotFoundException e) {

        }
    }

}
