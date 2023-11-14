package school.redrover;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class User2Test extends BaseTest {

    private static final String USER_NAME = "Jane";

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
        final String password = "Te5t";
        final String email = "test_redrov@yahoo.com";

        createUser(USER_NAME, password, email);

        getDriver().findElement(By.xpath("//span[contains(text(), 'log out')]")).click();

        getDriver().findElement(By.name("j_username")).sendKeys(USER_NAME);
        getDriver().findElement(By.name("j_password")).sendKeys(password);
        getDriver().findElement(By.name("Submit")).click();

        String userIconText = getDriver().findElement(By.xpath("//a[contains(@href,'user')]")).getText();
        assertEquals(userIconText, USER_NAME);
    }

    @Test
    public void testDeleteUserAndLogIn() {
        final String password = "te5t";
        final String email = "test_redrov@yahoo.com";

        createUser(USER_NAME, password, email);
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//a[@href='/asynchPeople/']")).click();

        getDriver().findElement(By.xpath(String.format("//a[@href='/user/%s/']", USER_NAME.toLowerCase()))).click();

        getDriver().findElement(By.xpath("//span[contains(text(),'Delete')]")).click();
        Alert alert = getDriver().switchTo().alert();
        alert.accept();

        getDriver().findElement(By.xpath("//span[contains(text(),'log out')]")).click();

        getDriver().findElement(By.name("j_username")).sendKeys(USER_NAME);
        getDriver().findElement(By.name("j_password")).sendKeys(password);
        getDriver().findElement(By.name("Submit")).click();

        String errorText = getDriver().findElement(By.className("app-sign-in-register__error")).getText();
        assertEquals(errorText, "Invalid username or password");
    }

    @Test
    public void testCreateUserAndCheckOnUserDatabase() {
        final String password = "Te5t";
        final String email = "test_redrov@yahoo.com";

        createUser(USER_NAME, password, email);

        assertTrue(getDriver().findElement(By.xpath(String.format("//a[@href='user/%s/']", USER_NAME.toLowerCase()))).isDisplayed());
    }

    @Test(dependsOnMethods = {"testCreateUserAndCheckOnUserDatabase"})
    public void testSetDefaultUserView() {
        final String viewName = USER_NAME + "view";

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("name")).sendKeys("projectName");
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//a[@href='/asynchPeople/']")).click();
        getDriver().findElement(By.xpath(String.format("//a[@href='/user/%s/']", USER_NAME.toLowerCase()))).click();
        getDriver().findElement(By.xpath(String.format("//a[@href='/user/%s/my-views']", USER_NAME.toLowerCase()))).click();
        getDriver().findElement(By.className("addTab")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys(viewName);
        getDriver().findElement(By.xpath("//label[@for='hudson.model.MyView']")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//a[@href='/asynchPeople/']")).click();
        getDriver().findElement(By.xpath(String.format("//a[@href='/user/%s/']", USER_NAME.toLowerCase()))).click();
        getDriver().findElement(By.cssSelector("a[href*='configure']")).click();
        getDriver().findElement(By.name("_.primaryViewName")).sendKeys(viewName);
        getDriver().findElement(By.xpath("//button[@name='Apply']")).click();

        WebElement myViews = getDriver().findElement(By.cssSelector("a[href*='my-views']"));
        new Actions(getDriver())
                .scrollToElement(myViews)
                .perform();
        myViews.click();

        String activeTabName = getDriver().findElement(By.xpath("//div[@class='tab active']")).getText();
        assertEquals(activeTabName, viewName);
    }
}
