package es.udc.pojo.model.cine;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

/**
 * The Class CineDaoHibernate.
 */
@Repository("cineDao")
public class CineDaoHibernate extends GenericDaoHibernate<Cine, Long> implements
        CineDao {

}