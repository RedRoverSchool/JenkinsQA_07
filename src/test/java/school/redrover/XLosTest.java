package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;

@Ignore
public class XLosTest {

    @Test
    public void SauceDemoPositiveLoginTest(){
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1000));

        try {
            driver.get("https://www.saucedemo.com/");
            WebElement username = driver.findElement(By.id("user-name"));
            WebElement password = driver.findElement(By.id("password"));
            WebElement loginBtn = driver.findElement(By.id("login-button"));

            username.sendKeys("standard_user");
            password.sendKeys("secret_sauce");
            loginBtn.click();

            Thread.sleep(2000);

            String expectedTitle = "Swag Labs";
            String actualTitle = driver.getTitle();

            Assert.assertEquals(actualTitle, expectedTitle, "Login failed.");
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            driver.quit();
        }
    }
}
