package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class FirstTest {
    public WebDriver driver;


    @BeforeMethod
    public void beforeMethod() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.google.com/");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

        @Test
        public void testSearch() throws InterruptedException {

            try {

                WebElement textBox = driver.findElement(By.className("gLFyf"));
                // textBox.sendKeys("Selenium");
//            Thread.sleep(800);
//            WebElement searchButton = driver.findElement(By.className("gNO89b"));
//            searchButton.click();
//            WebElement title = driver.findElement(By.className("yKMVIe"));
//            String value = title.getText();
//            Assert.assertEquals(value, "Selenium");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
