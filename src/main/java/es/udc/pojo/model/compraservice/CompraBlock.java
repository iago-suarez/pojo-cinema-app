package es.udc.pojo.model.compraservice;

import java.util.List;

import es.udc.pojo.model.compra.Compra;

/**
 * The Class CompraBlock.
 */
public class CompraBlock {

    /** The compras. */
    private List<Compra> compras;

    /** The exist more compras. */
    private boolean      existMoreCompras;

    /**
     * Instantiates a new compra block.
     *
     * @param compras
     *            the compras
     * @param existMoreCompras
     *            the exist more compras
     */
    public CompraBlock(List<Compra> compras, boolean existMoreCompras) {

        this.compras = compras;
        this.existMoreCompras = existMoreCompras;

    }

    /**
     * Gets the compras.
     *
     * @return the compras
     */
    public List<Compra> getCompras() {
        return compras;
    }

    /**
     * Gets the exist more compras.
     *
     * @return the exist more compras
     */
    public boolean getExistMoreCompras() {
        return existMoreCompras;
    }

}
