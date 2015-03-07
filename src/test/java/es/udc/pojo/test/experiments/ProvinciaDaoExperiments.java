package es.udc.pojo.test.experiments;

import java.util.HashSet;
import java.util.List;

import org.hibernate.Transaction;

import es.udc.pojo.model.cine.Cine;
import es.udc.pojo.model.provincia.Provincia;
import es.udc.pojo.model.provincia.ProvinciaDao;
import es.udc.pojo.model.provincia.ProvinciaDaoHibernate;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

/**
 * The Class ProvinciaDaoExperiments.
 */
public class ProvinciaDaoExperiments {

    /**
     * The main method.
     *
     * @param args
     *            the arguments
     */
    public static void main(String[] args) {

        ProvinciaDaoHibernate provinciaDaoHibernate = new ProvinciaDaoHibernate();
        provinciaDaoHibernate.setSessionFactory(HibernateUtil
                .getSessionFactory());
        ProvinciaDao provinciaDao = provinciaDaoHibernate;

        Transaction tx = HibernateUtil.getSessionFactory().getCurrentSession()
                .beginTransaction();
        try {

            // Create Provincia.
            Provincia provincia = new Provincia("A Coruña", new HashSet<Cine>());
            provinciaDao.save(provincia);

            Long idProvincia = provincia.getIdProvincia();
            System.out.println("Provincia whith idProvincia '" + idProvincia
                    + "' has been created");
            System.out.println(provincia);

            // Find provincia.
            provincia = provinciaDao.find(idProvincia);
            System.out.println("Provincia whith idProvincia '" + idProvincia
                    + "' has been retrieved");
            System.out.println(provincia);

            Provincia provincia2 = new Provincia("Lugo", new HashSet<Cine>());
            provinciaDao.save(provincia2);

            List<Provincia> provincias = provinciaDao.findAll();
            System.out.println("The Next Provincias has been retrieved");
            System.out.println(provincias);

            // Borramos la provinicia
            provinciaDao.remove(idProvincia);
            provinciaDao.remove(provincia2.getIdProvincia());

            try {
                provinciaDao.find(idProvincia);
                System.err.println("ERROR: The Provincia alredy exists");
            } catch (InstanceNotFoundException e) {
                System.out.println("Provincia has been removed correctly");
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
