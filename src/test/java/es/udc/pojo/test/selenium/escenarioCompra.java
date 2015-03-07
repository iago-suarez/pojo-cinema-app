package es.udc.pojo.test.selenium;

import static junit.framework.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * The Class escenarioCompra.
 */
public class escenarioCompra {

    /** The driver. */
    private WebDriver driver;

    /**
     * Open browser.
     */
    @Before
    public void openBrowser() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:9090/pojo-cinema-app/catalogo/seleccioncine");

    }

    /**
     * Close browser.
     */
    @After
    public void closeBrowser() {
        driver.close();
        driver.quit();
    }

    /**
     * Precondicion: Debe existir una sesion en el cine para que el test
     * funcione.
     *
     * @throws InterruptedException
     *             the interrupted exception
     */
    @Test
    public void comprarEntradas() throws InterruptedException {

        /* Escogemos cine */
        Select selectProvincia = new Select(
                (new WebDriverWait(driver, 10)).until(ExpectedConditions
                        .presenceOfElementLocated(By.id("selectProvincia"))));

        selectProvincia.selectByVisibleText("PONTEVEDRA");

        WebElement zone = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By
                        .id("modelZoneProvincia")));
        Select selectCine = new Select(zone.findElement(By.tagName("select")));

        selectCine.selectByVisibleText("FILMAX PONTEVEDRA");

        WebElement seleccionar = driver.findElement(By.className("button"))
                .findElement(By.tagName("input"));
        seleccionar.click();

        /*
         * Estamos en la pagina inicial, buscamos la primera sesion de la
         * primera pelicula
         */
        WebElement sesion = (new WebDriverWait(driver, 10)).until(
                ExpectedConditions.presenceOfElementLocated(By.id("sesion")))
                .findElement(By.tagName("a"));
        sesion.click();

        /* Estamos en la pagina de detalles de sesion */
        WebElement comprar = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By
                        .linkText("Comprar")));
        comprar.click();

        /*
         * Navegamos hacia la pagina de autenticacion, esperando a que cargue la
         * pagina
         */

        WebElement loginName = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By
                        .id("loginName")));
        WebElement passwd = driver.findElement(By.id("password"));
        WebElement submit = driver.findElement(By.id("loginEnterBut"))
                .findElement(By.tagName("input"));

        loginName.sendKeys("EMOSQUEIRA");
        passwd.sendKeys("1234");
        submit.click();

        /* Deberiamos estar en la pagina de comprar entradas */

        WebElement numeroTarjetaCredito = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By
                        .id("numeroTarjetaCredito")));
        WebElement expTarjetaCredito = driver.findElement(By
                .id("expiracionTarjetaCredito"));
        WebElement numEntradas = driver.findElement(By.id("numeroDeEntradas"));
        WebElement comprarEntradas = driver.findElement(By.className("table"))
                .findElement(By.tagName("input"));

        numeroTarjetaCredito.sendKeys("1234567890123456");
        expTarjetaCredito.sendKeys("01/2016");
        numEntradas.sendKeys("1");
        comprarEntradas.click();

        /* Obtenemos el id de compra */
        WebElement idCompraObtenido = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By
                        .tagName("h3")));
        String idCompra1 = idCompraObtenido.getText();

        /*
         * Visualizamos el historico de compras y obtenemos el id de compra mas
         * reciente (que debe ser el de las entradas que acabamos de comprar)
         */
        WebElement misCompras = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By
                        .id("menuLinks"))).findElement(By.tagName("a"));
        misCompras.click();
        WebElement idCompraMisCompras = driver.findElement(By.id("idCompra"));
        String idCompra2 = idCompraMisCompras.getText();

        /*
         * El id obtenido despues de comprar y el primero del historico deben
         * coincidir
         */
        assertEquals(idCompra1, idCompra2);

    }

}
