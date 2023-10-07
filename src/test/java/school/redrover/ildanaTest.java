package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class ildanaTest {

    @Test
    public void testSearch () {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.coursera.org/");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        WebElement input = driver.findElement(By.xpath("//input[@placeholder='What do you want to learn?']"));
        input.sendKeys("Automation",Keys.ENTER);

        WebElement text = driver.findElement(By.xpath("//div[@data-e2e='NumberOfResultsSection']"));
        String value = text.getText();
        Assert.assertEquals(value,"656 results for \"Automation\"");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        driver.quit();

    }

    @Test
    public void testFilter(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.coursera.org/search?query=Automation&");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        WebElement filter = driver.findElement(By.xpath("//span[text()='Computer Science']"));
        filter.click();

        WebElement clearbutton = driver.findElement(By.xpath("//span[text()='Clear all']"));
        String buttonText = clearbutton.getText();

        Assert.assertEquals(buttonText,"Clear all" );

        driver.quit();

    }




}
