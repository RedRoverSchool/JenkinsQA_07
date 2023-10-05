package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.function.Function;

public class GroupJavaAutomationTest {
    WebDriver driver;

    @BeforeMethod
    public void beforeMethod() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void herokuAppHomePageTest() throws InterruptedException {
        try {
            Assert.assertEquals(driver.getTitle(), "The Internet");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void herokuAppAddRemoveTest() throws InterruptedException {
        try {
            WebElement addRemove = driver.findElement(By.xpath("//a[@href='/add_remove_elements/']"));
            addRemove.click();
            WebElement addElement = driver.findElement(By.xpath("//*[@id='content']/div/button"));
            addElement.click();
            //explicit expectation - declaration, initialization
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            //explicitly wait for the visibility of the button element
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"elements\"]/button[1]")));
            //clicking on the button, this action will only happen when the button element is visible on the page
            driver.findElement(By.xpath("//*[@id=\"elements\"]/button[1]")).click();

        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }
    @Test
    public void herokuAppGroupTest() throws InterruptedException {
        try {
            driver.getCurrentUrl();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
