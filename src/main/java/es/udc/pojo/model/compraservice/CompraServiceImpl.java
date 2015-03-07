package es.udc.pojo.model.compraservice;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pojo.model.compra.Compra;
import es.udc.pojo.model.compra.CompraDao;
import es.udc.pojo.model.sesion.Sesion;
import es.udc.pojo.model.sesion.SesionDao;
import es.udc.pojo.model.userprofile.UserProfileDao;
import es.udc.pojo.model.util.CompraYaEntregadaException;
import es.udc.pojo.model.util.SesionLlenaException;
import es.udc.pojo.model.util.SesionPasadaException;
import es.udc.pojo.model.util.TarjetaCaducadaException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@Service("compraService")
@Transactional
public class CompraServiceImpl implements CompraService {

    // private static final int MAXIMOENTRADAS = 10;
    @Autowired
    private CompraDao      compraDao;
    @Autowired
    private UserProfileDao userProfileDao;
    @Autowired
    private SesionDao      sesionDao;

    public Long createCompra(Long usrId, int numEntradas,
            String numTarjetaCredito, Calendar fechaExpTarjeta, Long idSesion)
            throws InstanceNotFoundException, SesionLlenaException,
            TarjetaCaducadaException, SesionPasadaException {

        Sesion sesion = sesionDao.find(idSesion);

        if (sesion.getHorayFecha().before(Calendar.getInstance()))
            throw new SesionPasadaException(sesion);

        if (fechaExpTarjeta.before(Calendar.getInstance()))
            throw new TarjetaCaducadaException("Tarjeta caducada");
        // if (numEntradas > MAXIMOENTRADAS)
        // throw new BadArgumentException(
        // "Ha excedido el número máximo de localidades a comprar");

        if (sesion.getnAsistentes() + numEntradas > sesion.getSala()
                .getCapacidad())
            throw new SesionLlenaException(sesion);

        Compra compra = new Compra(numEntradas, numTarjetaCredito,
                fechaExpTarjeta, false, Calendar.getInstance(),
                userProfileDao.find(usrId), sesion);
        compraDao.save(compra);

        // Descontamos las entradas vendidas a la sesino
        sesion.aumentarAsistentes(numEntradas);
        sesionDao.save(sesion);

        return compra.getIdCompra();
    }

    @Transactional(readOnly = true)
    public CompraBlock findCompras(Long usrId, int startIndex, int count) {

        /*
         * Find count+1 compras to determine if there exist more compras above
         * the specified range.
         */
        List<Compra> compras = compraDao.findComprasUsuario(usrId, startIndex,
                count + 1);

        boolean existMoreCompras = compras.size() == (count + 1);

        /*
         * Remove the last compra from the returned list if there exist more
         * compras above the specified range.
         */
        if (existMoreCompras) {
            compras.remove(compras.size() - 1);
        }
        /* Return CompraBlock */
        return new CompraBlock(compras, existMoreCompras);
    }

    @Transactional(readOnly = true)
    public Compra findCompra(Long idCompra) throws InstanceNotFoundException {

        return compraDao.find(idCompra);
    }

    public Compra entregarEntradas(Long idCompra)
            throws CompraYaEntregadaException, InstanceNotFoundException,
            SesionPasadaException {

        Compra compra = compraDao.find(idCompra);
        if (compra.isEntregada())
            throw new CompraYaEntregadaException(compra);
        if (compra.getSesion().getHorayFecha().before(Calendar.getInstance()))
            throw new SesionPasadaException(compra.getSesion());
        compra.setEntregada(true);
        compraDao.save(compra);
        return compra;

    }

}
