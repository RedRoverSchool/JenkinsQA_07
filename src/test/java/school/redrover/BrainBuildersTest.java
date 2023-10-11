package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

@Ignore
public class BrainBuildersTest {

    @Test
    public void  testCreatingDoubleRoom() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://automationintesting.online");

            driver.findElement(By.linkText("admin panel")).click();

            if (driver.findElement(By.id("username")).isDisplayed()) {
                WebElement inputName = driver.findElement(By.id("username"));
                WebElement inputPassword = driver.findElement(By.id("password"));
                inputName.click();
                inputName.sendKeys("admin");
                inputPassword.click();
                inputPassword.sendKeys("password");
                driver.findElement(By.id("doLogin")).click();
            }

            Thread.sleep(2000);
            driver.findElement(By.id("roomName")).click();
            driver.findElement(By.id("roomName")).sendKeys("111");
            driver.findElement(By.id("type")).click();
            {
                WebElement dropdown = driver.findElement(By.id("type"));
                dropdown.findElement(By.xpath("//option[. = 'Double']")).click();
            }
            driver.findElement(By.id("accessible")).click();
            {
                WebElement dropdown = driver.findElement(By.id("accessible"));
                dropdown.findElement(By.xpath("//option[. = 'true']")).click();
            }

            driver.findElement(By.id("roomPrice")).click();
            driver.findElement(By.id("roomPrice")).sendKeys("150");
            driver.findElement(By.id("wifiCheckbox")).click();
            driver.findElement(By.id("refreshCheckbox")).click();
            driver.findElement(By.id("tvCheckbox")).click();
            driver.findElement(By.id("safeCheckbox")).click();
            driver.findElement(By.id("createRoom")).click();
            driver.findElement(By.linkText("home page")).click();

            Thread.sleep(5000);
            Assert.assertTrue(driver.getPageSource().contains("Double"));
        } finally {
            driver.quit();
        }
    }
    @Test
    public void testAlcobendasSearch() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://www.alcobendas.org/es");

            String title = driver.getTitle();
            assertEquals(title, "Página Web del Ayuntamiento de Alcobendas");

            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

            WebElement lupaButton = driver.findElement(By.xpath("//*[@id='block-views-block-ayto-vista-lupa-header-block-1']/div/div"));
            WebElement buscarButton = driver.findElement(By.xpath("//*[@id='edit-submit-ayto-resultados-de-busqueda-bloque']"));
            WebElement searchInput = driver.findElement(By.xpath("//*[@id='edit-buscar']"));

            lupaButton.click();
            searchInput.sendKeys("yoga");
            buscarButton.click();

            WebElement resultOfSearch = driver.findElement(By.cssSelector("h2:nth-child(2)"));

            String value = resultOfSearch.getText();
            Assert.assertEquals(value, "/2 resultados");
        } finally {
            driver.quit();
        }
    }
    @Test
    public void testAskentSearch() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://www.askent.ru/");

            String title = driver.getTitle();
            assertEquals(title, "ASKENT - российский бренд аксессуаров из натуральной кожи");

            WebElement magnifierIcon = driver.findElement(By.xpath("//*[@id='no_indent']/div[5]/div[2]/div/div[3]/div[2]/div[1]"));
            magnifierIcon.click();

            WebElement searchTextField = driver.findElement(By.xpath("//*[@id='no_indent']/div[5]/div[2]/div/div[3]/div[2]/div[2]/form/input"));
            searchTextField.click();
            searchTextField.sendKeys("сумка");

            WebElement magnifierButton = driver.findElement(By.xpath("//*[@id='no_indent']/div[5]/div[2]/div/div[3]/div[2]/div[2]/form/button"));
            magnifierButton.click();

            WebElement searchResult = driver.findElement(By.cssSelector("h1"));
            String result = searchResult.getText();
            Assert.assertEquals(result, "Результаты поиска");
        } finally {
            driver.quit();
        }
    }
}
