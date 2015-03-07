package es.udc.pojo.web.encoders;

import org.apache.tapestry5.ValueEncoder;

import es.udc.pojo.model.catalogoservice.CatalogoService;
import es.udc.pojo.model.provincia.Provincia;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

/**
 * The Class ProvinciaEncoder.
 */
public class ProvinciaEncoder implements ValueEncoder<Provincia> {

    /** The catalogo service. */
    CatalogoService catalogoService;

    /**
     * Instantiates a new provincia encoder.
     *
     * @param provinciaFinderService
     *            the provincia finder service
     */
    public ProvinciaEncoder(CatalogoService provinciaFinderService) {
        this.catalogoService = provinciaFinderService;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.tapestry5.ValueEncoder#toClient(java.lang.Object)
     */
    @Override
    public String toClient(Provincia value) {
        return String.valueOf(value.getIdProvincia());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.tapestry5.ValueEncoder#toValue(java.lang.String)
     */
    @Override
    public Provincia toValue(String id) {
        try {
            return catalogoService.findProvincia(Long.parseLong(id));
        } catch (NumberFormatException | InstanceNotFoundException e) {
            return null;
        }
    }

}
