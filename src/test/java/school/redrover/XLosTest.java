package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class XLosTest extends BaseTest {

    @Test
    public void SauceDemoPositiveLoginTest(){

        try {
            getDriver().get("https://www.saucedemo.com/");
            WebElement username = getDriver().findElement(By.id("user-name"));
            WebElement password = getDriver().findElement(By.id("password"));
            WebElement loginBtn = getDriver().findElement(By.id("login-button"));

            username.sendKeys("standard_user");
            password.sendKeys("secret_sauce");
            loginBtn.click();

            Thread.sleep(2000);

            String expectedTitle = "Swag Labs";
            String actualTitle = getDriver().getTitle();

            Assert.assertEquals(actualTitle, expectedTitle, "Login failed.");
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
