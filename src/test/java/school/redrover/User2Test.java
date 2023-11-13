package school.redrover;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class User2Test extends BaseTest {
    private void createUser(String userName, String password, String email) {
        getDriver().findElement(By.xpath("//a[contains(@href,'manage')]")).click();

        getDriver().findElement(By.xpath("//dt[contains(text(),'Users')]")).click();

        getDriver().findElement(By.xpath("//a[@href='addUser']")).click();

        getDriver().findElement(By.name("username")).sendKeys(userName);
        getDriver().findElement(By.name("password1")).sendKeys(password);
        getDriver().findElement(By.name("password2")).sendKeys(password);
        getDriver().findElement(By.name("email")).sendKeys(email);
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testCreateUserAndLogIn() {
        final String userName = "Test";
        final String password = "Te5t";
        final String email = "test_redrov@yahoo.com";

        createUser(userName, password, email);

        getDriver().findElement(By.xpath("//span[contains(text(), 'log out')]")).click();

        getDriver().findElement(By.name("j_username")).sendKeys(userName);
        getDriver().findElement(By.name("j_password")).sendKeys(password);
        getDriver().findElement(By.name("Submit")).click();

        String userIconText = getDriver().findElement(By.xpath("//a[contains(@href,'user')]")).getText();
        assertEquals(userIconText, userName);
    }

    @Test
    public void testDeleteUserAndLogIn() {
        final String userName = "NewUser";
        final String password = "te5t";
        final String email = "test_redrov@yahoo.com";

        createUser(userName, password, email);
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//a[@href='/asynchPeople/']")).click();

        getDriver().findElement(By.xpath(String.format("//a[@href='/user/%s/']", userName.toLowerCase()))).click();

        getDriver().findElement(By.xpath("//span[contains(text(),'Delete')]")).click();
        Alert alert = getDriver().switchTo().alert();
        alert.accept();

        getDriver().findElement(By.xpath("//span[contains(text(),'log out')]")).click();

        getDriver().findElement(By.name("j_username")).sendKeys(userName);
        getDriver().findElement(By.name("j_password")).sendKeys(password);
        getDriver().findElement(By.name("Submit")).click();

        String errorText = getDriver().findElement(By.className("app-sign-in-register__error")).getText();
        assertEquals(errorText, "Invalid username or password");
    }

    @Test
    public void testCreateUserAndCheckOnUserDatabase() {
        final String userName = "Jane";
        final String password = "Te5t";
        final String email = "test_redrov@yahoo.com";

        createUser(userName, password, email);

        assertTrue(getDriver().findElement(By.xpath(String.format("//a[@href='user/%s/']", userName.toLowerCase()))).isDisplayed());
    }
}
