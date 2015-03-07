package es.udc.pojo.model.compraservice;

import java.util.List;

import es.udc.pojo.model.compra.Compra;

public class CompraBlock {

    private List<Compra> compras;
    private boolean      existMoreCompras;

    public CompraBlock(List<Compra> compras, boolean existMoreCompras) {

        this.compras = compras;
        this.existMoreCompras = existMoreCompras;

    }

    public List<Compra> getCompras() {
        return compras;
    }

    public boolean getExistMoreCompras() {
        return existMoreCompras;
    }

}
