package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class GroupJavaAutomationTest {

    @Test
    public void herokuappHomePageTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/");
        String title = driver.getTitle();
        assertEquals(title, "The Internet");
        driver.quit();
    }
    @Test
    public void herokuAppAddRemoveTest() throws InterruptedException {
        WebDriver driver = new FirefoxDriver();
        WebDriverManager.firefoxdriver().setup();
        driver.manage().window().maximize();

        driver.get("https://the-internet.herokuapp.com/");
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

}

