package es.udc.pojo.test.experiments;

import java.util.HashSet;

import org.hibernate.Transaction;

import es.udc.pojo.model.cine.Cine;
import es.udc.pojo.model.cine.CineDao;
import es.udc.pojo.model.cine.CineDaoHibernate;
import es.udc.pojo.model.provincia.Provincia;
import es.udc.pojo.model.provincia.ProvinciaDao;
import es.udc.pojo.model.provincia.ProvinciaDaoHibernate;
import es.udc.pojo.model.sala.Sala;
import es.udc.pojo.model.sala.SalaDao;
import es.udc.pojo.model.sala.SalaDaoHibernate;
import es.udc.pojo.model.sesion.SesionDaoHibernate;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public class SalaDaoExperiments {

    public static void main(String[] args) {

        ProvinciaDaoHibernate provinciaDaoHibernate = new ProvinciaDaoHibernate();
        provinciaDaoHibernate.setSessionFactory(HibernateUtil
                .getSessionFactory());
        ProvinciaDao provinciaDao = provinciaDaoHibernate;

        CineDaoHibernate cineDaoHibernate = new CineDaoHibernate();
        cineDaoHibernate.setSessionFactory(HibernateUtil.getSessionFactory());
        CineDao cineDao = cineDaoHibernate;

        SalaDaoHibernate salaDaoHibernate = new SalaDaoHibernate();
        salaDaoHibernate.setSessionFactory(HibernateUtil.getSessionFactory());
        SalaDao salaDao = salaDaoHibernate;

        SesionDaoHibernate sesionDaoHibernate = new SesionDaoHibernate();
        sesionDaoHibernate.setSessionFactory(HibernateUtil.getSessionFactory());

        Transaction tx = HibernateUtil.getSessionFactory().getCurrentSession()
                .beginTransaction();
        try {

            // Create Sala.
            Provincia provincia = new Provincia("A Coruña", new HashSet<Cine>());
            provinciaDao.save(provincia);

            Cine cine = new Cine("Filmax Marineda", new Float(9.80), provincia,
                    new HashSet<Sala>());
            cineDao.save(cine);

            Sala sala = new Sala(1, 120, cine);
            salaDao.save(sala);
            Long idSala = sala.getIdSala();

            System.out.println("Sala whith idSala '" + idSala
                    + "' has been created");
            System.out.println(cine);

            // Find Sala
            sala = salaDao.find(idSala);
            System.out.println("Sala whith idSala '" + idSala
                    + "' has been retrieved");
            System.out.println(sala);

            // Borramos la sala
            salaDao.remove(sala.getIdSala());

            cineDao.remove(cine.getIdCine());
            provinciaDao.remove(provincia.getIdProvincia());

            try {
                salaDao.find(idSala);
                System.err.println("ERROR: The Sala alredy exists");
            } catch (InstanceNotFoundException e) {
                System.out.println("Sala whith id " + sala.getIdSala()
                        + " has been removed correctly");
            }

            // ... proceed in the same way for other entities / methods / use
            // cases

            tx.commit();

        } catch (RuntimeException e) {
            e.printStackTrace();
            tx.rollback();
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            tx.commit();
        } finally {
            HibernateUtil.getSessionFactory().getCurrentSession().close();
        }

        HibernateUtil.shutdown();

    }

}
