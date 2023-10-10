import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;


public class MaklerTest {

        @Test
        public void testSearch() throws InterruptedException {
            WebDriver driver = new ChromeDriver();
            driver.get("https://makler.md/");

            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));

            WebElement textBox = driver.findElement(By.className("disable-border"));
            textBox.sendKeys("Handmade");

            Thread.sleep(5000);

            WebElement searchButton = driver.findElement(By.id("header_searchButton"));
            searchButton.click();

            /* WebElement title = driver.findElement(By.id("header_searchField"));
            String value = title.getText();
            Assert.assertEquals(value,"Handmade");*/
            driver.quit();


        }


    }





