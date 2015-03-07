package es.udc.pojo.model.compra;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

/**
 * The Class CompraDaoHibernate.
 */
@Repository("compraDao")
public class CompraDaoHibernate extends GenericDaoHibernate<Compra, Long>
        implements CompraDao {

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.udc.pojo.model.compra.CompraDao#findComprasUsuario(java.lang.Long,
     * int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Compra> findComprasUsuario(Long usrId, int startIndex, int count) {

        return getSession()
                .createQuery(
                        "SELECT o FROM Compra o WHERE "
                                + "o.userprofile.userProfileId = :usrId "
                                + "ORDER BY o.fechaCompra DESC,o.idCompra DESC")
                .setParameter("usrId", usrId).setFirstResult(startIndex)
                .setMaxResults(count).list();

    }
}
