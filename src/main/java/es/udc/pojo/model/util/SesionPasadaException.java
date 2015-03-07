package es.udc.pojo.model.util;

import es.udc.pojo.model.sesion.Sesion;

/**
 * The Class SesionPasadaException.
 */
@SuppressWarnings("serial")
public class SesionPasadaException extends Exception {

    /** The Sesion. */
    private Sesion Sesion;

    /**
     * Instantiates a new sesion pasada exception.
     *
     * @param sesion
     *            the sesion
     */
    public SesionPasadaException(Sesion sesion) {
        super("The Sesion whith id=\"" + sesion.getIdSesion()
                + "\" has expired.");

        this.setSesion(Sesion);
    }

    /**
     * Gets the sesion.
     *
     * @return the sesion
     */
    public Sesion getSesion() {
        return Sesion;
    }

    /**
     * Sets the sesion.
     *
     * @param Sesion
     *            the new sesion
     */
    public void setSesion(Sesion Sesion) {
        this.Sesion = Sesion;
    }
}
