package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

    public class CreateUser2Test extends BaseTest {

        public static final String USER_NAME = "Username";
        public static final String FULL_NAME = "User Full Name";

        public void goToUserCreatePage(){
            getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
            getDriver().findElement(By.xpath("//a[@href='securityRealm/']")).click();
            getDriver().findElement(By.xpath("//a[@href='addUser']")).click();
        }

        @Test
        public void testVerifyRequiredFields() {
            List<String> expectedListOfRequiredFields = List.of("Username", "Password", "Confirm password", "Full name", "E-mail address");

            goToUserCreatePage();
            List <WebElement> actualListOfRequiredFields = getDriver().findElements(By.xpath("//div[@class='jenkins-form-item tr ']/div[@class='jenkins-form-label help-sibling']"));
            List<String> actualListOfRequiredFieldsText = new ArrayList<>();
            for (int i = 0; i < actualListOfRequiredFields.size(); i++) {
                actualListOfRequiredFieldsText.add(actualListOfRequiredFields.get(i).getText());
            }

            Assert.assertEquals(actualListOfRequiredFieldsText, expectedListOfRequiredFields);
        }

        @Test
        public void testVerifyErrorMessagesEmptyFields() {
            List<String> expectedErrorMessagesText = List.of("\"\" is prohibited as a username for security reasons.",
                    "Password is required",
                    "Password is required",
                    "\"\" is prohibited as a full name for security reasons.",
                    "Invalid e-mail address");

            goToUserCreatePage();
            getDriver().findElement(By.name("Submit")).click();
            List <WebElement> actualListOfErrorMessages = getDriver().findElements(By.xpath("//div[@class='error jenkins-!-margin-bottom-2']"));
            List<String> actualErrorMessagesText = new ArrayList<>();
            for (int i = 0; i < actualListOfErrorMessages.size(); i++) {
                actualErrorMessagesText.add(actualListOfErrorMessages.get(i).getText());
            }

            Assert.assertEquals(actualErrorMessagesText, expectedErrorMessagesText);
        }

        public void createUser(String username, String password, String confirmPassword, String fullName, String eMailAddress){
            getDriver().findElement(By.id("username")).sendKeys(username);
            getDriver().findElement(By.name("password1")).sendKeys(password);
            getDriver().findElement(By.name("password2")).sendKeys(confirmPassword);
            getDriver().findElement(By.name("fullname")).sendKeys(fullName);
            getDriver().findElement(By.name("email")).sendKeys(eMailAddress);
            getDriver().findElement(By.name("Submit")).click();
        }
        @Test
        public void testVerifyUserCreated() {
            String password = "qwerty";
            String confirmPassword = "qwerty";
            String eMailAddress = "user@mail.com";
            String usersPageTitleActual = "Users";

            goToUserCreatePage();
            createUser(USER_NAME, password, confirmPassword, FULL_NAME, eMailAddress);

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), usersPageTitleActual);
        Assert.assertTrue(getDriver().findElement(By.id("people")).getText().contains(USER_NAME) && getDriver().findElement(By.id("people")).getText().contains(FULL_NAME));
    }

        @Test(dependsOnMethods = "testVerifyUserCreated")
        public void testVerifyUserIdButton(){
            getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
            getDriver().findElement(By.xpath("//a[@href='securityRealm/']")).click();

            getDriver().findElement(By.xpath("//table[@id='people']//td/a[text()='" + USER_NAME+ "']")).click();
            String titleOfUserPageActual = getDriver().findElement(By.tagName("h1")).getText();

            Assert.assertEquals(titleOfUserPageActual, FULL_NAME);
            Assert.assertTrue(getDriver().findElement(By.id("main-panel")).getText().contains("Jenkins User ID: " + USER_NAME));
        }

        @Test(dependsOnMethods = "testVerifyUserCreated")
        public void testVerifyUserConfigurationButton(){
            getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
            getDriver().findElement(By.xpath("//a[@href='securityRealm/']")).click();

            getDriver().findElement(By.xpath("//a[contains(@href, '" + USER_NAME.toLowerCase() + "/configure')]")).click();
            String breadcrumbTrailLastSectionText = getDriver().findElement(By.cssSelector("#breadcrumbs li:last-child")).getText();

            Assert.assertTrue(getDriver().getCurrentUrl().contains(USER_NAME.toLowerCase() + "/configure"));
            Assert.assertEquals(breadcrumbTrailLastSectionText, "Configure");
        }
}


