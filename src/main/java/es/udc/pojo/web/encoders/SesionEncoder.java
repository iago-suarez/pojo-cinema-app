package es.udc.pojo.web.encoders;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.tapestry5.ValueEncoder;

import es.udc.pojo.model.catalogoservice.CatalogoService;
import es.udc.pojo.model.sesion.Sesion;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public class SesionEncoder implements ValueEncoder<Sesion> {

    CatalogoService  catalogoService;

    SimpleDateFormat sdf           = new SimpleDateFormat(
                                           "EEE:MMM:dd:HH:mm:ss:z:yyyy");

    private String   nullString    = "null";

    private String   dividerString = "-";

    public SesionEncoder(CatalogoService sesionFinderService) {
        this.catalogoService = sesionFinderService;
    }

    @Override
    public String toClient(Sesion value) {
        String cadena = "";
        if (value.getIdSesion() != null) {
            cadena += value.getIdSesion();
        } else {
            cadena += nullString;
        }
        cadena += (dividerString + value.getPrecio());
        cadena += (dividerString + value.getHorayFecha().getTimeInMillis());

        if (value.getPelicula().getIdPelicula() != null)
            cadena += (dividerString + value.getPelicula().getIdPelicula());
        else {
            cadena += dividerString + nullString;
        }

        if (value.getSala().getIdSala() != null)
            cadena += (dividerString + value.getSala().getIdSala());
        else {
            cadena += dividerString + nullString;
        }
        cadena += (dividerString + value.getnAsistentes());

        return cadena;

    }

    @Override
    public Sesion toValue(String entrada) {

        String[] campos = entrada.split(dividerString);

        Sesion s = new Sesion();

        if (!campos[0].equals(nullString)) {
            s.setIdSesion(Long.parseLong(campos[0]));
        }

        s.setPrecio(Float.parseFloat(campos[1]));

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.parseLong(campos[2]));

        if (!campos[3].equals(nullString)) {
            try {
                s.setPelicula(catalogoService.findPelicula(Long
                        .parseLong(campos[3])));
            } catch (NumberFormatException | InstanceNotFoundException e) {
                e.printStackTrace();
            }
        }

        // if (!campos[4].equals(nullString)) {
        // try {
        // s.setSala(catalogoService.findSala(Long
        // .parseLong(campos[4])));
        // } catch (NumberFormatException | InstanceNotFoundException e) {
        // e.printStackTrace();
        // }
        // }

        s.setnAsistentes(Integer.parseInt(campos[5]));

        return s;

    }

}
