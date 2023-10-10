import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class GoogleTest {

        @Test
        public void testSearch() throws InterruptedException {
            WebDriver driver = new ChromeDriver();
            driver.get("https://www.google.com/");
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(9000));

            WebElement textBox = driver.findElement(By.className("gLFyF"));
            textBox.sendKeys("Selenium");

            Thread.sleep(9000);

            WebElement searchButton = driver.findElement(By.className("gNO89b"));
            searchButton.click();

            WebElement title = driver.findElement(By.className("yKMVIe"));
            String value = title.getText();
            Assert.assertEquals(value,"Selenium");
            driver.quit();


        }


    }

