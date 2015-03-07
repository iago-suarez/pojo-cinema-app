package es.udc.pojo.web.encoders;

import org.apache.tapestry5.ValueEncoder;

import es.udc.pojo.model.catalogoservice.CatalogoService;
import es.udc.pojo.model.provincia.Provincia;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public class ProvinciaEncoder implements ValueEncoder<Provincia> {

    CatalogoService catalogoService;

    public ProvinciaEncoder(CatalogoService provinciaFinderService) {
        this.catalogoService = provinciaFinderService;
    }

    @Override
    public String toClient(Provincia value) {
        return String.valueOf(value.getIdProvincia());
    }

    @Override
    public Provincia toValue(String id) {
        try {
            return catalogoService.findProvincia(Long.parseLong(id));
        } catch (NumberFormatException | InstanceNotFoundException e) {
            return null;
        }
    }

}
