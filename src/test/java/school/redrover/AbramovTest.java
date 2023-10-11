package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class AbramovTest {
    WebDriver driver;
    @BeforeTest
    public void beforeTest(){
        driver = new FirefoxDriver();
    }

    @AfterTest
    public void afterTest(){
        driver.quit();
    }

    @Test
    public void titleTest(){
        driver.get("https://bandcamp.com/");

        String titleValue = driver.findElement(By.cssSelector("h1")).getText();
        Assert.assertEquals(titleValue,"Bandcamp");
    }

    @Test
    public void searchByTagSelectTest(){
        driver.get("https://bandcamp.com/");

        WebElement searchForm = driver.findElement(By.xpath("//div[@id='corphome-autocomplete-form']//input[contains(@class, 'search-bar')]"));
        searchForm.click();

        WebElement electronicTag = driver.findElement(By.xpath("//ul[@class='genre-list']//a[contains(@href, 'electronic')]"));
        electronicTag.click();

        String title = driver.findElement(By.xpath("//h1[@class='name']")).getText();
        Assert.assertEquals(title, "electronic");
    }

    @Test
    public void searchByTagTypeTest() throws InterruptedException {
        driver.get("https://bandcamp.com/");

        WebElement searchForm = driver.findElement(By.xpath("//div[@id='corphome-autocomplete-form']//input[contains(@class, 'search-bar')]"));
        searchForm.sendKeys("trance");

        Thread.sleep(1000);
        WebElement typedTag = driver.findElement(By.xpath("//li[@class='simple results-tags']//span[contains(text(),'trance')]"));
        typedTag.click();

        String title = driver.findElement(By.xpath("//h1[@class='name']")).getText();
        Assert.assertEquals(title, "trance");
    }
}
