package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class User4Test extends BaseTest {

    @Test
    public void testVerifyRequiredFields() {
        List<String> expectedLabelNames = List.of("Username", "Password", "Confirm password", "Full name", "E-mail address");
        List<String> actualLabelNames = new ArrayList<>();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href = '/manage']"))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='securityRealm/']"))).click();
        getDriver().findElement(By.cssSelector("a[href='addUser']")).click();

        for (String labelName : expectedLabelNames) {
            String labelText = getDriver().findElement(By.xpath("//div[text() = '" + labelName + "']")).getText();
            actualLabelNames.add(labelText);
            WebElement input = getDriver().findElement
                    (By.xpath("//div[@class='jenkins-form-label help-sibling'][text() = '" + labelName + "']" +
                            "/following-sibling::div/input"));

            Assert.assertNotNull(input);
        }

        Assert.assertEquals(expectedLabelNames, actualLabelNames);
    }


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
    public void testShowingValidationMessages() {
        getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href = 'securityRealm/']")).click();
        getDriver().findElement(By.xpath("//a[@href = 'addUser']")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        List<WebElement> listOfValidationMessages = getDriver().findElements(By.xpath(
                "//div[@class = 'error jenkins-!-margin-bottom-2']"));

        Assert.assertFalse(listOfValidationMessages.isEmpty());
    }
}
