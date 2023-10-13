package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;


@Ignore
public class GroupSurvivorsTest {

    @Test
    public void  evgenySSearchTest() throws InterruptedException {
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://ru.wikipedia.org/wiki/");

            WebElement textBox = driver.findElement(By.className("vector-search-box-input"));
            textBox.sendKeys("Java");

            Thread.sleep(600);

            WebElement searchButton = driver.findElement(By.xpath("//input[@class='searchButton']"));
            searchButton.click();

            WebElement title = driver.findElement(By.className("mw-page-title-main"));
            String value = title.getText();
            Assert.assertEquals(value, "Java");
        } finally {
            driver.quit();
        }
    }
}