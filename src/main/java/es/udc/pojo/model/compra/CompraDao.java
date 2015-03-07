package es.udc.pojo.model.compra;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;

public interface CompraDao extends GenericDao<Compra, Long> {

    /**
     * Returns a list of compras of the user between two dates.
     *
     * @param usrId
     *            the account identifier
     * @param startIndex
     *            the index (starting from 0) of the first object to return
     * @param count
     *            the maximum number of compras to return
     * @return the list of compras
     */
    public List<Compra> findComprasUsuario(Long usrId, int startIndex, int count);

}
