package es.udc.pojo.test.model.compraservice;

import static es.udc.pojo.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.pojo.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pojo.model.cine.Cine;
import es.udc.pojo.model.cine.CineDao;
import es.udc.pojo.model.compra.Compra;
import es.udc.pojo.model.compra.CompraDao;
import es.udc.pojo.model.compraservice.CompraBlock;
import es.udc.pojo.model.compraservice.CompraService;
import es.udc.pojo.model.pelicula.Pelicula;
import es.udc.pojo.model.pelicula.PeliculaDao;
import es.udc.pojo.model.provincia.Provincia;
import es.udc.pojo.model.provincia.ProvinciaDao;
import es.udc.pojo.model.sala.Sala;
import es.udc.pojo.model.sala.SalaDao;
import es.udc.pojo.model.sesion.Sesion;
import es.udc.pojo.model.sesion.SesionDao;
import es.udc.pojo.model.userprofile.TipoUsuario;
import es.udc.pojo.model.userprofile.UserProfile;
import es.udc.pojo.model.userprofile.UserProfileDao;
import es.udc.pojo.model.userservice.UserService;
import es.udc.pojo.model.userservice.util.PasswordEncrypter;
import es.udc.pojo.model.util.BadArgumentException;
import es.udc.pojo.model.util.CompraYaEntregadaException;
import es.udc.pojo.model.util.SesionLlenaException;
import es.udc.pojo.model.util.SesionPasadaException;
import es.udc.pojo.model.util.TarjetaCaducadaException;
import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class CompraServiceTest {

    private static final long NON_EXISTENT_COMPRA_ID = -1;
    @Autowired
    private UserProfileDao    userProfileDao;
    @Autowired
    private ProvinciaDao      provinciaDao;
    @Autowired
    private CineDao           cineDao;
    @Autowired
    private SalaDao           salaDao;
    @Autowired
    private SesionDao         sesionDao;
    @Autowired
    private PeliculaDao       peliculaDao;
    @Autowired
    private CompraDao         compraDao;
    @Autowired
    private UserService       userService;
    @Autowired
    private CompraService     compraService;

    @Test
    public void testCreateCompraAndFindCompra()
            throws DuplicateInstanceException, InstanceNotFoundException,
            SesionLlenaException, BadArgumentException, InterruptedException,
            TarjetaCaducadaException, SesionPasadaException {

        // Creamos los datos necesarios para realizar una compra
        UserProfile userProfile = new UserProfile("daoUser",
                PasswordEncrypter.crypt("userPassword"), "name", "lastName",
                "user@udc.es", TipoUsuario.ESPECTADOR);
        userProfileDao.save(userProfile);

        Provincia provincia = new Provincia("A Coruña", new HashSet<Cine>());
        provinciaDao.save(provincia);
        Cine cine = new Cine("Filmax Marineda", new Float(9.80), provincia,
                new HashSet<Sala>());
        cineDao.save(cine);
        Sala sala = new Sala(20, 30, cine);
        salaDao.save(sala);
        Pelicula pelicula = new Pelicula("Revolucion en la granja",
                Calendar.getInstance(), Calendar.getInstance(), 5,
                "La pelicula que emociono a  la critica e hizo llorar a Putin");
        peliculaDao.save(pelicula);

        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 1);
        Sesion sesion = new Sesion(new Float(13), c, pelicula, sala, 0);
        sesionDao.save(sesion);

        Calendar exptarjeta = Calendar.getInstance();
        exptarjeta.add(Calendar.YEAR, 1);

        int nLocalidades = 3;
        // Añadimos una compra
        Long idCompra = compraService.createCompra(
                userProfile.getUserProfileId(), nLocalidades,
                "1234567889123456", exptarjeta, sesion.getIdSesion());

        Compra compra = compraService.findCompra(idCompra);

        /* Check data. */

        assertEquals(userProfile, compra.getUserprofile());
        assertEquals(3, compra.getNumLocalidades());
        assertEquals("1234567889123456", compra.getNumTarjeta());
        assertEquals(exptarjeta, compra.getfExpiracion());
        assertEquals(sesion, compra.getSesion());

        // Comprobamos que se ha aumentado en nLocalidades el número de
        // asistentes
        // que inicialmente era 0
        assertEquals(nLocalidades, sesion.getnAsistentes());

    }

    @Test(expected = InstanceNotFoundException.class)
    public void testFindNotExistentCompra() throws InstanceNotFoundException {

        compraService.findCompra(new Long(34567));
    }

    @Test(expected = SesionLlenaException.class)
    public void testSesionLlena() throws SesionLlenaException,
            InstanceNotFoundException, BadArgumentException,
            TarjetaCaducadaException, SesionPasadaException {

        // Creamos los datos necesarios para realizar una compra
        UserProfile userProfile = new UserProfile("daoUser",
                PasswordEncrypter.crypt("userPassword"), "name", "lastName",
                "user@udc.es", TipoUsuario.ESPECTADOR);
        userProfileDao.save(userProfile);

        Provincia provincia = new Provincia("A Coruña", new HashSet<Cine>());
        provinciaDao.save(provincia);
        Cine cine = new Cine("Filmax Marineda", new Float(9.80), provincia,
                new HashSet<Sala>());
        cineDao.save(cine);
        // Ponemos la capacidad en 10 :
        Sala sala = new Sala(20, 10, cine);
        salaDao.save(sala);

        Pelicula pelicula = new Pelicula("Revolucion en la granja",
                Calendar.getInstance(), Calendar.getInstance(), 5,
                "La pelicula que emociono a  la critica e hizo llorar a Putin");
        peliculaDao.save(pelicula);
        // Creamos la sesión como si ya hubiese 6 personas asistentes
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 1);
        Sesion sesion = new Sesion(new Float(13), c, pelicula, sala, 6);
        sesionDao.save(sesion);

        Calendar exptarjeta = Calendar.getInstance();
        exptarjeta.add(Calendar.YEAR, 1);

        // Dejamos la sesión llena
        compraService.createCompra(userProfile.getUserProfileId(), 4,
                "1233567819123456", (Calendar) exptarjeta.clone(),
                sesion.getIdSesion());

        assertEquals(sesion.getnAsistentes(), 10);

        // Añadimos una compra con la sesion llena
        compraService.createCompra(userProfile.getUserProfileId(), 3,
                "1234567889123456", (Calendar) exptarjeta.clone(),
                sesion.getIdSesion());
    }

    @Test(expected = TarjetaCaducadaException.class)
    public void testCreateCompraTarjetaCaducada()
            throws DuplicateInstanceException, InstanceNotFoundException,
            SesionLlenaException, BadArgumentException, InterruptedException,
            TarjetaCaducadaException, SesionPasadaException {

        // Creamos los datos necesarios para realizar una compra
        UserProfile userProfile = new UserProfile("daoUser",
                PasswordEncrypter.crypt("userPassword"), "name", "lastName",
                "user@udc.es", TipoUsuario.ESPECTADOR);
        userProfileDao.save(userProfile);

        Provincia provincia = new Provincia("A Coruña", new HashSet<Cine>());
        provinciaDao.save(provincia);
        Cine cine = new Cine("Filmax Marineda", new Float(9.80), provincia,
                new HashSet<Sala>());
        cineDao.save(cine);
        Sala sala = new Sala(20, 30, cine);
        salaDao.save(sala);
        Pelicula pelicula = new Pelicula("Revolucion en la granja",
                Calendar.getInstance(), Calendar.getInstance(), 5,
                "La pelicula que emociono a  la critica e hizo llorar a Putin");
        peliculaDao.save(pelicula);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 1);
        Sesion sesion = new Sesion(new Float(13), c, pelicula, sala, 0);
        sesionDao.save(sesion);

        Calendar exptarjeta = Calendar.getInstance();
        exptarjeta.set(Calendar.YEAR, 1999);

        // Añadimos una compra
        compraService.createCompra(userProfile.getUserProfileId(), 3,
                "1234567889123456", exptarjeta, sesion.getIdSesion());

    }

    @Test
    public void testFindCompras() {

        // Creamos los datos necesarios para realizar una compra
        UserProfile userProfile = new UserProfile("daoUser",
                PasswordEncrypter.crypt("userPassword"), "name", "lastName",
                "user@udc.es", TipoUsuario.ESPECTADOR);
        userProfileDao.save(userProfile);

        Provincia provincia = new Provincia("A Coruña", new HashSet<Cine>());
        provinciaDao.save(provincia);
        Cine cine = new Cine("Filmax Marineda", new Float(9.80), provincia,
                new HashSet<Sala>());
        cineDao.save(cine);
        Sala sala = new Sala(20, 30, cine);
        salaDao.save(sala);
        Pelicula pelicula = new Pelicula("Revolucion en la granja",
                Calendar.getInstance(), Calendar.getInstance(), 5,
                "La pelicula que emociono a  la critica e hizo llorar a Putin");
        peliculaDao.save(pelicula);

        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 1);
        Sesion sesion = new Sesion(new Float(13), c, pelicula, sala, 0);
        sesionDao.save(sesion);

        // -> Caso vacío
        CompraBlock compras = compraService.findCompras(
                userProfile.getUserProfileId(), 0, 3);
        assertTrue(compras.getCompras().isEmpty());
        assertFalse(compras.getExistMoreCompras());

        Compra compra1 = new Compra(1, "1233567819123456",
                Calendar.getInstance(), false, Calendar.getInstance(),
                userProfile, sesion);
        compraDao.save(compra1);

        // -> Caso count =3 , con 1 compra
        compras = compraService.findCompras(userProfile.getUserProfileId(), 0,
                3);

        assertEquals(compras.getCompras().get(0), compra1);
        assertFalse(compras.getExistMoreCompras());

        Compra compra2 = new Compra(2, "1233567819123456",
                Calendar.getInstance(), false, Calendar.getInstance(),
                userProfile, sesion);
        compraDao.save(compra2);

        // Comprobar que salen ordenadas correctamente
        compras = compraService.findCompras(userProfile.getUserProfileId(), 0,
                5);

        assertFalse(compras.getCompras().isEmpty());

        Compra compra3 = new Compra(3, "1233567819123456",
                Calendar.getInstance(), false, Calendar.getInstance(),
                userProfile, sesion);
        compraDao.save(compra3);

        Compra compra4 = new Compra(4, "1233567819123456",
                Calendar.getInstance(), false, Calendar.getInstance(),
                userProfile, sesion);
        compraDao.save(compra4);

        Compra compra5 = new Compra(5, "1233567819123456",
                Calendar.getInstance(), false, Calendar.getInstance(),
                userProfile, sesion);
        compraDao.save(compra5);

        // -> Caso count =3 , con 5 compras,
        compras = compraService.findCompras(userProfile.getUserProfileId(), 0,
                3);
        assertTrue(compras.getExistMoreCompras());
        compras = compraService.findCompras(userProfile.getUserProfileId(), 3,
                3);
        assertFalse(compras.getExistMoreCompras());
        assertEquals(compras.getCompras().size(), 2);

        Compra compra6 = new Compra(6, "1233567819123456",
                Calendar.getInstance(), false, Calendar.getInstance(),
                userProfile, sesion);
        compraDao.save(compra6);

        // -> Caso count =3 , con 6 compras

        compras = compraService.findCompras(userProfile.getUserProfileId(), 3,
                3);
        assertFalse(compras.getExistMoreCompras());
        assertEquals(compras.getCompras().size(), 3);

        Compra compra7 = new Compra(7, "1233567819123456",
                Calendar.getInstance(), false, Calendar.getInstance(),
                userProfile, sesion);
        compraDao.save(compra7);

        // -> Caso count =3 , con 7 compras

        compras = compraService.findCompras(userProfile.getUserProfileId(), 3,
                3);
        assertTrue(compras.getExistMoreCompras());
        assertEquals(compras.getCompras().size(), 3);

    }

    @Test
    public void entregarEntradas() throws CompraYaEntregadaException,
            InstanceNotFoundException, SesionLlenaException,
            BadArgumentException, TarjetaCaducadaException,
            SesionPasadaException {

        // Creamos los datos necesarios para realizar una compra
        UserProfile userProfile = new UserProfile("daoUser2",
                PasswordEncrypter.crypt("userPassword2"), "name2", "lastName2",
                "user2@udc.es", TipoUsuario.ESPECTADOR);
        userProfileDao.save(userProfile);

        Provincia provincia = new Provincia("Lugo", new HashSet<Cine>());
        provinciaDao.save(provincia);
        Cine cine = new Cine("Filmax As Termas", new Float(9.80), provincia,
                new HashSet<Sala>());
        cineDao.save(cine);
        Sala sala = new Sala(20, 30, cine);
        salaDao.save(sala);
        Pelicula pelicula = new Pelicula("No habra paz para los malvados",
                Calendar.getInstance(), Calendar.getInstance(), 15,
                "La pelicula que emociono a Spielberg");
        peliculaDao.save(pelicula);

        Calendar expsesion = Calendar.getInstance();
        expsesion.add(Calendar.YEAR, 1);
        Sesion sesion = new Sesion(new Float(13), expsesion, pelicula, sala, 0);
        sesionDao.save(sesion);

        // Añadimos una compra
        Calendar exptarjeta = Calendar.getInstance();
        exptarjeta.add(Calendar.YEAR, 1);
        Long compraId = compraService.createCompra(
                userProfile.getUserProfileId(), 3, "1234567889123456",
                exptarjeta, sesion.getIdSesion());

        Compra compra = compraService.findCompra(compraId);

        // Comprobamos que las entregadas inicialmente no estan entregadas
        assertFalse(compra.isEntregada());

        // Entregamos entradas
        compraService.entregarEntradas(compraId);

        assertEquals(3, compra.getSesion().getnAsistentes());

        // TODO La excepcion CompraYaEntregadaException
    }

    @Test(expected = CompraYaEntregadaException.class)
    public void entregarEntradasYaEntregadas()
            throws CompraYaEntregadaException, InstanceNotFoundException,
            SesionLlenaException, BadArgumentException,
            TarjetaCaducadaException, SesionPasadaException {

        // Creamos los datos necesarios para realizar una compra
        UserProfile userProfile = new UserProfile("daoUser2",
                PasswordEncrypter.crypt("userPassword2"), "name2", "lastName2",
                "user2@udc.es", TipoUsuario.ESPECTADOR);
        userProfileDao.save(userProfile);

        Provincia provincia = new Provincia("Lugo", new HashSet<Cine>());
        provinciaDao.save(provincia);
        Cine cine = new Cine("Filmax As Termas", new Float(9.80), provincia,
                new HashSet<Sala>());
        cineDao.save(cine);
        Sala sala = new Sala(20, 30, cine);
        salaDao.save(sala);
        Pelicula pelicula = new Pelicula("No habra paz para los malvados",
                Calendar.getInstance(), Calendar.getInstance(), 15,
                "La pelicula que emociono a Spielberg");
        peliculaDao.save(pelicula);

        Calendar sessionDate = Calendar.getInstance();
        sessionDate.add(Calendar.MONTH, 1);
        Sesion sesion = new Sesion(new Float(13), sessionDate, pelicula, sala,
                0);
        sesionDao.save(sesion);

        // Añadimos una compra
        Calendar exptarjeta = Calendar.getInstance();
        exptarjeta.add(Calendar.YEAR, 1);
        Long compraId = compraService.createCompra(
                userProfile.getUserProfileId(), 3, "1234567889123456",
                exptarjeta, sesion.getIdSesion());

        Compra compra = compraService.findCompra(compraId);

        // Comprobamos que las entregadas inicialmente no estan entregadas
        assertFalse(compra.isEntregada());

        // Entregamos entradas
        compraService.entregarEntradas(compraId);

        assertEquals(3, compra.getSesion().getnAsistentes());

        compraService.entregarEntradas(compraId);

    }

    @Test(expected = InstanceNotFoundException.class)
    public void entregarEntradasInstanceNotFound()
            throws CompraYaEntregadaException, InstanceNotFoundException,
            SesionLlenaException, BadArgumentException,
            TarjetaCaducadaException, SesionPasadaException {

        compraService.entregarEntradas(NON_EXISTENT_COMPRA_ID);

    }

    @Test(expected = SesionPasadaException.class)
    public void crearCompraSesionPasada() throws SesionPasadaException,
            InstanceNotFoundException, SesionLlenaException,
            TarjetaCaducadaException {
        // Creamos los datos necesarios para realizar una compra
        UserProfile userProfile = new UserProfile("daoUser2",
                PasswordEncrypter.crypt("userPassword2"), "name2", "lastName2",
                "user2@udc.es", TipoUsuario.ESPECTADOR);
        userProfileDao.save(userProfile);

        Provincia provincia = new Provincia("Lugo", new HashSet<Cine>());
        provinciaDao.save(provincia);
        Cine cine = new Cine("Filmax As Termas", new Float(9.80), provincia,
                new HashSet<Sala>());
        cineDao.save(cine);
        Sala sala = new Sala(20, 30, cine);
        salaDao.save(sala);

        Calendar expsesion = Calendar.getInstance();
        expsesion.set(Calendar.YEAR, 1999);
        Pelicula pelicula = new Pelicula("No habra paz para los malvados",
                Calendar.getInstance(), Calendar.getInstance(), 15,
                "La pelicula que emociono a Spielberg");
        peliculaDao.save(pelicula);

        // Creamos una sesion que ya ha pasado
        Calendar fechaPasada = Calendar.getInstance();
        fechaPasada.set(Calendar.YEAR, 1990);

        Sesion sesion = new Sesion(new Float(13), fechaPasada, pelicula, sala,
                0);
        sesionDao.save(sesion);

        // Añadimos una compra
        Calendar exptarjeta = Calendar.getInstance();
        exptarjeta.add(Calendar.YEAR, 1);
        compraService.createCompra(userProfile.getUserProfileId(), 3,
                "1234567889123456", exptarjeta, sesion.getIdSesion());
    }

    @Test(expected = SesionPasadaException.class)
    public void entregarEntradasSesionPasada()
            throws CompraYaEntregadaException, InstanceNotFoundException,
            SesionLlenaException, BadArgumentException,
            TarjetaCaducadaException, SesionPasadaException {

        // Creamos los datos necesarios para realizar una compra
        UserProfile userProfile = new UserProfile("daoUser2",
                PasswordEncrypter.crypt("userPassword2"), "name2", "lastName2",
                "user2@udc.es", TipoUsuario.ESPECTADOR);
        userProfileDao.save(userProfile);

        Provincia provincia = new Provincia("Lugo", new HashSet<Cine>());
        provinciaDao.save(provincia);
        Cine cine = new Cine("Filmax As Termas", new Float(9.80), provincia,
                new HashSet<Sala>());
        cineDao.save(cine);
        Sala sala = new Sala(20, 30, cine);
        salaDao.save(sala);

        Calendar expsesion = Calendar.getInstance();
        expsesion.set(Calendar.YEAR, 1999);
        Pelicula pelicula = new Pelicula("No habra paz para los malvados",
                Calendar.getInstance(), Calendar.getInstance(), 15,
                "La pelicula que emociono a Spielberg");
        peliculaDao.save(pelicula);

        Calendar fechaPasada = Calendar.getInstance();
        fechaPasada.set(Calendar.YEAR, 1990);

        Sesion sesion = new Sesion(new Float(13), fechaPasada, pelicula, sala,
                0);
        sesionDao.save(sesion);

        // Añadimos una compra
        Calendar exptarjeta = Calendar.getInstance();
        exptarjeta.add(Calendar.YEAR, 1);
        Compra compra = new Compra(4, "1234567889123456", exptarjeta, false,
                Calendar.getInstance(), userProfile, sesion);
        compraDao.save(compra);

        // Comprobamos que las entregadas inicialmente no estan entregadas
        assertFalse(compra.isEntregada());

        // Entregamos entradas
        compraService.entregarEntradas(compra.getIdCompra());

        assertEquals(3, compra.getSesion().getnAsistentes());

    }
}
