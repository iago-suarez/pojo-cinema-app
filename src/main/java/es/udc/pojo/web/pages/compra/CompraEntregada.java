package es.udc.pojo.web.pages.compra;

import es.udc.pojo.web.services.AuthenticationPolicy;
import es.udc.pojo.web.services.AuthenticationPolicyType;

/**
 * The Class CompraEntregada.
 */
@AuthenticationPolicy(AuthenticationPolicyType.TAQUILLERO)
public class CompraEntregada {

    /** The id compra. */
    private Long idCompra;

    /**
     * Gets the id compra.
     *
     * @return the id compra
     */
    public Long getIdCompra() {
        return idCompra;
    }

    /**
     * Sets the id compra.
     *
     * @param idCompra
     *            the new id compra
     */
    public void setIdCompra(Long idCompra) {
        this.idCompra = idCompra;
    }

}