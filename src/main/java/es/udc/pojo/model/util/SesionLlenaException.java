package es.udc.pojo.model.util;

import es.udc.pojo.model.sesion.Sesion;

/**
 * The Class SesionLlenaException.
 */
@SuppressWarnings("serial")
public class SesionLlenaException extends Exception {

    /** The sesion. */
    private Sesion sesion;

    /**
     * Instantiates a new sesion llena exception.
     *
     * @param sesion
     *            the sesion
     */
    public SesionLlenaException(Sesion sesion) {
        super("La sesion con id = " + sesion.getIdSesion() + "está llena");
        this.setSesion(sesion);
    }

    /**
     * Gets the sesion.
     *
     * @return the sesion
     */
    public Sesion getSesion() {
        return sesion;
    }

    /**
     * Sets the sesion.
     *
     * @param sesion
     *            the new sesion
     */
    public void setSesion(Sesion sesion) {
        this.sesion = sesion;
    }
}
