package es.udc.pojo.web.encoders;

import org.apache.tapestry5.ValueEncoder;

import es.udc.pojo.model.catalogoservice.CatalogoService;
import es.udc.pojo.model.cine.Cine;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public class CineEncoder implements ValueEncoder<Cine> {

    CatalogoService catalogoService;

    public CineEncoder(CatalogoService cineFinderService) {
        this.catalogoService = cineFinderService;
    }

    @Override
    public String toClient(Cine value) {
        return String.valueOf(value.getIdCine());
    }

    @Override
    public Cine toValue(String id) {
        try {
            return catalogoService.findCine(Long.parseLong(id));
        } catch (NumberFormatException | InstanceNotFoundException e) {
            return null;
        }
    }

}
