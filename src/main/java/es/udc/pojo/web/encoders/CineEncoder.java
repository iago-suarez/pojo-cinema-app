package es.udc.pojo.web.encoders;

import org.apache.tapestry5.ValueEncoder;

import es.udc.pojo.model.catalogoservice.CatalogoService;
import es.udc.pojo.model.cine.Cine;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

/**
 * The Class CineEncoder.
 */
public class CineEncoder implements ValueEncoder<Cine> {

    /** The catalogo service. */
    CatalogoService catalogoService;

    /**
     * Instantiates a new cine encoder.
     *
     * @param cineFinderService
     *            the cine finder service
     */
    public CineEncoder(CatalogoService cineFinderService) {
        this.catalogoService = cineFinderService;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.tapestry5.ValueEncoder#toClient(java.lang.Object)
     */
    @Override
    public String toClient(Cine value) {
        return String.valueOf(value.getIdCine());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.tapestry5.ValueEncoder#toValue(java.lang.String)
     */
    @Override
    public Cine toValue(String id) {
        try {
            return catalogoService.findCine(Long.parseLong(id));
        } catch (NumberFormatException | InstanceNotFoundException e) {
            return null;
        }
    }

}
