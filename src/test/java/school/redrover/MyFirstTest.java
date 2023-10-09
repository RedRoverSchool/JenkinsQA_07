package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class MyFirstTest {

    @Test
    public void testSearch(){

        WebDriver driver = new ChromeDriver();


        driver.get("https://elitetransit.com/");
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1000));
        WebElement buttonContact = driver.findElement(By.xpath("//ul[@id='top-menu']//a[normalize-space()='Contact']"));
        buttonContact.click();
        String title = driver.getTitle();

        Assert.assertEquals(title, "Contact | ELITE Transit Solutions");


        driver.quit();

    }


}
