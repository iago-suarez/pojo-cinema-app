package es.udc.pojo.model.compraservice;

import java.util.Calendar;

import es.udc.pojo.model.compra.Compra;
import es.udc.pojo.model.util.CompraYaEntregadaException;
import es.udc.pojo.model.util.SesionLlenaException;
import es.udc.pojo.model.util.SesionPasadaException;
import es.udc.pojo.model.util.TarjetaCaducadaException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public interface CompraService {

    public Long createCompra(Long usrId, int numEntradas,
            String numTarjetaCredito, Calendar fechaExpTarjeta, Long idSesion)
            throws InstanceNotFoundException, SesionLlenaException,
            TarjetaCaducadaException, SesionPasadaException;

    public CompraBlock findCompras(Long usrId, int startIndex, int count);

    // TODO Hace falta un DTO ??
    public Compra findCompra(Long idCompra) throws InstanceNotFoundException;

    public Compra entregarEntradas(Long idCompra)
            throws CompraYaEntregadaException, SesionPasadaException,
            InstanceNotFoundException;
}
