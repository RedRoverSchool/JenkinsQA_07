package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewUserTest extends BaseTest {
    private static final By MANAGE_JENKINS = By.xpath("//a[@href='/manage']");
    private static final By MANAGE_USERS = By.xpath("//dd[contains(text(),'Create')]");
    private static final By CREATE_USERS = By.xpath("//a[contains(text(),'Create')]");
    private static final By USERNAME_INPUT = By.id("username");
    private static final By PASSWORD_INPUT = (By.name("password1"));
    private static final By CONFIRM_PASSWORD_INPUT = (By.name("password2"));
    private static final By FULLNAME_INPUT = (By.name("fullname"));
    private static final By EMAIL_INPUT = (By.name("email"));
    private static final By CREATE_USER_CONFIRM_BUTTON = (By.name("Submit"));
    @Test
    public void testCreateWithValidName() {

        WebElement manageJenkins = getDriver().findElement(MANAGE_JENKINS);
        manageJenkins.click();
        WebElement manageUsers = getDriver().findElement(MANAGE_USERS);
        manageUsers.click();
        WebElement createUser = getDriver().findElement(CREATE_USERS);
        createUser.click();
        WebElement userName = getDriver().findElement(USERNAME_INPUT);
        userName.sendKeys("test_user");
        WebElement password = getDriver().findElement(PASSWORD_INPUT);
        password.sendKeys("test_password");
        WebElement confirmPassword = getDriver().findElement(CONFIRM_PASSWORD_INPUT);
        confirmPassword.sendKeys("test_password");
        WebElement fullName = getDriver().findElement(FULLNAME_INPUT);
        fullName.sendKeys("test_user");
        WebElement email = getDriver().findElement(EMAIL_INPUT);
        email.sendKeys("test_user@gmail.com");
        WebElement createUserButton = getDriver().findElement(CREATE_USER_CONFIRM_BUTTON);
        createUserButton.click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//tbody/tr[2]/td[2]/a[1]")).getText(), "test_user");
    }
}
