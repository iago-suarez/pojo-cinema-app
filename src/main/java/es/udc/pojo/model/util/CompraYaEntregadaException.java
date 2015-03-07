package es.udc.pojo.model.util;

import es.udc.pojo.model.compra.Compra;

/**
 * The Class CompraYaEntregadaException.
 */
@SuppressWarnings("serial")
public class CompraYaEntregadaException extends Exception {

    /** The compra. */
    private Compra compra;

    /**
     * Instantiates a new compra ya entregada exception.
     *
     * @param compra
     *            the compra
     */
    public CompraYaEntregadaException(Compra compra) {
        super("Compra con id = " + compra.getIdCompra() + "ya entregada");
        this.setCompra(compra);
    }

    /**
     * Gets the compra.
     *
     * @return the compra
     */
    public Compra getCompra() {
        return compra;
    }

    /**
     * Sets the compra.
     *
     * @param compra
     *            the new compra
     */
    public void setCompra(Compra compra) {
        this.compra = compra;
    }
}
