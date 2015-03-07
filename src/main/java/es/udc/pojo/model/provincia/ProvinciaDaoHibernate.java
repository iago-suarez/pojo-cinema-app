package es.udc.pojo.model.provincia;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("provinciaDao")
public class ProvinciaDaoHibernate extends GenericDaoHibernate<Provincia, Long>
        implements ProvinciaDao {

    @SuppressWarnings("unchecked")
    public List<Provincia> findAll() {
        return getSession().createQuery(
                "SELECT p FROM Provincia p ORDER BY p.nProvincia").list();
    }
}
