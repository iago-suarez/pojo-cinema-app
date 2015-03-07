package es.udc.pojo.model.sala;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

/**
 * The Class SalaDaoHibernate.
 */
@Repository("salaDao")
public class SalaDaoHibernate extends GenericDaoHibernate<Sala, Long> implements
        SalaDao {

}
