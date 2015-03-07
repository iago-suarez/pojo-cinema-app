package es.udc.pojo.test.model.catalogoservice;

import static es.udc.pojo.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.pojo.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pojo.model.catalogoservice.CatalogoService;
import es.udc.pojo.model.catalogoservice.PeliculaSesionDto;
import es.udc.pojo.model.cine.Cine;
import es.udc.pojo.model.cine.CineDao;
import es.udc.pojo.model.pelicula.Pelicula;
import es.udc.pojo.model.pelicula.PeliculaDao;
import es.udc.pojo.model.provincia.Provincia;
import es.udc.pojo.model.provincia.ProvinciaDao;
import es.udc.pojo.model.sala.Sala;
import es.udc.pojo.model.sala.SalaDao;
import es.udc.pojo.model.sesion.Sesion;
import es.udc.pojo.model.sesion.SesionDao;
import es.udc.pojo.model.userservice.UserService;
import es.udc.pojo.model.util.BadArgumentException;
import es.udc.pojo.model.util.CoincidentSesionsException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class CatalogoServiceTest {

    private final long      NON_EXISTENT_CINE_ID = -1;
    @Autowired
    private UserService     userService;
    @Autowired
    private CatalogoService catalogoService;
    @Autowired
    private ProvinciaDao    provinciaDao;
    @Autowired
    private CineDao         cineDao;
    @Autowired
    private SalaDao         salaDao;
    @Autowired
    private PeliculaDao     peliculaDao;
    @Autowired
    private SesionDao       sesionDao;

    @Test
    public void testFindProvincias() {

        List<Provincia> provinciasprueba = new ArrayList<Provincia>();

        List<Provincia> provincias = catalogoService.findProvincias();
        assertTrue(provincias.isEmpty());

        // Create Provincia.
        Provincia pcoruna = new Provincia("A Coruña", new HashSet<Cine>());
        Provincia plugo = new Provincia("Lugo", new HashSet<Cine>());
        Provincia pourense = new Provincia("Ourense", new HashSet<Cine>());
        Provincia pponte = new Provincia("Pontevedra", new HashSet<Cine>());
        provinciaDao.save(pcoruna);
        provinciaDao.save(plugo);
        provinciaDao.save(pourense);
        provinciaDao.save(pponte);

        provinciasprueba.add(pcoruna);
        provinciasprueba.add(plugo);
        provinciasprueba.add(pourense);
        provinciasprueba.add(pponte);
        provincias = catalogoService.findProvincias();
        assertTrue(provincias.size() == 4);
        assertEquals(provincias, provinciasprueba);

    }

    @Test(expected = InstanceNotFoundException.class)
    public void testFindNonExistentCine() throws InstanceNotFoundException {

        catalogoService.findCine(NON_EXISTENT_CINE_ID);

    }

    @Test
    public void testFindCine() throws InstanceNotFoundException {
        Provincia provincia = new Provincia("A Coruña", new HashSet<Cine>());

        Cine cine1 = new Cine("Filmax Marineda", new Float(9.80), provincia,
                new HashSet<Sala>());
        provinciaDao.save(provincia);
        cineDao.save(cine1);
        Cine cine = catalogoService.findCine(cine1.getIdCine());
        assertEquals(cine, cine1);
    }

    @Test
    public void testFindPeliculas() {
        Provincia provincia = new Provincia("A Coruña", new HashSet<Cine>());
        provinciaDao.save(provincia);

        Cine cine = new Cine("Filmax Marineda", new Float(9.80), provincia,
                new HashSet<Sala>());
        Cine cine2 = new Cine("Yelmo Espacio", new Float(9.80), provincia,
                new HashSet<Sala>());
        cineDao.save(cine);
        cineDao.save(cine2);

        Sala sala = new Sala(1, 120, cine);
        Sala sala2 = new Sala(1, 100, cine2);
        salaDao.save(sala);
        salaDao.save(sala2);

        // Creamos las peliculas necesarias para el test
        Pelicula pelicula = new Pelicula("Los Juego de hambre",
                Calendar.getInstance(), Calendar.getInstance(), 120,
                "Una pelicula interesante ");
        peliculaDao.save(pelicula);

        Pelicula pelicula2 = new Pelicula("The Artist", Calendar.getInstance(),
                Calendar.getInstance(), 120, "Una pelicula muda");
        peliculaDao.save(pelicula2);

        // Creamos sesiones con la fecha actual
        Calendar horaSesiones = Calendar.getInstance();
        horaSesiones.add(Calendar.DAY_OF_MONTH, 1);
        horaSesiones.set(Calendar.HOUR_OF_DAY, 17);

        // Creamos el calendar para la busqueda, que debe ser anterior a la
        // fecha de las sesiones
        Calendar busq = (Calendar) horaSesiones.clone();

        Sesion sesion = new Sesion(new Float(1.30),
                (Calendar) horaSesiones.clone(), pelicula, sala, 0);
        Sesion sesion2 = new Sesion(new Float(2.30),
                (Calendar) horaSesiones.clone(), pelicula, sala2, 0);
        Sesion sesion3 = new Sesion(new Float(3.30),
                (Calendar) horaSesiones.clone(), pelicula2, sala2, 0);

        // Crearemos esta sesion a la 1:30 para ver que las sesiones nocturnas
        // Validan correctamente
        Calendar sesion4Cal = Calendar.getInstance();
        sesion4Cal.add(Calendar.DAY_OF_MONTH, 2);
        sesion4Cal.set(Calendar.HOUR_OF_DAY, 1);
        sesion4Cal.set(Calendar.MINUTE, 30);

        Sesion sesion4 = new Sesion(new Float(4.30), sesion4Cal, pelicula2,
                sala2, 0);

        /*
         * Creamos dos sesiones con fechas posteriores, una justo un dia despues
         * de la otra
         */

        Calendar after = Calendar.getInstance();
        after.add(Calendar.YEAR, 1);
        after.set(Calendar.DAY_OF_MONTH, 2);
        after.set(Calendar.HOUR_OF_DAY, 17);
        Calendar after2 = Calendar.getInstance();
        after2.add(Calendar.YEAR, 1);
        after2.set(Calendar.DAY_OF_MONTH, 3);
        after2.set(Calendar.HOUR_OF_DAY, 17);
        Sesion sesionAfter = new Sesion(new Float(10.30), after, pelicula2,
                sala2, 0);
        Sesion sesionAfter2 = new Sesion(new Float(10.30), after2, pelicula2,
                sala2, 0);

        /* Almacenamos las sesiones en la BD */
        sesionDao.save(sesion);
        sesionDao.save(sesion2);
        sesionDao.save(sesion3);
        sesionDao.save(sesion4);
        sesionDao.save(sesionAfter);
        sesionDao.save(sesionAfter2);

        /* En 'cine' se proyecta 'pelicula' en la sesion 'sesion' */

        List<PeliculaSesionDto> pldto = catalogoService.findPeliculas(
                cine.getIdCine(), busq);

        List<Sesion> sesiones = new ArrayList<Sesion>();
        sesiones.add(sesion);
        assertTrue(pldto.size() == 1);
        assertEquals(pldto.get(0).getPelicula(), pelicula);
        assertEquals(pldto.get(0).getSesiones().get(0), sesion);

        /*
         * En 'cine2' se proyecta 'pelicula' en la sesion 'sesion2' y
         * 'pelicula2' en la sesion 'sesion3' y 'sesion4 '
         */

        List<PeliculaSesionDto> pldto2 = catalogoService.findPeliculas(
                cine2.getIdCine(), busq);
        assertTrue(pldto2.size() == 2);
        assertTrue(pldto2.get(0).getSesiones().size() == 1);
        assertTrue(pldto2.get(1).getSesiones().size() == 2);

        /*
         * Al ordenarlas por titulo debe aparecer primero 'pelicula' y despues
         * 'pelicula2'
         */
        assertEquals(pldto2.get(0).getPelicula(), pelicula);
        assertEquals(pldto2.get(1).getPelicula(), pelicula2);

        /*
         * Ahora comprobamos que 'pelicula' tiene una unica sesion: 'sesion2' y
         * 'pelicula2' tiene 2 sesiones: 'sesion3' y 'sesion4'
         */
        assertEquals(pldto2.get(0).getSesiones().get(0), sesion2);
        assertTrue(pldto2.get(1).getSesiones().size() == 2);
        assertTrue(pldto2.get(1).getSesiones().contains(sesion4));
        assertTrue(pldto2.get(1).getSesiones().contains(sesion3));
        /*
         * comprobamos que en fecha 'after' se proyecta 'pelicula2' en la sesion
         * 'sesionAfter'
         */
        pldto = catalogoService.findPeliculas(cine2.getIdCine(), after);
        assertEquals(pldto.get(0).getPelicula(), pelicula2);
        assertEquals(pldto.get(0).getSesiones().get(0), sesionAfter);

        /*
         * y que justo un dia despues se proyecta 'pelicula2' en la sesion
         * sesionAfter2
         */

        pldto = catalogoService.findPeliculas(cine2.getIdCine(), after2);
        assertEquals(pldto.get(0).getPelicula(), pelicula2);
        assertEquals(pldto.get(0).getSesiones().get(0), sesionAfter2);

        /* Comprobamos que un dia no se proyecta nada */
        Calendar after3 = Calendar.getInstance();
        after3.add(Calendar.YEAR, 4);
        after3.set(Calendar.MONTH, 11);
        after3.set(Calendar.DAY_OF_MONTH, 1);

        List<PeliculaSesionDto> pldto3 = catalogoService.findPeliculas(
                cine2.getIdCine(), after3);
        assertTrue(pldto3.isEmpty());

        /*
         * Comprobamos peliculas que pertenecen al mismo dia logico pero no
         * fisico
         */
        Calendar mismodia = Calendar.getInstance();
        mismodia.add(Calendar.YEAR, 1);
        mismodia.set(Calendar.MONTH, 11);
        mismodia.set(Calendar.DAY_OF_MONTH, 2);
        mismodia.set(Calendar.HOUR_OF_DAY, 5);
        mismodia.set(Calendar.MINUTE, 0);

        Calendar mismodia2 = Calendar.getInstance();
        mismodia2.add(Calendar.YEAR, 1);
        mismodia2.set(Calendar.MONTH, 11);
        mismodia2.set(Calendar.DAY_OF_MONTH, 3);
        mismodia2.set(Calendar.HOUR_OF_DAY, 4);
        mismodia2.set(Calendar.MINUTE, 59);

        Sesion sesionlogica = new Sesion(new Float(8.30), mismodia, pelicula2,
                sala2, 0);
        Sesion sesionlogica2 = new Sesion(new Float(8.30), mismodia2,
                pelicula2, sala2, 0);

        sesionDao.save(sesionlogica);
        sesionDao.save(sesionlogica2);

        List<PeliculaSesionDto> dialogico = catalogoService.findPeliculas(
                cine2.getIdCine(), mismodia);

        // la lista debe contener dos sesiones de la pelicula 'pelicula2' :
        // sesionlogica y sesionlogica2

        assertTrue(dialogico.size() == 1);
        assertEquals(dialogico.get(0).getPelicula(), pelicula2);
        assertTrue(dialogico.get(0).getSesiones().size() == 2);
        assertTrue(dialogico.get(0).getSesiones().contains(sesionlogica));
        assertTrue(dialogico.get(0).getSesiones().contains(sesionlogica2));

    }

    @Test
    public void findPeliculasActivasADia() {

        Calendar enero = Calendar.getInstance();
        Calendar febrero = Calendar.getInstance();
        Calendar marzo = Calendar.getInstance();
        Calendar abril = Calendar.getInstance();
        Calendar mayo = Calendar.getInstance();

        enero.set(Calendar.MONTH, 0);
        febrero.set(Calendar.MONTH, 1);
        marzo.set(Calendar.MONTH, 2);
        abril.set(Calendar.MONTH, 3);
        mayo.set(Calendar.MONTH, 4);

        // Creamos las peliculas necesarias para el test
        Pelicula pelicula = new Pelicula("Los Juego de hambre", enero, abril,
                120, "Una pelicula interesante ");
        peliculaDao.save(pelicula);

        Pelicula pelicula2 = new Pelicula("The Artist", enero, febrero, 120,
                "Una pelicula muda");
        peliculaDao.save(pelicula2);

        Pelicula pelicula3 = new Pelicula("La Gran Estafa ", febrero, abril,
                120, "Una pelicula sorda");
        peliculaDao.save(pelicula3);

        // No se recuperara ninguna pelicula
        List<Pelicula> peliculas = catalogoService
                .findPeliculasActivasADia(mayo);
        assertTrue(peliculas.size() == 0);

        // Se recuperarán la pelicula 1 y la 3
        peliculas = catalogoService.findPeliculasActivasADia(marzo);
        assertTrue(peliculas.size() == 2);
        assertTrue(peliculas.contains(pelicula));
        assertTrue(peliculas.contains(pelicula3));

        // Las peliculas con fecha de inicio o fin exactamente iguales
        // a la pasada por parámetro también serán rescatadas
        peliculas = catalogoService.findPeliculasActivasADia(enero);
        assertTrue(peliculas.size() == 2);
        assertTrue(peliculas.contains(pelicula));
        assertTrue(peliculas.contains(pelicula2));

    }

    @Test
    public void testCreateSesions() throws BadArgumentException,
            InstanceNotFoundException, CoincidentSesionsException {
        Provincia provincia = new Provincia("A Coruña", new HashSet<Cine>());
        provinciaDao.save(provincia);

        Cine cine2 = new Cine("Yelmo Espacio", new Float(9.80), provincia,
                new HashSet<Sala>());
        cineDao.save(cine2);

        Sala sala2 = new Sala(1, 100, cine2);
        salaDao.save(sala2);

        // Creamos las peliculas necesarias para el test
        Pelicula pelicula = new Pelicula("Los Juego de hambre",
                Calendar.getInstance(), Calendar.getInstance(), 120,
                "Una pelicula interesante ");
        peliculaDao.save(pelicula);

        Pelicula pelicula2 = new Pelicula("The Artist", Calendar.getInstance(),
                Calendar.getInstance(), 120, "Una pelicula muda");
        peliculaDao.save(pelicula2);

        // Creamos sesiones con la fecha actual
        Calendar h1 = Calendar.getInstance();
        h1.add(Calendar.YEAR, 1);
        h1.set(Calendar.HOUR_OF_DAY, 16);
        Sesion sesion = new Sesion(new Float(4.30), h1, pelicula, sala2, 0);

        Calendar h2 = Calendar.getInstance();
        h2.add(Calendar.YEAR, 1);
        h2.set(Calendar.HOUR_OF_DAY, 22);
        Sesion sesion2 = new Sesion(new Float(4.30), h2, pelicula, sala2, 0);

        // Esta es una sesion de madrugada
        Calendar h3 = Calendar.getInstance();
        h3.add(Calendar.YEAR, 1);
        h3.add(Calendar.DAY_OF_MONTH, 1);
        h3.set(Calendar.HOUR_OF_DAY, 1);
        Sesion sesion3 = new Sesion(new Float(4.30), h3, pelicula2, sala2, 0);

        List<Sesion> sadd = new ArrayList<Sesion>();
        sadd.add(sesion);
        sadd.add(sesion2);
        sadd.add(sesion3);

        catalogoService.createSesions(sadd, h1, cine2.getIdCine());

        /* Comprobamos que se han añadido correctamente */
        assertEquals(sesion, sesionDao.find(sesion.getIdSesion()));
        assertEquals(sesion2, sesionDao.find(sesion2.getIdSesion()));
        assertEquals(sesion3, sesionDao.find(sesion3.getIdSesion()));

    }

    @Test(expected = BadArgumentException.class)
    public void TestCreateSesionsDiferenteDia() throws BadArgumentException,
            CoincidentSesionsException {
        Provincia provincia = new Provincia("A CoruÃ±a", new HashSet<Cine>());
        provinciaDao.save(provincia);

        Cine cine2 = new Cine("Yelmo Espacio", new Float(9.80), provincia,
                new HashSet<Sala>());
        cineDao.save(cine2);

        Sala sala2 = new Sala(1, 100, cine2);
        salaDao.save(sala2);

        // Creamos las peliculas necesarias para el test
        Pelicula pelicula = new Pelicula("Los Juego de hambre",
                Calendar.getInstance(), Calendar.getInstance(), 120,
                "Una pelicula interesante ");
        peliculaDao.save(pelicula);

        Pelicula pelicula2 = new Pelicula("The Artist", Calendar.getInstance(),
                Calendar.getInstance(), 120, "Una pelicula muda");
        peliculaDao.save(pelicula2);

        // Creamos sesiones con fecha de dentro de un aÃ±o
        Calendar inOneYear = Calendar.getInstance();
        inOneYear.add(Calendar.YEAR, 1);

        Sesion sesion = new Sesion(new Float(4.30), inOneYear, pelicula, sala2,
                0);
        Sesion sesion2 = new Sesion(new Float(4.30), inOneYear, pelicula,
                sala2, 0);
        Sesion sesion3 = new Sesion(new Float(4.30), inOneYear, pelicula2,
                sala2, 0);

        /* aÃ±adimos una sesion en una fecha distinta al resto (+2 aÃ±os) */
        Calendar inTwoYears = Calendar.getInstance();
        inTwoYears.add(Calendar.YEAR, 2);

        Sesion sesion4 = new Sesion(new Float(4.30), inTwoYears, pelicula2,
                sala2, 0);

        List<Sesion> sadd = new ArrayList<Sesion>();
        sadd.add(sesion);
        sadd.add(sesion2);
        sadd.add(sesion3);
        sadd.add(sesion4);

        catalogoService.createSesions(sadd, Calendar.getInstance(),
                cine2.getIdCine()); // exception

    }

    @Test(expected = BadArgumentException.class)
    public void TestCreateSesionsDiferenteCine() throws BadArgumentException,
            CoincidentSesionsException {
        Provincia provincia = new Provincia("A Coruña", new HashSet<Cine>());
        provinciaDao.save(provincia);

        Cine cine = new Cine("Yelmo Marineda", new Float(9.80), provincia,
                new HashSet<Sala>());
        cineDao.save(cine);

        Cine cine2 = new Cine("Yelmo Espacio", new Float(9.80), provincia,
                new HashSet<Sala>());
        cineDao.save(cine2);

        Sala sala = new Sala(1, 100, cine);
        salaDao.save(sala);

        Sala sala2 = new Sala(1, 100, cine2);
        salaDao.save(sala2);

        Sala sala3 = new Sala(1, 100, cine2);
        salaDao.save(sala3);

        Pelicula pelicula = new Pelicula("Los Juego de hambre",
                Calendar.getInstance(), Calendar.getInstance(), 120,
                "Una pelicula interesante ");
        peliculaDao.save(pelicula);

        Pelicula pelicula2 = new Pelicula("The Artist", Calendar.getInstance(),
                Calendar.getInstance(), 120, "Una pelicula muda");
        peliculaDao.save(pelicula2);

        Calendar inOneYear = Calendar.getInstance();
        inOneYear.add(Calendar.YEAR, 1);

        // Sesion en el cine erroneo
        Sesion sesion = new Sesion(new Float(4.30), inOneYear, pelicula, sala,
                0);

        // Sesiones en el cine correcto
        Sesion sesion2 = new Sesion(new Float(4.30), inOneYear, pelicula,
                sala2, 0);
        Sesion sesion3 = new Sesion(new Float(4.30), inOneYear, pelicula2,
                sala3, 0);

        List<Sesion> sadd = new ArrayList<Sesion>();
        sadd.add(sesion);
        sadd.add(sesion2);
        sadd.add(sesion3);

        try {
            catalogoService.createSesions(sadd, inOneYear, cine2.getIdCine()); // exception
        } catch (BadArgumentException e) {
            assertEquals(e.getMessage(), "Las sesiones no son del mismo cine.");
            throw e;
        }
    }

    @Test(expected = BadArgumentException.class)
    public void TestCreateOnExistentSesions() throws BadArgumentException,
            CoincidentSesionsException {
        Provincia provincia = new Provincia("A Coruña", new HashSet<Cine>());
        provinciaDao.save(provincia);

        Cine cine2 = new Cine("Yelmo Espacio", new Float(9.80), provincia,
                new HashSet<Sala>());
        cineDao.save(cine2);

        Sala sala2 = new Sala(1, 100, cine2);
        salaDao.save(sala2);

        // Creamos las peliculas necesarias para el test
        Pelicula pelicula = new Pelicula("Los Juego de hambre",
                Calendar.getInstance(), Calendar.getInstance(), 120,
                "Una pelicula interesante ");
        peliculaDao.save(pelicula);

        Pelicula pelicula2 = new Pelicula("The Artist", Calendar.getInstance(),
                Calendar.getInstance(), 120, "Una pelicula muda");
        peliculaDao.save(pelicula2);

        // Creamos sesiones con la fecha actual

        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1);

        Sesion sesion = new Sesion(new Float(4.30), tomorrow, pelicula, sala2,
                0);
        Sesion sesion2 = new Sesion(new Float(4.30), tomorrow, pelicula2,
                sala2, 0);

        List<Sesion> sadd = new ArrayList<Sesion>();
        sadd.add(sesion);

        /* Añadimos una lista de sesiones en la fecha actual */
        catalogoService.createSesions(sadd, tomorrow, cine2.getIdCine());

        System.out.println(catalogoService.findPeliculas(cine2.getIdCine(),
                tomorrow));
        /*
         * Intentamos añadir mas sesiones en el mismo dia, con sesiones ya
         * existentes
         */
        sadd.remove(sesion);
        sadd.add(sesion2);
        catalogoService.createSesions(sadd, tomorrow, cine2.getIdCine()); // Exception
    }

    @Test(expected = BadArgumentException.class)
    public void TestCreateYesterdaySesions() throws BadArgumentException,
            CoincidentSesionsException {

        Provincia provincia = new Provincia("A Coruña", new HashSet<Cine>());
        provinciaDao.save(provincia);

        Cine cine2 = new Cine("Yelmo Espacio", new Float(9.80), provincia,
                new HashSet<Sala>());
        cineDao.save(cine2);

        Sala sala1 = new Sala(1, 100, cine2);
        salaDao.save(sala1);

        Sala sala2 = new Sala(2, 200, cine2);
        salaDao.save(sala2);

        // Creamos las peliculas necesarias para el test
        Pelicula pelicula = new Pelicula("Los Juego de hambre",
                Calendar.getInstance(), Calendar.getInstance(), 120,
                "Una pelicula interesante ");
        peliculaDao.save(pelicula);

        Pelicula pelicula2 = new Pelicula("The Artist", Calendar.getInstance(),
                Calendar.getInstance(), 120, "Una pelicula muda");
        peliculaDao.save(pelicula2);

        List<Sesion> sesions = new ArrayList<Sesion>();

        /* La fecha pasada es anterior a la actual */

        Calendar ayer = Calendar.getInstance();
        ayer.add(Calendar.DAY_OF_MONTH, -1);

        catalogoService.createSesions(sesions, ayer, cine2.getIdCine());
    }

    @Test
    public void TestCreateConcidentSesions() {

        boolean cached = false;

        Provincia provincia = new Provincia("A Coruña", new HashSet<Cine>());
        provinciaDao.save(provincia);

        Cine cine2 = new Cine("Yelmo Espacio", new Float(9.80), provincia,
                new HashSet<Sala>());
        cineDao.save(cine2);

        Sala sala1 = new Sala(1, 100, cine2);
        salaDao.save(sala1);

        Sala sala2 = new Sala(2, 200, cine2);
        salaDao.save(sala2);

        // Creamos las peliculas necesarias para el test
        Pelicula pelicula = new Pelicula("Los Juego de hambre",
                Calendar.getInstance(), Calendar.getInstance(), 120,
                "Una pelicula interesante ");
        peliculaDao.save(pelicula);

        Pelicula pelicula2 = new Pelicula("The Artist", Calendar.getInstance(),
                Calendar.getInstance(), 120, "Una pelicula muda");
        peliculaDao.save(pelicula2);

        List<Sesion> sesions = new ArrayList<Sesion>();

        /* Sesiones con salas distintas y fecha coincidente */
        cached = false;

        Calendar inOneYear = Calendar.getInstance();
        inOneYear.add(Calendar.YEAR, 1);

        Sesion s1 = new Sesion(new Float(4.30), inOneYear, pelicula, sala1, 0);
        sesions.add(s1);

        Sesion s2 = new Sesion(new Float(4.30), inOneYear, pelicula, sala2, 0);
        sesions.add(s2);

        try {
            catalogoService
                    .createSesions(sesions, inOneYear, cine2.getIdCine());
        } catch (BadArgumentException | CoincidentSesionsException e) {
            // e.printStackTrace();
            cached = true;
        }
        assertFalse(cached);
        sesions.clear();

        /* Sesiones con misma sala y f1 + d1 > f2 */
        cached = false;

        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1);
        tomorrow.set(Calendar.HOUR_OF_DAY, 20);

        sesions.clear();
        Sesion s3 = new Sesion(new Float(4.30), tomorrow, pelicula, sala1, 0);
        sesions.add(s3);

        Calendar tomorrow2 = Calendar.getInstance();
        tomorrow2.add(Calendar.DAY_OF_MONTH, 1);
        tomorrow2.set(Calendar.HOUR_OF_DAY, 21);

        Sesion s4 = new Sesion(new Float(4.30), tomorrow2, pelicula, sala1, 0);
        sesions.add(s4);

        try {
            catalogoService.createSesions(sesions, tomorrow, cine2.getIdCine());
        } catch (CoincidentSesionsException e) {
            // TODO Comprobar que las sesiones que recoge la excepcion son s3 y
            // s4
            cached = true;
        } catch (BadArgumentException e) {
            e.printStackTrace();
            cached = false;
        }
        assertTrue(cached);
        sesions.clear();

        /* Sesiones con misma sala y f1 < f2 + d2 */
        cached = false;

        sesions.clear();
        Sesion s5 = new Sesion(new Float(4.30), tomorrow2, pelicula, sala1, 0);
        sesions.add(s5);

        Sesion s6 = new Sesion(new Float(4.30), tomorrow, pelicula, sala1, 0);
        sesions.add(s6);

        try {
            catalogoService.createSesions(sesions, tomorrow, cine2.getIdCine());
        } catch (CoincidentSesionsException e) {
            // TODO Comprobar que las sesiones que recoge la excepcion son s3 y
            // s4
            cached = true;
        } catch (BadArgumentException e) {
            e.printStackTrace();
            cached = false;
        }
        assertTrue(cached);
        sesions.clear();
    }
}
