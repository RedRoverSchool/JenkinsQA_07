package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class User4Test extends BaseTest {
    private final static String JENKINS_HOME_ID = "jenkins-home-link";
    private final static String MANAGE_JENKINS_ELEMENT = "//a[@href = '/manage']";
    private final static String SECURITY_ELEMENT = "//a[@href = 'securityRealm/']";
    private final static String ADD_USER_ELEMENT = "//a[@href = 'addUser']";


    @Test
    public void testUserCreation() {
        final String username = "testUser";
        final String password = "1";
        final String email = "test@test.com";

        getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href = 'securityRealm/']")).click();
        getDriver().findElement(By.xpath("//a[@href = 'addUser']")).click();
        getDriver().findElement(By.xpath("//input[@name = 'username']")).sendKeys(username);
        getDriver().findElement(By.xpath("//input[@name = 'password1']")).sendKeys(password);
        getDriver().findElement(By.xpath("//input[@name = 'password2']")).sendKeys(password);
        getDriver().findElement(By.xpath("//input[@name = 'email']")).sendKeys(email);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        List<WebElement> listOfUserID = getDriver().findElements(By.xpath(
                "//td/a[@class = 'jenkins-table__link model-link inside']"));

        Assert.assertFalse(listOfUserID.isEmpty());

        boolean isNewUserIDShown = false;
        for (WebElement webElement : listOfUserID) {
            if (webElement.getText().contains(username)) {
                isNewUserIDShown = true;
                break;
            }
        }

        Assert.assertTrue(isNewUserIDShown);
    }

    @Test
    public void testFullNameAppearsSameAsUserID() {
        final String username = AdditionalUtils.generateRandomName();
        final String password = AdditionalUtils.generateRandomPassword(12);
        final String email = AdditionalUtils.generateRandomName() + "@" + "mail.com";

        getDriver().findElement(By.xpath(MANAGE_JENKINS_ELEMENT)).click();
        getDriver().findElement(By.xpath(SECURITY_ELEMENT)).click();
        getDriver().findElement(By.xpath(ADD_USER_ELEMENT)).click();

        getDriver().findElement(By.name("username")).sendKeys(username);
        getDriver().findElement(By.name("password1")).sendKeys(password);
        getDriver().findElement(By.name("password2")).sendKeys(password);
        getDriver().findElement(By.name("email")).sendKeys(email);
        getDriver().findElement(By.name("Submit")).click();

        String name = getDriver().findElement(By.xpath("(//td/a[@href='user/" + username + "/']/following::td[1])"))
                .getText();

        assertEquals(name, username);
    }
}
