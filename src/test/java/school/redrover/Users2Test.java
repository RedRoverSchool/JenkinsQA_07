package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class Users2Test extends BaseTest {

    private void goToUsersTab() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='securityRealm/']")).click();
    }

    private static final String USER_VALID_NAME = "USER_NAME_Test";

    @Test
    public void testCreateUserValidData() {
        goToUsersTab();

        getDriver().findElement(By.xpath("//a[@href='addUser']")).click();

        getDriver().findElement(By.id("username")).sendKeys(USER_VALID_NAME);
        getDriver().findElement(By.xpath("//input[@name='password1']")).sendKeys("123456_Test");
        getDriver().findElement(By.xpath("//input[@name='password2']")).sendKeys("123456_Test");
        getDriver().findElement(By.xpath("//input[@name='fullname']")).sendKeys("TestName");
        getDriver().findElement(By.xpath("//input[@name='email']")).sendKeys("Test@mail.ru");

        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//a[@href='user/user_name_test/']")).getText(), USER_VALID_NAME);
    }

    @Test
    public void testCreateUserEmptyName(){
        goToUsersTab();

        getDriver().findElement(By.xpath("//a[@href='addUser']")).click();

        getDriver().findElement(By.xpath("//input[@name='password1']")).sendKeys("Test_Test");
        getDriver().findElement(By.xpath("//input[@name='password2']")).sendKeys("Test_Test");
        getDriver().findElement(By.xpath("//input[@name='fullname']")).sendKeys("TestName");
        getDriver().findElement(By.xpath("//input[@name='email']")).sendKeys("Test@mail.ru");

        getDriver().findElement(By.xpath("//div[@id='bottom-sticker']/div/button")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//div[@id='main-panel']/form/div[1]/div[2]")).getText(), "\"\" is prohibited as a username for security reasons.");
    }

    @Test
    public void testCreateUserDifferentPassword() {
        goToUsersTab();

        getDriver().findElement(By.xpath("//a[@href='addUser']")).click();

        getDriver().findElement(By.id("username")).sendKeys("USER_NAME_Test");
        getDriver().findElement(By.name("password1")).sendKeys("123456_Test");
        getDriver().findElement(By.name("password2")).sendKeys("123456");
        getDriver().findElement(By.name("fullname")).sendKeys("TestName");
        getDriver().findElement(By.name("email")).sendKeys("Test@mail.ru");

        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//div[@id='main-panel']/form/div[1]/div[3]")).getText(), "Password didn't match");
        Assert.assertEquals(getDriver().findElement(
                By.xpath("//div[@id='main-panel']/form/div[1]/div[5]")).getText(), "Password didn't match");
    }

    @Test (dependsOnMethods = "testCreateUserValidData")
    public void testDeleteUserDropDown() {
        goToUsersTab();

        WebElement moveToUserName = getDriver().findElement(By.xpath("//table[@id='people']/tbody/tr[2]/td[2]/a"));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(moveToUserName).pause(1000).perform();

        WebElement moveToArrow = getDriver().findElement(By.cssSelector("#people > tbody > tr:nth-child(2) > td:nth-child(2) > a > button"));
        actions.sendKeys(moveToArrow, Keys.ENTER).pause(1000).click().perform();

        WebElement clickDelete = getDriver().findElement(By.xpath("//button[@href='/user/" + USER_VALID_NAME.toLowerCase() + "/doDelete']"));

        actions.moveToElement(clickDelete).pause(1000).click().perform();

        getDriver().switchTo().alert().accept();

        goToUsersTab();

        List<WebElement> usersList = getDriver().findElements(By.xpath("//a[@href='user/" + USER_VALID_NAME.toLowerCase() + "/']"));

        Assert.assertEquals(usersList.size(), 0);
    }
}

