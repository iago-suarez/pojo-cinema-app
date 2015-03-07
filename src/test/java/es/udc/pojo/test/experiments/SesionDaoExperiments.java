package es.udc.pojo.test.experiments;

import java.util.Calendar;
import java.util.HashSet;

import org.hibernate.Transaction;

import es.udc.pojo.model.cine.Cine;
import es.udc.pojo.model.cine.CineDao;
import es.udc.pojo.model.cine.CineDaoHibernate;
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
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public class SesionDaoExperiments {

    public static void main(String[] args) {

        ProvinciaDaoHibernate provinciaDaoHibernate = new ProvinciaDaoHibernate();
        provinciaDaoHibernate.setSessionFactory(HibernateUtil
                .getSessionFactory());
        ProvinciaDao provinciaDao = provinciaDaoHibernate;

        CineDaoHibernate cineDaoHibernate = new CineDaoHibernate();
        cineDaoHibernate.setSessionFactory(HibernateUtil.getSessionFactory());
        CineDao cineDao = cineDaoHibernate;

        PeliculaDaoHibernate peliculaDaoHibernate = new PeliculaDaoHibernate();
        peliculaDaoHibernate.setSessionFactory(HibernateUtil
                .getSessionFactory());
        PeliculaDao peliculaDao = peliculaDaoHibernate;

        SalaDaoHibernate salaDaoHibernate = new SalaDaoHibernate();
        salaDaoHibernate.setSessionFactory(HibernateUtil.getSessionFactory());
        SalaDao salaDao = salaDaoHibernate;

        SesionDaoHibernate sesionDaoHibernate = new SesionDaoHibernate();
        sesionDaoHibernate.setSessionFactory(HibernateUtil.getSessionFactory());
        SesionDao sesionDao = sesionDaoHibernate;

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

            // Creamos las peliculas necesarias para el test
            Pelicula pelicula = new Pelicula("Los Juego de hambre",
                    Calendar.getInstance(), Calendar.getInstance(), 120,
                    "Una pelicula interesante ");
            peliculaDao.save(pelicula);

            Pelicula pelicula2 = new Pelicula("300 II", Calendar.getInstance(),
                    Calendar.getInstance(), 120, "Una pelicula interesante ");
            peliculaDao.save(pelicula2);

            // Creamos una sesion con la fecha actual
            Sesion sesion = new Sesion(new Float(4.30), Calendar.getInstance(),
                    pelicula, sala, 0);
            sesionDao.save(sesion);
            Long idSesion = sesion.getIdSesion();

            System.out.println("Sesion whith idSesion '" + idSesion
                    + "' has been created");
            System.out.println(sesion);

            // Find Sesion
            sesion = sesionDao.find(idSesion);
            System.out.println("Sesion whith idSesion '" + idSesion
                    + "' has been retrieved");
            System.out.println(sesion);

            // Find Sesion by Sala

            Sala sala2 = new Sala(2, 120, cine);
            salaDao.save(sala2);

            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, 10);

            Sesion sesion2 = new Sesion(new Float(4.30), c, pelicula2, sala2, 0);
            sesionDao.save(sesion2);

            Sesion sesion3 = new Sesion(new Float(4.30),
                    Calendar.getInstance(), pelicula, sala, 0);
            sesionDao.save(sesion3);

            // // Probamos los métodos peliculaDao.findPeliculasByCine(...)
            // // Y sesionDao.findByCinePeliculaFecha(...)
            //
            // System.out
            // .println("********************* JOIN TEST *********************");
            //
            // // Anteriormente hemos creado una sesion con la fecha actual
            // List<Pelicula> peliculas = peliculaDao.findPeliculasByCine(
            // cine.getIdCine(), Calendar.getInstance());
            // System.out.println(peliculas);
            // if (peliculas.contains(pelicula) &&
            // peliculas.contains(pelicula2))
            // System.out
            // .println("Se ha encontrado la película en la colección");
            // else
            // System.out
            // .println("ERROR: No se han devuelto las peliculas buscadas");
            //
            // // Anteriormente hemos creado una sesion con la fecha actual
            // // List<Sesion> sesiones2 = sesionDao.findByCinePeliculaFecha(
            // // cine.getIdCine(), pelicula.getIdPelicula(),
            // // Calendar.getInstance());
            //
            // // System.out.println(sesiones2);
            // // if (sesiones2.contains(sesion) && !sesiones2.contains(sesion2)
            // // && sesiones2.contains(sesion3))
            // //
            // System.out.println("Se han obtenido las sesiones correctamete");
            // // else
            // // System.out
            // //
            // .println("ERROR: No se han obtenido las sesiones correctamente");
            // //
            // // // Borramos la Sesion
            // // sesionDao.remove(sesion.getIdSesion());
            // // sesionDao.remove(sesion2.getIdSesion());
            // // sesionDao.remove(sesion3.getIdSesion());
            //
            // peliculaDao.remove(pelicula.getIdPelicula());
            // peliculaDao.remove(pelicula2.getIdPelicula());
            //
            // salaDao.remove(sala.getIdSala());
            // salaDao.remove(sala2.getIdSala());
            // cineDao.remove(cine.getIdCine());
            // provinciaDao.remove(provincia.getIdProvincia());
            //
            // try {
            // salaDao.find(idSesion);
            // System.err.println("ERROR: The Sala alredy exists");
            // } catch (InstanceNotFoundException e) {
            // System.out.println("Sala whith id " + sala.getIdSala()
            // + " has been removed correctly");
            // }

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