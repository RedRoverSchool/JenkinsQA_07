package old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
@Ignore
public class UnicornsBrezhnievaTest {
    @Ignore
    @Test
    public void amazonSearch() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://www.amazon.com/");

            WebElement textBox = driver.findElement(By.id("twotabsearchtextbox"));
            textBox.sendKeys("magnet");

            Thread.sleep(1000);

            WebElement searchButton = driver.findElement(By.id("nav-search-submit-button"));
            searchButton.click();

            Thread.sleep(1000);

            WebElement searchResult = driver.findElement(By.className("sg-col-inner"));
            String value = searchResult.getText();
            assertTrue(value.contains("magnet"));
        } finally {
            driver.quit();
        }
    }
}


