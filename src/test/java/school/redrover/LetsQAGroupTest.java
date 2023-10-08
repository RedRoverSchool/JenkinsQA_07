package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class LetsQAGroupTest {
    private static final String BASE_URL = "https://www.sawinery.net/";
    WebDriver driver = new ChromeDriver();
    @Test
    public void testChrome(){

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        String title = driver.getTitle();
        Assert.assertEquals("Web form", title);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement textBox = driver.findElement(By.name("my-text"));
        WebElement submitButton = driver.findElement(By.cssSelector("button"));

        textBox.sendKeys("Selenium");
        submitButton.click();

        WebElement message = driver.findElement(By.id("message"));
        String value = message.getText();
        Assert.assertEquals("Received!", value);

        driver.quit();
    }


    @Test
    public void checkTitle() {
        driver.get(BASE_URL);

        String title = driver.getTitle();
        Assert.assertEquals("Sawinery - #1 Woodworking Education Resource", title);
    }
    @Test
    public void searchTest() {
        try {
            driver.get(BASE_URL);

            WebElement searchField = driver.findElement(By.xpath("//*[@id='elementor-search-form-77435a6']"));
            searchField.sendKeys("saw");
            searchField.sendKeys(Keys.ENTER);

            Thread.sleep(1000);

            WebElement searchResult = driver.findElement(By.xpath("//*[@id=\"content\"]/div/section[2]/div/div/div/div/div/div/article[1]/div/h3/a"));
            String searchText = searchResult.getAttribute("textContent").toLowerCase();
            Assert.assertTrue(searchText.contains("saw"));

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @AfterTest
    public void quitBrowser() {
        driver.quit();
    }

}
