package es.udc.pojo.model.util;

import es.udc.pojo.model.sesion.Sesion;

@SuppressWarnings("serial")
public class SesionLlenaException extends Exception {
    private Sesion sesion;

    public SesionLlenaException(Sesion sesion) {
        super("La sesion con id = " + sesion.getIdSesion() + "está llena");
        this.setSesion(sesion);
    }

    public Sesion getSesion() {
        return sesion;
    }

    public void setSesion(Sesion sesion) {
        this.sesion = sesion;
    }
}
