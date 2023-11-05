package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreateUserTest extends BaseTest {

    private void goToUsersTab() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='securityRealm/']")).click();
        getDriver().findElement(By.xpath("//a[@href='addUser']")).click();
    }

    @Test
    public void testCreateUserValidData() {
        goToUsersTab();

        getDriver().findElement(By.id("username")).sendKeys("USER_NAME_Test");
        getDriver().findElement(By.xpath("//input[@name='password1']")).sendKeys("123456_Test");
        getDriver().findElement(By.xpath("//input[@name='password2']")).sendKeys("123456_Test");
        getDriver().findElement(By.xpath("//input[@name='fullname']")).sendKeys("TestName");
        getDriver().findElement(By.xpath("//input[@name='email']")).sendKeys("Test@mail.ru");

        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//a[@href='user/user_name_test/']")).getText(), "USER_NAME_Test");
    }

    @Test
    public void testCreateUserEmptyName(){
        goToUsersTab();

        getDriver().findElement(By.xpath("//input[@name='password1']")).sendKeys("Test_Test");
        getDriver().findElement(By.xpath("//input[@name='password2']")).sendKeys("Test_Test");
        getDriver().findElement(By.xpath("//input[@name='fullname']")).sendKeys("TestName");
        getDriver().findElement(By.xpath("//input[@name='email']")).sendKeys("Test@mail.ru");

        getDriver().findElement(By.xpath("//div[@id='bottom-sticker']/div/button")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//div[@id='main-panel']/form/div[1]/div[2]")).getText(), "\"\" is prohibited as a username for security reasons.");
    }
}
