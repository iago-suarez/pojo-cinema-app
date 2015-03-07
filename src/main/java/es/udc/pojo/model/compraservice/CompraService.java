package es.udc.pojo.model.compraservice;

import java.util.Calendar;

import es.udc.pojo.model.compra.Compra;
import es.udc.pojo.model.util.CompraYaEntregadaException;
import es.udc.pojo.model.util.SesionLlenaException;
import es.udc.pojo.model.util.SesionPasadaException;
import es.udc.pojo.model.util.TarjetaCaducadaException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

/**
 * The Interface CompraService.
 */
public interface CompraService {

    /**
     * Creates the compra.
     *
     * @param usrId
     *            the usr id
     * @param numEntradas
     *            the num entradas
     * @param numTarjetaCredito
     *            the num tarjeta credito
     * @param fechaExpTarjeta
     *            the fecha exp tarjeta
     * @param idSesion
     *            the id sesion
     * @return the long
     * @throws InstanceNotFoundException
     *             the instance not found exception
     * @throws SesionLlenaException
     *             the sesion llena exception
     * @throws TarjetaCaducadaException
     *             the tarjeta caducada exception
     * @throws SesionPasadaException
     *             the sesion pasada exception
     */
    public Long createCompra(Long usrId, int numEntradas,
            String numTarjetaCredito, Calendar fechaExpTarjeta, Long idSesion)
            throws InstanceNotFoundException, SesionLlenaException,
            TarjetaCaducadaException, SesionPasadaException;

    /**
     * Find compras.
     *
     * @param usrId
     *            the usr id
     * @param startIndex
     *            the start index
     * @param count
     *            the count
     * @return the compra block
     */
    public CompraBlock findCompras(Long usrId, int startIndex, int count);

    // TODO Hace falta un DTO ??
    /**
     * Find compra.
     *
     * @param idCompra
     *            the id compra
     * @return the compra
     * @throws InstanceNotFoundException
     *             the instance not found exception
     */
    public Compra findCompra(Long idCompra) throws InstanceNotFoundException;

    /**
     * Entregar entradas.
     *
     * @param idCompra
     *            the id compra
     * @return the compra
     * @throws CompraYaEntregadaException
     *             the compra ya entregada exception
     * @throws SesionPasadaException
     *             the sesion pasada exception
     * @throws InstanceNotFoundException
     *             the instance not found exception
     */
    public Compra entregarEntradas(Long idCompra)
            throws CompraYaEntregadaException, SesionPasadaException,
            InstanceNotFoundException;
}
