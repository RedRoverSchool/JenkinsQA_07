package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

@Ignore
public class AikaTest {

    WebDriver driver;// = new ChromeDriver();
    String categoriesOpt = "Categories";

    @BeforeMethod
    public void setup() {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.get("https://mvnrepository.com/");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void relativeLocationToLeftOfTest() {

        WebElement popularOpt = driver.findElement(By.xpath("//a[text()= 'Popular']"));

        Assert.assertEquals(driver.findElement(with(By.tagName("a"))
                .toLeftOf(popularOpt)).getText(), categoriesOpt);

    }

    @Test
    public void relativeLocationBelowTest() {

         WebElement siteDeveloped = driver.findElement(By.xpath("//span[contains(text(), 'developed')]"));
         Actions actions = new Actions(driver);
         actions.moveToElement(siteDeveloped).perform();

         Assert.assertEquals(driver.findElement(with(By.tagName("a")).below(siteDeveloped))
                 .getText(), "Contact Us");
    }

    @Test
    public void relativeLocationAboveTest() {

        WebElement whatsNew = driver.findElement(By.tagName("h1"));
        WebElement inputField = driver.findElement(with(By.id("query")).above(whatsNew));

        Assert.assertTrue(inputField.isDisplayed());

    }


}
