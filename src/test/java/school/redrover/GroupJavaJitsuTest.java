package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GroupJavaJitsuTest {
    WebDriver driver;
    @BeforeTest
    public void browserStart (){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com");

    }
    @Test
    public void testGetTile (){

        String title = driver.getTitle();
        Assert.assertEquals("Swag Labs", title);
    }

    @Test
    public void testLogin(){
        WebDriver driver= new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        WebElement user = driver.findElement(By.xpath("//input[@placeholder='Username']"));
        WebElement password = driver.findElement(By.xpath("//input[@placeholder='Password']"));

        user.sendKeys("standard_user");
        password.sendKeys("secret_sauce");

        WebElement loginBtn = driver.findElement(By.xpath("//input[@id='login-button']"));
        loginBtn.click();
        String url = driver.getCurrentUrl();

        Assert.assertEquals(url,  "https://www.saucedemo.com/inventory.html");
        driver.quit();

    }
}
