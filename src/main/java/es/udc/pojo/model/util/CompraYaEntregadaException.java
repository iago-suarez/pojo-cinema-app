package es.udc.pojo.model.util;

import es.udc.pojo.model.compra.Compra;

@SuppressWarnings("serial")
public class CompraYaEntregadaException extends Exception {
    private Compra compra;

    public CompraYaEntregadaException(Compra compra) {
        super("Compra con id = " + compra.getIdCompra() + "ya entregada");
        this.setCompra(compra);
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }
}
