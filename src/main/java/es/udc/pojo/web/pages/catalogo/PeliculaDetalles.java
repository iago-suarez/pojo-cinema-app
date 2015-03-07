package es.udc.pojo.web.pages.catalogo;

import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.model.catalogoservice.CatalogoService;
import es.udc.pojo.model.pelicula.Pelicula;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public class PeliculaDetalles {

    private Long            idPelicula;
    private Pelicula        pelicula;

    @Inject
    private CatalogoService catalogoService;

    @Inject
    private Locale          locale;

    public void setIdPelicula(Long idPelicula) {
        this.idPelicula = idPelicula;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public Format getNumberFormat() {
        return NumberFormat.getInstance(locale);
    }

    public Format getDateFormat() {
        return DateFormat.getDateInstance(DateFormat.SHORT, locale);
    }

    Long onPassivate() {

        return idPelicula;
    }

    void onActivate(Long idPelicula) {

        this.idPelicula = idPelicula;
        try {
            pelicula = catalogoService.findPelicula(idPelicula);
        } catch (InstanceNotFoundException e) {

        }
    }

}
