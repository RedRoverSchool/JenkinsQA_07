package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

public class GroupJavaJitsuTest  extends BaseTest {

    @Test
    public void testGetTile (){
        getDriver().get("https://www.saucedemo.com");

        String title = getDriver().getTitle();
        Assert.assertEquals("Swag Labs", title);
    }

   @Ignore
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

    @Test
    public void firsTest() throws InterruptedException{
        JenkinsUtils.login(getDriver());
       WebElement newItem = getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']"));
       newItem.click();
       WebElement itemName = getDriver().findElement(By.id("name"));
       itemName.sendKeys("NewProject2");
       WebElement pipeLine = getDriver().findElement(By.xpath("//span[normalize-space()='Pipeline']"));
       pipeLine.click();
       WebElement button = getDriver().findElement(By.id("ok-button"));
       button.click();
        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1[normalize-space()='Configure']")).getText(),
                "Configure");
    }
}
