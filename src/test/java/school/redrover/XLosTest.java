package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class XLosTest extends BaseTest {

    // class name were not changed due to multiple failed tests in ci
    private static final String BASE_URL = "https://www.saucedemo.com/";

    @Test
    public void testSauceDemoPositiveLogin(){

        try {
            getDriver().get(BASE_URL);
            WebElement username = getDriver().findElement(By.id("user-name"));
            WebElement password = getDriver().findElement(By.id("password"));
            WebElement loginBtn = getDriver().findElement(By.id("login-button"));

            username.sendKeys("standard_user");
            password.sendKeys("secret_sauce");
            loginBtn.click();

            Thread.sleep(500);

            String expectedTitle = "Swag Labs";
            String actualTitle = getDriver().getTitle();

            Assert.assertEquals(actualTitle, expectedTitle, "Login failed.");
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoginWithWrongPassword(){
        try {
            getDriver().get(BASE_URL);
            WebElement username = getDriver().findElement(By.id("user-name"));
            WebElement password = getDriver().findElement(By.id("password"));
            WebElement loginBtn = getDriver().findElement(By.id("login-button"));

            username.sendKeys("standard_user");
            password.sendKeys("test");
            loginBtn.click();

            Thread.sleep(500);

            String actualErrorText = getDriver().findElement(By.tagName("h3")).getText();
            String expectedErrorText = "Epic sadface: Username and password do not match any user in this service";

            Assert.assertEquals(actualErrorText, expectedErrorText, "Error message didn't match.");
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
