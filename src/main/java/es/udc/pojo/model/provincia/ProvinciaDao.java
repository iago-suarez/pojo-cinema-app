package es.udc.pojo.model.provincia;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;

/**
 * The Interface ProvinciaDao.
 */
public interface ProvinciaDao extends GenericDao<Provincia, Long> {

    /**
     * Returns all Provincia.
     *
     * @return a List of Provincia
     */
    public List<Provincia> findAll();
}
