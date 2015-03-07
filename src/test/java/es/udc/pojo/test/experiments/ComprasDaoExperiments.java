package es.udc.pojo.test.experiments;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.HashSet;

import org.hibernate.Transaction;

import es.udc.pojo.model.cine.Cine;
import es.udc.pojo.model.cine.CineDao;
import es.udc.pojo.model.cine.CineDaoHibernate;
import es.udc.pojo.model.compra.Compra;
import es.udc.pojo.model.compra.CompraDao;
import es.udc.pojo.model.compra.CompraDaoHibernate;
import es.udc.pojo.model.pelicula.Pelicula;
import es.udc.pojo.model.pelicula.PeliculaDao;
import es.udc.pojo.model.pelicula.PeliculaDaoHibernate;
import es.udc.pojo.model.provincia.Provincia;
import es.udc.pojo.model.provincia.ProvinciaDao;
import es.udc.pojo.model.provincia.ProvinciaDaoHibernate;
import es.udc.pojo.model.sala.Sala;
import es.udc.pojo.model.sala.SalaDao;
import es.udc.pojo.model.sala.SalaDaoHibernate;
import es.udc.pojo.model.sesion.Sesion;
import es.udc.pojo.model.sesion.SesionDao;
import es.udc.pojo.model.sesion.SesionDaoHibernate;
import es.udc.pojo.model.userprofile.TipoUsuario;
import es.udc.pojo.model.userprofile.UserProfile;
import es.udc.pojo.model.userprofile.UserProfileDao;
import es.udc.pojo.model.userprofile.UserProfileDaoHibernate;
import es.udc.pojo.model.userservice.util.PasswordEncrypter;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public class ComprasDaoExperiments {

    public static void main(String[] args) {

        CompraDaoHibernate compraDaoHibernate = new CompraDaoHibernate();
        compraDaoHibernate.setSessionFactory(HibernateUtil.getSessionFactory());
        CompraDao compraDao = compraDaoHibernate;

        UserProfileDaoHibernate userProfileDaoHibernate = new UserProfileDaoHibernate();
        userProfileDaoHibernate.setSessionFactory(HibernateUtil
                .getSessionFactory());
        UserProfileDao userProfileDao = userProfileDaoHibernate;

        PeliculaDaoHibernate peliculaDaoHibernate = new PeliculaDaoHibernate();
        peliculaDaoHibernate.setSessionFactory(HibernateUtil
                .getSessionFactory());
        PeliculaDao peliculaDao = peliculaDaoHibernate;

        SesionDaoHibernate sesionDaoHibernate = new SesionDaoHibernate();
        sesionDaoHibernate.setSessionFactory(HibernateUtil.getSessionFactory());
        SesionDao sesionDao = sesionDaoHibernate;

        SalaDaoHibernate salaDaoHibernate = new SalaDaoHibernate();
        salaDaoHibernate.setSessionFactory(HibernateUtil.getSessionFactory());
        SalaDao salaDao = salaDaoHibernate;

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

            // Register user.
            UserProfile userProfile = new UserProfile("daoUser",
                    PasswordEncrypter.crypt("userPassword"), "name",
                    "lastName", "user@udc.es", TipoUsuario.ESPECTADOR);
            userProfileDao.save(userProfile);

            Provincia provincia = new Provincia("A Coruña", new HashSet<Cine>());
            provinciaDao.save(provincia);

            Cine cine = new Cine("Filmax Marineda", new Float(9.80), provincia,
                    new HashSet<Sala>());
            cineDao.save(cine);

            Pelicula pelicula = new Pelicula("Revolucion en la granja",
                    Calendar.getInstance(), Calendar.getInstance(), 5,
                    "La pelicula que emociono a  la critica e hizo llorar a Putin");
            peliculaDao.save(pelicula);

            Sala sala = new Sala(20, 30, cine);
            salaDao.save(sala);
            Sesion sesion = new Sesion(new Float(13), Calendar.getInstance(),
                    pelicula, sala, 0);
            sesionDao.save(sesion);
            // TODO añadir una sesion valida para cerciorarnos de que el error
            // corresponde a ello.
            Compra compra = new Compra(5, "1234567889123456",
                    Calendar.getInstance(), false, Calendar.getInstance(),
                    userProfile, sesion);
            compraDao.save(compra);

            compraDao.find(compra.getIdCompra());
            System.out.println("Se ha realiZADO la compra correctamente \n");

            assertEquals(compra, compraDao.find(compra.getIdCompra()));
            // ... proceed in the same way for other entities / methods / use
            // cases

            tx.commit();

            System.out.println("ADEUS");
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
