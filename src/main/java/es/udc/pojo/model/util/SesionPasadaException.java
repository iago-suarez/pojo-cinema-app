package es.udc.pojo.model.util;

import es.udc.pojo.model.sesion.Sesion;

@SuppressWarnings("serial")
public class SesionPasadaException extends Exception {

    private Sesion Sesion;

    public SesionPasadaException(Sesion sesion) {
        super("The Sesion whith id=\"" + sesion.getIdSesion()
                + "\" has expired.");

        this.setSesion(Sesion);
    }

    public Sesion getSesion() {
        return Sesion;
    }

    public void setSesion(Sesion Sesion) {
        this.Sesion = Sesion;
    }
}
