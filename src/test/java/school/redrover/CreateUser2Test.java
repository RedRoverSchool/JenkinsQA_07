package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

    public class CreateUser2Test extends BaseTest {

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
}


