package es.udc.pojo.web.encoders;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.ValueEncoder;

import es.udc.pojo.model.catalogoservice.CatalogoService;
import es.udc.pojo.model.sesion.Sesion;

public class SesionsEncoder implements ValueEncoder<List<Sesion>> {

    private static String specialCharacter = "!";

    private SesionEncoder sesionEncoder;

    public SesionsEncoder(CatalogoService catalogoService) {
        sesionEncoder = new SesionEncoder(catalogoService);
    }

    public String toClient(List<Sesion> value) {
        String result = "";

        for (int i = 0; i < value.size(); i++) {
            if (i == 0)
                result += sesionEncoder.toClient(value.get(i));
            else
                result += specialCharacter
                        + sesionEncoder.toClient(value.get(i));
        }
        return result;
    }

    public List<Sesion> toValue(String entrada) {

        ArrayList<Sesion> result = new ArrayList<Sesion>();

        String[] sesions = entrada.split(specialCharacter);
        for (String s : sesions) {
            result.add(sesionEncoder.toValue(s));
        }

        return result;
    }

}
