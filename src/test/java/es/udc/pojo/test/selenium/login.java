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
 * The Class login.
 */
public class login {

    /** The driver. */
    private WebDriver driver;

    /**
     * Open browser.
     */
    @Before
    public void openBrowser() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:9090/pojo-cinema-app");

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
     * Login test.
     */
    @Test
    public void loginTest() {

        /* Si hay cine preferido empezamos el test, sino lo escogemos */

        if (driver.getCurrentUrl().equals(
                "http://localhost:9090/pojo-cinema-app/catalogo/seleccioncine")) {
            Select selectProvincia = new Select(driver.findElement(By
                    .id("selectProvincia")));

            selectProvincia.selectByVisibleText("ASTURIAS");

            WebElement zone = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.presenceOfElementLocated(By
                            .id("modelZoneProvincia")));
            Select selectCine = new Select(zone.findElement(By
                    .tagName("select")));

            selectCine.selectByVisibleText("YELMO CINEPLEX GIJON");

            WebElement seleccionar = driver.findElement(By.className("button"));
            seleccionar.click();
        }

        /* Estamos en la pagina inicial */

        WebElement autenticarse = driver.findElement(By
                .linkText("Autenticarse"));
        autenticarse.click();

        /* Navegamos hacia la pagina de autenticacion */
        assertEquals(driver.getCurrentUrl(),
                "http://localhost:9090/pojo-cinema-app/user/login");

        WebElement loginName = driver.findElement(By.name("loginName"));
        WebElement passwd = driver.findElement(By.name("password"));
        WebElement submit = driver.findElement(By.id("loginEnterBut"))
                .findElement(By.tagName("input"));

        loginName.sendKeys("EMOSQUEIRA");
        passwd.sendKeys("1234");
        submit.click();

        /* Deberiamos estar en la pagina inicial */
        WebElement firstName = driver.findElement(By.id("menuWelcome"));
        assertEquals(firstName.getText(), "Hola EDUARDO");

    }

}
