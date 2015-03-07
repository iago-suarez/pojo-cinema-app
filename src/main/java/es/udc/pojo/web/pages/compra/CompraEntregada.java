package es.udc.pojo.web.pages.compra;

import es.udc.pojo.web.services.AuthenticationPolicy;
import es.udc.pojo.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.TAQUILLERO)
public class CompraEntregada {

    private Long idCompra;

    public Long getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Long idCompra) {
        this.idCompra = idCompra;
    }

}