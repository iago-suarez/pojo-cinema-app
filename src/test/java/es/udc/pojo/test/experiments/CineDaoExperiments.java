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
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public class CineDaoExperiments {

    public static void main(String[] args) {

        ProvinciaDaoHibernate provinciaDaoHibernate = new ProvinciaDaoHibernate();
        provinciaDaoHibernate.setSessionFactory(HibernateUtil
                .getSessionFactory());
        ProvinciaDao provinciaDao = provinciaDaoHibernate;

        CineDaoHibernate cineDaoHibernate = new CineDaoHibernate();
        cineDaoHibernate.setSessionFactory(HibernateUtil.getSessionFactory());
        CineDao cineDao = cineDaoHibernate;

        Transaction tx = HibernateUtil.getSessionFactory().getCurrentSession()
                .beginTransaction();
        try {

            // Create Cine.
            Provincia provincia = new Provincia("A Coruña", new HashSet<Cine>());
            provinciaDao.save(provincia);

            Cine cine = new Cine("Filmax Marineda", new Float(9.80), provincia,
                    new HashSet<Sala>());
            cineDao.save(cine);
            Long idCine = cine.getIdCine();

            System.out.println("Cine whith idCine '" + idCine
                    + "' has been created");
            System.out.println(cine);

            // Find Cine.
            cine = cineDao.find(idCine);
            System.out.println("Cine whith idCine '" + idCine
                    + "' has been retrieved");
            System.out.println(cine);

            // Find By Provincia.
            Provincia provincia2 = new Provincia("Lugo", new HashSet<Cine>());
            provinciaDao.save(provincia2);
            Cine cine2 = new Cine("Yelmo Rosales", new Float(5.80), provincia2,
                    new HashSet<Sala>());
            cineDao.save(cine2);
            Cine cine3 = new Cine("Espacio Coruña Marineda", new Float(4.80),
                    provincia, new HashSet<Sala>());
            cineDao.save(cine3);

            // Borramos el cine
            cineDao.remove(idCine);
            cineDao.remove(cine2.getIdCine());
            cineDao.remove(cine3.getIdCine());

            provinciaDao.remove(provincia.getIdProvincia());
            provinciaDao.remove(provincia2.getIdProvincia());

            try {
                cineDao.find(idCine);
                System.err.println("ERROR: The Cine alredy exists");
            } catch (InstanceNotFoundException e) {
                System.out.println("Cine whith id " + cine.getIdCine()
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
