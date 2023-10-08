package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;


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
    @Test

    public void sabinaFirstTest() throws InterruptedException{
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("http://baskino.me/index.php?do=stoptime&id=23027&action=count");
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
            WebElement textBox = driver.findElement(By.id("story"));
            WebElement loginButton = driver.findElement(By.className("find-button"));
            textBox.sendKeys("Avengers");
            loginButton.click();
            WebElement text = driver.findElement(By.className("logo"));
            String value = text.getText();
            Assert.assertEquals(value,"Baskino.me" );
        } finally {
            driver.quit();
        }

    }
}