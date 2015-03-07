package es.udc.pojo.test.experiments;

import java.util.Calendar;

import org.hibernate.Transaction;

import es.udc.pojo.model.pelicula.Pelicula;
import es.udc.pojo.model.pelicula.PeliculaDao;
import es.udc.pojo.model.pelicula.PeliculaDaoHibernate;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

/**
 * The Class PeliculaDaoExperiments.
 */
public class PeliculaDaoExperiments {

    /**
     * The main method.
     *
     * @param args
     *            the arguments
     */
    public static void main(String[] args) {

        PeliculaDaoHibernate peliculaDaoHibernate = new PeliculaDaoHibernate();
        peliculaDaoHibernate.setSessionFactory(HibernateUtil
                .getSessionFactory());
        PeliculaDao peliculaDao = peliculaDaoHibernate;

        Transaction tx = HibernateUtil.getSessionFactory().getCurrentSession()
                .beginTransaction();
        try {

            Pelicula pelicula = new Pelicula("Revolucion en la granja",
                    Calendar.getInstance(), Calendar.getInstance(), 50,
                    "La pelicula que emociono a  la critica e hizo llorar a Putin");
            peliculaDao.save(pelicula);

            Long peliculaId = pelicula.getIdPelicula();
            System.out.println("Pelicula with peliculaId '" + peliculaId
                    + "' has been created");
            System.out.println(pelicula);

            // Find pelicula.
            pelicula = peliculaDao.find(peliculaId);
            System.out.println("Pelicula with peliculaId '" + peliculaId
                    + "' has been retrieved");
            System.out.println();

            try {
                peliculaDao.find(pelicula.getIdPelicula());
                System.err.println("ERROR: The Pelicula alredy exists");
            } catch (InstanceNotFoundException e) {
                System.out.println("Pelicula has been removed correctly");
            }

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
